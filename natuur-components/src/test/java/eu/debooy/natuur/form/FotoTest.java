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

import static eu.debooy.natuur.TestConstants.FOTOBESTAND;
import static eu.debooy.natuur.TestConstants.FOTODETAIL;
import static eu.debooy.natuur.TestConstants.FOTOID;
import static eu.debooy.natuur.TestConstants.FOTOID_HASH;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.TAXONSEQ;
import static eu.debooy.natuur.TestConstants.WAARNEMINGID;
import eu.debooy.natuur.domain.FotoDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class FotoTest {
  private static  Foto    foto;
  private static  FotoDto fotoDto;

  @BeforeClass
  public static void setUpClass() {
    foto    = new Foto();
    foto.setFotoBestand(FOTOBESTAND);
    foto.setFotoDetail(FOTODETAIL);
    foto.setFotoId(FOTOID);
    foto.setOpmerking(OPMERKING);
    foto.setTaxonSeq(TAXONSEQ);
    foto.setWaarnemingId(WAARNEMINGID);

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
    var gelijk  = new Foto(foto);
    var groter  = new Foto();
    var kleiner = new Foto();

    groter.setFotoId(foto.getFotoId() + 1);
    kleiner.setFotoId(foto.getFotoId() - 1);

    assertTrue(foto.compareTo(groter) < 0);
    assertEquals(0, foto.compareTo(gelijk));
    assertTrue(foto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var   instance  = new Foto();

    assertEquals(foto, foto);
    assertNotEquals(foto, null);
    assertNotEquals(foto, FotoDto.COL_FOTODETAIL);
    assertNotEquals(foto, instance);

    instance.setFotoId(foto.getFotoId());
    assertEquals(foto, instance);

    instance  = new Foto(foto);
    assertEquals(foto, instance);

    instance  = new Foto(fotoDto);
    assertEquals(foto, instance);
  }

  @Test
  public void testGetFotoBestand() {
    assertEquals(FOTOBESTAND, foto.getFotoBestand());
  }

  @Test
  public void testGetFotoDetail() {
    assertEquals(FOTODETAIL, foto.getFotoDetail());
  }

  @Test
  public void testGetFotoId() {
    assertEquals(FOTOID, foto.getFotoId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, foto.getOpmerking());
  }

  @Test
  public void testGetTaxonSeq() {
    assertEquals(TAXONSEQ, foto.getTaxonSeq());
  }

  @Test
  public void testGetWaarnemingId() {
    assertEquals(WAARNEMINGID, foto.getWaarnemingId());
  }

  @Test
  public void testHashCode() {
    assertEquals(FOTOID_HASH, foto.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Foto();

    assertNull(instance.getFotoBestand());
    assertNull(instance.getFotoDetail());
    assertNull(instance.getFotoId());
    assertNull(instance.getOpmerking());
    assertEquals(Long.valueOf(0L), instance.getTaxonSeq());
    assertNull(instance.getWaarnemingId());
  }

  @Test
  public void testInit2() {
    var instance  = new Foto(foto);

    assertEquals(foto.getFotoBestand(), instance.getFotoBestand());
    assertEquals(foto.getFotoDetail(), instance.getFotoDetail());
    assertEquals(foto.getFotoId(), instance.getFotoId());
    assertEquals(foto.getOpmerking(), instance.getOpmerking());
    assertEquals(foto.getTaxonSeq(), instance.getTaxonSeq());
    assertEquals(foto.getWaarnemingId(), instance.getWaarnemingId());
  }

  @Test
  public void testInit3() {
    var instance  = new Foto(fotoDto);

    assertEquals(fotoDto.getFotoBestand(), instance.getFotoBestand());
    assertEquals(fotoDto.getFotoDetail(), instance.getFotoDetail());
    assertEquals(fotoDto.getFotoId(), instance.getFotoId());
    assertEquals(fotoDto.getOpmerking(), instance.getOpmerking());
    assertEquals(fotoDto.getTaxonSeq(), instance.getTaxonSeq());
    assertEquals(fotoDto.getWaarnemingId(), instance.getWaarnemingId());
  }

  @Test
  public void testPersist() {
    var parameter = new FotoDto();
    var instance  = new Foto(foto);

    instance.persist(parameter);

    assertEquals(instance.getFotoBestand(), parameter.getFotoBestand());
    assertEquals(instance.getFotoDetail(), parameter.getFotoDetail());
    assertEquals(instance.getFotoId(), parameter.getFotoId());
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getTaxonSeq(), parameter.getTaxonSeq());
    assertEquals(instance.getWaarnemingId(), parameter.getWaarnemingId());

    instance.persist(parameter);

    assertEquals(instance.getFotoBestand(), parameter.getFotoBestand());
    assertEquals(instance.getFotoDetail(), parameter.getFotoDetail());
    assertEquals(instance.getFotoId(), parameter.getFotoId());
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getTaxonSeq(), parameter.getTaxonSeq());
    assertEquals(instance.getWaarnemingId(), parameter.getWaarnemingId());
  }

  @Test
  public void testSetFotoBestand() {
    var instance  = new Foto();
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
    var instance  = new Foto();
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
    var instance  = new Foto();
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
    var instance  = new Foto();
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
    var instance  = new Foto();
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
    var instance  = new Foto();
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
