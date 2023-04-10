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
package eu.debooy.natuur.service;

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.WaarnemingDao;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.AantalPerId;
import eu.debooy.natuur.form.Waarneming;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurWaarnemingService")
@Path("/waarnemingen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class WaarnemingService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(WaarnemingService.class);

  @Inject
  private WaarnemingDao waarnemingDao;

  public WaarnemingService() {
    LOGGER.debug("init WaarnemingService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long waarnemingId) {
    waarnemingDao.delete(waarnemingDao.getByPrimaryKey(waarnemingId));
  }

  @GET
  @Path("/aantalperland")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getAantalPerLand() {
    try {
      var totalen = new ArrayList<AantalPerId>();
      waarnemingDao.getAantalPerLand().forEach(rij ->
          totalen.add(new AantalPerId(Long.valueOf(rij.getSleutel()),
                                      Long.valueOf(rij.getWaarde()))));
      return Response.ok().entity(totalen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/gebied/{gebiedId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGebiedWaarnemingen(
                      @PathParam(GebiedDto.COL_GEBIEDID) Long gebiedId) {
    try {
      return Response.ok().entity(waarnemingDao.getPerGebied(gebiedId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/gebied/{gebiedId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGebiedWaarnemingen(
                      @PathParam(GebiedDto.COL_GEBIEDID) Long gebiedId,
                      @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<Waarneming>  waarnemingen  = new ArrayList<>();
      waarnemingDao.getPerGebied(gebiedId)
              .forEach(waarneming ->
                          waarnemingen.add(new Waarneming(waarneming, taal)));
      return Response.ok().entity(waarnemingen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taxon/{taxonId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxonWaarnemingen(
                      @PathParam(TaxonDto.COL_TAXONID) Long taxonId) {
    try {
      return Response.ok().entity(waarnemingDao.getPerTaxon(taxonId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taxon/{taxonId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxonWaarnemingen(
                      @PathParam(TaxonDto.COL_TAXONID) Long taxonId,
                      @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<Waarneming>  waarnemingen  = new ArrayList<>();
      waarnemingDao.getPerTaxon(taxonId)
              .forEach(waarneming ->
                          waarnemingen.add(new Waarneming(waarneming, taal)));
      return Response.ok().entity(waarnemingen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
//    try {
//      return Response.ok().entity(waarnemingDao.getPerTaxon(taxonId)).build();
//    } catch (ObjectNotFoundException e) {
//      return Response.ok().entity(new ArrayList<>()).build();
//    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Long> getTaxa() {
    List<Long> taxa  = new ArrayList<>();

    try {
      taxa.addAll(waarnemingDao.getTaxa());
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxa;
  }

  @GET
  @Path("/{waarnemingId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getWaarneming(
      @PathParam(WaarnemingDto.COL_WAARNEMINGID) Long waarnemingId) {
    try {
      return Response.ok()
                     .entity(waarnemingDao.getByPrimaryKey(waarnemingId))
                                          .build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(WaarnemingDto.COL_WAARNEMINGID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getWaarnemingen() {
    try {
      return Response.ok().entity(waarnemingDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taal/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getWaarnemingen(
                      @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<Waarneming>  waarnemingen  = new ArrayList<>();
      waarnemingDao.getAll()
              .forEach(waarneming ->
                          waarnemingen.add(new Waarneming(waarneming, taal)));
      return Response.ok().entity(waarnemingen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(WaarnemingDto waarnemingDto) {
    if (null == waarnemingDto.getWaarnemingId()) {
      waarnemingDao.create(waarnemingDto);
    } else {
      waarnemingDao.update(waarnemingDto);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public WaarnemingDto waarneming(Long waarnemingId) {
    WaarnemingDto resultaat = waarnemingDao.getByPrimaryKey(waarnemingId);

    if (null == resultaat) {
      return new WaarnemingDto();
    }

    return resultaat;
  }
}
