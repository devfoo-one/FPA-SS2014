package de.bht.fpa.mail.s798158.fsnavigation;

public abstract class MyFileSystemObject {
  protected final java.io.File file;

  public MyFileSystemObject(final java.io.File file) {
    this.file = file;
  }

  public boolean hasChildren() {
    return false;
  }

  public MyFileSystemObject[] getChildren() {
    return new MyFileSystemObject[0];
  }

  public String getName() {
    return file.getName();
  }

  // public boolean isDirectory() {
  // return file.isDirectory();
  // }

}
