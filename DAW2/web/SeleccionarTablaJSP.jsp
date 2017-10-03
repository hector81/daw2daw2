<%-- 
    Document   : SeleccionarTablaJSP
    Created on : 17-feb-2017, 23:02:25
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
        <script>
            function capturarResultadoSelect(tabla) {
                localStorage.setItem("tabla", tabla);
            }

            window.onload = function inicio() {
                tablaSeleccionar = document.getElementById("tabla");
                //Elegimos la tabla
                tablaElegida = tablaSeleccionar.options[tablaSeleccionar.selectedIndex].value;
                localStorage.setItem("tabla", tablaElegida);//enviamos
            }
        </script>
    </head>
    <body>
        <form method="post" action="ModificarTabla.html">
            <select id="tabla" onchange="capturarResultadoSelect(this.value)">
                <%
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://127.0.0.1:3306/tallermecanico";;
                        String username = "root";
                        Connection conn = DriverManager.getConnection(url, username, "");
                        String query = "SELECT table_name FROM information_schema.tables where table_schema='tallermecanico';";
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
