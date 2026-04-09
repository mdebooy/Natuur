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

import java.util.Date;

/**
 * @author Marco de Booij
 */
public class AantalPerRegio {
  private final Long  aantal;
  private final Date  einddatum;
  private final Long  gezien;
  private final Long  regioId;
  private final Long  regiolijstId;
  private final Date  startdatum;

  public AantalPerRegio(Long regiolijstId, Long regioId,
                        Date startdatum, Date einddatum,
                        Long aantal, Long gezien) {
    this.aantal       = aantal;
    if (null != einddatum) {
      this.einddatum  = new Date(einddatum.getTime());
    } else {
      this.einddatum  = null;
    }
    this.gezien       = gezien;
    this.regioId      = regioId;
    this.regiolijstId = regiolijstId;
    this.startdatum   = new Date(startdatum.getTime());
  }

  public Long getAantal() {
    return aantal;
  }

  public Date getEinddatum() {
    if (null == einddatum) {
      return null;
    }

    return new Date(einddatum.getTime());
  }

  public Long getGezien() {
    return gezien;
  }

  public Long getRegioId() {
    return regioId;
  }

  public Long getRegiolijstId() {
    return regiolijstId;
  }

  public Date getStartdatum() {
    return new Date(startdatum.getTime());
  }
}
