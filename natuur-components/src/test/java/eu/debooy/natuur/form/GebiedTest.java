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
package eu.debooy.natuur.form;

import eu.debooy.doosutils.DoosUtils;
import static eu.debooy.natuur.TestConstants.COORDINATEN;
import static eu.debooy.natuur.TestConstants.GEBIEDID;
import static eu.debooy.natuur.TestConstants.GEBIEDID_HASH;
import static eu.debooy.natuur.TestConstants.LANDID;
import static eu.debooy.natuur.TestConstants.LATITUDE;
import static eu.debooy.natuur.TestConstants.LATITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE;
import static eu.debooy.natuur.TestConstants.LONGITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.NAAM_GR;
import static eu.debooy.natuur.TestConstants.NAAM_KL;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.GebiedDto;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class GebiedTest {
  private static  Gebied    gebied;
  private static  GebiedDto gebiedDto;

  @BeforeClass
  public static void setUpClass() {
    // Voor de juiste decimal point.
    Locale.setDefault(new Locale("nl"));

    gebied    = TestUtils.getGebied();
    gebiedDto = TestUtils.getGebiedDto();
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Gebied(gebied);
    var groter  = new Gebied();
    var kleiner = new Gebied();

    groter.setGebiedId(gebied.getGebiedId() + 1);
    kleiner.setGebiedId(gebied.getGebiedId() - 1);

    assertTrue(gebied.compareTo(groter) < 0);
    assertEquals(0, gebied.compareTo(gelijk));
    assertTrue(gebied.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Gebied();

    assertEquals(gebied, gebied);
    assertNotEquals(gebied, null);
    assertNotEquals(gebied, NAAM);
    assertNotEquals(gebied, instance);

    instance.setGebiedId(gebied.getGebiedId());
    assertEquals(gebied, instance);

    instance  = new Gebied(gebied);
    assertEquals(gebied, instance);

    instance  = new Gebied(gebiedDto);
    assertEquals(gebied, instance);
  }

  @Test
  public void testGetCoordinaten() {
    assertEquals(COORDINATEN, gebied.getCoordinaten());
  }

  @Test
  public void testGetGebiedId() {
    assertEquals(GEBIEDID, gebied.getGebiedId());
  }

  @Test
  public void testGetLandId() {
    assertEquals(LANDID, gebied.getLandId());
  }

  @Test
  public void testGetLatitude() {
    assertEquals(LATITUDE, gebied.getLatitude());
  }

  @Test
  public void testGetLatitudeGraden() {
    assertEquals(LATITUDE_GRADEN, gebied.getLatitudeGraden());
  }

  @Test
  public void testGetLatitudeMinuten() {
    assertEquals(LATITUDE_MINUTEN, gebied.getLatitudeMinuten());
  }

  @Test
  public void testGetLatitudeSeconden() {
    assertEquals(LATITUDE_SECONDEN, gebied.getLatitudeSeconden());
  }

  @Test
  public void testGetLongitude() {
    assertEquals(LONGITUDE, gebied.getLongitude());
  }

  @Test
  public void testGetLongitudeGraden() {
    assertEquals(LONGITUDE_GRADEN, gebied.getLongitudeGraden());
  }

  @Test
  public void testGetLongitudeMinuten() {
    assertEquals(LONGITUDE_MINUTEN, gebied.getLongitudeMinuten());
  }

  @Test
  public void testGetLongitudeSeconden() {
    assertEquals(LONGITUDE_SECONDEN, gebied.getLongitudeSeconden());
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, gebied.getNaam());
  }

  @Test
  public void testHashCode() {
    assertEquals(GEBIEDID_HASH, gebied.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Gebied();

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testInit2() {
    var instance  = new Gebied(gebied);

    assertEquals(gebied.getGebiedId(), instance.getGebiedId());
    assertEquals(gebied.getLandId(), instance.getLandId());
    assertEquals(gebied.getLatitude(), instance.getLatitude());
    assertEquals(gebied.getLatitudeGraden(), instance.getLatitudeGraden());
    assertEquals(gebied.getLatitudeMinuten(), instance.getLatitudeMinuten());
    assertEquals(gebied.getLatitudeSeconden(), instance.getLatitudeSeconden());
    assertEquals(gebied.getLongitude(), instance.getLongitude());
    assertEquals(gebied.getLongitudeGraden(), instance.getLongitudeGraden());
    assertEquals(gebied.getLongitudeMinuten(), instance.getLongitudeMinuten());
    assertEquals(gebied.getLongitudeSeconden(),
                 instance.getLongitudeSeconden());
    assertEquals(gebied.getNaam(), instance.getNaam());
  }

  @Test
  public void testInit3() {
    var instance  = new Gebied(gebiedDto);

    assertEquals(gebiedDto.getGebiedId(), instance.getGebiedId());
    assertEquals(gebiedDto.getLandId(), instance.getLandId());
    assertEquals(gebiedDto.getLatitude(), instance.getLatitude());
    assertEquals(gebiedDto.getLatitudeGraden(), instance.getLatitudeGraden());
    assertEquals(gebiedDto.getLatitudeMinuten(), instance.getLatitudeMinuten());
    assertEquals(gebiedDto.getLatitudeSeconden(),
                 instance.getLatitudeSeconden());
    assertEquals(gebiedDto.getLongitude(), instance.getLongitude());
    assertEquals(gebiedDto.getLongitudeGraden(), instance.getLongitudeGraden());
    assertEquals(gebiedDto.getLongitudeMinuten(),
                 instance.getLongitudeMinuten());
    assertEquals(gebiedDto.getLongitudeSeconden(),
                 instance.getLongitudeSeconden());
    assertEquals(gebied.getNaam(), instance.getNaam());
  }

  @Test
  public void testNaamComparator() {
    var groter  = new Gebied();
    var kleiner = new Gebied();

    groter.setNaam(NAAM_GR);
    kleiner.setNaam(NAAM_KL);

    Set<Gebied> gebieden  = new TreeSet<>(new Gebied.NaamComparator());
    gebieden.add(groter);
    gebieden.add(gebied);
    gebieden.add(kleiner);

    var tabel = new Gebied[gebieden.size()];
    System.arraycopy(gebieden.toArray(), 0, tabel, 0, gebieden.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(gebied.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testPersist() {
    var parameter = new GebiedDto();
    var instance  = new Gebied(gebied);

    instance.persist(parameter);

    assertEquals(instance.getGebiedId(), parameter.getGebiedId());
    assertEquals(instance.getLandId(), parameter.getLandId());
    assertEquals(instance.getLatitude(), parameter.getLatitude());
    assertEquals(instance.getLatitudeGraden(), parameter.getLatitudeGraden());
    assertEquals(instance.getLatitudeMinuten(), parameter.getLatitudeMinuten());
    assertEquals(instance.getLatitudeSeconden(),
                 parameter.getLatitudeSeconden());
    assertEquals(instance.getLongitude(), parameter.getLongitude());
    assertEquals(instance.getLongitudeGraden(), parameter.getLongitudeGraden());
    assertEquals(instance.getLongitudeMinuten(),
                 parameter.getLongitudeMinuten());
    assertEquals(instance.getLongitudeSeconden(),
                 parameter.getLongitudeSeconden());
    assertEquals(instance.getNaam(), parameter.getNaam());

    instance.persist(parameter);

    assertEquals(instance.getGebiedId(), parameter.getGebiedId());
    assertEquals(instance.getLandId(), parameter.getLandId());
    assertEquals(instance.getLatitude(), parameter.getLatitude());
    assertEquals(instance.getLatitudeGraden(), parameter.getLatitudeGraden());
    assertEquals(instance.getLatitudeMinuten(), parameter.getLatitudeMinuten());
    assertEquals(instance.getLatitudeSeconden(),
                 parameter.getLatitudeSeconden());
    assertEquals(instance.getLongitude(), parameter.getLongitude());
    assertEquals(instance.getLongitudeGraden(), parameter.getLongitudeGraden());
    assertEquals(instance.getLongitudeMinuten(),
                 parameter.getLongitudeMinuten());
    assertEquals(instance.getLongitudeSeconden(),
                 parameter.getLongitudeSeconden());
    assertEquals(instance.getNaam(), parameter.getNaam());
  }

  @Test
  public void testSetGebiedId() {
    var instance  = new Gebied();
    assertNotEquals(GEBIEDID, instance.getGebiedId());
    instance.setGebiedId(GEBIEDID);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertEquals(GEBIEDID, instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLandId() {
    var instance  = new Gebied();
    assertNotEquals(LANDID, instance.getLandId());
    instance.setLandId(LANDID);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(LANDID, instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLatitude() {
    var instance  = new Gebied();
    assertNotEquals(LATITUDE, instance.getLatitude());
    instance.setLatitude(LATITUDE);

    assertTrue(DoosUtils.isNotBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertEquals(LATITUDE, instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLatitudeGraden() {
    var instance  = new Gebied();
    assertNotEquals(LATITUDE_GRADEN, instance.getLatitudeGraden());
    instance.setLatitudeGraden(LATITUDE_GRADEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertEquals(LATITUDE_GRADEN, instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLatitudeMinuten() {
    var instance  = new Gebied();
    assertNotEquals(LATITUDE_MINUTEN, instance.getLatitudeMinuten());
    instance.setLatitudeMinuten(LATITUDE_MINUTEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertEquals(LATITUDE_MINUTEN, instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLatitudeSeconden() {
    var instance  = new Gebied();
    assertNotEquals(LATITUDE_SECONDEN, instance.getLatitudeSeconden());
    instance.setLatitudeSeconden(LATITUDE_SECONDEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertEquals(LATITUDE_SECONDEN, instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitude() {
    var instance  = new Gebied();
    assertNotEquals(LONGITUDE, instance.getLongitude());
    instance.setLongitude(LONGITUDE);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertEquals(LONGITUDE, instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeGraden() {
    var instance  = new Gebied();
    assertNotEquals(LONGITUDE_GRADEN, instance.getLongitudeGraden());
    instance.setLongitudeGraden(LONGITUDE_GRADEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertEquals(LONGITUDE_GRADEN, instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeMinuten() {
    var instance  = new Gebied();
    assertNotEquals(LONGITUDE_MINUTEN, instance.getLongitudeMinuten());
    instance.setLongitudeMinuten(LONGITUDE_MINUTEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertEquals(LONGITUDE_MINUTEN, instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeSeconden() {
    var instance  = new Gebied();
    assertNotEquals(LONGITUDE_SECONDEN, instance.getLongitudeSeconden());
    instance.setLongitudeSeconden(LONGITUDE_SECONDEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertEquals(LONGITUDE_SECONDEN, instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetNaam() {
    var instance  = new Gebied();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertEquals(NAAM, instance.getNaam());
  }
}
