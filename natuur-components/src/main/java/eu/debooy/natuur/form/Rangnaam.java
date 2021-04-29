/*
 * Copyright (c) 2021 Marco de Booij
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
import eu.debooy.natuur.domain.RangnaamDto;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Rangnaam
    extends Formulier implements Comparable<Rangnaam>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  naam;
  private String  rang;
  private String  taal;

  public Rangnaam() {}

  public Rangnaam(Rangnaam rangnaam) {
    naam  = rangnaam.getNaam();
    rang  = rangnaam.getRang();
    taal  = rangnaam.getTaal();
  }

  public Rangnaam(RangnaamDto rangnaamDto) {
    naam  = rangnaamDto.getNaam();
    rang  = rangnaamDto.getRang();
    taal  = rangnaamDto.getTaal();
  }

  public static class NaamComparator
      implements Comparator<Rangnaam>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Rangnaam naam1,
                       Rangnaam naam2) {
      return naam1.naam.compareTo(naam2.naam);
    }
  }

  @Override
  public int compareTo(Rangnaam andere) {
    return new CompareToBuilder().append(rang, andere.rang)
                                 .append(taal, andere.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Rangnaam)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Rangnaam  andere  = (Rangnaam) object;
    return new EqualsBuilder().append(rang, andere.rang)
                              .append(taal, andere.taal).isEquals();
  }

  public String getNaam() {
    return naam;
  }

  public String getRang() {
    return rang;
  }

  public String getTaal() {
    return taal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(rang).append(taal).toHashCode();
  }

  public void persist(RangnaamDto parameter) {
    if (!new EqualsBuilder().append(naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(naam);
    }
    if (!new EqualsBuilder().append(rang,
                                    parameter.getRang()).isEquals()) {
      parameter.setRang(rang);
    }
    if (!new EqualsBuilder().append(taal,
                                    parameter.getTaal()).isEquals()) {
      parameter.setTaal(taal);
    }
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }

  public void setRang(String rang) {
    this.rang  = rang;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }
}
