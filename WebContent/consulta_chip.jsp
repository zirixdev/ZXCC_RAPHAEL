<!--
ZIRIX CONTROL CENTER - CONSULTA SIM CARD
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<%
	String[] pkVal = {request.getParameter("COD_CHIP")};
	ChipServiceBean bean = new ChipServiceBean(pkVal);
%>

<!--Operacional -> Consulta -> Chip-->
<div id="operacional-consulta-chip-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_chip" data-toggle="tab">Sim Card</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_chip">
	            <fieldset class="field_control">
	            	<fieldset class="field_equip">
						<legend>Dados do Sim Card</legend>
						NFe:<input type="text" size="29px" id="nfe_chip" onkeypress="javascript: return EntradaNumerico(event);" maxlength="50" value="<%=bean.getNfe().trim()%>">
						<br>
						ICC-ID: <input type="text" size="25px" id="iccid" onkeypress="javascript: return EntradaNumerico(event);" maxlength="20" value="<%=bean.getIccid().trim()%>">
						<br>
						Operadora:<select id="operadora_chip" class="size_47">
						<%try {
							Vector<OperadoraChipDAO> list = OperadoraChipDAOService.loadAll();
							int cod_operadora_bean = bean.getCodOperadora();
							for (int i=0;i < list.size();i++) {
								OperadoraChipDAO dao = list.elementAt(i);
								int cod_operadora_servlet = dao.getPkValueFor("COD_OPERADORA");
								String str = String.valueOf(dao.getAttValueFor("NOME_OPERADORA")).trim();%>
					       			<option value="<%=cod_operadora_servlet%>" name="option_operadora_chip" 
								<%if (cod_operadora_servlet == cod_operadora_bean){%> selected
								<%}%>
								><%=str%></option>
						   <%}%>
						<%}catch (Exception e) {
						   	   out.println("Error... " + e.getMessage());
						  }%>
						</select>
						<br>
						Tecnologia: <input type="text" class="size_46" id="tecnologia_chip" maxlength="3" value="<%=bean.getTecnologia().trim()%>">
						<br>
						APN:<input type="text" size="28px" id="apn_chip" maxlength="49" value="<%=bean.getApn().trim()%>">
						<br>
						Estado:
						<select id="estado_chip" class="size_52">
						<%
							try {
								Vector<StatusChipDAO> list = StatusChipDAOService.loadAll();
								int cod_estado_bean = bean.getCodStatus();
								for (int i=0;i < list.size();i++) {
									StatusChipDAO dao = list.elementAt(i);
									int cod_estado_servlet = dao.getPkValueFor("COD_STATUS");
									String str = String.valueOf(dao.getAttValueFor("NOME_STATUS")).trim();%>
					       			   <option value="<%=cod_estado_servlet%>" name="option_estado_chip"
					       			<%if (cod_estado_servlet == cod_estado_bean){%> selected
									<%}%>
					       			><%=str%></option>
					       		<%}%>
						<%
							   } catch (Exception e) {
								   out.println("Error... " + e.getMessage());
							   }
						%>
						</select>
						<br>
						DDD: <input type="text" class="size_11" id="ddd_chip" onkeypress="javascript: return EntradaNumerico(event);" maxlength="2" value="<%=bean.getDdd().trim()%>">
						Número: <input type="text" class="size_26" id="numero_chip" onkeypress="javascript: return EntradaNumerico(event);" maxlength="10" value="<%=bean.getNumeroChip().trim()%>">
						<%
							int cod_modulo_bean = bean.getCodModulo();
							if(cod_modulo_bean > 0){%>
								<br>
								Número Módulo: <%=bean.getNumeroModulo().elementAt(0)[0].trim()%><br>
								Modelo: <%=bean.getModeloModulo().elementAt(0)[0].trim()%><br>
								Cliente: <%=bean.getNomeCliente().elementAt(0)[0].toUpperCase().trim()%><br>
							<%}%>
					</fieldset>
		            <div class="div_img_chip">
		            </div>
				</fieldset>
            </div>
            <div class="div_modal_bt">
            	<button type="button" id="salvar_modal" onclick="operacional_consultar_chip_salvar_function()">Salvar</button>
            	<button type="button" id="cancel_modal">Cancelar</button>
           	</div>	
        </div>
    </form>
</div>