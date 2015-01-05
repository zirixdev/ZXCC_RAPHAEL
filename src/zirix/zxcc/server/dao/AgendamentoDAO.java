/*ZIRIX CONTROL CENTER - AGENDAMENTO DAO
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;

public class AgendamentoDAO extends GenericDAO<AgendamentoDAO> {


	public AgendamentoDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public AgendamentoDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_PEDIDO",res.getInt("COD_PEDIDO"));
    	setAttValueFor("ESTADO",res.getInt("ESTADO"));
    	setAttValueFor("COD_DADOS_INSTALACAO",res.getInt("COD_DADOS_INSTALACAO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }

    public Set<String> getPkNamesSet() {
    	return AnexoPedidoDAO.createKey("COD_AGENDAMENTO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "AGENDAMENTO";
}