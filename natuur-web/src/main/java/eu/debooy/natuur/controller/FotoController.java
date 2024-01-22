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
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Marco de Booij
 */
@Named("natuurFoto")
@SessionScoped
public class FotoController extends Natuur {
  private static final  long    serialVersionUID  = 1L;

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
