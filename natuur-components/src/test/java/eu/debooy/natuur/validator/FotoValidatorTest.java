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

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import static eu.debooy.natuur.TestConstants.REQ_GEBIEDID;
import static eu.debooy.natuur.TestConstants.REQ_TAXONID;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.form.Foto;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 *
 * @author booymar
 */
public class FotoValidatorTest {
  public static final Message REQ_TAXONSEQ  =
      new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                  "_I18N.label.seq");
  @Test
  public void testValideer_LegeFoto() {
    Foto          foto      = new Foto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_GEBIEDID);
    expResult.add(REQ_TAXONID);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideer_LegeFotoDto() {
    FotoDto       foto      = new FotoDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_GEBIEDID);
    expResult.add(REQ_TAXONID);

    List<Message> result    = FotoValidator.valideer(foto);
    assertEquals(expResult.toString(), result.toString());
  }
}
