/*
 * Copyright (c) 2023 Marco de Booij
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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Regiolijst;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class RegiolijstValidatorTest {
  private static final  Message ERR_OMSCHRIJVING  =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_OMSCHRIJVING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{RegiolijstValidator.LBL_OMSCHRIJVING,
                                         2000})
                 .build();
  private static final  Message REQ_DATUM         =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_DATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegiolijstValidator.LBL_DATUM})
                 .build();
  private static final  Message REQ_REGIOID       =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_REGIOID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegiolijstValidator.LBL_REGIOID})
                 .build();

  private static  Date          datum;
  private static  Message       errDatum;
  private static  Date          morgen;
  private static  Regiolijst    regiolijst;
  private static  RegiolijstDto regiolijstDto;

  private static void setLeeg(List<Message> expResult) {
    expResult.add(REQ_DATUM);
    expResult.add(REQ_REGIOID);
  }

  @BeforeClass
  public static void setUpClass() {
    Calendar  kalender  = Calendar.getInstance();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    datum     = new Date();
    morgen    = kalender.getTime();

    errDatum  = new Message.Builder()
                           .setAttribute(WaarnemingDto.COL_DATUM)
                           .setSeverity(Message.ERROR)
                           .setMessage(PersistenceConstants.FUTURE)
                           .setParams(new Object[]{Datum.fromDate(morgen)})
                           .build();

    regiolijst     = new Regiolijst();
    regiolijstDto  = new RegiolijstDto();

    regiolijst.setDatum(datum);
    regiolijst.setOmschrijving(TestConstants.OMSCHRIJVING);
    regiolijst.setRegioId(TestConstants.REGIOID);

    regiolijst.persist(regiolijstDto);
  }

  @Test
  public void testFouteRegiolijstValidator1() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setDatum(morgen);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(errDatum.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstValidator2() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setOmschrijving(
        DoosUtils.stringMetLengte(TestConstants.OMSCHRIJVING, 2001, "X"));

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_OMSCHRIJVING.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstValidatorDto1() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setDatum(morgen);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(errDatum.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstValidatorDto2() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setOmschrijving(
        DoosUtils.stringMetLengte(TestConstants.OMSCHRIJVING, 2001, "X"));

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_OMSCHRIJVING.toString(), result.get(0).toString());
  }

  @Test
  public void testGoedeRegiolijstValidator1() {
    List<Message> result  = RegiolijstValidator.valideer(regiolijst);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstValidator2() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setOmschrijving(
        DoosUtils.stringMetLengte(TestConstants.OMSCHRIJVING, 2000, "X"));
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstValidatorDto1() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstValidatorDto2() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setOmschrijving(
        DoosUtils.stringMetLengte(TestConstants.OMSCHRIJVING, 2000, "X"));
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeRegiolijstValidator() {
    var           instance  = new Regiolijst();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeRegiolijstValidatorDto() {
    var           instance  = new RegiolijstDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }
}
