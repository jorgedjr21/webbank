/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import config.Dbconfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Jorge
 */
public class Transacao {
    
    public static int insertTransacao(String tipo, int nroConta, Double valor, Timestamp data) throws SQLException{
        Connection c = Dbconfig.getConnection();
        PreparedStatement pstm = null;
        pstm = c.prepareStatement("INSERT INTO transacao (Tipo,Nro_Conta,Valor,data) VALUES(?,?,?,?)");
        pstm.setString(1, tipo);
        pstm.setInt(2, nroConta);
        pstm.setDouble(3, valor);
        pstm.setTimestamp(4, data);
        return pstm.executeUpdate();
    }
    
    public static int insertTransferencia(String tipo, int nroConta, int nroContaTransf,Double valor, Timestamp data) throws SQLException{
        Connection c = Dbconfig.getConnection();
        PreparedStatement pstm = null;
        pstm = c.prepareStatement("INSERT INTO transacao (Tipo,Nro_Conta,Nro_Conta_Transf,Valor,data) VALUES(?,?,?,?,?)");
        pstm.setString(1, tipo);
        pstm.setInt(2, nroConta);
        pstm.setInt(3, nroContaTransf);
        pstm.setDouble(4, valor);
        pstm.setTimestamp(5, data);
        return pstm.executeUpdate();
    }
    
}
