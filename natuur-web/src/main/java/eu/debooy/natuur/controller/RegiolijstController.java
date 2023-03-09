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

import eu.debooy.doos.model.I18nSelectItem;
import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.domain.RegiolijstTaxonPK;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Regio;
import eu.debooy.natuur.form.Regiolijst;
import eu.debooy.natuur.form.RegiolijstTaxon;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.validator.RegiolijstTaxonValidator;
import eu.debooy.natuur.validator.RegiolijstValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.apache.myfaces.custom.fileupload.UploadedFile;
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
  private static final  String  DTIT_UPDATE   =
      "natuur.titel.regiolijsttaxon.update";
  private static final  String  DTIT_UPLOAD   =
      "natuur.titel.regiolijst.upload";
  private static final  String  TIT_CREATE    =
      "natuur.titel.regiolijst.create";
  private static final  String  TIT_RETRIEVE  =
      "natuur.titel.regiolijst.retrieve";
  private static final  String  TIT_UPDATE    =
      "natuur.titel.regiolijst.update";

  private final JSONArray         dubbel    = new JSONArray();
  private final JSONArray         nieuw     = new JSONArray();
  private final JSONArray         onbekend  = new JSONArray();
  private final List<SelectItem>  statusses = new LinkedList<>();

  private UploadedFile    bestand;
  private Regio           regio;
  private Regiolijst      regiolijst;
  private RegiolijstDto   regiolijstDto;
  private RegiolijstTaxon regiolijstTaxon;

  public void batch() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    dubbel.clear();
    nieuw.clear();
    onbekend.clear();

    setDetailSubTitel(getTekst(DTIT_UPLOAD, regio.getNaam()));
    redirect(REGIOLIJSTUPLOAD_REDIRECT);
  }

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regiolijst    = new Regiolijst();
    regiolijstDto = new RegiolijstDto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(REGIOLIJST_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    regiolijstTaxon = new RegiolijstTaxon();
    regiolijstTaxon.setRegioId(regiolijst.getRegioId());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(getTekst(DTIT_CREATE, regio.getNaam()));
    redirect(REGIOLIJSTTAXON_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      getRegiolijstService().delete(regiolijst.getRegioId());
      regiolijst      = new Regiolijst();
      regiolijstDto   = new RegiolijstDto();
      regiolijstTaxon = new RegiolijstTaxon();
      addInfo(PersistenceConstants.DELETED, regio.getNaam());
      redirect(REGIOLIJSTEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, regiolijst.getRegioId());
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
      var sleutel     = new RegiolijstTaxonPK(regiolijstTaxon.getRegioId(),
                                              regiolijstTaxon.getTaxonId());
      getRegiolijstTaxonService().delete(sleutel);
      regiolijstTaxon = new RegiolijstTaxon();
      addInfo(PersistenceConstants.DELETED, "'" + naam + "'");
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

  public UploadedFile getBestand() {
    return bestand;
  }

  public JSONArray getDubbel() {
    return dubbel;
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

  public Regiolijst getRegiolijst() {
    return regiolijst;
  }

  public RegiolijstTaxon getRegiolijstTaxon() {
    return regiolijstTaxon;
  }

  public Collection<SelectItem> getStatussen() {
    if (statusses.isEmpty()) {
      statusses.add(new SelectItem(" ", "--"));
      statusses.addAll(getI18nLijst(STATUSSEN, getGebruikersTaal(),
                              new I18nSelectItem.WaardeComparator()));
    }

    return statusses;
  }

  public boolean isGelezen() {
    return dubbel.size() + nieuw.size() + onbekend.size() > 0;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(RegiolijstDto.COL_REGIOID)) {
      addError(ComponentsConstants.GEENPARAMETER, RegiolijstDto.COL_REGIOID);
      return;
    }

    var sleutel = Long.valueOf(ec.getRequestParameterMap()
                                 .get(RegiolijstDto.COL_REGIOID));

    try {
      regiolijst  = new Regiolijst(getRegiolijstService().regiolijst(sleutel));
      setRegio(sleutel);
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTekst(TIT_RETRIEVE, regio.getNaam()));
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

    var ec          = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap()
           .containsKey(RegiolijstTaxonDto.COL_TAXONID)) {
      addError(ComponentsConstants.GEENPARAMETER,
               RegiolijstTaxonDto.COL_TAXONID);
      return;
    }

    var taxonId     = Long.valueOf(ec.getRequestParameterMap()
                                     .get(RegiolijstTaxonDto.COL_TAXONID));

    try {
      regiolijstTaxon =
          new RegiolijstTaxon(getRegiolijstTaxonService()
                  .regiolijstTaxon(regiolijst.getRegioId(), taxonId),
                              getGebruikersTaal());
      setDetailAktie(PersistenceConstants.UPDATE);
      setDetailSubTitel(getTekst(DTIT_UPDATE, regio.getNaam()));

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
      addMessage(messages);
      return;
    }

    var naam  = regio.getNaam();
    try {
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          regiolijst.persist(regiolijstDto);
          getRegiolijstService().save(regiolijstDto);
          regiolijst.setRegioId(regiolijstDto.getRegioId());
          setRegio(regiolijst.getRegioId());
          addInfo(PersistenceConstants.CREATED, "'" + naam + "'");
          update();
          break;
        case PersistenceConstants.UPDATE:
          regiolijst.persist(regiolijstDto);
          getRegiolijstService().save(regiolijstDto);
          addInfo(PersistenceConstants.UPDATED, "'" + naam + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
          break;
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
                      .regiolijstTaxon(regiolijstTaxon.getRegioId(),
                                       regiolijstTaxon.getTaxonId());
        addError(PersistenceConstants.DUPLICATE,
                  (getTaxonService()
                     .taxon(regiolijstTaxon.getTaxonId())
                                           .getNaam(getGebruikersTaal())));
        return;
      } catch (ObjectNotFoundException e) {
        // OK. Mag niet aanwezig zijn.
      }
    }

    if (null == regiolijstTaxon.getTaxon()) {
      regiolijstTaxon.setTaxon(
          new Taxon(getTaxonService().taxon(regiolijstTaxon.getTaxonId()),
                    getGebruikersTaal()));
    }

    var naam  = regiolijstTaxon.getTaxon().getNaam();
    var taxon = new RegiolijstTaxonDto();
    try {
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          regiolijstTaxon.persist(taxon);
          getRegiolijstTaxonService().save(taxon);
          addInfo(PersistenceConstants.CREATED, "'" + naam + "'");
          break;
        case PersistenceConstants.UPDATE:
          regiolijstTaxon.persist(taxon);
          getRegiolijstTaxonService().save(taxon);
          addInfo(PersistenceConstants.UPDATED, "'" + naam + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie());
          break;
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

  public Collection<SelectItem> getSelectRegios() {
    return getI18nLandnaam().getSelectRegios();
  }

  public void setBestand(UploadedFile bestand) {
    this.bestand  = bestand;
  }

  protected void setRegio(Long regioId) {
    if (null == regio
        || !regioId.equals(regio.getRegioId())) {
      regio = new Regio(getI18nLandnaam().getRegio(regioId));
    }
  }

  public String status(String status) {
    if (DoosUtils.isBlankOrNull(status)) {
      return "";
    }

    return getTekst(STATUSSEN + "." + status);
  }

  private void taxonToJson(TaxonDto taxon, JSONObject json) {
    json.put("volgnummer", taxon.getVolgnummer());
    json.put("taxonnaam", taxon.getTaxonnaam(getGebruikersTaal()).getNaam());
    json.put("latijnsenaam", taxon.getLatijnsenaam());
    json.put("uitgestorven", taxon.isUitgestorven());
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE, regio.getNaam()));
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
        latijnsenaam  = deel[0].replaceAll("(?:^\")|(?:\"$)", "")
                               .replace("\"\"", "\"")
                               .trim();
        if (DoosUtils.isBlankOrNull(latijnsenaam)) {
          continue;
        }

        if (deel.length > 1) {
          status      = deel[1].replaceAll("(?:^\")|(?:\"$)", "")
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
          json.put("latijnsenaam", latijnsenaam);
          onbekend.add(json);
        } else {
          var lijstTaxon  = new RegiolijstTaxonDto();

          lijstTaxon.setRegioId(regiolijst.getRegioId());
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
