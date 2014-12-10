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

public class VeiculoCombustivelDAOService {

	public static Vector<VeiculoCombustivelDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + VeiculoCombustivelDAO.TABLENAME + " ORDER BY NOME_COMBUSTIVEL ASC";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<VeiculoCombustivelDAO> daoList = new Vector<VeiculoCombustivelDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_COMBUSTIVEL = res.getInt("COD_COMBUSTIVEL");

				PkList pkList = new PkList();
				pkList.put("COD_COMBUSTIVEL",COD_COMBUSTIVEL);
				VeiculoCombustivelDAO dao = new VeiculoCombustivelDAO(pkList);

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
