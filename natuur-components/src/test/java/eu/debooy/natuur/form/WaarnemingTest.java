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
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import java.util.Date;
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
public class WaarnemingTest {
  private static  Date          datum;
  private static  Gebied        gebied;
  private static  Taxon         taxon;
  private static  TaxonDto      taxonDto;
  private static  Waarneming    waarneming;
  private static  WaarnemingDto waarnemingDto;

  @BeforeClass
  public static void setUpClass() {
    datum         = new Date();
    gebied        = TestUtils.getGebied();
    taxon         = TestUtils.getTaxon();
    taxonDto      = new TaxonDto();
    taxon.persist(taxonDto);

    waarneming    = new Waarneming();
    waarneming.setAantal(TestConstants.AANTAL);
    waarneming.setDatum(datum);
    waarneming.setGebied(gebied);
    waarneming.setOpmerking(TestConstants.OPMERKING);
    waarneming.setTaxon(taxon);
    waarneming.setWaarnemingId(TestConstants.WAARNEMINGID);

    waarnemingDto = new WaarnemingDto();
    waarnemingDto.setAantal(TestConstants.AANTAL);
    waarnemingDto.setDatum(datum);
    waarnemingDto.setGebied(TestUtils.getGebiedDto());
    waarnemingDto.setOpmerking(TestConstants.OPMERKING);
    waarnemingDto.setTaxon(taxonDto);
    waarnemingDto.setWaarnemingId(TestConstants.WAARNEMINGID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Waarneming();
    var groter  = new Waarneming();
    var kleiner = new Waarneming();

    gelijk.setWaarnemingId(waarneming.getWaarnemingId());
    groter.setWaarnemingId(waarneming.getWaarnemingId() + 1);
    kleiner.setWaarnemingId(waarneming.getWaarnemingId() - 1);

    assertTrue(waarneming.compareTo(groter) < 0);
    assertEquals(0, waarneming.compareTo(gelijk));
    assertTrue(waarneming.compareTo(kleiner) > 0);
  }

  @Test
  public void testDatumComparator() {
    var groter  = new Waarneming();
    var kleiner = new Waarneming();

    groter.setDatum(new Date());
    kleiner.setDatum(new Date(0));

    Set<Waarneming>  waarnemingen  =
        new TreeSet<>(new Waarneming.DatumComparator());
    waarnemingen.add(groter);
    waarnemingen.add(waarneming);
    waarnemingen.add(kleiner);

    var tabel = new Waarneming[waarnemingen.size()];
    System.arraycopy(waarnemingen.toArray(), 0, tabel, 0, waarnemingen.size());
    assertEquals(kleiner.getDatum(), tabel[0].getDatum());
    assertEquals(waarneming.getDatum(), tabel[1].getDatum());
    assertEquals(groter.getDatum(), tabel[2].getDatum());
  }

  @Test
  public void testEquals() {
    var instance  = new Waarneming();

    assertEquals(waarneming, waarneming);
    assertNotEquals(waarneming, null);
    assertNotEquals(waarneming, TestConstants.RANGNAAM);
    assertNotEquals(waarneming, instance);

    instance.setWaarnemingId(waarneming.getWaarnemingId());
    assertEquals(waarneming, instance);

    instance  = new Waarneming();
    instance.setWaarnemingId(waarneming.getWaarnemingId());
    assertEquals(waarneming, instance);

    instance  = new Waarneming(waarnemingDto);
    assertEquals(waarneming, instance);

    instance  = new Waarneming(waarnemingDto, TestConstants.TAAL);
    assertEquals(waarneming, instance);
  }

  @Test
  public void testGetAantal() {
    assertEquals(TestConstants.AANTAL, waarneming.getAantal());
  }

  @Test
  public void testGetAantalFotos() {
    assertEquals(TestConstants.AANTALFOTOS, waarneming.getAantalFotos());
  }

  @Test
  public void testGetDatum() {
    assertEquals(datum, waarneming.getDatum());
  }

  @Test
  public void testGetGebied() {
    assertEquals(TestConstants.GEBIEDID, waarneming.getGebied().getGebiedId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(TestConstants.OPMERKING, waarneming.getOpmerking());
  }

  @Test
  public void testGetSorteerdatum() {
    String  sorteerdatum  = Datum.fromDate(datum, DoosConstants.SORTEERDATUM);
    assertEquals(sorteerdatum, waarneming.getSorteerdatum());
  }

  @Test
  public void testGetTaxon() {
    assertEquals(TestConstants.TAXONID, waarneming.getTaxon().getTaxonId());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(TestConstants.WAARNEMINGID, waarneming.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.WAARNEMINGID_HASH, waarneming.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Waarneming();

    assertNull(instance.getAantal());
    assertNull(instance.getDatum());
    assertNull(instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testInit2() {
    var instance  = new Waarneming(waarnemingDto);

    assertEquals(waarnemingDto.getAantal(), instance.getAantal());
    assertEquals(waarnemingDto.getDatum(), instance.getDatum());
    assertEquals(new Gebied(waarnemingDto.getGebied()), instance.getGebied());
    assertEquals(waarnemingDto.getOpmerking(), instance.getOpmerking());
    assertEquals(new Taxon(waarnemingDto.getTaxon()), instance.getTaxon());
    assertEquals(waarnemingDto.getWaarnemingId(), instance.getWaarnemingId());
  }

  @Test
  public void testPersist() {
    var parameter = new WaarnemingDto();
    waarneming.persist(parameter);

    assertEquals(waarneming.getAantal(), parameter.getAantal());
    assertEquals(waarneming.getDatum(), parameter.getDatum());
    assertEquals(waarneming.getGebied(), new Gebied(parameter.getGebied()));
    assertEquals(waarneming.getOpmerking(), parameter.getOpmerking());
    assertEquals(waarneming.getTaxon(), new Taxon(parameter.getTaxon()));
    assertEquals(waarneming.getWaarnemingId(), parameter.getWaarnemingId());
  }

  @Test
  public void testSetAantal() {
    var instance  = new Waarneming();
    assertNotEquals(TestConstants.AANTAL, instance.getAantal());
    instance.setAantal(TestConstants.AANTAL);

    assertEquals(TestConstants.AANTAL, instance.getAantal());
  }

  @Test
  public void testSetDatum() {
    var instance  = new Waarneming();
    assertNotEquals(datum, instance.getDatum());

    instance.setDatum(datum);
    assertEquals(datum, instance.getDatum());

    // Geen reference maar value?
    Date  datum2  = instance.getDatum();
    assertEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());
    datum2.setTime(0);
    assertNotEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());

    instance.setDatum(null);
    assertNull(instance.getDatum());
    assertEquals("", instance.getSorteerdatum());
  }

  @Test
  public void testSetGebied() {
    var instance  = new Waarneming();
    assertNull(instance.getGebied());
    instance.setGebied(gebied);

    assertEquals(TestConstants.GEBIEDID, instance.getGebied().getGebiedId());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new Waarneming();
    assertNotEquals(TestConstants.OPMERKING, instance.getOpmerking());
    instance.setOpmerking(TestConstants.OPMERKING);

    assertEquals(TestConstants.OPMERKING, instance.getOpmerking());
  }

  @Test
  public void testSetTaxon() {
    var instance  = new Waarneming();
    assertNull(instance.getTaxon());
    instance.setTaxon(taxon);

    assertEquals(TestConstants.TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetWaarnemingId() {
    var instance  = new Waarneming();
    assertNotEquals(TestConstants.WAARNEMINGID, instance.getWaarnemingId());
    instance.setWaarnemingId(TestConstants.WAARNEMINGID);

    assertEquals(TestConstants.WAARNEMINGID, instance.getWaarnemingId());
  }
}
