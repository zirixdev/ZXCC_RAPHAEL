<!--
ZIRIX CONTROL CENTER - CONSULTA CLIENTE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<%
	String[] pkVal = {request.getParameter("COD_CLIENTE")};
	ClienteServiceBean beanCliente = new ClienteServiceBean(pkVal);
%>

<div id="operacional-consulta-cliente-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_cliente" data-toggle="tab">Cliente</a></li>
            <li><a href="#aba_contato" data-toggle="tab">Contatos</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_cliente">
                <fieldset class="field">
                <%if(beanCliente.getTipo() == 0){%>
                	<input type="radio" name="pessoa" value="pessoafisica" checked="checked">Pessoa Física
                    <input type="radio" name="pessoa" value="pessoajuridica">Pessoa Jurídica
                <%}else{%>
                	<input type="radio" name="pessoa" value="pessoafisica">Pessoa Física
                    <input type="radio" name="pessoa" value="pessoajuridica" checked="checked">Pessoa Jurídica
                <%}%>
                	<fieldset class="fild_vendedor">
                    	Vendedor: <select id="vendedor_list" class="size_60">
                    		<%        	
								try {
											
									Vector<VendedorDAO> list = VendedorDAOService.loadAll();
									for (int i=0;i < list.size();i++) {
										VendedorDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME_FANTASIA")).trim();
										int codVendedor = beanCliente.getCodVendedor();%>				   	
						       			   <option value="<%=dao.getPkValueFor("COD_VENDEDOR")%>" name="option_vendedor"<% 
											if(dao.getPkValueFor("COD_VENDEDOR") == codVendedor){%> selected
											<%}%>
											><%=str%></option>
						       		<%}%>
							<%
								   } catch (Exception e) {
									   out.println("Error... " + e.getMessage());
								   }	
							%>
                    	</select>
                    </fieldset>
                    <br>Razão Social / Nome:
                    <input type="text" id="nome_razaosocial" class="size_100" value="<%=beanCliente.getNome().trim()%>">
                    <br>Nome Fantasia / Apelido:
                    <input type="text" id="nomefantasia"  class="size_100" value="<%=beanCliente.getNomeFantasia().trim()%>">
                    <br>
                    Data de Nascimento: <input type="date" id="data_nasc" value="<%=beanCliente.getDtNascimento().trim()%>">
                    Site:<input type="text" id="url_site" class="size_50" value="<%=beanCliente.getSite().trim()%>">
                </fieldset>
                <fieldset class="field">
                    <legend>Documentos:</legend>
					<div id="div_doc">Número:
						<input type="text" class="size_25" id="numero_documento" onkeypress="javascript: return EntradaNumerico(event);">Tipo do Documento:
						<select id="tipodoc_list" >
                            <%        	
								try {
											
									Vector<TipoDocumentoDAO> list = TipoDocumentoDAOService.loadAll();
									for (int i=0;i < list.size();i++) {
										TipoDocumentoDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>			   	
						       			   <option value="<%=dao.getPkValueFor("COD_DOCUMENTO")%>" name="option_documento_tipo"><%=str%></option>
						       		<%}%>
							<%
								   } catch (Exception e) {
									   out.println("Error... " + e.getMessage());
								   }	
							%>
                        </select>
						<br>Data de Emissão:<input type="date" id="data_emissao">Órgão Emissor:
						<input class="size_28" type="text" id="orgao_emissor">
                    </div>
					<fieldset class="fieldinner">
                        <legend>Inseridos:</legend>
                        <div id="docs_inserido">
                        <%Vector<String[]> docList = beanCliente.getDocumento();
							for (int i=0;i < docList.size();i++) {%>
		                        <div id="doc_oculta_<%=i%>" class="class_oculta">
									<div id="num_doc_oculta_<%=i%>"><%=docList.elementAt(i)[0].trim()%></div>
									<div id="tipo_doc_oculta_<%=i%>"><%=docList.elementAt(i)[1].trim()%></div>
									<div id="dt_emiss_doc_oculta_<%=i%>"><%=docList.elementAt(i)[2].trim()%></div>
									<div id="org_emiss_doc_oculta_<%=i%>"><%=docList.elementAt(i)[3].trim()%></div>
								</div>
							<%}%>
                        </div>
                    </fieldset>
                    <div id="div_doc_bt">
                        <button type="button" id="incluir_documento" onclick="insert_documentos_function()">Incluir</button>
                    </div>
                </fieldset>
                <fieldset class="field">
                    <legend>Endereço:</legend>
                    <div id="div_end">
						Logradouro: <input type="text" class="size_100" id="endereco">
						<br>
						Complemento: <input type="text" class="size_23" id="complemento">
						Bairro: <input type="text" class="size_22" id="bairro">
						Cidade: <input type="text" class="size_22" id="cidade">
						<br>UF:&nbsp;
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
                        &nbsp;País:
                        <select id="pais_list" class="size_20" onchange="">
                           	<%try {
								Vector<PaisDAO> list = PaisDAOService.loadAll();
								for (int i=0;i < list.size();i++) {
									   PaisDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME_PAIS")).trim();%>				   	
									   <option value="<%=dao.getPkValueFor("COD_PAIS")%>" name="option_endereco_pais"<% 
											if(str.compareTo("Brasil") == 0){%> selected
											<%}%>
											><%=str%></option>
								<%}%>
							<%} catch (Exception e) {
								   out.println("Error... " + e.getMessage());
							   }%>
						</select>&nbsp;CEP:
						<input type="text" class="size_12" id="cep" maxlength="8"  onkeypress="javascript: return EntradaNumerico(event);">Tipo de Endereço:
                        <select id="tipo_end_list" class="size_24">
                            <%        	
								try {
											
									Vector<TipoEnderecoDAO> list = TipoEnderecoDAOService.loadAll();
									for (int i=0;i < list.size();i++) {
										TipoEnderecoDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>		   	
						       			   <option value="<%=dao.getPkValueFor("COD_ENDERECO")%>" name="option_endereco_tipo"><%=str%></option>
						       		<%}%>
							<%
								   } catch (Exception e) {
									   out.println("Error... " + e.getMessage());
								   }	
							%>
                        </select>
                        <br>
					</div>
                    <fieldset class="fieldinner">
                        <legend>Inseridos:</legend>
                        <div id="endereco_inserido">
							<%Vector<String[]> endList = beanCliente.getEnd();
								for (int i=0;i < endList.size();i++) {%>
			                        <div id="end_oculta_<%=i%>" class="class_oculta">
										<div id="endereco_oculta_<%=i%>"><%=endList.elementAt(i)[0].trim()%></div>
										<div id="bairro_oculta_<%=i%>"><%=endList.elementAt(i)[1].trim()%></div>
										<div id="cidade_oculta_<%=i%>"><%=endList.elementAt(i)[2].trim()%></div>
										<div id="uf_oculta_<%=i%>"><%=endList.elementAt(i)[3].trim()%></div>
										<div id="pais_oculta_<%=i%>"><%=endList.elementAt(i)[4].trim()%></div>
										<div id="complemento_oculta_<%=i%>"><%=endList.elementAt(i)[5].trim()%></div>
										<div id="cep_oculta_<%=i%>"><%=endList.elementAt(i)[6].trim()%></div>
										<div id="tipo_end_oculta_<%=i%>"><%=endList.elementAt(i)[7].trim()%></div>
										<br>
									</div>
								<%}%>
                        </div>
                    </fieldset>
                    <div id="div_end_bt">
                        <button type="button" id="incluir_endereco" onclick="insert_endereco_function()">Incluir</button>
                    </div>
                </fieldset>
            </div> 
            <div class="tab-pane" id="aba_contato">
                <fieldset class="field">
                    <div id="div_contato">
	                    DDD:<input type="text" class="size_5" id="ddd" maxlength="2" onkeypress="javascript: return EntradaNumerico(event);">
                        Número:<input type="text" class="size_19" id="numero_contato" maxlength="10" onkeypress="javascript: return EntradaNumerico(event);">
                        Tipo do Contato:
                        <select id="tipocont_list" class="size_21">
	                        <%        	
								try {
											
									Vector<TipoContatoDAO> list = TipoContatoDAOService.loadAll();
									for (int i=0;i < list.size();i++) {
										TipoContatoDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME_TIPO")).trim();%>					   	
						       			   <option value="<%=dao.getPkValueFor("COD_CONTATO")%>" name="option_contato_tipo"><%=str%></option>
						       		<%}%>
							<%
								   } catch (Exception e) {
									   out.println("Error... " + e.getMessage());
								   }	
							%>
	                    </select>
	                    Cod. País: <input type="text" class="size_5" id="cod_pais" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);">
	                    <br>Nome:<input class="size_32" type="text" id="nome_contato">
	                    Parentesco / Cargo:
                        <select id="info_contato_list" class="size_35">
                        	<%        	
								try {
											
									Vector<InfoContatoDAO> list = InfoContatoDAOService.loadAll();
									for (int i=0;i < list.size();i++) {
										InfoContatoDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME_GRAU")).trim();%>
						       			   <option value="<%=dao.getPkValueFor("COD_GRAU")%>"  name="option_parentesco_cargo"><%=str%></option>
						       		<%}%>
							<%
								   } catch (Exception e) {
									   out.println("Error... " + e.getMessage());
								   }	
							%>
                        </select>
                    </div>
                    <fieldset class="fieldinner">
                        <legend>Inseridos:</legend>
                        <div id="contato_inserido">
	                       	<%Vector<String[]> contatoList = beanCliente.getContato();
								for (int i=0;i < contatoList.size();i++) {%>
			                        <div id="contato_oculta_<%=i%>" class="class_oculta">
										<div id="tipo_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[0].trim()%></div>
										<div id="ddd_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[1].trim()%></div>
										<div id="numero_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[2].trim()%></div>
										<div id="cod_pais_oculta_<%=i%>"><%=contatoList.elementAt(i)[3].trim()%></div>
										<div id="nome_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[4].trim()%></div>
										<div id="grau_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[5].trim()%></div>
										<br>
									</div>
								<%}%>
                        </div>
                    </fieldset>
                    <div id="div_contato_bt">
                        <button type="button" id="incluir_contato" onclick="insert_contatos_function()">Incluir</button>
                    </div>
                </fieldset>
                <fieldset class="field">
                    <legend>E-mail:</legend>
                    <div id="div_email">
                        <input type="email" id="email" class="size_100">
                    </div>
                    <fieldset class="fieldinner">
                        <legend>Inseridos:</legend>
                        <div id="emails_inserido">
                            <%Vector<String[]> emailList = beanCliente.getEmail();
								for (int i=0;i < emailList.size();i++) {%>
			                        <div id="email_oculta_<%=i%>" class="class_oculta">
										<div id="nome_email_oculta_<%=i%>"><%=emailList.elementAt(i)[0].trim()%></div>
										<br>
									</div>
								<%}%>
                        </div>
                    </fieldset>
                    <div id="div_email_bt">
                        <button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>
                    </div>
                </fieldset>
            </div>
            <br>
            <div class="div_modal_bt">
	            <button type="button" id="salvar_modal" onclick="operacional_consultar_cliente_salvar_function()">Salvar</button>
	            <button type="button" id="cancel_modal">Cancelar</button>
            </div>
        </div>
    </form>
</div>