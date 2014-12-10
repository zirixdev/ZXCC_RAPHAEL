/*ZIRIX CONTROL CENTER - DEFINED PROCESS STATE DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class DefinedProcessStateDAO extends GenericDAO<DefinedProcessStateDAO> {


	public DefinedProcessStateDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public DefinedProcessStateDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("STATE_NAME",res.getString("ESTATE_NAMEMAIL"));
    	setAttValueFor("NEXT_STATE_ID",res.getInt("NEXT_STATE_ID"));
    	setAttValueFor("STATE_NUM",res.getInt("STATE_NUM"));
    }

    public Set<String> getPkNamesSet() {
    	return DefinedProcessStateDAO.createKey("PROCESS_STATE_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "DEFINED_PROCESS_STATE";
}