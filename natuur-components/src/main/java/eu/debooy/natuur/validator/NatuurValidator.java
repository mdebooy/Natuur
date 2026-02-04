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
package eu.debooy.natuur.validator;

import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import java.util.List;


/**
 * @author Marco de Booij
 */
@SuppressWarnings("java:S1118")
public abstract class NatuurValidator {
  protected static final  String  LBL_GEBIED      = "_I18N.label.gebied";
  protected static final  String  LBL_NAAM        = "_I18N.label.naam";
  protected static final  String  LBL_OPMERKING   = "_I18N.label.opmerking";
  protected static final  String  LBL_RANG        = "_I18N.label.rang";
  protected static final  String  LBL_SOORT       = "_I18N.label.soort";
  protected static final  String  LBL_TAAL        = "_I18N.label.taal";
  protected static final  String  LBL_WAARNEMING  = "_I18N.label.waarneming";

  protected static void valideerGebiedId(Long gebiedId, List<Message> fouten) {
    fouten.addAll(
        new Validator.Builder().setWaarde(gebiedId)
                               .setAttribute(GebiedDto.COL_GEBIEDID)
                               .setLabel(LBL_GEBIED)
                               .setRequired()
                               .valideer().getFouten());
  }

  protected static void valideerOpmerking(String opmerking,
                                          List<Message> fouten) {
    fouten.addAll(
        new Validator.Builder().setWaarde(opmerking)
                               .setAttribute(TaxonDto.COL_OPMERKING)
                               .setLabel(LBL_OPMERKING)
                               .setMaxLengte(2000)
                               .valideer().getFouten());
  }

  protected static void valideerRang(String rang, List<Message> fouten) {
    fouten.addAll(
        new Validator.Builder().setWaarde(rang)
                               .setAttribute(RangDto.COL_RANG)
                               .setLabel(LBL_RANG)
                               .setMaxLengte(3)
                               .setRequired()
                               .valideer().getFouten());
  }

  protected static void valideerTaxonId(Long taxonId, List<Message> fouten) {
    fouten.addAll(
        new Validator.Builder().setWaarde(taxonId)
                               .setAttribute(TaxonDto.COL_TAXONID)
                               .setLabel(LBL_SOORT)
                               .setRequired()
                               .valideer().getFouten());
  }

  protected static void valideerWaarnemingId(Long waarnemingId,
                                             List<Message> fouten) {
    fouten.addAll(
        new Validator.Builder().setWaarde(waarnemingId)
                               .setAttribute(WaarnemingDto.COL_WAARNEMINGID)
                               .setLabel(LBL_WAARNEMING)
                               .setRequired()
                               .valideer().getFouten());
  }
}
