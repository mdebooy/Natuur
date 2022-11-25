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
package eu.debooy.natuur.domain;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.domain.Dto;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.simple.JSONObject;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="RANGNAMEN", schema="NATUUR")
@IdClass(RangnaamPK.class)
@NamedQuery(name="rangnaamPerTaal", query="select t from TaxonnaamDto t where t.taal=:taal")
public class RangnaamDto extends Dto implements Comparable<RangnaamDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_NAAM  = "naam";
  public static final String  COL_RANG  = "rang";
  public static final String  COL_TAAL  = "taal";

  public static final String  PAR_TAAL  = "taal";

  public static final String  QRY_TAAL  = "rangnaamPerTaal";

  @Column(name="NAAM", length=255, nullable=false)
  private String  naam;
  @Id
  @Column(name="RANG", length=3, nullable=false)
  private String  rang;
  @Id
  @Column(name="TAAL", length=2, nullable=false)
  private String  taal;

  public static class NaamComparator
      implements Comparator<RangnaamDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RangnaamDto naamDto1,
                       RangnaamDto naamDto2) {
      return naamDto1.naam.compareTo(naamDto2.naam);
    }
  }

  @Override
  public int compareTo(RangnaamDto naamDto) {
    return new CompareToBuilder().append(rang, naamDto.rang)
                                 .append(taal, naamDto.taal)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RangnaamDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var naamDto = (RangnaamDto) object;
    return new EqualsBuilder().append(rang, naamDto.rang)
                              .append(taal, naamDto.taal)
                              .isEquals();
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

  public void setNaam(String naam) {
    this.naam = naam;
  }

  public void setRang(String rang) {
    this.rang = rang;
  }

  public void setTaal(String taal) {
    this.taal = taal;
  }

  public JSONObject toJSON() {
    var     json      = new JSONObject();
    String  attribute;
    Object  waarde;

    for (var method : DoosUtils.findGetters(this.getClass().getMethods())) {
      if ((!method.getName().startsWith(PersistenceConstants.GET)
              && !method.getName().startsWith(PersistenceConstants.IS))
              || method.getName().equals("getClass")) {
        continue;
      }

      if (method.getName().startsWith(PersistenceConstants.GET)) {
        attribute = method.getName().substring(3);
      } else {
        attribute = method.getName().substring(2);
      }

      try {
        attribute = attribute.substring(0, 1).toLowerCase()
                + attribute.substring(1);
        waarde = method.invoke(this);
        if (DoosUtils.isNotBlankOrNull(waarde)) {
          if (waarde instanceof Dto) {
            // Geef enkel de naam van de andere DTO.
          } else {
            json.put(attribute, waarde);
          }
        }
      } catch (IllegalAccessException | IllegalArgumentException
              | InvocationTargetException e) {
        var logger  = getLogger();
        if (null != logger) {
          logger.error("toJSON {}: {}", e.getClass().getName(),
                                        e.getLocalizedMessage());
        }
      }
    }

    return json;
  }
}
