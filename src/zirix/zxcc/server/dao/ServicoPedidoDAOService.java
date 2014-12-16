/*ZIRIX CONTROL CENTER - TIPO SERVIÇO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: MARIO DE SA VERA
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ServicoPedidoDAOService {

	public static Vector<ServicoPedidoDAO> loadAllForPedido(String COD_PEDIDO) throws SQLException {

		String query = "SELECT * FROM " + ServicoPedidoDAO.TABLENAME + " WHERE COD_PEDIDO = " + COD_PEDIDO;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ServicoPedidoDAO> daoList = new Vector<ServicoPedidoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_SERV_PED = res.getInt("COD_SERV_PED");

				PkList pkList = new PkList();
				pkList.put("COD_SERV_PED",COD_SERV_PED);
				ServicoPedidoDAO dao = new ServicoPedidoDAO(pkList);

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
