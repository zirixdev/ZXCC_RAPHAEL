/*ZIRIX CONTROL CENTER - CONTATO PROCEDIMENTO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class ContatoProcedimentoDAO extends GenericDAO<ContatoProcedimentoDAO> {

    public ContatoProcedimentoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ContatoProcedimentoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("COD_UNIDADE_CADASTRADA",res.getInt("COD_UNIDADE_CADASTRADA"));
    	setAttValueFor("COD_UNIDADE",res.getInt("COD_UNIDADE"));
    	setAttValueFor("COD_CONTATO",res.getInt("COD_CONTATO"));
    	setAttValueFor("DDD",res.getString("DDD"));
    	setAttValueFor("NUMERO",res.getString("NUMERO"));
    	setAttValueFor("COD_PAIS",res.getInt("COD_PAIS"));
    	setAttValueFor("NOME",res.getString("NOME"));
    	setAttValueFor("COD_GRAU",res.getInt("COD_GRAU"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ContatoProcedimentoDAO.createKey("COD_CONTATO_PROCEDIMENTO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "CONTATO_PROCEDIMENTO";
   
        
}