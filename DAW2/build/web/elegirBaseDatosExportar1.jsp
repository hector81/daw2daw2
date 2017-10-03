<%-- 
    Document   : elegirBaseDatosExportar1
    Created on : 26-feb-2017, 13:50:52
    Author     : user
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exportar Base datos</title>
        <script>
            function capturarBaseDatosSelect(baseDatos) {
                localStorage.setItem("baseDatos", baseDatos);
            }
            window.onload = function inicio() {
               baseDatosTablaSeleccionar = document.getElementById("baseDatos");
                //Elegimos la tabla
                baseDatosElegida = baseDatosTablaSeleccionar.options[baseDatosTablaSeleccionar.selectedIndex].value;
                localStorage.setItem("baseDatos", baseDatosElegida);//enviamos
            }
        </script>
    </head>
    <body>
        <h1>Seleciona la Base de datos</h1>
        <form method="post" action="OperacionesBaseDatos.jsp">
        <select id="baseDatos" onchange="capturarBaseDatosSelect(this.value)">
            <%
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://127.0.0.1:3306";
                    String username = "root";
                    Connection conn = DriverManager.getConnection(url, username, "");
                    String consultaTabla = "SELECT schema_name FROM information_schema.schemata";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(consultaTabla);
                    while (rs.next()) {
            %>
            <option value="<%=rs.getString("schema_name")%>"><%=rs.getString("schema_name")%></option>        
            <%
                }
            %>
        </select>
        <input type="submit" value="Enviar">
        <%
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </form>
</body>
</html>
