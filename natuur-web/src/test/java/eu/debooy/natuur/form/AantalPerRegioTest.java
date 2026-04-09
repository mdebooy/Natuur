/*
 * Copyright (c) 2024 Marco de Booij
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

import eu.debooy.natuur.NatuurTestConstants;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class AantalPerRegioTest {
  @Test
  public void testInit() {
    var datum     = new Date();
    var instance  = new AantalPerRegio(NatuurTestConstants.REGIOLIJSTID,
                                       NatuurTestConstants.REGIOID,
                                       datum,
                                       datum,
                                       NatuurTestConstants.AANTAL,
                                       NatuurTestConstants.GEZIEN);

    assertEquals(NatuurTestConstants.AANTAL, instance.getAantal());
    assertEquals(datum, instance.getStartdatum());
    assertEquals(datum, instance.getEinddatum());
    assertEquals(NatuurTestConstants.GEZIEN, instance.getGezien());
    assertEquals(NatuurTestConstants.REGIOID, instance.getRegioId());
    assertEquals(NatuurTestConstants.REGIOLIJSTID, instance.getRegiolijstId());
  }
}
