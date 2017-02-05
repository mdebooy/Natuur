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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="WAARNEMINGEN", schema="NATUUR")
public class WaarnemingDto
    extends Dto implements Comparable<WaarnemingDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Column(name="AANTAL")
  private Integer   aantal;
  @OrderColumn
  @Column(name="DATUM", nullable=false)
  private Date      datum;
  @OneToOne
  @JoinColumn(name="GEBIED_ID", nullable=false)
  private GebiedDto gebied;
  @Column(name="OPMERKING", length=2000)
  private String    opmerking;
  @OneToOne
  @JoinColumn(name="TAXON_ID", nullable=false)
  private TaxonDto  taxon;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="WAARNEMING_ID", nullable=false)
  private Long      waarnemingId;
  
  public WaarnemingDto clone() throws CloneNotSupportedException {
    WaarnemingDto clone = (WaarnemingDto) super.clone();

    return clone;
  }

  public int compareTo(WaarnemingDto waarnemingDto) {
    return new CompareToBuilder().append(waarnemingId,
                                         waarnemingDto.waarnemingId)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof WaarnemingDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    WaarnemingDto waarnemingDto = (WaarnemingDto) object;

    return new EqualsBuilder().append(waarnemingId,
                                      waarnemingDto.waarnemingId).isEquals();
  }

  public Integer getAantal() {
    return aantal;
  }

  public Date getDatum() {
    if (null == datum) {
      return null;
    }

    return (Date) datum.clone();
  }

  public GebiedDto getGebied() {
    return gebied;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public TaxonDto getTaxon() {
    return taxon;
  }

  public Long getWaarnemingId() {
    return waarnemingId;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(waarnemingId).toHashCode();
  }

  public void setAantal(Integer aantal) {
    this.aantal = aantal;
  }

  public void setDatum(Date datum) {
    if (null == datum) {
      this.datum  = null;
    } else {
      this.datum  = (Date) datum.clone();
    }
  }

  public void setGebied(GebiedDto gebied) {
    this.gebied = gebied;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking  = opmerking;
  }

  public void setTaxon(TaxonDto taxon) {
    this.taxon  = taxon;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}
