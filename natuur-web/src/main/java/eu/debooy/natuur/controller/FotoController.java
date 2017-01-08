/**
 * Copyright 2016 Marco de Booij
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
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.validator.FotoValidator;

import java.util.Collection;
import java.util.List;
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

  private Foto  foto;

  /**
   * Prepareer een nieuwe Foto.
   */
  public void create() {
    foto  = new Foto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  /**
   * Verwijder de Foto
   * 
   * @param Foto
   */
  public void delete(Long fotoId, String naam) {
    try {
      getFotoService().delete(fotoId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, "'" + naam + "'");
  }

  /**
   * Rapport met Waarnemingen.
   */
  public void fotolijst() {
    ExportData  exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "fotolijst");
    exportData.setKleuren(getLijstKleuren());

    exportData.setKolommen(new String[] { "klasseNaam", "klasseLatijnsenaam",
                                          "sequence", "land",
                                          "naam", "gebied" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.fotolijst"));

    Set<FotoOverzichtDto> rijen =
        new TreeSet<FotoOverzichtDto>(new FotoOverzichtDto.LijstComparator());
    rijen.addAll(getFotoService().fotoOverzicht());
    String      taal  = getGebruikersTaal();
    for (FotoOverzichtDto rij : rijen) {
      exportData.addData(new String[] {rij.getKlasseNaam(),
                                       rij.getKlasseLatijnsenaam(),
                                       rij.getTaxonSeq().toString(),
                                       getI18nLandnaam()
                                           .getI18nLandnaam(rij.getLandId(),
                                                            taal),
                                       rij.getNaam(),
                                       rij.getGebied()});
    }

    HttpServletResponse response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
    } catch (IllegalArgumentException e) {
      generateExceptionMessage(e);
      return;
    } catch (TechnicalException e) {
      generateExceptionMessage(e);
      return;
    }

    FacesContext.getCurrentInstance().responseComplete();
  }

  /**
   * Geef de geselecteerde foto.
   * 
   * @return Foto
   */
  public Foto getFoto() {
    return foto;
  }

  /**
   * Geef de lijst met fotos.
   * 
   * @return Collection<Foto> met Foto objecten.
   */
  public Collection<Foto> getFotos() {
    return getFotoService().query();
  }

  /**
   * Persist de Foto
   * 
   * @param Foto
   */
  public void save() {
    List<Message> messages  = FotoValidator.valideer(foto);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getFotoService().save(foto);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, foto.getTaxon().getNaam() + " "
                                               + foto.getTaxonSeq());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, foto.getTaxon().getNaam() + " "
                                              + foto.getTaxonSeq());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(FOTOS_REDIRECT);
  }

  /**
   * Zet de Foto die gewijzigd gaat worden klaar.
   * 
   * @param Long gebiedId
   */
  public void update(Long fotoId) {
    foto  = new Foto(getFotoService().foto(fotoId));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.foto.update");
    redirect(FOTO_REDIRECT);
  }
}