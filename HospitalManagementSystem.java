import java.sql.*;

import java.util.ArrayList;

import java.util.List;

import java.util.Scanner;

public class HospitalManagementSystem {

private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hospital";

private static final String USERNAME = "username";

private static final String PASSWORD = "password";

public static void main(String[] args) {

try {

Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, 

PASSWORD);

HospitalDatabase database = new HospitalDatabase(connection);

database.initializeSampleData();

Scanner scanner = new Scanner(System.in);

System.out.println("Welcome to Hospital Management System");

} catch (SQLException e) {

System.err.println("Database connection error: " + e.getMessage();

e.printStackTrace();

}

}

}

class HospitalDatabase {

private Connection connection;

public HospitalDatabase(Connection connection) {

this.connection = connection;

}

public void initializeSampleData() {

try (Statement statement = connection.createStatement()) {

statement.executeUpdate("INSERT INTO Patients VALUES (1, 'John Doe', '1990-01-01', 

'Male', '123 Main St', '123-456-7890')");

statement.executeUpdate("INSERT INTO Staff VALUES (1, 'Dr. Smith', 'Doctor', 1)");

statement.executeUpdate("INSERT INTO Departments VALUES (1, 'Cardiology', 'Heartrelated diseases')");

statement.executeUpdate("INSERT INTO Appointments VALUES (1, 1, 1, '2024-04-05 

10:00', 'Annual checkup', 'Scheduled')");

} catch (SQLException e) {

System.err.println("Sample data initialization error: " + e.getMessage());

e.printStackTrace();

}

}

public void addPatientRecord(String name, String dob, String gender, String address, String 

phone) {

String sql = "INSERT INTO Patients (name, dob, gender, address, phone) VALUES (?, ?, ?, ?, 

?)";

try (PreparedStatement statement = connection.prepareStatement(sql)) {

statement.setString(1, name);

statement.setString(2, dob);

statement.setString(3, gender);

statement.setString(4, address);

statement.setString(5, phone);

statement.executeUpdate();

System.out.println("Patient record added successfully.");

} catch (SQLException e) {

System.err.println("Error adding patient record: " + e.getMessage());

e.printStackTrace();

}

}

public void updatePatientRecord(int patientId, String name, String dob, String gender, String 

address, String phone) {

String sql = "UPDATE Patients SET name=?, dob=?, gender=?, address=?, phone=? WHERE 

patient_id=?";

try (PreparedStatement statement = connection.prepareStatement(sql)) {

statement.setString(1, name);

statement.setString(2, dob);

statement.setString(3, gender);

statement.setString(4, address);

statement.setString(5, phone);

statement.setInt(6, patientId);

int rowsUpdated = statement.executeUpdate();if (rowsUpdated > 0) {

System.out.println("Patient record updated successfully.");

} else {

System.out.println("No patient record found with ID " + patientId);

}

} catch (SQLException e) {

System.err.println("Error updating patient record: " + e.getMessage());

e.printStackTrace();

}}

public void deletePatientRecord(int patientId) {

String sql = "DELETE FROM Patients WHERE patient_id=?";

try (PreparedStatement statement = connection.prepareStatement(sql)) {

statement.setInt(1, patientId);

int rowsDeleted = statement.executeUpdate();

if (rowsDeleted > 0) {

System.out.println("Patient record deleted successfully.");

} else {

System.out.println("No patient record found with ID " + patientId);

}

} catch (SQLException e) {

System.err.println("Error deleting patient record: " + e.getMessage());

e.printStackTrace();

}

}

public List<Patient> getAllPatients() {

List<Patient> patients = new ArrayList<>();

String sql = "SELECT * FROM Patients";

try (Statement statement = connection.createStatement();

ResultSet resultSet = statement.executeQuery(sql)) {

while (resultSet.next()) {

Patient patient = new Patient(

resultSet.getInt("patient_id"),

resultSet.getString("name"),

resultSet.getString("dob"),

resultSet.getString("gender"),

resultSet.getString("address"),

resultSet.getString("phone"));

patients.add(patient);}}

catch (SQLException e) {

System.err.println("Error retrieving patients: " + e.getMessage());

e.printStackTrace();

}

return patients;

}

public void scheduleAppointment(int patientId, int staffId, String appointmentDate, String 

reason) {

String sql = "INSERT INTO Appointments (patient_id, staff_id, appointment_date, reason, 

status) VALUES (?, ?, ?, ?, 'Scheduled')";

try (PreparedStatement statement = connection.prepareStatement(sql)) {

statement.setInt(1, patientId);

statement.setInt(2, staffId);

statement.setString(3, appointmentDate);

statement.setString(4, reason);

statement.executeUpdate();

System.out.println("Appointment scheduled successfully.");

} catch (SQLException e) {

System.err.println("Error scheduling appointment: " + e.getMessage());

e.printStackTrace();

}

}

}

class Patient {

private int patientId;

private String name;

private String dob;

private String gender;

private String address;

private String phone;

public Patient(int patientId, String name, String dob, String gender, String address, String phone) {

this.patientId = patientId;

this.name = name;

this.dob = dob;

this.gender = gender;this.address = address;

this.phone = phone;

}

public int getPatientId() {

return patientId;

}

public void setPatientId(int patientId) {

this.patientId = patientId;

}

public String getName() {

return name;

}

public void setName(String name) {

this.name = name;

}

public String getDob() {

return dob;

}

public void setDob(String dob) {

this.dob = dob;

}

public String getGender() {

return gender;

}

public void setGender(String gender) {

this.gender = gender;

}

public String getAddress() {

return address;

}

public void setAddress(String address) {

this.address = address;

}

public String getPhone() {

return phone;

}

public void setPhone(String phone) {this.phone = phone;

}}
