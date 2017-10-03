/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vespertino
 */
public class ServletFormulario1InformacionCliente extends HttpServlet {

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
		
		String nom = request.getParameter("nombre");
		String tef = request.getParameter("telefono");
		String lugNaci = request.getParameter("lugar de nacimiento");
                

                
                //POROCOLO scheme de la peticion
                String scheme = request.getScheme();
                //ruta uri del servlet
                String uri = request.getServletPath();
                /// IP del cliente
                String ip = request.getRemoteAddr();
                // Host del cliente
                String host = request.getRemoteHost();
                //Devuelve el nombre del método HTTP con el que se realizó esta solicitud, por ejemplo, GET, POST o PUT.
                String metodo = request.getMethod();            
                //Devuelve la parte de la URL de esta solicitud desde el nombre del protocolo hasta la cadena de consulta en la primera línea de la solicitud HTTP.
                String uriRequest = request.getRequestURI();
        	//Reconstruye la URL que el cliente utilizó para realizar la solicitud.
                StringBuffer urlRequest = request.getRequestURL();
                //nombre del servidor
                String serverName = request.getServerName();
                //tipo param
                String mime = request.getContentType();
                //tipo myme
                String param = request.getQueryString();
                //nombre del puerto escucha Servidor acepta la conexión del cliente  getRemotePort
                String puertoESCUCHA = request.getRemoteHost();
                //clave usuario
                String clave=request.getParameter("telefono");
                
                
                
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Datos introducidos por Formulario cliente</title></head");
		out.println("<body>");
		out.println("<h1>DATOS INTRODUCIDOS</h1>");
		out.println("<table border)\"5\">");
		out.println("<tr><td><b>NOMBRE:</b></td><td><i>" + nom + "</i></td></tr>");
		out.println("<tr><td><b>TELÉFONO:</b></td><td><i>" + tef + "</i></td></tr>");
		out.println("<tr><td><b>LUGAR NACIMIENTO:</b></td><td><i>" + lugNaci + "</i></td></tr>");
		out.println("</table><p>");
                out.println("<h1>Información sobre la petición</h1>");
                
                out.println("<p>Protocolo de la petición: " + scheme + "</p>");
                out.println("<p>Nombre del cliente: " + host + "</p>");
                out.println("<p>Dirección ip del cliente: " + ip + "</p>");
                out.println("<p>Clave del usuario que realiza la petición: " + clave + "</p>");
                out.println("<p>Tipo de petición: " + metodo + "</p>");
                out.println("<p>Tipo MIME usado por el cliente: " + mime + "</p>");
                out.println("<p>Cadena de parámetros de la petición: " + param + "</p>");
                out.println("<p>URI de la petición: " + uriRequest + "</p>");
                out.println("<p>URL de la petición: " + urlRequest + "</p>");
                out.println("<p>Ruta URI del servlet: " + uri + "</p>");
                out.println("<p>Nombre del servidor web que recibe la petición: " + serverName + "</p>");
                out.println("<p>Nombre del puerto por el que el servidor acepta la conexión del cliente: " + puertoESCUCHA + "</p>");

                out.println("<h1>Encabezados de la petición: </h1>" + request.getHeaderNames());
                out.println("<p>Encabezados o headers asociados a la petición:</p>");
                
                out.println("<table style=width:'600px'>");
                Enumeration cabeceras = request.getHeaderNames();
                while (cabeceras.hasMoreElements()) {
                    String nombreCabecera = (String) cabeceras.nextElement();            
                    out.println("<tr><td>" + nombreCabecera 
                    + "</td><td>" + request.getHeader(nombreCabecera) + "</td></tr>");
                }
                out.println("</table>");
		out.println("</body>");
		out.println("</html>");
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
            out.println("<title>Servlet ServletFormulario1InformacionCliente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletFormulario1InformacionCliente at " + request.getContextPath() + "</h1>");
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
