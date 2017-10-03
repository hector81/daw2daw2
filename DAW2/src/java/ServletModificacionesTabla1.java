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
import java.sql.ResultSetMetaData;
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
public class ServletModificacionesTabla1 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            Logger.getLogger(ServletModificacionesTabla1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void consultarTablaID(String tabla,PrintWriter out,Connection con1, ResultSet rs1, ResultSet rs2,Statement stmt1,
            Statement stmt2, int id) throws SQLException {
        String field="";
        String consultaTABLAS = "SHOW COLUMNS FROM  " + tabla + ";";
        try {
            stmt2 = con1.prepareStatement(consultaTABLAS);
        } catch (SQLException ex) {
            Logger.getLogger(ServletModificacionesTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
        rs2 = stmt2.executeQuery(consultaTABLAS);
        ////////////////////
        String posicionColumna = "";
        int numeroColumnas = 0;
        int numeroFilas = 0;

        out.println("<b><caption>" + tabla + "</b><caption><br>");
        out.println("<table style='border:1px solid black'>");
        //1ºbucle para los campos
        out.println("<tr>");
        while (rs2.next()) {
            field = rs2.getString("FIELD");
            numeroColumnas++;
            out.println("<th>" + field + "</th>"); // arrayTablas.get(x) nombre de la tabla
        }
        out.println("</tr>");//cierro la parte de campos
        //ejecuto la consulta correspondiente
        consultaTABLAS = "SELECT *" + " FROM " + tabla + ";";
        rs2 = stmt2.executeQuery(consultaTABLAS);
        while (rs2.next()) {   //lee por filas 
            out.println("<tr>");
            for (int i = 1; i <= numeroColumnas; i++) {
                posicionColumna = rs2.getString(i) + "";//pasar a string  
                out.println("<td>" + posicionColumna + "</td>");

            }
            out.println("</tr>");
        }
        rs2.close();
        out.println("</table>");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //variable globales
        String nombreTabla, field, type, nul, key, defaul, extra, nombre, marca = "";
        int id=0;
        int importe =0;
        String consultaTABLAS = "";
        String tabla = request.getParameter("table");
        String base = request.getParameter("base");
        PrintWriter out = response.getWriter();

        //parametros capturados
        String usu = request.getParameter("nombreUsuario");
        String pas = request.getParameter("password");
        String tipoConsulta = request.getParameter("envio");
        
        //parametros recibidos de formulario
        //id = Integer.parseInt(request.getParameter("id"));
        nombre = request.getParameter("nombre");
        marca = request.getParameter("marca");
        //importe = Integer.parseInt(request.getParameter("importe"));
        
        
        String aux = request.getParameter("id");
        String aux2 = request.getParameter("importe");

        if (aux != null) {
            id = Integer.parseInt(request.getParameter("id"));
        }

        if (aux2 != null) {
            importe = Integer.parseInt(request.getParameter("importe"));
            nombre = request.getParameter("nombre");
            marca = request.getParameter("marca");
        }
      
        //Asignacion a la respiesta html que va a generarse
        response.setContentType("text/html");

        //Definir el driver y el url de la conexion a la BD teniendo en cuenta el SGBD empleado
        String driver = "com.mysql.jdbc.Driver";

        ///crear 2 conexiones para la base de datos principal y otra para la base datos que usamos
        String url = "jdbc:mysql://127.0.0.1:3306/" + base + "";

        Connection con1 = null;
        try {
            //Carga del driver
            Class.forName(driver);

            //Establecimiento de la conexion. El segundo y tercer argumento son el user
            //y password, por defecto, necesarios para conectarnos como administradores a la BD
            con1 = DriverManager.getConnection(url, usu, pas);

            out.println("<html>");
            out.println("<head><title>Datos introducidos por Formulario</title></head");
            out.println("<body>");
            out.println("<table border)\"5\">");
            out.println("<tr><td><b>USUARIO:</b></td><td><i>" + usu + "</i></td></tr>");
            out.println("<tr><td><b>USUARIO:</b></td><td><i>" + base + "</i></td></tr>");
            out.println("</table><p>");
            out.println("<h1>TU CONSULTA</h1>");

            //Ejecucion de la sentencia SQL y obtencion de resultados en un objeto ResultSet
            Statement stmt1 = null;
            Statement stmt2 = null;
            
            ResultSet rs1 = null;
            ResultSet rs2 = null;

            //para insertar tablas
            if("INSERT".equals(tipoConsulta)) {
                consultaTABLAS = "SELECT * from " + tabla;
                rs1 = con1.createStatement().executeQuery(consultaTABLAS);
                ResultSetMetaData rs22 = rs1.getMetaData();
                int columnas = rs22.getColumnCount();
                String nombreColumna[] = new String[columnas];
                String parametro ="";
                out.println("<h2>Tabla " + tabla + "</h2>");
                out.println("<form method='post' id='formulario' action='./ServletInsertarDatos'>");
                out.println("<table border='1px solid black'>");
                for (int i = 2; i < columnas + 1; i++) {
                    parametro =rs22.getColumnName(i);
                    out.println("<tr><td>" + parametro + " : <input type='text'  values=''" + 
                            "' id='nombre' name='" + parametro + "'></td></tr><br>");
                }

                out.println("</table>");
                out.println("<p><input type='reset' value='Borrar'></p>");
                out.println("<p><input type='submit' value='Enviar'></p>");
                out.println("<p>TABLA<input type='text' name='tabla' id='tabla' value='" + tabla + "'></p>");
                out.println("<p><input type='hidden' name='base' value='" + base + "'></p>");
                out.println("</form>");
              
            }else if("ADD".equals(tipoConsulta)) {

                consultaTABLAS = "SELECT * from " + tabla;
                rs1 = con1.createStatement().executeQuery(consultaTABLAS);
                ResultSetMetaData rs22 = rs1.getMetaData();
                int columnas = rs22.getColumnCount();
                out.println("<h2>Tabla " + tabla + "</h2>");
                out.println("<table border='1px solid black'>");
                for (int i = 1; i < columnas + 1; i++) {
                    out.println("<th><b>" + rs22.getColumnName(i) + "</b></th>");
                }
                out.println("<th><b>Modificar</b></th>");
                while (rs1.next()) {
                    out.println("<tr>");
                    for (int i = 1; i < columnas + 1; i++) {
                        out.println("<td>" + rs1.getString(i) + "</td>");
                    }
                    out.println("<td> <a href='./ServletModificarFilaTabla?id=" + rs1.getString(1) + "&columna=" + rs22.getColumnName(1) + "&tabla=" + tabla + "'><button>X</button></a></td></tr>");
                }
                out.println("</table>");
                out.println("<p><input type='hidden' name='base' value='" + base + "'></p>");
                //borrar tabla
            }else if("DROP".equals(tipoConsulta)) {
                consultaTABLAS = "SELECT * from " + tabla;
                rs1 = con1.createStatement().executeQuery(consultaTABLAS);
                ResultSetMetaData rs22 = rs1.getMetaData();
                //sacamos el numero de columnas
                int columnas = rs22.getColumnCount();
                out.println("<h2>Tabla " + tabla + " </h2>");
                out.println("<table border='1px solid black'>");
                for (int i = 1; i < columnas + 1; i++) {
                    out.println("<th><b>" + rs22.getColumnName(i) + "</b></th>");
                }
                out.println("<th><b>Borrar</b></th>");
                while (rs1.next()) {
                    out.println("<tr>");
                    for (int i = 1; i < columnas + 1; i++) {
                        out.println("<td>" + rs1.getString(i) + "</td>");
                    }
                    out.println("<td> <a href='./ServletBorrarDatosTabla1?id=" + rs1.getString(1) + "&columna=" + rs22.getColumnName(1) + "&tabla=" + tabla + "'><button>X</button></a></td></tr>");
                }
                out.println("<p><input type='hidden' name='base' value='" + base + "'></p>");
                out.println("</table>");
            }else if("SELECT".equals(tipoConsulta)) {
                //llamamos a la funcion mostrar consultarTablaID   
                consultarTablaID(tabla,out,con1, rs1, rs2,stmt1, stmt2,id);
                //////////////////////////////////////////////////////
            }else if("SELECT_ALL".equals(tipoConsulta)) {
                //llamamos a la funcion mostrar tabla                
                mostrarTabla(tabla,out,con1, rs1, rs2,stmt1, stmt2);
            } 

            //////fin consulta
            out.println("<br></h1>FIN CONSULTA</h1>");
            out.println("<br><a href='./ModificarTabla1.html'>Volver atrás</a>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletModificacionesTabla1.class.getName()).log(Level.SEVERE, null, ex);
            out.println("Controlador JDBC no encontrado: " + ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletModificacionesTabla1.class.getName()).log(Level.SEVERE, null, ex);
            out.println("TU USUARIO O  CONTRASEÑA SON INCORRECTOS <br>");
            out.println("Excepcion capturada de SQL: " + ex.toString());
        } finally {

            try {
                out.close();
                con1.close();
            } catch (SQLException ex) {
                Logger.getLogger(ServletModificacionesTabla1.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet ServletModificacionesTabla</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletModificacionesTabla at " + request.getContextPath() + "</h1>");
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
