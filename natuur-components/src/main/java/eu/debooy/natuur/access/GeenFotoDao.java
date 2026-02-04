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
package eu.debooy.natuur.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import static eu.debooy.natuur.NatuurConstants.NATUUR_EM;
import eu.debooy.natuur.domain.GeenFotoDto;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import java.util.List;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class GeenFotoDao extends Dao<GeenFotoDto> {
  @PersistenceContext(unitName=NATUUR_EM,
                      type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public GeenFotoDao() {
    super(GeenFotoDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  @Override
  public GeenFotoDto create(GeenFotoDto dto) {
    throw new UnsupportedOperationException();
  }

  public List<GeenFotoDto> getGeenFotoRang(String rang) {
    return getEntityManager().createNamedQuery(GeenFotoDto.QRY_PERRANG)
                             .setParameter(GeenFotoDto.PAR_PARENTRANG, rang)
                             .getResultList();
  }

  public List<GeenFotoDto> getGeenFotoTaxon(Long taxonId) {
    return getEntityManager().createNamedQuery(GeenFotoDto.QRY_PERTAXON)
                             .setParameter(GeenFotoDto.PAR_TAXONID, taxonId)
                             .getResultList();
  }
}
