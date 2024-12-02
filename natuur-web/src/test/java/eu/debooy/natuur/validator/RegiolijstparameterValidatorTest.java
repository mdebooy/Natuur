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
import eu.debooy.natuur.form.Regiolijstparameter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class RegiolijstparameterValidatorTest {
  public static final Message ERR_DUBBEL  =
      new Message.Builder()
                 .setSeverity(Message.ERROR)
                 .setMessage(RegiolijstparameterValidator.ERR_TALEN)
                 .build();
  public static final Message ERR_LEEG    =
      new Message.Builder()
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.EMPTY)
                 .build();
  public static final Message ERR_TAAL1   =
      new Message.Builder()
                 .setAttribute(Regiolijstparameter.COL_TAAL1)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]
                                {NatuurValidator.LBL_TAAL, 3})
                 .build();
  public static final Message ERR_TAAL2   =
      new Message.Builder()
                 .setAttribute(Regiolijstparameter.COL_TAAL2)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]
                                {NatuurValidator.LBL_TAAL, 3})
                 .build();
  public static final Message ERR_TAAL3   =
      new Message.Builder()
                 .setAttribute(Regiolijstparameter.COL_TAAL3)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FIXLENGTH)
                 .setParams(new Object[]
                                {NatuurValidator.LBL_TAAL, 3})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_TAAL1);
    expResult.add(ERR_TAAL2);
    expResult.add(ERR_TAAL3);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(ERR_LEEG);
  }

  @Test
  public void testDubbeleTalen() {
    var instance  = new Regiolijstparameter();

    instance.setTaal1(TestConstants.TAAL);
    instance.setTaal2(TestConstants.TAAL);
    instance.setTaal3(TestConstants.TAAL);
    var result    = RegiolijstparameterValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(RegiolijstparameterValidator.ERR_TALEN,
                 result.get(0).getMessage());

    instance.setTaal2(TestConstants.TAAL_GR);
    result    = RegiolijstparameterValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(RegiolijstparameterValidator.ERR_TALEN,
                 result.get(0).getMessage());

    instance.setTaal3(TestConstants.TAAL_GR);
    result    = RegiolijstparameterValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(RegiolijstparameterValidator.ERR_TALEN,
                 result.get(0).getMessage());

    instance.setTaal3(TestConstants.TAAL_GR);
    result    = RegiolijstparameterValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(RegiolijstparameterValidator.ERR_TALEN,
                 result.get(0).getMessage());

    instance.setTaal2(TestConstants.TAAL);
    result    = RegiolijstparameterValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(RegiolijstparameterValidator.ERR_TALEN,
                 result.get(0).getMessage());

    instance.setTaal2("");
    instance.setTaal3("");
    result    = RegiolijstparameterValidator.valideer(instance);

    assertTrue(result.isEmpty());

    instance.setTaal2(null);
    instance.setTaal3(null);
    result    = RegiolijstparameterValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testEmpty() {
    var instance  = new Regiolijstparameter();

    instance.setTaal3(TestConstants.TAAL_KL);

    List<Message> result    = RegiolijstparameterValidator.valideer(instance);
    assertTrue(result.isEmpty());
    instance.setTaal2(TestConstants.TAAL_GR);

    result    = RegiolijstparameterValidator.valideer(instance);
    assertTrue(result.isEmpty());
    instance.setTaal1(TestConstants.TAAL);

    result    = RegiolijstparameterValidator.valideer(instance);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testNullRegiolijstparameter() {
    Regiolijstparameter instance = null;
    var                 result    =
        RegiolijstparameterValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Regiolijstparameter.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testValideerFouteRegiolijstparameter() {
    var           instance  = new Regiolijstparameter();
    List<Message> expResult = new ArrayList<>();

    setFouten(expResult);

    instance.setTaal1(TestConstants.TAAL.substring(0, 1));
    instance.setTaal2(TestConstants.TAAL_GR.substring(0, 1));
    instance.setTaal3(TestConstants.TAAL_KL.substring(0, 1));

    List<Message> result    = RegiolijstparameterValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());

    instance.setTaal1(DoosUtils.stringMetLengte(TestConstants.TAAL, 4, "X"));
    instance.setTaal2(DoosUtils.stringMetLengte(TestConstants.TAAL_GR, 4, "X"));
    instance.setTaal3(DoosUtils.stringMetLengte(TestConstants.TAAL_KL, 4, "X"));

    result    = RegiolijstparameterValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeRegiolijstparameter() {
    var instance  = new Regiolijstparameter();

    instance.setTaal1(TestConstants.TAAL);
    instance.setTaal2(TestConstants.TAAL_GR);
    instance.setTaal3(TestConstants.TAAL_KL);

    List<Message> result    = RegiolijstparameterValidator.valideer(instance);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerLegeRegiolijstparameter() {
    var           instance  = new Regiolijstparameter();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstparameterValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }
}
