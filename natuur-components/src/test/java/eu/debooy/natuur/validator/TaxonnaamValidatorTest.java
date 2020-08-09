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
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_FOUT;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Taxonnaam;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonnaamValidatorTest {
  private static final  Message ERR_NAAM  =
      new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                  "_I18N.label.naam", 255);
  private static final  Message ERR_TAAL  =
      new Message(Message.ERROR, PersistenceConstants.FIXLENGTH,
                  "_I18N.label.taal", 2);

  private static final  Message REQ_NAAM  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.naam");
  private static final  Message REQ_TAAL  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.taal");

  @Test
  public void testValideerFouteTaxonnaam() {
    Taxonnaam     taxonnaam = new Taxonnaam();
    List<Message> expResult = new ArrayList<>();

    taxonnaam.setNaam(DoosUtils.stringMetLengte(NAAM, 256, "X"));
    taxonnaam.setTaal(TAAL_FOUT);

    expResult.add(ERR_NAAM);
    expResult.add(ERR_TAAL);

    List<Message> result    = TaxonnaamValidator.valideer(taxonnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonnaam() {
    Taxonnaam     taxonnaam = new Taxonnaam();
    List<Message> expResult = new ArrayList<>();

    taxonnaam.setNaam(NAAM);
    taxonnaam.setTaal(TAAL);

    List<Message> result    = TaxonnaamValidator.valideer(taxonnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxonnaam() {
    Taxonnaam     taxonnaam = new Taxonnaam();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_NAAM);
    expResult.add(REQ_TAAL);

    List<Message> result    = TaxonnaamValidator.valideer(taxonnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteTaxonnaamDto() {
    TaxonnaamDto  taxonnaam = new TaxonnaamDto();
    List<Message> expResult = new ArrayList<>();

    taxonnaam.setNaam(DoosUtils.stringMetLengte(NAAM, 256, "X"));
    taxonnaam.setTaal(TAAL_FOUT);

    expResult.add(ERR_NAAM);
    expResult.add(ERR_TAAL);

    List<Message> result    = TaxonnaamValidator.valideer(taxonnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeTaxonnaamDto() {
    TaxonnaamDto  taxonnaam = new TaxonnaamDto();
    List<Message> expResult = new ArrayList<>();

    taxonnaam.setNaam(NAAM);
    taxonnaam.setTaal(TAAL);

    List<Message> result    = TaxonnaamValidator.valideer(taxonnaam);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeTaxonnaamDto() {
    TaxonnaamDto  taxonnaam = new TaxonnaamDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_NAAM);
    expResult.add(REQ_TAAL);

    List<Message> result    = TaxonnaamValidator.valideer(taxonnaam);
    assertEquals(expResult.toString(), result.toString());
  }
}
