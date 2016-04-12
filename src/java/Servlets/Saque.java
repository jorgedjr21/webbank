/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Models.Conta;
import Models.Transacao;
import config.Dbconfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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
@WebServlet(name = "Saque", urlPatterns = {"/funcionarios/saque"})
public class Saque extends HttpServlet {

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
            out.println("<title>Servlet Saque</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Saque at " + request.getContextPath() + "</h1>");
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
        String cpf = request.getParameter("CPF");
        String senha = request.getParameter("senha");
        String valorstr = request.getParameter("valor");
        if (!checkCampos(contastr, cpf, senha, valorstr)) {
            dispatcher = request.getRequestDispatcher("../funcionarios/saque.jsp");
            request.setAttribute("error", "Todos os campos são obrigatórios!");
            dispatcher.forward(request, response);
            return;
        }

        try {
            if (correntista(cpf, senha)) {
                int numconta = Integer.parseInt(contastr);
                double valor = Double.parseDouble(valorstr);
                Conta conta = retornaConta(numconta, cpf);
                if (conta != null) {
                    Double maxLimite = conta.getSaldo() + conta.getLimite();
                    if (valor > maxLimite) {
                        dispatcher = request.getRequestDispatcher("../funcionarios/saque.jsp");
                        request.setAttribute("error", "Não é possivel sacar R$" + valor + " pois nesta conta só há disponivel o valor de R$" + maxLimite + " (saldo + limite). ");
                        dispatcher.forward(request, response);

                    } else {

                        Double newSaldo = conta.getSaldo() - valor;

                        if (atualizaSaldo(newSaldo, numconta, cpf) > 0) {
                            conta.setSaldo(newSaldo);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            GregorianCalendar calendar = new GregorianCalendar();
                            Timestamp s = Timestamp.valueOf(format.format(calendar.getTime()));
                            Transacao.insertTransacao("saque", numconta, valor, s);

                            dispatcher = request.getRequestDispatcher("../funcionarios/saque.jsp");
                            request.setAttribute("success", "Saque de R$ " + valor + " realizado com sucesso.O novo Saldo da conta <strong>" + conta.getNumero() + "</strong> é de <strong>R$ " + conta.getSaldo() + "</strong>");
                            dispatcher.forward(request, response);
                        } else {
                            dispatcher = request.getRequestDispatcher("../funcionarios/saque.jsp");
                            request.setAttribute("error", "Não foi possivel completar o saque, por favor tente novamente");
                            dispatcher.forward(request, response);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            dispatcher = request.getRequestDispatcher("../funcionarios/erro.jsp");
            request.setAttribute("SQLerror", e.getMessage());
            dispatcher.forward(request, response);
        }
        //processRequest(request, response);
    }

    private boolean checkCampos(String conta, String cpf, String senha, String valor) {
        if (conta == null || cpf == null || senha == null || valor == null) {
            return false;
        }

        if (conta.equals("") || cpf.equals("") || senha.equals("") || valor.equals("")) {
            return false;
        }
        return true;
    }

    private boolean correntista(String CPF, String senha) throws SQLException {
        ResultSet result = null;
        Connection c = Dbconfig.getConnection();

        pstm = c.prepareStatement("SELECT * FROM correntista WHERE CPF = ? and Senha = ?");
        pstm.setString(1, CPF);
        pstm.setString(2, senha);

        result = pstm.executeQuery();
        if (result.next()) {
            return true;
        }
        return false;
    }

    private int atualizaSaldo(Double newSaldo, int conta, String cpf) throws SQLException {
        Connection c = Dbconfig.getConnection();

        pstm = c.prepareStatement("UPDATE conta SET saldo = ? WHERE (conta.Primeiro_Corr = ? OR conta.Segundo_Corr = ? OR conta.Terceiro_Corr = ?)and conta.Numero = ? ");
        pstm.setDouble(1, newSaldo);
        pstm.setString(2, cpf);
        pstm.setString(3, cpf);
        pstm.setString(4, cpf);
        pstm.setInt(5, conta);
        return pstm.executeUpdate();
    }

    ;

    private Conta retornaConta(int numero, String cpf) throws SQLException {
        ResultSet result = null;
        Connection c = Dbconfig.getConnection();

        pstm = c.prepareStatement("SELECT * FROM conta WHERE (conta.Primeiro_Corr = ? OR conta.Segundo_Corr = ? OR conta.Terceiro_Corr = ?)and conta.Numero = ?");
        pstm.setString(1, cpf);
        pstm.setString(2, cpf);
        pstm.setString(3, cpf);
        pstm.setInt(4, numero);
        result = pstm.executeQuery();
        if (result.next()) {
            return new Conta(result.getInt("Numero"),
                    result.getString("Primeiro_Corr"),
                    result.getString("Terceiro_Corr"),
                    result.getString("Terceiro_Corr"),
                    result.getDouble("Saldo"),
                    result.getDouble("Limite"));
        } else {
            return null;
        }
    } ;
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
