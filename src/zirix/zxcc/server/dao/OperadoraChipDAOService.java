/*ZIRIX CONTROL CENTER - OPERADORA CHIP DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class OperadoraChipDAOService {

	public static Vector<OperadoraChipDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + OperadoraChipDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<OperadoraChipDAO> daoList = new Vector<OperadoraChipDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
				
			
			while (res.next()) {
							
				int COD_OPERADORA = res.getInt("COD_OPERADORA");							
			
				PkList pkList = new PkList();				
				pkList.put("COD_OPERADORA",COD_OPERADORA);
				OperadoraChipDAO dao = new OperadoraChipDAO(pkList);
				
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
