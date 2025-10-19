package strategy;

import model.Personagem;
import java.util.List;

// Interface Strategy para armas, define o comportamento de ataque que pode variar entre diferentes armas
public interface Arma {
    
    // Executa um ataque contra um ou mais alvos
    String atacar(Personagem atacante, List<Personagem> alvos);
    
    // Retorna o dano base da arma
    int getDanoBase();
    
    // Retorna o custo de mana para usar a arma
    int getCustoMana();
    
    // Retorna o nome da arma
    String getNome();
    
    // Verifica se o personagem atende aos requisitos para usar a arma
    boolean podeUsar(Personagem personagem);
    
    // Retorna a descrição dos requisitos da arma
    String getRequisitos();
}

