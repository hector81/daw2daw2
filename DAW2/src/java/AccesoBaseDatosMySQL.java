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

public class AccesoBaseDatosMySQL{
    public static void main(String args[]){

        //Definir el driver y el url de la conexion a la BD teniendo en cuenta el SGBD empleado
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:3306/tallermecanico";
        Connection con=null;
        try{
            //Carga del driver
            Class.forName(driver);

            //Establecimiento de la conexion. El segundo y tercer argumento son el user
            //y password, por defecto, necesarios para conectarnos como administradores a la BD
            con=DriverManager.getConnection(url,"root","");

            //Obtencion de un objeto Statement para ejecutar sentencias SQL
            Statement stmt=con.createStatement();

            //Ejecucion de la sentencia SQL y obtencion de resultados en un objeto ResultSet
            String sentenciaSQL="SELECT * from facturamecanica";
            ResultSet rs=stmt.executeQuery(sentenciaSQL);

            //Muestra de resultados mediante un bucle que recorre los registros que verifican
            //la sentencia
            String nombre,marca;
            int importe;
            while(rs.next()){
                nombre=rs.getString("Nombre");
                marca=rs.getString("Marca");
                importe=rs.getInt("Importe");

                System.out.println("Nombre: "+nombre+" ,marca: "+marca+" ,importe: "+importe);
            }
        }catch(ClassNotFoundException e){
            System.out.println("Controlador JDBC no encontrado: "+e.toString());
        }catch(SQLException e){
            System.out.println("Excepcion capturada de SQL: "+e.toString());

        //Cierre de la conexion
        }finally{
            try{
                if(con!=null){
                    con.close();
                }
            }catch(SQLException e){
                System.out.println("No se puede cerrar la conexion: "+e.toString());
            }
        }
    }
}