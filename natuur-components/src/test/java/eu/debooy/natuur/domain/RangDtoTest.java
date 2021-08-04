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

import static eu.debooy.natuur.TestConstants.NIVEAU;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangDtoTest {
  private static final int    HASHCODE  = 4260;
  private static final String TOSTRING  =
      "RangDto (niveau=[3], rang=[ra], rangnamen=[[RangnaamDto (rang=[ra], naam=[taal en], taal=[en], logger=<null>, class=[class eu.debooy.natuur.domain.RangnaamDto]), RangnaamDto (rang=[ra], naam=[taal nl], taal=[nl], logger=<null>, class=[class eu.debooy.natuur.domain.RangnaamDto])]], logger=<null>, class=[class eu.debooy.natuur.domain.RangDto])";

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
  public void testRangDto() {
    RangDto rangDto = new RangDto();

    rangDto.setNiveau(NIVEAU);
    rangDto.setRang(RANG);
    rangDto.setRangnamen(rangnamen);

    assertEquals("Niveau", NIVEAU, rangDto.getNiveau());
    assertEquals("Rang", RANG, rangDto.getRang());
    assertEquals("Namen", 2, rangDto.getRangnamen().size());
    assertTrue(TAAL, rangDto.hasRangnaam(TAAL));
    assertTrue(TAAL_KL, rangDto.hasRangnaam(TAAL_KL));
    assertEquals("Naam " + TAAL, "taal " + TAAL, rangDto.getRangnaam(TAAL).getNaam());
    assertEquals("Naam " + TAAL_KL, "taal " + TAAL_KL, rangDto.getRangnaam(TAAL_KL).getNaam());
    assertEquals("toString", TOSTRING, rangDto.toString());
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
}
