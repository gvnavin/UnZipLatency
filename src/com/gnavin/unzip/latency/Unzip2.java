package com.gnavin.unzip.latency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip2 {

  public static void main(String[] args) throws IOException {

//    for (int i = 0; i < 10; i++) {
    unzip();
//    Files.delete(Paths.get("output/Question"));
    final File file = new File("output/Question");
    System.out.println("file = " + file);
    final boolean delete = file.delete();
    System.out.println("delete = " + delete);
//    }

  }

  private static void unzip() throws IOException {
    long st = System.currentTimeMillis();

    String fileZip = "Question.zip";
    byte[] buffer = new byte[1024];

    ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
    ZipEntry zipEntry = zis.getNextEntry();

    String fileName = zipEntry.getName();
    //System.out.println("fileName = " + fileName);
    File newFile = new File("output/" + fileName);

    //System.out.println("newFile = " + newFile);
    final boolean exists = newFile.exists();
    //System.out.println("exists = " + exists);
    if (!exists) {
      final boolean mkdirs = newFile.mkdirs();
      //System.out.println("mkdirs = " + mkdirs);
    }

    zipEntry = zis.getNextEntry();

    while (zipEntry != null) {
      String fileName2 = zipEntry.getName();
      //System.out.println("fileName2 = " + fileName2);
      File newFile2 = new File("output/" + fileName2);

      //System.out.println("newFile2 = " + newFile2);
      final boolean exists2 = newFile2.exists();
      //System.out.println("exists2 = " + exists2);
//      if (!exists2) {
//        final boolean newFilecreateNewFile = newFile2.createNewFile();
//        //System.out.println("newFilecreateNewFile = " + newFilecreateNewFile);
//      }

      FileOutputStream fos = new FileOutputStream(newFile2);
      int len;
      while ((len = zis.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }
      fos.close();
      zipEntry = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();

    long et = System.currentTimeMillis();

    System.out.println("latency = " + (et - st));
  }

}
