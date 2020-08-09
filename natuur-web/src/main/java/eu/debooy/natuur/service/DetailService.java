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

import eu.debooy.natuur.access.DetailDao;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.form.Rangtotaal;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurDetailService")
@Lock(LockType.WRITE)
public class DetailService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(DetailService.class);

  @Inject
  private DetailDao       detailDao;

  public DetailService() {
    LOGGER.debug("init DetailService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getSoortenMetKlasse(String taal) {
    List<Taxon>     details = new ArrayList<>();
    List<DetailDto> rijen   = detailDao.getSoortenMetKlasse();
    for (DetailDto rij : rijen) {
      details.add(new Taxon(rij, taal));
    }

    return details;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Rangtotaal> getTotalenVoorRang(String rang) {
    return detailDao.getSoortenMetRang(rang);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getWaargenomen(String taal) {
    List<Taxon>     soorten = new ArrayList<>();
    List<DetailDto> rijen   = detailDao.getWaargenomen();
    for (DetailDto rij : rijen) {
      soorten.add(new Taxon(rij, taal));
    }

    return soorten;
  }
}
