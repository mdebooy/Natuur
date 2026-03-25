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
package eu.debooy.natuur.form;

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.test.TestConstants;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.domain.RegiolijstDto;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTest {
  private static  Date          einddatum;
  private static  Date          gisteren;
  private static  Date          morgen;
  private static  Regiolijst    regiolijst;
  private static  RegiolijstDto regiolijstDto;
  private static  Date          startdatum;

  @BeforeClass
  public static void setUpClass() throws ParseException {
    startdatum         = Datum.stripTime(Datum.toDate(TestConstants.RUSHDATUM,
                                                 TestConstants.FORMAAT));

    var kalender  = Calendar.getInstance();
    kalender.setTime(startdatum);
    kalender.add(Calendar.DATE, -1);
    gisteren      = kalender.getTime();
    kalender.add(Calendar.DATE, 2);
    morgen        = kalender.getTime();
    kalender.add(Calendar.DATE, 6);
    einddatum     = kalender.getTime();

    regiolijst    = new Regiolijst();
    regiolijstDto = new RegiolijstDto();

    regiolijst.setStartdatum(startdatum);
    regiolijst.setOmschrijving(NatuurTestConstants.OMSCHRIJVING);
    regiolijst.setRegioId(NatuurTestConstants.REGIOID);
    regiolijst.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);

    regiolijst.persist(regiolijstDto);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Regiolijst();
    var groter  = new Regiolijst();
    var kleiner = new Regiolijst();

    gelijk.setRegioId(regiolijst.getRegioId());
    gelijk.setStartdatum(regiolijst.getStartdatum());
    gelijk.setRegiolijstId(regiolijst.getRegiolijstId());
    groter.setRegioId(regiolijst.getRegioId());
    groter.setStartdatum(regiolijst.getStartdatum());
    groter.setRegiolijstId(regiolijst.getRegiolijstId() + 1);
    kleiner.setRegioId(regiolijst.getRegioId());
    kleiner.setStartdatum(regiolijst.getStartdatum());
    kleiner.setRegiolijstId(regiolijst.getRegiolijstId() - 1);

    assertTrue(regiolijst.compareTo(groter) < 0);
    assertEquals(0, regiolijst.compareTo(gelijk));
    assertTrue(regiolijst.compareTo(kleiner) > 0);

    gelijk.setRegioId(regiolijst.getRegioId());
    gelijk.setStartdatum(regiolijst.getStartdatum());
    groter.setRegioId(regiolijst.getRegioId());
    groter.setStartdatum(morgen);
    kleiner.setRegioId(regiolijst.getRegioId());
    kleiner.setStartdatum(gisteren);

    assertTrue(regiolijst.compareTo(groter) < 0);
    assertEquals(0, regiolijst.compareTo(gelijk));
    assertTrue(regiolijst.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Regiolijst();

    assertEquals(regiolijst, regiolijst);
    assertNotEquals(regiolijst, null);
    assertNotEquals(regiolijst, NatuurTestConstants.RANGNAAM);
    assertNotEquals(regiolijst, instance);

    instance.setRegioId(regiolijst.getRegioId());
    instance.setStartdatum(regiolijst.getStartdatum());
    instance.setRegiolijstId(regiolijst.getRegiolijstId());
    assertEquals(regiolijst, instance);

    instance  = new Regiolijst(regiolijstDto);
    assertEquals(regiolijst, instance);
  }

  @Test
  public void testGetEinddatum() {
    assertNull(regiolijst.getEinddatum());
  }

  @Test
  public void testGetOmschrijving() {
    assertEquals(NatuurTestConstants.OMSCHRIJVING,
                 regiolijst.getOmschrijving());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(NatuurTestConstants.REGIOID,
                 regiolijst.getRegioId());
  }

  @Test
  public void testGetRegioLijstId() {
    assertEquals(NatuurTestConstants.REGIOLIJSTID,
                 regiolijst.getRegiolijstId());
  }

  @Test
  public void testGetStartdatum() {
    assertEquals(startdatum, regiolijst.getStartdatum());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.REGIOLIJST_HASH, regiolijst.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Regiolijst();

    assertNull(instance.getStartdatum());
    assertNull(instance.getOmschrijving());
    assertNull(instance.getRegioId());
  }

  @Test
  public void testInit2() {
    var instance  = new Regiolijst(regiolijstDto);

    assertEquals(regiolijstDto.getStartdatum(), instance.getStartdatum());
    assertEquals(regiolijstDto.getOmschrijving(), instance.getOmschrijving());
    assertEquals(regiolijstDto.getRegioId(), instance.getRegioId());
  }

  @Test
  public void testPersist() {
    var parameter = new RegiolijstDto();

    regiolijst.persist(parameter);

    assertEquals(regiolijst.getStartdatum(), parameter.getStartdatum());
    assertEquals(regiolijst.getOmschrijving(), parameter.getOmschrijving());
    assertEquals(regiolijst.getRegioId(), parameter.getRegioId());

    regiolijst.persist(parameter);

    assertEquals(regiolijst.getStartdatum(), parameter.getStartdatum());
    assertEquals(regiolijst.getOmschrijving(), parameter.getOmschrijving());
    assertEquals(regiolijst.getRegioId(), parameter.getRegioId());
  }

  @Test
  public void testSetEinddatum() {
    var instance  = new Regiolijst();

    assertNull(instance.getEinddatum());

    instance.setEinddatum(einddatum);

    assertEquals(einddatum, instance.getEinddatum());

    // Geen reference maar value?
    Date  datum = instance.getEinddatum();

    assertEquals(datum, instance.getEinddatum());
    assertEquals(einddatum, instance.getEinddatum());

    datum.setTime(0);

    assertNotEquals(datum,   instance.getEinddatum());
    assertEquals(einddatum, instance.getEinddatum());

    instance.setEinddatum(null);
    assertNull(instance.getEinddatum());
  }

  @Test
  public void testSetOmschrijving() {
    var instance  = new Regiolijst();

    assertNotEquals(NatuurTestConstants.OMSCHRIJVING,
                    instance.getOmschrijving());

    instance.setOmschrijving(NatuurTestConstants.OMSCHRIJVING);

    assertEquals(NatuurTestConstants.OMSCHRIJVING, instance.getOmschrijving());

    instance.setOmschrijving(null);

    assertNull(instance.getOmschrijving());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new Regiolijst();

    assertNotEquals(NatuurTestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(NatuurTestConstants.REGIOID);

    assertEquals(NatuurTestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetRegiolijstId() {
    var instance  = new Regiolijst();

    assertNotEquals(NatuurTestConstants.REGIOLIJSTID,
                    instance.getRegiolijstId());

    instance.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);

    assertEquals(NatuurTestConstants.REGIOLIJSTID, instance.getRegiolijstId());
  }

  @Test
  public void testSetStartdatum() {
    var instance  = new Regiolijst();

    assertNotEquals(startdatum, instance.getStartdatum());

    instance.setStartdatum(startdatum);

    assertEquals(startdatum, instance.getStartdatum());

    // Geen reference maar value?
    Date  datum = instance.getStartdatum();

    assertEquals(datum, instance.getStartdatum());
    assertEquals(startdatum, instance.getStartdatum());

    datum.setTime(0);

    assertNotEquals(datum,   instance.getStartdatum());
    assertEquals(startdatum, instance.getStartdatum());

    instance.setStartdatum(null);
    assertNull(instance.getStartdatum());
  }
}
