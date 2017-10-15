package uniandes.grupo5.servlet;

import com.jcraft.jsch.*;
import java.io.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uniandes.grupo5.ssh.SSHConector;

/**
 * Servlet implementation class miservlet
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    String hecho;
    String lugar;
    String perso;
    String fecha_inicial;
    String fecha_final;
    
    private static final String USERNAME = "bigdata05";
    private static final String HOST = "bigdata-cluster1-01.virtual.uniandes.edu.co";
    private static final int PORT = 22;
    private static final String PASSWORD = "5b616d23a2a2130bbbcf7f47bf8b8ee5";
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
        try{
            SSHConector sshConector = new SSHConector();             
             
            sshConector.connect(USERNAME, PASSWORD, HOST, PORT);
            
            
            hecho = request.getParameter("historia");
            if (hecho.trim().length() == 0){
                hecho = "No registrado";
            };
            
            lugar = request.getParameter("lugar");
            if (lugar.trim().length() == 0){
                lugar = "No registrado";
            };
             
            perso = request.getParameter("personaje");
            if (perso.trim().length() == 0){
                perso = "No registrado";
            };
            
            fecha_inicial = request.getParameter("datepickerinicial");
            if (fecha_inicial.trim().length() == 0){
                fecha_inicial = "No registrado";
            };
            
            fecha_final = request.getParameter("datepickerfinal");
            if (fecha_final.trim().length() == 0){
                fecha_final = "No registrado";
            };
            
            //String result = sshConector.executeCommand("hadoop jar prueba_taller2.jar " + num1 + " " + num2);
            sshConector.executeCommand("hadoop fs -rm -r -f  salida_taller2");
            String result = 
                    sshConector.executeCommand("hadoop jar Wikibz.jar uniandes.reuters.job.XML_Job datos_wiki_test_comp salida_taller2 " + hecho.replace(" ", "_") + " " + lugar.replace(" ", "_") + " " + perso.replace(" ", "_") + " " + fecha_inicial.replace(" ", "_") + " " + fecha_final.replace(" ", "_"));                        
            
            System.out.println("hadoop jar Wiki.jar uniandes.reuters.job.XML_Job datos_wiki_test salida_taller2 " + hecho.replace(" ", "_") + " " + lugar.replace(" ", "_") + " " + perso.replace(" ", "_") + " " + fecha_inicial.replace(" ", "_") + " " + fecha_final.replace(" ", "_"));
            
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
                out.println("<head>");
                    out.println("<title>Respuesta Servlet</title>");
                    out.println("<link href=\"Resources/css/screen.css\" rel=\"stylesheet\" type=\"text/css\"/>");                                    
                    out.println("<style>\n" +
                                ".links line {\n" +
                                "stroke: #999;\n" +
                                "stroke-opacity: 0.5;\n" +
                                "}\n\n" +

                              ".nodes circle {\n" +
                                "stroke: #fff;\n" +
                                "stroke-width: 1.5px;\n" +
                              "}\n" +
                                "</style>");
                out.println("</head>");
                out.println("<body style=\"background-color: darkslategray\"> <BR><BR><BR>");                    
                    out.println("<h1 style=\"color: black\" align=\"center\">PARAMETROS DE LA BÚSQUEDA</h1><BR><BR>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Hecho historico = </b>" + hecho.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Lugar = </b>" + lugar.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Personaje = </b>" + perso.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Fecha inicial = </b>" + fecha_inicial.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Fecha final = </b>" + fecha_final.replace("_", " ")  + "</h2><BR><BR>");                    
                    //out.println("<h2 style=\"color: black\">El resultado del jar es..." + result + "</h2>");
                    out.println("<svg width=\"1400\" height=\"600\"></svg>");
                    out.println("<script src=\"https://d3js.org/d3.v4.min.js\"></script>\n" +
                                "<script>\n" +
                                "\n" +
                                "var svg = d3.select(\"svg\"),\n" +
                                "    width = +svg.attr(\"width\"),\n" +
                                "    height = +svg.attr(\"height\");\n" +
                                "\n" +
                                "var color = d3.scaleOrdinal(d3.schemeCategory20);\n" +
                                "\n" +
                                "var simulation = d3.forceSimulation()\n" +
                                "    .force(\"link\", d3.forceLink().id(function(d) { return d.id; }))\n" +
                                "    .force(\"charge\", d3.forceManyBody())\n" +
                                "    .force(\"center\", d3.forceCenter(width / 2, height / 2));\n" +
                                "\n" +
                                "d3.json(\"DatosGrafo/flare.json\", function(error, graph) {\n" +
                                "  if (error) throw error;\n" +
                                "\n" +
                                "  var link = svg.append(\"g\")\n" +
                                "      .attr(\"class\", \"links\")\n" +
                                "    .selectAll(\"line\")\n" +
                                "    .data(graph.links)\n" +
                                "    .enter().append(\"line\")\n" +
                                "      .attr(\"stroke-width\", function(d) { return Math.sqrt(d.value); });\n" +
                                "\n" +
                                "  var node = svg.append(\"g\")\n" +
                                "      .attr(\"class\", \"nodes\")\n" +
                                "    .selectAll(\"circle\")\n" +
                                "    .data(graph.nodes)\n" +
                                "    .enter().append(\"circle\")\n" +
                                "      .attr(\"r\", 6)\n" +
                                "      .attr(\"fill\", function (d, i) { \n" +
                                "			if (d.group==2){\n" +
                                "				return \"red\" ;\n" +
                                "		    }else{ return color(d.group);}\n" +
                                "			}) \n" +
                                "      .call(d3.drag()\n" +
                                "          .on(\"start\", dragstarted)\n" +
                                "          .on(\"drag\", dragged)\n" +
                                "          .on(\"end\", dragended));\n" +
                                "\n" +
                                "  node.append(\"title\")\n" +
                                "      .text(function(d) { return d.id; });\n" +
                                "\n" +
                                "  simulation\n" +
                                "      .nodes(graph.nodes)\n" +
                                "      .on(\"tick\", ticked);\n" +
                                "\n" +
                                "  simulation.force(\"link\")\n" +
                                "      .links(graph.links);\n" +
                                "\n" +
                                "  function ticked() {\n" +
                                "    link\n" +
                                "        .attr(\"x1\", function(d) { return d.source.x; })\n" +
                                "        .attr(\"y1\", function(d) { return d.source.y; })\n" +
                                "\n" +
                                "        .attr(\"x2\", function(d) { return d.target.x; })\n" +
                                "        .attr(\"y2\", function(d) { return d.target.y; });\n" +
                                "\n" +
                                "    node\n" +
                                "        .attr(\"cx\", function(d) { return d.x; })\n" +
                                "        .attr(\"cy\", function(d) { return d.y; });\n" +
                                "  }\n" +
                                "});\n" +
                                "\n" +
                                "function dragstarted(d) {\n" +
                                "  if (!d3.event.active) simulation.alphaTarget(0.3).restart();\n" +
                                "  d.fx = d.x;\n" +
                                "  d.fy = d.y;\n" +
                                "}\n" +
                                "\n" +
                                "function dragged(d) {\n" +
                                "  d.fx = d3.event.x;\n" +
                                "  d.fy = d3.event.y;\n" +
                                "}\n" +
                                "\n" +
                                "function dragended(d) {\n" +
                                "  if (!d3.event.active) simulation.alphaTarget(0);\n" +
                                "  d.fx = null;\n" +
                                "  d.fy = null;\n" +
                                "}\n" +
                                "\n" +
                                "</script>");
                out.println("<p class=\"linkVolver\" align=\"center\">\n" +
                "<a href=\"index.jsp\" style=\"font-size: 15pt; font-family: Comic Sans MS; color: white; align-items: center\">Inicio</a>\n" +
                "<br><br>\n" +
                "<a href=\"jsp/principal.jsp\" style=\"font-size: 15pt; font-family: Comic Sans MS; color: white; align-items: center\">Volver</a>\n" +
                "</p>");
                out.println("</body>");                
            out.println("</html>");
            out.close();
            
            
            sshConector.disconnect();
            
        } catch (JSchException ex) { 
            
            ex.printStackTrace();             
            System.out.println(ex.getMessage());
            
        } catch (IllegalAccessException ex) {
            
            ex.printStackTrace();             
            System.out.println(ex.getMessage());
            
        } catch (IOException ex) {
            
            ex.printStackTrace();             
            System.out.println(ex.getMessage());
            
        } catch (NullPointerException nu){
            
            nu.printStackTrace();
            System.out.println(nu.getMessage());
        }
    }    
}
