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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.form.Regiolijst;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class RegiolijstValidator extends NatuurValidator {
  protected static final  String  LBL_DATUM         = "_I18N.label.datum";
  protected static final  String  LBL_OMSCHRIJVING  =
      "_I18N.label.omschrijving";
  protected static final  String  LBL_REGIOID       = "_I18N.label.regio";

  private RegiolijstValidator() {}

  public static List<Message> valideer(RegiolijstDto regiolijst) {
    return valideer(new Regiolijst(regiolijst));
  }

  public static List<Message> valideer(Regiolijst regiolijst) {
    List<Message> fouten  = new ArrayList<>();

    valideerDatum(regiolijst.getDatum(), fouten);
    valideerOmschrijving(regiolijst.getOmschrijving(), fouten);
    valideerRegioId(regiolijst.getRegioId(), fouten);

    return fouten;
  }

  private static void valideerDatum(Date datum, List<Message> fouten) {
    if ( null == datum) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegiolijstDto.COL_DATUM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_DATUM})
                            .build());
      return;
    }

    if (datum.after(new Date())) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegiolijstDto.COL_DATUM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.FUTURE)
                            .setParams(new Object[]{Datum.fromDate(datum)})
                            .build());
    }
  }

  protected static void valideerOmschrijving(String omschrijving,
                                        List<Message> fouten) {
    if (DoosUtils.nullToEmpty(omschrijving).length() > 2000) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegiolijstDto.COL_OMSCHRIJVING)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_OMSCHRIJVING, 2000})
                            .build());
    }
  }

  protected static void valideerRegioId(Long regioId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(regioId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegiolijstDto.COL_REGIOID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_REGIOID})
                            .build());

    }
  }
}
