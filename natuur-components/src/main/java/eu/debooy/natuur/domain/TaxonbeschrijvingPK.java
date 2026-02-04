/*
 * Copyright (c) 2025 Marco de Booij
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

import eu.debooy.doosutils.DoosUtils;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class TaxonbeschrijvingPK
    implements Comparable<TaxonbeschrijvingPK>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    taxonId;
  private String  beschrijvingtype;

  public TaxonbeschrijvingPK() {}

  public TaxonbeschrijvingPK(Long taxonId, String beschrijvingtype) {
    super();
    this.taxonId          = taxonId;
    this.beschrijvingtype = beschrijvingtype;
  }

  @Override
  public int compareTo(TaxonbeschrijvingPK taxonbeschrijvingPK) {
    return new CompareToBuilder().append(taxonId, taxonbeschrijvingPK.taxonId)
                                 .append(beschrijvingtype,
                                         taxonbeschrijvingPK.beschrijvingtype)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof TaxonnaamPK)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var taxonnaamPK = (TaxonbeschrijvingPK) object;
    return new EqualsBuilder().append(taxonId, taxonnaamPK.taxonId)
                              .append(beschrijvingtype,
                                      taxonnaamPK.beschrijvingtype)
                              .isEquals();
  }

  public String getBeschrijvingtype() {
    return beschrijvingtype;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId)
                                .append(beschrijvingtype).toHashCode();
  }

  public void setBeschrijvingtype(String beschrijvingtype) {
    this.beschrijvingtype     = DoosUtils.stripToLowerCase(beschrijvingtype);
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("TaxonbeschrijvingPK")
                              .append(" (taxonId=").append(taxonId)
                              .append(", beschrijvingtype=")
                              .append(beschrijvingtype)
                              .append(")").toString();
  }
}