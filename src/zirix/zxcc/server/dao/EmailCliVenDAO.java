/*ZIRIX CONTROL CENTER - EMAIL CLI VEN DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class EmailCliVenDAO extends GenericDAO<EmailCliVenDAO> {



    public EmailCliVenDAO(PkList pkList) {
        super(TABLENAME,pkList);
        setCanDelete(true);
    }

    public EmailCliVenDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_CLI_VEN",res.getInt("COD_CLI_VEN"));
    	setAttValueFor("TIPO_CLI_VEN",res.getInt("TIPO_CLI_VEN"));
    	setAttValueFor("EMAIL",res.getString("EMAIL"));
    	setAttValueFor("DELETED",res.getString("DELETED"));
    }

    public Set<String> getPkNamesSet() {
    	return EmailCliVenDAO.createKey("COD_EMAIL", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "EMAIL_CLI_VEN";
}