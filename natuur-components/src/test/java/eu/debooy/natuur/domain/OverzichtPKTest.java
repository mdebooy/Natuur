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
      "OverzichtPK (parentId=" + TestConstants.PARENTTAXONID
             + ", parentRang=" + TestConstants.PARENTRANG
                   + ", rang=" + TestConstants.RANG + ")";

  private static  OverzichtPK overzichtPK;

  @BeforeClass
  public static void setUpClass() {
    overzichtPK = new OverzichtPK();
    overzichtPK.setParentId(TestConstants.PARENTTAXONID);
    overzichtPK.setParentRang(TestConstants.PARENTRANG);
    overzichtPK.setRang(TestConstants.RANG);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new OverzichtPK();
    var groter  = new OverzichtPK();
    var kleiner = new OverzichtPK();

    gelijk.setParentRang(overzichtPK.getParentRang());
    gelijk.setParentId(overzichtPK.getParentId());
    gelijk.setRang(overzichtPK.getRang());
    groter.setParentRang(TestConstants.PARENTRANG_GR);
    groter.setParentId(overzichtPK.getParentId());
    groter.setRang(overzichtPK.getRang());
    kleiner.setParentRang(TestConstants.PARENTRANG_KL);
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
    groter.setRang(TestConstants.RANG_GR);
    kleiner.setParentId(overzichtPK.getParentId());
    kleiner.setRang(TestConstants.RANG_KL);

    assertTrue(overzichtPK.compareTo(groter) < 0);
    assertEquals(0, overzichtPK.compareTo(gelijk));
    assertTrue(overzichtPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new OverzichtPK();

    assertEquals(overzichtPK, overzichtPK);
    assertNotEquals(overzichtPK, null);
    assertNotEquals(overzichtPK, TestConstants.RANG);
    assertNotEquals(overzichtPK, instance);

    instance  = new OverzichtPK(TestConstants.PARENTTAXONID,
                                TestConstants.PARENTRANG,
                                TestConstants.RANG);
    assertEquals(overzichtPK, instance);

    instance.setParentId(TestConstants.PARENTTAXONID - 1);
    assertNotEquals(overzichtPK, instance);
  }

  @Test
  public void testGetParentId() {
    assertEquals(TestConstants.PARENTTAXONID, overzichtPK.getParentId());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(TestConstants.PARENTRANG, overzichtPK.getParentRang());
  }

  @Test
  public void testGetRang() {
    assertEquals(TestConstants.RANG, overzichtPK.getRang());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.OVERZICHTPK_HASH, overzichtPK.hashCode());
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
    var instance  = new OverzichtPK(TestConstants.PARENTTAXONID,
                                    TestConstants.PARENTRANG,
                                    TestConstants.RANG);

    assertEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(TestConstants.RANG, instance.getRang());
  }

  @Test
  public void testSetParentId() {
    var instance  = new OverzichtPK();
    assertNotEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    instance.setParentId(TestConstants.PARENTTAXONID);

    assertEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetParentRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(TestConstants.PARENTRANG, instance.getParentRang());
    instance.setParentRang(TestConstants.PARENTRANG);

    assertNull(instance.getParentId());
    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(TestConstants.RANG, instance.getRang());
    instance.setRang(TestConstants.RANG);

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.RANG, instance.getRang());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, overzichtPK.toString());
  }
}
