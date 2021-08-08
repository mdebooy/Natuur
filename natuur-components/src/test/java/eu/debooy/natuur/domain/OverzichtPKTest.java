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
import static eu.debooy.natuur.TestConstants.OVERZICHTPK_HASH;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.PARENTRANG;
import static eu.debooy.natuur.TestConstants.PARENTRANG_GR;
import static eu.debooy.natuur.TestConstants.PARENTRANG_KL;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class OverzichtPKTest {
  private static final String TOSTRING  =
      "OverzichtPK (parentId=2, parentRang=ge, rang=so)";

  private static  OverzichtPK overzichtPK;

  @BeforeClass
  public static void setUpClass() {
    overzichtPK = new OverzichtPK();
    overzichtPK.setParentId(PARENTID);
    overzichtPK.setParentRang(PARENTRANG);
    overzichtPK.setRang(RANG);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new OverzichtPK();
    var groter  = new OverzichtPK();
    var kleiner = new OverzichtPK();

    gelijk.setParentRang(overzichtPK.getParentRang());
    gelijk.setParentId(overzichtPK.getParentId());
    gelijk.setRang(overzichtPK.getRang());
    groter.setParentRang(PARENTRANG_GR);
    groter.setParentId(overzichtPK.getParentId());
    groter.setRang(overzichtPK.getRang());
    kleiner.setParentRang(PARENTRANG_KL);
    kleiner.setParentId(overzichtPK.getParentId());
    kleiner.setRang(overzichtPK.getRang());

    assertTrue(overzichtPK.compareTo(groter) < 0);
    assertEquals(0, overzichtPK.compareTo(gelijk));
    assertTrue(overzichtPK.compareTo(kleiner) > 0);

    groter.setParentRang(overzichtPK.getParentRang());
    groter.setParentId(overzichtPK.getParentId() + 1);
    groter.setRang(overzichtPK.getRang());
    kleiner.setParentRang(overzichtPK.getParentRang());
    kleiner.setParentId(overzichtPK.getParentId() - 1);
    kleiner.setRang(overzichtPK.getRang());

    assertTrue(overzichtPK.compareTo(groter) < 0);
    assertEquals(0, overzichtPK.compareTo(gelijk));
    assertTrue(overzichtPK.compareTo(kleiner) > 0);

    groter.setParentId(overzichtPK.getParentId());
    groter.setRang(RANG_GR);
    kleiner.setParentId(overzichtPK.getParentId());
    kleiner.setRang(RANG_KL);

    assertTrue(overzichtPK.compareTo(groter) < 0);
    assertEquals(0, overzichtPK.compareTo(gelijk));
    assertTrue(overzichtPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new OverzichtPK();

    assertEquals(overzichtPK, overzichtPK);
    assertNotEquals(overzichtPK, null);
    assertNotEquals(overzichtPK, NAAM);
    assertNotEquals(overzichtPK, instance);
  }

  @Test
  public void testGetParentId() {
    assertEquals(PARENTID, overzichtPK.getParentId());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(PARENTRANG, overzichtPK.getParentRang());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, overzichtPK.getRang());
  }

  @Test
  public void testHashCode() {
    assertEquals(OVERZICHTPK_HASH, overzichtPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new OverzichtPK();

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testInit2() {
    var instance  = new OverzichtPK(PARENTID, PARENTRANG, RANG);

    assertEquals(PARENTID, instance.getParentId());
    assertEquals(PARENTRANG, instance.getParentRang());
    assertEquals(RANG, instance.getRang());
  }

  @Test
  public void testSetParentId() {
    var instance  = new OverzichtPK();
    assertNotEquals(PARENTID, instance.getParentId());
    instance.setParentId(PARENTID);

    assertEquals(PARENTID, instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetParentRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(PARENTRANG, instance.getParentRang());
    instance.setParentRang(PARENTRANG);

    assertNull(instance.getParentId());
    assertEquals(PARENTRANG, instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertEquals(RANG, instance.getRang());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, overzichtPK.toString());
  }
}
