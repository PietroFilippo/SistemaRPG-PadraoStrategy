package model.personagens;

import model.Atributos;
import model.Personagem;
import java.util.Arrays;
import java.util.List;


// Classe Paladino, personagem híbrido que combina resistência física com poder mágico
public class Paladino extends Personagem {
    private boolean bencoAtivaProximoTurno;
    
    public Paladino(String nome) {
        super(nome, 100, 100, new Atributos(12, 8, 12));
        this.bencoAtivaProximoTurno = false;
    }

    @Override
    public String aplicarHabilidadePassiva() {
        StringBuilder resultado = new StringBuilder();
        
        // Regenera vida
        int vidaRegenerada = Math.min(5, getVidaMaxima() - getVidaAtual());
        vidaAtual = Math.min(getVidaMaxima(), getVidaAtual() + 5);
        
        // Regenera mana
        int manaAntes = getManaAtual();
        regenerarMana(5);
        int manaRegenerada = getManaAtual() - manaAntes;
        
        resultado.append("Benção Divina ativa:");
        resultado.append(String.format(" +%d vida, +%d mana", vidaRegenerada, manaRegenerada));
        
        // Habilidade especial: A cada 3 turnos, cria um escudo
        if (bencoAtivaProximoTurno) {
            resultado.append(" | Escudo Sagrado próximo turno");
            bencoAtivaProximoTurno = false;
        }
        
        return resultado.toString();
    }

    @Override
    public int calcularDanoRecebido(int dano) {
        // Escudo Divino: Reduz dano em 15%
        int danoReduzido = (int) (dano * 0.85);
        
        // Chance de 10% de bloquear completamente o ataque
        if (Math.random() < 0.10) {
            System.out.println("  - ✨ " + nome + " bloqueou o ataque com Escudo Sagrado");
            return 0;
        }
        
        return danoReduzido;
    }

    @Override
    public List<String> getArmasPermitidas() {
        // Pode usar armas de guerreiro e de mago
        return Arrays.asList("Espada Longa", "Machado de Guerra", "Cajado Arcano", "Lança do Dragão");
    }

    // Habilidade especial do Paladino: Golpe Sagrado
    public String golpeSagrado(Personagem alvo) {
        if (getManaAtual() < 30) {
            return "Mana insuficiente para Golpe Sagrado (Necessário: 30 mana)";
        }
        
        consumirMana(30);
        
        // Dano baseado em força e inteligência
        int danoFisico = getAtributos().getForca();
        int danoMagico = getAtributos().getInteligencia();
        int danoTotal = danoFisico + danoMagico;
        
        alvo.receberDano(danoTotal);
        alvo.setDesprevenido(false);
        
        return String.format("%s usa Golpe Sagrado em %s\n  - Causou %d de dano sagrado (%d físico + %d mágico)",
                           getNome(), alvo.getNome(), danoTotal, danoFisico, danoMagico);
    }
}

