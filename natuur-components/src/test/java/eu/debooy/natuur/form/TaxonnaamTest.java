/*
 * Copyright (c) 2020 Marco de Booij
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

import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONNAAM_HASH;
import eu.debooy.natuur.domain.TaxonnaamDto;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonnaamTest {
  private static  Taxonnaam     taxonnaam;
  private static  TaxonnaamDto  taxonnaamDto;

  public TaxonnaamTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    taxonnaam     = new Taxonnaam();
    taxonnaam.setNaam(NAAM);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(TAXONID);

    taxonnaamDto  = new TaxonnaamDto();
    taxonnaamDto.setNaam(NAAM);
    taxonnaamDto.setTaal(TAAL);
    taxonnaamDto.setTaxonId(TAXONID);
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testCompareTo() {
    Taxonnaam gelijk  = new Taxonnaam(taxonnaam);
    Taxonnaam groter  = new Taxonnaam();
    groter.setTaxonId(taxonnaam.getTaxonId() + 1);
    Taxonnaam kleiner = new Taxonnaam();
    kleiner.setTaxonId(taxonnaam.getTaxonId() - 1);

    assertTrue(taxonnaam.compareTo(groter) < 0);
    assertTrue(taxonnaam.compareTo(gelijk) == 0);
    assertTrue(taxonnaam.compareTo(kleiner) > 0);

    groter.setTaxonId(taxonnaam.getTaxonId());
    groter.setTaal(TAAL_GR);
    kleiner.setTaxonId(taxonnaam.getTaxonId());
    kleiner.setTaal(TAAL_KL);

    assertTrue(taxonnaam.compareTo(groter) < 0);
    assertTrue(taxonnaam.compareTo(gelijk) == 0);
    assertTrue(taxonnaam.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Taxonnaam object    = null;
    Taxonnaam instance  = new Taxonnaam();

    assertFalse(taxonnaam.equals(object));
    assertFalse(taxonnaam.equals(instance));
    assertFalse(taxonnaam.equals(this));

    assertTrue(taxonnaam.equals(taxonnaam));

    instance.setTaal(taxonnaam.getTaal());
    instance.setTaxonId(taxonnaam.getTaxonId());
    assertTrue(taxonnaam.equals(instance));

    instance  = new Taxonnaam(taxonnaam);
    assertTrue(taxonnaam.equals(instance));

    instance  = new Taxonnaam(taxonnaamDto);
    assertTrue(taxonnaam.equals(instance));
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, taxonnaam.getNaam());
  }

  @Test
  public void testGetTaal() {
    assertEquals(TAAL, taxonnaam.getTaal());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TAXONID, taxonnaam.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TAXONNAAM_HASH, taxonnaam.hashCode());
  }

  @Test
  public void testPersist() {
    TaxonnaamDto  parameter = new TaxonnaamDto();
    Taxonnaam     instance  = new Taxonnaam();
    instance.persist(parameter);

    assertEquals(instance.getNaam(), parameter.getNaam());
    assertEquals(instance.getTaal(), parameter.getTaal());
    assertEquals(instance.getTaxonId(), parameter.getTaxonId());
  }

  @Test
  public void testSetNaam() {
    Taxonnaam instance  = new Taxonnaam();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
  }

  @Test
  public void testSetTaal() {
    Taxonnaam instance  = new Taxonnaam();
    assertNotEquals(TAAL, instance.getTaal());
    instance.setTaal(TAAL);

    assertEquals(TAAL, instance.getTaal());
  }

  @Test
  public void testSetTaxonId() {
    Taxonnaam instance  = new Taxonnaam();
    assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertEquals(TAXONID, instance.getTaxonId());
  }
}
