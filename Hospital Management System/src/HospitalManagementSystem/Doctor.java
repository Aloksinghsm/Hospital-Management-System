package HospitalManagementSystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
    private Connection connection;
    public  Doctor(Connection conn){
        this.connection= conn;
    }
    public void view_doctor(){
        String query="SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet resultSet= preparedStatement.executeQuery();
            System.out.println("DOCTORS : ");
            System.out.println("+------------+--------------------+----------------------+");
            System.out.println("| Doctor  ID |    Doctor  Name    |    Specialization    |");
            System.out.println("+------------+--------------------+----------------------+");
            while (resultSet.next()){
                int id=resultSet.getInt("Doctor_ID");
                String name=resultSet.getString("Doctor_name");
                String specialization=resultSet.getString("Specialization");
                System.out.printf("| %-10s | %-18s | %-20s |\n",id,name,specialization);
                System.out.println("+------------+--------------------+----------------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getDoctorByID(int Doctor_ID){
        String query="SELECT * FROM doctors WHERE Doctor_ID = ?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,Doctor_ID);
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