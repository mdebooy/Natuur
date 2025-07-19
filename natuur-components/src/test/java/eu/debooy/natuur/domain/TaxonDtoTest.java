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

import eu.debooy.doosutils.access.JsonBestand;
import eu.debooy.doosutils.errorhandling.exception.IllegalArgumentException;
import eu.debooy.doosutils.exception.BestandException;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.json.simple.JSONObject;
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
    taxonDto  = NatuurTestUtils.getTaxonDto();
  }

  @Test
  public void testAddNaam1() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaxonId(instance.getTaxonId());
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(NatuurTestConstants.TAXONNAAM,
                 instance.getTaxonnaam(NatuurTestConstants.TAAL).getNaam());
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(NatuurTestConstants.TAAL).getTaxonId());
    assertNull(instance.getTaxonnaam(NatuurTestConstants.TAAL_GR).getTaxonId());
  }

  @Test
  public void testAddNaam2() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(NatuurTestConstants.TAXONNAAM,
                 instance.getTaxonnaam(NatuurTestConstants.TAAL).getNaam());
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(NatuurTestConstants.TAAL).getTaxonId());
    assertNull(instance.getTaxonnaam(NatuurTestConstants.TAAL_GR).getTaxonId());
  }

  @Test
  public void testAddNaam3() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
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
    assertNotEquals(taxonDto, NatuurTestConstants.NAAM);
    assertNotEquals(taxonDto, instance);

    instance.setTaxonId(taxonDto.getTaxonId());
    assertEquals(taxonDto, instance);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(NatuurTestConstants.LATIJNSENAAM, taxonDto.getLatijnsenaam());
  }

  @Test
  public void testGetNaam1() {
    assertEquals(NatuurTestConstants.TAXONNAAM, taxonDto.getNaam(NatuurTestConstants.TAAL));
  }

  @Test
  public void testGetNaam2()
      throws java.lang.IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon = NatuurTestUtils.getOndersoortTaxonDto();

    assertEquals(NatuurTestConstants.ONDERSOORTNAAM,
                 taxon.getNaam(NatuurTestConstants.TAAL));
    assertEquals("", taxon.getNaam(NatuurTestConstants.TAAL_GR));
    assertEquals(NatuurTestConstants.ONDERSOORTLATIJNSENAAM, taxon.getLatijnsenaam());
    assertEquals(NatuurTestConstants.ONDERSOORTNAAM_KL,
                 taxon.getNaam(NatuurTestConstants.TAAL_KL));
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(NatuurTestConstants.OPMERKING, taxonDto.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(NatuurTestConstants.PARENTTAXONID, taxonDto.getParentId());
  }

  @Test
  public void testGetRang() {
    assertEquals(NatuurTestConstants.RANG, taxonDto.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, taxonDto.getTaxonId());
  }

  @Test
  public void testGetUitgestorven() {
    assertFalse(taxonDto.getUitgestorven());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(NatuurTestConstants.VOLGNUMMER, taxonDto.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.TAXONID_HASH, taxonDto.hashCode());
  }

  @Test
  public void testInit2a() {
    try {
      var json  = new JsonBestand.Builder()
              .setBestand(NatuurTestConstants.TAXON_JSON)
              .setClassLoader(TaxonDtoTest.class.getClassLoader())
              .build();
      var init  = (JSONObject) json.read();
      var taxon = new TaxonDto(init);

      assertEquals(NatuurTestConstants.LATIJNSENAAM, taxon.getLatijnsenaam());
      assertEquals(NatuurTestConstants.OPMERKING, taxon.getOpmerking());
      assertEquals(NatuurTestConstants.PARENTTAXONID, taxon.getParentId());
      assertEquals(NatuurTestConstants.RANG, taxon.getRang());
      assertEquals(NatuurTestConstants.TAXONID, taxon.getTaxonId());
      assertFalse(taxon.isUitgestorven());
      assertEquals(NatuurTestConstants.VOLGNUMMER, taxon.getVolgnummer());
    } catch (BestandException ex) {
      fail("Er had geen BestandException mogen wezen.");
    }
  }

  @Test
  public void testInit2b() {
    try {
      var json  =
          new JsonBestand.Builder()
                         .setBestand(NatuurTestConstants.LEEG_JSON)
                         .setClassLoader(TaxonDtoTest.class.getClassLoader())
                         .build();
      var init  = (JSONObject) json.read();
      var taxon = new TaxonDto(init);

      assertNull(taxon.getLatijnsenaam());
      assertNull(taxon.getOpmerking());
      assertNull(taxon.getParentId());
      assertNull(taxon.getRang());
      assertNull(taxon.getTaxonId());
      assertFalse(taxon.isUitgestorven());
      assertEquals(Long.valueOf(0), taxon.getVolgnummer());
    } catch (BestandException ex) {
      fail("Er had geen BestandException mogen wezen.");
    }
  }

  @Test
  public void testLatijnsenaamComparator() {
    var groter    = NatuurTestUtils.getTaxonDto();
    var kleiner   = NatuurTestUtils.getTaxonDto();

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);

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
    var groter    = NatuurTestUtils.getTaxonDto();
    var kleiner   = NatuurTestUtils.getTaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM_GR);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM_KL);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
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
    assertEquals(kleiner.getNaam(NatuurTestConstants.TAAL),
                 tabel[0].getNaam(NatuurTestConstants.TAAL));
    assertEquals(taxonDto.getNaam(NatuurTestConstants.TAAL),
                 tabel[1].getNaam(NatuurTestConstants.TAAL));
    assertEquals(groter.getNaam(NatuurTestConstants.TAAL),
                 tabel[2].getNaam(NatuurTestConstants.TAAL));
  }

  @Test
  public void testNaamComparator2() {
    var groter    = NatuurTestUtils.getTaxonDto();
    var kleiner   = NatuurTestUtils.getTaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
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
  public void testNaamComparator3() {
    var groter    = NatuurTestUtils.getTaxonDto();
    var kleiner   = NatuurTestUtils.getTaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM_GR);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM_KL);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.addNaam(taxonnaam);

    var           comparator  = new TaxonDto.NaamComparator();
    comparator.setTaal(NatuurTestConstants.TAAL_GR);
    Set<TaxonDto> taxa        = new TreeSet<>(comparator);
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
    assertNotEquals(NatuurTestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
    instance.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    instance.setRang(NatuurTestConstants.RANG);

    assertEquals(NatuurTestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new TaxonDto();
    assertNotEquals(NatuurTestConstants.OPMERKING, instance.getOpmerking());
    instance.setOpmerking(NatuurTestConstants.OPMERKING);
    instance.setRang(NatuurTestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertEquals(NatuurTestConstants.OPMERKING, instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentId() {
    var instance  = new TaxonDto();
    assertNotEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    instance.setParentId(NatuurTestConstants.PARENTTAXONID);
    instance.setRang(NatuurTestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRang() {
    var instance  = new TaxonDto();
    assertNotEquals(NatuurTestConstants.RANG, instance.getRang());
    instance.setRang(NatuurTestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId1() {
    var instance  = new TaxonDto();
    assertNotEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    instance.setRang(NatuurTestConstants.RANG);
    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId2() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertNull(instance.getTaxonId());
    assertNull(instance.getTaxonnaam(NatuurTestConstants.TAAL).getTaxonId());

    instance.setTaxonId(NatuurTestConstants.TAXONID);
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertEquals(NatuurTestConstants.TAXONID,
                 instance.getTaxonnaam(NatuurTestConstants.TAAL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen1() {
    var                       instance    = new TaxonDto();
    var                       taxonnaam1  = new TaxonnaamDto();
    var                       taxonnaam2  = new TaxonnaamDto();
    var                       taxonnaam3  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen  = new HashMap<>();

    instance.setTaxonId(NatuurTestConstants.TAXONID);
    taxonnaam1.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam1.setTaxonId(instance.getTaxonId());
    taxonnaam1.setTaal(NatuurTestConstants.TAAL);
    instance.addNaam(taxonnaam1);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL));
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL_GR));
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL_KL));

    taxonnaam2.setNaam(NatuurTestConstants.TAXONNAAM_GR);
    taxonnaam2.setTaal(NatuurTestConstants.TAAL_GR);
    taxonnaam3.setNaam(NatuurTestConstants.TAXONNAAM_KL);
    taxonnaam3.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnamen.put(taxonnaam2.getTaal(), taxonnaam2);
    taxonnamen.put(taxonnaam3.getTaal(), taxonnaam3);
    instance.setTaxonnamen(taxonnamen);

    assertEquals(2, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL));
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL_GR));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(NatuurTestConstants.TAAL_GR).getTaxonId());
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL_KL));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(NatuurTestConstants.TAAL_KL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen2() {
    var                 instance    = new TaxonDto();
    var                 taxonnaam1  = new TaxonnaamDto();
    var                 taxonnaam2  = new TaxonnaamDto();
    var                 taxonnaam3  = new TaxonnaamDto();
    List<TaxonnaamDto>  taxonnamen  = new ArrayList<>();

    instance.setTaxonId(NatuurTestConstants.TAXONID);
    taxonnaam1.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam1.setTaxonId(instance.getTaxonId());
    taxonnaam1.setTaal(NatuurTestConstants.TAAL);
    instance.addNaam(taxonnaam1);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL));
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL_GR));
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL_KL));

    taxonnaam2.setNaam(NatuurTestConstants.TAXONNAAM_GR);
    taxonnaam2.setTaal(NatuurTestConstants.TAAL_GR);
    taxonnaam3.setNaam(NatuurTestConstants.TAXONNAAM_KL);
    taxonnaam3.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnamen.add(taxonnaam2);
    taxonnamen.add(taxonnaam3);
    instance.setTaxonnamen(taxonnamen);

    assertEquals(2, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL));
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL_GR));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(NatuurTestConstants.TAAL_GR).getTaxonId());
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL_KL));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(NatuurTestConstants.TAAL_KL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen3() {
    var                 instance    = new TaxonDto();
    var                 taxonnaam   = new TaxonnaamDto();
    List<TaxonnaamDto>  taxonnamen  = new ArrayList<>();

    instance.setTaxonId(NatuurTestConstants.TAXONID);
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnaam.setTaxonId(instance.getTaxonId());
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(NatuurTestConstants.TAAL));
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL_GR));
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL_KL));

    instance.setTaxonnamen(taxonnamen);

    assertEquals(0, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(NatuurTestConstants.TAAL));
  }

  @Test
  public void testSetUitgestorven() {
    var instance  = new TaxonDto();
    assertFalse(instance.isUitgestorven());

    instance.setRang(NatuurTestConstants.RANG);
    instance.setStatus(NatuurConstants.STAT_UITGESTORVEN.toUpperCase());
    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertEquals(NatuurConstants.STAT_UITGESTORVEN, instance.getStatus());
    assertNull(instance.getTaxonId());
    assertTrue(instance.isUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());

    instance.setStatus(NatuurTestConstants.STATUS);
    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetVolgnummer() {
    var instance  = new TaxonDto();
    assertNotEquals(NatuurTestConstants.VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(NatuurTestConstants.VOLGNUMMER);
    instance.setRang(NatuurTestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertEquals("", instance.getNaam(NatuurTestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(NatuurTestConstants.VOLGNUMMER, instance.getVolgnummer());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator1() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();

    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setRang(taxonDto.getRang());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setRang(taxonDto.getRang());
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

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
    kleiner.setRang(taxonDto.getRang());
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
    var groter    = new TaxonDto();
    var kleiner   = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.NAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());

    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setRang(taxonDto.getRang());
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
    var groter    = new TaxonDto();
    var kleiner   = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM_GR);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(NatuurTestConstants.TAXONNAAM_KL);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(taxonDto.getLatijnsenaam());
    kleiner.setRang(taxonDto.getRang());
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
    assertEquals(kleiner.getNaam(NatuurTestConstants.TAAL),
                 tabel[0].getNaam(NatuurTestConstants.TAAL));
    assertEquals(taxonDto.getNaam(NatuurTestConstants.TAAL),
                 tabel[1].getNaam(NatuurTestConstants.TAAL));
    assertEquals(groter.getNaam(NatuurTestConstants.TAAL),
                 tabel[2].getNaam(NatuurTestConstants.TAAL));
  }

  @Test
  public void testVolgnummerNaamComparator3() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.NAAM);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
    kleiner.setRang(taxonDto.getRang());
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

  @Test
  public void testVolgnummerNaamComparator4() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(NatuurTestConstants.NAAM_GR);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(NatuurTestConstants.NAAM_KL);
    taxonnaam.setTaal(NatuurTestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
    kleiner.setRang(taxonDto.getRang());
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.setVolgnummer(taxonDto.getVolgnummer() - 1);
    kleiner.addNaam(taxonnaam);

    var           comparator  = new TaxonDto.VolgnummerNaamComparator();
    comparator.setTaal(NatuurTestConstants.TAAL_GR);
    Set<TaxonDto> taxa        = new TreeSet<>(comparator);
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
