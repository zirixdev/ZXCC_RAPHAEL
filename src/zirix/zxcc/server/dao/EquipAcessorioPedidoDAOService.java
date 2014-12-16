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

public class EquipAcessorioPedidoDAOService {
	
	public static Vector<EquipAcessorioPedidoDAO> loadAllForPedido(String COD_PEDIDO) throws SQLException {
		
		String query = "SELECT * FROM " + EquipAcessorioPedidoDAO.TABLENAME + " WHERE COD_PEDIDO = " + COD_PEDIDO;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<EquipAcessorioPedidoDAO> daoList = new Vector<EquipAcessorioPedidoDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_EQUIP_ACESSORIO_PEDIDO = res.getInt("COD_EQUIP_ACESSORIO_PEDIDO");							

				PkList pkList = new PkList();				
				pkList.put("COD_EQUIP_ACESSORIO_PEDIDO",COD_EQUIP_ACESSORIO_PEDIDO);
				EquipAcessorioPedidoDAO dao = new EquipAcessorioPedidoDAO(pkList);
				
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
