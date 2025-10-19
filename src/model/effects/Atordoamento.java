package model.effects;

import model.Personagem;
import model.StatusEffect;


// Efeito de atordoamento que faz o personagem perder o turno atual
public class Atordoamento extends StatusEffect {
    
    public Atordoamento() {
        super("Atordoamento", 1, 0);
    }

    @Override
    public String aplicarEfeito(Personagem personagem) {
        personagem.setAtordoado(true);
        return String.format("  - %s está atordoado e não pode agir neste turno", 
                           personagem.getNome());
    }
}

