<!--
ZIRIX CONTROL CENTER - FORMULÁRIO DE CONSULTA
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>
<!--Operacional -> Consulta -> Cliente-->
<div id="operacional-consulta-cliente-content">
    <ul class="nav nav-tabs">
		<li class="active"><a href="#nome" data-toggle="tab">Nome</a></li>
		<li><a href="#documento" data-toggle="tab">Documento</a></li>
	</ul>
	<div class="tab-content" style="background: #eeeeee;">
		<div class="tab-pane active" id="nome">
		    <fieldset class="fieldinner">
		        <b>Nome / Razão Social: </b><input list="nome_list" name="nome_razaosocial_consulta" id="item_nome_razao" class="size_65">
		        <datalist id="nome_list">      	
				<%try{
					Vector<ClienteDAO> list = ClienteDAOService.loadAll();
					for (int i=0;i < list.size();i++) {
						ClienteDAO dao = list.elementAt(i);
						String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>				   	
						<option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_CLIENTE")%>">
					<%}%>
				<%}catch(Exception e){
					out.println("Error... " + e.getMessage());
				}%>
		        </datalist>
		    </fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('NOME')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
	    </div>
		<div class="tab-pane" id="documento">
			<fieldset class="fieldinner">
				<b>Número Documento: </b><input type="text" class="size_16" name="numero_documento" id="num_doc_cons" disabled="disabled">
		        <b>Tipo do Documento: </b>
		       	<select id="tipodoc_list" disabled="disabled">
				<%try{
					Vector<TipoDocumentoDAO> list = TipoDocumentoDAOService.loadAll();
					for (int i=0;i < list.size();i++) {
						TipoDocumentoDAO dao = list.elementAt(i);
						String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>			   	
						<option value="<%=dao.getPkValueFor("COD_DOCUMENTO")%>" name="option_documento_tipo"><%=str%></option>
					<%}%>
				<%}catch(Exception e){
					out.println("Error... " + e.getMessage());
				}%>
				</select>
			</fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('DOC')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
	</div>
</div>

<!--Operacional -> Consulta -> Equipamento-->
<div id="operacional-consulta-equipamento-content">
    <br>
    <fieldset class="fieldinner">
        <b>ID Modulo: </b><input list="id_list" name="id_modulo_consulta" id="item_id_modulo" class="size_65">
        <datalist id="id_list">      	
		<%try{
			Vector<ModuloDAO> list = ModuloDAOService.loadAll();
			for (int i=0;i < list.size();i++) {
				ModuloDAO dao = list.elementAt(i);
				String str = String.valueOf(dao.getAttValueFor("NUMERO_MODULO")).trim();%>				   	
				<option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_MODULO")%>">
			<%}%>
		<%}catch(Exception e){
			out.println("Error... " + e.getMessage());
		  }%>
        </datalist>
    </fieldset>
	<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function()">Consultar</button>
	<button type="button" id="cancel_modal">Cancelar</button>
</div>

<!--Operacional -> Consulta -> Chip-->
<div id="operacional-consulta-chip-content">
    <br>
    <fieldset class="fieldinner">
        <b>ICC-ID: </b><input list="iccid_list" name="iccid_consulta" id="item_iccid" class="size_50">
        <datalist id="iccid_list">   	
		<%try{
			Vector<ChipDAO> list = ChipDAOService.loadAll();
			for (int i=0;i < list.size();i++) {
				ChipDAO dao = list.elementAt(i);
				String str = String.valueOf(dao.getAttValueFor("ICCID")).trim();%>				   	
				<option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_CHIP")%>">
       		<%}%>
		<%}catch(Exception e){
			out.println("Error... " + e.getMessage());
		}%>
        </datalist>
    </fieldset>
	<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function()">Consultar</button>
	<button type="button" id="cancel_modal">Cancelar</button>
</div>

<!--Operacional -> Consulta -> OS-->
<div id="operacional-consulta-os-content">
<%
	session.setAttribute("temListaOs", "0");
	String temListaOs = session.getAttribute("temListaOs").toString();
%>
	temListaOs = <%=temListaOs%>
    <ul class="nav nav-tabs">
		<li class="active"><a href="#numOs" data-toggle="tab">Número</a></li>
		<li><a href="#veiculo" data-toggle="tab">Veículo</a></li>
		<li><a href="#cliente" data-toggle="tab">Cliente</a></li>
		<li><a href="#data" data-toggle="tab">Data</a></li>
		<li><a href="#mesref" data-toggle="tab">Mês Referência</a></li>
		<li><a href="#tipoOs" data-toggle="tab">Tipo de OS</a></li>
	</ul>
	<div class="tab-content" style="background: #eeeeee;">
		<div class="tab-pane active" id="numOs">
		    <fieldset class="fieldinner">
		        <b>Nº. da OS: </b><input list="os_list" name="os_consulta" id="item_os" class="size_50">
		        <datalist id="os_list">   	
				<%try{
					Vector<String[]> list = OsDAOService.loadAllNumeroOs();
					for(int i=0;i < list.size();i++){
						String str = list.elementAt(i)[1].trim();%>				   	
						<option value="<%=str%>" data-label="<%=list.elementAt(i)[0].trim()%>">
			    	<%}%>
				<%}catch(Exception e){
					out.println("Error... " + e.getMessage());
				}%>
		        </datalist>
		    </fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('NUMOS')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
		<div class="tab-pane" id="veiculo">
		    <fieldset class="fieldinner">
				<b>Placa: </b><input list="veic_list" name="os_consulta" id="item_veic" class="size_50">
		        <datalist id="veic_list">   	
				<%try{
					Vector<VeiculoDAO> list = VeiculoDAOService.loadAll();
					for (int i=0;i < list.size();i++) {
						VeiculoDAO dao = list.elementAt(i);
						String str = String.valueOf(dao.getAttValueFor("PLACA")).trim();%>				   	
						<option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_VEICULO")%>">
			    	<%}%>
				<%}catch(Exception e){
					out.println("Error... " + e.getMessage());
				}%>
		        </datalist>
		    </fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('VEIC')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
		<div class="tab-pane" id="cliente">
			<fieldset class="fieldinner">
		        <b>Nome / Razão Social: </b><input list="nome_list" name="nome_razaosocial_consulta" id="item_nome_razao" class="size_65">
		        <datalist id="nome_list">      	
				<%try{
					Vector<ClienteDAO> list = ClienteDAOService.loadAll();
					for (int i=0;i < list.size();i++) {
					   ClienteDAO dao = list.elementAt(i);
					   String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>				   	
	       			   <option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_CLIENTE")%>">
		       		<%}%>
				<%}catch(Exception e){
				   out.println("Error... " + e.getMessage());
				}%>
		        </datalist>
	        </fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('CLIENTE')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
		<div class="tab-pane" id="data">
		    <fieldset class="fieldinner">
				<b>Data: </b><input type="date" id="data_busca_os">
			</fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('DATA')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
		<div class="tab-pane" id="mesref">
			<fieldset class="fieldinner">
				<%int ano = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(new java.util.Date())); %>
				<%int mes = Integer.parseInt(new java.text.SimpleDateFormat("MM").format(new java.util.Date())); %>
				<b>Ano: </b><input type="number" min="2014" max="<%=ano%>" value="<%=ano%>" id="ano_os"> <b>Mês: </b><input type="number" min="01" max="12"  value="<%=mes%>" id="mes_os">
			</fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('REF')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
		<div class="tab-pane" id="tipoOs">
			<fieldset class="fieldinner">
				<b>Tipo OS: </b><select id="tipoos_list">
				<%try{
					Vector<TipoOsDAO> list = TipoOsDAOService.loadAll();
					for (int i=0;i < list.size();i++) {
						TipoOsDAO dao = list.elementAt(i);
						String str = String.valueOf(dao.getAttValueFor("NOME_TIPO")).trim();%>			   	
						<option value="<%=dao.getPkValueFor("COD_TIPO_OS")%>" name="option_os_tipo"><%=str%></option>
					<%}%>
				<%}catch(Exception e){
					out.println("Error... " + e.getMessage());
				}%>
				</select>
			</fieldset>
			<button type="button" id="consultar_modal" value="Consultar" onclick="operacional_consulta_function('TIPO')">Consultar</button>
			<button type="button" id="cancel_modal">Cancelar</button>
		</div>
	</div>
</div>

<!--Comercial -> Consulta -> Cliente-->
<div id="comercial-consulta-cliente-content">
    <br>
    <fieldset class="fieldinner">
        <b>Nome / Razão Social: </b><input list="nome_list" name="nome_razaosocial_consulta" id="item_nome_razao" class="size_65">
        <datalist id="nome_list">      	
		<%try{
			Vector<ClienteProspeccaoDAO> list = ClienteProspeccaoDAOService.loadAll();
			for (int i=0;i < list.size();i++) {
				ClienteProspeccaoDAO dao = list.elementAt(i);
				String str = String.valueOf(dao.getAttValueFor("NOME")).trim();%>				   	
				<option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_CLIENTE_PROSPECCAO")%>">
			<%}%>
		<%}catch(Exception e){
			out.println("Error... " + e.getMessage());
		}%>
        </datalist>
    </fieldset>
</div>