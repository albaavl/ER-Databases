package db.xml;

import java.io.*;

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

	public static void xml2JavaShift() throws JAXBException {
		//Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshall the XML document from a file
		File file = new File("./xmls/External-Shift.xml");
		Shift shift = (Shift) unmarshaller.unmarshal(file);
		
		// Print the shift
		System.out.println("Shift:");
		System.out.println("Date: " + shift.getDate());
		System.out.println("Shift: " + shift.getTurn());
		System.out.println("Room: " + shift.getRoom());
		
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
	
	private static void printShifts() {
		Query q1 = em.createNativeQuery("SELECT * FROM shifts", Shift.class);
		List<Shift> sh = (List<Shift>) q1.getResultList();
		// Print the shifts
		for (Shift s : sh) {
			System.out.println(s);
		}
	}
	public static void java2XmlShift() throws JAXBException {
		
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
		printShifts();
		System.out.print("Choose a worker's shift to turn into an XML file");
		System.out.println("Introduce the worker's ID: ");
		int workerId = sc.nextInt(); 
		Query q2 = em.createNativeQuery("SELECT * FROM shifts WHERE doctor_id = ?", Shift.class);
		q2.setParameter(1, workerId);
		Shift s = (Shift) q2.getSingleResult();
				
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/External-Shift.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(s, file);
		// Printout
		marshaller.marshal(s, System.out);
	} 
	
	public static void xml2JavaWorker() throws JAXBException {
		//Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Worker.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshall the XML document from a file
		File file = new File("./xmls/External-Worker.xml");
		Worker w = (Worker) unmarshaller.unmarshal(file);
		
		// Print the worker
		System.out.println("Worker:");
		System.out.println("Name: " + w.getWorkerName());
		System.out.println("Surname: " + w.getWorkerSurname());
		System.out.println("Specialty: " + w.getSpecialtyId());
		System.out.println("Role: " + w.getTypeWorker());
		
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
		em.persist(w);
        
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
		JAXBContext jaxbContext = JAXBContext.newInstance(Shift.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Choose the worker to turn into an XML
		// Choose his new department
		printWorkers();
		System.out.print("Choose a worker (worker id) to turn into an XML file:");
		int workerId = sc.nextInt();
		Query q2 = em.createNativeQuery("SELECT * FROM workers WHERE id = ?", Worker.class);
		q2.setParameter(1, workerId);
		Shift s = (Shift) q2.getSingleResult();
				
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/External-Worker.xml");
		// Use the Marshaller to marshal the Java object to a file
		marshaller.marshal(s, file);
		// Printout
		marshaller.marshal(s, System.out);
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