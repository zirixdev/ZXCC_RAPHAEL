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

public class DadosInstalacaoDAOService {
	
	public static Vector<DadosInstalacaoDAO> loadAllForPedido(String COD_PEDIDO) throws SQLException {
		
		String query = "SELECT * FROM " + DadosInstalacaoDAO.TABLENAME + " WHERE COD_PEDIDO = " + COD_PEDIDO;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<DadosInstalacaoDAO> daoList = new Vector<DadosInstalacaoDAO>();
				
		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();		

			while (res.next()) {
							
				int COD_DADOS_INSTALACAO = res.getInt("COD_DADOS_INSTALACAO");							

				PkList pkList = new PkList();				
				pkList.put("COD_DADOS_INSTALACAO",COD_DADOS_INSTALACAO);
				DadosInstalacaoDAO dao = new DadosInstalacaoDAO(pkList);
				
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
