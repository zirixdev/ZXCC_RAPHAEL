/*ZIRIX CONTROL CENTER - TIPO DOCUMENTO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoDocumentoDAOService {

	public static Vector<TipoDocumentoDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoDocumentoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoDocumentoDAO> daoList = new Vector<TipoDocumentoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_DOCUMENTO = res.getInt("COD_DOCUMENTO");							

				PkList pkList = new PkList();
				pkList.put("COD_DOCUMENTO",COD_DOCUMENTO);
				TipoDocumentoDAO dao = new TipoDocumentoDAO(pkList);

				dao.read();

				dao.loadAttsFromResultSet(res);

				daoList.add(dao);
			}

			return daoList;

		} catch(SQLException e){ throw e; }

        finally {

        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
}
