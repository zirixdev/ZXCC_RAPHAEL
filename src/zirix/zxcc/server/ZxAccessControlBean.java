/*ZIRIX CONTROL CENTER - ZX ACCESS CONTROL BEAN
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.UsuarioDAO;
import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.PkList;


public class ZxAccessControlBean {

	private UsuarioDAO dao_ = null;
	private Integer COD_USUARIO_ = null;

	public ZxAccessControlBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {

		COD_USUARIO_ = new Integer((String)pkVal[0]);

		PkList pkList = new PkList();
	    pkList.put("COD_USUARIO",COD_USUARIO_);
	    dao_ = new UsuarioDAO(pkList);

	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {
	    }
	}

	public String getNomeUsuario() {
		String nomeUsuario = (String)dao_.getAttValueFor("NOME_USUARIO");	    	
		return nomeUsuario;
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getPermissaoUsuario(){
		Vector<String[]> PermissaoUsuario = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "PERMISSAO_USUARIO.COD_TELA "
					+ "                                                              , " + ZXMain.DB_NAME_ + "PERMISSAO_USUARIO.CHAVE "
					+ "                                                           FROM " + ZXMain.DB_NAME_ + "PERMISSAO_USUARIO "
					+ "                                                          WHERE " + ZXMain.DB_NAME_ + "PERMISSAO_USUARIO.COD_USUARIO = " + COD_USUARIO_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[2]; // pois eu sei que sao 2 atributos de fato !
			    attList[0] = values.get(i)[0].toString();
			    attList[1] = values.get(i)[1].toString();
			    PermissaoUsuario.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return PermissaoUsuario;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getCodTela(){
		Vector<String[]> CodTela = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "TELA.COD_TELA "
					+ "                                                           FROM " + ZXMain.DB_NAME_ + "TELA ");

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[1]; // pois eu sei que é 1 atributo de fato !
			    attList[0] = values.get(i)[0].toString();
			    CodTela.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return CodTela;
		}
	}
}
