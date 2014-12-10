/*ZIRIX CONTROL CENTER - INSTALA��O DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class InstalacaoDAO extends GenericDAO<InstalacaoDAO> {


    public InstalacaoDAO(PkList pkList) {
        super(TABLENAME,pkList);
        
    }

    public InstalacaoDAO(){
    	super(TABLENAME);
    }
        
    public PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_MODULO", res.getInt("COD_MODULO"));
    	setAttValueFor("COD_UNIDADE", res.getInt("COD_UNIDADE"));
    	setAttValueFor("DELETED", res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ClienteDAO.createKey("COD_INSTALACAO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "INSTALACAO";
}