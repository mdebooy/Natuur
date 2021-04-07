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
package eu.debooy.natuur.form;

import static eu.debooy.natuur.TestConstants.NIVEAU;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.RANG_HASH;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import eu.debooy.natuur.domain.RangDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangTest {
  private static  Rang    rang;
  private static  RangDto rangDto;

  @BeforeClass
  public static void setUpClass() {
    rang    = new Rang();
    rang.setNiveau(NIVEAU);
    rang.setRang(RANG);

    rangDto = new RangDto();
    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
  }

  @Test
  public void testCompareTo() {
    Rang  gelijk  = new Rang(rang);
    Rang  groter  = new Rang();
    groter.setRang(RANG_GR);
    Rang  kleiner = new Rang();
    kleiner.setRang(RANG_KL);

    assertTrue(rang.compareTo(groter) < 0);
    assertEquals(rang.compareTo(gelijk), 0);
    assertTrue(rang.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Rang  object    = null;
    Rang  instance  = new Rang();

    assertNotEquals(rang, object);
    assertNotEquals(rang, instance);

    instance.setRang(rang.getRang());
    assertEquals(rang, instance);

    instance  = new Rang(rang);
    assertEquals(rang, instance);

    instance  = new Rang(rangDto);
    assertEquals(rang, instance);
  }

  @Test
  public void testGetNiveau() {
    assertEquals(NIVEAU, rang.getNiveau());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, rang.getRang());
  }

  @Test
  public void testHashCode() {
    assertEquals(RANG_HASH, rang.hashCode());
  }

  @Test
  public void testPersist() {
    RangDto parameter = new RangDto();
    Rang    instance  = new Rang();
    instance.persist(parameter);

    assertEquals(instance.getNiveau(), parameter.getNiveau());
    assertEquals(instance.getRang(), parameter.getRang());
  }

  @Test
  public void testSetNiveau() {
    Rang  instance  = new Rang();
    assertNotEquals(NIVEAU, instance.getNiveau());
    instance.setNiveau(NIVEAU);

    assertEquals(NIVEAU, instance.getNiveau());
  }

  @Test
  public void testSetRang() {
    Rang  instance  = new Rang();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertEquals(RANG, instance.getRang());
  }
}
