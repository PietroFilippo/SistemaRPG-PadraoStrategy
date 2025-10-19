package strategy.armas;

import model.Personagem;
import model.effects.Sangramento;
import strategy.ArmaBase;
import java.util.List;

// Espada Longa, arma corpo a corpo com efeito de sangramento
public class EspadaLonga extends ArmaBase {
    
    public EspadaLonga() {
        super("Espada Longa", 15, 0, 
              "Corte Profundo - 30% de chance de causar sangramento (5 de dano por 3 turnos)");
    }

    @Override
    public String atacar(Personagem atacante, List<Personagem> alvos) {
        if (alvos.isEmpty() || !alvos.get(0).isVivo()) {
            return "Nenhum alvo válido";
        }

        if (!verificarMana(atacante)) {
            return String.format("%s não tem mana suficiente", atacante.getNome());
        }

        Personagem alvo = alvos.get(0);
        StringBuilder resultado = new StringBuilder();
        
        resultado.append(String.format("%s ataca %s com %s\n", 
                                      atacante.getNome(), alvo.getNome(), nome));
        
        int dano = calcularDanoCritico(danoBase);
        alvo.receberDano(dano);
        resultado.append(String.format("  - Causou %d de dano\n", dano));
        
        // 30% de chance de causar sangramento
        if (Math.random() < 0.30) {
            alvo.adicionarEfeito(new Sangramento());
            resultado.append("  - Corte Profundo " + alvo.getNome() + " está sangrando\n");
        }
        
        alvo.setDesprevenido(false);
        return resultado.toString();
    }

    @Override
    public boolean podeUsar(Personagem personagem) {
        return personagem.getAtributos().getForca() >= 10;
    }

    @Override
    public String getRequisitos() {
        return "Força >= 10";
    }
}

