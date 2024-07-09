package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url="jdbc:mysql://localhost:3306/hospital";

    private static final String username="root";

    private static final String password="Alok@2003";

    public static void main(String[] args){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);

            Doctor doctor=new Doctor(connection);

            while (true){
                System.out.println(" HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. ADD PATIENT ");
                System.out.println("2. VIEW PATIENTS ");
                System.out.println("3. VIEW DOCTORS ");
                System.out.println("4. BOOK APPOINTMENT ");
                System.out.println("5. EXIT ");
                System.out.println("ENTER YOUR CHOICE : ");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        //Add patients
                        patient.add_patient();
                        System.out.println();
                        break;
                    case 2:
                        //view patients
                        patient.view_patient();
                        System.out.println();
                        break;
                    case 3:
                        //view doctors
                        doctor.view_doctor();
                        System.out.println();
                        break;
                    case 4:
                        //Book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        //exit
                        System.out.println("Thank You ! For using hospital management system .");
                        return;
                    default:
                        System.out.println("Enter Valid Choice !!! ");
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.print("Enter Patient Id : ");
        int patientId= scanner.nextInt();
        System.out.print("Enter Doctor Id : ");
        int doctorId= scanner.nextInt();
        System.out.print("Enter Appointment date (YYYY-MM-DD) : ");
        String appointment_date= scanner.next();

        if(patient.getPatientByID(patientId) && doctor.getDoctorByID(doctorId)){
            if (checkDoctorAvailability(doctorId,appointment_date,connection)){
                String appointmentQuery="INSERT INTO appointments(patient_id ,doctor_id,appointment_date) VALUES (?,?,?)";
                try {
                    PreparedStatement preparedStatement= connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointment_date);
                    int rowsAffected= preparedStatement.executeUpdate();

                    if(rowsAffected>0){
                        System.out.println("Appointment Successfully Booked !!");
                    }else {
                        System.out.println("Failed to Book Appointment !!");
                    }

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor not Available on this Date !!!");
            }
        }else{
            System.out.println("Either Doctor or Patient doesn't exist !!!");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId,String appointmentdate,Connection connection){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";

        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                int count= resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
}
