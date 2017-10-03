import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    private String driver, url;
    private static Connection con;

    private ConexionBD() {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://127.0.0.1:3306/tallermecanico";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConexion() {
        if (con == null) {
            new ConexionBD();
        }
        return con;
    }
}