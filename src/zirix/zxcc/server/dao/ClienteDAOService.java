/*ZIRIX CONTROL CENTER - CLIENTE DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ClienteDAOService {
	
	public static Vector<ClienteDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + ClienteDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ClienteDAO> daoList = new Vector<ClienteDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_CLIENTE = res.getInt("COD_CLIENTE");							

				PkList pkList = new PkList();				
				pkList.put("COD_CLIENTE",COD_CLIENTE);
				ClienteDAO dao = new ClienteDAO(pkList);
				
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
    	
        String query = "SELECT COUNT(*) AS count FROM "+ ClienteDAO.TABLENAME;
        PreparedStatement counter = null;
        ResultSet res = null;
        Connection con = DAOManager.getInstance().getConnection(); 
        
        try
        {            	
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
