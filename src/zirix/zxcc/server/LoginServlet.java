/*ZIRIX CONTROL CENTER - LOGIN SERVLET
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO LTDA.
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zirix.zxcc.server.dao.*;
import zirix.zxcc.server.ZXCCConstantsServlet;

@WebServlet( name="LoginServlet", urlPatterns = {"/services/login"}, loadOnStartup=1)
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ZXCCConstantsServlet.main();
		String userLoginName = request.getParameter("login");
		String userPassword = request.getParameter("senha");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			String query = "SELECT " + ZXMain.DB_NAME_ + "USUARIO.COD_USUARIO "
					+	   "  FROM " + ZXMain.DB_NAME_ + "USUARIO "
					+ 	   " WHERE LOGIN = \'" + userLoginName + "\'"
					+ 	   "   AND SENHA = \'" + userPassword + "\'"
					+ 	   "   AND DELETED = 0";

		    ArrayList<Object[]> values = DAOManager.getInstance().executeQuery(query);
		    Object[] retVal = (Object[])values.get(0);
		    
		    if (((Integer)retVal[0]) != null){
		    	HttpSession session = request.getSession();
	            session.setAttribute("user", retVal[0].toString().trim());
		    	int minutos = 1;
	            session.setMaxInactiveInterval(minutos*60);
		    	response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp");
		    }
		    else
		    	out.println("<h1> ERROR LOGIN SERVLET ... </h1>");
	    } catch (Exception ex) {
			response.sendRedirect(ZXMain.URL_ADRESS_ + "index.html");
    		ex.printStackTrace();
		}  finally {}
	}
}
