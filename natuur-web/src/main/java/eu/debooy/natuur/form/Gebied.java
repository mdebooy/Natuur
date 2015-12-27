/**
 * Copyright 2015 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.natuur.form;

import eu.debooy.natuur.domain.GebiedDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Gebied implements Comparable<Gebied>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private Long    gebiedId;
  private Long    landId;
  private String  naam;

  public Gebied() {}

  public Gebied(GebiedDto gebiedDto) {
    gebiedId  = gebiedDto.getGebiedId();
    landId    = gebiedDto.getLandId();
    naam      = gebiedDto.getNaam();
  }

  /**
   * Sorteren op de naam van het gebied.
   */
  public static Comparator<Gebied> naamComparator  =
      new Comparator<Gebied>() {
    @Override
    public int compare(Gebied gebied1, Gebied gebied2) {
      return gebied1.naam.compareTo(gebied2.naam);
    }
  };

  @Override
  public int compareTo(Gebied andere) {
    return new CompareToBuilder().append(naam, andere.getNaam())
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Gebied)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Gebied  andere  = (Gebied) object;
    return new EqualsBuilder().append(gebiedId, andere.gebiedId).isEquals();
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
   * @return de gewijzigd
   */
  public boolean isGewijzigd() {
    return gewijzigd;
  }

  /**
   * Zet de gegevens in een GebiedDto
   *
   * @param GebiedDto
   */
  public void persist(GebiedDto parameter) {
    if (!new EqualsBuilder().append(gebiedId,
                                    parameter.getGebiedId()).isEquals()) {
      parameter.setGebiedId(gebiedId);
    }
    if (!new EqualsBuilder().append(this.landId,
                                    parameter.getLandId()).isEquals()) {
      parameter.setLandId(this.landId);
    }
    if (!new EqualsBuilder().append(this.naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(this.naam);
    }
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
