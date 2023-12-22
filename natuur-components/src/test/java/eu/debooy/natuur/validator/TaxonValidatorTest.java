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
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
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
  }

  @Test
  public void testNullTaxonDto() {
    TaxonDto      taxon     = null;
    List<Message> result    = TaxonValidator.valideer(taxon);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
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
}
