/*ZIRIX CONTROL CENTER - TIPO OS DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoOsDAOService {

	public static Vector<TipoOsDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoOsDAO.TABLENAME + " WHERE DELETED = 0;";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoOsDAO> daoList = new Vector<TipoOsDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_TIPO_OS = res.getInt("COD_TIPO_OS");

				PkList pkList = new PkList();
				pkList.put("COD_TIPO_OS",COD_TIPO_OS);
				TipoOsDAO dao = new TipoOsDAO(pkList);

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