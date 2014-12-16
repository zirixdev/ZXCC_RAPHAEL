/*ZIRIX CONTROL CENTER - CONTATO PROSPECÇÃO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;

public class ContatoProspeccaoDAO extends GenericDAO<ContatoProspeccaoDAO> {


    public ContatoProspeccaoDAO(PkList pkList) {
        super(TABLENAME,pkList);
        setCanDelete(true);
    }

    public ContatoProspeccaoDAO(){
    	super(TABLENAME);
    }
        
    public static PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_CLIENTE_PROSPECCAO",res.getInt("COD_CLIENTE_PROSPECCAO"));
    	setAttValueFor("COD_CONTATO",res.getInt("COD_CONTATO"));
    	setAttValueFor("DDD",res.getString("DDD"));
    	setAttValueFor("NUMERO",res.getString("NUMERO"));
    	setAttValueFor("COD_PAIS",res.getInt("COD_PAIS"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ContatoProspeccaoDAO.createKey("COD_CONTATO_PROS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "CONTATO_PROSPECCAO";
}