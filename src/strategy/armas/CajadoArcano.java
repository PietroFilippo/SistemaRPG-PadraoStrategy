package strategy.armas;

import model.Personagem;
import model.effects.Queimadura;
import strategy.ArmaBase;
import java.util.List;

// Cajado Arcano, arma mágica que causa queimadura
public class CajadoArcano extends ArmaBase {
    
    public CajadoArcano() {
        super("Cajado Arcano", 8, 25, 
              "Bola de Fogo - Causa queimadura (10 de dano por 2 turnos)");
    }

    @Override
    public String atacar(Personagem atacante, List<Personagem> alvos) {
        if (alvos.isEmpty() || !alvos.get(0).isVivo()) {
            return "Nenhum alvo válido";
        }

        if (!verificarMana(atacante)) {
            return String.format("%s não tem mana suficiente (Necessário: %d mana)", 
                               atacante.getNome(), custoMana);
        }

        Personagem alvo = alvos.get(0);
        StringBuilder resultado = new StringBuilder();
        
        resultado.append(String.format("%s conjura uma Bola de Fogo com %s\n", 
                                      atacante.getNome(), nome));
        
        int dano = calcularDanoCritico(danoBase);
        alvo.receberDano(dano);
        resultado.append(String.format("  - %s sofreu %d de dano\n", alvo.getNome(), dano));
        
        // Sempre causa queimadura
        alvo.adicionarEfeito(new Queimadura());
        resultado.append("  - Bola de Fogo " + alvo.getNome() + " está queimando\n");
        
        alvo.setDesprevenido(false);
        return resultado.toString();
    }

    @Override
    public boolean podeUsar(Personagem personagem) {
        return personagem.getAtributos().getInteligencia() >= 12;
    }

    @Override
    public String getRequisitos() {
        return "Inteligência >= 12";
    }
}

