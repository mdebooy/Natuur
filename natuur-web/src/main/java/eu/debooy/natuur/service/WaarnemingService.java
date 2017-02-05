/**
 * Copyright 2017 Marco de Booij
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
import eu.debooy.natuur.access.WaarnemingDao;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Waarneming;

import java.util.ArrayList;
import java.util.Collection;

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
@Named("natuurWaarnemingService")
@Lock(LockType.WRITE)
public class WaarnemingService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(WaarnemingService.class);

  @Inject
  private WaarnemingDao           waarnemingDao;

  public WaarnemingService() {
    LOGGER.debug("init WaarnemingService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long waarnemingId) {
    WaarnemingDto waarneming  = waarnemingDao.getByPrimaryKey(waarnemingId);
    waarnemingDao.delete(waarneming);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Waarneming> query() {
    Collection<Waarneming>  waarnemingen = new ArrayList<Waarneming>();
    try {
      Collection<WaarnemingDto> rijen = waarnemingDao.getAll();
      for (WaarnemingDto rij : rijen) {
        waarnemingen.add(new Waarneming(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Waarneming> query(String taal) {
    Collection<Waarneming>  waarnemingen = new ArrayList<Waarneming>();
    try {
      Collection<WaarnemingDto> rijen = waarnemingDao.getAll();
      for (WaarnemingDto rij : rijen) {
        waarnemingen.add(new Waarneming(rij, taal));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Waarneming waarneming) {
    WaarnemingDto dto = new WaarnemingDto();
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
  public WaarnemingDto waarneming(Long sleutel) {
    WaarnemingDto waarneming    = waarnemingDao.getByPrimaryKey(sleutel);

    return waarneming;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<WaarnemingDto> waarnemingOverzicht() {
    Collection<WaarnemingDto>  waarnemingen = new ArrayList<WaarnemingDto>();
    try {
      waarnemingen = waarnemingDao.getAll();
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return waarnemingen;
  }
}
