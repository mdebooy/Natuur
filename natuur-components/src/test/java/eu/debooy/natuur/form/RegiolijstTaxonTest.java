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
package eu.debooy.natuur.form;

import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTaxonTest {
  private static  RegiolijstTaxon regiolijstTaxon;

  @BeforeClass
  public static void setUpClass() {
    regiolijstTaxon = new RegiolijstTaxon();
    regiolijstTaxon.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);
    regiolijstTaxon.setStatus(NatuurTestConstants.STATUS);
    regiolijstTaxon.setTaxonId(NatuurTestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstTaxon();
    var groter  = new RegiolijstTaxon();
    var kleiner = new RegiolijstTaxon();

    gelijk.setRegiolijstId(regiolijstTaxon.getRegiolijstId());
    gelijk.setTaxonId(regiolijstTaxon.getTaxonId());
    groter.setRegiolijstId(regiolijstTaxon.getRegiolijstId() + 1);
    kleiner.setRegiolijstId(regiolijstTaxon.getRegiolijstId() - 1);

    assertTrue(regiolijstTaxon.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxon.compareTo(gelijk));
    assertTrue(regiolijstTaxon.compareTo(kleiner) > 0);

    groter.setRegiolijstId(regiolijstTaxon.getRegiolijstId());
    groter.setTaxonId(regiolijstTaxon.getTaxonId() + 1);
    kleiner.setRegiolijstId(regiolijstTaxon.getRegiolijstId());
    kleiner.setTaxonId(regiolijstTaxon.getTaxonId() - 1);

    assertTrue(regiolijstTaxon.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxon.compareTo(gelijk));
    assertTrue(regiolijstTaxon.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var dto       = new RegiolijstTaxonDto();
    var instance  = new RegiolijstTaxon();

    regiolijstTaxon.persist(dto);

    assertEquals(regiolijstTaxon, regiolijstTaxon);
    assertNotEquals(regiolijstTaxon, null);
    assertNotEquals(regiolijstTaxon, NatuurTestConstants.NAAM);
    assertNotEquals(regiolijstTaxon, instance);

    instance.setRegiolijstId(regiolijstTaxon.getRegiolijstId());
    instance.setTaxonId(regiolijstTaxon.getTaxonId());
    assertEquals(regiolijstTaxon, instance);

    instance  = new RegiolijstTaxon(dto);
    assertEquals(regiolijstTaxon, instance);
  }

  @Test
  public void testGetRegiolijstId() {
    assertEquals(NatuurTestConstants.REGIOLIJSTID, regiolijstTaxon.getRegiolijstId());
  }

  @Test
  public void testGetStatus() {
    assertEquals(NatuurTestConstants.STATUS, regiolijstTaxon.getStatus());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, regiolijstTaxon.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.REGIOLIJSTTAXON_HASH,
                 regiolijstTaxon.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new RegiolijstTaxon();

    assertFalse(instance.isGezien());
    assertNull(instance.getRegiolijstId());
    assertEquals("", instance.getStatus());
    assertNull(instance.getTaxon());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit2() {
    var dto       = new RegiolijstTaxonDto();
    dto.setGezien(true);
    dto.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);
    dto.setStatus(NatuurTestConstants.STATUS);
    var instance  = new RegiolijstTaxon(dto);

    assertTrue(instance.isGezien());
    assertEquals(dto.getRegiolijstId(), instance.getRegiolijstId());
    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());
    assertNull(instance.getTaxon().getTaxonId());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit3() {
    var taxonDto  = new TaxonDto();
    taxonDto.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxonDto.setTaxonId(NatuurTestConstants.TAXONID);
    var dto       = new RegiolijstTaxonDto();
    dto.setGezien(true);
    dto.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);
    dto.setStatus(NatuurTestConstants.STATUS);
    dto.setTaxon(taxonDto);
    var instance  = new RegiolijstTaxon(dto);

    assertTrue(instance.isGezien());
    assertEquals(dto.getRegiolijstId(), instance.getRegiolijstId());
    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxon().getTaxonId());
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 instance.getTaxon().getLatijnsenaam() );
    assertEquals(NatuurTestConstants.LATIJNSENAAM, instance.getTaxon().getNaam());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testInit4() {
    var taxonnaamDto  = new TaxonnaamDto();
    taxonnaamDto.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaamDto.setTaal(NatuurTestConstants.TAAL);
    var taxonDto      = new TaxonDto();
    taxonDto.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxonDto.setTaxonId(NatuurTestConstants.TAXONID);
    taxonDto.addNaam(taxonnaamDto);
    var dto           = new RegiolijstTaxonDto();
    dto.setGezien(true);
    dto.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);
    dto.setStatus(NatuurTestConstants.STATUS);
    dto.setTaxon(taxonDto);
    var instance      = new RegiolijstTaxon(dto, NatuurTestConstants.TAAL);

    assertTrue(instance.isGezien());
    assertEquals(dto.getRegiolijstId(), instance.getRegiolijstId());
    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxon().getTaxonId());
    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 instance.getTaxon().getLatijnsenaam() );
    assertEquals(NatuurTestConstants.TAXONNAAM, instance.getTaxon().getNaam());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testPersist() {
    var parameter = new RegiolijstTaxonDto();

    regiolijstTaxon.persist(parameter);

    assertEquals(regiolijstTaxon.getRegiolijstId(), parameter.getRegiolijstId());
    assertEquals(regiolijstTaxon.getStatus(), parameter.getStatus());
    assertEquals(regiolijstTaxon.getTaxonId(), parameter.getTaxonId());

    regiolijstTaxon.persist(parameter);

    assertEquals(regiolijstTaxon.getRegiolijstId(), parameter.getRegiolijstId());
    assertEquals(regiolijstTaxon.getStatus(), parameter.getStatus());
    assertEquals(regiolijstTaxon.getTaxonId(), parameter.getTaxonId());
  }

  @Test
  public void testSetGezien() {
    var instance  = new RegiolijstTaxon();

    assertFalse(instance.isGezien());
    instance.setGezien(true);
    assertTrue(instance.isGezien());
    instance.setGezien(false);
    assertFalse(instance.isGezien());
  }

  @Test
  public void testSetRegiolijstId() {
    var instance  = new RegiolijstTaxon();

    assertNotEquals(NatuurTestConstants.REGIOLIJSTID,
                    instance.getRegiolijstId());
    instance.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);

    assertEquals(NatuurTestConstants.REGIOLIJSTID, instance.getRegiolijstId());
  }

  @Test
  public void testSetStatus() {
    var instance  = new RegiolijstTaxon();

    assertNotEquals(NatuurTestConstants.STATUS, instance.getStatus());
    instance.setStatus(NatuurTestConstants.STATUS);

    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());
  }

  @Test
  public void testSetTaxon() {
    var instance  = new RegiolijstTaxon();
    var taxon     = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setTaxonId(NatuurTestConstants.TAXONID);

    assertNull(instance.getTaxon());
    instance.setTaxon(taxon);

    assertEquals(NatuurTestConstants.LATIJNSENAAM,
                 instance.getTaxon().getLatijnsenaam());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new RegiolijstTaxon();

    assertNotEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
  }
}
