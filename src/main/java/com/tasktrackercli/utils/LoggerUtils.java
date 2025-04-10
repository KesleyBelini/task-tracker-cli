package com.tasktrackercli.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtils {
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void log(String mensagem) {
        System.out.println("[LOG] " + LocalDateTime.now().format(FORMATADOR) + " - " + mensagem);
    }
}
