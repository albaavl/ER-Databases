package db.xml;

import java.io.*;
import java.util.List;
import db.jdbc.*;

import javax.persistence.*;
import javax.xml.transform.*;

import org.eclipse.persistence.exceptions.JAXBException;
import org.eclipse.persistence.jaxb.JAXBContext;

import db.pojos.*;

public class XMLManager {
	private static final String PERSISTENCE_PROVIDER = "project-provider";
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	public XMLManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void Xml2JavaShift() throws JAXBException{
		// Create the JAXBContext
				JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
				// Get the unmarshaller
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

				// Use the Unmarshaller to unmarshall the XML document from a file
				File file = new File("./xmls/External-Shift.xml");
				Shift shift = (Shift) unmarshaller.unmarshal(file);
				
				// Print the report
				System.out.println("Shift:");
				System.out.println("Date: " + shift.getShift());
				System.out.println("Shift: " + shift.getShift());
				System.out.println("Room: " + shift.getRoom());
				System.out.println("Worker Id: " + shift.getWorkerId());
				
				// Store the shift in the database
				// Create entity manager
				factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
				em = factory.createEntityManager();
				em.getTransaction().begin();
				em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
				em.getTransaction().commit();

				// Create a transaction
				EntityTransaction tx1 = em.getTransaction();

				// Start transaction
				tx1.begin();

				// Persist
				em.persist(shift);
	            
				// End transaction
				tx1.commit();
	}
	
	
	public void Java2XmlShift(Shift shift) throws JAXBException {
		
		// Get the entity manager
		em = Persistence.createEntityManagerFactory("project-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
				
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Choose the report to turn into an XML
		// Choose his new department
		printShifts();
		System.out.print("Choose a worker's shift to turn into an XML file:");
		int workerId = Integer.parseInt(reader.readLine());
		Query q2 = em.createNativeQuery("SELECT * FROM shifts WHERE workerId = ?", Shift.class);
		q2.setParameter(1, workerIid);
		Shift shift = (Shift) q2.getSingleResult();
				
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/Sample-Shift.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(shift, file);
		// Printout
		marshaller.marshal(shift, System.out);
	} 
	
	
	public void Xml2HtmlShift() {
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File("./xmls/Shift-Style.xslt")));
			transformer.transform(new StreamSource(new File("./xmls/External-Shift.xml")),new StreamResult(new File("./xmls/ERDatabases.html")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
