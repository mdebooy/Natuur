/**
 * Copyright (c) 2016 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.validator.Validator;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.form.Foto;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class FotoValidator extends NatuurValidator {
  protected static final  String  LBL_FOTOBESTAND = "_I18N.label.fotobestand";
  protected static final  String  LBL_FOTODETAIL  = "_I18N.label.fotodetail";
  protected static final  String  LBL_SEQ         = "_I18N.label.seq";

  private FotoValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Message> valideer(FotoDto foto) {
    if (null == foto) {
      return ComponentsUtils.objectIsNull(FotoDto.class.getSimpleName());
    }

    return valideer(new Foto(foto));
  }

  public static List<Message> valideer(Foto foto) {
    List<Message> fouten  = new ArrayList<>();
    if (null == foto) {
      return ComponentsUtils.objectIsNull(Foto.class.getSimpleName());
    }


    fouten.addAll(new Validator.Builder()
                               .setWaarde(foto.getFotoBestand())
                               .setAttribute(FotoDto.COL_FOTOBESTAND)
                               .setLabel(LBL_FOTOBESTAND)
                               .setMaxLengte(255)
                               .valideer().getFouten());
    fouten.addAll(new Validator.Builder()
                               .setWaarde(foto.getFotoDetail())
                               .setAttribute(FotoDto.COL_FOTODETAIL)
                               .setLabel(LBL_FOTODETAIL)
                               .setMaxLengte(20)
                               .valideer().getFouten());
    valideerOpmerking(DoosUtils.nullToEmpty(foto.getOpmerking()), fouten);
    fouten.addAll(new Validator.Builder()
                               .setWaarde(foto.getTaxonSeq())
                               .setAttribute(FotoDto.COL_TAXONSEQ)
                               .setLabel(LBL_SEQ)
                               .setRequired()
                               .valideer().getFouten());
    valideerWaarnemingId(foto.getWaarnemingId(), fouten);

    return fouten;
  }
}
