/*ZIRIX CONTROL CENTER - CHIP DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;

public class ChipDAO extends GenericDAO<ChipDAO> {


    public ChipDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ChipDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_MODULO",res.getInt("COD_MODULO"));
    	setAttValueFor("NFE",res.getString("NFE"));
    	setAttValueFor("ICCID",res.getString("ICCID"));
    	setAttValueFor("COD_OPERADORA",res.getInt("COD_OPERADORA"));
    	setAttValueFor("APN",res.getString("APN"));
    	setAttValueFor("TECNOLOGIA",res.getString("TECNOLOGIA"));
    	setAttValueFor("COD_STATUS",res.getInt("COD_STATUS"));
    	setAttValueFor("DDD",res.getString("DDD"));
    	setAttValueFor("NUMERO_CHIP",res.getString("NUMERO_CHIP"));
    	/*setAttValueFor("DATA_VIGENCIA",res.getDate("DATA_VIGENCIA"));
    	setAttValueFor("COD_CONTA",res.getInt("COD_CONTA"));
    	setAttValueFor("COD_PACOTE",res.getInt("COD_PACOTE"));*/
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {
    	return ChipDAO.createKey("COD_CHIP", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "CHIP";
   
        
}