/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import config.CookieUtilities;
import config.Dbconfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "ExtratoCorrentista", urlPatterns = {"/clientes/extratocorrentista"})
public class ExtratoCorrentista extends HttpServlet {

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
            out.println("<title>Servlet ExtratoCorrentista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExtratoCorrentista at " + request.getContextPath() + "</h1>");
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

        String datainicial = request.getParameter("datainicial");
        String datafinal = request.getParameter("datafinal");
        if (!checkCampos(datainicial, datafinal)) {
            dispatcher = request.getRequestDispatcher("../clientes/extrato.jsp");
            request.setAttribute("error", "A data inicial e final são obrigatórias!");
            dispatcher.forward(request, response);
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        Date inicialDate = null;
        Date finalDate = null;
        try {
            inicialDate = format.parse(datainicial);
            finalDate = format.parse(datafinal);
            if (diferencaEmDias(inicialDate.getTime(), finalDate.getTime()) > 30) {
                dispatcher = request.getRequestDispatcher("../clientes/extrato.jsp");
                request.setAttribute("error", "Período de consulta é maior que 30 dias!");
                dispatcher.forward(request, response);
                return;
            }

            String nroContastr = CookieUtilities.getCookie(request, "cconta").getValue();
            int conta = Integer.parseInt(nroContastr);

            try {
                result = getExtrato(conta, new java.sql.Date(inicialDate.getTime()), new java.sql.Date(finalDate.getTime()));
                dispatcher = request.getRequestDispatcher("../clientes/mostraextrato.jsp");
                request.setAttribute("transacoes", result);
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                dispatcher = request.getRequestDispatcher("../clientes/erro.jsp");
                request.setAttribute("SQLerror", e.getMessage());
                dispatcher.forward(request, response);
                return;
            }
        } catch (ParseException e) {
            dispatcher = request.getRequestDispatcher("../clientes/extrato.jsp");
            request.setAttribute("error", "Datas informadas em formato incorreto!");
            dispatcher.forward(request, response);
            return;
        }

        //processRequest(request, response);
    }

    private int diferencaEmDias(long de, long ate) {
        int diff = (int) ((ate - de) / (1000 * 60 * 60 * 24));
        return diff;
    }

    private boolean checkCampos(String datainicial, String datafinal) {
        if (datainicial == null || datafinal == null) {
            return false;
        }

        if (datainicial.equals("") || datafinal.equals("")) {
            return false;
        }
        return true;
    }

    private ResultSet getExtrato(int conta, java.sql.Date de, java.sql.Date ate) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM transacao WHERE (Nro_Conta = ? || Nro_Conta_Transf = ?) AND (data between ? AND ?)");
        pstm.setInt(1, conta);
        pstm.setInt(2,conta);
        pstm.setDate(3, de);
        pstm.setDate(4, ate);
        result = pstm.executeQuery();
        return result;
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
