<%-- 
    Document   : OperacionesBaseDatos
    Created on : 26-feb-2017, 13:47:59
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Formulario operaciones base datos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            var baseDatosElegida = localStorage.getItem("baseDatos");
            
            
            window.onload = function mostrarBaseDatos() {
                document.getElementById("baseDatosElegidaSelect").innerHTML = baseDatosElegida;
                document.getElementById("enviar").href = "./SeleccionarTablaJSP1.jsp?db=" + baseDatosElegida;
            }
                
            function seleccionarConsulta1(envio) {
                //COGEMOS EL DIV DONDE PONDREMOS LOS DATOS
                var consultaHTML = document.getElementById('formularioOperaciones');

                if (envio === "IMPORT") { //agregar
                    consultaHTML.innerHTML =
                            "<p><input type='hidden' name='envio' value='IMPORT'></p>" +
                            "<p><input type='hidden' name='BASE' value='" + baseDatosElegida + "'></p>";
                } else if (envio === "EXPORT") {//editar
                    consultaHTML.innerHTML =
                            "<p><input type='hidden' name='envio' value='EXPORT'></p>" +
                            "<p><input type='hidden' name='BASE' value='" + baseDatosElegida + "'></p>";
                } 
            }
            
            function seleccionarConsulta2(envio) {
                //COGEMOS EL DIV DONDE PONDREMOS LOS DATOS
                var consultaHTML = document.getElementById('formularioModificacion');

                if (envio === "MODIFICACIONES") { //agregar
                    consultaHTML.innerHTML =
                            "<p><input type='hidden' name='envio' value='MODIFICACIONES'></p>" +
                            "<p><input type='hidden' name='BASE' value='" + baseDatosElegida + "'></p>";
                   
                } 
            }

        </script>
    </head>
    <body>
        <form method="post" id="formu" action="./ServletOperacionesBD">
            <table>
                <tr>
                    <td>Introduce tu usuario</td>
                    <td><input type="text" value="root" name="nombreUsuario" size="25"></td>
                </tr>
                <tr>
                    <td>Introduce tu contraseña</td>
                    <td><input type="password" name="password" size="10" maxlength="9"></td>
                </tr>
                <tr>
                    <td>Base datos elegida</td>
                    <td><div id="baseDatosElegidaSelect"></div></td>
                </tr>
                <tr>
                    <td>     
                        <h1>Introduce acción si quieres exportar o importar</h1><br><br><br><br><br>
                    </td>
                    <td>
                        <select onchange="seleccionarConsulta1(this.value)">
                            <option value="">--Elije una opción--</option>
                            <option value="IMPORT">Importar</option>
                            <option value="EXPORT">Exportar</option>
                        </select>
                    </td>
                    <td>
                        <div id="formularioOperaciones"></div>
                    </td>    
                </tr>
            </table>
            <input type="submit" formaction="./elegirBaseDatosExportar1.jsp" value="Voler a elegir base datos">
            <input type="submit" formaction="./index.jsp" value="Inicio">
            <input type="reset" value="Borrar">
            <input type="submit" value="Enviar">
        </form>
            <table>
                    <td>Base datos elegida</td>
                    <td><div id="baseDatosElegidaSelect"></div></td>
                </tr>
                <tr>
                    <td>     
                        <h1>Introduce acción si quieres modificar</h1><br><br><br><br><br>
                    </td>
                    <td>
                        <select onchange="seleccionarConsulta2(this.value)">
                            <option value="">--Elije una opción--</option>
                            <option value="MODIFICACIONES">MODIFICACIONES</option>
                        </select>
                    </td>
                    <td>
                        <div id="formularioOperaciones"></div>
                    </td>    
                </tr>
            </table>
            <a id="enviar" href=""><button>Enviar</button></a>
    </body>
</html>
