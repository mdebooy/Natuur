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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosConstants;
import static eu.debooy.natuur.TestConstants.AANTAL;
import static eu.debooy.natuur.TestConstants.GEBIEDID;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.WAARNEMINGID;
import static eu.debooy.natuur.TestConstants.WAARNEMINGID_HASH;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class WaarnemingTest {
  private static Date           datum;
  private static Gebied         gebied;
  private static Taxon          taxon;
  private static Waarneming     waarneming;
  private static WaarnemingDto  waarnemingDto;

  @BeforeClass
  public static void setUpClass() {
    datum         = new Date();
    gebied        = new Gebied();
    gebied.setGebiedId(GEBIEDID);

    taxon         = new Taxon();
    taxon.setTaxonId(TAXONID);

    waarneming    = new Waarneming();
    waarneming.setAantal(AANTAL);
    waarneming.setDatum(datum);
    waarneming.setGebied(gebied);
    waarneming.setOpmerking(OPMERKING);
    waarneming.setTaxon(taxon);
    waarneming.setWaarnemingId(WAARNEMINGID);

    GebiedDto gebiedDto = new GebiedDto();
    gebiedDto.setGebiedId(GEBIEDID);
    TaxonDto  taxonDto  = new TaxonDto();
    taxonDto.setTaxonId(TAXONID);

    waarnemingDto = new WaarnemingDto();
    waarnemingDto.setAantal(AANTAL);
    waarnemingDto.setDatum(datum);
    waarnemingDto.setGebied(gebiedDto);
    waarnemingDto.setOpmerking(OPMERKING);
    waarnemingDto.setTaxon(taxonDto);
    waarnemingDto.setWaarnemingId(WAARNEMINGID);
  }

  @Test
  public void testCompareTo() {
    Waarneming  gelijk  = new Waarneming(waarneming);
    Waarneming  groter  = new Waarneming();
    groter.setWaarnemingId(waarneming.getWaarnemingId()+ 1);
    Waarneming  kleiner = new Waarneming();
    kleiner.setWaarnemingId(waarneming.getWaarnemingId()- 1);

    assertTrue(waarneming.compareTo(groter) < 0);
    assertEquals(0, waarneming.compareTo(gelijk));
    assertTrue(waarneming.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Waarneming  object    = null;
    Waarneming  instance  = new Waarneming();

    assertNotEquals(waarneming, object);
    assertNotEquals(waarneming, instance);

    instance.setWaarnemingId(waarneming.getWaarnemingId());
    assertEquals(waarneming, instance);

    instance  = new Waarneming(waarneming);
    assertEquals(waarneming, instance);

    instance  = new Waarneming(waarnemingDto);
    assertEquals(waarneming, instance);

    instance  = new Waarneming(waarnemingDto, TAAL);
    assertEquals(waarneming, instance);
  }

  @Test
  public void testGetAantal() {
    assertEquals(AANTAL, waarneming.getAantal());
  }

  @Test
  public void testGetDatum() {
    assertEquals(datum, waarneming.getDatum());
  }

  @Test
  public void testGetGebied() {
    assertEquals(GEBIEDID, waarneming.getGebied().getGebiedId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, waarneming.getOpmerking());
  }

  @Test
  public void testGetSorteerdatum() {
    String  sorteerdatum  = Datum.fromDate(datum, DoosConstants.SORTEERDATUM);
    assertEquals(sorteerdatum, waarneming.getSorteerdatum());
  }

  @Test
  public void testGetTaxon() {
    assertEquals(TAXONID, waarneming.getTaxon().getTaxonId());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(WAARNEMINGID, waarneming.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(WAARNEMINGID_HASH, waarneming.hashCode());
  }

  @Test
  public void testPersist() {
    WaarnemingDto parameter = new WaarnemingDto();
    Waarneming    instance  = new Waarneming();
    instance.setGebied(gebied);
    instance.setTaxon(taxon);
    instance.persist(parameter);

    assertEquals(instance.getAantal(), parameter.getAantal());
    assertEquals(instance.getDatum(), parameter.getDatum());
    assertEquals(instance.getGebied(), new Gebied(parameter.getGebied()));
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getTaxon(), new Taxon(parameter.getTaxon()));
    assertEquals(instance.getWaarnemingId(), parameter.getWaarnemingId());
  }

  @Test
  public void testSetAantal() {
    Waarneming  instance  = new Waarneming();
    assertNotEquals(AANTAL, instance.getAantal());
    instance.setAantal(AANTAL);

    assertEquals(AANTAL, instance.getAantal());
  }

  @Test
  public void testSetDatum() {
    Waarneming  instance  = new Waarneming();
    assertNotEquals(datum, instance.getDatum());
    Date        datum1    = datum;
    instance.setDatum(datum1);

    assertEquals(datum, instance.getDatum());

    Date  datum2  = instance.getDatum();
    datum1  = new Date(0);
    assertEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());
    datum2  = new Date(0);
    assertEquals(datum, instance.getDatum());
    assertEquals(datum1, datum2);
  }

  @Test
  public void testSetGebied() {
    Waarneming  instance  = new Waarneming();
    assertNull(instance.getGebied());
    instance.setGebied(gebied);

    assertEquals(GEBIEDID, instance.getGebied().getGebiedId());
  }

  @Test
  public void testSetOpmerking() {
    Waarneming  instance  = new Waarneming();
    assertNotEquals(OPMERKING, instance.getOpmerking());
    instance.setOpmerking(OPMERKING);

    assertEquals(OPMERKING, instance.getOpmerking());
  }

  @Test
  public void testSetTaxon() {
    Waarneming  instance  = new Waarneming();
    assertNull(instance.getTaxon());
    instance.setTaxon(taxon);

    assertEquals(TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetWaarnemingId() {
    Waarneming  instance  = new Waarneming();
    assertNotEquals(WAARNEMINGID, instance.getWaarnemingId());
    instance.setWaarnemingId(WAARNEMINGID);

    assertEquals(WAARNEMINGID, instance.getWaarnemingId());
  }
}
