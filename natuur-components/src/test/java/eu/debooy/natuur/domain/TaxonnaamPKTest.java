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

import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONNAAM_HASH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonnaamPKTest {
  private static final String TOSTRING  =
      "TaxonnaamPK (taxonId=" + TAXONID + ", taal="+ TAAL + ")";

  private static  TaxonnaamPK taxonnaamPK;

  @BeforeClass
  public static void setUpClass() {
    taxonnaamPK  = new TaxonnaamPK(TAXONID, TAAL);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new TaxonnaamPK();
    var groter  = new TaxonnaamPK();
    var kleiner = new TaxonnaamPK();

    gelijk.setTaal(taxonnaamPK.getTaal());
    gelijk.setTaxonId(taxonnaamPK.getTaxonId());
    groter.setTaal(taxonnaamPK.getTaal());
    groter.setTaxonId(taxonnaamPK.getTaxonId() + 1);
    kleiner.setTaal(taxonnaamPK.getTaal());
    kleiner.setTaxonId(taxonnaamPK.getTaxonId() - 1);

    assertTrue(taxonnaamPK.compareTo(groter) < 0);
    assertEquals(0, taxonnaamPK.compareTo(gelijk));
    assertTrue(taxonnaamPK.compareTo(kleiner) > 0);

    groter.setTaal(TAAL_GR);
    groter.setTaxonId(taxonnaamPK.getTaxonId());
    kleiner.setTaal(TAAL_KL);
    kleiner.setTaxonId(taxonnaamPK.getTaxonId());

    assertTrue(taxonnaamPK.compareTo(groter) < 0);
    assertEquals(0, taxonnaamPK.compareTo(gelijk));
    assertTrue(taxonnaamPK.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new TaxonnaamPK();

    assertEquals(taxonnaamPK, taxonnaamPK);
    assertNotEquals(taxonnaamPK, null);
    assertNotEquals(taxonnaamPK, NAAM);
    assertNotEquals(taxonnaamPK, instance);

    instance  = new TaxonnaamPK(TAXONID, TAAL);
    assertEquals(taxonnaamPK, instance);

    instance.setTaxonId(TAXONID - 1);
    assertNotEquals(taxonnaamPK, instance);
  }

  @Test
  public void getTaal() {
    assertEquals(TAAL, taxonnaamPK.getTaal());
  }

  @Test
  public void getTaxonId() {
    assertEquals(TAXONID, taxonnaamPK.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TAXONNAAM_HASH, taxonnaamPK.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new TaxonnaamPK();

    assertNull(instance.getTaal());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit2() {
    var instance  = new TaxonnaamPK(TAXONID, TAAL);

    assertEquals(TAAL, instance.getTaal());
    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetTaal() {
    var instance  = new TaxonnaamPK();
    assertNotEquals(TAAL, instance.getTaal());
    instance.setTaal(TAAL);

    assertEquals(TAAL, instance.getTaal());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new TaxonnaamPK();
    assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertNull(instance.getTaal());
    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testToString() {
    assertEquals(TOSTRING, taxonnaamPK.toString());
  }
}
