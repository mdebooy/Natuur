/*
 * Copyright (c) 2023 Marco de Booij
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

package eu.debooy.natuur.form;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class RegiolijstTaxon
    extends Formulier implements Comparable<RegiolijstTaxon>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    regioId;
  private String  status;
  private Taxon   taxon;
  private Long    taxonId;

  public RegiolijstTaxon() {}

  public RegiolijstTaxon(RegiolijstTaxonDto regiolijstTaxonDto) {
    this(regiolijstTaxonDto, null);
  }

  public RegiolijstTaxon(RegiolijstTaxonDto regiolijstTaxonDto, String taal) {
    regioId = regiolijstTaxonDto.getRegioId();
    status  = regiolijstTaxonDto.getStatus();
    if (null == regiolijstTaxonDto.getTaxon()) {
      taxon = new Taxon();
    } else {
      taxon = new Taxon(regiolijstTaxonDto.getTaxon(), taal);
    }
    taxonId = regiolijstTaxonDto.getTaxonId();
  }

  @Override
  public int compareTo(RegiolijstTaxon regiolijstTaxon) {
    return new CompareToBuilder().append(regioId, regiolijstTaxon.regioId)
                                 .append(taxonId, regiolijstTaxon.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegiolijstTaxon)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regiolijstTaxon = (RegiolijstTaxon) object;
    return new EqualsBuilder().append(regioId, regiolijstTaxon.regioId)
                              .append(taxonId, regiolijstTaxon.taxonId)
                              .isEquals();
  }

  public Long getRegioId() {
    return regioId;
  }

  public String getStatus() {
    return DoosUtils.nullToEmpty(status).trim();
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).append(taxonId).toHashCode();
  }

  public void persist(RegiolijstTaxonDto parameter) {
    if (!new EqualsBuilder().append(regioId,
                                    parameter.getRegioId()).isEquals()) {
      parameter.setRegioId(regioId);
    }
    if (!new EqualsBuilder().append(status,
                                    parameter.getStatus()).isEquals()) {
      parameter.setStatus(status);
    }
    if (!new EqualsBuilder().append(taxonId,
                                    parameter.getTaxonId()).isEquals()) {
      parameter.setTaxonId(taxonId);
    }
  }

  public void setRegioId(Long regioId) {
    this.regioId  = regioId;
  }

  public void setStatus(String status) {
    this.status   = status;
  }

  public void setTaxon(Taxon taxon) {
    this.taxon    = taxon;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }
}
