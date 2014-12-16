/*ZIRIX CONTROL CENTER - CLIENTE PROSPECT SERVICE BEAN
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.ClienteProspeccaoDAO;
import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.PkList;

public class ClienteProspectServiceBean {

	private ClienteProspeccaoDAO dao_ = null;
	private Integer COD_CLIENTE_PROSPECCAO_ = null;

	public ClienteProspectServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {

		COD_CLIENTE_PROSPECCAO_ = new Integer((String)pkVal[0]);
		PkList pkList = new PkList();
	    pkList.put("COD_CLIENTE_PROSPECCAO",COD_CLIENTE_PROSPECCAO_);
	    dao_ = new ClienteProspeccaoDAO(pkList);
	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {
	    }
	}

	public Integer getCodVendedor(){
		Integer codVendedor = (Integer)dao_.getAttValueFor("COD_VENDEDOR");
		return codVendedor;
	}

	public Integer getTipo(){
		Integer tipo = (Integer)dao_.getAttValueFor("TIPO");
		return tipo;
	}

	public String getNome() {
    	String nome = (String)dao_.getAttValueFor("NOME");
    	return nome;
	}

	public String getNomeFantasia() {
    	String nomeFantasia = (String)dao_.getAttValueFor("NOME_FANTASIA");
    	return nomeFantasia;
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getContato(){
		Vector<String[]> contatoClienteList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "TIPO_CONTATO.NOME_TIPO "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO.DDD "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO.NUMERO "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO.COD_PAIS "
		    		+ "							   FROM " + ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO "
					+ "                               , " + ZXMain.DB_NAME_ + "TIPO_CONTATO "
		    		+ "							  WHERE " + ZXMain.DB_NAME_ + "TIPO_CONTATO.COD_CONTATO = " + ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO.COD_CONTATO "
		    		+ "                             AND " + ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO.COD_CLIENTE_PROSPECCAO = " + COD_CLIENTE_PROSPECCAO_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[4];
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
			    attList[2] = (String) values.get(i)[2];
			    attList[3] = values.get(i)[3].toString();
			    contatoClienteList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return contatoClienteList;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEmail(){
		Vector<String[]> emailClienteList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT EMAIL "
		    		+ "							   FROM " + ZXMain.DB_NAME_ + "EMAIL_PROSPECCAO "
		    		+ "							  WHERE COD_CLIENTE_PROSPECCAO = " + COD_CLIENTE_PROSPECCAO_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[1];
			    attList[0] = (String) values.get(i)[0];
			    emailClienteList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return emailClienteList;
		}
	}
}