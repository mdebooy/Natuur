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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.TaxonnaamDao;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.domain.TaxonnaamPK;
import eu.debooy.natuur.form.AantalPerTaal;
import eu.debooy.natuur.form.Taxonnaam;
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
@Named("natuurTaxonnaamService")
@Path("/taxonnamen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class TaxonnaamService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonnaamService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private TaxonnaamDao taxonnaamDao;

  public TaxonnaamService() {
    LOGGER.debug("init TaxonnaamService");
  }

  @GET
  @Path("/aantalpertaal")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getAantalPerTaal() {
    try {
      var totalen = new ArrayList<AantalPerTaal>();
      taxonnaamDao.getAantalPerTaal().forEach(rij ->
          totalen.add(new AantalPerTaal(rij.getSleutel(),
                                       Long.valueOf(rij.getWaarde()))));
      return Response.ok().entity(totalen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonnaamDto taxonnaam(Long taxonId, String taal) {
    return taxonnaamDao.getByPrimaryKey(new TaxonnaamPK(taxonId, taal));
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxonnaam> taxonnamen(String taal) {
    List<Taxonnaam>     taxonnamen  = new ArrayList<>();
    List<TaxonnaamDto>  rijen       = taxonnaamDao.getPerTaal(taal);
    rijen.forEach(rij -> taxonnamen.add(new Taxonnaam(rij)));

    return taxonnamen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxonnaam> query(Long taxonId) {
    List<Taxonnaam>     taxonnamen  = new ArrayList<>();
    List<TaxonnaamDto>  rijen       = taxonnaamDao.getPerTaxon(taxonId);
    rijen.forEach(rij -> taxonnamen.add(new Taxonnaam(rij)));

    return taxonnamen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectTaxonnamen(String taal) {
    List<SelectItem>  items = new LinkedList<>();
    Set<TaxonnaamDto> rijen = new TreeSet<>(new TaxonnaamDto.NaamComparator());
    rijen.addAll(taxonnaamDao.getPerTaal(taal));
    rijen.forEach(rij -> items.add(new SelectItem(rij.getTaxonId(),
                                                  rij.getNaam())));

    return items;
  }

  @GET
  @Path("/zoek/{naam}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getZoek(@PathParam(TaxonnaamDto.COL_NAAM) String naam) {
    if (DoosUtils.isBlankOrNull(naam)) {
      return Response.ok().entity(new ArrayList<>()).build();
    }

    try {
      var taxonnamen  = new ArrayList<Taxonnaam>();
      taxonnaamDao.getZoeken(naam).forEach(rij ->
          taxonnamen.add(new Taxonnaam(rij)));
      return Response.ok().entity(taxonnamen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }
}
