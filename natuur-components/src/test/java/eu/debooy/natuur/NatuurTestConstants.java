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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.TaxonDto;


/**
 * @author Marco de Booij
 */
public final class NatuurTestConstants {
  public static final Integer AANTAL                  = 1;
  public static final Integer AANTALFOTOS             = 0;
  public static final String  COORDINATEN             =
      "N 30 31 33,210 - E 40 41 43,210";
  public static final int     DETAILPK_HASH           = 28871;
  public static final String  FOTOBESTAND             = "fotobestand";
  public static final String  FOTODETAIL              = "fotodetail";
  public static final Long    FOTOID                  = Long.MAX_VALUE - 8;
  public static final int     FOTOID_HASH             = -2147483011;
  public static final Long    GEBIEDID                = Long.MAX_VALUE - 9;
  public static final int     GEBIEDID_HASH           = -2147483010;
  public static final int     GEENFOTO_HASH           = -2140800512;
  public static final int     GEENFOTOPK_HASH         = 1153745;
  public static final String  GRANDPARENTLATIJNSENAAM = "Phalacrocoracidae";
  public static final String  GRANDPARENTNAAM         = "familie";
  public static final Long    GRANDPARENTNIVEAU       = 18L;
  public static final Long    GRANDPARENTTAXONID      = Long.MAX_VALUE - 4163;
  public static final String  GRANDPARENTRANG         = "fa";
  public static final String  GRANDPARENTRANGNAAM     = "familie";
  public static final Long    GRANDPARENTVOLGNUMMER   = 3800L;
  public static final Long    LANDID                  = 4L;
  public static final String  LATIJNSENAAM            = "Phalacrocorax carbo";
  public static final String  LATIJNSENAAM_GR         = "Rynchops flavirostris";
  public static final String  LATIJNSENAAM_KL         = "Emberiza flaviventris";
  public static final String  LATITUDE                = "N";
  public static final String  LATITUDE2               = "S";
  public static final Integer LATITUDE_GRADEN         = 30;
  public static final Integer LATITUDE_MINUTEN        = 31;
  public static final Double  LATITUDE_SECONDEN       = 33.21;
  public static final String  LEEG_JSON               = "leeg.json";
  public static final String  LONGITUDE               = "E";
  public static final String  LONGITUDE2              = "W";
  public static final Integer LONGITUDE_GRADEN        = 40;
  public static final Integer LONGITUDE_MINUTEN       = 41;
  public static final Double  LONGITUDE_SECONDEN      = 43.21;
  public static final String  NAAM                    = "naam";
  public static final String  NAAM_GR                 = "onaam";
  public static final String  NAAM_KL                 = "mnaam";
  public static final Long    NIVEAU                  = 24L;
  public static final Integer OPFOTO                  = 1;
  public static final String  OPMERKING               = "opmerking";
  public static final String  OMSCHRIJVING            =
      "Dit is een omschrijving.";
  public static final String  ONDERSOORTLATIJNSENAAM  =
      "Phalacrocorax carbo sinensis";
  public static final String  ONDERSOORTNAAM          =
      "Aalscholver ssp. sinensis";
  public static final String  ONDERSOORTNAAM_KL       =
      ".Eastern Great Cormorant";
  public static final Long    ONDERSOORTNIVEAU        = 25L;
  public static final String  ONDERSOORTOPMERKING     = "oso opmerking";
  public static final String  ONDERSOORTRANG          = "oso";
  public static final String  ONDERSOORTRANGNAAM      = "ondersoort";
  public static final Long    ONDERSOORTTAXONID       = 6834L;
  public static final Long    ONDERSOORTVOLGNUMMER    = 65789L;
  public static final int     OVERZICHTPK_HASH        = 1156411;
  public static final String  PARENTLATIJNSENAAM      = "Phalacrocorax";
  public static final String  PARENTNAAM              = "Aalscholvers";
  public static final String  PARENTNAAM_GR           = "Corvo-marinho";
  public static final String  PARENTNAAM_KL           =
      ".Cormorants, Shags";
  public static final Long    PARENTNIVEAU            = 22L;
  public static final String  PARENTOPMERKING         = "ge opmerking";
  public static final String  PARENTRANG              = "ge";
  public static final String  PARENTRANG_GR           = "oge";
  public static final String  PARENTRANG_KL           = "fa";
  public static final String  PARENTRANGNAAM          = "genus";
  public static final Long    PARENTTAXONID           = 124L;
  public static final String  PARENTUITGESTORVEN      = DoosConstants.ONWAAR;
  public static final Long    PARENTVOLGNUMMER        = 5000L;
  public static final int     PCTOPFOTO               = 20;
  public static final String  RANG                    = "so";
  public static final String  RANG_FOUT               = "xxxx";
  public static final String  RANG_GR                 = "ta";
  public static final int     RANG_HASH               = 4305;
  public static final String  RANG_KL                 = "or";
  public static final String  RANGNAAM                = "soort";
  public static final String  RANGNAAM_GR             = "stoort";
  public static final int     RANGNAAM_HASH           = 268443;
  public static final String  RANGNAAM_KL             = "orde";
  public static final Long    REGIOID                 = 101L;
  public static final int     REGIOLIJST_HASH         = 730;
  public static final int     REGIOLIJSTTAXON_HASH    = 28020;
  public static final int     REGIOLIJSTTAXONPK_HASH  = 28020;
  public static final String  TAAL                    = "nld";
  public static final String  STATUS                  = "st";
  public static final String  TAAL_FOUT               = "xxxx";
  public static final String  TAAL_GR                 = "por";
  public static final String  TAAL_KL                 = "eng";
  public static final String  TAXON_JSON              = "taxon.json";
  public static final Long    TAXONID                 = 1010L;
  public static final int     TAXONID_HASH            = 1639;
  public static final String  TAXONNAAM               = "Aalscholver";
  public static final String  TAXONNAAM_GR            = "Cormorão";
  public static final int     TAXONNAAM_HASH          = 169801;
  public static final String  TAXONNAAM_KL            = ".Great cormorant";
  public static final Long    TAXONSEQ                = 5L;
  public static final Integer TOTAAL                  = 10;
  public static final String  UITGESTORVEN            = DoosConstants.ONWAAR;
  public static final String  VARIETEITLATIJNSENAAM  =
      "Phalacrocorax carbo novaehollandiae";
  public static final String  VARIETEITNAAM           =
      "Aalscholver var. novaehollandiae";
  public static final String  VARIETEITNAAM_KL        =
      ".Black Cormorant";
  public static final Long    VARIETEITNIVEAU         = 26L;
  public static final String  VARIETEITOPMERKING      = "oso variëteit";
  public static final String  VARIETEITRANG           = "var";
  public static final String  VARIETEITRANGNAAM       = "variëteit";
  public static final Long    VARIETEITTAXONID        = 6862L;
  public static final Long    VARIETEITVOLGNUMMER     = 65790L;
  public static final String  VORMLATIJNSENAAM        =
      "Phalacrocorax carbo hanedae";
  public static final String  VORMNAAM                =
      "Aalscholver f. hanedae";
  public static final String  VORMNAAM_KL             =
      ".Japanese Great Cormorant";
  public static final Long    VORMNIVEAU              = 27L;
  public static final String  VORMOPMERKING           = "oso vorm";
  public static final String  VORMRANG                = "frm";
  public static final String  VORMRANGNAAM            = "vorm";
  public static final Long    VORMTAXONID             = 6594L;
  public static final Long    VORMVOLGNUMMER          = 65791L;
  public static final Long    VOLGNUMMER              = 800L;
  public static final Long    WAARNEMINGID            = 1100L;
  public static final int     WAARNEMINGID_HASH       = 1729;
  public static final Integer WAARGENOMEN             = 5;
//  public static final Long    AANTAL    = Long.valueOf(10);
  public static final String  COMPLEET  = "compleet";
  public static final Long    GEZIEN    = Long.valueOf(8);
  public static final Long    ID        = Long.valueOf(2112);
//  public static final Long    REGIOID   = Long.valueOf(126);
  public static final String  SORTERING = "sortering";
//  public static final String  TAAL      = "nld";
//  public static final String  TAAL_FOUT = "xxxx";
//  public static final String  TAAL_GR   = "por";
//  public static final String  TAAL_KL   = "eng";

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

  private NatuurTestConstants() {
    throw new IllegalStateException("Utility class");
  }
}
