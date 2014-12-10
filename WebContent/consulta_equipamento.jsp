<!--
ZIRIX CONTROL CENTER - CADASTRO DE EQUIPAMENTOS
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %> 

<%
	String[] pkVal = {request.getParameter("COD_MODULO")};
	EquipamentoServiceBean bean = new EquipamentoServiceBean(pkVal);
	int COD_MARCA_ = 0;
	int TIPO_UNIDADE_ = 0;
	String iccid_modulo_bean = "";
%>

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
	                    ID: <input type="text" class="size_91" id="numero_modulo" onkeypress="javascript: return EntradaNumerico(event);" value="<%=bean.getID().trim()%>">
	                    <br><b>SN:</b> <%=bean.getSN().trim()%>
	                    <br>NFe:<input type="text" class="size_89" id="numero_nfe" onkeypress="javascript: return EntradaNumerico(event);" value="<%=bean.getNfe().trim()%>">
	                    <br>Marca:<select id="marca_equip" class="size_86" onchange="select_modelo_function()">
	                    	<option value="0" name="option_marca_modulo">---SELECIONE UM---</option>
	                    	<%try {
								Vector<MarcaModuloDAO> marcaList = MarcaModuloDAOService.loadAll();
								Vector<String[]> modeloList = bean.getModeloModulo();
								int codMarcaBean = Integer.parseInt(modeloList.elementAt(0)[2].trim());
								COD_MARCA_ = codMarcaBean;
								for (int i=0;i < marcaList.size();i++) {
									MarcaModuloDAO dao = marcaList.elementAt(i);
									String str = String.valueOf(dao.getAttValueFor("NOME_MARCA")).trim();
									int codMarca = dao.getPkValueFor("COD_MARCA");%>
						    		<option value="<%=codMarca%>" name="option_marca_modulo"<%if(codMarca == codMarcaBean){%> selected<%}%>><%=str%></option>
						    	<%}%>
							<%}catch (Exception e) {
									out.println("Error... " + e.getMessage());
							  }%>
	                    </select>
	                    <div id="div_modelo_equip">
							Modelo:<select id="modelo_equip" class="size_84">
								<%try{
									Vector<DescModuloDAO> list = DescModuloDAOService.loadModelo(COD_MARCA_);
									Vector<String[]> modeloList = bean.getModeloModulo();
									int codModeloBean = Integer.parseInt(modeloList.elementAt(0)[0].trim());
									for (int i=0;i < list.size();i++) {
										DescModuloDAO dao = list.elementAt(i);
										String str = String.valueOf(dao.getAttValueFor("NOME_MODELO")).trim();
										int codModelo = dao.getPkValueFor("COD_MODELO");%>
										<option value="<%=codModelo%>" name="option_modelo_modulo"<%if(codModelo == codModeloBean){%> selected<%}%>><%=str%></option>
								  <%}%>
								<%}catch (Exception e) {
									out.println("Error... " + e.getMessage());
								}%>
							</select>
	                    </div>
	                    <%try{
							Vector<String[]> iccidList = bean.getIccID();
							iccid_modulo_bean = iccidList.elementAt(0)[0].trim();
						}catch (Exception e){
							out.println("Error... " + e.getMessage());
						}%>
	                    ICC-ID:<input list="iccid_list" name="iccid_consulta" id="item_iccid" class="size_83" onkeypress="javascript: return EntradaNumerico(event);" maxlength="20" value="<%=iccid_modulo_bean%>">
				        <datalist id="iccid_list">   	
						<%try {
							Vector<ChipDAO> list = ChipDAOService.loadSemModuloAndInstalado(pkVal[0]);
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
	            <button type="button" id="salvar_modal" onclick="operacional_consultar_equipamento_salvar_function()">Incluir</button>
	            <button type="button" id="cancel_modal">Cancelar</button>
            </div>
        </div>
    </form>
</div>