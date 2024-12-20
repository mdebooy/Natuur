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

package eu.debooy.natuur.form;

import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class GeenFotoTest {
  private static  GeenFoto  geenFoto;

  @BeforeClass
  public static void setUpClass() {
    geenFoto  = new GeenFoto();

    geenFoto.setParent(NatuurTestUtils.getParentTaxon());
    geenFoto.setTaxon(NatuurTestUtils.getTaxon());
  }

  @Test
  public void testCompareTo1() {
    var gelijk  = new GeenFoto(geenFoto);
    var groter  = new GeenFoto(geenFoto);
    var kleiner = new GeenFoto(geenFoto);

    groter.getParent().setVolgnummer(Long.MAX_VALUE);
    kleiner.getParent().setVolgnummer(Long.MIN_VALUE);

    assertTrue(geenFoto.compareTo(groter) < 0);
    assertEquals(0, geenFoto.compareTo(gelijk));
    assertTrue(geenFoto.compareTo(kleiner) > 0);
  }

  @Test
  public void testCompareTo2() {
    var gelijk  = new GeenFoto(geenFoto);
    var groter  = new GeenFoto(geenFoto);
    var kleiner = new GeenFoto(geenFoto);

    groter.getParent().setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    kleiner.getParent().setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);

    assertTrue(geenFoto.compareTo(groter) < 0);
    assertEquals(0, geenFoto.compareTo(gelijk));
    assertTrue(geenFoto.compareTo(kleiner) > 0);
  }

  @Test
  public void testCompareTo3() {
    var gelijk  = new GeenFoto(geenFoto);
    var groter  = new GeenFoto(geenFoto);
    var kleiner = new GeenFoto(geenFoto);

    groter.getTaxon().setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    kleiner.getTaxon().setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);

    assertTrue(geenFoto.compareTo(groter) < 0);
    assertEquals(0, geenFoto.compareTo(gelijk));
    assertTrue(geenFoto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new GeenFoto();

    instance.setParent(NatuurTestUtils.getTaxon());
    instance.setTaxon(NatuurTestUtils.getParentTaxon());

    assertEquals(geenFoto, geenFoto);
    assertNotEquals(geenFoto, null);
    assertNotEquals(geenFoto, TestConstants.NAAM);
    assertNotEquals(geenFoto, instance);

    instance.setParent(NatuurTestUtils.getParentTaxon());
    instance.setTaxon(NatuurTestUtils.getTaxon());
    assertEquals(geenFoto, instance);
  }

  @Test
  public void testInit1() {
    var instance  = new GeenFoto();

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertNull(instance.getTaxon());
  }

  @Test
  public void testInit2() {
    var instance  = new GeenFoto(geenFoto);

    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(TestConstants.PARENTTAXONID,
                 instance.getParent().getTaxonId());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testGetParent() {
    assertEquals(TestConstants.PARENTTAXONID,
                 geenFoto.getParent().getTaxonId());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(TestConstants.PARENTRANG, geenFoto.getParentRang());
  }

  @Test
  public void testGetTaxon() {
    assertEquals(TestConstants.TAXONID,
                 geenFoto.getTaxon().getTaxonId());
  }

  @Test
  public void testHashCode( ){
    assertEquals(TestConstants.GEENFOTO_HASH, geenFoto.hashCode());
  }

  @Test
  public void testSetParent1() {
    var instance  = new GeenFoto();

    assertNull(instance.getParent());

    instance.setParent(NatuurTestUtils.getParentTaxon());

    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(TestConstants.PARENTTAXONID,
                 instance.getParent().getTaxonId());
    assertNull(instance.getTaxon());
  }

  @Test
  public void testSetParent2() {
    var instance  = new GeenFoto();

    assertNull(instance.getParent());

    instance.setParent(NatuurTestUtils.getParentTaxonDto());

    assertEquals(TestConstants.PARENTLATIJNSENAAM,
                 instance.getParent().getNaam());
    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(TestConstants.PARENTTAXONID,
                 instance.getParent().getTaxonId());
    assertNull(instance.getTaxon());
  }

  @Test
  public void testSetParent3() {
    var instance  = new GeenFoto();

    assertNull(instance.getParent());

    instance.setParent(NatuurTestUtils.getParentTaxonDto(), TestConstants.TAAL_KL);

    assertEquals(TestConstants.PARENTNAAM_KL,
                 instance.getParent().getNaam());
    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(TestConstants.PARENTTAXONID,
                 instance.getParent().getTaxonId());
    assertNull(instance.getTaxon());
  }

  @Test
  public void testSetParentRang() {
    var instance  = new GeenFoto();

    assertNull(instance.getParentRang());

    instance.setParentRang(TestConstants.PARENTRANG);

    assertNull(instance.getParent());
    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getTaxon());
  }

  @Test
  public void testSetTaxon1() {
    var instance  = new GeenFoto();

    assertNull(instance.getTaxon());

    instance.setTaxon(NatuurTestUtils.getTaxon());

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetTaxon2() {
    var instance  = new GeenFoto();

    assertNull(instance.getTaxon());

    instance.setTaxon(NatuurTestUtils.getTaxonDto());

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetTaxon3() {
    var instance  = new GeenFoto();

    assertNull(instance.getTaxon());

    instance.setTaxon(NatuurTestUtils.getTaxonDto(), TestConstants.TAAL_KL);

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }
}
