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
import eu.debooy.natuur.access.RangDao;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.form.Rang;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
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
@Named("natuurRangService")
@Path("/rangen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class RangService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonService.class);

  @Inject
  private RangDao rangDao;

  public RangService() {
    LOGGER.debug("init RangService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(String sleutel) {
    RangDto rang  = rangDao.getByPrimaryKey(sleutel);
    rangDao.delete(rang);
  }

  @GET
  public Response getRangen() {
    return Response.ok().entity(rangDao.getAll()).build();
  }

  @GET
  @Path("/{rang}")
  public Response getRang(@PathParam(RangDto.COL_RANG) String rang) {
    RangDto rangDto;
    try {
      rangDto = rangDao.getByPrimaryKey(rang);
    } catch (ObjectNotFoundException e) {
      Message message = new Message.Builder()
                                   .setAttribute(RangDto.COL_RANG)
                                   .setMessage(PersistenceConstants.NOTFOUND)
                                   .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }

    return Response.ok().entity(rangDto).build();
  }

  @GET
  @Path("/vanaf/{niveau}")
  public Response getVanafRang(@PathParam(RangDto.COL_NIVEAU) Long niveau) {
    List<RangDto> rangen;
    try {
      rangen  = rangDao.getVanaf(niveau);
    } catch (ObjectNotFoundException e) {
      Message message = new Message.Builder()
                                   .setAttribute(RangDto.COL_NIVEAU)
                                   .setMessage(PersistenceConstants.NOTFOUND)
                                   .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }

    return Response.ok().entity(rangen).build();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Rang> query() {
    List<Rang>  rangen  = new ArrayList<>();

    try {
      rangDao.getAll().forEach(rij -> rangen.add(new Rang(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return rangen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Rang> query(Long niveau) {
    List<Rang>  groter  = new ArrayList<>();

    rangDao.getAll().stream()
                    .filter(rij -> (rij.getNiveau().compareTo(niveau) > 0))
                    .forEachOrdered(rij ->  groter.add(new Rang(rij)));

    return groter;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public RangDto rang(String rang) {
    try {
      return rangDao.getByPrimaryKey(rang);
    } catch (ObjectNotFoundException e) {
      return new RangDto();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Rang rang) {
    RangDto dto = new RangDto();
    rang.persist(dto);

    rangDao.create(dto);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectRangen() {
    List<SelectItem>  items = new LinkedList<>();
    Set<RangDto>      rijen = new TreeSet<>(new RangDto.NiveauComparator());

    try {
      rijen.addAll(rangDao.getAll());
      rijen.forEach(rij -> items.add(new SelectItem(rij.getRang(),
                                                    rij.getRang())));
    } catch (NullPointerException | ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return items;
  }
}
