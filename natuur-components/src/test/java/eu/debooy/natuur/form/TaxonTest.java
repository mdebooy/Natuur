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

import eu.debooy.doosutils.DoosConstants;
import static eu.debooy.doosutils.DoosConstants.ONWAAR;
import static eu.debooy.doosutils.DoosConstants.WAAR;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM_GR;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM_KL;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.NAAM_GR;
import static eu.debooy.natuur.TestConstants.NAAM_KL;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.PARENTLATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.PARENTNAAM;
import static eu.debooy.natuur.TestConstants.PARENTNAAM_GR;
import static eu.debooy.natuur.TestConstants.PARENTNAAM_KL;
import static eu.debooy.natuur.TestConstants.PARENTVOLGNUMMER;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANGNAAM;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONID_HASH;
import static eu.debooy.natuur.TestConstants.VOLGNUMMER;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.TaxonDto;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
  public void testAlfabetischeComparator1() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setNaam(taxon.getNaam());
    groter.setParentNaam(PARENTNAAM_GR);
    kleiner.setNaam(taxon.getNaam());
    kleiner.setParentNaam(PARENTNAAM_KL);

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.AlfabetischeComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getParentNaam(), tabel[0].getParentNaam());
    assertEquals(taxon.getParentNaam(), tabel[1].getParentNaam());
    assertEquals(groter.getParentNaam(), tabel[2].getParentNaam());
  }

  @Test
  public void testAlfabetischeComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setNaam(NAAM_GR);
    groter.setParentNaam(taxon.getParentNaam());
    kleiner.setNaam(NAAM_KL);
    kleiner.setParentNaam(taxon.getParentNaam());

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.AlfabetischeComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(taxon.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Taxon(taxon);
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setTaxonId(taxon.getTaxonId() + 1);
    kleiner.setTaxonId(taxon.getTaxonId() - 1);

    assertTrue(taxon.compareTo(groter) < 0);
    assertEquals(0, taxon.compareTo(gelijk));
    assertTrue(taxon.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Taxon();

    assertEquals(taxon, taxon);
    assertNotEquals(taxon, null);
    assertNotEquals(taxon, NAAM);
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
  public void testInit1() {
    var instance  = new Taxon();

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testInit2() {
    var instance  = new Taxon(taxon);

    assertEquals(taxon.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(taxon.getNaam(), instance.getNaam());
    assertEquals(taxon.getOpmerking(), instance.getOpmerking());
    assertEquals(taxon.getParentId(), instance.getParentId());
    assertEquals(taxon.getParentLatijnsenaam(),
                 instance.getParentLatijnsenaam());
    assertEquals(taxon.getParentNaam(), instance.getParentNaam());
    assertEquals(taxon.getParentVolgnummer(), instance.getParentVolgnummer());
    assertEquals(taxon.getRang(), instance.getRang());
    assertEquals(taxon.getRangnaam(), instance.getRangnaam());
    assertEquals(taxon.getTaxonId(), instance.getTaxonId());
    assertEquals(taxon.getUitgestorven(), instance.getUitgestorven());
    assertEquals(taxon.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testInit3() {
    var instance  = new Taxon(taxonDto);

    assertEquals(taxonDto.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(taxonDto.getLatijnsenaam(), instance.getNaam());
    assertEquals(taxonDto.getOpmerking(), instance.getOpmerking());
    assertEquals(taxonDto.getParentId(), instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(taxonDto.getRang(), instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(taxonDto.getTaxonId(), instance.getTaxonId());
    assertEquals(taxonDto.getUitgestorven(),
                 instance.getUitgestorven().equals(DoosConstants.WAAR));
    assertEquals(taxonDto.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testInit4() {
    var instance  = new Taxon(taxonDto, TAAL);

    assertEquals(taxonDto.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(taxonDto.getNaam(TAAL), instance.getNaam());
    assertEquals(taxonDto.getOpmerking(), instance.getOpmerking());
    assertEquals(taxonDto.getParentId(), instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(taxonDto.getRang(), instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(taxonDto.getTaxonId(), instance.getTaxonId());
    assertEquals(taxonDto.getUitgestorven(),
                 instance.getUitgestorven().equals(DoosConstants.WAAR));
    assertEquals(taxonDto.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testInit5() {
    var instance  = new Taxon(taxonDto, TAAL);

    instance.setUitgestorven(!taxonDto.isUitgestorven());

    assertEquals(taxonDto.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(taxonDto.getNaam(TAAL), instance.getNaam());
    assertEquals(taxonDto.getOpmerking(), instance.getOpmerking());
    assertEquals(taxonDto.getParentId(), instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(taxonDto.getRang(), instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(taxonDto.getTaxonId(), instance.getTaxonId());
    assertNotEquals(taxonDto.getUitgestorven(),
                    instance.getUitgestorven());
    assertEquals(taxonDto.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testLatijnsenaamComparator() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.LatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxon.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }

  @Test
  public void testLijstComparator1() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setParentNaam(taxon.getParentNaam());
    groter.setParentVolgnummer(taxon.getParentVolgnummer() + 1);
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setParentNaam(taxon.getParentNaam());
    kleiner.setParentVolgnummer(taxon.getParentVolgnummer() - 1);
    kleiner.setNaam(taxon.getNaam());
    kleiner.setVolgnummer(taxon.getVolgnummer());

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.LijstComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getParentVolgnummer(), tabel[0].getParentVolgnummer());
    assertEquals(taxon.getParentVolgnummer(), tabel[1].getParentVolgnummer());
    assertEquals(groter.getParentVolgnummer(), tabel[2].getParentVolgnummer());
  }

  @Test
  public void testLijstComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setParentNaam(PARENTNAAM_GR);
    groter.setParentVolgnummer(taxon.getParentVolgnummer());
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setParentNaam(PARENTNAAM_KL);
    kleiner.setParentVolgnummer(taxon.getParentVolgnummer());
    kleiner.setNaam(taxon.getNaam());
    kleiner.setVolgnummer(taxon.getVolgnummer());

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.LijstComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getParentNaam(), tabel[0].getParentNaam());
    assertEquals(taxon.getParentNaam(), tabel[1].getParentNaam());
    assertEquals(groter.getParentNaam(), tabel[2].getParentNaam());
  }

  @Test
  public void testLijstComparator3() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setParentNaam(taxon.getParentNaam());
    groter.setParentVolgnummer(taxon.getParentVolgnummer());
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer() + 1);
    kleiner.setParentNaam(taxon.getParentNaam());
    kleiner.setParentVolgnummer(taxon.getParentVolgnummer());
    kleiner.setNaam(taxon.getNaam());
    kleiner.setVolgnummer(taxon.getVolgnummer() - 1);

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.LijstComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getVolgnummer(), tabel[0].getVolgnummer());
    assertEquals(taxon.getVolgnummer(), tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(), tabel[2].getVolgnummer());
  }

  @Test
  public void testLijstComparator4() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setParentNaam(taxon.getParentNaam());
    groter.setParentVolgnummer(taxon.getParentVolgnummer());
    groter.setNaam(NAAM_GR);
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setParentNaam(taxon.getParentNaam());
    kleiner.setParentVolgnummer(taxon.getParentVolgnummer());
    kleiner.setNaam(NAAM_KL);
    kleiner.setVolgnummer(taxon.getVolgnummer());

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.LijstComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(taxon.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testNaamComparator1() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(taxon.getLatijnsenaam());
    groter.setNaam(NAAM_GR);
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setNaam(NAAM_KL);

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.NaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(taxon.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testNaamComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    groter.setNaam(taxon.getNaam());
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);
    kleiner.setNaam(taxon.getNaam());

    Set<Taxon>  taxa  = new TreeSet<>(new Taxon.NaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxon.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }

  @Test
  public void testPersist() {
    var parameter = new TaxonDto();
    var instance  = new Taxon(taxon);

    instance.persist(parameter);

    assertEquals(instance.getLatijnsenaam(), parameter.getLatijnsenaam());
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getParentId(), parameter.getParentId());
    assertEquals(instance.getRang(), parameter.getRang());
    assertEquals(instance.getTaxonId(), parameter.getTaxonId());
    assertEquals(instance.getVolgnummer(), parameter.getVolgnummer());
    assertFalse(parameter.isUitgestorven());

    parameter.setUitgestorven(true);
    instance.persist(parameter);

    assertEquals(instance.getLatijnsenaam(), parameter.getLatijnsenaam());
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getParentId(), parameter.getParentId());
    assertEquals(instance.getRang(), parameter.getRang());
    assertEquals(instance.getTaxonId(), parameter.getTaxonId());
    assertEquals(instance.getVolgnummer(), parameter.getVolgnummer());
    assertFalse(parameter.isUitgestorven());
  }

  @Test
  public void testSetLatijnsenaam() {
    var instance  = new Taxon();
    assertNotEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    instance.setLatijnsenaam(LATIJNSENAAM);

    assertEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals(LATIJNSENAAM, instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetNaam() {
    var instance  = new Taxon();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertNull(instance.getLatijnsenaam());
    assertEquals(NAAM, instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());

    instance.setLatijnsenaam(LATIJNSENAAM);
    assertEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals(NAAM, instance.getNaam());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new Taxon();
    assertNotEquals(OPMERKING, instance.getOpmerking());
    instance.setOpmerking(OPMERKING);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertEquals(OPMERKING, instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentId() {
    var instance  = new Taxon();
    assertNotEquals(PARENTID, instance.getParentId());
    instance.setParentId(PARENTID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertEquals(PARENTID, instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentLatijnsenaam() {
    var instance  = new Taxon();
    assertNotEquals(PARENTLATIJNSENAAM,
                           instance.getParentLatijnsenaam());
    instance.setParentLatijnsenaam(PARENTLATIJNSENAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(PARENTLATIJNSENAAM, instance.getParentLatijnsenaam());
    assertEquals(PARENTLATIJNSENAAM, instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentNaam() {
    var instance  = new Taxon();
    assertNotEquals(PARENTNAAM, instance.getParentNaam());
    instance.setParentNaam(PARENTNAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertEquals(PARENTNAAM, instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());

    instance.setParentLatijnsenaam(PARENTLATIJNSENAAM);
    assertEquals(PARENTLATIJNSENAAM, instance.getParentLatijnsenaam());
    assertEquals(PARENTNAAM, instance.getParentNaam());
  }

  @Test
  public void testSetParentVolgnummer() {
    var instance  = new Taxon();
    assertNotEquals(PARENTVOLGNUMMER, instance.getParentVolgnummer());
    instance.setParentVolgnummer(PARENTVOLGNUMMER);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertEquals(PARENTVOLGNUMMER, instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRang() {
    var instance  = new Taxon();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(RANG, instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRangnaam() {
    var instance  = new Taxon();
    assertNotEquals(RANGNAAM, instance.getRangnaam());
    instance.setRangnaam(RANGNAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertEquals(RANGNAAM, instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new Taxon();
    assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(TAXONID, instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven1() {
    var instance  = new Taxon();
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());

    instance.setUitgestorven(WAAR);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertTrue(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven2() {
    var instance  = new Taxon();
    instance.setUitgestorven(WAAR);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());

    instance.setUitgestorven(ONWAAR);
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven3() {
    var instance  = new Taxon();
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());

    instance.setUitgestorven(true);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertTrue(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven4() {
    var instance  = new Taxon();
    instance.setUitgestorven(true);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());

    instance.setUitgestorven(false);
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Integer.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetVolgnummer() {
    var instance  = new Taxon();
    assertNotEquals(VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(VOLGNUMMER);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(VOLGNUMMER, instance.getVolgnummer());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator1() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(taxon.getLatijnsenaam());
    groter.setVolgnummer(taxon.getVolgnummer() + 1);
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setVolgnummer(taxon.getVolgnummer() - 1);

    Set<Taxon>  taxa  =
        new TreeSet<>(new Taxon.VolgnummerLatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getVolgnummer(), tabel[0].getVolgnummer());
    assertEquals(taxon.getVolgnummer(), tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(), tabel[2].getVolgnummer());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);
    kleiner.setVolgnummer(taxon.getVolgnummer());

    Set<Taxon>  taxa  =
        new TreeSet<>(new Taxon.VolgnummerLatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxon.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }

  @Test
  public void testVolgnummerNaamComparator1() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(taxon.getLatijnsenaam());
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer() + 1);
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setNaam(taxon.getNaam());
    kleiner.setVolgnummer(taxon.getVolgnummer() - 1);

    Set<Taxon>  taxa  =
        new TreeSet<>(new Taxon.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getVolgnummer(), tabel[0].getVolgnummer());
    assertEquals(taxon.getVolgnummer(), tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(), tabel[2].getVolgnummer());
  }

  @Test
  public void testVolgnummerNaamComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(taxon.getLatijnsenaam());
    groter.setNaam(NAAM_GR);
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setNaam(NAAM_KL);
    kleiner.setVolgnummer(taxon.getVolgnummer());

    Set<Taxon>  taxa  =
        new TreeSet<>(new Taxon.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(taxon.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testVolgnummerNaamComparator3() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(LATIJNSENAAM_GR);
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setLatijnsenaam(LATIJNSENAAM_KL);
    kleiner.setNaam(taxon.getNaam());
    kleiner.setVolgnummer(taxon.getVolgnummer());

    Set<Taxon>  taxa  =
        new TreeSet<>(new Taxon.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);

    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getLatijnsenaam(), tabel[0].getLatijnsenaam());
    assertEquals(taxon.getLatijnsenaam(), tabel[1].getLatijnsenaam());
    assertEquals(groter.getLatijnsenaam(), tabel[2].getLatijnsenaam());
  }
}
