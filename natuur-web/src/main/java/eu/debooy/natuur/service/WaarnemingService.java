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
import eu.debooy.natuur.domain.WaarnemingDto;
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

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Waarneming> getGebiedWaarnemingen(Long gebiedId, String taal) {
    List<Waarneming>  waarnemingen  = new ArrayList<>();

    try {
      waarnemingDao.getPerGebied(gebiedId)
                   .forEach(rij -> waarnemingen.add(new Waarneming(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Waarneming> getTaxonWaarnemingen(Long taxonId) {
    List<Waarneming>  waarnemingen  = new ArrayList<>();

    try {
      waarnemingDao.getPerTaxon(taxonId)
                   .forEach(rij -> waarnemingen.add(new Waarneming(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }

  @GET
  @Path("/{waarnemingId}")
  public Response getWaarneming(
      @PathParam(WaarnemingDto.COL_WAARNEMINGID) Long waarnemingId) {
    WaarnemingDto waarneming;

    try {
      waarneming  = waarnemingDao.getByPrimaryKey(waarnemingId);
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(WaarnemingDto.COL_WAARNEMINGID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }

    return Response.ok().entity(waarneming).build();
  }

  @GET
  public Response getWaarnemingen() {
    return Response.ok().entity(waarnemingDao.getAll()).build();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Waarneming> query() {
    List<Waarneming>      waarnemingen  = new ArrayList<>();

    try {
      waarnemingDao.getAll()
                   .forEach(rij -> waarnemingen.add(new Waarneming(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Waarneming> query(String taal) {
    List<Waarneming>  waarnemingen  = new ArrayList<>();

    try {
      waarnemingDao.getAll()
                   .forEach(rij -> waarnemingen.add(new Waarneming(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Waarneming waarneming) {
    var dto = new WaarnemingDto();

    waarneming.persist(dto);

    save(dto);
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
    WaarnemingDto resultaat =
        waarnemingDao.getByPrimaryKey(waarnemingId);

    if (null == resultaat) {
      return new WaarnemingDto();
    }

    return resultaat;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<WaarnemingDto> waarnemingOverzicht() {
    try {
      return waarnemingDao.getAll();
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return new ArrayList<>();
  }
}
