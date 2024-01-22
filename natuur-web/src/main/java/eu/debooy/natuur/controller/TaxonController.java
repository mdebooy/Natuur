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
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.form.Taxonnaam;
import eu.debooy.natuur.validator.TaxonValidator;
import eu.debooy.natuur.validator.TaxonnaamValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
@Named("natuurTaxon")
@SessionScoped
public class TaxonController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(TaxonController.class);

  private static final  String  DTIT_CREATE   = "natuur.titel.taxonnaam.create";
  private static final  String  DTIT_UPDATE   = "natuur.titel.taxonnaam.update";
  private static final  String  HERBENOEMD    =
      "natuur.latijnsenamen.herbenoemd";
  private static final  String  TIT_CREATE    = "natuur.titel.taxon.create";
  private static final  String  TIT_UPDATE    = "natuur.titel.taxon.update";

  private static final  String  TAB_KINDEREN  = "Kinderen";
  private static final  String  TAB_NAMEN     = "Namen";

  private final JSONArray resultaat = new JSONArray();

  private UploadedFile  bestand;
  private Taxon         ouder;
  private Long          ouderNiveau;
  private Taxon         taxon;
  private TaxonDto      taxonDto;
  private Taxonnaam     taxonnaam;
  private boolean       wijzigen    = false;

  public void batch() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    redirect(TAXONNAMENUPLOAD_REDIRECT);
  }

  public void bepaalOuder(Long parentId) {
    if (null == parentId) {
      ouder       = new Taxon();
      ouderNiveau = Long.valueOf(0);
    } else {
      ouder       = new Taxon(getTaxonService().taxon(parentId),
                              getGebruikersTaalInIso6392t());
      ouderNiveau = getRangService().rang(ouder.getRang()).getNiveau();
    }
  }

  private void checkRangwijziging() {
    if (!taxon.getRang().equals(taxonDto.getRang())) {
      taxon.setRang(new Rang(getRangService().rang(taxon.getRang()),
                             getGebruikersTaalInIso6392t()));
    }

    if (DoosUtils.isBlankOrNull(taxon.getParentId())) {
      taxon.setParent(new Taxon());
      taxon.setParentRang(new Rang());

      return;
    }

    if (null == taxonDto.getParent()
        || !taxon.getParentId().equals(taxonDto.getParent().getParentId())) {
      taxon.setParent(getTaxonService().taxon(taxon.getParentId()),
                      getGebruikersTaalInIso6392t());
      taxon.setParentRang(new Rang(getRangService().rang(taxon.getParentRang()),
                          getGebruikersTaalInIso6392t()));
    }
  }

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = FacesContext.getCurrentInstance().getExternalContext();

    // TODO check ouderniveau bijkeuze voor rang.
    setActieveTab(TAB_KINDEREN);
    taxon       = new Taxon();
    taxonDto    = new TaxonDto();
    if (ec.getRequestParameterMap().containsKey(TaxonDto.COL_TAXONID)) {
      var parentId  = Long.valueOf(ec.getRequestParameterMap()
                                     .get(TaxonDto.COL_TAXONID));
      taxon.setParent(getTaxonService().taxon(parentId));
      ouderNiveau   = taxon.getParentNiveau();
    } else {
      taxon.setRang(new Rang(getRangService().rang(getParameter(DEF_RANG))));
    }

    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(TAXON_REDIRECT);
  }

  public void createDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setActieveTab(TAB_NAMEN);
    taxonnaam     = new Taxonnaam();
    taxonnaam.setTaal(getGebruikersTaalInIso6392t());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel(DTIT_CREATE);
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
      taxon       = new Taxon();
      taxonDto    = new TaxonDto();
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
      if (getGebruikersTaalInIso6392t().equals(taxonnaam.getTaal())) {
        taxon.setNaam(taxonDto.getNaam(getGebruikersTaalInIso6392t()));
        setSubTitel(getTekst(TIT_UPDATE,
                    getTaxonnaam(getGebruikersTaalInIso6392t())));
      }
      taxonnaam = new Taxonnaam();
      redirect(TAXON_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public UploadedFile getBestand() {
    return bestand;
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

  public String  getTaxonnaam(String taal) {
    return getTaxonnaam(taxonDto, taal);
  }

  public String  getTaxonnaam(TaxonDto taxon, String taal) {
    if (taxon.hasTaxonnaam(taal)) {
      return taxon.getNaam(taal);
    }

    if (null == taxon.getRang()
        || !taxon.getRang().equals(NatuurConstants.RANG_ONDERSOORT)) {
      return taxon.getLatijnsenaam();
    }

    try {
      var parent  = getTaxonService().taxon(taxon.getParentId());
      if (parent.hasTaxonnaam(taal)) {
        return String.format("%s ssp %s", parent.getNaam(taal),
                             taxon.getLatijnsenaam().split(" ")[2]);
      }
    } catch (ObjectNotFoundException e) {
      // Geen parent aanwezig = geen naam.
    }

    return taxon.getLatijnsenaam();
  }

  public JSONArray getTaxonnamen() {
    var taxonnamen  = new JSONArray();

    taxonDto.getTaxonnamen().forEach(rij -> taxonnamen.add(rij.toJSON()));

    return taxonnamen;
  }

  public JSONArray getUploadresultaat() {
    return resultaat;
  }

  public boolean getWijzigen() {
    return wijzigen;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
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
      setActieveTab(TAB_KINDEREN);
      taxonDto    = getTaxonService().taxon(taxonId);
      taxon       = new Taxon(taxonDto, getGebruikersTaalInIso6392t());
      bepaalOuder(taxon.getParentId());
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(getTaxonnaam(getGebruikersTaalInIso6392t()));
      setReturnTo(ec, TAXA_REDIRECT);
      redirect(TAXON_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_TAXON));
    }
  }

  public void retrieveDetail() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec  = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(TaxonnaamDto.COL_TAAL)) {
      addError(ComponentsConstants.GEENPARAMETER, TaxonnaamDto.COL_TAAL);
      return;
    }

    try {
      setActieveTab(TAB_NAMEN);
      taxonnaam   =
          new Taxonnaam(taxonDto.getTaxonnaam(ec.getRequestParameterMap()
                                                .get(TaxonnaamDto.COL_TAAL)));
      setDetailAktie(PersistenceConstants.UPDATE);
      setDetailSubTitel(DTIT_UPDATE);

      redirect(TAXONNAAM_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, getTekst(LBL_TAXONNAAM));
    }
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    checkRangwijziging();

    var messages  = TaxonValidator.valideer(taxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      var latijnsenaam  = taxon.getLatijnsenaam();
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          taxon.persist(taxonDto);
          getTaxonService().save(taxonDto);
          taxon.setTaxonId(taxonDto.getTaxonId());
          bepaalOuder(taxon.getParentId());
          setActieveTab(TAB_KINDEREN);
          addInfo(PersistenceConstants.CREATED,
                  getTaxonnaam(getGebruikersTaalInIso6392t()));
          update();
          break;
        case PersistenceConstants.UPDATE:
          taxon.persist(taxonDto);
          getTaxonService().save(taxonDto);
          bepaalOuder(taxon.getParentId());
          setActieveTab(TAB_KINDEREN);
          addInfo(PersistenceConstants.UPDATED,
                  getTaxonnaam(getGebruikersTaalInIso6392t()));
          if (!latijnsenaam.equals(taxonDto.getLatijnsenaam())) {
            var gewijzigd = wijzigKinderen(latijnsenaam,
                                           taxonDto.getLatijnsenaam(),
                                           taxonDto.getTaxonId());
            if (gewijzigd > 0) {
              addInfo(HERBENOEMD, gewijzigd);
            }
          }
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

    var taal  = taxonnaam.getTaal();
    try {
      var taxonnaamDto  = new TaxonnaamDto();
      taxonnaam.persist(taxonnaamDto);
      switch (getDetailAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          taxonDto.addNaam(taxonnaamDto);
          if (getGebruikersTaalInIso6392t().equals(taal)) {
            taxon.setNaam(taxonDto.getNaam(taal));
            setSubTitel(getTekst(TIT_UPDATE, taxon.getNaam()));
          }
          getTaxonService().save(taxonDto);
          addInfo(PersistenceConstants.CREATED, "'" + taal + "'");
          break;
        case PersistenceConstants.UPDATE:
          taxonDto.addNaam(taxonnaamDto);
          if (getGebruikersTaalInIso6392t().equals(taal)) {
            taxon.setNaam(taxonDto.getNaam(taal));
            setSubTitel(getTekst(TIT_UPDATE, taxon.getNaam()));
          }
          getTaxonService().save(taxonDto);
          addInfo(PersistenceConstants.UPDATED, "'" + taal + "'");
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie()) ;
          break;
      }
      redirect(TAXON_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taal);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taal);
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
    items.add(new SelectItem("", "--"));
    Set<TaxonDto>     rijen = new TreeSet<>(new TaxonDto.NaamComparator());
    rijen.addAll(getTaxonService().getOuders(niveau));
    rijen.forEach(rij ->
      items.add(new SelectItem(rij.getTaxonId(),
                               rij.getNaam(getGebruikersTaalInIso6392t()) + " ("
                                + rij.getLatijnsenaam() + ")")));

    return items;
  }

  public List<SelectItem> selectSoorten() {
    List<SelectItem>  items = new LinkedList<>();
    Set<Taxon>        rijen = new TreeSet<>(new Taxon.NaamComparator());
    rijen.addAll(getTaxonService().getSoorten(getGebruikersTaalInIso6392t()));
    rijen.forEach(rij ->
      items.add(new SelectItem(rij.getTaxonId(),
                               rij.getNaam() + " ("
                                + rij.getLatijnsenaam() + ")")));

    return items;
  }

  public void setBestand(UploadedFile bestand) {
    this.bestand  = bestand;
  }

  public void setWijzigen(boolean wijzigen) {
    this.wijzigen = wijzigen;
  }

  private void taxonToJson(TaxonDto taxon, JSONObject json) {
    json.put(TaxonDto.COL_VOLGNUMMER, taxon.getVolgnummer());
    json.put(TaxonDto.COL_LATIJNSENAAM, taxon.getLatijnsenaam());
    json.put(TaxonDto.COL_UITGESTORVEN, taxon.isUitgestorven());
  }

  private void taxonToJson(TaxonDto taxon, String taal, String naam,
                           JSONObject json) {
    taxonToJson(taxon, json);
    json.put(TaxonnaamDto.COL_TAAL, taal);
    json.put("nieuw", naam);
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setActieveTab(TAB_KINDEREN);
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE,
                         getTaxonnaam(getGebruikersTaalInIso6392t())));
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

    var taxa  = 0L;
    resultaat.clear();

    try (var invoer =
            new BufferedReader(
                new InputStreamReader(bestand.getInputStream()))) {
      var talen = invoer.readLine().replace("\"", "").split(",", -1);

      while (invoer.ready()) {
        taxa++;
        verwerkTaxon(invoer.readLine().split(",", -1), talen);
      }

      addInfo("message.upload", bestand.getName());
      addInfo("message.gelezen", taxa);
    } catch (IOException e) {
      generateExceptionMessage(e);
    }
  }

  private void verwerkTaxon(String[] deel, String[] taal) {
    var latijnsenaam  = deel[0].replaceAll("^\"+|\"+$", "")
                               .replace("\"\"", "\"")
                               .trim();
    if (DoosUtils.isBlankOrNull(latijnsenaam)) {
      return;
    }

    var gewijzigd = false;
    var item      = new TaxonDto();

    try {
      item = getTaxonService().taxon(latijnsenaam);
      if (null == item.getTaxonId()) {
        var json      = new JSONObject();
        json.put(TaxonDto.COL_LATIJNSENAAM, latijnsenaam);
        json.put("error", getTekst(PersistenceConstants.NOTFOUND, "").trim());
        resultaat.add(json);
      } else {
        for (var i = 1; i < taal.length; i++) {
          var naam  = deel[i].replaceAll("^\"+|\"+$", "")
                             .replace("\"\"", "\"")
                             .trim();
          if (verwerkTaxonnaam(naam, taal[i], item)) {
            gewijzigd = true;
          }
        }
      }
    } catch (DuplicateObjectException e) {
      var json      = new JSONObject();
      taxonToJson(item, json);
      json.put("error", e.getLocalizedMessage());

      resultaat.add(json);
    } catch (ObjectNotFoundException e) {
      var json      = new JSONObject();
      json.put(TaxonDto.COL_LATIJNSENAAM, latijnsenaam);
      json.put("error", getTekst(PersistenceConstants.NOTFOUND, "X").trim());

      resultaat.add(json);
    }

    if (gewijzigd) {
      getTaxonService().save(item);
    }
  }

  private boolean verwerkTaxonnaam(String naam, String taal, TaxonDto item) {
    if (DoosUtils.isBlankOrNull(naam)) {
      return false;
    }

    var json  = new JSONObject();

    if (item.hasTaxonnaam(taal)) {
      if (!item.getTaxonnaam(taal).getNaam().equals(naam)) {
        if (wijzigen) {
          taxonToJson(item, taal, naam, json);
          json.put("oud", item.getTaxonnaam(taal).getNaam());
          resultaat.add(json);

          item.getTaxonnaam(taal).setNaam(naam);

          return true;
        }
      }

      return false;
    }

    taxonToJson(item, taal, naam, json);
    resultaat.add(json);

    var taxonnaamDto  = new TaxonnaamDto();

    taxonnaamDto.setNaam(naam);
    taxonnaamDto.setTaal(taal);
    taxonnaamDto.setTaxonId(item.getTaxonId());
    item.addNaam(taxonnaamDto);

    return true;
  }

  private int wijzigKinderen(String oud, String nieuw, Long parentId) {
    var gewijzigd = 0;
    var kinderen  = getTaxonService().getKinderen(parentId);

    for (var kind : kinderen) {
      var latijnsenaam  = kind.getLatijnsenaam();
      if (latijnsenaam.startsWith(oud+" ")) {
        var nieuweLatijsenaam = latijnsenaam.replaceFirst(oud, nieuw);
        kind.setLatijnsenaam(nieuweLatijsenaam);
        try {
          getTaxonService().save(kind);
          gewijzigd++;
        } catch (DuplicateObjectException e) {
          addError(PersistenceConstants.DUPLICATE, kind.getLatijnsenaam());
        } catch (DoosRuntimeException e) {
          LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                     e.getLocalizedMessage()), e);
          generateExceptionMessage(e);
        }
        gewijzigd += wijzigKinderen(latijnsenaam, nieuweLatijsenaam,
                     kind.getTaxonId());
      }
    }

    return gewijzigd;
  }
}
