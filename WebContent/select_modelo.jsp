<!--
ZIRIX CONTROL CENTER - CONSULTA SIM CARD
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<%
	Integer COD_MARCA_ = Integer.parseInt(request.getParameter("COD_MARCA"));
%>
Modelo:<select id="modelo_equip" class="size_84">
	<%try {
		Vector<DescModuloDAO> list = DescModuloDAOService.loadModelo(COD_MARCA_);
		for (int i=0;i < list.size();i++) {
			DescModuloDAO dao = list.elementAt(i);
			String str = String.valueOf(dao.getAttValueFor("NOME_MODELO")).trim();%>
		<option value="<%=dao.getPkValueFor("COD_MODELO")%>" name="option_modelo_modulo"><%=str%></option>
	<%}%>
	<%}catch (Exception e) {
		out.println("Error... " + e.getMessage());
	}%>
</select>