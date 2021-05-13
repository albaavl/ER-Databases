package db.xml;

import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import db.xml.utils.CustomErrorHandler;

public class DTDCheckerReport {
	File xmlFile = new File("./xmls/External-Shift.xml"); 
    try {
    	// Create a DocumentBuilderFactory
        DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
        // Set it up so it validates XML documents
        dBF.setValidating(true);
        // Create a DocumentBuilder and an ErrorHandler (to check validity)
        DocumentBuilder builder = dBF.newDocumentBuilder();
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        builder.setErrorHandler((ErrorHandler) customErrorHandler);
        // Parse the XML file and print out the result
        Document doc = builder.parse(xmlFile);
        if (customErrorHandler.isValid()) {
            System.out.println(xmlFile + " was valid!");
        }
    } catch (ParserConfigurationException ex1) {
        System.out.println(xmlFile + " error while parsing!");
    } catch (SAXException ex2) {
        System.out.println(xmlFile + " was not well-formed!");
    } catch (IOException ex3) {
        System.out.println(xmlFile + " was not accesible!");
    }
}