/*ZIRIX CONTROL CENTER - MODULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ModuloDAOService {

	public static Vector<ModuloDAO> loadAll() throws SQLException {
		String query = "SELECT * FROM " + ModuloDAO.TABLENAME + " WHERE DELETED = 0";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ModuloDAO> daoList = new Vector<ModuloDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();
			while (res.next()) {
				int COD_MODULO = res.getInt("COD_MODULO");
				PkList pkList = new PkList();
				pkList.put("COD_MODULO",COD_MODULO);
				ModuloDAO dao = new ModuloDAO(pkList);
				dao.read();
				dao.loadAttsFromResultSet(res);
				daoList.add(dao);
			}
			return daoList;
		}catch(SQLException e){ throw e; }
        finally {
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
	public static Vector<ModuloDAO> loadAllEstoque() throws SQLException {
		String query = "SELECT * "
				+ 	   "  FROM " + ModuloDAO.TABLENAME  
				+	   " WHERE COD_CLIENTE = 1 "
				+ 	   "   AND DELETED = 0 "
				+ 	   "   AND ESTADO_MODULO = 1 ";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ModuloDAO> daoList = new Vector<ModuloDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();
			while (res.next()) {
				int COD_MODULO = res.getInt("COD_MODULO");
				PkList pkList = new PkList();
				pkList.put("COD_MODULO",COD_MODULO);
				ModuloDAO dao = new ModuloDAO(pkList);
				dao.read();
				dao.loadAttsFromResultSet(res);
				daoList.add(dao);
			}
			return daoList;
		}catch(SQLException e){ throw e; }
        finally {
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
}
