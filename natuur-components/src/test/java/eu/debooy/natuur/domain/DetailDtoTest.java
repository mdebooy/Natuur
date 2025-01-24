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

import eu.debooy.doosutils.test.TestUtils;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class DetailDtoTest {
  private static DetailDto      detailDto;

  @BeforeClass
  public static void setUpClass()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    detailDto = NatuurTestUtils.getSoortDetailDto();
  }

  @Test
  public void testCompareTo1()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var gelijk  = new DetailDto();
    var groter  = new DetailDto();
    var kleiner = new DetailDto();

    TestUtils.setField(gelijk,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId());
    TestUtils.setField(gelijk,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId());
    TestUtils.setField(groter,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId()+1);
    TestUtils.setField(groter,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId());
    TestUtils.setField(kleiner,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId()-1);
    TestUtils.setField(kleiner,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId());

    assertTrue(detailDto.compareTo(groter) < 0);
    assertEquals(0, detailDto.compareTo(gelijk));
    assertTrue(detailDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testCompareTo2()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var gelijk  = new DetailDto();
    var groter  = new DetailDto();
    var kleiner = new DetailDto();

    TestUtils.setField(gelijk,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId());
    TestUtils.setField(gelijk,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId());
    TestUtils.setField(groter,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId());
    TestUtils.setField(groter,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId()+1);
    TestUtils.setField(kleiner,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId());
    TestUtils.setField(kleiner,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId()-1);

    assertTrue(detailDto.compareTo(groter) < 0);
    assertEquals(0, detailDto.compareTo(gelijk));
    assertTrue(detailDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testCompareTo3()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var gelijk  = new DetailDto();
    var groter  = new DetailDto();
    var kleiner = new DetailDto();

    TestUtils.setField(gelijk,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId());
    TestUtils.setField(gelijk,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId());
    TestUtils.setField(groter,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId()-1);
    TestUtils.setField(groter,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId()+1);
    TestUtils.setField(kleiner,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId()+1);
    TestUtils.setField(kleiner,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId()-1);

    assertTrue(detailDto.compareTo(groter) < 0);
    assertEquals(0, detailDto.compareTo(gelijk));
    assertTrue(detailDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = new DetailDto();

    assertEquals(detailDto, detailDto);
    assertNotEquals(detailDto, null);
    assertNotEquals(detailDto, NatuurTestConstants.NAAM);
    assertNotEquals(detailDto, instance);

    TestUtils.setField(instance,
                       DetailDto.COL_TAXONID,
                       detailDto.getTaxonId());
    TestUtils.setField(instance,
                       DetailDto.COL_PARENTID,
                       detailDto.getParentId());
    assertEquals(detailDto, instance);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(NatuurTestConstants.LATIJNSENAAM, detailDto.getLatijnsenaam());
  }

  @Test
  public void testGetNaam1() {
    assertEquals(NatuurTestConstants.TAXONNAAM,
                 detailDto.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.TAXONNAAM_KL,
                 detailDto.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", detailDto.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNaam2()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getSoortDetailDto();
    assertEquals(NatuurTestConstants.TAXONNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.TAXONNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNaam3()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getOndersoortDetailDto();
    assertEquals(NatuurTestConstants.ONDERSOORTNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.ONDERSOORTNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNaam4()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getVarieteitDetailDto();
    assertEquals(NatuurTestConstants.VARIETEITNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.VARIETEITNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNaam5()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getVormDetailDto();
    assertEquals(NatuurTestConstants.VORMNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.VORMNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNiveau() {
    assertEquals(NatuurTestConstants.NIVEAU, detailDto.getNiveau());
  }

  @Test
  public void testGetOpFoto() {
    assertEquals(NatuurTestConstants.OPFOTO, detailDto.getOpFoto());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(NatuurTestConstants.OPMERKING, detailDto.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(NatuurTestConstants.PARENTTAXONID, detailDto.getParentId());
  }

  @Test
  public void testGetParentLatijnsenaam() {
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 detailDto.getParentLatijnsenaam());
  }

  @Test
  public void testGetParentnaam() {
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 detailDto.getParentnaam(NatuurTestConstants.TAAL_GR));
    assertEquals(NatuurTestConstants.PARENTNAAM_KL,
                 detailDto.getParentnaam(NatuurTestConstants.TAAL_KL));
  }

  @Test
  public void testGetParentnamen() {
    assertEquals(2, detailDto.getParentnamen().size());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(NatuurTestConstants.PARENTRANG, detailDto.getParentRang());
  }

  @Test
  public void testGetParentUitgestorven() {
    assertFalse(detailDto.getParentUitgestorven());
  }

  @Test
  public void testGetParentVolgnummer() {
    assertEquals(NatuurTestConstants.PARENTVOLGNUMMER,
                 detailDto.getParentVolgnummer());
  }

  @Test
  public void testGetRang() {
    assertEquals(NatuurTestConstants.RANG, detailDto.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, detailDto.getTaxonId());
  }

  @Test
  public void testGetTaxonnaam() {
    assertNull(detailDto.getTaxonnaam(NatuurTestConstants.TAAL_GR).getNaam());
    assertEquals(NatuurTestConstants.TAXONNAAM_KL,
                 detailDto.getTaxonnaam(NatuurTestConstants.TAAL_KL).getNaam());
  }

  @Test
  public void testGetTaxonnamen() {
    assertEquals(2, detailDto.getTaxonnamen().size());
  }

  @Test
  public void testGetUitgestorven() {
    assertFalse(detailDto.getUitgestorven());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(NatuurTestConstants.VOLGNUMMER, detailDto.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.DETAILPK_HASH, detailDto.hashCode());
  }

  @Test
  public void testHasParentnaam() {
    assertTrue(detailDto.hasParentnaam(NatuurTestConstants.TAAL_KL));
    assertFalse(detailDto.hasParentnaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testHasTaxonnaam() {
    assertTrue(detailDto.hasTaxonnaam(NatuurTestConstants.TAAL_KL));
    assertFalse(detailDto.hasTaxonnaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testIsGezien() {
    assertFalse(detailDto.isGezien());
  }

  @Test
  public void testIsOpFoto() {
    assertTrue(detailDto.isOpFoto());
  }

  @Test
  public void testIsParentUitgestorven() {
    assertFalse(detailDto.isParentUitgestorven());
  }

  @Test
  public void testSetGezien() {
    var instance  = new DetailDto();

    assertFalse(instance.isGezien());

    instance.setGezien(true);
    assertTrue(instance.isGezien());

    instance.setGezien(false);
    assertFalse(instance.isGezien());
  }

  @Test
  public void testIsUitgestorven() {
    assertFalse(detailDto.isUitgestorven());
  }

  @Test
  public void testOndersoort()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getOndersoortDetailDto();

    assertEquals(NatuurTestConstants.ONDERSOORTNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.ONDERSOORTNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testVarieteit()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getVarieteitDetailDto();

    assertEquals(NatuurTestConstants.VARIETEITNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.VARIETEITNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testVorm()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getVormDetailDto();

    assertEquals(NatuurTestConstants.VORMNAAM,
                 instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.VORMNAAM_KL,
                 instance.getNaam(NatuurTestConstants.TAAL_KL));
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL_GR));
  }
}
