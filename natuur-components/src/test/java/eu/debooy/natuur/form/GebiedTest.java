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
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.GebiedDto;
import java.util.Locale;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
    Gebied  gelijk  = new Gebied(gebied);
    Gebied  groter  = new Gebied();
    groter.setGebiedId(gebied.getGebiedId() + 1);
    Gebied  kleiner = new Gebied();
    kleiner.setGebiedId(gebied.getGebiedId() - 1);

    assertTrue(gebied.compareTo(groter) < 0);
    assertEquals(0, gebied.compareTo(gelijk));
    assertTrue(gebied.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Gebied  object    = null;
    Gebied  instance  = new Gebied();

    assertNotEquals(gebied, object);
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
  public void testPersist() {
    GebiedDto parameter = new GebiedDto();
    Gebied    instance  = new Gebied();
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
    Gebied  instance  = new Gebied();
    assertNotEquals(GEBIEDID, instance.getGebiedId());
    instance.setGebiedId(GEBIEDID);

    assertEquals(GEBIEDID, instance.getGebiedId());
  }

  @Test
  public void testSetLandId() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LANDID, instance.getLandId());
    instance.setLandId(LANDID);

    assertEquals(LANDID, instance.getLandId());
  }

  @Test
  public void testSetLatitude() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LATITUDE, instance.getLatitude());
    instance.setLatitude(LATITUDE);

    assertEquals(LATITUDE, instance.getLatitude());
  }

  @Test
  public void testSetLatitudeGraden() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LATITUDE_GRADEN, instance.getLatitudeGraden());
    instance.setLatitudeGraden(LATITUDE_GRADEN);

    assertEquals(LATITUDE_GRADEN, instance.getLatitudeGraden());
  }

  @Test
  public void testSetLatitudeMinuten() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LATITUDE_MINUTEN, instance.getLatitudeMinuten());
    instance.setLatitudeMinuten(LATITUDE_MINUTEN);

    assertEquals(LATITUDE_MINUTEN, instance.getLatitudeMinuten());
  }

  @Test
  public void testSetLatitudeSeconden() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LATITUDE_SECONDEN, instance.getLatitudeSeconden());
    instance.setLatitudeSeconden(LATITUDE_SECONDEN);

    assertEquals(LATITUDE_SECONDEN, instance.getLatitudeSeconden());
  }

  @Test
  public void testSetLongitude() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LONGITUDE, instance.getLongitude());
    instance.setLongitude(LONGITUDE);

    assertEquals(LONGITUDE, instance.getLongitude());
  }

  @Test
  public void testSetLongitudeGraden() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LONGITUDE_GRADEN, instance.getLongitudeGraden());
    instance.setLongitudeGraden(LONGITUDE_GRADEN);

    assertEquals(LONGITUDE_GRADEN, instance.getLongitudeGraden());
  }

  @Test
  public void testSetLongitudeMinuten() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LONGITUDE_MINUTEN, instance.getLongitudeMinuten());
    instance.setLongitudeMinuten(LONGITUDE_MINUTEN);

    assertEquals(LONGITUDE_MINUTEN, instance.getLongitudeMinuten());
  }

  @Test
  public void testSetLongitudeSeconden() {
    Gebied  instance  = new Gebied();
    assertNotEquals(LONGITUDE_SECONDEN, instance.getLongitudeSeconden());
    instance.setLongitudeSeconden(LONGITUDE_SECONDEN);

    assertEquals(LONGITUDE_SECONDEN, instance.getLongitudeSeconden());
  }

  @Test
  public void testSetNaam() {
    Gebied  instance  = new Gebied();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
  }
}
