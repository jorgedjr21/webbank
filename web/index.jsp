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
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="./funcionarios/login.jsp">Login Funcionários</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="content">

            <div class="row">
                <div class="col-md-5 col-md-offset-3">
                    <%
                        if (request.getAttribute("error") != null) {
                    %>
                    <div class="alert alert-danger text-center">
                        <strong>Erro!</strong><br><%= request.getAttribute("error")%>
                    </div>
                    <%}%>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Login de Correntista</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form" method="POST" action="./clientes/login">
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <label class="control-label" for="focusedInput">CPF</label>
                                            <input class="form-control" name="cpf" id="focusedInput" type="text" placeholder="Números sem pontos ou traços.">
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-md-6">
                                            <label class="control-label" for="focusedInput">Nº da conta</label>
                                            <input class="form-control" name="conta" id="focusedInput" type="text" placeholder="Nº da conta">
                                        </div>
                                        <div class="col-md-6">
                                            <label class="control-label" for="focusedInput">Senha</label>
                                            <input class="form-control" name="senha" id="focusedInput" type="password" placeholder="Senha">
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-md-12">
                                        <button class="btn btn-success btn-block btn-lg">Login</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="/WebBank/js/jquery-1.12.2.min.js"/>
        <script src="/WebBank/js/bootstrap.min.js"/> 
    </body>
</html>
