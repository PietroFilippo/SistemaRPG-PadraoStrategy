package strategy.armas;

import model.Personagem;
import strategy.ArmaBase;
import java.util.List;

// Adaga Sombria, arma furtiva com dano triplicado contra alvos desprevenidos
public class AdagaSombria extends ArmaBase {
    
    public AdagaSombria() {
        super("Adaga Sombria", 10, 10, 
              "Ataque Furtivo - Dano triplo se o inimigo estiver desprevenido");
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
        
        resultado.append(String.format("%s ataca furtivamente %s com %s\n", 
                                      atacante.getNome(), alvo.getNome(), nome));
        
        int dano = danoBase;
        
        // Dano triplo se alvo estiver desprevenido
        if (alvo.isDesprevenido()) {
            dano *= 3;
            resultado.append("  - Ataque Furtivo. Alvo estava desprevenido\n");
        }
        
        dano = calcularDanoCritico(dano);
        alvo.receberDano(dano);
        resultado.append(String.format("  - Causou %d de dano\n", dano));
        
        alvo.setDesprevenido(false);
        return resultado.toString();
    }

    @Override
    public boolean podeUsar(Personagem personagem) {
        return personagem.getAtributos().getDestreza() >= 12;
    }

    @Override
    public String getRequisitos() {
        return "Destreza >= 12";
    }
}

