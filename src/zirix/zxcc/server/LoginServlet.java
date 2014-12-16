/*ZIRIX CONTROL CENTER - LOGIN SERVLET
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
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

import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.ZXCCConstantsServlet;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet( name="LoginServlet", urlPatterns = {"/services/login"}, loadOnStartup=1)
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		    	response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp?COD_USUARIO="+((Integer)retVal[0]));
		    }
		    else
		    	out.println("<h1> ERROR TRIM ... </h1>");
	    } catch (Exception ex) {
	    	response.sendRedirect(ZXMain.URL_ADRESS_ + "index.jsp?LOGIN_FAILED=FAIL");
    		ex.printStackTrace();
		}  finally {}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
