/**
 * Copyright (c) 2017 Marco de Booij
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
package eu.debooy.natuur.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.TaxonnaamDto;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Taxonnaam
    extends Formulier implements Comparable<Taxonnaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  naam;
  private String  taal;
  private Long    taxonId;

  public Taxonnaam() {}

  public Taxonnaam(Taxonnaam taxonnaam) {
    taal    = taxonnaam.getTaal();
    taxonId = taxonnaam.getTaxonId();
    naam    = taxonnaam.getNaam();
  }

  public Taxonnaam(TaxonnaamDto taxonnaamDto) {
    taal    = taxonnaamDto.getTaal();
    taxonId = taxonnaamDto.getTaxonId();
    naam    = taxonnaamDto.getNaam();
  }

  public static class NaamComparator
      implements Comparator<Taxonnaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxonnaam naam1,
                       Taxonnaam naam2) {
      return naam1.naam.compareTo(naam2.naam);
    }
  }

  @Override
  public int compareTo(Taxonnaam taxonnaam) {
    return new CompareToBuilder().append(taxonId, taxonnaam.taxonId)
                                 .append(taal, taxonnaam.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Taxonnaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var taxonnaam = (Taxonnaam) object;
    return new EqualsBuilder().append(taxonId, taxonnaam.taxonId)
                              .append(taal, taxonnaam.taal).isEquals();
  }

  public String getNaam() {
    return naam;
  }

  public String getTaal() {
    return taal;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).append(taal).toHashCode();
  }

  public void persist(TaxonnaamDto parameter) {
    if (!new EqualsBuilder().append(taal,
                                    parameter.getTaal()).isEquals()) {
      parameter.setTaal(taal);
    }
    if (!new EqualsBuilder().append(taxonId,
                                    parameter.getTaxonId()).isEquals()) {
      parameter.setTaxonId(taxonId);
    }
    if (!new EqualsBuilder().append(naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(naam);
    }
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }
}
