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

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.RegiolijstDto;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Regiolijst extends Formulier
    implements Comparable<Regiolijst>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Date    datum;
  private String  omschrijving;
  private Long    regioId;

  public Regiolijst() {}

  public Regiolijst(RegiolijstDto regiolijst) {
    datum         = regiolijst.getDatum();
    omschrijving  = regiolijst.getOmschrijving();
    regioId       = regiolijst.getRegioId();
  }

  @Override
  public int compareTo(Regiolijst regiolijst) {
    return new CompareToBuilder().append(regioId, regiolijst.regioId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Regiolijst)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regiolijst  = (Regiolijst) object;
    return new EqualsBuilder().append(regioId, regiolijst.regioId).isEquals();
  }

  public Date getDatum() {
    if (null == datum) {
      return null;
    }

    return new Date(datum.getTime());
  }

  public String getOmschrijving() {
    return omschrijving;
  }

  public Long getRegioId() {
    return regioId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).toHashCode();
  }

  public void persist(RegiolijstDto regiolijstDto) {
    regiolijstDto.setDatum(datum);
    regiolijstDto.setOmschrijving(omschrijving);
    regiolijstDto.setRegioId(regioId);
  }

  public void setDatum(Date datum) {
    if (null == datum) {
      this.datum      = null;
    } else {
      this.datum      = new Date(datum.getTime());
    }
  }

  public void setOmschrijving(String omschrijving) {
    this.omschrijving = omschrijving;
  }

  public void setRegioId(Long regioId) {
    this.regioId      = regioId;
  }

}
