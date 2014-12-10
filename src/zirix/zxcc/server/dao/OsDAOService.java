/*ZIRIX CONTROL CENTER - OS DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.ZXMain;

public class OsDAOService {

	public static Vector<OsDAO> loadAll() throws SQLException {
		String query = "SELECT * FROM " + OsDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<OsDAO> daoList = new Vector<OsDAO>();
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_OS = res.getInt("COD_OS");
				PkList pkList = new PkList();
				pkList.put("COD_OS",COD_OS);
				OsDAO dao = new OsDAO(pkList);
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

	@SuppressWarnings("finally")
	public static Vector<String[]> loadAllNumeroOs() throws SQLException {
		Vector<String[]> numeroOS = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT OS.COD_OS "
					+ 														   "     , CONCAT(NUMERO_OS.ANO_OS,NUMERO_OS.MES_OS,'/',LPAD(NUMERO_OS.NUM_OS,6,0)) "
					+ 														   "  FROM OS "
					+ 														   "     , NUMERO_OS "
					+ 														   " WHERE OS.COD_NUM_OS = NUMERO_OS.COD_NUM_OS ");
			for (int i=0;i < values.size();i++) {
				String[] attList = new String[2];
				attList[0] = values.get(i)[0].toString();
				attList[1] = values.get(i)[1].toString();
				numeroOS.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return numeroOS;
		}
	}
}
	
	
