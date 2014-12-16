/*ZIRIX CONTROL CENTER - DADOS AGENDAMENTO DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import zirix.zxcc.server.*;

public class DadosAgendamentoDAO extends GenericDAO<DadosInstalacaoDAO> {

    public DadosAgendamentoDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public DadosAgendamentoDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();		
		key.put(name, new Integer(value));

		return key;				
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {

    	setAttValueFor("ENDERECO",res.getString("ENDERECO"));
    	setAttValueFor("BAIRRO",res.getString("BAIRRO"));
    	setAttValueFor("CIDADE",res.getString("CIDADE"));
    	setAttValueFor("UF",res.getString("UF"));
    	setAttValueFor("COD_PAIS",res.getInt("COD_PAIS"));
    	setAttValueFor("COMPLEMENTO",res.getString("COMPLEMENTO"));
    	setAttValueFor("CEP",res.getString("CEP"));
    	setAttValueFor("REFERENCIA",res.getString("REFERENCIA"));
    	setAttValueFor("DDD",res.getString("DDD"));
    	setAttValueFor("NUMERO",res.getString("NUMERO"));
    	setAttValueFor("NOME",res.getString("NOME"));
    	setAttValueFor("COD_AGENDAMENTO",res.getInt("COD_AGENDAMENTO"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }
    
    public Set<String> getPkNamesSet() {    	
    	return DadosInstalacaoDAO.createKey("COD_DADOS_AGENDAMENTO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();    	    	
    }
    
    public final static String TABLENAME = ZXMain.DB_NAME_ + "DADOS_AGENDAMENTO";
   
        
}