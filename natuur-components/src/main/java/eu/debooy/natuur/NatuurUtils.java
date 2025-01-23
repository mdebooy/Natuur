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

package eu.debooy.natuur;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import java.util.Arrays;
import java.util.Map;


/**
 * @author Marco de Booij
 */
public final class NatuurUtils {
  public static final String  BOOLEANFALSE = "☐";
  public static final String  BOOLEANTRUE  = "☑";
  public static final String  CAMERAFALSE  = "";
  public static final String  CAMERATRUE   = "☒";

  private NatuurUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String formatLatijnsenaam(String latijnsenaam) {
    return (latijnsenaam.substring(0, 1).toUpperCase()
            + latijnsenaam.substring(1).toLowerCase())
                .replaceAll(NatuurConstants.UITGESTORVEN, "").trim();
  }

  public static String getBoolean(boolean schakelaar) {
    return schakelaar ? BOOLEANTRUE : BOOLEANFALSE;
  }

  public static String getCamera(boolean schakelaar) {
    return schakelaar ? CAMERATRUE : CAMERAFALSE;
  }

  public static String getLatijnsenaam(String latijnsenaam,
                                       Boolean uitgestorven) {
    if (Boolean.FALSE.equals(uitgestorven)) {
      return latijnsenaam;
    }

    return String.format("%s %s", latijnsenaam, NatuurConstants.UITGESTORVEN);
  }

  public static String getNaam(String naam, String latijnsenaam) {
    return naam.equals(latijnsenaam) ? "" : naam;
  }

  public static String getNaam(DetailDto detail, String taal) {
    return getNaam(detail.getNaam(taal), detail.getLatijnsenaam());
  }

  public static String getNaam(TaxonDto taxon, String taal) {
    return getNaam(taxon.getNaam(taal), taxon.getLatijnsenaam());
  }

  public static String getNaam(Map<String, TaxonnaamDto> taxonnamen,
                               Map<String, TaxonnaamDto> parentnamen,
                               String latijnsenaam, String rang, String taal) {
    if (taxonnamen.containsKey(taal)) {
      return taxonnamen.get(taal).getNaam();
    }

    if (parentnamen.containsKey(taal)) {
      switch (rang) {
        case NatuurConstants.RANG_ONDERSOORT:
          return String.format("%s ssp. %s", parentnamen.get(taal).getNaam(),
                               latijnsenaam.split(" ")[2]);
        case NatuurConstants.RANG_VARIETEIT:
          return String.format("%s var. %s", parentnamen.get(taal).getNaam(),
                               latijnsenaam.split(" ")[2]);
        case NatuurConstants.RANG_VORM:
          return String.format("%s f. %s", parentnamen.get(taal).getNaam(),
                               latijnsenaam.split(" ")[2]);
        default:
          break;
      }
    }

    return "";
  }

  public static String getSubtitel(String latijnsenaam, boolean uitgestorven,
                                   String... talen) {
    var resultaat = new StringBuilder();

    resultaat.append(NatuurUtils.getLatijnsenaam(latijnsenaam, uitgestorven));

    Arrays.asList(talen).stream()
                        .filter(DoosUtils::isNotBlankOrNull)
                        .filter(taal -> !taal.equals(latijnsenaam))
                        .forEachOrdered(taal ->
                                resultaat.append("/").append(taal));

    return resultaat.toString();
  }

  private static Boolean isSpecialeRang(String rang) {
    return (rang.equals(NatuurConstants.RANG_ONDERSOORT)
            || rang.equals(NatuurConstants.RANG_VARIETEIT)
            || rang.equals(NatuurConstants.RANG_VORM));
  }

  public static Boolean isUitgestorven(String latijnsenaam) {
    return latijnsenaam.trim().endsWith(NatuurConstants.UITGESTORVEN);
  }
}
