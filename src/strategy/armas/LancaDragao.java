package strategy.armas;

import model.Personagem;
import strategy.ArmaBase;
import java.util.List;

// Lança do Dragão, arma lendária com efeito de perfuração
public class LancaDragao extends ArmaBase {
    
    public LancaDragao() {
        super("Lança do Dragão", 20, 20, 
              "Perfuração Dracônica - Ignora 50% da resistência do alvo e causa dano verdadeiro");
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
        
        resultado.append(String.format("%s perfura %s com a %s\n", 
                                      atacante.getNome(), alvo.getNome(), nome));
        
        int dano = calcularDanoCritico(danoBase);
        
        // Efeito único: Ignora 50% da resistência do alvo
        // Calcula o dano que seria recebido normalmente
        int danoNormal = alvo.calcularDanoRecebido(dano);
        
        // Calcula o dano bloqueado pela habilidade passiva
        int danoBloqueado = dano - danoNormal;
        
        // Adiciona 50% do dano bloqueado de volta (ignora metade da resistência)
        int danoFinal = danoNormal + (int)(danoBloqueado * 0.5);
        
        // Aplica o dano final (não pode usar receberDano pois aplicaria resistência novamente)
        int vidaAntes = alvo.getVidaAtual();
        int novaVida = Math.max(0, vidaAntes - danoFinal);
        
        // Usa reflexão para acessar o campo protected via subclasse auxiliar
        try {
            java.lang.reflect.Field vidaAtualField = alvo.getClass().getSuperclass().getDeclaredField("vidaAtual");
            vidaAtualField.setAccessible(true);
            vidaAtualField.set(alvo, novaVida);
        } catch (Exception e) {
            // Fallback: usa receberDano normal
            alvo.receberDano(danoNormal);
        }
        
        resultado.append(String.format("  - Perfuração Dracônica. Causou %d de dano (ignorou 50%% da resistência)\n", 
                                      danoFinal));
        resultado.append("  - (Ignora 50% da resistência do inimigo)\n");
        
        alvo.setDesprevenido(false);
        return resultado.toString();
    }

    @Override
    public boolean podeUsar(Personagem personagem) {
        // Requisitos altos: Força E Destreza
        return personagem.getAtributos().getForca() >= 12 
            && personagem.getAtributos().getDestreza() >= 12;
    }

    @Override
    public String getRequisitos() {
        return "Força >= 12 E Destreza >= 12";
    }
}

