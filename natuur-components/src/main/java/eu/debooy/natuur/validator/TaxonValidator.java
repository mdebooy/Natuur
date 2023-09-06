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
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class TaxonValidator extends NatuurValidator {
  public static final String  ERR_LATIJNSENAAMONDERSOORT  =
      "errors.latijnsenaam.ondersoort";
  public static final String  ERR_LATIJNSENAAMSOORT       =
      "errors.latijnsenaam.soort";

  protected static final  String  LBL_LATIJNSENAAM  =
      "_I18N.label.latijnsenaam";
  protected static final  String  LBL_UITGESTORVEN  =
      "_I18N.label.uitgestorven";
  protected static final  String  LBL_VOLGNUMMER    =
      "_I18N.label.volgnummer";

  private TaxonValidator() {}

  public static List<Message> valideer(TaxonDto taxon) {
    return valideer(new Taxon(taxon));
  }

  public static List<Message> valideer(Taxon taxon) {
    List<Message> fouten  = new ArrayList<>();

    valideerLatijnsenaam(DoosUtils.nullToEmpty(taxon.getLatijnsenaam()),
                         DoosUtils.nullToEmpty(taxon.getRang()),
                         fouten);
    valideerOpmerking(DoosUtils.nullToEmpty(taxon.getOpmerking()), fouten);
    valideerRang(DoosUtils.nullToEmpty(taxon.getRang()), fouten);
    valideerUitgestorven(taxon.getUitgestorven(), fouten);
    valideerVolgnummer(taxon.getVolgnummer(), fouten);

    return fouten;
  }

  private static void valideerLatijnsenaam(String latijnsenaam,
                                           String rang,
                                           List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(latijnsenaam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_LATIJNSENAAM})
                            .build());
      return;
    }

    if (latijnsenaam.length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_LATIJNSENAAM, 255})
                            .build());
    }

    var deel  = latijnsenaam.split(" ");
    if (rang.equals(NatuurConstants.RANG_SOORT)
      && deel.length != 2) {
        fouten.add(new Message.Builder()
                              .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                              .setSeverity(Message.ERROR)
                              .setMessage(ERR_LATIJNSENAAMSOORT)
                              .build());
    }

    if (rang.equals(NatuurConstants.RANG_ONDERSOORT)
      && deel.length != 3) {
        fouten.add(new Message.Builder()
                              .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                              .setSeverity(Message.ERROR)
                              .setMessage(ERR_LATIJNSENAAMONDERSOORT)
                              .build());
    }
  }

  private static void valideerUitgestorven(Boolean uitgestorven,
                                           List<Message> fouten) {
    if (null == uitgestorven) {
      fouten.add(new Message.Builder()
                            .setAttribute(TaxonDto.COL_UITGESTORVEN)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_UITGESTORVEN})
                            .build());
    }
  }

  private static void valideerVolgnummer(Long volgnummer,
                                         List<Message> fouten) {
    if (null == volgnummer) {
      fouten.add(new Message.Builder()
                            .setAttribute(TaxonDto.COL_VOLGNUMMER)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_VOLGNUMMER})
                            .build());
    }
  }
}
