package com.gnavin.unzip.latency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//https://www.journaldev.com/960/java-unzip-file-example

public class Main {

  public static void main(String[] args) {

    String zipFilePath = "Question.zip";

    String destDir = "output";

    unzip(zipFilePath, destDir);

  }

  private static void unzip(String zipFilePath, String destDir) {
    File dir = new File(destDir);
    // create output directory if it doesn't exist
    if (!dir.exists()) {
      dir.mkdirs();
    }
    FileInputStream fis;
    //buffer for read and write data to file
    byte[] buffer = new byte[1024];
    try {
      fis = new FileInputStream(zipFilePath);
      ZipInputStream zis = new ZipInputStream(fis);
      ZipEntry ze = zis.getNextEntry();
      while (ze != null) {
        String fileName = ze.getName();

        System.out.println("fileName = " + fileName);

        File newFile = new File(destDir + File.separator + fileName);
        System.out.println("Unzipping to " + newFile.getAbsolutePath());

        System.out.println("newFile = " + newFile);
        if (!newFile.exists()) {
          newFile.mkdirs();
        }

        //create directories for sub directories in zip
        final String parent = newFile.getParent();
        System.out.println("parent = " + parent);
        final File file = new File(parent);

        final boolean mkdirs = file.mkdirs();
        System.out.println("mkdirs = " + mkdirs);

        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
        fos.close();
        //close this ZipEntry
        zis.closeEntry();
        ze = zis.getNextEntry();
      }
      //close last ZipEntry
      zis.closeEntry();
      zis.close();
      fis.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
