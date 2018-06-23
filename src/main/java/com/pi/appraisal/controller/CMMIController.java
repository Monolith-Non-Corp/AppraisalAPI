package com.pi.appraisal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.appraisal.entity.AreaProceso;
import com.pi.appraisal.entity.MetaEspecifica;
import com.pi.appraisal.entity.Nivel;
import com.pi.appraisal.entity.PracticaEspecifica;
import com.pi.appraisal.repository.AreaProcesoRepository;
import com.pi.appraisal.repository.MetaEspecificaRepository;
import com.pi.appraisal.repository.NivelRepository;
import com.pi.appraisal.repository.PracticaEspecificaRepository;

@RestController
@RequestMapping("api/cmmi")
public class CMMIController {
	
	private final NivelRepository nivelRepository;
	private final AreaProcesoRepository areaProcesoRepository;
	private final MetaEspecificaRepository metaEspecificaRepository;
	private final PracticaEspecificaRepository practicaEspecificaRepository;

	@Autowired
	public CMMIController(NivelRepository nivelRepository, AreaProcesoRepository areaProcesoRepository, MetaEspecificaRepository metaEspecificaRepository, PracticaEspecificaRepository practicaEspecificaRepository) {
		this.nivelRepository = nivelRepository;
		this.areaProcesoRepository = areaProcesoRepository;
		this.metaEspecificaRepository = metaEspecificaRepository;
		this.practicaEspecificaRepository = practicaEspecificaRepository;
	}

	@GetMapping("nivel")
	public ResponseEntity<Nivel.NivelImpl> getNivelByLvl(@RequestBody() Integer lvl) {
		return nivelRepository.findByLvl(lvl).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("area")
	public ResponseEntity<List<AreaProceso.AreaProcesoImpl>> getAreaProcesoByNivel(@RequestBody() Nivel nivel) {
		return ResponseEntity.ok(areaProcesoRepository.findAllByNivel(nivel));
	}
	
	@GetMapping("meta")
	public ResponseEntity<List<MetaEspecifica.MetaEspecificaImpl>> getMetaEspecificaByAreaProceso(@RequestBody() AreaProceso area) {
		return ResponseEntity.ok(metaEspecificaRepository.findAllByAreaProceso(area));
	}
	
	@GetMapping("practica")
	public ResponseEntity<List<PracticaEspecifica.PracticaEspecificaImpl>> getPracticaEspecificaByMetaEspecifica(@RequestBody() MetaEspecifica meta) {
		return ResponseEntity.ok(practicaEspecificaRepository.findAllByMetaEspecifica(meta));
	}
}
