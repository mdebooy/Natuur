/**
 * Copyright (c) 2016 Marco de Booij
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
package eu.debooy.natuur.controller;

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.form.Taxonnaam;
import eu.debooy.natuur.validator.TaxonValidator;
import eu.debooy.natuur.validator.TaxonnaamValidator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurTaxon")
@SessionScoped
public class TaxonController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(TaxonController.class);

  private static final  String  KINDEREN_TAB      = "kinderenTab";
  private static final  String  NAMEN_TAB         = "namenTab";

  private String        aktieveTab;
  private Taxon         ouder;
  private Long          ouderNiveau;
  private Taxon         taxon;
  private TaxonDto      taxonDto;
  private Taxonnaam     taxonnaam;

  public void bepaalOuder(Long parentId) {
    if (null == parentId) {
      ouder       = new Taxon();
      ouderNiveau = Long.valueOf(0);
    } else {
      ouder       = new Taxon(getTaxonService().taxon(parentId),
                              getGebruikersTaal());
      ouderNiveau = getRangService().rang(ouder.getRang()).getNiveau();
    }
  }

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    ExternalContext ec      = FacesContext.getCurrentInstance()
                                          .getExternalContext();

    aktieveTab  = KINDEREN_TAB;
    taxon       = new Taxon();
    taxonDto    = new TaxonDto();
    if (ec.getRequestParameterMap().containsKey(TaxonDto.COL_TAXONID)) {
      var parentId  = Long.valueOf(ec.getRequestParameterMap()
                                     .get(TaxonDto.COL_TAXONID));
      var ouderRang = getTaxonService().taxon(parentId).getRang();
      ouderNiveau   = getRangService().rang(ouderRang).getNiveau();
    } else {
      ouderNiveau = Long.valueOf(0);
    }
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst("natuur.titel.taxon.create"));
    redirect(TAXON_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    aktieveTab    = NAMEN_TAB;
    taxonnaam     = new Taxonnaam();
    taxonnaam.setTaal(getGebruikersTaal());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel("natuur.titel.taxonnaam.create");
    redirect(TAXONNAAM_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      getTaxonService().delete(taxon.getTaxonId());
      addInfo(PersistenceConstants.DELETED, taxon.getNaam());
      redirect(TAXA_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxon.getTaxonId());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void deleteDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      taxonDto.removeTaxonnaam(taxonnaam.getTaal());
      getTaxonService().save(taxonDto);
      addInfo(PersistenceConstants.DELETED, "'" + taxonnaam.getTaal() + "'");
      redirect(TAXON_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public String getAktieveTab() {
    return aktieveTab;
  }

  public Taxon getOuder() {
    return ouder;
  }

  public Long getOuderNiveau() {
    return ouderNiveau;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Taxonnaam getTaxonnaam() {
    return taxonnaam;
  }

  public JSONArray getTaxonnamen() {
    JSONArray taxonnamen  = new JSONArray();

    taxonDto.getTaxonnamen().forEach(rij -> taxonnamen.add(rij.toJSON()));

    return taxonnamen;
  }

  public void retrieve() {
    if (!isUser() && !isView()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    ExternalContext ec      = FacesContext.getCurrentInstance()
                                          .getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(TaxonDto.COL_TAXONID)) {
      addError(ComponentsConstants.GEENPARAMETER, TaxonDto.COL_TAXONID);
      return;
    }

    Long            taxonId = Long.valueOf(ec.getRequestParameterMap()
                                             .get(TaxonDto.COL_TAXONID));

    aktieveTab  = KINDEREN_TAB;
    taxonDto    = getTaxonService().taxon(taxonId);
    taxon       = new Taxon(taxonDto, getGebruikersTaal());
    bepaalOuder(taxon.getParentId());
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(taxon.getNaam());
    redirect(TAXON_REDIRECT);
  }

  public void retrieveDetail() {
    if (!isUser() && !isView()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    ExternalContext ec      = FacesContext.getCurrentInstance()
                                          .getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(TaxonnaamDto.COL_TAAL)) {
      addError(ComponentsConstants.GEENPARAMETER, TaxonnaamDto.COL_TAAL);
      return;
    }

    aktieveTab  = NAMEN_TAB;
    taxonnaam   =
        new Taxonnaam(taxonDto.getTaxonnaam(ec.getRequestParameterMap()
                                              .get(TaxonnaamDto.COL_TAAL)));
    setDetailAktie(PersistenceConstants.RETRIEVE);
    setDetailSubTitel("natuur.titel.taxonnaam.retrieve");

    redirect(TAXONNAAM_REDIRECT);
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = TaxonValidator.valideer(taxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      taxon.persist(taxonDto);
      getTaxonService().save(taxonDto);
      aktieveTab  = KINDEREN_TAB;
      bepaalOuder(taxon.getParentId());
      var naam    = taxon.getNaam();
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          taxon.setTaxonId(taxonDto.getTaxonId());
          addInfo(PersistenceConstants.CREATED, naam);
          setAktie(PersistenceConstants.UPDATE);
          setSubTitel(getTekst("natuur.titel.taxon.update", naam));
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, naam);
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxon.getLatijnsenaam());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxon.getNaam());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void saveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = TaxonnaamValidator.valideer(taxonnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    if (getDetailAktie().getAktie() == PersistenceConstants.CREATE
        && taxonDto.hasTaxonnaam(taxonnaam.getTaal())) {
      addError(PersistenceConstants.DUPLICATE, taxonnaam.getTaal());
      return;
    }

    try {
      var taxonnaamDto  = new TaxonnaamDto();
      taxonnaam.persist(taxonnaamDto);
      taxonDto.addNaam(taxonnaamDto);
      if (getGebruikersTaal().equals(taxonnaam.getTaal())) {
        taxon.setNaam(taxonnaam.getNaam());
        setSubTitel(getTekst("natuur.titel.taxon.update", taxonnaam.getNaam()));
      }
      getTaxonService().save(taxonDto);
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          addInfo(PersistenceConstants.CREATED,
                  "'" + taxonnaam.getTaal() + "'");
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED,
                  "'" + taxonnaam.getTaal() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie()) ;
          break;
      }
      redirect(TAXON_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxonnaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public List<SelectItem> selectOuders(String rang) {
    Long              niveau;
    if (DoosUtils.isBlankOrNull(rang)) {
      niveau  = Long.valueOf("1000");
    } else {
      niveau  = getRangService().rang(rang).getNiveau();
    }
    List<SelectItem>  items = new LinkedList<>();
    Set<TaxonDto>     rijen = new TreeSet<>(new TaxonDto.NaamComparator());
    rijen.addAll(getTaxonService().getOuders(niveau));
    rijen.forEach(rij ->
      items.add(new SelectItem(rij.getTaxonId(),
                               rij.getNaam(getGebruikersTaal()) + " ("
                                + rij.getLatijnsenaam() + ")")));

    return items;
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    aktieveTab  = KINDEREN_TAB;
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst("natuur.titel.taxon.update", taxon.getNaam()));
  }

  public void updateDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    aktieveTab    = NAMEN_TAB;
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("natuur.titel.taxonnaam.update");
  }
}
