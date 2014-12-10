/*ZIRIX CONTROL CENTER - INFO CONTATO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class InfoContatoDAOService {

	public static Vector<InfoContatoDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + InfoContatoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<InfoContatoDAO> daoList = new Vector<InfoContatoDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
				
			
			while (res.next()) {
							
				int COD_GRAU = res.getInt("COD_GRAU");							
			
				// TODO ClienteDAO.loadPkFromResultSet
				PkList pkList = new PkList();				
				pkList.put("COD_GRAU",COD_GRAU);
				InfoContatoDAO dao = new InfoContatoDAO(pkList);
				
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
