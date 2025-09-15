//Daniel Borges Valentim - 10427564
//João Vitor Golfieri Mendonça - 10434460
package model;

import java.util.Objects;

public class Servidor implements Comparable<Servidor> {
    private final String nome;
    private final String cargoBase;
    private final String cargoComissao;
    private final double remuneracaoBruta;
    private final String unidade;
    private final String jornada;

    public Servidor(String nome, String cargoBase, String cargoComissao,
                    double remuneracaoBruta, String unidade, String jornada) {
        this.nome = nome == null ? "" : nome.trim();
        this.cargoBase = cargoBase == null ? "" : cargoBase.trim();
        this.cargoComissao = cargoComissao == null ? "" : cargoComissao.trim();
        this.remuneracaoBruta = remuneracaoBruta;
        this.unidade = unidade == null ? "" : unidade.trim();
        this.jornada = jornada == null ? "" : jornada.trim();
    }

    public String getNome() { return nome; }
    public String getCargoBase() { return cargoBase; }
    public String getCargoComissao() { return cargoComissao; }
    public double getRemuneracaoBruta() { return remuneracaoBruta; }
    public String getUnidade() { return unidade; }
    public String getJornada() { return jornada; }

    @Override public int compareTo(Servidor o) {
        return this.nome.compareToIgnoreCase(o.nome);
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servidor s)) return false;
        return nome.equalsIgnoreCase(s.nome);
    }
    @Override public int hashCode() { return Objects.hash(nome.toLowerCase()); }

    @Override public String toString() {
        return String.format("%s | %s | %s | R$ %.2f | %s | %s",
            nome, cargoBase, cargoComissao, remuneracaoBruta, unidade, jornada);
    }
}
