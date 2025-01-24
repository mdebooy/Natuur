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

import eu.debooy.natuur.NatuurTestConstants;
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
    fotoDto.setFotoBestand(NatuurTestConstants.FOTOBESTAND);
    fotoDto.setFotoDetail(NatuurTestConstants.FOTODETAIL);
    fotoDto.setFotoId(NatuurTestConstants.FOTOID);
    fotoDto.setOpmerking(NatuurTestConstants.OPMERKING);
    fotoDto.setTaxonSeq(NatuurTestConstants.TAXONSEQ);
    fotoDto.setWaarnemingId(NatuurTestConstants.WAARNEMINGID);
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

    instance.setFotoId(NatuurTestConstants.FOTOID - 1);
    assertNotEquals(fotoDto, instance);
  }

  @Test
  public void testGetFotoBestand() {
    assertEquals(NatuurTestConstants.FOTOBESTAND, fotoDto.getFotoBestand());
  }

  @Test
  public void testGetFotoDetail() {
    assertEquals(NatuurTestConstants.FOTODETAIL, fotoDto.getFotoDetail());
  }

  @Test
  public void testGetFotoId() {
    assertEquals(NatuurTestConstants.FOTOID, fotoDto.getFotoId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(NatuurTestConstants.OPMERKING, fotoDto.getOpmerking());
  }

  @Test
  public void testGetTaxonSeq() {
    assertEquals(NatuurTestConstants.TAXONSEQ, fotoDto.getTaxonSeq());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(NatuurTestConstants.WAARNEMINGID, fotoDto.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.FOTOID_HASH, fotoDto.hashCode());
  }

  @Test
  public void testSetFotoBestand() {
    var instance  = new FotoDto();
    assertNotEquals(NatuurTestConstants.FOTOBESTAND, instance.getFotoBestand());
    instance.setFotoBestand(NatuurTestConstants.FOTOBESTAND);

    assertEquals(NatuurTestConstants.FOTOBESTAND, instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotoDetail() {
    var instance  = new FotoDto();
    assertNotEquals(NatuurTestConstants.FOTODETAIL, instance.getFotoDetail());
    instance.setFotoDetail(NatuurTestConstants.FOTODETAIL);

    assertNull(instance.getFotoBestand());
    assertEquals(NatuurTestConstants.FOTODETAIL, instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotoId() {
    var instance  = new FotoDto();
    assertNotEquals(NatuurTestConstants.FOTOID, instance.getFotoId());
    instance.setFotoId(NatuurTestConstants.FOTOID);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertEquals(NatuurTestConstants.FOTOID, instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new FotoDto();
    assertNotEquals(NatuurTestConstants.OPMERKING, instance.getOpmerking());
    instance.setOpmerking(NatuurTestConstants.OPMERKING);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertEquals(NatuurTestConstants.OPMERKING, instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetTaxonSeq() {
    var instance  = new FotoDto();
    assertNotEquals(NatuurTestConstants.TAXONSEQ, instance.getTaxonSeq());
    instance.setTaxonSeq(NatuurTestConstants.TAXONSEQ);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(NatuurTestConstants.TAXONSEQ, instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetWaarnemingId() {
    var instance  = new FotoDto();
    assertNotEquals(NatuurTestConstants.WAARNEMINGID, instance.getWaarnemingId());
    instance.setWaarnemingId(NatuurTestConstants.WAARNEMINGID);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertEquals(NatuurTestConstants.WAARNEMINGID, instance.getWaarnemingId());
  }
}
