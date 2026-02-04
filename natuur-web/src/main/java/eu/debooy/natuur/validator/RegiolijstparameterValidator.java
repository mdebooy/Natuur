/*
 * Copyright (c) 2024 Marco de Booij
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
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.form.Regiolijstparameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Marco de Booij
 */
public class RegiolijstparameterValidator extends NatuurValidator {
  protected static final  String  ERR_TALEN = "errors.uniek.talen";

  private RegiolijstparameterValidator() {
   throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(Regiolijstparameter parameter) {
    if (null == parameter) {
      return
        ComponentsUtils.objectIsNull(Regiolijstparameter.class.getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    valideerAanwezig(parameter.getTaal1(), parameter.getTaal2(),
                     parameter.getTaal3(), fouten);
    fouten.addAll(new Validator.Builder()
                               .setWaarde(parameter.getTaal1())
                               .setAttribute(Regiolijstparameter.COL_TAAL1)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(parameter.getTaal2())
                               .setAttribute(Regiolijstparameter.COL_TAAL2)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(parameter.getTaal3())
                               .setAttribute(Regiolijstparameter.COL_TAAL3)
                               .setLabel(LBL_TAAL)
                               .setFixLengte(3)
                               .valideer().getFouten());
    valideerUniek(parameter.getTaal1(), parameter.getTaal2(),
                  parameter.getTaal3(), fouten);

    return fouten;
  }

  private static void valideerAanwezig(String taal1, String taal2, String taal3,
                                       List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taal1)
        && DoosUtils.isBlankOrNull(taal2)
        && DoosUtils.isBlankOrNull(taal3)) {
      fouten.add(new Message.Builder()
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.EMPTY)
                            .build());
    }
  }

  private static void valideerUniek(String taal1, String taal2, String taal3,
                                    List<Message> fouten) {
    var         aantal  = 0;
    Set<String> talen   = new HashSet<>();

    if (DoosUtils.isNotBlankOrNull(taal1)) {
      talen.add(taal1);
      aantal++;
    }
    if (DoosUtils.isNotBlankOrNull(taal2)) {
      talen.add(taal2);
      aantal++;
    }
    if (DoosUtils.isNotBlankOrNull(taal3)) {
      talen.add(taal3);
      aantal++;
    }

    if (talen.size() != aantal) {
      fouten.add(new Message.Builder()
                            .setSeverity(Message.ERROR)
                            .setMessage(ERR_TALEN)
                            .build());
    }
  }
}
