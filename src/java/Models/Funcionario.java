/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author jorge
 */
public class Funcionario {
    
    private int Codigo;
    private String Nome,Email,Senha,Funcao;

    
    public Funcionario(int codigo,String nome,String email,String senha,String funcao){
        this.Codigo = codigo;
        this.Nome = nome;
        this.Email = email;
        this.Senha = senha;
        this.Funcao = funcao;
    }
    
    public Funcionario(String nome,String senha,String email,String funcao){
        this.Nome = nome;
        this.Email = email;
        this.Senha = senha;
        this.Funcao = funcao;
    }
    /**
     * @return the Codigo
     */
    public int getCodigo() {
        return Codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.Nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.Email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return Senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.Senha = senha;
    }

    /**
     * @return the funcao
     */
    public String getFuncao() {
        return Funcao;
    }

    /**
     * @param funcao the funcao to set
     */
    public void setFuncao(String funcao) {
        this.Funcao = funcao;
    }
    
    
}
