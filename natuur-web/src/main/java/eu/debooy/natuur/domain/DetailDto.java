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

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;

/**
 * Deze Entity is enkel read-only. Het voorziet in de mogelijkheid om enkel
 * bepaalde rangen te laten zien. Bijvoorbeeld een soort met zijn klasse.
 * 
 * @author Marco de Booij
 */
@Entity
@Table(name="DETAILS", schema="NATUUR")
@IdClass(DetailPK.class)
@NamedQueries({
  @NamedQuery(name="soortMetKlasse", query="select d from DetailDto d where d.parentRang='kl' and d.rang='so'"),
  @NamedQuery(name="totalen", query="select d.parentNaam as naam, d.parentLatijnsenaam as latijnsenaam, count(d.naam) as totaal from DetailDto d where d.parentRang=:groep and d.rang in ('so', 'oso') group by d.parentNaam, d.parentLatijnsenaam order by d.parentNaam, d.parentLatijnsenaam")})
public class DetailDto extends Dto implements Comparable<DetailDto> {
  private static final  long  serialVersionUID  = 1L;

  @ReadOnly
  @Column(name="LATIJNSENAAM", insertable= false, updatable=false)
  private String    latijnsenaam;
  @ReadOnly
  @Column(name="NAAM", insertable= false, updatable=false)
  private String    naam;
  @ReadOnly
  @Column(name="NIVEAU", insertable= false, updatable=false)
  private Long      niveau;
  @ReadOnly
  @Column(name="OPMERKING", insertable= false, updatable=false)
  private String    opmerking;
  @Id
  @ReadOnly
  @Column(name="PARENT_ID", insertable= false, updatable=false)
  private Long      parentId;
  @ReadOnly
  @Column(name="PARENT_NAAM", insertable= false, updatable=false)
  private String    parentNaam;
  @ReadOnly
  @Column(name="PARENT_LATIJNSENAAM", insertable= false, updatable=false)
  private String    parentLatijnsenaam;
  @ReadOnly
  @Column(name="PARENT_RANG", insertable= false, updatable=false)
  private String    parentRang;
  @ReadOnly
  @Column(name="RANG", insertable= false, updatable=false)
  private String    rang;
  @Id
  @ReadOnly
  @Column(name="TAXON_ID", insertable= false, updatable=false)
  private Long      taxonId;

  /**
   * Sorteren op de parentnaam en naam van het detail.
   */
  public static class LijstComparator
      implements Comparator<DetailDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.parentNaam,
                                           detailDto2.parentNaam)
                                   .append(detailDto1.naam, detailDto2.naam)
                                   .toComparison();
    }
  }

  /**
   * Sorteren op de parentnaam en naam van het detail.
   */
  public static class NaamComparator
      implements Comparator<DetailDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.naam, detailDto2.naam)
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(DetailDto detailDto) {
    return new CompareToBuilder().append(parentId, detailDto.parentId)
                                 .append(taxonId, detailDto.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof DetailDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    DetailDto detailDto = (DetailDto) object;
    return new EqualsBuilder().append(parentId, detailDto.parentId)
                              .append(taxonId, detailDto.taxonId)
                              .isEquals();
  }

  /**
   * @return de latijnsenaam
   */
  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  /**
   * @return de naam
   */
  public String getNaam() {
    return naam;
  }

  /**
   * @return de opmerking
   */
  public String getOpmerking() {
    return opmerking;
  }

  /**
   * @return de parentId
   */
  public Long getParentId() {
    return parentId;
  }

  /**
   * @return de parentNaam
   */
  public String getParentNaam() {
    return parentNaam;
  }

  /**
   * @return de parentLatijnsenaam
   */
  public String getParentLatijnsenaam() {
    return parentLatijnsenaam;
  }

  /**
   * @return de parentRang
   */
  public String getParentRang() {
    return parentRang;
  }

  /**
   * @return de rang
   */
  public String getRang() {
    return rang;
  }

  /**
   * @return de taxonId
   */
  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentId).append(taxonId).toHashCode();
  }
}
