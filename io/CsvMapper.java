//Daniel Borges Valentim - 10427564
//João Vitor Golfieri Mendonça - 10434460
package io;

import model.Servidor;
import repo.RepositorioServidores;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvMapper {
    public static int carregar(String caminho, RepositorioServidores repo) throws IOException {
        int inseridos = 0, pulados = 0;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminho), StandardCharsets.UTF_8))) {
            String header = br.readLine(); // cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";", -1);
                if (d.length < 6) { pulados++; continue; }
                String nome   = d[0].trim();
                String cBase  = d[1].trim();
                String cCom   = d[2].trim();
                double remun;
                try {
                    remun = Double.parseDouble(d[3].trim().replace(".", "").replace(",", "."));
                } catch (NumberFormatException e) { pulados++; continue; }
                String unid   = d[4].trim();
                String jornada= d[5].trim();

                if (repo.inserir(new Servidor(nome, cBase, cCom, remun, unid, jornada))) inseridos++;
                else pulados++;
            }
        }
        System.out.printf("Carregados: %d | Pulados: %d%n", inseridos, pulados);
        return inseridos;
    }
}
