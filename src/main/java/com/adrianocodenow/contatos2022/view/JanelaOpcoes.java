/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adrianocodenow.contatos2022.view;

import javax.swing.JOptionPane;

/**
 *
 * @author apereira
 */
public class JanelaOpcoes {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(
                null,
                "Essa janela de teste",
                "Mensagem Teste",
                JOptionPane.INFORMATION_MESSAGE
        );
        String nome = JOptionPane.showInputDialog(
                null,
                "Qual é o seu nome completo?",
                "Entrada de Dados",
                JOptionPane.DEFAULT_OPTION
        );
        JOptionPane.showMessageDialog(
                null,
                nome,
                "Seu nome é:",
                JOptionPane.INFORMATION_MESSAGE
        );
        int opcao = JOptionPane.showConfirmDialog(
                null,
                "Confirma a compra?"
        );
        JOptionPane.showMessageDialog(
                null,
                (opcao == 0 ? "Compra Confirmada" : "Compra Cancelada"),
                "Mensagem Teste",
                JOptionPane.INFORMATION_MESSAGE
        );

    }
}
