/*ZIRIX CONTROL CENTER - CLIENTE DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: M�RIO DE S� VERA & RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;

public class ClienteDAO extends GenericDAO<ClienteDAO> {


    public ClienteDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ClienteDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("NOME",res.getString("NOME"));
    	setAttValueFor("TIPO",res.getInt("TIPO"));
    	setAttValueFor("NOME_FANTASIA",res.getString("NOME_FANTASIA"));
    	setAttValueFor("SITE",res.getString("SITE"));
    	setAttValueFor("DATA_NASCIMENTO",res.getDate("DATA_NASCIMENTO"));
    	setAttValueFor("DATA_INGRESSO",res.getDate("DATA_INGRESSO"));
    	setAttValueFor("COD_VENDEDOR", res.getInt("COD_VENDEDOR"));  
    	setAttValueFor("DELETED",res.getInt("DELETED"));   	
    }

    public Set<String> getPkNamesSet() {
    	return ClienteDAO.createKey("COD_CLIENTE", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "CLIENTE";
}