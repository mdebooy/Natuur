/**
 * Copyright 2015 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.natuur.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.TaxonDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Taxon
    extends Formulier implements Cloneable, Comparable<Taxon>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  latijnsenaam;
  private String  naam;
  private String  opmerking;
  private Long    parentId;
  private String  rang;
  private Long    taxonId;

  public Taxon() {}

  public Taxon(TaxonDto taxonDto) {
    latijnsenaam  = taxonDto.getLatijnsenaam();
    naam          = taxonDto.getNaam();
    opmerking     = taxonDto.getOpmerking();
    parentId      = taxonDto.getParentId();
    rang          = taxonDto.getRang();
    taxonId       = taxonDto.getTaxonId();
  }

  public Taxon(DetailDto detailDto) {
    latijnsenaam  = detailDto.getLatijnsenaam();
    naam          = detailDto.getNaam();
    opmerking     = detailDto.getOpmerking();
    parentId      = detailDto.getParentId();
    rang          = detailDto.getRang();
    taxonId       = detailDto.getTaxonId();
  }

  public static class LatijnsenaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Taxon taxon1, Taxon taxon2) {
      return taxon1.latijnsenaam.compareTo(taxon2.latijnsenaam);
    }
  }

  public static class NaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Taxon taxon1, Taxon taxon2) {
      return taxon1.naam.compareTo(taxon2.naam);
    }
  }
  
  public Taxon clone() throws CloneNotSupportedException {
    Taxon clone = (Taxon) super.clone();

    return clone;
  }

  public int compareTo(Taxon andere) {
    return new CompareToBuilder().append(taxonId, andere.taxonId)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Taxon)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Taxon andere  = (Taxon) object;
    return new EqualsBuilder().append(taxonId, andere.taxonId).isEquals();
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public String getNaam() {
    return naam;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Long getParentId() {
    return parentId;
  }

  public String getRang() {
    return rang;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  public void persist(TaxonDto parameter) {
    if (!new EqualsBuilder().append(this.latijnsenaam,
                                    parameter.getLatijnsenaam()).isEquals()) {
      parameter.setLatijnsenaam(this.latijnsenaam);
    }
    if (!new EqualsBuilder().append(this.naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(this.naam);
    }
    if (!new EqualsBuilder().append(this.opmerking,
                                    parameter.getOpmerking()).isEquals()) {
      parameter.setOpmerking(this.opmerking);
    }
    if (!new EqualsBuilder().append(this.parentId,
                                    parameter.getParentId()).isEquals()) {
      parameter.setParentId(this.parentId);
    }
    if (!new EqualsBuilder().append(this.rang,
                                    parameter.getRang()).isEquals()) {
      parameter.setRang(this.rang);
    }
    if (!new EqualsBuilder().append(this.taxonId,
                                    parameter.getTaxonId()).isEquals()) {
      parameter.setTaxonId(this.taxonId);
    }
  }

  public void setLatijnsenaam(String latijnsenaam) {
    if (!new EqualsBuilder().append(this.latijnsenaam, latijnsenaam)
                            .isEquals()) {
      gewijzigd         = true;
      this.latijnsenaam = latijnsenaam;
    }
  }

  public void setNaam(String naam) {
    if (!new EqualsBuilder().append(this.naam, naam).isEquals()) {
      gewijzigd = true;
      this.naam = naam;
    }
  }

  public void setOpmerking(String opmerking) {
    if (!new EqualsBuilder().append(this.opmerking, opmerking).isEquals()) {
      gewijzigd       = true;
      this.opmerking  = opmerking;
    }
  }

  public void setParentId(Long parentId) {
    if (!new EqualsBuilder().append(this.parentId, parentId).isEquals()) {
      gewijzigd     = true;
      this.parentId = parentId;
    }
  }

  public void setRang(String rang) {
    if (!new EqualsBuilder().append(this.rang, rang).isEquals()) {
      gewijzigd = true;
      this.rang = rang;
    }
  }

  public void setTaxonId(Long taxonId) {
    if (!new EqualsBuilder().append(this.taxonId, taxonId).isEquals()) {
      gewijzigd     = true;
      this.taxonId  = taxonId;
    }
  }
}
