/*ZIRIX CONTROL CENTER - CHIP SERVICE SERVLET
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zirix.zxcc.server.dao.*;

@WebServlet( name="ChipService", urlPatterns = {"/services/chip"}, loadOnStartup=1)
public class ChipServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public ChipServiceServlet() {
        super();
    }

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   response.setContentType("text/html");
	   PrintWriter out = response.getWriter();
	   String OP_CODE = request.getParameter("OP_CODE");
	   try{
		   ChipDAO daoChip = new ChipDAO();
		   PkList pkList;
		   if ((OP_CODE.compareTo("UPDATE") == 0) || (OP_CODE.compareTo("CREATE") == 0)) {
			   daoChip.setAttValueFor("COD_MODULO", 0);
			   String NFE = request.getParameter("NFE");
			   daoChip.setAttValueFor("NFE", NFE);
			   String ICCID = request.getParameter("ICCID");
			   daoChip.setAttValueFor("ICCID", ICCID);
			   String COD_OPERADORA = request.getParameter("OPERADORA");
			   daoChip.setAttValueFor("COD_OPERADORA", COD_OPERADORA);
			   String APN = request.getParameter("APN");
			   daoChip.setAttValueFor("APN", APN);
			   String TECNOLOGIA = request.getParameter("TECNOLOGIA");
			   daoChip.setAttValueFor("TECNOLOGIA", TECNOLOGIA);
			   String COD_STATUS = request.getParameter("ESTADO");
			   daoChip.setAttValueFor("COD_STATUS", COD_STATUS);
			   String DDD = request.getParameter("DDD");
			   daoChip.setAttValueFor("DDD", DDD);
			   String NUMERO_CHIP = request.getParameter("NUMERO");
			   daoChip.setAttValueFor("NUMERO_CHIP", NUMERO_CHIP);
			   daoChip.setAttValueFor("DELETED", 0);
			   if (OP_CODE.compareTo("UPDATE") == 0){
				   String COD_CHIP = request.getParameter("COD_CHIP");
				   pkList = ChipDAO.createKey("COD_CHIP", Integer.parseInt(COD_CHIP));
				   daoChip.setPkList(pkList);
				   daoChip.update();
			   }else{
				   daoChip.Create();
			   }
			   response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp");
		   }else if (OP_CODE.compareTo("DELETE") == 0){
			   String COD_CHIP = request.getParameter("COD_CHIP");
			   pkList = ChipDAO.createKey("COD_CHIP", Integer.parseInt(COD_CHIP));
			   daoChip.setPkList(pkList);
			   daoChip.delete();
		   }
	   }catch (Exception e) {
			   out.println("Error on ChipServiceServlet... " + ' ' + e.getMessage());
	   }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
