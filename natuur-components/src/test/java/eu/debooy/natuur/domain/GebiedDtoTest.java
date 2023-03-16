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
package eu.debooy.natuur.domain;

import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.TestUtils;
import java.util.Locale;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class GebiedDtoTest {
  private static  GebiedDto gebiedDto;

  @BeforeClass
  public static void setUpClass() {
    // Voor de juiste decimal point.
    Locale.setDefault(new Locale("nl"));

    gebiedDto = TestUtils.getGebiedDto();
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new GebiedDto();
    var groter  = new GebiedDto();
    var kleiner = new GebiedDto();

    gelijk.setGebiedId(gebiedDto.getGebiedId());
    gelijk.setNaam(gebiedDto.getNaam());
    groter.setGebiedId(gebiedDto.getGebiedId());
    groter.setNaam(TestConstants.NAAM_GR);
    kleiner.setGebiedId(gebiedDto.getGebiedId());
    kleiner.setNaam(TestConstants.NAAM_KL);

    assertTrue(gebiedDto.compareTo(groter) < 0);
    assertEquals(0, gebiedDto.compareTo(gelijk));
    assertTrue(gebiedDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new GebiedDto();

    assertEquals(gebiedDto, gebiedDto);
    assertNotEquals(gebiedDto, null);
    assertNotEquals(gebiedDto, TestConstants.NAAM);
    assertNotEquals(gebiedDto, instance);

    instance.setGebiedId(gebiedDto.getGebiedId());
    assertEquals(gebiedDto, instance);

    instance.setGebiedId(gebiedDto.getGebiedId() + 1);
    assertNotEquals(gebiedDto, instance);
  }

  @Test
  public void testGetGebiedId() {
    assertEquals(TestConstants.GEBIEDID, gebiedDto.getGebiedId());
  }

  @Test
  public void testGetLandId() {
    assertEquals(TestConstants.LANDID, gebiedDto.getLandId());
  }

  @Test
  public void testGetLatitude() {
    assertEquals(TestConstants.LATITUDE, gebiedDto.getLatitude());
  }

  @Test
  public void testGetLatitudeGraden() {
    assertEquals(TestConstants.LATITUDE_GRADEN, gebiedDto.getLatitudeGraden());
  }

  @Test
  public void testGetLatitudeMinuten() {
    assertEquals(TestConstants.LATITUDE_MINUTEN,
                 gebiedDto.getLatitudeMinuten());
  }

  @Test
  public void testGetLatitudeSeconden() {
    assertEquals(TestConstants.LATITUDE_SECONDEN,
                 gebiedDto.getLatitudeSeconden());
  }

  @Test
  public void testGetLongitude() {
    assertEquals(TestConstants.LONGITUDE, gebiedDto.getLongitude());
  }

  @Test
  public void testGetLongitudeGraden() {
    assertEquals(TestConstants.LONGITUDE_GRADEN,
                 gebiedDto.getLongitudeGraden());
  }

  @Test
  public void testGetLongitudeMinuten() {
    assertEquals(TestConstants.LONGITUDE_MINUTEN,
                 gebiedDto.getLongitudeMinuten());
  }

  @Test
  public void testGetLongitudeSeconden() {
    assertEquals(TestConstants.LONGITUDE_SECONDEN,
                 gebiedDto.getLongitudeSeconden());
  }

  @Test
  public void testGetNaam() {
    assertEquals(TestConstants.NAAM, gebiedDto.getNaam());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.GEBIEDID_HASH, gebiedDto.hashCode());
  }

  @Test
  public void testSetGebiedId() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.GEBIEDID, instance.getGebiedId());
    instance.setGebiedId(TestConstants.GEBIEDID);

    assertEquals(TestConstants.GEBIEDID, instance.getGebiedId());
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
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LANDID, instance.getLandId());
    instance.setLandId(TestConstants.LANDID);

    assertNull(instance.getGebiedId());
    assertEquals(TestConstants.LANDID, instance.getLandId());
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
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LATITUDE, instance.getLatitude());
    instance.setLatitude(TestConstants.LATITUDE);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertEquals(TestConstants.LATITUDE, instance.getLatitude());
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
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LATITUDE_GRADEN,
                    instance.getLatitudeGraden());
    instance.setLatitudeGraden(TestConstants.LATITUDE_GRADEN);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertEquals(TestConstants.LATITUDE_GRADEN, instance.getLatitudeGraden());
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
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LATITUDE_MINUTEN,
                    instance.getLatitudeMinuten());
    instance.setLatitudeMinuten(TestConstants.LATITUDE_MINUTEN);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertEquals(TestConstants.LATITUDE_MINUTEN, instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLatitudeSeconden() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LATITUDE_SECONDEN,
                    instance.getLatitudeSeconden());
    instance.setLatitudeSeconden(TestConstants.LATITUDE_SECONDEN);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertEquals(TestConstants.LATITUDE_SECONDEN,
                 instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitude() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LONGITUDE, instance.getLongitude());
    instance.setLongitude(TestConstants.LONGITUDE);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertEquals(TestConstants.LONGITUDE, instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeGraden() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LONGITUDE_GRADEN,
                    instance.getLongitudeGraden());
    instance.setLongitudeGraden(TestConstants.LONGITUDE_GRADEN);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertEquals(TestConstants.LONGITUDE_GRADEN, instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeMinuten() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LONGITUDE_MINUTEN,
                    instance.getLongitudeMinuten());
    instance.setLongitudeMinuten(TestConstants.LONGITUDE_MINUTEN);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertEquals(TestConstants.LONGITUDE_MINUTEN,
                 instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeSeconden() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.LONGITUDE_SECONDEN,
                    instance.getLongitudeSeconden());
    instance.setLongitudeSeconden(TestConstants.LONGITUDE_SECONDEN);

    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertEquals(TestConstants.LONGITUDE_SECONDEN,
                 instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetNaam() {
    var instance  = new GebiedDto();
    assertNotEquals(TestConstants.NAAM, instance.getNaam());
    instance.setNaam(TestConstants.NAAM);

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
    assertEquals(TestConstants.NAAM, instance.getNaam());
  }
}
