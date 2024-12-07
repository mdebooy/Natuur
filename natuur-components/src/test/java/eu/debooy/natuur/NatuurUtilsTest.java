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
          String.format("%s %s", TestConstants.LATIJNSENAAM,
                                 NatuurConstants.UITGESTORVEN);

  @Test
  public void testFormatLatijnsenaam() {
    assertEquals(TestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(TestConstants.LATIJNSENAAM));
    assertEquals(TestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(ISUITGESTORVEN));
    assertEquals(TestConstants.LATIJNSENAAM,
                 NatuurUtils.formatLatijnsenaam(TestConstants.LATIJNSENAAM
                    + " "));
    assertEquals(TestConstants.LATIJNSENAAM,
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
    var latijnsenaam  = String.format("%s %s", TestConstants.LATIJNSENAAM,
                                               NatuurConstants.UITGESTORVEN);

    assertEquals(latijnsenaam,
                 NatuurUtils.getLatijnsenaam(TestConstants.LATIJNSENAAM,
                                             Boolean.TRUE));
    assertEquals(TestConstants.LATIJNSENAAM,
                 NatuurUtils.getLatijnsenaam(TestConstants.LATIJNSENAAM,
                                             Boolean.FALSE));
  }

  @Test
  public void testGetNaam1() {
    var taxon = TestUtils.getParentTaxonDto();

    assertEquals(TestConstants.PARENTNAAM_KL,
                 NatuurUtils.getNaam(taxon, TestConstants.TAAL_KL));
    assertEquals("", NatuurUtils.getNaam(taxon, TestConstants.TAAL_GR));

    taxon.setRang(NatuurConstants.RANG_ONDERSOORT);
    assertEquals("", NatuurUtils.getNaam(taxon, TestConstants.TAAL_GR));
  }

  @Test
  public void testGetSubtitel() {
    var resultaat1  = String.format("%s %s/%s/%s/%s",
                                    TestConstants.LATIJNSENAAM,
                                    NatuurConstants.UITGESTORVEN,
                                    TestConstants.TAXONNAAM,
                                    TestConstants.TAXONNAAM_GR,
                                    TestConstants.TAXONNAAM_KL);
    var resultaat2  = String.format("%s/%s/%s/%s",
                                    TestConstants.LATIJNSENAAM,
                                    TestConstants.TAXONNAAM,
                                    TestConstants.TAXONNAAM_GR,
                                    TestConstants.TAXONNAAM_KL);
    var resultaat3  = String.format("%s/%s/%s",
                                    TestConstants.LATIJNSENAAM,
                                    TestConstants.TAXONNAAM_GR,
                                    TestConstants.TAXONNAAM_KL);

    assertEquals(resultaat1,
                 NatuurUtils.getSubtitel(TestConstants.LATIJNSENAAM, true,
                                         TestConstants.TAXONNAAM,
                                         TestConstants.TAXONNAAM_GR,
                                         TestConstants.TAXONNAAM_KL));
    assertEquals(resultaat2,
                 NatuurUtils.getSubtitel(TestConstants.LATIJNSENAAM, false,
                                         TestConstants.TAXONNAAM,
                                         TestConstants.TAXONNAAM_GR,
                                         TestConstants.TAXONNAAM_KL));
    assertEquals(resultaat3,
                 NatuurUtils.getSubtitel(TestConstants.LATIJNSENAAM, false,
                                         TestConstants.LATIJNSENAAM,
                                         TestConstants.TAXONNAAM_GR,
                                         TestConstants.TAXONNAAM_KL));
    assertEquals(resultaat3,
                 NatuurUtils.getSubtitel(TestConstants.LATIJNSENAAM, false,
                                         TestConstants.TAXONNAAM_GR,
                                         TestConstants.LATIJNSENAAM,
                                         TestConstants.TAXONNAAM_KL));
    assertEquals(resultaat3,
                 NatuurUtils.getSubtitel(TestConstants.LATIJNSENAAM, false,
                                         TestConstants.TAXONNAAM_GR,
                                         TestConstants.TAXONNAAM_KL,
                                         TestConstants.LATIJNSENAAM));
  }

  @Test
  public void testIsUitgestorven() {
    assertTrue(NatuurUtils.isUitgestorven(ISUITGESTORVEN));
    assertTrue(NatuurUtils.isUitgestorven(ISUITGESTORVEN + " "));
    assertFalse(NatuurUtils.isUitgestorven(TestConstants.LATIJNSENAAM));
  }
}
