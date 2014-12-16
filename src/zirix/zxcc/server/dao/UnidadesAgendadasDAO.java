/*ZIRIX CONTROL CENTER - UNIDADES AGENDADAS DAO
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class UnidadesAgendadasDAO extends GenericDAO<UnidadesAgendadasDAO> {


    public UnidadesAgendadasDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public UnidadesAgendadasDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	} 
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_AGENDAMENTO",res.getInt("COD_AGENDAMENTO"));
    	setAttValueFor("COD_UNIDADE",res.getInt("COD_UNIDADE"));
    	setAttValueFor("TIPO_UNIDADE",res.getInt("TIPO_UNIDADE"));
    	setAttValueFor("ESTADO",res.getInt("ESTADO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    	setAttValueFor("COD_OS",res.getInt("COD_OS"));
    	setAttValueFor("DATA_AGENDAMENTO",res.getDate("DATA_AGENDAMENTO"));
    	setAttValueFor("HORA_AGENDAMENTO",res.getTime("HORA_AGENDAMENTO"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return ObsPedidoDAO.createKey("COD_UNIDADES_AGENDADAS", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "UNIDADES_AGENDADAS";
   
        
}
