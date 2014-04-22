package de.bht.fpa.mail.s798158.fsnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ViewLabelProvider extends LabelProvider {
  @Override
  public String getText(Object element) {
    // here you decide for each tree item which text to show. You usually do a
    // bunch on instanceof checks for every possible type in your tree.
    return super.getText(element);
  }

  @Override
  public Image getImage(Object element) {
    // here you decide for each tree item which icon to show. You usually do a
    // bunch on instanceof checks for every possible type in your tree.
    return super.getImage(element);
  }
}
