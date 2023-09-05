/**
 * Copyright (c) 2017 Marco de Booij
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

import eu.debooy.doosutils.KeyValue;
import eu.debooy.doosutils.access.Dao;
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import eu.debooy.natuur.domain.TaxonnaamDto;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class TaxonnaamDao extends Dao<TaxonnaamDto> {
  @PersistenceContext(unitName="natuur",
                      type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public TaxonnaamDao() {
    super(TaxonnaamDto.class);
  }

  public Collection<KeyValue> getAantalPerTaal() {
    var query = getEntityManager().createNamedQuery(
                    TaxonnaamDto.QRY_TOTPERTAAL);

    List<Object[]>  rijen     = query.getResultList();
    Set<KeyValue>   resultaat = new HashSet<>();
    if (null != rijen) {
      for (Object[] rij : rijen) {
        resultaat.add(new KeyValue((String) rij[0],
                                   Long.toString((Long) rij[1])));
      }
    }

    return resultaat;
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public List<TaxonnaamDto> getPerTaal(String taal) {
    Map<String, Object> params  = new HashMap<>();
    params.put(TaxonnaamDto.PAR_TAAL, taal);

    return namedQuery(TaxonnaamDto.QRY_TAAL, params);
  }

  public List<TaxonnaamDto> getPerTaxon(Long taxonId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(TaxonnaamDto.PAR_TAXONID, taxonId);

    return namedQuery(TaxonnaamDto.QRY_TAXON, params);
  }

  public List<TaxonnaamDto> getZoeken(String naam) {
    Map<String, Object> params  = new HashMap<>();
    params.put(TaxonnaamDto.PAR_NAAM, naam.replace('*', '%'));

    return namedQuery(TaxonnaamDto.QRY_ZOEKEN, params);
  }
}
