/*
 * Copyright (c) 2023 Marco de Booij
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

import eu.debooy.doos.component.Export;
import eu.debooy.doos.component.business.IDoosRemote;
import eu.debooy.doos.model.ExportData;
import eu.debooy.doos.model.I18nSelectItem;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.NatuurUtils;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.domain.RegiolijstTaxonPK;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Regiolijst;
import eu.debooy.natuur.form.RegiolijstTaxon;
import eu.debooy.natuur.form.Regiolijstparameter;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.validator.RegiolijstTaxonValidator;
import eu.debooy.natuur.validator.RegiolijstValidator;
import eu.debooy.natuur.validator.RegiolijstparameterValidator;
import eu.debooy.sedes.component.entity.Regio;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurRegiolijst")
@SessionScoped
public class RegiolijstController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(RegiolijstController.class);

  private static final  String  DTIT_CREATE   =
      "natuur.titel.regiolijsttaxon.create";
  private static final  String  DTIT_DELETE   =
      "natuur.titel.regiolijsttaxon.delete";
  private static final  String  DTIT_UPDATE   =
      "natuur.titel.regiolijsttaxon.update";
  private static final  String  DTIT_UPLOAD   =
      "natuur.titel.regiolijst.upload";
  private static final  String  FMT_TITEL     = "%s (%s)";
  private static final  String  TIT_CREATE    =
      "natuur.titel.regiolijst.create";
  private static final  String  TIT_RETRIEVE  =
      "natuur.titel.regiolijst.retrieve";
  private static final  String  TIT_UPDATE    =
      "natuur.titel.regiolijst.update";

  private final JSONArray           dubbel                = new JSONArray();
  private final JSONArray           nieuw                 = new JSONArray();
  private final JSONArray           onbekend              = new JSONArray();
  private final Regiolijstparameter regiolijstparameters  =
      new Regiolijstparameter();
  private final List<SelectItem>    statusses             = new LinkedList<>();

  @EJB
  private transient IDoosRemote   doosRemote;
  private transient Part          bestand;

  private Regio               regio;
  private Regiolijst          regiolijst;
  private RegiolijstDto       regiolijstDto;
  private RegiolijstTaxon     regiolijstTaxon;
  private RegiolijstTaxonDto  regiolijstTaxonDto;

  public void batch() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    dubbel.clear();
    nieuw.clear();
    onbekend.clear();

    setDetailSubTitel(getTekst(DTIT_UPLOAD,
                               regio.getNaam(), regiolijst.getPeriode()));
    redirect(REGIOLIJSTUPLOAD_REDIRECT);
  }

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regiolijst    = new Regiolijst();
    regiolijstDto = new RegiolijstDto();
    regiolijst.setStartdatum(new Date());
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    setReturnTo(getExternalContext(), REGIOLIJSTEN_REDIRECT);
    redirect(REGIOLIJST_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regiolijstTaxon     = new RegiolijstTaxon();
    regiolijstTaxonDto  = new RegiolijstTaxonDto();
    regiolijstTaxon.setRegiolijstId(regiolijst.getRegiolijstId());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(getTekst(DTIT_CREATE,
                               regio.getNaam(), regiolijst.getPeriode()));
    redirect(REGIOLIJSTTAXON_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var naam  = String.format(FMT_TITEL,
                              regio.getNaam(), regiolijst.getPeriode());
    try {
      getRegiolijstService().delete(regiolijst.getRegiolijstId());
      addInfo(PersistenceConstants.DELETED, naam);
      regiolijst      = new Regiolijst();
      regiolijstDto   = new RegiolijstDto();
      regiolijstTaxon = new RegiolijstTaxon();
      redirect(REGIOLIJSTEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
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

    var naam  = regiolijstTaxon.getTaxon().getNaam();
    try {
      var sleutel     = new RegiolijstTaxonPK(regiolijstTaxon.getRegiolijstId(),
                                              regiolijstTaxon.getTaxonId());
      getRegiolijstTaxonService().delete(sleutel);
      regiolijstTaxon     = new RegiolijstTaxon();
      regiolijstTaxonDto  = new RegiolijstTaxonDto();
      addInfo(PersistenceConstants.DELETED, naam);
      redirect(REGIOLIJST_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public int getAantalDubbel() {
    return dubbel.size();
  }

  public int getAantalNieuw() {
    return nieuw.size();
  }

  public int getAantalOnbekend() {
    return onbekend.size();
  }

  public Part getBestand() {
    return bestand;
  }

  public JSONArray getDubbel() {
    return dubbel;
  }

  /**
   * Indien geen einddatum dan de dag voor de startdatum gebruiken.
   * @return '
   */
  public String getEinddatum() {
    return Datum.fromDate(
        DoosUtils.nullToValue(regiolijst.getEinddatum(),
                              new Date(regiolijst.getStartdatum()
                                                 .getTime() - 86400000)));
  }

  private String getGezien(boolean gezien, boolean opFoto) {
    if (opFoto) {
      return NatuurUtils.getCamera(true);
    }

    return NatuurUtils.getBoolean(gezien);
  }

  public JSONArray getNieuw() {
    return nieuw;
  }

  public JSONArray getOnbekend() {
    return onbekend;
  }

  public Regio getRegio() {
    return regio;
  }

  public Regiolijstparameter getParameters() {
    return regiolijstparameters;
  }

  public Regiolijst getRegiolijst() {
    return regiolijst;
  }

  public RegiolijstTaxon getRegiolijstTaxon() {
    return regiolijstTaxon;
  }

  public String getStartdatum() {
    return Datum.fromDate(regiolijst.getStartdatum());
  }

  public Collection<SelectItem> getStatussen() {
    if (statusses.isEmpty()) {
      statusses.add(new SelectItem(" ", "--"));
      var rijen = getI18nLijst(STATUSSEN, getGebruikersTaal(),
                               new I18nSelectItem.WaardeComparator());
      rijen.forEach(rij ->
        statusses.add(new SelectItem(rij.getValue(),
                                     String.format(
                                         NatuurConstants.FMT_NAAMLATIJNSENAAM,
                                                   rij.getLabel(),
                                                   rij.getValue()
                                                      .toString()
                                                      .toUpperCase())))
      );
    }

    return statusses;
  }

  public boolean isGelezen() {
    return dubbel.size() + nieuw.size() + onbekend.size() > 0;
  }

  public void parameters() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regiolijstparameters.setTaal1(getParameter(NatuurConstants.PAR_LIJSTTAAL
                                                + "1"));
    regiolijstparameters.setTaal2(getParameter(NatuurConstants.PAR_LIJSTTAAL
                                                + "2"));
    regiolijstparameters.setTaal3(getParameter(NatuurConstants.PAR_LIJSTTAAL
                                                + "3"));

    redirect(REGIOLIJSTPARAMS_REDIRECT);
  }

  public void regiolijst() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = RegiolijstparameterValidator.valideer(regiolijstparameters);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    var exportData  = new ExportData();
    var taal1       = regiolijstparameters.getTaal1();
    var taal2       = regiolijstparameters.getTaal2();
    var taal3       = regiolijstparameters.getTaal3();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "regiolijst");
    exportData.setParameters(getLijstParameters());

    exportData.setKolommen(new String[] { "klasse", "gezien",
                                          TaxonDto.COL_LATIJNSENAAM,
                                          "taal1", "taal2", "taal3" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst(TIT_RETRIEVE,
                                regio.getNaam(), regiolijst.getPeriode()));
    exportData.addVeld("LabelLatijnsenaam", getTekst("label.latijnsenaam"));
    exportData.addVeld("LabelTaal1",        doosRemote.getIso6392tNaam(taal1,
                                                                       taal1));
    exportData.addVeld("LabelTaal2",        doosRemote.getIso6392tNaam(taal2,
                                                                       taal2));
    exportData.addVeld("LabelTaal3",        doosRemote.getIso6392tNaam(taal3,
                                                                       taal3));

    Set<DetailDto>  rijen = new TreeSet<>(new DetailDto.LijstComparator());
    rijen.addAll(getDetailService()
                    .getVanRegiolijst(regiolijst.getRegiolijstId()));
    rijen.forEach(rij ->
      exportData.addData(
          new String[] {NatuurUtils.getSubtitel(rij.getParentLatijnsenaam(),
                                                rij.getParentUitgestorven(),
                                                rij.getParentnaam(taal1),
                                                rij.getParentnaam(taal2),
                                                rij.getParentnaam(taal3)),
                        getGezien(rij.isGezien(), rij.isOpFoto()),
                        NatuurUtils.getLatijnsenaam(rij.getLatijnsenaam(),
                                                    rij.isUitgestorven()),
                        NatuurUtils.getNaam(rij, taal1),
                        NatuurUtils.getNaam(rij, taal2),
                        NatuurUtils.getNaam(rij, taal3)})
    );

    var response  = (HttpServletResponse) getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
      FacesContext.getCurrentInstance().responseComplete();
    } catch (IllegalArgumentException | TechnicalException e) {
      generateExceptionMessage(e);
    }
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = getExternalContext();

    if (!checkEcParameters(ec.getRequestParameterMap(),
                           RegiolijstDto.COL_REGIOLIJSTID)) {
      return;
    }

    var sleutel = Long.valueOf(ec.getRequestParameterMap()
                                 .get(RegiolijstDto.COL_REGIOLIJSTID));

    try {
      regiolijstDto = getRegiolijstService().regiolijst(sleutel);
      regiolijst    = new Regiolijst(regiolijstDto);
      setRegio(regiolijst.getRegioId());
      setAktie(PersistenceConstants.RETRIEVE);
      setDeletetekst(String.format(FMT_TITEL,
                                   regio.getNaam(), regiolijst.getPeriode()));
      setSubTitel(getTekst(TIT_RETRIEVE,
                           regio.getNaam(), regiolijst.getPeriode()));
      setReturnTo(ec, REGIOLIJSTEN_REDIRECT);
      redirect(REGIOLIJST_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_REGIOLIJST));
    }
  }

  public void retrieveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec          = getExternalContext();

    if (!checkEcParameters(ec.getRequestParameterMap(),
                           RegiolijstTaxonDto.COL_TAXONID)) {
      return;
    }

    var taxonId     = Long.valueOf(ec.getRequestParameterMap()
                                     .get(RegiolijstTaxonDto.COL_TAXONID));

    try {
      regiolijstTaxonDto  =
          getRegiolijstTaxonService()
              .regiolijstTaxon(regiolijst.getRegiolijstId(), taxonId);
      regiolijstTaxon     = new RegiolijstTaxon(regiolijstTaxonDto,
                                                getGebruikersTaalInIso6392t());
      setDetailAktie(PersistenceConstants.UPDATE);
      setDetailDeletetekst(regiolijstTaxon.getTaxon().getNaam());
      setDetailDeletetitel(getTekst(DTIT_DELETE,
                                    regio.getNaam(), regiolijst.getPeriode()));
      setDetailSubTitel(getTekst(DTIT_UPDATE,
                                 regio.getNaam(), regiolijst.getPeriode()));

      redirect(REGIOLIJSTTAXON_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_TAXON));
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = RegiolijstValidator.valideer(regiolijst);
    if (!messages.isEmpty()) {
      var informatief = addMessage(messages);
      if (informatief != messages.size()) {
        return;
      }
    }

    setRegio(regiolijst.getRegioId());
    var naam  = String.format(FMT_TITEL,
                              regio.getNaam(), regiolijst.getPeriode());
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE -> {
          regiolijst.persist(regiolijstDto);
          getRegiolijstService().save(regiolijstDto);
          regiolijst.setRegiolijstId(regiolijstDto.getRegiolijstId());
          addInfo(PersistenceConstants.CREATED, naam);
          update();
        }
        case PersistenceConstants.UPDATE -> {
          regiolijst.persist(regiolijstDto);
          getRegiolijstService().update(regiolijstDto);
          addInfo(PersistenceConstants.UPDATED, naam);
        }
        default -> addError(ComponentsConstants.WRONGREDIRECT,
                            getAktie().getAktie()) ;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, naam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
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

    var messages  = RegiolijstTaxonValidator.valideer(regiolijstTaxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    if (getDetailAktie().getAktie() == PersistenceConstants.CREATE) {
      try {
        getRegiolijstTaxonService()
                      .regiolijstTaxon(regiolijstTaxon.getRegiolijstId(),
                                       regiolijstTaxon.getTaxonId());
        addError(PersistenceConstants.DUPLICATE,
                  (getTaxonService().taxon(
                      regiolijstTaxon.getTaxonId())
                                     .getNaam(getGebruikersTaalInIso6392t())));
        return;
      } catch (ObjectNotFoundException e) {
        // OK. Mag niet aanwezig zijn.
      }
    }

    if (null == regiolijstTaxon.getTaxon()) {
      regiolijstTaxon.setTaxon(
          new Taxon(getTaxonService().taxon(regiolijstTaxon.getTaxonId()),
                    getGebruikersTaalInIso6392t()));
    }

    var naam  = regiolijstTaxon.getTaxon().getNaam();
    try {
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE -> {
          regiolijstTaxon.persist(regiolijstTaxonDto);
          getRegiolijstTaxonService().save(regiolijstTaxonDto);
          regiolijstTaxon.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId());
          addInfo(PersistenceConstants.CREATED, naam);
        }
        case PersistenceConstants.UPDATE -> {
          regiolijstTaxon.persist(regiolijstTaxonDto);
          getRegiolijstTaxonService().update(regiolijstTaxonDto);
          addInfo(PersistenceConstants.UPDATED, naam);
        }
        default -> addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie());
      }
      redirect(REGIOLIJST_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, naam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void setBestand(Part bestand) {
    this.bestand  = bestand;
  }

  protected void setRegio(Long regioId) {
    if (null == regio
        || !regioId.equals(regio.getRegioId())) {
      regio = getSedesRemote().getRegio(regioId, getGebruikersTaalInIso6392t());
    }
  }

  public String status(String status) {
    if (DoosUtils.isBlankOrNull(status)) {
      return "";
    }

    return getTekst(STATUSSEN + "." + status);
  }

  public void synchroniseer() {
    if (DoosUtils.isBlankOrNull(regiolijstDto.getEinddatum())) {
      addInfo("message.geen.periode");
      return;
    }

    List<Long>  inLijst       = new ArrayList<>();
    var         regiolijstId  = regiolijstDto.getRegiolijstId();
    int[]       toegevoegd    = new int[1];

    toegevoegd[0] = 0;

    getRegiolijstTaxonService().query(regiolijstId)
                               .forEach(taxon ->
                                            inLijst.add(taxon.getTaxonId()));
    getTaxonService().getTaxaPerPeriode(regiolijstDto.getStartdatum(),
                                        regiolijstDto.getEinddatum(),
                                        getGebruikersTaalInIso6392t())
                     .forEach(taxon -> {
      var taxonId = taxon.getTaxonId();
      if (!inLijst.contains(taxonId)) {
        var nieuwe  = new RegiolijstTaxonDto();
        nieuwe.setRegiolijstId(regiolijstId);
        nieuwe.setTaxonId(taxonId);
        try {
          getRegiolijstTaxonService().save(nieuwe);
          toegevoegd[0]++;
        } catch (DuplicateObjectException e) {
          addError(PersistenceConstants.DUPLICATE, taxon.getLatijnsenaam());
        }
      }
    });

    addInfo("message.toegevoegd", toegevoegd[0]);
  }

  private void taxonToJson(TaxonDto taxon, JSONObject json) {
    json.put(TaxonDto.COL_VOLGNUMMER, taxon.getVolgnummer());
    json.put("taxonnaam",
             taxon.getTaxonnaam(getGebruikersTaalInIso6392t()).getNaam());
    json.put(TaxonDto.COL_LATIJNSENAAM, taxon.getLatijnsenaam());
    json.put(TaxonDto.COL_STATUS, taxon.getStatus());
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setDeletetekst(String.format(FMT_TITEL,
                                 regio.getNaam(), regiolijst.getPeriode()));
    setSubTitel(getTekst(TIT_UPDATE,
                         regio.getNaam(), regiolijst.getPeriode()));
  }

  public void uploading() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    if (null == bestand) {
      addError("errors.nofile");
      return;
    }

    dubbel.clear();
    nieuw.clear();
    onbekend.clear();

    try (var invoer =
            new BufferedReader(
                new InputStreamReader(bestand.getInputStream()))) {
      while (invoer.ready()) {
        var deel  = invoer.readLine().split(",", -1);
        String  latijnsenaam;
        String  status;
        latijnsenaam  = DoosUtils.stripBeginEnEind(deel[0], "\"")
                                 .replace("\"\"", "\"")
                                 .trim();
        if (DoosUtils.isBlankOrNull(latijnsenaam)) {
          continue;
        }

        if (deel.length > 1) {
          status      = DoosUtils.stripBeginEnEind(deel[1], "\"")
                                 .replace("\"\"", "\"")
                                 .trim();
        } else {
          status      = "";
        }

        uploadTaxon(latijnsenaam, status);
      }

      addInfo("message.upload", bestand.getName());
      addInfo("message.gelezen",
              dubbel.size() + nieuw.size() + onbekend.size());
    } catch (IOException e) {
      generateExceptionMessage(e);
    }
  }

  private void uploadTaxon(String latijnsenaam, String status) {
    var json  = new JSONObject();
    var taxon = new TaxonDto();

    try {
      taxon = getTaxonService().taxon(latijnsenaam);
      if (null == taxon.getTaxonId()) {
        json.put(TaxonDto.COL_LATIJNSENAAM, latijnsenaam);
        onbekend.add(json);
      } else {
        var lijstTaxon  = new RegiolijstTaxonDto();

        lijstTaxon.setRegiolijstId(regiolijst.getRegiolijstId());
        lijstTaxon.setStatus(status);
        lijstTaxon.setTaxonId(taxon.getTaxonId());
        lijstTaxon.setTaxon(taxon);
        getRegiolijstTaxonService().save(lijstTaxon);
        taxonToJson(taxon, json);
         nieuw.add(json);
      }
    } catch (DuplicateObjectException e) {
      taxonToJson(taxon, json);
      dubbel.add(json);
    }
  }
}
