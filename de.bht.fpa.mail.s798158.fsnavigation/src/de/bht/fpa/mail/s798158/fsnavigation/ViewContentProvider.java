package de.bht.fpa.mail.s798158.fsnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ViewContentProvider implements ITreeContentProvider {

  /**
   * This method is called when the user expands a tree node in the View. The
   * parameter of the method is the selected item, and the method is expected to
   * return the direct children of the item.
   * 
   * @param parentElement
   *          the expanded element in the tree, for which the framework requests
   *          the children
   */
  @Override
  public Object[] getChildren(Object parentElement) {
    // For every parentElement, we return an empty array. That means that for
    // every given tree item, we say it has no children. Here you should cast
    // the parentElement to your own class and return its children.
    // return new Object[0];
    final MyFile elementFile = (MyFile) parentElement;
    return elementFile.getChildren();
  }

  /**
   * This method is called when the user expands a tree node in the View. The
   * framework asks is the given element has any children. The parameter of the
   * method is a tree item, and the method is expected to return
   * <code>true></code> if the item has children, or <code>false</code> if it
   * has no children.
   * 
   * @param element
   *          a tree item, for which the framework wants to know if it has
   *          children
   */
  @Override
  public boolean hasChildren(Object element) {
    // For every element, we say to the framework that is has no children. Here
    // you should cast the element to your own class and check if it has
    // children.
    final MyFile elementFile = (MyFile) element;
    return elementFile.hasChildren();
  }

  // ==========================================================================
  // In our simple tree, you don't need to change any of the following methods.
  // ==========================================================================

  @Override
  public Object[] getElements(Object parent) {
    return getChildren(parent);
  }

  @Override
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }

  @Override
  public void dispose() {
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }
}