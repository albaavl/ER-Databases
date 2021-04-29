package db.menu;
 
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.chrono.ThaiBuddhistDate;
import java.util.*;

import db.pojos.*;
import pojos.users.Role;
import pojos.users.User;
import db.interfaces.UserManager;
import db.jdbc.*;
import db.jpa.JPAUserManager;

public class Main {
	static String username;
	static String password;
	static int option = 1;
	static Connection c ;
	static Scanner sc = new Scanner(System.in);
	static SQL jdbc = new SQL();
	private static UserManager userman = new JPAUserManager();

	public static void main(String[] args) throws Exception, SQLException {
		jdbc.connect();
		userman.connect();
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
					jdbc.disconnect(c);
					userman.disconnect();
					System.exit(0);
					break;
				default:
					break;
				}
			}while(true);
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
		username = sc.next();
		System.out.println("Password:");
		password = sc.next();
		User user = userman.checkPassword(username, password);
		if(user == null) {
			System.out.println("Wrong username or passwoerd");
			return;
		} else if(user.getRole().getRole().equalsIgnoreCase("admin")){
			adminMenu();
		} else if(user.getRole().getRole().equalsIgnoreCase("user")){
			userMenu();
		}
	}
	public static void userMenu() throws Exception{
		do {
			System.out.println("Choose an option: ");
			System.out.println("1. Add user ");
			System.out.println("2. Apply: ");
			System.out.println("0. Exit ");
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				addUser();
				break;
			case 3:
				Apply();
				break;
			case 0: 
				jdbc.disconnect(c);
				userman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}
		}while(true);
	}
	public static void oldMenu() throws Exception{
		try {
			if(username.equalsIgnoreCase("ms")&&password.equalsIgnoreCase("ms")) {
				c = jdbc.connect();
				Worker medStaff = new Worker();
				while(option != 0) {
				System.out.println("Choose an option[0-3]:");
				System.out.println(" 1.Access to a patient's profile \n 2.Consult my shifts \n 0. Exit");
				option = sc.nextInt();
				switch(option) {
				case 0:
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
				}
			}if(username.equalsIgnoreCase("as")&&password.equalsIgnoreCase("as")) {
				Worker adstaff = new Worker();
				while (option!=0) {
				System.out.println("Choose an option[0-3]:");
				//register new patient, register new worker, consult patient's profile, add new medical test, edit shifts
				System.out.println(" 1. Register new patient \n 2. Register new worker\n 3. Acces to a patient's profile\n 4. Request new medical test\n 5. Edit shifts \n 0. Exit");
				option = sc.nextInt();
				switch (option) {
				case 0:
					jdbc.disconnect(c);
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
					requestMedTest();
					break;	
				case 5: 
					System.out.println("Edit shifts");
					editShift();
					break;	
				}
				
			}if(username.equalsIgnoreCase("p")&&password.equalsIgnoreCase("p")) {
				Patient patient = new Patient();//deberia ser el patient que ha hecho el log in
				System.out.println("Consult my treatment");
				System.out.println("Would you like to order your treatments by[0-4]:"
						+ "\n0.Exit \n1. Date \n2. Medication \n3. Duration \n4. I want to search for a specific treatment by the name of the medication");
				option = sc.nextInt();
				String order;
				List<Treatment> treatments = new ArrayList<>();
				while(option!=0) {
				switch (option) {
				case 0:
					System.out.println("Thank you for using our system");
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
			}
			}
			}
			}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void accessToAPatientsProfile() {
		Patient patient = selectPatient();
		while (option!=0) {
			System.out.println("Choose an option[0-3]:");
			System.out.println("\n1. Consult medical test \n2. Add treatment and diagnosis \n3. Edit treatment and diagnosis \\n 0. Exit");
			option = sc.nextInt();
			switch(option) {
			case 0:
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
	
	private static void consultMedicalTest() {
		Patient patient = selectPatient();
		List<MedicalTest> tests = new ArrayList<MedicalTest>();
		tests = SQL.searchMedicalTestByMedCardNumber(null, patient.getMedicalCardId()); //connection c, medicalCard
		if (tests == null) {
			System.out.println("No tests available for patient " + patient.getPatientName()+" "+ patient.getPatientSurname());
		} else {
			System.out.println("These are all the medical tests of "+patient.getPatientName()+" "+patient.getPatientSurname()+" ordered by date:");
			System.out.println(tests.toString());
		}
	}
	
	private static void createDiagnosisAndTreatment(Patient patient) {
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
		SQL.addTreatment(null, t, patient.getMedicalCardId()); 
		//en la función que habeis creado no le pasa la medical card, sino el id del paciente para la tabla doctor-paciente, ns si quereis que 
		//sea así o es un error, si es así, en la clase de patient no hay un patient id, lo que usamos es la medical card, que no es lo mismo, habría que crearlo
		System.out.println("Treatment added");
	}

	private static void editDiagnosisAndTreatment(Patient  patient) {
		List<Treatment> treatments = new ArrayList<Treatment>();
		treatments = SQL.searchTreatmentsByMedCardNumber(null, patient.getMedicalCardId()); //Connection c, Integer medCard
		if(treatments == null) {
			System.out.println("No treatments available for patient "+ patient.getPatientName()+" "+ patient.getPatientSurname());
		} else {
			System.out.println("These are all the treatments associated to patient "+patient.getPatientName()+" "+patient.getPatientSurname()+" ordered by date:");
			System.out.println(treatments.toString());
			Treatment t = null;
			while (t == null) {
			System.out.println("Enter the id of the treatment that you want to edit:");
			Integer id = sc.nextInt();
			t = new Treatment(SQL.searchTreatmentsByID(null, id));//Connection c, Integer id
			}
			System.out.println("This is the selected treatment:");
			System.out.println(t.toString());
			System.out.println("Enter the new diagnosis, if you don't want to edit it enter a 0:");
			String diagnosis = sc.next();
			System.out.println("Enter the new medication, if you don't want to edit it enter a 0:");
			String medication = sc.next();
			System.out.println("Enter the new duration, if you don't want to edit it enter a 0:");
			String duration = sc.next();
			System.out.println("Enter the new recommendations, if you don't want to edit them enter a 0:");
			String recommendation = sc.next();
			Treatment nuevo = new Treatment(SQL.editTreatment(t.getTreatmentId(),diagnosis,medication,duration,recommendation)); //FUNCION NO CREADA 
			System.out.println("This is the edited treatment:");
			System.out.println(nuevo.toString());
		}		
	}
	private static void editShift() {
		Worker worker = selectWorker();
		System.out.println("These are the shifts associated to worker "+worker.getWorkerName()+ " " +worker.getWorkerSurname()+ " ordered by date: ");
		consultShifts(worker);
		Shift shift = new Shift();
		while (shift == null) {
			System.out.println("Enter the date of the shift that you want to edit:");
			Date date = Date.valueOf(sc.next());
			shift = SQL.searchShiftByDate (null, worker.getWorkerId(), date);//Connection c, Integer workerId, Date date
			}
		System.out.println("This is the selected shift:");
		System.out.println(shift.toString());
		System.out.println("Enter the new shift, if you don't want to edit it enter a 0:");
		String s = sc.next();
		shift.setShift(s);
		System.out.println("Enter the new room, if you don't want to edit it enter a 0:");
		Integer room = sc.nextInt();
		shift.setRoom(room);
		Shift newShift = new Shift(SQL.editShift(null, worker.getWorkerId(), shift.getShift(), shift.getRoom(), shift.getDate());//Connection c, int workerId, String shift, int room, Date date
		System.out.println("This is the edited shift:");
		System.out.println(newShift.toString());
	}
	
	private static void consultShifts(Worker w) {
		System.out.println("Do you want to see your shifts for an specific date? [YES/NO]");
		String answer = sc.next();
		//para que busque el turno del trabajador para x dia
		if(answer.equalsIgnoreCase("YES")&&password.equalsIgnoreCase("yes")) {
			System.out.println("Insert date: [dd/mm/yyyy]");
			String date = sc.next();
			java.util.Date shiftDate = new SimpleDateFormat("dd/MM/yyyy").parse(date); //igual hay una forma mejor de pasarlo a date
			Shift s = SQL.searchShiftByDate (null, w.getWorkerId(), shiftDate); //Connection c, Integer workerId
			if ( s == null) {
				System.out.println("No shifts available for worker " + w.getWorkerName()+" "+ w.getWorkerSurname() + " on " + shiftDate);
			} else {
				System.out.println("These are the shifts of "+w.getWorkerName()+" "+ w.getWorkerSurname()+" on " + shiftDate);
				System.out.println(s.toString());
			}
		//para que busque todos los turnos del trabajador
		} else if(answer.equalsIgnoreCase("NO")&&password.equalsIgnoreCase("no")){
		Shift s = SQL.searchShiftByWorkerId (null, w.getWorkerId()); //Connection c, Integer workerId
		if ( s == null) {
			System.out.println("No shifts available for worker " + w.getWorkerName()+" "+ w.getWorkerSurname());
		} else {
			System.out.println("These are the shifts of "+w.getWorkerName()+" "+ w.getWorkerSurname()+" ordered by date:");
			System.out.println(s.toString());
		}
		 }else {
			System.out.println("Not valid answer. Insert [YES/NO]");
		}
	}
	
	public static void createPatient () throws NotBoundException {		
		Patient p = new Patient();
		System.out.println("Please, input the patient info:");
		System.out.print("Name: ");
		String name = sc.next();
		p.setPatientName(name);
		System.out.print("Surname: ");
		String surname = sc.next();
		p.setPatientSurename(surname);
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
		p.setBdate(bdate);
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
		SQL.addPatient(null, p); //Connection c, Patient p
	}
	
	public static void createWorker() throws NotBoundException {		
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
		s.setWorkerId(w.getWorkerId()); //en teoría el workerId debería autoincrementarse, 
		System.out.println("Start date:  yyyy-[m]m-[d]d");
		String startDate = sc.next();
		Date date = Date.valueOf(startDate);
		s.setDate(date);
		SQL.addWorker(null, w); //Connection c,Worker w
	}
	public static void requestMedTest() throws NotBoundException{
		Patient patient = selectPatient();
		Worker worker = selectWorker();
		MedicalTest medTest = new MedicalTest();
		medTest.setIdPatient(patient.getMedicalCardId());
		medTest.setIdDoctor(worker.getWorkerId());
		System.out.print("Type of medical test: ");
		String type = sc.next();
		medTest.setTestType(type);
		SQL.addMedicalTest(null, medTest);//Connection c, MedicalTest medtest
	}
	
	public static void showPatientsTreatments(Patient patient){
		String treatment;
		List<Treatment> treatmentsList = new ArrayList<Treatment>();
		treatmentsList = SQL.searchPatientsTreatment(null, patient);
		if(treatmentList.isEmpty()){
			System.out.println ("Ther is no treatment available for this patient");
		} else{
			System.out.println("Here you can see all your treatments ordered by date: ");
			System.out.println(treatmentList.toString);
		}
	}
	
	private static void adAccessToPatientsProfile() {
		Patient patient = selectPatient();
		System.out.println(patient.toString());
		}
	
	private static Patient selectPatient() {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient patient = null;
		while(patientList.isEmpty()) {
		System.out.println("Enter the patient's surname:");
		String surname = sc.next();
		patientList = SQL.searchPatient(null, surname); //(connection c, integer id)
		}
		while(patient == null) {
		System.out.println(patientList.toString());
		System.out.println("Enter the medical card number of the chosen patient:");
		Integer medCard = Integer.parseInt(sc.next());
		patient = new Patient(SQL.selectPatient (null, medCard)); //(connection c, integer id)
		}
		return patient;
	}
	private static Worker selectWorker() {
		List<Worker> wList = new ArrayList<Worker>();
		Worker w = null;
		while(wList.isEmpty()) {
		System.out.println("Enter the doctor's surname:");
		String surname = sc.next();
		wList = SQL.searchWorker(null, surname); //(connection c, integer id)
		}
		while(w == null) {
		System.out.println(wList.toString());
		System.out.println("Enter the id of the chosen worker:");
		Integer id = Integer.parseInt(sc.next());
		w = new Worker(SQL.selectWorker (null, id)); //Connection c, Integer workerId
		}
		return w;
	}
}