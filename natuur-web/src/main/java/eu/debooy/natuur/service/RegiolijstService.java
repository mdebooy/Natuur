/*
 * Copyright (c) 2023 Marco de Booij
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
import eu.debooy.natuur.access.RegiolijstDao;
import eu.debooy.natuur.domain.RegiolijstDto;
import eu.debooy.natuur.domain.TaxonDto;
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
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurRegiolijstService")
@Path("/regiolijsten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class RegiolijstService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(RegiolijstService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private RegiolijstDao regiolijstDao;

  public RegiolijstService() {
    LOGGER.debug("init RegiolijstService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long regioId) {
    regiolijstDao.delete(regiolijstDao.getByPrimaryKey(regioId));
  }

  @GET
  @Path("/{regiolijstId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getRegiolijst
      (@PathParam(RegiolijstDto.COL_REGIOLIJSTID) Long regiolijstId) {
    try {
      return Response.ok().entity(regiolijstDao.getByPrimaryKey(regiolijstId))
                     .build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new RegiolijstDto()).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getRegiolijsten() {
    try {
      return Response.ok().entity(regiolijstDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<RegiolijstDto>()).build();
    }
  }

  @GET
  @Path("/taxon/{taxonId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxonRegios(
                      @PathParam(TaxonDto.COL_TAXONID) Long taxonId) {
    try {
      return Response.ok()
                     .entity(regiolijstDao.getPerTaxon(taxonId))
                     .build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public RegiolijstDto regiolijst(Long regiolijstId) {
    return regiolijstDao.getByPrimaryKey(regiolijstId);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(RegiolijstDto regiolijst) {
    regiolijstDao.create(regiolijst);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void update(RegiolijstDto regiolijst) {
    regiolijstDao.update(regiolijst);
  }
}
