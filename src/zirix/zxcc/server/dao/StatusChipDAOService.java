/*ZIRIX CONTROL CENTER - STATUS CHIP DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StatusChipDAOService {

	public static Vector<StatusChipDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + StatusChipDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<StatusChipDAO> daoList = new Vector<StatusChipDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
				
			
			while (res.next()) {
							
				int COD_STATUS = res.getInt("COD_STATUS");							
			
				PkList pkList = new PkList();				
				pkList.put("COD_STATUS",COD_STATUS);
				StatusChipDAO dao = new StatusChipDAO(pkList);
				
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
