/*ZIRIX CONTROL CENTER - CLIENTE PROSPECÇÃO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ClienteProspeccaoDAOService {
	public static Vector<ClienteProspeccaoDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + ClienteProspeccaoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ClienteProspeccaoDAO> daoList = new Vector<ClienteProspeccaoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();
			while (res.next()) {
				int COD_CLIENTE_PROSPECCAO = res.getInt("COD_CLIENTE_PROSPECCAO");
				PkList pkList = new PkList();
				pkList.put("COD_CLIENTE_PROSPECCAO",COD_CLIENTE_PROSPECCAO);
				ClienteProspeccaoDAO dao = new ClienteProspeccaoDAO(pkList);
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
