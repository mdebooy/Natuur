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

  private Long  taxonId;
  private Long  taxonSeq;

  /**
   * Standaard constructor.
   */
  public FotoPK() {}

  /**
   * @param Long taxonId
   * @param Long taxonSeq
   */
  public FotoPK(Long taxonId, Long taxonSeq) {
    super();
    this.taxonId  = taxonId;
    this.taxonSeq = taxonSeq;
  }

  @Override
   public boolean equals(Object object) {
     if (!(object instanceof FotoPK)) {
       return false;
     }
     FotoPK fotoPK  = (FotoPK) object;
     return new EqualsBuilder().append(taxonId, fotoPK.taxonId)
                               .append(taxonSeq, fotoPK.taxonSeq)
                               .isEquals();
   }

  /**
   * @return Long de taxonId
   */
  public Long getTaxonID() {
    return taxonId;
  }

  /**
   * @return Long de taxonSeq
   */
  public Long getTaxonSeq() {
    return taxonSeq;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId)
                                .append(taxonSeq).toHashCode();
  }

  /**
   * @param Long taxonId de waarde van taxonId
   */
  public void setTaxonID(Long taxonId) {
    this.taxonId  = taxonId;
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
                              .append(" (taxonId=").append(taxonId)
                              .append(", taxonSeq=").append(taxonSeq)
                              .append(")").toString();
  }
}
