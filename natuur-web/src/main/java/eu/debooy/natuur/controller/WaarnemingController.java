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

import eu.debooy.doos.component.Export;
import eu.debooy.doos.model.ExportData;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.form.Waarneming;
import eu.debooy.natuur.validator.FotoValidator;
import eu.debooy.natuur.validator.WaarnemingValidator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurWaarneming")
@SessionScoped
public class WaarnemingController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(WaarnemingController.class);

  private static final  String  DTIT_CREATE   = "natuur.titel.foto.create";
  private static final  String  DTIT_RETRIEVE = "natuur.titel.foto.retrieve";
  private static final  String  DTIT_UPDATE   = "natuur.titel.foto.update";
  private static final  String  TIT_CREATE    =
      "natuur.titel.waarneming.create";
  private static final  String  TIT_RETRIEVE  =
      "natuur.titel.waarneming.retrieve";
  private static final  String  TIT_UPDATE    =
      "natuur.titel.waarneming.update";

  private Foto          foto;
  private FotoDto       fotoDto;
  private Waarneming    waarneming;
  private WaarnemingDto waarnemingDto;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var   ec        = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(TaxonDto.COL_TAXONID)) {
      addError(ComponentsConstants.GEENPARAMETER, TaxonDto.COL_TAXONID);
      return;
    }

    var taxonId     = Long.valueOf(ec.getRequestParameterMap()
                                   .get(TaxonDto.COL_TAXONID));

    try {
      var gebied    = getGebiedService().gebied(
                          Long.valueOf(getParameter(DEF_GEBIEDID)));
      var taxon     = getTaxonService().taxon(taxonId);
      waarnemingDto = new WaarnemingDto();
      waarnemingDto.setTaxonId(taxon.getTaxonId());
      waarnemingDto.setDatum(new Date());
      waarnemingDto.setGebied(gebied);
      waarneming    = new Waarneming(waarnemingDto, getGebruikersTaal());
      setAktie(PersistenceConstants.CREATE);
      setSubTitel(getTekst(TIT_CREATE));
      redirect(WAARNEMING_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_PARAMETERS));
    }
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    foto    = new Foto();
    fotoDto = new FotoDto();
    foto.setWaarnemingId(waarneming.getWaarnemingId());
    foto.persist(fotoDto);
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(getTekst(DTIT_CREATE));
    redirect(WNMFOTO_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var waarnemingId = waarneming.getWaarnemingId();
    try {
      getWaarnemingService().delete(waarnemingId);
      waarneming    = new Waarneming();
      waarnemingDto = new WaarnemingDto();
      addInfo(PersistenceConstants.DELETED,
              formateerDatum(waarneming.getDatum()));
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, waarnemingId);
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

    var taxonSeq = foto.getTaxonSeq();
    try {
      waarnemingDto.removeFoto(taxonSeq);
      getWaarnemingService().save(waarnemingDto);
      foto    = new Foto();
      fotoDto = new FotoDto();
      addInfo(PersistenceConstants.DELETED, "'" + taxonSeq + "'");
      redirect(WAARNEMING_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonSeq);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public String formateerDatum(Date datum) {
    return Datum.fromDate(datum, getTekst("kalender.datum.formaat"));
  }

  public Foto getFoto() {
    return foto;
  }

  public JSONArray getFotos() {
    var fotos = new JSONArray();

    waarnemingDto.getFotos().forEach(rij -> fotos.add(rij.toJSON()));

    return fotos;
  }

  public List<SelectItem> getSelectWaarnemingen() {
    List<SelectItem>  items = new LinkedList<>();
    Set<Taxon>        rijen = new TreeSet<>(new Taxon.NaamComparator());
    rijen.addAll(getDetailService().getSoortenMetKlasse(getGebruikersTaal()));
    rijen.forEach(rij ->
      items.add(new SelectItem(rij,
                               rij.getNaam()
                                       + " (" + rij.getLatijnsenaam() + ")")));

    return items;
  }

  public Waarneming getWaarneming() {
    return waarneming;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec            = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap()
           .containsKey(WaarnemingDto.COL_WAARNEMINGID)) {
      addError(ComponentsConstants.GEENPARAMETER,
               WaarnemingDto.COL_WAARNEMINGID);
      return;
    }

    var waarnemingId  =
        Long.valueOf(ec.getRequestParameterMap()
                       .get(WaarnemingDto.COL_WAARNEMINGID));

    try {
      waarnemingDto = getWaarnemingService().waarneming(waarnemingId);
      waarneming    = new Waarneming(waarnemingDto, getGebruikersTaal());
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTekst(TIT_RETRIEVE));
      redirect(WAARNEMING_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_WAARNEMING));
    }
  }

  public void retrieveDetail() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap()
           .containsKey(FotoDto.COL_FOTOID)) {
      addError(ComponentsConstants.GEENPARAMETER, FotoDto.COL_FOTOID);
      return;
    }

    var fotoId  =
        Long.valueOf(ec.getRequestParameterMap()
                       .get(FotoDto.COL_FOTOID));

    try {
      fotoDto = getFotoService().foto(fotoId);
      foto    = new Foto(fotoDto);
      setDetailAktie(PersistenceConstants.RETRIEVE);
      setDetailSubTitel(getTekst(DTIT_RETRIEVE));
      redirect(FOTO_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_FOTO));
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = WaarnemingValidator.valideer(waarneming);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var datum = formateerDatum(waarneming.getDatum());
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          waarneming.setGebied(
              new Gebied(getGebiedService().gebied(
                      waarneming.getGebied().getGebiedId())));
          waarneming.persist(waarnemingDto);
          getWaarnemingService().save(waarnemingDto);
          waarneming.setWaarnemingId(waarnemingDto.getWaarnemingId());
          addInfo(PersistenceConstants.CREATED, datum);
          break;
        case PersistenceConstants.UPDATE:
          waarneming.setGebied(
              new Gebied(getGebiedService().gebied(
                      waarneming.getGebied().getGebiedId())));
          waarneming.persist(waarnemingDto);
          getWaarnemingService().save(waarnemingDto);
          addInfo(PersistenceConstants.UPDATED, datum);
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
      redirect(TAXON_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, datum);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, datum);
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

    var messages      = FotoValidator.valideer(foto);
    var fotoOverzicht =
            getFotoService().fotoTaxonSeq(waarnemingDto.getTaxon().getTaxonId(),
                                          foto.getTaxonSeq());
    if (null != fotoOverzicht.getFotoId()
            && !fotoOverzicht.getFotoId().equals(foto.getFotoId())) {
          messages.add(new Message.Builder()
                            .setAttribute(FotoDto.COL_TAXONSEQ)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.DUPLICATE)
                            .setParams(new Object[]{foto.getTaxonSeq()})
                            .build());
    }

    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var taxonSeq  = foto.getTaxonSeq();
    try {
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          foto.persist(fotoDto);
          getFotoService().save(fotoDto);
          waarnemingDto.addFoto(fotoDto);
          addInfo(PersistenceConstants.CREATED, "'" + taxonSeq + "'");
          break;
        case PersistenceConstants.UPDATE:
          foto.persist(fotoDto);
          getFotoService().save(fotoDto);
          waarnemingDto.addFoto(fotoDto);
          addInfo(PersistenceConstants.UPDATED, "'" + taxonSeq + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie()) ;
          break;
      }
      redirect(WAARNEMING_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxonSeq);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonSeq);
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
    setSubTitel(getTekst(TIT_UPDATE));
  }

  public void updateDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel(getTekst(DTIT_UPDATE));
  }

  public void waarnemingenlijst() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "waarnemingen");
    exportData.setParameters(getLijstParameters());

    exportData.setKolommen(new String[] { "parentNaam", "parentLatijnsenaam",
                                          "naam", "latijnsenaam" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.waarnemingen"));

    Set<Taxon> rijen = new TreeSet<>(new Taxon.LijstComparator());
    rijen.addAll(getDetailService().getWaargenomen(getGebruikersTaal()));
    rijen.forEach(rij ->
      exportData.addData(new String[] {rij.getParentNaam(),
                                       rij.getParentLatijnsenaam(),
                                       rij.getNaam(),
                                       rij.getLatijnsenaam()}));

    var response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
      FacesContext.getCurrentInstance().responseComplete();
    } catch (IllegalArgumentException | TechnicalException e) {
      generateExceptionMessage(e);
    }
  }
}
