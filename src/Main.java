import batalha.Batalha;
import model.Personagem;
import model.personagens.*;
import strategy.Arma;
import strategy.armas.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Classe principal de demonstração do sistema de combate RPG, demonstra o uso do padrão Strategy com diferentes armas e personagens
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Sistema de Combate RPG");
        System.out.println("\n");

        // Menu principal
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n");
            System.out.println("Menu Principal");
            System.out.println("-----------------------------------------------------------");
            System.out.println("1. Demonstração rápida (Automática)");
            System.out.println("2. Batalha customizada");
            System.out.println("3. Demonstrar todas as Armas");
            System.out.println("4. Demonstrar todos os Personagens");
            System.out.println("5. Batalha todos vs todos");
            System.out.println("0. Sair");
            System.out.println("-----------------------------------------------------------");
            System.out.print("\nEscolha uma opção: ");
            
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        demonstracaoRapida();
                        break;
                    case 2:
                        batalhaCustomizada(scanner);
                        break;
                    case 3:
                        demonstrarArmas();
                        break;
                    case 4:
                        demonstrarPersonagens();
                        break;
                    case 5:
                        batalhaEpica();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\nOpção inválida!");
                }
            } catch (Exception e) {
                System.out.println("\nEntrada inválida!");
                scanner.nextLine(); // Limpa o buffer
                opcao = -1;
            }

        } while (opcao != 0);

        scanner.close();
    }

    // Demonstração rápida com uma batalha automática
    private static void demonstracaoRapida() {
        System.out.println("\n");
        System.out.println("Demonstração rápida");
        System.out.println("\n");

        // Cria os personagens
        Guerreiro boromir = new Guerreiro("Boromir");
        Mago gandalf = new Mago("Gandalf");
        Arqueiro legolas = new Arqueiro("Legolas");

        // Equipa as armas
        boromir.equiparArma(new EspadaLonga());
        gandalf.equiparArma(new CajadoArcano());
        legolas.equiparArma(new ArcoElfico());

        // Cria os times
        List<Personagem> time1 = Arrays.asList(boromir);
        List<Personagem> time2 = Arrays.asList(gandalf, legolas);

        // Inicia a batalha
        Batalha batalha = new Batalha(time1, time2);
        batalha.iniciar();

        // Executa os turnos automáticos
        int turno = 0;
        while (batalha.isEmAndamento() && turno < 10) {
            if (turno % 2 == 0 && boromir.isVivo()) {
                batalha.executarTurnoAutomatico(boromir, time2);
            } else {
                if (gandalf.isVivo()) {
                    batalha.executarTurnoAutomatico(gandalf, time1);
                } else if (legolas.isVivo()) {
                    batalha.executarTurnoAutomatico(legolas, time1);
                }
            }
            turno++;
            
            // Pausa para leitura
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignorar
            }
        }
    }

    // Permite criar uma batalha customizada
    private static void batalhaCustomizada(Scanner scanner) {
        System.out.println("\n");
        System.out.println("Batalha customizada");
        System.out.println("\n");

        // Escolhe os personagens
        System.out.println("Escolha o Time 1:");
        Personagem p1 = escolherPersonagem(scanner, "Jogador 1");
        Arma arma1 = escolherArma(scanner, p1);
        p1.equiparArma(arma1);

        System.out.println("\nEscolha o Time 2:");
        Personagem p2 = escolherPersonagem(scanner, "Jogador 2");
        Arma arma2 = escolherArma(scanner, p2);
        p2.equiparArma(arma2);

        // Cria a batalha
        List<Personagem> time1 = Arrays.asList(p1);
        List<Personagem> time2 = Arrays.asList(p2);

        Batalha batalha = new Batalha(time1, time2);
        batalha.iniciar();

        // Batalha por turnos automaticos
        int turno = 0;
        while (batalha.isEmAndamento() && turno < 20) {
            if (turno % 2 == 0 && p1.isVivo()) {
                System.out.println("\n>>> Turno de " + p1.getNome());
                System.out.print("Pressione ENTER para atacar...");
                scanner.nextLine();
                batalha.executarTurno(p1, p2, Arrays.asList(p2));
            } else if (p2.isVivo()) {
                System.out.println("\n>>> Turno de " + p2.getNome());
                System.out.print("Pressione ENTER para atacar...");
                scanner.nextLine();
                batalha.executarTurno(p2, p1, Arrays.asList(p1));
            }
            turno++;
        }
    }

    // Demonstra todas as armas disponíveis
    private static void demonstrarArmas() {
        System.out.println("\n");
        System.out.println("Demonstração de todas as armas");
        System.out.println("\n");

        Arma[] armas = {
            new EspadaLonga(),
            new ArcoElfico(),
            new CajadoArcano(),
            new MachadoDeGuerra(),
            new AdagaSombria(),
            new LancaDragao()
        };

        for (Arma arma : armas) {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("  " + arma.getNome());
            System.out.println("-----------------------------------------------------------");
            System.out.println("  Dano base: " + arma.getDanoBase());
            System.out.println("  Custo mana: " + arma.getCustoMana());
            System.out.println("  Requisitos: " + arma.getRequisitos());
            System.out.println("  Efeito especial: ");
            
            // Demonstra o efeito
            Guerreiro dummy = new Guerreiro("Alvo de teste");
            dummy.equiparArma(new EspadaLonga());
            
            // Criar atacante adequado
            Personagem atacante;
            if (arma.getNome().contains("Cajado")) {
                atacante = new Mago("Mago teste");
            } else if (arma.getNome().contains("Arco")) {
                atacante = new Arqueiro("Arqueiro teste");
            } else {
                atacante = new Guerreiro("Guerreiro teste");
            }
            atacante.equiparArma(arma);
            
            System.out.println("\n  > Teste de ataque:");
            String resultado = arma.atacar(atacante, Arrays.asList(dummy));
            System.out.println("  " + resultado.replace("\n", "\n  "));
        }
    }

    // Demonstra todos os personagens disponíveis
    private static void demonstrarPersonagens() {
        System.out.println("\n");
        System.out.println("Demonstração de todos os personagens");
        System.out.println("\n");

        Personagem[] personagens = {
            new Guerreiro("Guerreiro exemplo"),
            new Arqueiro("Arqueiro exemplo"),
            new Mago("Mago exemplo"),
            new Paladino("Paladino exemplo")
        };

        for (Personagem p : personagens) {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("  " + p.getClass().getSimpleName() + " - " + p.getNome());
            System.out.println("-----------------------------------------------------------");
            System.out.println("  Vida: " + p.getVidaMaxima());
            System.out.println("  Mana: " + p.getManaMaxima());
            System.out.println("  Força: " + p.getAtributos().getForca());
            System.out.println("  Destreza: " + p.getAtributos().getDestreza());
            System.out.println("  Inteligência: " + p.getAtributos().getInteligencia());
            System.out.println("  Habilidade passiva: " + p.aplicarHabilidadePassiva());
            System.out.println("  Armas permitidas: " + String.join(", ", p.getArmasPermitidas()));
        }
    }

    // Batalha épica com todos os personagens
    private static void batalhaEpica() {
        System.out.println("\n");
        System.out.println("Batalha todos vs todos");
        System.out.println("\n");

        // Time dos heróis
        Guerreiro aragorn = new Guerreiro("Aragorn, Rei de Gondor");
        aragorn.equiparArma(new EspadaLonga());
        
        Arqueiro legolas = new Arqueiro("Legolas, Príncipe Élfico");
        legolas.equiparArma(new ArcoElfico());
        
        Paladino gandalf = new Paladino("Gandalf, O Branco");
        gandalf.equiparArma(new LancaDragao());

        // Time dos vilões
        Mago saruman = new Mago("Saruman, O Multicolorido");
        saruman.equiparArma(new CajadoArcano());
        
        Guerreiro lurtz = new Guerreiro("Lurtz, Uruk-hai");
        lurtz.equiparArma(new MachadoDeGuerra());
        
        Arqueiro gollum = new Arqueiro("Gollum, A Criatura");
        gollum.equiparArma(new AdagaSombria());

        // Cria os times
        List<Personagem> herois = Arrays.asList(aragorn, legolas, gandalf);
        List<Personagem> viloes = Arrays.asList(saruman, lurtz, gollum);

        // Inicia a batalha
        Batalha batalha = new Batalha(herois, viloes);
        batalha.iniciar();

        // Batalha por turnos automaticos
        int turno = 0;
        while (batalha.isEmAndamento() && turno < 30) {
            List<Personagem> time1 = batalha.getTime1();
            List<Personagem> time2 = batalha.getTime2();
            
            // Encontra um herói vivo
            Personagem heroiVivo = time1.stream().filter(Personagem::isVivo).findFirst().orElse(null);
            if (heroiVivo != null) {
                batalha.executarTurnoAutomatico(heroiVivo, time2);
            }
            
            // Encontra um vilão vivo
            Personagem vilaoVivo = time2.stream().filter(Personagem::isVivo).findFirst().orElse(null);
            if (vilaoVivo != null) {
                batalha.executarTurnoAutomatico(vilaoVivo, time1);
            }
            
            turno++;
            
            // Pausa para leitura
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                // Ignorar
            }
        }
    }

    // Auxiliar para escolher o personagem
    private static Personagem escolherPersonagem(Scanner scanner, String nome) {
        System.out.println("\nEscolha a classe:");
        System.out.println("1. Guerreiro (Alta vida, resistente)");
        System.out.println("2. Arqueiro (Esquiva, ataques rápidos)");
        System.out.println("3. Mago (Alta mana, ataques mágicos)");
        System.out.println("4. Paladino (Híbrido, versátil)");
        System.out.print("Opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Nome do personagem: ");
        String nomePersonagem = scanner.nextLine();
        if (nomePersonagem.trim().isEmpty()) {
            nomePersonagem = nome;
        }
        
        switch (opcao) {
            case 1: return new Guerreiro(nomePersonagem);
            case 2: return new Arqueiro(nomePersonagem);
            case 3: return new Mago(nomePersonagem);
            case 4: return new Paladino(nomePersonagem);
            default: return new Guerreiro(nomePersonagem);
        }
    }

    // Auxiliar para escolher a arma
    private static Arma escolherArma(Scanner scanner, Personagem personagem) {
        System.out.println("\nEscolha a arma:");
        System.out.println("1. Espada Longa");
        System.out.println("2. Arco Élfico");
        System.out.println("3. Cajado Arcano");
        System.out.println("4. Machado de Guerra");
        System.out.println("5. Adaga Sombria");
        System.out.println("6. Lança do Dragão");
        System.out.print("Opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        Arma arma;
        switch (opcao) {
            case 1: arma = new EspadaLonga(); break;
            case 2: arma = new ArcoElfico(); break;
            case 3: arma = new CajadoArcano(); break;
            case 4: arma = new MachadoDeGuerra(); break;
            case 5: arma = new AdagaSombria(); break;
            case 6: arma = new LancaDragao(); break;
            default: arma = new EspadaLonga();
        }
        
        if (!personagem.equiparArma(arma)) {
            System.out.println("\nRequisitos não atendidos. Equipando espada longa.");
            arma = new EspadaLonga();
        }
        
        return arma;
    }
}

