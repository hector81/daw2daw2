/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vespertino
 */
public class SegundoServlet extends HttpServlet {
    /*
    //Si se emplea para inicializar, el init(Servlet config)
    public void init(ServletConfig config) throws ServletException {
        //si no se hace esta llamada, error al ejecutar el servlet
       //super.init(config);
        System.out.println("Usuario: " + config.getInitParameter("user"));
        System.out.println("Password: " + config.getInitParameter("password"));
        Date ahora = new Date();
        log("SegundoServlet inicializado el " + ahora);
        System.out.println("Mensaje lanzado desde el init");
    }
    */
    
    @Override
    public void init() throws ServletException {
        //Los parametros capturados se escriben en tomcat_home\log\stdu.txt
        System.out.println("Usuario: " + getServletConfig().getInitParameter("user"));
        System.out.println("Password: " + getServletConfig().getInitParameter("password"));
        Date ahora = new Date();
        log("SegundoServlet inicializado el " + ahora);
        System.out.println("Mensaje lanzado desde el init");
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //captura de parametro
        String nombre = request.getParameter("nombre");

        //asignacion a la respuesta html que va a generarse, del tipo MIME
        response.setContentType("text/html");

        //obtencion del objeto que escribe la respuesta. Pertenece a java.io
        PrintWriter out = response.getWriter();

        //generacion de la respuesta html
        out.println("<html>");
        out.println("<head><title>Segundo servlet</title></head>");
        out.println("<body>");
        out.println("Ahora se utilizaria el user y password capturados durante la inicializacion, para conectar con una base de datos.");
        out.println("<br>");
        out.println("Se hará más adelante");
        out.println("</body>");
        out.println("</html>");
    }

    public void destroy() {
        Date d = new Date();
        log("SegundoServlet fuera de servicio el " + d);
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
            out.println("<title>Servlet SegundoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SegundoServlet at " + request.getContextPath() + "</h1>");
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
    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    */
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
