/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class ServletOperacionesBD extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String BASEdatos = request.getParameter("BASE");

        //parametros capturados
        String usu = request.getParameter("nombreUsuario");
        String pas = request.getParameter("password");
        String tipoOperacion = request.getParameter("envio");
        out.println("<html>");
        out.println("<head><title>Datos introducidos por Formulario</title></head");
        out.println("<body>");
        out.println("<table border)\"5\">");
        out.println("<tr><td><b>USUARIO:</b></td><td><i>" + usu + "</i></td></tr>");
        out.println("</table><p>");
        out.println("<h1>TU CONSULTA</h1>");
        if("EXPORT".equals(tipoOperacion)) {            
            out.println("<h2>BASE DATOS ELEGIDA: " + BASEdatos + "</h2>");
            out.println("<form method='post' id='formulario' action='./ExportarBaseDatos'>");
            out.println("<p><input type='submit' value='Confirmar exportacion'></p>");
            out.println("<p><input type='hidden' name='usuario' id='usuario' value='" + usu + "'></p>");
            out.println("<p><input type='hidden' name='BASE' id='BASE' value='" + BASEdatos + "'></p>");
            out.println("</form>");
            
        }else if("IMPORT".equals(tipoOperacion)) {            
            out.println("<form enctype='multipart/form-data' method='post' action='ImportarBaseDatos'>");
            out.println("<p><input type='hidden' name='usuario' id='usuario' value='" + usu + "'></p>");
            out.println("Escoge una base de datos <input type='file' name='fichero'/></br>"); 
            out.println("<input type='submit' value='Importar'/>"); 
            out.println("</form>"); 
            
        }
        out.println("<br></h1>FIN CONSULTA</h1>");
        out.println("<br><a href='./index.jsp'>Volver atrás</a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
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
