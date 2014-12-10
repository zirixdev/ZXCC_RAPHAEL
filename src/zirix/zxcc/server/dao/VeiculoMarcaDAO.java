/*ZIRIX CONTROL CENTER - VEICULO MARCA DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class VeiculoMarcaDAO extends GenericDAO<VeiculoMarcaDAO> {


    public VeiculoMarcaDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public VeiculoMarcaDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}
            
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	
    	setAttValueFor("NOME_MARCA",res.getString("NOME_MARCA"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }

    public Set<String> getPkNamesSet() {
    	return VeiculoMarcaDAO.createKey("COD_MARCA", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "VEICULO_MARCA";
}