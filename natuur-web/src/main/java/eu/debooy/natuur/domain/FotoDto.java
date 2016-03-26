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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="FOTOS", schema="NATUUR")
public class FotoDto extends Dto implements Comparable<FotoDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="FOTO_ID", nullable=false)
  private Long      fotoId;
  @OneToOne
  @JoinColumn(name="GEBIED_ID", nullable=false)
  private GebiedDto gebied;
  @OneToOne
  @JoinColumn(name="TAXON_ID", nullable=false)
  private TaxonDto  taxon;
  @Column(name="TAXON_SEQ", nullable=false)
  private Long      taxonSeq;
  
  public FotoDto clone() throws CloneNotSupportedException {
    FotoDto clone = (FotoDto) super.clone();

    return clone;
  }

  public int compareTo(FotoDto fotoDto) {
    return new CompareToBuilder().append(fotoId, fotoDto.fotoId).toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof FotoDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    FotoDto fotoDto = (FotoDto) object;

    return new EqualsBuilder().append(fotoId, fotoDto.fotoId).isEquals();
  }

  /**
   * @return de fotoId
   */
  public Long getFotoId() {
    return fotoId;
  }

  /**
   * @return het gebied
   */
  public GebiedDto getGebied() {
    return gebied;
  }

  /**
   * @return de taxon
   */
  public TaxonDto getTaxon() {
    return taxon;
  }

  /**
   * @return de taxonSeq
   */
  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }

  /**
   * @param fotoId de waarde van fotoId
   */
  public void setFotoId(Long fotoId) {
    this.fotoId = fotoId;
  }

  /**
   * @param gebiedId de waarde van gebiedId
   */
  public void setGebied(GebiedDto gebied) {
    this.gebied = gebied;
  }

  /**
   * @param TaxonDto de Taxon
   */
  public void setTaxon(TaxonDto taxon) {
    this.taxon  = taxon;
  }

  /**
   * @param taxonSeq de waarde van taxonSeq
   */
  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq = taxonSeq;
  }
}
