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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import static eu.debooy.natuur.TestConstants.AANTAL;
import static eu.debooy.natuur.TestConstants.FOTOBESTAND;
import static eu.debooy.natuur.TestConstants.FOTODETAIL;
import static eu.debooy.natuur.TestConstants.FOTOID;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.TAXONSEQ;
import static eu.debooy.natuur.TestConstants.WAARNEMINGID;
import static eu.debooy.natuur.TestConstants.WAARNEMINGID_HASH;
import eu.debooy.natuur.TestUtils;
import java.util.Date;
import java.util.Map;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class WaarnemingDtoTest {
  private static  Date                datum;
  private static  Map<Long, FotoDto>  fotos;
  private static  GebiedDto           gebiedDto;
  private static  TaxonDto            taxonDto;
  private static  WaarnemingDto       waarnemingDto;

  @BeforeClass
  public static void beforeClass() {
    datum         = new Date();
    fotos         = TestUtils.getFotos();
    gebiedDto     = TestUtils.getGebiedDto();
    taxonDto      = TestUtils.getTaxonDto();

    waarnemingDto = new WaarnemingDto();

    waarnemingDto.setAantal(AANTAL);
    waarnemingDto.setDatum(datum);
    waarnemingDto.setFotos(fotos);
    waarnemingDto.setGebied(gebiedDto);
    waarnemingDto.setOpmerking(OPMERKING);
    waarnemingDto.setTaxonId(taxonDto.getTaxonId());
    waarnemingDto.setWaarnemingId(WAARNEMINGID);
  }

  @Test
  public void testAddFoto1() {
    var fotoDto   = new FotoDto();
    var instance  = new WaarnemingDto();

    fotoDto.setFotoBestand(FOTOBESTAND);
    fotoDto.setFotoDetail(FOTODETAIL);
    fotoDto.setFotoId(FOTOID);
    fotoDto.setOpmerking(OPMERKING);
    fotoDto.setTaxonSeq(TAXONSEQ);
    fotoDto.setWaarnemingId(WAARNEMINGID);
    fotos.put(TAXONSEQ, fotoDto);

    instance.setWaarnemingId(WAARNEMINGID);
    instance.addFoto(fotoDto);

    assertEquals(1, instance.getAantalFotos());
    assertEquals(fotoDto, instance.getFoto(TAXONSEQ));
  }

  @Test
  public void testAddFoto2() {
    var fotoDto   = new FotoDto();
    var instance  = new WaarnemingDto();

    fotoDto.setFotoBestand(FOTOBESTAND);
    fotoDto.setFotoDetail(FOTODETAIL);
    fotoDto.setFotoId(FOTOID);
    fotoDto.setOpmerking(OPMERKING);
    fotoDto.setTaxonSeq(TAXONSEQ);
    fotos.put(TAXONSEQ, fotoDto);

    instance.setWaarnemingId(WAARNEMINGID);
    instance.addFoto(fotoDto);
    var result  = instance.getFoto(TAXONSEQ);

    assertEquals(1, instance.getAantalFotos());
    assertEquals(WAARNEMINGID, result.getWaarnemingId());
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new WaarnemingDto();
    var groter  = new WaarnemingDto();
    var kleiner = new WaarnemingDto();

    gelijk.setWaarnemingId(WAARNEMINGID);
    groter.setWaarnemingId(WAARNEMINGID + 1);
    kleiner.setWaarnemingId(WAARNEMINGID - 1);

    assertTrue(waarnemingDto.compareTo(groter) < 0);
    assertEquals(0, waarnemingDto.compareTo(gelijk));
    assertTrue(waarnemingDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new WaarnemingDto();

    assertEquals(waarnemingDto, waarnemingDto);
    assertNotEquals(waarnemingDto, null);
    assertNotEquals(waarnemingDto, NAAM);
    assertNotEquals(waarnemingDto, instance);

    instance.setWaarnemingId(waarnemingDto.getWaarnemingId());
    assertEquals(waarnemingDto, instance);

    instance.setWaarnemingId(waarnemingDto.getWaarnemingId() + 1);
    assertNotEquals(waarnemingDto, instance);
  }

  @Test
  public void testGetAantal() {
    assertEquals(AANTAL, waarnemingDto.getAantal());
  }

  @Test
  public void testGetAantalFotos() {
    assertEquals(fotos.size(), waarnemingDto.getAantalFotos());
  }

  @Test
  public void testGetDatum() {
    assertEquals(datum, waarnemingDto.getDatum());
  }

  @Test
  public void testGetFoto() {
    assertEquals(TestUtils.getFotos().get(TAXONSEQ),
                 waarnemingDto.getFoto(TAXONSEQ));
    assertEquals(new FotoDto(), waarnemingDto.getFoto(TAXONSEQ - 1));
  }

  @Test
  public void testGetGebied() {
    assertEquals(gebiedDto, waarnemingDto.getGebied());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, waarnemingDto.getOpmerking());
  }

  @Test
  public void testGetTaxon() {
    assertNull(waarnemingDto.getTaxon());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(WAARNEMINGID, waarnemingDto.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(WAARNEMINGID_HASH, waarnemingDto.hashCode());
  }

  @Test
  public void testRemoveFoto() {
    var instance  = new WaarnemingDto();

    instance.setWaarnemingId(WAARNEMINGID);
    instance.setFotos(TestUtils.getFotos());

    assertEquals(2, instance.getFotos().size());
    instance.removeFoto(TAXONSEQ);
    assertEquals(1, instance.getFotos().size());
    try {
      instance.removeFoto(TAXONSEQ);
      Assert.fail("Geen throw ObjectNotFoundException");
    } catch (ObjectNotFoundException e) {
      // Is goed.
    }
  }

  @Test
  public void testSetAantal() {
    var instance  = new WaarnemingDto();
    assertNotEquals(AANTAL, instance.getAantal());

    instance.setAantal(AANTAL);

    assertEquals(AANTAL, instance.getAantal());
    assertNull(instance.getDatum());
    assertTrue(instance.getFotos().isEmpty());
    assertNull(instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetDatum() {
    var instance  = new WaarnemingDto();
    assertNotEquals(datum, instance.getDatum());

    instance.setDatum(datum);

    assertNull(instance.getAantal());
    assertEquals(datum, instance.getDatum());
    assertTrue(instance.getFotos().isEmpty());
    assertNull(instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());

    // Geen reference maar value?
    Date  datum2  = instance.getDatum();
    assertEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());
    datum2.setTime(0);
    assertNotEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());

    instance.setDatum(null);
    assertNull(instance.getDatum());
  }

  @Test
  public void testSetFotos1() {
    var instance  = new WaarnemingDto();
    assertTrue(instance.getFotos().isEmpty());

    instance.setFotos(TestUtils.getFotos());

    assertNull(instance.getAantal());
    assertNull(instance.getDatum());
    assertEquals(2, instance.getFotos().size());
    assertNull(instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotos2() {
    var instance  = new WaarnemingDto();
    assertTrue(instance.getFotos().isEmpty());

    instance.setFotos(TestUtils.getFotos().values());

    assertNull(instance.getAantal());
    assertNull(instance.getDatum());
    assertEquals(2, instance.getFotos().size());
    assertNull(instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetGebied() {
    var instance  = new WaarnemingDto();
    assertNotEquals(gebiedDto, instance.getGebied());

    instance.setGebied(gebiedDto);

    assertNull(instance.getAantal());
    assertNull(instance.getDatum());
    assertTrue(instance.getFotos().isEmpty());
    assertEquals(gebiedDto, instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new WaarnemingDto();
    assertNotEquals(OPMERKING, instance.getOpmerking());

    instance.setOpmerking(OPMERKING);

    assertNull(instance.getAantal());
    assertNull(instance.getDatum());
    assertTrue(instance.getFotos().isEmpty());
    assertNull(instance.getGebied());
    assertEquals(OPMERKING, instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetWaarnemingId() {
    var instance  = new WaarnemingDto();
    assertNotEquals(WAARNEMINGID, instance.getWaarnemingId());

    instance.setWaarnemingId(WAARNEMINGID);

    assertNull(instance.getAantal());
    assertNull(instance.getDatum());
    assertTrue(instance.getFotos().isEmpty());
    assertNull(instance.getGebied());
    assertNull(instance.getOpmerking());
    assertNull(instance.getTaxon());
    assertEquals(WAARNEMINGID, instance.getWaarnemingId());
  }
}
