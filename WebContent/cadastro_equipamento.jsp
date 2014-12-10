<!--
ZIRIX CONTROL CENTER - CADASTRO DE EQUIPAMENTOS
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<!--Operacional -> Cadastro -> Equipamento-->
<div id="operacional-cadastro-equipamentos-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_modulo" data-toggle="tab">Módulo</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_modulo">
            	<fieldset class="field_control">
	                <fieldset class="field_equip">
	                    <legend>Dados do Equipamento:</legend>
	                    ID: <input type="text" class="size_91" id="numero_modulo" onkeypress="javascript: return EntradaNumerico(event);">
	                    <br>SN: <input type="text" class="size_90" id="sn_modulo">
	                    <br>NFe:<input type="text" class="size_89" id="numero_nfe" onkeypress="javascript: return EntradaNumerico(event);">
	                    <br>Marca:<select id="marca_equip" class="size_86" onchange="select_modelo_function()">
	                    	<option value="0" name="option_marca_modulo">---SELECIONE UM---</option>
	                    	<%try {
								Vector<MarcaModuloDAO> list = MarcaModuloDAOService.loadAll();
								for (int i=0;i < list.size();i++) {
									MarcaModuloDAO dao = list.elementAt(i);
									String str = String.valueOf(dao.getAttValueFor("NOME_MARCA")).trim();%>
						    		<option value="<%=dao.getPkValueFor("COD_MARCA")%>" name="option_marca_modulo"><%=str%></option>
						    	<%}%>
							<%}catch (Exception e) {
									out.println("Error... " + e.getMessage());
							  }%>
	                    </select>
	                    <div id="div_modelo_equip">
							Modelo:<input type="text" id="modelo_equip" class="size_84" disabled="disabled">
	                    </div>
	                    ICC-ID:<input list="iccid_list" name="iccid_consulta" id="item_iccid" class="size_83" onkeypress="javascript: return EntradaNumerico(event);" maxlength="20">
				        <datalist id="iccid_list">   	
						<%try {	
							Vector<ChipDAO> list = ChipDAOService.loadSemModulo();
							for (int i=0;i < list.size();i++) {
									ChipDAO dao = list.elementAt(i);
									String str = String.valueOf(dao.getAttValueFor("ICCID")).trim();%>				   	
									<option value="<%=str%>" data-label="<%=dao.getPkValueFor("COD_CHIP")%>">
				       	<%}%>
						<%}catch (Exception e) {
							   out.println("Error... " + e.getMessage());
						  }%>
				        </datalist>
	                </fieldset>
                </fieldset>
            </div>
            <div class="div_modal_bt">
	            <button type="button" id="incluir_modal" onclick="operacional_cadastrar_equipamento_function()">Incluir</button>
	            <button type="button" id="cancel_modal">Cancelar</button>
            </div>
        </div>
    </form>
</div>