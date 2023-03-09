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
import eu.debooy.natuur.access.FotoDao;
import eu.debooy.natuur.access.FotoOverzichtDao;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Foto;
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
@Named("natuurFotoService")
@Path("/fotos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class FotoService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(FotoService.class);

  @Inject
  private FotoDao           fotoDao;
  @Inject
  private FotoOverzichtDao  fotoOverzichtDao;

  public FotoService() {
    LOGGER.debug("init FotoService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long fotoId) {
    FotoDto foto  = fotoDao.getByPrimaryKey(fotoId);
    fotoDao.delete(foto);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public FotoDto foto(Long sleutel) {
    FotoDto resultaat = fotoDao.getByPrimaryKey(sleutel);

    if (null == resultaat) {
      return new FotoDto();
    }

    return resultaat;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public FotoOverzichtDto fotoTaxonSeq(Long taxonId, Long taxonSeq) {
    return fotoOverzichtDao.getTaxonSeq(taxonId, taxonSeq);
  }

  @GET
  @Path("/gebied/{gebiedId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getFotosGebied(
                      @PathParam(GebiedDto.COL_GEBIEDID) Long gebiedId) {
    try {
      return Response.ok()
                     .entity(fotoOverzichtDao.getPerGebied(gebiedId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @GET
  @Path("/taxon/{taxonId}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getFotosTaxon(@PathParam(TaxonDto.COL_TAXONID) Long taxonId) {
    try {
      return Response.ok()
                     .entity(fotoOverzichtDao.getPerTaxon(taxonId)).build();
    } catch (ObjectNotFoundException e) {
      return Response.ok().entity(new ArrayList<>()).build();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<FotoOverzichtDto> fotosPerGebied(Long gebiedId) {
    try {
      return fotoOverzichtDao.getPerGebied(gebiedId);
    } catch (ObjectNotFoundException e) {
      return new ArrayList<>();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<FotoOverzichtDto> fotosPerTaxon(Long taxonId) {
    try {
      return fotoOverzichtDao.getPerTaxon(taxonId);
    } catch (ObjectNotFoundException e) {
      return new ArrayList<>();
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<FotoOverzichtDto> fotoOverzicht() {
    try {
      return fotoOverzichtDao.getAll();
    } catch (ObjectNotFoundException e) {
      return new ArrayList<>();
    }
  }

  @GET
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getFotos() {
    return Response.ok().entity(fotoOverzichtDao.getAll()).build();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Foto> query() {
    List<Foto>  fotos = new ArrayList<>();

    fotoDao.getAll().forEach(rij -> fotos.add(new Foto(rij)));

    return fotos;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Foto> query(String taal) {
    List<Foto>  fotos = new ArrayList<>();

    fotoDao.getAll().forEach(rij -> fotos.add(new Foto(rij)));

    return fotos;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(FotoDto fotoDto) {
    if (null == fotoDto.getFotoId()) {
      fotoDao.create(fotoDto);
    } else {
      fotoDao.update(fotoDto);
    }
  }
}
