package DB_Con;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class DBConnection {

//********************************** Start of DBConnection ********************************************************
    private static DBConnection instance = new DBConnection();

    private DBConnection() {
    }

    public static DBConnection getInstance() {
        return instance;
        // singleton design pattern applied
    }

    Connection conn;

    public Connection CreateConnection() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/skin_diseases", "root", "najla");
            return conn;
        } catch (Exception ex) {

            System.out.println(ex);
            conn = null;
            return conn;
        }
    }

//********************************** End of DBConnection ******************************************************
//********************************* Start of result retreival ********************************************************
    public ResultSet treatment(String disease_name, String causes, String treatments) throws Exception {

        ResultSet result;
        try {

            conn = CreateConnection();
            PreparedStatement ps;
            String statement = "select disease_name,causes,treatments from treatment where disease_id=?";
            ps = conn.prepareStatement(statement);

            ps.setString(1, disease_name);
            ps.setString(2, causes);
            ps.setString(3, treatments);
            result = ps.executeQuery();

            return result;

        } catch (Exception ex) {

            result = null;
            return result;

        }

    }

    public HashMap<String, String> treatment(String disease_id) {

        HashMap<String, String> treatmentInfo = new HashMap<>();
        try {
            ResultSet result;
            conn = CreateConnection();
            PreparedStatement ps;
            String statement = "select disease_name,causes,treatments from treatment where disease_id=?";
            ps = conn.prepareStatement(statement);
            ps.setString(1, disease_id);
            result = ps.executeQuery();

            while (result.next()) {
                String dName = result.getString("disease_name");
                String causes = result.getString("causes");
                String treatment = result.getString("treatments");

                treatmentInfo.put("name", dName);
                treatmentInfo.put("cause", causes);
                treatmentInfo.put("treatments", treatment);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return treatmentInfo;

    }

//********************************* End of result retreival ******************************************************************************************
}
