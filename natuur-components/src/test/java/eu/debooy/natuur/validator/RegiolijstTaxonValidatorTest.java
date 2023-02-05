/*
 * Copyright (c) 2023 Marco de Booij
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
import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.form.RegiolijstTaxon;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RegiolijstTaxonValidatorTest {
  private static final  Message ERR_STATUS  =
      new Message.Builder()
                 .setAttribute(RegiolijstTaxonDto.COL_STATUS)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{RegiolijstTaxonValidator.LBL_STATUS,
                                         2})
                 .build();
  private static final  Message REQ_REGIOID =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_REGIOID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegiolijstTaxonValidator.LBL_REGIOID})
                 .build();
  private static final  Message REQ_TAXONID =
      new Message.Builder()
                 .setAttribute(RegiolijstDto.COL_REGIOID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{RegiolijstTaxonValidator.LBL_TAXONID})
                 .build();

  private static  RegiolijstTaxon     regiolijstTaxon;
  private static  RegiolijstTaxonDto  regiolijstTaxonDto;

  private static void setLeeg(List<Message> expResult) {
    expResult.add(REQ_REGIOID);
    expResult.add(REQ_TAXONID);
  }

  @BeforeClass
  public static void setUpClass() {
    regiolijstTaxon     = new RegiolijstTaxon();
    regiolijstTaxonDto  = new RegiolijstTaxonDto();

    regiolijstTaxon.setRegioId(TestConstants.REGIOID);
    regiolijstTaxon.setStatus(TestConstants.STATUS);
    regiolijstTaxon.setTaxonId(TestConstants.TAXONID);

    regiolijstTaxon.persist(regiolijstTaxonDto);
  }

  @Test
  public void testFouteRegiolijstTaxon() {
    var           instance  = new RegiolijstTaxon(regiolijstTaxonDto);

    instance.setStatus("XXX");

    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STATUS.toString(), result.get(0).toString());
  }

  @Test
  public void testFouteRegiolijstTaxonDto() {
    var           instance  = new RegiolijstTaxonDto();

    regiolijstTaxon.persist(instance);
    instance.setStatus("XXX");

    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);

    assertEquals(1, result.size());
    assertEquals(ERR_STATUS.toString(), result.get(0).toString());
  }

  @Test
  public void testGoedeRegiolijstTaxon1() {
    List<Message> result  = RegiolijstTaxonValidator.valideer(regiolijstTaxon);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstTaxon2() {
    var           instance  = new RegiolijstTaxon(regiolijstTaxonDto);

    instance.setStatus(DoosUtils.stringMetLengte(TestConstants.STATUS, 2, "X"));
    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstTaxonDto1() {
    var           instance  = new RegiolijstTaxonDto();

    regiolijstTaxon.persist(instance);
    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGoedeRegiolijstTaxonDto2() {
    var           instance  = new RegiolijstTaxonDto();

    regiolijstTaxon.persist(instance);
    instance.setStatus(DoosUtils.stringMetLengte(TestConstants.STATUS, 2, "X"));
    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testLegeRegiolijstTaxon() {
    var           instance  = new RegiolijstTaxon();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testLegeRegiolijstTaxonDto() {
    var           instance  = new RegiolijstTaxonDto();
    List<Message> expResult = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result    = RegiolijstTaxonValidator.valideer(instance);
    assertEquals(expResult.toString(), result.toString());
  }
}
