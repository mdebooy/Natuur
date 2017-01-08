/**
 * Copyright 2016 Marco de Booij
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
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;


/**
 * Deze Entity is enkel read-only.
 * 
 * @author Marco de Booij
 */
@Entity
@Table(name="FOTO_OVERZICHT", schema="NATUUR")
public class FotoOverzichtDto
    extends Dto implements Comparable<FotoOverzichtDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Id
  @ReadOnly
  @Column(name="FOTO_ID")
  private Long    fotoId;
  @ReadOnly
  @Column(name="GEBIED")
  private String  gebied;
  @ReadOnly
  @Column(name="KLASSE_LATIJNSENAAM")
  private String  klasseLatijnsenaam;
  @ReadOnly
  @Column(name="KLASSE_NAAM")
  private String  klasseNaam;
  @ReadOnly
  @Column(name="LAND_ID")
  private Long    landId;
  @ReadOnly
  @Column(name="LATIJNSENAAM")
  private String  latijnsenaam;
  @ReadOnly
  @Column(name="NAAM")
  private String  naam;
  @ReadOnly
  @Column(name="TAXON_SEQ")
  private Long    taxonSeq;

  /**
   * Sorteren op de parentnaam en naam van het detail.
   */
  public static class LijstComparator
      implements Comparator<FotoOverzichtDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(FotoOverzichtDto fotoOverzichtDto1,
                       FotoOverzichtDto fotoOverzichtDto2) {
      return new CompareToBuilder().append(fotoOverzichtDto1.klasseNaam,
                                           fotoOverzichtDto2.klasseNaam)
                                   .append(fotoOverzichtDto1.naam,
                                           fotoOverzichtDto2.naam)
                                   .append(fotoOverzichtDto1.taxonSeq,
                                           fotoOverzichtDto2.taxonSeq)
                                   .toComparison();
    }
  }
  
  public FotoOverzichtDto clone() throws CloneNotSupportedException {
    FotoOverzichtDto  clone = (FotoOverzichtDto) super.clone();

    return clone;
  }

  public int compareTo(FotoOverzichtDto fotoOverzichtDto) {
    return new CompareToBuilder().append(fotoId, fotoOverzichtDto.fotoId)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof FotoOverzichtDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    FotoOverzichtDto  fotoOverzichtDto  = (FotoOverzichtDto) object;

    return new EqualsBuilder().append(fotoId, fotoOverzichtDto.fotoId)
                              .isEquals();
  }

  public Long getFotoId() {
    return fotoId;
  }

  public String getGebied() {
    return gebied;
  }

  public String getKlasseLatijnsenaam() {
    return klasseLatijnsenaam;
  }

  public String getKlasseNaam() {
    return klasseNaam;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public String getNaam() {
    return naam;
  }

  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }
}
