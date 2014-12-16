/*ZIRIX CONTROL CENTER - TIPO CONTATO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoContatoDAOService {

	public static Vector<TipoContatoDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + TipoContatoDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TipoContatoDAO> daoList = new Vector<TipoContatoDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
				
			
			while (res.next()) {
							
				int COD_CONTATO = res.getInt("COD_CONTATO");							
			
				// TODO ClienteDAO.loadPkFromResultSet
				PkList pkList = new PkList();				
				pkList.put("COD_CONTATO",COD_CONTATO);
				TipoContatoDAO dao = new TipoContatoDAO(pkList);
				
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
