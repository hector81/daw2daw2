<%-- 
    Document   : SeleccionarTablaJSP1
    Created on : 26-feb-2017, 13:49:34
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
        <title>JSP Select Table</title>
        <%
        String base=" ";
        if(base.equals(" ")){
             base = request.getParameter("db");
        }
        %>
        <script>
            function capturarResultadoSelectTabla(tabla) {
                localStorage.setItem("tabla", tabla);
            }
            function capturarResultadoSelectBase(baseDATOS) {
                localStorage.setItem("baseDATOS", baseDATOS);//enviamos
            }
            window.onload = function inicio() {
                tablaSeleccionar = document.getElementById("tabla");
                //Elegimos la tabla
                tablaElegida = tablaSeleccionar.options[tablaSeleccionar.selectedIndex].value;
                localStorage.setItem("tabla", tablaElegida);//enviamos
                baseSeleccionar = document.getElementById("baseDATOS");
                //Elegimos la tabla
                baseElegida = baseSeleccionar.options[baseSeleccionar.selectedIndex].value;
                localStorage.setItem("baseDATOS", baseElegida);//enviamos
            }
        </script>
    </head>
    <body>
        
        <p>Elige tabla de <%=base%></p>
        <form method="post" action="ModificarTabla1.html">
            <select id="baseDATOS" onchange="capturarResultadoSelectBase(this.value)">
                <option value="<%=base%>"><%=base%><option>  
            </select>
            <select id="tabla" onchange="capturarResultadoSelectTabla(this.value)">
                <%
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://127.0.0.1:3306";
                        String username = "root";
                        Connection conn = DriverManager.getConnection(url, username, "");
                        String query = "SELECT table_name FROM information_schema.tables where table_schema='" + base + "'";
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                %>
                <option value="<%=rs.getString("table_name")%>"><%=rs.getString("table_name")%><option>        
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
