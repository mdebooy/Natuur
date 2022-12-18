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
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import java.util.List;


/**
 * @author Marco de Booij
 */
@SuppressWarnings("java:S1118")
public abstract class NatuurValidator {
  protected static void valideerGebiedId(Long gebiedId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(gebiedId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_GEBIEDID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.gebied"})
                            .build());
    }
  }

  protected static void valideerOpmerking(String opmerking,
                                        List<Message> fouten) {
    if (DoosUtils.nullToEmpty(opmerking).length() > 2000) {
      fouten.add(new Message.Builder()
                            .setAttribute(TaxonDto.COL_OPMERKING)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{"_I18N.label.opmerking",
                                                    2000})
                            .build());
    }
  }

  protected static void valideerRang(String rang, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(rang)) {
      fouten.add(new Message.Builder()
                            .setAttribute(RangDto.COL_RANG)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.rang"})
                            .build());
      return;
    }

    if (rang.length() > 3) {
      fouten.add(new Message.Builder()
                            .setAttribute(RangDto.COL_RANG)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{"_I18N.label.rang", 3})
                            .build());
    }
  }

  protected static void valideerTaxonId(Long taxonId, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taxonId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(TaxonDto.COL_TAXONID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.soort"})
                            .build());
    }
  }

  protected static void valideerWaarnemingId(Long waarnemingId,
                                             List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(waarnemingId)) {
      fouten.add(new Message.Builder()
                            .setAttribute(WaarnemingDto.COL_WAARNEMINGID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.waarneming"})
                            .build());

    }
  }
}
