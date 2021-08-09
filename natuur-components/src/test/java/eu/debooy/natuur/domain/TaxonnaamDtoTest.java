package eu.debooy.natuur.domain;

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

import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.NAAM_GR;
import static eu.debooy.natuur.TestConstants.NAAM_KL;
import static eu.debooy.natuur.TestConstants.RANGNAAM;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONNAAM_HASH;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonnaamDtoTest {
  private static  TaxonnaamDto  taxonnaamDto;

  @BeforeClass
  public static void beforeClass() {
    taxonnaamDto  = new TaxonnaamDto();

    taxonnaamDto.setNaam(NAAM);
    taxonnaamDto.setTaal(TAAL);
    taxonnaamDto.setTaxonId(TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new TaxonnaamDto();
    var groter  = new TaxonnaamDto();
    var kleiner = new TaxonnaamDto();

    gelijk.setNaam(NAAM);
    gelijk.setTaal(TAAL);
    gelijk.setTaxonId(TAXONID);
    groter.setNaam(NAAM);
    groter.setTaal(TAAL);
    groter.setTaxonId(TAXONID + 1);
    kleiner.setNaam(NAAM);
    kleiner.setTaal(TAAL);
    kleiner.setTaxonId(TAXONID - 1);

    assertTrue(taxonnaamDto.compareTo(groter) < 0);
    assertEquals(0, taxonnaamDto.compareTo(gelijk));
    assertTrue(taxonnaamDto.compareTo(kleiner) > 0);

    groter.setTaal(TAAL_GR);
    groter.setTaxonId(TAXONID);
    kleiner.setTaal(TAAL_KL);
    kleiner.setTaxonId(TAXONID);

    assertTrue(taxonnaamDto.compareTo(groter) < 0);
    assertEquals(0, taxonnaamDto.compareTo(gelijk));
    assertTrue(taxonnaamDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new TaxonnaamDto();

    assertEquals(taxonnaamDto, taxonnaamDto);
    assertNotEquals(taxonnaamDto, null);
    assertNotEquals(taxonnaamDto, RANGNAAM);
    assertNotEquals(taxonnaamDto, instance);

    instance.setTaxonId(taxonnaamDto.getTaxonId());
    instance.setTaal(taxonnaamDto.getTaal());
    assertEquals(taxonnaamDto, instance);

    instance.setTaxonId(taxonnaamDto.getTaxonId() + 1);
    assertNotEquals(taxonnaamDto, instance);

    instance.setTaxonId(taxonnaamDto.getTaxonId());
    instance.setTaal(TAAL_GR);
    assertNotEquals(taxonnaamDto, instance);
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, taxonnaamDto.getNaam());
  }

  @Test
  public void testGetTaal() {
    assertEquals(TAAL, taxonnaamDto.getTaal());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TAXONID, taxonnaamDto.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TAXONNAAM_HASH, taxonnaamDto.hashCode());
  }

  @Test
  public void testNaamComparator() {
    var groter  = new TaxonnaamDto();
    var kleiner = new TaxonnaamDto();

    groter.setNaam(NAAM_GR);

    kleiner.setNaam(NAAM_KL);

    Set<TaxonnaamDto> taxonnamen =
        new TreeSet<>(new TaxonnaamDto.NaamComparator());
    taxonnamen.add(groter);
    taxonnamen.add(taxonnaamDto);
    taxonnamen.add(kleiner);

    var tabel = new TaxonnaamDto[taxonnamen.size()];
    System.arraycopy(taxonnamen.toArray(), 0, tabel, 0, taxonnamen.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(taxonnaamDto.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testSetNaam() {
    var instance  = new TaxonnaamDto();
    assertNotEquals(NAAM, instance.getNaam());

    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
    assertNull(instance.getTaal());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaal() {
    var instance  = new TaxonnaamDto();
    assertNotEquals(TAAL, instance.getTaal());

    instance.setTaal(TAAL);

    assertNull(instance.getNaam());
    assertEquals(TAAL, instance.getTaal());
    assertNull(instance.getTaxonId());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new TaxonnaamDto();
    assertNotEquals(TAXONID, instance.getTaxonId());

    instance.setTaxonId(TAXONID);

    assertNull(instance.getNaam());
    assertNull(instance.getTaal());
    assertEquals(TAXONID, instance.getTaxonId());
  }
}
