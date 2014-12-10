<!--
ZIRIX CONTROL CENTER - CADASTRO CLIENTE
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<!--Operacional -> Cadastro -> Cliente-->
<div id="operacional-cadastro-cliente-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_cliente" data-toggle="tab">Cliente</a></li>
            <li><a href="#aba_contato" data-toggle="tab">Contatos</a></li>
        </ul>
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_cliente">
                <fieldset class="field">
                    <input type="radio" name="pessoa" value="pessoafisica" checked="checked">Pessoa Física
                    <input type="radio" name="pessoa" value="pessoajuridica">Pessoa Jurídica
                    <fieldset class="fild_vendedor">
                    	Vendedor: <select id="vendedor_list" class="size_60">
                    		<%        	
								try {
											
									Vector<VendedorDAO> list = VendedorDAOService.loadAll();
									for (int i=0;i < list.size();i++) {
										VendedorDAO dao = list.elementAt(i);
										int codVendedor = dao.getPkValueFor("COD_VENDEDOR");
										String str = String.valueOf(dao.getAttValueFor("NOME_FANTASIA")).trim();%>
						       			<option value="<%=codVendedor%>" name="option_vendedor"
						       			<%if(codVendedor==1) {%>selected<%}%>
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
                    <input type="text" id="nome_razaosocial" class="size_100">
                    <br>Nome Fantasia:
                    <input type="text" id="nomefantasia"  class="size_100">
                    <br>Data de Nascimento: <input type="date" id="data_nasc">Site:<input type="text" id="url_site" class="size_50">
                </fieldset>
                <fieldset class="field">
                    <legend>Documentos:</legend>
                    <div id="div_doc">Número:
                        <input type="text" class="size_25" id="numero_documento" onkeypress="javascript: return EntradaNumerico(event);">Tipo do Documento:
                        <select id="tipodoc_list">
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
                        <br>Data de Emissão:&nbsp;<input type="date" id="data_emissao">&nbsp;Órgão Emissor:
                        <input class="size_28" type="text" id="orgao_emissor">
                    </div>
                    <fieldset class="fieldinner">
                        <legend>Inseridos:</legend>
                        <div id="docs_inserido">
                            
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
                        Complemento: <input type="text" class="size_22" id="complemento">
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
                        <select id="pais_list" class="size_16" onchange="">
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
                        <input type="text" class="size_11" id="cep" maxlength="8" onkeypress="javascript: return EntradaNumerico(event);">Tipo de Endereço:
                        <select id="tipo_end_list" class="size_27">
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
                        <br>Nome:<input type="text" class="size_32" id="nome_contato">
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
                            
                        </div>
                    </fieldset>
                    <div id="div_contato_bt">
                        <button type="button" id="incluir_contato" onclick="insert_contatos_function(false)">Incluir</button>
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
                            
                        </div>
                    </fieldset>
                    <div id="div_email_bt">
                        <button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>
                    </div>
                </fieldset>
            </div>
            <div class="div_modal_bt">
	           <button type="button" id="incluir_modal" onclick="operacional_cadastrar_cliente_cadastrar_function()">Incluir</button>
	           <button type="button" id="cancel_modal">Cancelar</button>
            </div>
        </div>
    </form>
</div>