package com.example.f4b14n;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.Socket;

@Controller
public class ControladorSocket {

    private String enviarComandoAlServidor(String comando) throws IOException {
        try (Socket socket = new Socket("localhost", 1234);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(comando);
            return in.readLine();
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";  // Thymeleaf busca templates/index.html sin extensión
    }

    @PostMapping("/enviar")
    public String enviarComando(@RequestParam String comando, Model model) {
        try {
            String respuesta = enviarComandoAlServidor(comando);
            model.addAttribute("respuesta", respuesta);
        } catch (Exception e) {
            model.addAttribute("respuesta", "Error: " + e.getMessage());
        }
        model.addAttribute("comando", comando);
        return "index";
    }
}
