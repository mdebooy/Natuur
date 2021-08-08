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

import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANGNAAM_HASH;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangnaamPKTest {
  private static final String TOSTRING  = "RangnaamPK (rang=so, taal=nl)";

  private static  RangnaamPK rangnaamPK;

  @BeforeClass
  public static void setUpClass() {
    rangnaamPK  = new RangnaamPK(RANG, TAAL);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RangnaamPK();
    var groter  = new RangnaamPK();
    var kleiner = new RangnaamPK();

    gelijk.setTaal(rangnaamPK.getTaal());
    gelijk.setRang(rangnaamPK.getRang());
    groter.setTaal(rangnaamPK.getTaal());
    groter.setRang(RANG_GR);
    kleiner.setTaal(rangnaamPK.getTaal());
    kleiner.setRang(RANG_KL);

    assertTrue(rangnaamPK.compareTo(groter) < 0);
    assertEquals(0, rangnaamPK.compareTo(gelijk));
    assertTrue(rangnaamPK.compareTo(kleiner) > 0);

    groter.setTaal(TAAL_GR);
    groter.setRang(rangnaamPK.getRang());
    kleiner.setTaal(TAAL_KL);
    kleiner.setRang(rangnaamPK.getRang());

    assertTrue(rangnaamPK.compareTo(groter) < 0);
    assertEquals(0, rangnaamPK.compareTo(gelijk));
    assertTrue(rangnaamPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RangnaamPK();

    assertEquals(rangnaamPK, rangnaamPK);
    assertNotEquals(rangnaamPK, null);
    assertNotEquals(rangnaamPK, NAAM);
    assertNotEquals(rangnaamPK, instance);

    instance  = new RangnaamPK(RANG, TAAL);
    assertEquals(rangnaamPK, instance);
  }

  @Test
  public void getRang() {
    assertEquals(RANG, rangnaamPK.getRang());
  }

  @Test
  public void getTaal() {
    assertEquals(TAAL, rangnaamPK.getTaal());
  }

  @Test
  public void testHashCode() {
    assertEquals(RANGNAAM_HASH, rangnaamPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new RangnaamPK();

    assertNull(instance.getRang());
    assertNull(instance.getTaal());
  }

  @Test
  public void testInit2() {
    var instance  = new RangnaamPK(RANG, TAAL);

    assertEquals(TAAL, instance.getTaal());
    assertEquals(RANG, instance.getRang());
  }

  @Test
  public void testSetRang() {
    var instance  = new RangnaamPK();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getTaal());
    assertEquals(RANG, instance.getRang());
  }

  @Test
  public void testSetTaal() {
    var instance  = new RangnaamPK();
    assertNotEquals(TAAL, instance.getTaal());
    instance.setTaal(TAAL);

    assertEquals(TAAL, instance.getTaal());
    assertNull(instance.getRang());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, rangnaamPK.toString());
  }
}
