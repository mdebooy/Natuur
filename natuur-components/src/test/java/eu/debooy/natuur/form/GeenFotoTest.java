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
import eu.debooy.natuur.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

    geenFoto.setParent(TestUtils.getParentTaxon());
    geenFoto.setTaxon(TestUtils.getTaxon());
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

    instance.setParent(TestUtils.getParentTaxon());

    assertEquals(TestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(TestConstants.PARENTTAXONID,
                 instance.getParent().getTaxonId());
    assertNull(instance.getTaxon());
  }

  @Test
  public void testSetParent2() {
    var instance  = new GeenFoto();

    assertNull(instance.getParent());

    instance.setParent(TestUtils.getParentTaxonDto());

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

    instance.setParent(TestUtils.getParentTaxonDto(), TestConstants.TAAL_KL);

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

    instance.setTaxon(TestUtils.getTaxon());

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetTaxon2() {
    var instance  = new GeenFoto();

    assertNull(instance.getTaxon());

    instance.setTaxon(TestUtils.getTaxonDto());

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetTaxon3() {
    var instance  = new GeenFoto();

    assertNull(instance.getTaxon());

    instance.setTaxon(TestUtils.getTaxonDto(), TestConstants.TAAL_KL);

    assertNull(instance.getParent());
    assertNull(instance.getParentRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }
}
