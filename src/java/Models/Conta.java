/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Jorge
 */
public class Conta {
        private int numero;
        private String Primeiro_Corr,Segundo_Corr,Terceiro_Corr;
        private Double Saldo,Limite;
        
        public Conta(int numero, String Primeiro_Corr,String Segundo_Corr,String Terceiro_Corr,Double Saldo, Double Limite){
            this.numero = numero;
            this.Primeiro_Corr = Primeiro_Corr;
            this.Segundo_Corr = Segundo_Corr;
            this.Terceiro_Corr = Terceiro_Corr;
            this.Saldo = Saldo;
            this.Limite = Limite;
        }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the Primeiro_Corr
     */
    public String getPrimeiro_Corr() {
        return Primeiro_Corr;
    }

    /**
     * @param Primeiro_Corr the Primeiro_Corr to set
     */
    public void setPrimeiro_Corr(String Primeiro_Corr) {
        this.Primeiro_Corr = Primeiro_Corr;
    }

    /**
     * @return the Segundo_Corr
     */
    public String getSegundo_Corr() {
        return Segundo_Corr;
    }

    /**
     * @param Segundo_Corr the Segundo_Corr to set
     */
    public void setSegundo_Corr(String Segundo_Corr) {
        this.Segundo_Corr = Segundo_Corr;
    }

    /**
     * @return the Terceiro_Corr
     */
    public String getTerceiro_Corr() {
        return Terceiro_Corr;
    }

    /**
     * @param Terceiro_Corr the Terceiro_Corr to set
     */
    public void setTerceiro_Corr(String Terceiro_Corr) {
        this.Terceiro_Corr = Terceiro_Corr;
    }

    /**
     * @return the Saldo
     */
    public Double getSaldo() {
        return Saldo;
    }

    /**
     * @param Saldo the Saldo to set
     */
    public void setSaldo(Double Saldo) {
        this.Saldo = Saldo;
    }

    /**
     * @return the Limite
     */
    public Double getLimite() {
        return Limite;
    }

    /**
     * @param Limite the Limite to set
     */
    public void setLimite(Double Limite) {
        this.Limite = Limite;
    }
        
}
