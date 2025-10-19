package model.effects;

import model.Personagem;
import model.StatusEffect;

// Efeito de queimadura que causa dano de fogo ao longo do tempo
public class Queimadura extends StatusEffect {
    
    public Queimadura() {
        super("Queimadura", 2, 10);
    }

    @Override
    public String aplicarEfeito(Personagem personagem) {
        personagem.receberDano(dano);
        return String.format("  - %s está queimando e sofreu %d de dano (%d turnos restantes)", 
                           personagem.getNome(), dano, turnosRestantes);
    }
}

