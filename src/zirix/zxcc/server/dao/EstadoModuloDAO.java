/*ZIRIX CONTROL CENTER - ESTADO MODULO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class EstadoModuloDAO extends GenericDAO<EstoqueDAO> {


    public EstadoModuloDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public EstadoModuloDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("NOME_ESTADO", res.getString("NOME_ESTADO"));
    	setAttValueFor("DELETED", res.getInt("DELETED"));
    }

    public Set<String> getPkNamesSet() {    	
    	return EstadoModuloDAO.createKey("COD_ESTADO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "ESTADO_MODULO";
          
}