/**
 * Copyright (c) 2015 Marco de Booij
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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.RangDto;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Rang
    extends Formulier implements Comparable<Rang>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  naam;
  private Long    niveau;
  private String  rangcode;

  public Rang() {}

  public Rang(Rang rang) {
    naam      = rang.getNaam();
    niveau    = rang.getNiveau();
    rangcode  = rang.getRang();
  }

  public Rang(RangDto rangDto) {
    this(rangDto, null);
  }

  public Rang(RangDto rangDto, String taal) {
    if (DoosUtils.isNotBlankOrNull(taal)) {
      naam    = rangDto.getNaam(taal);
    }
    niveau    = rangDto.getNiveau();
    rangcode  = rangDto.getRang();
  }

  public static class NiveauComparator
      implements Comparator<Rang>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Rang rang1, Rang rang2) {
      return rang1.niveau.compareTo(rang2.niveau);
    }
  }

  @Override
  public int compareTo(Rang rang) {
    return new CompareToBuilder().append(rangcode, rang.rangcode)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Rang)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var rang  = (Rang) object;
    return new EqualsBuilder().append(rangcode, rang.rangcode).isEquals();
  }

  public String getNaam() {
    return (DoosUtils.isBlankOrNull(naam) ? rangcode : naam);
  }

  public Long getNiveau() {
    return niveau;
  }

  public String getRang() {
    return rangcode;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(rangcode).toHashCode();
  }

  public void persist(RangDto parameter) {
    if (!new EqualsBuilder().append(niveau,
                                    parameter.getNiveau()).isEquals()) {
      parameter.setNiveau(niveau);
    }
    if (!new EqualsBuilder().append(rangcode,
                                    parameter.getRang()).isEquals()) {
      parameter.setRang(rangcode);
    }
  }

  public void setNaam(String naam) {
    this.naam     = naam;
  }

  public void setNiveau(Long niveau) {
    this.niveau   = niveau;
  }

  public void setRang(String rang) {
    this.rangcode = rang;
  }
}
