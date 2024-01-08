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
package eu.debooy.natuur.service;

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.GebiedDao;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.form.Gebied;
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
@Named("natuurGebiedService")
@Path("/gebieden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class GebiedService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(GebiedService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private GebiedDao gebiedDao;

  public GebiedService() {
    LOGGER.debug("init GebiedService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long gebiedId) {
    GebiedDto gebied  = gebiedDao.getByPrimaryKey(gebiedId);
    gebiedDao.delete(gebied);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public GebiedDto gebied(Long gebiedId) {
    GebiedDto resultaat = gebiedDao.getByPrimaryKey(gebiedId);

    if (null == resultaat) {
      return new GebiedDto();
    }

    return resultaat;
  }

  @GET
  @Path("/{gebiedId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGebied(@PathParam(GebiedDto.COL_GEBIEDID) Long gebiedId) {
    try {
      return Response.ok().entity(gebiedDao.getByPrimaryKey(gebiedId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(GebiedDto.COL_GEBIEDID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getGebieden() {
    try {
      return Response.ok().entity(gebiedDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Gebied> query() {
    List<Gebied>    gebieden  = new ArrayList<>();

    try {
      List<GebiedDto> rijen   = gebiedDao.getAll();
      rijen.forEach(rij -> gebieden.add(new Gebied(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return gebieden;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(GebiedDto gebied) {
    if (null == gebied.getGebiedId()) {
      gebiedDao.create(gebied);
    } else {
      gebiedDao.update(gebied);
    }
  }
}
