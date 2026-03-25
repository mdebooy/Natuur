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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.domain.RegiolijstDto;
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
  private static final  Message ERR_EINDDATUM_TKMST   =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_EINDDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FUTURE)
                 .setParams(new Object[]{RegiolijstValidator.LBL_EINDDATUM})
                 .build();
    private static final  Message ERR_STARTDATUM    =
        new Message.Builder()
                   .setAttribute(RegiolijstDto.COL_STARTDATUM)
                   .setSeverity(Message.ERROR)
                   .setMessage(PersistenceConstants.FUTURE)
                   .setParams(new Object[]{RegiolijstValidator.LBL_STARTDATUM})
                   .build();
  private static final  Message ERR_STARTDATUM_TKMST  =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_STARTDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.FUTURE)
                 .setParams(new Object[]{RegiolijstValidator.LBL_STARTDATUM})
                 .build();
  private static final  Message ERR_STARTDATUM_VOOR   =
      new Message.Builder()
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.DATEBEFORE)
                 .setParams(new Object[]{RegiolijstValidator.LBL_EINDDATUM,
                                         RegiolijstValidator.LBL_STARTDATUM})
                 .build();
  private static final  Message ERR_OMSCHRIJVING  =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_OMSCHRIJVING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{RegiolijstValidator.LBL_OMSCHRIJVING,
                                         2000})
                 .build();
  private static final  Message REQ_STARTDATUM    =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_STARTDATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegiolijstValidator.LBL_STARTDATUM})
                 .build();
  private static final  Message REQ_REGIOID       =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_REGIOID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegiolijstValidator.LBL_REGIOID})
                 .build();

  private static  Date          einddatum;
  private static  Date          startdatum;
  private static  Date          morgen;
  private static  Regiolijst    regiolijst;
  private static  RegiolijstDto regiolijstDto;
  private static  Date          toekomstdatum1;
  private static  Date          toekomstdatum2;

  private static void setLeeg(List<Message> expResult) {
    expResult.add(REQ_REGIOID);
    expResult.add(REQ_STARTDATUM);
  }

  @BeforeClass
  public static void setUpClass() {
    einddatum          = new Date();
    Calendar  kalender  = Calendar.getInstance();
    kalender.add(Calendar.DAY_OF_YEAR, -1);
    startdatum    = kalender.getTime();
    kalender.add(Calendar.DAY_OF_YEAR, 2);
    morgen        = kalender.getTime();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    toekomstdatum1  = kalender.getTime();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    toekomstdatum2  = kalender.getTime();

    regiolijst    = new Regiolijst();
    regiolijstDto = new RegiolijstDto();

    regiolijst.setStartdatum(startdatum);
    regiolijst.setOmschrijving(NatuurTestConstants.OMSCHRIJVING);
    regiolijst.setRegioId(NatuurTestConstants.REGIOID);

    regiolijst.persist(regiolijstDto);
  }

  @Test
  public void testFouteRegiolijst01() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setStartdatum(morgen);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijst02() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setOmschrijving(
        DoosUtils.stringMetLengte(NatuurTestConstants.OMSCHRIJVING, 2001, "X"));

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_OMSCHRIJVING.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijst11() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setStartdatum(toekomstdatum1);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM_TKMST.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijst12() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setEinddatum(toekomstdatum2);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_EINDDATUM_TKMST.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijst13() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setEinddatum(startdatum);
    instance.setStartdatum(einddatum);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM_VOOR.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstDto01() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setStartdatum(morgen);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstDto02() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setOmschrijving(
        DoosUtils.stringMetLengte(NatuurTestConstants.OMSCHRIJVING, 2001, "X"));

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_OMSCHRIJVING.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstDto11() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setStartdatum(toekomstdatum1);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM_TKMST.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstDto12() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setEinddatum(toekomstdatum2);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_EINDDATUM_TKMST.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijsDtot13() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setEinddatum(startdatum);
    instance.setStartdatum(einddatum);

    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STARTDATUM_VOOR.toString(), result.get(0).toString());
  }

  @Test
  public void testGoedeRegiolijst01() {
    List<Message> result  = RegiolijstValidator.valideer(regiolijst);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijst02() {
    var           instance  = new Regiolijst(regiolijstDto);

    instance.setOmschrijving(
        DoosUtils.stringMetLengte(NatuurTestConstants.OMSCHRIJVING, 2000, "X"));
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstDto01() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstDto02() {
    var           instance  = new RegiolijstDto();

    regiolijst.persist(instance);
    instance.setOmschrijving(
        DoosUtils.stringMetLengte(NatuurTestConstants.OMSCHRIJVING, 2000, "X"));
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeRegiolijst() {
    var           instance  = new Regiolijst();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeRegiolijstDto() {
    var           instance  = new RegiolijstDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testNullRegiolijst() {
    Regiolijst    instance  = null;
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Regiolijst.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullRegiolijstDto() {
    RegiolijstDto instance  = null;
    List<Message> result    = RegiolijstValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(RegiolijstDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }
}
