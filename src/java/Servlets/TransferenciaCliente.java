/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Models.Conta;
import Models.Transacao;
import config.CookieUtilities;
import config.Dbconfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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
@WebServlet(name = "TransferenciaCliente", urlPatterns = {"/clientes/transferencia"})
public class TransferenciaCliente extends HttpServlet {

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
            out.println("<title>Servlet TransferenciaCliente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransferenciaCliente at " + request.getContextPath() + "</h1>");
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

        Cookie contacookie = CookieUtilities.getCookie(request, "cconta");
        int conta = Integer.parseInt(contacookie.getValue());
        String cdestinostr = request.getParameter("cdestino");
        String valorstr = request.getParameter("valor");

        if (!checkCampos(cdestinostr, valorstr)) {
            dispatcher = request.getRequestDispatcher("../clientes/transferencia.jsp");
            request.setAttribute("error", "A conta destino e o valor são obrigatórios");
            dispatcher.forward(request, response);
            return;
        }

        int cdestino = Integer.parseInt(cdestinostr);
        Double valor = Double.parseDouble(valorstr);
        try {
            Conta c = getConta(conta);
            Conta d = getConta(cdestino);
            if (valor > (c.getSaldo() + c.getLimite())) {
                dispatcher = request.getRequestDispatcher("../clientes/transferencia.jsp");
                request.setAttribute("error", "Saldo insuficiente para realização da transferência");
                dispatcher.forward(request, response);

            } else {
                Double saldoC = c.getSaldo() - valor;
                Double saldoD = d.getSaldo() + valor;
                c = new Conta(c.getNumero(), c.getPrimeiro_Corr(), c.getSegundo_Corr(), c.getTerceiro_Corr(), saldoC, c.getLimite());
                d = new Conta(d.getNumero(), d.getPrimeiro_Corr(), d.getSegundo_Corr(), d.getTerceiro_Corr(), saldoD, d.getLimite());
                if (atualizaConta(c.getNumero(), saldoC) > 0 && atualizaConta(d.getNumero(), saldoD) > 0) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    GregorianCalendar calendar = new GregorianCalendar();
                    Timestamp s = Timestamp.valueOf(format.format(calendar.getTime()));

                    Transacao.insertTransferencia("transf", c.getNumero(), d.getNumero(), valor, s);
                    dispatcher = request.getRequestDispatcher("../clientes/transferencia.jsp");
                    request.setAttribute("success", "Transferência realizada com sucesso!");
                    dispatcher.forward(request, response);
                } else {
                    dispatcher = request.getRequestDispatcher("../clientes/transferencia.jsp");
                    request.setAttribute("error", "Não foi possivel realizar a transferência. Por favor, tente novamente!");
                    dispatcher.forward(request, response);
                }

            }

        } catch (SQLException e) {
            dispatcher = request.getRequestDispatcher("../clientes/erro.jsp");
            request.setAttribute("SQLerror", e.getMessage());
            dispatcher.forward(request, response);
            return;
        }
//processRequest(request, response);

    }

    private boolean checkCampos(String cdestino, String valor) {
        if (cdestino == null || valor == null) {
            return false;
        }

        if (cdestino.equals("") || valor.equals("")) {
            return false;
        }
        return true;
    }

    private Conta getConta(int Numero) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM conta WHERE Numero = ?");
        pstm.setInt(1, Numero);
        result = pstm.executeQuery();
        if (result.next()) {
            return new Conta(result.getInt("Numero"), result.getString("Primeiro_Corr"),
                    result.getString("Segundo_Corr"), result.getString("Terceiro_Corr"),
                    result.getDouble("Saldo"), result.getDouble("Limite"));
        }
        return null;
    }

    private int atualizaConta(int Numero, Double saldo) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("UPDATE conta SET saldo = ? WHERE Numero = ?");
        pstm.setDouble(1, saldo);
        pstm.setInt(2, Numero);
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
