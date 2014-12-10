/*
ZIRIX CONTROL CENTER - CONTROLE DOS MENUS
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVASCRIPT E AJAX
*/
xmlDoc=loadXMLDoc("js/VariaveisZXCC.xml");
var url_adress = xmlDoc.getElementsByTagName("adress")[0].textContent;
var ambiente = xmlDoc.getElementsByTagName("local")[0].textContent;
var cod_usuario_;

var nav = $("#topNav");
nav.find("li").each(function(){
    if ($(this).find("ul").length > 0) {
        $("<span>").text("^").appendTo($(this).children(":first"));
    }
});
nav.find(".li-menu").each(function() {
    if ($(this).find(".ul-submenu").length > 0) {
        $(this).on("click",(function() {
            $(this).find(".ul-submenu").stop(true, true).slideDown();
        }));
        $(this).mouseleave(function() {
            $(this).find(".ul-submenu").stop(true, true).slideUp();
        });
    }
});
nav.find(".li-menu").each(function() {
    if ($(this).find(".ul-submenu-last").length > 0) {
        $(this).on("click",(function() {
            $(this).find(".ul-submenu-last").stop(true, true).slideDown();
        }));
        $(this).mouseleave(function() {
            $(this).find(".ul-submenu-last").stop(true, true).slideUp();
        });
    }
});
nav.find(".li-submenu").each(function(){
    if ($(this).find(".ul-submenu-filho").length > 0) {
        $(this).mouseenter(function() {
            $(this).find(".ul-submenu-filho").stop(true, true).slideDown();
        });
        $(this).mouseleave(function() {
            $(this).find(".ul-submenu-filho").stop(true, true).slideUp();
        });
    }
});
nav.find(".li-submenu-last").each(function(){
    if ($(this).find(".ul-submenu-filho").length > 0) {
        $(this).mouseenter(function() {
            $(this).find(".ul-submenu-filho").stop(true, true).slideDown();
        });
        $(this).mouseleave(function() {
            $(this).find(".ul-submenu-filho").stop(true, true).slideUp();
        });
    }
});

function modal_click(id){
	cod_usuario = document.getElementById("cod_usuario");
    this.menu_option = id.id;
    switch(menu_option) {
        case "menu_opr_cad_cli":
            $.ajax({
                url: url_adress + "cadastro_cliente.jsp",
                success: function(result) {
                    $('.modal-content').html(result);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_opr_cad_eqp":
            $.ajax({
                url: url_adress + "cadastro_equipamento.jsp",
                success: function(result) {
                    $('.modal-content').html(result);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_opr_cad_chp":
            $.ajax({
                url: url_adress + "cadastro_chip.jsp",
                success: function(result) {
                    $('.modal-content').html(result);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_opr_con":
            $.ajax({
                url: url_adress + "pop_up.html",
                success: function(result) {
                    var html = jQuery('<div>').html(result);
                    var content = html.find("div#operacional-consulta-content").html();
                    $('.modal-content').html(content);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_adm_cad_ven":
            $.ajax({
                url: url_adress + "cadastro_vendedor.jsp",
                success: function(result) {
                    $('.modal-content').html(result);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_com_cad_cli":
            $.ajax({
                url: url_adress + "cadastro_cli_prospect.jsp",
                success: function(result) {
                    $('.modal-content').html(result);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_com_cad_ped":
            $.ajax({
                url: url_adress + "cadastro_novo_pedido.jsp",
                success: function(result) {
                    $('.modal-content').html(result);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
        	break;
        case "menu_com_con":
            $.ajax({
                url: url_adress + "pop_up.html",
                success: function(result) {
                    var html = jQuery('<div>').html(result);
                    var content = html.find("div#comercial-consulta-content").html();
                    $('.modal-content').html(content);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
        case "menu_ext":
        	if(confirm('Deseja sair do sitema?')){
        		document.location.href = url_adress + "services/logout";
        	}
        	break;
        case "no_permission":
        	alert('Usuário sem permissão para essa função!');
        	break;
        default:
            $.ajax({
                url: url_adress + "pop_up.html",
                success: function(result) {
                    var html = jQuery('<div>').html(result);
                    var content = html.find("div#em-construcao-content").html();
                    $('.modal-content').html(content);
                    $('.modal').modal({backdrop:'static'});
                },
                error: function(e){
                    alert('error');
                }
            });
            break;
    }
}

$('.modal-content').on('click', '#cancel_modal', function(e){
    e.preventDefault();
    $('.modal-content').html('');
    $('.modal').modal('hide');
});

window.addEventListener("message", callModalTarefas, false);

function SCHED_WORK_FUNCTION(tela){	
	switch(tela){
	case "agend":
		$.ajax({
	        url: url_adress + "agendamento.jsp?WORK_ID=0&COD_USUARIO=1&AREA=AGEND&PK_OBJ=1",
	        success: function(result) {
	            $('.modal-content').html(result);
	            $('.modal').modal({backdrop:'static'});
	            carregar_dados_agendamento_pedido_function();
	        },
	        error: function(e){
	            alert('error');
	        }
	    });
		break;
	case "visualiza":
		$.ajax({
	        url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID=0&COD_USUARIO=1&AREA=CAD_GS&PK_OBJ=1",
	        success: function(result) {
	            $('.modal-content').html(result);
	            $('.modal').modal({backdrop:'static'});
	            carregar_dados_confirmacao_pedido_function();
	        },
	        error: function(e){
	            alert('error');
	        }
	    });
		break;
	case "scheded":
		$.ajax({
	        url: url_adress + "consulta_agendamento.jsp?WORK_ID=0&COD_USUARIO=1&AREA=CONSULAGEND&PK_OBJ=1",
	        success: function(result) {
	            $('.modal-content').html(result);
	            $('.modal').modal({backdrop:'static'});
	            carregar_dados_agendamento_pedido_function();
	        },
	        error: function(e){
	            alert('error');
	        }
	    });
		break;
	}
}

function SCHED_WORK_START_FUNCTION(work_id,service_name,pk_column){
	var message = work_id + "//" + service_name + "//" + pk_column;
	window.parent.postMessage(message,'*');
}

function SCHED_WORK_END_FUNCTION(work_id){
	var CheckBox = document.getElementById("checkboxConfirmCliProsp").checked;
    if(CheckBox){
		cod_usuario = document.getElementById("cod_usuario");
		cod_usuario_ = cod_usuario.innerHTML.trim();
		var adress = "";
		adress = url_adress + 'services/startservlet?OP_CODE=ENDWORK&COD_USUARIO=' + cod_usuario_ + '&WORK_ID=' + work_id;
		document.location.href = adress;
    }
}

function callModalTarefas(event){
	cod_usuario = document.getElementById("cod_usuario");
	cod_usuario_ = cod_usuario.innerHTML.trim();
	var data = event.data;
	var work_id = "";
	var service_name = "";
	var stringSize = data.length;
	var selectedPK = "";
	
	if(stringSize > 0){
		for(var i=0; i<stringSize; i++){
			if(data.charAt(i+1) == "/" && data.charAt(i+2) == "/"){
				work_id = data.slice(Number(0), Number(i+1));
				restData = data.slice(Number(i+3),Number(stringSize));
				break;
			}
		}
		stringSize = restData.length;
		for(var i=0; i<stringSize; i++){
			if(restData.charAt(i+1) == "/" && restData.charAt(i+2) == "/"){
				service_name = restData.slice(Number(0),Number(i+1));
				selectedPK = restData.slice(Number(i+3),Number(stringSize));
				break;
			}
		}
	    switch(service_name) {
	    case "adm_verifica_novo_pedido":
	        $.ajax({
	            url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=ADM&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_confirmacao_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	        break;
	    case "fin_verifica_spc_serasa":
	        $.ajax({
	            url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=FIN&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_confirmacao_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	        break;
	    case "opr_cad_globalsearch":
	        $.ajax({
	            url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=CAD_GS&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_confirmacao_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	    	break;
	    case "opr_cad_syscom":
	        $.ajax({
	            url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=CAD_SYSCOM&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_confirmacao_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	    	break;
	    case "opr_cad_zxlog":
	        $.ajax({
	            url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=CAD_ZXLOG&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_confirmacao_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	    	break;
	    case "opr_separacao_equipamento":
	        $.ajax({
	            url: url_adress + "visualizacao_novo_pedido.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=SEP_EQUIP&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_confirmacao_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	    	break;
	    case "opr_agendamento":
	        $.ajax({
	            url: url_adress + "agendamento.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=AGEND&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_agendamento_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	    	break;
	    case "opr_acompanha_inst":
	        $.ajax({
	            url: url_adress + "consulta_agendamento.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=ACOMP_AGEND&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_agendamento_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	    	break;
	    case "adm_finaliza_processo":
	    	//TODO
	        /*$.ajax({
	            url: url_adress + "consulta_agendamento.jsp.jsp?WORK_ID="+work_id+"&COD_USUARIO="+cod_usuario_+"&AREA=ADM_FINAL&PK_OBJ="+selectedPK,
	            success: function(result) {
	                $('.modal-content').html(result);
	                $('.modal').modal({backdrop:'static'});
	                carregar_dados_agendamento_pedido_function();
	            },
	            error: function(e){
	                alert('error');
	            }
	        });*/
	    	break;
	    default:
	        $.ajax({
	            url: url_adress + "pop_up.html",
	            success: function(result) {
	                var html = jQuery('<div>').html(result);
	                var content = html.find("div#em-construcao-content").html();
	                $('.modal-content').html(content);
	                $('.modal').modal({backdrop:'static'});
	            },
	            error: function(e){
	                alert('error');
	            }
	        });
	        break;
	    } 
	}else{
		alert("Serviço não encontrado!");
	} 
}