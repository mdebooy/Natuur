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

import eu.debooy.doosutils.errorhandling.exception.IllegalArgumentException;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM_GR;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM_KL;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONID_HASH;
import static eu.debooy.natuur.TestConstants.TAXONNAAM;
import static eu.debooy.natuur.TestConstants.TAXONNAAM_GR;
import static eu.debooy.natuur.TestConstants.TAXONNAAM_KL;
import static eu.debooy.natuur.TestConstants.VOLGNUMMER;
import eu.debooy.natuur.TestUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class TaxonDtoTest {
  private static  TaxonDto  taxonDto;

  @BeforeClass
  public static void setUpClass() {
    taxonDto  = TestUtils.getTaxonDto();
  }

  @Test
  public void testAddNaam1() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaxonId(instance.getTaxonId());
    taxonnaam.setTaal(TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(TAXONNAAM, instance.getTaxonnaam(TAAL).getNaam());
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TAAL).getTaxonId());
    assertNull(instance.getTaxonnaam("taal").getTaxonId());
  }

  @Test
  public void testAddNaam2() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaal(TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(TAXONNAAM, instance.getTaxonnaam(TAAL).getNaam());
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TAAL).getTaxonId());
    assertNull(instance.getTaxonnaam("taal").getTaxonId());
  }

  @Test
  public void testAddNaam3() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(11L);
    try {
      instance.addNaam(taxonnaam);
      fail("Geen IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new TaxonDto();
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();

    gelijk.setTaxonId(taxonDto.getTaxonId());
    groter.setTaxonId(taxonDto.getTaxonId() + 1);
    kleiner.setTaxonId(taxonDto.getTaxonId() - 1);

    assertTrue(taxonDto.compareTo(groter) < 0);
    assertEquals(0, taxonDto.compareTo(gelijk));
    assertTrue(taxonDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new TaxonDto();

    assertEquals(taxonDto, taxonDto);
    assertNotEquals(taxonDto, null);
    assertNotEquals(taxonDto, NAAM);
    assertNotEquals(taxonDto, instance);

    instance.setTaxonId(taxonDto.getTaxonId());
    assertEquals(taxonDto, instance);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(LATIJNSENAAM, taxonDto.getLatijnsenaam());
  }

  @Test
  public void testGetNaam() {
    assertEquals(TAXONNAAM, taxonDto.getNaam(TAAL));
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, taxonDto.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(PARENTID, taxonDto.getParentId());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, taxonDto.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TAXONID, taxonDto.getTaxonId());
  }

  @Test
  public void testGetUitgestorven() {
    assertFalse(taxonDto.getUitgestorven());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(VOLGNUMMER, taxonDto.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(TAXONID_HASH, taxonDto.hashCode());
  }

  @Test
  public void testLatijnsenaamComparator() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();

    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);

    Set<TaxonDto> taxa  = new TreeSet<>(new TaxonDto.LatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxonDto.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }

  @Test
  public void testNaamComparator1() {
    var groter    = new TaxonDto();
    var kleiner   = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TAXONNAAM_GR);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TAXONNAAM_KL);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.addNaam(taxonnaam);

    Set<TaxonDto> taxa  = new TreeSet<>(new TaxonDto.NaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getNaam(TAAL), tabel[0].getNaam(TAAL));
    assertEquals(taxonDto.getNaam(TAAL), tabel[1].getNaam(TAAL));
    assertEquals(groter.getNaam(TAAL), tabel[2].getNaam(TAAL));
  }

  @Test
  public void testNaamComparator2() {
    var groter    = new TaxonDto();
    var kleiner   = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.addNaam(taxonnaam);

    Set<TaxonDto> taxa  = new TreeSet<>(new TaxonDto.NaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxonDto.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }

  @Test
  public void testSetLatijnsenaam() {
    var instance  = new TaxonDto();
    assertNotEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    instance.setLatijnsenaam(LATIJNSENAAM);

    assertEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals(LATIJNSENAAM, instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new TaxonDto();
    assertNotEquals(OPMERKING, instance.getOpmerking());
    instance.setOpmerking(OPMERKING);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertEquals(OPMERKING, instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentId() {
    var instance  = new TaxonDto();
    assertNotEquals(PARENTID, instance.getParentId());
    instance.setParentId(PARENTID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertEquals(PARENTID, instance.getParentId());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRang() {
    var instance  = new TaxonDto();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId1() {
    var instance  = new TaxonDto();
    assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getRang());
    assertEquals(TAXONID, instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId2() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaal(TAAL);
    instance.addNaam(taxonnaam);

    assertNull(instance.getTaxonId());
    assertNull(instance.getTaxonnaam(TAAL).getTaxonId());

    instance.setTaxonId(TAXONID);
    assertEquals(TAXONID, instance.getTaxonId());
    assertEquals(TAXONID, instance.getTaxonnaam(TAAL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen1() {
    var                       instance    = new TaxonDto();
    var                       taxonnaam1  = new TaxonnaamDto();
    var                       taxonnaam2  = new TaxonnaamDto();
    var                       taxonnaam3  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen  = new HashMap<>();

    instance.setTaxonId(TAXONID);
    taxonnaam1.setNaam(TAXONNAAM);
    taxonnaam1.setTaxonId(instance.getTaxonId());
    taxonnaam1.setTaal(TAAL);
    instance.addNaam(taxonnaam1);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(TAAL));
    assertFalse(instance.hasTaxonnaam(TAAL_GR));
    assertFalse(instance.hasTaxonnaam(TAAL_KL));

    taxonnaam2.setNaam(TAXONNAAM_GR);
    taxonnaam2.setTaal(TAAL_GR);
    taxonnaam3.setNaam(TAXONNAAM_KL);
    taxonnaam3.setTaal(TAAL_KL);
    taxonnamen.put(taxonnaam2.getTaal(), taxonnaam2);
    taxonnamen.put(taxonnaam3.getTaal(), taxonnaam3);
    instance.setTaxonnamen(taxonnamen);

    assertEquals(2, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(TAAL));
    assertTrue(instance.hasTaxonnaam(TAAL_GR));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TAAL_GR).getTaxonId());
    assertTrue(instance.hasTaxonnaam(TAAL_KL));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TAAL_KL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen2() {
    var                 instance    = new TaxonDto();
    var                 taxonnaam1  = new TaxonnaamDto();
    var                 taxonnaam2  = new TaxonnaamDto();
    var                 taxonnaam3  = new TaxonnaamDto();
    List<TaxonnaamDto>  taxonnamen  = new ArrayList<>();

    instance.setTaxonId(TAXONID);
    taxonnaam1.setNaam(TAXONNAAM);
    taxonnaam1.setTaxonId(instance.getTaxonId());
    taxonnaam1.setTaal(TAAL);
    instance.addNaam(taxonnaam1);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(TAAL));
    assertFalse(instance.hasTaxonnaam(TAAL_GR));
    assertFalse(instance.hasTaxonnaam(TAAL_KL));

    taxonnaam2.setNaam(TAXONNAAM_GR);
    taxonnaam2.setTaal(TAAL_GR);
    taxonnaam3.setNaam(TAXONNAAM_KL);
    taxonnaam3.setTaal(TAAL_KL);
    taxonnamen.add(taxonnaam2);
    taxonnamen.add(taxonnaam3);
    instance.setTaxonnamen(taxonnamen);

    assertEquals(2, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(TAAL));
    assertTrue(instance.hasTaxonnaam(TAAL_GR));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TAAL_GR).getTaxonId());
    assertTrue(instance.hasTaxonnaam(TAAL_KL));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TAAL_KL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen3() {
    var                 instance    = new TaxonDto();
    var                 taxonnaam   = new TaxonnaamDto();
    List<TaxonnaamDto>  taxonnamen  = new ArrayList<>();

    instance.setTaxonId(TAXONID);
    taxonnaam.setNaam(TAXONNAAM);
    taxonnaam.setTaxonId(instance.getTaxonId());
    taxonnaam.setTaal(TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(TAAL));
    assertFalse(instance.hasTaxonnaam(TAAL_GR));
    assertFalse(instance.hasTaxonnaam(TAAL_KL));

    instance.setTaxonnamen(taxonnamen);

    assertEquals(0, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(TAAL));
  }

  @Test
  public void testSetUitgestorven() {
    var instance  = new TaxonDto();
    assertFalse(instance.isUitgestorven());

    instance.setUitgestorven(true);
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertTrue(instance.isUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());

    instance.setUitgestorven(false);
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetVolgnummer() {
    var instance  = new TaxonDto();
    assertNotEquals(VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(VOLGNUMMER);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(VOLGNUMMER, instance.getVolgnummer());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator1() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();

    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setVolgnummer(taxonDto.getVolgnummer() - 1);

    Set<TaxonDto> taxa  =
        new TreeSet<>(new TaxonDto.VolgnummerLatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getVolgnummer(), tabel[0].getVolgnummer());
    assertEquals(taxonDto.getVolgnummer(), tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(), tabel[2].getVolgnummer());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator2() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();

    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer());
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.setVolgnummer(taxonDto.getVolgnummer());

    Set<TaxonDto> taxa  =
        new TreeSet<>(new TaxonDto.VolgnummerLatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxonDto.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }

  @Test
  public void testVolgnummerNaamComparator1() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NAAM);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.setVolgnummer(taxonDto.getVolgnummer() - 1);
    kleiner.addNaam(taxonnaam);

    Set<TaxonDto> taxa  =
        new TreeSet<>(new TaxonDto.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getVolgnummer(), tabel[0].getVolgnummer());
    assertEquals(taxonDto.getVolgnummer(), tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(), tabel[2].getVolgnummer());
  }

  @Test
  public void testVolgnummerNaamComparator2() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TAXONNAAM_GR);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TAXONNAAM_KL);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.setVolgnummer(taxonDto.getVolgnummer());
    kleiner.addNaam(taxonnaam);

    Set<TaxonDto> taxa  =
        new TreeSet<>(new TaxonDto.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getNaam(TAAL), tabel[0].getNaam(TAAL));
    assertEquals(taxonDto.getNaam(TAAL), tabel[1].getNaam(TAAL));
    assertEquals(groter.getNaam(TAAL), tabel[2].getNaam(TAAL));
  }

  @Test
  public void testVolgnummerNaamComparator3() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NAAM);
    taxonnaam.setTaal(TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.setVolgnummer(taxonDto.getVolgnummer() - 1);
    kleiner.addNaam(taxonnaam);

    Set<TaxonDto> taxa  =
        new TreeSet<>(new TaxonDto.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxonDto);
    taxa.add(kleiner);

    var tabel = new TaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxonDto.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }
}
