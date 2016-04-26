/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Models.Conta;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
@WebServlet(name = "SaldoF", urlPatterns = {"/funcionarios/saldo"})
public class SaldoF extends HttpServlet {

    RequestDispatcher dispatcher = null;
    private ResultSet result;
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
            out.println("<title>Servlet SaldoF</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaldoF at " + request.getContextPath() + "</h1>");
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
        String contastr = request.getParameter("conta");

        if (!checkContastr(contastr)) {
            dispatcher = request.getRequestDispatcher("../funcionarios/saldo.jsp");
            request.setAttribute("error", "A conta a ser consultada é obrigatória!");
            dispatcher.forward(request, response);
        }

        int Numero = Integer.parseInt(contastr);
        try {

            Conta c = getConta(Numero);
            
            if (c == null) {
                dispatcher = request.getRequestDispatcher("../funcionarios/saldo.jsp");
                request.setAttribute("error", "Conta inexistente!");
                dispatcher.forward(request, response);
            }
            
            System.out.println(c.getLimite());
            dispatcher = request.getRequestDispatcher("../funcionarios/saldo.jsp");
            request.setAttribute("success", "O saldo atual da conta é: R$" + c.getSaldo() + " com limite de: R$" + c.getLimite());
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            dispatcher = request.getRequestDispatcher("../funcionarios/erro.jsp");
            request.setAttribute("error", e.getMessage());
            dispatcher.forward(request, response);
        }

        //processRequest(request, response);
    }

    private Conta getConta(int Conta) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM conta WHERE Numero = ? ");
        pstm.setInt(1, Conta);
        result = pstm.executeQuery();
        if (result.next()) {
            return new Conta(result.getInt("Numero"), result.getString("Primeiro_Corr"),
                    result.getString("Segundo_Corr"), result.getString("Terceiro_Corr"),
                    result.getDouble("Saldo"), result.getDouble("Limite"));
        }
        return null;
    }

    private boolean checkContastr(String conta) {
        if (conta == null || conta.equals("")) {
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
