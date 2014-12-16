/*ZIRIX CONTROL CENTER - EQUIPAMENTO SERVICE BEAN
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.ModuloDAO;
import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.PkList;

public class EquipamentoServiceBean {

	private ModuloDAO dao_ = null;
	private Integer COD_MODULO_ = null;
	private Integer COD_MODELO_ = null;
	private Integer COD_CHIP_ = null;

	public EquipamentoServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {

		COD_MODULO_ = new Integer((String)pkVal[0]);
		PkList pkList = new PkList();
	    pkList.put("COD_MODULO",COD_MODULO_);
	    dao_ = new ModuloDAO(pkList);

	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {}
		COD_MODELO_ = (Integer)dao_.getAttValueFor("COD_MODELO");
		COD_CHIP_ = (Integer)dao_.getAttValueFor("COD_CHIP");
	}

	public String getID() {
    	String NumModelo = (String)dao_.getAttValueFor("NUMERO_MODULO");	    	
    	return NumModelo;
	}

	public Integer getCodCliente(){
		Integer CodCliente = (Integer)dao_.getAttValueFor("COD_CLIENTE");
		return CodCliente;
	}

	public Integer getModelo(){
		Integer CodModelo = (Integer)dao_.getAttValueFor("COD_MODELO");
		return CodModelo;
	}

	public Integer getCodChip(){
		Integer CodChip = (Integer)dao_.getAttValueFor("COD_CHIP");
		return CodChip;
	}

	public String getNfe() {
    	String Nfe = (String)dao_.getAttValueFor("NFE");	    	
    	return Nfe;
	}

	public Integer getCodEstado(){
		Integer CodEstado = (Integer)dao_.getAttValueFor("COD_ESTADO");
		return CodEstado;
	}

	public Integer getCodInstalacao(){
		Integer CodInstalacao = (Integer)dao_.getAttValueFor("COD_INSTALACAO");
		return CodInstalacao;
	}

	public String getSN() {
    	String SN = (String)dao_.getAttValueFor("SN_MODULO");	    	
    	return SN;
	}

	@SuppressWarnings("finally")
	public Vector<Integer[]> getCodUnidade(){
		Vector<Integer[]> CodUnidade = new Vector<Integer[]>();
		
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "TIPO_UNIDADE.COD_UNIDADE "
					+ " 											 FROM " + ZXMain.DB_NAME_ + "TIPO_UNIDADE "
					+ " 											    , " + ZXMain.DB_NAME_ + "INSTALACAO "
					+ " 											    , " + ZXMain.DB_NAME_ + "MODULO "
					+ "                                             WHERE " + ZXMain.DB_NAME_ + "TIPO_UNIDADE.COD_UNIDADE = " + ZXMain.DB_NAME_ + "INSTALACAO.COD_UNIDADE "
					+ "                                               AND " + ZXMain.DB_NAME_ + "INSTALACAO.COD_INSTALACAO = " + ZXMain.DB_NAME_ + "MODULO.COD_INSTALACAO "
					+ "                                               AND " + ZXMain.DB_NAME_ + "MODULO.COD_MODULO = " + COD_MODULO_);

			for (int i=0;i < values.size();i++) {
				Integer[] attList = new Integer[1];
				attList[0] = Integer.parseInt(values.get(i)[0].toString());
				CodUnidade.add(attList);
			}
			
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return CodUnidade;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getModeloModulo(){
		Vector<String[]> ModeloModulo = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_MODELO, NOME_MODELO, COD_MARCA "
					+ " 											 FROM " + ZXMain.DB_NAME_ + "DESC_MODULO "
					+ "                                             WHERE COD_MODELO = " + COD_MODELO_);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[3];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				ModeloModulo.add(attList);
			}
			
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return ModeloModulo;
		}
	}
	
	@SuppressWarnings("finally")
	public Vector<String[]> getMarcaModulo(){
		Vector<String[]> MarcaModulo = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "MARCA_MODULO.NOME_MARCA "
					+ " 											 FROM " + ZXMain.DB_NAME_ + "DESC_MODULO "
					+ "                                                 , " + ZXMain.DB_NAME_ + "MARCA_MODULO "
					+ "                                             WHERE " + ZXMain.DB_NAME_ + "MARCA_MODULO.COD_MARCA = " + ZXMain.DB_NAME_ + "DESC_MODULO.COD_MARCA"
					+ "                                               AND " + ZXMain.DB_NAME_ + "DESC_MODULO.COD_MODELO = " + COD_MODELO_);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				MarcaModulo.add(attList);
			}
			
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return MarcaModulo;
		}
	}
	
	@SuppressWarnings("finally")
	public Vector<String[]> getIccID(){
		Vector<String[]> IccID = new Vector<String[]>();
		
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ICCID "
					+ " 											 FROM " + ZXMain.DB_NAME_ + "CHIP "
					+ "                                             WHERE COD_CHIP = " + COD_CHIP_);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				IccID.add(attList);
			}
			
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return IccID;
		}
	}
}