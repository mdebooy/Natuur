/*
 * Copyright (c) 2021 Marco de Booij
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

import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.NAAM_GR;
import static eu.debooy.natuur.TestConstants.NAAM_KL;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANGNAAM_HASH;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import eu.debooy.natuur.domain.RangnaamDto;
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
public class RangnaamTest {
  private static  Rangnaam    rangnaam;
  private static  RangnaamDto rangnaamDto;

  @BeforeClass
  public static void setUpClass() {
    rangnaam     = new Rangnaam();
    rangnaam.setNaam(NAAM);
    rangnaam.setRang(RANG);
    rangnaam.setTaal(TAAL);

    rangnaamDto  = new RangnaamDto();
    rangnaamDto.setNaam(NAAM);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setTaal(TAAL);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Rangnaam(rangnaam);
    var groter  = new Rangnaam();
    groter.setRang(RANG_GR);
    var kleiner = new Rangnaam();
    kleiner.setRang(RANG_KL);

    assertTrue(rangnaam.compareTo(groter) < 0);
    assertEquals(0, rangnaam.compareTo(gelijk));
    assertTrue(rangnaam.compareTo(kleiner) > 0);

    groter.setRang(RANG_GR);
    groter.setTaal(TAAL_GR);
    kleiner.setRang(RANG_KL);
    kleiner.setTaal(TAAL_KL);

    assertTrue(rangnaam.compareTo(groter) < 0);
    assertEquals(0, rangnaam.compareTo(gelijk));
    assertTrue(rangnaam.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new Rangnaam();

    assertEquals(rangnaam, rangnaam);
    assertNotEquals(rangnaam, null);
    assertNotEquals(rangnaam, NAAM);
    assertNotEquals(rangnaam, instance);

    instance.setRang(rangnaam.getRang());
    instance.setTaal(rangnaam.getTaal());
    assertEquals(rangnaam, instance);

    instance  = new Rangnaam(rangnaam);
    assertEquals(rangnaam, instance);

    instance  = new Rangnaam(rangnaamDto);
    assertEquals(rangnaam, instance);
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, rangnaam.getNaam());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, rangnaam.getRang());
  }

  @Test
  public void testGetTaal() {
    assertEquals(TAAL, rangnaam.getTaal());
  }

  @Test
  public void testHashCode() {
    assertEquals(RANGNAAM_HASH, rangnaam.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Rangnaam();

    assertNull(instance.getNaam());
    assertNull(instance.getRang());
    assertNull(instance.getTaal());
  }

  @Test
  public void testInit2() {
    var instance  = new Rangnaam(rangnaam);

    assertEquals(rangnaam.getNaam(), instance.getNaam());
    assertEquals(rangnaam.getRang(), instance.getRang());
    assertEquals(rangnaam.getTaal(), instance.getTaal());
  }

  @Test
  public void testInit3() {
    var instance  = new Rangnaam(rangnaamDto);

    assertEquals(rangnaamDto.getNaam(), instance.getNaam());
    assertEquals(rangnaamDto.getRang(), instance.getRang());
    assertEquals(rangnaamDto.getTaal(), instance.getTaal());
  }

  @Test
  public void testNaamComparator() {
    var groter  = new Rangnaam();
    var kleiner = new Rangnaam();

    groter.setNaam(NAAM_GR);
    kleiner.setNaam(NAAM_KL);

    Set<Rangnaam> rangnamen = new TreeSet<>(new Rangnaam.NaamComparator());
    rangnamen.add(groter);
    rangnamen.add(rangnaam);
    rangnamen.add(kleiner);

    var tabel = new Rangnaam[rangnamen.size()];
    System.arraycopy(rangnamen.toArray(), 0, tabel, 0, rangnamen.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(rangnaam.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testPersist() {
    var parameter = new RangnaamDto();
    var instance  = new Rangnaam();

    instance.persist(parameter);

    assertEquals(instance.getNaam(), parameter.getNaam());
    assertEquals(instance.getRang(), parameter.getRang());
    assertEquals(instance.getTaal(), parameter.getTaal());

    instance.persist(parameter);

    assertEquals(instance.getNaam(), parameter.getNaam());
    assertEquals(instance.getRang(), parameter.getRang());
    assertEquals(instance.getTaal(), parameter.getTaal());
  }

  @Test
  public void testSetNaam() {
    var instance  = new Rangnaam();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
    assertNull(instance.getRang());
    assertNull(instance.getTaal());
  }

  @Test
  public void testSetRang() {
    var instance  = new Rangnaam();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getNaam());
    assertEquals(RANG, instance.getRang());
    assertNull(instance.getTaal());
  }

  @Test
  public void testSetTaal() {
    var instance  = new Rangnaam();
    assertNotEquals(TAAL, instance.getTaal());
    instance.setTaal(TAAL);

    assertNull(instance.getNaam());
    assertNull(instance.getRang());
    assertEquals(TAAL, instance.getTaal());
  }
}
