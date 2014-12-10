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
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_OS "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   " WHERE TIPO_UNIDADE = 2 "
					+ 														   "   AND COD_UNIDADE = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
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
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_OS "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   " WHERE COD_CLIENTE = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
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
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_OS "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   " WHERE COD_TIPO_OS = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}

	
	public Vector<String[]> getOsData(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_OS "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   " WHERE DATA_GERACAO = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}

	public Vector<String[]> getOsReferencia(){
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_OS "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "     , "
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "OS "
					+ 														   " WHERE COD_TIPO_OS = " + KEY_);
			for (int i=0;i<values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				listaOS_.add(attList);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			return listaOS_;
		}
	}
}
