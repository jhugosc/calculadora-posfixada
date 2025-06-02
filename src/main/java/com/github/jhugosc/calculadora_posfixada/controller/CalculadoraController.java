package com.github.jhugosc.calculadora_posfixada.controller;

import com.github.jhugosc.calculadora_posfixada.dto.ExpressaoRequestDTO;
import com.github.jhugosc.calculadora_posfixada.dto.ResultadoResponseDTO;
import com.github.jhugosc.calculadora_posfixada.dto.ErrorResponseDTO;
import com.github.jhugosc.calculadora_posfixada.service.CalculadoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculadora")
public class CalculadoraController {

    private final CalculadoraService calculadoraService;

    public CalculadoraController(CalculadoraService calculadoraService) {
        this.calculadoraService = calculadoraService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<?> calcular(@RequestBody ExpressaoRequestDTO expressaoRequest) {
        try {
            double resultado = calculadoraService.calcularExpressao(expressaoRequest.getExpressao());
            return ResponseEntity.ok(new ResultadoResponseDTO(resultado));
        } catch (IllegalArgumentException | ArithmeticException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
