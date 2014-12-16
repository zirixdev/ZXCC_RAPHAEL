/*ZIRIX CONTROL CENTER - MARCA MODULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MarcaModuloDAOService {

	public static Vector<MarcaModuloDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + MarcaModuloDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<MarcaModuloDAO> daoList = new Vector<MarcaModuloDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_MARCA = res.getInt("COD_MARCA");

				PkList pkList = new PkList();
				pkList.put("COD_MARCA",COD_MARCA);
				MarcaModuloDAO dao = new MarcaModuloDAO(pkList);

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
