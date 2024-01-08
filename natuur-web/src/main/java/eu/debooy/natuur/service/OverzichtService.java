/*
 * Copyright (c) 2021 Marco de Booij
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
import eu.debooy.natuur.access.OverzichtDao;
import eu.debooy.natuur.domain.OverzichtDto;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.form.Rangtotaal;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurOverzichtService")
@Path("/overzicht")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Lock(LockType.WRITE)
public class OverzichtService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(OverzichtService.class);

  @SuppressWarnings("java:S6813")
  @Inject
  private OverzichtDao  overzichtDao;

  public OverzichtService() {
    LOGGER.debug("init OverzichtService");
  }

  @GET
  @Path("/{rang}/{taal}")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Response getTotalenVoorRang(
                      @PathParam(OverzichtDto.COL_RANG) String rang,
                      @PathParam(RangnaamDto.COL_TAAL) String taal) {
    Map<Long, Rangtotaal> totalen = new HashMap<>();

    try {
      overzichtDao.getOverzichtRang(rang).forEach(rij -> {
        var taxonId = rij.getParentId();
        if (totalen.containsKey(taxonId)) {
          var rangtotaal  = totalen.get(taxonId);
          rangtotaal.addOpFoto(rij.getOpFoto());
          rangtotaal.addTotaal(rij.getTotaal());
          rangtotaal.addWaargenomen(rij.getWaargenomen());
          totalen.put(taxonId, rangtotaal);
        } else {
          totalen.put(taxonId, new Rangtotaal(rij, taal));
        }
      });
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege Map gegeven.
    }

    return Response.ok().entity(totalen.values()).build();
  }
}
