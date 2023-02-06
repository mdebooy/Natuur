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
import eu.debooy.natuur.domain.RegiolijstDto;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class RegiolijstTest {
  private static  Date          datum;
  private static  Regiolijst    regiolijst;
  private static  RegiolijstDto regiolijstDto;

  @BeforeClass
  public static void setUpClass() {
    datum         = new Date();
    regiolijst    = new Regiolijst();
    regiolijstDto = new RegiolijstDto();

    regiolijst.setDatum(datum);
    regiolijst.setOmschrijving(TestConstants.OMSCHRIJVING);
    regiolijst.setRegioId(TestConstants.REGIOID);

    regiolijst.persist(regiolijstDto);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Regiolijst();
    var groter  = new Regiolijst();
    var kleiner = new Regiolijst();

    gelijk.setRegioId(regiolijst.getRegioId());
    groter.setRegioId(regiolijst.getRegioId() + 1);
    kleiner.setRegioId(regiolijst.getRegioId() - 1);

    assertTrue(regiolijst.compareTo(groter) < 0);
    assertEquals(0, regiolijst.compareTo(gelijk));
    assertTrue(regiolijst.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Regiolijst();

    assertEquals(regiolijst, regiolijst);
    assertNotEquals(regiolijst, null);
    assertNotEquals(regiolijst, TestConstants.RANGNAAM);
    assertNotEquals(regiolijst, instance);

    instance.setRegioId(regiolijst.getRegioId());
    assertEquals(regiolijst, instance);

    instance  = new Regiolijst(regiolijstDto);
    assertEquals(regiolijst, instance);
  }

  @Test
  public void testGetDatum() {
    assertEquals(datum, regiolijst.getDatum());
  }

  @Test
  public void testGetOmschrijving() {
    assertEquals(TestConstants.OMSCHRIJVING, regiolijst.getOmschrijving());
  }

  @Test
  public void testGetRegioId() {
    assertEquals(TestConstants.REGIOID, regiolijst.getRegioId());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.REGIOLIJST_HASH, regiolijst.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Regiolijst();

    assertNull(instance.getDatum());
    assertNull(instance.getOmschrijving());
    assertNull(instance.getRegioId());
  }

  @Test
  public void testInit2() {
    var instance  = new Regiolijst(regiolijstDto);

    assertEquals(regiolijstDto.getDatum(), instance.getDatum());
    assertEquals(regiolijstDto.getOmschrijving(), instance.getOmschrijving());
    assertEquals(regiolijstDto.getRegioId(), instance.getRegioId());
  }

  @Test
  public void testPersist() {
    var parameter = new RegiolijstDto();

    regiolijst.persist(parameter);

    assertEquals(regiolijst.getDatum(), parameter.getDatum());
    assertEquals(regiolijst.getOmschrijving(), parameter.getOmschrijving());
    assertEquals(regiolijst.getRegioId(), parameter.getRegioId());

    regiolijst.persist(parameter);

    assertEquals(regiolijst.getDatum(), parameter.getDatum());
    assertEquals(regiolijst.getOmschrijving(), parameter.getOmschrijving());
    assertEquals(regiolijst.getRegioId(), parameter.getRegioId());
  }

  @Test
  public void testSetDatum() {
    var instance  = new Regiolijst();

    assertNotEquals(datum, instance.getDatum());

    instance.setDatum(datum);

    assertEquals(datum, instance.getDatum());

    // Geen reference maar value?
    Date  datum2  = instance.getDatum();

    assertEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());

    datum2.setTime(0);

    assertNotEquals(datum2, instance.getDatum());
    assertEquals(datum, instance.getDatum());

    instance.setDatum(null);
    assertNull(instance.getDatum());
  }

  @Test
  public void testSetOmschrijving() {
    var instance  = new Regiolijst();

    assertNotEquals(TestConstants.OMSCHRIJVING, instance.getOmschrijving());

    instance.setOmschrijving(TestConstants.OMSCHRIJVING);

    assertEquals(TestConstants.OMSCHRIJVING, instance.getOmschrijving());

    instance.setOmschrijving(null);

    assertNull(instance.getOmschrijving());
  }

  @Test
  public void testSetRegioId() {
    var instance  = new Regiolijst();

    assertNotEquals(TestConstants.REGIOID, instance.getRegioId());

    instance.setRegioId(TestConstants.REGIOID);

    assertEquals(TestConstants.REGIOID, instance.getRegioId());
  }
}
