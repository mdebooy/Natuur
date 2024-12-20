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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonValidatorTest {
  private static final  Message ERR_LATIJNSENAAM  =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{TaxonValidator.LBL_LATIJNSENAAM, 255})
                 .build();
  private static final  Message ERR_ONDERSOORT1   =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAMONDERSOORT)
                 .build();
  private static final  Message ERR_ONDERSOORT2   =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAMFOUT)
                 .build();
  private static final  Message ERR_RANG          =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_RANG)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_PARENTNIVEAU)
                 .setParams(new Object[]{TestConstants.RANGNAAM,
                                         TestConstants.PARENTRANGNAAM})
                 .build();
  private static final  Message ERR_SOORT1        =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAMSOORT)
                 .build();
  private static final  Message ERR_SOORT2        =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAMFOUT)
                 .build();
  private static final  Message REQ_LATIJNSENAAM  =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{TaxonValidator.LBL_LATIJNSENAAM})
                 .build();
  private static final  Message REQ_UITGESTORVEN  =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_UITGESTORVEN)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{TaxonValidator.LBL_UITGESTORVEN})
                 .build();
  private static final  Message REQ_VOLGNUMMER    =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_VOLGNUMMER)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{TaxonValidator.LBL_VOLGNUMMER})
                 .build();

  private static void setFoutenList(List<Message> expResult) {
    expResult.add(ERR_LATIJNSENAAM);
    expResult.add(TestConstants.ERR_OPMERKING);
    expResult.add(TestConstants.ERR_RANG);
    expResult.add(REQ_VOLGNUMMER);
  }

  @Test
  public void testNullTaxon() {
    Taxon         taxon     = null;
    List<Message> result    = TaxonValidator.valideer(taxon);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Taxon.class.getSimpleName(), result.get(0).getAttribute());
  }

  @Test
  public void testNullTaxonDto() {
    TaxonDto      taxon     = null;
    List<Message> result    = TaxonValidator.valideer(taxon);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(TaxonDto.class.getSimpleName(), result.get(0).getAttribute());
  }

  @Test
  public void testValideerFouteOndersoort1() {
    var ondersoort  = NatuurTestUtils.getTaxon();

    ondersoort.setRang(NatuurConstants.RANG_ONDERSOORT);

    var result  = TaxonValidator.valideer(ondersoort);

    assertEquals(1, result.size());
    assertEquals(ERR_ONDERSOORT1.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteOndersoort2() {
    var ondersoort  = NatuurTestUtils.getTaxonOndersoort();

    ondersoort.setParentLatijnsenaam(TestConstants.LATIJNSENAAM_GR);

    var result  = TaxonValidator.valideer(ondersoort);

    assertEquals(1, result.size());
    assertEquals(ERR_ONDERSOORT2.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteRang() {
    var soort = NatuurTestUtils.getTaxon();

    soort.setParentNiveau(TestConstants.ONDERSOORTNIVEAU);

    var result  = TaxonValidator.valideer(soort);

    assertEquals(1, result.size());
    assertEquals(ERR_RANG.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteSoort1() {
    var soort = NatuurTestUtils.getParentTaxon();

    soort.setRang(NatuurConstants.RANG_SOORT);

    var result  = TaxonValidator.valideer(soort);

    assertEquals(1, result.size());
    assertEquals(ERR_SOORT1.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteSoort2() {
    var soort = NatuurTestUtils.getTaxon();

    soort.setParentLatijnsenaam(TestConstants.LATIJNSENAAM_GR.split(" ")[0]);

    var result  = TaxonValidator.valideer(soort);

    assertEquals(1, result.size());
    assertEquals(ERR_SOORT2.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteTaxon() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(DoosUtils.stringMetLengte(TestConstants.LATIJNSENAAM,
                                                    256, "X"));
    taxon.setOpmerking(DoosUtils.stringMetLengte(TestConstants.OPMERKING,
                                                 2001, "X"));
    taxon.setRang(TestConstants.RANG_FOUT);
    taxon.setVolgnummer(null);

    setFoutenList(expResult);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteTaxonDto() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(DoosUtils.stringMetLengte(TestConstants.LATIJNSENAAM,
                                                    256, "X"));
    taxon.setOpmerking(DoosUtils.stringMetLengte(TestConstants.OPMERKING,
                                                 2001, "X"));
    taxon.setRang(TestConstants.RANG_FOUT);
    taxon.setVolgnummer(null);

    setFoutenList(expResult);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteUitgestorven() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    taxon.setOpmerking(TestConstants.OPMERKING);
    taxon.setRang(TestConstants.RANG);
    taxon.setUitgestorven("");

    expResult.add(REQ_UITGESTORVEN);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());

    taxon.setUitgestorven("X");

    expResult.clear();
    expResult.add(REQ_UITGESTORVEN);

    result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxon() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setParentLatijnsenaam(TestConstants.PARENTLATIJNSENAAM);
    taxon.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    taxon.setOpmerking(TestConstants.OPMERKING);
    taxon.setRang(TestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonDto() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    taxon.setOpmerking(TestConstants.OPMERKING);
    taxon.setRang(TestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeUitgestorven() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    taxon.setOpmerking(TestConstants.OPMERKING);
    taxon.setRang(TestConstants.RANG);
    taxon.setUitgestorven(DoosConstants.WAAR);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());

    taxon.setUitgestorven(DoosConstants.ONWAAR);

    result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxon() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_LATIJNSENAAM);
    expResult.add(TestConstants.REQ_RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxonDto() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_LATIJNSENAAM);
    expResult.add(TestConstants.REQ_RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerOndersoort1() {
    var ondersoort  = NatuurTestUtils.getTaxonOndersoort();

    var result  = TaxonValidator.valideer(ondersoort);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerOndersoort2() {
    var ondersoort  = NatuurTestUtils.getTaxonOndersoort();

    ondersoort.setParentLatijnsenaam(null);

    var result  = TaxonValidator.valideer(ondersoort);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testValideerRang() {
    var soort = NatuurTestUtils.getTaxon();

    var result  = TaxonValidator.valideer(soort);

    assertTrue(result.isEmpty());

    soort.setParentNiveau(null);

    result  = TaxonValidator.valideer(soort);

    assertTrue(result.isEmpty());

    soort.setNiveau(null);

    result  = TaxonValidator.valideer(soort);

    assertTrue(result.isEmpty());
  }
}
