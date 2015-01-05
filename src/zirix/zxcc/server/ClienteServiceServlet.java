/*ZIRIX CONTROL CENTER - CLIENTE SERVICE SERVLET
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zirix.zxcc.server.dao.*;

@WebServlet( name="ClienteService", urlPatterns = {"/services/cliente"}, loadOnStartup=1)
public class ClienteServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ClienteServiceServlet() {
	    super();
	}

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   response.setContentType("text/html");
		   PrintWriter out = response.getWriter();
		   String OP_CODE = request.getParameter("OP_CODE");
		   try {
			   ClienteDAO daoCliente = new ClienteDAO();
			   PkList pkList;
	
			   if ((OP_CODE.compareTo("UPDATE") == 0) || (OP_CODE.compareTo("CREATE") == 0)) {
	
				   String TIPO = request.getParameter("TIPO").trim();
				   daoCliente.setAttValueFor("TIPO", TIPO);
	
				   String NOME = request.getParameter("NOME").trim();
				   daoCliente.setAttValueFor("NOME", NOME);
	
				   String NOME_FANTASIA = request.getParameter("NOME_FANTASIA").trim();
				   daoCliente.setAttValueFor("NOME_FANTASIA", NOME_FANTASIA);
	
				   String SITE = request.getParameter("SITE").trim();
				   daoCliente.setAttValueFor("SITE", SITE);
	
				   String DATA_NASCIMENTO = request.getParameter("DATA_NASCIMENTO").trim();
				   daoCliente.setAttValueFor("DATA_NASCIMENTO", DATA_NASCIMENTO);
	
				   if (OP_CODE.compareTo("CREATE") == 0){
					   String DATA_INGRESSO = request.getParameter("DATA_INGRESSO").trim();
					   daoCliente.setAttValueFor("DATA_INGRESSO", DATA_INGRESSO);
				   }
	
				   String COD_VENDEDOR = request.getParameter("COD_VENDEDOR").trim();
				   daoCliente.setAttValueFor("COD_VENDEDOR", COD_VENDEDOR);
				   
				   daoCliente.setAttValueFor("DELETED", 0);
	
				   if (OP_CODE.compareTo("UPDATE") == 0){
	
					   String COD_CLIENTE = request.getParameter("COD_CLIENTE").trim();
					   pkList = ClienteDAO.createKey("COD_CLIENTE", Integer.parseInt(COD_CLIENTE));
	
					   daoCliente.setPkList(pkList);
					   daoCliente.update();
				   }
				   else
				   {
					   daoCliente.Create();
					   int pkListValue = 0;
					   Vector<String[]> CodCliente_ = new Vector<String[]>();
					   
					   try {
						   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_CLIENTE "
								   + " 											                 FROM " + ZXMain.DB_NAME_ + "CLIENTE "
								   + "                                                          WHERE NOME = '" + NOME + "'");
	
						   for (int i=0;i < values.size();i++) {
							   String[] attList = new String[1];
							   attList[0] = values.get(i)[0].toString();;
							   CodCliente_.add(attList);
						   }
					   }catch (SQLException ex) {
						   ex.printStackTrace();
					   }  finally {
						   pkListValue = Integer.parseInt(CodCliente_.elementAt(0)[0].trim());
					   }
	
					   if(pkListValue != 0){
						   int arraysize = Integer.parseInt(request.getParameter("QDOC"));
						   for(int d = 0 ; d < arraysize ; d++){
							   DocumentoClienteDAO daoDocumentoCliente = new DocumentoClienteDAO();
	
							   daoDocumentoCliente.setAttValueFor("COD_CLIENTE",pkListValue);
	
							   String COD_DOCUMENTO_ = request.getParameter("TIPODOC_"+d).trim();
							   daoDocumentoCliente.setAttValueFor("COD_DOCUMENTO",COD_DOCUMENTO_);
	
							   String NUMERO_ = request.getParameter("NUMDOC_"+d).trim();
							   daoDocumentoCliente.setAttValueFor("NUMERO",NUMERO_);
	
							   String DATA_EMISSAO_ = request.getParameter("DTDOC_"+d).trim();
							   daoDocumentoCliente.setAttValueFor("DATA_EMISSAO",DATA_EMISSAO_);
	
							   String ORGAO_EMISSOR_ = request.getParameter("ORGDOC_"+d).trim();
							   daoDocumentoCliente.setAttValueFor("ORGAO_EMISSOR",ORGAO_EMISSOR_);

							   daoDocumentoCliente.setAttValueFor("DELETED",0);
							   daoDocumentoCliente.Create();
						   }
	
						   arraysize = Integer.parseInt(request.getParameter("QEND"));
						   for(int d = 0 ; d < arraysize ; d++){
							   EnderecoClienteDAO daoEnderecoCliente = new EnderecoClienteDAO();
	
							   daoEnderecoCliente.setAttValueFor("COD_CLIENTE",pkListValue);
	
							   String ENDERECO_ = request.getParameter("END_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("ENDERECO",ENDERECO_);
	
							   String COMPLEMENTO_ = request.getParameter("COMP_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("COMPLEMENTO",COMPLEMENTO_);
	
							   String BAIRRO_ = request.getParameter("BAIRRO_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("BAIRRO",BAIRRO_);
	
							   String CIDADE_ = request.getParameter("CIDADE_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("CIDADE",CIDADE_);
	
							   String UF_ = request.getParameter("UF_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("UF",UF_);
	
							   String COD_PAIS_ = request.getParameter("PAIS_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("COD_PAIS",COD_PAIS_);
	
							   String CEP_ = request.getParameter("CEP_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("CEP",CEP_);
	
							   String COD_ENDERECO_ = request.getParameter("TIPOEND_"+d).trim();
							   daoEnderecoCliente.setAttValueFor("COD_ENDERECO",COD_ENDERECO_);
	
							   daoEnderecoCliente.Create();
						   }
	
						   arraysize = Integer.parseInt(request.getParameter("QCTO"));
						   for(int d = 0 ; d < arraysize ; d++){
							   ContatoClienteDAO daoContatoCliente = new ContatoClienteDAO();
							   
							   daoContatoCliente.setAttValueFor("COD_CLIENTE",pkListValue);
							   
							   String DDD_ = request.getParameter("DDD_"+d).trim();
							   daoContatoCliente.setAttValueFor("DDD",DDD_);
							   
							   String NUMERO_ = request.getParameter("NUMCTO_"+d).trim();
							   daoContatoCliente.setAttValueFor("NUMERO",NUMERO_);
							   
							   String COD_CONTATO_ = request.getParameter("TIPOCTO_"+d).trim();
							   daoContatoCliente.setAttValueFor("COD_CONTATO",COD_CONTATO_);
							   
							   String COD_PAIS_ = request.getParameter("PAISCTO_"+d).trim();
							   daoContatoCliente.setAttValueFor("COD_PAIS",COD_PAIS_);
							   
							   String NOME_ = request.getParameter("NOMECTO_"+d).trim();
							   daoContatoCliente.setAttValueFor("NOME",NOME_);
							   
							   String COD_GRAU_ = request.getParameter("PARENCTO_"+d).trim();
							   daoContatoCliente.setAttValueFor("COD_GRAU",COD_GRAU_);
							   
							   daoContatoCliente.Create();
						   }
	
						   arraysize = Integer.parseInt(request.getParameter("QMAIL"));
						   for(int d = 0 ; d < arraysize ; d++){
							   EmailCliVenDAO daoEmailCliVen = new EmailCliVenDAO();
	
							   daoEmailCliVen.setAttValueFor("COD_CLI_VEN",pkListValue);
	
							   daoEmailCliVen.setAttValueFor("TIPO_CLI_VEN",0);
	
							   String EMAIL_ = request.getParameter("MAIL_"+d).trim();
							   daoEmailCliVen.setAttValueFor("EMAIL",EMAIL_);
	
							   daoEmailCliVen.Create();
						   }
					   }else{
						   out.println("Error on ClienteServiceServlet... " + "\nCOD_CLIENTE n�o encontrado! ");
					   }
				   }
				   response.sendRedirect(ZXMain.URL_ADRESS_ + "/zx_cc.jsp");
			   }else if (OP_CODE.compareTo("DELETE") == 0){
				   String COD_CLIENTE = request.getParameter("COD_CLIENTE");
				   pkList = ClienteDAO.createKey("COD_CLIENTE", Integer.parseInt(COD_CLIENTE));
				   daoCliente.setPkList(pkList);
				   daoCliente.delete();
			   }
		   } catch (Exception e) {
				   out.println("Error on ClienteServiceServlet... " + ' ' + e.getMessage());
		   }
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
