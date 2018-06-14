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
import com.pi.appraisal.repository.MetaEspecificaResporitory;
import com.pi.appraisal.repository.NivelRepository;
import com.pi.appraisal.repository.PracticaEspecificaRepository;

@RestController
@RequestMapping("api/cmmi")
public class CMMIController {
	
	@Autowired
	private NivelRepository nivelRepository;
	@Autowired
	private AreaProcesoRepository areaProcesoRepository;
	@Autowired
	private MetaEspecificaResporitory metaEspecificarResporitory;
	@Autowired
	private PracticaEspecificaRepository practicaEspecificaRepository;

	@GetMapping("nivel")
	public ResponseEntity<Nivel.NivelImpl> getNivelByLvl(@RequestBody() Integer lvl) {
		return nivelRepository.findByLvl(lvl).map(nivel -> ResponseEntity.ok(nivel)).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("area")
	public ResponseEntity<List<AreaProceso.AreaProcesoImpl>> getAreaProcesoByNivel(@RequestBody() Nivel nivel) {
		return ResponseEntity.ok(areaProcesoRepository.findAllByNivel(nivel));
	}
	
	@GetMapping("meta")
	public ResponseEntity<List<MetaEspecifica.MetaEspecificaImpl>> getMetaEspecificaByAreaProceso(@RequestBody() AreaProceso area) {
		return ResponseEntity.ok(metaEspecificarResporitory.findAllByAreaProceso(area));
	}
	
	@GetMapping("practica")
	public ResponseEntity<List<PracticaEspecifica.PracticaEspecificaImpl>> getPracticaEspecificaByMetaEspecifica(@RequestBody() MetaEspecifica meta) {
		return ResponseEntity.ok(practicaEspecificaRepository.findAllByMetaEspecifica(meta));
	}
}
