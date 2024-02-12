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
import java.util.Arrays;


/**
 * @author Marco de Booij
 */
public final class NatuurUtils {
  private NatuurUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String formatLatijnsenaam(String latijnsenaam) {
    return (latijnsenaam.substring(0, 1).toUpperCase()
            + latijnsenaam.substring(1).toLowerCase())
                .replaceAll(NatuurConstants.UITGESTORVEN, "").trim();
  }

  public static String getBoolean(boolean schakelaar) {
    if (schakelaar) {
      return "☑";
    }

    return "☐";
  }

  public static String getLatijnsenaam(String latijnsenaam,
                                       Boolean uitgestorven) {
    if (Boolean.FALSE.equals(uitgestorven)) {
      return latijnsenaam;
    }

    return String.format("%s %s", latijnsenaam, NatuurConstants.UITGESTORVEN);
  }

  public static String getNaam(DetailDto detail, String taal) {
    if (detail.hasTaxonnaam(taal)) {
      return detail.getNaam(taal);
    }

    if (detail.getRang().equals(NatuurConstants.RANG_ONDERSOORT)
        && detail.hasParentnaam(taal)) {
      return detail.getNaam(taal);
    }

    return "";
  }

  public static String getNaam(TaxonDto taxon, String taal) {
    if (taxon.hasTaxonnaam(taal)) {
      return taxon.getNaam(taal);
  }

  if (taxon.getRang().equals(NatuurConstants.RANG_ONDERSOORT)
      && taxon.hasParentnaam(taal)) {
      return taxon.getNaam(taal);
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

  public static Boolean isUitgestorven(String latijnsenaam) {
    return latijnsenaam.endsWith(NatuurConstants.UITGESTORVEN);
  }
}
