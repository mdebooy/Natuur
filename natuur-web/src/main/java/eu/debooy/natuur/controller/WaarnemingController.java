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
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.form.Waarneming;
import eu.debooy.natuur.validator.WaarnemingValidator;
import java.util.ArrayList;
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
      LoggerFactory.getLogger(GebiedController.class);

  private Waarneming    waarneming;
  private WaarnemingDto waarnemingDto;

  public void create(Long taxonId) {
    GebiedDto gebied  = getGebiedService().gebied(
        Long.valueOf(getParameter("natuur.default.gebiedid")));
    TaxonDto  taxon   = getTaxonService().taxon(taxonId);
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

  public void delete(Long waarnemingId) {
    try {
      waarneming =
          new Waarneming(getWaarnemingService().waarneming(waarnemingId));
      getWaarnemingService().delete(waarnemingId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, waarnemingId);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED,
            Datum.fromDate(waarneming.getDatum()));
  }

  public String formateerDatum(Date datum) {
    return Datum.fromDate(datum);
  }

  public List<Foto> getFotos() {
    List<Foto>  fotos = new ArrayList<>();

    waarnemingDto.getFotos().forEach(foto -> fotos.add(new Foto(foto)));

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

  public List<Waarneming> getTaxonWaarnemingen(Long taxonId) {
    List<Waarneming>  resultaat;

    try {
      resultaat = getWaarnemingService().getTaxonWaarnemingen(taxonId);
    } catch (Exception e) {
      addError("errors.geen.i18n", e.getClass());
      resultaat = new ArrayList<>();
    }

    return resultaat;
  }

  public Waarneming getWaarneming() {
    return waarneming;
  }

  public List<Waarneming> getWaarnemingen() {
    List<Waarneming>  resultaat;

    try {
      resultaat = getWaarnemingService().query(getGebruikersTaal());
    } catch (Exception e) {
      addError("errors.geen.i18n", e.getClass());
      resultaat = new ArrayList<>();
    }

    return resultaat;
  }

  public void save() {
    List<Message> messages  = WaarnemingValidator.valideer(waarneming);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    String  melding = formateerDatum(waarneming.getDatum()) + " "
                      + waarneming.getTaxon().getNaam();
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
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
    }
  }

  public void update(Long waarnemingId) {
    waarnemingDto = getWaarnemingService().waarneming(waarnemingId);
    waarneming    = new Waarneming(waarnemingDto, getGebruikersTaal());
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.waarneming.update");
    redirect(WAARNEMING_REDIRECT);
  }

  public void waarnemingenlijst() {
    ExportData  exportData  = new ExportData();

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

    HttpServletResponse response  =
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
