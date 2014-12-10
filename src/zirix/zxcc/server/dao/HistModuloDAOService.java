/*ZIRIX CONTROL CENTER - HIST MODULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class HistModuloDAOService {

	public static Vector<HistModuloDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + HistModuloDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<HistModuloDAO> daoList = new Vector<HistModuloDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_HIST_MODULO = res.getInt("COD_HIST_MODULO");

				PkList pkList = new PkList();
				pkList.put("COD_HIST_MODULO",COD_HIST_MODULO);
				HistModuloDAO dao = new HistModuloDAO(pkList);

				dao.read();

				dao.loadAttsFromResultSet(res);

				daoList.add(dao);
			}
			return daoList;
		}catch(SQLException e){ throw e; }
        finally {
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
}
