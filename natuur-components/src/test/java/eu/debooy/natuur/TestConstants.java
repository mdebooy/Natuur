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

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.TaxonDto;


/**
 * @author Marco de Booij
 */
public final class TestConstants {
  public static final Integer AANTAL              = 1;
  public static final String  COORDINATEN         =
      "N 30 31 33,210 - E 40 41 43,210";
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
  public static final Integer OPFOTO              = 1;
  public static final String  OPMERKING           = "opmerking";
  public static final Long    PARENTID            = 2L;
  public static final String  PARENTLATIJNSENAAM  = "parentlatijnsenaam";
  public static final String  PARENTNAAM          = "parentnaam";
  public static final String  PARENTRANG          = "pr";
  public static final Integer PARENTVOLGNUMMER    = 5000;
  public static final String  RANG                = "ra";
  public static final String  RANG_FOUT           = "xxxx";
  public static final String  RANG_GR             = "so";
  public static final int     RANG_HASH           = 4260;
  public static final String  RANG_KL             = "or";
  public static final String  RANGNAAM            = "rang";
  public static final String  RANGNAAM_GR         = "soort";
  public static final int     RANGNAAM_HASH       = 161138;
  public static final String  RANGNAAM_KL         = "order";
  public static final String  TAAL                = "nl";
  public static final String  TAAL_FOUT           = "xxx";
  public static final String  TAAL_GR             = "pt";
  public static final String  TAAL_KL             = "en";
  public static final Long    TAXONID             = Long.MAX_VALUE - 10;
  public static final int     TAXONID_HASH        = -2147483009;
  public static final int     TAXONNAAM_HASH      = -2147456487;
  public static final Long    TAXONSEQ            = 5L;
  public static final Integer TOTAAL              = 10;
  public static final Integer VOLGNUMMER          = Integer.MIN_VALUE;
  public static final Long    WAARNEMINGID        = Long.MAX_VALUE - 11;
  public static final int     WAARNEMINGID_HASH   = -2147483008;

  public static final Message ERR_OPMERKING =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_OPMERKING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{"_I18N.label.opmerking", 2000})
                 .build();
  public static final Message ERR_RANG      =
      new Message.Builder()
                 .setAttribute(RangDto.COL_RANG)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{"_I18N.label.rang", 3})
                 .build();

  public static final Message REQ_GEBIEDID  =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_GEBIEDID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{"_I18N.label.gebied"})
                 .build();
  public static final Message REQ_RANG      =
      new Message.Builder()
                 .setAttribute(RangDto.COL_RANG)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{"_I18N.label.rang"})
                 .build();
  public static final Message REQ_TAXONID   =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_TAXONID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{"_I18N.label.soort"})
                 .build();

  private TestConstants() {
    throw new IllegalStateException("Utility class");
  }
}
