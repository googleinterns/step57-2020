package data;

import com.google.gson.Gson;

import java.io.File;
import java.util.*;

public class FileParser {
  private static final String DEFAULT_FILE_PATH = "../../src/main/resources";

  private String basePath;

  public FileParser() {
    this.basePath = DEFAULT_FILE_PATH;
  }

  public FileParser(String filepath) {
    this.basePath = filepath;
  }




}

