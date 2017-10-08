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
           
        hecho = request.getParameter("historia");
        hecho = request.getParameter("lugar");
        
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
                out.println("<body style=\"background-color: black\"> <BR><BR><BR>");
                    out.println("<h1>PARAMETROS DE LA BÚSQUEDA</h1><BR><BR>");
                    out.println("<h2>Hecho historico = " + hecho  + "</h2>");
                    out.println("<h2>Lugar = " + lugar  + "</h2>");
                    out.println("<h2>Personaje = " + perso  + "</h2>");
                    out.println("<h2>Fecha inicial = " + fecha_inicial  + "</h2>");
                    out.println("<h2>Fecha final = " + fecha_final  + "</h2>");
                    out.println("<h2>El resultado del jar es... " + result + "</h2><BR>");
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
