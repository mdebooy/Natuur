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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import static eu.debooy.natuur.TestConstants.NIVEAU;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANGNAAM;
import static eu.debooy.natuur.TestConstants.RANGNAAM_GR;
import static eu.debooy.natuur.TestConstants.RANGNAAM_KL;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.RANG_HASH;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_GR;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangDtoTest {
  private static  RangDto                   rangDto;
  private static  Map<String, RangnaamDto>  rangnamen;

  @BeforeClass
  public static void beforeClass() {
    rangDto   = new RangDto();
    rangnamen = new HashMap<>();

    var rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setNaam(RANGNAAM);
    rangnamen.put(TAAL, rangnaamDto);
    rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL_KL);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setNaam(RANGNAAM_KL);
    rangnamen.put(TAAL_KL, rangnaamDto);

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen);
  }

  @Test
  public void testAddNaam() {
    var instance = new RangDto();

    instance.setNiveau(NIVEAU);
    instance.setRang(RANG);

    rangnamen.values().forEach(rangnaam ->  instance.addNaam(rangnaam));

    var rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL_GR);
    rangnaamDto.setNaam(RANGNAAM_GR);
    instance.addNaam(rangnaamDto);

    assertEquals(3, instance.getRangnamen().size());
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new RangDto();
    var groter  = new RangDto();
    var kleiner = new RangDto();

    gelijk.setNiveau(NIVEAU);
    gelijk.setRang(RANG);
    gelijk.setRangnamen(rangnamen);

    groter.setNiveau(NIVEAU);
    groter.setRang(RANG_GR);
    groter.setRangnamen(rangnamen);

    kleiner.setNiveau(NIVEAU);
    kleiner.setRang(RANG_KL);
    kleiner.setRangnamen(rangnamen);

    assertTrue(rangDto.compareTo(groter) < 0);
    assertEquals(0, rangDto.compareTo(gelijk));
    assertTrue(rangDto.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    var instance  = new RangDto();

    assertEquals(rangDto, rangDto);
    assertNotEquals(rangDto, null);
    assertNotEquals(rangDto, RANGNAAM);
    assertNotEquals(rangDto, instance);

    instance.setRang(RANG);
    assertEquals(rangDto, instance);

    instance.setRang(RANG_GR);
    assertNotEquals(rangDto, instance);
  }

  @Test
  public void testGetNiveau() {
    assertEquals(NIVEAU, rangDto.getNiveau());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, rangDto.getRang());
  }

  @Test
  public void testGetNaam() {
    assertEquals(RANG, rangDto.getNaam(TAAL_GR));
    assertEquals(RANGNAAM, rangDto.getNaam(TAAL));
  }

  @Test
  public void testGetRangnaam() {
    assertNull(rangDto.getRangnaam(TAAL_GR).getRang());
    assertEquals(RANGNAAM, rangDto.getRangnaam(TAAL).getNaam());
    assertEquals(RANGNAAM_KL, rangDto.getRangnaam(TAAL_KL).getNaam());
  }

  @Test
  public void testGetRangnamen() {
    assertEquals(2, rangDto.getRangnamen().size());
    assertTrue(TAAL, rangDto.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, rangDto.hasRangnaam(TAAL_KL));
    assertFalse(TAAL_GR, rangDto.hasRangnaam(TAAL_GR));
  }

  @Test
  public void testHashCode() {
    assertEquals(RANG_HASH, rangDto.hashCode());
  }

  @Test
  public void testNiveauComparator() {
    var groter  = new RangDto();
    var kleiner = new RangDto();

    groter.setNiveau(NIVEAU + 1);
    groter.setRang(RANG_KL);
    groter.setRangnamen(rangnamen);

    kleiner.setNiveau(NIVEAU - 1);
    kleiner.setRang(RANG);
    kleiner.setRangnamen(rangnamen);

    Set<RangDto>  rangen  = new TreeSet<>(new RangDto.NiveauComparator());
    rangen.add(groter);
    rangen.add(rangDto);
    rangen.add(kleiner);

    var tabel = new RangDto[rangen.size()];
    System.arraycopy(rangen.toArray(), 0, tabel, 0, rangen.size());
    assertEquals(kleiner.getNiveau(), tabel[0].getNiveau());
    assertEquals(rangDto.getNiveau(), tabel[1].getNiveau());
    assertEquals(groter.getNiveau(), tabel[2].getNiveau());
  }

  @Test
  public void testRemoveRangnaam() {
    var instance  = new RangDto();

    instance.setNiveau(NIVEAU);
    instance.setRang(RANG);
    instance.setRangnamen(rangnamen);

    assertEquals(2, instance.getRangnamen().size());
    instance.removeRangnaam(TAAL_KL);
    assertEquals(1, instance.getRangnamen().size());
    try {
      instance.removeRangnaam(TAAL_KL);
      Assert.fail("Geen throw ObjectNotFoundException");
    } catch (ObjectNotFoundException e) {
      // Is goed.
    }
  }

  @Test
  public void testSetNiveau() {
    var instance  = new RangDto();
    assertNotEquals(NIVEAU, instance.getNiveau());

    instance.setNiveau(NIVEAU);

    assertEquals(NIVEAU, instance.getNiveau());
    assertNull(instance.getRang());
    assertTrue(instance.getRangnamen().isEmpty());
  }

  @Test
  public void testSetRang() {
    var instance  = new RangDto();
    assertNotEquals(RANG, instance.getRang());

    instance.setRang(RANG);

    assertNull(instance.getNiveau());
    assertEquals(RANG, instance.getRang());
    assertTrue(instance.getRangnamen().isEmpty());
}

  @Test
  public void testSetRangnamen1() {
    var instance    = new RangDto();
    var rangnaamDto = new RangnaamDto();

    assertTrue(instance.getRangnamen().isEmpty());

    instance.setRangnamen(rangnamen);
    rangnaamDto.setTaal(TAAL_GR);
    rangnaamDto.setNaam(RANGNAAM_GR);
    instance.addNaam(rangnaamDto);

    assertEquals(3, instance.getRangnamen().size());
    assertTrue(TAAL, instance.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, instance.hasRangnaam(TAAL_KL));
    assertTrue(TAAL_GR, instance.hasRangnaam(TAAL_GR));

    instance.setRangnamen(rangnamen);
    assertEquals(2, instance.getRangnamen().size());
    assertTrue(TAAL, instance.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, instance.hasRangnaam(TAAL_KL));
    assertFalse(TAAL_GR, instance.hasRangnaam(TAAL_GR));
  }

  @Test
  public void testSetRangnamen2() {
    var instance    = new RangDto();
    var rangnaamDto = new RangnaamDto();

    assertTrue(instance.getRangnamen().isEmpty());

    instance.setRangnamen(rangnamen.values());
    rangnaamDto.setTaal(TAAL_GR);
    rangnaamDto.setNaam(RANGNAAM_GR);
    instance.addNaam(rangnaamDto);

    assertEquals(3, instance.getRangnamen().size());
    assertTrue(TAAL, instance.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, instance.hasRangnaam(TAAL_KL));
    assertTrue(TAAL_GR, instance.hasRangnaam(TAAL_GR));

    instance.setRangnamen(rangnamen.values());
    assertEquals(2, instance.getRangnamen().size());
    assertTrue(TAAL, instance.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, instance.hasRangnaam(TAAL_KL));
    assertFalse(TAAL_GR, instance.hasRangnaam(TAAL_GR));
  }
}
