/*
 * Copyright (c) 2021 Marco de Booij
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
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.form.Rangnaam;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangnaamValidatorTest {
  private static final  Message ERR_NAAM  =
      new Message.Builder()
                 .setAttribute(RangnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{NatuurValidator.LBL_NAAM, 255})
                 .build();
  private static final  Message ERR_TAAL  =
      new Message.Builder()
                 .setAttribute(RangnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]{NatuurValidator.LBL_TAAL, 3})
                 .build();

  private static final  Message REQ_NAAM  =
      new Message.Builder()
                 .setAttribute(RangnaamDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{NatuurValidator.LBL_NAAM})
                 .build();
  private static final  Message REQ_TAAL  =
      new Message.Builder()
                 .setAttribute(RangnaamDto.COL_TAAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{NatuurValidator.LBL_TAAL})
                 .build();

  @Test
  public void testValideerFouteRangnaam() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(DoosUtils.stringMetLengte(TestConstants.NAAM, 256, "X"));
    rangnaam.setRang(TestConstants.RANG_FOUT);
    rangnaam.setTaal(TestConstants.TAAL_FOUT);

    expResult.add(ERR_NAAM);
    expResult.add(TestConstants.ERR_RANG);
    expResult.add(ERR_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRangnaam() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(TestConstants.NAAM);
    rangnaam.setRang(TestConstants.RANG);
    rangnaam.setTaal(TestConstants.TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRangnaam() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_NAAM);
    expResult.add(TestConstants.REQ_RANG);
    expResult.add(REQ_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRangnaamDto() {
    RangnaamDto   rangnaam  = new RangnaamDto();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(DoosUtils.stringMetLengte(TestConstants.NAAM, 256, "X"));
    rangnaam.setRang(TestConstants.RANG_FOUT);
    rangnaam.setTaal(TestConstants.TAAL_FOUT);

    expResult.add(ERR_NAAM);
    expResult.add(TestConstants.ERR_RANG);
    expResult.add(ERR_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRangnaamDto() {
    RangnaamDto   rangnaam  = new RangnaamDto();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(TestConstants.NAAM);
    rangnaam.setRang(TestConstants.RANG);
    rangnaam.setTaal(TestConstants.TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRangnaamDto() {
    RangnaamDto   rangnaam  = new RangnaamDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_NAAM);
    expResult.add(TestConstants.REQ_RANG);
    expResult.add(REQ_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }
}
