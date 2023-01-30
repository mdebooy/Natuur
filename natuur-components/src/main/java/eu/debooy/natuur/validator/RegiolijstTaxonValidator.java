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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.form.RegiolijstTaxon;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public class RegiolijstTaxonValidator {
  protected static final  String  LBL_REGIOID = "_I18N.label.regio";
  protected static final  String  LBL_STATUS  = "_I18N.label.status";
  protected static final  String  LBL_TAXONID = "_I18N.label.taxon";

  private RegiolijstTaxonValidator() {}

  public static List<Message> valideer(RegiolijstTaxonDto regiolijstTaxon) {
    return valideer(new RegiolijstTaxon(regiolijstTaxon));
  }

  public static List<Message> valideer(RegiolijstTaxon regiolijstTaxon) {
    List<Message> fouten  = new ArrayList<>();

    valideerRegioId(regiolijstTaxon.getRegioId(), fouten);
    valideerStatus(regiolijstTaxon.getStatus(), fouten);
    valideerTaxonId(regiolijstTaxon.getTaxonId(), fouten);

    return fouten;
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

  protected static void valideerStatus(String status,
                                        List<Message> fouten) {
    if (DoosUtils.nullToEmpty(status).length() > 2) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegiolijstTaxonDto.COL_STATUS)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_STATUS, 2})
                            .build());
    }
  }

  protected static void valideerTaxonId(Long taxonId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taxonId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(RegiolijstDto.COL_REGIOID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_TAXONID})
                            .build());
    }
  }
}
