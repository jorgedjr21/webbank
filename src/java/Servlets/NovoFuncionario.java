/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Models.Funcionario;
import config.Dbconfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
@WebServlet(name = "NovoFuncionario", urlPatterns = {"/funcionarios/novoFuncionario"})
public class NovoFuncionario extends HttpServlet {

    RequestDispatcher dispatcher = null;
    private PreparedStatement pstm;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NovoFuncionario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NovoFuncionario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome, senha, email, funcao;
        nome = request.getParameter("nome");
        senha = request.getParameter("senha");
        email = request.getParameter("email");
        funcao = request.getParameter("funcao");

        if (!checkCampos(nome, senha, email, funcao)) {
            dispatcher = request.getRequestDispatcher("../funcionarios/newfun.jsp");
            request.setAttribute("error", "Todos os campos são obrigatórios!");
            dispatcher.forward(request, response);
            return;
        }

        Funcionario f = new Funcionario(nome, senha, email, funcao);
        try {
            int i = insereFuncionario(f);
            if (i > 0) {
                dispatcher = request.getRequestDispatcher("../funcionarios/newfun.jsp");
                request.setAttribute("success", "Funcionário adicionado com sucesso!");
                dispatcher.forward(request, response);
            } else {
                dispatcher = request.getRequestDispatcher("../funcionarios/newfun.jsp");
                request.setAttribute("error", "Não foi possivel inserir o funcionário. Tente novamente!");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("SQLerror", e.getMessage());
            request.getRequestDispatcher("../funcionarios/erro.jsp").forward(request, response);
        }
        //processRequest(request, response);
    }

    private boolean checkCampos(String nome, String senha, String email, String funcao) {
        if (nome == null || senha == null || email == null || funcao == null) {
            return false;
        }

        if (nome.equals("") || senha.equals("") || email.equals("") || funcao.equals("")) {
            return false;
        }
        return true;
    }

    private int insereFuncionario(Funcionario f) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("INSERT INTO funcionario (Nome,Senha,Email,Funcao) VALUES(?,?,?,?)");
        pstm.setString(1, f.getNome());
        pstm.setString(2, f.getSenha());
        pstm.setString(3, f.getEmail());
        pstm.setString(4, f.getFuncao());
        return pstm.executeUpdate();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
