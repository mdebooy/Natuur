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
import static eu.debooy.natuur.TestConstants.GEBIEDID;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONSEQ;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
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
public class FotoTest {
  private static Gebied   gebied;
  private static Taxon    taxon;
  private static Foto     foto;
  private static FotoDto  fotoDto;

  @BeforeClass
  public static void setUpClass() {
    gebied  = new Gebied();
    gebied.setGebiedId(GEBIEDID);

    taxon   = new Taxon();
    taxon.setTaxonId(TAXONID);

    foto    = new Foto();
    foto.setFotoBestand(FOTOBESTAND);
    foto.setFotoDetail(FOTODETAIL);
    foto.setFotoId(FOTOID);
    foto.setGebied(gebied);
    foto.setOpmerking(OPMERKING);
    foto.setTaxon(taxon);
    foto.setTaxonSeq(TAXONSEQ);

    GebiedDto gebiedDto = new GebiedDto();
    gebiedDto.setGebiedId(GEBIEDID);
    TaxonDto  taxonDto  = new TaxonDto();
    taxonDto.setTaxonId(TAXONID);

    fotoDto = new FotoDto();
    fotoDto.setFotoBestand(FOTOBESTAND);
    fotoDto.setFotoDetail(FOTODETAIL);
    fotoDto.setFotoId(FOTOID);
    fotoDto.setGebied(gebiedDto);
    fotoDto.setOpmerking(OPMERKING);
    fotoDto.setTaxon(taxonDto);
    fotoDto.setTaxonSeq(TAXONSEQ);
  }

  @Test
  public void testCompareTo() {
    Foto  gelijk  = new Foto(foto);
    Foto  groter  = new Foto();
    groter.setFotoId(foto.getFotoId()+ 1);
    Foto  kleiner = new Foto();
    kleiner.setFotoId(foto.getFotoId()- 1);

    assertTrue(foto.compareTo(groter) < 0);
    assertTrue(foto.compareTo(gelijk) == 0);
    assertTrue(foto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Foto  object    = null;
    Foto  instance  = new Foto();

    assertFalse(foto.equals(object));
    assertFalse(foto.equals(instance));

    instance.setFotoId(foto.getFotoId());
    assertTrue(foto.equals(instance));

    instance  = new Foto(foto);
    assertTrue(foto.equals(instance));

    instance  = new Foto(fotoDto);
    assertTrue(foto.equals(instance));
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
  public void testGetGebied() {
    assertEquals(GEBIEDID, foto.getGebied().getGebiedId());
  }

  @Test
  public void testGetOpmerking() {
    assertEquals(OPMERKING, foto.getOpmerking());
  }

  @Test
  public void testGetTaxon() {
    assertEquals(TAXONID, foto.getTaxon().getTaxonId());
  }

  @Test
  public void testGetTaxonSeq() {
    assertEquals(TAXONSEQ, foto.getTaxonSeq());
  }

  @Test
  public void testHashCode() {
    assertEquals(FOTOID_HASH, foto.hashCode());
  }

  @Test
  public void testPersist() {
    FotoDto parameter = new FotoDto();
    Foto    instance  = new Foto();
    instance.persist(parameter);

    assertEquals(instance.getFotoBestand(), parameter.getFotoBestand());
    assertEquals(instance.getFotoDetail(), parameter.getFotoDetail());
    assertEquals(instance.getFotoId(), parameter.getFotoId());
    assertEquals(instance.getGebied(), parameter.getGebied());
    assertEquals(instance.getOpmerking(), parameter.getOpmerking());
    assertEquals(instance.getTaxon(), parameter.getTaxon());
    assertEquals(instance.getTaxonSeq(), parameter.getTaxonSeq());
  }

  @Test
  public void testSetFotoBestand() {
    Foto  instance  = new Foto();
    assertNotEquals(FOTOBESTAND, instance.getFotoBestand());
    instance.setFotoBestand(FOTOBESTAND);

    assertEquals(FOTOBESTAND, instance.getFotoBestand());
  }

  @Test
  public void testSetFotoDetail() {
    Foto  instance  = new Foto();
    assertNotEquals(FOTODETAIL, instance.getFotoDetail());
    instance.setFotoDetail(FOTODETAIL);

    assertEquals(FOTODETAIL, instance.getFotoDetail());
  }

  @Test
  public void testSetFotoId() {
    Foto  instance  = new Foto();
    assertNotEquals(FOTOID, instance.getFotoId());
    instance.setFotoId(FOTOID);

    assertEquals(FOTOID, instance.getFotoId());
  }

  @Test
  public void testSetGebied() {
    Foto  instance  = new Foto();
    assertNull(instance.getGebied());
    instance.setGebied(gebied);

    assertEquals(GEBIEDID, instance.getGebied().getGebiedId());
  }

  @Test
  public void testSetOpmerking() {
    Foto  instance  = new Foto();
    assertNotEquals(OPMERKING, instance.getOpmerking());
    instance.setOpmerking(OPMERKING);

    assertEquals(OPMERKING, instance.getOpmerking());
  }

  @Test
  public void testSetTaxon() {
    Foto  instance  = new Foto();
    assertNull(instance.getTaxon());
    instance.setTaxon(taxon);

    assertEquals(TAXONID, instance.getTaxon().getTaxonId());
  }

  @Test
  public void testSetTaxonSeq() {
    Foto  instance  = new Foto();
    assertNotEquals(TAXONSEQ, instance.getTaxonSeq());
    instance.setTaxonSeq(TAXONSEQ);

    assertEquals(TAXONSEQ, instance.getTaxonSeq());
  }
}
