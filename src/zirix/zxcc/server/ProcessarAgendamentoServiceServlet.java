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
@WebServlet( name="ProcessarAgendamentoServiceServlet", urlPatterns = {"/services/processaragendamento"}, loadOnStartup=1)
public class ProcessarAgendamentoServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProcessarAgendamentoServiceServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String OP_CODE = request.getParameter("OP_CODE");
		String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
		String WORK_ID = request.getParameter("WORK_ID");
		int PK_COLUMN = Integer.parseInt(request.getParameter("PK_COLUMN"));
		int arraysize = 0;

		Evaluator eval = new Evaluator(Integer.parseInt(WORK_ID));
		try {
			UnidadesAgendadasDAO daoUnidadesAgendadas = new UnidadesAgendadasDAO();
			OsDAO daoOs = new OsDAO();
			PkList pkListUnidadeAgendada;
			PkList pkListOs;
			if (OP_CODE.compareTo("UPDATE") == 0){
				String FRUSTRADA = request.getParameter("FRUSTRADA").trim();
				String COD_TECNICO = request.getParameter("COD_TECNICO").trim();
				String CHEGADA_TECNICO = request.getParameter("CHEGADA_TECNICO").trim();
				String SAIDA_TECNICO = request.getParameter("SAIDA_TECNICO").trim();
				String COD_UNIDADES_AGENDADAS = request.getParameter("COD_UNIDADES_AGENDADAS").trim();
				String COD_OS = request.getParameter("COD_OS").trim();
				arraysize = Integer.parseInt(request.getParameter("QOBS").trim());
				for(int d = 0 ; d < arraysize ; d++){
					ObsOsDAO daoObsOs = new ObsOsDAO();
					daoObsOs.setAttValueFor("COD_OS",COD_OS);
					daoObsOs.setAttValueFor("INDICE",d);
					String OBSERVACAO = request.getParameter("OBSERVACAO_"+d);
					daoObsOs.setAttValueFor("OBSERVACAO",OBSERVACAO);
					daoObsOs.setAttValueFor("DELETED",0);
					daoObsOs.Create();
				}
				daoOs.setAttValueFor("COD_TECNICO",COD_TECNICO);
				daoOs.setAttValueFor("ARRAIVE_TIME",CHEGADA_TECNICO);
				daoOs.setAttValueFor("LEAVE_TIME",SAIDA_TECNICO);
				if(FRUSTRADA.compareTo("sim") == 0){
					daoOs.setAttValueFor("FRUSTRADA", 1);
				}else if(FRUSTRADA.compareTo("nao") == 0){
					daoOs.setAttValueFor("FRUSTRADA", 0);
					String RESPOSTA_UNIDADE = request.getParameter("RESPOSTA_UNIDADE").trim();
					if(RESPOSTA_UNIDADE.compareTo("inprodutivo") == 0){
						daoUnidadesAgendadas.setAttValueFor("ESTADO",2);
					}if(RESPOSTA_UNIDADE.compareTo("instalado") == 0){
						daoUnidadesAgendadas.setAttValueFor("ESTADO",1);
						TestesOsDAO daoTestesOs = new TestesOsDAO();
						daoTestesOs.setAttValueFor("COD_OS",COD_OS);
						String IGNICAO = request.getParameter("IGNICAO").trim();
						daoTestesOs.setAttValueFor("IGNICAO", IGNICAO);
						String BLOQUEIO = request.getParameter("BLOQUEIO").trim();
						daoTestesOs.setAttValueFor("BLOQUEIO", BLOQUEIO);
						String GPS = request.getParameter("GPS").trim();
						daoTestesOs.setAttValueFor("GPS", GPS);
						String GPRS = request.getParameter("GPRS").trim();
						daoTestesOs.setAttValueFor("GPRS", GPRS);
						String SIRENE = request.getParameter("SIRENE").trim();
						daoTestesOs.setAttValueFor("SIRENE", SIRENE);
						String PANICO = request.getParameter("PANICO").trim();
						daoTestesOs.setAttValueFor("PANICO", PANICO);
						String RPM = request.getParameter("RPM").trim();
						daoTestesOs.setAttValueFor("RPM", RPM);
						daoTestesOs.setAttValueFor("DELETED", 0);
						daoTestesOs.Create();
					}
				}
				pkListUnidadeAgendada = UnidadesAgendadasDAO.createKey("COD_UNIDADES_AGENDADAS", Integer.parseInt(COD_UNIDADES_AGENDADAS));
				pkListOs = OsDAO.createKey("COD_OS", Integer.parseInt(COD_OS));
				daoUnidadesAgendadas.setPkList(pkListUnidadeAgendada);
				daoUnidadesAgendadas.update();
				daoOs.setPkList(pkListOs);
				daoOs.update();
				if(WORK_ID.compareTo("0") != 0){
					if(eval.endWork()){
						Schedule sched = new Schedule(Integer.parseInt(WORK_ID));
						sched.changeState(PK_COLUMN);
					}
				}
				int pkInstalacao = 0;
				int count = 0;
				Vector<String[]> CountInstalacao_ = new Vector<String[]>();
				try {
					ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COUNT(*) "
							+														   "  FROM " + ZXMain.DB_NAME_ + "INSTALACAO "
							+														   " WHERE COD_MODULO = 1 "
							+														   "   AND COD_UNIDADE = 1 "
							+														   "   AND DELETED = 0 ");
					for (int i=0;i < values.size();i++) {
						String[] attList = new String[1];
						attList[0] = values.get(i)[0].toString();
						CountInstalacao_.add(attList);
					}
				}catch (SQLException ex) {
					ex.printStackTrace();
				}finally{
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
							+														   "  FROM " + ZXMain.DB_NAME_ + "INSTALACAO "
							+														   " WHERE COD_MODULO = 1 "
							+														   "   AND COD_UNIDADE = 1 "
							+														   "   AND DELETED = 0 ");
					for (int i=0;i < values.size();i++) {
						String[] attList = new String[1];
						attList[0] = values.get(i)[0].toString();
						CodInstalacao_.add(attList);
					}
				}catch (SQLException ex) {
					ex.printStackTrace();
				}finally{
					pkInstalacao = Integer.parseInt(CodInstalacao_.elementAt(0)[0].trim());
				}
				int ID_MODULO = Integer.parseInt(request.getParameter("ID_MODULO").trim());
				PkList pkListModulo;
				ModuloDAO daoModulo = new ModuloDAO();
				pkListModulo = daoModulo.createKey("COD_MODULO", ID_MODULO);
				daoModulo.setPkList(pkListModulo);
				daoModulo.setAttValueFor("COD_INSTALACAO",pkInstalacao);
				int pkCodCliente = 0;
				int pkUnidade = 0;
				Vector<String[]> UnidAgendadas_ = new Vector<String[]>();
				try {
					ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT VEICULO.COD_CLIENTE "
							+ 														   "     , UNIDADES_AGENDADAS.COD_UNIDADE"
							+														   "  FROM " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
							+														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
							+														   " WHERE VEICULO.COD_VEICULO = UNIDADES_AGENDADAS.COD_UNIDADE "
							+														   "   AND UNIDADES_AGENDADAS.TIPO_UNIDADE = 2 "
							+														   "   AND UNIDADES_AGENDADAS.COD_UNIDADES_AGENDADAS = " + COD_UNIDADES_AGENDADAS);
					for (int i=0;i < values.size();i++) {
						String[] attList = new String[2];
						attList[0] = values.get(i)[0].toString();
						attList[1] = values.get(i)[1].toString();
						UnidAgendadas_.add(attList);
					}
				}catch (SQLException ex) {
					ex.printStackTrace();
				}finally{
					pkCodCliente = Integer.parseInt(UnidAgendadas_.elementAt(0)[0].trim());
					pkUnidade = Integer.parseInt(UnidAgendadas_.elementAt(0)[1].trim());
				}
				daoModulo.setAttValueFor("COD_CLIENTE",pkCodCliente);
				daoModulo.update();
				PkList pkListInstalcao;
				InstalacaoDAO daoInstalacao = new InstalacaoDAO();
				pkListInstalcao = daoInstalacao.createKey("COD_INSTALACAO", pkInstalacao);
				daoInstalacao.setPkList(pkListInstalcao);
				daoInstalacao.setAttValueFor("COD_MODULO",ID_MODULO);
				daoInstalacao.setAttValueFor("COD_UNIDADE",2);
				daoInstalacao.setAttValueFor("COD_VEICULO",pkUnidade);
				daoInstalacao.update();
				PkList pkListVeiculo;
				VeiculoDAO daoVeiculo = new VeiculoDAO();
				pkListVeiculo = daoVeiculo.createKey("COD_VEICULO", pkUnidade);
				daoVeiculo.setPkList(pkListVeiculo);
				daoVeiculo.setAttValueFor("COD_INSTALACAO",pkInstalacao);
				daoVeiculo.update();
			}else if (OP_CODE.compareTo("DELETE") == 0){
				String COD_UNIDADES_AGENDADAS = request.getParameter("COD_UNIDADES_AGENDADAS").trim();
				pkListUnidadeAgendada = UnidadesAgendadasDAO.createKey("COD_UNIDADES_AGENDADAS", Integer.parseInt(COD_UNIDADES_AGENDADAS));
				daoUnidadesAgendadas.setPkList(pkListUnidadeAgendada);
				daoUnidadesAgendadas.delete();
			}
		}catch(Exception e){
			out.println("Error on ProcessarAgendamentoServiceServlet... " + ' ' + e.getMessage());
		}
		response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
