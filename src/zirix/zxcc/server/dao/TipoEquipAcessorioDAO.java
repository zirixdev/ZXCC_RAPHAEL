/*ZIRIX CONTROL CENTER - TIPO EQUIPAMENTO E ACESSORIO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class TipoEquipAcessorioDAO extends GenericDAO<TipoEquipAcessorioDAO> {

    public TipoEquipAcessorioDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public TipoEquipAcessorioDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("NOME_EQUIP_ACESSORIO",res.getString("NOME_EQUIP_ACESSORIO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return TipoEquipAcessorioDAO.createKey("COD_EQUIP_ACESSORIO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "TIPO_EQUIP_ACESSORIO";
   
        
}