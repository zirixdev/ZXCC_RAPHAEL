/*ZIRIX CONTROL CENTER - ENDEREÇO VENDEDOR DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class EnderecoVendedorDAO extends GenericDAO<EnderecoVendedorDAO> {


    public EnderecoVendedorDAO(PkList pkList) {
        super(TABLENAME,pkList);
        setCanDelete(true);
    }

    public EnderecoVendedorDAO(){
    	super(TABLENAME);
    }
        
    public static PkList createKey(String name,int value) {
		
		PkList key = new PkList();		
		key.put(name, new Integer(value));
		
		return key;				
	}
  
    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("COD_VENDEDOR", res.getInt("COD_VENDEDOR"));
    	setAttValueFor("ENDERECO",res.getString("ENDERECO"));
    	setAttValueFor("CIDADE",res.getString("CIDADE"));
    	setAttValueFor("BAIRRO",res.getString("BAIRRO"));
    	setAttValueFor("UF",res.getString("UF"));
    	setAttValueFor("COD_PAIS", res.getInt("COD_PAIS"));
    	setAttValueFor("COMPLEMENTO",res.getString("COMPLEMENTO"));
    	setAttValueFor("CEP",res.getString("CEP"));
    	setAttValueFor("COD_ENDERECO", res.getInt("COD_ENDERECO"));
    	
    }
    
    public Set<String> getPkNamesSet() {    	
    	return EnderecoVendedorDAO.createKey("COD_ENDERECO_VEN", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }

	public final static String TABLENAME = ZXMain.DB_NAME_ + "ENDERECO_VENDEDOR";
}