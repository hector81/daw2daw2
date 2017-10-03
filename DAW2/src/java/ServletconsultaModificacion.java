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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vespertino
 */
public class ServletconsultaModificacion extends HttpServlet {

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
		
		String usu = request.getParameter("nombre");
		String pas = request.getParameter("password");
                String consultaCREATE = request.getParameter("consultaModificacionCREATE");
                String consultaDROP = request.getParameter("consultaModificacionDROP");
                String consultaINSERT = request.getParameter("consultaModificacionINSERT");
                
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
                
                out.println("<html>");
		out.println("<head><title>Datos introducidos por Formulario</title></head");
		out.println("<body>");
		out.println("<h1>DATOS INTRODUCIDOS</h1>");
		out.println("<table border)\"5\">");
		out.println("<tr><td><b>USUARIO:</b></td><td><i>" + usu + "</i></td></tr>");
		//out.println("<tr><td><b>CONTRASEÑA:</b></td><td><i>" + pas + "</i></td></tr>");
		out.println("</table><p>");
		
                
                out.println("<h1>TU CONSULTA</h1>");
                
                //Definir el driver y el url de la conexion a la BD teniendo en cuenta el SGBD empleado
                String driver="com.mysql.jdbc.Driver";
                String url="jdbc:mysql://127.0.0.1:3306/tallermecanico";
                Connection con=null;
                try{
                    //Carga del driver
                    Class.forName(driver);

                    //Establecimiento de la conexion. El segundo y tercer argumento son el user
                    //y password, por defecto, necesarios para conectarnos como administradores a la BD
                    con=DriverManager.getConnection(url,usu,pas);

                    //Obtencion de un objeto Statement para ejecutar sentencias SQL
                    Statement stmt=con.createStatement();

                    //Ejecucion de la sentencia SQL y obtencion de resultados en un objeto ResultSet  
                    if(consultaCREATE != ""){
                        stmt.executeUpdate(consultaCREATE);
                        out.println("CREADA TABLA");
                    }
                    
                    
                    if(consultaDROP != ""){
                        stmt.executeUpdate(consultaDROP);
                        out.println("BORRADA TABLA");
                    }
                    
                    if(consultaINSERT != ""){
                        stmt.executeUpdate(consultaINSERT);
                        out.println("INSERTADOS DATOS TABLA");
                    }
                    
                }catch(ClassNotFoundException e){
                    out.println("Controlador JDBC no encontrado: "+e.toString());
                }catch(SQLException e){
                    out.println("ALGO HA FALLADO EN LA INSERCION <br>");
                    out.println("Excepcion capturada de SQL: "+e.toString());
                //Cierre de la conexion
                }finally{
                    try{
                        if(con!=null){
                            con.close();
                        }
                    }catch(SQLException e){
                        out.println("No se puede cerrar la conexion: "+e.toString());
                    }
                }              
                out.println("<br></h1>FIN CONSULTA</h1>");
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
            out.println("<title>Servlet ServletconsultaModificacion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletconsultaModificacion at " + request.getContextPath() + "</h1>");
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
