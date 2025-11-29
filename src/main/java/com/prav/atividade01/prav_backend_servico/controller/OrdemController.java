package com.prav.atividade01.prav_backend_servico.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

import com.prav.atividade01.prav_backend_servico.dto.OrdemDto;
import com.prav.atividade01.prav_backend_servico.dto.OrdemItemDto;
import com.prav.atividade01.prav_backend_servico.model.Ordem;
import com.prav.atividade01.prav_backend_servico.model.OrdemItem;
import com.prav.atividade01.prav_backend_servico.model.Servico;
import com.prav.atividade01.prav_backend_servico.repository.OrdemRepository;
import com.prav.atividade01.prav_backend_servico.repository.ServicoRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrdemController {

    @Autowired
    private OrdemRepository ordemRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("/ordens")
    public List<OrdemDto> listar() {
        return ordemRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/ordens/{id}")
    public ResponseEntity<OrdemDto> buscar(@PathVariable Long id) {
        return ordemRepository.findById(id).map(o -> ResponseEntity.ok(toDto(o))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/ordens")
    public ResponseEntity<OrdemDto> criar(@RequestBody OrdemDto dto) {
        Ordem ordem = fromDto(dto);
        ordem.setDataCriacao(LocalDateTime.now());
        calcularTotais(ordem);
        Ordem salvo = ordemRepository.save(ordem);
        return ResponseEntity.ok(toDto(salvo));
    }

    @PutMapping("/ordens/{id}")
    public ResponseEntity<OrdemDto> atualizar(@PathVariable Long id, @RequestBody OrdemDto dto) {
        return ordemRepository.findById(id).map(existing -> {
            existing.setReferencia(dto.getReferencia());
            existing.setClienteId(dto.getClienteId());
            existing.setStatus(dto.getStatus());
            // substituir itens
            existing.getItens().clear();
            if (dto.getItens() != null) {
                for (OrdemItemDto itemDto : dto.getItens()) {
                    OrdemItem item = new OrdemItem();
                    item.setLinhaNum(itemDto.getLinhaNum());
                    item.setDescricao(itemDto.getDescricao());
                    item.setQuantidade(itemDto.getQuantidade() == null ? BigDecimal.ZERO : itemDto.getQuantidade());
                    item.setPrecoUnitario(itemDto.getPrecoUnitario() == null ? BigDecimal.ZERO : itemDto.getPrecoUnitario());
                    if (itemDto.getServicoId() != null) {
                        Servico s = servicoRepository.findById(itemDto.getServicoId()).orElse(null);
                        item.setServico(s);
                    }
                    item.setOrdem(existing);
                    BigDecimal subtotal = item.getPrecoUnitario().multiply(item.getQuantidade());
                    item.setSubtotal(subtotal);
                    existing.getItens().add(item);
                }
            }
            calcularTotais(existing);
            Ordem atualizado = ordemRepository.save(existing);
            return ResponseEntity.ok(toDto(atualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/ordens/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return ordemRepository.findById(id).map(existing -> {
            ordemRepository.delete(existing);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // mapeamentos auxiliares
    private OrdemDto toDto(Ordem o) {
        OrdemDto dto = new OrdemDto();
        dto.setId(o.getId());
        dto.setReferencia(o.getReferencia());
        dto.setClienteId(o.getClienteId());
        dto.setDataCriacao(o.getDataCriacao());
        dto.setStatus(o.getStatus());
        dto.setTotal(o.getTotal());
        if (o.getItens() != null) {
            dto.setItens(o.getItens().stream().map(i -> {
                OrdemItemDto idto = new OrdemItemDto();
                idto.setId(i.getId());
                idto.setLinhaNum(i.getLinhaNum());
                idto.setDescricao(i.getDescricao());
                idto.setQuantidade(i.getQuantidade());
                idto.setPrecoUnitario(i.getPrecoUnitario());
                idto.setSubtotal(i.getSubtotal());
                idto.setServicoId(i.getServico() != null ? i.getServico().getCodigo() : null);
                idto.setServicoNome(i.getServico() != null ? i.getServico().getTipoServico() : null);
                return idto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private Ordem fromDto(OrdemDto dto) {
        Ordem o = new Ordem();
        o.setReferencia(dto.getReferencia());
        o.setClienteId(dto.getClienteId());
        o.setStatus(dto.getStatus());
        if (dto.getItens() != null) {
            for (OrdemItemDto idto : dto.getItens()) {
                OrdemItem item = new OrdemItem();
                item.setLinhaNum(idto.getLinhaNum());
                item.setDescricao(idto.getDescricao());
                item.setQuantidade(idto.getQuantidade() == null ? BigDecimal.ZERO : idto.getQuantidade());
                item.setPrecoUnitario(idto.getPrecoUnitario() == null ? BigDecimal.ZERO : idto.getPrecoUnitario());
                if (idto.getServicoId() != null) {
                    Servico s = servicoRepository.findById(idto.getServicoId()).orElse(null);
                    item.setServico(s);
                }
                item.setOrdem(o);
                o.getItens().add(item);
            }
        }
        return o;
    }

    private void calcularTotais(Ordem ordem) {
        BigDecimal total = BigDecimal.ZERO;
        if (ordem.getItens() != null) {
            for (OrdemItem item : ordem.getItens()) {
                if (item.getPrecoUnitario() == null) item.setPrecoUnitario(BigDecimal.ZERO);
                if (item.getQuantidade() == null) item.setQuantidade(BigDecimal.ZERO);
                BigDecimal subtotal = item.getPrecoUnitario().multiply(item.getQuantidade());
                item.setSubtotal(subtotal);
                total = total.add(subtotal);
            }
        }
        ordem.setTotal(total);
    }
}
