package com.pi.appraisal.controller;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.AreaProceso.AreaProcesoImpl;
import com.pi.appraisal.component.Impl;
import com.pi.appraisal.entity.MetaEspecifica;
import com.pi.appraisal.entity.MetaEspecifica.MetaEspecificaImpl;
import com.pi.appraisal.entity.Nivel;
import com.pi.appraisal.entity.Nivel.NivelImpl;
import com.pi.appraisal.entity.PracticaEspecifica.PracticaEspecificaImpl;
import com.pi.appraisal.repository.AreaProcesoRepository;
import com.pi.appraisal.repository.MetaEspecificaRepository;
import com.pi.appraisal.repository.NivelRepository;
import com.pi.appraisal.repository.PracticaEspecificaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de CMMI.
 * Encuentra de forma descendente todos los niveles de CMMI (2-5) y sus componentes
 */
@RestController
@RequestMapping("api/cmmi")
public class CMMIController {

    private final NivelRepository nivelRepository;
    private final AreaProcesoRepository areaProcesoRepository;
    private final MetaEspecificaRepository metaEspecificaRepository;
    private final PracticaEspecificaRepository practicaEspecificaRepository;

    @Autowired
    public CMMIController(
            NivelRepository nivelRepository,
            AreaProcesoRepository areaProcesoRepository,
            MetaEspecificaRepository metaEspecificaRepository,
            PracticaEspecificaRepository practicaEspecificaRepository) {
        this.nivelRepository = nivelRepository;
        this.areaProcesoRepository = areaProcesoRepository;
        this.metaEspecificaRepository = metaEspecificaRepository;
        this.practicaEspecificaRepository = practicaEspecificaRepository;
    }

    @GetMapping("nivel")
    public ResponseEntity<List<NivelImpl>> getAllNivel() {
        return ResponseEntity.ok(nivelRepository.findAll().stream().map(Impl::to).collect(Collectors.toList()));
    }

    @GetMapping("nivel/{lvl}")
    public ResponseEntity<NivelImpl> getNivel(@PathVariable("lvl") Integer lvl) {
        return nivelRepository.findByLvl(lvl)
                .map(Impl::to)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("area/{nivel}")
    public ResponseEntity<List<AreaProcesoImpl>> getAreaProcesos(@PathVariable("nivel") Integer nivel) {
        return ResponseEntity.ok(areaProcesoRepository.findAllByNivel(new Nivel(nivel)).stream()
                .map(Impl::to)
                .collect(Collectors.toList()));
    }

    @GetMapping("meta/{area}")
    public ResponseEntity<List<MetaEspecificaImpl>> getMetaEspecificas(@PathVariable("area") Integer area) {
        return ResponseEntity.ok(metaEspecificaRepository.findAllByAreaProceso(new AreaProceso(area)).stream()
                .map(Impl::to)
                .collect(Collectors.toList()));
    }

    @GetMapping("practica/{meta}")
    public ResponseEntity<List<PracticaEspecificaImpl>> getPracticaEspecificas(@PathVariable("meta") Integer meta) {
        return ResponseEntity.ok(practicaEspecificaRepository.findAllByMetaEspecifica(new MetaEspecifica(meta)).stream()
                .map(Impl::to)
                .collect(Collectors.toList()));
    }
}
