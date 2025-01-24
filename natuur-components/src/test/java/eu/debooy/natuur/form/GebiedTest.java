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
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import eu.debooy.natuur.domain.GebiedDto;
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
public class GebiedTest {
  private static  Gebied    gebied;
  private static  GebiedDto gebiedDto;

  @BeforeClass
  public static void setUpClass() {
    // Voor de juiste decimal point.
    Locale.setDefault(new Locale("nl"));

    gebied    = NatuurTestUtils.getGebied();
    gebiedDto = NatuurTestUtils.getGebiedDto();
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Gebied();
    var groter  = new Gebied();
    var kleiner = new Gebied();

    gelijk.setGebiedId(gebied.getGebiedId());
    gelijk.setNaam(NatuurTestConstants.NAAM);
    groter.setGebiedId(gebied.getGebiedId());
    groter.setNaam(NatuurTestConstants.NAAM_GR);
    kleiner.setGebiedId(gebied.getGebiedId());
    kleiner.setNaam(NatuurTestConstants.NAAM_KL);

    assertTrue(gebied.compareTo(groter) < 0);
    assertEquals(0, gebied.compareTo(gelijk));
    assertTrue(gebied.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Gebied();

    assertEquals(gebied, gebied);
    assertNotEquals(gebied, null);
    assertNotEquals(gebied, NatuurTestConstants.NAAM);
    assertNotEquals(gebied, instance);

    instance.setGebiedId(gebied.getGebiedId());
    assertEquals(gebied, instance);

    instance  = new Gebied();
    instance.setGebiedId(gebied.getGebiedId());
    assertEquals(gebied, instance);

    instance  = new Gebied(gebiedDto);
    assertEquals(gebied, instance);
  }

  @Test
  public void testGetCoordinaten() {
    assertEquals(NatuurTestConstants.COORDINATEN, gebied.getCoordinaten());
  }

  @Test
  public void testGetGebiedId() {
    assertEquals(NatuurTestConstants.GEBIEDID, gebied.getGebiedId());
  }

  @Test
  public void testGetLandId() {
    assertEquals(NatuurTestConstants.LANDID, gebied.getLandId());
  }

  @Test
  public void testGetLatitude() {
    assertEquals(NatuurTestConstants.LATITUDE, gebied.getLatitude());
  }

  @Test
  public void testGetLatitudeGraden() {
    assertEquals(NatuurTestConstants.LATITUDE_GRADEN, gebied.getLatitudeGraden());
  }

  @Test
  public void testGetLatitudeMinuten() {
    assertEquals(NatuurTestConstants.LATITUDE_MINUTEN, gebied.getLatitudeMinuten());
  }

  @Test
  public void testGetLatitudeSeconden() {
    assertEquals(NatuurTestConstants.LATITUDE_SECONDEN, gebied.getLatitudeSeconden());
  }

  @Test
  public void testGetLongitude() {
    assertEquals(NatuurTestConstants.LONGITUDE, gebied.getLongitude());
  }

  @Test
  public void testGetLongitudeGraden() {
    assertEquals(NatuurTestConstants.LONGITUDE_GRADEN, gebied.getLongitudeGraden());
  }

  @Test
  public void testGetLongitudeMinuten() {
    assertEquals(NatuurTestConstants.LONGITUDE_MINUTEN, gebied.getLongitudeMinuten());
  }

  @Test
  public void testGetLongitudeSeconden() {
    assertEquals(NatuurTestConstants.LONGITUDE_SECONDEN,
                 gebied.getLongitudeSeconden());
  }

  @Test
  public void testGetNaam() {
    assertEquals(NatuurTestConstants.NAAM, gebied.getNaam());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.GEBIEDID_HASH, gebied.hashCode());
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
  public void testPersist() {
    var parameter = new GebiedDto();

    gebied.persist(parameter);

    assertEquals(gebied.getGebiedId(), parameter.getGebiedId());
    assertEquals(gebied.getLandId(), parameter.getLandId());
    assertEquals(gebied.getLatitude(), parameter.getLatitude());
    assertEquals(gebied.getLatitudeGraden(), parameter.getLatitudeGraden());
    assertEquals(gebied.getLatitudeMinuten(), parameter.getLatitudeMinuten());
    assertEquals(gebied.getLatitudeSeconden(),
                 parameter.getLatitudeSeconden());
    assertEquals(gebied.getLongitude(), parameter.getLongitude());
    assertEquals(gebied.getLongitudeGraden(), parameter.getLongitudeGraden());
    assertEquals(gebied.getLongitudeMinuten(),
                 parameter.getLongitudeMinuten());
    assertEquals(gebied.getLongitudeSeconden(),
                 parameter.getLongitudeSeconden());
    assertEquals(gebied.getNaam(), parameter.getNaam());
  }

  @Test
  public void testSetGebiedId() {
    var instance  = new Gebied();
    assertNotEquals(NatuurTestConstants.GEBIEDID, instance.getGebiedId());
    instance.setGebiedId(NatuurTestConstants.GEBIEDID);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertEquals(NatuurTestConstants.GEBIEDID, instance.getGebiedId());
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
    assertNotEquals(NatuurTestConstants.LANDID, instance.getLandId());
    instance.setLandId(NatuurTestConstants.LANDID);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(NatuurTestConstants.LANDID, instance.getLandId());
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
    assertNotEquals(NatuurTestConstants.LATITUDE, instance.getLatitude());
    instance.setLatitude(NatuurTestConstants.LATITUDE);

    assertTrue(DoosUtils.isNotBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertEquals(NatuurTestConstants.LATITUDE, instance.getLatitude());
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
    assertNotEquals(NatuurTestConstants.LATITUDE_GRADEN,
                    instance.getLatitudeGraden());
    instance.setLatitudeGraden(NatuurTestConstants.LATITUDE_GRADEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertEquals(NatuurTestConstants.LATITUDE_GRADEN, instance.getLatitudeGraden());
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
    assertNotEquals(NatuurTestConstants.LATITUDE_MINUTEN,
                    instance.getLatitudeMinuten());
    instance.setLatitudeMinuten(NatuurTestConstants.LATITUDE_MINUTEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertEquals(NatuurTestConstants.LATITUDE_MINUTEN, instance.getLatitudeMinuten());
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
    assertNotEquals(NatuurTestConstants.LATITUDE_SECONDEN,
                    instance.getLatitudeSeconden());
    instance.setLatitudeSeconden(NatuurTestConstants.LATITUDE_SECONDEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertEquals(NatuurTestConstants.LATITUDE_SECONDEN, instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitude() {
    var instance  = new Gebied();
    assertNotEquals(NatuurTestConstants.LONGITUDE, instance.getLongitude());
    instance.setLongitude(NatuurTestConstants.LONGITUDE);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertEquals(NatuurTestConstants.LONGITUDE, instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeGraden() {
    var instance  = new Gebied();
    assertNotEquals(NatuurTestConstants.LONGITUDE_GRADEN,
                    instance.getLongitudeGraden());
    instance.setLongitudeGraden(NatuurTestConstants.LONGITUDE_GRADEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertEquals(NatuurTestConstants.LONGITUDE_GRADEN, instance.getLongitudeGraden());
    assertNull(instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeMinuten() {
    var instance  = new Gebied();
    assertNotEquals(NatuurTestConstants.LONGITUDE_MINUTEN,
                    instance.getLongitudeMinuten());
    instance.setLongitudeMinuten(NatuurTestConstants.LONGITUDE_MINUTEN);

    assertTrue(DoosUtils.isBlankOrNull(instance.getCoordinaten()));
    assertNull(instance.getGebiedId());
    assertEquals(Long.valueOf(0), instance.getLandId());
    assertNull(instance.getLatitude());
    assertNull(instance.getLatitudeGraden());
    assertNull(instance.getLatitudeMinuten());
    assertNull(instance.getLatitudeSeconden());
    assertNull(instance.getLongitude());
    assertNull(instance.getLongitudeGraden());
    assertEquals(NatuurTestConstants.LONGITUDE_MINUTEN,
                 instance.getLongitudeMinuten());
    assertNull(instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetLongitudeSeconden() {
    var instance  = new Gebied();
    assertNotEquals(NatuurTestConstants.LONGITUDE_SECONDEN,
                    instance.getLongitudeSeconden());
    instance.setLongitudeSeconden(NatuurTestConstants.LONGITUDE_SECONDEN);

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
    assertEquals(NatuurTestConstants.LONGITUDE_SECONDEN,
                 instance.getLongitudeSeconden());
    assertNull(instance.getNaam());
  }

  @Test
  public void testSetNaam() {
    var instance  = new Gebied();
    assertNotEquals(NatuurTestConstants.NAAM, instance.getNaam());
    instance.setNaam(NatuurTestConstants.NAAM);

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
    assertEquals(NatuurTestConstants.NAAM, instance.getNaam());
  }
}
