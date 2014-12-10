<!--
ZIRIX CONTROL CENTER - CADASTRO DE NOVO PEDIDO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>
<%
	String user = null;
	if(session.getAttribute("user") == null){
		response.setContentType("text/html");
		response.sendRedirect("index.html");
	}else{
		user = (String) session.getAttribute("user");
	}

	String[] PK_OBJ = {request.getParameter("PK_OBJ")};
	String WORK_ID = request.getParameter("WORK_ID");
	String AREA = request.getParameter("AREA");
	NovoPedidoServiceBean beanPedido = new NovoPedidoServiceBean(PK_OBJ);
	String[] pkCodCliente = {beanPedido.getCodCliente()};
	ClienteServiceBean beanCliente = new ClienteServiceBean(pkCodCliente);
	ScheduleBean bean = new ScheduleBean(user);
	bean.setStartTimestamp(WORK_ID);
%>
<!--Comercial -> Novo Pedido-->
<div id="comercial-novo-pedido-content">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#aba_cliente" data-toggle="tab">Cliente</a></li>
		<li><a href="#aba_unidade" data-toggle="tab"<%if(AREA.compareTo("FIN")==0){%> hidden="hidden"<%}%>>Unidades</a></li>
		<li><a href="#aba_servicos" data-toggle="tab"<%if(AREA.compareTo("FIN")==0){%> hidden="hidden"<%}%>>Serviços</a></li>
		<li><a href="#aba_observacoes" data-toggle="tab"<%if(AREA.compareTo("FIN")==0){%> hidden="hidden"<%}%>>Observações</a></li>
		<li><a href="#aba_anexos" data-toggle="tab" hidden="hidden">Anexos</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane active" id="aba_cliente">
			<fieldset class="field">
			<legend><b>Cliente:</b></legend>
				<%if(beanCliente.getTipo() == 0){%>
					<b>Pessoa Física</b>
				<%}else{%>
					<b>Pessoa Jurídica</b>
				<%}%>
				<fieldset class="fild_vendedor">
					<b>Vendedor:</b>
					<%try {
						Vector<VendedorDAO> list = VendedorDAOService.loadAll();
						for (int i=0;i < list.size();i++) {
							VendedorDAO dao = list.elementAt(i);
							String str = String.valueOf(dao.getAttValueFor("NOME_FANTASIA")).trim();
							int codVendedor = beanCliente.getCodVendedor();
							if(dao.getPkValueFor("COD_VENDEDOR") == codVendedor){%> <%=str%><%}%>
						<%}%>
					<%}catch (Exception e){
						out.println("Error ao retornar vendedor... " + e.getMessage());
					}%>
				</fieldset>
					<br><b>Razão Social / Nome:</b> <%=beanCliente.getNome().trim()%>
					<br><b>Nome Fantasia / Apelido:</b> <%=beanCliente.getNomeFantasia().trim()%>
					<br><b>Data de Nascimento:</b> <%=beanCliente.getDtNascimento().trim()%>
					<%if(beanCliente.getSite().trim().compareTo("") !=0 ){%><br><b>Site:</b> <%=beanCliente.getSite().trim()%><%}%>
			</fieldset>
			<fieldset class="field">
			<legend><b>Documentos:</b></legend>
				<div id="documentos">
					<%Vector<String[]> docList = beanCliente.getDocumento();%>
					<select id="tipodoc_list">
					<%try{
						Vector<TipoDocumentoDAO> list = TipoDocumentoDAOService.loadAll();
						for (int j=0;j<list.size();j++) {
							TipoDocumentoDAO dao = list.elementAt(j);
							String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>			   	
								<option value="<%=dao.getPkValueFor("COD_DOCUMENTO")%>" name="option_documento_tipo"><%=str%></option>
						<%}%>
					<%}catch (Exception e){
						out.println("Error... " + e.getMessage());
					}%></select>
					<div id="docs_inserido">
						<%for (int i=0;i < docList.size();i++) {%>
							<div id="doc_oculta_<%=i%>" class="class_oculta">
								<div id="num_doc_oculta_<%=i%>"><%=docList.elementAt(i)[0].trim()%></div>
								<div id="tipo_doc_oculta_<%=i%>"><%=docList.elementAt(i)[1].trim()%></div>
								<div id="dt_emiss_doc_oculta_<%=i%>"><%=docList.elementAt(i)[2].trim()%></div>
								<div id="org_emiss_doc_oculta_<%=i%>"><%=docList.elementAt(i)[3].trim()%></div>
							</div>
						<%}%>
					</div>
				</div>
			</fieldset>
			<fieldset class="field" <%if(AREA.compareTo("FIN")==0){%> hidden="hidden"<%}%>>
			<legend><b>Endereço:</b></legend>
				<div id="endereco">
					<%Vector<String[]> endList = beanCliente.getEnd();%>
					<select id="tipo_end_list" class="size_27">
					<%try{
					Vector<TipoEnderecoDAO> list = TipoEnderecoDAOService.loadAll();
					for (int j=0;j<list.size();j++) {
						TipoEnderecoDAO dao = list.elementAt(j);
						String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>		   	
						<option value="<%=dao.getPkValueFor("COD_ENDERECO")%>" name="option_endereco_tipo"><%=str%></option>
						<%}%>
					<%}catch (Exception e){
						out.println("Error... " + e.getMessage());
					}%></select>
					<select id="pais_list" class="size_16" onchange="">
					<%try {
					Vector<PaisDAO> list = PaisDAOService.loadAll();
					for (int j=0;j<list.size();j++) {
						PaisDAO dao = list.elementAt(j);
						String str = String.valueOf(dao.getAttValueFor("NOME_PAIS")).trim();%>				   	
						<option value="<%=dao.getPkValueFor("COD_PAIS")%>" name="option_endereco_pais"<% 
						if(str.compareTo("Brasil") == 0){%> selected<%}%>><%=str%></option>
						<%}%>
					<%} catch (Exception e) {
						out.println("Error... " + e.getMessage());
					}%></select>
					<select id="uf_list" class="size_8">
						<option value="1" name="option_endereco_uf">AC</option>
						<option value="2" name="option_endereco_uf">AL</option>
						<option value="3" name="option_endereco_uf">AP</option>
						<option value="4" name="option_endereco_uf">AM</option>
						<option value="5" name="option_endereco_uf">BA</option>
						<option value="6" name="option_endereco_uf">CE</option>
						<option value="7" name="option_endereco_uf">DF</option>
						<option value="8" name="option_endereco_uf">ES</option>
						<option value="9" name="option_endereco_uf">GO</option>
						<option value="10" name="option_endereco_uf">MA</option>
						<option value="11" name="option_endereco_uf">MT</option>
						<option value="12" name="option_endereco_uf">MS</option>
						<option value="13" name="option_endereco_uf">MG</option>
						<option value="14" name="option_endereco_uf">PA</option>
						<option value="15" name="option_endereco_uf">PB</option>
						<option value="16" name="option_endereco_uf">PR</option>
						<option value="17" name="option_endereco_uf">PE</option>
						<option value="18" name="option_endereco_uf">PI</option>
						<option value="19" name="option_endereco_uf">RJ</option>
						<option value="20" name="option_endereco_uf">RN</option>
						<option value="21" name="option_endereco_uf">RS</option>
						<option value="22" name="option_endereco_uf">RO</option>
						<option value="23" name="option_endereco_uf">RR</option>
						<option value="24" name="option_endereco_uf">SC</option>
						<option value="25" name="option_endereco_uf">SP</option>
						<option value="26" name="option_endereco_uf">SE</option>
						<option value="27" name="option_endereco_uf">TO</option>
						<option value="28" name="option_endereco_uf">OUTRO</option>
					</select>
					<div id="endereco_inserido">
						<%for (int i=0;i < endList.size();i++) {%>
							<div id="end_oculta_<%=i%>" class="class_oculta">
								<div id="endereco_oculta_<%=i%>"><%=endList.elementAt(i)[0].trim()%></div>
								<div id="bairro_oculta_<%=i%>"><%=endList.elementAt(i)[1].trim()%></div>
								<div id="cidade_oculta_<%=i%>"><%=endList.elementAt(i)[2].trim()%></div>
								<div id="uf_oculta_<%=i%>"><%=endList.elementAt(i)[3].trim()%></div>
								<div id="pais_oculta_<%=i%>"><%=endList.elementAt(i)[4].trim()%></div>
								<div id="complemento_oculta_<%=i%>"><%=endList.elementAt(i)[5].trim()%></div>
								<div id="cep_oculta_<%=i%>"><%=endList.elementAt(i)[6].trim()%></div>
								<div id="tipo_end_oculta_<%=i%>"><%=endList.elementAt(i)[7].trim()%></div>
							</div>
						<%}%>
					</div>
				</div>
			</fieldset>
			<fieldset class="field"<%if(AREA.compareTo("FIN")==0){%> hidden="hidden"<%}%>>
			<legend><b>Contato:</b></legend>
				<div id="contato">
					<%Vector<String[]> contatoList = beanCliente.getContato();%>
					<select id="tipocont_list" class="size_21">
                    <%try{
						Vector<TipoContatoDAO> list = TipoContatoDAOService.loadAll();
						for (int i=0;i < list.size();i++) {
							TipoContatoDAO dao = list.elementAt(i);
							String str = String.valueOf(dao.getAttValueFor("NOME_TIPO")).trim();%>					   	
							<option value="<%=dao.getPkValueFor("COD_CONTATO")%>" name="option_contato_tipo"><%=str%></option>
						<%}%>
					<%}catch (Exception e){
						out.println("Error... " + e.getMessage());
					}%></select>
					<select id="info_contato_list" class="size_35">
					<%try {
						Vector<InfoContatoDAO> list = InfoContatoDAOService.loadAll();
						for (int i=0;i < list.size();i++) {
							InfoContatoDAO dao = list.elementAt(i);
							String str = String.valueOf(dao.getAttValueFor("NOME_GRAU")).trim();%>
							<option value="<%=dao.getPkValueFor("COD_GRAU")%>"  name="option_parentesco_cargo"><%=str%></option>
						<%}%>
					<%}catch (Exception e){
						out.println("Error... " + e.getMessage());
					}%></select>
					<div id="contato_inserido">
						<%for (int i=0;i < contatoList.size();i++) {%>
							<div id="contato_oculta_<%=i%>" class="class_oculta">
								<div id="tipo_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[0].trim()%></div>
								<div id="ddd_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[1].trim()%></div>
								<div id="numero_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[2].trim()%></div>
								<div id="cod_pais_oculta_<%=i%>"><%=contatoList.elementAt(i)[3].trim()%></div>
								<div id="nome_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[4].trim()%></div>
								<div id="grau_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[5].trim()%></div>
							</div>
						<%}%>
					</div>
				</div>
			</fieldset>
			<fieldset class="field"<%if(AREA.compareTo("FIN")==0){%> hidden="hidden"<%}%>>
			<legend><b>E-mail:</b></legend>
				<div id="email">
					<div id="emails_inserido">
						<%Vector<String[]> emailList = beanCliente.getEmail();
						for (int i=0;i < emailList.size();i++) {%>
							<div id="email_oculta_<%=i%>" class="class_oculta">
								<div id="nome_email_oculta_<%=i%>"><%=emailList.elementAt(i)[0].trim()%></div>
							</div>
						<%}%>
					</div>
				</div>
			</fieldset>
		</div>
		<div class="tab-pane" id="aba_unidade">
			<fieldset class="field">
			<legend><b>Dados para Instalação:</b></legend>
				<%Vector<String[]> instalacaoList = beanPedido.getDadosInstalacao();
					for (int i=0;i < instalacaoList.size();i++) {%>
						<b>Endereço:</b>&nbsp;<%=instalacaoList.elementAt(i)[0].trim()%>
						<%if(instalacaoList.elementAt(i)[5].trim().compareTo("") !=0 ){%>
							<br><b>Complemento:</b>&nbsp;<%=instalacaoList.elementAt(i)[5].trim()%>
						<%}%>
						<br><b>Bairro:</b>&nbsp;<%=instalacaoList.elementAt(i)[1].trim()%>&nbsp;&nbsp;&nbsp;<b>Cidade:</b>&nbsp;<%=instalacaoList.elementAt(i)[2].trim()%>&nbsp;&nbsp;&nbsp;<b>UF.:</b>&nbsp;<div id="uf_inst_visu" style="display: inline-block;"><%=instalacaoList.elementAt(i)[3].trim()%></div>
						<br><b>País:</b>&nbsp;<%=instalacaoList.elementAt(i)[4].trim()%>&nbsp;&nbsp;&nbsp;<b>CEP.:</b>&nbsp;<%=instalacaoList.elementAt(i)[6].trim()%>
						<br><b>Ponto de Referência:</b>&nbsp;<%=instalacaoList.elementAt(i)[7].trim()%>
						<br><b>Contato Responsável:</b>&nbsp;<%=instalacaoList.elementAt(i)[10].trim()%>&nbsp;-&nbsp;(<%=instalacaoList.elementAt(i)[8].trim()%>)&nbsp;<%=instalacaoList.elementAt(i)[9].trim()%>
					<%} %>
			</fieldset>
			<fieldset class="field">
			<legend><b>Unidades Rastreadas:</b></legend>
				<ul class="nav nav-tabs">
					<%Vector<String[]> CodUnidadesVeiculo = beanPedido.getCodUnidadesVeiculo();
					for (int i=0;i < CodUnidadesVeiculo.size();i++) {%>
						<li<%if(i==0){%> class="active"<%}%>><a href="#aba_unidade_<%=i+1%>" data-toggle="tab">Veículo <%=i+1%></a></li>
					<%}%>
				</ul>
				<div class="tab-content" style="background: #eeeeee;">
					<%for (int i=0;i < CodUnidadesVeiculo.size();i++) {%>
						<div class="tab-pane<%if(i==0){%> active<%}%>" id="aba_unidade_<%=i+1%>">
						<%try{
							Vector<VeiculoDAO> listVeiculo = VeiculoDAOService.loadAllPedido(Integer.parseInt(CodUnidadesVeiculo.elementAt(i)[0]));
							for(int j=0; j<listVeiculo.size(); j++){
								VeiculoDAO dao = listVeiculo.elementAt(j);%>
								<b>Placa: </b>&nbsp;<%=String.valueOf(dao.getAttValueFor("PLACA")).trim()%>&nbsp;&nbsp;&nbsp;<b>Chassi:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("CHASSI")).trim()%>&nbsp;&nbsp;&nbsp;<b>Renavam:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("RENAVAN")).trim()%>
								<br><b>Marca:</b>&nbsp;<%=beanPedido.getNomeMarca(Integer.parseInt(String.valueOf(dao.getAttValueFor("COD_MARCA")).trim()))%>&nbsp;&nbsp;&nbsp;<b>Modelo:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("MODELO")).trim()%>
								<br><b>Ano de Fabricação:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("ANO_FAB")).trim()%>&nbsp;&nbsp;&nbsp;<b>Ano do Modelo:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("ANO_MOD")).trim()%>&nbsp;&nbsp;&nbsp;<b>Cor:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("COR")).trim()%>
								<br><b>Tipo de Combustível:</b>&nbsp;<%=beanPedido.getNomeCombustivel(Integer.parseInt(String.valueOf(dao.getAttValueFor("COD_COMBUSTIVEL")).trim()))%>&nbsp;&nbsp;&nbsp;<b>Voltagem:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("VOLT")).trim()%>&nbsp;&nbsp;&nbsp;<b>KM:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("KM")).trim()%>
								<br><b>Data da Última Vistoria:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("DATA_ULT_VISTORIA")).trim()%>
								<%if(AREA.compareTo("CAD_GS")==0){%>
									<br><b>Senha para Atendimento:</b>&nbsp;<%=beanPedido.getSenhaAtendimento(Integer.parseInt(CodUnidadesVeiculo.elementAt(i)[0]))%>
								<%}else{%>
									<br><b>Senha para Atendimento:</b>&nbsp;****
								<%}%>
								<fieldset class="fieldinner">
								<legend><b>Contatos para Procedimento:</b></legend>
									<%Vector<String[]> contatoProcedimentoList = beanPedido.getContatoProcedimento(Integer.parseInt(CodUnidadesVeiculo.elementAt(i)[0]));
									for(int l=0;l<contatoProcedimentoList.size();l++){
										if(l!=0){%>
											<canvas id="myCanvasCtoProced_<%=i%>_<%=l%>" width="550" height="1" style="border:0px;"></canvas>
											<script>var c = document.getElementById("myCanvasCtoProced_<%=i%>_<%=l%>");
											var ctx = c.getContext("2d");
											ctx.moveTo(0,0);
											ctx.lineTo(550,0);
											ctx.stroke();</script>
										<%}%>
										<b>Nome:</b>&nbsp;<%=contatoProcedimentoList.elementAt(l)[4].trim()%>&nbsp;&nbsp;&nbsp;<b>Parentesco/Cargo:</b>&nbsp;<%=contatoProcedimentoList.elementAt(l)[5].trim()%>
										<br><b><%=contatoProcedimentoList.elementAt(l)[0].trim()%>:</b>&nbsp;+<%=contatoProcedimentoList.elementAt(l)[3].trim()%>(<%=contatoProcedimentoList.elementAt(l)[1].trim()%>)&nbsp;<%=contatoProcedimentoList.elementAt(l)[2].trim()%>
										<br>
									<%}%>
								</fieldset>
								<%if(AREA.compareTo("SEP_EQUIP")==0){
									String COD_VEICULO = CodUnidadesVeiculo.elementAt(i)[0];%>
									<b>Equipamento selecionado:</b>&nbsp;<input list="id_list_<%=i%>" name="id_modulo_<%=i%>" id="item_id_modulo_<%=i%>" class="size_30">
									<datalist id="id_list_<%=i%>">
										<%try{
											Vector<ModuloDAO> list = ModuloDAOService.loadAllEstoque();
											for (int f=0;f < list.size();f++) {
												ModuloDAO daoModulo = list.elementAt(f);
												String str = String.valueOf(daoModulo.getAttValueFor("NUMERO_MODULO")).trim();%>				   	
												<option value="<%=str%>" data-label="<%=daoModulo.getPkValueFor("COD_MODULO")%>--<%=COD_VEICULO%>">
											<%}%>
										<%}catch (Exception e) {
											out.println("Error... " + e.getMessage());
										  }%>
							        </datalist>
						        <%}%>
							<%}
						}catch (Exception e){
							out.println("Error ao preencher abas unidades... " + e.getMessage());
						}%>
						</div>
					<%}%>
				</div>
			</fieldset>
		</div>
		<div id="QtdUnidadesVeiculo" style="visibility: hidden;"><%=CodUnidadesVeiculo.size()%></div>
		<div class="tab-pane" id="aba_servicos">
			<fieldset class="field">
			<legend><b>Tipo do Pedido:</b></legend>
				<b>Pedido de <%=beanPedido.getTipoPedido()%> <%if(beanPedido.getTipoPedido().compareTo("Teste") == 0){%> por <%=beanPedido.getInfoPedido()%> dias<%}%> - Nº:</b> <%=beanPedido.getNumeroPedido()%>
				<br><b>Dia de Vencimento:</b> <%=beanPedido.getDataVencimento()%>&nbsp;-&nbsp;Cliente<%if(beanPedido.getEnvioBoleto() == 0){%> não<%}%> deseja que o boleto seja enviado por e-mail.
				<fieldset class="fieldinner">
				<legend><b>Serviços:</b></legend> 
					<%try{
						Vector<String[]> listServico = beanPedido.getServicoPedido();
						for(int i=0; i<listServico.size();i++){
							if(i!=0){%>
								<canvas id="myCanvasServico_<%=i%>" width="650" height="1" style="border:0px;"></canvas>
								<script>
									var c = document.getElementById("myCanvasServico_<%=i%>");
									var ctx = c.getContext("2d");
									ctx.moveTo(0,0);
									ctx.lineTo(650,0);
									ctx.stroke();
								</script>
							<%}%>
							<b><%=listServico.elementAt(i)[0].trim()%>:</b> <%=listServico.elementAt(i)[1].trim()%> unid. - <b>Valor Unitário:</b> R$<%=listServico.elementAt(i)[2].trim()%>   <b>Total:</b> R$<%=listServico.elementAt(i)[3].trim()%>
						<%}
					}catch (Exception e){
						out.println("Error ao preencher serviços... " + e.getMessage());
					}%>
				</fieldset>
				<b>Valor Total dos Serviços:</b> R$<%=beanPedido.getValorTotalServico()%>
				<br>
				<fieldset class="fieldinner">
				<legend><b>Equipamentos/Acessórios:</b></legend>
					<%try{
						Vector<String[]> listEquipamento = beanPedido.getEquipAcessorioPedido();
						for(int i=0; i<listEquipamento.size();i++){
							if(i!=0){%>
								<canvas id="myCanvasEquipamento_<%=i%>" width="650" height="1" style="border:0px;"></canvas>
								<script>
									var c = document.getElementById("myCanvasEquipamento_<%=i%>");
									var ctx = c.getContext("2d");
									ctx.moveTo(0,0);
									ctx.lineTo(650,0);
									ctx.stroke();
								</script>
							<%}%>
							<b><%=listEquipamento.elementAt(i)[0].trim()%>:</b> <%=listEquipamento.elementAt(i)[1].trim()%> unid. - <b>Valor Unitário:</b> R$<%=listEquipamento.elementAt(i)[2].trim()%>   <b>Total:</b> R$<%=listEquipamento.elementAt(i)[3].trim()%>
						<%}
					}catch (Exception e){
						out.println("Error ao preencher equipamentos... " + e.getMessage());
					}%>
				</fieldset>
				<b>Valor Total dos Equipamentos e Acessórios:</b> R$<%=beanPedido.getValorTotalEquipamento()%>
			</fieldset>
		</div>
		<div class="tab-pane" id="aba_observacoes">
			<fieldset class="field">
			<legend><b>Observações:</b></legend>
				<%try{
					Vector<String[]> listObservacoes = beanPedido.getObsPedido();
					for(int i=0; i<listObservacoes.size(); i++){%>
						<%=listObservacoes.elementAt(i)[0].trim()%>
					<%}
				}catch(Exception e){
					out.println("Error ao preencher observações... " + e.getMessage());
				}%>
			</fieldset>
		</div>
		<div class="tab-pane" id="aba_anexos">
			<fieldset class="field">
				<input type="file" name="anexos" id="upload_arquivo" multiple="multiple">
				<button type="button" id="enviar_arquivo_anexo">Enviar</button>
				<br>
				<fieldset class="fieldinner">
				<legend>Arquivos:</legend>
					<div id="arquivos_upload">
					</div>
				</fieldset>
			</fieldset>
		</div>
		<%int combos = 0;
		if(AREA.compareTo("ADM")==0){
			combos = 1;
		}else if (AREA.compareTo("CAD_GS")==0){
			combos = 2;
		}else if (AREA.compareTo("CAD_SYSCOM")==0){
			combos = 3;
		}else if (AREA.compareTo("CAD_ZXLOG")==0){
			combos = 4;
		}else if (AREA.compareTo("SEP_EQUIP")==0){
			combos = 5;
		}else if (AREA.compareTo("FIN")==0){
			combos = 6;
		}%>
		<%switch(combos){
			case 1:%>
				<input type="checkbox" value="1" id="dadosCorretos">Confirmo que todos os dados do pedido estão corretos.
			<%	break;
			case 2:%>
				<input type="checkbox" value="1" id="dadosCorretos">Confirmo que todos os dados foram cadastrados corretamento no Global Search.
			<%	break;
			case 3:%>
				<input type="checkbox" value="1" id="dadosCorretos">Confirmo que todos os dados foram cadastrados corretamento no Syscom.
			<%	break;
			case 4:%>
				<input type="checkbox" value="1" id="dadosCorretos">Confirmo que todos os dados foram cadastrados corretamento no ZXLog.
			<%	break;
			case 5:%>
				<input type="checkbox" value="1" id="dadosCorretos">Confirmo ter realizado a separação dos equipamentos necessários para a instalação.
			<%	break;
			case 6:%>
				<input type="checkbox" value="1" id="scpSerasa">Confirmo que o cliente não possui restrição no SPC/SERASA.
		<%	break;
		}%>
		<br>
		<button type="button" id="conferido_modal" onclick="form_novo_pedido_function('<%=AREA%>','<%=WORK_ID%>','<%=PK_OBJ[0]%>')">Conferido</button>
		<button type="button" id="cancel_modal">Cancelar</button>
	</div>
</div>