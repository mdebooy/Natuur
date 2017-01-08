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

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class DetailPK  implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long  parentId;
  private Long  taxonId;

  /**
   * Standaard constructor.
   */
  public DetailPK() {}

  /**
   * @param Long parentId
   * @param Long taxonId
   */
  public DetailPK(Long parentId, Long taxonId) {
    super();
    this.parentId = parentId;
    this.taxonId  = taxonId;
  }

   public boolean equals(Object object) {
     if (!(object instanceof DetailPK)) {
       return false;
     }
     DetailPK detailPK  = (DetailPK) object;
     return new EqualsBuilder().append(parentId, detailPK.parentId)
                               .append(taxonId, detailPK.taxonId)
                               .isEquals();
   }

  /**
   * @return Long de parentId
   */
  public Long getTaxonID() {
    return parentId;
  }

  /**
   * @return Long de taxonId
   */
  public Long getTaxonSeq() {
    return taxonId;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(parentId)
                                .append(taxonId).toHashCode();
  }

  /**
   * @param Long parentId de waarde van parentId
   */
  public void setTaxonID(Long parentId) {
    this.parentId = parentId;
  }

  /**
   * @param Long taxonId de waarde van taxonId
   */
  public void setTaxonSeq(Long taxonId) {
    this.taxonId  = taxonId;
  }

  public String toString() {
    return new StringBuilder().append("DetailPK")
                              .append(" (parentId=").append(parentId)
                              .append(", taxonId=").append(taxonId)
                              .append(")").toString();
  }
}
