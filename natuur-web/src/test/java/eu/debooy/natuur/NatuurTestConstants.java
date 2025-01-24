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
package eu.debooy.natuur;


/**
 * @author Marco de Booij
 */
public final class NatuurTestConstants {
  public static final Long    AANTAL    = Long.valueOf(10);
  public static final String  COMPLEET  = "compleet";
  public static final Long    GEZIEN    = Long.valueOf(8);
  public static final Long    ID        = Long.valueOf(2112);
  public static final Long    REGIOID   = Long.valueOf(126);
  public static final String  SORTERING = "sortering";
  public static final String  TAAL      = "nld";
  public static final String  TAAL_FOUT = "xxxx";
  public static final String  TAAL_GR   = "por";
  public static final String  TAAL_KL   = "eng";

  private NatuurTestConstants() {
    throw new IllegalStateException("Utility class");
  }
}
