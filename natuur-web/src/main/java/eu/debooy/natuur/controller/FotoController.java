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
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Waarneming;
import eu.debooy.natuur.validator.FotoValidator;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurFoto")
@SessionScoped
public class FotoController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(FotoController.class);

  private static final  String  TIT_CREATE    = "natuur.titel.foto.create";
  private static final  String  TIT_DELETE    = "natuur.titel.foto.delete";
  private static final  String  TIT_RETRIEVE  = "natuur.titel.foto.retrieve";
  private static final  String  TIT_UPDATE    = "natuur.titel.foto.update";

  private Foto       foto;
  private FotoDto    fotoDto;
  private Waarneming waarneming;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var   ec          = getExternalContext();

    if (!checkEcParameters(ec.getRequestParameterMap(),
                           FotoDto.COL_WAARNEMINGID)) {
      return;
    }

    var waarnemingId  = Long.valueOf(ec.getRequestParameterMap()
                                       .get(FotoDto.COL_WAARNEMINGID));

    try {
      waarneming  =
          new Waarneming(getWaarnemingService().waarneming(waarnemingId));
      foto        = new Foto();
      fotoDto     = new FotoDto();
      foto.setWaarnemingId(waarneming.getWaarnemingId());
      foto.persist(fotoDto);
      setAktie(PersistenceConstants.CREATE);
      setReturnTo(ec, FOTOS_REDIRECT);
      setSubTitel(getTekst(TIT_CREATE));
      redirect(FOTO_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, waarnemingId);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var taxonSeq = foto.getTaxonSeq();
    try {
      getFotoService().delete(foto.getFotoId());
      foto    = new Foto();
      fotoDto = new FotoDto();
      addInfo(PersistenceConstants.DELETED, taxonSeq);
      redirect(getReturnTo());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonSeq);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void fotolijst() {
    if (!isUser() && !isView()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "fotolijst");
    exportData.setParameters(getLijstParameters());

    exportData.setKolommen(new String[] { "klasseNaam", "klasseLatijnsenaam",
                                          "sequence", "land",
                                          "naam", "gebied" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.fotolijst"));

    var                   taal            = getGebruikersTaalInIso6392t();
    Map<Long, String>     landnamen       = new HashMap<>();
    var                   lijstComparator =
        new FotoOverzichtDto.LijstComparator();
    lijstComparator.setTaal(taal);

    Set<FotoOverzichtDto> rijen           = new TreeSet<>(lijstComparator);
    rijen.addAll(getFotoService().fotoOverzicht(NatuurConstants.RANG_KLASSE));
    rijen.forEach(rij -> {
      var landId  = rij.getLandId();
      landnamen.computeIfAbsent(landId,
                                k -> getSedesRemote().getI18nLandnaam(landId,
                                                                      taal));
      exportData.addData(new String[] {rij.getParentNaam(taal),
                                       rij.getParentLatijnsenaam(),
                                       rij.getTaxonSeq().toString(),
                                       landnamen.get(landId),
                                       rij.getNaam(taal),
                                       rij.getGebied()});
    });

    var response  = (HttpServletResponse) getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
      FacesContext.getCurrentInstance().responseComplete();
    } catch (IllegalArgumentException | TechnicalException e) {
      generateExceptionMessage(e);
    }
  }

  public Foto getFoto() {
    return foto;
  }

  public Waarneming getWaarneming() {
    return waarneming;
  }

  public void retrieve() {
    if (!isGerechtigd()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec      = getExternalContext();

    if (!checkEcParameters(ec.getRequestParameterMap(),
                           FotoDto.COL_FOTOID)) {
      return;
    }

    var fotoId        = Long.valueOf(ec.getRequestParameterMap()
                                       .get(FotoDto.COL_FOTOID));

    try {
      fotoDto     = getFotoService().foto(fotoId);
      foto        = new Foto(fotoDto);
      waarneming  =
          new Waarneming(getWaarnemingService()
                            .waarneming(foto.getWaarnemingId()),
                         getGebruikersTaalInIso6392t());
      setAktie(PersistenceConstants.RETRIEVE);
      setDeletetekst(String.format("%s - %s", foto.getTaxonSeq(),
                                              foto.getFotoBestand()));
      setDeletetitel(getTekst(TIT_DELETE, waarneming.getTaxon().getNaam()));
      setSubTitel(getTekst(TIT_RETRIEVE));
      setReturnTo(ec, FOTOS_REDIRECT);
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

    var messages      = FotoValidator.valideer(foto);
    var fotoOverzicht =
            getFotoService().fotoTaxonSeq(waarneming.getTaxon().getTaxonId(),
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
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE -> {
          foto.persist(fotoDto);
          getFotoService().save(fotoDto);
          foto.setFotoId(fotoDto.getFotoId());
          addInfo(PersistenceConstants.CREATED, taxonSeq);
          update();
        }
        case PersistenceConstants.UPDATE -> {
          foto.persist(fotoDto);
          getFotoService().save(fotoDto);
          addInfo(PersistenceConstants.UPDATED, taxonSeq);
          update();
        }
        default -> addError(ComponentsConstants.WRONGREDIRECT,
                            getAktie().getAktie()) ;
      }
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
    setDeletetekst(String.format("%s - %s", foto.getTaxonSeq(),
                                            foto.getFotoBestand()));
    setDeletetitel(getTekst(TIT_DELETE, waarneming.getTaxon().getNaam()));
    setSubTitel(getTekst(TIT_UPDATE));
  }
}
