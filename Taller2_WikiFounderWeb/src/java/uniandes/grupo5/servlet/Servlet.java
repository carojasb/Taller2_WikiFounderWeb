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
    
    int num1;
    int num2;
 
    private static final String USERNAME = "bigdata05";
    private static final String HOST = "bigdata-cluster1-01.virtual.uniandes.edu.co";
    private static final int PORT = 22;
    private static final String PASSWORD = "5b616d23a2a2130bbbcf7f47bf8b8ee5";
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
        hecho = request.getParameter("historia");
        
        try{
            SSHConector sshConector = new SSHConector();             
             
            sshConector.connect(USERNAME, PASSWORD, HOST, PORT);
            
            
            num1 = Integer.parseInt(request.getParameter("historia"));
            num2 = Integer.parseInt(request.getParameter("lugar"));
            
            String result = sshConector.executeCommand("hadoop jar prueba_taller2.jar " + num1 + " " + num2);
            
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
                out.println("<head>");
                    out.println("<title>Respuesta Servlet</title>");
                    out.println("<link href=\"Resources/css/screen.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                out.println("</head>");
                out.println("<body style=\"background-color: black\"> <BR><BR><BR>");                    
                    out.println("<h1>PARAMETROS DE LA BÚSQUEDA</h1><BR><BR>");
                    out.println("<h2>La suma entre " + num1 + " y " + num2 + " es = " + result + "</h2><BR>");                                      
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
    

            // TODO Auto-generated method stub
           /* hecho = request.getParameter("historia");
            lugar = request.getParameter("lugar");
            perso = request.getParameter("personaje");
            
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
                out.println("<head>");
                    out.println("<title>Respuesta Servlet</title>");
                    out.println("<link href=\"Resources/css/screen.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                out.println("</head>");
                out.println("<body style=\"background-color: black\"> <BR><BR><BR>");                    
                    out.println("<h1>PARAMETROS DE LA BÚSQUEDA</h1><BR><BR>");
                    out.println("<h2>Hecho histórico a buscar = " + hecho + "</h2><BR>");
                    out.println("<h2>Lugar a buscar = " + lugar + "</h2><BR>");
                    out.println("<h2>Personaje a buscar = " + perso + "</h2><BR>");
                out.println("</body>");
            out.println("</html>");*/
           /* 
            num1 = Integer.parseInt(request.getParameter("historia"));
            num2 = Integer.parseInt(request.getParameter("lugar"));
            
            Process proc = Runtime.getRuntime().exec("java -jar C:/Users/Asus/Documents/Prueba_taller2.jar " + num1 + " " + num2);
            InputStream resultado = proc.getInputStream();
            BufferedReader br = new BufferedReader (new InputStreamReader (resultado));
            
            //System.out.println(br.readLine()); 
             
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
                out.println("<head>");
                    out.println("<title>Respuesta Servlet</title>");
                    out.println("<link href=\"Resources/css/screen.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                out.println("</head>");
                out.println("<body style=\"background-color: black\"> <BR><BR><BR>");                    
                    out.println("<h1>PARAMETROS DE LA BÚSQUEDA</h1><BR><BR>");
                    out.println("<h2>La suma entre " + num1 + " y " + num2 + " es = " + br.readLine() + "</h2><BR>");                                      
                out.println("</body>");
            out.println("</html>");
            out.close();*/
    }    
}
