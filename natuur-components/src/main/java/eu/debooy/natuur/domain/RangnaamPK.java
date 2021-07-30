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
public class RangnaamPK implements Comparable<RangnaamPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  rang;
  private String  taal;

  public RangnaamPK() {}

  public RangnaamPK(String rang, String taal) {
    super();
    this.rang = rang;
    this.taal = taal;
  }

  @Override
  public int compareTo(RangnaamPK naamPK) {
    return new CompareToBuilder().append(rang, naamPK.rang)
                                 .append(taal, naamPK.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RangnaamPK)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    RangnaamPK  naamPK  = (RangnaamPK) object;
    return new EqualsBuilder().append(rang, naamPK.rang)
                              .append(taal, naamPK.taal)
                              .isEquals();
  }

  public String getRang() {
    return rang;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(rang)
                                .append(taal).toHashCode();
  }

  public void setRang(String rang) {
    this.rang = rang;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("RangnaamPK")
                              .append(" (rang=").append(rang)
                              .append(", taal=").append(taal)
                              .append(")").toString();
  }
}
