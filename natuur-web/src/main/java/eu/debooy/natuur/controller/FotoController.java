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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Waarneming;
import eu.debooy.natuur.validator.FotoValidator;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
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

  private Foto        foto;
  private FotoDto     fotoDto;
  private Waarneming  waarneming;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    foto        = new Foto();
    fotoDto     = new FotoDto();
    waarneming  = new Waarneming();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  public void create(Long waarnemingId) {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    foto        = new Foto();
    foto.setWaarnemingId(waarnemingId);
    fotoDto     = new FotoDto();
    fotoDto.setWaarnemingId(waarnemingId);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  public void delete(Long fotoId) {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      fotoDto = getFotoService().foto(fotoId);
      getFotoService().delete(fotoId);
      addInfo(PersistenceConstants.DELETED, "'" + fotoDto.getTaxonSeq() + "'");
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, fotoId);
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

    var               taal            = getGebruikersTaal();
    Map<Long, String> landnamen       = new HashMap<>();
    var               lijstComparator = new FotoOverzichtDto.LijstComparator();
    lijstComparator.setTaal(taal);

    getFotoService().fotoOverzicht()
                    .forEach(rij -> {
      var landId  = rij.getLandId();
      landnamen.computeIfAbsent(landId,
                                k -> getI18nLandnaam().getI18nLandnaam(landId,
                                                                       taal));
      exportData.addData(new String[] {rij.getKlasseNaam(taal),
                                       rij.getKlasseLatijnsenaam(),
                                       rij.getTaxonSeq().toString(),
                                       landnamen.get(landId),
                                       rij.getNaam(taal),
                                       rij.getGebied()});
    });

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

  public Foto getFoto() {
    return foto;
  }

  public Waarneming getWaarneming() {
    return waarneming;
  }

  public void retrieve(Long fotoId) {
    if (!isUser() && !isView()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    fotoDto     = getFotoService().foto(fotoId);
    foto        = new Foto(fotoDto);

    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel("natuur.titel.foto.retrieve");
    redirect(FOTO_REDIRECT);
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = FotoValidator.valideer(foto);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      foto.persist(fotoDto);
      getFotoService().save(fotoDto);
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          foto.setFotoId(fotoDto.getFotoId());
          addInfo(PersistenceConstants.CREATED, foto.getTaxonSeq().toString());
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, foto.getTaxonSeq().toString());
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
          break;
      }
      redirect(WAARNEMING_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, foto.getTaxonSeq().toString());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, foto.getTaxonSeq().toString());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void update(Long fotoId) {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    fotoDto       = getFotoService().foto(fotoId);
    foto          = new Foto(fotoDto);
    if (DoosUtils.isNotBlankOrNull(foto.getWaarnemingId())) {
//      waarneming  = new Waarneming(getWaarnemingService()
//                            .waarneming(foto.getWaarnemingId()));
    } else {
      waarneming  = new Waarneming();
    }
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.foto.update");
    redirect(FOTO_REDIRECT);
  }
}
