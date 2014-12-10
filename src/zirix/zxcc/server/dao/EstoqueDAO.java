/*ZIRIX CONTROL CENTER - ESTOQUE DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class EstoqueDAO extends GenericDAO<EstadoModuloDAO> {


    public EstoqueDAO(PkList pkList) {
        super(TABLENAME,pkList);
        
    }

    public EstoqueDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_INSTALACAO", res.getInt("COD_INSTALACAO"));
    	setAttValueFor("DELETED", res.getInt("DELETED"));
    }

    public Set<String> getPkNamesSet() {    	
    	return EstoqueDAO.createKey("COD_ESTOQUE", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "ESTOQUE";
          
}