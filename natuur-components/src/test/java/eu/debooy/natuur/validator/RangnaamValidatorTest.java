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
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_FOUT;
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
      new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                  "_I18N.label.naam", 255);
  private static final  Message ERR_TAAL  =
      new Message(Message.ERROR, PersistenceConstants.FIXLENGTH,
                  "_I18N.label.taal", 2);

  private static final  Message REQ_NAAM  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.naam");
  private static final  Message REQ_TAAL  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.taal");

  @Test
  public void testValideerFouteRangnaam() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(DoosUtils.stringMetLengte(NAAM, 256, "X"));
    rangnaam.setTaal(TAAL_FOUT);

    expResult.add(ERR_NAAM);
    expResult.add(ERR_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRangnaam() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(NAAM);
    rangnaam.setTaal(TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRangnaam() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_NAAM);
    expResult.add(REQ_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteRangnaamDto() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(DoosUtils.stringMetLengte(NAAM, 256, "X"));
    rangnaam.setTaal(TAAL_FOUT);

    expResult.add(ERR_NAAM);
    expResult.add(ERR_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRangnaamDto() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    rangnaam.setNaam(NAAM);
    rangnaam.setTaal(TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeRangnaamDto() {
    Rangnaam      rangnaam  = new Rangnaam();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_NAAM);
    expResult.add(REQ_TAAL);

    List<Message> result    = RangnaamValidator.valideer(rangnaam);
    assertEquals(expResult.toString(), result.toString());
  }
}
