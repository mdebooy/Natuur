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

import eu.debooy.natuur.TestConstants;
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
  private static  Date          datum;
  private static  RegiolijstDto regiolijstDto;

  @BeforeClass
  public static void beforeClass() {
    datum           = new Date();
    regiolijstDto   = new RegiolijstDto();

    regiolijstDto.setDatum(datum);
    regiolijstDto.setOmschrijving(TestConstants.OMSCHRIJVING);
    regiolijstDto.setRegioId(TestConstants.REGIOID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstDto();
    var groter  = new RegiolijstDto();
    var kleiner = new RegiolijstDto();

    gelijk.setRegioId(regiolijstDto.getRegioId());
    groter.setRegioId(regiolijstDto.getRegioId() + 1);
    kleiner.setRegioId(regiolijstDto.getRegioId() -1);

    assertTrue(regiolijstDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstDto.compareTo(gelijk));
    assertTrue(regiolijstDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegiolijstDto();

    assertEquals(regiolijstDto, regiolijstDto);
    assertNotEquals(regiolijstDto, null);
    assertNotEquals(regiolijstDto, TestConstants.RANGNAAM);
    assertNotEquals(regiolijstDto, instance);

    instance.setRegioId(regiolijstDto.getRegioId());
    assertEquals(regiolijstDto, instance);

    instance.setRegioId(regiolijstDto.getRegioId() + 1);
    assertNotEquals(regiolijstDto, instance);
  }

  @Test
  public void testGetDatum() {
    assertEquals(datum, regiolijstDto.getDatum());
  }

  @Test
  public void testGetOmschrijving() {
    assertEquals(TestConstants.OMSCHRIJVING, regiolijstDto.getOmschrijving());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(TestConstants.REGIOID, regiolijstDto.getRegioId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.REGIOLIJST_HASH, regiolijstDto.hashCode());
  }

  @Test
  public void testSetDatum() {
    var instance  = new RegiolijstDto();

    assertNotEquals(datum, instance.getDatum());

    instance.setDatum(datum);

    assertEquals(datum, instance.getDatum());

    // Geen reference maar value?
    Date  datum2  = instance.getDatum();

    assertEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());

    datum2.setTime(0);

    assertNotEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());

    instance.setDatum(null);

    assertNull(instance.getDatum());
  }

  @Test
  public void testSetOMschrijving() {
    var instance  = new RegiolijstDto();
    assertNotEquals(TestConstants.OMSCHRIJVING, instance.getOmschrijving());

    instance.setOmschrijving(TestConstants.OMSCHRIJVING);

    assertEquals(TestConstants.OMSCHRIJVING, instance.getOmschrijving());

    instance.setOmschrijving(null);

    assertNull(instance.getOmschrijving());
  }

  @Test
  public void testSetRegioId() {
    var instance    = new RegiolijstDto();

    assertNotEquals(TestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(TestConstants.REGIOID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
  }
}
