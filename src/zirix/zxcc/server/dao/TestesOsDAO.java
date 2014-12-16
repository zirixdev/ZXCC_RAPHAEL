/*ZIRIX CONTROL CENTER - TESTES OS DAO
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class TestesOsDAO extends GenericDAO<EstadoModuloDAO> {


    public TestesOsDAO(PkList pkList) {
        super(TABLENAME,pkList);
        
    }

    public TestesOsDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_OS", res.getInt("COD_OS"));
    	setAttValueFor("IGNICAO", res.getInt("IGNICAO"));
    	setAttValueFor("BLOQUEIO", res.getInt("BLOQUEIO"));
    	setAttValueFor("GPS", res.getInt("COD_INGPSSTALACAO"));
    	setAttValueFor("GPRS", res.getInt("GPRS"));
    	setAttValueFor("SIRENE", res.getInt("SIRENE"));
    	setAttValueFor("PANICO", res.getInt("PANICO"));
    	setAttValueFor("RPM", res.getInt("RPM"));
    	setAttValueFor("DELETED", res.getInt("DELETED"));
    }

    public Set<String> getPkNamesSet() {    	
    	return EstoqueDAO.createKey("COD_TESTES_OS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "TESTES_OS";
          
}