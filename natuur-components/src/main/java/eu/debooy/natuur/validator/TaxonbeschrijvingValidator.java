/*
 * Copyright (c) 2025 Marco de Booij
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

import eu.debooy.doosutils.ComponentsUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.TaxonbeschrijvingDto;
import eu.debooy.natuur.form.Taxonbeschrijving;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco de Booij
 */
public class TaxonbeschrijvingValidator extends NatuurValidator {
  protected static final  String  LBL_BESCHRIJVING      =
      "_I18N.label.beschrijving";
  protected static final  String  LBL_BESCHRIJVINGTYPE  =
      "_I18N.label.beschrijvingtype";

  private TaxonbeschrijvingValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(TaxonbeschrijvingDto taxonbeschrijving) {
    if (null == taxonbeschrijving) {
      return ComponentsUtils.objectIsNull(
          TaxonbeschrijvingDto.class.getSimpleName());
    }

    return valideer(new Taxonbeschrijving(taxonbeschrijving));
  }

  public static List<Message> valideer(Taxonbeschrijving taxonbeschrijving) {
    if (null == taxonbeschrijving) {
      return ComponentsUtils.objectIsNull(Taxonbeschrijving.class
                                                           .getSimpleName());
    }

    List<Message> fouten  = new ArrayList<>();

    fouten.addAll(
        new Validator.Builder()
                     .setWaarde(taxonbeschrijving.getBeschrijving())
                     .setAttribute(TaxonbeschrijvingDto.COL_BESCHRIJVING)
                     .setLabel(LBL_BESCHRIJVING)
                     .setMaxLengte(4000)
                     .setRequired()
                     .valideer().getFouten());
    fouten.addAll(
        new Validator.Builder()
                     .setWaarde(taxonbeschrijving.getBeschrijvingtype())
                     .setAttribute(TaxonbeschrijvingDto.COL_BESCHRIJVINGTYPE)
                     .setLabel(LBL_BESCHRIJVINGTYPE)
                     .setLowerCase()
                     .setMaxLengte(10)
                     .setRequired()
                     .valideer().getFouten());

    return fouten;
  }
}
