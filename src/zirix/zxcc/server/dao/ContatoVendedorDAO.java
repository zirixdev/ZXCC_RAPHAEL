/*ZIRIX CONTROL CENTER - CONTATO VENDEDOR DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class ContatoVendedorDAO extends GenericDAO<ContatoVendedorDAO> {

    public ContatoVendedorDAO(PkList pkList) {
        super(TABLENAME,pkList);
        setCanDelete(true);
    }

    public ContatoVendedorDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_VENDEDOR",res.getInt("COD_VENDEDOR"));
    	setAttValueFor("COD_CONTATO",res.getInt("COD_CONTATO"));
    	setAttValueFor("DDD",res.getString("DDD"));
    	setAttValueFor("NUMERO",res.getString("NUMERO"));
    	setAttValueFor("COD_PAIS",res.getInt("COD_PAIS"));
    	setAttValueFor("NOME",res.getString("NOME"));
    }

    public Set<String> getPkNamesSet() {    	
    	return ContatoVendedorDAO.createKey("COD_CONTATO_VEN", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "CONTATO_VENDEDOR";

}