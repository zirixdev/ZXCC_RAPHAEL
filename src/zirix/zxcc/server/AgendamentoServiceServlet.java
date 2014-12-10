/*ZIRIX CONTROL CENTER - AGENDAMENTO SERVICE SERVLET
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO
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
@WebServlet( name="AgendamentoServiceServlet", urlPatterns = {"/services/agendamento"}, loadOnStartup=1)
public class AgendamentoServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AgendamentoServiceServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String OP_CODE = request.getParameter("OP_CODE");
		String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
		String WORK_ID = request.getParameter("WORK_ID");
		String DATA_INGRESSO = request.getParameter("DATA_INGRESSO").trim();
		int PK_COLUMN = 0;
		Evaluator eval = new Evaluator(Integer.parseInt(WORK_ID));
		try {
			AgendamentoDAO daoAgendamento = new AgendamentoDAO();
			PkList pkList;
			if ((OP_CODE.compareTo("UPDATE") == 0) || (OP_CODE.compareTo("CREATE") == 0)) {
			   String COD_PEDIDO = request.getParameter("CODPEDIDO").trim();
			   daoAgendamento.setAttValueFor("COD_PEDIDO", COD_PEDIDO);
			   daoAgendamento.setAttValueFor("ESTADO", 0);
			   daoAgendamento.setAttValueFor("DELETED", 0);
			   String END_AGEND = request.getParameter("END_AGEND").trim();
			   if(END_AGEND.compareTo("sim") == 0){
				   String COD_DADOS_INSTALACAO = request.getParameter("CODDADOINST").trim();
				   daoAgendamento.setAttValueFor("COD_DADOS_INSTALACAO", COD_DADOS_INSTALACAO);
			   }
			   if(OP_CODE.compareTo("UPDATE") == 0){
				   String COD_AGENDAMENTO = request.getParameter("COD_AGENDAMENTO").trim();
				   pkList = AgendamentoDAO.createKey("COD_AGENDAMENTO", Integer.parseInt(COD_AGENDAMENTO));
				   daoAgendamento.setPkList(pkList);
				   daoAgendamento.update();
			   }else{
				   daoAgendamento.Create();
				   int pkListValue = 0;
				   int pkCodCliente = 0;
				   Vector<String[]> CodAgendamento_ = new Vector<String[]>();
				   try {
					   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT MAX(COD_AGENDAMENTO) "
							   +                                                          "     , COD_CLIENTE "
							   +                                                          "  FROM " + ZXMain.DB_NAME_ + "AGENDAMENTO "
							   +                                                          "     , " + ZXMain.DB_NAME_ + "PEDIDO "
							   +                                                          " WHERE AGENDAMENTO.COD_PEDIDO = " + COD_PEDIDO
							   +                                                          "   AND PEDIDO.COD_PEDIDO = " + COD_PEDIDO);
					   for (int i=0;i < values.size();i++) {
						   String[] attList = new String[2];
						   attList[0] = values.get(i)[0].toString();;
						   attList[1] = values.get(i)[1].toString();;
						   CodAgendamento_.add(attList);
					   }
				   }catch (SQLException ex) {
					   ex.printStackTrace();
				   }  finally {
					   pkListValue = Integer.parseInt(CodAgendamento_.elementAt(0)[0].trim());
					   pkCodCliente = Integer.parseInt(CodAgendamento_.elementAt(0)[1].trim());
				   }
				   if(pkListValue != 0){
					   int arraysize = Integer.parseInt(request.getParameter("QOBS").trim());
					   for(int d = 0 ; d < arraysize ; d++){
						   ObsAgendamentoDAO daoObsAgendamento = new ObsAgendamentoDAO();
						   daoObsAgendamento.setAttValueFor("COD_AGENDAMENTO",pkListValue);
						   daoObsAgendamento.setAttValueFor("INDICE",d);
						   String OBSERVACAO = request.getParameter("OBSERVACAO_"+d);
						   daoObsAgendamento.setAttValueFor("OBSERVACAO",OBSERVACAO);
						   daoObsAgendamento.setAttValueFor("CHAVE",0);
						   daoObsAgendamento.setAttValueFor("DELETED",0);
						   daoObsAgendamento.Create();
					   }
					   arraysize = Integer.parseInt(request.getParameter("TOTALUNIDAGEND").trim());
					   for(int d = 0 ; d < arraysize ; d++){
						   UnidadesAgendadasDAO daoUnidadesAgendadas = new UnidadesAgendadasDAO();
						   daoUnidadesAgendadas.setAttValueFor("COD_AGENDAMENTO",pkListValue);
						   String COD_UNIDADE = request.getParameter("CODVEIC_"+d);
						   String DATA_AGENDAMENTO = request.getParameter("DATA_AGENDAMENTO_"+d);
						   String HORA_AGENDAMENTO = request.getParameter("HORA_AGENDAMENTO_"+d);
						   daoUnidadesAgendadas.setAttValueFor("DATA_AGENDAMENTO",DATA_AGENDAMENTO);
						   daoUnidadesAgendadas.setAttValueFor("HORA_AGENDAMENTO",HORA_AGENDAMENTO);
						   daoUnidadesAgendadas.setAttValueFor("COD_UNIDADE",COD_UNIDADE);
						   daoUnidadesAgendadas.setAttValueFor("TIPO_UNIDADE",2);
						   daoUnidadesAgendadas.setAttValueFor("ESTADO",0);
						   daoUnidadesAgendadas.setAttValueFor("DELETED",0);
						   NumeroOsDAO daoNumeroOs = new NumeroOsDAO();
						   daoNumeroOs.setAttValueFor("COD_USUARIO", COD_USUARIO);
						   daoNumeroOs.setAttValueFor("DELETED", 0);
						   int count = 0;
						   int num_os = 0;
						   Vector<String[]> NumOS_ = new Vector<String[]>();
						   try{
							   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COUNT(*) "		//00
									   + 														  "     , MAX(NUM_OS) "		//01
									   + 														  "  FROM " + ZXMain.DB_NAME_ + "NUMERO_OS "
									   + 														  " WHERE ANO_OS = YEAR(NOW()) "
									   +														  "   AND MES_OS = MONTH(NOW()) ");
							   for (int i=0;i < values.size();i++) {
								   String[] attList = new String[2];
								   attList[0] = values.get(i)[0].toString();
								   count = Integer.parseInt(attList[0].trim());
								   if(count > 0){
									   attList[1] = values.get(i)[1].toString();
									   num_os = Integer.parseInt(attList[1]);
								   }
								   NumOS_.add(attList);
							   }
						   }catch(SQLException ex){
							   ex.printStackTrace();
						   }finally{}
						   num_os++;
						   daoNumeroOs.setAttValueFor("ANO_OS", DATA_INGRESSO.substring(0, 4));
						   daoNumeroOs.setAttValueFor("MES_OS", DATA_INGRESSO.substring(5, 7));
						   daoNumeroOs.setAttValueFor("NUM_OS", num_os);
						   daoNumeroOs.Create();
						   int pkNumOS = 0;
						   Vector<String[]> NumeroOS_ = new Vector<String[]>();
						   try{
							   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_NUM_OS "
									   + 														   "  FROM " + ZXMain.DB_NAME_ + "NUMERO_OS "
									   + 														   " WHERE ANO_OS = " + DATA_INGRESSO.substring(0, 4)
									   + 														   "   AND MES_OS = " + DATA_INGRESSO.substring(5, 7)
									   + 														   "   AND NUM_OS = " + num_os
									   + 														   "   AND COD_USUARIO = " + COD_USUARIO);
							   for (int i=0;i < values.size();i++) {
								   String[] attList = new String[1];
								   attList[0] = values.get(i)[0].toString();
								   NumeroOS_.add(attList);
							   }
						   }catch(SQLException ex){
							   ex.printStackTrace();
						   }finally{
							   pkNumOS = Integer.parseInt(NumeroOS_.elementAt(0)[0].trim());
						   }
						   OsDAO daoOS = new OsDAO();
						   daoOS.setAttValueFor("COD_NUM_OS", pkNumOS);
						   daoOS.setAttValueFor("FRUSTRADA", 0);
						   daoOS.setAttValueFor("COD_TIPO_OS", 1);
						   daoOS.setAttValueFor("HAVE_TEST", 0);
						   daoOS.setAttValueFor("DELETED", 0);
						   daoOS.setAttValueFor("COD_CLIENTE", pkCodCliente);
						   daoOS.setAttValueFor("COD_UNIDADE",COD_UNIDADE);
						   daoOS.setAttValueFor("TIPO_UNIDADE",2);
						   daoOS.setAttValueFor("COD_AGENDAMENTO",pkListValue);
						   daoOS.Create();
						   int pkCodOS = 0;
						   Vector<String[]> CodOS_ = new Vector<String[]>();
						   try{
							   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_OS "
									   + 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
									   + 														   " WHERE COD_NUM_OS = " + pkNumOS);
							   for (int i=0;i < values.size();i++) {
								   String[] attList = new String[1];
								   attList[0] = values.get(i)[0].toString();
								   CodOS_.add(attList);
							   }
						   }catch(SQLException ex){
							   ex.printStackTrace();
						   }finally{
							   pkCodOS = Integer.parseInt(CodOS_.elementAt(0)[0].trim());
						   }
						   daoUnidadesAgendadas.setAttValueFor("COD_OS",pkCodOS);
						   daoUnidadesAgendadas.Create();
						   int pkCodUnidadeAgendada;
						   Vector<String[]> CodUnidadeAgendada_ = new Vector<String[]>();
						   try {
							   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_UNIDADES_AGENDADAS "
									   + " 											                 FROM " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
									   + "                                                          WHERE COD_OS = " + pkCodOS);
							   for (int i=0;i < values.size();i++) {
								   String[] attList = new String[1];
								   attList[0] = values.get(i)[0].toString();;
								   CodUnidadeAgendada_.add(attList);
							   }
						   }catch (SQLException ex) {
							   ex.printStackTrace();
						   }  finally {
							   pkCodUnidadeAgendada = Integer.parseInt(CodUnidadeAgendada_.elementAt(0)[0].trim());
						   }
						   PK_COLUMN = pkCodUnidadeAgendada;
						   eval.startDepedencyWorkAgendamento(PK_COLUMN, DATA_AGENDAMENTO, HORA_AGENDAMENTO);
					   }
					   if(END_AGEND.compareTo("nao") == 0){
						   DadosAgendamentoDAO daoDadosAgendamento = new DadosAgendamentoDAO();
						   String ENDERECO = request.getParameter("ENDINST");
						   daoDadosAgendamento.setAttValueFor("ENDERECO",ENDERECO);
						   String BAIRRO = request.getParameter("BAIRROINST");
						   daoDadosAgendamento.setAttValueFor("BAIRRO",BAIRRO);
						   String CIDADE = request.getParameter("CIDADEINST");
						   daoDadosAgendamento.setAttValueFor("CIDADE",CIDADE);
						   String UF = request.getParameter("UFINST");
						   daoDadosAgendamento.setAttValueFor("UF",UF);
						   String COD_PAIS = request.getParameter("PAISINST");
						   daoDadosAgendamento.setAttValueFor("COD_PAIS",COD_PAIS);
						   String COMPLEMENTO = request.getParameter("COMPINST");
						   daoDadosAgendamento.setAttValueFor("COMPLEMENTO",COMPLEMENTO);
						   String CEP = request.getParameter("CEPINST");
						   daoDadosAgendamento.setAttValueFor("CEP",CEP);
						   String REFERENCIA = request.getParameter("REFERINST");
						   daoDadosAgendamento.setAttValueFor("REFERENCIA",REFERENCIA);
						   String DDD = request.getParameter("DDDINST");
						   daoDadosAgendamento.setAttValueFor("DDD",DDD);
						   String NUMERO = request.getParameter("NUMEROINST");
						   daoDadosAgendamento.setAttValueFor("NUMERO",NUMERO);
						   String NOMEINST = request.getParameter("NOMEINST");
						   daoDadosAgendamento.setAttValueFor("NOME",NOMEINST);
						   daoDadosAgendamento.setAttValueFor("COD_AGENDAMENTO",pkListValue);
						   daoDadosAgendamento.setAttValueFor("DELETED",0);
						   daoDadosAgendamento.Create();
					   }
				   }else{
					   out.println("Error on AgendamentoServiceServlet... " + "\nIngresso do Agendamento! ");
				   }
			   }
			   if(WORK_ID.compareTo("0") != 0){
				   if(eval.endWorkAgendamento()){
					   String REAGENDAR = request.getParameter("REAGENDAR").toString().trim();
					   if(REAGENDAR.compareTo("true") == 0){
						   Schedule.createSameWork(Integer.parseInt(WORK_ID));
					   }
				   }
			   }
			}else if (OP_CODE.compareTo("DELETE") == 0){
				String COD_AGENDAMENTO = request.getParameter("COD_AGENDAMENTO").trim();
				pkList = AgendamentoDAO.createKey("COD_AGENDAMENTO", Integer.parseInt(COD_AGENDAMENTO));
				daoAgendamento.setPkList(pkList);
				daoAgendamento.delete();
			}
		}catch(Exception e){
			out.println("Error on AgendamentoServiceServlet... " + ' ' + e.getMessage());
		}
		response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}

