/*ZIRIX CONTROL CENTER - SCHEDULE PROCESS DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class SchedProcessDAO extends GenericDAO<SchedProcessDAO> {


	public SchedProcessDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public SchedProcessDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("PROCESS_NAME",res.getString("PROCESS_NAME"));
    	setAttValueFor("STATE_ID",res.getInt("STATE_ID"));
    }

    public Set<String> getPkNamesSet() {
    	return SchedProcessDAO.createKey("PROCESS_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "SCHED_PROCESS";
}