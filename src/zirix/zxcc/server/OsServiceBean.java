/*ZIRIX CONTROL CENTER - OS SERVICE BEAN
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO LTDA.
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.*;

public class OsServiceBean {

	private OsDAO dao_ = null;
	private Integer COD_OS_ = null;
	private Integer COD_CLIENTE_ = null;
	private Integer COD_NUM_OS_ = null;
	private Integer COD_TECNICO_ = null;
	private Integer COD_TIPO_OS_ = null;
	private Integer COD_UNIDADE_ = null;
	private Integer TIPO_UNIDADE_ = null;
	private Integer COD_AGENDAMENTO_ = null;

	public OsServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {
		COD_OS_ = new Integer((String)pkVal[0]);
		PkList pkList = new PkList();
	    pkList.put("COD_OS",COD_OS_);
	    dao_ = new OsDAO(pkList);
	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {
	    	setVariables();
	    }
	}

	public void setVariables(){
		COD_CLIENTE_ = (Integer)dao_.getAttValueFor("COD_CLIENTE");
		COD_NUM_OS_ = (Integer)dao_.getAttValueFor("COD_NUM_OS");
		COD_TECNICO_ = (Integer)dao_.getAttValueFor("COD_TECNICO");
		COD_TIPO_OS_ = (Integer)dao_.getAttValueFor("COD_TIPO_OS");
		COD_UNIDADE_ = (Integer)dao_.getAttValueFor("COD_UNIDADE");
		TIPO_UNIDADE_ = (Integer)dao_.getAttValueFor("TIPO_UNIDADE");
		COD_AGENDAMENTO_ = (Integer)dao_.getAttValueFor("COD_AGENDAMENTO");
	}

	public String getCODNUMOS(){
		String horarioChegada = dao_.getAttValueFor("COD_NUM_OS").toString();
		return horarioChegada;
	}
	
	public String getHorarioChegada(){
		String horarioChegada = dao_.getAttValueFor("ARRAIVE_TIME").toString();
		return horarioChegada;
	}

	public String getHorarioSaida(){
		String horarioSaida = dao_.getAttValueFor("LEAVE_TIME").toString();
		return horarioSaida;
	}

	public String getFrustrada(){
		int frustrada = (Integer)dao_.getAttValueFor("FRUSTRADA");
		String retFrustrada = null;
		if(frustrada==1){
			retFrustrada = "Visita Frustrada";
		}else{
			retFrustrada = "Visita Realizada";
		}
		return retFrustrada;
	}

	public String getTipoOS(){
		String tipoOS = new String();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT NOME_TIPO "		//00
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "TIPO_OS "
					+ 														   " WHERE COD_TIPO_OS = " + COD_TIPO_OS_);
			for (int i=0;i < values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				tipoOS = attList[0];
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {}
		return tipoOS;
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getNumeroOS(){
		Vector<String[]> numeroOS = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT CONCAT(ANO_OS,MES_OS,'/',LPAD(NUM_OS,6,0)) "		//00
					+ 														   "     , DATE_FORMAT(DATA_GERACAO,'%d/%m/%Y - %T') "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   " WHERE COD_NUM_OS = " + COD_NUM_OS_);
			for (int i=0;i < values.size();i++) {
				String[] attList = new String[2];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				numeroOS.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return numeroOS;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getDadosCliente(){
		Vector<String[]> dadosCliente = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT NOME "				//00
					+ 														   "     , NOME_FANTASIA "		//01
					+ 														   "     , TIPO "				//02
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "CLIENTE "
					+ 														   " WHERE COD_CLIENTE = " + COD_CLIENTE_);
			for(int i=0;i < values.size();i++){
				String[] attList = new String[3];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				dadosCliente.add(attList);
			}
		}catch(SQLException ex){
    		ex.printStackTrace();
		}finally{
			return dadosCliente;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getDocumentoCliente(int tipo_cli){
		Vector<String[]> documentoCliente = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = new ArrayList<Object[]>();
			if(tipo_cli==0){
				values = DAOManager.getInstance().executeQuery("SELECT NUMERO "				//00
						+ 									   "     , COD_DOCUMENTO "		//01
						+ 									   "  FROM " + ZXMain.DB_NAME_ + "DOCUMENTO_CLIENTE "
						+ 									   " WHERE COD_DOCUMENTO = 2 "
						+ 									   "   AND COD_CLIENTE = " + COD_CLIENTE_);
			}else if(tipo_cli==1){
				values = DAOManager.getInstance().executeQuery("SELECT NUMERO "				//00
						+ 									   "     , COD_DOCUMENTO "		//01
						+ 									   "  FROM " + ZXMain.DB_NAME_ + "DOCUMENTO_CLIENTE "
						+ 									   " WHERE COD_DOCUMENTO = 3 "
						+ 									   "   AND COD_CLIENTE = " + COD_CLIENTE_);
			}
			for(int i=0;i < values.size();i++){
				String[] attList = new String[2];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				documentoCliente.add(attList);
			}
		}catch(SQLException ex){
    		ex.printStackTrace();
		}finally{
			return documentoCliente;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getContatoCliente(){
		Vector<String[]> contatoCliente = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_PAIS "		//00
					+ 														   "     , DDD "			//01
					+ 														   "     , NUMERO "			//02
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "CONTATO_CLIENTE "
					+ 														   " WHERE COD_CLIENTE = " + COD_CLIENTE_);
			for(int i=0;i < values.size();i++){
				String[] attList = new String[3];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				contatoCliente.add(attList);
			}
		}catch(SQLException ex){
    		ex.printStackTrace();
		}finally{
			return contatoCliente;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEnderecoAgendamento(){
		String localAgendamento = new String();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT IFNULL(COD_DADOS_INSTALACAO,'NULO') "
					+ 														   "  FROM AGENDAMENTO "
					+ 														   " WHERE COD_AGENDAMENTO = " + COD_AGENDAMENTO_);
			for(int i=0;i < values.size();i++){
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				localAgendamento = attList[0];
			}
		}catch(SQLException ex){
    		ex.printStackTrace();
		}finally{}
		if(localAgendamento.compareTo("NULO")==0){
			Vector<String[]> enderecoAgendamento = new Vector<String[]>();
			try{
				ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ENDERECO "		//00
						+ 														   "     , COMPLEMENTO "	//01
						+ 														   "     , BAIRRO "			//02
						+ 														   "     , CIDADE "			//03
						+ 														   "     , UF "				//04
						+ 														   "     , NOME_PAIS "		//05
						+ 														   "     , CEP "			//06
						+ 														   "  FROM " + ZXMain.DB_NAME_ + "DADOS_AGENDAMENTO "
						+														   "     , " + ZXMain.DB_NAME_ + "PAIS "
						+ 														   " WHERE PAIS.COD_PAIS = DADOS_AGENDAMENTO.COD_PAIS"
						+ 														   "   AND COD_AGENDAMENTO = " + COD_AGENDAMENTO_);
				for(int i=0;i < values.size();i++){
					String[] attList = new String[7];
					attList[0] = values.get(i)[0].toString();
					attList[1] = values.get(i)[1].toString();
					attList[2] = values.get(i)[2].toString();
					attList[3] = values.get(i)[3].toString();
					attList[4] = values.get(i)[4].toString();
					switch(Integer.parseInt(attList[4])){
						case 1:  attList[4] = "AC"; break; case 2:  attList[4] = "AL"; break; case 3:  attList[4] = "AP"; break; case 4:  attList[4] = "AM"; break; case 5:  attList[4] = "BA"; break; case 6:  attList[4] = "CE"; break; case 7:  attList[4] = "DF"; break; case 8:  attList[4] = "ES"; break; case 9:  attList[4] = "GO"; break; case 10:  attList[4] = "MA"; break; case 11:  attList[4] = "MT"; break; case 12:  attList[4] = "MS"; break; case 13:  attList[4] = "MG"; break; case 14:  attList[4] = "PA"; break; case 15:  attList[4] = "PB"; break; case 16:  attList[4] = "PR"; break; case 17:  attList[4] = "PE"; break; case 18:  attList[4] = "PI"; break; case 19:  attList[4] = "RJ"; break; case 20:  attList[4] = "RN"; break; case 21:  attList[4] = "RS"; break; case 22:  attList[4] = "RO"; break; case 23:  attList[4] = "RR"; break; case 24:  attList[4] = "SC"; break; case 25:  attList[4] = "SP"; break; case 26:  attList[4] = "SE"; break; case 27:  attList[4] = "TO"; break; case 28:  attList[4] = "OUTRO"; break;
					}
					attList[5] = values.get(i)[5].toString();
					attList[6] = values.get(i)[6].toString();
					enderecoAgendamento.add(attList);
				}
			}catch(SQLException ex){
	    		ex.printStackTrace();
			}finally{
				return enderecoAgendamento;
			}
		}else{
			Vector<String[]> enderecoAgendamento = new Vector<String[]>();
			try{
				ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ENDERECO "		//00
						+ 														   "     , COMPLEMENTO "	//01
						+ 														   "     , BAIRRO "			//02
						+ 														   "     , CIDADE "			//03
						+ 														   "     , UF "				//04
						+ 														   "     , NOME_PAIS "		//05
						+ 														   "     , CEP "			//06
						+ 														   "  FROM " + ZXMain.DB_NAME_ + "DADOS_INSTALACAO "
						+														   "     , " + ZXMain.DB_NAME_ + "PAIS "
						+ 														   " WHERE PAIS.COD_PAIS = DADOS_INSTALACAO.COD_PAIS"
						+ 														   "   AND COD_DADOS_INSTALACAO = " + localAgendamento);
				for(int i=0;i < values.size();i++){
					String[] attList = new String[7];
					attList[0] = values.get(i)[0].toString();
					attList[1] = values.get(i)[1].toString();
					attList[2] = values.get(i)[2].toString();
					attList[3] = values.get(i)[3].toString();
					attList[4] = values.get(i)[4].toString();
					switch(Integer.parseInt(attList[4])){
						case 1:  attList[4] = "AC"; break; case 2:  attList[4] = "AL"; break; case 3:  attList[4] = "AP"; break; case 4:  attList[4] = "AM"; break; case 5:  attList[4] = "BA"; break; case 6:  attList[4] = "CE"; break; case 7:  attList[4] = "DF"; break; case 8:  attList[4] = "ES"; break; case 9:  attList[4] = "GO"; break; case 10:  attList[4] = "MA"; break; case 11:  attList[4] = "MT"; break; case 12:  attList[4] = "MS"; break; case 13:  attList[4] = "MG"; break; case 14:  attList[4] = "PA"; break; case 15:  attList[4] = "PB"; break; case 16:  attList[4] = "PR"; break; case 17:  attList[4] = "PE"; break; case 18:  attList[4] = "PI"; break; case 19:  attList[4] = "RJ"; break; case 20:  attList[4] = "RN"; break; case 21:  attList[4] = "RS"; break; case 22:  attList[4] = "RO"; break; case 23:  attList[4] = "RR"; break; case 24:  attList[4] = "SC"; break; case 25:  attList[4] = "SP"; break; case 26:  attList[4] = "SE"; break; case 27:  attList[4] = "TO"; break; case 28:  attList[4] = "OUTRO"; break;
					}
					attList[5] = values.get(i)[5].toString();
					attList[6] = values.get(i)[6].toString();
					enderecoAgendamento.add(attList);
				}
			}catch(SQLException ex){
	    		ex.printStackTrace();
			}finally{
				return enderecoAgendamento;
			}
		}
	}

	public Vector<String[]> getDadosUnidade(){
		Vector<String[]> dadosUnidade = new Vector<String[]>();
		switch(TIPO_UNIDADE_){
		case 2:
			try{
				ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT PLACA "											//00
						+ 														   "     , CHASSI "											//01
						+ 														   "     , RENAVAN "										//02
						+ 														   "     , ANO_FAB "										//03
						+ 														   "     , ANO_MOD "										//04
						+ 														   "     , NOME_MARCA "										//05
						+ 														   "     , MODELO "											//06
						+ 														   "     , COR "											//07
						+ 														   "     , NOME_COMBUSTIVEL "								//08
						+ 														   "     , DATE_FORMAT(DATA_ULT_VISTORIA,'%d/%m/%Y') "		//09
						+ 														   "     , KM "												//10
						+ 														   "  FROM " + ZXMain.DB_NAME_ + "VEICULO "
						+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO_MARCA "
						+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO_COMBUSTIVEL "
						+ 														   " WHERE VEICULO_MARCA.COD_MARCA = VEICULO.COD_MARCA "
						+ 														   "   AND VEICULO_COMBUSTIVEL.COD_COMBUSTIVEL = VEICULO.COD_COMBUSTIVEL "
						+ 														   "   AND VEICULO.COD_VEICULO = " + COD_UNIDADE_);
				for(int i=0;i < values.size();i++){
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
					dadosUnidade.add(attList);
				}
			}catch(SQLException ex){
	    		ex.printStackTrace();
			}finally{}
			break;
		}
		return dadosUnidade;
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getTestesOS(){
		Vector<String[]> testesOS = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT IF(IGNICAO=1,'checked',' ') "	//00
					+ 														   "     , IF(BLOQUEIO=1,'checked',' ') "	//01
					+ 														   "     , IF(GPS=1,'checked',' ') "		//02
					+ 														   "     , IF(GPRS=1,'checked',' ') "		//03
					+ 														   "     , IF(SIRENE=1,'checked',' ') "		//04
					+ 														   "     , IF(PANICO=1,'checked',' ') "		//05
					+ 														   "     , IF(RPM=1,'checked',' ') "		//06
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "TESTES_OS "
					+ 														   " WHERE COD_OS = " + COD_OS_);
			for (int i=0;i < values.size();i++) {
				String[] attList = new String[7];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				testesOS.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return testesOS;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getObsOS(){
		Vector<String[]> obsOS = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OBSERVACAO "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OBS_OS "
					+ 														   " WHERE COD_OS = " + COD_OS_
					+														   " ORDER BY INDICE ASC ");
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				obsOS.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return obsOS;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getTecnicoOS(){
		Vector<String[]> tecnicoOS = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT NOME_FANTASIA"
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "TECNICO "
					+ 														   " WHERE COD_TECNICO = " + COD_TECNICO_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				tecnicoOS.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return tecnicoOS;
		}
	}

	@SuppressWarnings("finally")
	public String[] getAgendadoPara(){
		String[] agendadoPara = new String[2];
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT DATE_FORMAT(DATA_AGENDAMENTO,'%d/%m/%Y') "		//00
					+ 														   "     , DATE_FORMAT(HORA_AGENDAMENTO,'%H:%i') "			//01
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   " WHERE COD_UNIDADE = " + COD_UNIDADE_
					+ 														   "   AND TIPO_UNIDADE = " + TIPO_UNIDADE_
					+ 														   "   AND COD_AGENDAMENTO = " + COD_AGENDAMENTO_);
			for (int i=0;i<values.size();i++) {
				agendadoPara[0] = values.get(i)[0].toString();
				agendadoPara[1] = values.get(i)[1].toString();
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{
			return agendadoPara;
		}
	}
}