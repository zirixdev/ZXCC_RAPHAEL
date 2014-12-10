/*ZIRIX CONTROL CENTER - OBS OS DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class ObsOsDAO extends GenericDAO<ObsPedidoDAO> {


    public ObsOsDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ObsOsDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	} 
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_OS",res.getInt("COD_OS"));
    	setAttValueFor("INDICE",res.getInt("INDICE"));
    	setAttValueFor("OBSERVACAO",res.getString("OBSERVACAO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ObsPedidoDAO.createKey("COD_OBS_OS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "OBS_OS";
   
        
}