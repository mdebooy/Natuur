/**
 * Copyright (c) 2023 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
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

import eu.debooy.doosutils.domain.Dto;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIOLIJSTEN", schema="NATUUR")
public class RegiolijstDto
    extends Dto implements Comparable<RegiolijstDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_DATUM         = "datum";
  public static final String  COL_OMSCHRIJVING  = "omschrijving";
  public static final String  COL_REGIOID       = "regioId";

  @Column(name="DATUM", nullable=false)
  private Date    datum;
  @Column(name="OMSCHRIJVING", length=2000)
  private String  omschrijving;
  @Id
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;

  @Override
  public int compareTo(RegiolijstDto regiolijstDto) {
    return new CompareToBuilder().append(regioId, regiolijstDto.regioId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegiolijstDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regiolijstDto = (RegiolijstDto) object;

    return new EqualsBuilder().append(regioId,
                                      regiolijstDto.regioId).isEquals();
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

  public void setDatum(Date datum) {
    if (null == datum) {
      this.datum        = null;
    } else {
      this.datum        = new Date(datum.getTime());
    }
  }

  public void setOmschrijving(String omschrijving) {
    if (null == omschrijving) {
      this.omschrijving = null;
    } else {
      this.omschrijving = omschrijving;
    }
  }

  public void setRegioId(Long regioId) {
    this.regioId        = regioId;
  }
}
