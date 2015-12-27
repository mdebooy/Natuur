/**
 * Copyright 2015 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.natuur;

import eu.debooy.doos.component.Export;
import eu.debooy.doos.model.ExportData;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.components.bean.DoosBean;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.service.DetailService;
import eu.debooy.natuur.service.GebiedService;
import eu.debooy.natuur.service.TaxonService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuur")
@SessionScoped
public class Natuur extends DoosBean {
  private static final  long    serialVersionUID  = 1L;

  private static  Logger  logger  =
      LoggerFactory.getLogger(Natuur.class);

  private Gebied    gebied;
  private Taxon     taxon;

  private transient DetailService detailService;
  private transient GebiedService gebiedService;
  private transient TaxonService  taxonService;

  public static final String  ADMIN_ROLE        = "natuur-admin";
  public static final String  APPLICATIE_NAAM   = "Natuur";
  public static final String  GEBIED_REDIRECT   = "/gebieden/gebied.jsf";
  public static final String  GEBIEDEN_REDIRECT = "/gebieden/gebieden.jsf";
  public static final String  TAXON_REDIRECT    = "/taxa/taxon.jsf";
  public static final String  TAXA_REDIRECT     = "/taxa/taxa.jsf";
  public static final String  USER_ROLE         = "natuur-user";

  public Natuur() {
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    logger.debug("Nieuwe Sessie geopend.");
  }

  // Details
  /**
   * Geef de DetailService. Als die nog niet gekend is haal het dan op.
   * 
   * @return DetailService
   */
  private DetailService getDetailService() {
    if (null == detailService) {
      detailService  = (DetailService)
          new JNDI.JNDINaam().metBean(DetailService.class).locate();
    }

    return detailService;
  }

  /**
   * Geef de lijst met soorten/waarnemingen.
   * 
   * @return List<DetailDto> met DetailDto objecten.
   */
  public List<DetailDto> getSoorten() {
    return getDetailService().getSoortenMetKlasse();
  }

  /**
   * Raport met Waarnemingen.
   */
  public void waarnemingen() {
    ExportData  exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      "Marco DE BOOIJ");
    exportData.addMetadata("lijstnaam",   "waarnemingen");
    exportData.setKleuren(getLijstKleuren());

    exportData.setKolommen(new String[] { "parentNaam", "parentLatijnsenaam",
                                          "naam", "latijnsenaam" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.waarnemingen"));

    List<DetailDto> waarnemingen  =
        new ArrayList<DetailDto>(getDetailService().getSoortenMetKlasse());
    Collections.sort(waarnemingen, new DetailDto.LijstComparator());
    for (DetailDto waarneming : waarnemingen) {
      exportData.addData(new String[] {waarneming.getParentNaam(),
                                       waarneming.getParentLatijnsenaam(),
                                       waarneming.getNaam(),
                                       waarneming.getLatijnsenaam()});

    }

    HttpServletResponse response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
    } catch (IllegalArgumentException e) {
      generateExceptionMessage(e);
      return;
    } catch (TechnicalException e) {
      generateExceptionMessage(e);
      return;
    }

    FacesContext.getCurrentInstance().responseComplete();
  }

  // Gebieden
  /**
   * Zet het Gebied dat gevraagd is klaar.
   * 
   * @param Long gebiedId
   */
  public void bekijkGebied(Long gebiedId) {
    gebied  = new Gebied(getGebiedService().gebied(gebiedId));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(gebied.getNaam());
    redirect(GEBIED_REDIRECT);
  }

  /**
   * Geef het geselecteerde gebied.
   * 
   * @return Gebied
   */
  public Gebied getGebied() {
    return gebied;
  }

  /**
   * Geef de lijst met gebieds.
   * 
   * @return Set<Gebied> met Gebied objecten.
   */
  public Set<Gebied> getGebieden() {
    return getGebiedService().lijst();
  }

  /**
   * Geef de GebiedService. Als die nog niet gekend is haal het dan op.
   * 
   * @return GebiedService
   */
  private GebiedService getGebiedService() {
    if (null == gebiedService) {
      gebiedService = (GebiedService)
          new JNDI.JNDINaam().metBean(GebiedService.class).locate();
    }

    return gebiedService;
  }

  /**
   * Prepareer een nieuw gebied.
   */
  public void nieuwGebied() {
    gebied  = new Gebied();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.gebied.create");
    redirect(GEBIED_REDIRECT);
  }

  /**
   * Persist het Gebied
   * 
   * @param Gebied
   */
  public void saveGebied() {
    List<Message> messages  = getGebiedService().valideer(gebied);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    getGebiedService().save(gebied);
    redirect(GEBIEDEN_REDIRECT);
  }

  /**
   * Verwijder het Gebied
   * 
   * @param Gebied
   */
  public void verwijderGebied(Gebied gebied) {
  }

  /**
   * Zet de Gebied die gewijzigd gaat worden klaar.
   * 
   * @param Long gebiedId
   */
  public void wijzigGebied(Long gebiedId) {
    gebied  = new Gebied(getGebiedService().gebied(gebiedId));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.gebied.update");
    redirect(GEBIED_REDIRECT);
  }

  // Taxa
  /**
   * Zet het Gebied dat gevraagd is klaar.
   * 
   * @param Long gebiedId
   */
  public void bekijkTaxon(Long taxonId) {
    taxon = new Taxon(getTaxonService().taxon(taxonId));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(taxon.getNaam());
    redirect(TAXON_REDIRECT);
  }

  /**
   * Geef de lijst met taxa.
   * 
   * @return Set<Taxon> met Taxon objecten.
   */
  public Set<Taxon> getTaxa() {
    return getTaxonService().lijst();
  }

  /**
   * Geef het geselecteerde taxon.
   * 
   * @return Taxon
   */
  public Taxon getTaxon() {
    return taxon;
  }

  /**
   * Geef de TaxonService. Als die nog niet gekend is haal het dan op.
   * 
   * @return TaxonService
   */
  private TaxonService getTaxonService() {
    if (null == taxonService) {
      taxonService  = (TaxonService)
          new JNDI.JNDINaam().metBean(TaxonService.class).locate();
    }

    return taxonService;
  }

  /**
   * Prepareer een nieuw taxon.
   */
  public void nieuwTaxon() {
    taxon     = new Taxon();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Persist het Taxon
   * 
   * @param Taxon
   */
  public void saveTaxon() {
    List<Message> messages  = getTaxonService().valideer(taxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    getTaxonService().save(taxon);
    redirect(TAXA_REDIRECT);
  }

  /**
   * Verwijder het Taxon
   * 
   * @param Taxon
   */
  public void verwijderTaxon(Taxon taxon) {
  }

  /**
   * Zet de Taxon die gewijzigd gaat worden klaar.
   * 
   * @param Long
   */
  public void wijzigTaxon(Long taxonId) {
    taxon     = new Taxon(getTaxonService().taxon(taxonId));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.taxon.update");
    redirect(TAXON_REDIRECT);
  }
}
