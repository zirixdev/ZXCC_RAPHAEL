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

public class TipoUnidadeDAOService {

	public static Vector<TipoUnidadeDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoUnidadeDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoUnidadeDAO> daoList = new Vector<TipoUnidadeDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_UNIDADE = res.getInt("COD_UNIDADE");

				PkList pkList = new PkList();
				pkList.put("COD_UNIDADE",COD_UNIDADE);
				TipoUnidadeDAO dao = new TipoUnidadeDAO(pkList);

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

	public static Vector<TipoUnidadeDAO> loadAllForPedido(String COD_PEDIDO) throws SQLException {

		String query = "SELECT * FROM " + TipoUnidadeDAO.TABLENAME + " WHERE COD_UNIDADE = (SELECT " +
				"COD_UNIDADE FROM UNIDADES_AGENDADAS WHERE COD_AGENDAMENTO = (SELECT COD_AGENDAMENTO " +
				"FROM AGENDAMENTO WHERE COD_PEDIDO = " + COD_PEDIDO + "))";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoUnidadeDAO> daoList = new Vector<TipoUnidadeDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_UNIDADE = res.getInt("COD_UNIDADE");

				PkList pkList = new PkList();
				pkList.put("COD_UNIDADE",COD_UNIDADE);
				TipoUnidadeDAO dao = new TipoUnidadeDAO(pkList);

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
