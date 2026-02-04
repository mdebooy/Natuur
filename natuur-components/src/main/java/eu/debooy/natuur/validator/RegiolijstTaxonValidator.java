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

package eu.debooy.natuur.validator;

import eu.debooy.doosutils.ComponentsUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.form.RegiolijstTaxon;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class RegiolijstTaxonValidator {
  protected static final  String  LBL_REGIOLIJSTID  = "_I18N.label.regiolijst";
  protected static final  String  LBL_STATUS        = "_I18N.label.status";
  protected static final  String  LBL_TAXONID       = "_I18N.label.taxon";

  private RegiolijstTaxonValidator() {}

  public static List<Message> valideer(RegiolijstTaxonDto regiolijstTaxon) {
     if (null == regiolijstTaxon) {
      return
        ComponentsUtils.objectIsNull(RegiolijstTaxonDto.class.getSimpleName());
    }

   return valideer(new RegiolijstTaxon(regiolijstTaxon));
  }

  public static List<Message> valideer(RegiolijstTaxon regiolijstTaxon) {
    if (null == regiolijstTaxon) {
      return
        ComponentsUtils.objectIsNull(RegiolijstTaxon.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(regiolijstTaxon.getRegiolijstId())
                               .setAttribute(
                                  RegiolijstTaxonDto.COL_REGIOLIJSTID)
                               .setLabel(LBL_REGIOLIJSTID)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regiolijstTaxon.getStatus())
                               .setAttribute(RegiolijstTaxonDto.COL_STATUS)
                               .setLabel(LBL_STATUS)
                               .setMaxLengte(2)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regiolijstTaxon.getTaxonId())
                               .setAttribute(RegiolijstTaxonDto.COL_TAXONID)
                               .setLabel(LBL_TAXONID)
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}
