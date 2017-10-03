/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vespertino
 */
import java.sql.*;

public class AccesoBaseDatosMySQLPruebas {

    public static void main(String args[]) {
        //Definir el driver y el url de la conexión a la BD teniendo en cuenta el SGBD empleado
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/TallerMecanico";
        Connection con = null;
        try {
            //Carga del driver
            Class.forName(driver);
            //Establecimiento de la conexión. El segundo y tercer argumento son el user
            //y password, por defecto, necesarios para conectarnos como administradores a la BD
            con = DriverManager.getConnection(url, "root", "");
            //Obtención de un objeto Statement para ejecutar sentencias SQL
            Statement stmt = con.createStatement();
            // Definición sentencia INSERT.
            // En el campo id, pasarle null para que el valor autonumérico asigne ids adecuadamente
            //String sentenciaSQL1 = "INSERT INTO FacturaMecanica VALUES(null,\"Luz\",2500,\"Porche\")";
            String sentenciaSQL1 = "INSERT INTO FacturaMecanica VALUES(null,'Luz',2500,'Porche')";
            // Ejec sentencia INSERT
            System.out.println(stmt.executeUpdate(sentenciaSQL1));
            //Ejecución de la sentencia SQL y obtención de resultados en un objeto ResultSet
            String sentenciaSQL = "SELECT * FROM FacturaMecanica";
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            //Muestra de resultados mediante un bucle que recorre los registros que verifican la sentencia
            while (rs.next()) {
            // Nombres de campo son case insensitive
                String conductores = rs.getString("Nombre");
                String marcas = rs.getString("Marca");
                int importes = rs.getInt("Importe");
                System.out.println("Conductor: " + conductores + ". Marca: " + marcas + ". Importe: " + importes);
            }
            // Mostrar numero de registros de la tabla
            // Se crea un ResultSet que contiene un sólo campo de nombre "count(*)"
            String sentenciaSQL2 = "SELECT COUNT(*) FROM FacturaMecanica";
            rs = stmt.executeQuery(sentenciaSQL2);
            if (rs.next()) {
                System.out.println("Numero registros: " + rs.getString("count(*)"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Controlador JDBC no encontrado: " + e.toString());
        } catch (SQLException e) {
            System.out.println("Excepcion capturada de SQL: " + e.toString());
            //Cierre de la conexión
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("No se puede cerrar la conexion: " + e.toString());
            }
        }
    }
}
