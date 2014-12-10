<!--
ZIRIX CONTROL CENTER - CADASTRO DE SIM CARD
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<!--Operacional -> Cadastro -> Chip-->
<div id="operacional-cadastro-chip-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_chip" data-toggle="tab">Sim Card</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_chip">
	            <fieldset class="field_control">
	            	<fieldset class="field_equip">
						<legend>Dados do Sim Card</legend>
						NFe:<input type="text" size="29px" id="nfe_chip" onkeypress="javascript: return EntradaNumerico(event);" maxlength="50">
						<br>
						ICC-ID: <input type="text" size="25px" id="iccid" onkeypress="javascript: return EntradaNumerico(event);" maxlength="20">
						<br>
						Operadora:<select id="operadora_chip" class="size_47">
						<%
							try {
								Vector<OperadoraChipDAO> list = OperadoraChipDAOService.loadAll();
								for (int i=0;i < list.size();i++) {
									OperadoraChipDAO dao = list.elementAt(i);
									String str = String.valueOf(dao.getAttValueFor("NOME_OPERADORA")).trim();%>
					       			   <option value="<%=dao.getPkValueFor("COD_OPERADORA")%>" name="option_operadora_chip"><%=str%></option>
					       		<%}%>
						<%
							   } catch (Exception e) {
								   out.println("Error... " + e.getMessage());
							   }
						%>
						</select>
						<br>
						Tecnologia: <input type="text" class="size_46" id="tecnologia_chip" maxlength="3">
						<br>
						APN:<input type="text" size="28px" id="apn_chip" maxlength="49">
						<br>
						Estado:
						<select id="estado_chip" class="size_52">
						<%try {
							Vector<StatusChipDAO> list = StatusChipDAOService.loadAll();
							for (int i=0;i < list.size();i++) {
								StatusChipDAO dao = list.elementAt(i);
								String str = String.valueOf(dao.getAttValueFor("NOME_STATUS")).trim();%>
								<option value="<%=dao.getPkValueFor("COD_STATUS")%>" name="option_estado_chip"><%=str%></option>
							<%}%>
						<%}catch (Exception e) {
							out.println("Error... " + e.getMessage());
						  }%>
						</select>
						<br>
						DDD: <input type="text" class="size_11" id="ddd_chip" onkeypress="javascript: return EntradaNumerico(event);" maxlength="2">
						Número: <input type="text" class="size_26" id="numero_chip" onkeypress="javascript: return EntradaNumerico(event);" maxlength="10">
					</fieldset>
		            <div class="div_img_chip">
		            </div>
				</fieldset>
            </div>
            <div class="div_modal_bt">
            	<button type="button" id="incluir_modal" onclick="operacional_cadastrar_chip_function()">Incluir</button>
            	<button type="button" id="cancel_modal">Cancelar</button>
           	</div>	
        </div>
    </form>
</div>