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
package eu.debooy.natuur.domain;

import eu.debooy.doosutils.domain.Dto;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="WAARNEMINGEN", schema="NATUUR")
@NamedQuery(name="waarnemingenPerGebied", query="select w from WaarnemingDto w where w.gebied.gebiedId=:gebiedId")
@NamedQuery(name="waarnemingenPerTaxon", query="select w from WaarnemingDto w, DetailDto d where w.taxon.taxonId=d.taxonId and d.parentId=:taxonId")
@NamedQuery(name="waarnemingenTotalenPerLand",
            query="select w.gebied.landId, count(w.waarnemingId) from WaarnemingDto w group by w.gebied.landId")
public class WaarnemingDto
    extends Dto implements Comparable<WaarnemingDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_AANTAL        = "aantal";
  public static final String  COL_DATUM         = "datum";
  public static final String  COL_GEBIEDID      = "gebiedId";
  public static final String  COL_OPMERKING     = "opmerking";
  public static final String  COL_TAXONID       = "taxonId";
  public static final String  COL_WAARNEMINGID  = "waarnemingId";

  public static final String  PAR_GEBIEDID  = "gebiedId";
  public static final String  PAR_TAXONID   = "taxonId";

  public static final String  QRY_PERGEBIED   = "waarnemingenPerGebied";
  public static final String  QRY_PERTAXON    = "waarnemingenPerTaxon";
  public static final String  QRY_TAXON       = "select distinct w.taxon.taxonId from WaarnemingDto w";
  public static final String  QRY_TOTPERLAND  = "waarnemingenTotalenPerLand";

  @Column(name="AANTAL")
  private Integer   aantal;
  @OrderColumn
  @Column(name="DATUM", nullable=false)
  private Date      datum;
  @OneToOne
  @JoinColumn(name="GEBIED_ID", nullable=false)
  private GebiedDto gebied;
  @Column(name="OPMERKING", length=2000)
  private String    opmerking;
  @OneToOne
  @JoinColumn(name="TAXON_ID", nullable=false)
  private TaxonDto  taxon;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="WAARNEMING_ID", nullable=false)
  private Long      waarnemingId;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=FotoDto.class, orphanRemoval=true)
  @JoinColumn(name="WAARNEMING_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taxonSeq")
  private Map<Long, FotoDto>  fotos = new HashMap<>();

  public void addFoto(FotoDto fotoDto) {
    if (null == fotoDto.getWaarnemingId()) {
      fotoDto.setWaarnemingId(waarnemingId);
    }

    // Voor het geval dat de taxonSeq is veranderd.
    var aanwezig = fotos.entrySet()
                        .stream()
                        .filter(entry -> (entry.getValue().equals(fotoDto)))
                        .findFirst();
    if (aanwezig.isPresent()) {
      fotos.remove(aanwezig.get().getKey());
    }

    fotos.put(fotoDto.getTaxonSeq(), fotoDto);
  }

  @Override
  public int compareTo(WaarnemingDto waarnemingDto) {
    return new CompareToBuilder().append(waarnemingId,
                                         waarnemingDto.waarnemingId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof WaarnemingDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var waarnemingDto = (WaarnemingDto) object;

    return new EqualsBuilder().append(waarnemingId,
                                      waarnemingDto.waarnemingId).isEquals();
  }

  public Integer getAantal() {
    return aantal;
  }

  @Transient
  public int getAantalFotos() {
    return fotos.size();
  }

  public Date getDatum() {
    if (null == datum) {
      return null;
    }

    return new Date(datum.getTime());
  }

  public FotoDto getFoto(Long taxonSeq) {
    if (fotos.containsKey(taxonSeq)) {
      return fotos.get(taxonSeq);
    } else {
      return new FotoDto();
    }
  }

  public Collection<FotoDto> getFotos() {
    return fotos.values();
  }

  public GebiedDto getGebied() {
    return gebied;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public TaxonDto getTaxon() {
    return taxon;
  }

  public Long getWaarnemingId() {
    return waarnemingId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(waarnemingId).toHashCode();
  }

  public void removeFoto(Long taxonSeq) {
    if (fotos.containsKey(taxonSeq)) {
      fotos.remove(taxonSeq);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE,
                                        taxonSeq.toString());
    }
  }

  public void setAantal(Integer aantal) {
    this.aantal = aantal;
  }

  public void setDatum(Date datum) {
    if (null == datum) {
      this.datum    = null;
    } else {
      this.datum    = new Date(datum.getTime());
    }
  }

  public void setFotos(Collection<FotoDto> fotos) {
    this.fotos.clear();
    fotos.forEach(foto -> this.fotos.put(foto.getTaxonSeq(), foto));
  }

  public void setFotos(Map<Long, FotoDto> fotos) {
    this.fotos.clear();
    this.fotos.putAll(fotos);
  }

  public void setGebied(GebiedDto gebied) {
    this.gebied = gebied;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking    = opmerking;
  }

  public void setTaxon(TaxonDto taxon) {
    this.taxon        = taxon;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}
