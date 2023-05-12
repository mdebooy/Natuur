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

/**
 * @author Marco de Booij
 */
public class AantalPerRegio {
  private final Long  aantal;
  private final Long  gezien;
  private final Long  regioId;

  public AantalPerRegio(Long regioId, Long aantal, Long gezien) {
    this.aantal   = aantal;
    this.gezien   = gezien;
    this.regioId  = regioId;
  }

  public Long getAantal() {
    return aantal;
  }

  public Long getGezien() {
    return gezien;
  }

  public Long getRegioId() {
    return regioId;
  }
}
