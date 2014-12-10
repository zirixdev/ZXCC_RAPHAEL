/*ZIRIX CONTROL CENTER - AGENDAMENTO SERVICE BEAN
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.*;

public class AgendamentoServiceBean {

	private AgendamentoDAO daoAgendamento_ = null;
	private UnidadesAgendadasDAO daoUnidadesAgendadas_ = null;
	private Integer COD_AGENDAMENTO_ = null;
	private Integer COD_UNIDADES_AGENDADAS_ = null;

	public AgendamentoServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {

		COD_UNIDADES_AGENDADAS_ = new Integer((String)pkVal[0]);

		PkList pkList = new PkList();
	    pkList.put("COD_UNIDADES_AGENDADAS",COD_UNIDADES_AGENDADAS_);
	    daoUnidadesAgendadas_ = new UnidadesAgendadasDAO(pkList);

	    try {
	    	daoUnidadesAgendadas_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {}

		COD_AGENDAMENTO_ = Integer.parseInt(daoUnidadesAgendadas_.getAttValueFor("COD_AGENDAMENTO").toString());

		pkList = new PkList();
	    pkList.put("COD_AGENDAMENTO",COD_AGENDAMENTO_);
	    daoAgendamento_ = new AgendamentoDAO(pkList);

	    try {
	    	daoAgendamento_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {}
	}

	public String getDataAgendamento(){
		String dataAgendamento = daoUnidadesAgendadas_.getAttValueFor("DATA_AGENDAMENTO").toString();
		String dataFinal = dataAgendamento.substring(8, 10) + "/" + dataAgendamento.substring(5, 7) + "/" + dataAgendamento.substring(0, 4);
		return dataFinal;
	}

	public String getHoraAgendamento(){
		String horaAgendamento = daoUnidadesAgendadas_.getAttValueFor("HORA_AGENDAMENTO").toString();
		return horaAgendamento;
	}

	@SuppressWarnings("finally")
	public String getNumeroModulo(String COD_UNIDADE, String TIPO_UNIDADE) {
    	String numeroModulo = "";
    	try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT MODULO.NUMERO_MODULO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "MODULO_UNIDADE_AGENDADA "
					+ 														   "     , " + ZXMain.DB_NAME_ + "MODULO "
					+ 														   " WHERE MODULO_UNIDADE_AGENDADA.TIPO_UNIDADE = " + TIPO_UNIDADE
					+ 														   "   AND MODULO_UNIDADE_AGENDADA.COD_UNIDADE = " + COD_UNIDADE
					+ 														   "   AND MODULO.COD_MODULO = MODULO_UNIDADE_AGENDADA.COD_MODULO ;");
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				numeroModulo = attList[0];
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return numeroModulo;
		}
	}

	@SuppressWarnings("finally")
	public Integer getNumeroPedido() {
		int codPedido = Integer.parseInt(daoAgendamento_.getAttValueFor("COD_PEDIDO").toString());
    	int numeroPedido = 0;
    	try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT PEDIDO.NUM_PEDIDO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "PEDIDO "
					+ 														   " WHERE PEDIDO.COD_PEDIDO = " + codPedido);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				numeroPedido = Integer.parseInt(attList[0]);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return numeroPedido;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEnderecoInstalacaoAgendamento(){
		Integer cod_dados_instalacao = Integer.parseInt(daoAgendamento_.getAttValueFor("COD_DADOS_INSTALACAO").toString());
		Vector<String[]> enderecoInstalacao = new Vector<String[]>();
		if(cod_dados_instalacao == null){
			try{
				ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT DADOS_AGENDAMENTO.ENDERECO "					//0
					+ 														   "     , DADOS_AGENDAMENTO.BAIRRO "						//1
					+														   "     , DADOS_AGENDAMENTO.CIDADE "						//2
					+ 														   "     , DADOS_AGENDAMENTO.UF "							//3
					+														   "     , PAIS.NOME_PAIS "									//4
					+ 														   "     , DADOS_AGENDAMENTO.COMPLEMENTO "					//5
					+														   "     , DADOS_AGENDAMENTO.CEP "							//6
					+ 														   "     , DADOS_AGENDAMENTO.REFERENCIA "					//7
					+														   "     , DADOS_AGENDAMENTO.DDD "							//8
					+ 														   "     , DADOS_AGENDAMENTO.NUMERO "						//9
					+														   "     , DADOS_AGENDAMENTO.NOME "							//10
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "DADOS_AGENDAMENTO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "PAIS "
					+ 														   " WHERE DADOS_AGENDAMENTO.COD_PAIS = PAIS.COD_PAIS"
					+ 														   "   AND COD_AGENDAMENTO = " + COD_AGENDAMENTO_);
				for (int i=0;i<values.size();i++) {
					String[] attList = new String[11];
					attList[0] = values.get(i)[0].toString();
					attList[1] = values.get(i)[1].toString();
					attList[2] = values.get(i)[2].toString();
					attList[3] = values.get(i)[3].toString();
					attList[4] = values.get(i)[4].toString();
					attList[5] = values.get(i)[5].toString();
					attList[6] = values.get(i)[6].toString();
					attList[7] = values.get(i)[7].toString();
					attList[8] = values.get(i)[8].toString();
					attList[9] = values.get(i)[9].toString();
					attList[10] = values.get(i)[10].toString();
					enderecoInstalacao.add(attList);;
				}
			}catch (SQLException ex) {
	    		ex.printStackTrace();
			}finally{
				return enderecoInstalacao;
			}
		}else{
			try{
				ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT DADOS_INSTALACAO.ENDERECO "						//0
						+ 														   "     , DADOS_INSTALACAO.BAIRRO "						//1
						+														   "     , DADOS_INSTALACAO.CIDADE "						//2
						+ 														   "     , DADOS_INSTALACAO.UF "							//3
						+														   "     , PAIS.NOME_PAIS "									//4
						+ 														   "     , DADOS_INSTALACAO.COMPLEMENTO "					//5
						+														   "     , DADOS_INSTALACAO.CEP "							//6
						+ 														   "     , DADOS_INSTALACAO.REFERENCIA "					//7
						+														   "     , DADOS_INSTALACAO.DDD "							//8
						+ 														   "     , DADOS_INSTALACAO.NUMERO "						//9
						+														   "     , DADOS_INSTALACAO.NOME "							//10
						+ 														   "  FROM " + ZXMain.DB_NAME_ + "DADOS_INSTALACAO "
						+ 														   "     , " + ZXMain.DB_NAME_ + "PAIS "
						+ 														   " WHERE DADOS_INSTALACAO.COD_PAIS = PAIS.COD_PAIS"
						+ 														   "   AND COD_DADOS_INSTALACAO = " + cod_dados_instalacao);
				for (int i=0;i<values.size();i++) {
					String[] attList = new String[11];
					attList[0] = values.get(i)[0].toString();
					attList[1] = values.get(i)[1].toString();
					attList[2] = values.get(i)[2].toString();
					attList[3] = values.get(i)[3].toString();
					attList[4] = values.get(i)[4].toString();
					attList[5] = values.get(i)[5].toString();
					attList[6] = values.get(i)[6].toString();
					attList[7] = values.get(i)[7].toString();
					attList[8] = values.get(i)[8].toString();
					attList[9] = values.get(i)[9].toString();
					attList[10] = values.get(i)[10].toString();
					enderecoInstalacao.add(attList);;
				}
			}catch (SQLException ex) {
	    		ex.printStackTrace();
			}finally{
				return enderecoInstalacao;
			}
		}
	}

	@SuppressWarnings("finally")
	public String getCodCliente(){
		String codCliente = "";
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT PEDIDO.COD_CLIENTE "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "AGENDAMENTO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "PEDIDO "
					+ 														   " WHERE PEDIDO.COD_PEDIDO = AGENDAMENTO.COD_PEDIDO "
					+														   "   AND AGENDAMENTO.COD_AGENDAMENTO = " + COD_AGENDAMENTO_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				codCliente = attList[0];
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return codCliente;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getObsAgendamento(){
		Vector<String[]> obsAgendamento = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OBSERVACAO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OBS_AGENDAMENTO "
					+ 														   " WHERE COD_AGENDAMENTO = " + COD_AGENDAMENTO_
					+														   " ORDER BY INDICE ASC ");
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				obsAgendamento.add(attList);;
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return obsAgendamento;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getUnidadesAgendadas(){
		Vector<String[]> unidadesAgendadas= new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT UNIDADES_AGENDADAS.COD_UNIDADES_AGENDADAS "									//00
					+ 														   "     , VEICULO.PLACA "																//01
					+ 														   "     , VEICULO_MARCA.NOME_MARCA "													//02
					+ 														   "     , VEICULO.MODELO "																//03
					+ 														   "     , TIPO_UNIDADE.NOME "															//04
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "	//05
					+ 														   "     , OS.COD_OS "																	//06
					+														   "     , UNIDADES_AGENDADAS.TIPO_UNIDADE "											//07
					+														   "     , VEICULO.COD_VEICULO "														//08
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO_MARCA "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_UNIDADE "
					+ 														   "     , " + ZXMain.DB_NAME_ + "OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   " WHERE VEICULO.COD_VEICULO = UNIDADES_AGENDADAS.COD_UNIDADE "
					+ 														   "   AND UNIDADES_AGENDADAS.TIPO_UNIDADE = TIPO_UNIDADE.COD_UNIDADE "
					+ 														   "   AND VEICULO_MARCA.COD_MARCA = VEICULO.COD_MARCA "
					+ 														   "   AND UNIDADES_AGENDADAS.ESTADO = 0 "
					+ 														   "   AND UNIDADES_AGENDADAS.COD_OS = OS.COD_OS "
					+ 														   "   AND OS.COD_NUM_OS = NUMERO_OS.COD_NUM_OS "
					+ 														   "   AND COD_UNIDADES_AGENDADAS = " + COD_UNIDADES_AGENDADAS_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[9];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				attList[8] = values.get(i)[8].toString();
				unidadesAgendadas.add(attList);;
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return unidadesAgendadas;
		}
	}
}