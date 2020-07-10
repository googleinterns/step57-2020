// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import data.JsonConverter;
import data.Vendor;

/** Test the functionality of the JsonConverter class. */
@RunWith(JUnit4.class)
public final class JsonConverterTest {

  /** Test that createFile() successfully creates a new file. */
  @Test
  public void testCreateFileMethodCreatesNewFile() {
    String vendorID = "vend_1";
    String legVendID = "legVend_27";
    int nextGenVendorID = 17;
    Vendor vendor = new Vendor(vendorID, legVendID, nextGenVendorID);

    JsonConverter converter = new JsonConverter();
    File file = converter.createFile(vendor.getVendorID());

    Scanner input;
    try {
      input = new Scanner(file);
    } catch(FileNotFoundException e) {
      input = null;
    }

    assertFalse("createFile() didn't correctly create a new file.",
      input == null);
  }

  /** Test that createFile() writes the expected content to a file. */
  @Test
  public void testCreateFileMethodWritesExpectedContent() {
    String vendorID = "vend_1";
    String legVendID = "legVend_27";
    int nextGenVendorID = 17;
    Vendor vendor = new Vendor(vendorID, legVendID, nextGenVendorID);

    JsonConverter converter = new JsonConverter();
    File file = converter.createFile(vendor.getVendorID());

    String expectedFileContent = "{\"Vehicle\":{\"Car\":\"Blue Tacoma\"}}";
    String fileContent = "";

    Scanner input;
    try {
      input = new Scanner(file);
    } catch(FileNotFoundException e) {
      input = null;
    }

    while(input.hasNextLine()) {
      fileContent += input.nextLine();
    }

    assertTrue("createFile() didn't add the right content to the file.",
      fileContent.equals(expectedFileContent));
  }

  /** Test that updateFile() returns true for prototype. */
  @Test
  public void testUpdateFileMethod() throws IOException {
    Vendor vendor = new Vendor();
    JsonConverter converter = new JsonConverter();

    boolean expectedResponse = true;
    boolean actualResponse = converter.updateFile(vendor);

    assertTrue("updateFile() didn't return true when it should have.",
      expectedResponse == actualResponse);
  }

  /**
   * Test that getConfig() returns the expected file content.
   * The vendorID must match an existing filepath in the filesystem.
   */
  @Test
  public void testGetConfigMethod() {
    JsonConverter converter = new JsonConverter();
    String vendorID = "vend_1";
    String expectedResponse = "{\"Vehicle\":{\"Car\":\"Blue Tacoma\"}}";

    String actualResponse = converter.getConfig(vendorID, true);

    assertTrue("getConfig() didn't return the expected file content.",
      expectedResponse.equals(actualResponse));
  }

  /**
   * Test that getConfig() returns null when faulty vendorID is passed in.
   * The vendorID must match an existing filepath in the filesystem.
   */
  @Test
  public void testGetConfigMethodWithFakeVendorID() {
    JsonConverter converter = new JsonConverter();
    String vendorID = "fakeVendorID";
    String expectedResponse = null;

    String actualResponse = converter.getConfig(vendorID, true);

    assertTrue("getConfig() didn't return null when it should have.",
      expectedResponse == actualResponse);
  }
}
