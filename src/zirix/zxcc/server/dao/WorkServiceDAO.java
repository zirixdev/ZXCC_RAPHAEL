/*ZIRIX CONTROL CENTER - WORK SERVICE DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class WorkServiceDAO extends GenericDAO<WrokAlertDAO> {


	public WorkServiceDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public WorkServiceDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("SERVICE_NAME",res.getString("SERVICE_NAME"));
    	setAttValueFor("PROCESS_ID",res.getInt("PROCESS_ID"));
    	setAttValueFor("WORK_ID",res.getInt("WORK_ID"));
    }

    public Set<String> getPkNamesSet() {
    	return WrokAlertDAO.createKey("WORK_SERVICE_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "WORK_SERVICE";
}