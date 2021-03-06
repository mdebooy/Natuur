/**
 * Copyright (c) 2015 Marco de Booij
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
import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="RANGEN", schema="NATUUR")
@NamedQuery(name="rangenVanaf", query="select r from RangDto r where r.niveau>:niveau")
public class RangDto extends Dto implements Comparable<RangDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_NIVEAU  = "niveau";
  public static final String  COL_RANG    = "rang";

  public static final String  PAR_NIVEAU  = "niveau";

  public static final String  QRY_VANAF = "rangenVanaf";

  @Column(name="NIVEAU", nullable=false, updatable=false)
  private Long    niveau;
  @Id
  @Column(name="RANG", length=3, nullable=false)
  private String  rang;

  public static class NiveauComparator
      implements Comparator<RangDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RangDto rangDto1, RangDto rangDto2) {
      return rangDto1.niveau.compareTo(rangDto2.niveau);
    }
  }

  @Override
  public int compareTo(RangDto rangDto) {
    return new CompareToBuilder().append(rang, rangDto.rang)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RangDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    RangDto rangDto  = (RangDto) object;
    return new EqualsBuilder().append(rang, rangDto.rang)
                              .isEquals();
  }

  public Long getNiveau() {
    return niveau;
  }

  public String getRang() {
    return rang;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(rang).toHashCode();
  }

  public void setNiveau(Long niveau) {
    this.niveau = niveau;
  }

  public void setRang(String rang) {
    this.rang = rang;
  }
}
