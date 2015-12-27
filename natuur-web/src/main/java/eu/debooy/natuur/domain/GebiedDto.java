/**
 * Copyright 2015 Marco de Booij
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

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
@Entity
@Table(name="GEBIEDEN", schema="NATUUR")
public class GebiedDto extends Dto implements Comparable<GebiedDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="GEBIED_ID", nullable=false, unique=true)
  private Long    gebiedId;
  @Column(name="LAND_ID", nullable=false)
  private Long    landId    = Long.valueOf(0);
  @Column(name="NAAM", length=225, nullable=false)
  private String  naam;

  /**
   * Sorteren op de naam van het gebied.
   */
  public static class NaamComparator implements Comparator<GebiedDto> {
    @Override
    public int compare(GebiedDto gebiedDto1, GebiedDto gebiedDto2) {
      return gebiedDto1.naam.compareTo(gebiedDto2.naam);
    }
  };

  @Override
  public GebiedDto clone() throws CloneNotSupportedException {
    GebiedDto  clone = (GebiedDto) super.clone();

    return clone;
  }

  @Override
  public int compareTo(GebiedDto gebiedDto) {
    return new CompareToBuilder().append(gebiedId,
                                         gebiedDto.gebiedId).toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof GebiedDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    GebiedDto gebiedDto  = (GebiedDto) object;
    return new EqualsBuilder().append(gebiedId, gebiedDto.gebiedId).isEquals();
  }

  /**
   * @return de gebiedId
   */
  public Long getGebiedId() {
    return gebiedId;
  }

  /**
   * @return de landId
   */
  public Long getLandId() {
    return landId;
  }

  /**
   * @return de naam
   */
  public String getNaam() {
    return naam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(gebiedId).toHashCode();
  }

  /**
   * @param gebiedId de waarde van gebiedId
   */
  public void setGebiedId(Long gebiedId) {
    this.gebiedId = gebiedId;
  }

  /**
   * @param landId de waarde van landId
   */
  public void setLandId(Long landId) {
    this.landId = landId;
  }

  /**
   * @param naam de waarde van naam
   */
  public void setNaam(String naam) {
    this.naam = naam;
  }
}
