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
                                Cookie c = CookieUtilities.getCookie(request, "fnome");%>
                        <li><a href="#">Olá, <%= c.getValue()%></a></li>
                        <li><a href="../clientes/funcoes.jsp">Funções</a></li>  
                        <% } else {
                                response.sendRedirect("/WebBank");
                            } %>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <%
                            if (!CookieUtilities.findCookie(request, "flogin")) {
                        %>
                        <li><a href="../funcionarios/login.jsp">Login Funcionários</a></li>
                            <%} else {%>
                        <li><a href="../funcionarios/logout">Logout</a></li>
                            <%}%>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container-fluid">
            <%
                if (!CookieUtilities.findCookie(request, "flogin")) {
            %>
            <div class="row"> 
                <div class="col-md-12">
                    <div class="alert alert-danger text-center">
                        <strong>Erro!</strong>Você precisa <a href="./login.jsp" class="alert-link">logar</a> para ter acesso a esta página.
                    </div>
                </div>
            </div>

            <%} else
            %>
            <div class="row">
                <%
                    if (request.getAttribute("SQLerror") != null) {
                        String message = request.getAttribute("SQLerror").toString();
                %>
                <div class="col-md-4 col-md-offset-4">
                    <div class="alert alert-danger text-center">
                        <%= message %>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
        <script src="/WebBank/js/jquery-1.12.2.min.js"/>
        <script src="/WebBank/js/bootstrap.min.js"/> 
    </body>
</html>
