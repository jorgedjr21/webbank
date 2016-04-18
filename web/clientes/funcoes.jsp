<%@page import="config.CookieUtilities"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>WebBank - COM222 - Trabalho1</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/WebBank/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">COM222</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <%
                            if (CookieUtilities.findCookie(request, "cconta")) {
                                Cookie c = CookieUtilities.getCookie(request, "cnome");%>
                        <li><a href="#">Ol�, <%= c.getValue()%></a></li>
                        <li><a href="../clientes/funcoes.jsp">Fun��es</a></li>                        
                            <% } %>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <%
                            if (!CookieUtilities.findCookie(request, "cconta")) {
                        %>
                        <li><a href="/WebBank/index.jsp">Login Clientes</a></li>
                            <%} else {%>
                        <li><a href="../clientes/logout">Logout</a></li>
                            <%}%>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="content">
            <div class="row">
                <%
                    if (!CookieUtilities.findCookie(request, "cconta")) {
                %>
                <div class="row"> 
                    <div class="col-md-12">
                        <div class="alert alert-danger text-center">
                            <strong>Erro!</strong>Voc� precisa <a href="/WebBank" class="alert-link">logar</a> para ter acesso a esta p�gina.
                        </div>
                    </div>
                </div>

                <%} else{
                %>

                <div class="row">
                    <div class="col-md-4 col-md-offset-3">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">Opera��es</h3>
                            </div>
                            <div class="panel-body">
                                <ul class="text-center list-unstyled">
                                    <li>
                                        <a href="../clientes/saque.jsp"><i class="fa fa-arrow-up fa-lg text-danger"></i> Transfer�ncia</a>
                                    </li>
                                    <li>
                                        <a href="../clientes/deposito.jsp"><i class="fa fa-arrow-down fa-lg text-success"></i> Saldo</a>
                                    </li>
                                    <li>
                                        <a href="../clientes/pagamento.jsp"><i class="fa fa-barcode fa-lg text-warning"></i> Extrato</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>



                <%}%>
            </div>
        </div>
        <script src="/WebBank/js/jquery-1.12.2.min.js"/>
        <script src="/WebBank/js/bootstrap.min.js"/> 
    </body>
</html>
