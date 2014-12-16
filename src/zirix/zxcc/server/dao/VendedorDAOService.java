/*ZIRIX CONTROL CENTER - VENDEDOR DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class VendedorDAOService {

	public static Vector<VendedorDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + VendedorDAO.TABLENAME + " ORDER BY NOME ASC";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<VendedorDAO> daoList = new Vector<VendedorDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
				
			
			while (res.next()) {
							
				int COD_VENDEDOR = res.getInt("COD_VENDEDOR");							
			
				// TODO ClienteDAO.loadPkFromResultSet
				PkList pkList = new PkList();				
				pkList.put("COD_VENDEDOR",COD_VENDEDOR);
				VendedorDAO dao = new VendedorDAO(pkList);
				
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
