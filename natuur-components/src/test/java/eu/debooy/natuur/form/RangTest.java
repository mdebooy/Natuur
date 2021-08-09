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
import static eu.debooy.natuur.TestConstants.RANGNAAM;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.RANG_HASH;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import static eu.debooy.natuur.TestConstants.TAAL;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.RangDto;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
    rangDto.setRangnamen(TestUtils.getRangnamen());
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Rang(rang);
    var groter  = new Rang();
    var kleiner = new Rang();

    groter.setRang(RANG_GR);
    kleiner.setRang(RANG_KL);

    assertTrue(rang.compareTo(groter) < 0);
    assertEquals(0, rang.compareTo(gelijk));
    assertTrue(rang.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Rang();

    assertEquals(rang, rang);
    assertNotEquals(rang, null);
    assertNotEquals(rang, RANGNAAM);
    assertNotEquals(rang, instance);

    instance.setRang(rang.getRang());
    assertEquals(rang, instance);

    instance  = new Rang(rang);
    assertEquals(rang, instance);

    instance  = new Rang(rangDto);
    assertEquals(rang, instance);
  }

  @Test
  public void testGetNaam() {
    assertEquals(RANG, rang.getNaam());
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
  public void testInit1() {
    var instance  = new Rang();

    assertNull(instance.getNiveau());
    assertNull(instance.getRang());
    assertNull(instance.getNaam());
  }

  @Test
  public void testInit2() {
    var instance  = new Rang(rang);

    assertEquals(NIVEAU, instance.getNiveau());
    assertEquals(RANG, instance.getRang());
    assertEquals(RANG, instance.getNaam());
  }

  @Test
  public void testInit3() {
    var instance  = new Rang(rangDto);

    assertEquals(NIVEAU, instance.getNiveau());
    assertEquals(RANG, instance.getRang());
    assertEquals(RANG, instance.getNaam());
  }

  @Test
  public void testInit4() {
    var instance  = new Rang(rangDto, TAAL);

    assertEquals(NIVEAU, instance.getNiveau());
    assertEquals(RANG, instance.getRang());
    assertEquals(RANGNAAM, instance.getNaam());
  }

  @Test
  public void testNiveauComparator() {
    var groter  = new Rang();
    var kleiner = new Rang();

    groter.setNiveau(NIVEAU + 1);
    groter.setRang(RANG_KL);

    kleiner.setNiveau(NIVEAU - 1);
    kleiner.setRang(RANG);

    Set<Rang> rangen  = new TreeSet<>(new Rang.NiveauComparator());
    rangen.add(groter);
    rangen.add(rang);
    rangen.add(kleiner);

    var tabel = new Rang[rangen.size()];
    System.arraycopy(rangen.toArray(), 0, tabel, 0, rangen.size());
    assertEquals(kleiner.getNiveau(), tabel[0].getNiveau());
    assertEquals(rang.getNiveau(), tabel[1].getNiveau());
    assertEquals(groter.getNiveau(), tabel[2].getNiveau());
  }

  @Test
  public void testPersist() {
    var parameter = new RangDto();
    var instance  = new Rang(rang);

    instance.persist(parameter);

    assertEquals(instance.getNiveau(), parameter.getNiveau());
    assertEquals(instance.getRang(), parameter.getRang());

    instance.persist(parameter);

    assertEquals(instance.getNiveau(), parameter.getNiveau());
    assertEquals(instance.getRang(), parameter.getRang());
  }

  @Test
  public void testSetNiveau() {
    var instance  = new Rang();
    assertNotEquals(NIVEAU, instance.getNiveau());
    instance.setNiveau(NIVEAU);

    assertNull(instance.getNaam());
    assertEquals(NIVEAU, instance.getNiveau());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetRang() {
    var instance  = new Rang();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertEquals(RANG, instance.getNaam());
    assertNull(instance.getNiveau());
    assertEquals(RANG, instance.getRang());
  }
}
