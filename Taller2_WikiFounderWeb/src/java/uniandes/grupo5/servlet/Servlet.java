package uniandes.grupo5.servlet;

import com.jcraft.jsch.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.regex.Pattern;
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
                        
            String validar = sshConector.executeCommand("if [ -d relaciones_taller2 ]; then echo \"SI\"; else echo \"NO\"; fi");
            String part = null;
            String part2 = null;
            String part3 = null;
            String original = null;
            
            if (validar.contains("NO")){
                
                String result = 
                  sshConector.executeCommand("hadoop jar Wiki.jar uniandes.reuters.job.TraerTodasReferencias datos_wiki_test relaciones_taller2");
                sshConector.executeCommand("hadoop fs -get relaciones_taller2");
                                
            }else if (validar.contains("SI")){                        
                
                //Ejecutar la clase que busca en el archivo de relaciones
                sshConector.executeCommand("hadoop fs -rm -r -f  salida_taller2");
                sshConector.executeCommand("rm -r -f salida_taller2");
                String result = 
                        sshConector.executeCommand("hadoop jar Wiki.jar uniandes.reuters.job.Archivo_Salida_Job relaciones_taller2 salida_taller2 " + hecho.replace(" ", "_") + " " + lugar.replace(" ", "_") + " " + perso.replace(" ", "_") + " " + fecha_inicial.replace(" ", "_") + " " + fecha_final.replace(" ", "_"));
                sshConector.executeCommand("hadoop fs -get salida_taller2");
                
                original = sshConector.executeCommand("cat salida_taller2/part-r-00000");
                part = sshConector.executeCommand("cat salida_taller2/part-r-00000")
                        .replaceAll("<br>", " ").replaceAll("<br />", " ")
                        .replaceFirst("[||||{]", "{ \"nodes\": [{").replaceAll("[||||]", "\n").replace("1"+"{", "{").replaceAll("\\n", "_")
                        .replaceFirst(Pattern.quote(","+"____"+"{"+"\"source"), "], \"links\": [ {\"source").replaceAll("____", " ");
                part = part.substring(0,part.length() - 3) + " ] }";                
                //part2 = "{ \"nodes\": [{\"id\": \"ricky minard\",\"group\": 1}, {\"id\": \"image =\",\"group\": 2}, {\"id\": \"caption =\",\"group\": 2}, {\"id\": \"name = ricky minard\",\"group\": 2}, {\"id\": \"position = [[shooting guard]] / [[small forward]]\",\"group\": 2}, {\"id\": \"height_ft = 6\",\"group\": 2}, {\"id\": \"height_in = 5\",\"group\": 2}, {\"id\": \"weight_lbs = 220\",\"group\": 2}, {\"id\": \"league = [[korisliiga]]\",\"group\": 2}, {\"id\": \"team = tampereen pyrintÃ¶\",\"group\": 2}, {\"id\": \"number = 24\",\"group\": 2}, {\"id\": \"nationality = american\",\"group\": 2}, {\"id\": \"birth_date = {{birth date and age\",\"group\": 2}, {\"id\": \"birth_place = [[mansfield, ohio]]\",\"group\": 2}, {\"id\": \"high_school = [[mansfield senior high school\",\"group\": 2}, {\"id\": \"college = [[morehead state eagles men's basketball\",\"group\": 2}, {\"id\": \"draft_year = 2004\",\"group\": 2}, {\"id\": \"draft_round = 2\",\"group\": 2}, {\"id\": \"draft_pick = 48\",\"group\": 2}, {\"id\": \"draft_team = [[sacramento kings]]\",\"group\": 2}, {\"id\": \"career_start = 2004\",\"group\": 2}, {\"id\": \"career_end =\",\"group\": 2}, {\"id\": \"years1 = 2004â??2005\",\"group\": 2}, {\"id\": \"team1 = [[columbus riverdragons]]\",\"group\": 2}, {\"id\": \"years2 = 2005\",\"group\": 2}, {\"id\": \"team2 = [[pallacanestro biella\",\"group\": 2}, {\"id\": \"years3 = 2005â??2007\",\"group\": 2}, {\"id\": \"team3 = [[pallacanestro reggiana\",\"group\": 2}, {\"id\": \"years4 = 2007â??2009\",\"group\": 2}, {\"id\": \"team4 = [[sutor basket montegranaro\",\"group\": 2}, {\"id\": \"years5 = 2009â??2010\",\"group\": 2}, {\"id\": \"team5 = [[pallacanestro virtus roma\",\"group\": 2}, {\"id\": \"years6 = 2010\",\"group\": 2}, {\"id\": \"team6 = [[bc khimki\",\"group\": 2}, {\"id\": \"years7 = 2010â??2011\",\"group\": 2}, {\"id\": \"team7 = [[unics kazan]]\",\"group\": 2}, {\"id\": \"years8 = 2011â??2012\",\"group\": 2}, {\"id\": \"team8 = [[bc azovmash\",\"group\": 2}, {\"id\": \"years9 = 2012\",\"group\": 2}, {\"id\": \"team9 = [[a.s. junior pallacanestro casale\",\"group\": 2}, {\"id\": \"years10 = 2012â??2013\",\"group\": 2}, {\"id\": \"team10 = [[virtus pallacanestro bologna\",\"group\": 2}, {\"id\": \"years11 = 2013\",\"group\": 2}, {\"id\": \"team11 = [[beÅ?iktaÅ? men's basketball team\",\"group\": 2}, {\"id\": \"years12 = 2013â??2014\",\"group\": 2}, {\"id\": \"team12 = [[bc budivelnyk\",\"group\": 2}, {\"id\": \"years13 = 2015\",\"group\": 2}, {\"id\": \"team13 = [[spo rouen basket]]\",\"group\": 2}, {\"id\": \"years14 = 2015\",\"group\": 2}, {\"id\": \"team14 = [[bg gÃ¶ttingen]]\",\"group\": 2}, {\"id\": \"years15 = 2015â??present\",\"group\": 2}, {\"id\": \"team15 = [[tampereen pyrintÃ¶ (basketball)\",\"group\": 2}, {\"id\": \"highlights = * [[eurocup basketball\",\"group\": 2}], \"links\": [ {\"source\": \"ricky minard\",\"target\": \"image =\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"caption =\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"name = ricky minard\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"position = [[shooting guard]] / [[small forward]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"height_ft = 6\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"height_in = 5\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"weight_lbs = 220\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"league = [[korisliiga]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team = tampereen pyrintÃ¶\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"number = 24\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"nationality = american\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"birth_date = {{birth date and age\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"birth_place = [[mansfield, ohio]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"high_school = [[mansfield senior high school\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"college = [[morehead state eagles men's basketball\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"draft_year = 2004\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"draft_round = 2\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"draft_pick = 48\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"draft_team = [[sacramento kings]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"career_start = 2004\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"career_end =\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years1 = 2004â??2005\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team1 = [[columbus riverdragons]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years2 = 2005\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team2 = [[pallacanestro biella\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years3 = 2005â??2007\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team3 = [[pallacanestro reggiana\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years4 = 2007â??2009\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team4 = [[sutor basket montegranaro\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years5 = 2009â??2010\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team5 = [[pallacanestro virtus roma\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years6 = 2010\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team6 = [[bc khimki\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years7 = 2010â??2011\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team7 = [[unics kazan]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years8 = 2011â??2012\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team8 = [[bc azovmash\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years9 = 2012\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team9 = [[a.s. junior pallacanestro casale\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years10 = 2012â??2013\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team10 = [[virtus pallacanestro bologna\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years11 = 2013\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team11 = [[beÅ?iktaÅ? men's basketball team\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years12 = 2013â??2014\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team12 = [[bc budivelnyk\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years13 = 2015\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team13 = [[spo rouen basket]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years14 = 2015\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team14 = [[bg gÃ¶ttingen]]\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"years15 = 2015â??present\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"team15 = [[tampereen pyrintÃ¶ (basketball)\",\"value\": 1}, {\"source\": \"ricky minard\",\"target\": \"highlights = * [[eurocup basketball\",\"value\": 1},	{\"id\": \"texas state highway 186\",\"group\": 1}, {\"id\": \"state=tx\",\"group\": 2}, {\"id\": \"maint=[[txdot]]\",\"group\": 2}, {\"id\": \"type=sh\",\"group\": 2}, {\"id\": \"route=186\",\"group\": 2}, {\"id\": \"map=texas 186 map.svg\",\"group\": 2}, {\"id\": \"length_mi=47.71\",\"group\": 2}, {\"id\": \"length_ref={{txdot\",\"group\": 2}, {\"id\": \"accessdate=february 1, 2014}}\",\"group\": 2}, {\"id\": \"length_round=2\",\"group\": 2}, {\"id\": \"established=by 1933\",\"group\": 2}, {\"id\": \"direction_a=west\",\"group\": 2}, {\"id\": \"direction_b=east\",\"group\": 2}, {\"id\": \"terminus_a=[[image:future plate blue.svg\",\"group\": 2}, {\"id\": \"link=]][[image:no image wide.svg\",\"group\": 2}, {\"id\": \"link=]] {{jct\",\"group\": 2}, {\"id\": \"state=tx\",\"group\": 2}, {\"id\": \"junction={{jct\",\"group\": 2}, {\"id\": \"state=tx\",\"group\": 2}, {\"id\": \"terminus_b=[[port mansfield, tx\",\"group\": 2}, {\"id\": \"counties=[[hidalgo county, texas\",\"group\": 2}, {\"id\": \"previous_type=sh\",\"group\": 2}, {\"id\": \"previous_route=185\",\"group\": 2}, {\"id\": \"next_type=sh\",\"group\": 2}, {\"id\": \"next_route=188\",\"group\": 2}, {\"source\": \"texas state highway 186\",\"target\": \"state=tx\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"maint=[[txdot]]\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"type=sh\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"route=186\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"map=texas 186 map.svg\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"length_mi=47.71\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"length_ref={{txdot\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"accessdate=february 1, 2014}}\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"length_round=2\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"established=by 1933\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"direction_a=west\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"direction_b=east\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"terminus_a=[[image:future plate blue.svg\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"link=]][[image:no image wide.svg\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"link=]] {{jct\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"state=tx\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"junction={{jct\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"state=tx\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"terminus_b=[[port mansfield, tx\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"counties=[[hidalgo county, texas\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"previous_type=sh\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"previous_route=185\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"next_type=sh\",\"value\": 1}, {\"source\": \"texas state highway 186\",\"target\": \"next_route=188\",\"value\": 1},	{\"id\": \"the nice boys\",\"group\": 1}, {\"id\": \"name = the nice boys\",\"group\": 2}, {\"id\": \"image =\",\"group\": 2}, {\"id\": \"caption =\",\"group\": 2}, {\"id\": \"image_size =\",\"group\": 2}, {\"id\": \"background = group_or_band\",\"group\": 2}, {\"id\": \"alias =\",\"group\": 2}, {\"id\": \"origin = [[portland, oregon\",\"group\": 2}, {\"id\": \"genre = [[power pop]]\",\"group\": 2}, {\"id\": \"years_active = 2004–present\",\"group\": 2}, {\"id\": \"label = [[birdman records\",\"group\": 2}, {\"id\": \"associated_acts = [[the exploding hearts]], [[the riffs]]\",\"group\": 2}, {\"id\": \"website =\",\"group\": 2}, {\"id\": \"current_members = terry six brian lelko alan mansfield colin jarrel gabe lageson\",\"group\": 2}, {\"id\": \"past_members =\",\"group\": 2}, {\"source\": \"the nice boys\",\"target\": \"name = the nice boys\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"image =\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"caption =\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"image_size =\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"background = group_or_band\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"alias =\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"origin = [[portland, oregon\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"genre = [[power pop]]\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"years_active = 2004–present\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"label = [[birdman records\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"associated_acts = [[the exploding hearts]], [[the riffs]]\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"website =\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"current_members = terry six brian lelko alan mansfield colin jarrel gabe lageson\",\"value\": 1}, {\"source\": \"the nice boys\",\"target\": \"past_members =\",\"value\": 1} ] }";
                
                               
            };
            
            
            
            
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
                out.println("<head>");
                    //out.println(original + "<BR><BR><BR>");
                    //out.println(part);
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
                    out.println("<svg width=\"1000\" height=\"500\"></svg><br>");       
                    out.println(part);                    
                    out.println("<script src=\"https://d3js.org/d3.v4.min.js\"></script>\n" +
                                                        
                                "<script type=\"text/javascript\" src=\"js/dibujar_grafo.js\"></script>\n" +
                                "<script>\n" +
                                "\n" +
                                "var nodos_rel = " + part + "\n" +
                                        
                                "dibujar_grafo(nodos_rel); \n" +
                            
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
