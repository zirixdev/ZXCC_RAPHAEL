/*ZIRIX CONTROL CENTER - RESTRICTION WORK DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class RestrictionWorkDAO extends GenericDAO<RestrictionWorkDAO> {


	public RestrictionWorkDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public RestrictionWorkDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("RESTRICTION_VALUES",res.getTime("RESTRICTION_VALUES"));
    }

    public Set<String> getPkNamesSet() {
    	return RestrictionWorkDAO.createKey("RESTRICTION_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "RESTRICTION_WORK";
}