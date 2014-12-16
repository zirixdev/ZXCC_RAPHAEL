/*ZIRIX CONTROL CENTER - TIPO ENDEREÇO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoEnderecoDAOService {

	public static Vector<TipoEnderecoDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoEnderecoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoEnderecoDAO> daoList = new Vector<TipoEnderecoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_ENDERECO = res.getInt("COD_ENDERECO");

				PkList pkList = new PkList();
				pkList.put("COD_ENDERECO",COD_ENDERECO);
				TipoEnderecoDAO dao = new TipoEnderecoDAO(pkList);

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