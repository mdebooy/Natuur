/**
 * Copyright (c) 2016 Marco de Booij
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
package eu.debooy.natuur.controller;

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Rangnaam;
import eu.debooy.natuur.form.Rangtotaal;
import eu.debooy.natuur.validator.RangValidator;
import eu.debooy.natuur.validator.RangnaamValidator;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurRang")
@SessionScoped
public class RangController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(RangController.class);

  private Rang        rang;
  private RangDto     rangDto;
  private Rangnaam    rangnaam;
  private RangnaamDto rangnaamDto;

  public void create() {
    rang    = new Rang();
    rangDto = new RangDto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.rang.create");
    redirect(RANG_REDIRECT);
  }

  public void createRangnaam() {
    rangnaam    = new Rangnaam();
    rangnaam.setTaal(getGebruikersTaal());
    rangnaam.setRang(rang.getRang());
    rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(getGebruikersTaal());
    rangnaamDto.setRang(rangDto.getRang());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel("natuur.titel.rangnaam.create");
    redirect(RANGNAAM_REDIRECT);
  }

  public void delete(String rang) {
    try {
      getRangService().delete(rang);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, getRangtekst(rang));
  }

  public void deleteRangnaam(String taal) {
    try {
      rangDto.removeRangnaam(taal);
      getRangService().save(rangDto);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taal);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, "'" + taal + "'");
  }

  public Rang getRang() {
    return rang;
  }

  public Collection<Rang> getRangen() {
    return getRangService().query(getGebruikersTaal());
  }

  public Rangnaam getRangnaam() {
    return rangnaam;
  }

  public Collection<Rangnaam> getRangnamen() {
    Collection<Rangnaam>  rangnamen = new HashSet<>();
    rangDto.getRangnamen().forEach(rij -> rangnamen.add(new Rangnaam(rij)));

    return rangnamen;
  }

  public String getRangtekst(String rang) {
    return getRangService().rang(rang).getNaam(getGebruikersTaal());
  }

  public Collection<Rangtotaal> getRangtotalen() {
    Map<Long, Rangtotaal> totalen = new HashMap<>();

    getOverzichtService()
        .getTotalenVoorRang(rang.getRang()).forEach(rij -> {
      Long  taxonId = rij.getParentId();
      if (totalen.containsKey(taxonId)) {
        Rangtotaal  rangtotaal  = totalen.get(taxonId);
        rangtotaal.addOpFoto(rij.getOpFoto());
        rangtotaal.addTotaal(rij.getTotaal());
        rangtotaal.addWaargenomen(rij.getWaargenomen());
        totalen.put(taxonId, rangtotaal);
      } else {
        totalen.put(taxonId, new Rangtotaal(rij, getGebruikersTaal()));
      }
    });

    return totalen.values();
  }

  public List<SelectItem> getSelectRangen() {
    List<SelectItem>  items = new LinkedList<>();
    Set<Rang>         rijen = new TreeSet<>(new Rang.NiveauComparator());
    rijen.addAll(getRangService().query());
    rijen.forEach(rij ->  items.add(new SelectItem(rij.getRang(),
                                    getRangtekst(rij.getRang()))));

    return items;
  }

  public void retrieve(String rang) {
    rangDto   = getRangService().rang(rang);
    this.rang = new Rang(rangDto);
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(MessageFormat.format(getTekst("natuur.titel.rang.totalen"),
                                     getRangtekst(rang)));
    redirect(RANG_TOTALEN_REDIRECT);
  }

  public void save() {
    List<Message> messages  = RangValidator.valideer(rang);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getRangService().save(rang);
      switch (getAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        addInfo(PersistenceConstants.CREATED, "'" + rang.getRang() + "'");
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, "'" + rang.getRang() + "'");
        break;
      default:
        addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie()) ;
        break;
      }
      redirect(RANGEN_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, rang.getRang());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang.getRang());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void saveRangnaam() {
    List<Message> messages  = RangnaamValidator.valideer(rangnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      rangnaamDto  = new RangnaamDto();
      rangnaam.persist(rangnaamDto);
      rangDto.addNaam(rangnaamDto);
      getRangService().save(rangDto);
      switch (getDetailAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        addInfo(PersistenceConstants.CREATED, "'" + rangnaam.getTaal() + "'");
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, "'" + rangnaam.getTaal() + "'");
        break;
      default:
        addError(ComponentsConstants.WRONGREDIRECT,
                 getDetailAktie().getAktie());
        break;
      }
      redirect(RANG_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, rangnaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rangnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public List<SelectItem> selectRangen(Long niveau) {
    List<SelectItem>  items = new LinkedList<>();
    Set<Rang>         rijen = new TreeSet<>(new Rang.NiveauComparator());
    rijen.addAll(getRangService().query(niveau));
    rijen.forEach(rij ->  items.add(new SelectItem(rij.getRang(),
                                    getRangtekst(rij.getRang()))));

    return items;
  }

  public void update(String rang) {
    rangDto   = getRangService().rang(rang);
    this.rang = new Rang(rangDto, getGebruikersTaal());
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.rang.update");
    redirect(RANG_REDIRECT);
  }

  public void updateRangnaam(String taal) {
    rangnaamDto  = rangDto.getRangnaam(taal);
    rangnaam     = new Rangnaam(rangnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("natuur.titel.rangnaam.update");
    redirect(RANGNAAM_REDIRECT);
  }
}
