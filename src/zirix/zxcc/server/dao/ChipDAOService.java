/*ZIRIX CONTROL CENTER - CHIP DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ChipDAOService {
	public static Vector<ChipDAO> loadAll() throws SQLException {
		String query = "SELECT * FROM " + ChipDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ChipDAO> daoList = new Vector<ChipDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();
			while (res.next()) {
				int COD_CHIP = res.getInt("COD_CHIP");
				PkList pkList = new PkList();
				pkList.put("COD_CHIP",COD_CHIP);
				ChipDAO dao = new ChipDAO(pkList);
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

	public static Vector<ChipDAO> loadSemModuloAndInstalado(String cod_modulo) throws SQLException {
		String query = "SELECT * FROM " + ChipDAO.TABLENAME + " WHERE COD_MODULO IN (0, " + cod_modulo + ")";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ChipDAO> daoList = new Vector<ChipDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();
			while (res.next()) {
				int COD_CHIP = res.getInt("COD_CHIP");
				PkList pkList = new PkList();
				pkList.put("COD_CHIP",COD_CHIP);
				ChipDAO dao = new ChipDAO(pkList);
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

	public static Vector<ChipDAO> loadSemModulo() throws SQLException {
		String query = "SELECT * FROM " + ChipDAO.TABLENAME + " WHERE COD_MODULO = 0";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ChipDAO> daoList = new Vector<ChipDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();
			while (res.next()) {
				int COD_CHIP = res.getInt("COD_CHIP");
				PkList pkList = new PkList();
				pkList.put("COD_CHIP",COD_CHIP);
				ChipDAO dao = new ChipDAO(pkList);
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

	public static int count() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM "+ ChipDAO.TABLENAME;
        PreparedStatement counter = null;
        ResultSet res = null;
        Connection con = DAOManager.getInstance().getConnection(); 
        try{
	        counter = con.prepareStatement(query);
	        res = counter.executeQuery();
	        res.next();
	        return res.getInt("count");
        }
        catch(SQLException e){ throw e; }
        finally {
        	if (res != null) res.close();
        	if (counter != null) counter.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
}
