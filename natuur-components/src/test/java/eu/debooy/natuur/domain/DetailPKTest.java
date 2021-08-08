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

import static eu.debooy.natuur.TestConstants.DETAILPK_HASH;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class DetailPKTest {
  private static final String TOSTRING  =
      "DetailPK (parentId=2, taxonId=9223372036854775797)";

  private static  DetailPK  detailPK;

  @BeforeClass
  public static void setUpClass() {
    detailPK  = new DetailPK(PARENTID, TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new DetailPK();
    var groter  = new DetailPK();
    var kleiner = new DetailPK();

    gelijk.setParentId(detailPK.getParentId());
    gelijk.setTaxonId(detailPK.getTaxonId());
    groter.setParentId(detailPK.getParentId() + 1);
    groter.setTaxonId(detailPK.getTaxonId());
    kleiner.setParentId(detailPK.getParentId() - 1);
    kleiner.setTaxonId(detailPK.getTaxonId());

    assertTrue(detailPK.compareTo(groter) < 0);
    assertEquals(0, detailPK.compareTo(gelijk));
    assertTrue(detailPK.compareTo(kleiner) > 0);

    groter.setParentId(detailPK.getParentId());
    groter.setTaxonId(detailPK.getTaxonId() + 1);
    kleiner.setParentId(detailPK.getParentId());
    kleiner.setTaxonId(detailPK.getTaxonId() - 1);

    assertTrue(detailPK.compareTo(groter) < 0);
    assertEquals(0, detailPK.compareTo(gelijk));
    assertTrue(detailPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new DetailPK();

    assertEquals(detailPK, detailPK);
    assertNotEquals(detailPK, null);
    assertNotEquals(detailPK, NAAM);
    assertNotEquals(detailPK, instance);
  }

  @Test
  public void getParentId() {
    assertEquals(PARENTID, detailPK.getParentId());
  }

  @Test
  public void getTaxonId() {
    assertEquals(TAXONID, detailPK.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(DETAILPK_HASH, detailPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new DetailPK();

    assertNull(instance.getParentId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit2() {
    var instance  = new DetailPK(PARENTID, TAXONID);

    assertEquals(PARENTID, instance.getParentId());
    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetParentId() {
    var instance  = new DetailPK();
    assertNotEquals(PARENTID, instance.getParentId());
    instance.setParentId(PARENTID);

    assertEquals(PARENTID, instance.getParentId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new DetailPK();
    assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertNull(instance.getParentId());
    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, detailPK.toString());
  }
}
