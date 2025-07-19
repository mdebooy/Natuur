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

import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
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
    taxon     = NatuurTestUtils.getTaxon();
    taxonDto  = NatuurTestUtils.getTaxonDto();
  }

  @Test
  public void testAlfabetischeComparator1() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setNaam(taxon.getNaam());
    groter.setParentNaam(NatuurTestConstants.PARENTNAAM_GR);
    kleiner.setNaam(taxon.getNaam());
    kleiner.setParentNaam(NatuurTestConstants.PARENTNAAM_KL);

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

    groter.setNaam(NatuurTestConstants.NAAM_GR);
    groter.setParentNaam(taxon.getParentNaam());
    kleiner.setNaam(NatuurTestConstants.NAAM_KL);
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
    assertNotEquals(taxon, NatuurTestConstants.NAAM);
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
    assertEquals(NatuurTestConstants.LATIJNSENAAM, taxon.getLatijnsenaam());
  }

  @Test
  public void testGetNaam() {
    assertEquals(NatuurTestConstants.NAAM, taxon.getNaam());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(NatuurTestConstants.OPMERKING, taxon.getOpmerking());
  }

  @Test
  public void testGetParentId() {
    assertEquals(NatuurTestConstants.PARENTTAXONID, taxon.getParentId());
  }

  @Test
  public void testGetParentLatijnsenaam() {
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 taxon.getParentLatijnsenaam());
  }

  @Test
  public void testGetParentNaam() {
    assertEquals(NatuurTestConstants.PARENTNAAM, taxon.getParentNaam());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(NatuurTestConstants.PARENTRANG, taxon.getParentRang());
  }

  @Test
  public void testGetParentVolgnummer() {
    assertEquals(NatuurTestConstants.PARENTVOLGNUMMER, taxon.getParentVolgnummer());
  }

  @Test
  public void testGetRang() {
    assertEquals(NatuurTestConstants.RANG, taxon.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, taxon.getTaxonId());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(NatuurTestConstants.VOLGNUMMER, taxon.getVolgnummer());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.TAXONID_HASH, taxon.hashCode());
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
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
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
    assertEquals(taxonDto.getUitgestorven(), instance.getUitgestorven());
    assertEquals(taxonDto.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testInit4() {
    var instance  = new Taxon(taxonDto, NatuurTestConstants.TAAL);

    assertEquals(taxonDto.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(taxonDto.getNaam(NatuurTestConstants.TAAL), instance.getNaam());
    assertEquals(taxonDto.getOpmerking(), instance.getOpmerking());
    assertEquals(taxonDto.getParentId(), instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(taxonDto.getRang(), instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(taxonDto.getTaxonId(), instance.getTaxonId());
    assertEquals(taxonDto.getUitgestorven(), instance.getUitgestorven());
    assertEquals(taxonDto.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testInit5() {
    var instance  = new Taxon(taxonDto, NatuurTestConstants.TAAL);

    assertEquals(taxonDto.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(taxonDto.getNaam(NatuurTestConstants.TAAL), instance.getNaam());
    assertEquals(taxonDto.getOpmerking(), instance.getOpmerking());
    assertEquals(taxonDto.getParentId(), instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(taxonDto.getRang(), instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(taxonDto.getStatus(), instance.getStatus());
    assertEquals(taxonDto.getTaxonId(), instance.getTaxonId());
    assertEquals(taxonDto.getUitgestorven(),
                    instance.getUitgestorven());
    assertEquals(taxonDto.getVolgnummer(), instance.getVolgnummer());
  }

  @Test
  public void testLatijnsenaamComparator() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);

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

    groter.setParentNaam(NatuurTestConstants.PARENTNAAM_GR);
    groter.setParentVolgnummer(taxon.getParentVolgnummer());
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setParentNaam(NatuurTestConstants.PARENTNAAM_KL);
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
    groter.setNaam(NatuurTestConstants.NAAM_GR);
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setParentNaam(taxon.getParentNaam());
    kleiner.setParentVolgnummer(taxon.getParentVolgnummer());
    kleiner.setNaam(NatuurTestConstants.NAAM_KL);
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
    groter.setNaam(NatuurTestConstants.NAAM_GR);
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setNaam(NatuurTestConstants.NAAM_KL);

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

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setNaam(taxon.getNaam());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
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
  }

  @Test
  public void testSetLatijnsenaam() {
    var instance  = new Taxon();
    assertNull(instance.getLatijnsenaam());
    instance.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);

    assertEquals(NatuurTestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals(NatuurTestConstants.LATIJNSENAAM, instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetNaam() {
    var instance  = new Taxon();
    assertNull(instance.getNaam());
    instance.setNaam(NatuurTestConstants.NAAM);

    assertNull(instance.getLatijnsenaam());
    assertEquals(NatuurTestConstants.NAAM, instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());

    instance.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    assertEquals(NatuurTestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
    assertEquals(NatuurTestConstants.NAAM, instance.getNaam());
  }

  @Test
  public void testSetNiveau() {
    var instance  = new Taxon();
    assertNull(instance.getNiveau());
    instance.setNiveau(NatuurTestConstants.NIVEAU);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertEquals(NatuurTestConstants.NIVEAU, instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new Taxon();
    assertNull(instance.getOpmerking());
    instance.setOpmerking(NatuurTestConstants.OPMERKING);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertEquals(NatuurTestConstants.OPMERKING, instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentId() {
    var instance  = new Taxon();
    assertNull(instance.getParentId());
    instance.setParentId(NatuurTestConstants.PARENTTAXONID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentLatijnsenaam() {
    var instance  = new Taxon();
    assertNull(instance.getParentLatijnsenaam());
    instance.setParentLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 instance.getParentLatijnsenaam());
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM, instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentNaam() {
    var instance  = new Taxon();
    assertNull(instance.getParentNaam());
    instance.setParentNaam(NatuurTestConstants.PARENTNAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertEquals(NatuurTestConstants.PARENTNAAM, instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());

    instance.setParentLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 instance.getParentLatijnsenaam());
    assertEquals(NatuurTestConstants.PARENTNAAM, instance.getParentNaam());
  }

  @Test
  public void testSetParentNiveau() {
    var instance  = new Taxon();
    assertNull(instance.getParentNiveau());
    instance.setParentNiveau(NatuurTestConstants.PARENTNIVEAU);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertEquals(NatuurTestConstants.PARENTNIVEAU, instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentRang1() {
    var instance  = new Taxon();
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    instance.setParentRang(NatuurTestConstants.PARENTRANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentRang2() {
    var instance  = new Taxon();
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    instance.setParentRang(NatuurTestUtils.getParentRang());

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertEquals(NatuurTestConstants.PARENTNIVEAU, instance.getParentNiveau());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(NatuurTestConstants.PARENTRANGNAAM, instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParent1() {
    var instance  = new Taxon();
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    instance.setParent(NatuurTestUtils.getParentTaxon());

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 instance.getParentLatijnsenaam());
    assertEquals(NatuurTestConstants.PARENTNAAM, instance.getParentNaam());
    assertEquals(NatuurTestConstants.PARENTNIVEAU, instance.getParentNiveau());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertEquals(NatuurTestConstants.PARENTRANGNAAM, instance.getParentRangnaam());
    assertEquals(NatuurTestConstants.PARENTVOLGNUMMER,
                 instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParent2() {
    var instance  = new Taxon();
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    instance.setParent(NatuurTestUtils.getParentTaxonDto());

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 instance.getParentLatijnsenaam());
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM, instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertEquals(NatuurTestConstants.PARENTVOLGNUMMER,
                 instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParent3() {
    var instance  = new Taxon();
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    instance.setTaxonId(NatuurTestConstants.TAXONID);
    instance.setParent(NatuurTestUtils.getParentTaxonDto(), NatuurTestConstants.TAAL);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertEquals(NatuurTestConstants.PARENTTAXONID, instance.getParentId());
    assertEquals(NatuurTestConstants.PARENTLATIJNSENAAM,
                 instance.getParentLatijnsenaam());
    assertEquals(NatuurTestConstants.PARENTNAAM, instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertEquals(NatuurTestConstants.PARENTRANG, instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertEquals(NatuurTestConstants.PARENTVOLGNUMMER,
                 instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetParentRangnaam() {
    var instance  = new Taxon();
    assertNull(instance.getParentRangnaam());
    instance.setParentRangnaam(NatuurTestConstants.PARENTRANGNAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertEquals(NatuurTestConstants.PARENTRANGNAAM, instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRangVolgnummer() {
    var instance  = new Taxon();
    assertNull(instance.getParentVolgnummer());
    instance.setParentVolgnummer(NatuurTestConstants.PARENTVOLGNUMMER);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertEquals(NatuurTestConstants.PARENTVOLGNUMMER,
                 instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRang1() {
    var instance  = new Taxon();
    assertNull(instance.getNiveau());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    instance.setRang(NatuurTestConstants.RANG);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRang2() {
    var instance  = new Taxon();
    assertNull(instance.getNiveau());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    instance.setRang(NatuurTestUtils.getRang());

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertEquals(NatuurTestConstants.NIVEAU, instance.getNiveau());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertEquals(NatuurTestConstants.RANG, instance.getRang());
    assertEquals(NatuurTestConstants.RANGNAAM, instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetRangnaam() {
    var instance  = new Taxon();
    assertNotEquals(NatuurTestConstants.RANGNAAM, instance.getRangnaam());
    instance.setRangnaam(NatuurTestConstants.RANGNAAM);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertEquals(NatuurTestConstants.RANGNAAM, instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new Taxon();
    assertNotEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven1() {
    var instance  = new Taxon();
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());

    instance.setStatus(NatuurConstants.STAT_UITGESTORVEN);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertTrue(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven2() {
    var instance  = new Taxon();
    instance.setStatus(NatuurConstants.STAT_UITGESTORVEN);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());

    instance.setStatus(NatuurTestConstants.STATUS);
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven3() {
    var instance  = new Taxon();
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());

    instance.setStatus(NatuurConstants.STAT_UITGESTORVEN.toUpperCase());
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertEquals(NatuurConstants.STAT_UITGESTORVEN, instance.getStatus());
    assertNull(instance.getTaxonId());
    assertTrue(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetUitgestorven4() {
    var instance  = new Taxon();
    instance.setStatus(NatuurConstants.STAT_UITGESTORVEN);
    assertTrue(instance.isUitgestorven());
    assertTrue(instance.getUitgestorven());

    instance.setStatus(NatuurTestConstants.STATUS);
    assertFalse(instance.isUitgestorven());
    assertFalse(instance.getUitgestorven());
    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(Long.valueOf(0), instance.getVolgnummer());
  }

  @Test
  public void testSetVolgnummer() {
    var instance  = new Taxon();
    assertNotEquals(NatuurTestConstants.VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(NatuurTestConstants.VOLGNUMMER);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpmerking());
    assertNull(instance.getParentId());
    assertNull(instance.getParentLatijnsenaam());
    assertNull(instance.getParentNaam());
    assertNull(instance.getParentNiveau());
    assertNull(instance.getParentRang());
    assertNull(instance.getParentRangnaam());
    assertNull(instance.getParentVolgnummer());
    assertNull(instance.getRang());
    assertNull(instance.getRangnaam());
    assertNull(instance.getTaxonId());
    assertFalse(instance.getUitgestorven());
    assertEquals(NatuurTestConstants.VOLGNUMMER, instance.getVolgnummer());
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
    assertEquals(kleiner.getVolgnummer(),
                 tabel[0].getVolgnummer());
    assertEquals(taxon.getVolgnummer(),
                 tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(),
                 tabel[2].getVolgnummer());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
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
    groter.setParentNaam(taxon.getParentNaam());
    groter.setParentVolgnummer(taxon.getParentVolgnummer());
    groter.setRang(taxon.getRang());
    groter.setVolgnummer(taxon.getVolgnummer() + 1);
    groter.setVolgnummer(taxon.getVolgnummer() + 1);
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setNaam(taxon.getNaam());
    kleiner.setParentNaam(taxon.getParentNaam());
    kleiner.setParentVolgnummer(taxon.getParentVolgnummer());
    kleiner.setRang(taxon.getRang());
    kleiner.setVolgnummer(taxon.getVolgnummer() - 1);
    kleiner.setVolgnummer(taxon.getVolgnummer() - 1);

    Set<Taxon>  taxa  =
        new TreeSet<>(new Taxon.VolgnummerNaamComparator());
    taxa.add(groter);
    taxa.add(taxon);
    taxa.add(kleiner);
    var tabel = new Taxon[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getVolgnummer(),
                 tabel[0].getVolgnummer());
    assertEquals(taxon.getVolgnummer(),
                 tabel[1].getVolgnummer());
    assertEquals(groter.getVolgnummer(),
                 tabel[2].getVolgnummer());
  }

  @Test
  public void testVolgnummerNaamComparator2() {
    var groter  = new Taxon();
    var kleiner = new Taxon();

    groter.setLatijnsenaam(taxon.getLatijnsenaam());
    groter.setNaam(NatuurTestConstants.NAAM_GR);
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setLatijnsenaam(taxon.getLatijnsenaam());
    kleiner.setNaam(NatuurTestConstants.NAAM_KL);
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

    groter.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.setNaam(taxon.getNaam());
    groter.setVolgnummer(taxon.getVolgnummer());
    kleiner.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
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
