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
package eu.debooy.natuur.domain;

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.test.TestConstants;
import eu.debooy.natuur.NatuurTestConstants;
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
public class RegiolijstDtoTest {
  private static  Date          einddatum;
  private static  Date          gisteren;
  private static  Date          morgen;
  private static  RegiolijstDto regiolijstDto;
  private static  Date          startdatum;

  @BeforeClass
  public static void beforeClass() throws ParseException {
    startdatum         = Datum.stripTime(Datum.toDate(TestConstants.RUSHDATUM,
                                                 TestConstants.FORMAAT));

    var kalender  = Calendar.getInstance();
    kalender.setTime(startdatum);
    kalender.add(Calendar.DATE, -1);
    gisteren      = kalender.getTime();
    kalender.add(Calendar.DATE, 2);
    morgen        = kalender.getTime();

    regiolijstDto   = new RegiolijstDto();

    regiolijstDto.setOmschrijving(NatuurTestConstants.OMSCHRIJVING);
    regiolijstDto.setRegioId(NatuurTestConstants.REGIOID);
    regiolijstDto.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);
    regiolijstDto.setStartdatum(startdatum);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstDto();
    var groter  = new RegiolijstDto();
    var kleiner = new RegiolijstDto();

    gelijk.setRegioId(regiolijstDto.getRegioId());
    gelijk.setStartdatum(regiolijstDto.getStartdatum());
    gelijk.setRegiolijstId(regiolijstDto.getRegiolijstId());
    groter.setRegioId(regiolijstDto.getRegioId());
    groter.setStartdatum(regiolijstDto.getStartdatum());
    groter.setRegiolijstId(regiolijstDto.getRegiolijstId() + 1);
    kleiner.setRegioId(regiolijstDto.getRegioId());
    kleiner.setStartdatum(regiolijstDto.getStartdatum());
    kleiner.setRegiolijstId(regiolijstDto.getRegiolijstId() - 1);

    assertTrue(regiolijstDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstDto.compareTo(gelijk));
    assertTrue(regiolijstDto.compareTo(kleiner) > 0);

    gelijk.setRegioId(regiolijstDto.getRegioId());
    gelijk.setStartdatum(regiolijstDto.getStartdatum());
    groter.setRegioId(regiolijstDto.getRegioId());
    groter.setStartdatum(morgen);
    kleiner.setRegioId(regiolijstDto.getRegioId());
    kleiner.setStartdatum(gisteren);

    assertTrue(regiolijstDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstDto.compareTo(gelijk));
    assertTrue(regiolijstDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegiolijstDto();

    assertEquals(regiolijstDto, regiolijstDto);
    assertNotEquals(regiolijstDto, null);
    assertNotEquals(regiolijstDto, NatuurTestConstants.RANGNAAM);
    assertNotEquals(regiolijstDto, instance);

    instance.setRegioId(regiolijstDto.getRegioId());
    instance.setStartdatum(regiolijstDto.getStartdatum());
    instance.setRegiolijstId(regiolijstDto.getRegiolijstId());
    assertEquals(regiolijstDto, instance);

    instance.setRegioId(regiolijstDto.getRegioId() + 1);
    assertNotEquals(regiolijstDto, instance);
  }

  @Test
  public void testGetEinddatum() {
    assertNull(regiolijstDto.getEinddatum());
  }

  @Test
  public void testGetOmschrijving() {
    assertEquals(NatuurTestConstants.OMSCHRIJVING,
                 regiolijstDto.getOmschrijving());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(NatuurTestConstants.REGIOID, regiolijstDto.getRegioId());
  }

  @Test
  public void testGetRegiolijstId() {
    assertEquals(NatuurTestConstants.REGIOLIJSTID,
                 regiolijstDto.getRegiolijstId());
  }

  @Test
  public void testGetStartdatum() {
    assertEquals(startdatum, regiolijstDto.getStartdatum());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.REGIOLIJST_HASH, regiolijstDto.hashCode());
  }

  @Test
  public void testSetEinddatum() {
    var instance  = new RegiolijstDto();

    assertNotEquals(startdatum, instance.getStartdatum());

    instance.setStartdatum(startdatum);

    assertEquals(startdatum, instance.getStartdatum());

    // Geen reference maar value?
    Date  datum2  = instance.getStartdatum();

    assertEquals(datum2, instance.getStartdatum());
    assertEquals(startdatum, instance.getStartdatum());

    datum2.setTime(0);

    assertNotEquals(datum2, instance.getStartdatum());
    assertEquals(startdatum, instance.getStartdatum());

    instance.setStartdatum(null);

    assertNull(instance.getStartdatum());
  }

  @Test
  public void testSetOmschrijving() {
    var instance  = new RegiolijstDto();
    assertNotEquals(NatuurTestConstants.OMSCHRIJVING,
                    instance.getOmschrijving());

    instance.setOmschrijving(NatuurTestConstants.OMSCHRIJVING);

    assertEquals(NatuurTestConstants.OMSCHRIJVING,
                 instance.getOmschrijving());

    instance.setOmschrijving(null);

    assertNull(instance.getOmschrijving());
  }

  @Test
  public void testSetRegioId() {
    var instance    = new RegiolijstDto();

    assertNotEquals(NatuurTestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(NatuurTestConstants.REGIOID);

    assertEquals(NatuurTestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetRegiolijstId() {
    var instance    = new RegiolijstDto();

    assertNotEquals(NatuurTestConstants.REGIOLIJSTID,
                    instance.getRegiolijstId());

    instance.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);

    assertEquals(NatuurTestConstants.REGIOLIJSTID, instance.getRegiolijstId());
  }

  @Test
  public void testSetStartdatum() {
    var instance  = new RegiolijstDto();

    assertNotEquals(startdatum, instance.getStartdatum());

    instance.setStartdatum(startdatum);

    assertEquals(startdatum, instance.getStartdatum());

    // Geen reference maar value?
    Date  datum2  = instance.getStartdatum();

    assertEquals(datum2, instance.getStartdatum());
    assertEquals(startdatum, instance.getStartdatum());

    datum2.setTime(0);

    assertNotEquals(datum2, instance.getStartdatum());
    assertEquals(startdatum, instance.getStartdatum());

    instance.setStartdatum(null);

    assertNull(instance.getStartdatum());
  }
}
