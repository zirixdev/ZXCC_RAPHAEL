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

public class EnderecoClienteDAOService {
	
	public static Vector<EnderecoClienteDAO> loadAllForCliente(String COD_CLIENTE) throws SQLException {
		
		String query = "SELECT * FROM " + EnderecoClienteDAO.TABLENAME + " WHERE COD_CLIENTE = " + COD_CLIENTE;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<EnderecoClienteDAO> daoList = new Vector<EnderecoClienteDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_ENDERECO_CLI = res.getInt("COD_ENDERECO_CLI");							

				PkList pkList = new PkList();				
				pkList.put("COD_ENDERECO_CLI",COD_ENDERECO_CLI);
				EnderecoClienteDAO dao = new EnderecoClienteDAO(pkList);
				
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
