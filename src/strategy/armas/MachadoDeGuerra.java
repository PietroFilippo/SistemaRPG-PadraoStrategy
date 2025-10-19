package strategy.armas;

import model.Personagem;
import model.effects.Atordoamento;
import strategy.ArmaBase;
import java.util.List;

// Machado de Guerra, arma pesada que pode atordoar
public class MachadoDeGuerra extends ArmaBase {
    
    public MachadoDeGuerra() {
        super("Machado de Guerra", 18, 5, 
              "Golpe Esmagador - 25% de chance de atordoar o inimigo (perde 1 turno)");
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
        
        resultado.append(String.format("%s desfere um golpe devastador com %s\n", 
                                      atacante.getNome(), nome));
        
        int dano = calcularDanoCritico(danoBase);
        alvo.receberDano(dano);
        resultado.append(String.format("  - Causou %d de dano\n", dano));
        
        // 25% de chance de atordoar
        if (Math.random() < 0.25) {
            alvo.adicionarEfeito(new Atordoamento());
            resultado.append("  - Golpe Esmagador " + alvo.getNome() + " está atordoado\n");
        }
        
        alvo.setDesprevenido(false);
        return resultado.toString();
    }

    @Override
    public boolean podeUsar(Personagem personagem) {
        return personagem.getAtributos().getForca() >= 15;
    }

    @Override
    public String getRequisitos() {
        return "Força >= 15";
    }
}

