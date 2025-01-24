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

package eu.debooy.natuur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class NatuurUtilsTest {
  private static final  String  ISUITGESTORVEN  =
          String.format("%s %s", NatuurTestConstants.LATIJNSENAAM,
                                 NatuurConstants.UITGESTORVEN);

  @Test
  public void testFormatLatijnsenaam() {
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(NatuurTestConstants.LATIJNSENAAM));
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(ISUITGESTORVEN));
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(NatuurTestConstants.LATIJNSENAAM
                    + " "));
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(ISUITGESTORVEN + " "));
  }

  @Test
  public void testGetBoolean() {
    assertEquals(NatuurUtils.BOOLEANFALSE, NatuurUtils.getBoolean(false));
    assertEquals(NatuurUtils.BOOLEANTRUE,  NatuurUtils.getBoolean(true));
  }

  @Test
  public void testGetCamera() {
    assertEquals(NatuurUtils.CAMERAFALSE, NatuurUtils.getCamera(false));
    assertEquals(NatuurUtils.CAMERATRUE,  NatuurUtils.getCamera(true));
  }

  @Test
  public void testGetLatijnsenaam() {
    var latijnsenaam  = String.format("%s %s", NatuurTestConstants.LATIJNSENAAM,
                                               NatuurConstants.UITGESTORVEN);

    assertEquals(latijnsenaam,
                 NatuurUtils.getLatijnsenaam(NatuurTestConstants.LATIJNSENAAM,
                                             Boolean.TRUE));
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 NatuurUtils.getLatijnsenaam(NatuurTestConstants.LATIJNSENAAM,
                                             Boolean.FALSE));
  }

  @Test
  public void testGetNaam1() {
    var taxon = NatuurTestUtils.getParentTaxonDto();

    assertEquals(NatuurTestConstants.PARENTNAAM_KL,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_KL));
    assertEquals("", NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_GR));

    taxon.setRang(NatuurConstants.RANG_ONDERSOORT);
    assertEquals("", NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNaam2() {
    var taxon = NatuurTestUtils.getParentTaxonDto();

    assertEquals(NatuurTestConstants.PARENTNAAM_KL,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_KL));
    assertEquals("", NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetNaam3()
      throws IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon = NatuurTestUtils.getOndersoortTaxonDto();

    assertEquals(NatuurTestConstants.ONDERSOORTNAAM,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL));
    assertEquals("", NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_GR));
    assertEquals(NatuurTestConstants.ONDERSOORTNAAM_KL,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_KL));
  }

  @Test
  public void testGetNaam4()
      throws IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon = NatuurTestUtils.getVarieteitTaxonDto();

    assertEquals(NatuurTestConstants.VARIETEITNAAM,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL));
    assertEquals("", NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_GR));
    assertEquals(NatuurTestConstants.VARIETEITNAAM_KL,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_KL));
  }

  @Test
  public void testGetNaam5()
      throws IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon = NatuurTestUtils.getVormTaxonDto();

    assertEquals(NatuurTestConstants.VORMNAAM,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL));
    assertEquals("", NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_GR));
    assertEquals(NatuurTestConstants.VORMNAAM_KL,
                 NatuurUtils.getNaam(taxon, NatuurTestConstants.TAAL_KL));
  }

  @Test
  public void testGetNaam6()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getSoortDetailDto();
    assertEquals(NatuurTestConstants.TAXONNAAM,
                 NatuurUtils.getNaam(instance, NatuurTestConstants.TAAL));
  }

  @Test
  public void testGetNaam7()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var instance  = NatuurTestUtils.getSoortDetailDto();
    assertEquals("",
                 NatuurUtils.getNaam(instance, NatuurTestConstants.TAAL_GR));
  }

  @Test
  public void testGetSubtitel() {
    var resultaat1  = String.format("%s %s/%s/%s/%s",
                                    NatuurTestConstants.LATIJNSENAAM,
                                    NatuurConstants.UITGESTORVEN,
                                    NatuurTestConstants.TAXONNAAM,
                                    NatuurTestConstants.TAXONNAAM_GR,
                                    NatuurTestConstants.TAXONNAAM_KL);
    var resultaat2  = String.format("%s/%s/%s/%s",
                                    NatuurTestConstants.LATIJNSENAAM,
                                    NatuurTestConstants.TAXONNAAM,
                                    NatuurTestConstants.TAXONNAAM_GR,
                                    NatuurTestConstants.TAXONNAAM_KL);
    var resultaat3  = String.format("%s/%s/%s",
                                    NatuurTestConstants.LATIJNSENAAM,
                                    NatuurTestConstants.TAXONNAAM_GR,
                                    NatuurTestConstants.TAXONNAAM_KL);

    assertEquals(resultaat1,
                 NatuurUtils.getSubtitel(NatuurTestConstants.LATIJNSENAAM, true,
                                         NatuurTestConstants.TAXONNAAM,
                                         NatuurTestConstants.TAXONNAAM_GR,
                                         NatuurTestConstants.TAXONNAAM_KL));
    assertEquals(resultaat2,
                 NatuurUtils.getSubtitel(NatuurTestConstants.LATIJNSENAAM, false,
                                         NatuurTestConstants.TAXONNAAM,
                                         NatuurTestConstants.TAXONNAAM_GR,
                                         NatuurTestConstants.TAXONNAAM_KL));
    assertEquals(resultaat3,
                 NatuurUtils.getSubtitel(NatuurTestConstants.LATIJNSENAAM, false,
                                         NatuurTestConstants.LATIJNSENAAM,
                                         NatuurTestConstants.TAXONNAAM_GR,
                                         NatuurTestConstants.TAXONNAAM_KL));
    assertEquals(resultaat3,
                 NatuurUtils.getSubtitel(NatuurTestConstants.LATIJNSENAAM, false,
                                         NatuurTestConstants.TAXONNAAM_GR,
                                         NatuurTestConstants.LATIJNSENAAM,
                                         NatuurTestConstants.TAXONNAAM_KL));
    assertEquals(resultaat3,
                 NatuurUtils.getSubtitel(NatuurTestConstants.LATIJNSENAAM, false,
                                         NatuurTestConstants.TAXONNAAM_GR,
                                         NatuurTestConstants.TAXONNAAM_KL,
                                         NatuurTestConstants.LATIJNSENAAM));
  }

  @Test
  public void testIsUitgestorven() {
    assertTrue(NatuurUtils.isUitgestorven(ISUITGESTORVEN));
    assertTrue(NatuurUtils.isUitgestorven(ISUITGESTORVEN + " "));
    assertFalse(NatuurUtils.isUitgestorven(NatuurTestConstants.LATIJNSENAAM));
  }
}
