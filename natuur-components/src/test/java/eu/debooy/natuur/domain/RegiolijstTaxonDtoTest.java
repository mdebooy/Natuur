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
package eu.debooy.natuur.domain;

import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTaxonDtoTest {
  private static  RegiolijstTaxonDto  regiolijstTaxonDto;

  @BeforeClass
  public static void beforeClass() {
    regiolijstTaxonDto  = new RegiolijstTaxonDto();

    regiolijstTaxonDto.setRegiolijstId(NatuurTestConstants.REGIOLIJSTID);
    regiolijstTaxonDto.setStatus(NatuurTestConstants.STATUS);
    regiolijstTaxonDto.setTaxonId(NatuurTestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstTaxonDto();
    var groter  = new RegiolijstTaxonDto();
    var kleiner = new RegiolijstTaxonDto();

    gelijk.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId());
    gelijk.setTaxonId(regiolijstTaxonDto.getTaxonId());
    groter.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId());
    groter.setTaxonId(regiolijstTaxonDto.getTaxonId() + 1);
    kleiner.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId());
    kleiner.setTaxonId(regiolijstTaxonDto.getTaxonId() - 1);

    assertTrue(regiolijstTaxonDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonDto.compareTo(gelijk));
    assertTrue(regiolijstTaxonDto.compareTo(kleiner) > 0);

    groter.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId() + 1);
    groter.setTaxonId(regiolijstTaxonDto.getTaxonId());
    kleiner.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId() - 1);
    kleiner.setTaxonId(regiolijstTaxonDto.getTaxonId());

    assertTrue(regiolijstTaxonDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonDto.compareTo(gelijk));
    assertTrue(regiolijstTaxonDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegiolijstTaxonDto();

    assertEquals(regiolijstTaxonDto, regiolijstTaxonDto);
    assertNotEquals(regiolijstTaxonDto, null);
    assertNotEquals(regiolijstTaxonDto, NatuurTestConstants.RANGNAAM);
    assertNotEquals(regiolijstTaxonDto, instance);

    instance.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId());
    instance.setTaxonId(regiolijstTaxonDto.getTaxonId());
    assertEquals(regiolijstTaxonDto, instance);

    instance.setTaxonId(regiolijstTaxonDto.getTaxonId() + 1);
    assertNotEquals(regiolijstTaxonDto, instance);

    instance.setRegiolijstId(regiolijstTaxonDto.getRegiolijstId() + 1);
    instance.setTaxonId(regiolijstTaxonDto.getTaxonId());
    assertNotEquals(regiolijstTaxonDto, instance);
  }

  @Test
  public void testGetRegio() {
    assertEquals(NatuurTestConstants.REGIOLIJSTID,
                 regiolijstTaxonDto.getRegiolijstId());
  }

  @Test
  public void testGetTaal() {
    assertEquals(NatuurTestConstants.STATUS, regiolijstTaxonDto.getStatus());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, regiolijstTaxonDto.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.REGIOLIJSTTAXON_HASH,
                 regiolijstTaxonDto.hashCode());
  }

  @Test
  public void testSetRegiolijstId() {
    var instance  = new RegiolijstTaxonDto();
    assertNotEquals(NatuurTestConstants.REGIOID, instance.getRegiolijstId());

    instance.setRegiolijstId(NatuurTestConstants.REGIOID);

    assertEquals(NatuurTestConstants.REGIOID, instance.getRegiolijstId());
  }

  @Test
  public void testSetStatus() {
    var instance  = new RegiolijstTaxonDto();
    assertNull(instance.getStatus());

    instance.setStatus(NatuurTestConstants.STATUS);

    assertEquals(NatuurTestConstants.STATUS, instance.getStatus());

    instance.setStatus(null);

    assertNull(instance.getStatus());
  }

  @Test
  public void testSetTaxon() {
    var instance  = new RegiolijstTaxonDto();
    var taxon     = NatuurTestUtils.getTaxonDto();
    assertNull( instance.getTaxon());

    instance.setTaxon(taxon);

    assertEquals(taxon, instance.getTaxon());

    instance.setTaxon(null);

    assertNull(instance.getTaxon());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new RegiolijstTaxonDto();
    assertNull(instance.getTaxonId());

    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator1() {
    var groter  = new RegiolijstTaxonDto();
    var kleiner = new RegiolijstTaxonDto();

    groter.setTaxon(NatuurTestUtils.getTaxonDto());
    kleiner.setTaxon(NatuurTestUtils.getTaxonDto());

    groter.getTaxon().setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    groter.getTaxon().setVolgnummer(100L);
    kleiner.getTaxon().setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    kleiner.getTaxon().setVolgnummer(12L);
    Set<RegiolijstTaxonDto> taxa  = new TreeSet<>
        (new RegiolijstTaxonDto.VolgnummerLatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(kleiner);

    var tabel = new RegiolijstTaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(kleiner.getTaxon().getVolgnummer(),
                 tabel[0].getTaxon().getVolgnummer());
    assertEquals(groter.getTaxon().getVolgnummer(),
                 tabel[1].getTaxon().getVolgnummer());
    assertEquals(tabel[0].getTaxon().getLatijnsenaam(),
                 tabel[1].getTaxon().getLatijnsenaam());
  }

  @Test
  public void testVolgnummerLatijnsenaamComparator2() {
    var groter  = new RegiolijstTaxonDto();
    var kleiner = new RegiolijstTaxonDto();

    groter.setTaxon(NatuurTestUtils.getTaxonDto());
    kleiner.setTaxon(NatuurTestUtils.getTaxonDto());

    groter.getTaxon().setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);
    groter.getTaxon().setVolgnummer(12L);
    kleiner.getTaxon().setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_KL);
    kleiner.getTaxon().setVolgnummer(12L);
    Set<RegiolijstTaxonDto> taxa  = new TreeSet<>
        (new RegiolijstTaxonDto.VolgnummerLatijnsenaamComparator());
    taxa.add(groter);
    taxa.add(kleiner);

    var tabel = new RegiolijstTaxonDto[taxa.size()];
    System.arraycopy(taxa.toArray(), 0, tabel, 0, taxa.size());
    assertEquals(tabel[0].getTaxon().getVolgnummer(),
                 tabel[1].getTaxon().getVolgnummer());
    assertEquals(NatuurTestConstants.LATIJNSENAAM_KL,
                 tabel[0].getTaxon().getLatijnsenaam());
    assertEquals(NatuurTestConstants.LATIJNSENAAM_GR,
                 tabel[1].getTaxon().getLatijnsenaam());
  }
}
