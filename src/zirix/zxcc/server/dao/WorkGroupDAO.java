/*ZIRIX CONTROL CENTER - WORK GROUP DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class WorkGroupDAO extends GenericDAO<WorkGroupDAO> {


	public WorkGroupDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public WorkGroupDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("WORK_GROUP_ID",res.getInt("WORK_ID"));
    }

    public Set<String> getPkNamesSet() {
    	return WorkGroupDAO.createKey("WORK_GROUP_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "WORK_GROUP";
}