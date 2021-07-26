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
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.validator.GebiedValidator;
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
@Named("natuurGebied")
@SessionScoped
public class GebiedController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(GebiedController.class);

  private Gebied  gebied;

  public void create() {
    gebied  = new Gebied();
    gebied.setLandId(Long.parseLong(getParameter("natuur.default.landid")));
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.gebied.create");
    redirect(GEBIED_REDIRECT);
  }

  public void delete(Long gebiedId, String naam) {
    try {
      getGebiedService().delete(gebiedId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format("RT: %s", e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, naam);
  }

  public String gebied(Long gebiedId) {
    return getGebiedService().gebied(gebiedId).getNaam();
  }

  public Gebied getGebied() {
    return gebied;
  }

  public Collection<Gebied> getGebieden() {
    return getGebiedService().query();
  }

  public Collection<SelectItem> getSelectGebieden() {
    List<SelectItem>  items = new LinkedList<>();
    Set<Gebied>       rijen = new TreeSet<>(new Gebied.NaamComparator());
    rijen.addAll(getGebiedService().query());
    rijen.forEach(rij -> items.add(new SelectItem(rij, rij.getNaam())));

    return items;
  }

  public Collection<SelectItem> getSelectGebiedenId() {
    List<SelectItem>  items = new LinkedList<>();
    Set<Gebied>       rijen = new TreeSet<>(new Gebied.NaamComparator());
    rijen.addAll(getGebiedService().query());
    rijen.forEach(rij -> items.add(new SelectItem(rij.getGebiedId(),
                                                  rij.getNaam())));

    return items;
  }

  public Collection<SelectItem> getLatitudes() {
    List<SelectItem>  items = new LinkedList<>();
    items.add(new SelectItem("N", getTekst("windstreek.N")));
    items.add(new SelectItem("S", getTekst("windstreek.S")));

    return items;
  }

  public Collection<SelectItem> getLongitudes() {
    List<SelectItem>  items = new LinkedList<>();
    items.add(new SelectItem("E", getTekst("windstreek.E")));
    items.add(new SelectItem("W", getTekst("windstreek.W")));

    return items;
  }

  public void save() {
    List<Message> messages  = GebiedValidator.valideer(gebied);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getGebiedService().save(gebied);
      switch (getAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        addInfo(PersistenceConstants.CREATED, gebied.getNaam());
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, gebied.getNaam());
        break;
      default:
        addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
        break;
      }
      redirect(GEBIEDEN_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, gebied.getNaam());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, gebied.getNaam());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format("RT: %s", e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void update(Long gebiedId) {
    gebied  = new Gebied(getGebiedService().gebied(gebiedId));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.gebied.update");
    redirect(GEBIED_REDIRECT);
  }
}
