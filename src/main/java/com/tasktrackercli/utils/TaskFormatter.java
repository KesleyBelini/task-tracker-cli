package com.tasktrackercli.utils;

import com.tasktrackercli.model.Task;

public class TaskFormatter {
    public static String formatarExibicaoTarefa(Task tarefa) {
        String icone = switch (tarefa.getStatus()) {
            case PENDENTE -> "🕒";
            case EM_ANDAMENTO -> "🔄";
            case CONCLUIDA -> "✅";
        };

        return String.format("%s [%d] %s", icone, tarefa.getId(), tarefa.getDescricao());
    }
}
