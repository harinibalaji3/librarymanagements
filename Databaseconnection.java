import java.sql.*;
public class Databaseconnection {
   public static Connection getConnection()throws SQLException{
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
    }
    catch(ClassNotFoundException e){
        e.printStackTrace();
        throw new SQLException("database connection error");
    }
   }
   public static void main(String[]args){
    try{
        Connection connection=getConnection();
        System.out.println("connected to database");
        connection.close();
    }
    catch(SQLException e){
        e.printStackTrace();
    }
   }
}
