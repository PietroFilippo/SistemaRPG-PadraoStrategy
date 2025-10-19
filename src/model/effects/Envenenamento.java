package model.effects;

import model.Personagem;
import model.StatusEffect;

// Efeito de envenenamento que causa dano crescente ao longo do tempo
public class Envenenamento extends StatusEffect {
    private int turnoAtual;
    
    public Envenenamento() {
        super("Envenenamento", 4, 3);
        this.turnoAtual = 1;
    }

    @Override
    public String aplicarEfeito(Personagem personagem) {
        // Dano aumenta a cada turno (3, 6, 9, 12)
        int danoAtual = dano * turnoAtual;
        personagem.receberDano(danoAtual);
        turnoAtual++;
        
        return String.format("  - %s está envenenado e sofreu %d de dano (%d turnos restantes, dano crescente)", 
                           personagem.getNome(), danoAtual, turnosRestantes);
    }
}

