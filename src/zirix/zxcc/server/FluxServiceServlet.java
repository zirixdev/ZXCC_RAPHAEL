/*ZIRIX CONTROL CENTER - FLUX SERVICE SERVLET
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVA*/
package zirix.zxcc.server;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zirix.zxcc.server.dao.*;

@WebServlet( name="FluxServiceServlet", urlPatterns = {"/services/startservlet"}, loadOnStartup=1)
public class FluxServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FluxServiceServlet(){
	    super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   response.setContentType("text/html");
	   PrintWriter out = response.getWriter();
	   String OP_CODE = request.getParameter("OP_CODE");
	   try{
		   int PROCESS_ID;
		   Vector<String[]> CodProcessWork = new Vector<String[]>();
		   Vector<String[]> NameProcess = new Vector<String[]>();
		   SchedProcessDAO daoSchedProcess = new SchedProcessDAO();
		   if (OP_CODE.compareTo("STARTFLUX") == 0) {
			   int DEFINED_PROCESS_ID = Integer.parseInt(request.getParameter("PROCESS_ID"));
			   int PK_COLUMN = Integer.parseInt(request.getParameter("PK_COLUMN"));
			   try{
				   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT PROCESS_NAME FROM " + ZXMain.DB_NAME_ + "DEFINED_PROCESS WHERE PROCESS_ID = " + DEFINED_PROCESS_ID);
				   for (int i=0;i < values.size();i++) {
					   String[] attList = new String[1];
					   attList[0] = values.get(i)[0].toString();
					   NameProcess.add(attList);
				   }
			   }catch (SQLException ex) {
				   out.println("Error on FluxServiceServlet -> Select Process_Name ... " + ex.getMessage());
			   }finally {
				   daoSchedProcess.setAttValueFor("PROCESS_NAME", (NameProcess.elementAt(0)[0].trim()).toString());
			   }
			   daoSchedProcess.setAttValueFor("STATE_ID", 1);
			   daoSchedProcess.Create();
			   try{
				   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT MAX(PROCESS_ID) FROM " + ZXMain.DB_NAME_ + "SCHED_PROCESS;");
				   for (int i=0;i < values.size();i++) {
					   String[] attList = new String[1];
					   attList[0] = values.get(i)[0].toString();
					   CodProcessWork.add(attList);
				   }
			   }catch (SQLException ex) {
				   out.println("Error on FluxServiceServlet... " + ex.getMessage());
			   }finally{
				   PROCESS_ID = Integer.parseInt(CodProcessWork.elementAt(0)[0].trim());
			   }
			   Schedule.createSchedWork(PROCESS_ID,DEFINED_PROCESS_ID,1,0, PK_COLUMN);
		   }
		   else if (OP_CODE.compareTo("ENDWORK") == 0) {
			   int WORK_ID = Integer.parseInt(request.getParameter("WORK_ID"));
			   int PK_COLUMN = Integer.parseInt(request.getParameter("PK_COLUMN"));
			   Evaluator eval = new Evaluator(WORK_ID);
			   if(eval.endWork()){
				   Schedule sched = new Schedule(WORK_ID);
				   sched.changeState(PK_COLUMN);
			   }
		   }
	   }
	   catch (Exception e){
		   out.println("Error on FluxServiceServlet... " + e.getMessage());
	   }
	   response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}