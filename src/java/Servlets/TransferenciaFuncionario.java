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
 * @author Gabriel
 */
@WebServlet(name = "TransferenciaFuncionario", urlPatterns = {"/funcionarios/transferencia"})
public class TransferenciaFuncionario extends HttpServlet {

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
            out.println("<title>Servlet TransferenciaFuncionario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransferenciaFuncionario at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        String cpfcorr = request.getParameter("cpfcorr");
        String senha = request.getParameter("senha");
        String corigemstr = request.getParameter("corigem");
        String cdestinostr = request.getParameter("cdestino");
        String valorstr = request.getParameter("valor");

        if (!checkCampos(cpfcorr, senha, corigemstr, cdestinostr, valorstr)) {
            dispatcher = request.getRequestDispatcher("../funcionarios/transferencia.jsp");
            request.setAttribute("error", "Todos os campos tem preenchimento obrigatório");
            dispatcher.forward(request, response);
            return;
        }

        try {
            if (correntista(cpfcorr, senha)) {
                int numcorigem = Integer.parseInt(corigemstr);
                int numcdestino = Integer.parseInt(cdestinostr);
                Conta corigem = correntistaConta(cpfcorr, numcorigem);
                if (corigem == null) {
                    dispatcher = request.getRequestDispatcher("../funcionarios/transferencia.jsp");
                    request.setAttribute("error", "A conta origem não possui o correntista informado!");
                    dispatcher.forward(request, response);
                    return;
                }

                Conta cdestino = contaDestino(numcdestino);
                if (cdestino == null) {
                    dispatcher = request.getRequestDispatcher("../funcionarios/transferencia.jsp");
                    request.setAttribute("error", "A conta destino não existe");
                    dispatcher.forward(request, response);
                    return;
                }

                Double valor = Double.parseDouble(valorstr);
                Double valordisponivel = corigem.getSaldo() + corigem.getLimite();
                if (valordisponivel < valor) {
                    dispatcher = request.getRequestDispatcher("../funcionarios/transferencia.jsp");
                    request.setAttribute("error", "Não há saldo e limite suficiente para realizar a transferência!");
                    dispatcher.forward(request, response);
                    return;

                } else {
                    Double saldoOrigem = corigem.getSaldo() - valor;
                    Double saldoDestino = cdestino.getSaldo() + valor;
                    if (atualizaConta(corigem.getNumero(), saldoOrigem) > 0 && atualizaConta(cdestino.getNumero(), saldoDestino) > 0) {
                        corigem.setSaldo(corigem.getSaldo() - valor);
                        cdestino.setSaldo(cdestino.getSaldo() + valor);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        GregorianCalendar calendar = new GregorianCalendar();
                        Timestamp s = Timestamp.valueOf(format.format(calendar.getTime()));

                        Transacao.insertTransferencia("transf", corigem.getNumero(), cdestino.getNumero(), valor, s);
                        dispatcher = request.getRequestDispatcher("../funcionarios/transferencia.jsp");
                        request.setAttribute("success", "Transferência realizada com sucesso!");
                        dispatcher.forward(request, response);
                    }
                }
            } else {
                dispatcher = request.getRequestDispatcher("../funcionarios/transferencia.jsp");
                request.setAttribute("error", "Correntista informado não existe!");
                dispatcher.forward(request, response);
                return;
            }
        } catch (SQLException e) {
            dispatcher = request.getRequestDispatcher("../funcionarios/erro.jsp");
            request.setAttribute("SQLerror", e.getMessage());
            dispatcher.forward(request, response);
            return;
        }
    }

    private boolean checkCampos(String cpfcorr, String senha, String corigem, String cdestino, String valor) {
        if (cpfcorr == null || senha == null || corigem == null || cdestino == null || valor == null) {
            return false;
        }

        if (cpfcorr.equals("") || senha.equals("") || corigem.equals("") || cdestino.equals("") || valor.equals("")) {
            return false;
        }
        return true;
    }

    private int atualizaConta(int Numero, Double saldo) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("UPDATE conta SET saldo = ? WHERE Numero = ?");
        pstm.setDouble(1, saldo);
        pstm.setInt(2, Numero);
        return pstm.executeUpdate();
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

    private Conta correntistaConta(String CPF, int numero) throws SQLException {
        ResultSet result = null;
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM conta WHERE (conta.Primeiro_Corr = ? OR conta.Segundo_Corr = ? OR conta.Terceiro_Corr = ?)and conta.Numero = ?");
        pstm.setString(1, CPF);
        pstm.setString(2, CPF);
        pstm.setString(3, CPF);
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
    }

    private Conta contaDestino(int numero) throws SQLException {
        ResultSet result = null;
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM conta WHERE conta.Numero = ?");
        pstm.setInt(1, numero);

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
