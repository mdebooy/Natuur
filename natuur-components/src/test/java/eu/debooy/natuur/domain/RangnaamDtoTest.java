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
package eu.debooy.natuur.domain;

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
public class RangnaamDtoTest {
  private static  RangnaamDto rangnaamDto;

  @BeforeClass
  public static void setUpClass() {
    rangnaamDto  = new RangnaamDto();
    rangnaamDto.setNaam(NAAM);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setTaal(TAAL);
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RangnaamDto();
    var groter  = new RangnaamDto();
    var kleiner = new RangnaamDto();

    gelijk.setRang(RANG);
    gelijk.setTaal(TAAL);
    groter.setRang(RANG_GR);
    kleiner.setRang(RANG_KL);

    assertTrue(rangnaamDto.compareTo(groter) < 0);
    assertEquals(0, rangnaamDto.compareTo(gelijk));
    assertTrue(rangnaamDto.compareTo(kleiner) > 0);

    groter.setRang(RANG);
    groter.setTaal(TAAL_GR);
    kleiner.setRang(RANG);
    kleiner.setTaal(TAAL_KL);

    assertTrue(rangnaamDto.compareTo(groter) < 0);
    assertEquals(0, rangnaamDto.compareTo(gelijk));
    assertTrue(rangnaamDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RangnaamDto();

    assertEquals(rangnaamDto, rangnaamDto);
    assertNotEquals(rangnaamDto, null);
    assertNotEquals(rangnaamDto, NAAM);
    assertNotEquals(rangnaamDto, instance);

    instance.setRang(rangnaamDto.getRang());
    instance.setTaal(rangnaamDto.getTaal());
    assertEquals(rangnaamDto, instance);
  }

  @Test
  public void testGetNaam() {
    assertEquals(NAAM, rangnaamDto.getNaam());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, rangnaamDto.getRang());
  }

  @Test
  public void testGetTaal() {
    assertEquals(TAAL, rangnaamDto.getTaal());
  }

  @Test
  public void testHashCode() {
    assertEquals(RANGNAAM_HASH, rangnaamDto.hashCode());
  }

  @Test
  public void testNaamComparator() {
    var groter  = new RangnaamDto();
    var kleiner = new RangnaamDto();

    groter.setNaam(NAAM_GR);
    kleiner.setNaam(NAAM_KL);

    Set<RangnaamDto>  rangnamen =
        new TreeSet<>(new RangnaamDto.NaamComparator());
    rangnamen.add(groter);
    rangnamen.add(rangnaamDto);
    rangnamen.add(kleiner);

    var tabel = new RangnaamDto[rangnamen.size()];
    System.arraycopy(rangnamen.toArray(), 0, tabel, 0, rangnamen.size());
    assertEquals(kleiner.getNaam(), tabel[0].getNaam());
    assertEquals(rangnaamDto.getNaam(), tabel[1].getNaam());
    assertEquals(groter.getNaam(), tabel[2].getNaam());
  }

  @Test
  public void testSetNaam() {
    var instance  = new RangnaamDto();
    assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
    assertNull(instance.getRang());
    assertNull(instance.getTaal());
  }

  @Test
  public void testSetRang() {
    var instance  = new RangnaamDto();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getNaam());
    assertEquals(RANG, instance.getRang());
    assertNull(instance.getTaal());
  }

  @Test
  public void testSetTaal() {
    var instance  = new RangnaamDto();
    assertNotEquals(TAAL, instance.getTaal());
    instance.setTaal(TAAL);

    assertNull(instance.getNaam());
    assertNull(instance.getRang());
    assertEquals(TAAL, instance.getTaal());
  }
}
