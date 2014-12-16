/*ZIRIX CONTROL CENTER - OS DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class OsDAO extends GenericDAO<PedidoDAO> {


    public OsDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public OsDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
    }
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_NUM_OS",res.getInt("COD_NUM_OS"));
    	setAttValueFor("COD_TECNICO",res.getInt("COD_TECNICO"));
    	setAttValueFor("ARRAIVE_TIME",res.getTime("ARRAIVE_TIME"));
    	setAttValueFor("LEAVE_TIME",res.getTime("LEAVE_TIME"));
    	setAttValueFor("FRUSTRADA",res.getInt("FRUSTRADA"));
    	setAttValueFor("COD_TIPO_OS",res.getInt("COD_TIPO_OS"));
    	setAttValueFor("HAVE_TEST",res.getInt("HAVE_TEST"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("COD_UNIDADE",res.getInt("COD_UNIDADE"));
    	setAttValueFor("TIPO_UNIDADE",res.getInt("TIPO_UNIDADE"));
    	setAttValueFor("COD_AGENDAMENTO",res.getInt("COD_AGENDAMENTO"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return PedidoDAO.createKey("COD_OS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "OS";
   
        
}
