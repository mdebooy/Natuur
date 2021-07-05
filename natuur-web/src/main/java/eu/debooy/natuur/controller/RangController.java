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
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Rangtotaal;
import eu.debooy.natuur.validator.RangValidator;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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

  private Rang  rang;

  public void create() {
    rang  = new Rang();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.rang.create");
    redirect(RANG_REDIRECT);
  }

  public void delete(String rang) {
    try {
      getRangService().delete(rang);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format("RT: %s", e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, getRangtekst(rang));
  }

  public Rang getRang() {
    return rang;
  }

  public Collection<Rang> getRangen() {
    return getRangService().query(getGebruikersTaal());
  }

  public String getRangtekst(String rang) {
    return getRangService().rang(rang).getNaam(getGebruikersTaal());
  }

  public Collection<Rangtotaal> getRangtotalen() {
    String            taal  = getGebruikersTaal();
    List<Rangtotaal>  rijen =
        getDetailService().getTotalenVoorRang(rang.getRang());
    rijen.forEach(rij -> {
      String  naam;
      try {
        naam  = getTaxonnaamService().taxonnaam(rij.getTaxonId(), taal)
                                     .getNaam();
      } catch (ObjectNotFoundException e) {
        naam  = rij.getLatijnsenaam();
      }
      if (DoosUtils.isBlankOrNull(naam)) {
        rij.setNaam(rij.getLatijnsenaam());
      } else {
        rij.setNaam(naam);
      }
    });

    return rijen;
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
    this.rang = new Rang(getRangService().rang(rang));
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
      LOGGER.error(String.format("RT: %s", e.getLocalizedMessage()), e);
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
    this.rang = new Rang(getRangService().rang(rang));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.rang.update");
    redirect(RANG_REDIRECT);
  }
}
