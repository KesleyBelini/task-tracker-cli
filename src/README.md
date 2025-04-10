# 🧩 Task Tracker CLI

Um gerenciador simples de tarefas para terminal, feito em Java, com persistência em JSON e foco na prática de lógica, enums, coleções e leitura/escrita de arquivos.

## ✨ Funcionalidades
- [x] Criar tarefas
- [x] Editar tarefas
- [x] Remover tarefas
- [x] Filtrar por status
- [x] Atualizar status
- [x] Persistência em `tarefas.json`

## 🛠️ Tecnologias
- Java 17
- Gson
- POO (Classes, Enums, Encapsulamento)
- CLI com `Scanner`
- Lógica de programação

## 🚀 Como rodar
```bash
javac -d out src/com/tasktrackercli/**/*.java
java -cp out com.tasktrackercli.Main
```

## 💡 Futuras melhorias
- Testes com JUnit 
- Exportar backups 
- Interface gráfica ou REST API