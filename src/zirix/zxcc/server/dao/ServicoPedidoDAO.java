/*ZIRIX CONTROL CENTER - SERVIÇO PEDIDO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class ServicoPedidoDAO extends GenericDAO<ServicoPedidoDAO> {


    public ServicoPedidoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ServicoPedidoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	
    	setAttValueFor("COD_SERVICO",res.getInt("COD_SERVICO"));
    	setAttValueFor("COD_PEDIDO",res.getInt("COD_PEDIDO"));
    	setAttValueFor("QUANTIDADE",res.getInt("QUANTIDADE"));
    	setAttValueFor("VALOR_UNITARIO",res.getFloat("VALOR_UNITARIO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ServicoPedidoDAO.createKey("COD_SERV_PED", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "SERVICO_PEDIDO";
   
        
}
