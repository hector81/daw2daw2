/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class ServletInsertarDatos extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String consultaTABLAS = "";
        String field = "";
        int numeroColumnas = 0;
        String columna = "";
        String valores = "";
        String valoresINSERTADOS = "";
        String tabla = request.getParameter("tabla");

//        String tabla = "facturamecanica";
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>INSERTAR FILA</title>");
            out.println("</head>");
            out.println("<body>");

            //cogemos el ultimo id despues de hacer la insercion
            String ultimoIntid = obtnerIdAnterior(tabla, out);
            /////////////////////////////              
            Statement stmt = ConexionBD.getConexion().createStatement();
            consultaTABLAS = "SHOW COLUMNS FROM  " + tabla + ";";
            out.println("<b>" + tabla + "</b><br>");
            ResultSet rs1 = stmt.executeQuery(consultaTABLAS);

            //detectar auto_increment
            boolean incremento = false;
            //esto lo usamos para capturar las columnas y 
            while (rs1.next()) {
                field = rs1.getString("FIELD");
                valores += field + ",";
                columna = request.getParameter(field);
                numeroColumnas++;
                if (rs1.getString("EXTRA").equals("auto_increment")) {
                    incremento = true;
                }
            }
            valores = valores.substring(0, valores.length() - 1);
     

            String interrogaciones = "";
            for (int y = 1; y < numeroColumnas; y++) {
                interrogaciones += "?,";
            }
            interrogaciones = interrogaciones.substring(0, interrogaciones.length() - 1);
            if (incremento == false) {
                consultaTABLAS = "INSERT INTO " + tabla + "(" + valores + ")" + " VALUES(?," + interrogaciones + ")";
            } else {
                consultaTABLAS = "INSERT INTO " + tabla + "(" + valores + ")" + " VALUES(?," + interrogaciones + ")";
            }

            out.println(consultaTABLAS);
            PreparedStatement stmt3 = ConexionBD.getConexion().prepareStatement(consultaTABLAS);
            consultaTABLAS = "SHOW COLUMNS FROM  " + tabla + ";";
            ResultSet rs2 = stmt.executeQuery(consultaTABLAS);
            //esta parte la hacemos para ingresar los datos con el metodo set
            int z = 0;
            int r = 0;
            
            int pasarStringaInt = 0;
            while (rs2.next()) {
                int inter = Integer.parseInt(ultimoIntid) + 1;
                if (incremento == false) {
                    stmt3.setInt(1, inter);
                    z++;         
                    field = rs2.getString("FIELD");
                    columna = request.getParameter(field);
                    out.println("<p> " + z + "</p>");
                    out.println("<p> " + columna + "</p>");
                    out.println("<p> " + "----------" + "</p>");
                    if (comprobarSiEsNumero(columna)) {
                        
                        pasarStringaInt = Integer.parseInt(columna);
                        stmt3.setInt(z, pasarStringaInt);
                    }
                    if (!comprobarSiEsNumero(columna)) {
                        stmt3.setString(z, columna);
                    }
                } else {
                    z++;
                    field = rs2.getString("FIELD");
                    columna = request.getParameter(field);
                    if (comprobarSiEsNumero(columna)) {
                        pasarStringaInt = Integer.parseInt(columna);
                        stmt3.setInt(z, pasarStringaInt);
                    }
                    if (!comprobarSiEsNumero(columna)) {
                        stmt3.setString(z, columna);
                    }
                }
            }
            stmt3.executeUpdate();
            stmt3.close();

            out.println("<p>INSERCIÓN REALIZADA.</p><br>");
            //llamamos a la funcion mostrar tabla

            out.println("<h2>Tabla " + tabla + " Datos Actualizados</h2>");

            out.println("<a href='./ModificarTabla.html'><button>Volver</button></a>");

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletBorrarDatosTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    public String obtnerIdAnterior(String tabla, PrintWriter out) {
        String field = "";
        String consultaTABLAS = "";
        Statement stmt4 = null;
        ResultSet rs4 = null;
        int numeroColumnas = 0;
        String posicionColumna = "";
        String ultimoIntid = "";

        try {
            stmt4 = ConexionBD.getConexion().createStatement();
            rs4 = stmt4.executeQuery("SHOW COLUMNS FROM  " + tabla + ";");
            //1ºbucle para los campos              
            while (rs4.next()) {
                field = rs4.getString("FIELD");
                numeroColumnas++;
                //convertimos field a mayusculas
                field = field.toUpperCase();
            }//cierro la parte de campos
            //ejecuto la consulta correspondiente
            consultaTABLAS = "SELECT *" + " FROM " + tabla + ";";
            rs4 = stmt4.executeQuery(consultaTABLAS);
            while (rs4.next()) {   //lee por filas 
                for (int i = 1; i <= numeroColumnas; i++) {
                    if (i == 1) {
                        posicionColumna = rs4.getString(i) + "";
                        ultimoIntid = posicionColumna;
                    }
                    posicionColumna = rs4.getString(i) + "";//pasar a string                   
                }
            }
            rs4.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServletInsertarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ultimoIntid;
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
