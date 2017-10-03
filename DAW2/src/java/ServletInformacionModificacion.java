/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vespertino
 */
public class ServletInformacionModificacion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //variable globales
        String nombreTabla, field, type, nul, key, defaul, extra = "";
        String consultaTABLAprincipal = "";
        String consultaTABLAS = "";
        PrintWriter out = response.getWriter();
        
        //parametros capturados
        String usu = request.getParameter("nombre");
        String pas = request.getParameter("password");
        String consultaCREATE = request.getParameter("consultaModificacionCREATE");
        String consultaALTER = request.getParameter("consultaModificacionALTER");
        String consultaINSERT = request.getParameter("consultaModificacionINSERT");
        //Asignacion a la respiesta html que va a generarse
        response.setContentType("text/html");

        //Definir el driver y el url de la conexion a la BD teniendo en cuenta el SGBD empleado
        String driver = "com.mysql.jdbc.Driver";

        ///crear 2 conexiones para la base de datos principal y otra para la base datos que usamos
        String url = "jdbc:mysql://127.0.0.1:3306/information_schema";
        String url2 = "jdbc:mysql://127.0.0.1:3306/tallermecanico";
        
        Connection con1 = null;
        Connection con2 = null;
        Connection con3 = null;
        Connection con4 = null;
        
        try {
            //Carga del driver
            Class.forName(driver);

            //Establecimiento de la conexion. El segundo y tercer argumento son el user
            //y password, por defecto, necesarios para conectarnos como administradores a la BD
            con1 = DriverManager.getConnection(url, usu, pas);
            con2 = DriverManager.getConnection(url2, usu, pas);
            
            out.println("<html>");
            out.println("<head><title>Datos introducidos por Formulario</title></head");
            out.println("<body>");
            out.println("<h1>DATOS INTRODUCIDOS</h1>");
            out.println("<table border)\"5\">");
            out.println("<tr><td><b>USUARIO:</b></td><td><i>" + usu + "</i></td></tr>");
            out.println("</table><p>");
            out.println("<h1>TU CONSULTA</h1>");
            /*SACAR DATOS DE LAS TABLAS*/
            out.println("TABLAS<br>");
            
            //Ejecucion de la sentencia SQL y obtencion de resultados en un objeto ResultSet
            Statement stmt1 = null;
            Statement stmt2 = null;

            ResultSet rs1 = null;
            ResultSet rs2 = null;
            ResultSet rs3 = null;
            ResultSet rs4 = null;
            consultaTABLAprincipal = "SELECT TABLE_NAME FROM TABLES WHERE TABLE_SCHEMA = 'tallermecanico'";
            stmt1 = con1.createStatement();
            stmt2 = con2.createStatement();

            //Obtencion de un objeto Statement para ejecutar sentencias SQL           
            //REALIZAR LAS CONSULTAS
            if (!"".equals(consultaINSERT) && "".equals(consultaALTER) && "".equals(consultaINSERT)) {
                stmt2.executeUpdate(consultaINSERT);
                out.println("INSERTADOS DATOS TABLA");
            }
            if(!"".equals(consultaALTER) && "".equals(consultaCREATE) && "".equals(consultaINSERT)) {
                stmt2.executeUpdate(consultaALTER);
                out.println("ALTERADA TABLA");
            }
            if(!"".equals(consultaCREATE) && "".equals(consultaALTER) && "".equals(consultaINSERT)) {
                stmt2.executeUpdate(consultaCREATE);
                out.println("CREADA TABLA");
            }
            if ("".equals(consultaCREATE) && "".equals(consultaALTER) && "".equals(consultaINSERT)) {
                out.println("NO HAY TABLAS AFECTADAS<br>");
            }
            rs1 = stmt1.executeQuery(consultaTABLAprincipal);
            int contador= 0;
            
            out.println("<table style='border:1px solid black'>");
            ArrayList<String> arrayTablas = new ArrayList<String>();
            //Con el arrayList creamos un array con las tablas que luego usaremos
            while (rs1.next()) {
                contador++;
                nombreTabla = rs1.getString("TABLE_NAME");
                out.println("<tr><td style='border:1px solid black'>TABLA " + contador + " : " + nombreTabla + "</td></tr>");
                arrayTablas.add(nombreTabla);
            }         
            rs1.close();//cerramos
            out.println("</table>");
            
            
            out.println("DATOS DE LAS TABLAS<br>");
            
            for (int x = 0; x < arrayTablas.size(); x++) {
                //SHOW COLUMNS FROM facturamecanica
                consultaTABLAS = "SHOW COLUMNS FROM  " + arrayTablas.get(x) + ";";
                out.println("<b>" + arrayTablas.get(x) + "</b><br>");
                rs2 = stmt2.executeQuery(consultaTABLAS);
                out.println("<table style='border:1px solid black'>");
                out.println("<tr><th>Field</th><th>Type</th><th>Null</th><th>Key</th><th>Default</th><th>Extra</th></tr>");
                while (rs2.next()) {
                    field = rs2.getString("FIELD");
                    type = rs2.getString("TYPE");
                    nul = rs2.getString("NULL");
                    key = rs2.getString("KEY");
                    defaul = rs2.getString("DEFAULT");
                    extra = rs2.getString("EXTRA");
                    out.println("<tr><td>"+field+"</td><td>"+type+"</td><td>"+nul+"</td>"
                        + "<td>"+key+"</td><td>"+defaul+"</td><td>"+extra+"</td></tr>");
                    
                }
                out.println("</table>");
            }
            
            rs2.close();
            
            String consulta="";
            String posicionColumna="";
            int numeroColumnas = 0;
            int numeroFilas = 0;
            for (int x = 0; x < arrayTablas.size(); x++) {                
                rs3 = stmt2.executeQuery("SHOW COLUMNS FROM  " + arrayTablas.get(x) + ";");
                out.println("<b><caption>" + arrayTablas.get(x) + "</b><caption><br>");
                out.println("<table style='border:1px solid black'>");
                //1ºbucle para los campos
                out.println("<tr>");               
                while (rs3.next()) {
                    field = rs3.getString("FIELD");
                    numeroColumnas++;
                    out.println("<th>" +field+ "</th>"); // arrayTablas.get(x) nombre de la tabla
                }
                out.println("</tr>");//cierro la parte de campos
                //ejecuto la consulta correspondiente
                consulta ="SELECT *"  + " FROM " + arrayTablas.get(x) + ";";
                rs4 = stmt2.executeQuery(consulta);
                while (rs4.next()) {   //lee por filas 
                    out.println("<tr>"); 
                    for (int i = 1; i <= numeroColumnas; i++) {
                        posicionColumna = rs4.getString(i) + "";//pasar a string  
                        out.println("<td>" + posicionColumna + "</td>");
                        
                    }
                    numeroFilas++;
                    out.println("</tr>");
                }

                rs3.close();              
                out.println("</table>");
                out.println("<b>Numero Filas: </b>" + numeroFilas + "<br>");
                out.println("<b>Numero Columnas: </b>" + numeroColumnas + "<br>");
                numeroColumnas = 0;
                numeroFilas = 0;
                rs4.close();
                         
            }                     
            //////fin consulta
            out.println("<br></h1>FIN CONSULTA</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletInformacionModificacion.class.getName()).log(Level.SEVERE, null, ex);
            out.println("Controlador JDBC no encontrado: " + ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletInformacionModificacion.class.getName()).log(Level.SEVERE, null, ex);
            out.println("TU USUARIO O  CONTRASEÑA SON INCORRECTOS <br>");
            out.println("Excepcion capturada de SQL: " + ex.toString());
        } finally {

            try {
                out.close();
                con1.close();
                con2.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServletInformacionModificacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletInformacionModificacion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletInformacionModificacion at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    */
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