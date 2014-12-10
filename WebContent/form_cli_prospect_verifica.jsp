<!--
ZIRIX CONTROL CENTER - CADASTRO CLIENTE PROSPECÇÃO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->

<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>
<%
	String user = null;
	if(session.getAttribute("user") == null){
		response.setContentType("text/html");
		response.sendRedirect("index.html");
	}else{
		user = (String) session.getAttribute("user");
	}
	String WORK_ID = request.getParameter("WORK_ID");
	if(WORK_ID.compareTo("0") != 0){
		ScheduleBean bean = new ScheduleBean(user);
		bean.setStartTimestamp(WORK_ID);
	}
%>
<div id="adm-verifica-prospeccao-content">
    <form class="outer_form">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#aba_cliente" data-toggle="tab">Verificar Cadastro</a></li>
        </ul>
        <div class="tab-content"> 
            <div class="tab-pane active" id="aba_cliente">
                <fieldset class="field">
                    Confirma os dados inseridos?
	               	<div class="checkboxConfirm">
				  		<input type="checkbox" value="1" id="checkboxConfirmCliProsp" name="teste_nome">
					  	<label for="checkboxConfirmCliProsp"></label>
					</div>
                </fieldset>
            </div>
            <div class="div_modal_bt">
				<button type="button" id="incluir_modal" onclick="SCHED_WORK_END_FUNCTION('<%=WORK_ID%>')">Salvar</button>
				<button type="button" id="cancel_modal">Cancelar</button>
            </div>
        </div>
    </form>
</div>