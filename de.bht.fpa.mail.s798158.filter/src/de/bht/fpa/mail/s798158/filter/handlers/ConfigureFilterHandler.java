package de.bht.fpa.mail.s798158.filter.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.filter.FilterGroupType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.NullFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s798158.filter.ImportanceFilter;
import de.bht.fpa.mail.s798158.filter.IntersectionFilter;
import de.bht.fpa.mail.s798158.filter.ReadFilter;
import de.bht.fpa.mail.s798158.filter.RecipientsFilter;
import de.bht.fpa.mail.s798158.filter.SenderFilter;
import de.bht.fpa.mail.s798158.filter.SubjectFilter;
import de.bht.fpa.mail.s798158.filter.TextFilter;
import de.bht.fpa.mail.s798158.filter.UnionFilter;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ConfigureFilterHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public ConfigureFilterHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    FilterDialog filterDialog = new FilterDialog(window.getShell());
    filterDialog.open();

    List<FilterCombination> filterCombinations = filterDialog.getFilterCombinations();
    FilterGroupType filterGroupType = filterDialog.getFilterGroupType();
    List<IFilter> innerFilters = new ArrayList<IFilter>();

    for (FilterCombination f : filterCombinations) {

      // Filterliste bilden
      switch (f.getFilterType()) {
      case SENDER:
        innerFilters.add(new SenderFilter((String) f.getFilterValue(), f.getFilterOperator()));
        break;
      case RECIPIENTS:
        innerFilters.add(new RecipientsFilter((String) f.getFilterValue(), f.getFilterOperator()));
        break;
      case SUBJECT:
        innerFilters.add(new SubjectFilter((String) f.getFilterValue(), f.getFilterOperator()));
        break;
      case TEXT:
        innerFilters.add(new TextFilter((String) f.getFilterValue(), f.getFilterOperator()));
        break;
      case READ:
        innerFilters.add(new ReadFilter((boolean) f.getFilterValue()));
        break;
      case IMPORTANCE:
        innerFilters.add(new ImportanceFilter((Importance) f.getFilterValue()));
        break;
      default:
        innerFilters.add(new NullFilter());
      }
    }

    // in Kombinationsobjekt kippen
    switch (filterGroupType) {
    case INTERSECTION:
      for (IFilter i : innerFilters) {
        System.out.println(i);
      }
      return new IntersectionFilter(innerFilters);
    case UNION:
      return new UnionFilter(innerFilters);
    default:
      return new NullFilter();
    }
  }
}
