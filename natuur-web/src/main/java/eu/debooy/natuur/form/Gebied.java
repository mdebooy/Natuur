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

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.GebiedDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Gebied
    extends Formulier implements Cloneable, Comparable<Gebied>, Serializable {
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

  public static class NaamComparator
      implements Comparator<Gebied>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Gebied gebied1, Gebied gebied2) {
      return gebied1.naam.compareTo(gebied2.naam);
    }
  }
  
  public Gebied clone() throws CloneNotSupportedException {
    Gebied clone = (Gebied) super.clone();

    return clone;
  }

  public int compareTo(Gebied andere) {
    return new CompareToBuilder().append(gebiedId, andere.gebiedId)
                                 .toComparison();
  }

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

  public Long getGebiedId() {
    return gebiedId;
  }

  public Long getLandId() {
    return landId;
  }

  public String getNaam() {
    return naam;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(gebiedId).toHashCode();
  }

  public boolean isGewijzigd() {
    return gewijzigd;
  }

  public void persist(GebiedDto parameter) {
    if (!new EqualsBuilder().append(gebiedId,
                                    parameter.getGebiedId()).isEquals()) {
      parameter.setGebiedId(gebiedId);
    }
    if (!new EqualsBuilder().append(landId,
                                    parameter.getLandId()).isEquals()) {
      parameter.setLandId(landId);
    }
    if (!new EqualsBuilder().append(naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(naam);
    }
  }

  public void setGebiedId(Long gebiedId) {
    if (!new EqualsBuilder().append(this.gebiedId, gebiedId).isEquals()) {
      gewijzigd     = true;
      this.gebiedId = gebiedId;
    }
  }

  public void setLandId(Long landId) {
    if (!new EqualsBuilder().append(this.landId, landId).isEquals()) {
      gewijzigd   = true;
      this.landId = landId;
    }
  }

  public void setNaam(String naam) {
    if (!new EqualsBuilder().append(this.naam, naam).isEquals()) {
      gewijzigd = true;
      this.naam = naam;
    }
  }
}
