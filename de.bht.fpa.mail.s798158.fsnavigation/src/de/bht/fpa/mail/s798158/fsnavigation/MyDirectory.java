package de.bht.fpa.mail.s798158.fsnavigation;

import java.io.File;
import java.util.ArrayList;

public class MyDirectory extends MyFileSystemObject {

  public MyDirectory(File file) {
    super(file);
  }

  @Override
  public boolean hasChildren() {
    return file.listFiles() != null && file.listFiles().length > 0;
  }

  @Override
  public ArrayList<MyFileSystemObject> getChildren() {
    final ArrayList<MyFileSystemObject> result = new ArrayList<MyFileSystemObject>();
    for (final java.io.File element : file.listFiles()) {
      if (element.isDirectory()) {
        result.add(new MyDirectory(element));
      }
      if (element.isFile()) {
        result.add(new MyFile(element));
      }
    }
    return result;
  }
}
