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
                        <li><a href="#">Ol�, <%= c.getValue()%></a></li>
                        <li><a href="../funcionarios/funcoes.jsp">Fun��es</a></li>  

                        <% } else {
                                response.sendRedirect("../funcionarios/login.jsp");
                            } %>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <%
                            if (!CookieUtilities.findCookie(request, "flogin")) {
                        %>
                        <li><a href="../funcionarios/login.jsp">Login Funcion�rios</a></li>
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
                        <strong>Erro!</strong>Voc� precisa <a href="./login.jsp" class="alert-link">logar</a> para ter acesso a esta p�gina.
                    </div>
                </div>
            </div>

            <%} else {
            %>
            <%
                if (request.getAttribute("success") != null) {
            %>
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="alert alert-success text-center">
                        <p><strong> <%= request.getAttribute("success") %></strong></p>
                        <p>O n�mero da conta � -> <strong><%= request.getAttribute("conta") %></strong></p>
                        
                    </div>
                </div>
            </div>
            <%}%>
            
            <%
                if (request.getAttribute("error") != null) {
            %>
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="alert alert-danger text-center">
                        <strong> <%= request.getAttribute("error") %></strong>
                    </div>
                </div>
            </div>
            <%}%>
            <form name="newacc" action="../funcionarios/newacc" method="POST">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title">Conta</h3>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Saldo</label>
                                            <input class="form-control" id="focusedInput" name="saldo" type="text" placeholder="Saldo">
                                        </div>
                                    </div>


                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Limite</label>
                                            <input class="form-control" id="focusedInput" name="limite" type="text" placeholder="Limite">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="alert alert-warning text-center">
                            <p>Todas as contas devem ter <strong>obrigatoriamente</strong> um correntista principal!</p>
                            <p>Os correntista 3 <strong>n�o ter� a ordem trocada</strong> caso o correntista 2 n�o seja cadastrado!</p>
                        </div>
                    </div>

                </div>
                <div class="row">

                    <div class="col-md-12">  
                        <div class="col-md-4 ">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Correntista Principal</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Nome</label>
                                            <input class="form-control" id="focusedInput" name="nome1" type="text" placeholder="Nome do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Email</label>
                                            <input class="form-control" id="focusedInput" name="email1" type="text" placeholder="Email do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Endere�o</label>
                                            <input class="form-control" id="focusedInput" name="endereco1" type="text" placeholder="Endere�o do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">CPF</label>
                                            <input class="form-control" id="focusedInput" name="cpf1" type="text" placeholder="CPF do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Senha</label>
                                            <input class="form-control" id="focusedInput" name="senha1" type="password" placeholder="Senha do Correntista">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Correntista 2</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Nome</label>
                                            <input class="form-control" id="focusedInput" name="nome2" type="text" placeholder="Nome do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Email</label>
                                            <input class="form-control" id="focusedInput" name="email2" type="text" placeholder="Email do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Endere�o</label>
                                            <input class="form-control" id="focusedInput" name="endereco2" type="text" placeholder="Endere�o do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">CPF</label>
                                            <input class="form-control" id="focusedInput" name="cpf2" type="text" placeholder="CPF do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Senha</label>
                                            <input class="form-control" id="focusedInput" name="senha2" type="password" placeholder="Senha do Correntista">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Correntista 3</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Nome</label>
                                            <input class="form-control" id="focusedInput" name="nome3" type="text" placeholder="Nome do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Email</label>
                                            <input class="form-control" id="focusedInput" name="email3" type="text" placeholder="Email do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Endere�o</label>
                                            <input class="form-control" id="focusedInput" name="endereco3" type="text" placeholder="Endere�o do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">CPF</label>
                                            <input class="form-control" id="focusedInput" name="cpf3" type="text" placeholder="CPF do Correntista">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label" for="focusedInput">Senha</label>
                                            <input class="form-control" id="focusedInput" name="senha3" type="password" placeholder="Senha do Correntista">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <button type="submit" class="btn btn-success btn-block btn-lg">Cadastrar Conta <i class="fa fa-plus-circle"></i></button>
                    </div>
                </div>
            </form>



            <%}%>
        </div>
        <script src="/WebBank/js/jquery-1.12.2.min.js"/>
        <script src="/WebBank/js/bootstrap.min.js"/> 
    </body>
</html>
