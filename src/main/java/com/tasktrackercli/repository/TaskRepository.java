package com.tasktrackercli.repository;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.tasktrackercli.model.Task;
import com.tasktrackercli.utils.LocalDateTimeAdapter;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskRepository {
    private final String nomeArquivo;

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    public TaskRepository(String caminhoArquivo) {
        this.nomeArquivo = caminhoArquivo;
    }

    public void escreverTarefasNoArquivo(List<Task> tarefas) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(tarefas, writer);
        } catch (IOException e) {
            System.out.println("[ERRO] Erro ao salvar as tarefas no JSON: " + e.getMessage());
        }
    }

    public List<Task> carregarTarefasDoArquivo() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                jsonBuilder.append(linha);
            }

            String json = jsonBuilder.toString().trim();
            if (json.isEmpty()) {
                return new ArrayList<>();
            }

            Task[] tarefasArray = gson.fromJson(json, Task[].class);
            return tarefasArray == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(tarefasArray));
        } catch (IOException | JsonSyntaxException e) {

            System.out.println("[ERRO] Erro ao carregar as tarefas do JSON.");
            return new ArrayList<>();
        }
    }
}

