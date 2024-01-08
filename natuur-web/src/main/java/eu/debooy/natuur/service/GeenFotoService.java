/*
 * Copyright (c) 2022 Marco de Booij
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
package eu.debooy.natuur.service;

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.GeenFotoDao;
import eu.debooy.natuur.domain.GeenFotoDto;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.GeenFoto;
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
@Named("natuurGeenFotoService")
@Path("/geenfotos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class GeenFotoService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(GeenFotoService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private GeenFotoDao geenFotoDao;

  public GeenFotoService() {
    LOGGER.debug("init GeenFotoService");
  }

  @GET
  @Path("/rang/{rang}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGeenFotosVoorRang(
                      @PathParam(RangDto.COL_RANG) String rang) {
    try {
      return Response.ok().entity(geenFotoDao.getGeenFotoRang(rang)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/rang/{rang}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGeenFotosVoorRang(
                      @PathParam(RangDto.COL_RANG) String rang,
                      @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<GeenFoto>  geenFotos = new ArrayList<>();
      geenFotoDao.getGeenFotoRang(rang)
              .forEach(geenFoto ->
                          geenFotos.add(new GeenFoto(geenFoto, taal)));
      return Response.ok().entity(geenFotos).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taxon/{taxonId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGeenFotosVoorTaxon(
                      @PathParam(GeenFotoDto.COL_TAXONID) Long taxonId) {
    try {
      return Response.ok().entity(geenFotoDao.getGeenFotoTaxon(taxonId))
                     .build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taxon/{taxonId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGeenFotosVoorTaxon(
                      @PathParam(GeenFotoDto.COL_TAXONID) Long taxonId,
                      @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<GeenFoto>  geenFotos = new ArrayList<>();
      geenFotoDao.getGeenFotoTaxon(taxonId)
              .forEach(geenFoto ->
                          geenFotos.add(new GeenFoto(geenFoto, taal)));
      return Response.ok().entity(geenFotos).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }
}
