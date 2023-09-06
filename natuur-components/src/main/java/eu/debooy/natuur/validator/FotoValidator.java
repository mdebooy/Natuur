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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
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

  private FotoValidator() {}

  public static List<Message> valideer(FotoDto foto) {
    return valideer(new Foto(foto));
  }

  public static List<Message> valideer(Foto foto) {
    List<Message> fouten  = new ArrayList<>();

    valideerFotoBestand(DoosUtils.nullToEmpty(foto.getFotoBestand()), fouten);
    valideerFotoDetail(DoosUtils.nullToEmpty(foto.getFotoDetail()), fouten);
    valideerOpmerking(DoosUtils.nullToEmpty(foto.getOpmerking()), fouten);
    valideerTaxonSeq(foto.getTaxonSeq(), fouten);
    valideerWaarnemingId(foto.getWaarnemingId(), fouten);

    return fouten;
  }

  private static void valideerFotoBestand(String fotoBestand,
                                          List<Message> fouten) {
    if (fotoBestand.length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(FotoDto.COL_FOTOBESTAND)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_FOTOBESTAND,
                                                    255})
                            .build());
    }
  }

  private static void valideerFotoDetail(String fotoDetail,
                                         List<Message> fouten) {
    if (fotoDetail.length() > 20) {
      fouten.add(new Message.Builder()
                            .setAttribute(FotoDto.COL_FOTODETAIL)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{LBL_FOTODETAIL, 20})
                            .build());
    }
  }

  private static void valideerTaxonSeq(Long taxonSeq, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(taxonSeq)) {
      fouten.add(new Message.Builder()
                            .setAttribute(FotoDto.COL_TAXONSEQ)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{LBL_SEQ})
                            .build());
    }
  }
}
