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
package eu.debooy.natuur.domain;

import eu.debooy.natuur.TestConstants;
import static eu.debooy.natuur.TestConstants.RANGNAAM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTaxonDtoTest {
  private static  RegiolijstTaxonDto  regiolijstTaxonDto;

  @BeforeClass
  public static void beforeClass() {
    regiolijstTaxonDto  = new RegiolijstTaxonDto();

    regiolijstTaxonDto.setRegioId(TestConstants.REGIOID);
    regiolijstTaxonDto.setStatus(TestConstants.STATUS);
    regiolijstTaxonDto.setTaxonId(TestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RegiolijstTaxonDto();
    var groter  = new RegiolijstTaxonDto();
    var kleiner = new RegiolijstTaxonDto();

    gelijk.setRegioId(regiolijstTaxonDto.getRegioId());
    gelijk.setTaxonId(regiolijstTaxonDto.getTaxonId());
    groter.setRegioId(regiolijstTaxonDto.getRegioId());
    groter.setTaxonId(regiolijstTaxonDto.getTaxonId() + 1);
    kleiner.setRegioId(regiolijstTaxonDto.getRegioId());
    kleiner.setTaxonId(regiolijstTaxonDto.getTaxonId() - 1);

    assertTrue(regiolijstTaxonDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonDto.compareTo(gelijk));
    assertTrue(regiolijstTaxonDto.compareTo(kleiner) > 0);

    groter.setRegioId(regiolijstTaxonDto.getRegioId() + 1);
    groter.setTaxonId(regiolijstTaxonDto.getTaxonId());
    kleiner.setRegioId(regiolijstTaxonDto.getRegioId() - 1);
    kleiner.setTaxonId(regiolijstTaxonDto.getTaxonId());

    assertTrue(regiolijstTaxonDto.compareTo(groter) < 0);
    assertEquals(0, regiolijstTaxonDto.compareTo(gelijk));
    assertTrue(regiolijstTaxonDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RegiolijstTaxonDto();

    assertEquals(regiolijstTaxonDto, regiolijstTaxonDto);
    assertNotEquals(regiolijstTaxonDto, null);
    assertNotEquals(regiolijstTaxonDto, RANGNAAM);
    assertNotEquals(regiolijstTaxonDto, instance);

    instance.setRegioId(regiolijstTaxonDto.getRegioId());
    instance.setTaxonId(regiolijstTaxonDto.getTaxonId());
    assertEquals(regiolijstTaxonDto, instance);

    instance.setTaxonId(regiolijstTaxonDto.getTaxonId() + 1);
    assertNotEquals(regiolijstTaxonDto, instance);

    instance.setRegioId(regiolijstTaxonDto.getRegioId() + 1);
    instance.setTaxonId(regiolijstTaxonDto.getTaxonId());
    assertNotEquals(regiolijstTaxonDto, instance);
  }

  @Test
  public void testGetRegio() {
    assertEquals(TestConstants.REGIOID, regiolijstTaxonDto.getRegioId());
  }

  @Test
  public void testGetTaal() {
    assertEquals(TestConstants.STATUS, regiolijstTaxonDto.getStatus());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TestConstants.TAXONID, regiolijstTaxonDto.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.REGIOLIJSTTAXON_HASH,
                 regiolijstTaxonDto.hashCode());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new RegiolijstTaxonDto();
    assertNotEquals(TestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(TestConstants.REGIOID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
  }

  @Test
  public void testSetStatus() {
    var instance  = new RegiolijstTaxonDto();
    assertNotEquals(TestConstants.STATUS, instance.getStatus());

    instance.setStatus(TestConstants.STATUS);

    assertEquals(TestConstants.STATUS, instance.getStatus());
  }

  @Test
  public void testSetTaxonId() {
    var instance  = new RegiolijstTaxonDto();
    assertNotEquals(TestConstants.TAXONID, instance.getTaxonId());

    instance.setTaxonId(TestConstants.TAXONID);

    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
  }
}
