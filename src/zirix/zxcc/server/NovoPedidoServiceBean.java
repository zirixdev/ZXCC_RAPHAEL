/*ZIRIX CONTROL CENTER - CHIP SERVICE BEAN
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.*;

public class NovoPedidoServiceBean {

	private PedidoDAO daoPedido_ = null;
	private Integer COD_PEDIDO_ = null;

	public NovoPedidoServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {

		COD_PEDIDO_ = new Integer((String)pkVal[0]);

		PkList pkList = new PkList();
	    pkList.put("COD_PEDIDO",COD_PEDIDO_);
	    daoPedido_ = new PedidoDAO(pkList);

	    try {
	    	daoPedido_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {}
	}

	public String getCodCliente(){
		String codCliente = daoPedido_.getAttValueFor("COD_CLIENTE").toString();
		return codCliente;
	}

	public Integer getDataVencimento() {
    	int dataVencimento = (Integer)daoPedido_.getAttValueFor("DATA_VENCIMENTO");
    	return dataVencimento;
	}

	public Integer getEnvioBoleto() {
    	int envioBoleto = (Integer)daoPedido_.getAttValueFor("BOLETO_EMAIL");
    	return envioBoleto;
	}
	
	public String getInfoPedido() {
		String infoPedido = daoPedido_.getAttValueFor("INFO_PEDIDO").toString();
    	return infoPedido;
	}

	public Integer getCodTipo() {
    	int codTipo = (Integer)daoPedido_.getAttValueFor("COD_TIPO");
    	return codTipo;
	}

	public Integer getNumeroPedido() {
    	int numeroPedido = (Integer)daoPedido_.getAttValueFor("NUM_PEDIDO");
    	return numeroPedido;
	}
	
	@SuppressWarnings("finally")
	public String getTipoPedido(){
		String tipoPedido = "";
		int codTipo = (Integer)daoPedido_.getAttValueFor("COD_TIPO");
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT NOME_TIPO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "TIPO_PEDIDO "
					+ 														   " WHERE COD_TIPO = " + codTipo);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[2];
				attList[0] = (String) values.get(i)[0];
				tipoPedido = attList[0];
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return tipoPedido;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getDadosInstalacao(){
		Vector<String[]> dadosInstalacao = new Vector<String[]>();
		try {
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
					+														   "     , DADOS_INSTALACAO.COD_DADOS_INSTALACAO "			//11
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "DADOS_INSTALACAO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "PAIS "
					+ 														   " WHERE DADOS_INSTALACAO.COD_PAIS = PAIS.COD_PAIS "
					+														   "   AND DADOS_INSTALACAO.COD_PEDIDO = " + COD_PEDIDO_);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[12];
				attList[0] = (String) values.get(i)[0];
				attList[1] = (String) values.get(i)[1];
				attList[2] = (String) values.get(i)[2];
				attList[3] = (String) values.get(i)[3];
				attList[4] = (String) values.get(i)[4];
				attList[5] = (String) values.get(i)[5];
				attList[6] = (String) values.get(i)[6];
				attList[7] = (String) values.get(i)[7];
				attList[8] = (String) values.get(i)[8];
				attList[9] = (String) values.get(i)[9];
				attList[10] = (String) values.get(i)[10];
				attList[11] = values.get(i)[11].toString();
				dadosInstalacao.add(attList);
			}

		}catch (SQLException ex){
    		ex.printStackTrace();
		}finally{
			return dadosInstalacao;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getCodUnidadesVeiculo(){
		Vector<String[]> codUnidades = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_VEICULO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   " WHERE COD_PEDIDO = " + COD_PEDIDO_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				codUnidades.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return codUnidades;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getCountUnidadesVeiculo(){
		Vector<String[]> countUnidades = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COUNT(UNIDADES_AGENDADAS.COD_UNIDADE) as CONTADOR "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   " WHERE UNIDADES_AGENDADAS.COD_UNIDADE IN (VEICULO.COD_VEICULO) "
					+ 														   "   AND UNIDADES_AGENDADAS.TIPO_UNIDADE = 2 "
					+ 														   "   AND UNIDADES_AGENDADAS.ESTADO NOT IN (2) "
					+ 														   "   AND VEICULO.COD_PEDIDO = " + COD_PEDIDO_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				countUnidades.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return countUnidades;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getCodUnidadesVeiculoToSched(){
		Vector<String[]> codUnidades = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT VEICULO.COD_VEICULO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   " WHERE UNIDADES_AGENDADAS.COD_UNIDADE NOT IN (VEICULO.COD_VEICULO) "
					+ 														   "   AND UNIDADES_AGENDADAS.TIPO_UNIDADE = 2 "
					+ 														   "   AND VEICULO.COD_PEDIDO = " + COD_PEDIDO_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				codUnidades.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return codUnidades;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getDadosVeiculo(int cod_veiculo){
		Vector<String[]> dadosVeiculo = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_MARCA "
					+ 														   "     , PLACA"
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   " WHERE COD_VEICULO = " + cod_veiculo);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[2];
				attList[0] = (String) values.get(i)[0].toString();
				attList[1] = (String) values.get(i)[1].toString();
				dadosVeiculo.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return dadosVeiculo;
		}
	}

	@SuppressWarnings("finally")
	public String getNomeMarca(int codMarca){
		String nomeMarca = "";
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT NOME_MARCA "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "VEICULO_MARCA "
					+ 														   " WHERE COD_MARCA = " + codMarca);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = (String) values.get(i)[0];
				nomeMarca = attList[0];
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return nomeMarca;
		}
	}

	@SuppressWarnings("finally")
	public String getNomeCombustivel(int codCombustivel){
		String nomeCombustivel = "";
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT NOME_COMBUSTIVEL "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "VEICULO_COMBUSTIVEL "
					+ 														   " WHERE COD_COMBUSTIVEL = " + codCombustivel);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = (String) values.get(i)[0];
				nomeCombustivel = attList[0];
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return nomeCombustivel;
		}
	}
	
	@SuppressWarnings("finally")
	public String getSenhaAtendimento(int codVeiculo){
		String senhaAtendimento = "";
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT SENHA "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "PERGUNTA_PROCEDIMENTO "
					+ 														   " WHERE COD_UNIDADE = 2 "
					+                                                          "   AND COD_UNIDADE_CADASTRADA = " + codVeiculo);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = (String) values.get(i)[0];
				senhaAtendimento = attList[0];
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return senhaAtendimento;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getContatoProcedimento(int codVeiculo){
		Vector<String[]> contatoProcedimento = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT TIPO_CONTATO.NOME_TIPO "			//0
					+ 														   "     , CONTATO_PROCEDIMENTO.DDD "		//1
					+														   "     , CONTATO_PROCEDIMENTO.NUMERO "	//2
					+ 														   "     , CONTATO_PROCEDIMENTO.COD_PAIS "	//3
					+														   "     , CONTATO_PROCEDIMENTO.NOME "		//4
					+ 														   "     , INFO_CONTATO.NOME_GRAU "			//5
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "CONTATO_PROCEDIMENTO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_CONTATO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "INFO_CONTATO "
					+ 														   " WHERE TIPO_CONTATO.COD_CONTATO = CONTATO_PROCEDIMENTO.COD_CONTATO "
					+														   "   AND INFO_CONTATO.COD_GRAU = CONTATO_PROCEDIMENTO.COD_GRAU "
					+														   "   AND CONTATO_PROCEDIMENTO.COD_UNIDADE = 2 "
					+														   "   AND CONTATO_PROCEDIMENTO.COD_UNIDADE_CADASTRADA = " + codVeiculo);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[6];
				attList[0] = (String) values.get(i)[0].toString();
				attList[1] = (String) values.get(i)[1].toString();
				attList[2] = (String) values.get(i)[2].toString();
				attList[3] = (String) values.get(i)[3].toString();
				attList[4] = (String) values.get(i)[4].toString();
				attList[5] = (String) values.get(i)[5].toString();
				contatoProcedimento.add(attList);
			}

		}catch (SQLException ex){
    		ex.printStackTrace();
		}finally{
			return contatoProcedimento;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getServicoPedido(){
		Vector<String[]> servicoPedido = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT TIPO_SERVICO.NOME_SERVICO "										//0
					+ 														   "     , SERVICO_PEDIDO.QUANTIDADE "										//1
					+														   "     , SERVICO_PEDIDO.VALOR_UNITARIO "									//2
					+														   "     , (SERVICO_PEDIDO.QUANTIDADE * SERVICO_PEDIDO.VALOR_UNITARIO) "	//3
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "SERVICO_PEDIDO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_SERVICO "
					+ 														   " WHERE TIPO_SERVICO.COD_SERVICO = SERVICO_PEDIDO.COD_SERVICO "
					+														   "   AND SERVICO_PEDIDO.COD_PEDIDO = " + COD_PEDIDO_);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[4];
				attList[0] = (String) values.get(i)[0];
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				servicoPedido.add(attList);
			}

		}catch (SQLException ex){
    		ex.printStackTrace();
		}finally{
			return servicoPedido;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEquipAcessorioPedido(){
		Vector<String[]> servicoPedido = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT TIPO_EQUIP_ACESSORIO.NOME_EQUIP_ACESSORIO "										//0
					+ 														   "     , EQUIP_ACESSORIO_PEDIDO.QUANTIDADE "												//1
					+														   "     , EQUIP_ACESSORIO_PEDIDO.VALOR_UNITARIO "											//2
					+														   "     , (EQUIP_ACESSORIO_PEDIDO.QUANTIDADE * EQUIP_ACESSORIO_PEDIDO.VALOR_UNITARIO) "	//3
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "EQUIP_ACESSORIO_PEDIDO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_EQUIP_ACESSORIO "
					+ 														   " WHERE TIPO_EQUIP_ACESSORIO.COD_EQUIP_ACESSORIO = EQUIP_ACESSORIO_PEDIDO.COD_EQUIP_ACESSORIO "
					+														   "   AND EQUIP_ACESSORIO_PEDIDO.COD_PEDIDO = " + COD_PEDIDO_);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[4];
				attList[0] = (String) values.get(i)[0];
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				servicoPedido.add(attList);
			}

		}catch (SQLException ex){
    		ex.printStackTrace();
		}finally{
			return servicoPedido;
		}
	}

	@SuppressWarnings("finally")
	public String getValorTotalServico(){
		String valorTotal = "";
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT SUM(QUANTIDADE*VALOR_UNITARIO) "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "SERVICO_PEDIDO "
					+ 														   " WHERE COD_PEDIDO = " + COD_PEDIDO_);
			valorTotal = values.get(0)[0].toString();
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return valorTotal;
		}
	}

	@SuppressWarnings("finally")
	public String getValorTotalEquipamento(){
		String valorTotal = "";
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT SUM(QUANTIDADE*VALOR_UNITARIO) "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "EQUIP_ACESSORIO_PEDIDO "
					+ 														   " WHERE COD_PEDIDO = " + COD_PEDIDO_);
			valorTotal = values.get(0)[0].toString();
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return valorTotal;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getObsPedido(){
		Vector<String[]> obsPedido = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OBSERVACAO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OBS_PEDIDO "
					+ 														   " WHERE COD_PEDIDO = " + COD_PEDIDO_
					+														   " ORDER BY INDICE ASC ");
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = (String) values.get(i)[0];
				obsPedido.add(attList);;
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return obsPedido;
		}
	}
}