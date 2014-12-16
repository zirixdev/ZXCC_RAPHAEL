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

public class TipoEquipAcessorioDAOService {

	public static Vector<TipoEquipAcessorioDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TipoEquipAcessorioDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoEquipAcessorioDAO> daoList = new Vector<TipoEquipAcessorioDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_EQUIP_ACESSORIO = res.getInt("COD_EQUIP_ACESSORIO");

				PkList pkList = new PkList();
				pkList.put("COD_EQUIP_ACESSORIO",COD_EQUIP_ACESSORIO);
				TipoEquipAcessorioDAO dao = new TipoEquipAcessorioDAO(pkList);

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