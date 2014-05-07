package de.bht.fpa.mail.s798158.fsnavigation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class MyDirectory extends MyFileSystemObject {

  public MyDirectory(File file) {
    super(file);
  }

  @Override
  public boolean hasChildren() {
    // FileFilter auf Directories
    FileFilter fileFilter = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory();
      }
    };

    return this.getFile().listFiles(fileFilter) != null && this.getFile().listFiles(fileFilter).length > 0;
  }

  @Override
  public ArrayList<MyFileSystemObject> getChildren() {
    final ArrayList<MyFileSystemObject> result = new ArrayList<MyFileSystemObject>();
    // FileFilter auf Directories
    FileFilter fileFilter = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory();
      }
    };

    for (final java.io.File element : this.getFile().listFiles(fileFilter)) {
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
