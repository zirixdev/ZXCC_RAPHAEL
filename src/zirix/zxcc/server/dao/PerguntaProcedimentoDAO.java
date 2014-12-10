/*ZIRIX CONTROL CENTER - PERGUNTA PROCEDIMENTO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class PerguntaProcedimentoDAO extends GenericDAO<PerguntaProcedimentoDAO> {

    public PerguntaProcedimentoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public PerguntaProcedimentoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_UNIDADE",res.getInt("COD_UNIDADE"));
    	setAttValueFor("SENHA",res.getString("SENHA"));
    	setAttValueFor("COD_UNIDADE_CADASTRADA",res.getInt("COD_UNIDADE_CADASTRADA"));
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return PerguntaProcedimentoDAO.createKey("COD_PERGUNTA_PROCEDIMENTO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "PERGUNTA_PROCEDIMENTO";
   
        
}