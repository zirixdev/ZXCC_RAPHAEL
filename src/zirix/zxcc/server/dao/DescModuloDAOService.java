/*ZIRIX CONTROL CENTER - DESC MODULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DescModuloDAOService {

	public static Vector<DescModuloDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + DescModuloDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<DescModuloDAO> daoList = new Vector<DescModuloDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_MODELO = res.getInt("COD_MODELO");

				PkList pkList = new PkList();
				pkList.put("COD_MODELO",COD_MODELO);
				DescModuloDAO dao = new DescModuloDAO(pkList);

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

	public static Vector<DescModuloDAO> loadModelo(Integer cod_marca) throws SQLException{
		
		String query = "SELECT * FROM " + DescModuloDAO.TABLENAME + " WHERE COD_MARCA=" + cod_marca;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<DescModuloDAO> daoList = new Vector<DescModuloDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {
				int COD_MODELO = res.getInt("COD_MODELO");

				PkList pkList = new PkList();
				pkList.put("COD_MODELO",COD_MODELO);
				DescModuloDAO dao = new DescModuloDAO(pkList);

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