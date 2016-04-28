/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import config.Dbconfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jorge
 */
@WebServlet(name = "LoginCliente", urlPatterns = {"/clientes/login"})
public class LoginCliente extends HttpServlet {

    private PreparedStatement pstm;
    private ResultSet result;
    private RequestDispatcher dispatcher = null;

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
            out.println("<title>Servlet LoginCliente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginCliente at " + request.getContextPath() + "</h1>");
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

        Connection c = Dbconfig.getConnection();
        String cpf = request.getParameter("cpf");
        String contastr = request.getParameter("conta");
        String senha = request.getParameter("senha");
        if (!checkCampos(cpf, contastr, senha)) {
            dispatcher = request.getRequestDispatcher("../index.jsp");
            request.setAttribute("error", "Todos os campos são obrigatórios!");
            dispatcher.forward(request, response);
            return;
        }

        int conta = Integer.parseInt(contastr);

        try {
            pstm = c.prepareStatement("SELECT * FROM conta c"
                    + " JOIN correntista co ON (c.Primeiro_Corr = co.CPF || c.Segundo_Corr = co.CPF || c.Terceiro_Corr = co.CPF)"
                    + " WHERE co.CPF = ? and c.Numero = ? and co.Senha = ?");
            pstm.setString(1, cpf);
            pstm.setInt(2, conta);
            pstm.setString(3, senha);

            result = pstm.executeQuery();
            if (result.next()) {

                Cookie cf = new Cookie("cnome", result.getString("Nome"));
                Cookie cn = new Cookie("cconta", String.valueOf(result.getInt("Numero")));
                Cookie ccpf = new Cookie("ccpf", result.getString("CPF"));
                response.addCookie(cf);
                response.addCookie(cn);
                response.addCookie(ccpf);
                response.sendRedirect("../clientes/funcoes.jsp");
            } else {
                request.setAttribute("error", "Correntista informado não existe ou dados incorretos. Tente novamente!");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            dispatcher = request.getRequestDispatcher("../clientes/erro.jsp");
            request.setAttribute("SQLerror", e.getMessage());
            dispatcher.forward(request, response);
            return;
        }

        //processRequest(request, response);
    }

    private boolean checkCampos(String cpf, String conta, String senha) {
        if (cpf == null || senha == null || conta == null) {
            return false;
        }

        if (cpf.equals("") || senha.equals("") || conta.equals("")) {
            return false;
        }
        return true;
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
