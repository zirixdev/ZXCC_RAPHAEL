/*ZIRIX CONTROL CENTER - DOCUMENTO CLIENTE DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class DocumentoClienteDAO extends GenericDAO<DocumentoClienteDAO> {


    public DocumentoClienteDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public DocumentoClienteDAO(){
    	super(TABLENAME);
    }
        
    public static PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("COD_DOCUMENTO",res.getInt("COD_DOCUMENTO"));
    	setAttValueFor("NUMERO",res.getString("NUMERO"));
    	setAttValueFor("DATA_EMISSAO",res.getDate("DATA_EMISSAO"));
    	setAttValueFor("ORGAO_EMISSOR",res.getString("ORGAO_EMISSOR")); 
    	setAttValueFor("DELETED",res.getInt("DELETED"));	        	    	   
    }
    
    public Set<String> getPkNamesSet() {    	
    	return DocumentoClienteDAO.createKey("COD_DOCUMENTO_CLI", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "DOCUMENTO_CLIENTE";
}