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

import eu.debooy.doosutils.Aktie;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Rangnaam;
import eu.debooy.natuur.validator.RangValidator;
import eu.debooy.natuur.validator.RangnaamValidator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurRang")
@SessionScoped
public class RangController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(RangController.class);

  private static final  String  DTIT_CREATE   = "natuur.titel.rangnaam.create";
  private static final  String  DTIT_DELETE   = "natuur.titel.rangnaam.delete";
  private static final  String  DTIT_RETRIEVE =
      "natuur.titel.rangnaam.retrieve";
  private static final  String  DTIT_UPDATE   =
      "natuur.titel.rangnaam.update";
  private static final  String  TIT_CREATE    = "natuur.titel.rang.create";
  private static final  String  TIT_GEENFOTOS = "natuur.titel.geenfotos";
  private static final  String  TIT_UPDATE    = "natuur.titel.rang.update";

  private final Aktie       geenFotoAktie = new Aktie();

  private TaxonDto    geenFotos;
  private Rang        rang;
  private RangDto     rangDto;
  private Rangnaam    rangnaam;
  private RangnaamDto rangnaamDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    rang    = new Rang();
    rangDto = new RangDto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(RANG_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    rangnaam    = new Rangnaam();
    rangnaam.setTaal(getGebruikersTaalInIso6392t());
    rangnaam.setRang(rang.getRang());
    rangnaamDto = new RangnaamDto();
    rangnaam.persist(rangnaamDto);
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(getTekst(DTIT_CREATE, rang.getNaam()));
    redirect(RANGNAAM_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      getRangService().delete(rang.getRang());
      addInfo(PersistenceConstants.DELETED, this.rang.getNaam());
      rang        = new Rang();
      rangDto     = new RangDto();
      rangnaam    = new Rangnaam();
      rangnaamDto = new RangnaamDto();
      redirect(RANGEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang);
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

    var taal      = rangnaam.getTaal();
    try {
      rangDto.removeRangnaam(taal);
      getRangService().save(rangDto);
      rangnaamDto = new RangnaamDto();
      addInfo(PersistenceConstants.DELETED, "'" + taal + "'");
      if (getGebruikersTaalInIso6392t().equals(rangnaam.getTaal())) {
        rang.setNaam(null);
        if (getAktie().isWijzig()) {
          setSubTitel(getTekst(TIT_UPDATE, rang.getNaam()));
        } else {
          setSubTitel(rang.getNaam());
        }
      }
      rangnaam    = new Rangnaam();
      redirect(RANG_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taal);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Aktie getGeenFotosAktie() {
    return geenFotoAktie;
  }

  public String getDeleteTitel() {
    return getTekst(DTIT_DELETE, rang.getNaam());
  }

  public Long getGeenFotosTaxonId() {
    return geenFotos.getTaxonId();
  }

  public String getGeenFotosTitel() {
    return getTekst(TIT_GEENFOTOS,
                    geenFotos.getNaam(getGebruikersTaalInIso6392t()));
  }

  public Rang getRang() {
    return rang;
  }

  public Rangnaam getRangnaam() {
    return rangnaam;
  }

  public JSONArray getRangnamen() {
    var rangnamen = new JSONArray();

    rangDto.getRangnamen().forEach(rij -> rangnamen.add(rij.toJSON()));

    return rangnamen;
  }

  public String getRangtekst(String rang) {
    return getRangService().rang(rang).getNaam(getGebruikersTaalInIso6392t());
  }

  public List<SelectItem> getSelectRangen() {
    List<SelectItem>  items = new ArrayList<>();

    getRangService().query().stream().sorted(new Rang.NiveauComparator())
                    .forEachOrdered(rij ->
                        items.add(new SelectItem(rij.getRang(),
                                  getRangtekst(rij.getRang()))));

    return items;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec    = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(RangDto.COL_RANG)) {
      addError(ComponentsConstants.GEENPARAMETER, RangDto.COL_RANG);
      return;
    }

    try {
      rangDto = getRangService().rang(ec.getRequestParameterMap()
                                        .get(RangDto.COL_RANG));
      rang    = new Rang(rangDto, getGebruikersTaalInIso6392t());
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(rang.getNaam());
      redirect(RANG_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_RANG));
    }
  }

  public void retrieveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec  = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(RangnaamDto.COL_TAAL)) {
      addError(ComponentsConstants.GEENPARAMETER, RangnaamDto.COL_TAAL);
      return;
    }

    try {
      rangnaamDto  = rangDto.getRangnaam(ec.getRequestParameterMap()
                                           .get(RangnaamDto.COL_TAAL));
      rangnaam     = new Rangnaam(rangnaamDto);
      setDetailAktie(PersistenceConstants.RETRIEVE);
      setDetailSubTitel(getTekst(DTIT_RETRIEVE, rang.getNaam()));
      redirect(RANGNAAM_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_TAAL));
    }
  }

  public void retrieveGeenFotos() {
    if (!isUser() && !isView()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(TaxonDto.COL_TAXONID)) {
      addError(ComponentsConstants.GEENPARAMETER, TaxonDto.COL_TAXONID);
      return;
    }

    var taxonId = Long.valueOf(ec.getRequestParameterMap()
                                 .get(TaxonDto.COL_TAXONID));

    try {
      geenFotos = getTaxonService().taxon(taxonId);
      redirect(GEENFOTOS_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_TAXON));
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = RangValidator.valideer(rang);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          getRangService().save(rang);
          addInfo(PersistenceConstants.CREATED, "'" + rang.getRang() + "'");
          rangDto = getRangService().rang(rang.getRang());
          update();
          break;
        case PersistenceConstants.UPDATE:
          rang.persist(rangDto);
          getRangService().save(rangDto);
          addInfo(PersistenceConstants.UPDATED, "'" + rang.getRang() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, rang.getRang());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang.getRang());
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

    var messages  = RangnaamValidator.valideer(rangnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    if (getDetailAktie().getAktie() == PersistenceConstants.CREATE
        && rangDto.hasRangnaam(rangnaam.getTaal())) {
      addError(PersistenceConstants.DUPLICATE, rangnaam.getTaal());
      return;
    }

    var taal  = rangnaam.getTaal();
    try {
      rangnaamDto  = new RangnaamDto();
      rangnaam.persist(rangnaamDto);
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          rangDto.addNaam(rangnaamDto);
          getRangService().save(rangDto);
          if (getGebruikersTaalInIso6392t().equals(taal)) {
            rang.setNaam(rangDto.getNaam(taal));
            setSubTitel(getTekst(TIT_UPDATE, rang.getNaam()));
          }
          setDetailAktie(PersistenceConstants.RETRIEVE);
          addInfo(PersistenceConstants.CREATED, "'" + rangnaam.getTaal() + "'");
          break;
        case PersistenceConstants.UPDATE:
          rangDto.addNaam(rangnaamDto);
          getRangService().save(rangDto);
          if (getGebruikersTaalInIso6392t().equals(taal)) {
            rang.setNaam(rangDto.getNaam(taal));
            setSubTitel(getTekst(TIT_UPDATE, rang.getNaam()));
          }
          setDetailAktie(PersistenceConstants.RETRIEVE);
          addInfo(PersistenceConstants.UPDATED, "'" + rangnaam.getTaal() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie());
          break;
      }
      redirect(RANG_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, rangnaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rangnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public List<SelectItem> selectRangen(Long niveau) {
    List<SelectItem>  items = new LinkedList<>();

    getRangService().query(niveau)
                    .forEach(rij ->  items.add(new SelectItem(rij.getRang(),
                                               getRangtekst(rij.getRang()))));

    return items;
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE, rang.getNaam()));
  }

  public void updateDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel(getTekst(DTIT_UPDATE, rang.getNaam()));
  }
}
