Requirements: postgresql installed

Application: SE

For running,
1. Open psql shell create database aims
2. Connect to the database and create the schema using schema.sql file in app folder.
3. Add data to dept, faculty, students and users using psql shell.
4. Now, go to app\src\main\java\myapp\db\DatabaseConnection.java and update the database url, username and password.
5. Use 'gradle run' to start the application.


For testing,
1. Open psql shell create database aims_test
2. Connect to the database and create the schema using schema.sql file in app folder.
3. Add data to the database by running all the queries in test_data.sql
4. Now, go to app\src\main\java\myapp\db\DatabaseConnection.java and update the database url, username and password.
5. Update the fileloc for test in app\src\test\java\myapp\FacultyCommands\UpdateViewGradesTest.java
6. Use './gradlew clean test jacocoTestReport' to test and generate jacoco report for the application.

Possible roles in users relation:
0 for dean
1 for acad section
2 for faculty
3 for student

Departments for science core:
ph,ma,bio,sc

Possible events in current_event relation:
registration, deregister, grading, offer_course, takeback_course, ongoing, completed

Possible status in student_course:
R for registered
F for failed
C for completed and passed

Assumption: Acad Section always adds correct event.