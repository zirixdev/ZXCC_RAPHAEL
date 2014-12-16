/*ZIRIX CONTROL CENTER - OBS AGENDAMENTO DAO
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class ObsAgendamentoDAO extends GenericDAO<ObsPedidoDAO> {


    public ObsAgendamentoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ObsAgendamentoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	} 
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_AGENDAMENTO",res.getInt("COD_AGENDAMENTO"));
    	setAttValueFor("INDICE",res.getInt("INDICE"));
    	setAttValueFor("OBSERVACAO",res.getString("OBSERVACAO"));
    	setAttValueFor("CHAVE",res.getInt("CHAVE"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ObsPedidoDAO.createKey("COD_OBS_AGENDAMENTO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "OBS_AGENDAMENTO";
   
        
}