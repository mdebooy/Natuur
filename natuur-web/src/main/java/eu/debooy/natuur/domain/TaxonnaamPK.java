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

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class TaxonnaamPK
    implements Cloneable, Comparable<TaxonnaamPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    taxonId;
  private String  taal;

  /**
   * Standaard constructor.
   */
  public TaxonnaamPK() {}

  /**
   * @param Long taxonId
   * @param Long taal
   */
  public TaxonnaamPK(Long taxonId, String taal) {
    super();
    this.taxonId  = taxonId;
    this.taal     = taal;
  }

  public TaxonnaamPK clone() throws CloneNotSupportedException {
    TaxonnaamPK  clone = (TaxonnaamPK) super.clone();

    return clone;
  }

  public int compareTo(TaxonnaamPK taxonnaamPK) {
    return new CompareToBuilder().append(taxonId, taxonnaamPK.taxonId)
                                 .append(taal, taxonnaamPK.taal)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof TaxonnaamPK)) {
      return false;
    }
    TaxonnaamPK taxonnaamPK = (TaxonnaamPK) object;
    return new EqualsBuilder().append(taxonId, taxonnaamPK.taxonId)
                              .append(taal, taxonnaamPK.taal)
                              .isEquals();
  }

  public Long getLandId() {
    return taxonId;
  }

  public String getTaal() {
    return taal;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(taxonId)
                                .append(taal).toHashCode();
  }

  public void setLandId(Long taxonId) {
    this.taxonId  = taxonId;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public String toString() {
    return new StringBuilder().append("TaxonnaamPK")
                              .append(" (taxonId=").append(taxonId)
                              .append(", taal=").append(taal)
                              .append(")").toString();
  }
}