/**
 * Copyright 2017 Marco de Booij
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
package eu.debooy.natuur.validator;

import eu.debooy.doosutils.ComponentsUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Taxonnaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class TaxonnaamValidator extends NatuurValidator {
  private TaxonnaamValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(TaxonnaamDto taxonnaam) {
    if (null == taxonnaam) {
      return ComponentsUtils.objectIsNull(TaxonnaamDto.class.getSimpleName());
    }

    return valideer(new Taxonnaam(taxonnaam));
  }

  public static List<Message> valideer(Taxonnaam taxonnaam) {
    if (null == taxonnaam) {
      return ComponentsUtils.objectIsNull(Taxonnaam.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(taxonnaam.getNaam())
                               .setAttribute(TaxonnaamDto.COL_NAAM)
                               .setLabel(LBL_NAAM)
                               .setMaxLengte(255)
                               .setRequired()
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(taxonnaam.getTaal())
                               .setAttribute(TaxonnaamDto.COL_TAAL)
                               .setLabel(LBL_TAAL)
                               .setLowerCase()
                               .setFixLengte(3)
                               .setRequired()
                               .valideer().getFouten());

    return fouten;
  }
}
