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
package eu.debooy.natuur.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.natuur.domain.TaxonnaamDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
public class TaxonnaamDao extends Dao<TaxonnaamDto> {
  @PersistenceContext(unitName="natuur", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public TaxonnaamDao() {
    super(TaxonnaamDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public List<TaxonnaamDto> getPerTaxon(Long taxonId) {
    Map<String, Object> params  = new HashMap<String, Object>();
    params.put("taxonId", taxonId);

    return namedQuery("perTaxon", params);
  }

  public List<TaxonnaamDto> getPerTaal(String taal) {
    Map<String, Object> params  = new HashMap<String, Object>();
    params.put("taal", taal);

    return namedQuery("taxonnamenPerTaal", params);
  }
}
