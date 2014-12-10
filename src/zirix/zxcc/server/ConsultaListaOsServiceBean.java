/*ZIRIX CONTROL CENTER - CONSULTA LISTA OS SERVICE BEAN
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.*;

public class ConsultaListaOsServiceBean {
	private String KEY_ = null;
	Vector<String[]> listaOS_ = new Vector<String[]>();

	public ConsultaListaOsServiceBean(String KEY) {
		KEY_ = KEY.trim();
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getOsVeiculo(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OS.COD_OS "																			//00
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "			//01
					+ 														   "     , DATE_FORMAT(NUMERO_OS.DATA_GERACAO,'%d/%m/%Y') "										//02
					+ 														   "     , CLIENTE.NOME "																		//03
					+ 														   "     , VEICULO.PLACA "																		//04
					+ 														   "     , TIPO_OS.NOME_TIPO "																	//05
					+ 														   "     , DATE_FORMAT(UNIDADES_AGENDADAS.DATA_AGENDAMENTO,'%d/%m/%Y') "						//06
					+ 														   "     , TECNICO.NOME_FANTASIA "																//07
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "CLIENTE "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TECNICO "
					+ 														   " WHERE NUMERO_OS.COD_NUM_OS = OS.COD_NUM_OS "
					+ 														   "   AND CLIENTE.COD_CLIENTE = OS.COD_CLIENTE "
					+ 														   "   AND TIPO_OS.COD_TIPO_OS = OS.COD_TIPO_OS "
					+ 														   "   AND UNIDADES_AGENDADAS.COD_OS = OS.COD_OS "
					+ 														   "   AND TECNICO.COD_TECNICO = OS.COD_TECNICO "
					+ 														   "   AND VEICULO.COD_VEICULO = OS.COD_UNIDADE "
					+ 														   "   AND OS.TIPO_UNIDADE = 2 "
					+ 														   "   AND OS.COD_UNIDADE = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[8];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getOsCliente(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OS.COD_OS "																			//00
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "			//01
					+ 														   "     , DATE_FORMAT(NUMERO_OS.DATA_GERACAO,'%d/%m/%Y') "										//02
					+ 														   "     , CLIENTE.NOME "																		//03
					+ 														   "     , VEICULO.PLACA "																		//04
					+ 														   "     , TIPO_OS.NOME_TIPO "																	//05
					+ 														   "     , DATE_FORMAT(UNIDADES_AGENDADAS.DATA_AGENDAMENTO,'%d/%m/%Y') "						//06
					+ 														   "     , TECNICO.NOME_FANTASIA "																//07
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "CLIENTE "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TECNICO "
					+ 														   " WHERE NUMERO_OS.COD_NUM_OS = OS.COD_NUM_OS "
					+ 														   "   AND CLIENTE.COD_CLIENTE = OS.COD_CLIENTE "
					+ 														   "   AND TIPO_OS.COD_TIPO_OS = OS.COD_TIPO_OS "
					+ 														   "   AND UNIDADES_AGENDADAS.COD_OS = OS.COD_OS "
					+ 														   "   AND TECNICO.COD_TECNICO = OS.COD_TECNICO "
					+ 														   "   AND VEICULO.COD_VEICULO = OS.COD_UNIDADE "
					+ 														   "   AND OS.TIPO_UNIDADE = 2 "
					+ 														   "   AND CLIENTE.COD_CLIENTE = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[8];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getOsTipo(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OS.COD_OS "																			//00
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "			//01
					+ 														   "     , DATE_FORMAT(NUMERO_OS.DATA_GERACAO,'%d/%m/%Y') "										//02
					+ 														   "     , CLIENTE.NOME "																		//03
					+ 														   "     , VEICULO.PLACA "																		//04
					+ 														   "     , TIPO_OS.NOME_TIPO "																	//05
					+ 														   "     , DATE_FORMAT(UNIDADES_AGENDADAS.DATA_AGENDAMENTO,'%d/%m/%Y') "						//06
					+ 														   "     , TECNICO.NOME_FANTASIA "																//07
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "CLIENTE "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TECNICO "
					+ 														   " WHERE NUMERO_OS.COD_NUM_OS = OS.COD_NUM_OS "
					+ 														   "   AND CLIENTE.COD_CLIENTE = OS.COD_CLIENTE "
					+ 														   "   AND TIPO_OS.COD_TIPO_OS = OS.COD_TIPO_OS "
					+ 														   "   AND UNIDADES_AGENDADAS.COD_OS = OS.COD_OS "
					+ 														   "   AND TECNICO.COD_TECNICO = OS.COD_TECNICO "
					+ 														   "   AND VEICULO.COD_VEICULO = OS.COD_UNIDADE "
					+ 														   "   AND OS.TIPO_UNIDADE = 2 "
					+ 														   "   AND OS.COD_TIPO_OS = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[8];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}

	
	@SuppressWarnings("finally")
	public Vector<String[]> getOsData(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OS.COD_OS "																			//00
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "			//01
					+ 														   "     , DATE_FORMAT(NUMERO_OS.DATA_GERACAO,'%d/%m/%Y') "										//02
					+ 														   "     , CLIENTE.NOME "																		//03
					+ 														   "     , VEICULO.PLACA "																		//04
					+ 														   "     , TIPO_OS.NOME_TIPO "																	//05
					+ 														   "     , DATE_FORMAT(UNIDADES_AGENDADAS.DATA_AGENDAMENTO,'%d/%m/%Y') "						//06
					+ 														   "     , TECNICO.NOME_FANTASIA "																//07
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "CLIENTE "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TECNICO "
					+ 														   " WHERE NUMERO_OS.COD_NUM_OS = OS.COD_NUM_OS "
					+ 														   "   AND CLIENTE.COD_CLIENTE = OS.COD_CLIENTE "
					+ 														   "   AND TIPO_OS.COD_TIPO_OS = OS.COD_TIPO_OS "
					+ 														   "   AND UNIDADES_AGENDADAS.COD_OS = OS.COD_OS "
					+ 														   "   AND TECNICO.COD_TECNICO = OS.COD_TECNICO "
					+ 														   "   AND VEICULO.COD_VEICULO = OS.COD_UNIDADE "
					+ 														   "   AND OS.TIPO_UNIDADE = 2 "
					+ 														   "   AND DATE_FORMAT(NUMERO_OS.DATA_GERACAO,'%Y-%m-%d') = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[8];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getOsReferencia(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OS.COD_OS "																			//00
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "			//01
					+ 														   "     , DATE_FORMAT(NUMERO_OS.DATA_GERACAO,'%d/%m/%Y') "										//02
					+ 														   "     , CLIENTE.NOME "																		//03
					+ 														   "     , VEICULO.PLACA "																		//04
					+ 														   "     , TIPO_OS.NOME_TIPO "																	//05
					+ 														   "     , DATE_FORMAT(UNIDADES_AGENDADAS.DATA_AGENDAMENTO,'%d/%m/%Y') "						//06
					+ 														   "     , TECNICO.NOME_FANTASIA "																//07
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "NUMERO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "CLIENTE "
					+ 														   "     , " + ZXMain.DB_NAME_ + "VEICULO "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TIPO_OS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "TECNICO "
					+ 														   " WHERE NUMERO_OS.COD_NUM_OS = OS.COD_NUM_OS "
					+ 														   "   AND CLIENTE.COD_CLIENTE = OS.COD_CLIENTE "
					+ 														   "   AND TIPO_OS.COD_TIPO_OS = OS.COD_TIPO_OS "
					+ 														   "   AND UNIDADES_AGENDADAS.COD_OS = OS.COD_OS "
					+ 														   "   AND TECNICO.COD_TECNICO = OS.COD_TECNICO "
					+ 														   "   AND VEICULO.COD_VEICULO = OS.COD_UNIDADE "
					+ 														   "   AND OS.TIPO_UNIDADE = 2 "
					+ 														   "   AND OS.COD_TIPO_OS = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[8];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}
}
