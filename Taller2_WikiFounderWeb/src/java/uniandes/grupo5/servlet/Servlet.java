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
                hecho = "No_registrado";
            };
            
            lugar = request.getParameter("lugar");
            if (lugar.trim().length() == 0){
                lugar = "No_registrado";
            };
            
            perso = request.getParameter("personaje");
            if (perso.trim().length() == 0){
                perso = "No_registrado";
            };
            
            fecha_inicial = request.getParameter("datepickerinicial");
            if (fecha_inicial.trim().length() == 0){
                fecha_inicial = "No_registrado";
            };
            
            fecha_final = request.getParameter("datepickerfinal");
            if (fecha_final.trim().length() == 0){
                fecha_final = "No_registrado";
            };
            
            //String result = sshConector.executeCommand("hadoop jar prueba_taller2.jar " + num1 + " " + num2);
            sshConector.executeCommand("hadoop fs -rm -r -f salida_taller2");
            String result = sshConector.executeCommand("hadoop jar Wiki.jar uniandes.reuters.job.XML_Job datos_wiki_test salida_taller2");
                        
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
                out.println("<head>");
                    out.println("<title>Respuesta Servlet</title>");
                    out.println("<link href=\"Resources/css/screen.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                out.println("</head>");
                out.println("<style>\n" +
                            "\n" +
                            ".node {\n" +
                            "  cursor: pointer;\n" +
                            "}\n" +
                            "\n" +
                            ".node circle {\n" +
                            "  fill: #fff;\n" +
                            "  stroke: steelblue;\n" +
                            "  stroke-width: 1.5px;\n" +
                            "}\n" +
                            "\n" +
                            ".node text {\n" +
                            "  font: 10px sans-serif;\n" +
                            "}\n" +
                            "\n" +
                            ".link {\n" +
                            "  fill: none;\n" +
                            "  stroke: #ccc;\n" +
                            "  stroke-width: 1.5px;\n" +
                            "}\n" +
                            "\n" +
                            "</style>");
                out.println("<body style=\"background-color: darkslategray\"> <BR><BR><BR>");                    
                    out.println("<h1 style=\"color: black\" align=\"center\">PARAMETROS DE LA BÚSQUEDA</h1><BR><BR>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Hecho historico = </b>" + hecho.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Lugar = </b>" + lugar.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Personaje = </b>" + perso.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Fecha inicial = </b>" + fecha_inicial.replace("_", " ")  + "</h2>");
                    out.println("<h2 style=\"color: black\" align=\"center\"> <b>Fecha final = </b>" + fecha_final.replace("_", " ")  + "</h2><BR><BR>");                    
                    out.println("<h2 style=\"color: black\">El resultado del jar es..." + result + "</h2>");
                    out.println("<script src=\"//d3js.org/d3.v3.min.js\"></script>\n" +
                "<script>\n" +
                "\n" +
                "var margin = {top: 20, right: 120, bottom: 20, left: 120},\n" +
                "    width = 960 - margin.right - margin.left,\n" +
                "    height = 800 - margin.top - margin.bottom;\n" +
                "\n" +
                "var i = 0,\n" +
                "    duration = 750,\n" +
                "    root;\n" +
                "\n" +
                "var tree = d3.layout.tree()\n" +
                "    .size([height, width]);\n" +
                "\n" +
                "var diagonal = d3.svg.diagonal()\n" +
                "    .projection(function(d) { return [d.y, d.x]; });\n" +
                "\n" +
                "var svg = d3.select(\"body\").append(\"svg\")\n" +
                "    .attr(\"width\", width + margin.right + margin.left)\n" +
                "    .attr(\"height\", height + margin.top + margin.bottom)\n" +
                "  .append(\"g\")\n" +
                "    .attr(\"transform\", \"translate(\" + margin.left + \",\" + margin.top + \")\");\n" +
                "\n" +
                "d3.json(\"Datos_Grafo/flare.json\", function(error, flare) {\n" +
                "  if (error) throw error;\n" +
                "\n" +
                "  root = flare;\n" +
                "  root.x0 = height / 2;\n" +
                "  root.y0 = 0;\n" +
                "\n" +
                "  function collapse(d) {\n" +
                "    if (d.children) {\n" +
                "      d._children = d.children;\n" +
                "      d._children.forEach(collapse);\n" +
                "      d.children = null;\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  root.children.forEach(collapse);\n" +
                "  update(root);\n" +
                "});\n" +
                "\n" +
                "d3.select(self.frameElement).style(\"height\", \"800px\");\n" +
                "\n" +
                "function update(source) {\n" +
                "\n" +
                "  // Compute the new tree layout.\n" +
                "  var nodes = tree.nodes(root).reverse(),\n" +
                "      links = tree.links(nodes);\n" +
                "\n" +
                "  // Normalize for fixed-depth.\n" +
                "  nodes.forEach(function(d) { d.y = d.depth * 180; });\n" +
                "\n" +
                "  // Update the nodes…\n" +
                "  var node = svg.selectAll(\"g.node\")\n" +
                "      .data(nodes, function(d) { return d.id || (d.id = ++i); });\n" +
                "\n" +
                "  // Enter any new nodes at the parent's previous position.\n" +
                "  var nodeEnter = node.enter().append(\"g\")\n" +
                "      .attr(\"class\", \"node\")\n" +
                "      .attr(\"transform\", function(d) { return \"translate(\" + source.y0 + \",\" + source.x0 + \")\"; })\n" +
                "      .on(\"click\", click);\n" +
                "\n" +
                "  nodeEnter.append(\"circle\")\n" +
                "      .attr(\"r\", 1e-6)\n" +
                "      .style(\"fill\", function(d) { return d._children ? \"lightsteelblue\" : \"#fff\"; });\n" +
                "\n" +
                "  nodeEnter.append(\"text\")\n" +
                "      .attr(\"x\", function(d) { return d.children || d._children ? -10 : 10; })\n" +
                "      .attr(\"dy\", \".35em\")\n" +
                "      .attr(\"text-anchor\", function(d) { return d.children || d._children ? \"end\" : \"start\"; })\n" +
                "      .text(function(d) { return d.name; })\n" +
                "      .style(\"fill-opacity\", 1e-6);\n" +
                "\n" +
                "  // Transition nodes to their new position.\n" +
                "  var nodeUpdate = node.transition()\n" +
                "      .duration(duration)\n" +
                "      .attr(\"transform\", function(d) { return \"translate(\" + d.y + \",\" + d.x + \")\"; });\n" +
                "\n" +
                "  nodeUpdate.select(\"circle\")\n" +
                "      .attr(\"r\", 4.5)\n" +
                "      .style(\"fill\", function(d) { return d._children ? \"lightsteelblue\" : \"#fff\"; });\n" +
                "\n" +
                "  nodeUpdate.select(\"text\")\n" +
                "      .style(\"fill-opacity\", 1);\n" +
                "\n" +
                "  // Transition exiting nodes to the parent's new position.\n" +
                "  var nodeExit = node.exit().transition()\n" +
                "      .duration(duration)\n" +
                "      .attr(\"transform\", function(d) { return \"translate(\" + source.y + \",\" + source.x + \")\"; })\n" +
                "      .remove();\n" +
                "\n" +
                "  nodeExit.select(\"circle\")\n" +
                "      .attr(\"r\", 1e-6);\n" +
                "\n" +
                "  nodeExit.select(\"text\")\n" +
                "      .style(\"fill-opacity\", 1e-6);\n" +
                "\n" +
                "  // Update the links…\n" +
                "  var link = svg.selectAll(\"path.link\")\n" +
                "      .data(links, function(d) { return d.target.id; });\n" +
                "\n" +
                "  // Enter any new links at the parent's previous position.\n" +
                "  link.enter().insert(\"path\", \"g\")\n" +
                "      .attr(\"class\", \"link\")\n" +
                "      .attr(\"d\", function(d) {\n" +
                "        var o = {x: source.x0, y: source.y0};\n" +
                "        return diagonal({source: o, target: o});\n" +
                "      });\n" +
                "\n" +
                "  // Transition links to their new position.\n" +
                "  link.transition()\n" +
                "      .duration(duration)\n" +
                "      .attr(\"d\", diagonal);\n" +
                "\n" +
                "  // Transition exiting nodes to the parent's new position.\n" +
                "  link.exit().transition()\n" +
                "      .duration(duration)\n" +
                "      .attr(\"d\", function(d) {\n" +
                "        var o = {x: source.x, y: source.y};\n" +
                "        return diagonal({source: o, target: o});\n" +
                "      })\n" +
                "      .remove();\n" +
                "\n" +
                "  // Stash the old positions for transition.\n" +
                "  nodes.forEach(function(d) {\n" +
                "    d.x0 = d.x;\n" +
                "    d.y0 = d.y;\n" +
                "  });\n" +
                "}\n" +
                "\n" +
                "// Toggle children on click.\n" +
                "function click(d) {\n" +
                "  if (d.children) {\n" +
                "    d._children = d.children;\n" +
                "    d.children = null;\n" +
                "  } else {\n" +
                "    d.children = d._children;\n" +
                "    d._children = null;\n" +
                "  }\n" +
                "  update(d);\n" +
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
