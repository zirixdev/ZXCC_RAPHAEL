/*ZIRIX CONTROL CENTER - ESTADO MODULO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class EstadoModuloDAOService {

	public static Vector<EstadoModuloDAO> loadAll() throws SQLException {
		
		String query = "SELECT * FROM " + EstadoModuloDAO.TABLENAME;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<EstadoModuloDAO> daoList = new Vector<EstadoModuloDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		
				
			
			while (res.next()) {
							
				int COD_ESTADO = res.getInt("COD_ESTADO");							
			
				PkList pkList = new PkList();				
				pkList.put("COD_ESTADO",COD_ESTADO);
				EstadoModuloDAO dao = new EstadoModuloDAO(pkList);
				
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
