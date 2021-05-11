package db.menu;
 

import java.rmi.NotBoundException;
import java.security.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import db.pojos.*;
import pojos.users.*;
import db.interfaces.UserManager;
import db.jdbc.*;
import db.jpa.JPAUserManager;

public class Main {

	static Connection c ;
	static Scanner sc = new Scanner(System.in);
	static SQL jdbc = new SQL();
	private static JPAUserManager userman = new JPAUserManager();
	public static int option = 0;

	public static void main(String[] args) throws Exception, SQLException {
		try{
			c= jdbc.connect();
			userman.connect();
			try{
				jdbc.create(c);
			}catch(SQLException ex) {
				if(!ex.getMessage().contains("already exists")) {
					ex.printStackTrace();
				}
			}
			do {
				System.out.println("Welcome to the Quiron's ER");
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("0. Exit");
				int choice = sc.nextInt();
				switch(choice) {
				case 1:
					register();
					break;
				case 2:
					login();
					break;
				case 0: 
					jdbc.disconnect(c); //�l en su connect no le tiene que pasar ning�n par�metro REVISAR
					userman.disconnect();
					System.exit(0);
					break;
				default:
					break;
				}
			}while(true);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private static void register() throws Exception {
		//ask the user for an email
		System.out.println("Please, write your username: ");
		String username = sc.next();
		//ask the user for a password
		System.out.println("Please, write your password: ");
		String password= sc.next();
		//List the roles
		System.out.println(userman.getRoles());
		//ask the user for a role
		System.out.println("Type the chosen role ID: ");
		int id = sc.nextInt();
		Role role = userman.getRole(id);
		//generate the hash
		MessageDigest md = MessageDigest.getInstance("MDS");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(username, hash, role);
		userman.newUser(user);
	}
	
	private static void login() throws Exception{
		System.out.println("Please enter your username and password:");
		System.out.println("Username:");
		String username = sc.next();
		System.out.println("Password:");
		String password = sc.next();
		User user = userman.checkPassword(username, password);
		if(user == null) {
			System.out.println("Wrong username or password");
			return;
		} else if(user.getRole().getRole().equalsIgnoreCase("patient")){
			patientMenu();
		} else if(user.getRole().getRole().equalsIgnoreCase("medicalStaff")){
			medStaffMenu();
		}else if(user.getRole().getRole().equalsIgnoreCase("adStaff")){
			adStaffMenu();
		}
	}

	
	public static void patientMenu() throws Exception{
		
		Patient patient = new Patient();//deberia ser el patient que ha hecho el log in
		System.out.println("Consult my treatment");
		System.out.println("Would you like to order your treatments by[0-4]:"
				+ "\n0.Exit \n1. Date \n2. Medication \n3. Duration \n4. I want to search for a specific treatment by the name of the medication");
		int option = sc.nextInt();
		String order;
		List<Treatment> treatments = new ArrayList<>();
		
		do{
			switch (option) {
				case 0:
					System.out.println("Thank you for using our system");
					jdbc.disconnect(c);
					userman.disconnect();
					System.exit(0);
				case 1:
					order = "date";
					System.out.println("Here you can see all your treatments ordered by date");
					treatments.addAll(jdbc.searchPatientsTreatment(c,patient, order));
					break;
				case 2:
					order = "med";
					System.out.println("Here you can see all your treatments ordered by medication");
					treatments.addAll(jdbc.searchPatientsTreatment(c,patient, order));
					break;
				case 3:
					order = "duration";
					System.out.println("Here you can see all your treatments ordered by duration");
					treatments.addAll(jdbc.searchPatientsTreatment(c,patient, order));
					break;
				case 4:
					System.out.println("Please, enter the name of the medication:");
					String med = sc.next();
					treatments.addAll(jdbc.searchTreatmentByMed(c,patient, med));
					break;
			}		
			for (Treatment treatment : treatments) {
				System.out.println(treatment.toString());
			}
		}while(true);
	}
	
	public static void medStaffMenu() throws Exception{
		
		Worker medStaff = new Worker();
		System.out.println("Choose an option[0-2]:");
		System.out.println(" 1.Access to a patient's profile \n 2.Consult my shifts \n 0. Exit");
		option = sc.nextInt();
		do{
			switch(option) {
				case 0:
					System.out.println("Thank you for using our system");
					jdbc.disconnect(c);
					userman.disconnect();
					System.exit(0);
				case 1: 
					System.out.println("Access to a patient's profile");
					accessToAPatientsProfile();
					break;
				case 2:
					System.out.println("Consult my shifts");
					consultShifts(medStaff);
					break;
			}
		}while(true);
	}
	
	public static void adStaffMenu() throws Exception{
		
		Worker adstaff = new Worker();
		System.out.println("Choose an option[0-5]:");
		System.out.println(" 1. Register new patient \n 2. Register new worker\n 3. Acces to a patient's profile\n 4. Request new medical test\n 5. Edit shifts \n 0. Exit");
		option = sc.nextInt();
		
		do {
			switch (option) {
				case 0:
					System.out.println("Thank you for using our system");
					jdbc.disconnect(c);
					userman.disconnect();
					System.exit(0);
				case 1: 
					System.out.println("Register new patient");
					createPatient();
					break;
				case 2: 
					System.out.println("Register new worker");
					createWorker();
					break;
				case 3: 
					System.out.println("Acces to a patient's profile");
					adAccessToPatientsProfile();
					break;	
				case 4: 
					System.out.println("Request new medical test");
//					requestMedTest();
					break;	
				case 5: 
					System.out.println("Edit shifts");
					editShift();
					break;	
			}
		}while(true);
	}
	
	private static void accessToAPatientsProfile() throws Exception {
		Patient patient = selectPatient();
		while (option!=0) {
			System.out.println("Choose an option[0-3]:");
			System.out.println("\n1. Consult medical test \n2. Add treatment and diagnosis \n3. Edit treatment and diagnosis \\n 0. Exit");
			option = sc.nextInt();
			switch(option) {
			case 0:
				System.out.println("Thank you for using our system");
				jdbc.disconnect(c);
				userman.disconnect();
			System.exit(0);
			case 1:
				System.out.println("Consult medical tests");
				consultMedicalTest();
				break;
			case 2:
				System.out.println("Add treatment and diagnosis");
				createDiagnosisAndTreatment(patient);
				break;
			case 3:
				System.out.println("Edit treatment and diagnosis");
				editDiagnosisAndTreatment(patient);
				break;
			}
		}
	}
	
	private static void consultMedicalTest() throws Exception {
		Patient patient = selectPatient();
		List<MedicalTest> tests = new ArrayList<MedicalTest>();
		tests.addAll(jdbc.searchMedicalTestByMedCardNumber(c, patient.getMedicalCardId())); //connection c, medicalCard
		if (tests.isEmpty()) {
			System.out.println("No tests available for patient " + patient.getPatientName()+" "+ patient.getPatientSurname());
		} else {
			System.out.println("These are all the medical tests of "+patient.getPatientName()+" "+patient.getPatientSurname()+" ordered by date:");
			for(MedicalTest med : tests) {
				System.out.println(med.toString());
			}
		}
	}
	
	private static void createDiagnosisAndTreatment(Patient patient) throws Exception {
		System.out.println("Enter the diagnosis:");
		String diagnosis = sc.next();
		System.out.println("Enter the medication:");
		String medication = sc.next();
		System.out.println("Enter the start date in this format yyyy-mm-dd:");
		Date startDate = Date.valueOf(sc.next());
		System.out.println("Enter the duration(number of days):");
		Integer duration = Integer.parseInt(sc.next());
		System.out.println("Enter the recommendations");
		String recommendation = sc.next();
		Treatment t = new Treatment(diagnosis, recommendation, startDate, medication, duration);
		jdbc.addTreatment(c, t, patient.getMedicalCardId()); 
		System.out.println("Treatment added");
	}

	private static void editDiagnosisAndTreatment(Patient  patient) throws Exception {
		List<Treatment> treatments = new ArrayList<Treatment>();
		treatments.addAll(jdbc.searchTreatmentsByMedCard(c, patient.getMedicalCardId()));
		if(treatments.isEmpty()) {
			System.out.println("No treatments available for patient "+ patient.getPatientName()+" "+ patient.getPatientSurname());
		} else {
			System.out.println("These are all the treatments associated to patient "+patient.getPatientName()+" "+patient.getPatientSurname()+" ordered by date:");
			System.out.println(treatments.toString());
			Treatment t = null;
			while (t == null) {
			System.out.println("Enter the id of the treatment that you want to edit:");
			Integer id = sc.nextInt();
			t = new Treatment(jdbc.selectTreatment(c, id));//Connection c, Integer id
			}
			System.out.println("This is the selected treatment:");
			System.out.println(t.toString());
			System.out.println("Enter the new diagnosis, if you don't want to edit it enter a 0:");
			String diagnosis = sc.next();
			if(diagnosis.equals("0")) {
				diagnosis = null;
			}
			System.out.println("Enter the new medication, if you don't want to edit it enter a 0:");
			String medication = sc.next();
			if(medication.equals("0")) {
				medication = null;
			}
			System.out.println("Enter the new duration, if you don't want to edit it enter a 0:");
			Integer duration = sc.nextInt();
			if(duration == 0) {
				duration = null;
			}
			System.out.println("Enter the new recommendations, if you don't want to edit them enter a 0:");
			String recommendation = sc.next();
			if(recommendation.equals("0")) {
				recommendation = null;
			}
			jdbc.editTreatment(t.getTreatmentId(),diagnosis,medication,duration,recommendation); 
			System.out.println("This is the edited treatment:");
			Treatment nuevo = new Treatment(); //habria que buscar el nuevo treatment por lo que habria que crear
			//un buscador de treatment con las caracteristicas exactas que ha introducido el usuario arriba
			System.out.println(nuevo.toString());
		}		
	}
	private static void editShift() throws Exception {
		Worker worker = selectWorker();
		System.out.println("These are the shifts associated to worker "+worker.getWorkerName()+ " " +worker.getWorkerSurname()+ " ordered by date: ");
		consultShifts(worker);
		Shift shift = new Shift();
		while (shift == null) {
			System.out.println("Enter the ID of the shift that you want to edit:");
			Integer id = sc.nextInt();
			shift = jdbc.searchShiftByID (c, worker.getWorkerId(), id);//Connection c, Integer workerId, Integer id FUNCION NO CREADA
			}
		System.out.println("This is the selected shift:");
		System.out.println(shift.toString());
		System.out.println("Enter the new shift[1-3], if you don't want to edit it enter a 0: \n1.Morning \n2.Afternoon \n3.Night");
		Integer s = sc.nextInt();
		String time =null;
		switch(s) {
		case 0:
			time =null;
			break;
		case 1:
			time = "Morning";
			break;
		case 2:
			time = "Afternoon";
			break;
		case 3:
			time = "Night";
			break;
		}
		System.out.println("Enter the new room, if you don't want to edit it enter a 0:");
		Integer room = sc.nextInt();
		if(room == 0) {
			room=null;
		}
		jdbc.editShift(c, worker.getWorkerId(), time,room, shift.getDate());//Connection c, int workerId, String shift, int room, Date date
		System.out.println("This is the edited shift:");
		Shift newShift = new Shift(); //FUNCION NO CREADA habria que buscar el shift de nuevo con las caracteristicas introducidas por el usuario o mejor aun con el id que tenemos ya del primer select
		System.out.println(newShift.toString());
	}
	
	private static void consultShifts(Worker w) {
		System.out.println("Do you want to see your shifts for an specific date? [YES/NO]");
		String answer = sc.next();
		while(!answer.equalsIgnoreCase("0")) {
		//para que busque el turno del trabajador para x dia
//		if(answer.equalsIgnoreCase("YES")) {
//			System.out.println("Insert date: [dd/mm/yyyy]");
//			String date = sc.next();
//			Date shiftDate = Date.valueOf(date);
//			List<Shift> s = new ArrayList<>();
//			s.addAll( jdbc.searchShiftByDate (c, w.getWorkerId(), shiftDate)); //Connection c, Integer workerId
//			if ( s.isEmpty()) {
//				System.out.println("No shifts available for worker " + w.getWorkerName()+" "+ w.getWorkerSurname() + " on " + shiftDate);
//			} else {
//				System.out.println("These are the shifts of "+w.getWorkerName()+" "+ w.getWorkerSurname()+" on " + shiftDate);
//				System.out.println(s.toString());
//			}
//		//para que busque todos los turnos del trabajador
//		} else if(answer.equalsIgnoreCase("NO")&&answer.equalsIgnoreCase("no")){
//		Shift s = jdbc.searchShiftByWorkerId (c, w.getWorkerId()); //Connection c, Integer workerId
//		if ( s == null) {
//			System.out.println("No shifts available for worker " + w.getWorkerName()+" "+ w.getWorkerSurname());
//		} else {
//			System.out.println("These are the shifts of "+w.getWorkerName()+" "+ w.getWorkerSurname()+" ordered by date:");
//			System.out.println(s.toString());
//		}
//		 }else {
//			System.out.println("Not valid answer. Insert [YES/NO]");
//		}
			}
		
	}
	
	public static void createPatient () throws NotBoundException, Exception {		
		Patient p = new Patient();
		System.out.println("Please, input the patient info:");
		System.out.print("Name: ");
		String name = sc.next();
		p.setPatientName(name);
		System.out.print("Surname: ");
		String surname = sc.next();
		p.setPatientSurname(surname);
		System.out.print("Medical card number: ");
		Integer medCardNumber = sc.nextInt();
		p.setMedicalCardId(medCardNumber);
		System.out.print("Gender: ");
		String gender = sc.next();
		p.setGender(gender);
		System.out.print("Blood type: ");
		String bloodType = sc.next();
		p.setBloodType(bloodType);
		System.out.print("Allergies: ");
		String allergie = sc.next();
		p.setAllergieType(allergie);
		System.out.print("Date of birth: yyyy-[m]m-[d]d");
		String birthdate = sc.next();
		Date bdate = Date.valueOf(birthdate);
		p.setbDate(bdate);
		System.out.print("Check in date: yyyy-[m]m-[d]d"); //averiguar como meter horas y minutos
		String checkindate = sc.next();
		Date cdate = Date.valueOf(checkindate);
		p.setCheckInDate(cdate);
		System.out.print("Address: ");				
		String address = sc.next();
		p.setPatientAddress(address);
		System.out.print("Hospitalization [YES/NO]: ");
		Boolean hospitalized = sc.nextBoolean();
		p.setHospitalized(hospitalized);
		jdbc.addPatient(c, p); //Connection c, Patient p
	}
	
	public static void createWorker() throws Exception {		
		Worker w = new Worker();
		Shift s = new Shift();
		System.out.println("Please, input the worker info:");
		System.out.print("Name: ");
		String name = sc.next();
		w.setWorkerName(name);
		System.out.print("Surname: ");
		String surname = sc.next();
		w.setWorkerSurname(surname);
		System.out.print("Type of worker: ");
		String type = sc.next();
		w.setTypeWorker(type);
		System.out.print("Specialty: ");
		String specialty = sc.next();
		w.setSpecialtyId(specialty);
		System.out.print("Room: ");
		Integer roomER = sc.nextInt();
		w.setRoomEr(roomER);
		s.setRoom(roomER);
		System.out.print("Shift: ");
		String shift = sc.next();
		s.setShift(shift);
		s.setWorkerId(w.getWorkerId()); //en teor�a el workerId deber�a autoincrementarse, 
		System.out.println("Start date:  yyyy-[m]m-[d]d");
		String startDate = sc.next();
		Date date = Date.valueOf(startDate);
		s.setDate(date);
		jdbc.addWorker(c, w); //Connection c,Worker w
	}
//	public static void requestMedTest() throws NotBoundException{
//		Patient patient = new Patient(selectPatient());
//		Worker worker = new Worker (selectWorker()); 
//		MedicalTest medTest = new MedicalTest();
//		medTest.setPatient(patient.getMedicalCardId());
//		medTest.setDoctor(worker.getWorkerId());
//		System.out.print("Type of medical test: ");
//		String type = sc.next();
//		medTest.setTestType(type);
//		jdbc.addMedicalTest(c, medTest);//Connection c, MedicalTest medtest
//	}
//	
//	public static void showPatientsTreatments(Patient patient) throws Exception{
//		String treatment;
//		List<Treatment> treatmentsList = new ArrayList<Treatment>();
//		treatmentsList = jdbc.searchPatientsTreatment(null, patient);
//		if(treatmentList.isEmpty()){
//			System.out.println ("Ther is no treatment available for this patient");
//		} else{
//			System.out.println("Here you can see all your treatments ordered by date: ");
//			System.out.println(treatmentList.toString);
//		}
//	}
	
	private static void adAccessToPatientsProfile() throws Exception {
		Patient patient = selectPatient();
		System.out.println(patient.toString());
		}
	
	private static Patient selectPatient() throws Exception {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient patient = null;
		while(patientList.isEmpty()) {
		System.out.println("Enter the patient's surname:");
		String surname = sc.next();
		patientList.addAll(jdbc.searchPatient(c, surname)); //(connection c, integer id)
		}
		while(patient == null) {
		System.out.println(patientList.toString());
		System.out.println("Enter the medical card number of the chosen patient:");
		Integer medCard = Integer.parseInt(sc.next());
		patient = new Patient(jdbc.selectPatient (c, medCard)); //(connection c, integer id)
		}
		return patient;
	}
	private static Worker selectWorker() throws Exception {
		List<Worker> wList = new ArrayList<Worker>();
		Worker w = null;
		while(wList.isEmpty()) {
		System.out.println("Enter the doctor's surname:");
		String surname = sc.next();
		wList = jdbc.searchWorker(c, surname); //(connection c, integer id)
		}
		while(w == null) {
		System.out.println(wList.toString());
		System.out.println("Enter the id of the chosen worker:");
		Integer id = Integer.parseInt(sc.next());
		w = new Worker(jdbc.selectWorker (c, id)); //Connection c, Integer workerId
		}
		return w;
	}
}