/*
 * Copyright (c) 2025 Marco de Booij
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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.TaxonbeschrijvingDao;
import eu.debooy.natuur.domain.TaxonbeschrijvingDto;
import eu.debooy.natuur.domain.TaxonbeschrijvingPK;
import eu.debooy.natuur.form.Taxonbeschrijving;
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
@Named("natuurTaxonbeschrijvingService")
@Path("/taxonbeschrijvingen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class TaxonbeschrijvingService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonbeschrijvingService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private TaxonbeschrijvingDao  taxonbeschrijvingDao;

  public TaxonbeschrijvingService() {
    LOGGER.debug("init TaxonbeschrijvingService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(TaxonbeschrijvingDto taxonbeschrijving) {
    taxonbeschrijvingDao.delete(taxonbeschrijving);
  }

  @GET
  @Path("/taxon/{taxonId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getPerTaxon(
      @PathParam(TaxonbeschrijvingDto.COL_TAXONID) Long taxonId) {
    if (DoosUtils.isBlankOrNull(taxonId)) {
      return Response.ok().entity(new ArrayList<>()).build();
    }

    try {
      var taxonbeschrijvingen  = new ArrayList<Taxonbeschrijving>();
      taxonbeschrijvingDao.getPerTaxon(taxonId).forEach(rij ->
          taxonbeschrijvingen.add(new Taxonbeschrijving(rij)));
      return Response.ok().entity(taxonbeschrijvingen).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(TaxonbeschrijvingDto taxonbeschrijving) {
    taxonbeschrijvingDao.create(taxonbeschrijving);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonbeschrijvingDto taxonbeschrijving(Long taxonId, String taal) {
    return taxonbeschrijvingDao.getByPrimaryKey(new TaxonbeschrijvingPK(taxonId,
                                                                        taal));
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void update(TaxonbeschrijvingDto taxonbeschrijving) {
    taxonbeschrijvingDao.update(taxonbeschrijving);
  }
}
