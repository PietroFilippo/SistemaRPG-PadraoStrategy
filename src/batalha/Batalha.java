package batalha;

import model.Personagem;
import strategy.Arma;
import java.util.ArrayList;
import java.util.List;


// Gerencia o sistema de batalha por turnos
public class Batalha {
    private final List<Personagem> time1;
    private final List<Personagem> time2;
    private int turnoAtual;
    private boolean batalhaEmAndamento;

    public Batalha(List<Personagem> time1, List<Personagem> time2) {
        this.time1 = new ArrayList<>(time1);
        this.time2 = new ArrayList<>(time2);
        this.turnoAtual = 1;
        this.batalhaEmAndamento = true;
    }

  
    // Inicia a batalha
 
    public void iniciar() {

        System.out.println("Batalha Iniciada");
        System.out.println("\nTime 1:");
        for (Personagem p : time1) {
            System.out.println("  - " + p.getStatus());
        }
        System.out.println("\nTime 2:");
        for (Personagem p : time2) {
            System.out.println("  - " + p.getStatus());
        }
        System.out.println("\n");
    }

    // Executa um turno da batalha
    public void executarTurno(Personagem atacante, Personagem alvo, List<Personagem> alvos) {
        if (!batalhaEmAndamento) {
            return;
        }

        System.out.println("\nTurno " + turnoAtual);

        // Reseta o atordoamento no início do turno
        atacante.setAtordoado(false);

        // Processa os efeitos do atacante
        System.out.println("→ Processando efeitos de " + atacante.getNome() + ":");
        String efeitosAtacante = atacante.processarEfeitos();
        if (!efeitosAtacante.isEmpty()) {
            System.out.println(efeitosAtacante);
        } else {
            System.out.println("  - Nenhum efeito ativo\n");
        }

        // Aplica habilidade passiva
        System.out.println("→ " + atacante.aplicarHabilidadePassiva() + "\n");

        // Verifica se está atordoado
        if (atacante.isAtordoado()) {
            System.out.println(atacante.getNome() + " está atordoado e não pode agir\n");
            turnoAtual++;
            return;
        }

        // Verifica se o atacante está vivo após efeitos
        if (!atacante.isVivo()) {
            System.out.println(atacante.getNome() + " foi derrotado pelos efeitos\n");
            verificarVitoria();
            return;
        }

        // Executa o ataque
        if (atacante.getArmaEquipada() != null) {
            List<Personagem> alvosVivos = new ArrayList<>();
            if (alvos != null && !alvos.isEmpty()) {
                for (Personagem a : alvos) {
                    if (a.isVivo()) {
                        alvosVivos.add(a);
                    }
                }
            } else if (alvo != null && alvo.isVivo()) {
                alvosVivos.add(alvo);
            }

            if (!alvosVivos.isEmpty()) {
                String resultado = atacante.getArmaEquipada().atacar(atacante, alvosVivos);
                System.out.println(resultado);
            } else {
                System.out.println("Nenhum alvo válido para atacar\n");
            }
        } else {
            System.out.println(atacante.getNome() + " não possui arma equipada\n");
        }

        // Processa os efeitos do alvo
        if (alvo != null && alvo.isVivo()) {
            System.out.println("→ Processando efeitos de " + alvo.getNome() + ":");
            String efeitosAlvo = alvo.processarEfeitos();
            if (!efeitosAlvo.isEmpty()) {
                System.out.println(efeitosAlvo);
            } else {
                System.out.println("  - Nenhum efeito ativo\n");
            }
        }

        // Mostra o status atual
        mostrarStatusAtual();

        // Verifica a vitória
        verificarVitoria();

        turnoAtual++;
    }


    // Executa um turno automático
    public void executarTurnoAutomatico(Personagem atacante, List<Personagem> inimigos) {
        // Escolhe o inimigo com menos vida
        Personagem alvo = null;
        int menorVida = Integer.MAX_VALUE;
        
        for (Personagem inimigo : inimigos) {
            if (inimigo.isVivo() && inimigo.getVidaAtual() < menorVida) {
                menorVida = inimigo.getVidaAtual();
                alvo = inimigo;
            }
        }

        if (alvo != null) {
            executarTurno(atacante, alvo, List.of(alvo));
        }
    }

    // Permite trocar a arma durante a batalha
    public boolean trocarArma(Personagem personagem, Arma novaArma) {
        if (!personagem.isVivo()) {
            System.out.println(personagem.getNome() + " está morto e não pode trocar de arma");
            return false;
        }

        if (personagem.equiparArma(novaArma)) {
            System.out.println("\n✓ " + personagem.getNome() + " equipou " + novaArma.getNome());
            System.out.println("  Requisitos: " + novaArma.getRequisitos());
            return true;
        } else {
            System.out.println("\n✗ " + personagem.getNome() + " não atende aos requisitos para usar " + novaArma.getNome());
            System.out.println("  Requisitos: " + novaArma.getRequisitos());
            return false;
        }
    }

    // Mostra o status atual de todos os personagens
    private void mostrarStatusAtual() {
        System.out.println("\n------------- Status Atual -------------");
        System.out.println("\nTime 1:");
        for (Personagem p : time1) {
            System.out.println("  " + p.getStatus() + (p.isVivo() ? " V" : " X [Morto]"));
        }
        System.out.println("\nTime 2:");
        for (Personagem p : time2) {
            System.out.println("  " + p.getStatus() + (p.isVivo() ? " V" : " X [Morto]"));
        }
        System.out.println("\n");
    }

    // Verifica se algum time venceu
    private void verificarVitoria() {
        boolean time1Vivo = time1.stream().anyMatch(Personagem::isVivo);
        boolean time2Vivo = time2.stream().anyMatch(Personagem::isVivo);

        if (!time1Vivo) {
            System.out.println("\nTime 2 venceu a batalha");
            batalhaEmAndamento = false;
        } else if (!time2Vivo) {
            System.out.println("\nTime 1 venceu a batalha");
            batalhaEmAndamento = false;
        }
    }

    // Retorna se a batalha ainda está em andamento
    public boolean isEmAndamento() {
        return batalhaEmAndamento;
    }

    public int getTurnoAtual() {
        return turnoAtual;
    }

    public List<Personagem> getTime1() {
        return new ArrayList<>(time1);
    }

    public List<Personagem> getTime2() {
        return new ArrayList<>(time2);
    }
}

