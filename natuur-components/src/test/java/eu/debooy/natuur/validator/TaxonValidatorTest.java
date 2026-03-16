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
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAM)
                 .build();
  private static final  Message ERR_RANG          =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_RANG)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_PARENTNIVEAU)
                 .setParams(new Object[]{NatuurTestConstants.RANGNAAM,
                                         NatuurTestConstants.PARENTRANGNAAM})
                 .build();
  private static final  Message ERR_SOORT0        =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAMANDERS)
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
                 .setMessage(TaxonValidator.ERR_LATIJNSENAAM)
                 .build();
  private static final  Message REQ_LATIJNSENAAM  =
      new Message.Builder()
                 .setAttribute(TaxonDto.COL_LATIJNSENAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{TaxonValidator.LBL_LATIJNSENAAM})
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
    expResult.add(NatuurTestConstants.ERR_OPMERKING);
    expResult.add(NatuurTestConstants.ERR_RANG);
    expResult.add(REQ_VOLGNUMMER);
    expResult.add(ERR_SOORT0);
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
  public void testValideerFouteLatijnsenaam01() {
    var instance  = NatuurTestUtils.getOndersoortTaxon();

    instance.setRang(NatuurConstants.RANG_GESLACHT);

    var result    = TaxonValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_SOORT0.toString(), result.get(0).toString());
  }

  @Test
  // Test om te zien of de latijnsenaam intern wordt goedgezet.
  public void testValideerFouteLatijnsenaam02() {
    var instance  = NatuurTestUtils.getGeslachtTaxon();

    instance.setLatijnsenaam(instance.getLatijnsenaam().toUpperCase());

    var result    = TaxonValidator.valideer(instance);

    assertEquals(0, result.size());
  }

  @Test
  public void testValideerFouteLatijnsenaam11() {
    try {
      var instance  = NatuurTestUtils.getOndersoortTaxonDto();

      instance.setRang(NatuurConstants.RANG_GESLACHT);

      var result    = TaxonValidator.valideer(instance);

      assertEquals(1, result.size());
      assertEquals(ERR_SOORT0.toString(), result.get(0).toString());
    } catch (IllegalArgumentException | IllegalAccessException
            | NoSuchFieldException e) {
      fail("Geen Exception verwacht: " + e.getLocalizedMessage());
    }
  }

  @Test
  // Test om te zien of de latijnsenaam intern wordt goedgezet.
  public void testValideerFouteLatijnsenaam12() throws IllegalArgumentException {
    try {
      var instance  = NatuurTestUtils.getGeslachtTaxonDto();

      instance.setLatijnsenaam(instance.getLatijnsenaam().toUpperCase());

      var result    = TaxonValidator.valideer(instance);

      assertEquals(0, result.size());
    } catch (IllegalArgumentException | IllegalAccessException
            | NoSuchFieldException e) {
      fail("Geen Exception verwacht: " + e.getLocalizedMessage());
    }
  }

  @Test
  public void testValideerFouteOndersoort2() {
    var ondersoort  = NatuurTestUtils.getTaxonOndersoort();

    ondersoort.setParentLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR);

    var result  = TaxonValidator.valideer(ondersoort);

    assertEquals(1, result.size());
    assertEquals(ERR_ONDERSOORT2.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteRang() {
    var soort = NatuurTestUtils.getTaxon();

    soort.setParentNiveau(NatuurTestConstants.ONDERSOORTNIVEAU);

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

    soort.setParentLatijnsenaam(NatuurTestConstants.LATIJNSENAAM_GR
                                                   .split(" ")[0]);

    var result  = TaxonValidator.valideer(soort);

    assertEquals(1, result.size());
    assertEquals(ERR_SOORT2.toString(), result.get(0).toString());
  }

  @Test
  public void testValideerFouteTaxon() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(DoosUtils.stringMetLengte(NatuurTestConstants.LATIJNSENAAM,
                                                    256, "X"));
    taxon.setOpmerking(DoosUtils.stringMetLengte(NatuurTestConstants.OPMERKING,
                                                 2001, "X"));
    taxon.setRang(NatuurTestConstants.RANG_FOUT);
    taxon.setVolgnummer(null);

    setFoutenList(expResult);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteTaxonDto() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(DoosUtils.stringMetLengte(NatuurTestConstants.LATIJNSENAAM,
                                                    256, "X"));
    taxon.setOpmerking(DoosUtils.stringMetLengte(NatuurTestConstants.OPMERKING,
                                                 2001, "X"));
    taxon.setRang(NatuurTestConstants.RANG_FOUT);
    taxon.setVolgnummer(null);

    setFoutenList(expResult);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxon1() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setParentLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);
    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setRang(NatuurTestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxon2() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setParentLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);
    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM.toLowerCase());
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setRang(NatuurTestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxon3() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    taxon.setParentLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);
    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM.toUpperCase());
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setRang(NatuurTestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonDto1() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setRang(NatuurTestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonDto2() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM.toLowerCase());
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setRang(NatuurTestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonDto3() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM.toUpperCase());
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setRang(NatuurTestConstants.RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxon() {
    var           taxon     = new Taxon();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_LATIJNSENAAM);
    expResult.add(NatuurTestConstants.REQ_RANG);

    var           result    = TaxonValidator.valideer(taxon);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxonDto() {
    var           taxon     = new TaxonDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_LATIJNSENAAM);
    expResult.add(NatuurTestConstants.REQ_RANG);

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
