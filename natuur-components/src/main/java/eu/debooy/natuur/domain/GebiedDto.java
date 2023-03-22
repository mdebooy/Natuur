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

import eu.debooy.doosutils.domain.Dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="GEBIEDEN", schema="NATUUR")
public class GebiedDto extends Dto implements Comparable<GebiedDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_GEBIEDID          = "gebiedId";
  public static final String  COL_LANDID            = "landId";
  public static final String  COL_LATITUDE          = "latitude";
  public static final String  COL_LATITUDEGRADEN    = "latitudeGraden";
  public static final String  COL_LATITUDEMINUTEN   = "latitudeMinuten";
  public static final String  COL_LATITUDESECONDEN  = "latitudeSeconden";
  public static final String  COL_LONGITUDE         = "longitude";
  public static final String  COL_LONGITUDEGRADEN   = "longitudeGraden";
  public static final String  COL_LONGITUDEMINUTEN  = "longitudeMinuten";
  public static final String  COL_LONGITUDESECONDEN = "longitudeSeconden";
  public static final String  COL_NAAM              = "naam";

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="GEBIED_ID", nullable=false, unique=true, updatable=false)
  private Long    gebiedId;
  @Column(name="LAND_ID", nullable=false)
  private Long    landId    = Long.valueOf(0);
  @Column(name="LATITUDE", length=1)
  private String  latitude;
  @Column(name="LATITUDE_GRADEN", length=2)
  private Integer latitudeGraden;
  @Column(name="LATITUDE_MINUTEN", length=2)
  private Integer latitudeMinuten;
  @Column(name="LATITUDE_SECONDEN", precision=5, scale=3)
  private Double  latitudeSeconden;
  @Column(name="LONGITUDE", length=1)
  private String  longitude;
  @Column(name="LONGITUDE_GRADEN", length=3)
  private Integer longitudeGraden;
  @Column(name="LONGITUDE_MINUTEN", length=2)
  private Integer longitudeMinuten;
  @Column(name="LONGITUDE_SECONDEN", precision=5, scale=3)
  private Double  longitudeSeconden;
  @Column(name="NAAM", length=225, nullable=false)
  private String  naam;

  @Override
  public int compareTo(GebiedDto gebiedDto) {
    return new CompareToBuilder().append(naam.toUpperCase(),
                                         gebiedDto.naam.toUpperCase())
                                 .append(gebiedId, gebiedDto.gebiedId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof GebiedDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var gebiedDto  = (GebiedDto) object;
    return new EqualsBuilder().append(gebiedId, gebiedDto.gebiedId).isEquals();
  }

  public Long getGebiedId() {
    return gebiedId;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLatitude() {
    return latitude;
  }

  public Integer getLatitudeGraden() {
    return latitudeGraden;
  }

  public Integer getLatitudeMinuten() {
    return latitudeMinuten;
  }

  public Double getLatitudeSeconden() {
    return latitudeSeconden;
  }

  public String getLongitude() {
    return longitude;
  }

  public Integer getLongitudeGraden() {
    return longitudeGraden;
  }

  public Integer getLongitudeMinuten() {
    return longitudeMinuten;
  }

  public Double getLongitudeSeconden() {
    return longitudeSeconden;
  }

  public String getNaam() {
    return naam;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(gebiedId).toHashCode();
  }

  public void setGebiedId(Long gebiedId) {
    this.gebiedId = gebiedId;
  }

  public void setLandId(Long landId) {
    this.landId = landId;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public void setLatitudeGraden(Integer latitudeGraden) {
    this.latitudeGraden = latitudeGraden;
  }

  public void setLatitudeMinuten(Integer latitudeMinuten) {
    this.latitudeMinuten = latitudeMinuten;
  }

  public void setLatitudeSeconden(Double latitudeSeconden) {
    this.latitudeSeconden = latitudeSeconden;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public void setLongitudeGraden(Integer longitudeGraden) {
    this.longitudeGraden = longitudeGraden;
  }

  public void setLongitudeMinuten(Integer longitudeMinuten) {
    this.longitudeMinuten = longitudeMinuten;
  }

  public void setLongitudeSeconden(Double longitudeSeconden) {
    this.longitudeSeconden = longitudeSeconden;
  }

  public void setNaam(String naam) {
    this.naam = naam;
  }
}
