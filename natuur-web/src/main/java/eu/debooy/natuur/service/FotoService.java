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

import eu.debooy.natuur.access.FotoDao;
import eu.debooy.natuur.access.FotoOverzichtDao;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoOverzichtDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurFotoService")
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
  public List<FotoOverzichtDto> fotosPerTaxon(Long taxonId) {
    return fotoOverzichtDao.getPerTaxon(taxonId);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<FotoOverzichtDto> fotoOverzicht() {
    return fotoOverzichtDao.getAll();
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Foto> query() {
    List<Foto>    fotos = new ArrayList<>();

    fotoDao.getAll().forEach(rij -> fotos.add(new Foto(rij)));

    return fotos;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Foto> query(String taal) {
    List<Foto>    fotos = new ArrayList<>();

    fotoDao.getAll().forEach(rij -> fotos.add(new Foto(rij)));

    return fotos;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Foto foto) {
    FotoDto  dto = new FotoDto();
    foto.persist(dto);

    save(dto);
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
