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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class OverzichtPK implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    parentId;
  private String  parentRang;
  private String  rang;

  public OverzichtPK() {}

  public OverzichtPK(Long parentId, String parentRang, String rang) {
    super();
    this.parentId   = parentId;
    this.parentRang = parentRang;
    this.rang       = rang;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof OverzichtPK)) {
      return false;
    }
    OverzichtPK  overzichtPK = (OverzichtPK) object;
    return new EqualsBuilder().append(parentId, overzichtPK.parentId)
                              .append(parentRang, overzichtPK.parentRang)
                              .append(rang, overzichtPK.rang)
                              .isEquals();
  }

  public Long getParentId() {
    return parentId;
  }

  public String getParentRang() {
    return parentRang;
  }

  public String getRang() {
    return rang;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public void setParentRang(String parentRang) {
    this.parentRang = parentRang;
  }

  public void setRang(String rang) {
    this.rang = rang;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentId)
                                .append(parentRang)
                                .append(rang).toHashCode();
  }

  @Override
  public String toString() {
    return new StringBuilder().append("OverzichtPK")
                              .append(" (parentId=").append(parentId)
                              .append(", parentRang=").append(parentRang)
                              .append(", rang=").append(rang)
                              .append(")").toString();
  }
}
