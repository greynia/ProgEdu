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

public class WebAssignment extends AssignmentTypeMethod {

  public String getSampleZip() {
    String folderName = "WebQuickStart.zip";
    return folderName;
  }

  public void createJenkinsJob() {
    
  }

  public void searchFile(String entryNewName) {

  }
  
  public void copyTestFile(File folder, String strFolder, String testFilePath) {
    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        copyTestFile(fileEntry, strFolder, testFilePath);
      } else {
        // web project
        if (fileEntry.getAbsolutePath().contains("features")) {
          String entry = fileEntry.getAbsolutePath();
          File dataFile = new File(strFolder + "/features");
          File targetFile = new File(testFilePath + "/features");
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
  
  public String getJenkinsConfig() {
    return "config_web.xml";
  }
  
  public void modifyXmlFile(String filePath, String updateDbUrl, String userName, String proName,
      String tomcatUrl, StringBuilder sb) {
    final String PROGEDU_DB_URL = "progeduDbUrl";
    final String JOB_NAME = "jobName";
    final String PRO_NAME = "proName";
    try {
      String filepath = filePath;
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(filepath);

      String strJobName = userName + "_" + proName;
      Node jobName = doc.getElementsByTagName(JOB_NAME).item(0);
      jobName.setTextContent(strJobName);

      Node ndUrl = doc.getElementsByTagName("command").item(0);
      ndUrl.setTextContent(sb.toString());

      Node testFileName = doc.getElementsByTagName("testFileName").item(0);
      testFileName.setTextContent(proName);

      Node progeduDbUrl = doc.getElementsByTagName(PROGEDU_DB_URL).item(0);
      progeduDbUrl.setTextContent(updateDbUrl);

      Node user = doc.getElementsByTagName("user").item(0);
      user.setTextContent(userName);

      Node ndProName = doc.getElementsByTagName(PRO_NAME).item(0);
      ndProName.setTextContent(proName);

      Node proDetailUrl = doc.getElementsByTagName("proDetailUrl").item(0);
      proDetailUrl.setTextContent(tomcatUrl);

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
