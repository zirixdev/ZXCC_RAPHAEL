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

public class ContatoClienteDAOService {
	
	public static Vector<ContatoClienteDAO> loadAllForCliente(String COD_CLIENTE) throws SQLException {
		
		String query = "SELECT * FROM " + ContatoClienteDAO.TABLENAME + " WHERE COD_CLIENTE = " + COD_CLIENTE;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ContatoClienteDAO> daoList = new Vector<ContatoClienteDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_CONTATO_CLI = res.getInt("COD_CONTATO_CLI");							

				PkList pkList = new PkList();				
				pkList.put("COD_CONTATO_CLI",COD_CONTATO_CLI);
				ContatoClienteDAO dao = new ContatoClienteDAO(pkList);
				
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
