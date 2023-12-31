package br.com.pizzaria.uniamerica.service;

import br.com.pizzaria.uniamerica.dto.saborDTOs.SaborDTO;
import br.com.pizzaria.uniamerica.entities.Sabor;
import br.com.pizzaria.uniamerica.repository.SaborRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaborService {
    @Autowired
    private SaborRepository saborRepository;

    public SaborDTO findById(Long id){
        Sabor sabor = this.saborRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        return copyEntityToDto(sabor);
    }

    public List<SaborDTO> findAll(){
        List<Sabor> list = this.saborRepository.findAll();
        List<SaborDTO> saborDTOList = new ArrayList<>();
        for (Sabor sabor: list) {
            if (sabor.isAtivo()){
                var transformaEmDTO = copyEntityToDto(sabor);
                saborDTOList.add(transformaEmDTO);
            }
        }
        return saborDTOList;
    }

    @Transactional
    public SaborDTO altera(Sabor sabor){
        Sabor saborAlterado = this.saborRepository.findById(sabor.getId())
                .orElseThrow(()-> new RuntimeException("Id não encontrado!"));

        saborAlterado.setNome(sabor.getNome());

        this.saborRepository.save(saborAlterado);
        return copyEntityToDto(saborAlterado);
    }

    @Transactional
    public SaborDTO cadastra(SaborDTO saborDTO){
        Sabor sabor = new Sabor(saborDTO);
        this.saborRepository.save(sabor);
        return copyEntityToDto(sabor);
    }


    @Transactional
    public String desativa(Long id){
        Sabor sabor = this.saborRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado!"));
        sabor.setAtivo(false);
        return "Sabor desativado com sucesso!";
    }


    private SaborDTO copyEntityToDto(Sabor sabor) {
        SaborDTO saborDTO = new SaborDTO();
        saborDTO.setId(sabor.getId());
        saborDTO.setNome(sabor.getNome());
        return saborDTO;
    }
}
