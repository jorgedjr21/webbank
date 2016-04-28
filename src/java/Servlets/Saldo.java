/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Models.Conta;
import config.CookieUtilities;
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
 * @author Jorge
 */
@WebServlet(name = "Saldo", urlPatterns = {"/clientes/saldo"})
public class Saldo extends HttpServlet {

    private RequestDispatcher dispatcher = null;
    private PreparedStatement pstm;
    private ResultSet result;

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
            out.println("<title>Servlet Saldo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Saldo at " + request.getContextPath() + "</h1>");
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

        //response.sendRedirect("../clientes/saldo.jsp");
        if (CookieUtilities.findCookie(request, "cconta") && CookieUtilities.findCookie(request, "ccpf")) {
            Cookie cconta = CookieUtilities.getCookie(request, "cconta");
            Cookie ccpf = CookieUtilities.getCookie(request, "ccpf");
            String contastr = cconta.getValue();

            int conta = Integer.parseInt(contastr);
            String cpf = ccpf.getValue();
            try {
                Conta c = getConta(conta, cpf);
                request.setAttribute("numero", c.getNumero());
                request.setAttribute("saldo", c.getSaldo());
                request.setAttribute("limite", c.getLimite());

                dispatcher = request.getRequestDispatcher("../clientes/saldo.jsp");
                dispatcher.forward(request, response);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                dispatcher = request.getRequestDispatcher("../clientes/erro.jsp");
                request.setAttribute("SQLerror", e.getMessage());
                dispatcher.forward(request, response);
                return;
            }
        } else {
            dispatcher = request.getRequestDispatcher("../clientes/erro.jsp");
            request.setAttribute("SQLerror", "erro");
            dispatcher.forward(request, response);
            return;

        }

        //processRequest(request, response);
    }

    private Conta getConta(int numero, String cpf) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM conta WHERE (Primeiro_Corr = ? OR Segundo_Corr = ? OR Terceiro_Corr = ?) AND Numero = ? ");
        pstm.setString(1, cpf);
        pstm.setString(2, cpf);
        pstm.setString(3, cpf);
        pstm.setInt(4, numero);
        result = pstm.executeQuery();
        if (result.next()) {
            return new Conta(result.getInt("Numero"), result.getString("Primeiro_Corr"), result.getString("Segundo_Corr"), result.getString("Terceiro_Corr"), result.getDouble("Saldo"), result.getDouble("Limite"));
        }
        return null;
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
        processRequest(request, response);
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
