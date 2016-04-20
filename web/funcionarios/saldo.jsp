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
                            if (CookieUtilities.findCookie(request, "fnome")) {
                                Cookie c = CookieUtilities.getCookie(request, "fnome");
                        %>
                        <li><a href="#">Ol�, <%= c.getValue()%></a></li>
                        <li><a href="../funcionarios/funcoes.jsp">Fun��es</a></li>                        
                            <% } %>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <%
                            if (!CookieUtilities.findCookie(request, "fnone")) {
                        %>
                        <li><a href="/WebBank/funcionarios/login.jsp">Login Clientes</a></li>
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
                    if (!CookieUtilities.findCookie(request, "fnome")) {
                %>
                <div class="row"> 
                    <div class="col-md-12">
                        <div class="alert alert-danger text-center">
                            <strong>Erro!</strong>Voc� precisa <a href="/WebBank/funcionarios/login.jsp" class="alert-link">logar</a> para ter acesso a esta p�gina.
                        </div>
                    </div>
                </div>

                <%} else {
                %>
                
                <%
                    if (request.getAttribute("error") != null) {
                %>
                <div class="row">
                    <div class="col-md-5 col-md-offset-3">
                        <div class="alert alert-danger text-center">
                            <strong> <%= request.getAttribute("error")%></strong>
                        </div>
                    </div>
                </div>
                <%}%>
                
                <%
                if (request.getAttribute("success") != null) {
            %>
            <div class="row">
                <div class="col-md-5 col-md-offset-3">
                    <div class="alert alert-success text-center">
                        <p><strong> <%= request.getAttribute("success") %></strong></p>
                        
                        
                    </div>
                </div>
            </div>
            <%}%>
            

                <form name="saldo" action="../funcionarios/saldo" method="POST">
                    <div class="row">
                        <div class="col-md-5 col-md-offset-3">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Saldo</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Conta para Consulta</label>
                                            <input class="form-control" id="focusedInput" name="conta" type="text" placeholder="Conta para Consulta">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-5 col-md-offset-3">
                            <button type="submit" class="btn btn-primary btn-block btn-lg">Consultar </button>
                        </div>
                    </div>
                </form>   


                <%}%>
            </div>
        </div>
        <script src="/WebBank/js/jquery-1.12.2.min.js"/>
        <script src="/WebBank/js/bootstrap.min.js"/> 
    </body>
</html>
