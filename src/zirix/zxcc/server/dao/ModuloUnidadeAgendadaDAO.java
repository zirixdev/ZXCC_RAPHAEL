/*ZIRIX CONTROL CENTER - MODULO UNIDADE AGENDAR DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/
package zirix.zxcc.server.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;
public class ModuloUnidadeAgendadaDAO extends GenericDAO<ModuloUnidadeAgendadaDAO> {
	public ModuloUnidadeAgendadaDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }
    public ModuloUnidadeAgendadaDAO(){
    	super(TABLENAME);
    }
    public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_MODULO", res.getInt("COD_MODULO"));
    	setAttValueFor("COD_UNIDADE", res.getInt("COD_UNIDADE"));
    	setAttValueFor("TIPO_UNIDADE", res.getInt("TIPO_UNIDADE"));
    }
    public Set<String> getPkNamesSet() {
    	return ModuloUnidadeAgendadaDAO.createKey("COD_MODULO_UNIDADE_AGENDADA", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }
	public final static String TABLENAME = ZXMain.DB_NAME_ + "MODULO_UNIDADE_AGENDADA";
}