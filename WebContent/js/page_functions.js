/*
ZIRIX CONTROL CENTER - FUNÇÕES DE CONTROLE DE MENU
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLUÇÕES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVASCRIPT E AJAX
*/

xmlDoc=loadXMLDoc("js/VariaveisZXCC.xml");
var url_adress = xmlDoc.getElementsByTagName("adress")[0].textContent;

Date.prototype.yyyymmdd = function() {
	var yyyy = this.getFullYear().toString();
	var mm = (this.getMonth()+1).toString();
	var dd  = this.getDate().toString();

	return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
};  

function reloadIFrame(codUsuario) {
	parent.frames['tarefasIFrame'].location.href = "tarefas.jsp?COD_USUARIO=" + codUsuario;
}

function unit_function(){
    var values = $("#tipo_unidade_list").val();
    var fild_content = '';
    switch(values) {
        case "2":
        	fild_content = "div#unid_veiculo";
            break;
        default:
        $('#tipo_unidade').html('');
    }
    $.ajax({
        url: url_adress + "tipo_unidade.jsp",
        success: function(result) {
            var html = jQuery('<div>').html(result);
            var content = html.find(fild_content).html();
            $('#tipo_unidade').html(content);
        },
        error: function(e){
            alert('error');
        }
    });
}

function libera_div(input_name){
	var content_div;
	if(input_name == "Teste"){
		content_div = '- <input type="text" class="size_25" id="teste_dias" onkeypress="javascript: return EntradaNumerico(event);"> dias.';
		$('#div_outro_servico').html("");
		$('#div_teste_dias').html(content_div);
	}else if(input_name == "Outro"){
		content_div = '- <input type="text" class="size_50" id="outro_servico">';
		$('#div_teste_dias').html("");
		$('#div_outro_servico').html(content_div);
	}else{
		$('#div_teste_dias').html("");
		$('#div_outro_servico').html("");
	}
}

function change_consulta_operacional_function(){
	var values = $("input[type='radio'][name='busca_op']:checked").val();
    var fild_content = '';
    switch(values) {
        case "cliente":
        	fild_content = "div#operacional-consulta-cliente-content";
            break;
        case "equipamento":
        	fild_content = "div#operacional-consulta-equipamento-content";
            break;
        case "chip":
        	fild_content = "div#operacional-consulta-chip-content";
            break;
        case "os":
        	fild_content = "div#operacional-consulta-os-content";
            break;
        default:
        	$('.consulta_operacional').html('');
        	break;
    }
    $.ajax({
        url: url_adress + "consulta.jsp",
        success: function(result) {
            var html = jQuery('<div>').html(result);
            var content = html.find(fild_content).html();
            $('.consulta_operacional').html(content);
        },
        error: function(e){
            alert('error');
        }
    });
}

var cod_cliente_consulta;
var cod_modulo_consulta;
var cod_chip_consulta;
var cod_vendedor_consulta;
var cod_cliente_prospect_consulta;
var cod_os_consulta;
var cod_placa_consulta;

function operacional_consulta_function(type){
	this.cod = type || 0;
    var values = $("input[type='radio'][name='busca_op']:checked").val();
    var adress;
    switch(values) {
        case "cliente":
        	var cod_aux = 0;
        	if(cod == "NOME"){
        		cod_aux = 1;
        	}else if(cod == "DOC"){
        		cod_aux = 2;
        	}
        	switch(cod_aux){
        	case 1:
                var val_datalist_nome = $('#item_nome_razao').val();
        		if (val_datalist_nome !== ""){
	            	cod_cliente_consulta = $('#nome_list option').filter(function() {
	                    return this.value == val_datalist_nome;
	                }).data('label');
	                adress= url_adress + "consulta_cliente.jsp?COD_CLIENTE=";
	                adress= adress + cod_cliente_consulta;
	                $.ajax({
	                    url: adress,
	                    success: function(result) {
	                        $('.modal-content').html(result);
	                        $('.modal').modal({backdrop:'static'});
	                        carregar_dados_consulta_cliente_function();
	                    },
	                    error: function(){
	                        alert('Erro ao buscar dados do CLIENTE selecionado!');
	                    }
	                });
            	}else{
                    alert('Não é possível realizar a busca sem o preenchimento de um dos campos!');
                    document.getElementById("item_nome_razao").focus();
                    return 0;
                }
        		break;
        	case 2:
                var val_doc_selected = $('#item_tipo_doc :selected').val();
                var val_num_doc = $('#num_doc_cons').val();
                if ((val_num_doc !== "") && (val_doc_selected !== "")){
                	
                }
        		break;
        	}
            break;
        case "equipamento":
        	var val_datalist_id = $('#item_id_modulo').val();
            if (val_datalist_id !== ""){
            	cod_modulo_consulta = $('#id_list option').filter(function() {
                    return this.value == val_datalist_id;
                }).data('label');
                adress= url_adress + "consulta_equipamento.jsp?COD_MODULO=";
                adress= adress + cod_modulo_consulta;
                $.ajax({
                    url: adress,
                    success: function(result) {
                        $('.modal-content').html(result);
                        $('.modal').modal({backdrop:'static'});
                    },
                    error: function(){
                        alert('Erro ao buscar dados do MODULO selecionado!');
                    }
                });
            }
        	else{
        		alert('Não é possível realizar a busca sem o preenchimento do campo ID!');
                document.getElementById("iccid_list").focus();
                return 0;
        	}
        	break;
        case "chip":
        	var val_iccid_selected = $('#item_iccid').val();
        	if(val_iccid_selected !== ""){
        		cod_chip_consulta = $('#iccid_list option').filter(function() {
        			return this.value == val_iccid_selected;
	            }).data('label');
	            adress= url_adress + "consulta_chip.jsp?COD_CHIP=";
	            adress= adress + cod_chip_consulta;
	            $.ajax({
	                url: adress,
	                success: function(result) {
	                    $('.modal-content').html(result);
	                    $('.modal').modal({backdrop:'static'});
	                },
	                error: function(){
	                    alert('Erro ao buscar dados do SIM CARD selecionado!');
	                }
	            });
        	}else{
        		alert('Não é possível realizar a busca sem o preenchimento do campo ICC-ID!');
                document.getElementById("iccid_list").focus();
                return 0;
        	}
        	break;
        case "os":
        	var cod_aux = 0;
        	if(cod == "NUMOS"){
        		cod_aux = 1;
        	}else if(cod == "VEIC"){
        		cod_aux = 2;
        	}else if(cod == "CLIENTE"){
        		cod_aux = 3;
        	}else if(cod == "DATA"){//Join NUMERO_OS
        		cod_aux = 4;
        	}else if(cod == "REF"){//Join NUMERO_OS
        		cod_aux = 5;
        	}else if(cod == "TIPO"){
        		cod_aux = 6;
        	}
        	switch(cod_aux){
        	case 1:
        		var val_os_selected = $('#item_os').val();
	        	if(val_os_selected !== ""){
	        		cod_os_consulta = $('#os_list option').filter(function() {
	        			return this.value == val_os_selected;
		            }).data('label');
		            adress= url_adress + "consulta_os.jsp?COD_OS=";
		            adress= adress + cod_os_consulta;
		            $.ajax({
		                url: adress,
		                success: function(result) {
		                    $('.modal-content').html(result);
		                    $('.modal').modal({backdrop:'static'});
		                },
		                error: function(){
		                    alert('Erro ao buscar dados da Ordem de Serviço selecionado!');
		                }
		            });
	        	}else{
	        		alert('Não é possível realizar a busca sem o preenchimento do Número da OS!');
	                document.getElementById("os_list").focus();
	                return 0;
	        	}
        		break;
        	case 2:
        		var val_placa_selected = $('#item_veic').val();
	        	if(val_placa_selected !== ""){
	        		placa_consulta = $('#veic_list option').filter(function() {
	        			return this.value == val_placa_selected;
		            }).data('label');
		            adress= url_adress + "consulta_lista_os.jsp?TIPO_CONSULTA=VEICULO";
		            adress= adress + "&KEY=" + placa_consulta;
		            $.ajax({
		                url: adress,
		                success: function(result) {
		                    $('.modal-content').html(result);
		                    $('.modal').modal({backdrop:'static'});
		                },
		                error: function(){
		                    alert('Erro ao buscar dados da Ordem de Serviço selecionado!');
		                }
		            });
	        	}else{
	        		alert('Não é possível realizar a busca sem o preenchimento da Placa do Veículo!');
	                document.getElementById("veic_list").focus();
	                return 0;
	        	}
        		break;
        	case 3:
        		var val_cliente_selected = $('#item_nome_razao').val();
	        	if(val_cliente_selected !== ""){
	        		cod_cliente_consulta = $('#nome_list option').filter(function() {
	        			return this.value == val_cliente_selected;
		            }).data('label');
		            adress= url_adress + "consulta_lista_os.jsp?TIPO_CONSULTA=CLIENTE";
		            adress= adress + "&KEY=" + cod_cliente_consulta;
		            $.ajax({
		                url: adress,
		                success: function(result) {
		                    $('.modal-content').html(result);
		                    $('.modal').modal({backdrop:'static'});
		                },
		                error: function(){
		                    alert('Erro ao buscar dados da Ordem de Serviço selecionado!');
		                }
		            });
	        	}else{
	        		alert('Não é possível realizar a busca sem o preenchimento da Nome do Cliente!');
	                document.getElementById("nome_list").focus();
	                return 0;
	        	}
        		break;
        	case 4:
        		var val_data_os_selected = $('#data_busca_os').val();
        		if(val_data_os_selected !== ""){
		            adress= url_adress + "consulta_lista_os.jsp?TIPO_CONSULTA=DATA";
		            adress= adress + "&KEY=" + val_data_os_selected;
		            $.ajax({
		                url: adress,
		                success: function(result) {
		                    $('.modal-content').html(result);
		                    $('.modal').modal({backdrop:'static'});
		                },
		                error: function(){
		                    alert('Erro ao buscar dados da Ordem de Serviço selecionado!');
		                }
		            });
	        	}else{
	        		alert('Não é possível realizar a busca sem o preenchimento da Data!');
	                document.getElementById("data_busca_os").focus();
	                return 0;
	        	}
        		break;
        	case 5:
        		var val_ano_os_selected = $('#ano_os').val();
        		var val_mes_os_selected = $('#mes_os').val();
        		if((val_ano_os_selected !== "") && (val_mes_os_selected !== "")){
		            adress= url_adress + "consulta_lista_os.jsp?TIPO_CONSULTA=REFERENCIA";
		            adress= adress + "&KEY=" + val_ano_os_selected + "//" + val_mes_os_selected;
		            $.ajax({
		                url: adress,
		                success: function(result) {
		                    $('.modal-content').html(result);
		                    $('.modal').modal({backdrop:'static'});
		                },
		                error: function(){
		                    alert('Erro ao buscar dados da Ordem de Serviço selecionado!');
		                }
		            });
	        	}else{
	        		alert('Não é possível realizar a busca sem o preenchimento da Referência!');
	                document.getElementById("ano_os").focus();
	                return 0;
	        	}
        		break;
        	case 6:
        		var tipo_os_selected = document.getElementById("tipoos_list");
        		var val_tipo_os_selected = tipo_os_selected.options[tipo_os_selected.selectedIndex].value;
	            adress= url_adress + "consulta_lista_os.jsp?TIPO_CONSULTA=TIPO";
	            adress= adress + "&KEY=" + val_tipo_os_selected;
	            $.ajax({
	                url: adress,
	                success: function(result) {
	                    $('.modal-content').html(result);
	                    $('.modal').modal({backdrop:'static'});
	                },
	                error: function(){
	                    alert('Erro ao buscar dados da Ordem de Serviço selecionado!');
	                }
	            });
	    		break;
        	}
        	break;
        default:
            $('.consulta_operacional').html('');
    }
}

function comercial_consulta_function(e){
    var values = $("input[type='radio'][name='busca_com']:checked").val();
    var adress;
    switch(values) {
        case "cliente":
            var val_datalist_nome = $('#item_nome_razao').val();
            if (val_datalist_nome !== ""){
            	cod_cliente_prospect_consulta = $('#nome_list option').filter(function() {
                    return this.value == val_datalist_nome;
                }).data('label');
                adress= url_adress + "consulta_cli_prospect.jsp?COD_CLIENTE_PROSPECCAO=";
                adress= adress + cod_cliente_prospect_consulta;
                $.ajax({
                    url: adress,
                    success: function(result) {
                        $('.modal-content').html(result);
                        $('.modal').modal({backdrop:'static'});
                        carregar_dados_consulta_cliente_prospect_function();
                    },
                    error: function(){
                        alert('Erro ao buscar dados do CLIENTE selecionado!');
                    }
                });
            }
            else{
                alert('Não é possível realizar a busca sem o preenchimento do campo Nome / Razão Social!');
                document.getElementById("item_nome_razao").focus();
                return 0;
            }
            break;
        case "pedido":
        	break;
        default:
            $('.consulta_comercial').html('');
    }
}

function change_consulta_comercial_function(){
	var values = $("input[type='radio'][name='busca_com']:checked").val();
    var fild_content = '';
    switch(values) {
        case "cliente":
        	fild_content = "div#comercial-consulta-cliente-content";
            break;
        case "pedido":
        	fild_content = "div#comercial-consulta-pedido-content";
            break;
        default:
        	$('.consulta_comercial').html('');
        	break;
    }
    $.ajax({
        url: url_adress + "consulta.jsp",
        success: function(result) {
            var html = jQuery('<div>').html(result);
            var content = html.find(fild_content).html();
            $('.consulta_comercial').html(content);
        },
        error: function(e){
            alert('error');
        }
    });
}

var h = window.innerHeight;
$('.page-background').height(h - 235);
$('.conteudo').height(h - 301);

var clean_doc;
var control_div_doc = new Array();
function div_doc_inseridas (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

var clean_end;
var control_div_end = new Array();
function div_end_inseridas (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

var clean_contato;
var control_div_contato = new Array();
function div_contato_inseridas (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

var clean_email;
var control_div_email = new Array();
function div_email_inseridas (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

function vetor_doc_inserido(numero, tipo_doc, dt_emiss, org_emiss){
    this.numero = numero;
    this.tipo_doc = tipo_doc;
    this.dt_emiss = dt_emiss;
    this.org_emiss = org_emiss;
}

var control_vetor_doc = new Array();
control_vetor_doc[0] = 0;
var control_vetor_doc_tipo = new Array();

function insert_documentos_function() {
    clean_doc = $('#div_doc').html();
    var doc_list = $('#tipodoc_list').val();
    var doc_name = $('#tipodoc_list :selected').text();
    var num_doc = document.getElementById("numero_documento").value;
    var dt_emis = document.getElementById("data_emissao").value;
    if(dt_emis === null){
        dt_emis = "";
    }
    var org_ems = document.getElementById("orgao_emissor").value;
    if(org_ems === null){
        org_ems = "";
    }
    var insert = "X";

    var tipo_doc_tipo_obj = $('#tipodoc_list');
    var tipo_doc_tamanho_tipo = tipo_doc_tipo_obj[0].length;
    for (var i=0; i<tipo_doc_tamanho_tipo;i++){
        control_vetor_doc_tipo[i] = document.getElementsByName("option_documento_tipo")[i].text;
    }

    if (num_doc === ""){
        alert('Favor inserir número do documento.');
        document.getElementById("numero_documento").focus();
        return 0;
    }

    if (doc_name === ""){
        alert('Necessário escolher o tipo do documento.');
        document.getElementById("doc_list").focus();
        return 0;
    }
    switch(doc_list){
        case "1"://RG
            if (dt_emis === "" || org_ems === ""){
                alert('Esse tipo de documento necessita que seja preenchido data de emissão e órgão emissor.');
                if (dt_emis === ""){
                    document.getElementById("data_emissao").focus();
                }
                else {
                    document.getElementById("orgao_emissor").focus();
                }
                return 0;
            }
            break;
        case "2"://CPF
            if(!validarCPF(num_doc)){
                alert('CPF inválido.\n\
Favor digitar o número do documento corretamente.');
                document.getElementById("numero_documento").focus();
                return 0;
            }
            break;
        case "3"://CNPJ
            if(!validarCNPJ(num_doc)){
                alert('CNPJ inválido.\n\
Favor digitar o número do documento corretamente.');
                document.getElementById("numero_documento").focus();
                return 0;
            }
            break;
    }

    if(dt_emis === "")
    	dt_emis = "5000-12-31";
    if(org_ems === "")
    	org_ems = "VAZIO";

    i = control_div_doc.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_doc[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
            control_div_doc[control_div_doc.length] = new div_doc_inseridas(i,0);
            control_vetor_doc[i] = new vetor_doc_inserido(num_doc, doc_list, dt_emis, org_ems);
        }
    }
    else{
        control_div_doc[0] = new div_doc_inseridas(0,0);
        control_vetor_doc[0] = new vetor_doc_inserido(num_doc, doc_list, dt_emis, org_ems);
    }

    if (insert === "X"){
        var content_div_doc = $('#docs_inserido').html();
        content_div_doc = content_div_doc + '<div id="doc_' + control_div_doc[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="doc_inserido" value="doc_' + control_div_doc[i].cod + '" onclick="change_doc_button_function()">\n\
                                                  <div id="num_doc_' + control_div_doc[i].cod + '">' + num_doc + '</div>&nbsp;&#45;\n\
                                                  <div id="tipo_doc_' + control_div_doc[i].cod + '">' + doc_name + '</div>';
        if (dt_emis !== "5000-12-31"){
            content_div_doc = content_div_doc + '&nbsp;&#45;<div id="dt_emiss_doc_' + control_div_doc[i].cod + '">' + dt_emis + '</div>';
        }else{
            content_div_doc = content_div_doc + '<div id="dt_emiss_doc_' + control_div_doc[i].cod + '"></div>';
        }
        if (org_ems !== "VAZIO"){
            content_div_doc = content_div_doc + '&nbsp;&#45;<div id="org_emiss_doc_' + control_div_doc[i].cod + '">' + org_ems + '</div>';
        }
        else{
            content_div_doc = content_div_doc + '<div id="org_emiss_doc_' + control_div_doc[i].cod + '"></div>';
        }

        content_div_doc = content_div_doc + '<br> </div>';

        $('#docs_inserido').html(content_div_doc);
    }
    else {
        var content_div_doc = '<input type="radio" name="doc_inserido" value="doc_' + control_div_doc[j].cod + '" onclick="change_doc_button_function()">\n\
                                                  <div id="num_doc_' + control_div_doc[j].cod + '">' + num_doc + '</div>&nbsp;&#45;\n\
                                                  <div id="tipo_doc_' + control_div_doc[j].cod + '">' + doc_name + '</div>';
        if (dt_emis !== "5000-12-31"){
            content_div_doc = content_div_doc + '&nbsp;&#45;<div id="dt_emiss_doc_' + control_div_doc[j].cod + '">' + dt_emis + '</div>';
        }
        else{
            content_div_doc = content_div_doc + '<div id="dt_emiss_doc_' + control_div_doc[j].cod + '"></div>';
        }
        if (org_ems !== "VAZIO"){
            content_div_doc = content_div_doc + '&nbsp;&#45;<div id="org_emiss_doc_' + control_div_doc[j].cod + '">' + org_ems + '</div> <br>';
        }
        else{
            content_div_doc = content_div_doc + '<div id="org_emiss_doc_' + control_div_doc[j].cod + '"></div> <br>';
        }

        $('#doc_' + control_div_doc[j].cod).html(content_div_doc);
        control_div_doc[j].excluida = 0;
        control_vetor_doc[j] = new vetor_doc_inserido(num_doc, doc_list, dt_emis, org_ems);
    }
    limpa_campos_doc_function();
    var content_div_bt = '<button type="button" id="incluir_documento" onclick="insert_documentos_function()">Incluir</button>';
    $('#div_doc_bt').html(content_div_bt);
    document.getElementById("numero_documento").focus();
}

function delete_documentos_function() {
    var div_select = $("input[type='radio'][name='doc_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(4),Number(length));
    var num_doc = $('#num_doc_' + div_deletar).html();
    var doc_name = $('#tipo_doc_' + div_deletar).html();
    var content_div_doc = $('#' + div_select).html();

    if (confirm('O ' + doc_name.trim() + ' de Número: ' + num_doc.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_doc[div_deletar].excluida = 1;
        control_vetor_doc[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_doc);
    }

    var content_div_bt = '<button type="button" id="incluir_documento" onclick="insert_documentos_function()">Incluir</button>';
    $('#div_doc_bt').html(content_div_bt);
    limpa_campos_doc_function();
}

function change_doc_button_function(){
    var content_div_bt = '<button type="button" id="incluir_documento" onclick="insert_documentos_function()">Incluir</button>\n\
                          <button type="button" id="editar_documento" onclick="editar_documentos_function()">Editar</button>\n\
                          <button type="button" id="delete_documento" onclick="delete_documentos_function()">Excluir</button>';
    $('#div_doc_bt').html(content_div_bt);
}

var div_doc_editar;

function editar_documentos_function() {
    var content_div_bt = '<button type="button" id="salvar_documento" onclick="salvar_documentos_function()">Salvar</button>\n\
                          <button type="button" id="cancelar_documento" onclick="cancelar_documentos_function()">Cancelar</button>';
    $('#div_doc_bt').html(content_div_bt);

    var div_select = $("input[type='radio'][name='doc_inserido']:checked").val();
    var content_div_doc = $('#' + div_select).html();
    var length = div_select.length;
    div_doc_editar = div_select.slice(Number(4),Number(length));
    var num_doc = control_vetor_doc[div_doc_editar].numero;
    var tipo_doc = control_vetor_doc[div_doc_editar].tipo_doc;
    var dt_emiss_doc = control_vetor_doc[div_doc_editar].dt_emiss;
    var org_emiss_doc = control_vetor_doc[div_doc_editar].org_emiss;

    $('#' + div_select).html("");
    $('#' + div_select).html(content_div_doc);

    content_div_doc = "";

    content_div_doc = 'Número:\n\
                        <input type="text" class="size_25" id="numero_documento" value="' + num_doc + '" onkeypress="javascript: return EntradaNumerico(event);">Tipo do Documento:\n\
                        <select id="tipodoc_list" >';
    for(var i=0;i<control_vetor_doc_tipo.length;i++){
        content_div_doc = content_div_doc + '<option value="' + Number(i+1) + '"  name="option_documento_tipo"';
        if (Number(tipo_doc) === Number(i+1)){
            content_div_doc = content_div_doc + ' selected';
        }
        content_div_doc = content_div_doc + '>' + control_vetor_doc_tipo[i] + '</option>';
    }
    content_div_doc = content_div_doc + '</select>';

    if(dt_emiss_doc === "5000-12-31"){
        content_div_doc = content_div_doc + '<br>Data de Emissão:<input type="date" id="data_emissao">Órgão Emissor:';
    }
    else {
        content_div_doc = content_div_doc + '<br>Data de Emissão:<input type="date" id="data_emissao" value="' + dt_emiss_doc + '">Órgão Emissor:';
    }
    if (org_emiss_doc === "VAZIO"){
        content_div_doc = content_div_doc + '<input class="size_20" type="text" id="orgao_emissor">';
    }
    else {
        content_div_doc = content_div_doc + '<input class="size_20" type="text" id="orgao_emissor" value="'+ org_emiss_doc + '">';
    }

    $('#div_doc').html("");
    $('#div_doc').html(content_div_doc);
}

function limpa_campos_doc_function(){
    var content_div_doc = clean_doc;
    $('#div_doc').html("");
    $('#div_doc').html(content_div_doc);
}

function cancelar_documentos_function(){
    var content_div_bt = '<button type="button" id="incluir_documento" onclick="insert_documentos_function()">Incluir</button>';
    $('#div_doc_bt').html(content_div_bt);

    limpa_campos_doc_function();
}

function salvar_documentos_function(){
    var content_div_bt = '<button type="button" id="incluir_documento" onclick="insert_documentos_function()">Incluir</button>';
    $('#div_doc_bt').html(content_div_bt);

    var div_salvar = div_doc_editar;

    $('#doc_' + div_salvar).html("");    

    var num_doc = document.getElementById("numero_documento").value;
    var doc_list = $('#tipodoc_list').val();
    var doc_name = $('#tipodoc_list :selected').text();
    var dt_emis = document.getElementById("data_emissao").value;
    var org_ems = document.getElementById("orgao_emissor").value;

    if (num_doc === ""){
        alert('Favor inserir número do documento.');
        document.getElementById("numero_documento").focus();
        return 0;
    }

    if (doc_name === ""){
        alert('Necessário escolher o tipo do documento.');
        document.getElementById("doc_list").focus();
        return 0;
    }

    switch(doc_list){
        case "1"://RG
            if (dt_emis === "" || org_ems === ""){
                alert('Esse tipo de documento necessita que seja preenchido data de emissão e órgão emissor.');
                if (dt_emis === ""){
                    document.getElementById("data_emissao").focus();
                }
                else {
                    document.getElementById("orgao_emissor").focus();
                }
                return 0;
            }
            break;
        case "2"://CPF
            if(!validarCPF(num_doc)){
                alert('CPF inválido.\n\
Favor digitar o número do documento corretamente.');
                document.getElementById("numero_documento").focus();
                return 0;
            }
            break;
        case "3"://CNPJ
            if(!validarCNPJ(num_doc)){
                alert('CNPJ inválido.\n\
Favor digitar o número do documento corretamente.');
                document.getElementById("numero_documento").focus();
                return 0;
            }
            break;
    }

    if(dt_emis === "")
    	dt_emis = "5000-12-31";
    if(org_ems === "")
    	org_ems = "VAZIO";

    var content_div_doc = '<input type="radio" name="doc_inserido" value="doc_' + control_div_doc[div_salvar].cod + '" onclick="change_doc_button_function()">\n\
                             <div id="num_doc_' + control_div_doc[div_salvar].cod + '">' + num_doc + '</div>&nbsp;&#45;\n\
                             <div id="tipo_doc_' + control_div_doc[div_salvar].cod + '">' + doc_name + '</div>';
    if (dt_emis !== "5000-12-31"){
        content_div_doc = content_div_doc + '&nbsp;&#45;&nbsp;<div id="dt_emiss_doc_' + control_div_doc[div_salvar].cod + '">' + dt_emis + '</div>';
    }else{
        content_div_doc = content_div_doc + '<div id="dt_emiss_doc_' + control_div_doc[div_salvar].cod + '"></div>';
    }
    if (org_ems !== "VAZIO"){
        content_div_doc = content_div_doc + '&nbsp;&#45;&nbsp;<div id="org_emiss_doc_' + control_div_doc[div_salvar].cod + '">' + org_ems + '</div> <br>';
    }
    else{
        content_div_doc = content_div_doc + '<div id="org_emiss_doc_' + control_div_doc[div_salvar].cod + '"></div> <br>';
    }

    $('#doc_' + control_div_doc[div_salvar].cod).html(content_div_doc);
    control_div_doc[div_salvar].excluida = 0;
    control_vetor_doc[div_salvar].numero = num_doc;
    control_vetor_doc[div_salvar].tipo_doc = doc_list;
    control_vetor_doc[div_salvar].dt_emiss = dt_emis;
    control_vetor_doc[div_salvar].org_emiss = org_ems;

    limpa_campos_doc_function();
}

function EntradaNumerico(evt) {
    var key_code = evt.keyCode  ? evt.keyCode  :
                   evt.charCode ? evt.charCode :
                   evt.which    ? evt.which    : void 0;

    // Habilita teclas <DEL>, <TAB>, <ENTER>, <ESC> , <BACKSPACE> , <-> e <subtract>
    if (key_code == 8  ||  key_code == 9  ||  key_code == 13  ||  key_code == 27  ||  key_code == 46 || key_code == 109 || key_code == 189) {
        return true;
    }
    // Habilita teclas <HOME>, <END>, mais as quatros setas de navegaÃ§Ã£o (cima, baixo, direta, esquerda)
    else if ((key_code >= 35)  &&  (key_code <= 40)) {
        return true;
    }
    // Habilita Números de 0 a 9
    // 48 a 57 sÃ£o os cÃ³digos para Números
    else if ((key_code >= 48)  &&  (key_code <= 57)) {
        return true;
    }
    return false;
}

function validarCPF(cpf) {

    cpf = cpf.replace(/[^\d]+/g,'');

    if(cpf == '') return false;

    // Elimina CPFs invalidos conhecidos
    if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || 
                            cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999")
            return false;

    // Valida 1o digito
    var add = 0;
    for (var i=0; i < 9; i ++){
        add += parseInt(cpf.charAt(i)) * (10 - i);
    }
    rev = 11 - (add % 11);
    if (rev == 10 || rev == 11){            
        rev = 0;
    }
    if (rev != parseInt(cpf.charAt(9))){
        return false;
    }

    // Valida 2o digito
    add = 0;
    for (i = 0; i < 10; i ++){
        add += parseInt(cpf.charAt(i)) * (11 - i);
    }
    var rev = 11 - (add % 11);
    if (rev == 10 || rev == 11){
        rev = 0;
    }
    if (rev != parseInt(cpf.charAt(10))){
        return false;
    }
    return true;
}

function validarCNPJ(cnpj) {

    cnpj = cnpj.replace(/[^\d]+/g,'');

    if (cnpj.length != 14 || cnpj == "00000000000000" || cnpj == "11111111111111" || cnpj == "22222222222222" || cnpj == "33333333333333" || cnpj == "44444444444444" || 
                             cnpj == "55555555555555" || cnpj == "66666666666666" || cnpj == "77777777777777" || cnpj == "88888888888888" || cnpj == "99999999999999"){
            return false;
    }

    // Valida DVs
    var tamanho = cnpj.length - 2;
    var numeros = cnpj.substring(0,tamanho);
    var digitos = cnpj.substring(tamanho);
    var soma = 0;
    var pos = tamanho - 7;
    for (var i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2){
            pos = 9;
        }
    }
    var resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(0)){
        return false;
    }
    tamanho = tamanho + 1;
    numeros = cnpj.substring(0,tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2){
            pos = 9;
        }
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(1)){
        return false;
    }
    return true;
}

function vetor_end_inserido(endereco, complemento, bairro, cidade, uf, pais, cep, tipo_end){
    this.endereco = endereco;
    this.complemento = complemento;
    this.bairro = bairro;
    this.cidade = cidade;
    this.uf = uf;
    this.pais = pais;
    this.cep = cep;
    this.tipo_end = tipo_end;    
}

var control_vetor_end = new Array();
control_vetor_end[0] = 0;
var control_vetor_end_tipo = new Array();
var control_vetor_end_pais = new Array();
var control_vetor_end_uf = new Array();

function insert_endereco_function() {
    clean_end = $('#div_end').html();
    var endereco = document.getElementById("endereco").value.trim();
    var complemento = document.getElementById("complemento").value.trim();
    var bairro = document.getElementById("bairro").value.trim();
    var cidade = document.getElementById("cidade").value.trim();
    var uf_list = $('#uf_list').val();
    var uf_nome = $('#uf_list :selected').text();
    var pais_list = $('#pais_list').val();
    var cep = document.getElementById("cep").value.trim();
    var tipo_end_list = $('#tipo_end_list').val();
    var tipo_end_nome = $('#tipo_end_list :selected').text();
    var insert = "X";

    var tipo_endereco_tipo_obj = $('#tipo_end_list');
    var tipo_endereco_tamanho_tipo = tipo_endereco_tipo_obj[0].length;
    for (var i=0; i<tipo_endereco_tamanho_tipo;i++){
        control_vetor_end_tipo[i] = document.getElementsByName("option_endereco_tipo")[i].text;
    }

    var tipo_endereco_pais_obj = $('#pais_list');
    var tipo_endereco_tamanho_pais = tipo_endereco_pais_obj[0].length;
    for (i=0; i<tipo_endereco_tamanho_pais;i++){
        control_vetor_end_pais[i] = document.getElementsByName("option_endereco_pais")[i].text;
    }

    var tipo_endereco_uf_obj = $('#uf_list');
    var tipo_endereco_tamanho_uf = tipo_endereco_uf_obj[0].length;
    for (i=0; i<tipo_endereco_tamanho_uf;i++){
        control_vetor_end_uf[i] = document.getElementsByName("option_endereco_uf")[i].text;
    }

    if (endereco === ""){
        alert('Favor inserir o LOGRADOURO do cliente.');
        document.getElementById("endereco").focus();
        return 0;
    }

    if (bairro === ""){
        alert('Favor inserir o BAIRRO do cliente.');
        document.getElementById("bairro").focus();
        return 0;
    }

    if (cidade === ""){
        alert('Favor inserir a CIDADE do cliente.');
        document.getElementById("cidade").focus();
        return 0;
    }

    if (uf_nome === "OUTRO" && complemento === ""){
        alert('Necessário escolher UF do cliente ou preencher em complemento em caso de OUTRO.');
        document.getElementById("complemento").focus();
        return 0;
    }

    if (cep === ""){
        alert('Favor inserir o CEP do cliente.');
        document.getElementById("cep").focus();
        return 0;
    }

    if (tipo_end_nome === ""){
        alert('Necessário escolher o TIPO DE ENDEREÇO do cliente.');
        document.getElementById("tip_end_list").focus();
        return 0;
    }

    if(complemento === null){
        complemento = "";
    }

    i = control_div_end.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_end[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
            control_div_end[i] = new div_end_inseridas(i,0);
            control_vetor_end[i] = new vetor_end_inserido(endereco,complemento,bairro,cidade,uf_list,pais_list,cep,tipo_end_list);
        }
    }
    else{
        control_div_end[0] = new div_end_inseridas(0,0);
        control_vetor_end[0] = new vetor_end_inserido(endereco,complemento,bairro,cidade,uf_list,pais_list,cep,tipo_end_list);
    }

    if (insert === "X"){
        var content_div_end = $('#endereco_inserido').html();
        content_div_end = content_div_end + '<div id="end_' + control_div_end[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="end_inserido" value="end_' + control_div_end[i].cod + '" onclick="change_end_button_function()">\n\
                                                  <div id="tipo_end_' + control_div_end[i].cod + '">' + tipo_end_nome + '</div>: \n\
                                                  <div id="endereco_' + control_div_end[i].cod + '">' + endereco + '</div> <br> </div>';

        $('#endereco_inserido').html(content_div_end);
    }
    else {
        var content_div_end = '<input type="radio" name="end_inserido" value="end_' + control_div_end[j].cod + '" onclick="change_end_button_function()">\n\
                                                  <div id="tipo_end_' + control_div_end[j].cod + '">' + tipo_end_nome + '</div>: \n\
                                                  <div id="endereco_' + control_div_end[j].cod + '">' + endereco + '</div> <br>';

        $('#end_' + control_div_end[j].cod).html(content_div_end);
        control_div_end[j].excluida = 0;
        control_vetor_end[j] = new vetor_end_inserido(endereco,complemento,bairro,cidade,uf_list,pais_list,cep,tipo_end_list);
    }
    limpa_campos_end_function();
    var content_div_bt = '<button type="button" id="incluir_endereco" onclick="insert_endereco_function()">Incluir</button>';
    $('#div_end_bt').html(content_div_bt);
    document.getElementById("endereco").focus();
}

function delete_endereco_function() {
    var div_select = $("input[type='radio'][name='end_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(4),Number(length));
    var tipo_end = $('#tipo_end_' + div_deletar).html();
    var endereco = $('#endereco_' + div_deletar).html();
    var content_div_end = $('#' + div_select).html();

    if (confirm('O endereço ' + tipo_end.trim() + ' ' + endereco.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_end[div_deletar].excluida = 1;
        control_vetor_end[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_end);
    }

    var content_div_bt = '<button type="button" id="incluir_endereco" onclick="insert_endereco_function()">Incluir</button>';
    $('#div_end_bt').html(content_div_bt);
    limpa_campos_end_function();
}

function change_end_button_function(){
    var content_div_bt = '<button type="button" id="incluir_endereco" onclick="insert_endereco_function()">Incluir</button>\n\
                          <button type="button" id="editar_endereco" onclick="editar_endereco_function()">Editar</button>\n\
                          <button type="button" id="delete_endereco" onclick="delete_endereco_function()">Excluir</button>';
    $('#div_end_bt').html(content_div_bt);
}

var div_end_editar;

function editar_endereco_function() {
    var content_div_bt = '<button type="button" id="salvar_endereco" onclick="salvar_endereco_function()">Salvar</button>\n\
                          <button type="button" id="cancelar_endereco" onclick="cancelar_endereco_function()">Cancelar</button>';
    $('#div_end_bt').html(content_div_bt);

    var div_select = $("input[type='radio'][name='end_inserido']:checked").val();
    var content_div_end = $('#' + div_select).html();
    var length = div_select.length;
    div_end_editar = div_select.slice(Number(4),Number(length));

    var endereco = control_vetor_end[div_end_editar].endereco;
    var complemento = control_vetor_end[div_end_editar].complemento;
    var bairro = control_vetor_end[div_end_editar].bairro;
    var cidade = control_vetor_end[div_end_editar].cidade;
    var uf = control_vetor_end[div_end_editar].uf;
    var pais = control_vetor_end[div_end_editar].pais;
    var cep = control_vetor_end[div_end_editar].cep;
    var tipo_end = control_vetor_end[div_end_editar].tipo_end;

    $('#' + div_select).html("");
    $('#' + div_select).html(content_div_end);

    content_div_end = "";

    content_div_end = 'Logradouro: <input type="text" class="size_97" id="endereco" value="' + endereco + '">\n\
                        <br>';
    if(complemento === null){
        content_div_end = content_div_end + 'Complemento: <input type="text" class="size_23" id="complemento">';
    }
    else{
        content_div_end = content_div_end + 'Complemento: <input type="text" class="size_23" id="complemento" value="' + complemento + '">';
    }
    content_div_end = content_div_end + 'Bairro: <input type="text" class="size_22" id="bairro" value="' + bairro + '">\n\
                        Cidade: <input type="text" class="size_22" id="cidade" value="' + cidade + '">\n\
                        <br>UF:&nbsp;';

    content_div_end = content_div_end + '<select id="uf_list" class="size_8">';
    for(var i=0;i<control_vetor_end_uf.length;i++){
        content_div_end = content_div_end + '<option value="' + Number(i+1) + '" name="option_endereco_uf"';
        if (Number(uf) === Number(i+1)){
            content_div_end = content_div_end + ' selected';
        }
        content_div_end = content_div_end + '>' + control_vetor_end_uf[i] + '</option>';
    }
    content_div_end = content_div_end + '</select>';
    content_div_end = content_div_end + '&nbsp;País:\n\
                        <select id="pais_list" class="size_20">';

    for(i=0;i<control_vetor_end_pais.length;i++){
        content_div_end = content_div_end + '<option value="' + Number(i+1) + '" name="option_endereco_pais"';
        if (Number(pais) === Number(i+1)){
            content_div_end = content_div_end + ' selected';
        }
        content_div_end = content_div_end + '>' + control_vetor_end_pais[i] + '</option>';
    }

    content_div_end = content_div_end + '</select>&nbsp;CEP:\n\
                        <input type="text" class="size_12" id="cep" maxlength="9" value="' + cep + '" onkeypress="javascript: return EntradaNumerico(event);">&nbsp;Tipo de EndereÃ§o:\n\
                        <select id="tipo_end_list" class="size_24">';
    for(i=0;i<control_vetor_end_tipo.length;i++){
    content_div_end = content_div_end + '<option value="' + Number(i+1) + '" name="option_endereco_tipo"';
    if (Number(tipo_end) === Number(i+1)){
        content_div_end = content_div_end + ' selected';
    }
    content_div_end = content_div_end + '>' + control_vetor_end_tipo[i] + '</option>';
    }
    content_div_end = content_div_end + '</select> <br>';

    $('#div_end').html("");
    $('#div_end').html(content_div_end);
}

function limpa_campos_end_function(){
    var content_div_end = clean_end;
    $('#div_end').html("");
    $('#div_end').html(content_div_end);
}

function cancelar_endereco_function(){
    var content_div_bt = '<button type="button" id="incluir_endereco" onclick="insert_endereco_function()">Incluir</button>';
    $('#div_end_bt').html(content_div_bt);

    limpa_campos_end_function();
}

function salvar_endereco_function(){
    var content_div_bt = '<button type="button" id="incluir_endereco" onclick="insert_endereco_function()">Incluir</button>';
    $('#div_end_bt').html(content_div_bt);

    var div_salvar = div_end_editar;

    $('#end_' + div_salvar).html("");    

    var endereco = document.getElementById("endereco").value.trim();
    var complemento = document.getElementById("complemento").value.trim();
    var bairro = document.getElementById("bairro").value.trim();
    var cidade = document.getElementById("cidade").value.trim();
    var uf_list = $('#uf_list').val();
    var uf_nome = $('#uf_list :selected').text();
    var pais_list = $('#pais_list').val();
    var cep = document.getElementById("cep").value.trim();
    var tipo_end_list = $('#tipo_end_list').val();
    var tipo_end_nome = $('#tipo_end_list :selected').text();

    if (endereco === ""){
        alert('Favor inserir o LOGRADOURO do cliente.');
        document.getElementById("endereco").focus();
        return 0;
    }

    if (bairro === ""){
        alert('Favor inserir o BAIRRO do cliente.');
        document.getElementById("bairro").focus();
        return 0;
    }

    if (cidade === ""){
        alert('Favor inserir a CIDADE do cliente.');
        document.getElementById("cidade").focus();
        return 0;
    }

    if (uf_nome === "OUTRO" && complemento === ""){
        alert('Necessário escolher UF do cliente ou preencher em complemento em caso de OUTRO.');
        document.getElementById("complemento").focus();
        return 0;
    }

    if (cep === ""){
        alert('Favor inserir o CEP do cliente.');
        document.getElementById("cep").focus();
        return 0;
    }

    if (tipo_end_nome === ""){
        alert('Necessário escolher o TIPO DE ENDEREÇO do cliente.');
        document.getElementById("tip_end_list").focus();
        return 0;
    }

    if(complemento === null){
        complemento = "";
    }

    var content_div_end = '<input type="radio" name="end_inserido" value="end_' + control_div_end[div_salvar].cod + '" onclick="change_end_button_function()">\n\
                                                  <div id="tipo_end_' + control_div_end[div_salvar].cod + '">' + tipo_end_nome + ': </div>\n\
                                                  <div id="endereco_' + control_div_end[div_salvar].cod + '">' + endereco + '</div> <br>';

    $('#end_' + control_div_end[div_salvar].cod).html(content_div_end);
    control_div_end[div_salvar].excluida = 0;

    control_vetor_end[div_salvar].endereco = endereco;
    control_vetor_end[div_salvar].complemento = complemento;
    control_vetor_end[div_salvar].bairro = bairro;
    control_vetor_end[div_salvar].cidade = cidade;
    control_vetor_end[div_salvar].uf = uf_list;
    control_vetor_end[div_salvar].pais = pais_list;
    control_vetor_end[div_salvar].cep = cep;
    control_vetor_end[div_salvar].tipo_end = tipo_end_list;

    limpa_campos_end_function();
}

function vetor_contato_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco){
    this.ddd = ddd;
    this.numero = numero;
    this.tipo_contato = tipo_contato;
    this.cod_pais = cod_pais;
    this.nome = nome;
    this.parentesco = parentesco;
}

var control_vetor_contato = new Array();
control_vetor_contato[0] = 0;
var control_vetor_contato_tipo = new Array();
var control_vetor_contato_parentesco = new Array();

function insert_contatos_function(comercial) {
    clean_contato = $('#div_contato').html();
    var ddd = document.getElementById("ddd").value.trim();
    var numero = document.getElementById("numero_contato").value.trim();
    var tipo_contato = $('#tipocont_list').val();
    var tipo_contato_nome = $('#tipocont_list :selected').text();
    var cod_pais = document.getElementById("cod_pais").value.trim();
    var nome = document.getElementById("nome_contato").value.trim();
    var parentesco = $('#info_contato_list').val();
    var parentesco_nome = $('#info_contato_list :selected').text();
    var insert = "X";

    var tipo_contato_tipo_obj = $('#tipocont_list');
    var tipo_contato_tamanho = tipo_contato_tipo_obj[0].length;
    for (var i=0; i<tipo_contato_tamanho;i++){
        control_vetor_contato_tipo[i] = document.getElementsByName("option_contato_tipo")[i].text;
    }
    var tipo_contato_parentesco_obj = $('#info_contato_list');
    var tipo_contato_tamanho = tipo_contato_parentesco_obj[0].length;
    for (i=0; i<tipo_contato_tamanho;i++){
        control_vetor_contato_parentesco[i] = document.getElementsByName("option_parentesco_cargo")[i].text;
    }

    if (ddd === ""){
        alert('Favor inserir o DDD do contato.');
        document.getElementById("ddd").focus();
        return 0;
    }

    if (numero === ""){
        alert('Favor inserir o NÚMERO do contato.');
        document.getElementById("numero_contato").focus();
        return 0;
    }

    if (cod_pais === ""){
        alert('Favor inserir o COD. PAÍS do contato.');
        document.getElementById("cod_pais").focus();
        return 0;
    }

    if (nome === ""){
        alert('Favor inserir o NOME do contato.');
        document.getElementById("nome_contato").focus();
        return 0;
    }

    i = control_div_contato.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_contato[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
            control_div_contato[i] = new div_contato_inseridas(i,0);
            control_vetor_contato[i] = new vetor_contato_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco);
        }
    }
    else{
        control_div_contato[0] = new div_contato_inseridas(0,0);
        control_vetor_contato[0] = new vetor_contato_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco);
    }

    if (insert === "X"){
        var content_div_contato = $('#contato_inserido').html();
        content_div_contato = content_div_contato + '<div id="contato_' + control_div_contato[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="contato_inserido" value="contato_' + control_div_contato[i].cod + '" onclick="change_contato_button_function(' + comercial + ')">\n\
                                                  <div id="tipo_contato_' + control_div_contato[i].cod + '">' + tipo_contato_nome + '</div>: \n\
                                                  <div id="nome_contato_' + control_div_contato[i].cod + '">' + nome + '</div>&nbsp;+\n\
                                                  <div id="cod_pais_' + control_div_contato[i].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_' + control_div_contato[i].cod + '">' + ddd + '</div>)\n\
                                                  <div id="numero_contato_' + control_div_contato[i].cod + '">' + numero + '</div>&nbsp;-\n\
                                                  <div id="grau_contato_' + control_div_contato[i].cod + '">' + parentesco_nome + '</div> <br> </div>';
        $('#contato_inserido').html(content_div_contato);
    }
    else {
        var content_div_contato = '<input type="radio" name="contato_inserido" value="contato_' + control_div_contato[j].cod + '" onclick="change_contato_button_function(' + comercial + ')">\n\
                                                  <div id="tipo_contato_' + control_div_contato[j].cod + '">' + tipo_contato_nome + '</div>: \n\
                                                  <div id="nome_contato_' + control_div_contato[j].cod + '">' + nome + '</div>&nbsp;+\n\
                                                  <div id="cod_pais_' + control_div_contato[j].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_' + control_div_contato[j].cod + '">' + ddd + '</div>)\n\
                                                  <div id="numero_contato_' + control_div_contato[j].cod + '">' + numero + '</div>&nbsp;-\n\
                                                  <div id="grau_contato_' + control_div_contato[j].cod + '">' + parentesco_nome + '</div> <br>';

        $('#contato_' + control_div_contato[j].cod).html(content_div_contato);
        control_div_contato[j].excluida = 0;
        control_vetor_contato[j] = new vetor_contato_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco);
    }
    limpa_campos_contato_function();
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_function(' + comercial + ')">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);
    document.getElementById("ddd").focus();
}

function delete_contatos_function(comercial) {
    var div_select = $("input[type='radio'][name='contato_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(8),Number(length));
    var tipo_contato = $('#tipo_contato_' + div_deletar).html();
    var nome = $('#nome_contato_' + div_deletar).html();
    var cod_pais = $('#cod_pais_' + div_deletar).html();
    var ddd = $('#ddd_contato_' + div_deletar).html();
    var numero = $('#numero_contato_' + div_deletar).html();

    var content_div_contato = $('#' + div_select).html();

    if (confirm('O contato ' + tipo_contato.trim() + ': ' + nome.trim() + ' +' + cod_pais.trim() + '(' + ddd.trim() + ')' + numero.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_contato[div_deletar].excluida = 1;
        control_vetor_contato[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_contato);
    }

    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_function(' + comercial + ')">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);
    limpa_campos_contato_function();
}

function change_contato_button_function(comercial){
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_function(' + comercial + ')">Incluir</button>\n\
                          <button type="button" id="editar_contato" onclick="editar_contatos_function()">Editar</button>\n\
                          <button type="button" id="delete_contato" onclick="delete_contatos_function()">Excluir</button>';
    $('#div_contato_bt').html(content_div_bt);
}

var div_contato_editar;

function editar_contatos_function(comercial) {
    var content_div_bt = '<button type="button" id="salvar_contato" onclick="salvar_contatos_function(' + comercial + ')">Salvar</button>\n\
                          <button type="button" id="cancelar_contato" onclick="cancelar_contatos_function()">Cancelar</button>';
    $('#div_contato_bt').html(content_div_bt);

    var div_select = $("input[type='radio'][name='contato_inserido']:checked").val();
    var content_div_contato = $('#' + div_select).html();
    var length = div_select.length;
    div_contato_editar = div_select.slice(Number(8),Number(length));

    var ddd = control_vetor_contato[div_contato_editar].ddd;
    var numero = control_vetor_contato[div_contato_editar].numero;
    var tipo_contato = control_vetor_contato[div_contato_editar].tipo_contato;
    var cod_pais = control_vetor_contato[div_contato_editar].cod_pais;
    var nome = control_vetor_contato[div_contato_editar].nome;
    var parentesco = control_vetor_contato[div_contato_editar].parentesco;

    $('#' + div_select).html("");
    $('#' + div_select).html(content_div_contato);

    content_div_contato = "";

    content_div_contato = 'DDD:<input type="text" class="size_5" id="ddd" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);" value="' + ddd + '">\n\
                        Número:<input type="text" class="size_19" id="numero_contato" maxlength="10" onkeypress="javascript: return EntradaNumerico(event);" value="' + numero + '">\n\
                        Tipo do Contato:\n\
                        <select id="tipocont_list" class="size_21">';
    for(var i=0;i<control_vetor_contato_tipo.length;i++){
        content_div_contato = content_div_contato + '<option value="' + Number(i+1) + '" name="option_contato_tipo"';
        if (Number(tipo_contato) === Number(i+1)){
            content_div_contato = content_div_contato + ' selected';
        }
        content_div_contato = content_div_contato + '>' + control_vetor_contato_tipo[i] + '</option>';
    }
    content_div_contato = content_div_contato + '</select>\n\
                        Cod. País: <input type="text" class="size_5" id="cod_pais" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);" value="' + cod_pais + '">\n\
                        <br>Nome:<input type="text" class="size_32" id="nome_contato" value="' + nome + '">\n\
                        Parentesco / Cargo:\n\
                        <select id="info_contato_list" class="size_35">';
    for(i=0;i<control_vetor_contato_parentesco.length;i++){
        content_div_contato = content_div_contato + '<option value="' + Number(i+1) + '" name="option_parentesco_cargo"';
        if (Number(parentesco) === Number(i+1)){
            content_div_contato = content_div_contato + ' selected';
        }
        content_div_contato = content_div_contato + '>' + control_vetor_contato_parentesco[i] + '</option>';
    }
    content_div_contato = content_div_contato + '</select>';

    $('#div_contato').html("");
    $('#div_contato').html(content_div_contato);
}

function limpa_campos_contato_function(){
    var content_div_contato = clean_contato;
    $('#div_contato').html("");
    $('#div_contato').html(content_div_contato);
}

function cancelar_contatos_function(comercial){
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_function(' + comercial + ')">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);

    limpa_campos_contato_function();
}

function salvar_contatos_function(comercial){
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_function(' + comercial + ')">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);

    var div_salvar = div_contato_editar;

    $('#contato_' + div_salvar).html("");    

    var ddd = document.getElementById("ddd").value.trim();
    var numero = document.getElementById("numero_contato").value.trim();
    var tipo_contato = $('#tipocont_list').val();
    var tipo_contato_nome = $('#tipocont_list :selected').text();
    var cod_pais = document.getElementById("cod_pais").value.trim();
    var nome = document.getElementById("nome_contato").value.trim();
    var parentesco = $('#info_contato_list').val();
    var parentesco_nome = $('#info_contato_list :selected').text();

    if (ddd === ""){
        alert('Favor inserir o DDD do contato.');
        document.getElementById("ddd").focus();
        return 0;
    }

    if (numero === ""){
        alert('Favor inserir o NÚMERO do contato.');
        document.getElementById("numero_contato").focus();
        return 0;
    }

    if (cod_pais === ""){
        alert('Favor inserir o COD. PAÍS do contato.');
        document.getElementById("cod_pais").focus();
        return 0;
    }

    if (nome === ""){
        alert('Favor inserir o NOME do contato.');
        document.getElementById("nome_contato").focus();
        return 0;
    }

    var content_div_contato = '<input type="radio" name="contato_inserido" value="contato_' + control_div_contato[div_salvar].cod + '" onclick="change_contato_button_function(' + comercial + ')">\n\
                                                  <div id="tipo_contato_' + control_div_contato[div_salvar].cod + '">' + tipo_contato_nome + '</div>: \n\
                                                  <div id="nome_contato_' + control_div_contato[div_salvar].cod + '">' + nome + '</div>&nbsp;+\n\
                                                  <div id="cod_pais_' + control_div_contato[div_salvar].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_' + control_div_contato[div_salvar].cod + '">' + ddd + '</div>)&nbsp;\n\
                                                  <div id="numero_contato_' + control_div_contato[div_salvar].cod + '">' + numero + '</div>&nbsp;-\n\
                                                  <div id="grau_contato_' + control_div_contato[div_salvar].cod + '">' + parentesco_nome + '</div> <br>';

    $('#contato_' + control_div_contato[div_salvar].cod).html(content_div_contato);
    control_div_contato[div_salvar].excluida = 0;

    control_vetor_contato[div_salvar].ddd = ddd;
    control_vetor_contato[div_salvar].numero = numero;
    control_vetor_contato[div_salvar].tipo_contato = tipo_contato;
    control_vetor_contato[div_salvar].cod_pais = cod_pais;
    control_vetor_contato[div_salvar].nome = nome;
    control_vetor_contato[div_salvar].parentesco = parentesco;

    limpa_campos_contato_function();
}

function vetor_email_inserido(email){
    this.email = email;
}

var control_vetor_email = new Array();
control_vetor_email[0] = 0;

function insert_emails_function() {
    clean_email = $('#div_email').html();
    var email = document.getElementById("email").value.trim();
    var insert = "X";

    if (email === ""){
        alert('Favor inserir o EMAIL do contato.');
        document.getElementById("email").focus();
        return 0;
    }

    var i = control_div_email.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_email[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
            control_div_email[i] = new div_contato_inseridas(i,0);
			control_vetor_email[i] = new vetor_email_inserido(email);
        }
    }
    else{
        control_div_email[0] = new div_contato_inseridas(0,0);
        control_vetor_email[0] = new vetor_email_inserido(email);
    }

    if (insert === "X"){
        var content_div_email = $('#emails_inserido').html();
        content_div_email = content_div_email + '<div id="email_' + control_div_email[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="email_inserido" value="email_' + control_div_email[i].cod + '" onclick="change_email_button_function()">\n\
                                                  <div id="nome_email_' + control_div_email[i].cod + '">' + email + '</div> <br> </div>';

        $('#emails_inserido').html(content_div_email);
    }
    else {
        var content_div_email = '<input type="radio" name="email_inserido" value="email_' + control_div_email[j].cod + '" onclick="change_email_button_function()">\n\
                                                  <div id="nome_email_' + control_div_email[j].cod + '">' + email + '</div> <br>';

        $('#email_' + control_div_email[j].cod).html(content_div_email);
        control_div_email[j].excluida = 0;
        control_vetor_email[j] = new vetor_email_inserido(email);
    }
    limpa_campos_email_function();
    var content_div_bt = '<button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>';
    $('#div_email_bt').html(content_div_bt);
    document.getElementById("email").focus();
}

function delete_emails_function() {
    var div_select = $("input[type='radio'][name='email_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(6),Number(length));

    var email = $('#nome_email_' + div_deletar).html();

    var content_div_email = $('#' + div_select).html();

    if (confirm('O email ' + email.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_email[div_deletar].excluida = 1;
        control_vetor_email[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_email);
    }

    var content_div_bt = '<button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>';
    $('#div_email_bt').html(content_div_bt);
    limpa_campos_email_function();
}

function change_email_button_function(){
    var content_div_bt = '<button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>\n\
                          <button type="button" id="editar_email" onclick="editar_emails_function()">Editar</button>\n\
                          <button type="button" id="delete_email" onclick="delete_emails_function()">Excluir</button>';
    $('#div_email_bt').html(content_div_bt);
}

var div_emails_editar;

function editar_emails_function() {
    var content_div_bt = '<button type="button" id="salvar_email" onclick="salvar_emails_function()">Salvar</button>\n\
                          <button type="button" id="cancelar_email" onclick="cancelar_emails_function()">Cancelar</button>';
    $('#div_email_bt').html(content_div_bt);

    var div_select = $("input[type='radio'][name='email_inserido']:checked").val();
    var content_div_email = $('#' + div_select).html();
    var length = div_select.length;
    div_emails_editar = div_select.slice(Number(6),Number(length));

    var email = control_vetor_email[div_emails_editar].email;

    $('#' + div_select).html("");
    $('#' + div_select).html(content_div_email);

    content_div_email = "";
    content_div_email = '<input type="email" id="email" class="size_100" value="' + email + '">';

    $('#div_email').html("");
    $('#div_email').html(content_div_email);
}

function limpa_campos_email_function(){
    var content_div_email = clean_email;
    $('#div_email').html("");
    $('#div_email').html(content_div_email);
}

function cancelar_emails_function(){
    var content_div_bt = '<button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>';
    $('#div_email_bt').html(content_div_bt);

    limpa_campos_email_function();
}

function salvar_emails_function(){
    var content_div_bt = '<button type="button" id="incluir_email" onclick="insert_emails_function()">Incluir</button>';
    $('#div_email_bt').html(content_div_bt);

    var div_salvar = div_emails_editar;

    $('#email_' + div_salvar).html("");    

    var email = document.getElementById("email").value.trim();

    if (email === ""){
        alert('Favor inserir o EMAIL do contato.');
        document.getElementById("email").focus();
        return 0;
    }

    var content_div_email = '<input type="radio" name="email_inserido" value="email_' + control_div_email[div_salvar].cod + '" onclick="change_email_button_function()">\n\
                                                  <div id="nome_email_' + control_div_email[div_salvar].cod + '">' + email + '</div> <br>';

    $('#email_' + control_div_email[div_salvar].cod).html(content_div_email);
    control_div_email[div_salvar].excluida = 0;
    control_vetor_email[div_salvar].email = email;

    limpa_campos_email_function();
}

var control_vetor_doc_json = new Array();
function ajustar_vetor_doc_function(){

	var length = control_vetor_doc.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_doc_json[x] = control_vetor_doc[x];

	for(var i=0; i<length; i++){
		if(control_vetor_doc_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_doc_json[j] !== 0){
					control_vetor_doc_json[i] = control_vetor_doc_json[j];
					control_vetor_doc_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_doc_json[i] === 0)
			break;
	}
	control_vetor_doc_json.length = i;
}

var control_vetor_end_json = new Array();
function ajustar_vetor_end_function(){

	var length = control_vetor_end.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_end_json[x] = control_vetor_end[x];

	for(var i=0; i<length; i++){
		if(control_vetor_end_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_end_json[j] !== 0){
					control_vetor_end_json[i] = control_vetor_end_json[j];
					control_vetor_end_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_end_json[i] === 0)
			break;
	}
	control_vetor_end_json.length = i;
}

var control_vetor_contato_json = new Array();
function ajustar_vetor_contato_function(){

	var length = control_vetor_contato.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_contato_json[x] = control_vetor_contato[x];

	for(var i=0; i<length; i++){
		if(control_vetor_contato_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_contato_json[j] !== 0){
					control_vetor_contato_json[i] = control_vetor_contato_json[j];
					control_vetor_contato_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_contato_json[i] === 0)
			break;
	}
	control_vetor_contato_json.length = i;
}

var control_vetor_email_json = new Array();
function ajustar_vetor_email_function(){

	var length = control_vetor_email.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_email_json[x] = control_vetor_email[x];
		
	for(var i=0; i<length; i++){
		if(control_vetor_email_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_email_json[j] !== 0){
					control_vetor_email_json[i] = control_vetor_email_json[j];
					control_vetor_email_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_email_json[i] === 0)
			break;
	}
	control_vetor_email_json.length = i;
}

function doc_carregado(num_doc, tipo_doc, dt_emiss, org_emiss){
    this.num_doc = num_doc;
    this.tipo_doc = tipo_doc;
    this.dt_emiss = dt_emiss;
    this.org_emiss = org_emiss;
}

function end_carregado(logradouro, complemento, bairro, cidade, uf, pais, cep, tipo_end){
    this.logradouro = logradouro;
    this.complemento = complemento;
    this.bairro = bairro;
    this.cidade = cidade;
    this.uf = uf;
    this.pais = pais;
    this.cep = cep;
    this.tipo_end = tipo_end;
}

function contato_carregado(ddd, numero, tipo_contato, cod_pais, nome, grau_contato){
    this.ddd = ddd;
    this.numero = numero;
    this.tipo_contato = tipo_contato;
    this.cod_pais = cod_pais;
    this.nome = nome;
    this.grau_contato = grau_contato;
}

function email_carregado(email){
    this.email = email;
}

var doc_carregado_array = new Array();
var end_carregado_array = new Array();
var contato_carregado_array = new Array();
var email_carregado_array = new Array();

function carregar_dados_consulta_cliente_function(){
    var i;
    var j;
    /*Carregar documentos*/
    var div_doc_inserido_obj = $("#docs_inserido");
    clean_doc = $('#div_doc').html();
    var tamanho_doc = div_doc_inserido_obj[0].childElementCount;
    for(i=0;i<tamanho_doc;i++){
        doc_carregado_array[i] = new doc_carregado();
        doc_carregado_array[i].num_doc = $('#num_doc_oculta_' + i).html().trim();
        doc_carregado_array[i].tipo_doc = $('#tipo_doc_oculta_' + i).html().trim();
        doc_carregado_array[i].dt_emiss = $('#dt_emiss_doc_oculta_' + i).html().trim();
        doc_carregado_array[i].org_emiss = $('#org_emiss_doc_oculta_' + i).html().trim();
    }
    var tipo_doc_tipo_obj = $('#tipodoc_list');
    var tipo_doc_tamanho_tipo = tipo_doc_tipo_obj[0].length;
    for (i=0; i<tipo_doc_tamanho_tipo;i++){
        control_vetor_doc_tipo[i] = document.getElementsByName("option_documento_tipo")[i].text;
    }
    for(i=0;i<tamanho_doc;i++){
    	control_div_doc[i] = new div_doc_inseridas(0, 0);
    	control_vetor_doc[i] = new vetor_doc_inserido();
        control_vetor_doc[i].numero = doc_carregado_array[i].num_doc;
        for(j=0;j<tipo_doc_tamanho_tipo;j++){
            if(control_vetor_doc_tipo[j] === doc_carregado_array[i].tipo_doc){
                control_vetor_doc[i].tipo_doc = Number(j+1);
                break;
            }
        }
        if(doc_carregado_array[i].dt_emiss !== "5000-12-31"){
        	control_vetor_doc[i].dt_emiss = doc_carregado_array[i].dt_emiss;
        }else{
        	control_vetor_doc[i].dt_emiss = "";
        }
        if(doc_carregado_array[i].org_emiss !== "VAZIO"){
        	control_vetor_doc[i].org_emiss = doc_carregado_array[i].org_emiss;
        }else{
        	control_vetor_doc[i].org_emiss = "";
        }
    }
    $('#docs_inserido').html("");

    var content_div_doc = "";

    for(i=0;i<tamanho_doc;i++){
    	content_div_doc = content_div_doc + '<div id="doc_' + i + '" class="div_inseridos">\n\
    	<input type="radio" name="doc_inserido" value="doc_' + i + '" onclick="change_doc_button_function()">\n\
        <div id="num_doc_' + i + '">' + control_vetor_doc[i].numero + '</div>&nbsp;&#45;\n\
        <div id="tipo_doc_' + i + '">' + control_vetor_doc_tipo[Number(control_vetor_doc[i].tipo_doc) - 1] + '</div>';
		if (control_vetor_doc[i].dt_emiss !== ""){
		content_div_doc = content_div_doc + '&nbsp;&#45;&nbsp;<div id="dt_emiss_doc_' + i + '">' + control_vetor_doc[i].dt_emiss + '</div>';
		}else{
		content_div_doc = content_div_doc + '<div id="dt_emiss_doc_' + i + '"></div>';
		}
		if (control_vetor_doc[i].org_emiss !== ""){
		content_div_doc = content_div_doc + '&nbsp;&#45;&nbsp;<div id="org_emiss_doc_' + i + '">' + control_vetor_doc[i].org_emiss + '</div> <br> </div>';
		}
		else{
		content_div_doc = content_div_doc + '<div id="org_emiss_doc_' + i + '"></div> <br> </div>';
		}
    }
    $('#docs_inserido').html(content_div_doc);
    
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar endereços*/
    var div_end_inserido_obj = $("#endereco_inserido");
    var tamanho_end = div_end_inserido_obj[0].childElementCount;
    clean_end = $('#div_end').html();
    for(i=0;i<tamanho_end;i++){
        end_carregado_array[i] = new end_carregado();
        end_carregado_array[i].logradouro = $('#endereco_oculta_' + i).html().trim();
        end_carregado_array[i].complemento = $('#complemento_oculta_' + i).html().trim();
        end_carregado_array[i].bairro = $('#bairro_oculta_' + i).html().trim();
        end_carregado_array[i].cidade = $('#cidade_oculta_' + i).html().trim();
        end_carregado_array[i].uf = $('#uf_oculta_' + i).html().trim();
        end_carregado_array[i].pais = $('#pais_oculta_' + i).html().trim();
        end_carregado_array[i].cep = $('#cep_oculta_' + i).html().trim();
        end_carregado_array[i].tipo_end = $('#tipo_end_oculta_' + i).html().trim();
    }
    var tipo_endereco_tipo_obj = $('#tipo_end_list');
    var tipo_endereco_tamanho_tipo = tipo_endereco_tipo_obj[0].length;
    for (var i=0; i<tipo_endereco_tamanho_tipo;i++){
        control_vetor_end_tipo[i] = document.getElementsByName("option_endereco_tipo")[i].text;
    }
    var tipo_endereco_pais_obj = $('#pais_list');
    var tipo_endereco_tamanho_pais = tipo_endereco_pais_obj[0].length;
    for (i=0; i<tipo_endereco_tamanho_pais;i++){
        control_vetor_end_pais[i] = document.getElementsByName("option_endereco_pais")[i].text;
    }
    var tipo_endereco_uf_obj = $('#uf_list');
    var tipo_endereco_tamanho_uf = tipo_endereco_uf_obj[0].length;
    for (i=0; i<tipo_endereco_tamanho_uf;i++){
        control_vetor_end_uf[i] = document.getElementsByName("option_endereco_uf")[i].text;
    }
    for(i=0;i<tamanho_end;i++){
    	control_div_end[i] = new div_end_inseridas(0, 0);
    	control_vetor_end[i] = new vetor_end_inserido();
        control_vetor_end[i].endereco = end_carregado_array[i].logradouro;
        control_vetor_end[i].complemento = end_carregado_array[i].complemento;
        control_vetor_end[i].bairro = end_carregado_array[i].bairro;
        control_vetor_end[i].cidade = end_carregado_array[i].cidade;
        control_vetor_end[i].uf = end_carregado_array[i].uf;
        control_vetor_end[i].pais = end_carregado_array[i].pais;
        control_vetor_end[i].cep = end_carregado_array[i].cep;
        control_vetor_end[i].tipo_end = end_carregado_array[i].tipo_end;
    }
    $('#endereco_inserido').html("");

    var content_div_end = "";

    for(i=0;i<tamanho_end;i++){

    	content_div_end = content_div_end + '<div id="end_' + i + '" class="div_inseridos">\n\
							<input type="radio" name="end_inserido" value="end_' + i + '" onclick="change_end_button_function()">\n\
							<div id="tipo_end_' + i + '">' + control_vetor_end_tipo[Number(control_vetor_end[i].tipo_end) - 1] + ': </div>\n\
							<div id="endereco_' + i + '">' + control_vetor_end[i].endereco + '</div> <br>\n\
						</div>';
    }
    $('#endereco_inserido').html(content_div_end);
        
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar contatos*/
    var div_contato_inserido_obj = $("#contato_inserido");
    var tamanho_contato = div_contato_inserido_obj[0].childElementCount;
    clean_contato = $('#div_contato').html();
    for(i=0;i<tamanho_contato;i++){
        contato_carregado_array[i] = new contato_carregado();
        contato_carregado_array[i].ddd = $('#ddd_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].numero = $('#numero_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].tipo_contato = $('#tipo_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].cod_pais = $('#cod_pais_oculta_' + i).html().trim();
        contato_carregado_array[i].nome = $('#nome_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].grau_contato = $('#grau_contato_oculta_' + i).html().trim();
    }
    var tipo_contato_tipo_obj = $('#tipocont_list');
    var tipo_contato_tamanho_tipo = tipo_contato_tipo_obj[0].length;
    for (i=0; i<tipo_contato_tamanho_tipo;i++){
        control_vetor_contato_tipo[i] = document.getElementsByName("option_contato_tipo")[i].text;
    }
    var tipo_contato_parentesco_obj = $('#info_contato_list');
    var tipo_contato_tamanho_parentesco = tipo_contato_parentesco_obj[0].length;
    for (i=0; i<tipo_contato_tamanho_parentesco;i++){
        control_vetor_contato_parentesco[i] = document.getElementsByName("option_parentesco_cargo")[i].text;
    }
    for(i=0;i<tamanho_contato;i++){
    	control_div_contato[i] = new div_contato_inseridas(0, 0);
    	control_vetor_contato[i] = new vetor_contato_inserido();
        control_vetor_contato[i].ddd = contato_carregado_array[i].ddd;
        control_vetor_contato[i].numero = contato_carregado_array[i].numero;
        for(j=0;j<tipo_contato_tamanho_tipo;j++){
            if(control_vetor_contato_tipo[j] === contato_carregado_array[i].tipo_contato){
                control_vetor_contato[i].tipo_contato = Number(j+1);
                break;
            }
        }
        control_vetor_contato[i].cod_pais = contato_carregado_array[i].cod_pais;
        control_vetor_contato[i].nome = contato_carregado_array[i].nome;
        for(j=0;j<tipo_contato_tamanho_parentesco;j++){
            if(control_vetor_contato_parentesco[j] === contato_carregado_array[i].grau_contato){
                control_vetor_contato[i].parentesco = Number(j+1);
                break;
            }
        }
    }

    $('#contato_inserido').html("");

    var content_div_contato = "";

    for(i=0;i<tamanho_contato;i++){
    	content_div_contato = content_div_contato + '<div id="contato_' + i + '" class="div_inseridos">\n\
        <input type="radio" name="contato_inserido" value="contato_' + i + '" onclick="change_contato_button_function(false)">\n\
        <div id="tipo_contato_' + i + '">' + control_vetor_contato_tipo[Number(control_vetor_contato[i].tipo_contato) - 1] + '</div>: \n\
        <div id="nome_contato_' + i + '">' + control_vetor_contato[i].nome + '</div>&nbsp;+\n\
        <div id="cod_pais_' + i + '">' + control_vetor_contato[i].cod_pais + '</div>\n\
        (<div id="ddd_contato_' + i + '">' + control_vetor_contato[i].ddd + '</div>)\n\
        <div id="numero_contato_' + i + '">' + control_vetor_contato[i].numero + '</div>&nbsp;-\n\
        <div id="grau_contato_' + i + '">' + control_vetor_contato_parentesco[Number(control_vetor_contato[i].parentesco) - 1] + '</div> <br> </div>';
    }

    $('#contato_inserido').html(content_div_contato);

/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar emails*/
    var div_email_inserido_obj = $("#emails_inserido");
    var tamanho_email = div_email_inserido_obj[0].childElementCount;
    clean_email = $('#div_email').html();
    for(i=0;i<tamanho_email;i++){
        email_carregado_array[i] = new email_carregado();
        email_carregado_array[i].email = $('#nome_email_oculta_' + i).html().trim();
    }
    for(i=0;i<tamanho_email;i++){
    	control_div_email[i] = new div_email_inseridas(0, 0);
    	control_vetor_email[i] = new vetor_email_inserido();
        control_vetor_email[i].email = email_carregado_array[i].email;
    }
    $('#emails_inserido').html("");

    var content_div_email = "";

    for(i=0;i<tamanho_email;i++){
    	content_div_email = content_div_email + '<div id="email_' + i + '" class="div_inseridos">\n\
        <input type="radio" name="email_inserido" value="email_' + i + '" onclick="change_email_button_function()">\n\
        <div id="nome_email_' + i + '">' + control_vetor_email[i].email + '</div> <br> </div>';
    }

    $('#emails_inserido').html(content_div_email);
}

function operacional_cadastrar_cliente_cadastrar_function(){
	ajustar_vetor_doc_function();
	ajustar_vetor_end_function();
	ajustar_vetor_contato_function();
	ajustar_vetor_email_function();
	if(confirm('Deseja realizar o ingresso do Cliente?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var dt_nascimento = document.getElementById("data_nasc");
		var site = document.getElementById("url_site");
		var cod_vendedor = $('#vendedor_list :selected').val();
		var cod_usuario = document.getElementById("cod_usuario");

		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Cliente.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Cliente.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}
		if(dt_nascimento.value.trim() === ""){
			alert("Favor inserir DATA DE NASCIMENTO do Cliente");
			document.getElementById("data_nasc").focus();
			return 0;
		}
		if(site.value.trim() === ""){
			site.value = "";
		}

		if(control_vetor_doc_json[0] === 0){
			alert("Favor inserir DOCUMENTO do Cliente");
			document.getElementById("numero_documento").focus();
			return 0;
		}

		var adress_aux;
		var documentoLength = control_vetor_doc_json.length;
		adress_aux = '&QDOC=' + documentoLength;
		var tem_doc = false;
		for(var i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				if((control_vetor_doc_json[i].tipo_doc.trim() == 2)||(control_vetor_doc_json[i].tipo_doc.trim() == 3)){
					tem_doc = true;
				}
			}
		}
		if(!tem_doc){
			alert("Favor inserir pelo menos 1 DOCUMENTO CPF/CNPJ.");
			document.getElementById("numero_documento").focus();
			return 0;
		}
		for(i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPODOC_'+ i + '=' + control_vetor_doc_json[i].tipo_doc.trim();
				adress_aux = adress_aux + '&NUMDOC_'+ i + '=' + control_vetor_doc_json[i].numero.trim();
				adress_aux = adress_aux + '&DTDOC_'+ i + '=' + control_vetor_doc_json[i].dt_emiss.trim();
				adress_aux = adress_aux + '&ORGDOC_'+ i + '=' + control_vetor_doc_json[i].org_emiss.trim();
			}
		}

		if(control_vetor_end_json[0] === 0){
			alert("Favor inserir ENDEREÇO do Cliente");
			document.getElementById("endereco").focus();
			return 0;
		}

		var enderecoLength = control_vetor_end_json.length;
		adress_aux = adress_aux + '&QEND=' + enderecoLength;
		for(i=0;i<enderecoLength;i++){
			if(control_vetor_end_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&END_'+ i + '=' + control_vetor_end_json[i].endereco.trim();
				adress_aux = adress_aux + '&BAIRRO_'+ i + '=' + control_vetor_end_json[i].bairro.trim();
				adress_aux = adress_aux + '&CIDADE_'+ i + '=' + control_vetor_end_json[i].cidade.trim();
				adress_aux = adress_aux + '&UF_'+ i + '=' + control_vetor_end_json[i].uf.trim();
				adress_aux = adress_aux + '&PAIS_'+ i + '=' + control_vetor_end_json[i].pais.trim();
				adress_aux = adress_aux + '&COMP_'+ i + '=' + control_vetor_end_json[i].complemento.trim();
				adress_aux = adress_aux + '&CEP_'+ i + '=' + control_vetor_end_json[i].cep.trim();
				adress_aux = adress_aux + '&TIPOEND_'+ i + '=' + control_vetor_end_json[i].tipo_end.trim();
			}
		}

		if(control_vetor_contato_json[0] === 0){
			alert("Favor inserir CONTATO do Cliente");
			document.getElementById("ddd").focus();
			return 0;
		}

		var contatoLength = control_vetor_contato_json.length;
		adress_aux = adress_aux + '&QCTO=' + contatoLength;
		if(contatoLength < 3){
			alert("Favor inserir pelo menos 3 CONTATOS do Cliente.");
			document.getElementById("ddd").focus();
			return 0;
		}
		var tem_tel = false;
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				if(control_vetor_contato_json[i].tipo_contato.trim() == 3){
					tem_tel = true;
				}
			}
		}
		if(!tem_tel){
			alert("Favor inserir pelo menos 1 CONTATO do tipo Celular.");
			document.getElementById("ddd").focus();
			return 0;
		}
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_json[i].cod_pais.trim();
				adress_aux = adress_aux + '&NOMECTO_'+ i + '=' + control_vetor_contato_json[i].nome.trim();
				adress_aux = adress_aux + '&PARENCTO_'+ i + '=' + control_vetor_contato_json[i].parentesco.trim();
			}
		}

		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Cliente");
			document.getElementById("email").focus();
			return 0;
		}

		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}

		d = new Date();
		var adress = url_adress + 'services/cliente?OP_CODE=CREATE&TIPO=' + tipo_pessoa.trim() + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&SITE=' + site.value.trim() + '&DATA_NASCIMENTO=' + dt_nascimento.value.trim() + '&DATA_INGRESSO=' + d.yyyymmdd();
		adress = adress + '&COD_VENDEDOR='+ cod_vendedor.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		adress = adress + adress_aux;
		document.location.href = adress;
	}
	else
		return 0;
}

function operacional_consultar_cliente_salvar_function(){
	alert("Em desenvolvimento!");
	return 0;
	ajustar_vetor_doc_function();
	ajustar_vetor_end_function();
	ajustar_vetor_contato_function();
	ajustar_vetor_email_function();
	if(confirm('Deseja salvar os dados alterados do Cliente?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var dt_nascimento = document.getElementById("data_nasc");
		var site = document.getElementById("url_site");
		var cod_vendedor = $('#vendedor_list :selected').val();
		var cod_usuario = document.getElementById("cod_usuario");

		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Cliente.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Cliente.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}
		if(dt_nascimento.value.trim() === ""){
			alert("Favor inserir DATA DE NASCIMENTO do Cliente");
			document.getElementById("data_nasc").focus();
			return 0;
		}
		if(site.value.trim() === ""){
			site.value = "";
		}

		if(control_vetor_doc_json[0] === 0){
			alert("Favor inserir DOCUMENTO do Cliente");
			document.getElementById("numero_documento").focus();
			return 0;
		}

		var adress_aux;
		var documentoLength = control_vetor_doc_json.length;
		adress_aux = '&QDOC=' + documentoLength;
		for(var i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPODOC_'+ i + '=' + control_vetor_doc_json[i].tipo_doc.trim();
				adress_aux = adress_aux + '&NUMDOC_'+ i + '=' + control_vetor_doc_json[i].numero.trim();
				adress_aux = adress_aux + '&DTDOC_'+ i + '=' + control_vetor_doc_json[i].dt_emiss.trim();
				adress_aux = adress_aux + '&ORGDOC_'+ i + '=' + control_vetor_doc_json[i].org_emiss.trim();
			}
		}

		if(control_vetor_end_json[0] === 0){
			alert("Favor inserir ENDEREÇO do Cliente");
			document.getElementById("endereco").focus();
			return 0;
		}

		var enderecoLength = control_vetor_end_json.length;
		adress_aux = adress_aux + '&QEND=' + enderecoLength;
		for(i=0;i<enderecoLength;i++){
			if(control_vetor_end_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&END_'+ i + '=' + control_vetor_end_json[i].endereco.trim();
				adress_aux = adress_aux + '&BAIRRO_'+ i + '=' + control_vetor_end_json[i].bairro.trim();
				adress_aux = adress_aux + '&CIDADE_'+ i + '=' + control_vetor_end_json[i].cidade.trim();
				adress_aux = adress_aux + '&UF_'+ i + '=' + control_vetor_end_json[i].uf.trim();
				adress_aux = adress_aux + '&PAIS_'+ i + '=' + control_vetor_end_json[i].pais.trim();
				adress_aux = adress_aux + '&COMP_'+ i + '=' + control_vetor_end_json[i].complemento.trim();
				adress_aux = adress_aux + '&CEP_'+ i + '=' + control_vetor_end_json[i].cep.trim();
				adress_aux = adress_aux + '&TIPOEND_'+ i + '=' + control_vetor_end_json[i].tipo_end.trim();
			}
		}

		if(control_vetor_contato_json[0] === 0){
			alert("Favor inserir CONTATO do Cliente");
			document.getElementById("ddd").focus();
			return 0;
		}

		var contatoLength = control_vetor_contato_json.length;
		adress_aux = adress_aux + '&QCTO=' + contatoLength;
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_json[i].cod_pais.trim();
				adress_aux = adress_aux + '&NOMECTO_'+ i + '=' + control_vetor_contato_json[i].nome.trim();
				adress_aux = adress_aux + '&PARENCTO_'+ i + '=' + control_vetor_contato_json[i].parentesco.trim();
			}
		}

		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Cliente");
			document.getElementById("email").focus();
			return 0;
		}

		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}
		
		var adress = url_adress + 'services/cliente?OP_CODE=UPDATE&TIPO=' + tipo_pessoa.trim() + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&SITE=' + site.value.trim() + '&DATA_NASCIMENTO=' + dt_nascimento.value + '&COD_VENDEDOR='+ cod_vendedor.trim();
		adress = adress + '&COD_CLIENTE=' + cod_cliente_consulta.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		adress = adress + adress_aux;
		document.location.href = adress;
	}
	else
		return 0;
}

function operacional_cadastrar_chip_function(){
	if(confirm('Deseja realizar o ingresso do Sim Card?')){
		var nfe = document.getElementById("nfe_chip");
		var iccid = document.getElementById("iccid");
		var operadora_chip = $('#operadora_chip :selected').val();
		var tecnologia_chip = document.getElementById("tecnologia_chip");
		var apn_chip = document.getElementById("apn_chip");
		var estado_chip = $('#estado_chip :selected').val();
		var ddd_chip = document.getElementById("ddd_chip");
		var numero_chip = document.getElementById("numero_chip");
		var cod_usuario = document.getElementById("cod_usuario");

		if(nfe.value.trim() === ""){
			alert("Favor inserir a NFe do SIM CARD.");
			document.getElementById("nfe_chip").focus();
			return 0;
		}

		if(iccid.value.trim() === ""){
			alert("Favor inserir o ICC-ID do SIM CARD.");
			document.getElementById("iccid").focus();
			return 0;
		}

		if(tecnologia_chip.value.trim() === ""){
			alert("Favor inserir a TECNOLOGIA do SIM CARD.");
			document.getElementById("tecnologia_chip").focus();
			return 0;
		}

		if(apn_chip.value.trim() === ""){
			alert("Favor inserir a APN do SIM CARD.");
			document.getElementById("apn_chip").focus();
			return 0;
		}

		if(ddd_chip.value.trim() === ""){
			alert("Favor inserir o DDD do SIM CARD.");
			document.getElementById("ddd_chip").focus();
			return 0;
		}

		if(numero_chip.value.trim() === ""){
			alert("Favor inserir o NÚMERO do SIM CARD.");
			document.getElementById("numero_chip").focus();
			return 0;
		}

		adress = url_adress + 'services/chip?OP_CODE=CREATE&NFE=' + nfe.value.trim() + '&ICCID=' + iccid.value.trim() + '&OPERADORA=' + operadora_chip.trim();
		adress = adress + '&TECNOLOGIA=' + tecnologia_chip.value.trim() + '&APN=' + apn_chip.value.trim() + '&ESTADO=' + estado_chip.trim();
		adress = adress + '&DDD=' + ddd_chip.value.trim() + '&NUMERO=' + numero_chip.value.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		document.location.href = adress;
	}
	else
		return 0;
}

function operacional_consultar_chip_salvar_function(){
	alert("Em desenvolvimento!");
	return 0;
	if(confirm('Deseja realizar o ingresso do Sim Card?')){
		var nfe = document.getElementById("nfe_chip");
		var iccid = document.getElementById("iccid");
		var operadora_chip = $('#operadora_chip :selected').val();
		var tecnologia_chip = document.getElementById("tecnologia_chip");
		var apn_chip = document.getElementById("apn_chip");
		var estado_chip = $('#estado_chip :selected').val();
		var ddd_chip = document.getElementById("ddd_chip");
		var numero_chip = document.getElementById("numero_chip");
		var cod_usuario = document.getElementById("cod_usuario");

		if(nfe.value.trim() === ""){
			alert("Favor inserir a NFe do SIM CARD.");
			document.getElementById("nfe_chip").focus();
			return 0;
		}

		if(iccid.value.trim() === ""){
			alert("Favor inserir o ICC-ID do SIM CARD.");
			document.getElementById("iccid").focus();
			return 0;
		}

		if(tecnologia_chip.value.trim() === ""){
			alert("Favor inserir a TECNOLOGIA do SIM CARD.");
			document.getElementById("tecnologia_chip").focus();
			return 0;
		}

		if(apn_chip.value.trim() === ""){
			alert("Favor inserir a APN do SIM CARD.");
			document.getElementById("apn_chip").focus();
			return 0;
		}

		if(ddd_chip.value.trim() === ""){
			alert("Favor inserir o DDD do SIM CARD.");
			document.getElementById("ddd_chip").focus();
			return 0;
		}

		if(numero_chip.value.trim() === ""){
			alert("Favor inserir o NÚMERO do SIM CARD.");
			document.getElementById("numero_chip").focus();
			return 0;
		}

		adress = url_adress + 'services/chip?OP_CODE=UPDATE&NFE=' + nfe.value.trim() + '&ICCID=' + iccid.value.trim() + '&OPERADORA=' + operadora_chip.trim();
		adress = adress + '&TECNOLOGIA=' + tecnologia_chip.value.trim() + '&APN=' + apn_chip.value.trim() + '&ESTADO=' + estado_chip.trim() + '&DDD=' + ddd_chip.value.trim();
		adress = adress + '&NUMERO=' + numero_chip.value.trim() + '&COD_CHIP=' + cod_chip_consulta.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		document.location.href = adress;
	}
	else
		return 0;
}

function administrativo_cadastrar_vendedor_cadastrar_function(){
	ajustar_vetor_doc_function();
	ajustar_vetor_end_function();
	ajustar_vetor_contato_function();
	ajustar_vetor_email_function();
	if(confirm('Deseja realizar o ingresso do Vendedor?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var dt_nascimento = document.getElementById("data_nasc");
		var site = document.getElementById("url_site");
		var cod_usuario = document.getElementById("cod_usuario");

		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Vendedor.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Vendedor.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}
		if(dt_nascimento.value.trim() === ""){
			alert("Favor inserir DATA DE NASCIMENTO do Vendedor");
			document.getElementById("data_nasc").focus();
			return 0;
		}
		if(site.value.trim() === ""){
			site.value = "";
		}

		if(control_vetor_doc_json[0] === 0){
			alert("Favor inserir DOCUMENTO do Vendedor");
			document.getElementById("numero_documento").focus();
			return 0;
		}

		var adress_aux;
		var documentoLength = control_vetor_doc_json.length;
		adress_aux = '&QDOC=' + documentoLength;
		for(var i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPODOC_'+ i + '=' + control_vetor_doc_json[i].tipo_doc.trim();
				adress_aux = adress_aux + '&NUMDOC_'+ i + '=' + control_vetor_doc_json[i].numero.trim();
				adress_aux = adress_aux + '&DTDOC_'+ i + '=' + control_vetor_doc_json[i].dt_emiss.trim();
				adress_aux = adress_aux + '&ORGDOC_'+ i + '=' + control_vetor_doc_json[i].org_emiss.trim();
			}
		}

		if(control_vetor_end_json[0] === 0){
			alert("Favor inserir ENDEREÇO do Vendedor");
			document.getElementById("endereco").focus();
			return 0;
		}

		var enderecoLength = control_vetor_end_json.length;
		adress_aux = adress_aux + '&QEND=' + enderecoLength;
		for(i=0;i<enderecoLength;i++){
			if(control_vetor_end_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&END_'+ i + '=' + control_vetor_end_json[i].endereco.trim();
				adress_aux = adress_aux + '&BAIRRO_'+ i + '=' + control_vetor_end_json[i].bairro.trim();
				adress_aux = adress_aux + '&CIDADE_'+ i + '=' + control_vetor_end_json[i].cidade.trim();
				adress_aux = adress_aux + '&UF_'+ i + '=' + control_vetor_end_json[i].uf.trim();
				adress_aux = adress_aux + '&PAIS_'+ i + '=' + control_vetor_end_json[i].pais.trim();
				adress_aux = adress_aux + '&COMP_'+ i + '=' + control_vetor_end_json[i].complemento.trim();
				adress_aux = adress_aux + '&CEP_'+ i + '=' + control_vetor_end_json[i].cep.trim();
				adress_aux = adress_aux + '&TIPOEND_'+ i + '=' + control_vetor_end_json[i].tipo_end.trim();
			}
		}

		if(control_vetor_contato_json[0] === 0){
			alert("Favor inserir CONTATO do Vendedor");
			document.getElementById("ddd").focus();
			return 0;
		}

		var contatoLength = control_vetor_contato_json.length;
		adress_aux = adress_aux + '&QCTO=' + contatoLength;
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_json[i].cod_pais.trim();
				adress_aux = adress_aux + '&NOMECTO_'+ i + '=' + control_vetor_contato_json[i].nome.trim();
				adress_aux = adress_aux + '&PARENCTO_'+ i + '=' + control_vetor_contato_json[i].parentesco.trim();
			}
		}

		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Vendedor");
			document.getElementById("email").focus();
			return 0;
		}

		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}

		d = new Date();
		var adress = url_adress + 'services/vendedor?OP_CODE=CREATE&TIPO=' + tipo_pessoa.trim() + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&SITE=' + site.value.trim() + '&DATA_NASCIMENTO=' + dt_nascimento.value.trim() + '&DATA_INGRESSO=' + d.yyyymmdd().trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		adress = adress + adress_aux;
		document.location.href = adress;
	}
	else
		return 0;
}

function administrativo_consultar_vendedor_salvar_function(){
	alert("Em desenvolvimento!");
	return 0;
	ajustar_vetor_doc_function();
	ajustar_vetor_end_function();
	ajustar_vetor_contato_function();
	ajustar_vetor_email_function();
	if(confirm('Deseja salvar os dados alterados do Vendedor?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var dt_nascimento = document.getElementById("data_nasc");
		var site = document.getElementById("url_site");
		var cod_usuario = document.getElementById("cod_usuario");

		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Vendedor.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Vendedor.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}
		if(dt_nascimento.value.trim() === ""){
			alert("Favor inserir DATA DE NASCIMENTO do Vendedor");
			document.getElementById("data_nasc").focus();
			return 0;
		}
		if(site.value.trim() === ""){
			site.value = "";
		}

		if(control_vetor_doc_json[0] === 0){
			alert("Favor inserir DOCUMENTO do Vendedor");
			document.getElementById("numero_documento").focus();
			return 0;
		}

		var adress_aux;
		var documentoLength = control_vetor_doc_json.length;
		adress_aux = '&QDOC=' + documentoLength;
		for(var i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPODOC_'+ i + '=' + control_vetor_doc_json[i].tipo_doc.trim();
				adress_aux = adress_aux + '&NUMDOC_'+ i + '=' + control_vetor_doc_json[i].numero.trim();
				adress_aux = adress_aux + '&DTDOC_'+ i + '=' + control_vetor_doc_json[i].dt_emiss.trim();
				adress_aux = adress_aux + '&ORGDOC_'+ i + '=' + control_vetor_doc_json[i].org_emiss.trim();
			}
		}

		if(control_vetor_end_json[0] === 0){
			alert("Favor inserir ENDEREÇO do Vendedor");
			document.getElementById("endereco").focus();
			return 0;
		}

		var enderecoLength = control_vetor_end_json.length;
		adress_aux = adress_aux + '&QEND=' + enderecoLength;
		for(i=0;i<enderecoLength;i++){
			if(control_vetor_end_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&END_'+ i + '=' + control_vetor_end_json[i].endereco.trim();
				adress_aux = adress_aux + '&BAIRRO_'+ i + '=' + control_vetor_end_json[i].bairro.trim();
				adress_aux = adress_aux + '&CIDADE_'+ i + '=' + control_vetor_end_json[i].cidade.trim();
				adress_aux = adress_aux + '&UF_'+ i + '=' + control_vetor_end_json[i].uf.trim();
				adress_aux = adress_aux + '&PAIS_'+ i + '=' + control_vetor_end_json[i].pais.trim();
				adress_aux = adress_aux + '&COMP_'+ i + '=' + control_vetor_end_json[i].complemento.trim();
				adress_aux = adress_aux + '&CEP_'+ i + '=' + control_vetor_end_json[i].cep.trim();
				adress_aux = adress_aux + '&TIPOEND_'+ i + '=' + control_vetor_end_json[i].tipo_end.trim();
			}
		}

		if(control_vetor_contato_json[0] === 0){
			alert("Favor inserir CONTATO do Vendedor");
			document.getElementById("ddd").focus();
			return 0;
		}

		var contatoLength = control_vetor_contato_json.length;
		adress_aux = adress_aux + '&QCTO=' + contatoLength;
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_json[i].cod_pais.trim();
				adress_aux = adress_aux + '&NOMECTO_'+ i + '=' + control_vetor_contato_json[i].nome.trim();
				adress_aux = adress_aux + '&PARENCTO_'+ i + '=' + control_vetor_contato_json[i].parentesco.trim();
			}
		}

		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Vendedor");
			document.getElementById("email").focus();
			return 0;
		}

		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}
		
		var adress = url_adress + 'services/cliente?OP_CODE=UPDATE&TIPO=' + tipo_pessoa.trim() + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&SITE=' + site.value.trim() + '&DATA_NASCIMENTO=' + dt_nascimento.value.trim() + '&COD_VENDEDOR='+ cod_vendedor.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		adress = adress + adress_aux;
		document.location.href = adress;
	}
	else
		return 0;
}

function select_modelo_function(){
	var marca_equip_ = $('#marca_equip').val();
	var marca_equip_val = marca_equip_;
    var content = '';
    if(marca_equip_ != 0){
    	$.ajax({
            url: url_adress + "select_modelo.jsp?COD_MARCA=" + marca_equip_val,
            success: function(result) {
                $('#div_modelo_equip').html(result);
            },
            error: function(e){
                alert('error...');
            }
        });
    }else{
    	content = 'Modelo:<input type="text" id="modelo_equip" class="size_84" disabled="disabled">';
    	$('#div_modelo_equip').html(content);
    }
}

function operacional_cadastrar_equipamento_function(){
	if(confirm('Deseja realizar o ingresso do Equipamento?')){
		var id = document.getElementById("numero_modulo");
		var sn = document.getElementById("sn_modulo");
		var nfe = document.getElementById("numero_nfe");
		var marca = $('#marca_equip :selected').val();
		var modelo = $('#modelo_equip :selected').val();
		var val_datalist_iccid = $('#item_iccid').val();
		var cod_chip_cad_equip = 0;
        if (val_datalist_iccid !== ""){
        	cod_chip_cad_equip = $('#iccid_list option').filter(function() {
                return this.value == val_datalist_iccid;
            }).data('label');
        	if(cod_chip_cad_equip === ""){
    			alert("Necessário selecionar o ICC-ID do Sim Card instalado no Equipamento.");
    			document.getElementById("numero_chip").focus();
    			return 0;
    		}
        }
		var cod_usuario = document.getElementById("cod_usuario");

		if(id.value.trim() === ""){
			alert("Favor inserir o ID do Equipamento.");
			document.getElementById("numero_modulo").focus();
			return 0;
		}

		if(sn.value.trim() === ""){
			alert("Favor inserir o SN do Equipamento.");
			document.getElementById("sn_modulo").focus();
			return 0;
		}

		if(nfe.value.trim() === ""){
			alert("Favor inserir a NFe do Equipamento.");
			document.getElementById("numero_nfe").focus();
			return 0;
		}

		if(marca === ""){
			alert("Necessário selecionar a MARCA do Equipamento.");
			document.getElementById("marca_equip").focus();
			return 0;
		}

		if(modelo === ""){
			alert("Necessário selecionar o MODELO do Equipamento.");
			document.getElementById("modelo_equip").focus();
			return 0;
		}

		adress = url_adress + 'services/equipamento?OP_CODE=CREATE&COD_USUARIO=' + cod_usuario.innerHTML.trim() + '&COD_CHIP=' + cod_chip_cad_equip + '&NUM_MODULO=' + id.value.trim();
		adress = adress + '&SN=' + sn.value.trim() + '&NFE=' + nfe.value.trim() + '&COD_MODELO=' + modelo;
		document.location.href = adress;
	}else
		return 0;
}

function operacional_consultar_equipamento_salvar_function(){
	alert("Em desenvolvimento!");
	return 0;
	if(confirm('Deseja salvar os dados alterados do Equipamento?')){
		var instalado = $("input[type='radio'][name='modulo_instalado']:checked").val();
		var id = document.getElementById("numero_modulo");
		var sn = document.getElementById("sn_modulo");
		var nfe = document.getElementById("numero_nfe");
		var marca = $('#marca_equip :selected').val();
		var modelo = $('#modelo_equip :selected').val();
		var val_datalist_iccid = $('#item_iccid').val();
		var cod_chip_cad_equip = 0;
        if (val_datalist_iccid !== ""){
        	cod_chip_cad_equip = $('#iccid_list option').filter(function() {
                return this.value == val_datalist_iccid;
            }).data('label');
        	if(cod_chip_cad_equip === ""){
    			alert("Necessário selecionar o ICC-ID do Sim Card instalado no Equipamento.");
    			document.getElementById("numero_chip").focus();
    			return 0;
    		}
        }
        var cliente_cad_equip = "";
        if(Number(instalado) === 2){
			var val_datalist_cliente = $('#item_nome_razao').val();
	        if (val_datalist_cliente.trim() !== ""){
	        	cliente_cad_equip = $('#nome_list option').filter(function() {
	                return this.value == val_datalist_cliente;
	            }).data('label');
	        }
	        if(cliente_cad_equip === ""){
	        	alert("Necessário selecionar um cliente para o Equipamento!");
	        	document.getElementById("item_nome_razao").focus();
	        	return 0;
	        }
		}
		var cod_usuario = document.getElementById("cod_usuario");

		if(id.value.trim() === ""){
			alert("Favor inserir o ID do Equipamento.");
			document.getElementById("numero_modulo").focus();
			return 0;
		}

		if(sn.value.trim() === ""){
			alert("Favor inserir o SN do Equipamento.");
			document.getElementById("sn_modulo").focus();
			return 0;
		}

		if(nfe.value.trim() === ""){
			alert("Favor inserir a NFe do Equipamento.");
			document.getElementById("numero_nfe").focus();
			return 0;
		}

		if(marca === ""){
			alert("Necessário selecionar a MARCA do Equipamento.");
			document.getElementById("marca_equip").focus();
			return 0;
		}

		if(modelo === ""){
			alert("Necessário selecionar o MODELO do Equipamento.");
			document.getElementById("modelo_equip").focus();
			return 0;
		}

		adress = url_adress + 'services/equipamento?OP_CODE=CREATE&COD_USUARIO=' + cod_usuario.innerHTML.trim() + '&COD_CHIP=' + cod_chip_cad_equip + '&NUM_MODULO=' + id.value.trim();
		if(Number(instalado) !== 1){
			adress = adress + '&COD_CLIENTE=' + cliente_cad_equip;
		}
		adress = adress + '&SN=' + sn.value.trim() + '&NFE=' + nfe.value.trim() + '&COD_MODELO=' + modelo + '&INSTALADO=' + instalado + '&COD_MODULO=' + cod_modulo_consulta;
		document.location.href = adress;
	}else
		return 0;
}

var clean_contato_prospect;
var control_div_contato_prospect = new Array();
function div_contato_inseridas_prospect (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

function vetor_contato_inserido_prospect(ddd, numero, tipo_contato, cod_pais){
    this.ddd = ddd;
    this.numero = numero;
    this.tipo_contato = tipo_contato;
    this.cod_pais = cod_pais;
}

var control_vetor_contato_prospect = new Array();
control_vetor_contato_prospect[0] = 0;
var control_vetor_contato_prospect_tipo = new Array();

function insert_contatos_prospect_function() {
    clean_contato_prospect = $('#div_contato').html();
    var ddd = document.getElementById("ddd").value.trim();
    var numero = document.getElementById("numero_contato").value.trim();
    var tipo_contato = $('#tipocont_list').val();
    var tipo_contato_nome = $('#tipocont_list :selected').text();
    var cod_pais = document.getElementById("cod_pais").value.trim();
    var insert = "X";

    var tipo_contato_tipo_obj = $('#tipocont_list');
    var tipo_contato_tamanho = tipo_contato_tipo_obj[0].length;
    for (var i=0; i<tipo_contato_tamanho;i++){
        control_vetor_contato_prospect_tipo[i] = document.getElementsByName("option_contato_tipo")[i].text;
    }

    if (ddd === ""){
        alert('Favor inserir o DDD do contato.');
        document.getElementById("ddd").focus();
        return 0;
    }

    if (numero === ""){
        alert('Favor inserir o NÚMERO do contato.');
        document.getElementById("numero_contato").focus();
        return 0;
    }

    if (cod_pais === ""){
        alert('Favor inserir o COD. PAÍS do contato.');
        document.getElementById("cod_pais").focus();
        return 0;
    }

    i = control_div_contato_prospect.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_contato_prospect[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
            control_div_contato_prospect[i] = new div_contato_inseridas_prospect(i,0);
            control_vetor_contato_prospect[i] = new vetor_contato_inserido_prospect(ddd, numero, tipo_contato, cod_pais);
        }
    }
    else{
        control_div_contato_prospect[0] = new div_contato_inseridas_prospect(0,0);
        control_vetor_contato_prospect[0] = new vetor_contato_inserido_prospect(ddd, numero, tipo_contato, cod_pais);
    }

    if (insert === "X"){
        var content_div_contato = $('#contato_inserido').html();
        content_div_contato = content_div_contato + '<div id="contato_' + control_div_contato_prospect[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="contato_inserido" value="contato_' + control_div_contato_prospect[i].cod + '" onclick="change_contato_prospect_button_function()">\n\
                                                  <div id="tipo_contato_' + control_div_contato_prospect[i].cod + '">' + tipo_contato_nome + '</div>: +\n\
                                                  <div id="cod_pais_' + control_div_contato_prospect[i].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_' + control_div_contato_prospect[i].cod + '">' + ddd + '</div>)\n\
                                                  <div id="numero_contato_' + control_div_contato_prospect[i].cod + '">' + numero + '</div> </div>';

        $('#contato_inserido').html(content_div_contato);
    }
    else {
        var content_div_contato = '<input type="radio" name="contato_inserido" value="contato_' + control_div_contato_prospect[j].cod + '" onclick="change_contato_prospect_button_function()">\n\
                                                  <div id="tipo_contato_' + control_div_contato_prospect[j].cod + '">' + tipo_contato_nome + '</div>: +\n\
                                                  <div id="cod_pais_' + control_div_contato_prospect[j].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_' + control_div_contato_prospect[j].cod + '">' + ddd + '</div>)\n\
                                                  <div id="numero_contato_' + control_div_contato_prospect[j].cod + '">' + numero + '</div> <br>';

        $('#contato_' + control_div_contato_prospect[j].cod).html(content_div_contato);
        control_div_contato_prospect[j].excluida = 0;
        control_vetor_contato_prospect[j] = new vetor_contato_inserido_prospect(ddd, numero, tipo_contato, cod_pais);
    }
    limpa_campos_contato_prospect_function();
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_prospect_function()">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);
    document.getElementById("ddd").focus();
}

function delete_contatos_prospect_function() {
    var div_select = $("input[type='radio'][name='contato_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(8),Number(length));

    var tipo_contato = $('#tipo_contato_' + div_deletar).html();
    var cod_pais = $('#cod_pais_' + div_deletar).html();
    var ddd = $('#ddd_contato_' + div_deletar).html();
    var numero = $('#numero_contato_' + div_deletar).html();

    var content_div_contato = $('#' + div_select).html();

    if (confirm('O contato ' + tipo_contato.trim() + ': +' + cod_pais.trim() + '(' + ddd.trim() + ')' + numero.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_contato_prospect[div_deletar].excluida = 1;
        control_vetor_contato_prospect[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_contato);
    }

    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_prospect_function()">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);
    limpa_campos_contato_prospect_function();
}

function change_contato_prospect_button_function(){
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_prospect_function()">Incluir</button>\n\
                          <button type="button" id="editar_contato" onclick="editar_contatos_prospect_function()">Editar</button>\n\
                          <button type="button" id="delete_contato" onclick="delete_contatos_prospect_function()">Excluir</button>';
    $('#div_contato_bt').html(content_div_bt);
}

var div_contato_prospect_editar;

function editar_contatos_prospect_function() {
    var content_div_bt = '<button type="button" id="salvar_contato" onclick="salvar_contatos_prospect_function()">Salvar</button>\n\
                          <button type="button" id="cancelar_contato" onclick="cancelar_contatos_prospect_function()">Cancelar</button>';
    $('#div_contato_bt').html(content_div_bt);

    var div_select = $("input[type='radio'][name='contato_inserido']:checked").val();
    var content_div_contato = $('#' + div_select).html();
    var length = div_select.length;
    div_contato_prospect_editar = div_select.slice(Number(8),Number(length));

    var ddd = control_vetor_contato_prospect[div_contato_prospect_editar].ddd;
    var numero = control_vetor_contato_prospect[div_contato_prospect_editar].numero;
    var tipo_contato = control_vetor_contato_prospect[div_contato_prospect_editar].tipo_contato;
    var cod_pais = control_vetor_contato_prospect[div_contato_prospect_editar].cod_pais;

    $('#' + div_select).html("");
    $('#' + div_select).html(content_div_contato);

    content_div_contato = "";

    content_div_contato = 'DDD:<input type="text" class="size_5" id="ddd" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);" value="' + ddd + '">\n\
                        Número:<input type="text" class="size_19" id="numero_contato" maxlength="10" onkeypress="javascript: return EntradaNumerico(event);" value="' + numero + '">\n\
                        Tipo do Contato:\n\
                        <select id="tipocont_list" class="size_21">';
    for(var i=0;i<control_vetor_contato_prospect_tipo.length;i++){
        content_div_contato = content_div_contato + '<option value="' + Number(i+1) + '" name="option_contato_tipo"';
        if (Number(tipo_contato) === Number(i+1)){
            content_div_contato = content_div_contato + ' selected';
        }
        content_div_contato = content_div_contato + '>' + control_vetor_contato_prospect_tipo[i] + '</option>';
    }
    content_div_contato = content_div_contato + '</select>\n\
                        Cod. País: <input type="text" class="size_5" id="cod_pais" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);" value="' + cod_pais + '">';

    $('#div_contato').html("");
    $('#div_contato').html(content_div_contato);
}

function limpa_campos_contato_prospect_function(){
    var content_div_contato = clean_contato_prospect;
    $('#div_contato').html("");
    $('#div_contato').html(content_div_contato);
}

function cancelar_contatos_prospect_function(){
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_prospect_function()">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);

    limpa_campos_contato_prospect_function();
}

function salvar_contatos_prospect_function(){
    var content_div_bt = '<button type="button" id="incluir_contato" onclick="insert_contatos_prospect_function()">Incluir</button>';
    $('#div_contato_bt').html(content_div_bt);

    var div_salvar = div_contato_prospect_editar;

    $('#contato_' + div_salvar).html("");    

    var ddd = document.getElementById("ddd").value.trim();
    var numero = document.getElementById("numero_contato").value.trim();
    var tipo_contato = $('#tipocont_list').val();
    var tipo_contato_nome = $('#tipocont_list :selected').text();
    var cod_pais = document.getElementById("cod_pais").value.trim();

    if (ddd === ""){
        alert('Favor inserir o DDD do contato.');
        document.getElementById("ddd").focus();
        return 0;
    }

    if (numero === ""){
        alert('Favor inserir o NÚMERO do contato.');
        document.getElementById("numero_contato").focus();
        return 0;
    }

    if (cod_pais === ""){
        alert('Favor inserir o COD. PAÍS do contato.');
        document.getElementById("cod_pais").focus();
        return 0;
    }

    var content_div_contato = '<input type="radio" name="contato_inserido" value="contato_' + control_div_contato_prospect[div_salvar].cod + '" onclick="change_contato_prospect_button_function()">\n\
                                                  <div id="tipo_contato_' + control_div_contato_prospect[div_salvar].cod + '">' + tipo_contato_nome + '</div>: +\n\
                                                  <div id="cod_pais_' + control_div_contato_prospect[div_salvar].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_' + control_div_contato_prospect[div_salvar].cod + '">' + ddd + '</div>)&nbsp;\n\
                                                  <div id="numero_contato_' + control_div_contato_prospect[div_salvar].cod + '">' + numero + '</div> <br>';

    $('#contato_' + control_div_contato_prospect[div_salvar].cod).html(content_div_contato);
    control_div_contato_prospect[div_salvar].excluida = 0;

    control_vetor_contato_prospect[div_salvar].ddd = ddd;
    control_vetor_contato_prospect[div_salvar].numero = numero;
    control_vetor_contato_prospect[div_salvar].tipo_contato = tipo_contato;
    control_vetor_contato_prospect[div_salvar].cod_pais = cod_pais;

    limpa_campos_contato_prospect_function();
}

var control_vetor_contato_prospect_json = new Array();
function ajustar_vetor_contato_prospect_function(){

	var length = control_vetor_contato_prospect.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_contato_prospect_json[x] = control_vetor_contato_prospect[x];

	for(var i=0; i<length; i++){
		if(control_vetor_contato_prospect_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_contato_prospect_json[j] !== 0){
					control_vetor_contato_prospect_json[i] = control_vetor_contato_prospect_json[j];
					control_vetor_contato_prospect_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_contato_prospect_json[i] === 0)
			break;
	}
	control_vetor_contato_prospect_json.length = i;
}

function comercial_cadastrar_cliente_prospect_cadastrar_function(){
	ajustar_vetor_contato_prospect_function();
	ajustar_vetor_email_function();
	if(confirm('Deseja realizar o ingresso do Cliente?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var cod_vendedor = $('#vendedor_list :selected').val();
		var cod_usuario = document.getElementById("cod_usuario");

		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Cliente.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Cliente.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}

		if(control_vetor_contato_prospect_json[0] === 0){
			alert("Favor inserir CONTATO do Cliente");
			document.getElementById("ddd").focus();
			return 0;
		}

		var contatoLength = control_vetor_contato_prospect_json.length;
		var adress_aux = '&QCTO=' + contatoLength;
		for(var i=0;i<contatoLength;i++){
			if(control_vetor_contato_prospect_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_prospect_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_prospect_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_prospect_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_prospect_json[i].cod_pais.trim();
			}
		}

		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Cliente");
			document.getElementById("email").focus();
			return 0;
		}

		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}

		d = new Date();
		var adress = url_adress + 'services/prospect?OP_CODE=CREATE&TIPO=' + tipo_pessoa + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&DATA_INGRESSO=' + d.yyyymmdd() + '&COD_VENDEDOR='+ cod_vendedor.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim() + '&WORK_ID=' + work_id;
		adress = adress + adress_aux;
		document.location.href = adress;
	}
	else
		return 0;
}

function comercial_consultar_cliente_prospect_salvar_function(){
	alert("Em desenvolvimento!");
	return 0;
	ajustar_vetor_contato_prospect_function();
	ajustar_vetor_email_function();
	if(confirm('Deseja salvar os dados alterados do Cliente?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var cod_vendedor = $('#vendedor_list :selected').val();
		var cod_usuario = document.getElementById("cod_usuario");

		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Cliente.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Cliente.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}

		if(control_vetor_contato_prospect_json[0] === 0){
			alert("Favor inserir CONTATO do Cliente");
			document.getElementById("ddd").focus();
			return 0;
		}

		var contatoLength = control_vetor_contato_prospect_json.length;
		var adress_aux= '&QCTO=' + contatoLength;
		for(var i=0;i<contatoLength;i++){
			if(control_vetor_contato_prospect_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_prospect_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_prospect_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_prospect_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_prospect_json[i].cod_pais.trim();
			}
		}

		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Cliente");
			document.getElementById("email").focus();
			return 0;
		}

		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}
		
		var adress = url_adress + 'services/prospect?OP_CODE=UPDATE&TIPO=' + tipo_pessoa + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&COD_VENDEDOR='+ cod_vendedor.trim() + '&COD_CLIENTE_PROSPECT=' + cod_cliente_prospect_consulta.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim();
		adress = adress + adress_aux;
		document.location.href = adress;
	}
	else
		return 0;
}

var contato_prospect_carregado_array = new Array();

function contato_prospect_carregado(ddd, numero, tipo_contato, cod_pais){
    this.ddd = ddd;
    this.numero = numero;
    this.tipo_contato = tipo_contato;
    this.cod_pais = cod_pais;
}

function vetor_contato_prospect_inserido(ddd, numero, tipo_contato, cod_pais){
    this.ddd = ddd;
    this.numero = numero;
    this.tipo_contato = tipo_contato;
    this.cod_pais = cod_pais;
}

function carregar_dados_consulta_cliente_prospect_function(){
    var i;
    var j;
    /*Carregar contatos*/
    var div_contato_inserido_obj = $("#contato_inserido");
    var tamanho_contato = div_contato_inserido_obj[0].childElementCount;
    clean_contato = $('#div_contato').html();
    for(i=0;i<tamanho_contato;i++){
    	contato_prospect_carregado_array[i] = new contato_prospect_carregado();
    	contato_prospect_carregado_array[i].ddd = $('#ddd_contato_oculta_' + i).html().trim();
    	contato_prospect_carregado_array[i].numero = $('#numero_contato_oculta_' + i).html().trim();
    	contato_prospect_carregado_array[i].tipo_contato = $('#tipo_contato_oculta_' + i).html().trim();
    	contato_prospect_carregado_array[i].cod_pais = $('#cod_pais_oculta_' + i).html().trim();
    }
    var tipo_contato_tipo_obj = $('#tipocont_list');
    var tipo_contato_tamanho_tipo = tipo_contato_tipo_obj[0].length;
    for (i=0; i<tipo_contato_tamanho_tipo;i++){
    	control_vetor_contato_prospect_tipo[i] = document.getElementsByName("option_contato_tipo")[i].text;
    }

    for(i=0;i<tamanho_contato;i++){
    	control_div_contato_prospect[i] = new div_contato_inseridas_prospect(0, 0);
    	control_vetor_contato_prospect[i] = new vetor_contato_prospect_inserido();
    	control_vetor_contato_prospect[i].ddd = contato_prospect_carregado_array[i].ddd;
    	control_vetor_contato_prospect[i].numero = contato_prospect_carregado_array[i].numero;
        for(j=0;j<tipo_contato_tamanho_tipo;j++){
            if(control_vetor_contato_prospect_tipo[j] === contato_prospect_carregado_array[i].tipo_contato){
            	control_vetor_contato_prospect[i].tipo_contato = Number(j+1);
                break;
            }
        }
        control_vetor_contato_prospect[i].cod_pais = contato_prospect_carregado_array[i].cod_pais;
    }
    $('#contato_inserido').html("");

    var content_div_contato = "";

    for(i=0;i<tamanho_contato;i++){
    	content_div_contato = content_div_contato + '<div id="contato_' + i + '" class="div_inseridos">\n\
        <input type="radio" name="contato_inserido" value="contato_' + i + '" onclick="change_contato_prospect_button_function()">\n\
        <div id="tipo_contato_' + i + '">' + control_vetor_contato_prospect_tipo[Number(control_vetor_contato_prospect[i].tipo_contato) - 1] + '</div>: +\n\
        <div id="cod_pais_' + i + '">' + control_vetor_contato_prospect[i].cod_pais + '</div>\n\
        (<div id="ddd_contato_' + i + '">' + control_vetor_contato_prospect[i].ddd + '</div>)\n\
        <div id="numero_contato_' + i + '">' + control_vetor_contato_prospect[i].numero + '</div> <br> </div>';
    }

    $('#contato_inserido').html(content_div_contato);

/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar emails*/
    var div_email_inserido_obj = $("#emails_inserido");
    var tamanho_email = div_email_inserido_obj[0].childElementCount;
    clean_email = $('#div_email').html();
    for(i=0;i<tamanho_email;i++){
        email_carregado_array[i] = new email_carregado();
        email_carregado_array[i].email = $('#nome_email_oculta_' + i).html().trim();
    }
    for(i=0;i<tamanho_email;i++){
    	control_div_email[i] = new div_email_inseridas(0, 0);
    	control_vetor_email[i] = new vetor_email_inserido();
        control_vetor_email[i].email = email_carregado_array[i].email;
    }
    $('#emails_inserido').html("");

    var content_div_email = "";

    for(i=0;i<tamanho_email;i++){
    	content_div_email = content_div_email + '<div id="email_' + i + '" class="div_inseridos">\n\
        <input type="radio" name="email_inserido" value="email_' + i + '" onclick="change_email_button_function()">\n\
        <div id="nome_email_' + i + '">' + control_vetor_email[i].email + '</div> <br> </div>';
    }

    $('#emails_inserido').html(content_div_email);
}
/*****************************************************************************************************************************************************/
var clean_contato_procedimento;
var control_div_contato_procedimento = new Array();
function div_contato_procedimento_inseridas (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

function vetor_contato_procedimento_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco){
    this.ddd = ddd;
    this.numero = numero;
    this.tipo_contato = tipo_contato;
    this.cod_pais = cod_pais;
    this.nome = nome;
    this.parentesco = parentesco;
}

var control_vetor_contato_procedimento = new Array();
control_vetor_contato_procedimento[0] = 0;
var control_vetor_contato_procedimento_tipo = new Array();
var control_vetor_contato_procedimento_parentesco = new Array();

function insert_contatos_procedimento_function() {
    clean_contato_procedimento = $('#div_contato_procedimento').html();
    var ddd = document.getElementById("ddd_procedimento").value.trim();
    var numero = document.getElementById("numero_contato_procedimento").value.trim();
    var tipo_contato = $('#tipocont_procedimento_list').val();
    var tipo_contato_nome = $('#tipocont_procedimento_list :selected').text();
    var cod_pais = document.getElementById("cod_pais_procedimento").value.trim();
    var nome = document.getElementById("nome_contato_procedimento").value.trim();
    var parentesco = $('#info_contato_procedimento_list').val();
    var parentesco_nome = $('#info_contato_procedimento_list :selected').text();
    var insert = "X";

    var tipo_contato_tipo_obj = $('#tipocont_procedimento_list');
    var tipo_contato_tamanho = tipo_contato_tipo_obj[0].length;
    for (var i=0; i<tipo_contato_tamanho;i++){
        control_vetor_contato_tipo[i] = document.getElementsByName("option_contato_tipo")[i].text;
    }
    var tipo_contato_parentesco_obj = $('#info_contato_procedimento_list');
    var tipo_contato_tamanho = tipo_contato_parentesco_obj[0].length;
    for (i=0; i<tipo_contato_tamanho;i++){
        control_vetor_contato_parentesco[i] = document.getElementsByName("option_parentesco_cargo")[i].text;
    }

    if (ddd === ""){
        alert('Favor inserir o DDD do contato.');
        document.getElementById("ddd_procedimento").focus();
        return 0;
    }

    if (numero === ""){
        alert('Favor inserir o NÚMERO do contato.');
        document.getElementById("numero_contato_procedimento").focus();
        return 0;
    }

    if (cod_pais === ""){
        alert('Favor inserir o COD. PAÍS do contato.');
        document.getElementById("cod_pais_procedimento").focus();
        return 0;
    }

    if (nome === ""){
        alert('Favor inserir o NOME do contato.');
        document.getElementById("nome_contato_procedimento").focus();
        return 0;
    }

    i = control_div_contato_procedimento.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_contato_procedimento[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
            control_div_contato_procedimento[i] = new div_contato_inseridas(i,0);
            control_vetor_contato_procedimento[i] = new vetor_contato_procedimento_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco);
        }
    }
    else{
        control_div_contato_procedimento[0] = new div_contato_inseridas(0,0);
        control_vetor_contato_procedimento[0] = new vetor_contato_procedimento_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco);
    }

    if (insert === "X"){
        var content_div_contato = $('#contato_procedimento_inserido').html();
        content_div_contato = content_div_contato + '<div id="contato_procedimento_' + control_div_contato_procedimento[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="contato_procedimento_inserido" value="contato_procedimento_' + control_div_contato_procedimento[i].cod + '" onclick="change_contato_procedimento_button_function()">\n\
                                                  <div id="tipo_contato_procedimento_' + control_div_contato_procedimento[i].cod + '">' + tipo_contato_nome + '</div>: \n\
                                                  <div id="nome_contato_procedimento_' + control_div_contato_procedimento[i].cod + '">' + nome + '</div>&nbsp;+\n\
                                                  <div id="cod_pais_procedimento_' + control_div_contato_procedimento[i].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_procedimento_' + control_div_contato_procedimento[i].cod + '">' + ddd + '</div>)\n\
                                                  <div id="numero_contato_procedimento_' + control_div_contato_procedimento[i].cod + '">' + numero + '</div>&nbsp;-\n\
                                                  <div id="grau_contato_procedimento_' + control_div_contato_procedimento[i].cod + '">' + parentesco_nome + '</div> <br> </div>';
        $('#contato_procedimento_inserido').html(content_div_contato);
    }
    else {
        var content_div_contato = '<input type="radio" name="contato_procedimento_inserido" value="contato_procedimento_' + control_div_contato_procedimento[j].cod + '" onclick="change_contato_procedimento_button_function()">\n\
                                                  <div id="tipo_contato_procedimento_' + control_div_contato_procedimento[j].cod + '">' + tipo_contato_nome + '</div>: \n\
                                                  <div id="nome_contato_procedimento_' + control_div_contato_procedimento[j].cod + '">' + nome + '</div>&nbsp;+\n\
                                                  <div id="cod_pais_procedimento_' + control_div_contato_procedimento[j].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_procedimento_' + control_div_contato_procedimento[j].cod + '">' + ddd + '</div>)\n\
                                                  <div id="numero_contato_procedimento_' + control_div_contato_procedimento[j].cod + '">' + numero + '</div>&nbsp;-\n\
                                                  <div id="grau_contato_procedimento_' + control_div_contato_procedimento[j].cod + '">' + parentesco_nome + '</div> <br>';

        $('#contato_procedimento_' + control_div_contato_procedimento[j].cod).html(content_div_contato);
        control_div_contato_procedimento[j].excluida = 0;
        control_vetor_contato_procedimento[j] = new vetor_contato_procedimento_inserido(ddd, numero, tipo_contato, cod_pais, nome, parentesco);
    }
    limpa_campos_contato_procedimento_function();
    var content_div_bt = '<button type="button" id="incluir_contato_procedimento" onclick="insert_contatos_procedimento_function()">Incluir</button>';
    $('#div_contato_procedimento_bt').html(content_div_bt);
    document.getElementById("ddd_procedimento").focus();
}

function delete_contatos_procedimento_function() {
    var div_select = $("input[type='radio'][name='contato_procedimento_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(21),Number(length));
    var tipo_contato = $('#tipo_contato_procedimento_' + div_deletar).html();
    var nome = $('#nome_contato_procedimento_' + div_deletar).html();
    var cod_pais = $('#cod_pais_procedimento_' + div_deletar).html();
    var ddd = $('#ddd_contato_procedimento_' + div_deletar).html();
    var numero = $('#numero_contato_procedimento_' + div_deletar).html();

    var content_div_contato = $('#' + div_select).html();

    if (confirm('O contato ' + tipo_contato.trim() + ': ' + nome.trim() + ' +' + cod_pais.trim() + '(' + ddd.trim() + ')' + numero.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_contato_procedimento[div_deletar].excluida = 1;
        control_vetor_contato_procedimento[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_contato);
    }

    var content_div_bt = '<button type="button" id="incluir_contato_procedimento" onclick="insert_contatos_procedimento_function()">Incluir</button>';
    $('#div_contato_procedimento_bt').html(content_div_bt);
    limpa_campos_contato_procedimento_function();
}

function change_contato_procedimento_button_function(){
    var content_div_bt = '<button type="button" id="incluir_contato_procedimento" onclick="insert_contatos_procedimento_function()">Incluir</button>\n\
                          <button type="button" id="editar_contato_procedimento" onclick="editar_contatos_procedimento_function()">Editar</button>\n\
                          <button type="button" id="delete_contato_procedimento" onclick="delete_contatos_procedimento_function()">Excluir</button>';
    $('#div_contato_procedimento_bt').html(content_div_bt);
}

var div_contato_procedimento_editar;

function editar_contatos_procedimento_function() {
    var content_div_bt = '<button type="button" id="salvar_contato_procedimento" onclick="salvar_contatos_procedimento_function()">Salvar</button>\n\
                          <button type="button" id="cancelar_contato_procedimento" onclick="cancelar_contatos_procedimento_function()">Cancelar</button>';
    $('#div_contato_procedimento_bt').html(content_div_bt);

    var div_select = $("input[type='radio'][name='contato_procedimento_inserido']:checked").val();
    var content_div_contato = $('#' + div_select).html();
    var length = div_select.length;
    div_contato_procedimento_editar = div_select.slice(Number(21),Number(length));

    var ddd = control_vetor_contato_procedimento[div_contato_procedimento_editar].ddd;
    var numero = control_vetor_contato_procedimento[div_contato_procedimento_editar].numero;
    var tipo_contato = control_vetor_contato_procedimento[div_contato_procedimento_editar].tipo_contato;
    var cod_pais = control_vetor_contato_procedimento[div_contato_procedimento_editar].cod_pais;
    var nome = control_vetor_contato_procedimento[div_contato_procedimento_editar].nome;
    var parentesco = control_vetor_contato_procedimento[div_contato_procedimento_editar].parentesco;

    $('#' + div_select).html("");
    $('#' + div_select).html(content_div_contato);

    content_div_contato = "";

    content_div_contato = 'DDD:<input type="text" class="size_5" id="ddd_procedimento" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);" value="' + ddd + '">\n\
                        Número:<input type="text" class="size_19" id="numero_contato_procedimento" maxlength="10" onkeypress="javascript: return EntradaNumerico(event);" value="' + numero + '">\n\
                        Tipo do Contato:\n\
                        <select id="tipocont_procedimento_list" class="size_21">';
    for(var i=0;i<control_vetor_contato_procedimento_tipo.length;i++){
        content_div_contato = content_div_contato + '<option value="' + Number(i+1) + '" name="option_contato_tipo"';
        if (Number(tipo_contato) === Number(i+1)){
            content_div_contato = content_div_contato + ' selected';
        }
        content_div_contato = content_div_contato + '>' + control_vetor_contato_procedimento_tipo[i] + '</option>';
    }
    content_div_contato = content_div_contato + '</select>\n\
                        Cod. País: <input type="text" class="size_5" id="cod_pais_procedimento" maxlength="3" onkeypress="javascript: return EntradaNumerico(event);" value="' + cod_pais + '">\n\
                        <br>Nome:<input type="text" class="size_32" id="nome_contato_procedimento" value="' + nome + '">\n\
                        Parentesco / Cargo:\n\
                        <select id="info_contato_procedimento_list" class="size_35">';
    for(i=0;i<control_vetor_contato_procedimento_parentesco.length;i++){
        content_div_contato = content_div_contato + '<option value="' + Number(i+1) + '" name="option_parentesco_cargo"';
        if (Number(parentesco) === Number(i+1)){
            content_div_contato = content_div_contato + ' selected';
        }
        content_div_contato = content_div_contato + '>' + control_vetor_contato_procedimento_parentesco[i] + '</option>';
    }
    content_div_contato = content_div_contato + '</select>';

    $('#div_contato_procedimento').html("");
    $('#div_contato_procedimento').html(content_div_contato);
}

function limpa_campos_contato_procedimento_function(){
    var content_div_contato = clean_contato_procedimento;
    $('#div_contato_procedimento').html("");
    $('#div_contato_procedimento').html(content_div_contato);
}

function cancelar_contatos_procedimento_function(){
    var content_div_bt = '<button type="button" id="incluir_contato_procedimento" onclick="insert_contatos_procedimento_function()">Incluir</button>';
    $('#div_contato_procedimento_bt').html(content_div_bt);

    limpa_campos_contato_procedimento_function();
}

function salvar_contatos_procedimento_function(){
    var content_div_bt = '<button type="button" id="incluir_contato_procedimento" onclick="insert_contatos_procedimento_function()">Incluir</button>';
    $('#div_contato_procedimento_bt').html(content_div_bt);

    var div_salvar = div_contato_procedimento_editar;

    $('#contato_procedimento_' + div_salvar).html("");
    var ddd = document.getElementById("ddd_procedimento").value.trim();
    var numero = document.getElementById("numero_contato_procedimento").value.trim();
    var tipo_contato = $('#tipocont_procedimento_list').val();
    var tipo_contato_nome = $('#tipocont_procedimento_list :selected').text();
    var cod_pais = document.getElementById("cod_pais_procedimento").value.trim();
    var nome = document.getElementById("nome_contato_procedimento").value.trim();
    var parentesco = $('#info_contato_procedimento_list').val();
    var parentesco_nome = $('#info_contato_procedimento_list :selected').text();

    if (ddd === ""){
        alert('Favor inserir o DDD do contato.');
        document.getElementById("ddd_procedimento").focus();
        return 0;
    }

    if (numero === ""){
        alert('Favor inserir o NÚMERO do contato.');
        document.getElementById("numero_contato_procedimento").focus();
        return 0;
    }

    if (cod_pais === ""){
        alert('Favor inserir o COD. PAÍS do contato.');
        document.getElementById("cod_pais_procedimento").focus();
        return 0;
    }

    if (nome === ""){
        alert('Favor inserir o NOME do contato.');
        document.getElementById("nome_contato_procedimento").focus();
        return 0;
    }

    var content_div_contato = '<input type="radio" name="contato_procedimento_inserido" value="contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '" onclick="change_contato_procedimento_button_function()">\n\
                                                  <div id="tipo_contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '">' + tipo_contato_nome + '</div>: \n\
                                                  <div id="nome_contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '">' + nome + '</div>&nbsp;+\n\
                                                  <div id="cod_pais_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '">' + cod_pais + '</div>\n\
                                                  (<div id="ddd_contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '">' + ddd + '</div>)&nbsp;\n\
                                                  <div id="numero_contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '">' + numero + '</div>&nbsp;-\n\
                                                  <div id="grau_contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod + '">' + parentesco_nome + '</div> <br>';

    $('#contato_procedimento_' + control_div_contato_procedimento[div_salvar].cod).html(content_div_contato);
    control_div_contato_procedimento[div_salvar].excluida = 0;

    control_vetor_contato_procedimento[div_salvar].ddd = ddd;
    control_vetor_contato_procedimento[div_salvar].numero = numero;
    control_vetor_contato_procedimento[div_salvar].tipo_contato = tipo_contato;
    control_vetor_contato_procedimento[div_salvar].cod_pais = cod_pais;
    control_vetor_contato_procedimento[div_salvar].nome = nome;
    control_vetor_contato_procedimento[div_salvar].parentesco = parentesco;

    limpa_campos_contato_procedimento_function();
}

var control_vetor_contato_procedimento_json = new Array();
function ajustar_vetor_contato_procedimento_function(){

	control_vetor_contato_procedimento_json.length = 0;
	var length = control_vetor_contato_procedimento.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_contato_procedimento_json[x] = control_vetor_contato_procedimento[x];

	for(var i=0; i<length; i++){
		if(control_vetor_contato_procedimento_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_contato_procedimento_json[j] !== 0){
					control_vetor_contato_procedimento_json[i] = control_vetor_contato_procedimento_json[j];
					control_vetor_contato_procedimento_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_contato_procedimento_json[i] === 0)
			break;
	}
	control_vetor_contato_procedimento_json.length = i;
}


function vetor_unidade_inserido(contato, senha, tipo_unidade, unidade){
    this.contato = contato;
    this.senha = senha;
    this.tipo_unidade = tipo_unidade;
    this.unidade = unidade;
}

function vetor_tipo_unidade_veiculo(placa, chassi, renavan, ano_fab, ano_mod, marca, modelo, cor, combustivel, voltagem, km, data_vistoria){
	this.placa = placa;
	this.chassi = chassi;
	this.renavan = renavan;
	this.ano_fab = ano_fab;
	this.ano_mod = ano_mod;
	this.marca = marca;
	this.modelo = modelo;
	this.cor = cor;
	this.combustivel = combustivel;
	this.voltagem = voltagem;
	this.km = km;
	this.data_vistoria = data_vistoria;
}

var clean_unidade_inserido;
var control_div_unidade_inserido = new Array();
function div_unidade_inserido (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

var control_vetor_unidade_inserido = new Array();
control_vetor_unidade_inserido[0] = 0;

var control_vetor_unidade = new vetor_tipo_unidade_veiculo();

function insert_unidades_function(){
	clean_unidade_inserido = $('#unidade_procedimento').html();
    ajustar_vetor_contato_procedimento_function();
    if(control_vetor_contato_procedimento_json.length < 1){
    	alert("Favor inserir um CONTATO para o procedimento.");
        document.getElementById("ddd_procedimento").focus();
    	return 0;
    }
    var senha = document.getElementById("senha_atendimento_procedimento").value.trim();
    if (senha === ""){
        alert('Favor inserir a SENHA DE ATENDIMENTO para o procedimento.');
        document.getElementById("senha_atendimento_procedimento").focus();
        return 0;
    }
    var tipo_unidade = $('#tipo_unidade_list').val();
    if(tipo_unidade == 0){
    	alert("Favor selecionar o TIPO DE UNIDADE.");
    	return 0;
    }
    var tipo_unidade_nome = $('#tipo_unidade_list :selected').text();
    var insert = "X";

    switch(Number(tipo_unidade)) {
    case 2: //Veículo
    	var placa = document.getElementById("placa").value.trim();
    	if(placa === ""){
            alert('Favor inserir a PLACA do veículo.');
            document.getElementById("placa").focus();
            return 0;
    	}
    	var chassi = document.getElementById("chassi").value.trim();
    	if(chassi === ""){
            alert('Favor inserir o CHASSI do veículo.');
            document.getElementById("chassi").focus();
            return 0;
    	}
    	var renavan = document.getElementById("renavan").value.trim();
    	if(renavan === ""){
            alert('Favor inserir o RENAVAN do veículo.');
            document.getElementById("renavan").focus();
            return 0;
    	}
    	var ano_fab = document.getElementById("ano_fab").value;
    	if(ano_fab === ""){
            alert('Favor inserir o ANO DE FABRICAÇÃO do veículo.');
            document.getElementById("ano_fab").focus();
            return 0;
    	}
    	var ano_mod = document.getElementById("ano_mod").value;
    	if(ano_mod === ""){
            alert('Favor inserir o ANO DO MODELO do veículo.');
            document.getElementById("ano_mod").focus();
            return 0;
    	}
    	var marca = $('#marca_list').val();
        var marca_nome = $('#marca_list :selected').text();
    	var modelo = document.getElementById("modelo").value.trim();
    	if(modelo === ""){
            alert('Favor inserir o MODELO do veículo.');
            document.getElementById("modelo").focus();
            return 0;
    	}
    	var cor = document.getElementById("cor").value.trim();
    	if(cor === ""){
            alert('Favor inserir a COR do veículo.');
            document.getElementById("cor").focus();
            return 0;
    	}
    	var combustivel = $('#combustivel_list').val();
    	var voltagem = document.getElementById("voltagem").value.trim();
    	if(voltagem === ""){
            alert('Favor inserir a VOLTAGEM do veículo.');
            document.getElementById("voltagem").focus();
            return 0;
    	}
    	var km = document.getElementById("km").value.trim();
    	if(km === ""){
            alert('Favor inserir o KM do veículo.');
            document.getElementById("km").focus();
            return 0;
    	}
    	var data_vistoria = document.getElementById("data_vist").value.trim();
    	if(data_vistoria === ""){
            alert('Favor inserir a DATA DA ÚLTIMA VISTORIA do veículo.');
            document.getElementById("data_vist").focus();
            return 0;
    	}
    	control_vetor_unidade.placa = placa.toUpperCase();
    	control_vetor_unidade.chassi = chassi.toUpperCase();
    	control_vetor_unidade.renavan = renavan.toUpperCase();
    	control_vetor_unidade.ano_fab = ano_fab;
    	control_vetor_unidade.ano_mod = ano_mod;
    	control_vetor_unidade.marca = marca;
    	control_vetor_unidade.modelo = modelo.toUpperCase();
    	control_vetor_unidade.cor = cor.toUpperCase();
    	control_vetor_unidade.combustivel = combustivel;
    	control_vetor_unidade.voltagem = voltagem;
    	control_vetor_unidade.km = km;
    	control_vetor_unidade.data_vistoria = data_vistoria;
    	break;
	default:
		//add outros cases conforme for tendo outro tipo de unidade
		break;
    }

    var i = control_div_unidade_inserido.length;
    var j = 0;
    var arrayContatoProcedimento = new Array();
    for(var p=0;p<control_vetor_contato_procedimento_json.length;p++){
    	arrayContatoProcedimento[p] = new vetor_contato_procedimento_inserido(control_vetor_contato_procedimento_json[p].ddd,control_vetor_contato_procedimento_json[p].numero,control_vetor_contato_procedimento_json[p].tipo_contato,control_vetor_contato_procedimento_json[p].cod_pais,control_vetor_contato_procedimento_json[p].nome,control_vetor_contato_procedimento_json[p].parentesco);
    }

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_unidade_inserido[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
        	control_div_unidade_inserido[i] = new div_unidade_inserido(i,0);
        	control_vetor_unidade_inserido[i] = new vetor_unidade_inserido(arrayContatoProcedimento, senha, tipo_unidade, new vetor_tipo_unidade_veiculo(control_vetor_unidade.placa,control_vetor_unidade.chassi,control_vetor_unidade.renavan,control_vetor_unidade.ano_fab,control_vetor_unidade.ano_mod,control_vetor_unidade.marca,control_vetor_unidade.modelo,control_vetor_unidade.cor,control_vetor_unidade.combustivel,control_vetor_unidade.voltagem,control_vetor_unidade.km,control_vetor_unidade.data_vistoria));
        }
    }
    else{
    	control_div_unidade_inserido[0] = new div_unidade_inserido(0,0);
    	control_vetor_unidade_inserido[0] = new vetor_unidade_inserido(arrayContatoProcedimento, senha, tipo_unidade,  new vetor_tipo_unidade_veiculo(control_vetor_unidade.placa,control_vetor_unidade.chassi,control_vetor_unidade.renavan,control_vetor_unidade.ano_fab,control_vetor_unidade.ano_mod,control_vetor_unidade.marca,control_vetor_unidade.modelo,control_vetor_unidade.cor,control_vetor_unidade.combustivel,control_vetor_unidade.voltagem,control_vetor_unidade.km,control_vetor_unidade.data_vistoria));
    }

    var content_div_unidade = "";
    if (insert === "X"){
        content_div_unidade = $('#unidades_inseridas').html();
        content_div_unidade = content_div_unidade + '<div id="unidade_' + control_div_unidade_inserido[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="unidade_inserido" value="unidade_' + control_div_unidade_inserido[i].cod + '" onclick="change_unidade_button_function()">\n\
                                                  <div id="tipo_unidade_nome_' + control_div_unidade_inserido[i].cod + '">' + tipo_unidade_nome + '</div>';
        switch(Number(tipo_unidade)) {
        case 2: //Veículo
        	content_div_unidade = content_div_unidade + ' <div id="marca_nome_' + control_div_unidade_inserido[i].cod + '">' + marca_nome.toUpperCase() + '</div>\n\
        								- <div id="placa_veiculo_' + control_div_unidade_inserido[i].cod + '">' + control_vetor_unidade.placa.toUpperCase() + '</div>';
        }
        content_div_unidade = content_div_unidade + '<br> </div>';

        $('#unidades_inseridas').html(content_div_unidade);
    }else{
        content_div_unidade = '<input type="radio" name="unidade_inserido" value="unidade_' + control_div_unidade_inserido[j].cod + '" onclick="change_unidade_button_function()">\n\
        							<div id="tipo_unidade_nome_' + control_div_unidade_inserido[j].cod + '">' + tipo_unidade_nome + '</div>';
        switch(Number(tipo_unidade)) {
        case 2: //Veículo
        	content_div_unidade = content_div_unidade + ' <div id="marca_nome_' + control_div_unidade_inserido[j].cod + '">' + marca_nome.toUpperCase() + '</div>\n\
        								- <div id="placa_veiculo_' + control_div_unidade_inserido[j].cod + '">' + control_vetor_unidade.placa.toUpperCase() + '</div>';
        }
        content_div_unidade = content_div_unidade + '<br>';
        $('#unidade_' + control_div_unidade_inserido[j].cod).html(content_div_unidade);
        control_div_unidade_inserido[j].excluida = 0;
        control_vetor_unidade_inserido[j] = new vetor_unidade_inserido(arrayContatoProcedimento, senha, tipo_unidade,  new vetor_tipo_unidade_veiculo(control_vetor_unidade.placa,control_vetor_unidade.chassi,control_vetor_unidade.renavan,control_vetor_unidade.ano_fab,control_vetor_unidade.ano_mod,control_vetor_unidade.marca,control_vetor_unidade.modelo,control_vetor_unidade.cor,control_vetor_unidade.combustivel,control_vetor_unidade.voltagem,control_vetor_unidade.km,control_vetor_unidade.data_vistoria));
    }
    limpa_campos_unidade_function();
    var content_div_bt = '<button type="button" id="incluir_unidade" onclick="insert_unidades_function()">Incluir</button>';
    $('#div_unidade_bt').html(content_div_bt);
    $('#tipo_unidade').html('');
    document.getElementById("ddd_procedimento").focus();
}

function change_unidade_button_function(){
    var content_div_bt = '<button type="button" id="incluir_unidade" onclick="insert_unidades_function()">Incluir</button>\n\
    						<button type="button" id="delete_unidade" onclick="delete_unidades_function()">Excluir</button>';
    $('#div_unidade_bt').html(content_div_bt);
}

function limpa_campos_unidade_function(){
    var content_div_unidade = clean_unidade_inserido;
    $('#unidade_procedimento').html("");
    $('#unidade_procedimento').html(content_div_unidade);
}

function delete_unidades_function() {
    var div_select = $("input[type='radio'][name='unidade_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(8),Number(length));

    var tipo_unidade = control_vetor_unidade_inserido[div_deletar].tipo_unidade;
    var content_div_unidade = $('#' + div_select).html();
    
    switch(Number(tipo_unidade)) {
    case 2: //Veículo
    	var placa = $('#placa_veiculo_' + div_deletar).html();
    	var marca = $('#marca_nome_' + div_deletar).html();
    	if (confirm('O Veículo ' + marca + ' placa: ' + placa.trim() + ' será apagado.')) {
            $('#' + div_select).html("");
            control_div_unidade_inserido[div_deletar].excluida = 1;
            control_vetor_unidade_inserido[div_deletar] = 0;
        } else {
            $('#' + div_select).html(content_div_unidade);
        }
    }

    var content_div_bt = '<button type="button" id="incluir_unidade" onclick="insert_unidades_function()">Incluir</button>';
    $('#div_unidade_bt').html(content_div_bt);
    limpa_campos_unidade_function();
}

function valor_total_function(val){
	var content;
	if(val == "equip_acessorio"){
		var quantidade = Number(document.getElementById("quant_equip_acess").value.trim());
		var valor_unitario = Number(document.getElementById("valor_unit_equip_acess").value.trim());
		content = '<input class="size_100" type="text" id="valor_total_equip_acess" disabled="disabled" value="' + Number(quantidade * valor_unitario).toFixed(2) + '">';
		$('#valor_total_equip_acessorio').html(content);
	}else if(val == "servico"){
		var quantidade = Number(document.getElementById("quant_serv_monit").value.trim());
		var valor_unitario = Number(document.getElementById("valor_assin_serv_monit").value.trim());
		content = '<input class="size_100" type="text" id="valor_total_serv_monit" disabled="disabled" value="' + Number(quantidade * valor_unitario).toFixed(2) + '">';
		$('#valor_total_servico').html(content);
	}else if(val == "zerar_equip"){
		content = '<input class="size_100" type="text" id="valor_total_equip_acess" disabled="disabled" placeholder="0.00">';
		$('#valor_total_equip_acessorio').html(content);
	}else if(val == "zerar_servico"){
		content = '<input class="size_100" type="text" id="valor_total_serv_monit" disabled="disabled" placeholder="0.00">';
		$('#valor_total_servico').html(content);
	}
}

var clean_equip_acessorio_inserido;
var control_div_equip_acessorio_inserido = new Array();
function div_equip_acessorio_inserido (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

var control_vetor_equip_acessorio_inserido = new Array();
control_vetor_equip_acessorio_inserido[0] = 0;

function vetor_equip_acessorio_inserido(equip_acessorio, quantidade, valor_unitario){
    this.equip_acessorio = equip_acessorio;
    this.quantidade = quantidade;
    this.valor_unitario = valor_unitario;
}

function insert_equip_acessorio_function(){
	clean_equip_acessorio_inserido = $('#div_equipamentos_acessorios').html();

	var equip_acessorio = $('#equip_acess_list').val();
    var equip_acessorio_nome = $('#equip_acess_list :selected').text();

    var quantidade = document.getElementById("quant_equip_acess").value.trim();
    if (quantidade === ""){
        alert('Favor inserir a QUANTIDADE para o item: ' + equip_acessorio_nome + '.');
        document.getElementById("quant_equip_acess").focus();
        return 0;
    }

    var valor_unit = document.getElementById("valor_unit_equip_acess").value.trim();
    if (valor_unit === ""){
        alert('Favor inserir o VALOR UNITÁRIO para o item: ' + equip_acessorio_nome + '.');
        document.getElementById("valor_unit_equip_acess").focus();
        return 0;
    }
    var insert = "X";

    var i = control_div_equip_acessorio_inserido.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_equip_acessorio_inserido[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
        	control_div_equip_acessorio_inserido[i] = new div_unidade_inserido(i,0);
        	control_vetor_equip_acessorio_inserido[i] = new vetor_equip_acessorio_inserido(equip_acessorio, quantidade, valor_unit);
        }
    }
    else{
    	control_div_equip_acessorio_inserido[0] = new div_unidade_inserido(0,0);
    	control_vetor_equip_acessorio_inserido[0] = new vetor_equip_acessorio_inserido(equip_acessorio, quantidade, valor_unit);
    }

    var content_div_equip_acessorio = "";
    if (insert === "X"){
    	content_div_equip_acessorio = $('#equip_acessorio_inserido').html();
        content_div_equip_acessorio = content_div_equip_acessorio + '<div id="equip_acessorio_' + control_div_equip_acessorio_inserido[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="equip_acessorio_inserido" value="equip_acessorio_' + control_div_equip_acessorio_inserido[i].cod + '" onclick="change_equip_acessorio_button_function()">\n\
                                                  <div id="equip_acessorio_nome_' + control_div_equip_acessorio_inserido[i].cod + '">' + equip_acessorio_nome + '</div> - \n\
                                                  <div id="quantidade_equip_acessirio_' + control_div_equip_acessorio_inserido[i].cod + '">' + quantidade + '</div>\n\
                                                  <div id="total_equip_acessirio_' + control_div_equip_acessorio_inserido[i].cod + '"> Total: R$'+ Number(quantidade * valor_unit).toFixed(2) +'</div>\n\
                                                  <br> </div>';

        $('#equip_acessorio_inserido').html(content_div_equip_acessorio);
    }else{
    	content_div_equip_acessorio = '<input type="radio" name="equip_acessorio_inserido" value="equip_acessorio_' + control_div_equip_acessorio_inserido[j].cod + '" onclick="change_equip_acessorio_button_function()">\n\
        							<div id="equip_acessorio_nome_' + control_div_equip_acessorio_inserido[j].cod + '">' + equip_acessorio_nome + '</div> - \n\
                                    <div id="quantidade_equip_acessirio_' + control_div_equip_acessorio_inserido[i].cod + '">' + quantidade + '</div>\n\
                                    <div id="total_equip_acessirio_' + control_div_equip_acessorio_inserido[j].cod + '"> Total: R$'+ Number(quantidade * valor_unit).toFixed(2) +'</div>\n\
        							<br>';

        $('#equip_acessorio_' + control_div_equip_acessorio_inserido[j].cod).html(content_div_equip_acessorio);
        control_div_equip_acessorio_inserido[j].excluida = 0;
        control_vetor_equip_acessorio_inserido[j] = new vetor_equip_acessorio_inserido(equip_acessorio, quantidade, valor_unit);
    }

    limpa_campos_equip_acessorio_function();
    var content_div_bt = '<button type="button" id="incluir_equip_acessorio" onclick="insert_equip_acessorio_function()">Incluir</button>';
    $('#div_equip_acessorio_bt').html(content_div_bt);
    document.getElementById("quant_equip_acess").focus();
}

function limpa_campos_equip_acessorio_function(){
    var content_div_equip_acessorio = clean_equip_acessorio_inserido;
    $('#div_equipamentos_acessorios').html("");
    $('#div_equipamentos_acessorios').html(content_div_equip_acessorio);
    valor_total_function("zerar_equip");
}

function change_equip_acessorio_button_function(){
    var content_div_bt = '<button type="button" id="incluir_equip_acessorio" onclick="insert_equip_acessorio_function()">Incluir</button>\n\
    						<button type="button" id="delete_equip_acessorio" onclick="delete_equip_acessorio_function()">Excluir</button>';
    $('#div_equip_acessorio_bt').html(content_div_bt);
}

function delete_equip_acessorio_function() {
    var div_select = $("input[type='radio'][name='equip_acessorio_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(16),Number(length));

    var equip_acessorio_nome = $('#equip_acessorio_nome_' + div_deletar).html();
    var quantidade = control_vetor_equip_acessorio_inserido[div_deletar].quantidade;
    
    var content_div_equip_acessorio = $('#' + div_select).html();

	if (confirm('O item ' + equip_acessorio_nome.trim() + ' quantidade: ' + quantidade.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_equip_acessorio_inserido[div_deletar].excluida = 1;
        control_vetor_equip_acessorio_inserido[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_equip_acessorio);
    }

    var content_div_bt = '<button type="button" id="incluir_equip_acessorio" onclick="insert_equip_acessorio_function()">Incluir</button>';
    $('#div_equip_acessorio_bt').html(content_div_bt);
    limpa_campos_equip_acessorio_function();
}

var clean_servico_inserido;
var control_div_servico_inserido = new Array();
function div_servico_inserido (cod, excluida){
    this.cod = cod;
    this.excluida = excluida;
}

var control_vetor_servico_inserido = new Array();
control_vetor_servico_inserido[0] = 0;

function vetor_servico_inserido(servico, quantidade, valor_unitario){
    this.servico = servico;
    this.quantidade = quantidade;
    this.valor_unitario = valor_unitario;
}

function insert_servico_function(){
	clean_servico_inserido = $('#div_servico').html();

	var servico = $('#serv_monit_list').val();
    var servico_nome = $('#serv_monit_list :selected').text();

    var quantidade = document.getElementById("quant_serv_monit").value.trim();
    if (quantidade === ""){
        alert('Favor inserir a QUANTIDADE para o serviço: ' + servico_nome + '.');
        document.getElementById("quant_serv_monit").focus();
        return 0;
    }

    var valor_unit = document.getElementById("valor_assin_serv_monit").value.trim();
    if (valor_unit === ""){
        alert('Favor inserir o VALOR UNITÁRIO para o serviço: ' + servico_nome + '.');
        document.getElementById("valor_assin_serv_monit").focus();
        return 0;
    }
    var insert = "X";

    var i = control_div_servico_inserido.length;
    var j = 0;

    if (i !== 0){
        while (j < i && insert === "X"){
            if (control_div_servico_inserido[j].excluida === 1){
                insert = j;
            }
            else{
                j++;
            }
        }
        if (insert === "X"){
        	control_div_servico_inserido[i] = new div_servico_inserido(i,0);
        	control_vetor_servico_inserido[i] = new vetor_servico_inserido(servico, quantidade, valor_unit);
        }
    }
    else{
    	control_div_servico_inserido[0] = new div_servico_inserido(0,0);
    	control_vetor_servico_inserido[0] = new vetor_servico_inserido(servico, quantidade, valor_unit);
    }

    var content_div_servico = "";
    if (insert === "X"){
    	content_div_servico = $('#serv_monit_inserido').html();
        content_div_servico = content_div_servico + '<div id="servico_' + control_div_servico_inserido[i].cod + '" class="div_inseridos">\n\
                                                  <input type="radio" name="servico_inserido" value="servico_' + control_div_servico_inserido[i].cod + '" onclick="change_servico_button_function()">\n\
                                                  <div id="servico_nome_' + control_div_servico_inserido[i].cod + '">' + servico_nome + '</div> - \n\
                                                  <div id="quantidade_equip_acessirio_' + control_div_servico_inserido[i].cod + '">' + quantidade + '</div>\n\
                                                  <div id="total_equip_acessirio_' + control_div_servico_inserido[i].cod + '"> Total: R$'+ Number(quantidade * valor_unit).toFixed(2) +'</div>\n\
                                                  <br> </div>';

        $('#serv_monit_inserido').html(content_div_servico);
    }else{
    	content_div_servico = '<input type="radio" name="servico_inserido" value="servico_' + control_div_servico_inserido[j].cod + '" onclick="change_servico_button_function()">\n\
        							<div id="servico_nome_' + control_div_servico_inserido[j].cod + '">' + servico_nome + '</div> - \n\
                                    <div id="quantidade_equip_acessirio_' + control_div_servico_inserido[i].cod + '">' + quantidade + '</div>\n\
                                    <div id="total_equip_acessirio_' + control_div_servico_inserido[j].cod + '"> Total: R$'+ Number(quantidade * valor_unit).toFixed(2) +'</div>\n\
        							<br>';

        $('#servico_' + control_div_servico_inserido[j].cod).html(content_div_servico);
        control_div_servico_inserido[j].excluida = 0;
        control_vetor_servico_inserido[j] = new vetor_servico_inserido(servico, quantidade, valor_unit);
    }

    limpa_campos_servico_function();
    var content_div_bt = '<button type="button" id="incluir_servico" onclick="insert_servico_function()">Incluir</button>';
    $('#div_servico_bt').html(content_div_bt);
    document.getElementById("quant_serv_monit").focus();
}

function limpa_campos_servico_function(){
    var content_div_servico = clean_servico_inserido;
    $('#div_servico').html("");
    $('#div_servico').html(content_div_servico);
    valor_total_function("zerar_servico");
}

function change_servico_button_function(){
    var content_div_bt = '<button type="button" id="incluir_servico" onclick="insert_servico_function()">Incluir</button>\n\
    						<button type="button" id="delete_servico" onclick="delete_servico_function()">Excluir</button>';
    $('#div_servico_bt').html(content_div_bt);
}

function delete_servico_function() {
    var div_select = $("input[type='radio'][name='servico_inserido']:checked").val();
    var length = div_select.length;
    var div_deletar = div_select.slice(Number(8),Number(length));

    var servico_nome = $('#servico_nome_' + div_deletar).html();
    var quantidade = vetor_servico_inserido[div_deletar].quantidade;
    
    var content_div_servico = $('#' + div_select).html();

	if (confirm('O item ' + servico_nome.trim() + ' quantidade: ' + quantidade.trim() + ' será apagado.')) {
        $('#' + div_select).html("");
        control_div_servico_inserido[div_deletar].excluida = 1;
        control_vetor_servico_inserido[div_deletar] = 0;
    } else {
        $('#' + div_select).html(content_div_servico);
    }

    var content_div_bt = '<button type="button" id="incluir_servico" onclick="insert_servico_function()">Incluir</button>';
    $('#div_servico_bt').html(content_div_bt);
    limpa_campos_servico_function();
}

var control_vetor_equip_acessorio_json = new Array();
function ajustar_vetor_equip_acessorio_inserido(){
	var length = control_vetor_equip_acessorio_inserido.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_equip_acessorio_json[x] = control_vetor_equip_acessorio_inserido[x];

	for(var i=0; i<length; i++){
		if(control_vetor_equip_acessorio_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_equip_acessorio_json[j] !== 0){
					control_vetor_equip_acessorio_json[i] = control_vetor_equip_acessorio_json[j];
					control_vetor_equip_acessorio_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_equip_acessorio_json[i] === 0)
			break;
	}
	control_vetor_equip_acessorio_json.length = i;
}

var control_vetor_servico_json = new Array();
function ajustar_vetor_servico_inserido(){
	var length = control_vetor_servico_inserido.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_servico_json[x] = control_vetor_servico_inserido[x];

	for(var i=0; i<length; i++){
		if(control_vetor_servico_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_servico_json[j] !== 0){
					control_vetor_servico_json[i] = control_vetor_servico_json[j];
					control_vetor_servico_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_servico_json[i] === 0)
			break;
	}
	control_vetor_servico_json.length = i;
}

var control_vetor_unidade_json = new Array();
function ajustar_vetor_unidade_inserido(){
	var length = control_vetor_unidade_inserido.length;
	var flag;
	var j;
	for(var x=0; x<length; x++)
		control_vetor_unidade_json[x] = control_vetor_unidade_inserido[x];

	for(var i=0; i<length; i++){
		if(control_vetor_unidade_json[i] === 0){
			flag = true;
			j = i + 1;
			while(flag && j<length){
				if(control_vetor_unidade_json[j] !== 0){
					control_vetor_unidade_json[i] = control_vetor_unidade_json[j];
					control_vetor_unidade_json[j] = 0;
					flag = false;
				}
				j++;
			}
		}
	}
	for(i=0;i<length;i++){
		if(control_vetor_unidade_json[i] === 0)
			break;
	}
	control_vetor_unidade_json.length = i;
}

function comercial_cadastrar_novo_pedido_function(){
	ajustar_vetor_doc_function();
	ajustar_vetor_end_function();
	ajustar_vetor_contato_function();
	ajustar_vetor_email_function();
	ajustar_vetor_unidade_inserido();
	ajustar_vetor_equip_acessorio_inserido();
	ajustar_vetor_servico_inserido();
	if(confirm('Deseja realizar o Pedido?')){
		var values = $("input[type='radio'][name='pessoa']:checked").val();
		var tipo_pessoa;
		if(values === "pessoafisica"){
			tipo_pessoa = 0;
		}else{
			tipo_pessoa = 1;
		}
		var nome = document.getElementById("nome_razaosocial");
		var apelido  = document.getElementById("nomefantasia");
		var dt_nascimento = document.getElementById("data_nasc");
		var site = document.getElementById("url_site");
		var cod_usuario = document.getElementById("cod_usuario");
		var cod_vendedor = $('#vendedor_list :selected').val();
		var i=0;
		if(nome.value.trim() === ""){
			alert("Favor inserir NOME ou RAZÃO SOCIAL do Cliente.");
			document.getElementById("nome_razaosocial").focus();
			return 0;
		}
		if(apelido.value.trim() === ""){
			if(tipo_pessoa === 1){
				alert("Favor inserir NOME FANTASIA do Cliente.");
				document.getElementById("nomefantasia").focus();
				return 0;
			}else{
				apelido.value = "";
			}
		}
		if(dt_nascimento.value.trim() === ""){
			alert("Favor inserir DATA DE NASCIMENTO do Cliente");
			document.getElementById("data_nasc").focus();
			return 0;
		}
		if(site.value.trim() === ""){
			site.value = "";
		}
		if(control_vetor_doc_json[0] === 0){
			alert("Favor inserir DOCUMENTO do Cliente");
			document.getElementById("numero_documento").focus();
			return 0;
		}
		var adress_aux = '';
		var documentoLength = control_vetor_doc_json.length;
		adress_aux = '&QDOC=' + documentoLength;
		var tem_doc = false;
		for(var i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				if((control_vetor_doc_json[i].tipo_doc.trim() == 2)||(control_vetor_doc_json[i].tipo_doc.trim() == 3)){
					tem_doc = true;
				}
			}
		}
		if(!tem_doc){
			alert("Favor inserir pelo menos 1 DOCUMENTO CPF/CNPJ.");
			document.getElementById("numero_documento").focus();
			return 0;
		}
		for(i=0;i<documentoLength;i++){
			if(control_vetor_doc_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPODOC_'+ i + '=' + control_vetor_doc_json[i].tipo_doc.trim();
				adress_aux = adress_aux + '&NUMDOC_'+ i + '=' + control_vetor_doc_json[i].numero.trim();
				adress_aux = adress_aux + '&DTDOC_'+ i + '=' + control_vetor_doc_json[i].dt_emiss.trim();
				adress_aux = adress_aux + '&ORGDOC_'+ i + '=' + control_vetor_doc_json[i].org_emiss.trim();
			}
		}
		if(control_vetor_end_json[0] === 0){
			alert("Favor inserir ENDEREÇO do Cliente.");
			document.getElementById("endereco").focus();
			return 0;
		}
		var enderecoLength = control_vetor_end_json.length;
		adress_aux = adress_aux + '&QEND=' + enderecoLength;
		for(i=0;i<enderecoLength;i++){
			if(control_vetor_end_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&END_'+ i + '=' + control_vetor_end_json[i].endereco.trim();
				adress_aux = adress_aux + '&BAIRRO_'+ i + '=' + control_vetor_end_json[i].bairro.trim();
				adress_aux = adress_aux + '&CIDADE_'+ i + '=' + control_vetor_end_json[i].cidade.trim();
				adress_aux = adress_aux + '&UF_'+ i + '=' + control_vetor_end_json[i].uf.trim();
				adress_aux = adress_aux + '&PAIS_'+ i + '=' + control_vetor_end_json[i].pais.trim();
				adress_aux = adress_aux + '&COMP_'+ i + '=' + control_vetor_end_json[i].complemento.trim();
				adress_aux = adress_aux + '&CEP_'+ i + '=' + control_vetor_end_json[i].cep.trim();
				adress_aux = adress_aux + '&TIPOEND_'+ i + '=' + control_vetor_end_json[i].tipo_end.trim();
			}
		}
		if(control_vetor_contato_json[0] === 0){
			alert("Favor inserir CONTATO do Cliente.");
			document.getElementById("ddd").focus();
			return 0;
		}
		var contatoLength = control_vetor_contato_json.length;
		adress_aux = adress_aux + '&QCTO=' + contatoLength;
		if(contatoLength < 3){
			alert("Favor inserir pelo menos 3 CONTATOS do Cliente.");
			document.getElementById("ddd").focus();
			return 0;
		}
		var tem_tel = false;
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				if(control_vetor_contato_json[i].tipo_contato.trim() == 3){
					tem_tel = true;
				}
			}
		}
		if(!tem_tel){
			alert("Favor inserir pelo menos 1 CONTATO do tipo Celular.");
			document.getElementById("ddd").focus();
			return 0;
		}
		for(i=0;i<contatoLength;i++){
			if(control_vetor_contato_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&TIPOCTO_'+ i + '=' + control_vetor_contato_json[i].tipo_contato.trim();
				adress_aux = adress_aux + '&DDD_'+ i + '=' + control_vetor_contato_json[i].ddd.trim();
				adress_aux = adress_aux + '&NUMCTO_'+ i + '=' + control_vetor_contato_json[i].numero.trim();
				adress_aux = adress_aux + '&PAISCTO_'+ i + '=' + control_vetor_contato_json[i].cod_pais.trim();
				adress_aux = adress_aux + '&NOMECTO_'+ i + '=' + control_vetor_contato_json[i].nome.trim();
				adress_aux = adress_aux + '&PARENCTO_'+ i + '=' + control_vetor_contato_json[i].parentesco.trim();
			}
		}
		if(control_vetor_email_json[0] === 0){
			alert("Favor inserir EMAIL do Cliente");
			document.getElementById("email").focus();
			return 0;
		}
		var emailLength = control_vetor_email_json.length;
		adress_aux = adress_aux + '&QMAIL=' + emailLength;
		for(i=0;i<emailLength;i++){
			if(control_vetor_email_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&MAIL_'+ i + '=' + control_vetor_email_json[i].email.trim();
			}
		}
		var enderero_inst = document.getElementById("endereco_inst");
		if(enderero_inst.value.trim() === ""){
			alert("Favor inserir ENDEREÇO para instalação");
			document.getElementById("endereco_inst").focus();
			return 0;
		}
		var complemento_inst = document.getElementById("complemento_inst");
		if(complemento_inst.value.trim() === ""){
			complemento_inst.value = "";
		}
		var bairro_inst = document.getElementById("bairro_inst");
		if(bairro_inst.value.trim() === ""){
			alert("Favor inserir BAIRRO do endereço de instalação");
			document.getElementById("bairro_inst").focus();
			return 0;
		}
		var cidade_inst = document.getElementById("cidade_inst");
		if(cidade_inst.value.trim() === ""){
			alert("Favor inserir CIDADE do endereço de instalação");
			document.getElementById("cidade_inst").focus();
			return 0;
		}
		var uf_inst = $('#uf_list_inst').val();
		var pais_inst = $('#pais_list_inst').val();
		var cep_inst = document.getElementById("cep_inst");
		if(cep_inst.value.trim() === ""){
			alert("Favor inserir CEP do endereço de instalação");
			document.getElementById("cep_inst").focus();
			return 0;
		}
		var referencia_inst = document.getElementById("referencia_inst");
		if(referencia_inst.value.trim() === ""){
			alert("Favor inserir REFERENCIA do endereço de instalação");
			document.getElementById("referencia_inst").focus();
			return 0;
		}
		var ddd_inst = document.getElementById("ddd_inst");
		if(ddd_inst.value.trim() === ""){
			alert("Favor inserir DDD do contato para instalação");
			document.getElementById("ddd_inst").focus();
			return 0;
		}
		var numero_inst = document.getElementById("numero_inst");
		if(numero_inst.value.trim() === ""){
			alert("Favor inserir NÚMERO do contato para instalação");
			document.getElementById("numero_inst").focus();
			return 0;
		}
		var nome_inst = document.getElementById("contato_inst");
		if(nome_inst.value.trim() === ""){
			alert("Favor inserir NOME DO RESPONSÁVEL do contato para instalação");
			document.getElementById("contato_inst").focus();
			return 0;
		}
		adress_aux = adress_aux + '&ENDINST=' + enderero_inst.value.trim();
		adress_aux = adress_aux + '&COMPINST=' + complemento_inst.value.trim();
		adress_aux = adress_aux + '&BAIRROINST=' + bairro_inst.value.trim();
		adress_aux = adress_aux + '&CIDADEINST=' + cidade_inst.value.trim();
		adress_aux = adress_aux + '&UFINST=' + uf_inst + '&PAISINST=' + pais_inst;
		adress_aux = adress_aux + '&CEPINST=' + cep_inst.value.trim();
		adress_aux = adress_aux + '&REFINST=' + referencia_inst.value.trim();
		adress_aux = adress_aux + '&DDDINST=' + ddd_inst.value.trim();
		adress_aux = adress_aux + '&NUMEROINST=' + numero_inst.value.trim();
		adress_aux = adress_aux + '&NOMEINST=' + nome_inst.value.trim();
		if(control_vetor_unidade_json[0] === 0){
			alert("Favor inserir ao menos uma UNIDADE no PEDIDO");
			return 0;
		}
		var unidadeLength = control_vetor_unidade_json.length;
		adress_aux = adress_aux + '&QUNIDADE=' + unidadeLength;
		for(i=0;i<unidadeLength;i++){
			if(control_vetor_unidade_json[i]===0){
				break;
			}else{
				adress_aux = adress_aux + '&QCTOUNID_'+ i + '=' + control_vetor_unidade_json[i].contato.length;
				for(var j=0;j<control_vetor_unidade_json[i].contato.length;j++){
					adress_aux = adress_aux + '&TIPOCTOUNID_'+ i + '_' + j + '=' + control_vetor_unidade_json[i].contato[j].tipo_contato.trim();
					adress_aux = adress_aux + '&DDDUNID_'+ i + '_' + j + '=' + control_vetor_unidade_json[i].contato[j].ddd.trim();
					adress_aux = adress_aux + '&NUMCTOUNID_'+ i + '_' + j + '=' + control_vetor_unidade_json[i].contato[j].numero.trim();
					adress_aux = adress_aux + '&PAISCTOUNID_'+ i + '_' + j + '=' + control_vetor_unidade_json[i].contato[j].cod_pais.trim();
					adress_aux = adress_aux + '&NOMECTOUNID_'+ i + '_' + j + '=' + control_vetor_unidade_json[i].contato[j].nome.trim();
					adress_aux = adress_aux + '&PARENCTOUNID_'+ i + '_' + j + '=' + control_vetor_unidade_json[i].contato[j].parentesco.trim();
				}
				adress_aux = adress_aux + '&SENHA_ATENDIMENTO_' + i + '=' + control_vetor_unidade_json[i].senha;
				adress_aux = adress_aux + '&TIPO_UNIDADE_' + i + '=' + control_vetor_unidade_json[i].tipo_unidade;
				switch(Number(control_vetor_unidade_json[i].tipo_unidade)){
				case 2:
					adress_aux = adress_aux + '&PLACA_' + i + '=' + control_vetor_unidade_json[i].unidade.placa;
					adress_aux = adress_aux + '&CHASSI_' + i + '=' + control_vetor_unidade_json[i].unidade.chassi;
					adress_aux = adress_aux + '&RENAVAN_' + i + '=' + control_vetor_unidade_json[i].unidade.renavan;
					adress_aux = adress_aux + '&ANO_FAB_' + i + '=' + control_vetor_unidade_json[i].unidade.ano_fab;
					adress_aux = adress_aux + '&ANO_MOD_' + i + '=' + control_vetor_unidade_json[i].unidade.ano_mod;
					adress_aux = adress_aux + '&MARCA_' + i + '=' + control_vetor_unidade_json[i].unidade.marca;
					adress_aux = adress_aux + '&MODELO_' + i + '=' + control_vetor_unidade_json[i].unidade.modelo;
					adress_aux = adress_aux + '&COR_' + i + '=' + control_vetor_unidade_json[i].unidade.cor;
					adress_aux = adress_aux + '&COMBUSTIVEL_' + i + '=' + control_vetor_unidade_json[i].unidade.combustivel;
					adress_aux = adress_aux + '&VOLTAGEM_' + i + '=' + control_vetor_unidade_json[i].unidade.voltagem;
					adress_aux = adress_aux + '&KM_' + i + '=' + control_vetor_unidade_json[i].unidade.km;
					adress_aux = adress_aux + '&DATA_VISTORIA_' + i + '=' + control_vetor_unidade_json[i].unidade.data_vistoria;
				}
			}
		}
		var info_pedido = "VAZIO";
		var tipoPedido = $("input[type='radio'][name='servico']:checked").val();
		if((Number(tipoPedido) == 1) || (Number(tipoPedido) == 2) || (Number(tipoPedido) == 3)){
			if(control_vetor_equip_acessorio_json[0] === 0){
				alert("Favor inserir ao menos um EQUIPAMENTO OU ACESSÓRIO no PEDIDO");
				return 0;
			}
			if((Number(tipoPedido) == 3)){
				info_pedido = document.getElementById("teste_dias").value;
				if(info_pedido.trim() == ""){
					alert("Favor informar a quantidade de dias");
					document.getElementById("teste_dias").focus();
					return 0;
				}
			}
		}
		var equipAcessorioLength = control_vetor_equip_acessorio_json.length;
		adress_aux = adress_aux + '&QEQUIP_ACESSORIO=' + equipAcessorioLength;
		for(i=0;i<equipAcessorioLength;i++){
			adress_aux = adress_aux + '&ITEMEQUIP_' + i + '=' + control_vetor_equip_acessorio_json[i].equip_acessorio;
			adress_aux = adress_aux + '&QTDEQUIP_' + i + '=' + control_vetor_equip_acessorio_json[i].quantidade;
			adress_aux = adress_aux + '&VALOREQUIP_' + i + '=' + control_vetor_equip_acessorio_json[i].valor_unitario;
		}
		if(control_vetor_servico_json[0] === 0){
			alert("Favor inserir ao menos um SERVIÇO no PEDIDO");
			return 0;
		}
		var servicoLength = control_vetor_servico_json.length;
		adress_aux = adress_aux + '&QSERVICO=' + servicoLength;
		for(i=0;i<servicoLength;i++){
			adress_aux = adress_aux + '&ITEMSERVICO_' + i + '=' + control_vetor_servico_json[i].servico;
			adress_aux = adress_aux + '&QTDSERVICO_' + i + '=' + control_vetor_servico_json[i].quantidade;
			adress_aux = adress_aux + '&VALORSERVICO_' + i + '=' + control_vetor_servico_json[i].valor_unitario;
		}
		var observacoes = document.getElementById("observacoes");
		var obsLength = observacoes.textLength;
		if(Number(obsLength) == 0){
			adress_aux = adress_aux + '&QOBS=1&OBSERVACAO_0=Não há observações!';
		}else if(Number(obsLength)< 200){
			adress_aux = adress_aux + '&QOBS=1&OBSERVACAO_0=' + observacoes.value.trim();
		}else if(Number(obsLength) >= 200 && Number(obsLength) <= 398){
			adress_aux = adress_aux + '&QOBS=2&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
			adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
		}else if(Number(obsLength) >= 399 && Number(obsLength) <= 597){
			adress_aux = adress_aux + '&QOBS=3&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
			adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
			adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
		}else if(Number(obsLength) >= 598 && Number(obsLength) <= 796){
			adress_aux = adress_aux + '&QOBS=4&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
			adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
			adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
			adress_aux = adress_aux + '&OBSERVACAO_3=' + observacoes.value.trim().slice(Number(597),Number(796));
		}
		var envio_boleto = 0;
		if(document.getElementById("envio_boleto").checked){
			envio_boleto = 1;
		}

		var dt_vencimento = $("input[type='radio'][name='vencimento']:checked").val();
		dia = new Date();
		var adress = url_adress + 'services/novoPedido?OP_CODE=CREATE&TIPO=' + tipo_pessoa + '&NOME=' + nome.value.trim() + '&NOME_FANTASIA=' + apelido.value.trim();
		adress = adress + '&SITE=' + site.value.trim() + '&DATA_NASCIMENTO=' + dt_nascimento.value.trim() + '&DATA_INGRESSO=' + dia.yyyymmdd();
		adress = adress + '&COD_VENDEDOR='+ cod_vendedor.trim() + '&COD_USUARIO=' + cod_usuario.innerHTML.trim() + '&TIPO_PEDIDO=' + tipoPedido.trim();
		adress = adress + '&DATA_VENCIMENTO=' + dt_vencimento.trim() + '&INFO_PEDIDO=' + info_pedido.trim() + '&ENVIO_BOLETO=' + envio_boleto;
		adress = adress + adress_aux;
		document.location.href = adress;
	}
}

function carregar_dados_confirmacao_pedido_function(){
    var i;
    var j;
    /*Carregar documentos*/
    var div_doc_inserido_obj = $("#docs_inserido");
    var tamanho_doc = div_doc_inserido_obj[0].childElementCount;
    for(i=0;i<tamanho_doc;i++){
        doc_carregado_array[i] = new doc_carregado();
        doc_carregado_array[i].num_doc = $('#num_doc_oculta_' + i).html().trim();
        doc_carregado_array[i].tipo_doc = $('#tipo_doc_oculta_' + i).html().trim();
        doc_carregado_array[i].dt_emiss = $('#dt_emiss_doc_oculta_' + i).html().trim();
        doc_carregado_array[i].org_emiss = $('#org_emiss_doc_oculta_' + i).html().trim();
    }
    var tipo_doc_tipo_obj = $('#tipodoc_list');
    var tipo_doc_tamanho_tipo = tipo_doc_tipo_obj[0].length;
    for (i=0; i<tipo_doc_tamanho_tipo;i++){
        control_vetor_doc_tipo[i] = document.getElementsByName("option_documento_tipo")[i].text;
    }
    for(i=0;i<tamanho_doc;i++){
    	control_div_doc[i] = new div_doc_inseridas(0, 0);
    	control_vetor_doc[i] = new vetor_doc_inserido();
        control_vetor_doc[i].numero = doc_carregado_array[i].num_doc;
        for(j=0;j<tipo_doc_tamanho_tipo;j++){
            if(control_vetor_doc_tipo[j] === doc_carregado_array[i].tipo_doc){
                control_vetor_doc[i].tipo_doc = Number(j+1);
                break;
            }
        }
        if(doc_carregado_array[i].dt_emiss !== "5000-12-31"){
        	control_vetor_doc[i].dt_emiss = doc_carregado_array[i].dt_emiss;
        }else{
        	control_vetor_doc[i].dt_emiss = "";
        }
        if(doc_carregado_array[i].org_emiss !== "VAZIO"){
        	control_vetor_doc[i].org_emiss = doc_carregado_array[i].org_emiss;
        }else{
        	control_vetor_doc[i].org_emiss = "";
        }
    }
    var content_div_doc = "";
    for(i=0;i<tamanho_doc;i++){
    	content_div_doc = content_div_doc + '<b>Numero:</b> '+ control_vetor_doc[i].numero;
    	content_div_doc = content_div_doc + '<br><b>Tipo do Documento:</b> ' + control_vetor_doc_tipo[Number(control_vetor_doc[i].tipo_doc) - 1];
		if (control_vetor_doc[i].dt_emiss !== ""){
			content_div_doc = content_div_doc + '<br><b>Data de Emissão:</b> ' + control_vetor_doc[i].dt_emiss;
		}
		if (control_vetor_doc[i].org_emiss !== ""){
			content_div_doc = content_div_doc + '&nbsp;&nbsp;&nbsp;<b>Orgão Emissor:</b> ' + control_vetor_doc[i].org_emiss;
		}
		if(tamanho_doc > Number(1)){
			content_div_doc = content_div_doc + '<canvas id="myCanvasDoc_' + i + '" width="650" height="1" style="border:0px;"></canvas>\n\
<script>var c = document.getElementById("myCanvasDoc_' + i + '");\n\
var ctx = c.getContext("2d");\n\
ctx.moveTo(0,0);\n\
ctx.lineTo(650,0);\n\
ctx.stroke();</script><br>';
		}
    }
    $('#documentos').html("");
    $('#documentos').html(content_div_doc);
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar endereços*/
    var div_end_inserido_obj = $("#endereco_inserido");
    var tamanho_end = div_end_inserido_obj[0].childElementCount;
    for(i=0;i<tamanho_end;i++){
        end_carregado_array[i] = new end_carregado();
        end_carregado_array[i].logradouro = $('#endereco_oculta_' + i).html().trim();
        end_carregado_array[i].complemento = $('#complemento_oculta_' + i).html().trim();
        end_carregado_array[i].bairro = $('#bairro_oculta_' + i).html().trim();
        end_carregado_array[i].cidade = $('#cidade_oculta_' + i).html().trim();
        end_carregado_array[i].uf = $('#uf_oculta_' + i).html().trim();
        end_carregado_array[i].pais = $('#pais_oculta_' + i).html().trim();
        end_carregado_array[i].cep = $('#cep_oculta_' + i).html().trim();
        end_carregado_array[i].tipo_end = $('#tipo_end_oculta_' + i).html().trim();
    }
    var tipo_endereco_tipo_obj = $('#tipo_end_list');
    var tipo_endereco_tamanho_tipo = tipo_endereco_tipo_obj[0].length;
    for (var i=0; i<tipo_endereco_tamanho_tipo;i++){
        control_vetor_end_tipo[i] = document.getElementsByName("option_endereco_tipo")[i].text;
    }
    var tipo_endereco_pais_obj = $('#pais_list');
    var tipo_endereco_tamanho_pais = tipo_endereco_pais_obj[0].length;
    for (i=0; i<tipo_endereco_tamanho_pais;i++){
        control_vetor_end_pais[i] = document.getElementsByName("option_endereco_pais")[i].text;
    }
    var tipo_endereco_uf_obj = $('#uf_list');
    var tipo_endereco_tamanho_uf = tipo_endereco_uf_obj[0].length;
    for (i=0; i<tipo_endereco_tamanho_uf;i++){
        control_vetor_end_uf[i] = document.getElementsByName("option_endereco_uf")[i].text;
    }
    for(i=0;i<tamanho_end;i++){
    	control_div_end[i] = new div_end_inseridas(0, 0);
    	control_vetor_end[i] = new vetor_end_inserido();
        control_vetor_end[i].endereco = end_carregado_array[i].logradouro;
        control_vetor_end[i].complemento = end_carregado_array[i].complemento;
        control_vetor_end[i].bairro = end_carregado_array[i].bairro;
        control_vetor_end[i].cidade = end_carregado_array[i].cidade;
        control_vetor_end[i].uf = end_carregado_array[i].uf;
        control_vetor_end[i].pais = end_carregado_array[i].pais;
        control_vetor_end[i].cep = end_carregado_array[i].cep;
        control_vetor_end[i].tipo_end = end_carregado_array[i].tipo_end;
    }
    var content_div_end = "";
    for(i=0;i<tamanho_end;i++){
    	content_div_end = content_div_end + '<b>Tipo do Endereço:</b>&nbsp;' + control_vetor_end_tipo[Number(control_vetor_end[i].tipo_end) - 1];
    	content_div_end = content_div_end + '<br><b>Endereço:</b>&nbsp;' + control_vetor_end[i].endereco;
    	if(control_vetor_end[i].complemento.trim() != ""){
    		content_div_end = content_div_end + '<br><b>Complemento:</b>&nbsp;' + control_vetor_end[i].complemento;
    	}
    	content_div_end = content_div_end + '&nbsp;&nbsp;&nbsp;<b>Bairro:</b>&nbsp;' + control_vetor_end[i].bairro;
    	content_div_end = content_div_end + '&nbsp;&nbsp;&nbsp;<b>Cidade:</b>&nbsp;' + control_vetor_end[i].cidade;
    	content_div_end = content_div_end + '&nbsp;&nbsp;&nbsp;<b>UF:</b>&nbsp;' + control_vetor_end_uf[Number(control_vetor_end[i].uf) - 1];
    	content_div_end = content_div_end + '<br><b>País:</b>&nbsp;' + control_vetor_end_pais[Number(control_vetor_end[i].pais) - 1];
    	content_div_end = content_div_end + '&nbsp;&nbsp;&nbsp;<b>CEP.:</b>&nbsp;' + control_vetor_end[i].cep;
		if(tamanho_end > Number(1)){
	    	content_div_end = content_div_end + '<canvas id="myCanvasEnd_' + i + '" width="650" height="1" style="border:0px;"></canvas>\n\
<script>var c = document.getElementById("myCanvasEnd_' + i + '");\n\
var ctx = c.getContext("2d");\n\
ctx.moveTo(0,0);\n\
ctx.lineTo(650,0);\n\
ctx.stroke();</script><br>';
		}
    }
    var uf_end = $('#uf_inst_visu').html().trim();
    $('#uf_inst_visu').html("");
    $('#uf_inst_visu').html(control_vetor_end_uf[Number(uf_end) - 1]);
    $('#endereco').html("");
    $('#endereco').html(content_div_end);
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar contatos*/
    var div_contato_inserido_obj = $("#contato_inserido");
    var tamanho_contato = div_contato_inserido_obj[0].childElementCount;
    clean_contato = $('#div_contato').html();
    for(i=0;i<tamanho_contato;i++){
        contato_carregado_array[i] = new contato_carregado();
        contato_carregado_array[i].ddd = $('#ddd_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].numero = $('#numero_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].tipo_contato = $('#tipo_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].cod_pais = $('#cod_pais_oculta_' + i).html().trim();
        contato_carregado_array[i].nome = $('#nome_contato_oculta_' + i).html().trim();
        contato_carregado_array[i].grau_contato = $('#grau_contato_oculta_' + i).html().trim();
    }
    var tipo_contato_tipo_obj = $('#tipocont_list');
    var tipo_contato_tamanho_tipo = tipo_contato_tipo_obj[0].length;
    for (i=0; i<tipo_contato_tamanho_tipo;i++){
        control_vetor_contato_tipo[i] = document.getElementsByName("option_contato_tipo")[i].text;
    }
    var tipo_contato_parentesco_obj = $('#info_contato_list');
    var tipo_contato_tamanho_parentesco = tipo_contato_parentesco_obj[0].length;
    for (i=0; i<tipo_contato_tamanho_parentesco;i++){
        control_vetor_contato_parentesco[i] = document.getElementsByName("option_parentesco_cargo")[i].text;
    }
    for(i=0;i<tamanho_contato;i++){
    	control_div_contato[i] = new div_contato_inseridas(0, 0);
    	control_vetor_contato[i] = new vetor_contato_inserido();
        control_vetor_contato[i].ddd = contato_carregado_array[i].ddd;
        control_vetor_contato[i].numero = contato_carregado_array[i].numero;
        for(j=0;j<tipo_contato_tamanho_tipo;j++){
            if(control_vetor_contato_tipo[j] === contato_carregado_array[i].tipo_contato){
                control_vetor_contato[i].tipo_contato = Number(j+1);
                break;
            }
        }
        control_vetor_contato[i].cod_pais = contato_carregado_array[i].cod_pais;
        control_vetor_contato[i].nome = contato_carregado_array[i].nome;
        for(j=0;j<tipo_contato_tamanho_parentesco;j++){
            if(control_vetor_contato_parentesco[j] === contato_carregado_array[i].grau_contato){
                control_vetor_contato[i].parentesco = Number(j+1);
                break;
            }
        }
    }
    var content_div_contato = "";
    for(i=0;i<tamanho_contato;i++){
    	content_div_contato = content_div_contato + '<b>Nome:</b>&nbsp;' + control_vetor_contato[i].nome;
    	content_div_contato = content_div_contato + '&nbsp;&nbsp;&nbsp;<b>Parentesco/Cargo:</b>&nbsp;' + control_vetor_contato_parentesco[Number(control_vetor_contato[i].parentesco)-1] + '';
    	content_div_contato = content_div_contato + '<br><b>' + control_vetor_contato_tipo[Number(control_vetor_contato[i].tipo_contato)-1];
    	content_div_contato = content_div_contato + '</b>:&nbsp;+' + control_vetor_contato[i].cod_pais;
    	content_div_contato = content_div_contato + '&nbsp;(' + control_vetor_contato[i].ddd + ')';
    	content_div_contato = content_div_contato + '&nbsp;' + control_vetor_contato[i].numero;
		if(tamanho_contato > Number(1)){
	    	content_div_contato = content_div_contato + '<canvas id="myCanvasCto_' + i + '" width="650" height="1" style="border:0px;"></canvas>\n\
<script>var c = document.getElementById("myCanvasCto_' + i + '");\n\
var ctx = c.getContext("2d");\n\
ctx.moveTo(0,0);\n\
ctx.lineTo(650,0);\n\
ctx.stroke();</script><br>';
		}
    }
    $('#contato').html("");
    $('#contato').html(content_div_contato);
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
    /*Carregar emails*/
    var div_email_inserido_obj = $("#emails_inserido");
    var tamanho_email = div_email_inserido_obj[0].childElementCount;
    clean_email = $('#div_email').html();
    for(i=0;i<tamanho_email;i++){
        email_carregado_array[i] = new email_carregado();
        email_carregado_array[i].email = $('#nome_email_oculta_' + i).html().trim();
    }
    for(i=0;i<tamanho_email;i++){
    	control_div_email[i] = new div_email_inseridas(0, 0);
    	control_vetor_email[i] = new vetor_email_inserido();
        control_vetor_email[i].email = email_carregado_array[i].email;
    }
    var content_div_email = "";
    for(i=0;i<tamanho_email;i++){
    	content_div_email = content_div_email + '<b>' + Number(i+1) + ':</b>&nbsp;' + control_vetor_email[i].email;
		if(tamanho_email > Number(1)){
	    	content_div_email = content_div_email + '<canvas id="myCanvasMail_' + i + '" width="650" height="1" style="border:0px;"></canvas>\n\
<script>var c = document.getElementById("myCanvasMail_' + i + '");\n\
var ctx = c.getContext("2d");\n\
ctx.moveTo(0,0);\n\
ctx.lineTo(650,0);\n\
ctx.stroke();</script><br>';
		}
    }
    $('#email').html("");
    $('#email').html(content_div_email);
}

function form_novo_pedido_function(area,workId,pkObj){
	var cod_usuario = document.getElementById("cod_usuario").innerHTML.trim();
	switch (area){
	case "ADM":
		if(!document.getElementById("dadosCorretos").checked){
			if(confirm('Existe algum erro no formulário?')){
				//todo - VOLTAR PARA COMERCIAL CORRIGIR ERRO
			}
			alert("Favor confirmar se os dados do pedido estão corretos.");
			document.getElementById("dadosCorretos").focus();
			return 0;
		}
		break;
	case "CAD_GS":
		if(!document.getElementById("dadosCorretos").checked){
			alert("Favor marcar a box caso tenha realizado o cadastro no Global Search.");
			document.getElementById("dadosCorretos").focus();
			return 0;
		}
		break;
	case "CAD_SYSCOM":
		if(!document.getElementById("dadosCorretos").checked){
			alert("Favor marcar a box caso tenha realizado o cadastro no Syscom.");
			document.getElementById("dadosCorretos").focus();
			return 0;
		}
		break;
	case "CAD_ZXLOG":
		if(!document.getElementById("dadosCorretos").checked){
			alert("Favor marcar a box caso tenha realizado o cadastro no ZX Log.");
			document.getElementById("dadosCorretos").focus();
			return 0;
		}
		break;
	case "SEP_EQUIP":
		if(!document.getElementById("dadosCorretos").checked){
			alert("Favor marcar a box caso tenha realizado a separação dos equipamentos necessários para a instalação.");
			document.getElementById("dadosCorretos").focus();
			return 0;
		}
		var idModulo = "";
		var codVeiculo = "";
		var qtdVeiculo = document.getElementById("QtdUnidadesVeiculo").innerHTML.trim();
		var adress_aux = '&QTD_UNIDADE=' + qtdVeiculo;
		for(var j=0; j<Number(qtdVeiculo);j++){
			var val_id_equipamento = $('#item_id_modulo_' + j).val();
			if(val_id_equipamento == ""){
		    	alert("Favor informar o ID do Módulo a ser inserido.");
		    	document.getElementById("item_id_modulo_" + j).focus();
				return 0;
			}
			var ids = $('#id_list_' + j  + ' option').filter(function() {
		        return this.value == val_id_equipamento;
		    }).data('label');
			for(var i=0; i<ids.length; i++){
				if(ids.charAt(i+1) == "-" && ids.charAt(i+2) == "-"){
					idModulo = ids.slice(Number(0), Number(i+1));
					codVeiculo = ids.slice(Number(i+3),Number(ids.length));
					adress_aux = adress_aux + '&COD_MODULO_' + j + '=' + idModulo + '&COD_VEICULO_' + j + '=' + codVeiculo + '&TIPO_UNIDADE_' + j + '=2';
					break;
				}
			}
		}
		var adress = url_adress + 'services/separacaoequipamentoservlet?COD_USUARIO=' + cod_usuario + '&PK_COLUMN=' + pkObj + '&WORK_ID='+workId;
		adress = adress + adress_aux;
		window.location = adress;
		return 0;
		break;
	case "FIN":
		if(!document.getElementById("scpSerasa").checked){
			if(confirm('Existe alguma pendencia no SPC ou Serasa?')){
				//todo - ENVIAR PARA DIRETORIA DAR OK OU DECLARAR PENDÊNCIA IRRELEVANTE
			}
			alert("Favor confirmar se o cliente possui pendencia no SPC ou Serasa.");
			document.getElementById("scpSerasa").focus();
			return 0;
		}
		break;
	}
	var adress = url_adress + 'services/startservlet?OP_CODE=ENDWORK&COD_USUARIO=' + cod_usuario + '&PK_COLUMN=' + pkObj + '&WORK_ID='+workId;
	document.location.href = adress;
}

function carregar_dados_agendamento_pedido_function(){
	var tipo_endereco_uf_obj = $('#uf_list_inst');
    var tipo_endereco_tamanho_uf = tipo_endereco_uf_obj[0].length;
    for (var i=0; i<tipo_endereco_tamanho_uf;i++){
        control_vetor_end_uf[i] = document.getElementsByName("option_endereco_uf")[i].text;
    }
    var uf_end = $('#uf_agend_div').html().trim();
    $('#uf_agend_div').html("");
    $('#uf_agend_div').html(control_vetor_end_uf[Number(uf_end) - 1]);
}

function muda_endereco_function(){
    var end_agend = $('#end_inst').val();
    if(end_agend == "sim"){
    	var div_end = $('#label_end').html();
    	$('#endereco_instalacao').html("");
    	$('#endereco_instalacao').html(div_end);
    	carregar_dados_agendamento_pedido_function();
    }else if (end_agend == "nao"){
    	var div_end = $('#form_end').html();
    	$('#endereco_instalacao').html("");
    	$('#endereco_instalacao').html(div_end);
    }
}

var vector_unidades_agendamento = new Array();
var vector_data_agendamento = new Array();
var vector_hora_agendamento = new Array();

function operacional_agendamento_function(workId){
	var end_agend = $('#end_inst').val();
	var adress_aux = "&END_AGEND=" + end_agend;
    if(end_agend == "nao"){
    	var enderero_inst = document.getElementById("endereco_inst").value.trim();
		if(enderero_inst === ""){
			alert("Favor inserir ENDEREÇO para instalação");
			document.getElementById("endereco_inst").focus();
			return 0;
		}
		var complemento_inst = document.getElementById("complemento_inst").value.trim();
		if(complemento_inst === ""){
			complemento_inst.value = "";
		}
		var bairro_inst = document.getElementById("bairro_inst").value.trim();
		if(bairro_inst === ""){
			alert("Favor inserir BAIRRO do endereço de instalação");
			document.getElementById("bairro_inst").focus();
			return 0;
		}
		var cidade_inst = document.getElementById("cidade_inst").value.trim();
		if(cidade_inst === ""){
			alert("Favor inserir CIDADE do endereço de instalação");
			document.getElementById("cidade_inst").focus();
			return 0;
		}
		var uf_inst = $('#uf_list_inst').val();
		var pais_inst = $('#pais_list_inst').val();
		var cep_inst = document.getElementById("cep_inst").value.trim();
		if(cep_inst === ""){
			alert("Favor inserir CEP do endereço de instalação");
			document.getElementById("cep_inst").focus();
			return 0;
		}
		var referencia_inst = document.getElementById("referencia_inst").value.trim();
		if(referencia_inst === ""){
			alert("Favor inserir REFERENCIA do endereço de instalação");
			document.getElementById("referencia_inst").focus();
			return 0;
		}
		var ddd_inst = document.getElementById("ddd_inst").value.trim();
		if(ddd_inst === ""){
			alert("Favor inserir DDD do contato para instalação");
			document.getElementById("ddd_inst").focus();
			return 0;
		}
		var numero_inst = document.getElementById("numero_inst").value.trim();
		if(numero_inst === ""){
			alert("Favor inserir NÚMERO do contato para instalação");
			document.getElementById("numero_inst").focus();
			return 0;
		}
		var nome_inst = document.getElementById("contato_inst").value.trim();
		if(nome_inst === ""){
			alert("Favor inserir NOME DO RESPONSÁVEL do contato para instalação");
			document.getElementById("contato_inst").focus();
			return 0;
		}
    	adress_aux = adress_aux + "&ENDINST=" + enderero_inst;
    	adress_aux = adress_aux + "&COMPINST=" + complemento_inst;
    	adress_aux = adress_aux + "&BAIRROINST=" + bairro_inst;
    	adress_aux = adress_aux + "&CIDADEINST=" + cidade_inst;
    	adress_aux = adress_aux + "&UFINST=" + uf_inst;
    	adress_aux = adress_aux + "&PAISINST=" + pais_inst;
    	adress_aux = adress_aux + "&CEPINST=" + cep_inst;
    	adress_aux = adress_aux + "&REFERINST=" + referencia_inst;
    	adress_aux = adress_aux + "&DDDINST=" + ddd_inst;
    	adress_aux = adress_aux + "&NUMEROINST=" + numero_inst;
    	adress_aux = adress_aux + "&NOMEINST=" + nome_inst;
    }else{
        var cod_dado_inst = document.getElementById("cod_dado_inst").innerHTML.trim();
    	adress_aux = adress_aux + "&CODDADOINST=" + cod_dado_inst;
    }
	var cod_usuario = document.getElementById("cod_usuario").innerHTML.trim();
	var cod_pedido = document.getElementById("cod_pedido").innerHTML.trim();
    var total_unidades = document.getElementById("total_unidades").innerHTML.trim();
    var total_unidades_agendadas = 0;
    var reagendar = "false";
    var data_agendamento = "";
    var hora_agendamento = "";

    for(var i=0;i<total_unidades;i++){
    	data_agendamento = document.getElementById("data_agendamento_"+i).value.trim();
    	hora_agendamento = document.getElementById("hora_agendamento_"+i).value.trim();

    	if((data_agendamento == "" || hora_agendamento == "") && !document.getElementById("reagendar_"+i).checked){
    		if(document.getElementById("reagendar_"+i).checked){
    			reagendar = "true";
    		}else{
    			if(data_agendamento == ""){
    				alert("Favor selecionar a DATA DE AGENDAMENTO.");
    				document.getElementById("data_agendamento_"+i).focus();
    				return 0;
    			}
    			if(hora_agendamento == ""){
    				alert("Favor selecionar a HORA DE AGENDAMENTO.");
    				document.getElementById("hora_agendamento_"+i).focus();
    				return 0;
    			}
    		}
    	}else{
    		if(document.getElementById("reagendar_"+i).checked){
    			reagendar = "true";
    		}else{
	    		vector_unidades_agendamento[total_unidades_agendadas] = document.getElementById("reagendar_"+i).value;
	    		vector_data_agendamento[total_unidades_agendadas] = data_agendamento;
	    		vector_hora_agendamento[total_unidades_agendadas] = hora_agendamento;
	    		total_unidades_agendadas++;
    		}
    	}
    }

    total_unidades_agendadas = vector_unidades_agendamento.length;
    if(total_unidades_agendadas == 0 && reagendar == "false"){
    	alert("Favor selecionar ao menos uma UNIDADE.");
		return 0;
    }

	adress_aux = adress_aux + "&TOTALUNIDAGEND=" + total_unidades_agendadas;

	for(i=0;i<total_unidades_agendadas;i++){
		adress_aux = adress_aux + "&CODVEIC_" + i + "=" + vector_unidades_agendamento[i];
		adress_aux = adress_aux + "&DATA_AGENDAMENTO_" + i + "=" + vector_data_agendamento[i];
		adress_aux = adress_aux + "&HORA_AGENDAMENTO_" + i + "=" + vector_hora_agendamento[i];
	}

	var observacoes = document.getElementById("observacoes");
	var obsLength = observacoes.textLength;
	if(Number(obsLength) == 0){
		adress_aux = adress_aux + '&QOBS=1&OBSERVACAO_0=Não há observações!';
	}else if(Number(obsLength)< 200){
		adress_aux = adress_aux + '&QOBS=1&OBSERVACAO_0=' + observacoes.value.trim();
	}else if(Number(obsLength) >= 200 && Number(obsLength) <= 398){
		adress_aux = adress_aux + '&QOBS=2&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
		adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
	}else if(Number(obsLength) >= 399 && Number(obsLength) <= 597){
		adress_aux = adress_aux + '&QOBS=3&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
		adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
		adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
	}else if(Number(obsLength) >= 598 && Number(obsLength) <= 796){
		adress_aux = adress_aux + '&QOBS=4&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
		adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
		adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
		adress_aux = adress_aux + '&OBSERVACAO_3=' + observacoes.value.trim().slice(Number(597),Number(796));
	}
	dia = new Date();
	var adress = url_adress + 'services/agendamento?OP_CODE=CREATE&COD_USUARIO=' + cod_usuario + '&CODPEDIDO=' + cod_pedido 
							+ '&WORK_ID=' + workId + '&DATA_INGRESSO=' + dia.yyyymmdd() + '&REAGENDAR=' + reagendar;
    adress = adress + adress_aux;
	document.location.href = adress;
}

var obs_frustrada = '<textarea placeholder="Observações" cols="70" rows="4" id="obs_frustrada" maxlength="796"></textarea>';

function desbloqueia_unidades_function(){
    var frustrada = $('#frustrada').val();
    if(frustrada == "sim"){
    	$('#obs_frust').html(obs_frustrada);
    	$('#unidades').html("");
    }else if (frustrada == "nao"){
    	var div_unidades = $('#dados_unidades').html();
    	$('#obs_frust').html("");
    	$('#unidades').html("");
    	$('#unidades').html(div_unidades);
    }	
}

function operacional_processar_agendamento_function(workId,pkObj,codUnidadeAgendada,codOS){
	if (confirm("Confirma a instalação?")){
		var frustrada = $('#frustrada').val();
		var adress_aux = "&FRUSTRADA=" + frustrada;
		var cod_usuario = document.getElementById("cod_usuario").innerHTML.trim();
		var val_datalist_nome = $('#item_nome_tecnico').val();
		if(val_datalist_nome == ""){
	    	alert("Favor informar o Técnico.");
	    	document.getElementById("item_nome_tecnico").focus();
			return 0;
		}
		var cod_tecnico = $('#nome_list option').filter(function() {
	        return this.value == val_datalist_nome;
	    }).data('label');
		var horaChegadaTecnico = document.getElementById("chegada_tecnico").value.trim();
		var horaSaidaTecnico = document.getElementById("saida_tecnico").value.trim();
		if(horaChegadaTecnico == ""){
	    	alert("Favor informar o horário de chegada do Técnico.");
	    	document.getElementById("chegada_tecnico").focus();
			return 0;
		}
		if(horaSaidaTecnico == ""){
	    	alert("Favor informar o horário de saída do Técnico.");
	    	document.getElementById("saida_tecnico").focus();
			return 0;
		}
		adress_aux = adress_aux + "&COD_OS=" + codOS;
		adress_aux = adress_aux + "&COD_TECNICO=" + cod_tecnico;
		adress_aux = adress_aux + "&CHEGADA_TECNICO=" + horaChegadaTecnico;
		adress_aux = adress_aux + "&SAIDA_TECNICO=" + horaSaidaTecnico;
		if(frustrada == "sim"){
			var observacoes = document.getElementById("obs_frustrada");
			var obsLength = observacoes.textLength;
			if(Number(obsLength) == 0){
		    	alert("Favor informar o motivo da visita ter sido Frustrada.");
		    	document.getElementById("obs_frustrada").focus();
				return 0;
			}else if(Number(obsLength)< 200){
				adress_aux = adress_aux + '&QOBS=1&OBSERVACAO_0=' + observacoes.value.trim();
			}else if(Number(obsLength) >= 200 && Number(obsLength) <= 398){
				adress_aux = adress_aux + '&QOBS=2&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
				adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
			}else if(Number(obsLength) >= 399 && Number(obsLength) <= 597){
				adress_aux = adress_aux + '&QOBS=3&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
				adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
				adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
			}else if(Number(obsLength) >= 598 && Number(obsLength) <= 796){
				adress_aux = adress_aux + '&QOBS=4&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
				adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
				adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
				adress_aux = adress_aux + '&OBSERVACAO_3=' + observacoes.value.trim().slice(Number(597),Number(796));
			}
		}else{
			var observacoes = document.getElementById("observacoes");
			var obsLength = observacoes.textLength;
			if(Number(obsLength) == 0){
		    	alert("Favor informar local de instalação do módulo.");
		    	document.getElementById("observacoes").focus();
				return 0;
			}else if(Number(obsLength)< 200){
				adress_aux = adress_aux + '&QOBS=1&OBSERVACAO_0=' + observacoes.value.trim();
			}else if(Number(obsLength) >= 200 && Number(obsLength) <= 398){
				adress_aux = adress_aux + '&QOBS=2&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
				adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
			}else if(Number(obsLength) >= 399 && Number(obsLength) <= 597){
				adress_aux = adress_aux + '&QOBS=3&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
				adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
				adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
			}else if(Number(obsLength) >= 598 && Number(obsLength) <= 796){
				adress_aux = adress_aux + '&QOBS=4&OBSERVACAO_0=' + observacoes.value.trim().slice(Number(0),Number(199));
				adress_aux = adress_aux + '&OBSERVACAO_1=' + observacoes.value.trim().slice(Number(199),Number(398));
				adress_aux = adress_aux + '&OBSERVACAO_2=' + observacoes.value.trim().slice(Number(398),Number(597));
				adress_aux = adress_aux + '&OBSERVACAO_3=' + observacoes.value.trim().slice(Number(597),Number(796));
			}
			var val_id_equipamento = $('#item_id_modulo').val();
			if(val_id_equipamento == ""){
		    	alert("Favor informar o ID do Módulo a ser inserido.");
		    	document.getElementById("item_id_modulo").focus();
				return 0;
			}
			var val_id_equipamento = $('#item_id_modulo').val();
			var idModulo = $('#id_list option').filter(function() {
		        return this.value == val_id_equipamento;
		    }).data('label');
			adress_aux = adress_aux + '&ID_MODULO=' + idModulo;
		    var resposta_unidade = $('#resposta_unidade_').val();
			adress_aux = adress_aux + '&RESPOSTA_UNIDADE=' + resposta_unidade;
			if(resposta_unidade == "instalado"){
				if(document.getElementById("ignicao_").checked){
					adress_aux = adress_aux + '&IGNICAO=1';
	    		}else{
					adress_aux = adress_aux + '&IGNICAO=0';
	    		}
				if(document.getElementById("bloqueio_").checked){
					adress_aux = adress_aux + '&BLOQUEIO=1';
	    		}else{
					adress_aux = adress_aux + '&BLOQUEIO=0';
	    		}
				if(document.getElementById("gps_").checked){
					adress_aux = adress_aux + '&GPS=1';
	    		}else{
					adress_aux = adress_aux + '&GPS=0';
	    		}
				if(document.getElementById("gprs_").checked){
					adress_aux = adress_aux + '&GPRS=1';
	    		}else{
					adress_aux = adress_aux + '&GPRS=0';
	    		}
				if(document.getElementById("sirene_").checked){
					adress_aux = adress_aux + '&SIRENE=1';
	    		}else{
					adress_aux = adress_aux + '&SIRENE=0';
	    		}
				if(document.getElementById("panico_").checked){
					adress_aux = adress_aux + '&PANICO=1';
	    		}else{
					adress_aux = adress_aux + '&PANICO=0';
	    		}
				if(document.getElementById("rpm_").checked){
					adress_aux = adress_aux + '&RPM=1';
	    		}else{
					adress_aux = adress_aux + '&RPM=0';
	    		}
			}
			adress_aux = adress_aux + '&COD_UNIDADES_AGENDADAS=' + codUnidadeAgendada;
		}
		var adress = url_adress + "services/processaragendamento?OP_CODE=UPDATE&COD_USUARIO=" + cod_usuario + "&WORK_ID=" + workId + "&PK_COLUMN=" + pkObj;
		adress = adress + adress_aux;
		document.location.href = adress;
	}
}