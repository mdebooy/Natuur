/**
 * Copyright (c) 2023 Marco de Booij
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
package eu.debooy.natuur.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import static eu.debooy.natuur.NatuurConstants.NATUUR_EM;
import eu.debooy.natuur.domain.RegiolijstDto;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class RegiolijstDao extends Dao<RegiolijstDto> {
  @PersistenceContext(unitName=NATUUR_EM,
                      type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public RegiolijstDao() {
    super(RegiolijstDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public Object getPerTaxon(Long taxonId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(RegiolijstDto.PAR_TAXONID, taxonId);

    return namedQuery(RegiolijstDto.QRY_PERTAXON, params);
  }
}
