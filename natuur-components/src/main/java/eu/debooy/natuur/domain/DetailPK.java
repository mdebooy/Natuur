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
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class DetailPK  implements Comparable<DetailPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long  parentId;
  private Long  taxonId;

  public DetailPK() {}

  public DetailPK(Long parentId, Long taxonId) {
    super();
    this.parentId = parentId;
    this.taxonId  = taxonId;
  }

  @Override
  public int compareTo(DetailPK detailPK) {
    return new CompareToBuilder().append(parentId, detailPK.parentId)
                                 .append(taxonId, detailPK.taxonId)
                                 .toComparison();
  }

  @Override
   public boolean equals(Object object) {
     if (!(object instanceof DetailPK)) {
       return false;
     }
    if (object == this) {
      return true;
    }

    var detailPK  = (DetailPK) object;
     return new EqualsBuilder().append(parentId, detailPK.parentId)
                               .append(taxonId, detailPK.taxonId)
                               .isEquals();
   }

  public Long getParentId() {
    return parentId;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentId)
                                .append(taxonId).toHashCode();
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("DetailPK")
                              .append(" (parentId=").append(parentId)
                              .append(", taxonId=").append(taxonId)
                              .append(")").toString();
  }
}
