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
    groter.setGebiedId(gebiedDto.getGebiedId() + 1);
    kleiner.setGebiedId(gebiedDto.getGebiedId() - 1);

    assertTrue(gebiedDto.compareTo(groter) < 0);
    assertEquals(0, gebiedDto.compareTo(gelijk));
    assertTrue(gebiedDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new GebiedDto();

    assertEquals(gebiedDto, gebiedDto);
    assertNotEquals(gebiedDto, null);
    assertNotEquals(gebiedDto, NAAM);
    assertNotEquals(gebiedDto, instance);

    instance.setGebiedId(gebiedDto.getGebiedId());
    assertEquals(gebiedDto, instance);

    instance.setGebiedId(gebiedDto.getGebiedId() + 1);
    assertNotEquals(gebiedDto, instance);
  }

  @Test
  public void testGetGebiedId() {
    assertEquals(GEBIEDID, gebiedDto.getGebiedId());
  }

  @Test
  public void testGetLandId() {
    assertEquals(LANDID, gebiedDto.getLandId());
  }

  @Test
  public void testGetLatitude() {
    assertEquals(LATITUDE, gebiedDto.getLatitude());
  }

  @Test
  public void testGetLatitudeGraden() {
    assertEquals(LATITUDE_GRADEN, gebiedDto.getLatitudeGraden());
  }

  @Test
  public void testGetLatitudeMinuten() {
    assertEquals(LATITUDE_MINUTEN, gebiedDto.getLatitudeMinuten());
  }

  @Test
  public void testGetLatitudeSeconden() {
    assertEquals(LATITUDE_SECONDEN, gebiedDto.getLatitudeSeconden());
  }

  @Test
  public void testGetLongitude() {
    assertEquals(LONGITUDE, gebiedDto.getLongitude());
  }

  @Test
  public void testGetLongitudeGraden() {
    assertEquals(LONGITUDE_GRADEN, gebiedDto.getLongitudeGraden());
  }

  @Test
  public void testGetLongitudeMinuten() {
    assertEquals(LONGITUDE_MINUTEN, gebiedDto.getLongitudeMinuten());
  }

  @Test
  public void testGetLongitudeSeconden() {
    assertEquals(LONGITUDE_SECONDEN, gebiedDto.getLongitudeSeconden());
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, gebiedDto.getNaam());
  }

  @Test
  public void testHashCode() {
    assertEquals(GEBIEDID_HASH, gebiedDto.hashCode());
  }

  @Test
  public void testNaamComparator() {
    var groter  = new GebiedDto();
    var kleiner = new GebiedDto();

    groter.setNaam(NAAM_GR);
    kleiner.setNaam(NAAM_KL);

    Set<GebiedDto> gebieden  = new TreeSet<>(new GebiedDto.NaamComparator());
    gebieden.add(groter);
    gebieden.add(gebiedDto);
    gebieden.add(kleiner);

    var tabel = new GebiedDto[gebieden.size()];
    System.arraycopy(gebieden.toArray(), 0, tabel, 0, gebieden.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(gebiedDto.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testSetGebiedId() {
    var instance  = new GebiedDto();
    assertNotEquals(GEBIEDID, instance.getGebiedId());
    instance.setGebiedId(GEBIEDID);

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
    var instance  = new GebiedDto();
    assertNotEquals(LANDID, instance.getLandId());
    instance.setLandId(LANDID);

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
    var instance  = new GebiedDto();
    assertNotEquals(LATITUDE, instance.getLatitude());
    instance.setLatitude(LATITUDE);

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
    var instance  = new GebiedDto();
    assertNotEquals(LATITUDE_GRADEN, instance.getLatitudeGraden());
    instance.setLatitudeGraden(LATITUDE_GRADEN);

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
    var instance  = new GebiedDto();
    assertNotEquals(LATITUDE_MINUTEN, instance.getLatitudeMinuten());
    instance.setLatitudeMinuten(LATITUDE_MINUTEN);

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
    var instance  = new GebiedDto();
    assertNotEquals(LATITUDE_SECONDEN, instance.getLatitudeSeconden());
    instance.setLatitudeSeconden(LATITUDE_SECONDEN);

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
    var instance  = new GebiedDto();
    assertNotEquals(LONGITUDE, instance.getLongitude());
    instance.setLongitude(LONGITUDE);

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
    var instance  = new GebiedDto();
    assertNotEquals(LONGITUDE_GRADEN, instance.getLongitudeGraden());
    instance.setLongitudeGraden(LONGITUDE_GRADEN);

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
    var instance  = new GebiedDto();
    assertNotEquals(LONGITUDE_MINUTEN, instance.getLongitudeMinuten());
    instance.setLongitudeMinuten(LONGITUDE_MINUTEN);

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
    var instance  = new GebiedDto();
    assertNotEquals(LONGITUDE_SECONDEN, instance.getLongitudeSeconden());
    instance.setLongitudeSeconden(LONGITUDE_SECONDEN);

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
    var instance  = new GebiedDto();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

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
