/**
 * Copyright (c) 2023 Marco de Booij
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
public class RegiolijstTaxonPK
    implements Comparable<RegiolijstTaxonPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long  taxonId;
  private Long  regioId;

  public RegiolijstTaxonPK() {}

  public RegiolijstTaxonPK(Long regioId, Long taxonId) {
    super();
    this.regioId  = regioId;
    this.taxonId  = taxonId;
  }

  @Override
  public int compareTo(RegiolijstTaxonPK regiolijstTaxonPK) {
    return new CompareToBuilder().append(regioId, regiolijstTaxonPK.regioId)
                                 .append(taxonId, regiolijstTaxonPK.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegiolijstTaxonPK)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regiolijstTaxonPK = (RegiolijstTaxonPK) object;
    return new EqualsBuilder().append(regioId, regiolijstTaxonPK.regioId)
                              .append(taxonId, regiolijstTaxonPK.taxonId)
                              .isEquals();
  }

  public Long getRegioId() {
    return regioId;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId)
                                .append(taxonId).toHashCode();
  }

  public void setRegioId(Long regioId) {
    this.regioId = regioId;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("RegiolijstTaxonPK")
                              .append(" (regioId=").append(regioId)
                              .append(", taxonId=").append(taxonId)
                              .append(")").toString();
  }
}