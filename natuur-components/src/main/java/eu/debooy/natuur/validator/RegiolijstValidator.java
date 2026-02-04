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
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.form.Regiolijst;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class RegiolijstValidator extends NatuurValidator {
  protected static final  String  LBL_DATUM         = "_I18N.label.datum";
  protected static final  String  LBL_OMSCHRIJVING  =
      "_I18N.label.omschrijving";
  protected static final  String  LBL_REGIOID       = "_I18N.label.regio";

  private RegiolijstValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(RegiolijstDto regiolijst) {
    if (null == regiolijst) {
      return ComponentsUtils.objectIsNull(RegiolijstDto.class.getSimpleName());
    }

    return valideer(new Regiolijst(regiolijst));
  }

  public static List<Message> valideer(Regiolijst regiolijst) {
    if (null == regiolijst) {
      return ComponentsUtils.objectIsNull(Regiolijst.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(regiolijst.getDatum())
                               .setAttribute(RegiolijstDto.COL_DATUM)
                               .setLabel(LBL_DATUM)
                               .setRequired()
                               .setVerleden()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regiolijst.getOmschrijving())
                               .setAttribute(RegiolijstDto.COL_OMSCHRIJVING)
                               .setLabel(LBL_OMSCHRIJVING)
                               .setMaxLengte(2000)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(regiolijst.getRegioId())
                               .setAttribute(RegiolijstDto.COL_REGIOID)
                               .setLabel(LBL_REGIOID)
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}
