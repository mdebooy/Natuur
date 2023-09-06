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

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.form.Rang;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangValidatorTest {
  public static final Message ERR_NIVEAU  =
      new Message.Builder()
                 .setAttribute(RangDto.COL_NIVEAU)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.NIETGROTER)
                 .setParams(new Object[]{RangValidator.LBL_NIVEAU, 0})
                 .build();
  public static final Message REQ_NIVEAU  =
      new Message.Builder()
                 .setAttribute(RangDto.COL_NIVEAU)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RangValidator.LBL_NIVEAU})
                 .build();

  @Test
  public void testValideerGoedeRang() {
    var           rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang(TestConstants.RANG);
    rang.setNiveau(1L);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRang() {
    var           rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang(TestConstants.RANG_FOUT);
    rang.setNiveau(Long.MIN_VALUE);
    expResult.add(TestConstants.ERR_RANG);
    expResult.add(ERR_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRang() {
    var           rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    expResult.add(TestConstants.REQ_RANG);
    expResult.add(REQ_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRangDto() {
    var           rang      = new RangDto();
    List<Message> expResult = new ArrayList<>();

    rang.setRang(TestConstants.RANG);
    rang.setNiveau(1L);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRangDto() {
    var           rang      = new RangDto();
    List<Message> expResult = new ArrayList<>();

    rang.setRang(TestConstants.RANG_FOUT);
    rang.setNiveau(0L);
    expResult.add(TestConstants.ERR_RANG);
    expResult.add(ERR_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRangDto() {
    var           rang      = new RangDto();
    List<Message> expResult = new ArrayList<>();
    expResult.add(TestConstants.REQ_RANG);
    expResult.add(REQ_NIVEAU);
    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerRangNiveau() {
    var           rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang(TestConstants.RANG);
    rang.setNiveau(Long.MIN_VALUE);
    expResult.add(ERR_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerRangRang() {
    var           rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang(TestConstants.RANG_FOUT);
    rang.setNiveau(1L);
    expResult.add(TestConstants.ERR_RANG);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }
}
