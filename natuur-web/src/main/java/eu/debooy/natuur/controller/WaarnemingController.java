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
import javax.faces.context.ExternalContext;
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

  private Foto          foto;
  private FotoDto       fotoDto;
  private Waarneming    waarneming;
  private WaarnemingDto waarnemingDto;

  public void create(Long taxonId) {
    var gebied  = getGebiedService().gebied(
            Long.valueOf(getParameter("natuur.default.gebiedid")));
    var taxon   = getTaxonService().taxon(taxonId);
    waarnemingDto = new WaarnemingDto();
    waarnemingDto.setTaxon(taxon);
    waarnemingDto.setDatum(new Date());
    waarnemingDto.setGebied(gebied);
    waarneming    = new Waarneming();
    waarneming.setDatum(waarnemingDto.getDatum());
    waarneming.setGebied(new Gebied(gebied));
    waarneming.setTaxon(new Taxon(taxon, getGebruikersTaal()));
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.waarneming.create");
    redirect(WAARNEMING_REDIRECT);
  }

  public void createFoto() {
    foto    = new Foto();
    fotoDto = new FotoDto();
    foto.setWaarnemingId(waarneming.getWaarnemingId());
    fotoDto.setWaarnemingId(waarnemingDto.getWaarnemingId());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel("natuur.titel.foto.create");
    redirect(WNMFOTO_REDIRECT);
  }

  public void delete(Long waarnemingId) {
    try {
      getWaarnemingService().delete(waarnemingId);
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

  public void deleteFoto(Long taxonSeq) {
    try {
      waarnemingDto.removeFoto(taxonSeq);
      getWaarnemingService().save(waarnemingDto);
      addInfo(PersistenceConstants.DELETED, "'" + taxonSeq + "'");
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
    JSONArray fotos = new JSONArray();

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
    ExternalContext ec            =
        FacesContext.getCurrentInstance().getExternalContext();
    Long            waarnemingId  =
        Long.valueOf(ec.getRequestParameterMap()
                       .get(WaarnemingDto.COL_WAARNEMINGID));

    waarnemingDto = getWaarnemingService().waarneming(waarnemingId);
    waarneming    = new Waarneming(waarnemingDto, getGebruikersTaal());
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel("natuur.titel.waarneming.retrieve");
    redirect(WAARNEMING_REDIRECT);
  }

  public void save() {
    var messages  = WaarnemingValidator.valideer(waarneming);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var melding = formateerDatum(waarneming.getDatum()) + " "
                    + waarneming.getGebied().getNaam();
    try {
      waarneming.persist(waarnemingDto);
      getWaarnemingService().save(waarnemingDto);
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          waarneming.setWaarnemingId(waarnemingDto.getWaarnemingId());
          addInfo(PersistenceConstants.CREATED, melding);
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, melding);
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
      redirect(TAXON_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, melding);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, melding);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void saveFoto() {
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

    try {
      foto.persist(fotoDto);
      getFotoService().save(fotoDto);
      waarnemingDto.addFoto(fotoDto);
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          addInfo(PersistenceConstants.CREATED, "'" + foto.getTaxonSeq()+ "'");
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, "'" + foto.getTaxonSeq() + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie()) ;
          break;
      }
      redirect(WAARNEMING_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, foto.getTaxonSeq());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, foto.getTaxonSeq());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void update() {
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.waarneming.update");
  }

  public void updateDetail() {
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("natuur.titel.foto.update");
  }

  public void waarnemingenlijst() {
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
