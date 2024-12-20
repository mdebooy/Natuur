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
import eu.debooy.natuur.NatuurTestUtils;
import eu.debooy.natuur.TestConstants;
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
    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaxonId(instance.getTaxonId());
    taxonnaam.setTaal(TestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(TestConstants.TAXONNAAM,
                 instance.getTaxonnaam(TestConstants.TAAL).getNaam());
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TestConstants.TAAL).getTaxonId());
    assertNull(instance.getTaxonnaam(TestConstants.TAAL_GR).getTaxonId());
  }

  @Test
  public void testAddNaam2() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(TestConstants.TAXONNAAM,
                 instance.getTaxonnaam(TestConstants.TAAL).getNaam());
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TestConstants.TAAL).getTaxonId());
    assertNull(instance.getTaxonnaam(TestConstants.TAAL_GR).getTaxonId());
  }

  @Test
  public void testAddNaam3() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    instance.setTaxonId(10L);
    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
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
    assertNotEquals(taxonDto, TestConstants.NAAM);
    assertNotEquals(taxonDto, instance);

    instance.setTaxonId(taxonDto.getTaxonId());
    assertEquals(taxonDto, instance);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(TestConstants.LATIJNSENAAM, taxonDto.getLatijnsenaam());
  }

  @Test
  public void testGetNaam1() {
    assertEquals(TestConstants.TAXONNAAM, taxonDto.getNaam(TestConstants.TAAL));
  }

  @Test
  public void testGetNaam2()
      throws java.lang.IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon = NatuurTestUtils.getOndersoortTaxonDto();

    assertEquals(TestConstants.ONDERSOORTNAAM,
                 taxon.getNaam(TestConstants.TAAL));
    assertEquals(TestConstants.ONDERSOORTLATIJNSENAAM,
                 taxon.getNaam(TestConstants.TAAL_GR));
    assertEquals(TestConstants.ONDERSOORTLATIJNSENAAM, taxon.getLatijnsenaam());
    assertEquals(TestConstants.ONDERSOORTNAAM_KL,
                 taxon.getNaam(TestConstants.TAAL_KL));
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(TestConstants.OPMERKING, taxonDto.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(TestConstants.PARENTTAXONID, taxonDto.getParentId());
  }

  @Test
  public void testGetRang() {
    assertEquals(TestConstants.RANG, taxonDto.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TestConstants.TAXONID, taxonDto.getTaxonId());
  }

  @Test
  public void testGetUitgestorven() {
    assertFalse(taxonDto.getUitgestorven());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(TestConstants.VOLGNUMMER, taxonDto.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.TAXONID_HASH, taxonDto.hashCode());
  }

  @Test
  public void testInit2a() {
    try {
      var json  = new JsonBestand.Builder()
              .setBestand(TestConstants.TAXON_JSON)
              .setClassLoader(TaxonDtoTest.class.getClassLoader())
              .build();
      var init  = (JSONObject) json.read();
      var taxon = new TaxonDto(init);

      assertEquals(TestConstants.LATIJNSENAAM, taxon.getLatijnsenaam());
      assertEquals(TestConstants.OPMERKING, taxon.getOpmerking());
      assertEquals(TestConstants.PARENTTAXONID, taxon.getParentId());
      assertEquals(TestConstants.RANG, taxon.getRang());
      assertEquals(TestConstants.TAXONID, taxon.getTaxonId());
      assertFalse(taxon.isUitgestorven());
      assertEquals(TestConstants.VOLGNUMMER, taxon.getVolgnummer());
    } catch (BestandException ex) {
      fail("Er had geen BestandException mogen wezen.");
    }
  }

  @Test
  public void testInit2b() {
    try {
      var json  =
          new JsonBestand.Builder()
                         .setBestand(TestConstants.LEEG_JSON)
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

    groter.setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    kleiner.setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);

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

    taxonnaam.setNaam(TestConstants.TAXONNAAM_GR);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TestConstants.TAXONNAAM_KL);
    taxonnaam.setTaal(TestConstants.TAAL);
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
    assertEquals(kleiner.getNaam(TestConstants.TAAL),
                 tabel[0].getNaam(TestConstants.TAAL));
    assertEquals(taxonDto.getNaam(TestConstants.TAAL),
                 tabel[1].getNaam(TestConstants.TAAL));
    assertEquals(groter.getNaam(TestConstants.TAAL),
                 tabel[2].getNaam(TestConstants.TAAL));
  }

  @Test
  public void testNaamComparator2() {
    var groter    = NatuurTestUtils.getTaxonDto();
    var kleiner   = NatuurTestUtils.getTaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);
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

    taxonnaam.setNaam(TestConstants.TAXONNAAM_GR);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TestConstants.TAXONNAAM_KL);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.addNaam(taxonnaam);

    var           comparator  = new TaxonDto.NaamComparator();
    comparator.setTaal(TestConstants.TAAL_GR);
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
    assertNotEquals(TestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
    instance.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    instance.setRang(TestConstants.RANG);

    assertEquals(TestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals(TestConstants.LATIJNSENAAM,
                 instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new TaxonDto();
    assertNotEquals(TestConstants.OPMERKING, instance.getOpmerking());
    instance.setOpmerking(TestConstants.OPMERKING);
    instance.setRang(TestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertEquals(TestConstants.OPMERKING, instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentId() {
    var instance  = new TaxonDto();
    assertNotEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    instance.setParentId(TestConstants.PARENTTAXONID);
    instance.setRang(TestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertEquals(TestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRang() {
    var instance  = new TaxonDto();
    assertNotEquals(TestConstants.RANG, instance.getRang());
    instance.setRang(TestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId1() {
    var instance  = new TaxonDto();
    assertNotEquals(TestConstants.TAXONID, instance.getTaxonId());
    instance.setRang(TestConstants.RANG);
    instance.setTaxonId(TestConstants.TAXONID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId2() {
    var instance  = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertNull(instance.getTaxonId());
    assertNull(instance.getTaxonnaam(TestConstants.TAAL).getTaxonId());

    instance.setTaxonId(TestConstants.TAXONID);
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
    assertEquals(TestConstants.TAXONID,
                 instance.getTaxonnaam(TestConstants.TAAL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen1() {
    var                       instance    = new TaxonDto();
    var                       taxonnaam1  = new TaxonnaamDto();
    var                       taxonnaam2  = new TaxonnaamDto();
    var                       taxonnaam3  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen  = new HashMap<>();

    instance.setTaxonId(TestConstants.TAXONID);
    taxonnaam1.setNaam(TestConstants.TAXONNAAM);
    taxonnaam1.setTaxonId(instance.getTaxonId());
    taxonnaam1.setTaal(TestConstants.TAAL);
    instance.addNaam(taxonnaam1);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL));
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL_GR));
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL_KL));

    taxonnaam2.setNaam(TestConstants.TAXONNAAM_GR);
    taxonnaam2.setTaal(TestConstants.TAAL_GR);
    taxonnaam3.setNaam(TestConstants.TAXONNAAM_KL);
    taxonnaam3.setTaal(TestConstants.TAAL_KL);
    taxonnamen.put(taxonnaam2.getTaal(), taxonnaam2);
    taxonnamen.put(taxonnaam3.getTaal(), taxonnaam3);
    instance.setTaxonnamen(taxonnamen);

    assertEquals(2, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL));
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL_GR));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TestConstants.TAAL_GR).getTaxonId());
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL_KL));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TestConstants.TAAL_KL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen2() {
    var                 instance    = new TaxonDto();
    var                 taxonnaam1  = new TaxonnaamDto();
    var                 taxonnaam2  = new TaxonnaamDto();
    var                 taxonnaam3  = new TaxonnaamDto();
    List<TaxonnaamDto>  taxonnamen  = new ArrayList<>();

    instance.setTaxonId(TestConstants.TAXONID);
    taxonnaam1.setNaam(TestConstants.TAXONNAAM);
    taxonnaam1.setTaxonId(instance.getTaxonId());
    taxonnaam1.setTaal(TestConstants.TAAL);
    instance.addNaam(taxonnaam1);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL));
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL_GR));
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL_KL));

    taxonnaam2.setNaam(TestConstants.TAXONNAAM_GR);
    taxonnaam2.setTaal(TestConstants.TAAL_GR);
    taxonnaam3.setNaam(TestConstants.TAXONNAAM_KL);
    taxonnaam3.setTaal(TestConstants.TAAL_KL);
    taxonnamen.add(taxonnaam2);
    taxonnamen.add(taxonnaam3);
    instance.setTaxonnamen(taxonnamen);

    assertEquals(2, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL));
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL_GR));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TestConstants.TAAL_GR).getTaxonId());
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL_KL));
    assertEquals(instance.getTaxonId(),
                 instance.getTaxonnaam(TestConstants.TAAL_KL).getTaxonId());
  }

  @Test
  public void testSetTaxonnamen3() {
    var                 instance    = new TaxonDto();
    var                 taxonnaam   = new TaxonnaamDto();
    List<TaxonnaamDto>  taxonnamen  = new ArrayList<>();

    instance.setTaxonId(TestConstants.TAXONID);
    taxonnaam.setNaam(TestConstants.TAXONNAAM);
    taxonnaam.setTaxonId(instance.getTaxonId());
    taxonnaam.setTaal(TestConstants.TAAL);
    instance.addNaam(taxonnaam);

    assertEquals(1, instance.getTaxonnamen().size());
    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
    assertTrue(instance.hasTaxonnaam(TestConstants.TAAL));
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL_GR));
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL_KL));

    instance.setTaxonnamen(taxonnamen);

    assertEquals(0, instance.getTaxonnamen().size());
    assertFalse(instance.hasTaxonnaam(TestConstants.TAAL));
  }

  @Test
  public void testSetUitgestorven() {
    var instance  = new TaxonDto();
    assertFalse(instance.isUitgestorven());

    instance.setUitgestorven(true);
    instance.setRang(TestConstants.RANG);
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertTrue(instance.isUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());

    instance.setUitgestorven(false);
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetVolgnummer() {
    var instance  = new TaxonDto();
    assertNotEquals(TestConstants.VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(TestConstants.VOLGNUMMER);
    instance.setRang(TestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam(TestConstants.TAAL));
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(TestConstants.RANG, instance.getRang());
    assertNull(instance.getTaxonId());
    assertFalse(instance.isUitgestorven());
    assertEquals(TestConstants.VOLGNUMMER, instance.getVolgnummer());
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

    groter.setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer());
    kleiner.setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);
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

    taxonnaam.setNaam(TestConstants.NAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
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

    taxonnaam.setNaam(TestConstants.TAXONNAAM_GR);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    groter.setLatijnsenaam(taxonDto.getLatijnsenaam());
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer());
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TestConstants.TAXONNAAM_KL);
    taxonnaam.setTaal(TestConstants.TAAL);
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
    assertEquals(kleiner.getNaam(TestConstants.TAAL),
                 tabel[0].getNaam(TestConstants.TAAL));
    assertEquals(taxonDto.getNaam(TestConstants.TAAL),
                 tabel[1].getNaam(TestConstants.TAAL));
    assertEquals(groter.getNaam(TestConstants.TAAL),
                 tabel[2].getNaam(TestConstants.TAAL));
  }

  @Test
  public void testVolgnummerNaamComparator3() {
    var groter  = new TaxonDto();
    var kleiner = new TaxonDto();
    var taxonnaam = new TaxonnaamDto();

    taxonnaam.setNaam(TestConstants.NAAM);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());

    groter.setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    kleiner.setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);
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

    taxonnaam.setNaam(TestConstants.NAAM_GR);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());

    groter.setLatijnsenaam(TestConstants.LATIJNSENAAM_GR);
    groter.setRang(taxonDto.getRang());
    groter.setTaxonId(taxonDto.getTaxonId());
    groter.setVolgnummer(taxonDto.getVolgnummer() + 1);
    groter.addNaam(taxonnaam);

    taxonnaam = new TaxonnaamDto();
    taxonnaam.setNaam(TestConstants.NAAM_KL);
    taxonnaam.setTaal(TestConstants.TAAL);
    taxonnaam.setTaxonId(taxonDto.getTaxonId());
    kleiner.setLatijnsenaam(TestConstants.LATIJNSENAAM_KL);
    kleiner.setRang(taxonDto.getRang());
    kleiner.setTaxonId(taxonDto.getTaxonId());
    kleiner.setVolgnummer(taxonDto.getVolgnummer() - 1);
    kleiner.addNaam(taxonnaam);

    var           comparator  = new TaxonDto.VolgnummerNaamComparator();
    comparator.setTaal(TestConstants.TAAL_GR);
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
