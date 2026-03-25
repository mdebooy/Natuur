/**
 * Copyright (c) 2017 Marco de Booij
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
import eu.debooy.doosutils.validator.ValiDatum;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Waarneming;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class WaarnemingValidator extends NatuurValidator {
  protected static final  String  LBL_AANTAL  = "_I18N.label.aantal";
  protected static final  String  LBL_DATUM   = "_I18N.label.datum";

  private WaarnemingValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(WaarnemingDto waarneming) {
    if (null == waarneming) {
      return ComponentsUtils.objectIsNull(WaarnemingDto.class.getSimpleName());
    }

    return valideer(new Waarneming(waarneming));
  }

  public static List<Message> valideer(Waarneming waarneming) {
    if (null == waarneming) {
      return ComponentsUtils.objectIsNull(Waarneming.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(new Validator.Builder()
                               .setWaarde(waarneming.getAantal())
                               .setAttribute(WaarnemingDto.COL_AANTAL)
                               .setLabel(LBL_AANTAL)
                               .setMinWaarde(1L)
                               .valideer().getFouten());
    fouten.addAll(new ValiDatum.Builder()
                               .setDatum(waarneming.getDatum())
                               .setDatumAttribuut(WaarnemingDto.COL_DATUM)
                               .setDatumLabel(LBL_DATUM)
                               .setDatumRequired()
                               .setDatumVerleden()
                               .valideer().getFouten());
    if (null == waarneming.getGebied()) {
      valideerGebiedId(null, fouten);
    } else {
      valideerGebiedId(waarneming.getGebied().getGebiedId(), fouten);
    }
    valideerOpmerking(waarneming.getOpmerking(), fouten);
    if (null == waarneming.getTaxon()) {
      valideerTaxonId(null, fouten);
    } else {
      valideerTaxonId(waarneming.getTaxon().getTaxonId(), fouten);
    }

    return fouten;
  }
}
