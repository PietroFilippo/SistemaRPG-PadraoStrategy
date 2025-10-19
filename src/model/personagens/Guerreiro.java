package model.personagens;

import model.Atributos;
import model.Personagem;
import java.util.Arrays;
import java.util.List;

// Classe Guerreiro, tanque com alta resistência
public class Guerreiro extends Personagem {
    
    public Guerreiro(String nome) {
        super(nome, 120, 50, new Atributos(15, 8, 5));
    }

    @Override
    public String aplicarHabilidadePassiva() {
        return "Pele Dura ativa - Redução de 20% no dano recebido";
    }

    @Override
    public int calcularDanoRecebido(int dano) {
        // Pele Dura: Reduz dano em 20%
        return (int) (dano * 0.8);
    }

    @Override
    public List<String> getArmasPermitidas() {
        return Arrays.asList("Espada Longa", "Machado de Guerra");
    }
}

