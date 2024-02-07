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

package eu.debooy.natuur;


/**
 * @author Marco de Booij
 */
public final class NatuurConstants {
  private NatuurConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String  DEF_TAAL          = "nld";

  public static final String  RANG_KLASSE       = "kl";
  public static final String  RANG_ONDERSOORT   = "oso";
  public static final String  RANG_SOORT        = "so";

  public static final String  LAT_VOGELS        = "Aves";

  public static final String  UITGESTORVEN      = "†";

  public static final Integer VOLGNUMMERFACTOR  = 10000000;
}
