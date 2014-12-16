/*ZIRIX CONTROL CENTER - TIPO SERVIï¿½O DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoPedidoDAOService {

	public static Vector<TipoPedidoDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoPedidoDAO.TABLENAME + " WHERE DELETED = 0;";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoPedidoDAO> daoList = new Vector<TipoPedidoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_TIPO = res.getInt("COD_TIPO");

				PkList pkList = new PkList();
				pkList.put("COD_TIPO",COD_TIPO);
				TipoPedidoDAO dao = new TipoPedidoDAO(pkList);

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