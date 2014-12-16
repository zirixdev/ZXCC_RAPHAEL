/*ZIRIX CONTROL CENTER - MOTIVO HIST MODULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MotivoHistModuloDAOService {

	public static Vector<MotivoHistModuloDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + MotivoHistModuloDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<MotivoHistModuloDAO> daoList = new Vector<MotivoHistModuloDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_MOTIVO = res.getInt("COD_MOTIVO");

				PkList pkList = new PkList();
				pkList.put("COD_MOTIVO",COD_MOTIVO);
				MotivoHistModuloDAO dao = new MotivoHistModuloDAO(pkList);

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
