/*
 * Copyright (c) 2021 Marco de Booij
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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.form.Rangnaam;
import static eu.debooy.natuur.validator.NatuurValidator.LBL_NAAM;
import static eu.debooy.natuur.validator.NatuurValidator.LBL_TAAL;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class RangnaamValidator extends NatuurValidator {
  private RangnaamValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(RangnaamDto rangnaam) {
    if (null == rangnaam) {
      return ComponentsUtils.objectIsNull(RangnaamDto.class.getSimpleName());
    }

    return valideer(new Rangnaam(rangnaam));
  }

  public static List<Message> valideer(Rangnaam rangnaam) {
    if (null == rangnaam) {
      return ComponentsUtils.objectIsNull(Rangnaam.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(rangnaam.getNaam())
                               .setAttribute(RangnaamDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(255)
                               .setRequired()
                               .valideer().getFouten());
    valideerRang(DoosUtils.nullToEmpty(rangnaam.getRang()), fouten);
    fouten.addAll(new Validator.Builder()
                               .setWaarde(rangnaam.getTaal())
                               .setAttribute(RangnaamDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setLowerCase()
                               .setFixLengte(3)
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}
