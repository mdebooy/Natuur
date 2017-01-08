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
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.form.Taxon;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Marco de Booij
 */
@Named("natuurWaarneming")
@SessionScoped
public class WaarnemingController extends Natuur {
  private static final  long    serialVersionUID  = 1L;

  /**
   * Geef alle waarnemingen als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectWaarnemingen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<DetailDto>    rijen =
        new TreeSet<DetailDto>(new DetailDto.NaamComparator());
    rijen.addAll(getDetailService().getSoortenMetKlasse());
    for (DetailDto rij : rijen) {
      items.add(new SelectItem(new Taxon(rij),
                               rij.getNaam() + " (" + rij.getLatijnsenaam()
                                   + ")"));
    }

    return items;
  }

  /**
   * Geef de lijst met waarnemingen.
   * 
   * @return Collection<DetailDto> met DetailDto objecten.
   */
  public Collection<DetailDto> getWaarnemingen() {
    return getDetailService().getSoortenMetKlasse();
  }

  /**
   * Rapport met Waarnemingen.
   */
  public void waarnemingenlijst() {
    ExportData  exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "waarnemingen");
    exportData.setKleuren(getLijstKleuren());

    exportData.setKolommen(new String[] { "parentNaam", "parentLatijnsenaam",
                                          "naam", "latijnsenaam" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.waarnemingen"));

    Set<DetailDto> rijen =
        new TreeSet<DetailDto>(new DetailDto.LijstComparator());
    rijen.addAll(getDetailService().getSoortenMetKlasse());
    for (DetailDto rij : rijen) {
      exportData.addData(new String[] {rij.getParentNaam(),
                                       rij.getParentLatijnsenaam(),
                                       rij.getNaam(),
                                       rij.getLatijnsenaam()});

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
}
