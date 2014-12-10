/*ZIRIX CONTROL CENTER - LOGOUT SERVLET
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO LTDA.
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( name="LogOutServlet", urlPatterns = {"/services/logout"}, loadOnStartup=1)
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogOutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
	        for(Cookie cookie : cookies){
	            if(cookie.getName().equals("JSESSIONID")){
	                System.err.println("JSESSIONID="+cookie.getValue());
	            }
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
        }
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
		response.sendRedirect(ZXMain.URL_ADRESS_ + "index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
