/**
 * Copyright 2017 Marco de Booij
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


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="TAXONNAMEN", schema="NATUUR")
@IdClass(TaxonnaamPK.class)
@NamedQueries({
  @NamedQuery(name="taxonnaamPerTaxon", query="select t from TaxonnaamDto t where t.taxonId=:taxonId"),
  @NamedQuery(name="taxonnaamPerTaal", query="select t from TaxonnaamDto t where t.taal=:taal")
})
public class TaxonnaamDto extends Dto
    implements Comparable<TaxonnaamDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  public static final String  QRY_TAXON = "taxonnaamPerTaxon";
  public static final String  QRY_TAAL  = "taxonnaamPerTaal";

  @Column(name="NAAM", length=155, nullable=false)
  private String  naam;
  @Id
  @Column(name="TAAL", length=2, nullable=false)
  private String  taal;
  @Id
  @Column(name="TAXON_ID", nullable=false)
  private Long    taxonId;

  public static class NaamComparator
      implements Comparator<TaxonnaamDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(TaxonnaamDto naamDto1,
                       TaxonnaamDto naamDto2) {
      return naamDto1.naam.compareTo(naamDto2.naam);
    }
  }
  
  public TaxonnaamDto clone() throws CloneNotSupportedException {
    TaxonnaamDto clone = (TaxonnaamDto) super.clone();

    return clone;
  }

  public int compareTo(TaxonnaamDto naamDto) {
    return new CompareToBuilder().append(taxonId, naamDto.taxonId)
                                 .append(taal, naamDto.taal)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof TaxonnaamDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    TaxonnaamDto naamDto = (TaxonnaamDto) object;
    return new EqualsBuilder().append(taxonId, naamDto.taxonId)
                              .append(taal, naamDto.taal)
                              .isEquals();
  }

  public String getTaal() {
    return taal;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public String getNaam() {
    return naam;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).append(taal).toHashCode();
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }
}
