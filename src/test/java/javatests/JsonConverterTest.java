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

package javatests;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import data.JsonConverter;
import data.Vendor;

/** Test the functionality of the JsonConverter class. */
@RunWith(JUnit4.class)
public final class JsonConverterTest {
  @Test
  public void testCreateFileMethod() {
    // Test that the method can create a file with content.
    JsonConverter converter = new JsonConverter();
    File file = converter.createFile();

    String expectedFileContent = "{\"Vehicle\":{\"Car\":\"Blue Tacoma\"}}";
    String fileContent = "";

    Scanner input;
    try {
      input = new Scanner(file);
    } catch(FileNotFoundException e) {
      input = null;
    }

    while(input.hasNextLine())
    {
      fileContent += input.nextLine();
    }

    // Test that the file isn't empty and has the right content.
    assertTrue("createFile() didn't add any content to the file when is should've",
      !fileContent.equals(""));
    assertTrue("createFile() didn't add the right content to the file.", 
      fileContent.equals(expectedFileContent));
  }

  @Test
  public void testUpdateFileMethod() throws IOException {
    // For the protoype, updateFile() calls createFile() and returns true.
    JsonConverter converter = new JsonConverter();
    Vendor vendor = new Vendor();

    boolean expectedResponse = true;
    boolean actualResponse = converter.updateFile(vendor);

    // Test that the actual and expected responses are the same.
    assertTrue("updateFile() didn't return true when it should have.", 
      expectedResponse == actualResponse);
  }
}
