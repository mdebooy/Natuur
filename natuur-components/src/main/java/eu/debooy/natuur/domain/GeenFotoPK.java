/*
 * Copyright (c) 2021 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
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
public class GeenFotoPK implements Comparable<GeenFotoPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    parentId;
  private String  parentRang;
  private Long    taxonId;

  public GeenFotoPK() {}

  public GeenFotoPK(Long parentId, String parentRang, Long taxonId) {
    super();
    this.parentId   = parentId;
    this.parentRang = parentRang;
    this.taxonId    = taxonId;
  }

  @Override
  public int compareTo(GeenFotoPK geenfotoPK) {
    return new CompareToBuilder().append(parentRang, geenfotoPK.parentRang)
                                 .append(parentId, geenfotoPK.parentId)
                                 .append(taxonId, geenfotoPK.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof GeenFotoPK)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var geenfotoPK  = (GeenFotoPK) object;
    return new EqualsBuilder().append(parentId, geenfotoPK.parentId)
                              .append(parentRang, geenfotoPK.parentRang)
                              .append(taxonId, geenfotoPK.taxonId)
                              .isEquals();
  }

  public Long getParentId() {
    return parentId;
  }

  public String getParentRang() {
    return parentRang;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentId)
                                .append(parentRang)
                                .append(taxonId).toHashCode();
  }

  public void setParentId(Long parentId) {
    this.parentId   = parentId;
  }

  public void setParentRang(String parentRang) {
    this.parentRang = parentRang;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId    = taxonId;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("GeenFotoPK")
                              .append(" (parentId=").append(parentId)
                              .append(", parentRang=").append(parentRang)
                              .append(", taxonId=").append(taxonId)
                              .append(")").toString();
  }
}
