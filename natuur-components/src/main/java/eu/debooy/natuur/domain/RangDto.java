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
package eu.debooy.natuur.domain;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.domain.Dto;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="RANGEN", schema="NATUUR")
@NamedQuery(name="rangenVanaf", query="select r from RangDto r where r.niveau>:niveau")
public class RangDto extends Dto implements Comparable<RangDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_NIVEAU  = "niveau";
  public static final String  COL_RANG    = "rang";

  public static final String  PAR_NIVEAU  = "niveau";

  public static final String  QRY_VANAF = "rangenVanaf";

  @Column(name="NIVEAU", nullable=false, updatable=false)
  private Long    niveau;
  @Id
  @Column(name="RANG", length=3, nullable=false)
  private String  rang;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=RangnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="RANG", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, RangnaamDto>  rangnamen = new HashMap<>();

  public static class NiveauComparator
      implements Comparator<RangDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RangDto rangDto1, RangDto rangDto2) {
      return rangDto1.niveau.compareTo(rangDto2.niveau);
    }
  }

  public void addNaam(RangnaamDto rangnaamDto) {
    if (DoosUtils.isBlankOrNull(rangnaamDto.getRang())) {
      rangnaamDto.setRang(rang);
    }
    rangnamen.put(rangnaamDto.getTaal(), rangnaamDto);
  }

  @Override
  public int compareTo(RangDto rangDto) {
    return new CompareToBuilder().append(rang, rangDto.rang)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RangDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    RangDto rangDto  = (RangDto) object;
    return new EqualsBuilder().append(rang, rangDto.rang)
                              .isEquals();
  }

  @Transient
  public String getNaam(String taal) {
    if (rangnamen.containsKey(taal)) {
      return rangnamen.get(taal).getNaam();
    } else {
      return rang;
    }
  }

  public Long getNiveau() {
    return niveau;
  }

  public String getRang() {
    return rang;
  }

  public Collection<RangnaamDto> getRangnamen() {
    return rangnamen.values();
  }

  public RangnaamDto getRangnaam(String taal) {
    if (rangnamen.containsKey(taal)) {
      return rangnamen.get(taal);
    } else {
      return new RangnaamDto();
    }
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(rang).toHashCode();
  }

  @Transient
  public boolean hasRangnaam(String taal) {
    return rangnamen.containsKey(taal);
  }

  public void removeRangnaam(String taal) {
    if (rangnamen.containsKey(taal)) {
      rangnamen.remove(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public void setNiveau(Long niveau) {
    this.niveau = niveau;
  }

  public void setRang(String rang) {
    this.rang = rang;
  }

  public void setRangnamen(Collection<RangnaamDto> rangnamen) {
    this.rangnamen.clear();
    rangnamen.forEach(rangnaam -> this.rangnamen.put(rangnaam.getTaal(),
                                                     rangnaam));
  }

  public void setRangnamen(Map<String, RangnaamDto> rangnamen) {
    this.rangnamen.clear();
    this.rangnamen.putAll(rangnamen);
  }
}
