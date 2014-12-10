/*ZIRIX CONTROL CENTER - GROUP ALERT DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class GroupAlertDAO extends GenericDAO<GroupAlertDAO> {


	public GroupAlertDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public GroupAlertDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("GROUP_NAME",res.getString("GROUP_NAME"));
    }

    public Set<String> getPkNamesSet() {
    	return GroupAlertDAO.createKey("GROUP_ALERT_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "GROUP_ALERT";
}