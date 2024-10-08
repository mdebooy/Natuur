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
import eu.debooy.natuur.access.TaxonDao;
import eu.debooy.natuur.access.TaxonnaamDao;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.domain.TaxonnaamPK;
import eu.debooy.natuur.form.Taxon;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
@Named("natuurTaxonService")
@Path("/taxa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class TaxonService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private RangDao       rangDao;
  @SuppressWarnings("java:S6813")
  @Inject
  private TaxonDao      taxonDao;
  @SuppressWarnings("java:S6813")
  @Inject
  private TaxonnaamDao  taxonnaamDao;

  public TaxonService() {
    LOGGER.debug("init TaxonService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long taxonId) {
    taxonDao.delete(taxonDao.getByPrimaryKey(taxonId));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<TaxonDto> getKinderen(Long parentId) {
    try {
      return taxonDao.getKinderen(parentId);
    } catch (ObjectNotFoundException e) {
      return new ArrayList<>();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<TaxonDto> getOuders(Long kind) {
    try {
      return taxonDao.getOuders(kind);
    } catch (ObjectNotFoundException e) {
      return new ArrayList<>();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getSoorten() {
    List<Taxon> soorten = new ArrayList<>();

    try {
      taxonDao.getSoorten().forEach(rij -> soorten.add(new Taxon(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getSoorten(String taal) {
    List<Taxon> soorten = new ArrayList<>();

    try {
      taxonDao.getSoorten().forEach(rij -> soorten.add(new Taxon(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxa() {
    try {
      return Response.ok().entity(taxonDao.getAll()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taal/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxa(@PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<Taxon> taxa  = new ArrayList<>();
      taxonDao.getAll().forEach(taxon -> taxa.add(new Taxon(taxon, taal)));
      return Response.ok().entity(taxa).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/{taxonId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxon(@PathParam(TaxonDto.COL_TAXONID) Long taxonId) {
    try {
      return Response.ok().entity(taxonDao.getTaxon(taxonId)).build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(TaxonDto.COL_TAXONID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @Path("/{taxonId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxon(@PathParam(TaxonDto.COL_TAXONID) Long taxonId,
                           @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      return Response.ok().entity(new Taxon(taxonDao.getTaxon(taxonId), taal))
                     .build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(TaxonDto.COL_TAXONID)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @Path("/taxonnaam/{taxonId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTaxonnaam(@PathParam(TaxonDto.COL_TAXONID) Long taxonId,
                               @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      return Response.ok().entity(taxonnaamDao
                                    .getByPrimaryKey(new TaxonnaamPK(taxonId,
                                                                     taal)))
                     .build();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(TaxonnaamDto.COL_TAAL)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }
  }

  @GET
  @Path("/kinderen/{parentId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response kinderen(@PathParam(TaxonDto.COL_PARENTID) Long parentId) {
    try {
      return Response.ok().entity(taxonDao.getKinderen(parentId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/kinderen/{parentId}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response kinderen(@PathParam(TaxonDto.COL_PARENTID) Long parentId,
                           @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    try {
      List<Taxon> taxa  = new ArrayList<>();
      taxonDao.getKinderen(parentId)
              .forEach(taxon -> taxa.add(new Taxon(taxon, taal)));
      return Response.ok().entity(taxa).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> query() {
    List<Taxon> taxa  = new ArrayList<>();

    try {
      taxonDao.getAll().forEach(rij -> taxa.add(new Taxon(rij)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxa;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> query(String taal) {
    List<Taxon> taxa  = new ArrayList<>();

    try {
      taxonDao.getAll().forEach(rij -> taxa.add(new Taxon(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxa;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(TaxonDto taxon) {
    if (null == taxon.getTaxonId()) {
      taxonDao.create(taxon);
      taxon.setTaxonId(taxon.getTaxonId());
    } else {
      taxonDao.update(taxon);
    }
  }

  @GET
  @Path("/ddlb/ouders/{rang}/{taal}")
  public Response selectOuders(@PathParam(TaxonDto.COL_RANG) String rang,
                               @PathParam(TaxonnaamDto.COL_TAAL) String taal) {
    Long  niveau;

    try {
      niveau  = rangDao.getByPrimaryKey(rang).getNiveau();
    } catch (ObjectNotFoundException e) {
      var message = new Message.Builder()
                               .setAttribute(TaxonDto.COL_RANG)
                               .setMessage(PersistenceConstants.NOTFOUND)
                               .setSeverity(Message.ERROR).build();
      return Response.status(400).entity(message).build();
    }

    Map<String, String> items = new LinkedHashMap<>();
    var                 comp  = new TaxonDto.NaamComparator();
    comp.setTaal(taal);
    Set<TaxonDto>       rijen = new TreeSet<>(comp);
    try {
      rijen.addAll(taxonDao.getOuders(niveau));
      rijen.forEach(rij ->  items.put(" " + rij.getTaxonId(),
                                      rij.getNaam(taal)
                                          + " (" + rij.getLatijnsenaam()
                                          + ")"));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return Response.ok().entity(items).build();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonDto taxon(Long taxonId) {
    TaxonDto  resultaat = taxonDao.getByPrimaryKey(taxonId);

    if (null == resultaat) {
      return new TaxonDto();
    }

    return resultaat;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonDto taxon(String latijnsenaam) {
    TaxonDto  resultaat = taxonDao.getTaxon(latijnsenaam);

    if (null == resultaat) {
      return new TaxonDto();
    }

    return resultaat;
  }
}
