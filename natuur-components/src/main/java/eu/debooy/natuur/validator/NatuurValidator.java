/*
 * Copyright (c) 2020 Marco de Booij
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
import java.util.List;


/**
 * @author Marco de Booij
 */
public class NatuurValidator {
  protected static void valideerGebiedId(Long gebiedId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(gebiedId)) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.gebied"));
    }
  }

  protected static void valideerOpmerking(String opmerking,
                                        List<Message> fouten) {
    if (opmerking.length() > 2000) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                             "_I18N.label.opmerking", 2000));
    }
  }

  protected static void valideerRang(String rang, List<Message> fouten) {
    if (rang.length() < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.rang"));
    } else if (rang.length() > 3) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                             "_I18N.label.rang", 3));
    }
  }

  protected static void valideerTaxonId(Long taxonId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taxonId)) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.soort"));
    }
  }
}
