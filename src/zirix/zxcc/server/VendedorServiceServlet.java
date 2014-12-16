/*ZIRIX CONTROL CENTER - VENDEDOR SERVICE SERVLET
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

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

import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.VendedorDAO;
import zirix.zxcc.server.dao.ContatoVendedorDAO;
import zirix.zxcc.server.dao.DocumentoVendedorDAO;
import zirix.zxcc.server.dao.EmailCliVenDAO;
import zirix.zxcc.server.dao.EnderecoVendedorDAO;
import zirix.zxcc.server.dao.PkList;

/**
 * Servlet implementation class VendedorService
 */
	@WebServlet( name="VendedorService", urlPatterns = {"/services/vendedor"}, loadOnStartup=1)
	public class VendedorServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public VendedorServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Set response content type
		   response.setContentType("text/html");

		   PrintWriter out = response.getWriter();

		   String OP_CODE = request.getParameter("OP_CODE");

		   try {
			   VendedorDAO daoVendedor = new VendedorDAO();
			   PkList pkList;

			   if ((OP_CODE.compareTo("UPDATE") == 0) || (OP_CODE.compareTo("CREATE") == 0)) {

				   String TIPO = request.getParameter("TIPO").trim();
				   daoVendedor.setAttValueFor("TIPO", TIPO);

				   String NOME = request.getParameter("NOME").trim();
				   daoVendedor.setAttValueFor("NOME", NOME);

				   String NOME_FANTASIA = request.getParameter("NOME_FANTASIA").trim();
				   daoVendedor.setAttValueFor("NOME_FANTASIA", NOME_FANTASIA);

				   String SITE = request.getParameter("SITE").trim();
				   daoVendedor.setAttValueFor("SITE", SITE);

				   String DATA_NASCIMENTO = request.getParameter("DATA_NASCIMENTO").trim();
				   daoVendedor.setAttValueFor("DATA_NASCIMENTO", DATA_NASCIMENTO);
				   
				   daoVendedor.setAttValueFor("DELETED", 0);

				   if (OP_CODE.compareTo("CREATE") == 0){
					   String DATA_INGRESSO = request.getParameter("DATA_INGRESSO").trim();
					   daoVendedor.setAttValueFor("DATA_INGRESSO", DATA_INGRESSO);
				   }

				   if (OP_CODE.compareTo("UPDATE") == 0){

					   String COD_VENDEDOR = request.getParameter("COD_VENDEDOR").trim();
					   pkList = VendedorDAO.createKey("COD_VENDEDOR", Integer.parseInt(COD_VENDEDOR));

					   daoVendedor.setPkList(pkList);
					   daoVendedor.update();
				   }
				   else
				   {
					   daoVendedor.Create();
					   int pkListValue = 0;
					   Vector<String[]> CodVendedor_ = new Vector<String[]>();
					   
					   try {
						   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_CLIENTE "
								   + " 											                 FROM " + ZXMain.DB_NAME_ + "CLIENTE "
								   + "                                                          WHERE NOME = '" + NOME + "'");

						   for (int i=0;i < values.size();i++) {
							   String[] attList = new String[1];
							   attList[0] = values.get(i)[0].toString();;
							   CodVendedor_.add(attList);
						   }
					   }catch (SQLException ex) {
						   ex.printStackTrace();
					   }  finally {
						   pkListValue = Integer.parseInt(CodVendedor_.elementAt(0)[0].trim());
					   }

					   if(pkListValue != 0){
						   int arraysize = Integer.parseInt(request.getParameter("QDOC"));
						   for(int d = 0 ; d < arraysize ; d++){
							   DocumentoVendedorDAO daoDocumentoVendedor = new DocumentoVendedorDAO();

							   daoDocumentoVendedor.setAttValueFor("COD_VENDEDOR",pkListValue);

							   String COD_DOCUMENTO_ = request.getParameter("TIPODOC_"+d).trim();
							   daoDocumentoVendedor.setAttValueFor("COD_DOCUMENTO",COD_DOCUMENTO_);

							   String NUMERO_ = request.getParameter("NUMDOC_"+d).trim();
							   daoDocumentoVendedor.setAttValueFor("NUMERO",NUMERO_);

							   String DATA_EMISSAO_ = request.getParameter("DTDOC_"+d).trim();
							   daoDocumentoVendedor.setAttValueFor("DATA_EMISSAO",DATA_EMISSAO_);

							   String ORGAO_EMISSOR_ = request.getParameter("ORGDOC_"+d).trim();
							   daoDocumentoVendedor.setAttValueFor("ORGAO_EMISSOR",ORGAO_EMISSOR_);

							   daoDocumentoVendedor.setAttValueFor("DELETED",0);
							   daoDocumentoVendedor.Create();
						   }
	
						   arraysize = Integer.parseInt(request.getParameter("QEND"));
						   for(int d = 0 ; d < arraysize ; d++){
							   EnderecoVendedorDAO daoEnderecoVendedor = new EnderecoVendedorDAO();
	
							   daoEnderecoVendedor.setAttValueFor("COD_VENDEDOR",pkListValue);
	
							   String ENDERECO_ = request.getParameter("END_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("ENDERECO",ENDERECO_);
	
							   String COMPLEMENTO_ = request.getParameter("COMP_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("COMPLEMENTO",COMPLEMENTO_);
	
							   String BAIRRO_ = request.getParameter("BAIRRO_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("BAIRRO",BAIRRO_);
	
							   String CIDADE_ = request.getParameter("CIDADE_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("CIDADE",CIDADE_);
	
							   String UF_ = request.getParameter("UF_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("UF",UF_);
	
							   String COD_PAIS_ = request.getParameter("PAIS_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("COD_PAIS",COD_PAIS_);
	
							   String CEP_ = request.getParameter("CEP_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("CEP",CEP_);
	
							   String COD_ENDERECO_ = request.getParameter("TIPOEND_"+d).trim();
							   daoEnderecoVendedor.setAttValueFor("COD_ENDERECO",COD_ENDERECO_);
	
							   daoEnderecoVendedor.Create();
						   }
	
						   arraysize = Integer.parseInt(request.getParameter("QCTO"));
						   for(int d = 0 ; d < arraysize ; d++){
							   ContatoVendedorDAO daoContatoVendedor = new ContatoVendedorDAO();
							   
							   daoContatoVendedor.setAttValueFor("COD_VENDEDOR",pkListValue);
							   
							   String DDD_ = request.getParameter("DDD_"+d).trim();
							   daoContatoVendedor.setAttValueFor("DDD",DDD_);
							   
							   String NUMERO_ = request.getParameter("NUMCTO_"+d).trim();
							   daoContatoVendedor.setAttValueFor("NUMERO",NUMERO_);
							   
							   String COD_CONTATO_ = request.getParameter("TIPOCTO_"+d).trim();
							   daoContatoVendedor.setAttValueFor("COD_CONTATO",COD_CONTATO_);
							   
							   String COD_PAIS_ = request.getParameter("PAISCTO_"+d).trim();
							   daoContatoVendedor.setAttValueFor("COD_PAIS",COD_PAIS_);
							   
							   String NOME_ = request.getParameter("NOMECTO_"+d).trim();
							   daoContatoVendedor.setAttValueFor("NOME",NOME_);
							   
							   String COD_GRAU_ = request.getParameter("PARENCTO_"+d).trim();
							   daoContatoVendedor.setAttValueFor("COD_GRAU",COD_GRAU_);
							   
							   daoContatoVendedor.Create();
						   }
	
						   arraysize = Integer.parseInt(request.getParameter("QMAIL"));
						   for(int d = 0 ; d < arraysize ; d++){
							   EmailCliVenDAO daoEmailCliVen = new EmailCliVenDAO();
	
							   daoEmailCliVen.setAttValueFor("COD_CLI_VEN",pkListValue);
	
							   daoEmailCliVen.setAttValueFor("TIPO_CLI_VEN",1);
	
							   String EMAIL_ = request.getParameter("MAIL_"+d).trim();
							   daoEmailCliVen.setAttValueFor("EMAIL",EMAIL_);
							   daoEmailCliVen.setAttValueFor("DELETED",0);
	
							   daoEmailCliVen.Create();
						   }
					   }else{
						   out.println("Error on VendedorServiceServlet... " + "\nCOD_VENDEDOR não encontrado! ");
					   }
				   }
				   // TODO CRIAR PÁGINA DE REDIRECIONAMENTO OU ALERT DE INGRESSO REALIZADO
				   String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
				   response.sendRedirect(ZXMain.URL_ADRESS_ + "zx_cc.jsp?COD_USUARIO=" + COD_USUARIO);
			   }

			   else if (OP_CODE.compareTo("DELETE") == 0){

				   String COD_VENDEDOR = request.getParameter("COD_VENDEDOR");
				   pkList = VendedorDAO.createKey("COD_VENDEDOR", Integer.parseInt(COD_VENDEDOR));

				   daoVendedor.setPkList(pkList);
				   daoVendedor.delete();
			   }
		   } catch (Exception e) {
				   out.println("Error on VendedorServiceServlet... " + ' ' + e.getMessage());
		   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
