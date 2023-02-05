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
package eu.debooy.natuur.form;

import eu.debooy.natuur.TestConstants;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTaxonTest {
  private static  RegiolijstTaxon regiolijstTaxon;

  @BeforeClass
  public static void setUpClass() {
    regiolijstTaxon = new RegiolijstTaxon();
    regiolijstTaxon.setRegioId(TestConstants.REGIOID);
    regiolijstTaxon.setStatus(TestConstants.STATUS);
    regiolijstTaxon.setTaxonId(TestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    RegiolijstTaxon gelijk  = new RegiolijstTaxon();
    RegiolijstTaxon groter  = new RegiolijstTaxon();
    RegiolijstTaxon kleiner = new RegiolijstTaxon();

    gelijk.setRegioId(regiolijstTaxon.getRegioId());
    gelijk.setTaxonId(regiolijstTaxon.getTaxonId());
    groter.setRegioId(regiolijstTaxon.getRegioId() + 1);
    kleiner.setRegioId(regiolijstTaxon.getRegioId() - 1);

    assertTrue(regiolijstTaxon.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxon.compareTo(gelijk));
    assertTrue(regiolijstTaxon.compareTo(kleiner) > 0);

    groter.setRegioId(regiolijstTaxon.getRegioId());
    groter.setTaxonId(regiolijstTaxon.getTaxonId() + 1);
    kleiner.setRegioId(regiolijstTaxon.getRegioId());
    kleiner.setTaxonId(regiolijstTaxon.getTaxonId() - 1);

    assertTrue(regiolijstTaxon.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxon.compareTo(gelijk));
    assertTrue(regiolijstTaxon.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var             dto       = new RegiolijstTaxonDto();
    RegiolijstTaxon instance  = new RegiolijstTaxon();

    regiolijstTaxon.persist(dto);

    assertEquals(regiolijstTaxon, regiolijstTaxon);
    assertNotEquals(regiolijstTaxon, null);
    assertNotEquals(regiolijstTaxon, TestConstants.NAAM);
    assertNotEquals(regiolijstTaxon, instance);

    instance.setRegioId(regiolijstTaxon.getRegioId());
    instance.setTaxonId(regiolijstTaxon.getTaxonId());
    assertEquals(regiolijstTaxon, instance);

    instance  = new RegiolijstTaxon(dto);
    assertEquals(regiolijstTaxon, instance);
  }

  @Test
  public void testGetRegioId() {
    assertEquals(TestConstants.REGIOID, regiolijstTaxon.getRegioId());
  }

  @Test
  public void testGetStatus() {
    assertEquals(TestConstants.STATUS, regiolijstTaxon.getStatus());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TestConstants.TAXONID, regiolijstTaxon.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.REGIOLIJSTTAXON_HASH,
                 regiolijstTaxon.hashCode());
  }

  @Test
  public void testPersist() {
    RegiolijstTaxonDto  parameter = new RegiolijstTaxonDto();

    regiolijstTaxon.persist(parameter);

    assertEquals(regiolijstTaxon.getRegioId(), parameter.getRegioId());
    assertEquals(regiolijstTaxon.getStatus(), parameter.getStatus());
    assertEquals(regiolijstTaxon.getTaxonId(), parameter.getTaxonId());

    regiolijstTaxon.persist(parameter);

    assertEquals(regiolijstTaxon.getRegioId(), parameter.getRegioId());
    assertEquals(regiolijstTaxon.getStatus(), parameter.getStatus());
    assertEquals(regiolijstTaxon.getTaxonId(), parameter.getTaxonId());
  }

  @Test
  public void testSetRegioId() {
    RegiolijstTaxon instance  = new RegiolijstTaxon();
    assertNotEquals(TestConstants.REGIOID, instance.getRegioId());
    instance.setRegioId(TestConstants.REGIOID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetStatus() {
    RegiolijstTaxon instance  = new RegiolijstTaxon();
    assertNotEquals(TestConstants.STATUS, instance.getStatus());
    instance.setStatus(TestConstants.STATUS);

    assertEquals(TestConstants.STATUS, instance.getStatus());
  }

  @Test
  public void testSetTaxonId() {
    RegiolijstTaxon instance  = new RegiolijstTaxon();
    assertNotEquals(TestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(TestConstants.TAXONID);

    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
  }
}
