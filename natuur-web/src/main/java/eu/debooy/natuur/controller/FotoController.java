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
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import eu.debooy.natuur.domain.FotoOverzichtDto.LijstComparator;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.FotoOverzicht;
import eu.debooy.natuur.form.Waarneming;
import eu.debooy.natuur.validator.FotoValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
    foto        = new Foto();
    fotoDto     = new FotoDto();
    waarneming  = new Waarneming();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  public void create(Long waarnemingId) {
    waarneming  =
        new Waarneming(getWaarnemingService().waarneming(waarnemingId));
    foto        = new Foto();
    foto.setWaarnemingId(waarnemingId);
    fotoDto     = new FotoDto();
    fotoDto.setWaarnemingId(waarnemingId);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  public void delete(Long fotoId) {
    try {
      fotoDto = getFotoService().foto(fotoId);
      getFotoService().delete(fotoId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, fotoId);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, "'" + fotoDto.getTaxonSeq() + "'");
  }

  public void fotolijst() {
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

    String                taal            = getGebruikersTaal();
    Map<Long, String>     landnamen       = new HashMap<>();
    LijstComparator       lijstComparator =
        new FotoOverzichtDto.LijstComparator();
    lijstComparator.setTaal(taal);
    Set<FotoOverzichtDto> rijen           = new TreeSet<>(lijstComparator);
    rijen.addAll(getFotoService().fotoOverzicht());
    rijen.forEach(rij -> {
      Long  landId  = rij.getLandId();
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

  public Foto getFoto() {
    return foto;
  }

  public Collection<FotoOverzicht> getFotos() {
    Map<Long, String>   landnamen = new HashMap<>();
    String              taal      = getGebruikersTaal();
    List<FotoOverzicht> overzicht = new ArrayList<>();
    getFotoService().fotoOverzicht().forEach(rij -> {
      Long  landId  = rij.getLandId();
      landnamen.computeIfAbsent(landId,
                                k -> getI18nLandnaam().getI18nLandnaam(landId,
                                                                       taal));
      overzicht.add(new FotoOverzicht(rij, taal, landnamen.get(landId)));
    });

    return overzicht;
  }

  public Waarneming getWaarneming() {
    return waarneming;
  }

  public void retrieve(Long fotoId) {
    fotoDto     = getFotoService().foto(fotoId);
    foto        = new Foto(fotoDto);
    waarneming  =
        new Waarneming(getWaarnemingService()
                .waarneming(fotoDto.getWaarnemingId()));

    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel("natuur.titel.foto.retrieve");
    redirect(FOTO_REDIRECT);
  }

  public void save() {
    List<Message> messages  = FotoValidator.valideer(foto);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    String  melding = foto.getTaxonSeq().toString();
    try {
      foto.persist(fotoDto);
      getFotoService().save(fotoDto);
      switch (getAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        foto.setFotoId(fotoDto.getFotoId());
        addInfo(PersistenceConstants.CREATED, melding);
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, melding);
        break;
      default:
        addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
        break;
      }
      redirect(WAARNEMING_REDIRECT);
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

  public void update(Long fotoId) {
    fotoDto       = getFotoService().foto(fotoId);
    foto          = new Foto(fotoDto);
    if (DoosUtils.isNotBlankOrNull(foto.getWaarnemingId())) {
      waarneming  = new Waarneming(getWaarnemingService()
                            .waarneming(foto.getWaarnemingId()));
    } else {
      waarneming  = new Waarneming();
    }
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.foto.update");
    redirect(FOTO_REDIRECT);
  }
}
