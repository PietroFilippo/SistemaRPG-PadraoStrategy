package strategy.armas;

import model.Personagem;
import strategy.ArmaBase;
import java.util.List;

// Arco Élfico, arma de longo alcance que atinge múltiplos alvos
public class ArcoElfico extends ArmaBase {
    
    public ArcoElfico() {
        super("Arco Élfico", 12, 15, 
              "Chuva de Flechas - Ataque em área, atinge todos os inimigos");
    }

    @Override
    public String atacar(Personagem atacante, List<Personagem> alvos) {
        if (alvos.isEmpty()) {
            return "Nenhum alvo válido";
        }

        if (!verificarMana(atacante)) {
            return String.format("%s não tem mana suficiente (Necessário: %d mana)", 
                               atacante.getNome(), custoMana);
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("%s usa %s - Chuva de Flechas\n", 
                                      atacante.getNome(), nome));
        
        int alvosAtingidos = 0;
        for (Personagem alvo : alvos) {
            if (alvo.isVivo()) {
                int dano = calcularDanoCritico(danoBase);
                alvo.receberDano(dano);
                resultado.append(String.format("  - %s sofreu %d de dano\n", 
                                             alvo.getNome(), dano));
                alvo.setDesprevenido(false);
                alvosAtingidos++;
            }
        }
        
        resultado.append(String.format("  - Atingiu %d alvo(s)\n", alvosAtingidos));
        return resultado.toString();
    }

    @Override
    public boolean podeUsar(Personagem personagem) {
        return personagem.getAtributos().getDestreza() >= 8;
    }

    @Override
    public String getRequisitos() {
        return "Destreza >= 8";
    }
}

