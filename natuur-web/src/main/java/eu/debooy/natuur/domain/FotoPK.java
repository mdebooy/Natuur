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
package eu.debooy.natuur.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class FotoPK implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long  taxon;
  private Long  taxonSeq;

  /**
   * Standaard constructor.
   */
  public FotoPK() {}

  /**
   * @param Long taxon
   * @param Long taxonSeq
   */
  public FotoPK(Long taxon, Long taxonSeq) {
    super();
    this.taxon    = taxon;
    this.taxonSeq = taxonSeq;
  }

  @Override
   public boolean equals(Object object) {
     if (!(object instanceof FotoPK)) {
       return false;
     }
     FotoPK fotoPK  = (FotoPK) object;
     return new EqualsBuilder().append(taxon, fotoPK.getTaxon())
                               .append(taxonSeq, fotoPK.taxonSeq)
                               .isEquals();
   }

  /**
   * @return Long de taxon
   */
  public Long getTaxon() {
    return taxon;
  }

  /**
   * @return Long de taxonSeq
   */
  public Long getTaxonSeq() {
    return taxonSeq;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxon)
                                .append(taxonSeq).toHashCode();
  }

  /**
   * @param TaxonDto taxon de waarde van taxon
   */
  public void setTaxonId(Long taxon) {
    this.taxon  = taxon;
  }

  /**
   * @param Long taxonSeq de waarde van taxonSeq
   */
  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq = taxonSeq;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("FotoPK")
                              .append(" (taxon=").append(taxon)
                              .append(", taxonSeq=").append(taxonSeq)
                              .append(")").toString();
  }
}
