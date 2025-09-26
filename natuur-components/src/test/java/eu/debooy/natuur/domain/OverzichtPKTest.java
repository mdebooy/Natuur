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

import eu.debooy.natuur.NatuurTestConstants;
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
      "OverzichtPK (parentId=" + NatuurTestConstants.PARENTTAXONID
             + ", parentRang=" + NatuurTestConstants.PARENTRANG
                   + ", rang=" + NatuurTestConstants.RANG
                 + ", status=" + NatuurTestConstants.STATUS + ")";

  private static  OverzichtPK overzichtPK;

  @BeforeClass
  public static void setUpClass() {
    overzichtPK = new OverzichtPK();
    overzichtPK.setParentId(NatuurTestConstants.PARENTTAXONID);
    overzichtPK.setParentRang(NatuurTestConstants.PARENTRANG);
    overzichtPK.setRang(NatuurTestConstants.RANG);
    overzichtPK.setStatus(NatuurTestConstants.STATUS);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new OverzichtPK();
    var groter  = new OverzichtPK();
    var kleiner = new OverzichtPK();

    gelijk.setParentRang(overzichtPK.getParentRang());
    gelijk.setParentId(overzichtPK.getParentId());
    gelijk.setRang(overzichtPK.getRang());
    gelijk.setStatus(overzichtPK.getStatus());
    groter.setParentRang(NatuurTestConstants.PARENTRANG_GR);
    groter.setParentId(overzichtPK.getParentId());
    groter.setRang(overzichtPK.getRang());
    groter.setStatus(overzichtPK.getStatus());
    kleiner.setParentRang(NatuurTestConstants.PARENTRANG_KL);
    kleiner.setParentId(overzichtPK.getParentId());
    kleiner.setRang(overzichtPK.getRang());
    kleiner.setStatus(overzichtPK.getStatus());

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
    groter.setRang(NatuurTestConstants.RANG_GR);
    kleiner.setParentId(overzichtPK.getParentId());
    kleiner.setRang(NatuurTestConstants.RANG_KL);

    assertTrue(overzichtPK.compareTo(groter) < 0);
    assertEquals(0, overzichtPK.compareTo(gelijk));
    assertTrue(overzichtPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new OverzichtPK();

    assertEquals(overzichtPK, overzichtPK);
    assertNotEquals(overzichtPK, null);
    assertNotEquals(overzichtPK, NatuurTestConstants.RANG);
    assertNotEquals(overzichtPK, instance);

    instance  = new OverzichtPK(NatuurTestConstants.PARENTTAXONID,
                                NatuurTestConstants.PARENTRANG,
                                NatuurTestConstants.RANG,
                                NatuurTestConstants.STATUS);
    assertEquals(overzichtPK, instance);

    instance.setParentId(NatuurTestConstants.PARENTTAXONID - 1);
    assertNotEquals(overzichtPK, instance);
  }

  @Test
  public void testGetParentId() {
    assertEquals(NatuurTestConstants.PARENTTAXONID, overzichtPK.getParentId());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(NatuurTestConstants.PARENTRANG, overzichtPK.getParentRang());
  }

  @Test
  public void testGetRang() {
    assertEquals(NatuurTestConstants.RANG, overzichtPK.getRang());
  }

  @Test
  public void testGetStatus() {
    assertEquals(NatuurTestConstants.STATUS, overzichtPK.getStatus());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.OVERZICHTPK_HASH, overzichtPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new OverzichtPK();

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
    assertNull(instance.getStatus());
  }

  @Test
  public void testInit2() {
    var instance  = new OverzichtPK(NatuurTestConstants.PARENTTAXONID,
                                    NatuurTestConstants.PARENTRANG,
                                    NatuurTestConstants.RANG,
                                    NatuurTestConstants.STATUS);

    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());
  }

  @Test
  public void testSetParentId() {
    var instance  = new OverzichtPK();
    assertNotEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    instance.setParentId(NatuurTestConstants.PARENTTAXONID);

    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
    assertNull(instance.getStatus());
  }

  @Test
  public void testSetParentRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    instance.setParentRang(NatuurTestConstants.PARENTRANG);

    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getRang());
    assertNull(instance.getStatus());
  }

  @Test
  public void testSetRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(NatuurTestConstants.RANG, instance.getRang());
    instance.setRang(NatuurTestConstants.RANG);

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getStatus());
  }

  @Test
  public void testSetStatus() {
    var instance  = new OverzichtPK();
    assertNotEquals(NatuurTestConstants.STATUS, instance.getStatus());
    instance.setStatus(NatuurTestConstants.STATUS);

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, overzichtPK.toString());
  }
}
