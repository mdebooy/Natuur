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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.DetailDao;
import eu.debooy.natuur.access.WaarnemingDao;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.form.Taxon;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurDetailService")
@Path("/details")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class DetailService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(DetailService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private DetailDao detailDao;
  @SuppressWarnings("java:S6813")
  @Inject
  private WaarnemingDao       waarnemingDao;

  public DetailService() {
    LOGGER.debug("init DetailService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getSoortenMetKlasse(String taal) {
    List<Taxon> details = new ArrayList<>();

    try {
      detailDao.getSoortenMetKlasse()
               .forEach(rij -> details.add(new Taxon(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return details;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<DetailDto> getSoortenMetParent(Long parentId) {
    List<DetailDto> soorten = new ArrayList<>();

    try {
      soorten.addAll(detailDao.getSoortenMetParent(parentId));
      setGezien(soorten);
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<DetailDto> getVanRegiolijst(Long regioId) {
    List<DetailDto> soorten = new ArrayList<>();

    try {
      soorten.addAll(detailDao.getVanRegiolijst(regioId));
      setGezien(soorten);
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getWaargenomen(String taal) {
    List<Taxon> soorten = new ArrayList<>();

    try {
      detailDao.getWaargenomen()
               .forEach(rij -> soorten.add(new Taxon(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  private void setGezien(List<DetailDto> taxa) {
    var gezien  = waarnemingDao.getTaxa();

    taxa.forEach(rij -> rij.setGezien(gezien.contains(rij.getTaxonId())));
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response waarnemingen() {
    try {
      return Response.ok().entity(detailDao.getWaargenomen()).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }
}
