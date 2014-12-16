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

public class ObsPedidoDAOService {
	
	public static Vector<ObsPedidoDAO> loadAllForPedido(String COD_PEDIDO) throws SQLException {
		
		String query = "SELECT * FROM " + ObsPedidoDAO.TABLENAME + " WHERE COD_PEDIDO = " + COD_PEDIDO;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<ObsPedidoDAO> daoList = new Vector<ObsPedidoDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_OBS= res.getInt("COD_OBS");							

				PkList pkList = new PkList();				
				pkList.put("COD_OBS",COD_OBS);
				ObsPedidoDAO dao = new ObsPedidoDAO(pkList);
				
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
