package com.tasktrackercli.service;

import com.tasktrackercli.model.StatusTaskEnum;
import com.tasktrackercli.model.Task;
import com.tasktrackercli.repository.TaskRepository;
import com.tasktrackercli.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tasktrackercli.utils.LoggerUtils.log;

public class TaskService {
    private final TaskRepository repositorio;
    private List<Task> listaDeTarefas = new ArrayList<>();
    private static int ultimoId = 0;
    private static final LoggerUtils log = new LoggerUtils();

    public TaskService(TaskRepository repositorio) {
        this.repositorio = repositorio;
        this.listaDeTarefas = repositorio.carregarTarefasDoArquivo();
        atualizarUltimoId();
    }

    public void criarTarefa(String descricao) {
        int id = gerarId();
        Task tarefa = new Task(id, descricao);
        listaDeTarefas.add(tarefa);
        log("[OK] Tarefa criada: ID " + tarefa.getId());
        salvarTarefas();
    }

    public void editarTarefa(Integer id, String novaDescricao) {
        Task tarefa = buscarTarefaPorId(id);

        if (tarefa != null) {
            tarefa.setDescricao(novaDescricao);
            log("[INFO] Tarefa editada: ID " + tarefa.getId());
        } else {
            log("[ERRO] Tarefa não encontrada: ID " + id);
        }
        salvarTarefas();
    }

    public void excluirTarefa(Integer id) {
        Task tarefa = buscarTarefaPorId(id);

        if (tarefa != null) {
            listaDeTarefas.remove(tarefa);
            log("[OK] Tarefa excluida: ID " + tarefa.getId());
        } else {
            log("[ERRO] Tarefa não encontrada: ID " + id);
        }
        salvarTarefas();
    }

    public List<Task> pegarListaDeTarefas() {
        if (listaDeTarefas.isEmpty()) {
            log("[INFO] Nenhuma tarefa encontrada");
            return null;
        } else {
            return new ArrayList<>(listaDeTarefas);
        }
    }

    public List<Task> pegarTarefasPorStatus(StatusTaskEnum status) {
        return listaDeTarefas.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public void mudarStatusTarefa(Integer id, StatusTaskEnum novoStatus) {
        Task tarefa = buscarTarefaPorId(id);
        if (tarefa != null) {
            tarefa.setStatus(novoStatus);
            log("[OK] Tarefa atualizada: ID" + tarefa.getId());
        } else {
            log("[ERRO] Tarefa não encontrada: " + id);
        }
        salvarTarefas();
    }

    public void salvarTarefas() {
        repositorio.escreverTarefasNoArquivo(listaDeTarefas);
        log("[INFO] Tarefa salva com sucesso! Total de tarefas: " + listaDeTarefas.size());
    }

    public Task buscarTarefaPorId(int id) {
        for (Task tarefa : listaDeTarefas) {
            if (tarefa.getId() == id) {
                return tarefa;
            }
        }
        return null;
    }

    private int gerarId() {
        return ++ultimoId;
    }

    public void atualizarUltimoId() {
        for (Task tarefa : listaDeTarefas) {
            if (tarefa.getId() > ultimoId) {
                ultimoId = tarefa.getId();
            }
        }
    }
}
