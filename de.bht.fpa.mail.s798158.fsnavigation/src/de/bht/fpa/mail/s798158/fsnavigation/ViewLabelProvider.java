package de.bht.fpa.mail.s798158.fsnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.bht.fpa.mail.s798158.common.IDirectory;

public class ViewLabelProvider extends LabelProvider {
  @Override
  public String getText(Object element) {
    // here you decide for each tree item which text to show. You usually do a
    // bunch on instanceof checks for every possible type in your tree.
    IDirectory elementFile = (IDirectory) element;
    return elementFile.getName();
  }

  @Override
  public Image getImage(Object element) {
    // here you decide for each tree item which icon to show. You usually do a
    // bunch on instanceof checks for every possible type in your tree.

    final IDirectory elementMyFSO = (IDirectory) element;
    final Image img;

    if (elementMyFSO instanceof MyDirectory) {
      img = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/folder.png").createImage();
    } else {
      img = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/document-new.png").createImage();
    }
    return img;
  }
}
