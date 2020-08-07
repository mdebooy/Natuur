/**
 * Copyright (c) 2016 Marco de Booij
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
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Rangtotaal
    extends Formulier implements Comparable<Rangtotaal>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  latijnsenaam;
  private String  naam;
  private Long    opFoto;
  private Long    taxonId;
  private Long    totaal;

  public Rangtotaal(Object[] rij) {
    taxonId       = (Long)   rij[0];
    latijnsenaam  = (String) rij[1];
    totaal        = (Long)   rij[2];
    opFoto        = (Long)   rij[3];
  }

  @Override
  public int compareTo(Rangtotaal andere) {
    return new CompareToBuilder().append(taxonId, andere.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Rangtotaal)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Rangtotaal  andere  = (Rangtotaal) object;
    return new EqualsBuilder().append(taxonId, andere.taxonId).isEquals();
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public String getNaam() {
    return naam;
  }

  public Long getOpFoto() {
    return opFoto;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public Long getTotaal() {
    return totaal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam = latijnsenaam;
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }

  public void setOpFoto(Long opFoto) {
    this.opFoto = opFoto;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }

  public void setTotaal(Long totaal) {
    this.totaal = totaal;
  }
}
