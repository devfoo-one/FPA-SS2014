package de.bht.fpa.mail.s798158.fsnavigation;

import java.io.File;

public class MyDirectory extends MyFileSystemObject {

  public MyDirectory(File file) {
    super(file);
  }

  @Override
  public boolean hasChildren() {
    return file.listFiles() != null && file.listFiles().length > 0;
  }

  @Override
  public MyFileSystemObject[] getChildren() {
    int i = 0;
    final MyFileSystemObject[] result = new MyFileSystemObject[file.listFiles().length];
    for (final java.io.File element : file.listFiles()) {
      if (element.isDirectory()) {
        result[i] = new MyDirectory(element);
      }
      if (element.isFile()) {
        result[i] = new MyFile(element);
      }
      i++;
    }
    return result;
  }
}
