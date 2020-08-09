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
package eu.debooy.natuur.validator;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import static eu.debooy.natuur.TestConstants.ERR_OPMERKING;
import static eu.debooy.natuur.TestConstants.FOTOBESTAND;
import static eu.debooy.natuur.TestConstants.FOTODETAIL;
import static eu.debooy.natuur.TestConstants.GEBIEDID;
import static eu.debooy.natuur.TestConstants.LANDID;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.LATITUDE;
import static eu.debooy.natuur.TestConstants.LATITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE;
import static eu.debooy.natuur.TestConstants.LONGITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.PARENTNAAM;
import static eu.debooy.natuur.TestConstants.PARENTVOLGNUMMER;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.REQ_GEBIEDID;
import static eu.debooy.natuur.TestConstants.REQ_TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.VOLGNUMMER;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author booymar
 */
public class FotoValidatorTest {
  public static final Message ERR_FOTOBESTAND =
      new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                             "_I18N.label.fotobestand", 255);
  public static final Message ERR_FOTODETAIL  =
      new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                             "_I18N.label.fotodetail", 20);

  public static final Message REQ_TAXONSEQ  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.seq");

  private static  Gebied    gebied;
  private static  GebiedDto gebiedDto;
  private static  Taxon     taxon;
  private static  TaxonDto  taxonDto;

  @BeforeClass
  public static void setUpClass() {
    gebied    = new Gebied();
    gebied.setGebiedId(GEBIEDID);
    gebied.setLandId(LANDID);
    gebied.setLatitude(LATITUDE);
    gebied.setLatitudeGraden(LATITUDE_GRADEN);
    gebied.setLatitudeMinuten(LATITUDE_MINUTEN);
    gebied.setLatitudeSeconden(LATITUDE_SECONDEN);
    gebied.setLongitude(LONGITUDE);
    gebied.setLongitudeGraden(LONGITUDE_GRADEN);
    gebied.setLongitudeMinuten(LONGITUDE_MINUTEN);
    gebied.setLongitudeSeconden(LONGITUDE_SECONDEN);
    gebied.setNaam(NAAM);

    gebiedDto = new GebiedDto();
    gebiedDto.setGebiedId(GEBIEDID);
    gebiedDto.setLandId(LANDID);
    gebiedDto.setLatitude(LATITUDE);
    gebiedDto.setLatitudeGraden(LATITUDE_GRADEN);
    gebiedDto.setLatitudeMinuten(LATITUDE_MINUTEN);
    gebiedDto.setLatitudeSeconden(LATITUDE_SECONDEN);
    gebiedDto.setLongitude(LONGITUDE);
    gebiedDto.setLongitudeGraden(LONGITUDE_GRADEN);
    gebiedDto.setLongitudeMinuten(LONGITUDE_MINUTEN);
    gebiedDto.setLongitudeSeconden(LONGITUDE_SECONDEN);
    gebiedDto.setNaam(NAAM);

    taxon     = new Taxon();
    taxon.setLatijnsenaam(LATIJNSENAAM);
    taxon.setNaam(NAAM);
    taxon.setOpmerking(OPMERKING);
    taxon.setParentId(PARENTID);
    taxon.setParentNaam(PARENTNAAM);
    taxon.setParentVolgnummer(PARENTVOLGNUMMER);
    taxon.setRang(RANG);
    taxon.setTaxonId(TAXONID);
    taxon.setVolgnummer(VOLGNUMMER);

    taxonDto  = new TaxonDto();
    taxonDto.setLatijnsenaam(LATIJNSENAAM);
    taxonDto.setOpmerking(OPMERKING);
    taxonDto.setParentId(PARENTID);
    taxonDto.setRang(RANG);
    taxonDto.setTaxonId(TAXONID);
    taxonDto.setVolgnummer(VOLGNUMMER);
  }

  @Test
  public void testValideerFouteFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(DoosUtils.stringMetLengte(FOTOBESTAND, 256, "X"));
    foto.setFotoDetail(DoosUtils.stringMetLengte(FOTODETAIL, 21, "X"));
    foto.setOpmerking(DoosUtils.stringMetLengte(OPMERKING, 2001, "X"));
    foto.setTaxonSeq(null);

    expResult.add(ERR_FOTOBESTAND);
    expResult.add(ERR_FOTODETAIL);
    expResult.add(REQ_GEBIEDID);
    expResult.add(ERR_OPMERKING);
    expResult.add(REQ_TAXONID);
    expResult.add(REQ_TAXONSEQ);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(FOTOBESTAND);
    foto.setFotoDetail(FOTODETAIL);
    foto.setGebied(gebied);
    foto.setOpmerking(OPMERKING);
    foto.setTaxon(taxon);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_GEBIEDID);
    expResult.add(REQ_TAXONID);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(DoosUtils.stringMetLengte(FOTOBESTAND, 256, "X"));
    foto.setFotoDetail(DoosUtils.stringMetLengte(FOTODETAIL, 21, "X"));
    foto.setOpmerking(DoosUtils.stringMetLengte(OPMERKING, 2001, "X"));
    foto.setTaxonSeq(null);

    expResult.add(ERR_FOTOBESTAND);
    expResult.add(ERR_FOTODETAIL);
    expResult.add(REQ_GEBIEDID);
    expResult.add(ERR_OPMERKING);
    expResult.add(REQ_TAXONID);
    expResult.add(REQ_TAXONSEQ);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    foto.setFotoBestand(FOTOBESTAND);
    foto.setFotoDetail(FOTODETAIL);
    foto.setGebied(gebiedDto);
    foto.setOpmerking(OPMERKING);
    foto.setTaxon(taxonDto);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_GEBIEDID);
    expResult.add(REQ_TAXONID);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }
}
