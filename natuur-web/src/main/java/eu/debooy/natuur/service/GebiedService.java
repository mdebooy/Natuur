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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurGebiedService")
@Lock(LockType.WRITE)
public class GebiedService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(GebiedService.class);

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

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Gebied> query() {
    List<Gebied>    gebieden  = new ArrayList<>();
    List<GebiedDto> rijen     = gebiedDao.getAll();
    for (GebiedDto rij : rijen) {
      gebieden.add(new Gebied(rij));
    }

    return gebieden;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Gebied gebied) {
    GebiedDto  dto = new GebiedDto();
    gebied.persist(dto);

    if (null == gebied.getGebiedId()) {
      gebiedDao.create(dto);
      gebied.setGebiedId(dto.getGebiedId());
    } else {
      gebiedDao.update(dto);
    }
  }
}
