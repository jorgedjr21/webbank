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
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jorge
 */
@WebServlet(name = "/funcionarios/newacc", urlPatterns = {"/funcionarios/newacc"})
public class newacc extends HttpServlet {

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
            out.println("<title>Servlet newacc</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet newacc at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        response.sendRedirect("../funcionarios/newacc.jsp");
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

        RequestDispatcher dispatcher = null;
        Double saldo = null;
        Double limite = null;
        
        try{
        
        saldo = Double.parseDouble(request.getParameter("saldo"));
        limite = Double.parseDouble(request.getParameter("limite"));
        }catch(NumberFormatException e){
            request.setAttribute("error", "Os campos saldo e limite devem ser preenchidos!");
            dispatcher = request.getRequestDispatcher("../funcionarios/newacc.jsp");
            dispatcher.forward(request, response);
        }
        boolean correntista1 = false;

        String nome1 = request.getParameter("nome1");
        String email1 = request.getParameter("email1");
        String endereco1 = request.getParameter("endereco1");
        String cpf1 = request.getParameter("cpf1");
        String senha1 = request.getParameter("senha1");

        String nome2 = request.getParameter("nome2");
        String email2 = request.getParameter("email2");
        String endereco2 = request.getParameter("endereco2");
        String cpf2 = request.getParameter("cpf2");
        String senha2 = request.getParameter("senha2");

        String nome3 = request.getParameter("nome3");
        String email3 = request.getParameter("email3");
        String endereco3 = request.getParameter("endereco3");
        String cpf3 = request.getParameter("cpf3");
        String senha3 = request.getParameter("senha3");
        ;

        if (saldo <= 0 || saldo == null) {
            saldo = 0.0d;
        }

        if (limite <= 0 || limite == null) {
            limite = 0.0d;
        }

        if (checkCampos(nome1, email1, endereco1, cpf1, senha1)) {
            try {
                if (!checkCorrentista(cpf1)) {
                    insertCorrentista(nome1, email1, endereco1, cpf1, senha1);
                }
                correntista1 = true;
            } catch (SQLException e) {
                request.setAttribute("SQLerror", e.getMessage());
                dispatcher = request.getRequestDispatcher("../funcionarios/erro.jsp");
                dispatcher.forward(request, response);
                //request.getRequestDispatcher("../funcionarios/erro.jsp").forward(request, response);
            }
        } else {

            request.setAttribute("error", "Correntista principal não foi informado!");
            dispatcher = request.getRequestDispatcher("../funcionarios/newacc.jsp");
            dispatcher.forward(request, response);

            // request.getRequestDispatcher("../funcionarios/newacc.jsp").forward(request, response);
        }

        if (checkCampos(nome2, email2, endereco2, cpf2, senha2) && correntista1) {
            try {
                if (!checkCorrentista(cpf2)) {
                    insertCorrentista(nome2, email2, endereco2, cpf2, senha2);
                }
            } catch (SQLException e) {
                request.setAttribute("SQLerror", e.getMessage());
                request.getRequestDispatcher("../funcionarios/erro.jsp").forward(request, response);
            }
        }

        if (checkCampos(nome3, email3, endereco3, cpf3, senha3) && correntista1) {
            try {
                if (!checkCorrentista(cpf3)) {
                    insertCorrentista(nome3, email3, endereco3, cpf3, senha3);
                }
            } catch (SQLException e) {
                request.setAttribute("SQLerror", e.getMessage());
                request.getRequestDispatcher("../funcionarios/erro.jsp").forward(request, response);
            }
        }

        if (correntista1) {
            if (cpf2 == null || cpf2.equals("")) {
                cpf2 = null;
            }
            if (cpf3 == null || cpf3.equals("")) {
                cpf3 = null;
            }
            try {
                int id = insertConta(cpf1, cpf2, cpf3, saldo, limite);
                request.setAttribute("success", "Conta cadastrada com sucesso!");
                request.setAttribute("conta",id);
                dispatcher = request.getRequestDispatcher("../funcionarios/newacc.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("SQLerror", e.getMessage());
                request.getRequestDispatcher("../funcionarios/erro.jsp").forward(request, response);
            }
        }
        //processRequest(request, response);
    }

    private boolean checkCampos(String nome, String email, String endereco, String cpf, String senha) {
        boolean valid = true;
        if (nome == null || email == null || endereco == null || cpf == null || senha == null) {
            valid = false;
        }

        if (!valid) {
            return valid;
        }

        if (nome.equals("") || email.equals("") || endereco.equals("") || cpf.equals("") || senha.equals("")) {
            valid = false;
        }
        return valid;

    };
    
    private void insertCorrentista(String nome, String email, String endereco, String cpf, String senha) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("INSERT INTO correntista (CPF,Nome,Senha,Endereco,Email) VALUES(?,?,?,?,?)");
        pstm.setString(1, cpf);
        pstm.setString(2, nome);
        pstm.setString(3, senha);
        pstm.setString(4, endereco);
        pstm.setString(5, email);
        pstm.execute();
    }

    private int insertConta(String primeiro_corr, String segundo_corr, String terceiro_corr, Double saldo, Double limite) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("INSERT INTO conta (Primeiro_Corr,Segundo_Corr,Terceiro_Corr,Saldo,Limite) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, primeiro_corr);
        pstm.setString(2, segundo_corr);
        pstm.setString(3, terceiro_corr);
        pstm.setDouble(4, saldo);
        pstm.setDouble(5, limite);
        int rows = pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 1;
    }

    private boolean checkCorrentista(String cpf) throws SQLException {
        Connection c = Dbconfig.getConnection();
        pstm = c.prepareStatement("SELECT * FROM correntista WHERE CPF = ?");
        pstm.setString(1, cpf);

        ResultSet result = pstm.executeQuery();
        if (!result.next()) {
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
