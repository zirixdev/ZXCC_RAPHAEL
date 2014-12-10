<!--
ZIRIX CONTROL CENTER - CONSULTA OS
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>
<%
	String[] pkVal = {request.getParameter("COD_OS")};
	OsServiceBean bean = new OsServiceBean(pkVal);
%>

<!--Operacional -> Consulta -> OS -->
<div id="operacional-consulta-os-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_os" data-toggle="tab">Ordem de Serviço</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_os">
	            <fieldset class="field">
	            	<%Vector<String[]> listNumeroOS = bean.getNumeroOS();
					for(int i=0; i<listNumeroOS.size(); i++){%>
						<h3>Nº da OS.: <%=listNumeroOS.elementAt(i)[0].trim()%></h3>
						<b>Data da OS.: </b><%=listNumeroOS.elementAt(i)[1].trim()%>
					<%}%>
	            	<br><b>Nome Cliente: </b>
	            	<%int tipo_cli = 0;
					Vector<String[]> listDadosCliente = bean.getDadosCliente();
					for(int i=0; i<listDadosCliente.size(); i++){%>
						<%=listDadosCliente.elementAt(i)[1].trim()%>
						<%tipo_cli = Integer.parseInt(listDadosCliente.elementAt(i)[2].trim());
					}%>
					<b><%if(tipo_cli == 0){%>CPF:<%}else if(tipo_cli == 1){%>CNPJ:<%}%> </b>
	            	<%
					Vector<String[]> listDocCliente = bean.getDocumentoCliente(tipo_cli);
					for(int i=0; i<listDocCliente.size(); i++){%>
						<%=listDocCliente.elementAt(i)[0].trim()%>
					<%}
					Vector<String[]> listEndCliente = bean.getEnderecoAgendamento();
					for(int i=0; i<listEndCliente.size(); i++){%>
						<br><b>Endereço: </b><%=listEndCliente.elementAt(i)[0].trim()%><%if(listEndCliente.elementAt(i)[1].trim().length() > 0){%> <b>Complemento: </b><%=listEndCliente.elementAt(i)[1].trim()%><br><%}%> <b>Bairro: </b><%=listEndCliente.elementAt(i)[2].trim()%> <b>Cidade: </b><%=listEndCliente.elementAt(i)[3].trim()%>
						<br><b>UF: </b><%=listEndCliente.elementAt(i)[4].trim()%> <b>País: </b><%=listEndCliente.elementAt(i)[5].trim()%> <b>CEP.: </b><%=listEndCliente.elementAt(i)[6].trim()%>
					<%}%>
	            	<br><b>Contato: </b>
					<%Vector<String[]> listCtoCliente = bean.getContatoCliente();
					for(int i=0; i<listCtoCliente.size(); i++){%>
						<%if((i>0)&&(i%3==0)){%><br><%}%>
						+<%=listCtoCliente.elementAt(i)[0].trim()%>(<%=listCtoCliente.elementAt(i)[1].trim()%>)<%=listCtoCliente.elementAt(i)[2].trim()%><%if(i+1 < listCtoCliente.size()){%> /<%}%>
					<%}%>
	            	<br>
	            	<fieldset class="fieldinner">
                        <legend><b>Dados do Veículo:</b></legend>
		            	<%Vector<String[]> listUnidade = bean.getDadosUnidade();
						for(int i=0; i<listUnidade.size(); i++){%>
							<b>Placa: </b><%=listUnidade.elementAt(i)[0].trim()%>
							<b>Chassi: </b><%=listUnidade.elementAt(i)[1].trim()%>
							<b>Renavan: </b><%=listUnidade.elementAt(i)[2].trim()%><br>
							<b>Marca: </b><%=listUnidade.elementAt(i)[5].trim()%>
							<b>Modelo: </b><%=listUnidade.elementAt(i)[6].trim()%><br>
							<b>Ano Fabricação: </b><%=listUnidade.elementAt(i)[3].trim()%>
							<b>Ano Modelo: </b><%=listUnidade.elementAt(i)[4].trim()%>
							<b>Cor: </b><%=listUnidade.elementAt(i)[7].trim()%><br>
							<b>Tipo de Combustível: </b><%=listUnidade.elementAt(i)[8].trim()%>
							<b>Data da última vistoria: </b><%=listUnidade.elementAt(i)[9].trim()%>
							<b>KM Atual: </b><%=listUnidade.elementAt(i)[10].trim()%><br>
						<%}%>
					</fieldset>
					<fieldset class="fieldinner">
		            	<legend><b>OS de <%=bean.getTipoOS()%>:</b></legend>
		            	<b>Estatus do Servico: </b><%=bean.getFrustrada()%>
		            	<br><b>Testes Realizados:</b>
		            	<br>
		            	<%Vector<String[]> listTestesOK = bean.getTestesOS();
						for(int i=0; i<listTestesOK.size(); i++){%>
							<input type="checkbox" disabled="disabled" id="ignicao_" <%=listTestesOK.elementAt(i)[0].trim()%>> <b>Ignição</b>
							<input type="checkbox" disabled="disabled" id="bloqueio_" <%=listTestesOK.elementAt(i)[1].trim()%>> <b>Bloqueio</b>
							<input type="checkbox" disabled="disabled" id="gps_" <%=listTestesOK.elementAt(i)[2].trim()%>> <b>GPS</b>
							<input type="checkbox" disabled="disabled" id="gprs_" <%=listTestesOK.elementAt(i)[3].trim()%>> <b>GPRS</b>
							<input type="checkbox" disabled="disabled" id="sirene_" <%=listTestesOK.elementAt(i)[4].trim()%>> <b>Sirene</b>
							<input type="checkbox" disabled="disabled" id="panico_" <%=listTestesOK.elementAt(i)[5].trim()%>> <b>Alerta de Pânico</b>
							<input type="checkbox" disabled="disabled" id="rpm_" <%=listTestesOK.elementAt(i)[6].trim()%>> <b>RPM</b>
						<%}%>
		            	<br><b>Observações sobre a instalação: </b>
		            	<%Vector<String[]> listObservacoes = bean.getObsOS();
						for(int i=0; i<listObservacoes.size(); i++){%>
							<%=listObservacoes.elementAt(i)[0].trim()%>
						<%}%>
		            	<br><b>Mecânico / Atendente:</b>
		            	<%Vector<String[]> tecnico = bean.getTecnicoOS();
						for(int i=0; i<tecnico.size(); i++){%>
							<%=tecnico.elementAt(i)[0].trim()%>
						<%}%>
						<br><b>Agendado para: </b><%String[] agendadoPara = bean.getAgendadoPara();%><%=agendadoPara[0]%> às <%=agendadoPara[1]%> 
		            	<br><b>Data e Hora de Chegada: </b><%=bean.getHorarioChegada()%> <b>Saida: </b><%=bean.getHorarioSaida()%>
            		</fieldset>
            	</fieldset>
            </div>
            <div class="div_modal_bt">
            	<button type="button" id="print_modal" onclick="operacional_imprimir_os_function('<%=pkVal[0]%>')">Imprimir</button>
            	<button type="button" id="cancel_modal">Fechar</button>
           	</div>
        </div>
    </form>
</div>