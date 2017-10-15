<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>B�squeda Principal</title>
        <link href="../Resources/css/screen.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>        
        
        <script>
        $(document).ready(function() {
           $("#datepickerinicial").datepicker({
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                yearRange: "-3000:+0"
           });
         });
         
        $(document).ready(function() {
           $("#datepickerfinal").datepicker({
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                yearRange: "-3000:+0"
           });
         });
        </script>
    </head>
    <body style="background-color: darkslategray"><BR><BR><BR>    
        <form name="form_principal" action="../servlet" method="post">
            
            <h1 style="color: black" align="center">Realizar b�squeda en Wikipedia</h1><BR><BR><BR>
            <a align="center">
            
                <h2 style="color: black">Que desea buscar?</h2><BR><BR>
                
                <p align="center">
                <label style="color: black">Hecho hist�rico =</label>
                <input type="text" name="historia" placeholder="Ejemplo: Segunda Guerra Mundial" size="33"/><BR><BR>
                
                <label style="color: black">Lugar =</label>
                <input type="text" name="lugar" placeholder="Ejemplo: Stalingrado" size="41"/><BR><BR>

                <label style="color: black">Personaje =</label>
                <input type="text" name="personaje" placeholder="Ejemplo: Friedrich Paulus" size="38"/><BR><BR>


                <label style="color: black"> Fecha Inicial = </label>
                <input type="text" name="datepickerinicial" id="datepickerinicial" readonly="readonly" size="12" />


                <label style="color: black"> Fecha Final = </label>
                <input type="text" name="datepickerfinal" id="datepickerfinal" readonly="readonly" size="12" /><BR><BR><BR>
                </p>

                <p class="BtnIniciar">
                    <input type="submit" value="Buscar" name="btn_buscar"/>
                </p>
            
            </a>
            <BR><BR>
            <p class="linkVolver" align="center">                
            <a href="../index.jsp" style="font-size: 15pt; font-family: Comic Sans MS; color: white; align-items: center">Inicio</a>
            
            <%
            String hist;
            hist = request.getParameter("historia");
            %>
            
        </form>
    </body>
</html>
