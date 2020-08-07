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

import java.util.Date;


/**
 * @author Marco de Booij
 */
public final class TestConstants {
  public static final Integer AANTAL              = 1;
  public static final String  COORDINATEN         =
      "N 30 31 33,210 - E 40 41 43,210";
  public static final Date    DATUM               = new Date();
  public static final String  FOTOBESTAND         = "fotobestand";
  public static final String  FOTODETAIL          = "fotodetail";
  public static final Long    FOTOID              = Long.MAX_VALUE - 8;
  public static final int     FOTOID_HASH         = -2147483011;
  public static final Long    GEBIEDID            = Long.MAX_VALUE - 9;
  public static final int     GEBIEDID_HASH       = -2147483010;
  public static final Long    LANDID              = 4L;
  public static final String  LATIJNSENAAM        = "latijnsenaam";
  public static final String  LATITUDE            = "N";
  public static final Integer LATITUDE_GRADEN     = 30;
  public static final Integer LATITUDE_MINUTEN    = 31;
  public static final Double  LATITUDE_SECONDEN   = 33.21;
  public static final String  LONGITUDE           = "E";
  public static final Integer LONGITUDE_GRADEN    = 40;
  public static final Integer LONGITUDE_MINUTEN   = 41;
  public static final Double  LONGITUDE_SECONDEN  = 43.21;
  public static final String  NAAM                = "naam";
  public static final Long    NIVEAU              = 3L;
  public static final Long    OPFOTO              = 1L;
  public static final String  OPMERKING           = "opmerking";
  public static final Long    PARENTID            = 2L;
  public static final String  PARENTLATIJNSENAAM  = "parentlatijnsenaam";
  public static final String  PARENTNAAM          = "parentnaam";
  public static final Integer PARENTVOLGNUMMER    = 5000;
  public static final String  RANG                = "ra";
  public static final String  RANG_GR             = "so";
  public static final int     RANG_HASH           = 4260;
  public static final String  RANG_KL             = "or";
  public static final String  TAAL                = "nl";
  public static final String  TAAL_GR             = "pt";
  public static final String  TAAL_KL             = "en";
  public static final Long    TAXONID             = Long.MAX_VALUE - 10;
  public static final int     TAXONID_HASH        = -2147483009;
  public static final int     TAXONNAAM_HASH      = -2147456487;
  public static final Long    TAXONSEQ            = 5L;
  public static final Long    TOTAAL              = 10L;
  public static final Integer VOLGNUMMER          = Integer.MIN_VALUE;
  public static final Long    WAARNEMINGID        = Long.MAX_VALUE - 11;
  public static final int     WAARNEMINGID_HASH   = -2147483008;
}
