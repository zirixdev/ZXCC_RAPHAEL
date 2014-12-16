/*ZIRIX CONTROL CENTER - TIPO SERVIÇO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoServicoDAOService {

	public static Vector<TipoServicoDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoServicoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoServicoDAO> daoList = new Vector<TipoServicoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_SERVICO = res.getInt("COD_SERVICO");

				PkList pkList = new PkList();
				pkList.put("COD_SERVICO",COD_SERVICO);
				TipoServicoDAO dao = new TipoServicoDAO(pkList);

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