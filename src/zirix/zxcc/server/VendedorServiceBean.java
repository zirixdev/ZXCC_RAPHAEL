/*ZIRIX CONTROL CENTER - VENDEDOR SERVICE BEAN
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import zirix.zxcc.server.dao.*;

public class VendedorServiceBean {

	private VendedorDAO dao_ = null;
	private Integer COD_VENDEDOR_ = null;

	public VendedorServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public void setPk(Object[] pkVal) {

		COD_VENDEDOR_ = new Integer((String)pkVal[0]);
		PkList pkList = new PkList();
	    pkList.put("COD_VENDEDOR",COD_VENDEDOR_);
	    dao_ = new VendedorDAO(pkList);

	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {
	    }
	}

	public Integer getTipo(){
		Integer tipo = (Integer)dao_.getAttValueFor("TIPO");
		return tipo;
	}

	public String getNome() {
    	String nome = (String)dao_.getAttValueFor("NOME");
    	return nome;
	}

	public String getNomeFantasia() {
    	String nomeFantasia = (String)dao_.getAttValueFor("NOME_FANTASIA");
    	return nomeFantasia;
	}

	public String getDtNascimento() {
		String dtNascimento = dao_.getAttValueFor("DATA_NASCIMENTO").toString();
    	return dtNascimento;
	}

	public String getSite() {
    	String site = (String)dao_.getAttValueFor("SITE");
    	return site;
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEnd() {
	    Vector<String[]> endVendedorList = new Vector<String[]>();
	    try {
		    ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ENDERECO, BAIRRO, CIDADE, UF, COD_PAIS, COMPLEMENTO, CEP, COD_ENDERECO "
		    		+ "							   FROM " + ZXMain.DB_NAME_ + "ENDERECO_VENDEDOR "
		    		+ "							  WHERE COD_VENDEDOR = " + COD_VENDEDOR_);
		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[8];
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
			    attList[2] = (String) values.get(i)[2];
			    attList[3] = values.get(i)[3].toString();
			    attList[4] = values.get(i)[4].toString();
			    attList[5] = (String) values.get(i)[5];
			    attList[6] = (String) values.get(i)[6];
			    attList[7] = values.get(i)[7].toString();
			    endVendedorList.add(attList);
		    }
	    } catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return endVendedorList;
	    }
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getContato(){
		Vector<String[]> contatoVendedorList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "TIPO_CONTATO.NOME_TIPO "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.DDD "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.NUMERO "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.COD_PAIS "
					+ "                                                              , " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.NOME "
					+ "                                                              , " + ZXMain.DB_NAME_ + "INFO_CONTATO.NOME_GRAU "
		    		+ "							   FROM " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR "
					+ "                               , " + ZXMain.DB_NAME_ + "INFO_CONTATO "
					+ "                               , " + ZXMain.DB_NAME_ + "TIPO_CONTATO "
		    		+ "							  WHERE " + ZXMain.DB_NAME_ + "INFO_CONTATO.COD_GRAU = " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.COD_GRAU "
		    		+ "                             AND " + ZXMain.DB_NAME_ + "TIPO_CONTATO.COD_CONTATO = " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.COD_CONTATO "
		    		+ "                             AND " + ZXMain.DB_NAME_ + "CONTATO_VENDEDOR.COD_VENDEDOR = " + COD_VENDEDOR_);
		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[6];
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
			    attList[2] = (String) values.get(i)[2];
			    attList[3] = values.get(i)[3].toString();
			    attList[4] = (String) values.get(i)[4];
			    attList[5] = (String) values.get(i)[5];
			    contatoVendedorList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return contatoVendedorList;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getDocumento(){
		Vector<String[]> documentoVendedorList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "DOCUMENTO_VENDEDOR.NUMERO "
					+ ",							    " + ZXMain.DB_NAME_ + "TIPO_DOCUMENTO.NOME "
					+ ",                                " + ZXMain.DB_NAME_ + "DOCUMENTO_VENDEDOR.DATA_EMISSAO "
					+ ",                                " + ZXMain.DB_NAME_ + "DOCUMENTO_VENDEDOR.ORGAO_EMISSOR "
		    		+ "							   FROM " + ZXMain.DB_NAME_ + "DOCUMENTO_VENDEDOR "
		    		+ "							      , " + ZXMain.DB_NAME_ + "TIPO_DOCUMENTO "
		    		+ "							  WHERE " + ZXMain.DB_NAME_ + "TIPO_DOCUMENTO.COD_DOCUMENTO = " + ZXMain.DB_NAME_ + "DOCUMENTO_VENDEDOR.COD_DOCUMENTO "
		    		+ "                             AND " + ZXMain.DB_NAME_ + "DOCUMENTO_VENDEDOR.COD_VENDEDOR = " + COD_VENDEDOR_);
		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[4];
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
		    	attList[2] = ((java.sql.Date) values.get(i)[2]).toLocalDate().toString();
			    attList[3] = (String) values.get(i)[3];
			    documentoVendedorList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return documentoVendedorList;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEmail(){
		Vector<String[]> emailVendedorList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT EMAIL "
		    		+ "							   FROM " + ZXMain.DB_NAME_ + "EMAIL_CLI_VEN "
		    		+ "							  WHERE TIPO_CLI_VEN = 1 "
		    		+ "                             AND COD_CLI_VEN = " + COD_VENDEDOR_);
		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[1];
			    attList[0] = (String) values.get(i)[0];
			    emailVendedorList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return emailVendedorList;
		}
	}
}