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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.form.Foto;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class FotoValidatorTest {
  public static final Message ERR_FOTOBESTAND   =
      new Message.Builder()
                 .setAttribute(FotoDto.COL_FOTOBESTAND)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{FotoValidator.LBL_FOTOBESTAND, 255})
                 .build();
  public static final Message ERR_FOTODETAIL    =
      new Message.Builder()
                 .setAttribute(FotoDto.COL_FOTODETAIL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{FotoValidator.LBL_FOTODETAIL, 20})
                 .build();
  public static final Message REQ_TAXONSEQ      =
      new Message.Builder()
                 .setAttribute(FotoDto.COL_TAXONSEQ)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{FotoValidator.LBL_SEQ})
                 .build();
  public static final Message REQ_WAARNEMINGID  =
      new Message.Builder()
                 .setAttribute(FotoDto.COL_WAARNEMINGID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{NatuurValidator.LBL_WAARNEMING})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_FOTOBESTAND);
    expResult.add(ERR_FOTODETAIL);
    expResult.add(TestConstants.ERR_OPMERKING);
    expResult.add(REQ_TAXONSEQ);
    expResult.add(REQ_WAARNEMINGID);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_WAARNEMINGID);
  }

  @Test
  public void testValideerFouteFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(DoosUtils.stringMetLengte(TestConstants.FOTOBESTAND,
                                                  256, "X"));
    foto.setFotoDetail(DoosUtils.stringMetLengte(TestConstants.FOTODETAIL,
                                                 21, "X"));
    foto.setOpmerking(DoosUtils.stringMetLengte(TestConstants.OPMERKING,
                                                2001, "X"));
    foto.setTaxonSeq(null);
    foto.setWaarnemingId(null);

    setFouten(expResult);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(TestConstants.FOTOBESTAND);
    foto.setFotoDetail(TestConstants.FOTODETAIL);
    foto.setOpmerking(TestConstants.OPMERKING);
    foto.setWaarnemingId(TestConstants.WAARNEMINGID);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(DoosUtils.stringMetLengte(TestConstants.FOTOBESTAND,
                                                  256, "X"));
    foto.setFotoDetail(DoosUtils.stringMetLengte(TestConstants.FOTODETAIL,
                                                 21, "X"));
    foto.setOpmerking(DoosUtils.stringMetLengte(TestConstants.OPMERKING,
                                                2001, "X"));
    foto.setTaxonSeq(null);
    foto.setWaarnemingId(null);

    setFouten(expResult);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(TestConstants.FOTOBESTAND);
    foto.setFotoDetail(TestConstants.FOTODETAIL);
    foto.setOpmerking(TestConstants.OPMERKING);
    foto.setWaarnemingId(TestConstants.WAARNEMINGID);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }
}
