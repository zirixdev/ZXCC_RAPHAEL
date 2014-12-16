/*ZIRIX CONTROL CENTER - EQUIPAMENTO E ACESSORIO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class EquipAcessorioPedidoDAO extends GenericDAO<EquipAcessorioPedidoDAO> {

    public EquipAcessorioPedidoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public EquipAcessorioPedidoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
    }

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_EQUIP_ACESSORIO",res.getInt("COD_EQUIP_ACESSORIO"));
    	setAttValueFor("COD_PEDIDO",res.getInt("COD_PEDIDO"));
    	setAttValueFor("QUANTIDADE",res.getInt("QUANTIDADE"));
    	setAttValueFor("VALOR_UNITARIO",res.getFloat("VALOR_UNITARIO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return EquipAcessorioPedidoDAO.createKey("COD_EQUIP_ACESSORIO_PEDIDO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "EQUIP_ACESSORIO_PEDIDO";
   
        
}
