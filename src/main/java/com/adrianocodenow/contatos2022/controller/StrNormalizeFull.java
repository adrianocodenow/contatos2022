/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adrianocodenow.contatos2022.controller;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author apereira
 */
public class StrNormalizeFull {

    public static String ascii(String s) {

        return s.replaceAll("[ãâàáä]", "a")
                .replaceAll("[êèéë]", "e")
                .replaceAll("[îìíï]", "i")
                .replaceAll("[õôòóö]", "o")
                .replaceAll("[ûúùü]", "u")
                .replaceAll("[ÃÂÀÁÄ]", "A")
                .replaceAll("[ÊÈÉË]", "E")
                .replaceAll("[ÎÌÍÏ]", "I")
                .replaceAll("[ÕÔÒÓÖ]", "O")
                .replaceAll("[ÛÙÚÜ]", "U")
                .replace('ç', 'c')
                .replace('Ç', 'C')
                .replace('ÿ', 'y')
                .replace('Ÿ', 'Y')
                .replace('ñ', 'n')
                .replace('Ñ', 'N');
    }

    public static String addTildeOptions(String s) {
        return s.toLowerCase()
                .replaceAll("[aáàäâã]", "\\[aáàäâã\\]")
                .replaceAll("[eéèëê]", "\\[eéèëê\\]")
                .replaceAll("[iíìî]", "\\[iíìî\\]")
                .replaceAll("[oóòöôõ]", "\\[oóòöôõ\\]")
                .replaceAll("[uúùüû]", "\\[uúùüû\\]")
                .replace("*", "[*]")
                .replace("?", "[?]");
    }

    public static String asciiFullTrim(String txt) {

        return txt.replaceAll("\\s", "");

    }

    public static String asciiAddSpace(String txt) {

        return txt.replaceAll("", " ");

    }

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static String getOnlyStrings(String s) {
        return s.replaceAll("[^a-zA-Z]", "");
    }

    public static String getOnlyStringsW(String s) {
        return s.replaceAll("\\W+", "");
    }

    public static String getStringsDigits(String s) {
        return s.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String getStringsDigitsMath(String s) {
        return s.replaceAll("[^a-zA-Z0-9.,+\\-/*%()\\[\\]{}\\^]", "");
    }

    public static String getOnlyDigits(String s) {
        return s.replaceAll("[^0-9]", "");
    }

    public static String getDigitsPointComma(String s) {
        return s.replaceAll("[^0-9.,]", "");
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String validZip(String s) {
        return s.replaceAll("[^0-9.\\-]", "[^\\d{2}\\.\\d{3}\\-\\d{3}$]");
    }

    public static boolean validZip1(String s) {
        Pattern regex = Pattern.compile("^\\d{2}\\.\\d{3}-\\d{3}");
        Matcher resultado = regex.matcher(s);
        return resultado.matches();
    }

    public static void main(String[] args) {
        System.out.println("ãâàáäêèéëîìíïõôòóöûúùüÃÂÀÁÄÊÈÉËÎÌÍÏÕÔÒÓÖÛÙÚÜçÇñÑÿŸ = "
                + ascii("ãâàáäêèéëîìíïõôòóöûúùüÃÂÀÁÄÊÈÉËÎÌÍÏÕÔÒÓÖÛÙÚÜçÇñÑÿŸ"));
        System.out.println("ãâàáäêèéëîìíïõôòóöûúùüÃÂÀÁÄÊÈÉËÎÌÍÏÕÔÒÓÖÛÙÚÜçÇñÑÿŸ = "
                + removerAcentos("ãâàáäêèéëîìíïõôòóöûúùüÃÂÀÁÄÊÈÉËÎÌÍÏÕÔÒÓÖÛÙÚÜçÇñÑÿŸ"));
        System.out.println("Minha casa. Nossa casa! asciiFullTrim = " + asciiFullTrim("Minha casa. Nossa casa! = "));
        System.out.println("Minha casa. Nossa casa! asciiAddSpace = " + asciiAddSpace("Minha casa. Nossa casa! = "));
        System.out.println("MinhaCasa.NossaCasa! splitCamelCase = " + splitCamelCase("MinhaCasa.NossaCasa! = "));
        System.out.println("MinhaCasa.NossaCasa! ?*addTildeOptions*¿! = "
                + addTildeOptions("MinhaCasa.NossaCasa! ?*addTildeOptions*¿! = "));
        System.out.println("Acentuação em geral removerAcentos = áäãà ÁÄÀã éëè ÉËÈ "
                + removerAcentos("Acentuação em geral removerAcentos = áäãà ÁÄÀã éëè ÉËÈ "));
        System.out.println("String is getOnlyStrings = " + getOnlyStrings("!&(*^*(^(+one(&(^()(*)(*&^%$#@!#$%^&*()("));
        System.out.println("String is getOnlyStringsW = " + getOnlyStringsW("!&(*^*(^(+oneáéíóú(&(^()(*)(*&^%$#@!#$%^&*()("));
        System.out.println("String is ascii = " + ascii("!&(*^*(^(+oneáéíóú(&(^()(*)(*&^%$#@!#$%^&*()("));
        System.out.println("String is removerAcentos = " + removerAcentos("!&(*^*(^(+oneáéíóú(&(^()(*)(*&^%$#@!#$%^&*()("));
        System.out.println("String is getStringsDigits = " + getStringsDigits("!&(*^*(^(+one(&(^()(*)123(*&^%$#@567!#$%^&*()("));
        System.out.println("String is getStringsDigitsMath = "
                + getStringsDigitsMath("!&(*^*(^(+one(&(^(-)(*){[(1.23)]}áéíóú(*&^%$+#@5,67-!#/$%^&*()("));
        System.out.println("Number is getOnlyDigits = " + getOnlyDigits("&(*^*(^(+91-&*9hi-6,39-00.97(&(^("));
        System.out.println("Number is getDigitsPointComma = " + getDigitsPointComma("&(*^*(^(+91-&*9hi-6,39-00.97(&(^("));
        System.out.println("Valida CEP 12.345-678 " + validZip("12345-678"));
        System.out.println("Valida CEP 12.345-678 " + validZip1("12.345-678"));
    }

}
