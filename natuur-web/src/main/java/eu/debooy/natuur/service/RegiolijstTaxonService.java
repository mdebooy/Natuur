/*
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
package eu.debooy.natuur.service;

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.RegiolijstTaxonDao;
import eu.debooy.natuur.access.WaarnemingDao;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.RegiolijstTaxonDto;
import eu.debooy.natuur.domain.RegiolijstTaxonPK;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.AantalPerRegio;
import eu.debooy.natuur.form.RegiolijstTaxon;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurRegiolijstTaxonService")
@Path("/regiolijsttaxa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class RegiolijstTaxonService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(RegiolijstTaxonService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private RegiolijstTaxonDao  regiolijstTaxonDao;
  @SuppressWarnings("java:S6813")
  @Inject
  private WaarnemingDao       waarnemingDao;

  public RegiolijstTaxonService() {
    LOGGER.debug("init RegiolijstTaxonService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(RegiolijstTaxonPK sleutel) {
    regiolijstTaxonDao.delete(regiolijstTaxonDao.getByPrimaryKey(sleutel));
  }

  @GET
  @Path("/aantalperregiolijst")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getAantalPerRegiolijst() {
    List<AantalPerRegio>  aantallen = new ArrayList<>();
    try {
      regiolijstTaxonDao.getAantalPerRegiolijst()
                        .forEach(aantal ->
                            aantallen.add(new AantalPerRegio(
                                    Long.valueOf(String.valueOf(aantal[0])),
                                    Long.valueOf(String.valueOf(aantal[1])),
                                    new Date(((java.util.Date) aantal[2])
                                            .getTime()),
                                    Long.valueOf(String.valueOf(aantal[3])),
                                    Long.valueOf(String.valueOf(aantal[4])))));
      return Response.ok().entity(aantallen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new RegiolijstDto()).build();
    }
  }

  @GET
  @Path("/{regiolijstId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPerRegiolijst
      (@PathParam(RegiolijstDto.COL_REGIOLIJSTID) Long regiolijstId) {
    try {
      var taxa  = regiolijstTaxonDao.getPerRegiolijst(regiolijstId);
      setGezien(taxa);
      return Response.ok().entity(taxa).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/{regiolijstId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPerRegiolijst
      (@PathParam(RegiolijstDto.COL_REGIOLIJSTID) Long regiolijstId,
                  @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      var taxa  = regiolijstTaxonDao.getPerRegiolijst(regiolijstId);
      var lijst = new ArrayList<RegiolijstTaxon>();
      setGezien(taxa);
      taxa.forEach(taxon -> lijst.add(new RegiolijstTaxon(taxon, taal)));
      return Response.ok().entity(lijst).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getRegiolijsten() {
    try {
      var taxa  = regiolijstTaxonDao.getAll();
      setGezien(taxa);
      return Response.ok().entity(taxa).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<RegiolijstTaxonDto> query(Long regiolijstId) {
    List<RegiolijstTaxonDto>  taxa  = new ArrayList<>();

    try {
      taxa.addAll(regiolijstTaxonDao.getPerRegiolijst(regiolijstId));
      setGezien(taxa);
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxa;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public RegiolijstTaxonDto regiolijstTaxon(Long regioIlijstd, Long taxonId) {
    return regiolijstTaxon(new RegiolijstTaxonPK(regioIlijstd, taxonId));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public RegiolijstTaxonDto regiolijstTaxon(RegiolijstTaxonPK sleutel) {
    return regiolijstTaxonDao.getByPrimaryKey(sleutel);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(RegiolijstTaxonDto regiolijstTaxon) {
    regiolijstTaxonDao.create(regiolijstTaxon);
  }

  private void setGezien(List<RegiolijstTaxonDto> taxa) {
    var gezien  = waarnemingDao.getTaxa();

    taxa.forEach(rij -> rij.setGezien(gezien.contains(rij.getTaxon()
                                                         .getTaxonId())));
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void update(RegiolijstTaxonDto regiolijstTaxon) {
    regiolijstTaxonDao.update(regiolijstTaxon);
  }
}
