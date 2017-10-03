/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class ServletBorrarDatosTabla extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static boolean comprobarSiEsNumero(String cadenaComprobar) {
        try {
            double d = Integer.parseInt(cadenaComprobar);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String consultaTABLAS = "";
        String columna = request.getParameter("columna");
        String tabla = request.getParameter("tabla");
        Connection con1 = null;
        //Definir el driver y el url de la conexion a la BD teniendo en cuenta el SGBD empleado
        String driver = "com.mysql.jdbc.Driver";

        ///crear 2 conexiones para la base de datos principal y otra para la base datos que usamos
        String url = "jdbc:mysql://127.0.0.1:3306/tallermecanico";
        try {
            //Carga del driver
            Class.forName(driver);

            //Establecimiento de la conexion. El segundo y tercer argumento son el user
            //y password, por defecto, necesarios para conectarnos como administradores a la BD
            con1 = DriverManager.getConnection(url, "root", "");
            PreparedStatement stmt3=null ;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BORRAR FILA</title>");
            out.println("</head>");
            out.println("<body>");
            consultaTABLAS = "DELETE FROM " + tabla + " WHERE " + columna + " = ?";
            stmt3 = con1.prepareStatement(consultaTABLAS);
            if (comprobarSiEsNumero(request.getParameter("id"))) {
                stmt3.setInt(1, Integer.parseInt(request.getParameter("id")));
            } else {
                stmt3.setString(1, request.getParameter("id"));
            }
            stmt3.executeUpdate();
            out.println("<h1>Fila ha sido borrada </h1>");


            //Ejecucion de la sentencia SQL y obtencion de resultados en un objeto ResultSet
            Statement stmt1 = null;
            Statement stmt2 = null;
            
            ResultSet rs1 = null;
            ResultSet rs2 = null;

            mostrarTabla(tabla,out,con1, rs1, rs2,stmt1,stmt2);
            out.println("<a href='./ModificarTabla.html'><button>Volver</button></a>");

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletBorrarDatosTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    public void mostrarTabla(String tabla,PrintWriter out,Connection con1, ResultSet rs1, ResultSet rs2,Statement stmt1,
            Statement stmt2) {
        try {
            String field, type, nul, key, defaul, extra, nombre, marca = "";
            out.println("ENSEÑAR DATOS TÉCNICOS DE LA TABLA");
            String consultaTABLAS = "SHOW COLUMNS FROM  " + tabla + ";";
            out.println("<b>" + tabla + "</b><br>");
            stmt2 = con1.prepareStatement(consultaTABLAS);
            rs1 = stmt2.executeQuery(consultaTABLAS);
            out.println("<table style='border:1px solid black'>");
            out.println("<tr><th>Field</th><th>Type</th><th>Null</th><th>Key</th><th>Default</th><th>Extra</th></tr>");
            while (rs1.next()) {
                field = rs1.getString("FIELD");
                type = rs1.getString("TYPE");
                nul = rs1.getString("NULL");
                key = rs1.getString("KEY");
                defaul = rs1.getString("DEFAULT");
                extra = rs1.getString("EXTRA");
                out.println("<tr><td>" + field + "</td><td>" + type + "</td><td>" + nul + "</td>"
                        + "<td>" + key + "</td><td>" + defaul + "</td><td>" + extra + "</td></tr>");
                
            }   out.println("</table>");
            ////////////////////
            out.println("ENSEÑAR DATOS  DE LA TABLA");
            String consulta = "";
            String posicionColumna = "";
            int numeroColumnas = 0;
            int numeroFilas = 0;
            rs2 = stmt2.executeQuery("SHOW COLUMNS FROM  " + tabla + ";");
            out.println("<b><caption>" + tabla + "</b><caption><br>");
            out.println("<table style='border:1px solid black'>");
            //1ºbucle para los campos
            out.println("<tr>");
            while (rs2.next()) {
                field = rs2.getString("FIELD");
                numeroColumnas++;
                out.println("<th>" + field + "</th>"); // arrayTablas.get(x) nombre de la tabla
            }   out.println("</tr>");//cierro la parte de campos
            //ejecuto la consulta correspondiente
            consulta = "SELECT *" + " FROM " + tabla + ";";
            rs2 = stmt2.executeQuery(consulta);
            while (rs2.next()) {   //lee por filas
                out.println("<tr>");
                for (int i = 1; i <= numeroColumnas; i++) {
                    posicionColumna = rs2.getString(i) + "";//pasar a string
                    out.println("<td>" + posicionColumna + "</td>");
                    
                }
                numeroFilas++;
                out.println("</tr>");
            }   rs1.close();
            rs2.close();
            out.println("</table>");
            out.println("<b>Numero Filas: </b>" + numeroFilas + "<br>");
            out.println("<b>Numero Columnas: </b>" + numeroColumnas + "<br>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletModificacionesTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletBorrarDatosTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletBorrarDatosTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
