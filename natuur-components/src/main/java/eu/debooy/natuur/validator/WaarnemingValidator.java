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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Waarneming;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class WaarnemingValidator extends NatuurValidator {
  private WaarnemingValidator() {}

  public static List<Message> valideer(WaarnemingDto waarneming) {
    return valideer(new Waarneming(waarneming));
  }

  public static List<Message> valideer(Waarneming waarneming) {
    List<Message> fouten  = new ArrayList<>();

    valideerAantal(waarneming.getAantal(), fouten);
    valideerDatum(waarneming.getDatum(), fouten);
    if (null == waarneming.getTaxon()) {
      valideerGebiedId(null, fouten);
    } else {
      valideerGebiedId(waarneming.getTaxon().getTaxonId(), fouten);
    }
    valideerOpmerking(DoosUtils.nullToEmpty(waarneming.getOpmerking()),
                      fouten);
    if (null == waarneming.getTaxon()) {
      valideerTaxonId(null, fouten);
    } else {
      valideerTaxonId(waarneming.getTaxon().getTaxonId(), fouten);
    }

    return fouten;
  }

  private static void valideerAantal(Integer aantal, List<Message> fouten) {
    if (null != aantal
        && aantal.compareTo(0) < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.ISKLEINER,
                             "_I18N.label.aantal", 1));
    }
  }

  private static void valideerDatum(Date datum, List<Message> fouten) {
    if ( null == datum) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.datum"));
    } else if (datum.after(new Date())) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.FUTURE,
                             Datum.fromDate(datum)));
    }
  }
}
