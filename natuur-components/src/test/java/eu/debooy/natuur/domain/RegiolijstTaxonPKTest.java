/*
 * Copyright (c) 2023 Marco de Booij
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
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTaxonPKTest {
  private static final String TOSTRING  =
      "RegiolijstTaxonPK (regioId=" + TestConstants.REGIOID
                          + ", taxonId="+ TestConstants.TAXONID + ")";

  private static  RegiolijstTaxonPK regiolijstTaxonPK;

  @BeforeClass
  public static void setUpClass() {
    regiolijstTaxonPK  = new RegiolijstTaxonPK(TestConstants.REGIOID,
                                               TestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstTaxonPK();
    var groter  = new RegiolijstTaxonPK();
    var kleiner = new RegiolijstTaxonPK();

    gelijk.setRegioId(regiolijstTaxonPK.getRegioId());
    gelijk.setTaxonId(regiolijstTaxonPK.getTaxonId());
    groter.setRegioId(regiolijstTaxonPK.getRegioId());
    groter.setTaxonId(regiolijstTaxonPK.getTaxonId() + 1);
    kleiner.setRegioId(regiolijstTaxonPK.getRegioId());
    kleiner.setTaxonId(regiolijstTaxonPK.getTaxonId() - 1);

    assertTrue(regiolijstTaxonPK.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonPK.compareTo(gelijk));
    assertTrue(regiolijstTaxonPK.compareTo(kleiner) > 0);

    groter.setRegioId(regiolijstTaxonPK.getRegioId() + 1);
    groter.setTaxonId(regiolijstTaxonPK.getTaxonId());
    kleiner.setRegioId(regiolijstTaxonPK.getRegioId() - 1);
    kleiner.setTaxonId(regiolijstTaxonPK.getTaxonId());

    assertTrue(regiolijstTaxonPK.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonPK.compareTo(gelijk));
    assertTrue(regiolijstTaxonPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegiolijstTaxonPK();

    assertEquals(regiolijstTaxonPK, regiolijstTaxonPK);
    assertNotEquals(regiolijstTaxonPK, null);
    assertNotEquals(regiolijstTaxonPK, NAAM);
    assertNotEquals(regiolijstTaxonPK, instance);

    instance  = new RegiolijstTaxonPK(TestConstants.REGIOID,
                                      TestConstants.TAXONID);
    assertEquals(regiolijstTaxonPK, instance);

    instance.setTaxonId(TAXONID - 1);
    assertNotEquals(regiolijstTaxonPK, instance);
  }

  @Test
  public void getRegioId() {
    assertEquals(TestConstants.REGIOID, regiolijstTaxonPK.getRegioId());
  }

  @Test
  public void getTaxonId() {
    assertEquals(TestConstants.TAXONID, regiolijstTaxonPK.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.REGIOLIJSTTAXONPK_HASH,
                 regiolijstTaxonPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new RegiolijstTaxonPK();

    assertNull(instance.getRegioId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit2() {
    var instance  = new RegiolijstTaxonPK(TestConstants.REGIOID,
                                          TestConstants.TAXONID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new RegiolijstTaxonPK();
    assertNotEquals(TestConstants.REGIOID, instance.getRegioId());
    instance.setRegioId(TestConstants.REGIOID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new RegiolijstTaxonPK();
    assertNotEquals(TestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(TestConstants.TAXONID);

    assertNull(instance.getRegioId());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, regiolijstTaxonPK.toString());
  }
}
