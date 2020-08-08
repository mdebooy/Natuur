/**
 * Copyright (c) 2016 Marco de Booij
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
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class TaxonValidator extends NatuurValidator {
  private TaxonValidator() {}

  public static List<Message> valideer(TaxonDto taxon) {
    return valideer(new Taxon(taxon));
  }

  public static List<Message> valideer(Taxon taxon) {
    List<Message> fouten  = new ArrayList<>();

    valideerLatijnsenaam(DoosUtils.nullToEmpty(taxon.getLatijnsenaam()),
                         fouten);
    valideerOpmerking(DoosUtils.nullToEmpty(taxon.getOpmerking()), fouten);
    valideerRang(DoosUtils.nullToEmpty(taxon.getRang()), fouten);
    valideerVolgnummer(taxon.getVolgnummer(), fouten);

    return fouten;
  }

  private static void valideerLatijnsenaam(String latijnsenaam,
                                           List<Message> fouten) {
    if (latijnsenaam.length() < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.latijnsenaam"));
    } else if (latijnsenaam.length() > 255) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                             "_I18N.label.latijnsenaam", 255));
    }
  }

  private static void valideerVolgnummer(Integer volgnummer,
                                         List<Message> fouten) {
    if (null == volgnummer) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.volgnummer"));
    }
  }
}
