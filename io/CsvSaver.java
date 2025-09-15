//Daniel Borges Valentim - 10427564
//João Vitor Golfieri Mendonça - 10434460
package io;

import model.Servidor;
import tree.BST;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class CsvSaver {
    public static void salvar(String caminho, BST<Servidor> arvore) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminho), StandardCharsets.UTF_8))) {
            bw.write("Nome Completo;Cargo Base;Cargo em Comissão;Remuneração Bruta;Unidade;Jornada\n");
            Locale localeBR = new Locale("pt", "BR");
            arvore.preOrder(s -> {
                try {
                    String remunBR = String.format(localeBR, "%.2f", s.getRemuneracaoBruta()).replace('.', ',');
                    bw.write(String.join(";", new String[]{
                        s.getNome(), s.getCargoBase(), s.getCargoComissao(), remunBR, s.getUnidade(), s.getJornada()
                    }));
                    bw.newLine();
                } catch (IOException e) { throw new UncheckedIOException(e); }
            });
        }
    }
}
