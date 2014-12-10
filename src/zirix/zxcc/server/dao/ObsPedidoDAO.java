/*ZIRIX CONTROL CENTER - OBS PEDIDO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class ObsPedidoDAO extends GenericDAO<ObsPedidoDAO> {


    public ObsPedidoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ObsPedidoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	} 
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_PEDIDO",res.getInt("COD_PEDIDO"));
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("INDICE",res.getInt("INDICE"));
    	setAttValueFor("OBSERVACAO",res.getString("OBSERVACAO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ObsPedidoDAO.createKey("COD_OBS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "OBS_PEDIDO";
   
        
}