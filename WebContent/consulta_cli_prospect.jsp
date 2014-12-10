<!--
ZIRIX CONTROL CENTER - CONSULTA CLIENTE PROSPECÇÃO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<%
	String[] pkVal = {request.getParameter("COD_CLIENTE_PROSPECCAO")};
	ClienteProspectServiceBean bean = new ClienteProspectServiceBean(pkVal);
%>

<div id="comercial-consulta-prospeccao-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_cliente" data-toggle="tab">Cliente</a></li>
            <li><a href="#aba_contato" data-toggle="tab">Contatos</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_cliente">
                <fieldset class="field">
                <%if(bean.getTipo() == 0){%>
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
										int codVendedor = bean.getCodVendedor();%>				   	
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
                    <input type="text" id="nome_razaosocial" class="size_100" value="<%=bean.getNome().trim()%>" maxlength="49">
                    <br>Responsável:
                    <input type="text" id="nomefantasia"  class="size_100" value="<%=bean.getNomeFantasia().trim()%>" maxlength="49">
                </fieldset>
                <fieldset class="field">
                    <legend>Contato:</legend>
                    <div id="div_contato">
	                    DDD:<input type="text" class="size_5" id="ddd" maxlength="2" onkeypress="javascript: return EntradaNumerico(event);">
                        Número:<input type="text" class="size_19" id="numero_contato" maxlength="9" onkeypress="javascript: return EntradaNumerico(event);">
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
                    </div>
                    <fieldset class="fieldinner">
                        <legend>Inseridos:</legend>
                        <div id="contato_inserido">
                        	<%Vector<String[]> contatoList = bean.getContato();
							for (int i=0;i < contatoList.size();i++) {%>
		                        <div id="contato_oculta_<%=i%>" class="class_oculta">
									<div id="tipo_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[0].trim()%></div>
									<div id="ddd_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[1].trim()%></div>
									<div id="numero_contato_oculta_<%=i%>"><%=contatoList.elementAt(i)[2].trim()%></div>
									<div id="cod_pais_oculta_<%=i%>"><%=contatoList.elementAt(i)[3].trim()%></div>
									<br>
								</div>
							<%}%>
                        </div>
                    </fieldset>
                    <div id="div_contato_bt">
                        <button type="button" id="incluir_contato" onclick="insert_contatos_prospect_function()">Incluir</button>
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
                            <%Vector<String[]> emailList = bean.getEmail();
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
	            <button type="button" id="salvar_modal" onclick="comercial_consultar_cliente_prospect_salvar_function()">Salvar</button>
	            <button type="button" id="cancel_modal">Cancelar</button>
            </div>
        </div>
    </form>
</div>