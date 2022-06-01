/*
 * Copyright (c) 2022 Marco de Booij
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

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.GeenFotoDao;
import eu.debooy.natuur.form.GeenFoto;
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
@Named("natuurGeenFotoService")
@Lock(LockType.WRITE)
public class GeenFotoService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(GeenFotoService.class);

  @Inject
  private GeenFotoDao geenfotoDao;

  public GeenFotoService() {
    LOGGER.debug("init GeenFotoService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<GeenFoto> getGeenFotosVoorRang(String rang, String taal) {
    List<GeenFoto>  geenFotos = new ArrayList<>();

    try {
      geenfotoDao.getGeenFotoRang(rang)
                 .forEach(rij -> geenFotos.add(new GeenFoto(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return geenFotos;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<GeenFoto> getGeenFotosVoorTaxon(Long taxonId, String taal) {
    List<GeenFoto>  geenFotos = new ArrayList<>();

    try {
      geenfotoDao.getGeenFotoTaxon(taxonId)
                 .forEach(rij -> geenFotos.add(new GeenFoto(rij, taal)));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return geenFotos;
  }
}
