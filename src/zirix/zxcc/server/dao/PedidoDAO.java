/*ZIRIX CONTROL CENTER - PEDIDO DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class PedidoDAO extends GenericDAO<PedidoDAO> {


    public PedidoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public PedidoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_VENDEDOR",res.getInt("COD_VENDEDOR"));
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("NUM_PEDIDO",res.getInt("NUM_PEDIDO"));
    	setAttValueFor("COD_TIPO",res.getInt("COD_TIPO"));
    	setAttValueFor("DATA_VENCIMENTO",res.getInt("DATA_VENCIMENTO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    	setAttValueFor("INFO_PEDIDO",res.getString("INFO_PEDIDO"));
    	setAttValueFor("BOLETO_EMAIL",res.getInt("BOLETO_EMAIL"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return PedidoDAO.createKey("COD_PEDIDO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "PEDIDO";
   
        
}