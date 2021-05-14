package db.menu;
 

import java.rmi.NotBoundException;

import java.security.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import db.pojos.*;
import pojos.users.*;
import db.interfaces.UMInterface;
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
				System.out.println("1. Login");
				System.out.println("0. Exit");
				int choice = sc.nextInt();
				switch(choice) {
				case 1:
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
	private static void register(String name, String surname, Integer idUser) throws Exception {
		//autogenerateID
		String username = ""+name.charAt(0)+"."+surname+""+Integer.parseInt(surname.substring(0, 1));
		//autogenerated password
		String[] symbols = {"0", "1", "9", "7", "K", "Q", "a", "b", "c", "U","w","3","0"};
        int length = 14;
        Random random;
            random = SecureRandom.getInstanceStrong();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                 int indexRandom = random.nextInt ( symbols.length );
                 sb.append( symbols[indexRandom] );
            }
            String password = sb.toString();
		//List the roles
		System.out.println(userman.getRoles());
		//ask the user for a role
		System.out.println("Type the chosen role ID: ");
		int id = sc.nextInt();
		Role role = userman.getRole(id);
		//generate the hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(username, hash, role);
		userman.newUser(user);
		System.out.println("The autogenerated username is:"+ username);
		System.out.println("The autogenerated password is:"+ password);
		if(user.getRole().getRole().equalsIgnoreCase("patient")) {
			jdbc.createLinkUserPatient(c, user.getUserId(), idUser); 
		} else if(user.getRole().getRole().equalsIgnoreCase("medstaff")||user.getRole().getRole().equalsIgnoreCase("adstaff")) {
			jdbc.createLinkUserWorker(c, user.getUserId(), idUser); 
		} 
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
			patientMenu(user.getUserId());
		} else if(user.getRole().getRole().equalsIgnoreCase("medicalStaff")){
			medStaffMenu(user.getUserId());
		}else if(user.getRole().getRole().equalsIgnoreCase("adStaff")){
			adStaffMenu(user.getUserId());
		}
	}

	
	public static void patientMenu(Integer userID) throws Exception{
		
		Patient patient = new Patient(jdbc.selectPatientByUserId(c,userID));
		System.out.println("Hello Mr/Ms "+patient.getPatientSurname());
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
	
	public static void medStaffMenu(Integer userID) throws Exception{
		
		Worker medStaff = new Worker(jdbc.selectWorkerByUserId(c,userID));
		System.out.println("Hello Mr/Ms "+medStaff.getWorkerSurname());
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
					accessToAPatientsProfile(medStaff);
					break;
				case 2:
					System.out.println("Consult my shifts");
					consultShifts(medStaff);
					break;
			}
		}while(true);
	}
	
	public static void adStaffMenu(Integer userID) throws Exception{
		
		Worker adStaff = new Worker(jdbc.selectWorkerByUserId(c,userID));
		System.out.println("Hello Mr/Ms "+adStaff.getWorkerSurname());
		System.out.println("Choose an option[0-5]:");
		System.out.println(" 1. Register new administration staff \n1. Register new patient \n 2. Register new worker\n 3. Acces to a patient's profile\n 4. Request new medical test\n 5. Edit shifts \n 0. Exit");
		option = sc.nextInt();
		
		do {
			switch (option) {
				case 0:
					System.out.println("Thank you for using our system");
					jdbc.disconnect(c);
					userman.disconnect();
					System.exit(0);
				case 1: 
					System.out.println("Register new worker");
					createWorker();
					break;
				case 2: 
					System.out.println("Register new patient");
					createPatient();
					break;
				case 3: 
					//TODO podemos poner esta opcion para xml
					break;
				case 4: 
					System.out.println("Access to a patient's profile");
					adAccessToPatientsProfile();
					break;	
				case 5: 
					System.out.println("Add new medical test");
					addMedTest();
					break;	
				case 6: 
					System.out.println("Edit shifts");
					editShift();
					break;	
			}
		}while(true);
	}
	
	private static void accessToAPatientsProfile(Worker medStaff) throws Exception {
		Patient patient = selectPatient();
		do {
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
				consultMedicalTest(patient);
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
		} while(true);
	}
	
	private static void consultMedicalTest(Patient patient) throws Exception {
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
		Treatment t = new Treatment(diagnosis, recommendation, startDate, medication, duration, patient.getMedicalCardId());
		jdbc.addTreatment(c, t); 
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
			Integer id = null;
			while (t == null) {
			System.out.println("Enter the id of the treatment that you want to edit:");
			id = sc.nextInt();
			t = new Treatment(jdbc.selectTreatment(c, id));//Connection c, Integer id
			}
			System.out.println("This is the selected treatment:");
			System.out.println(t.toString());
			System.out.println("Enter the new diagnosis, if you don't want to edit it enter a 0:");
			String diagnosis = sc.next();
			if(diagnosis.equals("0")) {
				diagnosis = null;
			}
			System.out.println("Enter the new start date in this format[yyyy-mm-dd], if you don't want to edit it enter a 0:");
			String date = sc.next();
			Date startDate;
			if(date.equals("0")) {
				startDate = null;
			}else {
				startDate = Date.valueOf(date);
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
			Treatment nuevo = new Treatment(jdbc.editTreatment(c, id ,diagnosis,medication,startDate, duration,recommendation)); 
			System.out.println("This is the edited treatment:");
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
			shift = jdbc.selectShift (c,id);
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
		System.out.println("Enter the new date, if you don't want to edit it enter a 0:");
		String date = sc.next();
		Date fecha;
		if(date.equals("0")) {
			fecha=null;
		}else {
			fecha = Date.valueOf(date);
		}
		Shift newShift = new Shift(jdbc.editShift(c,shift.getShiftId(),worker.getWorkerId(), time,room, fecha));//Connection c, int workerId, String shift, int room, Date date
		System.out.println("This is the edited shift:");
		System.out.println(newShift.toString());
	}
	
	private static void consultShifts(Worker w) throws Exception {
		System.out.println("Do you want to see your shifts for an specific date? [YES/NO]");
		String answer = sc.next();
		//para que busque el turno del trabajador para x dia
		if(answer.equalsIgnoreCase("YES")) {
			System.out.println("Insert date: [dd/mm/yyyy]");
			String date = sc.next();
			Date shiftDate = Date.valueOf(date);
			List<Shift> s = new ArrayList<>();
			s.addAll( jdbc.searchShiftByDate (c, w.getWorkerId(), shiftDate)); //Connection c, Integer workerId
			if (s.isEmpty()) {
				System.out.println("No shifts available for worker " + w.getWorkerName()+" "+ w.getWorkerSurname() + " on " + shiftDate);
			} else {
				System.out.println("These are the shifts of "+w.getWorkerName()+" "+ w.getWorkerSurname()+" on " + shiftDate);
				for(Shift shift : s) {
					System.out.println(shift.toString());
				}
			}
		//para que busque todos los turnos del trabajador
		} else if(answer.equalsIgnoreCase("NO")){
			List<Shift> s = new ArrayList<>();
			s.addAll(jdbc.searchShiftByWorkerId (c, w.getWorkerId()));  //Connection c, Integer workerId
			if (s.isEmpty()) {
				System.out.println("No shifts available for worker " + w.getWorkerName()+" "+ w.getWorkerSurname());
			} else {
				System.out.println("These are the shifts of "+w.getWorkerName()+" "+ w.getWorkerSurname()+" ordered by date:");
				for(Shift shift : s) {
					System.out.println(shift.toString());
				}
			}
		}else {
			System.out.println("Not valid answer. Insert [YES/NO]");
		}
	}
		
	
	public static void createPatient () throws NotBoundException, Exception {
		//System.out.flush();

		Patient p = new Patient();

		System.out.println("Please, input the patient info:");
		System.out.print("Name: "); 
		String name = sc.nextLine();
		p.setPatientName(name);
		System.out.print("Surname: "); 
		String surname = sc.nextLine();
		p.setPatientSurname(surname);
		System.out.print("Medical card number: "); 
		Integer medCardNumber; 
			do { 
				medCardNumber = sc.nextInt(); 
				System.out.print("Error. Please introduce medical card number: "); //Ns si poner esto o si poner q el paciente ya existe. Creo q es mejor lo de q el paciente ya existe.
			} while (jdbc.selectPatient(c, medCardNumber) != null );
			p.setMedicalCardId(medCardNumber);

		System.out.print("Gender: ");
		String gender = sc.next();   
		try {
			p.setGender(gender);		
		} catch (Exception e) {
			do{
				System.out.print("Not a valid gender. Please introduce a gender (Male or Female): ");
				gender = sc.next();
			} while (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")));
			p.setGender(gender);
		}

		System.out.print("Blood type: ");
		String bloodType = sc.next();
		try {
			p.setBloodType(bloodType);
		} catch (Exception e) {

			int bloodtypecontrol = 0;

			do{
				int bloodtypefailcount = 0;

				if(bloodtypefailcount >= 2){
					System.out.println("Valid blood types are: A+, A-, B+, B-, AB+, AB-, O+, O-");
				}

				System.out.print("Not a valid blood type. Please introduce a valid one: ");
				bloodType = sc.next();
				bloodtypefailcount++;

				switch (bloodType) {
				case "o+":
				case "O+":
					bloodType = "0+";
					bloodtypecontrol = 1;
					break;
				case "O-":
				case "o-":
					bloodType = "0-";
					bloodtypecontrol = 1;
					break;
				case "A+":
				case "A-":
				case "B+":
				case "B-":
				case "AB+":
				case "AB-":
				case "0+":
				case "0-":
				case "a+":
				case "a-":
				case "b+":
				case "b-":
				case "ab+":
				case "ab-":
					bloodtypecontrol = 1;
					break;
				default:
					bloodtypecontrol = 0;
					break;
				}

			} while (bloodtypecontrol == 0);

			p.setBloodType(bloodType);
		}

		System.out.print("Allergies, enter a 0 if the patient has no allergies: "); 
		String allergie = sc.next();
		if(allergie.equalsIgnoreCase("0")) {
			allergie = null;
		}
		p.setAllergieType(allergie);

		System.out.print("Date of birth [yyyy-mm-dd]: ");	
		String birthdate = sc.next();
		Date bdate; 
		try {
			bdate = Date.valueOf(birthdate);
			if (bdate.before(Date.valueOf(LocalDate.now()))) {
				p.setbDate(bdate);
			} else {
				do {
					System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");
					birthdate = sc.next();
					bdate = Date.valueOf(birthdate);
				} while (bdate.before(Date.valueOf(LocalDate.now())));
				p.setbDate(bdate);
			}
		} catch (Exception e) {
			int b=0;
			do {
				try {	
					System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
					birthdate = sc.next();
					bdate = Date.valueOf(birthdate); 
			
					if (bdate.before(Date.valueOf(LocalDate.now()))) {
						p.setbDate(bdate);
					} else {
						do {
							System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");							
							birthdate = sc.next();
							bdate = Date.valueOf(birthdate);
						} while (bdate.before(Date.valueOf(LocalDate.now())));
						p.setbDate(bdate);
					}
					b=1;
				} catch (Exception e1) {
				}
			} while (b==0);
		}
		
		System.out.print("Check in date [yyyy-mm-dd]: "); 
		String checkindate = sc.next();
		Date cdate;
		try {
			cdate = Date.valueOf(checkindate);
			if (cdate.before(Date.valueOf(LocalDate.now()))) {
				p.setCheckInDate(cdate);
			} else {
				do {
					System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");
					checkindate = sc.next();
					cdate = Date.valueOf(checkindate);
				} while (cdate.before(Date.valueOf(LocalDate.now())));
				p.setCheckInDate(cdate);
			}
		} catch (Exception e) {
			int b=0;
			do {
				try {	
					System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
					checkindate = sc.next();
					cdate = Date.valueOf(checkindate); 
			
					if (cdate.before(Date.valueOf(LocalDate.now()))) {
						p.setCheckInDate(cdate);
					} else {
						do {
							System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");							
							checkindate = sc.next();
							cdate = Date.valueOf(checkindate);
						} while (cdate.before(Date.valueOf(LocalDate.now())));
						p.setCheckInDate(cdate);
					}
					b=1;
				} catch (Exception e1) {
				}
			} while (b==0);
		}

		System.out.print("Address: ");				
		String address = sc.nextLine();
		p.setPatientAddress(address);
		Boolean hospitalized;
		String hosp;
		do{
			System.out.print("Hospitalization [true/false]: ");
			hosp = sc.next();
		}while(!(hosp.equals("true")||hosp.equals("false")));
		hospitalized = Boolean.valueOf(hosp);
		p.setHospitalized(hospitalized);
		String answer;
		do {
		System.out.println("Would you like to link this patient to a doctor?[yes/no]");
		answer = sc.next();
		}while (!(answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("no")));
		jdbc.addPatient(c, p);//Connection c, Patient p
		if(answer.equalsIgnoreCase("yes")) {
			Worker doctor = new Worker(selectWorker());
			jdbc.createLinkDoctorPatient(c, doctor.getWorkerId(), p.getMedicalCardId());
		}
		System.out.println("Let's proceed with the registration, the username and password will be autogenerated by the system:");
		register(p.getPatientName(),p.getPatientSurname(), p.getMedicalCardId());
		System.out.println("The patient was succesfully added to the database");
	}
	
	public static void createWorker() throws Exception{
		Worker w = new Worker();

		System.out.println("Please, input the worker info:");
		System.out.print("Name: ");
		String name = sc.nextLine();
		w.setWorkerName(name);

		System.out.print("Surname: ");
		String surname = sc.nextLine();
		w.setWorkerSurname(surname);

		System.out.print("Type of worker: ");
		String type = sc.next();
		w.setTypeWorker(type);

		System.out.print("Specialty: ");
		String specialty = sc.next();
		w.setSpecialtyId(specialty);


		System.out.println("Let's proceed with the registration, the username and password will be autogenerated by the system:");
		register(w.getWorkerName(), w.getWorkerSurname(), w.getWorkerId());  
		jdbc.addWorker(c, w); //Connection c,Worker w
		System.out.println("The worker was succesfully added to the database");

		
	}

	/**
	 * Adds a shift for a given worker
	 * @param workerId - Shift will be assigned to this worker.
	 * @throws Exception
	 */
	public static void createShift(int workerId) throws Exception{
		Shift s = new Shift();

		System.out.print("Start date for this shift [yyyy-mm-dd]:  ");
		String startDate = sc.next();
		Date date;
		try {
			date = Date.valueOf(startDate);
			s.setDate(date); //TODO - aqui hay un throw q no me gusta mucho y ns si deberíamos (En caso de dejarlo hay q hacer un poco de copypasta)
		} catch (Exception e) {
			int b=0;
			do {
				try {
					System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
					startDate = sc.next();
					date = Date.valueOf(startDate);
					b=1;
					s.setDate(date); 
				} catch (Exception e1) {
				}
			} while (b==0);
		}

		System.out.print("Shift (morning/afternoon/night): ");
		String shift = sc.next();
		int scount = 0;
		do {
			if(shift.equalsIgnoreCase("morning") || shift.equalsIgnoreCase("afternoon") || shift.equalsIgnoreCase("night")){
				s.setTurn(shift);
				scount = 1;
			} else {
				System.out.print("Please introduce a valid shift (morning/afternoon/night): ");
				shift = sc.next();
			}
		} while (scount == 0);

		System.out.print("Room: "); //TODO - Deberíamos limitar esto. (como lo q hay debajo)
		Integer roomER = sc.nextInt();
		s.setRoom(roomER);

		// System.out.print("Room: "); 
		// Integer roomER = sc.nextInt();
		// int MAXRoomEr = 100; //Poner un limite cualquiera.
		// if (roomER <= MAXRoomEr) { 
		// 	s.setRoom(roomER);
		// } else {
		// 	int roomcontrol = 0;
		// 	do {
		// 		System.out.print("That room doesnt exist, please introduce a valid room: ");
		// 		roomER = sc.nextInt();
		// 		if (roomER <= MAXRoomEr) { 
		// 			s.setRoom(roomER);
		// 			roomcontrol = 1;
		// 		}
		// 	} while (roomcontrol == 0);
		// }
		s.setWorkerId(workerId);
		jdbc.addShift(c, s);
		System.out.println("The shift was succesfully added.");
	}

	public static void addMedTest() throws Exception{
		Patient patient = new Patient(selectPatient());
		MedicalTest medTest = new MedicalTest();
		medTest.setPatientId(patient.getMedicalCardId());
		System.out.print("Type of medical test: ");
		String type = sc.next();
		medTest.setTestType(type);
		jdbc.addMedicalTest(c, medTest);//Connection c, MedicalTest medtest
		System.out.println("Medical test added");
	}
	
	
	private static void adAccessToPatientsProfile() throws Exception { 

		System.out.flush();
		System.out.println("Access to patient profile.");
		System.out.println("Please, select one of the following option[0-2]");
		System.out.println("1. Change patient data.");
		System.out.println("2. Add a doctor to the patient.");
		System.out.println("0. Back.");
		int key;
		int ctrl1 = 0; //Control para el primer menu.
		int key2;
		int ctrl2 = 0; //Control para segundo menu.
		Patient patient; 
		do {
			key = sc.nextInt();

			switch (key) {
				case 1: //Cambiar cosas del patient

					System.out.flush();
					System.out.println("Change Patient data.");
					patient = selectPatient();
					System.out.println("The selected patient is:");
					System.out.println(patient.toString());
					System.out.println("What would you like to change?");
					System.out.println("1. Name.\n" +
									   "2. Surname.\n" +
									   "3. Gender.\n" +
									   "4. Blood type.\n" +
									   "5. Allergies.\n" +
									   "6. Birth date.\n" +
									   "7. Check-in date.\n" +
									   "8. Address.\n" +
									   "9. Hospitalization.\n" + 
									   "0. Exit."
									);
					System.out.print("Select one option: ");

					do {
						key2 = sc.nextInt();

						switch (key2) { 
							case 1: //nombre
								System.out.print("Introduce the new name: ");
								String newName = sc.nextLine();
								jdbc.updatePatientName(c, patient.getMedicalCardId(), newName);
								ctrl2 = 1; //TODO - ns si dejar esto o si quitarlo para q se quede en el menu hasta q no le de a exit (por si quiere seguir haciendo cosas)
								break;
							case 2: //apellidos
								System.out.print("Introduce the new surname: ");
								String newSurname = sc.nextLine();
								jdbc.updatePatientSurname(c, patient.getMedicalCardId(), newSurname);
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 3: //genero
								System.out.print("Introduce the new gender: ");
								String newGender = sc.next();
								
								if (!(newGender.equalsIgnoreCase("male") || newGender.equalsIgnoreCase("female"))) {
									
									do{
										System.out.print("Not a valid gender. Please introduce a gender (Male or Female): ");
										newGender = sc.next();
									} while (!(newGender.equalsIgnoreCase("male") || newGender.equalsIgnoreCase("female")));
						
								}

								jdbc.updatePatientGender(c, patient.getMedicalCardId(), newGender);
								
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 4: //sangre
								System.out.print("Introduce the new blood type (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
								String newBloodType;
								//TODO - revisar esto

								int bloodtypecontrol = 0;
								int bloodtypefailcount = 0;

								do{
					
									newBloodType = sc.next();
					
									switch (newBloodType) {
									case "o+":
									case "O+":
										newBloodType = "0+";
										bloodtypecontrol = 1;
										break;
									case "O-":
									case "o-":
										newBloodType = "0-";
										bloodtypecontrol = 1;
										break;
									case "A+":
									case "A-":
									case "B+":
									case "B-":
									case "AB+":
									case "AB-":
									case "0+":
									case "0-":
									case "a+":
									case "a-":
									case "b+":
									case "b-":
									case "ab+":
									case "ab-":
										bloodtypecontrol = 1;
										break;
									default:
										bloodtypefailcount++;
										bloodtypecontrol = 0;

										if(bloodtypefailcount >= 2){
											System.out.println("Valid blood types are: A+, A-, B+, B-, AB+, AB-, O+, O-");
										}
										
										System.out.print("Not a valid blood type. Please introduce a valid one: ");	
										break;
									}
									

								} while (bloodtypecontrol == 0);
					

								jdbc.updatePatientBloodType(c, patient.getMedicalCardId(), newBloodType);
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 5: //alergias
								System.out.print("Introduce the new allergies (none - if patient doesnt have): ");
								String newAllergies = sc.nextLine();
								jdbc.updatePatientAllergies(c, patient.getMedicalCardId(), newAllergies);
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 6: // bdate
								System.out.print("Introduce the new birthdate [yyyy-mm-dd]: ");
								Date newBDate;
								String bdatesString = sc.next();
								try { //TODO - revisar esto.
									newBDate = Date.valueOf(bdatesString);
									if (newBDate.before(Date.valueOf(LocalDate.now()))) {
										jdbc.updatePatientBirthDate(c, patient.getMedicalCardId(), newBDate);
									} else {
										do {
											System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");
											bdatesString = sc.next();
											newBDate = Date.valueOf(bdatesString);
										} while (newBDate.before(Date.valueOf(LocalDate.now())));
										jdbc.updatePatientBirthDate(c, patient.getMedicalCardId(), newBDate);
									}
								} catch (Exception e) {
									int b=0;
									do {
										try {	
											System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
											bdatesString = sc.next();
											newBDate = Date.valueOf(bdatesString); 
									
											if (newBDate.before(Date.valueOf(LocalDate.now()))) {
												jdbc.updatePatientBirthDate(c, patient.getMedicalCardId(), newBDate);
											} else {
												do {
													System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");							
													bdatesString = sc.next();
													newBDate = Date.valueOf(bdatesString);
												} while (newBDate.before(Date.valueOf(LocalDate.now())));
												jdbc.updatePatientBirthDate(c, patient.getMedicalCardId(), newBDate);
											}
											b=1;
										} catch (Exception e1) {
										}
									} while (b==0);
								}
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 7: //check in
								System.out.print("Introduce the new check-in date [yyyy-mm-dd]: ");
								Date newCheckInDate;
								String checkindateString = sc.next();
								try {
									newCheckInDate = Date.valueOf(checkindateString);
									if (newCheckInDate.before(Date.valueOf(LocalDate.now()))) {
										jdbc.updatePatientCheckInDate(c, patient.getMedicalCardId(), newCheckInDate);
									} else {
										do {
											System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");
											checkindateString = sc.next();
											newCheckInDate = Date.valueOf(checkindateString);
										} while (newCheckInDate.before(Date.valueOf(LocalDate.now())));
										jdbc.updatePatientCheckInDate(c, patient.getMedicalCardId(), newCheckInDate);
									}
								} catch (Exception e) {
									int b=0;
									do {
										try {
											System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
											checkindateString = sc.next();
											newCheckInDate = Date.valueOf(checkindateString); 
									
											if (newCheckInDate.before(Date.valueOf(LocalDate.now()))) {
												jdbc.updatePatientCheckInDate(c, patient.getMedicalCardId(), newCheckInDate);
											} else {
												do {
													System.out.print("Please introduce a valid date [yyyy-mm-dd]: ");							
													checkindateString = sc.next();
													newCheckInDate = Date.valueOf(checkindateString);
												} while (newCheckInDate.before(Date.valueOf(LocalDate.now())));
												jdbc.updatePatientCheckInDate(c, patient.getMedicalCardId(), newCheckInDate);
											}
											b=1;
										} catch (Exception e1) {
										}
									} while (b==0);
								}

								ctrl2 = 1; // lo mismo q arriba
								break;
							case 8: //direccion
								System.out.print("Introduce the new address: ");
								String newAddress = sc.nextLine();
								jdbc.updatePatientAddress(c, patient.getMedicalCardId(), newAddress);
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 9: //hospitalizado
								System.out.print("Does the patient need to be hospitalized?: ");
								boolean hosp = sc.nextBoolean();
								jdbc.updatePatientHospitalization(c, patient.getMedicalCardId(), hosp);
								ctrl2 = 1; // lo mismo q arriba
								break;
							case 0:  //Exit
								ctrl2 = 1;
								break;

							default:
								System.out.print("Not a valid option. Please select a valid option: ");
								break;
						}
	
					} while (ctrl2 == 0);

					ctrl1 = 1; //TODO - ns si dejar esto o si quitarlo para q se quede en el menu hasta q no le de a exit (por si quiere seguir haciendo cosas)
					break;
				case 2: //Añadir un doctor al patient

					System.out.flush();
					System.out.println("Add doctor to patient.");

					patient = selectPatient();
					System.out.println("Select the doctor that you want to add:");
					Worker doctor = new Worker(selectWorker());
					jdbc.createLinkDoctorPatient(c, doctor.getWorkerId(), patient.getMedicalCardId());
		
					ctrl1 = 1; //TODO - ns si dejar esto o si quitarlo para q se quede en el menu hasta q no le de a exit (por si quiere seguir haciendo cosas)
					break;
				case 0: //me he equivocado halp xD
					ctrl1 = 1;
					break;
				default:
					System.out.print("Not a valid option. Please select a valid option: ");
					break;
			}
	

		} while (ctrl1 == 0);
	
	}
	
	private static Patient selectPatient() throws Exception {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient patient = null;
		while(patientList.isEmpty()) {
		System.out.print("Enter the patient's surname: ");
		String surname = sc.next();
		patientList.addAll(jdbc.searchPatient(c, surname)); //(connection c, integer id)
		}
		while(patient == null) {
		System.out.println(patientList.toString());  
		System.out.print("Enter the medical card number of the chosen patient: ");
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


// 	public static void createWorker() throws Exception {
		
// 		// - Esto habría q separarlo, no creo q sea buena idea crear shift y worker a la vez.
// // A�ADIR EL REGISTER AL FINAL DEL WORKER IGUAL QUE ESTA HECHO EN PATIENT
// 		Worker w = new Worker();
// 		Shift s = new Shift();

// 		System.out.println("Please, input the worker info:");
// 		System.out.print("Name: ");
// 		String name = sc.nextLine();
// 		w.setWorkerName(name);

// 		System.out.print("Surname: ");
// 		String surname = sc.nextLine();
// 		w.setWorkerSurname(surname);

// 		System.out.print("Type of worker: ");
// 		String type = sc.next();
// 		w.setTypeWorker(type);

// 		System.out.print("Specialty: ");
// 		String specialty = sc.next();
// 		w.setSpecialtyId(specialty);

// 		System.out.print("Room: ");
// 		Integer roomER = sc.nextInt();
// 		w.setRoomEr(roomER);
// 		s.setRoom(roomER);

// 		System.out.print("Shift: ");
// 		String shift = sc.next();
// 		s.setTurn(shift);
// 		s.setWorkerId(w.getWorkerId()); //en teor�a el workerId deber�a autoincrementarse, //esto hace kaboom 99% seguro, pq worker id no esta iniciado en ningun momento..
		
// 		System.out.print("Start date [yyyy-mm-dd]:  ");
// 		String startDate = sc.next();
// 		Date date;
// 		try {
// 			date = Date.valueOf(startDate);
// 			s.setDate(date); // - aqui hay un throw q no me gusta mucho y ns si deberíamos (En caso de dejarlo hay q hacer un poco de copypasta)
// 		} catch (Exception e) {
// 			int b=0;
// 			do {
// 				try {
// 					System.out.print("Please introduce a valid date format [yyyy-mm-dd]: ");
// 					startDate = sc.next();
// 					date = Date.valueOf(startDate);
// 					b=1;
// 					s.setDate(date); 
// 				} catch (Exception e1) {
// 				}
// 			} while (b==0);
// 		}
// 		jdbc.addWorker(c, w); //Connection c,Worker w
// 	}
