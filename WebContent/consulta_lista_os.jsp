<!--
ZIRIX CONTROL CENTER - CONSULTA LISTA OS
DESENVOLVIDO POR RAPHAEL B. MARQUES

UTILIZADA NOS CASOS DE RETORNO DE LISTA PÓS CONSULTA INICIAL

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>
<%
String TIPO_CONSULTA = request.getParameter("TIPO_CONSULTA"); //NOME, DOCUMENTO, NUMERO_OS, ETC...
String KEY = request.getParameter("KEY");
//Carregar o devido bean. Criar um para que dê o retorno com o nome de qual utilizar
//ou cria-se um genérico que na construção dele ele mesmo saiba qual utilizar (complexo).
%>

<!--Operacional -> Consulta -> Lista -->
<div id="operacional-consulta-lista-content">
    <form class="outer_form">
		<fieldset class="fieldinner">
		<legend><b>Resultado da busca</b></legend>
			<%String listTestesOK = "0";
				for(int i=0; i<listTestesOK.length(); i++){%>
					<%=listTestesOK.trim()%>
				<%}%>
		</fieldset>
    </form>
</div>