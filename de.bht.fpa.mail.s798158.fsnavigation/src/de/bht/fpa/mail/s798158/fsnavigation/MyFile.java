package de.bht.fpa.mail.s798158.fsnavigation;

public class MyFile {
  private final java.io.File file;

  public MyFile(final java.io.File file) {
    this.file = file;
  }

  public boolean hasChildren() {
    if (file.isDirectory()) {
      return file.listFiles().length > 0;
    } else {
      return false;
    }
  }

  public String getName() {
    return file.getName();
  }

  public Object[] getChildren() {
    int i = 0;
    final Object[] result = new Object[file.listFiles().length];
    for (final java.io.File element : file.listFiles()) {
      result[i] = new MyFile(element);
      i++;
    }
    return result;
  }

  public boolean isDirectory() {
    return file.isDirectory();
  }

}
