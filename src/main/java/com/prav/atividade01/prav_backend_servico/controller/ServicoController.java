package com.prav.atividade01.prav_backend_servico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prav.atividade01.prav_backend_servico.exception.ResourceNotFoundException;
import com.prav.atividade01.prav_backend_servico.repository.ServicoRepository;
import com.prav.atividade01.prav_backend_servico.model.Servico;



@RestController
@RequestMapping("/cservico/")
@CrossOrigin(origins="*")
public class ServicoController {
	
	//Cria o repositorio JPA para ser usado aqui no controlador
	@Autowired
	private ServicoRepository autorep;
	
	
	@GetMapping("/servico")  //Indica que esse será o nome do endereço a ser chamado
	public List<Servico> listar(){
		
		//para chamar o "listar", o endereço completo deverá ser:
		// http://localhost:8080/cservico/servico -- usando o protocolo http, método GET
		
		return autorep.findAll();
		
	}
	
	@GetMapping("/servico/{id}")
	public ResponseEntity<Servico> consultar(@PathVariable Long id) {
		
		Servico auto = autorep.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Servico nao encontrado."));
			
		return ResponseEntity.ok(auto);
	}
	
@PutMapping("/servico/{id}")
public ResponseEntity<Servico> alterar(@PathVariable Long id, @RequestBody Servico servico) {
    Servico auto = autorep.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Servico nao encontrado."));

    auto.setCodigo(servico.getCodigo());
    auto.setTipoServico(servico.getTipoServico());
    auto.setDescricaoDetalhada(servico.getDescricaoDetalhada());
    auto.setDataExecucao(servico.getDataExecucao());
    auto.setValor(servico.getValor());
    auto.setDisponivel(servico.disponivel());

    Servico atualizado = autorep.save(auto);
    return ResponseEntity.ok(atualizado);
}	


// Método para Criar (Create)
@PostMapping("/servico")
public Servico incluir(@RequestBody Servico servico) {
    
    return autorep.save(servico);
    
}

// Método para Excluir (Delete)
@DeleteMapping("/servico/{id}")
public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Long id) {
    // 1. Busca o servico pelo ID
    // Lança exceção se não for encontrado, seguindo o padrão de 'consultar' e 'alterar' [4, 5]
    Servico auto = autorep.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Servico nao encontrado para exclusao."));
    
    // 2. Exclui o servico encontrado
    autorep.delete(auto);
    
	Map<String, Boolean> resposta = new HashMap<>();
	resposta.put("Servico Excluido!", true);       

	return ResponseEntity.ok (resposta);
}


}
