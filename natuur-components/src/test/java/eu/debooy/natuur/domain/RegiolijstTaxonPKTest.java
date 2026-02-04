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

import eu.debooy.natuur.NatuurTestConstants;
import static eu.debooy.natuur.NatuurTestConstants.NAAM;
import static eu.debooy.natuur.NatuurTestConstants.TAXONID;
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
      "RegiolijstTaxonPK (regiolijstId=" + NatuurTestConstants.REGIOLIJSTID
                          + ", taxonId="+ NatuurTestConstants.TAXONID + ")";

  private static  RegiolijstTaxonPK regiolijstTaxonPK;

  @BeforeClass
  public static void setUpClass() {
    regiolijstTaxonPK  = new RegiolijstTaxonPK(NatuurTestConstants.REGIOLIJSTID,
                                               NatuurTestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstTaxonPK();
    var groter  = new RegiolijstTaxonPK();
    var kleiner = new RegiolijstTaxonPK();

    gelijk.setRegiolijstId(regiolijstTaxonPK.getRegiolijstId());
    gelijk.setTaxonId(regiolijstTaxonPK.getTaxonId());
    groter.setRegiolijstId(regiolijstTaxonPK.getRegiolijstId());
    groter.setTaxonId(regiolijstTaxonPK.getTaxonId() + 1);
    kleiner.setRegiolijstId(regiolijstTaxonPK.getRegiolijstId());
    kleiner.setTaxonId(regiolijstTaxonPK.getTaxonId() - 1);

    assertTrue(regiolijstTaxonPK.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonPK.compareTo(gelijk));
    assertTrue(regiolijstTaxonPK.compareTo(kleiner) > 0);

    groter.setRegiolijstId(regiolijstTaxonPK.getRegiolijstId() + 1);
    groter.setTaxonId(regiolijstTaxonPK.getTaxonId());
    kleiner.setRegiolijstId(regiolijstTaxonPK.getRegiolijstId() - 1);
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

    instance  = new RegiolijstTaxonPK(NatuurTestConstants.REGIOLIJSTID,
                                      NatuurTestConstants.TAXONID);
    assertEquals(regiolijstTaxonPK, instance);

    instance.setTaxonId(TAXONID - 1);
    assertNotEquals(regiolijstTaxonPK, instance);
  }

  @Test
  public void getRegiolijstId() {
    assertEquals(NatuurTestConstants.REGIOLIJSTID,
                 regiolijstTaxonPK.getRegiolijstId());
  }

  @Test
  public void getTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID,
                 regiolijstTaxonPK.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.REGIOLIJSTTAXONPK_HASH,
                 regiolijstTaxonPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new RegiolijstTaxonPK();

    assertNull(instance.getRegiolijstId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit2() {
    var instance  = new RegiolijstTaxonPK(NatuurTestConstants.REGIOLIJSTID,
                                          NatuurTestConstants.TAXONID);

    assertEquals(NatuurTestConstants.REGIOLIJSTID, instance.getRegiolijstId());
    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetRegiolijstId() {
    var instance  = new RegiolijstTaxonPK();
    assertNotEquals(NatuurTestConstants.REGIOLIJSTID,
                    instance.getRegiolijstId());
    instance.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);

    assertEquals(NatuurTestConstants.REGIOLIJSTID, instance.getRegiolijstId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new RegiolijstTaxonPK();
    assertNotEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertNull(instance.getRegiolijstId());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, regiolijstTaxonPK.toString());
  }
}
