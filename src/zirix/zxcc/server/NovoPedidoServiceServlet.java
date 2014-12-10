/*ZIRIX CONTROL CENTER - NOVO PEDIDO SERVICE SERVLET
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO
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
@WebServlet( name="NovoPedidoServiceServlet", urlPatterns = {"/services/novoPedido"}, loadOnStartup=1)
public class NovoPedidoServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public NovoPedidoServiceServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String OP_CODE = request.getParameter("OP_CODE");
		String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
		int pkCodPedido = 0;
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
				String DATA_INGRESSO = request.getParameter("DATA_INGRESSO").trim();
				daoCliente.setAttValueFor("DATA_INGRESSO", DATA_INGRESSO);
				String COD_VENDEDOR = request.getParameter("COD_VENDEDOR").trim();
				daoCliente.setAttValueFor("COD_VENDEDOR", COD_VENDEDOR);
				daoCliente.setAttValueFor("DELETED", 0);
				if(OP_CODE.compareTo("UPDATE") == 0){
					String COD_CLIENTE = request.getParameter("COD_CLIENTE").trim();
					pkList = ClienteDAO.createKey("COD_CLIENTE", Integer.parseInt(COD_CLIENTE));
					daoCliente.setPkList(pkList);
					daoCliente.update();
				}else{
					daoCliente.Create();
					int pkListValue = 0;
					Vector<String[]> CodCliente_ = new Vector<String[]>();
					try{
						ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_CLIENTE "
								+ 														   "  FROM " + ZXMain.DB_NAME_ + "CLIENTE "
								+ 														   " WHERE NOME = '" + NOME + "'");
						for (int i=0;i < values.size();i++) {
							String[] attList = new String[1];
							attList[0] = values.get(i)[0].toString();;
							CodCliente_.add(attList);
						}
					}catch (SQLException ex){
						ex.printStackTrace();
					}finally{
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
							daoEmailCliVen.setAttValueFor("DELETED",0);
							daoEmailCliVen.Create();
						}
						int pkNumPedido = 0;
						NumeroPedidoDAO daoNumeroPedido = new NumeroPedidoDAO();
						daoNumeroPedido.setAttValueFor("COD_USUARIO", COD_USUARIO);
						daoNumeroPedido.setAttValueFor("DATA_GERACAO", DATA_INGRESSO);
						daoNumeroPedido.setAttValueFor("DELETED", 0);
						daoNumeroPedido.Create();
						Vector<String[]> NumeroPedido_ = new Vector<String[]>();
						try{
							ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT MAX(NUM_PEDIDO) "
									+ 														   "  FROM " + ZXMain.DB_NAME_ + "NUMERO_PEDIDO "
									+ 														   " WHERE COD_USUARIO = " + COD_USUARIO);
							for (int i=0;i < values.size();i++) {
								String[] attList = new String[1];
								attList[0] = values.get(i)[0].toString();
								NumeroPedido_.add(attList);
							}
						}catch(SQLException ex){
							ex.printStackTrace();
						}finally{
							pkNumPedido = Integer.parseInt(NumeroPedido_.elementAt(0)[0].trim());
						}
						String TIPO_PEDIDO = request.getParameter("TIPO_PEDIDO").trim();
						String DATA_VENCIMENTO = request.getParameter("DATA_VENCIMENTO").trim();
						String INFO_PEDIDO = request.getParameter("INFO_PEDIDO").trim();
						String BOLETO_EMAIL = request.getParameter("ENVIO_BOLETO").trim();
						PedidoDAO daoPedido = new PedidoDAO();
						daoPedido.setAttValueFor("COD_VENDEDOR", COD_VENDEDOR);
						daoPedido.setAttValueFor("COD_CLIENTE", pkListValue);
						daoPedido.setAttValueFor("NUM_PEDIDO", pkNumPedido);
						daoPedido.setAttValueFor("COD_TIPO", TIPO_PEDIDO);
						daoPedido.setAttValueFor("DATA_VENCIMENTO",DATA_VENCIMENTO);
						daoPedido.setAttValueFor("DELETED", 0);
						if(TIPO_PEDIDO.compareTo("3") == 0){
							daoPedido.setAttValueFor("INFO_PEDIDO", INFO_PEDIDO);
						}
						daoPedido.setAttValueFor("BOLETO_EMAIL", BOLETO_EMAIL);
						daoPedido.Create();
						Vector<String[]> CodPedido_ = new Vector<String[]>();
						try{
							ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_PEDIDO "
									+ 														   "  FROM " + ZXMain.DB_NAME_ + "PEDIDO "
									+ 														   " WHERE COD_CLIENTE = " + pkListValue
									+ 														   "   AND NUM_PEDIDO = " + pkNumPedido);
							for (int i=0;i < values.size();i++) {
								String[] attList = new String[1];
								attList[0] = values.get(i)[0].toString();
								CodPedido_.add(attList);
							}
						}catch(SQLException ex){
							ex.printStackTrace();
						}finally{
							pkCodPedido = Integer.parseInt(CodPedido_.elementAt(0)[0].trim());
						}
						arraysize = Integer.parseInt(request.getParameter("QUNIDADE").trim());
						for(int d = 0 ; d < arraysize ; d++){
							int pkCodVeiculo = 0;
							String TIPO_UNIDADE_ = request.getParameter("TIPO_UNIDADE_"+d).trim();
							switch (Integer.parseInt(TIPO_UNIDADE_)){
							case 2:
								VeiculoDAO daoVeiculo = new VeiculoDAO();
								daoVeiculo.setAttValueFor("COD_CLIENTE", pkListValue);
								String PLACA = request.getParameter("PLACA_"+d).trim();
								daoVeiculo.setAttValueFor("PLACA", PLACA);
								String ANO_FAB = request.getParameter("ANO_FAB_"+d).trim();
								daoVeiculo.setAttValueFor("ANO_FAB", ANO_FAB);
								String ANO_MOD = request.getParameter("ANO_MOD_"+d).trim();
								daoVeiculo.setAttValueFor("ANO_MOD", ANO_MOD);
								String MARCA = request.getParameter("MARCA_"+d).trim();
								daoVeiculo.setAttValueFor("COD_MARCA", MARCA);
								String MODELO = request.getParameter("MODELO_"+d).trim();
								daoVeiculo.setAttValueFor("MODELO", MODELO);
								String COR = request.getParameter("COR_"+d).trim();
								daoVeiculo.setAttValueFor("COR", COR);
								String CHASSI = request.getParameter("CHASSI_"+d).trim();
								daoVeiculo.setAttValueFor("CHASSI", CHASSI);
								String RENAVAN = request.getParameter("RENAVAN_"+d).trim();
								daoVeiculo.setAttValueFor("RENAVAN", RENAVAN);
								daoVeiculo.setAttValueFor("DATA_INGRESSO", DATA_INGRESSO);
								String COMBUSTIVEL = request.getParameter("COMBUSTIVEL_"+d).trim();
								daoVeiculo.setAttValueFor("COD_COMBUSTIVEL", COMBUSTIVEL);
								String VOLTAGEM = request.getParameter("VOLTAGEM_"+d).trim();
								daoVeiculo.setAttValueFor("VOLT", VOLTAGEM);
								String KM = request.getParameter("KM_"+d).trim();
								daoVeiculo.setAttValueFor("KM", KM);
								String DATA_ULT_VISTORIA = request.getParameter("DATA_VISTORIA_"+d).trim();
								daoVeiculo.setAttValueFor("DATA_ULT_VISTORIA", DATA_ULT_VISTORIA);
								daoVeiculo.setAttValueFor("COD_PEDIDO", pkCodPedido);
								daoVeiculo.setAttValueFor("DELETED", 0);
								daoVeiculo.Create();
								Vector<String[]> CodVeiculo_ = new Vector<String[]>();
								try{
									ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT MAX(COD_VEICULO) "
											+ 														   "  FROM " + ZXMain.DB_NAME_ + "VEICULO "
											+ 														   " WHERE COD_CLIENTE = " + pkListValue
											+ 														   "   AND COD_PEDIDO = " + pkCodPedido);
									for(int i=0;i < values.size();i++){
										String[] attList = new String[1];
										attList[0] = values.get(i)[0].toString();
										CodVeiculo_.add(attList);
									}
								}catch(SQLException ex){
									ex.printStackTrace();
								}finally{
									pkCodVeiculo = Integer.parseInt(CodVeiculo_.elementAt(0)[0].trim());
								}
							}
							int arraysize2 = Integer.parseInt(request.getParameter("QCTOUNID_"+d).trim());
							for(int f = 0; f<arraysize2; f++){
								ContatoProcedimentoDAO daoContatoProcedimento = new ContatoProcedimentoDAO();
								daoContatoProcedimento.setAttValueFor("COD_CLIENTE", pkListValue);
								daoContatoProcedimento.setAttValueFor("COD_UNIDADE_CADASTRADA", pkCodVeiculo);
								daoContatoProcedimento.setAttValueFor("COD_UNIDADE", TIPO_UNIDADE_);
								String TIPOCTOUNID = request.getParameter("TIPOCTOUNID_" + d + "_" + f);
								daoContatoProcedimento.setAttValueFor("COD_CONTATO", TIPOCTOUNID);
								String DDDUNID = request.getParameter("DDDUNID_" + d + "_" + f);
								daoContatoProcedimento.setAttValueFor("DDD", DDDUNID);
								String NUMCTOUNID = request.getParameter("NUMCTOUNID_" + d + "_" + f);
								daoContatoProcedimento.setAttValueFor("NUMERO", NUMCTOUNID);
								String PAISCTOUNID = request.getParameter("PAISCTOUNID_" + d + "_" + f);
								daoContatoProcedimento.setAttValueFor("COD_PAIS", PAISCTOUNID);
								String NOMECTOUNID = request.getParameter("NOMECTOUNID_" + d + "_" + f);
								daoContatoProcedimento.setAttValueFor("NOME", NOMECTOUNID);
								String PARENCTOUNID = request.getParameter("PARENCTOUNID_" + d + "_" + f);
								daoContatoProcedimento.setAttValueFor("COD_GRAU", PARENCTOUNID);
								daoContatoProcedimento.Create();
							}
							PerguntaProcedimentoDAO daoPerguntaProcedimento = new PerguntaProcedimentoDAO();
							String SENHA = request.getParameter("SENHA_ATENDIMENTO_"+d).trim();
							daoPerguntaProcedimento.setAttValueFor("COD_CLIENTE", pkListValue);
							daoPerguntaProcedimento.setAttValueFor("SENHA", SENHA);
							daoPerguntaProcedimento.setAttValueFor("COD_UNIDADE_CADASTRADA", pkCodVeiculo);
							daoPerguntaProcedimento.setAttValueFor("COD_UNIDADE", TIPO_UNIDADE_);
							daoPerguntaProcedimento.setAttValueFor("DELETED", 0);
							daoPerguntaProcedimento.Create();
						}
						arraysize = Integer.parseInt(request.getParameter("QEQUIP_ACESSORIO").trim());
						for(int d = 0 ; d < arraysize ; d++){
							EquipAcessorioPedidoDAO daoEquipAcessorioPedido = new EquipAcessorioPedidoDAO();
							String COD_EQUIP_ACESSORIO = request.getParameter("ITEMEQUIP_"+d);
							daoEquipAcessorioPedido.setAttValueFor("COD_EQUIP_ACESSORIO", COD_EQUIP_ACESSORIO);
							String QUANTIDADE = request.getParameter("QTDEQUIP_"+d);
							daoEquipAcessorioPedido.setAttValueFor("QUANTIDADE", QUANTIDADE);
							String VALOR_UNITARIO = request.getParameter("VALOREQUIP_"+d);
							daoEquipAcessorioPedido.setAttValueFor("VALOR_UNITARIO", VALOR_UNITARIO);
							daoEquipAcessorioPedido.setAttValueFor("COD_PEDIDO", pkCodPedido);
							daoEquipAcessorioPedido.setAttValueFor("DELETED", 0);
							daoEquipAcessorioPedido.Create();
						}
						arraysize = Integer.parseInt(request.getParameter("QSERVICO").trim());
						for(int d = 0 ; d < arraysize ; d++){
							ServicoPedidoDAO daoServicoPedido = new ServicoPedidoDAO();
							String COD_SERVICO = request.getParameter("ITEMSERVICO_"+d);
							daoServicoPedido.setAttValueFor("COD_SERVICO", COD_SERVICO);
							String QUANTIDADE = request.getParameter("QTDSERVICO_"+d);
							daoServicoPedido.setAttValueFor("QUANTIDADE", QUANTIDADE);
							String VALOR_UNITARIO = request.getParameter("VALORSERVICO_"+d);
							daoServicoPedido.setAttValueFor("VALOR_UNITARIO", VALOR_UNITARIO);
							daoServicoPedido.setAttValueFor("COD_PEDIDO", pkCodPedido);
							daoServicoPedido.setAttValueFor("DELETED", 0);
							daoServicoPedido.Create();
						}
						DadosInstalacaoDAO daoDadosInstalacao = new DadosInstalacaoDAO();
						String ENDERECO = request.getParameter("ENDINST");
						daoDadosInstalacao.setAttValueFor("ENDERECO",ENDERECO);
						String BAIRRO = request.getParameter("BAIRROINST");
						daoDadosInstalacao.setAttValueFor("BAIRRO",BAIRRO);
						String CIDADE = request.getParameter("CIDADEINST");
						daoDadosInstalacao.setAttValueFor("CIDADE",CIDADE);
						String UF = request.getParameter("UFINST");
						daoDadosInstalacao.setAttValueFor("UF",UF);
						String COD_PAIS = request.getParameter("PAISINST");
						daoDadosInstalacao.setAttValueFor("COD_PAIS",COD_PAIS);
						String COMPLEMENTO = request.getParameter("COMPINST");
						daoDadosInstalacao.setAttValueFor("COMPLEMENTO",COMPLEMENTO);
						String CEP = request.getParameter("CEPINST");
						daoDadosInstalacao.setAttValueFor("CEP",CEP);
						String REFERENCIA = request.getParameter("REFINST");
						daoDadosInstalacao.setAttValueFor("REFERENCIA",REFERENCIA);
						String DDD = request.getParameter("DDDINST");
						daoDadosInstalacao.setAttValueFor("DDD",DDD);
						String NUMERO = request.getParameter("NUMEROINST");
						daoDadosInstalacao.setAttValueFor("NUMERO",NUMERO);
						String NOMEINST = request.getParameter("NOMEINST");
						daoDadosInstalacao.setAttValueFor("NOME",NOMEINST);
						daoDadosInstalacao.setAttValueFor("COD_PEDIDO",pkCodPedido);
						daoDadosInstalacao.setAttValueFor("DELETED",0);
						daoDadosInstalacao.Create();
						arraysize = Integer.parseInt(request.getParameter("QOBS").trim());
						for(int d = 0 ; d < arraysize ; d++){
							ObsPedidoDAO daoObsPedido = new ObsPedidoDAO();
							daoObsPedido.setAttValueFor("COD_PEDIDO",pkCodPedido);
							daoObsPedido.setAttValueFor("COD_CLIENTE",pkListValue);
							daoObsPedido.setAttValueFor("INDICE",d);
							String OBSERVACAO = request.getParameter("OBSERVACAO_"+d);
							daoObsPedido.setAttValueFor("OBSERVACAO",OBSERVACAO);
							daoObsPedido.setAttValueFor("DELETED",0);
							daoObsPedido.Create();
						}
					}else{
						out.println("Error on NovoPedidoServiceServlet... " + "\nNUM_PEDIDO n�o encontrado! ");
					}
				}
			}response.sendRedirect(ZXMain.URL_ADRESS_ + "services/startservlet?OP_CODE=STARTFLUX&COD_USUARIO=" + COD_USUARIO + "&PROCESS_ID=1&PK_COLUMN=" + pkCodPedido);
		}catch(Exception e){
			out.println("Error on ClienteServiceServlet... " + ' ' + e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
