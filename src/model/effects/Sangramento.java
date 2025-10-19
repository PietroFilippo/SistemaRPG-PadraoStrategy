package model.effects;

import model.Personagem;
import model.StatusEffect;


// Efeito de sangramento que causa dano ao longo do tempo
public class Sangramento extends StatusEffect {
    
    public Sangramento() {
        super("Sangramento", 3, 5);
    }

    @Override
    public String aplicarEfeito(Personagem personagem) {
        personagem.receberDano(dano);
        return String.format("  - %s está sangrando e sofreu %d de dano (%d turnos restantes)", 
                           personagem.getNome(), dano, turnosRestantes);
    }
}

