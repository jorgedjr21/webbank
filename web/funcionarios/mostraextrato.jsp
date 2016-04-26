<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.ResultSet"%>
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
                        <li><a href="../funcionarios/funcoes.jsp">Funções</a></li>                        
                            <% } %>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <%
                            if (!CookieUtilities.findCookie(request, "fnome")) {
                        %>
                        <li><a href="/WebBank/funcionarios/login.jsp">Login Clientes</a></li>
                            <%} else {%>
                        <li><a href="../funcionarios/logout">Logout</a></li>
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
                            <strong>Erro!</strong>Você precisa <a href="/WebBank" class="alert-link">logar</a> para ter acesso a esta página.
                        </div>
                    </div>
                </div>

                <%} else{
                %>

                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">Extrato</h3>
                            </div>
                            <div class="panel-body">
                                <% 
                                 ResultSet transacoes = (ResultSet) request.getAttribute("transacoes");
                                %>
                                <table class="table table-striped">
                                    <thead>
                                        <tr >
                                            <th class="text-center">Código</th>
                                            <th class="text-center">Tipo</th>
                                            <th class="text-center">Conta</th>
                                            <th class="text-center">Conta destino</th>
                                            <th class="text-center">Valor</th>
                                            <th class="text-center">Data</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% while(transacoes.next()){
                                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                            String date = format.format(transacoes.getTimestamp("data"));
                                        %>
                                        <tr>
                                            <td class="text-center"><%= transacoes.getInt("Codigo") %></td>
                                            <td class="text-center"><%= transacoes.getString("Tipo") %></td>
                                            <td class="text-center"><%= transacoes.getInt("Nro_Conta") %></td>
                                            <% if(transacoes.getInt("Nro_Conta_Transf") <= 0 ){%>
                                                <td class="text-center" >-</td>
                                            <%}else{%>
                                                <td class="text-center" ><%= transacoes.getInt("Nro_Conta_Transf") %></td>
                                                <%}%>
                                            <td class="text-center" >R$ <%= transacoes.getDouble("Valor") %></td>
                                            <td class="text-center"><%= date %></td>
                                        </tr>
                                        
                                        <%}%>
                                    </tbody>
                                </table>
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
