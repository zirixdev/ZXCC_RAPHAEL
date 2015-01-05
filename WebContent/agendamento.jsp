<!--
ZIRIX CONTROL CENTER - AGENDAMENTO
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<%
	String user = null;
	if(session.getAttribute("user") == null){
		response.setContentType("text/html");
		response.sendRedirect("index.html");
	}else{
		user = (String) session.getAttribute("user");
	}
	String[] PK_OBJ = {request.getParameter("PK_OBJ")};
	String WORK_ID = request.getParameter("WORK_ID");
	String AREA = request.getParameter("AREA");
	NovoPedidoServiceBean beanPedido = new NovoPedidoServiceBean(PK_OBJ);
	String[] pkCodCliente = {beanPedido.getCodCliente()};
	ClienteServiceBean beanCliente = new ClienteServiceBean(pkCodCliente);
	ScheduleBean bean = new ScheduleBean(user);
	bean.setStartTimestamp(WORK_ID);
%>

<!--Operacional -> Agendamento-->
<div id="operacional-agendamento-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_agendamento" data-toggle="tab">Agendamento</a></li>
            <li><a href="#aba_unidades" data-toggle="tab">Unidades</a></li>
            <li><a href="#aba_observacoes" data-toggle="tab">Observações</a></li>
        </ul>   
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_agendamento">
	            <fieldset class="field">
				<legend><b>Agendamento do Pedido Nº. <%=beanPedido.getNumeroPedido()%>:</b></legend>
					<b>Cliente:</b> <%=beanCliente.getNome().trim()%>
					<br><b>Endereço de instalação igual do pedido?</b><select id="end_inst" class="size_15" onchange="muda_endereco_function()">
						<option value="sim" selected="selected">Sim</option>
						<option value="nao">Não</option>
					</select>
					<fieldset class="fieldinner">
						<div id="endereco_instalacao">
							<%Vector<String[]> instalacaoList = beanPedido.getDadosInstalacao();%>
							<b>Endereço:</b>&nbsp;<%=instalacaoList.elementAt(0)[0].trim()%>
							<%if(instalacaoList.elementAt(0)[5].trim().compareTo("") !=0 ){%>
								<br><b>Complemento:</b>&nbsp;<%=instalacaoList.elementAt(0)[5].trim()%>
							<%}%>
							<br><b>Bairro:</b>&nbsp;<%=instalacaoList.elementAt(0)[1].trim()%>&nbsp;&nbsp;&nbsp;<b>Cidade:</b>&nbsp;<%=instalacaoList.elementAt(0)[2].trim()%>&nbsp;&nbsp;&nbsp;<b>UF.:</b>&nbsp;<div id="uf_agend_div" style="display: inline-block;"><%=instalacaoList.elementAt(0)[3].trim()%></div>
							<br><b>País:</b>&nbsp;<%=instalacaoList.elementAt(0)[4].trim()%>&nbsp;&nbsp;&nbsp;<b>CEP.:</b>&nbsp;<%=instalacaoList.elementAt(0)[6].trim()%>
							<br><b>Ponto de Referência:</b>&nbsp;<%=instalacaoList.elementAt(0)[7].trim()%>
							<br><b>Contato Responsável:</b>&nbsp;<%=instalacaoList.elementAt(0)[10].trim()%>&nbsp;-&nbsp;(<%=instalacaoList.elementAt(0)[8].trim()%>)&nbsp;<%=instalacaoList.elementAt(0)[9].trim()%>
						</div>
					</fieldset>
				</fieldset>
            </div>
            <div class="tab-pane" id="aba_unidades">
                <fieldset class="field">
				<legend><b>Unidades para o Agendamento:</b></legend>
				<%Vector<String[]> CodUnidadesVeiculo = beanPedido.getCodUnidadesVeiculo();
				Vector<String[]> CountUnidadesVeiculo = beanPedido.getCountUnidadesVeiculo();
				if(CountUnidadesVeiculo.size() > 0){
					if(CodUnidadesVeiculo.size() > Integer.parseInt(CountUnidadesVeiculo.elementAt(0)[0])){
						if(Integer.parseInt(CountUnidadesVeiculo.elementAt(0)[0]) != 0){
							String codScheded = null;
							for(int i=0; i<CountUnidadesVeiculo.size(); i++){
								if(i==0){
									codScheded = CountUnidadesVeiculo.elementAt(i)[1];
								}else{
									codScheded = codScheded + CountUnidadesVeiculo.elementAt(i)[1];
								}
								if(i+1<CountUnidadesVeiculo.size()){
									codScheded = codScheded + ",";
								}
							}
							Vector<String[]> CodVeiculoToSched = beanPedido.getCodUnidadesVeiculoToSched(codScheded);
							for (int i=0;i < CodVeiculoToSched.size();i++) {
								try{
									Vector<VeiculoDAO> listVeiculo = VeiculoDAOService.loadAllPedido(Integer.parseInt(CodVeiculoToSched.elementAt(i)[0]));
									VeiculoDAO dao = listVeiculo.elementAt(0);
									if(i!=0){%>
										<canvas id="myCanvasUnidades_<%=i%>" width="680" height="1" style="border:0px;"></canvas>
											<script>
												var c = document.getElementById("myCanvasUnidades_<%=i%>");
												var ctx = c.getContext("2d");
												ctx.moveTo(0,0);
												ctx.lineTo(680,0);
												ctx.stroke();
											</script>
									<%}%>
									<%String COD_VEICULO = CodUnidadesVeiculo.elementAt(i)[0].trim();%>
									<b>Veículo</b>&nbsp;<%=beanPedido.getNomeMarca(Integer.parseInt(String.valueOf(dao.getAttValueFor("COD_MARCA")).trim()))%>&nbsp;&nbsp;
									<b>Placa:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("PLACA")).trim()%>
									<br><b>Data do Agendamento:</b> <input type="date" id="data_agendamento_<%=i%>">
		                			<b>Hora:</b> <input type="time" id="hora_agendamento_<%=i%>">
		                			<br><input type="checkbox" value="<%=COD_VEICULO%>" id="reagendar_<%=i%>" name="reagendar_unidade">&nbsp;<b>Não foi possível realizar o agendamento dessa unidade.</b>
								<%}catch (Exception e){
									out.println("Error ao preencher unidades agendamento... " + e.getMessage());
								}
							}
						}else{
							for (int i=0;i < CodUnidadesVeiculo.size();i++){
								try{
									Vector<VeiculoDAO> listVeiculo = VeiculoDAOService.loadAllPedido(Integer.parseInt(CodUnidadesVeiculo.elementAt(i)[0]));
									VeiculoDAO dao = listVeiculo.elementAt(0);
									if(i!=0){%>
										<canvas id="myCanvasUnidades_<%=i%>" width="680" height="1" style="border:0px;"></canvas>
											<script>
												var c = document.getElementById("myCanvasUnidades_<%=i%>");
												var ctx = c.getContext("2d");
												ctx.moveTo(0,0);
												ctx.lineTo(680,0);
												ctx.stroke();
											</script>
									<%}%>
									<%String COD_VEICULO = CodUnidadesVeiculo.elementAt(i)[0].trim();%>
									<b>Veículo</b>&nbsp;<%=beanPedido.getNomeMarca(Integer.parseInt(String.valueOf(dao.getAttValueFor("COD_MARCA")).trim()))%>&nbsp;&nbsp;
									<b>Placa:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("PLACA")).trim()%>
									<br><b>Data do Agendamento:</b> <input type="date" id="data_agendamento_<%=i%>">
		                			<b>Hora:</b> <input type="time" id="hora_agendamento_<%=i%>">
		                			<br><input type="checkbox" value="<%=COD_VEICULO%>" id="reagendar_<%=i%>" name="reagendar_unidade">&nbsp;<b>Não foi possível realizar o agendamento dessa unidade.</b>
								<%}catch (Exception e){
									out.println("Error ao preencher unidades agendamento... " + e.getMessage());
								}%>
							<%}
						}
					}
				}else{
					for (int i=0;i < CodUnidadesVeiculo.size();i++){
						try{
							Vector<VeiculoDAO> listVeiculo = VeiculoDAOService.loadAllPedido(Integer.parseInt(CodUnidadesVeiculo.elementAt(i)[0]));
							VeiculoDAO dao = listVeiculo.elementAt(0);
							if(i!=0){%>
								<canvas id="myCanvasUnidades_<%=i%>" width="680" height="1" style="border:0px;"></canvas>
									<script>
										var c = document.getElementById("myCanvasUnidades_<%=i%>");
										var ctx = c.getContext("2d");
										ctx.moveTo(0,0);
										ctx.lineTo(680,0);
										ctx.stroke();
									</script>
							<%}%>
							<%String COD_VEICULO = CodUnidadesVeiculo.elementAt(i)[0].trim();%>
							<b>Veículo</b>&nbsp;<%=beanPedido.getNomeMarca(Integer.parseInt(String.valueOf(dao.getAttValueFor("COD_MARCA")).trim()))%>&nbsp;&nbsp;
							<b>Placa:</b>&nbsp;<%=String.valueOf(dao.getAttValueFor("PLACA")).trim()%>
							<br><b>Data do Agendamento:</b> <input type="date" id="data_agendamento_<%=i%>">
                			<b>Hora:</b> <input type="time" id="hora_agendamento_<%=i%>">
                			<br><input type="checkbox" value="<%=COD_VEICULO%>" id="reagendar_<%=i%>" name="reagendar_unidade">&nbsp;<b>Não foi possível realizar o agendamento dessa unidade.</b>
						<%}catch (Exception e){
							out.println("Error ao preencher unidades agendamento... " + e.getMessage());
						}%>
					<%}
				}%>
                </fieldset>
            </div>
            <div class="tab-pane" id="aba_observacoes">
                <fieldset class="field">
                    <textarea placeholder="Observações e Pendências" cols="80" rows="8" id="observacoes" maxlength="796"></textarea>
                </fieldset>
            </div>
            <div class="div_modal_bt">
            	<button type="button" id="incluir_modal" onclick="operacional_agendamento_function('<%=WORK_ID%>')">Incluir</button>
            	<button type="button" id="cancel_modal">Cancelar</button>
           	</div>
           	<div class="tab-pane" id="aba_oculta">
           	<%if(CountUnidadesVeiculo.size() > 0){%>
           		<div id="total_unidades"><%=CodUnidadesVeiculo.size()-Integer.parseInt(CountUnidadesVeiculo.elementAt(0)[0])%></div>
           	<%}else{%>
           		<div id="total_unidades"><%=CodUnidadesVeiculo.size()%></div>
           	<%}%>
           		<div id="cod_dado_inst"><%=instalacaoList.elementAt(0)[11].trim()%></div>
           		<div id="cod_pedido"><%=beanPedido.getNumeroPedido()%></div>
		        <div id="form_end" style="visibility: hidden;">
			    	<div id="div_end">
						Endereço: <input type="text" class="size_100" id="endereco_inst" maxlength="99">
						<br>
						Complemento:<input type="text" class="size_23" id="complemento_inst" maxlength="26">
						Bairro:<input type="text" class="size_22" id="bairro_inst" maxlength="50">
						Cidade:<input type="text" class="size_22" id="cidade_inst" maxlength="50">
						<br>UF:&nbsp;
						<select id="uf_list_inst" class="size_8">
							<option value="1" name="option_endereco_uf">AC</option><option value="2" name="option_endereco_uf">AL</option><option value="3" name="option_endereco_uf">AP</option><option value="4" name="option_endereco_uf">AM</option><option value="5" name="option_endereco_uf">BA</option><option value="6" name="option_endereco_uf">CE</option><option value="7" name="option_endereco_uf">DF</option><option value="8" name="option_endereco_uf">ES</option><option value="9" name="option_endereco_uf">GO</option><option value="10" name="option_endereco_uf">MA</option><option value="11" name="option_endereco_uf">MT</option><option value="12" name="option_endereco_uf">MS</option><option value="13" name="option_endereco_uf">MG</option><option value="14" name="option_endereco_uf">PA</option><option value="15" name="option_endereco_uf">PB</option><option value="16" name="option_endereco_uf">PR</option><option value="17" name="option_endereco_uf">PE</option><option value="18" name="option_endereco_uf">PI</option><option value="19" name="option_endereco_uf">RJ</option><option value="20" name="option_endereco_uf">RN</option><option value="21" name="option_endereco_uf">RS</option><option value="22" name="option_endereco_uf">RO</option><option value="23" name="option_endereco_uf">RR</option><option value="24" name="option_endereco_uf">SC</option><option value="25" name="option_endereco_uf">SP</option><option value="26" name="option_endereco_uf">SE</option><option value="27" name="option_endereco_uf">TO</option><option value="28" name="option_endereco_uf">OUTRO</option>
						</select>
						&nbsp;País:
						<select id="pais_list_inst" class="size_16" onchange="">
							<%try{
								Vector<PaisDAO> list = PaisDAOService.loadAll();
								for (int i=0;i < list.size();i++) {
									PaisDAO dao = list.elementAt(i);
									String str = String.valueOf(dao.getAttValueFor("NOME_PAIS")).trim();%>				   	
									<option value="<%=dao.getPkValueFor("COD_PAIS")%>" name="option_endereco_pais_inst"<% 
									if(str.compareTo("Brasil") == 0){%> selected<%}%>><%=str%></option>
								<%}%>
							<%}catch (Exception e){
								out.println("Error... " + e.getMessage());
							}%>
						</select>
						CEP:<input type="text" class="size_11" id="cep_inst" maxlength="8" onkeypress="javascript: return EntradaNumerico(event);">
						Referência:<input type="text" class="size_34" id="referencia_inst" maxlength="249">
						<br>DDD:<input type="text" class="size_5" id="ddd_inst" maxlength="2" onkeypress="javascript: return EntradaNumerico(event);">
						Número:<input type="text" class="size_17" id="numero_inst" maxlength="10" onkeypress="javascript: return EntradaNumerico(event);">
						Contato Responsável:<input type="text" class="size_38" id="contato_inst" maxlength="50">
					</div>
			    </div>
			    <div id="label_end" style="visibility: hidden;">
				    <%Vector<String[]> instalacaoList_ = beanPedido.getDadosInstalacao();
					for (int i=0;i < instalacaoList.size();i++) {%>
						<b>Endereço:</b>&nbsp;<%=instalacaoList_.elementAt(i)[0].trim()%>
						<%if(instalacaoList_.elementAt(i)[5].trim().compareTo("") !=0 ){%>
							<br><b>Complemento:</b>&nbsp;<%=instalacaoList_.elementAt(i)[5].trim()%>
						<%}%>
						<br><b>Bairro:</b>&nbsp;<%=instalacaoList_.elementAt(i)[1].trim()%>&nbsp;&nbsp;&nbsp;<b>Cidade:</b>&nbsp;<%=instalacaoList_.elementAt(i)[2].trim()%>&nbsp;&nbsp;&nbsp;<b>UF.:</b>&nbsp;<div id="uf_agend_div" style="display: inline-block;"><%=instalacaoList.elementAt(i)[3].trim()%></div>
						<br><b>País:</b>&nbsp;<%=instalacaoList_.elementAt(i)[4].trim()%>&nbsp;&nbsp;&nbsp;<b>CEP.:</b>&nbsp;<%=instalacaoList_.elementAt(i)[6].trim()%>
						<br><b>Ponto de Referência:</b>&nbsp;<%=instalacaoList_.elementAt(i)[7].trim()%>
						<br><b>Contato Responsável:</b>&nbsp;<%=instalacaoList_.elementAt(i)[10].trim()%>&nbsp;-&nbsp;(<%=instalacaoList_.elementAt(i)[8].trim()%>)&nbsp;<%=instalacaoList_.elementAt(i)[9].trim()%>
					<%} %>
			    </div>
		    </div>
        </div>
    </form>
</div>