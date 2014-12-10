/*ZIRIX CONTROL CENTER - EMAIL PROSPECÇÃO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class EmailProspeccaoDAO extends GenericDAO<EmailProspeccaoDAO> {



    public EmailProspeccaoDAO(PkList pkList) {
        super(TABLENAME,pkList);
        setCanDelete(true);
    }

    public EmailProspeccaoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_CLIENTE_PROSPECCAO",res.getInt("COD_CLIENTE_PROSPECCAO"));
    	setAttValueFor("EMAIL",res.getString("EMAIL"));
    }

    public Set<String> getPkNamesSet() {
    	return EmailProspeccaoDAO.createKey("COD_EMAIL", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "EMAIL_PROSPECCAO";
}