<!DOCTYPE html>
<!--ZIRIX CONTROL CENTER - MAIN PAGE
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
	/*String msg = null;
	if(session.getAttribute("msg") != null){
		msg = (String) session.getAttribute("msg");
	}*/
	String sessionId = null;
	sessionId = (String) session.getId();
%>

<%
	String[] pkVal = {user};
	ZxAccessControlBean bean = new ZxAccessControlBean(pkVal);
	Vector<String[]> permissaoUsuarioList = bean.getPermissaoUsuario();
	Vector<String[]> telaList = bean.getCodTela();
	
	Integer[] permissionTela = new Integer[telaList.size()];

	for (int i=0;i < permissaoUsuarioList.size();i++){
		permissionTela[Integer.parseInt(permissaoUsuarioList.elementAt(i)[0].trim())-1] = Integer.parseInt(permissaoUsuarioList.elementAt(i)[1].trim());
	}
%>

<html lang="pt-br">
    <head>
        <title>ZX CC</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <link rel="stylesheet" href="css/Bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="css/nav.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/modal.css">
        <link rel="stylesheet" href="css/sizes.css">
        <link rel="stylesheet" href="css/button.css">
    </head>
    <body>
        <div class="container">        
            <header>
           		&nbsp;Bem-vindo: <%=bean.getNomeUsuario()%>
           		<div id="cod_usuario" style="visibility: hidden;"><%=user%></div>
            </header>
            <div class="page-background">
                <nav id="topNav">
                    <ul>
                        <li class="li-menu">
                            <a <%if(permissionTela[0] == ZXCCConstantsServlet.READ || permissionTela[0] == ZXCCConstantsServlet.WRITE) {%>
                            id="menu_opr">Operacional</a> <%
 	//TELA = 1
 %>
                            <ul class="ul-submenu">
                                <li class="li-submenu">
                                    <a <%if(permissionTela[1] == ZXCCConstantsServlet.WRITE){%> 
                                    id="menu_opr_cad">Cadastro</a> <%
 	//TELA = 2
 %>
                                    <ul class="ul-submenu-filho">
                                        <li>
                                            <a <%if(permissionTela[2] == ZXCCConstantsServlet.WRITE){%> 
                                            id="menu_opr_cad_cli" class="modal_btn" onclick="modal_click(menu_opr_cad_cli)">Cliente</a> <%
 	//TELA = 3
 %>
											<%
												}else{
											%>
											id="no_permission" class="modal_btn">Cliente</a> <%
 	//TELA = 3
 %>
											<%
												}
											%>
                                        </li>
                                        <li>
                                            <a <%if(permissionTela[3] == ZXCCConstantsServlet.WRITE){%> 
                                            id="menu_opr_cad_eqp" class="modal_btn" onclick="modal_click(menu_opr_cad_eqp)">Equipamento</a> <%
 	//TELA = 4
 %>
											<%
												}else{
											%>
											id="no_permission" class="modal_btn">Equipamento</a> <%
 	//TELA = 4
 %>
											<%
												}
											%>
                                        </li>
                                        <li class="last">
                                            <a <%if(permissionTela[4] == ZXCCConstantsServlet.WRITE){%> 
                                            id="menu_opr_cad_chp" class="modal_btn" onclick="modal_click(menu_opr_cad_chp)">Sim Card</a> <%
 	//TELA = 5
 %>
											<%
												}else{
											%>
											id="no_permission" class="modal_btn">Sim Card</a> <%
 	//TELA = 5
 %>
											<%
												}
											%>
                                        </li>
                                    </ul>
                                    <%
                                    	}else{
                                    %>
									id="no_permission" class="modal_btn" onclick="modal_click()">Cadastro</a> <%
 	//TELA = 2
 %>
									<%
										}
									%>
                                </li>
                                <li class="li-submenu-last">
                                    <a <%if(permissionTela[5] == ZXCCConstantsServlet.READ || permissionTela[5] == ZXCCConstantsServlet.WRITE) {%>
                                    id="menu_opr_con" class="modal_btn" onclick="modal_click(menu_opr_con)">Consultas</a> <%
 	//TELA = 6
 %>
                                    <%
                                    	}else{
                                    %>
									id="no_permission" class="modal_btn">Consultas</a> <%
 	//TELA = 6
 %>
									<%
										}
									%>
                                </li>
                            </ul>
                            <%
                            	}else{
                            %>
                            id="no_permission" class="modal_btn">Operacional</a> <%
 	//TELA = 1
 %>
                            <%
                            	}
                            %>
                        </li>
                        <li class="li-menu">
                            <a <%if(permissionTela[6] == ZXCCConstantsServlet.READ || permissionTela[6] == ZXCCConstantsServlet.WRITE) {%>
                            id="menu_adm">Administrativo</a> <%
 	//TELA = 7
 %>
                            <ul class="ul-submenu">
                                <li class="li-submenu">
                                    <a <%if(permissionTela[7] == ZXCCConstantsServlet.WRITE){%> 
                                    id="menu_adm_cad">Cadastro</a> <%
 	//TELA = 8
 %>
                                    <ul class="ul-submenu-filho">
                                        <li class="last">
                                    	<a <%if(permissionTela[8] == ZXCCConstantsServlet.WRITE){%> 
                                        id="menu_adm_cad_ven" class="modal_btn" onclick="modal_click(menu_adm_cad_ven)">Vendedor</a> <%
 	//TELA = 9
 %>
	                                    <%
	                                    	}else{
	                                    %>
										id="no_permission" class="modal_btn">Vendedor</a> <%
 	//TELA = 9
 %>
										<%
											}
										%>
                                        </li>
                                    </ul>
                                    <%
                                    	}else{
                                    %>
									id="no_permission" class="modal_btn">Cadastro</a> <%
 	//TELA = 8
 %>
									<%
										}
									%>
                                </li>
                                <li class="li-submenu-last">
                                    <a <%if(permissionTela[9] == ZXCCConstantsServlet.READ || permissionTela[9] == ZXCCConstantsServlet.WRITE) {%>
                                    id="menu_adm_con" class="modal_btn" onclick="modal_click(menu_adm_con)">Consultas</a> <%
 	//TELA = 10
 %>
                                    <%
                                    	}else{
                                    %>
									id="no_permission" class="modal_btn">Consultas</a> <%
 	//TELA = 10
 %>
									<%
										}
									%>
                                </li>
                            </ul>
                            <%
                            	}else{
                            %>
                            id="no_permission" class="modal_btn">Administrativo</a> <%
 	//TELA = 7
 %>
                            <%
                            	}
                            %>
                        </li>
                        <li class="li-menu">
                            <a <%if(permissionTela[10] == ZXCCConstantsServlet.READ || permissionTela[10] == ZXCCConstantsServlet.WRITE) {%>
                            id="menu_com">Comercial</a> <%
 	//TELA = 11
 %>
                            <ul class="ul-submenu">
                                <li class="li-submenu">
                                	<a <%if(permissionTela[17] == ZXCCConstantsServlet.WRITE){%> 
                                    id="menu_com_cad">Cadastro</a> <%
 	//TELA = 18
 %>
                                    <ul class="ul-submenu-filho">
                                    	<li>
                                            <a <%if(permissionTela[18] == ZXCCConstantsServlet.WRITE){%> 
                                            id="menu_com_cad_cli" class="modal_btn" onclick="modal_click(menu_com_cad_cli)">Prospecção</a> <%
 	//TELA = 19
 %>
											<%
												}else{
											%>
											id="no_permission" class="modal_btn">Prospecção</a> <%
 	//TELA = 19
 %>
											<%
												}
											%>
                                        </li>
                                		<li class="last">
		                                    <a <%if(permissionTela[11] == ZXCCConstantsServlet.WRITE) {%>
                                    		id="menu_com_cad_ped" class="modal_btn" onclick="modal_click(menu_com_cad_ped)">Novo Pedido</a> <%
 	//TELA = 12
 %>
                                    		<%
                                    			}else{
                                    		%>
											id="no_permission" class="modal_btn">Novo Pedido</a> <%
 	//TELA = 12
 %>
											<%
												}
											%>
                                		</li>
                                    </ul>
                                    <%
                                    	}else{
                                    %>
									id="no_permission" class="modal_btn">Cadastro</a> <%
 	//TELA = 18
 %>
									<%
										}
									%>
                                </li>
                                <li class="last">
                                    <a <%if(permissionTela[16] == ZXCCConstantsServlet.READ || permissionTela[16] == ZXCCConstantsServlet.WRITE) {%>
                                    id="menu_com_con" class="modal_btn" onclick="modal_click(menu_com_con)">Consultas</a> <%
    //TELA = 17
%>
                                    <%}else{%>
									id="no_permission" class="modal_btn">Consultas</a> <%
	//TELA = 17
%>
									<%}%>
                                </li>
                            </ul>
                            <%}else{%>
                            id="no_permission" class="modal_btn">Comercial</a> <%
    //TELA = 11
%>
                            <%}%>
                        </li>
                        <li class="li-menu">
                            <a id="menu_rel" class="modal_btn" onclick="modal_click(menu_rel)">Relatórios</a> <%
    //TELA = 13
%>
                        </li>
                        <li class="li-menu">
                            <a id="menu_cfg" class="modal_btn" onclick="modal_click(menu_cfg)">Configurações</a> <%
    //TELA = 14
%>
                        </li>
                        <li class="li-menu">
                            <a id="menu_sos" class="modal_btn" onclick="modal_click(menu_sos)">Ajuda</a> <%
    //TELA = 15
%>
                        </li>
                        <li class="li-menu">
                            <a id="menu_ext" class="modal_btn" onclick="modal_click(menu_ext)">Sair</a> <%
    //TELA = 16
%>
                        </li>
                    </ul>
                </nav>
                <section class="tarefas">
                	<iframe src="tarefas.jsp?COD_USUARIO=<%=user%>" name="tarefasIFrame" frameborder="0" width="515px" height="100%">

                	</iframe>
                </section>
                <section class="conteudo">
                <%if(ZXMain.LOCAL_.compareTo("DEV") == 0){%>
	                <br>
	                <br>
	                <br>
	               	<div class="checkboxConfirm1">
				  		<input type="checkbox" value="1" id="checkboxConfirmInput" name="teste_nome" style="visibility: hidden;">
					  	<label for="checkboxConfirmInput"></label>
					</div>
					<br><br><br>
	               	URL_ADRESS = <%=ZXMain.getAdress()%>
	                <br>
	                DB_NAME = <%=ZXMain.getDbName()%>
	                <br>
	                LOCAL = <%=ZXMain.getLocal()%>
	                <br>
	                sessionId = <%=sessionId%>
                <%}%>
                </section>
            </div>
            <footer>
               <h1>Zirix - Control Center</h1>
            </footer>
            <div class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" id="modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!--Aqui entra o modal a ser chamado-->
                    </div>
                </div>
            </div>        
        </div>    
        <script src="js/jquery.js"></script>
        <script src="js/modernizr.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/menu_functions.js"></script>
        <script src="js/page_functions.js"></script>
        <script>window.setInterval("reloadIFrame(<%=user%>);", 30000); </script>
  </body>
</html>