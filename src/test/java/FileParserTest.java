package javatests;

import java.util.*;

import data.FileParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.json.JSONObject;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import data.Account;
import data.Vendor;

@RunWith(JUnit4.class)
public class FileParserTest {
  private static final String TEST_PATH_BASE = "src/test/resources/";

  FileParser parser;

  @Before
  public void setUp() {
    parser = new FileParser(TEST_PATH_BASE);
  }

  @Test
  public void testGetVendorIDs() {

  }
}