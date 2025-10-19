package model.personagens;

import model.Atributos;
import model.Personagem;
import java.util.Arrays;
import java.util.List;

// Classe Arqueiro, especialista em ataques a distância
public class Arqueiro extends Personagem {
    
    public Arqueiro(String nome) {
        super(nome, 90, 80, new Atributos(8, 15, 7));
    }

    @Override
    public String aplicarHabilidadePassiva() {
        return "Esquiva ativa - 25% de chance de evitar ataques";
    }

    @Override
    public int calcularDanoRecebido(int dano) {
        // Esquiva: 25% de chance de evitar completamente o ataque
        if (Math.random() < 0.25) {
            System.out.println("  - " + nome + " esquivou do ataque");
            return 0;
        }
        return dano;
    }

    @Override
    public List<String> getArmasPermitidas() {
        return Arrays.asList("Arco Élfico", "Adaga Sombria");
    }
}

