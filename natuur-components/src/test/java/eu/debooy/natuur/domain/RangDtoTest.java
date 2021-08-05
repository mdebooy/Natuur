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
import static eu.debooy.natuur.TestConstants.RANG_GR;
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
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangDtoTest {
  private static final int    HASHCODE  = 4260;

  private static final Map<String, RangnaamDto>  rangnamen  = new HashMap<>();

  @BeforeClass
  public static void beforeClass() {
    var rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setNaam("taal " + TAAL);
    rangnamen.put(TAAL, rangnaamDto);
    rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL_KL);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setNaam("taal " + TAAL_KL);
    rangnamen.put(TAAL_KL, rangnaamDto);
  }

  @Test
  public void testCompareTo() {
    RangDto rangDto1  = new RangDto();
    RangDto rangDto2  = new RangDto();
    RangDto rangDto3  = new RangDto();

    rangDto1.setNiveau(NIVEAU);
    rangDto1.setRang(RANG);
    rangDto1.setRangnamen(rangnamen);

    rangDto2.setNiveau(NIVEAU);
    rangDto2.setRang(RANG_KL);
    rangDto2.setRangnamen(rangnamen);

    rangDto3.setNiveau(NIVEAU);
    rangDto3.setRang(RANG);
    rangDto3.setRangnamen(rangnamen);

    assertEquals("groter dan", 3, rangDto1.compareTo(rangDto2));
    assertEquals("kleiner dan", -3, rangDto2.compareTo(rangDto1));
    assertEquals("gelijk aan", 0, rangDto1.compareTo(rangDto3));
  }

  @Test
  public void testEquals() {
    var rangDto   = new RangDto();
    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    assertEquals("== this", rangDto, rangDto);

    var gelijk    = new RangDto();
    gelijk.setNiveau(NIVEAU);
    gelijk.setRang(RANG);
    assertEquals("equals1", gelijk, rangDto);

    gelijk.setRang(RANG_GR);
    assertNotEquals("!equals", gelijk, rangDto);

    gelijk.setNiveau(NIVEAU + 1);
    gelijk.setRang(RANG);
    assertEquals("equals2", gelijk, rangDto);
  }

  @Test
  public void testHashCode() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen);

    assertEquals("HashCode", HASHCODE, rangDto.hashCode());
  }

  @Test
  public void testNiveauComparator() {
    RangDto rangDto1  = new RangDto();
    RangDto rangDto2  = new RangDto();
    RangDto rangDto3  = new RangDto();

    rangDto1.setNiveau(NIVEAU);
    rangDto1.setRang(RANG);
    rangDto1.setRangnamen(rangnamen);

    rangDto2.setNiveau(NIVEAU + 1);
    rangDto2.setRang(RANG_KL);
    rangDto2.setRangnamen(rangnamen);

    rangDto3.setNiveau(NIVEAU - 1);
    rangDto3.setRang(RANG);
    rangDto3.setRangnamen(rangnamen);

    Set<RangDto>  rangen  = new TreeSet<>(new RangDto.NiveauComparator());
    rangen.add(rangDto1);
    rangen.add(rangDto2);
    rangen.add(rangDto3);

    RangDto[] tabel = new RangDto[rangen.size()];
    System.arraycopy(rangen.toArray(),0,tabel,0,rangen.size());
    assertEquals("rang[0]", Long.valueOf(NIVEAU - 1), tabel[0].getNiveau());
    assertEquals("rang[1]", NIVEAU, tabel[1].getNiveau());
    assertEquals("rang[2]", Long.valueOf(NIVEAU + 1), tabel[2].getNiveau());
  }

  @Test
  public void testRangDto1() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen);

    assertEquals("Niveau", NIVEAU, rangDto.getNiveau());
    assertEquals("Rang", RANG, rangDto.getRang());
    assertEquals("Namen", 2, rangDto.getRangnamen().size());
    assertTrue(TAAL, rangDto.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, rangDto.hasRangnaam(TAAL_KL));
    assertFalse(TAAL_GR, rangDto.hasRangnaam(TAAL_GR));
    assertEquals("taal onbekend", RANG, rangDto.getNaam(TAAL_GR));
    assertEquals("Naam " + TAAL, "taal " + TAAL, rangDto.getRangnaam(TAAL).getNaam());
    assertEquals("Naam " + TAAL_KL, "taal " + TAAL_KL, rangDto.getRangnaam(TAAL_KL).getNaam());
  }

  @Test
  public void testRangDto2() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen.values());

    assertEquals("Niveau", NIVEAU, rangDto.getNiveau());
    assertEquals("Rang", RANG, rangDto.getRang());
    assertEquals("Namen", 2, rangDto.getRangnamen().size());
    assertTrue(TAAL, rangDto.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, rangDto.hasRangnaam(TAAL_KL));
    assertFalse(TAAL_GR, rangDto.hasRangnaam(TAAL_GR));
    assertEquals("taal onbekend", RANG, rangDto.getNaam(TAAL_GR));
    assertEquals("Naam " + TAAL, "taal " + TAAL, rangDto.getRangnaam(TAAL).getNaam());
    assertEquals("Naam " + TAAL_KL, "taal " + TAAL_KL, rangDto.getRangnaam(TAAL_KL).getNaam());
  }

  @Test
  public void testRangnamen1() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen);

    assertEquals("Namen", 2, rangDto.getRangnamen().size());
    rangDto.removeRangnaam(TAAL_KL);
    assertEquals("Namen", 1, rangDto.getRangnamen().size());
  }

  @Test
  public void testRangnamen2() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen.values());

    assertEquals("Namen", 2, rangDto.getRangnamen().size());
    rangDto.removeRangnaam(TAAL_KL);
    assertEquals("Namen", 1, rangDto.getRangnamen().size());
  }

  @Test
  public void testRangnamen3() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);

    rangnamen.values().forEach(rangnaam ->  rangDto.addNaam(rangnaam));
    var rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL_GR);
    rangnaamDto.setNaam("taal " + TAAL_GR);
    rangDto.addNaam(rangnaamDto);

    assertEquals("Namen", 3, rangDto.getRangnamen().size());
    rangDto.removeRangnaam(TAAL_KL);
    assertEquals("Namen", 2, rangDto.getRangnamen().size());
    try {
      rangDto.removeRangnaam(TAAL_KL);
      Assert.fail("Geen throw ObjectNotFoundException");
    } catch (ObjectNotFoundException e) {
      // Is goed.
    }
  }
}
