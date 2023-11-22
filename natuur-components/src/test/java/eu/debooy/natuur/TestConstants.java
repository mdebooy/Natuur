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
  public static final Integer AANTAL                  = 1;
  public static final Integer AANTALFOTOS             = 0;
  public static final String  COORDINATEN             =
      "N 30 31 33,210 - E 40 41 43,210";
  public static final int     DETAILPK_HASH           = -2147460291;
  public static final String  FOTOBESTAND             = "fotobestand";
  public static final String  FOTODETAIL              = "fotodetail";
  public static final Long    FOTOID                  = Long.MAX_VALUE - 8;
  public static final int     FOTOID_HASH             = -2147483011;
  public static final Long    GEBIEDID                = Long.MAX_VALUE - 9;
  public static final int     GEBIEDID_HASH           = -2147483010;
  public static final Long    LANDID                  = 4L;
  public static final String  LATIJNSENAAM            = "Phalacrocorax carbo";
  public static final String  LATIJNSENAAM_GR         = "Rynchops flavirostris";
  public static final String  LATIJNSENAAM_KL         = "Emberiza flaviventris";
  public static final String  LATITUDE                = "N";
  public static final String  LATITUDE2               = "S";
  public static final Integer LATITUDE_GRADEN         = 30;
  public static final Integer LATITUDE_MINUTEN        = 31;
  public static final Double  LATITUDE_SECONDEN       = 33.21;
  public static final String  LONGITUDE               = "E";
  public static final String  LONGITUDE2              = "W";
  public static final Integer LONGITUDE_GRADEN        = 40;
  public static final Integer LONGITUDE_MINUTEN       = 41;
  public static final Double  LONGITUDE_SECONDEN      = 43.21;
  public static final String  NAAM                    = "naam";
  public static final String  NAAM_GR                 = "onaam";
  public static final String  NAAM_KL                 = "mnaam";
  public static final Long    NIVEAU                  = 3L;
  public static final Integer OPFOTO                  = 1;
  public static final String  OPMERKING               = "opmerking";
  public static final String  OMSCHRIJVING            =
      "Dit is een omschrijving.";
  public static final int     OVERZICHTPK_HASH        = 989393;
  public static final Long    PARENTID                = 2L;
  public static final String  PARENTLATIJNSENAAM      = "Phalacrocorax";
  public static final String  PARENTNAAM              = "b_Aalscholvers";
  public static final String  PARENTNAAM_GR           = "c_Corvo-marinho";
  public static final String  PARENTNAAM_KL           = "a_Old World cormorants";
  public static final String  PARENTRANG              = "ge";
  public static final String  PARENTRANG_GR           = "oge";
  public static final String  PARENTRANG_KL           = "fa";
  public static final Long    PARENTVOLGNUMMER        = 5000L;
  public static final int     PCTOPFOTO               = 20;
  public static final String  RANG                    = "so";
  public static final String  RANG_FOUT               = "xxxx";
  public static final String  RANG_GR                 = "ta";
  public static final int     RANG_HASH               = 4305;
  public static final String  RANG_KL                 = "or";
  public static final String  RANGNAAM                = "rang";
  public static final String  RANGNAAM_GR             = "soort";
  public static final int     RANGNAAM_HASH           = 268443;
  public static final String  RANGNAAM_KL             = "orde";
  public static final Long    REGIOID                 = 101L;
  public static final int     REGIOLIJST_HASH         = 730;
  public static final int     REGIOLIJSTTAXON_HASH    = -2147456628;
  public static final int     REGIOLIJSTTAXONPK_HASH  = -2147456628;
  public static final String  TAAL                    = "nld";
  public static final String  STATUS                  = "st";
  public static final String  TAAL_FOUT               = "xxxx";
  public static final String  TAAL_GR                 = "por";
  public static final String  TAAL_KL                 = "eng";
  public static final Long    TAXONID                 = Long.MAX_VALUE - 10;
  public static final int     TAXONID_HASH            = -2147483009;
  public static final String  TAXONNAAM               = "b_Aalscholver";
  public static final String  TAXONNAAM_GR            = "c_Cormorão";
  public static final int     TAXONNAAM_HASH          = -2147350847;
  public static final String  TAXONNAAM_KL            = "a_Great cormorant";
  public static final Long    TAXONSEQ                = 5L;
  public static final Integer TOTAAL                  = 10;
  public static final Long    VOLGNUMMER              = 800L;
  public static final Long    WAARNEMINGID            = 1100L;
  public static final int     WAARNEMINGID_HASH       = 1729;
  public static final Integer WAARGENOMEN             = 5;

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
