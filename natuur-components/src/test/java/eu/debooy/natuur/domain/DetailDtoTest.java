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
import eu.debooy.natuur.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class DetailDtoTest {
  private static DetailDto  detailDto;

  @BeforeClass
  public static void setUpClass()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    detailDto = TestUtils.getDetailDto();
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(TestConstants.LATIJNSENAAM, detailDto.getLatijnsenaam());
  }

  @Test
  public void testGetNiveau() {
    assertEquals(TestConstants.NIVEAU, detailDto.getNiveau());
  }

  @Test
  public void testGetOpFoto() {
    assertEquals(TestConstants.OPFOTO, detailDto.getOpFoto());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(TestConstants.OPMERKING, detailDto.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(TestConstants.PARENTTAXONID, detailDto.getParentId());
  }

  @Test
  public void testGetParentLatijnsenaam() {
    assertEquals(TestConstants.PARENTLATIJNSENAAM,
                 detailDto.getParentLatijnsenaam());
  }

  @Test
  public void testGetParentnaam() {
    assertEquals(TestConstants.PARENTLATIJNSENAAM,
                 detailDto.getParentnaam(TestConstants.TAAL_GR));
    assertEquals(TestConstants.PARENTNAAM_KL,
                 detailDto.getParentnaam(TestConstants.TAAL_KL));
  }

  @Test
  public void testGetParentnamen() {
    assertEquals(2, detailDto.getParentnamen().size());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(TestConstants.PARENTRANG, detailDto.getParentRang());
  }

  @Test
  public void testGetParentUitgestorven() {
    assertFalse(detailDto.getParentUitgestorven());
  }

  @Test
  public void testGetParentVolgnummer() {
    assertEquals(TestConstants.PARENTVOLGNUMMER,
                 detailDto.getParentVolgnummer());
  }

  @Test
  public void testGetRang() {
    assertEquals(TestConstants.RANG, detailDto.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TestConstants.TAXONID, detailDto.getTaxonId());
  }

  @Test
  public void testGetTaxonnaam() {
    assertNull(detailDto.getTaxonnaam(TestConstants.TAAL_GR).getNaam());
    assertEquals(TestConstants.TAXONNAAM_KL,
                 detailDto.getTaxonnaam(TestConstants.TAAL_KL).getNaam());
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
    assertEquals(TestConstants.VOLGNUMMER, detailDto.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.DETAILPK_HASH, detailDto.hashCode());
  }

  @Test
  public void testHasParentnaam() {
    assertTrue(detailDto.hasParentnaam(TestConstants.TAAL_KL));
    assertFalse(detailDto.hasParentnaam(TestConstants.TAAL_GR));
  }

  @Test
  public void testHasTaxonnaam() {
    assertTrue(detailDto.hasTaxonnaam(TestConstants.TAAL_KL));
    assertFalse(detailDto.hasTaxonnaam(TestConstants.TAAL_GR));
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
  public void testIsUitgestorven() {
    assertFalse(detailDto.isUitgestorven());
  }
}
