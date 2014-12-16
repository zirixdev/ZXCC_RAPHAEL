package zirix.zxcc.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zirix.zxcc.server.dao.*;
import zirix.zxcc.server.mock.MockEvaluator;
import zirix.zxcc.server.mock.MockSchedule;
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

		MockEvaluator eval = new MockEvaluator(Integer.parseInt(WORK_ID));
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
						MockSchedule sched = new MockSchedule(Integer.parseInt(WORK_ID));
						sched.changeState(PK_COLUMN);
					}
				}
			}else if (OP_CODE.compareTo("DELETE") == 0){
				String COD_UNIDADES_AGENDADAS = request.getParameter("COD_UNIDADES_AGENDADAS").trim();
				pkListUnidadeAgendada = UnidadesAgendadasDAO.createKey("COD_UNIDADES_AGENDADAS", Integer.parseInt(COD_UNIDADES_AGENDADAS));
				daoUnidadesAgendadas.setPkList(pkListUnidadeAgendada);
				daoUnidadesAgendadas.delete();
			}
			
		}catch(Exception e){
			out.println("Error on ProcessarAgendamentoServiceServlet... " + ' ' + e.getMessage());
		}
		response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp?COD_USUARIO=" + COD_USUARIO);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
