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

import static eu.debooy.natuur.TestConstants.FOTOBESTAND;
import static eu.debooy.natuur.TestConstants.FOTODETAIL;
import static eu.debooy.natuur.TestConstants.FOTOID;
import static eu.debooy.natuur.TestConstants.FOTOID_HASH;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.TAXONSEQ;
import static eu.debooy.natuur.TestConstants.WAARNEMINGID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class FotoDtoTest {
  private static FotoDto  fotoDto;

  @BeforeClass
  public static void setUpClass() {
    fotoDto = new FotoDto();
    fotoDto.setFotoBestand(FOTOBESTAND);
    fotoDto.setFotoDetail(FOTODETAIL);
    fotoDto.setFotoId(FOTOID);
    fotoDto.setOpmerking(OPMERKING);
    fotoDto.setTaxonSeq(TAXONSEQ);
    fotoDto.setWaarnemingId(WAARNEMINGID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new FotoDto();
    var groter  = new FotoDto();
    var kleiner = new FotoDto();

    gelijk.setFotoId(fotoDto.getFotoId());
    groter.setFotoId(fotoDto.getFotoId() + 1);
    kleiner.setFotoId(fotoDto.getFotoId() - 1);

    assertTrue(fotoDto.compareTo(groter) < 0);
    assertEquals(0, fotoDto.compareTo(gelijk));
    assertTrue(fotoDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new FotoDto();

    assertEquals(fotoDto, fotoDto);
    assertNotEquals(fotoDto, null);
    assertNotEquals(fotoDto, FotoDto.COL_FOTODETAIL);
    assertNotEquals(fotoDto, instance);

    instance.setFotoId(fotoDto.getFotoId());
    assertEquals(fotoDto, instance);

    instance.setFotoId(FOTOID - 1);
    assertNotEquals(fotoDto, instance);
  }

  @Test
  public void testGetFotoBestand() {
    assertEquals(FOTOBESTAND, fotoDto.getFotoBestand());
  }

  @Test
  public void testGetFotoDetail() {
    assertEquals(FOTODETAIL, fotoDto.getFotoDetail());
  }

  @Test
  public void testGetFotoId() {
    assertEquals(FOTOID, fotoDto.getFotoId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, fotoDto.getOpmerking());
  }

  @Test
  public void testGetTaxonSeq() {
    assertEquals(TAXONSEQ, fotoDto.getTaxonSeq());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(WAARNEMINGID, fotoDto.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(FOTOID_HASH, fotoDto.hashCode());
  }

  @Test
  public void testSetFotoBestand() {
    var instance  = new FotoDto();
    assertNotEquals(FOTOBESTAND, instance.getFotoBestand());
    instance.setFotoBestand(FOTOBESTAND);

    assertEquals(FOTOBESTAND, instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotoDetail() {
    var instance  = new FotoDto();
    assertNotEquals(FOTODETAIL, instance.getFotoDetail());
    instance.setFotoDetail(FOTODETAIL);

    assertNull(instance.getFotoBestand());
    assertEquals(FOTODETAIL, instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotoId() {
    var instance  = new FotoDto();
    assertNotEquals(FOTOID, instance.getFotoId());
    instance.setFotoId(FOTOID);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertEquals(FOTOID, instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new FotoDto();
    assertNotEquals(OPMERKING, instance.getOpmerking());
    instance.setOpmerking(OPMERKING);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertEquals(OPMERKING, instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetTaxonSeq() {
    var instance  = new FotoDto();
    assertNotEquals(TAXONSEQ, instance.getTaxonSeq());
    instance.setTaxonSeq(TAXONSEQ);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(TAXONSEQ, instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetWaarnemingId() {
    var instance  = new FotoDto();
    assertNotEquals(WAARNEMINGID, instance.getWaarnemingId());
    instance.setWaarnemingId(WAARNEMINGID);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertEquals(WAARNEMINGID, instance.getWaarnemingId());
  }
}
