# ER-Databases

Database for the emergency room of a hospital.

The aim of this database is to provide an easy way for regulating all patients and workers that are attended in the emergency room of the hospital. By having a user account with a specific username and password to login, each patient will be able to consult his/her results after leaving the ER room, and each worker will be able to create, keep and modify the information of their patients and their own. The user will either be a patient, a worker from the medical staff or a worker from the administration staff. 


**PRESETTINGS**

The interaction will be different depending on who is accessing to the database. Before everyone can use the application, there will be an administration staff user (username: admin, password: admin) defined by default, so it can introduce the data of new workers and patients that will then have access to the application.
In order to have access to the capabilities of this database, every worker and patient must have been registered by an administration staff user. This register operation will automatically generate a unique username and a specific password for each new worker and patient. Once the patients and workers receive their own username and password, they will be ready to use the database and corresponding interface.


**USER INTERFACE**

Depending on the hospital-user  relationship, different capabilities will be available for the user:
- **Patient:** consult treatments ordered by date, medication or duration and search for a treatment by the name of the medication.
- **Medical Staff:** consult their shifts and access to their patients’ profiles, where the medical staff will be able to consult medical tests and create and edit the treatments and diagnosis of their patients.
- **Administration Staff:** register new workers, register new patients, access a patient’s profile, where he/she will be able to change a patient’s data or add a doctor to a patient; request a new medical test, edit any worker’s shift and delete workers and patients from the database.


**ADDITIONAL DOCUMENTATION**

The folder “Diagrams” contains the different diagrams (ER diagram, mockup, UML, user case diagram) that have been created in order to organize the ideas during the development of the database and the application.
