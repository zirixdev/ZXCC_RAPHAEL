/*ZIRIX CONTROL CENTER - CONTATO CLIENTE DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;

public class ContatoClienteDAO extends GenericDAO<ContatoClienteDAO> {


    public ContatoClienteDAO(PkList pkList) {
        super(TABLENAME,pkList);
        setCanDelete(true);
    }

    public ContatoClienteDAO(){
    	super(TABLENAME);
    }
        
    public static PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("COD_CONTATO",res.getInt("COD_CONTATO"));
    	setAttValueFor("DDD",res.getString("DDD"));
    	setAttValueFor("NUMERO",res.getString("NUMERO"));
    	setAttValueFor("COD_PAIS",res.getInt("COD_PAIS"));
    	setAttValueFor("NOME",res.getString("NOME"));
    	setAttValueFor("COD_GRAU",res.getInt("COD_GRAU"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ContatoClienteDAO.createKey("COD_CONTATO_CLI", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "CONTATO_CLIENTE";
}