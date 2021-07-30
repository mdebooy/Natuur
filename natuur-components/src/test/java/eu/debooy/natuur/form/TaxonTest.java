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

import static eu.debooy.doosutils.DoosConstants.ONWAAR;
import static eu.debooy.doosutils.DoosConstants.WAAR;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.PARENTLATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.PARENTNAAM;
import static eu.debooy.natuur.TestConstants.PARENTVOLGNUMMER;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONID_HASH;
import static eu.debooy.natuur.TestConstants.VOLGNUMMER;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.TaxonDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonTest {
  private static  Taxon     taxon;
  private static  TaxonDto  taxonDto;

  @BeforeClass
  public static void setUpClass() {
    taxon     = TestUtils.getTaxon();
    taxonDto  = TestUtils.getTaxonDto();
  }

  @Test
  public void testCompareTo() {
    Taxon gelijk  = new Taxon(taxon);
    Taxon groter  = new Taxon();
    groter.setTaxonId(taxon.getTaxonId() + 1);
    Taxon kleiner = new Taxon();
    kleiner.setTaxonId(taxon.getTaxonId() - 1);

    assertTrue(taxon.compareTo(groter) < 0);
    assertEquals(0, taxon.compareTo(gelijk));
    assertTrue(taxon.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Taxon object    = null;
    Taxon instance  = new Taxon();

    assertNotEquals(taxon, object);
    assertNotEquals(taxon, instance);

    instance.setTaxonId(taxon.getTaxonId());
    assertEquals(taxon, instance);

    instance  = new Taxon(taxon);
    assertEquals(taxon, instance);

    instance  = new Taxon(taxonDto);
    assertEquals(taxon, instance);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(LATIJNSENAAM, taxon.getLatijnsenaam());
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, taxon.getNaam());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, taxon.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(PARENTID, taxon.getParentId());
  }

  @Test
  public void testGetParentLatijnsenaam() {
    assertEquals(PARENTLATIJNSENAAM, taxon.getParentLatijnsenaam());
  }

  @Test
  public void testGetParentNaam() {
    assertEquals(PARENTNAAM, taxon.getParentNaam());
  }

  @Test
  public void testGetParentVolgnummer() {
    assertEquals(PARENTVOLGNUMMER, taxon.getParentVolgnummer());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, taxon.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TAXONID, taxon.getTaxonId());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(VOLGNUMMER, taxon.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(TAXONID_HASH, taxon.hashCode());
  }

  @Test
  public void testPersist() {
    TaxonDto  parameter = new TaxonDto();
    Taxon     instance  = new Taxon();
    instance.persist(parameter);

    assertEquals(instance.getLatijnsenaam(), parameter.getLatijnsenaam());
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getParentId(), parameter.getParentId());
    assertEquals(instance.getRang(), parameter.getRang());
    assertEquals(instance.getTaxonId(), parameter.getTaxonId());
    assertEquals(instance.getVolgnummer(), parameter.getVolgnummer());
  }

  @Test
  public void testSetLatijnsenaam() {
    Taxon instance  = new Taxon();
    assertNotEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    instance.setLatijnsenaam(LATIJNSENAAM);

    assertEquals(LATIJNSENAAM, instance.getLatijnsenaam());
  }

  @Test
  public void testSetNaam() {
    Taxon instance  = new Taxon();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
  }

  @Test
  public void testSetOpmerking() {
    Taxon instance  = new Taxon();
    assertNotEquals(OPMERKING, instance.getOpmerking());
    instance.setOpmerking(OPMERKING);

    assertEquals(OPMERKING, instance.getOpmerking());
  }

  @Test
  public void testSetParentId() {
    Taxon instance  = new Taxon();
    assertNotEquals(PARENTID, instance.getParentId());
    instance.setParentId(PARENTID);

    assertEquals(PARENTID, instance.getParentId());
  }

  @Test
  public void testSetParentLatijnsenaam() {
    Taxon instance  = new Taxon();
    assertNotEquals(PARENTLATIJNSENAAM,
                           instance.getParentLatijnsenaam());
    instance.setParentLatijnsenaam(PARENTLATIJNSENAAM);

    assertEquals(PARENTLATIJNSENAAM, instance.getParentLatijnsenaam());
  }

  @Test
  public void testSetParentNaam() {
    Taxon instance  = new Taxon();
    assertNotEquals(PARENTNAAM, instance.getParentNaam());
    instance.setParentNaam(PARENTNAAM);

    assertEquals(PARENTNAAM, instance.getParentNaam());
  }

  @Test
  public void testSetParentVolgnummer() {
    Taxon instance  = new Taxon();
    assertNotEquals(PARENTVOLGNUMMER, instance.getParentVolgnummer());
    instance.setParentVolgnummer(PARENTVOLGNUMMER);

    assertEquals(PARENTVOLGNUMMER, instance.getParentVolgnummer());
  }

  @Test
  public void testSetRang() {
    Taxon instance  = new Taxon();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertEquals(RANG, instance.getRang());
  }

  @Test
  public void testSetTaxonId() {
    Taxon instance  = new Taxon();
    assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetUitgestorven() {
    Taxon instance  = new Taxon();
    assertFalse("init ONWAAR", instance.isUitgestorven());
    assertEquals("init get ONWAAR", ONWAAR, instance.getUitgestorven());

    instance.setUitgestorven(WAAR);
    assertTrue("String WAAR", instance.isUitgestorven());
    assertEquals("String get WAAR", WAAR, instance.getUitgestorven());

    instance.setUitgestorven(ONWAAR);
    assertFalse("String ONWAAR", instance.isUitgestorven());
    assertEquals("String get ONWAAR", ONWAAR, instance.getUitgestorven());

    instance.setUitgestorven(true);
    assertTrue("boolean WAAR", instance.isUitgestorven());
    assertEquals("boolean get WAAR", WAAR, instance.getUitgestorven());

    instance.setUitgestorven(false);
    assertFalse("boolean ONWAAR", instance.isUitgestorven());
    assertEquals("boolean get ONWAAR", ONWAAR, instance.getUitgestorven());
  }

  @Test
  public void testSetVolgnummer() {
    Taxon instance  = new Taxon();
    assertNotEquals(VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(VOLGNUMMER);

    assertEquals(VOLGNUMMER, instance.getVolgnummer());
  }
}
