/*ZIRIX CONTROL CENTER - TIPO UNIDADE DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UnidadesAgendadasDAOService {


	public static Vector<UnidadesAgendadasDAO> loadAllForPedido(String COD_PEDIDO) throws SQLException {

		String query = "SELECT * FROM " + UnidadesAgendadasDAO.TABLENAME + " WHERE COD_AGENDAMENTO = (SELECT COD_AGENDAMENTO " +
				"FROM AGENDAMENTO WHERE COD_PEDIDO = " + COD_PEDIDO + ")";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<UnidadesAgendadasDAO> daoList = new Vector<UnidadesAgendadasDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_UNIDADES_AGENDADAS = res.getInt("COD_UNIDADES_AGENDADAS");

				PkList pkList = new PkList();
				pkList.put("COD_UNIDADES_AGENDADAS",COD_UNIDADES_AGENDADAS);
				UnidadesAgendadasDAO dao = new UnidadesAgendadasDAO(pkList);

				dao.read();
				dao.loadAttsFromResultSet(res);
				daoList.add(dao);
			}
			return daoList;
		}catch(SQLException e){ throw e; }
        finally{
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
}
