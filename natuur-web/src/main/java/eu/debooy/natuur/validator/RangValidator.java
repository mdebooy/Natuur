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
package eu.debooy.natuur.validator;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.form.Rang;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class RangValidator {
  private RangValidator() {
  }

  /**
   * Valideer de Rang.
   * 
   * @param Rang
   * @return List<Message>
   */
  public static List<Message> valideer(Rang rang) {
    List<Message> fouten  = new ArrayList<Message>();

    String  waarde  = rang.getRang();
    if (DoosUtils.isBlankOrNull(waarde)) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.rang"));
    }
    Long    getal   = rang.getNiveau();
    if (null == getal) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.niveau"));
    } else if (getal <= 0) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.NIETGROTER,
                             "_I18N.label.niveau", 0));
    }

    return fouten;
  }
}
