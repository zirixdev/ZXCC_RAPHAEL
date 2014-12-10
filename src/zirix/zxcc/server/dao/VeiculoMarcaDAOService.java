/*ZIRIX CONTROL CENTER - VENDEDOR DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class VeiculoMarcaDAOService {

	public static Vector<VeiculoMarcaDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + VeiculoMarcaDAO.TABLENAME + " ORDER BY NOME_MARCA ASC";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<VeiculoMarcaDAO> daoList = new Vector<VeiculoMarcaDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_MARCA = res.getInt("COD_MARCA");

				PkList pkList = new PkList();
				pkList.put("COD_MARCA",COD_MARCA);
				VeiculoMarcaDAO dao = new VeiculoMarcaDAO(pkList);

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
