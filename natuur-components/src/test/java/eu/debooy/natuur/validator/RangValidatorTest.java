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
import static eu.debooy.natuur.TestConstants.ERR_RANG;
import static eu.debooy.natuur.TestConstants.REQ_RANG;
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
      new Message(Message.ERROR, PersistenceConstants.NIETGROTER,
                  "_I18N.label.niveau", 0);
  public static final Message REQ_NIVEAU  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.niveau");

  @Test
  public void testValideerGoedeRang() {
    Rang          rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang("xxx");
    rang.setNiveau(1L);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRang() {
    Rang          rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang("xxxx");
    rang.setNiveau(Long.MIN_VALUE);
    expResult.add(ERR_RANG);
    expResult.add(ERR_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRang() {
    Rang          rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_RANG);
    expResult.add(REQ_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRangDto() {
    RangDto       rang      = new RangDto();
    List<Message> expResult = new ArrayList<>();

    rang.setRang("xxx");
    rang.setNiveau(1L);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRangDto() {
    RangDto       rang      = new RangDto();
    List<Message> expResult = new ArrayList<>();

    rang.setRang("xxxx");
    rang.setNiveau(0L);
    expResult.add(ERR_RANG);
    expResult.add(ERR_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRangDto() {
    RangDto       rang      = new RangDto();
    List<Message> expResult = new ArrayList<>();
    expResult.add(REQ_RANG);
    expResult.add(REQ_NIVEAU);
    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerRangNiveau() {
    Rang          rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang("xxx");
    rang.setNiveau(Long.MIN_VALUE);
    expResult.add(ERR_NIVEAU);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerRangRang() {
    Rang          rang      = new Rang();
    List<Message> expResult = new ArrayList<>();

    rang.setRang("xxxx");
    rang.setNiveau(1L);
    expResult.add(ERR_RANG);

    List<Message> result    = RangValidator.valideer(rang);
    assertEquals(expResult.toString(), result.toString());
  }
}
