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
package eu.debooy.natuur.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import eu.debooy.natuur.domain.DetailDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class DetailDao extends Dao<DetailDto> {
  @PersistenceContext(unitName="natuur",
                      type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public DetailDao() {
    super(DetailDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  @Override
  public DetailDto create(DetailDto dto) {
    throw new UnsupportedOperationException();
  }

  public List<DetailDto> getSoortenMetKlasse() {
    return namedQuery(DetailDto.QRY_SOORTMETKLASSE);
  }

  public List<DetailDto> getSoortenMetParent(Long parentId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(DetailDto.PAR_PARENTID, parentId);

    return namedQuery(DetailDto.QRY_SOORTMETPARENT, params);
  }

  public List<DetailDto> getUitgestorvenPerKlasse() {
    return namedQuery(DetailDto.QRY_UITGESTORVENPERKLASSE);
  }

  public List<DetailDto> getVanRegiolijst(Long regioId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(DetailDto.PAR_REGIOID, regioId);

    return namedQuery(DetailDto.QRY_VANREGIIOLIJST, params);
  }

  public List<DetailDto> getWaargenomen() {
    return namedQuery(DetailDto.QRY_WAARGENOMEN);
  }
}
