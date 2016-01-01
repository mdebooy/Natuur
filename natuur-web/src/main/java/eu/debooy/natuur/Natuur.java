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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.components.bean.DoosBean;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.service.DetailService;
import eu.debooy.natuur.service.FotoService;
import eu.debooy.natuur.service.GebiedService;
import eu.debooy.natuur.service.RangService;
import eu.debooy.natuur.service.TaxonService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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

  private Foto      foto;
  private Gebied    gebied;
  private Rang      rang;
  private Taxon     taxon;

  private transient DetailService detailService;
  private transient FotoService   fotoService;
  private transient GebiedService gebiedService;
  private transient RangService   rangService;
  private transient TaxonService  taxonService;

  public static final String  ADMIN_ROLE        = "natuur-admin";
  public static final String  APPLICATIE_NAAM   = "Natuur";
  public static final String  FOTO_REDIRECT     = "/fotos/foto.jsf";
  public static final String  FOTOS_REDIRECT    = "/fotos/fotos.jsf";
  public static final String  GEBIED_REDIRECT   = "/gebieden/gebied.jsf";
  public static final String  GEBIEDEN_REDIRECT = "/gebieden/gebieden.jsf";
  public static final String  RANG_REDIRECT     = "/rangen/rang.jsf";
  public static final String  RANGEN_REDIRECT   = "/rangen/rangen.jsf";
  public static final String  TAXON_REDIRECT    = "/taxa/taxon.jsf";
  public static final String  TAXA_REDIRECT     = "/taxa/taxa.jsf";
  public static final String  USER_ROLE         = "natuur-user";

  public Natuur() {
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    logger.debug("Nieuwe Natuur Sessie geopend.");
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
   * Geef alle gebieden als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectWaarnemingen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    List<DetailDto>   rijen = getDetailService().getSoortenMetKlasse();
    Collections.sort(rijen, new DetailDto.NaamComparator());
    for (DetailDto detailDto : rijen) {
      items.add(new SelectItem(detailDto.getTaxonId(), detailDto.getNaam()));
    }

    return items;
  }

  /**
   * Geef de lijst met waarnemingen.
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
        new LinkedList<DetailDto>(getDetailService().getSoortenMetKlasse());
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

  // Fotos
  /**
   * Geef de geselecteerde foto.
   * 
   * @return Foto
   */
  public Foto getFoto() {
    return foto;
  }

  /**
   * Geef de lijst met fotos.
   * 
   * @return List<Foto> met Gebied objecten.
   */
  public List<Foto> getFotos() {
    return getFotoService().lijst();
  }

  /**
   * Geef de FotoService. Als die nog niet gekend is haal het dan op.
   * 
   * @return FotoService
   */
  private FotoService getFotoService() {
    if (null == fotoService) {
      fotoService = (FotoService)
          new JNDI.JNDINaam().metBean(FotoService.class).locate();
    }

    return fotoService;
  }

  /**
   * Prepareer een nieuwe Foto.
   */
  public void nieuweFoto() {
    foto  = new Foto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  /**
   * Persist de Foto
   * 
   * @param Foto
   */
  public void saveFoto() {
    List<Message> messages  = getFotoService().valideer(foto);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    getFotoService().save(foto);
    redirect(FOTOS_REDIRECT);
  }

  /**
   * Verwijder de Foto
   * 
   * @param Foto
   */
  public void verwijderFoto(Foto foto) {
  }

  /**
   * Zet de Foto die gewijzigd gaat worden klaar.
   * 
   * @param Long gebiedId
   */
  public void wijzigFoto(Long taxonId, Long taxonSeq) {
    foto  = new Foto(getFotoService().foto(taxonId, taxonSeq));
    logger.debug(foto.toString());
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.foto.update");
    redirect(FOTO_REDIRECT);
  }

  // Gebieden
  /**
   * Geef het geselecteerde gebied.
   * 
   * @return Gebied
   */
  public Gebied getGebied() {
    return gebied;
  }

  /**
   * Geef de lijst met gebieden.
   * 
   * @return List<Gebied> met Gebied objecten.
   */
  public List<Gebied> getGebieden() {
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
   * Geef alle gebieden als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectGebieden() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    List<Gebied>      rijen = getGebiedService().lijst();
    Collections.sort(rijen, new Gebied.NaamComparator());
    for (Gebied gebied : rijen) {
      items.add(new SelectItem(gebied.getGebiedId(), gebied.getNaam()));
    }

    return items;
  }

  /**
   * Prepareer een nieuw gebied.
   */
  public void nieuwGebied() {
    gebied  = new Gebied();
    gebied.setLandId(Long.parseLong(getParameter("natuur.default.landid")));
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

  // Rangen
  /**
   * Geef de geselecteerde rang.
   * 
   * @return Rang
   */
  public Rang getRang() {
    return rang;
  }

  /**
   * Geef de lijst met rangen.
   * 
   * @return List<Rang> met Gebied objecten.
   */
  public List<Rang> getRangen() {
    return getRangService().lijst();
  }

  /**
   * Geef de RangService. Als die nog niet gekend is haal het dan op.
   * 
   * @return RangService
   */
  private RangService getRangService() {
    if (null == rangService) {
      rangService = (RangService)
          new JNDI.JNDINaam().metBean(RangService.class).locate();
    }

    return rangService;
  }

  /**
   * Geef alle rangen als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectRangen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    List<Rang>        rijen = getRangService().lijst();
    Collections.sort(rijen, new Rang.NiveauComparator());
    for (Rang rang : rijen) {
      items.add(new SelectItem(rang.getRang(),
                               getTekst("biologie.rang." + rang.getRang())));
    }

    return items;
  }

  /**
   * Prepareer een nieuw gebied.
   */
  public void nieuweRang() {
    rang  = new Rang();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.rang.create");
    redirect(RANG_REDIRECT);
  }

  /**
   * Persist de Rang
   * 
   * @param Rang
   */
  public void saveRang() {
    List<Message> messages  = getRangService().valideer(rang);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    getGebiedService().save(gebied);
    redirect(RANGEN_REDIRECT);
  }

  /**
   * Verwijder de Rang
   * 
   * @param Rang
   */
  public void verwijderRang(Rang rang) {
  }

  /**
   * Zet de Rang die gewijzigd gaat worden klaar.
   * 
   * @param String rang
   */
  public void wijzigRang(String rang) {
    this.rang = new Rang(getRangService().rang(rang));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.rang.update");
    redirect(RANG_REDIRECT);
  }

  // Taxa
  /**
   * Zet het Taxon dat gevraagd is klaar.
   * 
   * @param Long taxonId
   */
  public void bekijkTaxon(Long taxonId) {
    taxon = new Taxon(getTaxonService().taxon(taxonId));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(taxon.getNaam());
    redirect(TAXON_REDIRECT);
  }

  /**
   * Geef de lijst met kinderen van de taxon.
   * 
   * @return List<Taxon> met Taxon objecten.
   */
  public List<Taxon> getKinderen(Long parentId) {
    return getTaxonService().getKinderen(parentId);
  }

  /**
   * Geef de lijst met taxa.
   * 
   * @return List<Taxon> met Taxon objecten.
   */
  public List<Taxon> getTaxa() {
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
  public void nieuweTaxon() {
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
   * Geef alle taxa als SelectItems.
   * 
   * @return List<SelectItem>
   */
  public List<SelectItem> selectOuders(String rang) {
    Long              niveau;
    if (DoosUtils.isBlankOrNull(rang)) {
      niveau  = Long.valueOf("1000");
    } else {
      niveau  = getRangService().rang(rang).getNiveau();
    }
    List<SelectItem>  items   = new LinkedList<SelectItem>();
    List<TaxonDto>    rijen   =
        new LinkedList<TaxonDto>(getTaxonService().getOuders(niveau));
    Collections.sort(rijen, new TaxonDto.NaamComparator());
    for (TaxonDto taxonDto : rijen) {
      items.add(new SelectItem(taxonDto.getTaxonId(),
                               taxonDto.getNaam() + " ("
                                   + taxonDto.getLatijnsenaam() + ")"));
    }

    return items;
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
