/*
 * Copyright (c) 2025 Marco de Booij
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
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.domain.TaxonbeschrijvingDto;
import eu.debooy.natuur.form.Taxonbeschrijving;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Marco de Booij
 */
public class TaxonbeschrijvingValidatorTest {
  public static final Message ERR_BESCHRIJVING      =
      new Message.Builder()
                 .setAttribute(TaxonbeschrijvingDto.COL_BESCHRIJVING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
                    new Object[]{TaxonbeschrijvingValidator.LBL_BESCHRIJVING,
                                 4000})
                 .build();
  public static final Message ERR_BESCHRIJVINGTYPE  =
      new Message.Builder()
                 .setAttribute(TaxonbeschrijvingDto.COL_BESCHRIJVINGTYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(
        new Object[]{TaxonbeschrijvingValidator.LBL_BESCHRIJVINGTYPE, 10})
                 .build();
  public static final Message REQ_BESCHRIJVING      =
      new Message.Builder()
                 .setAttribute(TaxonbeschrijvingDto.COL_BESCHRIJVING)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
                    new Object[]{TaxonbeschrijvingValidator.LBL_BESCHRIJVING})
                 .build();
  public static final Message REQ_BESCHRIJVINGTYPE  =
      new Message.Builder()
                 .setAttribute(TaxonbeschrijvingDto.COL_BESCHRIJVINGTYPE)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(
        new Object[]{TaxonbeschrijvingValidator.LBL_BESCHRIJVINGTYPE})
                 .build();

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_BESCHRIJVING);
    expResult.add(ERR_BESCHRIJVINGTYPE);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_BESCHRIJVING);
    expResult.add(REQ_BESCHRIJVINGTYPE);
  }

  @Test
  public void testNullTaxonbeschrijving() {
    Taxonbeschrijving taxonbeschrijving = null;
    var               result            =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(Taxonbeschrijving.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testNullTaxonbeschrijvingDto() {
    TaxonbeschrijvingDto  taxonbeschrijving = null;
    var                   result            =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);

    assertEquals(1, result.size());
    assertEquals(PersistenceConstants.NULL, result.get(0).getMessage());
    assertEquals(TaxonbeschrijvingDto.class.getSimpleName(),
                 result.get(0).getAttribute());
  }

  @Test
  public void testValideerFouteTaxonbeschrijving() {
    var           taxonbeschrijving = new Taxonbeschrijving();
    List<Message> expResult         = new ArrayList<>();

    taxonbeschrijving.setBeschrijving(
        DoosUtils.stringMetLengte(NatuurTestConstants.BESCHRIJVING, 4001, "X"));
    taxonbeschrijving.setBeschrijvingtype(
        DoosUtils.stringMetLengte(NatuurTestConstants.BESCHRIJVINGTYPE,
                                  11, "X"));

    setFouten(expResult);

    var           result            =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteTaxonbeschrijvingDto() {
    var           taxonbeschrijving = new TaxonbeschrijvingDto();
    List<Message> expResult         = new ArrayList<>();

    taxonbeschrijving.setBeschrijving(
        DoosUtils.stringMetLengte(NatuurTestConstants.BESCHRIJVING, 4001, "X"));
    taxonbeschrijving.setBeschrijvingtype(
        DoosUtils.stringMetLengte(NatuurTestConstants.BESCHRIJVINGTYPE,
                                  11, "X"));

    setFouten(expResult);

    var           result            =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonbeschrijving() {
    var           taxonbeschrijving = new Taxonbeschrijving();
    List<Message> expResult         = new ArrayList<>();

    taxonbeschrijving.setBeschrijving(NatuurTestConstants.BESCHRIJVING);
    taxonbeschrijving.setBeschrijvingtype(NatuurTestConstants.BESCHRIJVINGTYPE);

    var           result    =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    assertEquals(expResult.toString(), result.toString());

    taxonbeschrijving.setBeschrijvingtype(NatuurTestConstants.BESCHRIJVINGTYPE
                                                             .toUpperCase());
    result  = TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonbeschrijvingDto() {
    var           taxonbeschrijving = new TaxonbeschrijvingDto();
    List<Message> expResult         = new ArrayList<>();

    taxonbeschrijving.setBeschrijving(NatuurTestConstants.BESCHRIJVING);
    taxonbeschrijving.setBeschrijvingtype(NatuurTestConstants.BESCHRIJVINGTYPE);

    var           result    =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    assertEquals(expResult.toString(), result.toString());

    taxonbeschrijving.setBeschrijvingtype(NatuurTestConstants.BESCHRIJVINGTYPE
                                                             .toUpperCase());
    result  = TaxonbeschrijvingValidator.valideer(taxonbeschrijving);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxonbeschrijving() {
    var           taxonbeschrijving = new Taxonbeschrijving();
    List<Message> expResult         = new ArrayList<>();

    setLeeg(expResult);

    var           result          =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);

    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxonbeschrijvingDto() {
    var           taxonbeschrijving = new TaxonbeschrijvingDto();
    List<Message> expResult         = new ArrayList<>();

    setLeeg(expResult);

    var           result            =
        TaxonbeschrijvingValidator.valideer(taxonbeschrijving);

    assertEquals(expResult.toString(), result.toString());
  }
}
