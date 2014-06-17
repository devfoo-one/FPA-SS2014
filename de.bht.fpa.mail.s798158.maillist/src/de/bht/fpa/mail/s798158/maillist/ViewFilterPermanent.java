package de.bht.fpa.mail.s798158.maillist;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Diese Klasse erweitert einen ViewerFilter mit der Methode setSearchString.
 * Dieser Filter kann permanent an einem View bleiben und durch die Methode
 * rekonfiguriert werden.
 * 
 * @author tom
 * 
 */
public class ViewFilterPermanent extends ViewerFilter {

  private String searchString;

  public ViewFilterPermanent(final String searchString) {
    this.searchString = searchString;
  }

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    return false;
  }

  public void setSearchString(final String searchString) {
    this.searchString = searchString;
  }

  public String getSearchString() {
    return searchString;
  }

}
