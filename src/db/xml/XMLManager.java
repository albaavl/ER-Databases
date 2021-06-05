package db.xml;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;
import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.sqlite.JDBC;

import db.pojos.*;
import db.jdbc.*;

public class XMLManager {
	
	private static final String PERSISTENCE_PROVIDER = "ER-provider";
	private static EntityManagerFactory factory;
	private static EntityManager em;
	static Scanner sc = new Scanner(System.in);

	public static void xml2JavaShift() throws Exception {
		//Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshall the XML document from a file
		File file = new File("./xmls/External-Shift.xml");
		List <Shift> shift =  (List<Shift>) unmarshaller.unmarshal(file);

		if(shift.isEmpty()) {
			throw new Exception("XML file is empty. Check that there are shifts linked to this worker and try again.");
		} else {
			System.out.println(shift.toString());
			for (Shift s : shift) {
				s = new Shift ( s.getDate(), s.getRoom(), s.getTurn(), s.getShiftId());
			}		
		}
		
		
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
		for (Shift s : shift) {
			em.persist(s);
		}
        
        
		// End transaction
		tx1.commit();
	}
	
	public static void java2XmlShift(Worker medStaff) throws JAXBException {
		
		// Get the entity manager
		em = Persistence.createEntityManagerFactory("ER-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
				
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Choose the worker's shift to turn into an XML
		Query q2 = em.createNativeQuery("SELECT * FROM shifts WHERE doctor_id = ?", Shift.class);
		q2.setParameter(1, medStaff.getWorkerId());
		List <Shift> s = q2.getResultList();
				
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/External-Shift.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(s, file);
		// Printout
		marshaller.marshal(s, System.out);
	} 
	
	public static void xml2JavaWorker() throws Exception {
		//Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Workers.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshall the XML document from a file
		File file = new File("./xmls/External-Worker.xml");
		Workers workers =   (Workers) unmarshaller.unmarshal(file);
		
		if(workers.getWorkers().isEmpty()) {
			throw new Exception("XML file is empty. Check that there are workers linked to the database and try again.");
		} else {
			System.out.println(workers.getWorkers().toString());
			for (Worker w : workers.getWorkers()) {
				w = new Worker( w.getWorkerName(),  w.getWorkerSurname(),  w.getSpecialtyId(), w.getTypeWorker());
			}		
		}
		
		// Store the worker in the database
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
		for (Worker w : workers.getWorkers()) {
			em.persist(w);
		}
        
		// End transaction
		tx1.commit();
	}
	private static void printWorkers() {
		Query q1 = em.createNativeQuery("SELECT * FROM workers", Worker.class);
		List<Worker> w = (List<Worker>) q1.getResultList();
		// Print the workers
		for (Worker worker : w) {
			System.out.println(worker);
		}
	}
	public static void java2XmlWorker() throws JAXBException {
		
		// Get the entity manager
		em = Persistence.createEntityManagerFactory("ER-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
				
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Workers.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Choose the worker to turn into an XML
		// Choose his new department
		printWorkers();
		Query q2 = em.createNativeQuery("SELECT * FROM workers", Worker.class);
		List<Worker> lw = q2.getResultList();
		Workers workers = new Workers();
		workers.setWorkers(lw);
		System.out.println(lw);		
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/External-Worker.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(workers, file);
		// Printout
		marshaller.marshal(workers, System.out);
	} 
	
	/**
	 * Simple transformation method.
	 * @param sourcePath - Absolute path to source xml file.
	 * @param xsltPath - Absolute path to xslt file.
	 * @param resultDir - Directory where you want to put resulting files.
	 */
	public static void simpleTransform(String sourcePath, String xsltPath,String resultDir) {
		TransformerFactory tER = TransformerFactory.newInstance();
		try {
			Transformer transformer = tER.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}