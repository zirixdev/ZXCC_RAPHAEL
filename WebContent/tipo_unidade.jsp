<!--
ZIRIX CONTROL CENTER - CONTROLE SOBRE TIPOS DE UNIDADES
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5 E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<div id="unid_veiculo">
    <fieldset class="fieldinner">
        <legend>Veículo:</legend>
        Placa: <input type="text" id="placa" class="size_15" maxlength="8">
        Chassi: <input type="text" id="chassi" class="size_20" maxlength="23">
        Renavan: <input type="text" id="renavan" class="size_19" maxlength="23" onkeypress="javascript: return EntradaNumerico(event);">
        <br>
        <%int ano = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()))+1;%>
        Ano de Fabricação:<input type="number" id="ano_fab" class="size_11" min="1930" max="<%=ano%>" onkeypress="javascript: return EntradaNumerico(event);">
        Ano do Modelo:<input type="number" id="ano_mod" class="size_11" min="1930" max="<%=ano%>" onkeypress="javascript: return EntradaNumerico(event);">
        Marca:<select id="marca_list" class="size_31">
		<%try {
			Vector<VeiculoMarcaDAO> list = VeiculoMarcaDAOService.loadAll();
			for (int i=0;i < list.size();i++) {
				VeiculoMarcaDAO dao = list.elementAt(i);
				int codMarca = dao.getPkValueFor("COD_MARCA");
				String str = String.valueOf(dao.getAttValueFor("NOME_MARCA")).trim();%>
				<option value="<%=codMarca%>" name="option_marca_veiculo"><%=str%></option>
			<%}%>
		<%} catch (Exception e) {
			out.println("Error... " + e.getMessage());
		}%>
        </select>
        <br>
        Modelo: <input type="text" id="modelo" class="size_32">
        Cor: <input type="text" id="cor" class="size_15">
        Combustível:<select id="combustivel_list" class="size_21">
        <%try {
			Vector<VeiculoCombustivelDAO> list = VeiculoCombustivelDAOService.loadAll();
			for (int i=0;i < list.size();i++) {
				VeiculoCombustivelDAO dao = list.elementAt(i);
				int codCombustivel = dao.getPkValueFor("COD_COMBUSTIVEL");
				String str = String.valueOf(dao.getAttValueFor("NOME_COMBUSTIVEL")).trim();%>
				<option value="<%=codCombustivel%>" name="option_veiculo_combustivel"><%=str%></option>
			<%}%>
		<%} catch (Exception e) {
			out.println("Error... " + e.getMessage());
		}%>
        </select>
        <br>
        Voltagem: <input type="text" id="voltagem" class="size_8" maxlength="2" onkeypress="javascript: return EntradaNumerico(event);">
        KM: <input type="text" id="km" class="size_15" onkeypress="javascript: return EntradaNumerico(event);">
        Data da Última Vistoria: <input type="date" id="data_vist" onkeypress="javascript: return EntradaNumerico(event);">
    </fieldset>
</div>