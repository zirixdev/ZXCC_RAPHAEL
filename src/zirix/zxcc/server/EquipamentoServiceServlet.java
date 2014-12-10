/*ZIRIX CONTROL CENTER - EQUIPAMENTO SERVICE SERVLET
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
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

/**
 * Servlet implementation class ClienteService
 */
@WebServlet( name="EquipamentoService", urlPatterns = {"/services/equipamento"}, loadOnStartup=1)
public class EquipamentoServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EquipamentoServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		   response.setContentType("text/html");

		   PrintWriter out = response.getWriter();

		   String OP_CODE = request.getParameter("OP_CODE");

		   try {
			   ModuloDAO daoModulo = new ModuloDAO();
			   PkList pkList;

			   if ((OP_CODE.compareTo("UPDATE") == 0) || (OP_CODE.compareTo("CREATE") == 0)) {

				   String NUMERO_MODULO = request.getParameter("NUM_MODULO").trim();
				   daoModulo.setAttValueFor("NUMERO_MODULO", NUMERO_MODULO);

				   daoModulo.setAttValueFor("COD_CLIENTE", 1); //Futuramente pode-se controlar mais de um estoque, como por exemplo WGP e técnicos próprios!

				   String COD_MODELO = request.getParameter("COD_MODELO").trim();
				   daoModulo.setAttValueFor("COD_MODELO", Integer.parseInt(COD_MODELO));

				   String COD_CHIP = request.getParameter("COD_CHIP").trim();
				   daoModulo.setAttValueFor("COD_CHIP", Integer.parseInt(COD_CHIP));

				   String NFE = request.getParameter("NFE").trim();
				   daoModulo.setAttValueFor("NFE", NFE);

				   daoModulo.setAttValueFor("COD_ESTADO", 1);
				   
				   daoModulo.setAttValueFor("DELETED", 0);

				   //REALIZAR CREATE INSTALACAO TEMP
				   int pkInstalacao = 0;
				   int count = 0;

				   Vector<String[]> CountInstalacao_ = new Vector<String[]>();
				   try {
					   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COUNT(*) "
							   + " 											                 FROM " + ZXMain.DB_NAME_ + "INSTALACAO "
							   + "                                                          WHERE COD_MODULO = 1 "
							   + "                                                            AND COD_UNIDADE = 1 "
							   + "                                                            AND DELETED = 0 ");
					   for (int i=0;i < values.size();i++) {
						   String[] attList = new String[1];
						   attList[0] = values.get(i)[0].toString();
						   CountInstalacao_.add(attList);
					   }
				   }catch (SQLException ex) {
					   ex.printStackTrace();
				   }  finally {
					   count = Integer.parseInt(CountInstalacao_.elementAt(0)[0].trim());
				   }

				   if(count == 0){
					   InstalacaoDAO daoInstalacao = new InstalacaoDAO();
					   daoInstalacao.setAttValueFor("COD_MODULO", 1);
					   daoInstalacao.setAttValueFor("COD_UNIDADE", 1);
					   daoInstalacao.setAttValueFor("DELETED", 0);
					   daoInstalacao.Create();
				   }

				   Vector<String[]> CodInstalacao_ = new Vector<String[]>();
				   try {
					   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_INSTALACAO "
							   + " 											                 FROM " + ZXMain.DB_NAME_ + "INSTALACAO "
							   + "                                                          WHERE COD_MODULO = 1 "
							   + "                                                            AND COD_UNIDADE = 1 "
							   + "                                                            AND DELETED = 0 ");
					   for (int i=0;i < values.size();i++) {
						   String[] attList = new String[1];
						   attList[0] = values.get(i)[0].toString();
						   CodInstalacao_.add(attList);
					   }
				   }catch (SQLException ex) {
					   ex.printStackTrace();
				   }  finally {
					   pkInstalacao = Integer.parseInt(CodInstalacao_.elementAt(0)[0].trim());
				   }
				   //FIM DA INSTALACAO TEMP
				   daoModulo.setAttValueFor("COD_INSTALACAO", pkInstalacao);

				   String SN = request.getParameter("SN").trim();
				   daoModulo.setAttValueFor("SN_MODULO", SN);

				   if (OP_CODE.compareTo("UPDATE") == 0){

					   String COD_MODULO = request.getParameter("COD_MODULO");
					   pkList = ModuloDAO.createKey("COD_MODULO", Integer.parseInt(COD_MODULO));

					   daoModulo.setPkList(pkList);
					   daoModulo.update();
				   }
				   else
				   {
					   daoModulo.Create();
					   int pkModulo = 0;
					   Vector<String[]> CodModulo_ = new Vector<String[]>();

					   try {
						   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_MODULO "
								   + " 											                 FROM " + ZXMain.DB_NAME_ + "MODULO "
								   + "                                                          WHERE NUMERO_MODULO = '" + NUMERO_MODULO + "'");
						   for (int i=0;i < values.size();i++) {
							   String[] attList = new String[1];
							   attList[0] = values.get(i)[0].toString();
							   CodModulo_.add(attList);
						   }
					   }catch (SQLException ex) {
						   ex.printStackTrace();
					   }  finally {
						   pkModulo = Integer.parseInt(CodModulo_.elementAt(0)[0].trim());
					   }

					   if(pkModulo != 0){
						   try {
							   DAOManager.getInstance().executeUpdate("UPDATE " + ZXMain.DB_NAME_ + "INSTALACAO SET COD_MODULO = " + pkModulo + " WHERE COD_INSTALACAO = " + pkInstalacao);
						   }catch (SQLException ex) {
							   ex.printStackTrace();
						   }finally{}
						   EstoqueDAO daoEstoque = new EstoqueDAO();
						   System.err.println("\n pkInstalacao pro DAO ESTOQUE = " + pkInstalacao);
						   daoEstoque.setAttValueFor("COD_INSTALACAO", pkInstalacao);
						   daoEstoque.setAttValueFor("DELETED", 0);
						   daoEstoque.Create();
						   try {
							   DAOManager.getInstance().executeUpdate("UPDATE " + ZXMain.DB_NAME_ + "CHIP SET COD_MODULO = " + pkModulo + " WHERE COD_CHIP = " + COD_CHIP);
						   }catch (SQLException ex) {
							   ex.printStackTrace();
						   }finally{}
					   }
				   }
				   String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
				   response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp?COD_USUARIO=" + COD_USUARIO);
			   }

			   else if (OP_CODE.compareTo("DELETE") == 0){

				   String COD_MODULO = request.getParameter("COD_MODULO");
				   pkList = ModuloDAO.createKey("COD_MODULO", Integer.parseInt(COD_MODULO));

				   daoModulo.setPkList(pkList);
				   daoModulo.delete();
			   }
		   } catch (Exception e) {
				   out.println("Error on EquipamentoServiceServlet... " + ' ' + e.getMessage());
		   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

