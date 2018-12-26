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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.GebiedDto;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Gebied
    extends Formulier implements Comparable<Gebied>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Long    gebiedId;
  private Long    landId;
  private String  latitude;
  private Integer latitudeGraden;
  private Integer latitudeMinuten;
  private Double  latitudeSeconden;
  private String  longitude;
  private Integer longitudeGraden;
  private Integer longitudeMinuten;
  private Double  longitudeSeconden;
  private String  naam;

  public Gebied() {}

  public Gebied(Gebied gebied) {
    gebiedId          = gebied.getGebiedId();
    landId            = gebied.getLandId();
    latitude          = gebied.getLatitude();
    latitudeGraden    = gebied.getLatitudeGraden();
    latitudeMinuten   = gebied.getLatitudeMinuten();
    latitudeSeconden  = gebied.getLatitudeSeconden();
    longitude         = gebied.getLongitude();
    longitudeGraden   = gebied.getLongitudeGraden();
    longitudeMinuten  = gebied.getLongitudeMinuten();
    longitudeSeconden = gebied.getLongitudeSeconden();
    naam              = gebied.getNaam();
  }

  public Gebied(GebiedDto gebiedDto) {
    gebiedId          = gebiedDto.getGebiedId();
    landId            = gebiedDto.getLandId();
    latitude          = gebiedDto.getLatitude();
    latitudeGraden    = gebiedDto.getLatitudeGraden();
    latitudeMinuten   = gebiedDto.getLatitudeMinuten();
    latitudeSeconden  = gebiedDto.getLatitudeSeconden();
    longitude         = gebiedDto.getLongitude();
    longitudeGraden   = gebiedDto.getLongitudeGraden();
    longitudeMinuten  = gebiedDto.getLongitudeMinuten();
    longitudeSeconden = gebiedDto.getLongitudeSeconden();
    naam              = gebiedDto.getNaam();
  }

  public static class NaamComparator
      implements Comparator<Gebied>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Gebied gebied1, Gebied gebied2) {
      return gebied1.naam.compareTo(gebied2.naam);
    }
  }

  public int compareTo(Gebied andere) {
    return new CompareToBuilder().append(gebiedId, andere.gebiedId)
                                 .toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Gebied)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Gebied  andere  = (Gebied) object;
    return new EqualsBuilder().append(gebiedId, andere.gebiedId).isEquals();
  }

  public String getCoordinaten() {
    StringBuilder coordinaten = new StringBuilder();

    if (DoosUtils.isNotBlankOrNull(latitude)) {
      DecimalFormat seconden    = new DecimalFormat("#0.000");

      coordinaten.append(latitude).append(" ").append(latitudeGraden)
                 .append(" ").append(latitudeMinuten).append(" ")
                 .append(seconden.format(latitudeSeconden))
                 .append(" - ")
                 .append(longitude).append(" ").append(longitudeGraden)
                 .append(" ").append(longitudeMinuten).append(" ")
                 .append(seconden.format(longitudeSeconden));
    }

    return coordinaten.toString();
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

  public int hashCode() {
    return new HashCodeBuilder().append(gebiedId).toHashCode();
  }

  public void persist(GebiedDto parameter) {
    if (!new EqualsBuilder().append(gebiedId,
                                    parameter.getGebiedId()).isEquals()) {
      parameter.setGebiedId(gebiedId);
    }
    if (!new EqualsBuilder().append(landId,
                                    parameter.getLandId()).isEquals()) {
      parameter.setLandId(landId);
    }
    if (!new EqualsBuilder().append(latitude,
                                    parameter.getLatitude()).isEquals()) {
      parameter.setLatitude(latitude);
    }
    if (!new EqualsBuilder().append(latitudeGraden,
                                    parameter.getLatitudeGraden())
                            .isEquals()) {
      parameter.setLatitudeGraden(latitudeGraden);
    }
    if (!new EqualsBuilder().append(latitudeMinuten,
                                    parameter.getLatitudeMinuten())
                            .isEquals()) {
      parameter.setLatitudeMinuten(latitudeMinuten);
    }
    if (!new EqualsBuilder().append(latitudeSeconden,
                                    parameter.getLatitudeSeconden())
                            .isEquals()) {
      parameter.setLatitudeSeconden(latitudeSeconden);
    }
    if (!new EqualsBuilder().append(longitude,
                                    parameter.getLongitude()).isEquals()) {
      parameter.setLongitude(longitude);
    }
    if (!new EqualsBuilder().append(longitudeGraden,
                                    parameter.getLongitudeGraden())
                            .isEquals()) {
      parameter.setLongitudeGraden(longitudeGraden);
    }
    if (!new EqualsBuilder().append(longitudeMinuten,
                                    parameter.getLongitudeMinuten())
                            .isEquals()) {
      parameter.setLongitudeMinuten(longitudeMinuten);
    }
    if (!new EqualsBuilder().append(longitudeSeconden,
                                    parameter.getLongitudeSeconden())
                            .isEquals()) {
      parameter.setLongitudeSeconden(longitudeSeconden);
    }
    if (!new EqualsBuilder().append(naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(naam);
    }
  }

  public void setGebiedId(Long gebiedId) {
    if (!new EqualsBuilder().append(this.gebiedId, gebiedId).isEquals()) {
      gewijzigd     = true;
      this.gebiedId = gebiedId;
    }
  }

  public void setLandId(Long landId) {
    if (!new EqualsBuilder().append(this.landId, landId).isEquals()) {
      gewijzigd   = true;
      this.landId = landId;
    }
  }

  public void setLatitude(String latitude) {
    if (!new EqualsBuilder().append(this.latitude, latitude).isEquals()) {
      gewijzigd     = true;
      this.latitude = latitude;
    }
  }

  public void setLatitudeGraden(Integer latitudeGraden) {
    if (!new EqualsBuilder().append(this.latitudeGraden, latitudeGraden)
                            .isEquals()) {
      gewijzigd           = true;
      this.latitudeGraden = latitudeGraden;
    }
  }

  public void setLatitudeMinuten(Integer latitudeMinuten) {
    if (!new EqualsBuilder().append(this.latitudeMinuten, latitudeMinuten)
                            .isEquals()) {
      gewijzigd             = true;
      this.latitudeMinuten  = latitudeMinuten;
    }
  }

  public void setLatitudeSeconden(Double latitudeSeconden) {
    if (!new EqualsBuilder().append(this.latitudeSeconden, latitudeSeconden)
                            .isEquals()) {
      gewijzigd             = true;
      this.latitudeSeconden = latitudeSeconden;
    }
  }

  public void setLongitude(String longitude) {
    if (!new EqualsBuilder().append(this.longitude, longitude).isEquals()) {
      gewijzigd       = true;
      this.longitude  = longitude;
    }
  }

  public void setLongitudeGraden(Integer longitudeGraden) {
    if (!new EqualsBuilder().append(this.longitudeGraden, longitudeGraden)
                            .isEquals()) {
      gewijzigd             = true;
      this.longitudeGraden  = longitudeGraden;
    }
  }

  public void setLongitudeMinuten(Integer longitudeMinuten) {
    if (!new EqualsBuilder().append(this.longitudeMinuten, longitudeMinuten)
                            .isEquals()) {
      gewijzigd             = true;
      this.longitudeMinuten = longitudeMinuten;
    }
  }

  public void setLongitudeSeconden(Double longitudeSeconden) {
    if (!new EqualsBuilder().append(this.longitudeSeconden, longitudeSeconden)
                            .isEquals()) {
      gewijzigd               = true;
      this.longitudeSeconden  = longitudeSeconden;
    }
  }

  public void setNaam(String naam) {
    if (!new EqualsBuilder().append(this.naam, naam).isEquals()) {
      gewijzigd = true;
      this.naam = naam;
    }
  }
}
