/*ZIRIX CONTROL CENTER - PERMISSÃO GRUPO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class PermissaoGrupoDAO extends GenericDAO<PermissaoGrupoDAO> {


    public PermissaoGrupoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public PermissaoGrupoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_GRUPO",res.getInt("COD_GRUPO"));
    	setAttValueFor("COD_TELA",res.getInt("COD_TELA"));
    	setAttValueFor("CHAVE",res.getInt("CHAVE"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {
    	return PermissaoGrupoDAO.createKey("COD_PERMISSAO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "PERMISSAO_GRUPO";
   
        
}