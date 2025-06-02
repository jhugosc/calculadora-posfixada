package com.github.jhugosc.calculadora_posfixada.controller;

import com.github.jhugosc.calculadora_posfixada.dto.ExpressaoRequestDTO;
import com.github.jhugosc.calculadora_posfixada.dto.ResultadoResponseDTO;
import com.github.jhugosc.calculadora_posfixada.dto.ErrorResponseDTO;
import com.github.jhugosc.calculadora_posfixada.service.CalculadoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que essa classe é um controller REST
@RequestMapping("/api/calculadora") // Define a URL base para esse controller
public class CalculadoraController {

    private final CalculadoraService calculadoraService;

    // Injeção de dependência do serviço de cálculo via construtor
    public CalculadoraController(CalculadoraService calculadoraService) {
        this.calculadoraService = calculadoraService;
    }

    // Endpoint POST para calcular a expressão enviada no corpo da requisição
    @PostMapping("/calcular")
    public ResponseEntity<?> calcular(@RequestBody ExpressaoRequestDTO expressaoRequest) {
        try {
            // Chama o serviço que executa o cálculo da expressão pós-fixada
            double resultado = calculadoraService.calcularExpressao(expressaoRequest.getExpressao());

            // Retorna o resultado dentro de um DTO com status 200 (OK)
            return ResponseEntity.ok(new ResultadoResponseDTO(resultado));

        } catch (IllegalArgumentException | ArithmeticException e) {
            // Captura erros de expressão malformada ou erros matemáticos (como divisão por zero)
            // Retorna status 400 (Bad Request) com mensagem de erro encapsulada no DTO de erro
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
