/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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
public class ServletModificarFilaTabla extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Integer.parseInt(str);
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

        //parametros pasados
        String columna = request.getParameter("columna");
        String tabla = request.getParameter("tabla");
        response.setContentType("text/html");

        try {
            /* TODO output your page here. You may use following sample code. */

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletBorrar</title>");
            out.println("</head>");
            out.println("<body>");
            Statement stmt = ConexionBD.getConexion().createStatement();
            if (isNumeric(request.getParameter("id"))) {
                consultaTABLAS = "SELECT * from " + tabla + " where " + columna + "=" + Integer.parseInt(request.getParameter("id")) + ";";
            } else {
                consultaTABLAS = "SELECT * from " + tabla + " where " + columna + "= '" + request.getParameter("id") + "';";
            }
            ResultSet rs = stmt.executeQuery(consultaTABLAS);
            ResultSetMetaData rs2 = rs.getMetaData();
            int columnas = rs2.getColumnCount();//numero de columnas

            out.println("<form method='post' action='./ServletModificarFilaTabla2'>");
            out.println("<h2>Tabla " + tabla + "</h2>");
            out.println("<p><table border ='1px solid black'><tr>");
            for (int i = 1; i < columnas + 1; i++) {
                out.println("<th><b>" + rs2.getColumnName(i) + "</b></th>");
            }
            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i < columnas + 1; i++) {
                     out.println("<td><i><input type='text'  id='" + rs2.getColumnName(i) + "' name='" + rs2.getColumnName(i) + "' value='" + rs.getString(i) + "'></i></td>");
                    
                }
                out.println("</tr>");
                out.println("<input type='hidden' name='clave' id='clave' value='" + rs.getString(1) + "'>");
            }
            out.println("</table>");
            out.println("<input type='hidden' name='table' id='table' value='" + tabla + "'>");
            out.println("<p><input type='submit' value='Enviar'><input type='reset' value='Borrar'></p></form>");
            out.println("<a href='./EligeOpcion.html'><button>Volver</button></a>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletModificarFilaTabla.class.getName()).log(Level.SEVERE, null, ex);
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
