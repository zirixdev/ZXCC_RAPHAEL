/*ZIRIX CONTROL CENTER - DEFINED WORK DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class DefinedWorkDAO extends GenericDAO<DefinedWorkDAO> {


	public DefinedWorkDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public DefinedWorkDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("WORK_NAME",res.getString("WORK_NAME"));
    	setAttValueFor("PROCESS_ID",res.getInt("PROCESS_ID"));
    	setAttValueFor("RESTRICTION_ID",res.getInt("RESTRICTION_ID"));
    	setAttValueFor("PROCESS_STATE_ID",res.getInt("PROCESS_STATE_ID"));
    	setAttValueFor("DEPENDENCY_WORK",res.getInt("DEPENDENCY_WORK"));
    	setAttValueFor("WORK_ALERT_ID",res.getInt("WORK_ALERT_ID"));
    	setAttValueFor("WORK_USER_ID",res.getInt("WORK_USER_ID"));
    }

    public Set<String> getPkNamesSet() {
    	return DefinedWorkDAO.createKey("WORK_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "DEFINED_WORK";
}