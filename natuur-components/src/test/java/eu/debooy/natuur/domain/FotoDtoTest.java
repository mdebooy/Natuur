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

import eu.debooy.natuur.TestConstants;
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
    fotoDto.setFotoBestand(TestConstants.FOTOBESTAND);
    fotoDto.setFotoDetail(TestConstants.FOTODETAIL);
    fotoDto.setFotoId(TestConstants.FOTOID);
    fotoDto.setOpmerking(TestConstants.OPMERKING);
    fotoDto.setTaxonSeq(TestConstants.TAXONSEQ);
    fotoDto.setWaarnemingId(TestConstants.WAARNEMINGID);
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

    instance.setFotoId(TestConstants.FOTOID - 1);
    assertNotEquals(fotoDto, instance);
  }

  @Test
  public void testGetFotoBestand() {
    assertEquals(TestConstants.FOTOBESTAND, fotoDto.getFotoBestand());
  }

  @Test
  public void testGetFotoDetail() {
    assertEquals(TestConstants.FOTODETAIL, fotoDto.getFotoDetail());
  }

  @Test
  public void testGetFotoId() {
    assertEquals(TestConstants.FOTOID, fotoDto.getFotoId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(TestConstants.OPMERKING, fotoDto.getOpmerking());
  }

  @Test
  public void testGetTaxonSeq() {
    assertEquals(TestConstants.TAXONSEQ, fotoDto.getTaxonSeq());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(TestConstants.WAARNEMINGID, fotoDto.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.FOTOID_HASH, fotoDto.hashCode());
  }

  @Test
  public void testSetFotoBestand() {
    var instance  = new FotoDto();
    assertNotEquals(TestConstants.FOTOBESTAND, instance.getFotoBestand());
    instance.setFotoBestand(TestConstants.FOTOBESTAND);

    assertEquals(TestConstants.FOTOBESTAND, instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotoDetail() {
    var instance  = new FotoDto();
    assertNotEquals(TestConstants.FOTODETAIL, instance.getFotoDetail());
    instance.setFotoDetail(TestConstants.FOTODETAIL);

    assertNull(instance.getFotoBestand());
    assertEquals(TestConstants.FOTODETAIL, instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetFotoId() {
    var instance  = new FotoDto();
    assertNotEquals(TestConstants.FOTOID, instance.getFotoId());
    instance.setFotoId(TestConstants.FOTOID);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertEquals(TestConstants.FOTOID, instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetOpmerking() {
    var instance  = new FotoDto();
    assertNotEquals(TestConstants.OPMERKING, instance.getOpmerking());
    instance.setOpmerking(TestConstants.OPMERKING);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertEquals(TestConstants.OPMERKING, instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetTaxonSeq() {
    var instance  = new FotoDto();
    assertNotEquals(TestConstants.TAXONSEQ, instance.getTaxonSeq());
    instance.setTaxonSeq(TestConstants.TAXONSEQ);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(TestConstants.TAXONSEQ, instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testSetWaarnemingId() {
    var instance  = new FotoDto();
    assertNotEquals(TestConstants.WAARNEMINGID, instance.getWaarnemingId());
    instance.setWaarnemingId(TestConstants.WAARNEMINGID);

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertEquals(TestConstants.WAARNEMINGID, instance.getWaarnemingId());
  }
}
