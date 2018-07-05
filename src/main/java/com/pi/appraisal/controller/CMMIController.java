package com.pi.appraisal.controller;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.MetaEspecifica;
import com.pi.appraisal.entity.Nivel;
import com.pi.appraisal.entity.PracticaEspecifica;
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

    @GetMapping("nivel/{lvl}")
    public ResponseEntity<Nivel> getNivel(@PathVariable("lvl") Integer lvl) {
        return nivelRepository.findByLvl(lvl).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("area/{nivel}")
    public ResponseEntity<List<AreaProceso>> getAreaProcesos(@PathVariable("nivel") Integer nivel) {
        return ResponseEntity.ok(areaProcesoRepository.findAllByNivel(new Nivel(nivel)));
    }

    @GetMapping("meta/{area}")
    public ResponseEntity<List<MetaEspecifica>> getMetaEspecificas(@PathVariable("area") Integer area) {
        return ResponseEntity.ok(metaEspecificaRepository.findAllByAreaProceso(new AreaProceso(area)));
    }

    @GetMapping("practica/{meta}")
    public ResponseEntity<List<PracticaEspecifica>> getPracticaEspecificas(@PathVariable("meta") Integer meta) {
        return ResponseEntity.ok(practicaEspecificaRepository.findAllByMetaEspecifica(new MetaEspecifica(meta)));
    }
}
