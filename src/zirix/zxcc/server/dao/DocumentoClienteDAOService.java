/*ZIRIX CONTROL CENTER - CLIENTE DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DocumentoClienteDAOService {
	
	public static Vector<DocumentoClienteDAO> loadAllForCliente(String COD_CLIENTE) throws SQLException {
		
		String query = "SELECT * FROM " + DocumentoClienteDAO.TABLENAME + " WHERE COD_CLIENTE = " + COD_CLIENTE;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<DocumentoClienteDAO> daoList = new Vector<DocumentoClienteDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_DOCUMENTO_CLI = res.getInt("COD_DOCUMENTO_CLI");							

				PkList pkList = new PkList();				
				pkList.put("COD_DOCUMENTO_CLI",COD_DOCUMENTO_CLI);
				DocumentoClienteDAO dao = new DocumentoClienteDAO(pkList);
				
				dao.read();
				dao.loadAttsFromResultSet(res);
				daoList.add(dao);
			}	

			return daoList;

		} catch(SQLException e){ 
			throw e; 
		}

        	finally {
        		if (res != null) res.close();
        		if (stmt != null) stmt.close();
        		DAOManager.getInstance().closeConnection(con);
        	}		
	}
}
