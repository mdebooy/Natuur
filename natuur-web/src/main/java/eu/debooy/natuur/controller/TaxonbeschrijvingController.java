/*
 * Copyright (c) 2025 Marco de Booij
 *  
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
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

import eu.debooy.doos.model.I18nSelectItem;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonbeschrijvingDto;
import eu.debooy.natuur.form.Taxonbeschrijving;
import eu.debooy.natuur.validator.TaxonbeschrijvingValidator;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurTaxonbeschrijving")
@SessionScoped
public class TaxonbeschrijvingController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(TaxonbeschrijvingController.class);


  private static final  String  PAR_TYPES     =
      "natuur.taxon.beschrijving.type";
  private static final  String  TIT_CREATE    =
      "natuur.titel.taxonbeschrijving.create";
  private static final  String  TIT_RETRIEVE  =
      "natuur.titel.taxonbeschrijving.retrieve";
  private static final  String  TIT_UPDATE    =
      "natuur.titel.taxonbeschrijving.update";

  private Taxonbeschrijving     taxonbeschrijving;
  private TaxonbeschrijvingDto  taxonbeschrijvingDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var   ec        = FacesContext.getCurrentInstance().getExternalContext();

    if (!checkEcParameters(ec.getRequestParameterMap(),
                          TaxonDto.COL_TAXONID)) {
      return;
    }

    var taxonId     = Long.valueOf(ec.getRequestParameterMap()
                                   .get(TaxonDto.COL_TAXONID));

    try {
      taxonbeschrijvingDto  = new TaxonbeschrijvingDto();
      taxonbeschrijvingDto.setTaxonId(taxonId);
      taxonbeschrijving     = new Taxonbeschrijving(taxonbeschrijvingDto);
      setAktie(PersistenceConstants.CREATE);
      setSubTitel(getTekst(TIT_CREATE));
      setReturnTo(ec, TAXON_REDIRECT);
      redirect(TAXONBESCHR_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_PARAMETERS));
    }
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      addInfo(PersistenceConstants.DELETED, getDeletetekst());
      getTaxonbeschrijvingService().delete(taxonbeschrijvingDto);
      taxonbeschrijving    = new Taxonbeschrijving();
      taxonbeschrijvingDto = new TaxonbeschrijvingDto();
      redirect(getReturnTo());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getDeletetekst());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public String getBeschrijvingtype() {
    if (null == taxonbeschrijving
        || DoosUtils.isBlankOrNull(taxonbeschrijving.getBeschrijvingtype())) {
      return DoosConstants.NA;
    }

    return getDeletetekst();
  }

  public List<SelectItem> getSelectBeschrijvingtypes() {
    List<SelectItem>  items = new ArrayList<>();

    items.addAll(getI18nLijst(PAR_TYPES, getGebruikersTaalInIso6392t(),
                              new I18nSelectItem.VolgordeComparator()));

    return items;
  }

  public Taxonbeschrijving getTaxonbeschrijving() {
    return taxonbeschrijving;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec                = FacesContext.getCurrentInstance()
                                        .getExternalContext();

    if (!checkEcParameters(ec.getRequestParameterMap(),
                           TaxonbeschrijvingDto.COL_BESCHRIJVINGTYPE,
                           TaxonbeschrijvingDto.COL_TAXONID)) {
      return;
    }

    var beschrijvingtype  = ec.getRequestParameterMap()
                              .get(TaxonbeschrijvingDto.COL_BESCHRIJVINGTYPE);
    var taxonId           =
        Long.valueOf(ec.getRequestParameterMap()
                       .get(TaxonbeschrijvingDto.COL_TAXONID));

    try {
      taxonbeschrijvingDto  =
          getTaxonbeschrijvingService().taxonbeschrijving(taxonId,
                                                          beschrijvingtype);
      taxonbeschrijving     = new Taxonbeschrijving(taxonbeschrijvingDto);
      setAktie(PersistenceConstants.RETRIEVE);
      setDeletetekst(getTekst(String.format(NatuurConstants.FMT_I18NCODE,
                                  PAR_TYPES,
                                  taxonbeschrijving.getBeschrijvingtype())));
      setSubTitel(getTekst(TIT_RETRIEVE));
      setReturnTo(ec, TAXON_REDIRECT);
      redirect(TAXONBESCHR_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_BESCHRIJVING));
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var beschrijvingtype  = taxonbeschrijving.getBeschrijvingtype();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE -> {
          taxonbeschrijving.persist(taxonbeschrijvingDto);
          getTaxonbeschrijvingService().save(taxonbeschrijvingDto);
          addInfo(PersistenceConstants.CREATED, beschrijvingtype);
          update();
        }
        case PersistenceConstants.UPDATE -> {
          taxonbeschrijving.persist(taxonbeschrijvingDto);
          getTaxonbeschrijvingService().update(taxonbeschrijvingDto);
          addInfo(PersistenceConstants.UPDATED, beschrijvingtype);
        }
        default -> addError(ComponentsConstants.WRONGREDIRECT,
                            getAktie().getAktie());
      }
      redirect(TAXON_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, beschrijvingtype);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, beschrijvingtype);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setDeletetekst(getTekst(String.format(NatuurConstants.FMT_I18NCODE,
                                PAR_TYPES,
                                taxonbeschrijving.getBeschrijvingtype())));
    setSubTitel(getTekst(TIT_UPDATE));
  }
}
