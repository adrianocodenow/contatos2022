/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adrianocodenow.contatos2022.controller;

import java.text.Normalizer;

/**
 *
 * @author apereira
 */
public class StrNormalize {

    public static String removeAcentos1(String s) {
        return s.replaceAll("[áàâäã]", "a")
                .replaceAll("[éèêë]", "e")
                .replaceAll("[íìîï]", "i")
                .replaceAll("[óòôöõ]", "o")
                .replaceAll("[úùûü]", "u")
                .replaceAll("[ÁÀÂÄÃ]", "A")
                .replaceAll("[ÉÈÊË]", "E")
                .replaceAll("[ÍÌÎÏ]", "I")
                .replaceAll("[ÓÒÔÖÕ]", "O")
                .replaceAll("[ÚÙÛÜ]", "U")
                .replaceAll("[ç]", "c")
                .replaceAll("[Ç]", "C")
                .replaceAll("[ÿ]", "y")
                .replaceAll("[Ÿ]", "Y")
                .replaceAll("[ñ]", "n")
                .replaceAll("[Ñ]", "N")
                .replaceAll("[¿]", "?");
    }

    public static String removeAcentos2(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String fullTrim(String s) {
        return s.replaceAll("\\s", "");
    }

    public static String full(String s) {
        return removeAcentos1(fullTrim(s)).toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(
                removeAcentos1(
                        "áàâäãéèêëíìîïóòôöõúùûüÁÀÂÄÃÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜçÇÿŸñÑ¿"
                        + "0123456789abcABC!@#$%^&*()?[]{}|\\/~`'\"-_+="
                ).toLowerCase());
        System.out.println(
                removeAcentos2(
                        "áàâäãéèêëíìîïóòôöõúùûüÁÀÂÄÃÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜçÇÿŸñÑ¿"
                        + "0123456789abcABC!@#$%^&*()?[]{}|\\/~`'\"-_+="
                ).toLowerCase());
        System.out.println(fullTrim("       10101mjlkasdf Acentuação   "));
        System.out.println(
                removeAcentos1(fullTrim("       10101mjlkasdf Acentuação   "))
                        .toLowerCase()
        );

    }
}
