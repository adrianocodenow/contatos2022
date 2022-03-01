package com.adrianocodenow.contatos2022.utils;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.jetbrains.annotations.NotNull;
/**
 *
 * @author ElderBR
 */
public class Msg {

    public static void Server(@NotNull String msg, @NotNull Class classe) {
        System.out.println(msg+" - Class: "+classe.getSimpleName());
    }
    public static void ServerLinha() {
        System.out.println("=====================================================");
    }
    public static void ServerErro(@NotNull String msg, @NotNull Exception e) {
        System.err.println(msg+"\nErro: "+ e.getMessage());
    }
    public static void ServerErro(@NotNull String msg,@NotNull String methodo, @NotNull Exception e) {
        System.err.println(msg+"\nMethodo: "+ methodo +"\nErro: "+ e.getMessage());
        e.printStackTrace();
    }
    
    public static void Aviso(Component parentComponent, String msg){
        JOptionPane.showMessageDialog(parentComponent, msg, "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void Erro(Component component, String msg, Exception e){
        JOptionPane.showMessageDialog(component, msg+"\nErro: "+ e.getMessage(), "AVISO", JOptionPane.ERROR_MESSAGE);
    }

}
