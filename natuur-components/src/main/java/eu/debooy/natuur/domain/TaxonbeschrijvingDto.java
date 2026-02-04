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
import eu.debooy.doosutils.domain.Dto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="TAXONBESCHRIJVINGEN", schema="NATUUR")
@IdClass(TaxonbeschrijvingPK.class)
@NamedQuery(name="taxonbeschrijvingenPerTaxon", query="select t from TaxonbeschrijvingDto t where t.taxonId=:taxonId")
public class TaxonbeschrijvingDto extends Dto implements Comparable<TaxonbeschrijvingDto>{
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_BESCHRIJVING      = "beschrijving";
  public static final String  COL_BESCHRIJVINGTYPE  = "beschrijvingtype";
  public static final String  COL_TAXONID           = "taxonId";

  public static final String  PAR_TAXONID       = "taxonId";

  public static final String  QRY_BESCHRIJVINGENPERTAXON  =
      "taxonbeschrijvingenPerTaxon";

  @Column(name="BESCHRIJVING", nullable=false, length = 4000)
  private String  beschrijving;
  @Id
  @Column(name="BESCHRIJVINGTYPE", nullable=false, length = 10)
  private String  beschrijvingtype;
  @Id
  @Column(name="TAXON_ID", nullable=false)
  private Long    taxonId;

  @Override
  public int compareTo(TaxonbeschrijvingDto taxonbeschrijvingDto) {
    return new CompareToBuilder().append(taxonId, taxonbeschrijvingDto.taxonId)
                                 .append(beschrijvingtype,
                                         taxonbeschrijvingDto.beschrijvingtype)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof TaxonbeschrijvingDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var taxonbeschrijvingDto  = (TaxonbeschrijvingDto) object;
    return new EqualsBuilder().append(taxonId, taxonbeschrijvingDto.taxonId)
                              .append(beschrijvingtype,
                                      taxonbeschrijvingDto.beschrijvingtype)
                              .isEquals();
  }

  public String getBeschrijving() {
    return beschrijving;
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

  public void setBeschrijving(String beschrijving) {
    this.beschrijving     = DoosUtils.strip(beschrijving);
  }

  public void setBeschrijvingtype(String beschrijvingtype) {
    this.beschrijvingtype = DoosUtils.stripToLowerCase(beschrijvingtype);
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId          = taxonId;
  }

}
