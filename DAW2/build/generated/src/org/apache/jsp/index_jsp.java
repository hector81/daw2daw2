package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Servlets realizados</h1>\n");
      out.write("        <p><a href=\"./PrimerServlet?nombre=Bernardo\">PrimerServlet</a></p>\n");
      out.write("        <p><a href=\"./SegundoServlet?nombre=Bernardo\">SegundoServlet</a></p>\n");
      out.write("        <p><a href=\"Formulario1.html\">Acceder a formulario</a></p>\n");
      out.write("        <p><a href=\"Formulario1Informacion.html\">Acceder a segundo</a></p>\n");
      out.write("        <p><a href=\"servletMysql.html\">Acceder a tablas</a></p>\n");
      out.write("        <p><a href=\"FormularioSelect.html\">Consulta personalizada</a></p>\n");
      out.write("        <p><a href=\"FormularioModificacionDatos.html\">CONSULTAS MODIFICACIÓN DE DATOS (DROP, CREATE , INSERT).Insertar, actualizar y eliminar datos </a></p>\n");
      out.write("        <p><a href=\"FormularioModifiInformacion.html\">Modificar y Informacion tabla </a></p>\n");
      out.write("        <p><a href=\"SeleccionarTablaJSP.jsp\">Modificaciones tabla</a></p><br>\n");
      out.write("        <p><a href=\"elegirBaseDatosExportar.jsp\">Exportar bases de datos</a></p><br>\n");
      out.write("        <p><a href=\"ElegirBaseDatos.jsp\">Modificaciones tabla</a></p><br>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
