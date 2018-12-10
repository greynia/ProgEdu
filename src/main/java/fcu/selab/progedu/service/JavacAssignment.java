package fcu.selab.progedu.service;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import fcu.selab.progedu.exception.LoadConfigFailureException;
import fcu.selab.progedu.utils.ZipHandler;

public class JavacAssignment extends AssignmentTypeMethod {

  public String getSampleZip() {
    String folderName = "JavacQuickStart.zip";
    return folderName;
  }

  public void searchFile(String entryNewName) {
    ZipHandler zipHandler = null;
    try {
      zipHandler = new ZipHandler();
      StringBuilder sb = new StringBuilder();
      String last = "";
      if (entryNewName.endsWith(".java")) {
        last = entryNewName.substring(entryNewName.length() - 5, entryNewName.length());
      }
      String fileName = null;
      for (int i = 0; i < entryNewName.length() - 3; i++) {
        if (entryNewName.substring(i, i + 3).equals("src")) {
          fileName = entryNewName.substring(i);
          System.out.println("Search java file fileName : " + fileName);
          if (last.equals(".java")) {
            sb.append("javac " + fileName + "\n");
            sb.append("echo \"BUILD SUCCESS\"");
            zipHandler.setStringBuilder(sb);
          }
        }
      }

    } catch (LoadConfigFailureException e) {
      e.printStackTrace();
    }
  }

  public void copyTestFile(File folder, String strFolder, String testFilePath) {
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        copyTestFile(fileEntry, strFolder, testFilePath);
      } else {
        if (fileEntry.getAbsolutePath().contains("src")) {
          String entry = fileEntry.getAbsolutePath();
          if (entry.contains("src/test")) {

            File dataFile = new File(strFolder + "/src/test");
            File targetFile = new File(testFilePath + "/src/test");
            try {
              FileUtils.copyDirectory(dataFile, targetFile);
              FileUtils.deleteDirectory(dataFile);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }

      }
    }
  }

  public String getJenkinsConfig() {
    return "config_javac.xml";
  }

  public void modifyXmlFile(String filePath, String updateDbUrl, String userName, String proName,
      String tomcatUrl, StringBuilder sb) {
    final String PROGEDU_DB_URL = "progeduDbUrl";
    final String PRO_NAME = "proName";
    try {
      String filepath = filePath;
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(filepath);

      Node ndUrl = doc.getElementsByTagName("command").item(0);
      ndUrl.setTextContent(sb.toString());

      Node progeduDbUrl = doc.getElementsByTagName(PROGEDU_DB_URL).item(0);
      progeduDbUrl.setTextContent(updateDbUrl);

      Node user = doc.getElementsByTagName("user").item(0);
      user.setTextContent(userName);

      Node ndProName = doc.getElementsByTagName(PRO_NAME).item(0);
      ndProName.setTextContent(proName);

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filepath));
      transformer.transform(source, result);
    } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
      e.printStackTrace();
    }

  }

  public void createJenkinsJob(String name) {

  }

}
