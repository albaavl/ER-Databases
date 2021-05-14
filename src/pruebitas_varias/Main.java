package pruebitas_varias;
import java.io.File;
import java.security.SecureRandom;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


import db.pojos.*;
import javax.xml.bind.*;


public class Main {


	public static void main(String[] args) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			File file = new File("./xmls/External-Shift.xml");
			Shift s = (Shift) unmarshaller.unmarshal(file);
			
			System.out.println("Shift:");
			System.out.println("Turn: " + s.getTurn());
			System.out.println("Room: " + s.getRoom());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}
	/*	public static void XmlToFamilyHistoryMenu() {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
			    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

				File file = new File("./xmls/External-Cancer.xml");
				Shift famhist = (FamilyHistory) unmarshaller.unmarshal(file);
				
				System.out.println("Family History:");
				System.out.println("Type: " + famhist.getType_cancerFam());
				System.out.println("Member: " + famhist.getMember());
				
			    
			}catch(Exception e) {
				e.printStackTrace();
			}
			public static void familyHistoryToXmlMenu() {
				try {
					printPatientsMenu();
					System.out.print("Choose a patient to turn its family history into an XML file:");
					int patient_id= Integer.parseInt(reader.readLine());
					dbmaster.familyHistoryToXml(patient_id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				public static void cancerToXmlMenu() {
					try {
						printCancerMenu();
						System.out.print("Choose a cancer to turn into an XML file:");
						int can_id = Integer.parseInt(reader.readLine());
						dbmaster.cancerToXml(can_id);
						
						}catch(Exception e) {
						e.printStackTrace();
					}
				}
				public static void XmlToCancerMenu() {
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(Cancer.class);
					    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

						File file = new File("./xmls/External-Cancer.xml");
						Cancer can = (Cancer) unmarshaller.unmarshal(file);
						
						System.out.println("Cancer:");
						System.out.println("Type: " + can.getCancer_type());
						List<Patient> p = can.getPatients();
						List<Treatment> t = can.getTreatments();
					    System.out.println(p);
					    System.out.println(t);
					    
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				
			
	}
}*/


