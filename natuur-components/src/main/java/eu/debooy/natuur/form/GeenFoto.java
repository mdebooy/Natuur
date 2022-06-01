/*
 * Copyright (c) 2022 Marco de Booij
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

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.GeenFotoDto;
import eu.debooy.natuur.domain.TaxonDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class GeenFoto
    extends Formulier implements Comparable<GeenFoto>, Serializable {

  private static final  long  serialVersionUID  = 1L;

  private Taxon   parent;
  private String  parentRang;
  private Taxon   taxon;

  public GeenFoto() {}

  public GeenFoto(GeenFoto geenFoto) {
    parent      = new Taxon(geenFoto.getParent());
    parentRang  = geenFoto.getParentRang();
    taxon       = new Taxon(geenFoto.getTaxon());
  }

  public GeenFoto(GeenFotoDto geenFoto) {
    parent      = new Taxon(geenFoto.getParent());
    parentRang  = geenFoto.getParentRang();
    taxon       = new Taxon(geenFoto.getTaxon());
  }

  public GeenFoto(GeenFotoDto geenFoto, String taal) {
    parent      = new Taxon(geenFoto.getParent(), taal);
    parentRang  = geenFoto.getParentRang();
    taxon       = new Taxon(geenFoto.getTaxon(), taal);
  }

  @Override
  public int compareTo(GeenFoto geenFoto) {
    return
        new CompareToBuilder().append(parent.getVolgnummer(),
                                      geenFoto.getParent().getVolgnummer())
                              .append(taxon.getVolgnummer(),
                                      geenFoto.getTaxon().getVolgnummer())
                              .append(parent.getLatijnsenaam(),
                                      geenFoto.getParent().getLatijnsenaam())
                              .append(taxon.getLatijnsenaam(),
                                      geenFoto.getTaxon().getLatijnsenaam())
                              .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof GeenFoto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var geenFoto = (GeenFoto) object;
    return new EqualsBuilder().append(parent.getParentId(),
                                      geenFoto.getParent().getParentId())
                              .append(parentRang, geenFoto.getParentRang())
                              .append(taxon.getTaxonId(),
                                      geenFoto.getTaxon().getTaxonId())
                              .isEquals();
  }

  public Taxon getParent() {
    return parent;
  }

  public String getParentRang() {
    return parentRang;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parent.getParentId())
                                .append(parentRang)
                                .append(taxon.getTaxonId()).toHashCode();
  }

  public void setParent(Taxon parent) {
    this.parent     = new Taxon(parent);
  }

  public void setParent(TaxonDto parent) {
    this.parent     = new Taxon(parent);
  }

  public void setParent(TaxonDto parent, String taal) {
    this.parent     = new Taxon(parent, taal);
  }

  public void setParentRang(String parentRang) {
    this.parentRang = parentRang;
  }

  public void setTaxon(Taxon taxon) {
    this.taxon      = new Taxon(taxon);
  }

  public void setTaxon(TaxonDto taxon) {
    this.taxon      = new Taxon(taxon);
  }

  public void setTaxon(TaxonDto taxon, String taal) {
    this.taxon      = new Taxon(taxon, taal);
  }
}
