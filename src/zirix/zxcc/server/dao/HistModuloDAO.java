/*ZIRIX CONTROL CENTER - HIST MODULO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class HistModuloDAO extends GenericDAO<HistModuloDAO> {


    public HistModuloDAO(PkList pkList) {
        super(TABLENAME,pkList);
        
    }

    public HistModuloDAO(){
    	super(TABLENAME);
    }
        
    public static PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_MODULO", res.getInt("COD_MODULO"));
    	setAttValueFor("DATA_INGRESSO",res.getDate("DATA_INGRESSO"));
    	setAttValueFor("DATA_REMOVIDO",res.getDate("DATA_REMOVIDO"));
    	setAttValueFor("COD_INSTALACAO", res.getInt("COD_INSTALACAO"));
    	setAttValueFor("COD_MOTIVO", res.getInt("COD_MOTIVO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return HistModuloDAO.createKey("COD_HIST_MODULO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "HIST_MODULO";
}