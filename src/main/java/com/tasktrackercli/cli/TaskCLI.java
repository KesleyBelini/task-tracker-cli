package com.tasktrackercli.cli;

import com.tasktrackercli.model.StatusTaskEnum;
import com.tasktrackercli.model.Task;
import com.tasktrackercli.repository.TaskRepository;
import com.tasktrackercli.service.TaskService;
import com.tasktrackercli.utils.TaskFormatter;

import java.util.List;
import java.util.Scanner;

public class TaskCLI {
    private final TaskService service;
    private final Scanner sc;

    public TaskCLI() {
        TaskRepository repositorio = new TaskRepository("tarefas.json");
        this.service = new TaskService(repositorio);
        this.sc = new Scanner(System.in);
    }

    public void init() {
        System.out.println("[INFO] Bem vindo ao Task Tracker CLI");
        boolean rodando = true;

        while (rodando) {
            int opcao = exibirMenu();

            switch (opcao) {
                case 0 -> {
                    System.out.println("[INFO] Finalizando o programa");
                    rodando = false;
                }
                case 1 -> adicionarTarefa();
                case 2 -> listarTarefas();
                case 3 -> listarTarefasPorStatus();
                case 4 -> editarTarefa();
                case 5 -> deletarTarefa();
                case 6 -> editarStatusTarefa();
                default -> System.out.println("[ERRO] Opção inválida, tente novamente");
            }
        }
    }

    public int exibirMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Adicionar Tarefa");
        System.out.println("2 - Listar Tarefas");
        System.out.println("3 - Listar Tarefas por Status");
        System.out.println("4 - Editar Tarefa");
        System.out.println("5 - Deletar Tarefa");
        System.out.println("6 - Editar Status da Tarefa");
        System.out.println("0 - Sair");

        System.out.println("Informe a opção desejada: ");
        while (!sc.hasNextInt()) {
            System.out.println("[ERRO] Opção inválida, tente novamente: ");
            sc.next();
        }
        return sc.nextInt();
    }

    public void adicionarTarefa() {
        limparBuffer();
        System.out.println("Adicione uma descrição a tarefa: ");
        String descricao = sc.nextLine().trim();
        service.criarTarefa(descricao);
    }

    public void listarTarefas() {
        System.out.println("\n--- Lista de Tarefas ---");
        List<Task> listaCarregada = service.pegarListaDeTarefas();

        System.out.println("[INFO] Total de tarefas: " + listaCarregada.size());
        for (int i = 0; i < listaCarregada.size(); i++) {
            Task tarefa = listaCarregada.get(i);
            System.out.println((i + 1) + " - " + TaskFormatter.formatarExibicaoTarefa(tarefa));
        }
    }

    public void listarTarefasPorStatus() {
        limparBuffer();
        System.out.println("[INFO] Escolha um status: ");
        String statusInput = sc.nextLine().replace(" ", "_").toUpperCase();

        List<Task> listaDeTarefasPorStatus;
        try {
            StatusTaskEnum statusEnum = StatusTaskEnum.valueOf(statusInput);
            listaDeTarefasPorStatus = service.pegarTarefasPorStatus(statusEnum);

            if (listaDeTarefasPorStatus.isEmpty()) {
                System.out.println("[INFO] Nenhuma tarefa encontrada com o status: " + statusEnum);
            } else {
                System.out.println("\n--- Lista de Tarefas com o Status: " + statusEnum + " ---");
                for (Task tarefa : listaDeTarefasPorStatus) {
                    System.out.println(TaskFormatter.formatarExibicaoTarefa(tarefa));
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] Status inválido! Opções disponíveis: Pendente, Em andamento, Concluída.");
        }
    }

    public void editarTarefa() {
        System.out.println("[INFO] Informe o ID da tarefa que deseja editar: ");
        while (!sc.hasNextInt()) {
            System.out.println("[INFO] Entrada inválida, Digite um número: ");
            sc.next();
        }
        int id = sc.nextInt();
        limparBuffer();

        System.out.println("[INFO] Informe a nova descrição: ");
        String novaDescricao = sc.nextLine();
        service.editarTarefa(id, novaDescricao);
    }

    public void deletarTarefa() {
        System.out.println("[INFO] Informe o ID da tarefa que deseja excluir: ");
        while (!sc.hasNextInt()) {
            System.out.println("[INFO] Entrada inválida, Digite um número: ");
            sc.next();
        }
        int idExcluir = sc.nextInt();
        limparBuffer();

        System.out.println("[INFO] Tem certeza que deseja excluir a tarefa? (S/N)");
        String confirmacao = sc.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            service.excluirTarefa(idExcluir);
            System.out.println("[OK] Tarefa excluída com sucesso!");
        } else {
            System.out.println("[INFO] Operação cancelada");
        }
    }

    public void editarStatusTarefa() {
        System.out.println("[INFO] Informe o ID da tarefa que deseja alterar o status: ");
        while (!sc.hasNextInt()) {
            System.out.println("[ERRO] Entrada inválida! Digite um número: ");
            sc.next();
        }
        int idTarefa = sc.nextInt();
        limparBuffer();

        Task tarefa = service.buscarTarefaPorId(idTarefa);
        if (tarefa != null) {
            System.out.println("[INFO] Status atual: " + tarefa.getStatus());
            StatusTaskEnum novoStatus = null;

            while (novoStatus == null) {
                exibirStatusDisponiveis();
                System.out.print("[INFO] Escolha um status: ");
                String entradaStatus = sc.nextLine().replace(" ", "_").toUpperCase();
                try {
                    novoStatus = StatusTaskEnum.valueOf(entradaStatus);
                } catch (IllegalArgumentException e) {
                    System.out
                            .println("[ERRO] Status inválido! Opções disponíveis: Pendente, Em andamento, Concluído.");
                }
            }
            service.mudarStatusTarefa(idTarefa, novoStatus);
            System.out.println("[OK] Status alterado com sucesso!");
        } else {
            System.out.println("[INFO] Tarefa não encontrada com o ID: " + idTarefa);
        }
    }

    public void exibirStatusDisponiveis() {
        System.out.println("[INFO] Status disponíveis:");
        for (StatusTaskEnum status : StatusTaskEnum.values()) {
            System.out.printf(" - %s%n", status.toString());
        }
    }

    public void limparBuffer() {
        sc.nextLine(); // Consumindo o buffer
    }
}
