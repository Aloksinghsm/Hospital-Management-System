package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;

    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection= connection;
        this.scanner =scanner;
    }

    public void add_patient(){
        System.out.print("Enter Patient Name : ");
        String name= scanner.next();
        System.out.print("Enter Patient Age : ");
        int age=scanner.nextInt();
        System.out.print("Enter Patient Gender : ");
        String gender = scanner.next();

        try {
            String query="INSERT INTO patients(Patient_name,age,gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient Added Successfully !!");
            }else {
                System.out.println("Admission Failed !!");
            }



        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void view_patient(){
        String query="SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet resultSet= preparedStatement.executeQuery();
            System.out.println("PATIENTS : ");
            System.out.println("+------------+--------------------+-------+--------+");
            System.out.println("| Patient ID |    Patient Name    |  Age  | Gender |");
            System.out.println("+------------+--------------------+-------+--------+");
            while (resultSet.next()){
                int id=resultSet.getInt("Patient_ID");
                String name=resultSet.getString("Patient_name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-5s | %-6s |\n",id,name,age,gender);
                System.out.println("+------------+--------------------+-------+--------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientByID(int Patient_ID){
        String query="SELECT * FROM patients WHERE Patient_ID = ?";

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,Patient_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else{
                return false;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
