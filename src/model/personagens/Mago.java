package model.personagens;

import model.Atributos;
import model.Personagem;
import java.util.Arrays;
import java.util.List;

// Classe Mago, conjurador poderoso com alta mana
public class Mago extends Personagem {
    
    public Mago(String nome) {
        super(nome, 70, 150, new Atributos(5, 7, 18));
    }

    @Override
    public String aplicarHabilidadePassiva() {
        regenerarMana(10);
        return "Regeneração de Mana ativa - Recuperou 10 de mana";
    }

    @Override
    public int calcularDanoRecebido(int dano) {
        // Mago não tem redução de dano
        return dano;
    }

    @Override
    public List<String> getArmasPermitidas() {
        return Arrays.asList("Cajado Arcano", "Adaga Sombria");
    }
}

