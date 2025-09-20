//Daniel Borges Valentim - 10427564
//João Vitor Golfieri Mendonça - 10434460
package service;

import model.Servidor;
import tree.BST;
import util.Pair;

import java.util.*;
import java.util.function.Consumer;

//Classe que oferece funcionalidades analíticas sobre os servidores
public class Analytics {
    //Retorna o servidor com menor e maior salário
    public static Pair<Servidor,Servidor> extremosSalario(BST<Servidor> arv) {
        final Servidor[] min = {null}, max = {null};
        arv.preOrder(s -> {
            if (min[0] == null || s.getRemuneracaoBruta() < min[0].getRemuneracaoBruta()) min[0] = s;
            if (max[0] == null || s.getRemuneracaoBruta() > max[0].getRemuneracaoBruta()) max[0] = s;
        });
        return new Pair<>(min[0], max[0]);
    }

    //Calcula o total gasto com servidores que possuem um cargo específico
    public static double totalPorCargo(BST<Servidor> arv, String cargoAlvo) {
        String alvo = cargoAlvo == null ? "" : cargoAlvo.trim().toLowerCase();
        final double[] total = {0};
        arv.inOrder(s -> {
            if (s.getCargoBase().toLowerCase().contains(alvo) || s.getCargoComissao().toLowerCase().contains(alvo)) {
                total[0] += s.getRemuneracaoBruta();
            }
        });
        return total[0];
    }

    //Retorna os N servidores com maior salário
    public static List<Servidor> topN(BST<Servidor> arv, int n) {
        PriorityQueue<Servidor> pq = new PriorityQueue<>(Comparator.comparingDouble(Servidor::getRemuneracaoBruta));
        arv.postOrder(s -> {
            if (pq.size() < n) pq.add(s);
            else if (s.getRemuneracaoBruta() > pq.peek().getRemuneracaoBruta()) { pq.poll(); pq.add(s); }
        });
        List<Servidor> out = new ArrayList<>(pq);
        out.sort(Comparator.comparingDouble(Servidor::getRemuneracaoBruta).reversed());
        return out;
    }

    //Calcula o gasto total por unidade
    public static Map<String, Double> gastoPorUnidade(BST<Servidor> arv) {
        Map<String, Double> mapa = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        arv.inOrder(s -> mapa.merge(s.getUnidade(), s.getRemuneracaoBruta(), Double::sum));
        return mapa;
    }
    
    //Calcula a distribuição de servidores por jornada
    public static Map<String, Long> distribuicaoPorJornada(BST<Servidor> arv) {
        Map<String, Long> mapa = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        arv.preOrder(s -> mapa.merge(s.getJornada(), 1L, Long::sum));
        return mapa;
    }
}
