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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.test.TestUtils;
import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.NatuurTestUtils;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import java.text.ParseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class FotoOverzichtTest {
  private static  FotoOverzicht    fotoOverzicht;
  private static  FotoOverzichtDto fotoOverzichtDto;

  @BeforeClass
  public static void setUpClass()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException, ParseException {
    fotoOverzichtDto  = NatuurTestUtils.getFotoOverzichtDto();
    fotoOverzicht     = new FotoOverzicht(fotoOverzichtDto);
  }

  @Test
  public void testCompareTo()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException, ParseException {
    var hulp    = NatuurTestUtils.getFotoOverzichtDto();
    var gelijk  = new FotoOverzicht(hulp);
    TestUtils.setField(hulp,
                       FotoOverzichtDto.COL_FOTOID,
                       NatuurTestConstants.FOTOID+1);
    var groter  = new FotoOverzicht(hulp);
    TestUtils.setField(hulp,
                       FotoOverzichtDto.COL_FOTOID,
                       NatuurTestConstants.FOTOID-1);
    var kleiner = new FotoOverzicht(hulp);

    assertTrue(fotoOverzicht.compareTo(groter) < 0);
    assertEquals(0, fotoOverzicht.compareTo(gelijk));
    assertTrue(fotoOverzicht.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException, ParseException {
    var   hulp      = NatuurTestUtils.getFotoOverzichtDto();
    TestUtils.setField(hulp,
                       FotoOverzichtDto.COL_FOTOID,
                       NatuurTestConstants.FOTOID+1);
     var   instance  = new FotoOverzicht(fotoOverzichtDto);

    assertEquals(fotoOverzicht, fotoOverzicht);
    assertNotEquals(fotoOverzicht, null);
    assertNotEquals(fotoOverzicht, FotoOverzichtDto.COL_FOTOID);
    assertNotEquals(hulp, instance);

    instance  = new FotoOverzicht(fotoOverzichtDto);
    assertEquals(fotoOverzicht, instance);
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.FOTOID_HASH, fotoOverzicht.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new FotoOverzicht(fotoOverzichtDto);

    assertEquals(fotoOverzichtDto.getDatum(),         instance.getDatum());
    assertEquals(fotoOverzichtDto.getFotoBestand(),
                 instance.getFotoBestand());
    assertEquals(fotoOverzichtDto.getFotoDetail(),    instance.getFotoDetail());
    assertEquals(fotoOverzichtDto.getFotoId(),        instance.getFotoId());
    assertEquals(fotoOverzichtDto.getGebied(),        instance.getGebied());
    assertEquals(fotoOverzichtDto.getLandId(),        instance.getLandId());
    assertTrue(DoosUtils.isBlankOrNull(instance.getLandnaam()));
    assertEquals(fotoOverzichtDto.getLatijnsenaam(),
                 instance.getLatijnsenaam());
    assertEquals(fotoOverzichtDto.getLatijnsenaam(),  instance.getNaam());
    assertEquals(fotoOverzichtDto.getParentId(),      instance.getParentId());
    assertEquals(fotoOverzichtDto.getParentLatijnsenaam(),
                 instance.getParentLatijnsenaam());
    assertEquals(fotoOverzicht.getParentNaam(),       instance.getParentNaam());
    assertEquals(fotoOverzichtDto.getParentRang(),    instance.getParentRang());
    assertEquals(fotoOverzichtDto.getParentStatus(),
                 instance.getParentStatus());
    assertEquals(fotoOverzichtDto.getParentVolgnummer(),
                 instance.getParentVolgnummer());
    assertEquals(fotoOverzichtDto.getRang(),          instance.getRang());
    assertEquals(fotoOverzicht.getSorteerdatum(),
                 instance.getSorteerdatum());
    assertEquals(fotoOverzichtDto.getStatus(),        instance.getStatus());
    assertEquals(fotoOverzichtDto.getTaxonId(),       instance.getTaxonId());
    assertEquals(fotoOverzichtDto.getTaxonSeq(),      instance.getTaxonSeq());
    assertEquals(fotoOverzichtDto.getVolgnummer(),    instance.getVolgnummer());
  }

  @Test
  public void testInit2a() {
    var instance  = new FotoOverzicht(fotoOverzichtDto,
                                      NatuurTestConstants.TAAL);

    assertEquals(fotoOverzichtDto.getDatum(),         instance.getDatum());
    assertEquals(fotoOverzichtDto.getFotoBestand(),
                 instance.getFotoBestand());
    assertEquals(fotoOverzichtDto.getFotoDetail(),    instance.getFotoDetail());
    assertEquals(fotoOverzichtDto.getFotoId(),        instance.getFotoId());
    assertEquals(fotoOverzichtDto.getGebied(),        instance.getGebied());
    assertEquals(fotoOverzichtDto.getLandId(),        instance.getLandId());
    assertTrue(DoosUtils.isBlankOrNull(instance.getLandnaam()));
    assertEquals(fotoOverzichtDto.getLatijnsenaam(),
                 instance.getLatijnsenaam());
    assertEquals(NatuurTestConstants.TAXONNAAM,       instance.getNaam());
    assertEquals(fotoOverzichtDto.getParentId(),      instance.getParentId());
    assertEquals(fotoOverzichtDto.getParentLatijnsenaam(),
                 instance.getParentLatijnsenaam());
    assertEquals(fotoOverzicht.getParentNaam(),       instance.getParentNaam());
    assertEquals(fotoOverzichtDto.getParentRang(),    instance.getParentRang());
    assertEquals(fotoOverzichtDto.getParentStatus(),
                 instance.getParentStatus());
    assertEquals(fotoOverzichtDto.getParentVolgnummer(),
                 instance.getParentVolgnummer());
    assertEquals(fotoOverzichtDto.getRang(),          instance.getRang());
    assertEquals(fotoOverzicht.getSorteerdatum(),
                 instance.getSorteerdatum());
    assertEquals(fotoOverzichtDto.getStatus(),        instance.getStatus());
    assertEquals(fotoOverzichtDto.getTaxonId(),       instance.getTaxonId());
    assertEquals(fotoOverzichtDto.getTaxonSeq(),      instance.getTaxonSeq());
    assertEquals(fotoOverzichtDto.getVolgnummer(),    instance.getVolgnummer());
  }

  @Test
  public void testInit2b() {
    var instance  = new FotoOverzicht(fotoOverzichtDto,
                                      NatuurTestConstants.TAAL_FOUT);

    assertEquals(fotoOverzichtDto.getDatum(),         instance.getDatum());
    assertEquals(fotoOverzichtDto.getFotoBestand(),
                 instance.getFotoBestand());
    assertEquals(fotoOverzichtDto.getFotoDetail(),    instance.getFotoDetail());
    assertEquals(fotoOverzichtDto.getFotoId(),        instance.getFotoId());
    assertEquals(fotoOverzichtDto.getGebied(),        instance.getGebied());
    assertEquals(fotoOverzichtDto.getLandId(),        instance.getLandId());
    assertTrue(DoosUtils.isBlankOrNull(instance.getLandnaam()));
    assertEquals(fotoOverzichtDto.getLatijnsenaam(),
                 instance.getLatijnsenaam());
    assertEquals(fotoOverzichtDto.getLatijnsenaam(),  instance.getNaam());
    assertEquals(fotoOverzichtDto.getParentId(),      instance.getParentId());
    assertEquals(fotoOverzichtDto.getParentLatijnsenaam(),
                 instance.getParentLatijnsenaam());
    assertEquals(fotoOverzicht.getParentNaam(),       instance.getParentNaam());
    assertEquals(fotoOverzichtDto.getParentRang(),    instance.getParentRang());
    assertEquals(fotoOverzichtDto.getParentStatus(),
                 instance.getParentStatus());
    assertEquals(fotoOverzichtDto.getParentVolgnummer(),
                 instance.getParentVolgnummer());
    assertEquals(fotoOverzichtDto.getRang(),          instance.getRang());
    assertEquals(fotoOverzicht.getSorteerdatum(),
                 instance.getSorteerdatum());
    assertEquals(fotoOverzichtDto.getStatus(),        instance.getStatus());
    assertEquals(fotoOverzichtDto.getTaxonId(),       instance.getTaxonId());
    assertEquals(fotoOverzichtDto.getTaxonSeq(),      instance.getTaxonSeq());
    assertEquals(fotoOverzichtDto.getVolgnummer(),    instance.getVolgnummer());
  }

  @Test
  public void testInit3() {
    var instance  = new FotoOverzicht(fotoOverzichtDto,
                                      NatuurTestConstants.TAAL,
                                      NatuurTestConstants.NAAM);

    assertEquals(fotoOverzichtDto.getDatum(),         instance.getDatum());
    assertEquals(fotoOverzichtDto.getFotoBestand(),
                 instance.getFotoBestand());
    assertEquals(fotoOverzichtDto.getFotoDetail(),    instance.getFotoDetail());
    assertEquals(fotoOverzichtDto.getFotoId(),        instance.getFotoId());
    assertEquals(fotoOverzichtDto.getGebied(),        instance.getGebied());
    assertEquals(fotoOverzichtDto.getLandId(),        instance.getLandId());
    assertEquals(NatuurTestConstants.NAAM,            instance.getLandnaam());
    assertEquals(fotoOverzichtDto.getLatijnsenaam(),
                 instance.getLatijnsenaam());
    assertEquals(NatuurTestConstants.TAXONNAAM,       instance.getNaam());
    assertEquals(fotoOverzichtDto.getParentId(),      instance.getParentId());
    assertEquals(fotoOverzichtDto.getParentLatijnsenaam(),
                 instance.getParentLatijnsenaam());
    assertEquals(fotoOverzicht.getParentNaam(),       instance.getParentNaam());
    assertEquals(fotoOverzichtDto.getParentRang(),    instance.getParentRang());
    assertEquals(fotoOverzichtDto.getParentStatus(),
                 instance.getParentStatus());
    assertEquals(fotoOverzichtDto.getParentVolgnummer(),
                 instance.getParentVolgnummer());
    assertEquals(fotoOverzichtDto.getRang(),          instance.getRang());
    assertEquals(fotoOverzicht.getSorteerdatum(),
                 instance.getSorteerdatum());
    assertEquals(fotoOverzichtDto.getStatus(),        instance.getStatus());
    assertEquals(fotoOverzichtDto.getTaxonId(),       instance.getTaxonId());
    assertEquals(fotoOverzichtDto.getTaxonSeq(),      instance.getTaxonSeq());
    assertEquals(fotoOverzichtDto.getVolgnummer(),    instance.getVolgnummer());
  }
}
