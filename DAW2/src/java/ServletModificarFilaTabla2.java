/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
public class ServletModificarFilaTabla2 extends HttpServlet {

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
        String sentenciaSQL = "";

        String tabla = request.getParameter("table");

        String consulta = "";

        response.setContentType("text/html");
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Modificar registro</title>");
            out.println("</head>");
            out.println("<body>");
            /* TODO output your page here. You may use following sample code. */
            sentenciaSQL = "SELECT * from " + tabla;
            ResultSet rs1 = ConexionBD.getConexion().createStatement().executeQuery(sentenciaSQL);
            ResultSetMetaData rs2 = rs1.getMetaData();
            int columnas = rs2.getColumnCount();//numero de columnas
            consulta += "UPDATE " + tabla + " SET ";
            for (int i = 1; i < columnas + 1; i++) {
                if (i < columnas) {
                    if (isNumeric(request.getParameter(rs2.getColumnName(i)))) {
                        consulta += "`" + rs2.getColumnName(i) + "`=" + Integer.parseInt(request.getParameter(rs2.getColumnName(i))) + ",";
                    } else {
                        consulta += "`" + rs2.getColumnName(i) + "`='" + request.getParameter(rs2.getColumnName(i)) + "' ,";
                    }
                } else {
                    if (isNumeric(request.getParameter(rs2.getColumnName(i)))) {
                        consulta += "`" + rs2.getColumnName(i) + "`=" + Integer.parseInt(request.getParameter(rs2.getColumnName(i))) + " ";
                    } else {
                        consulta += "`" + rs2.getColumnName(i) + "`='" + request.getParameter(rs2.getColumnName(i)) + "' ";
                    }
                }
            }
            if (isNumeric(request.getParameter("clave"))) {
                consulta += " WHERE `" + rs2.getColumnName(1) + "`=" + Integer.parseInt(request.getParameter("clave"));
            } else {
                consulta += " WHERE `" + rs2.getColumnName(1) + "`='" + request.getParameter("clave") + "';";
            }

            
            PreparedStatement stmt = ConexionBD.getConexion().prepareStatement(consulta);
            stmt.executeUpdate();
            out.println("<p>La se ha modificado correctamente.");

            out.println("<a href='./ModificarTabla.html'><button>Volver</button></a>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ServletModificarFilaTabla2.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
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
