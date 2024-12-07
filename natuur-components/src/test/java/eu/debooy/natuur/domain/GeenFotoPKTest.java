/*
 * Copyright (c) 2024 Marco de Booij
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
public class GeenFotoPKTest {
  private static final String TOSTRING  =
      "GeenFotoPK (parentId=" + TestConstants.PARENTTAXONID
          + ", parentRang=" + TestConstants.PARENTRANG
          + ", taxonId=" + TestConstants.TAXONID + ")";

  private static  GeenFotoPK  geenFotoPK;

  @BeforeClass
  public static void setUpClass() {
    geenFotoPK  = new GeenFotoPK(TestConstants.PARENTTAXONID,
                                 TestConstants.PARENTRANG,
                                 TestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new GeenFotoPK();
    var groter  = new GeenFotoPK();
    var kleiner = new GeenFotoPK();

    gelijk.setParentId(geenFotoPK.getParentId());
    gelijk.setParentRang(geenFotoPK.getParentRang());
    gelijk.setTaxonId(geenFotoPK.getTaxonId());
    groter.setParentId(geenFotoPK.getParentId());
    groter.setParentRang(TestConstants.PARENTRANG_GR);
    groter.setTaxonId(geenFotoPK.getTaxonId());
    kleiner.setParentId(geenFotoPK.getParentId());
    kleiner.setParentRang(TestConstants.PARENTRANG_KL);
    kleiner.setTaxonId(geenFotoPK.getTaxonId());

    assertTrue(geenFotoPK.compareTo(groter) < 0);
    assertEquals(0, geenFotoPK.compareTo(gelijk));
    assertTrue(geenFotoPK.compareTo(kleiner) > 0);

    groter.setParentId(geenFotoPK.getParentId() + 1);
    groter.setParentRang(TestConstants.PARENTRANG);
    groter.setTaxonId(geenFotoPK.getTaxonId());
    kleiner.setParentId(geenFotoPK.getParentId() - 1);
    kleiner.setParentRang(TestConstants.PARENTRANG);
    kleiner.setTaxonId(geenFotoPK.getTaxonId());

    assertTrue(geenFotoPK.compareTo(groter) < 0);
    assertEquals(0, geenFotoPK.compareTo(gelijk));
    assertTrue(geenFotoPK.compareTo(kleiner) > 0);

    groter.setParentId(geenFotoPK.getParentId());
    groter.setParentRang(TestConstants.PARENTRANG);
    groter.setTaxonId(geenFotoPK.getTaxonId() + 1);
    kleiner.setParentId(geenFotoPK.getParentId());
    kleiner.setParentRang(TestConstants.PARENTRANG);
    kleiner.setTaxonId(geenFotoPK.getTaxonId() - 1);

    assertTrue(geenFotoPK.compareTo(groter) < 0);
    assertEquals(0, geenFotoPK.compareTo(gelijk));
    assertTrue(geenFotoPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new GeenFotoPK();

    assertEquals(geenFotoPK, geenFotoPK);
    assertNotEquals(geenFotoPK, null);
    assertNotEquals(geenFotoPK, TestConstants.NAAM);
    assertNotEquals(geenFotoPK, instance);

    instance  = new GeenFotoPK(TestConstants.PARENTTAXONID,
                               TestConstants.PARENTRANG,
                               TestConstants.TAXONID);
    assertEquals(geenFotoPK, instance);

    instance.setParentId(TestConstants.PARENTTAXONID - 1);
    assertNotEquals(geenFotoPK, instance);
  }

  @Test
  public void getParentId() {
    assertEquals(TestConstants.PARENTTAXONID, geenFotoPK.getParentId());
  }

  @Test
  public void getParentRang() {
    assertEquals(TestConstants.PARENTRANG, geenFotoPK.getParentRang());
  }

  @Test
  public void getTaxonId() {
    assertEquals(TestConstants.TAXONID, geenFotoPK.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.GEENFOTOPK_HASH, geenFotoPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new GeenFotoPK();

    assertNull(instance.getParentId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit2() {
    var instance  = new GeenFotoPK(TestConstants.PARENTTAXONID,
                                   TestConstants.PARENTRANG,
                                   TestConstants.TAXONID);

    assertEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetParentId() {
    var instance  = new GeenFotoPK();
    assertNull(instance.getParentId());
    instance.setParentId(TestConstants.PARENTTAXONID);

    assertEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetParentRang() {
    var instance  = new GeenFotoPK();
    assertNull(instance.getParentRang());
    instance.setParentRang(TestConstants.PARENTRANG);

    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new GeenFotoPK();
    assertNull(instance.getTaxonId());
    instance.setTaxonId(TestConstants.TAXONID);

    assertNull(instance.getParentId());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, geenFotoPK.toString());
  }
}
