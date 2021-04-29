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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Taxonnaam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class RangnaamValidator extends NatuurValidator {
  private RangnaamValidator() {}

  public static List<Message> valideer(TaxonnaamDto taxonnaam) {
    return valideer(new Taxonnaam(taxonnaam));
  }

  public static List<Message> valideer(Taxonnaam taxonnaam) {
    List<Message> fouten  = new ArrayList<>();

    valideerNaam(DoosUtils.nullToEmpty(taxonnaam.getNaam()), fouten);
    valideerTaal(DoosUtils.nullToEmpty(taxonnaam.getTaal()), fouten);

    return fouten;
  }

  private static void valideerNaam(String naam, List<Message> fouten) {
    if (naam.length() < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.naam"));
    } else {
      if (naam.length() > 255) {
        fouten.add(new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                               "_I18N.label.naam", 255));
      }
    }
  }

  private static void valideerTaal(String taal, List<Message> fouten) {
    if (taal.length() < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.taal"));
    } else {
      if (taal.length() != 2) {
        fouten.add(new Message(Message.ERROR, PersistenceConstants.FIXLENGTH,
                               "_I18N.label.taal", 2));
      }
    }
  }
}
