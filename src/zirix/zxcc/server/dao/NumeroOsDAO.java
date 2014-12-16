/*ZIRIX CONTROL CENTER - NUMERO PEDIDO DAO
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class NumeroOsDAO extends GenericDAO<NumeroPedidoDAO> {


    public NumeroOsDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public NumeroOsDAO(){
    	super(TABLENAME);
    }
        
    public static PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_USUARIO",res.getInt("COD_USUARIO"));
    	setAttValueFor("DATA_GERACAO",res.getDate("DATA_GERACAO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    	setAttValueFor("ANO_OS",res.getInt("ANO_OS"));
    	setAttValueFor("MES_OS",res.getInt("MES_OS"));
    	setAttValueFor("NUM_OS",res.getInt("NUM_OS"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return NumeroPedidoDAO.createKey("COD_NUM_OS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "NUMERO_OS";
   
        
}