/*ZIRIX CONTROL CENTER - VE�CULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class VeiculoDAOService {

	private static Integer COD_VEICULO_ = null;

	public static Vector<VeiculoDAO> loadAllPedido(int pkVal) throws SQLException {
		COD_VEICULO_ = pkVal;
		String query = "SELECT * FROM " + VeiculoDAO.TABLENAME + " WHERE COD_VEICULO = " + COD_VEICULO_;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<VeiculoDAO> daoList = new Vector<VeiculoDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
			while (res.next()) {
				int COD_VEICULO = res.getInt("COD_VEICULO");							
				PkList pkList = new PkList();				
				pkList.put("COD_VEICULO",COD_VEICULO);
				VeiculoDAO dao = new VeiculoDAO(pkList);
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
	public static Vector<VeiculoDAO> loadAll() throws SQLException {
		String query = "SELECT * FROM " + VeiculoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<VeiculoDAO> daoList = new Vector<VeiculoDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
			while (res.next()) {
				int COD_VEICULO = res.getInt("COD_VEICULO");							
				PkList pkList = new PkList();				
				pkList.put("COD_VEICULO",COD_VEICULO);
				VeiculoDAO dao = new VeiculoDAO(pkList);
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
