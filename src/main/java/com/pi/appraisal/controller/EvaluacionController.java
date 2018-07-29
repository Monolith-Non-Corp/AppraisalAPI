package com.pi.appraisal.controller;

import com.pi.appraisal.component.SessionCache;
import com.pi.appraisal.repository.EvidenciaRepository;
import com.pi.appraisal.repository.NivelRepository;
import com.pi.appraisal.repository.OrganizacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.pi.appraisal.entity.UsuarioRol.Priviledge.ADMINISTRADOR;

@RestController
@RequestMapping("api/evaluacion")
public class EvaluacionController {

    private final OrganizacionRepository organizacionRepository;
    private final NivelRepository nivelRepository;
    private final EvidenciaRepository evidenciaRepository;
    private final SessionCache session;

    public EvaluacionController(OrganizacionRepository organizacionRepository, NivelRepository nivelRepository, EvidenciaRepository evidenciaRepository, SessionCache session) {
        this.organizacionRepository = organizacionRepository;
        this.nivelRepository = nivelRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.session = session;
    }

    @GetMapping("{organizacion}")
    public ResponseEntity<StatusImpl> validate(@PathVariable("organizacion") Integer organizacionIn,
                                               @RequestHeader("Credentials") String credentials) {
        return session.authenticate(credentials, ADMINISTRADOR)
                .map(usuario -> organizacionRepository.findById(organizacionIn).map(organizacion -> {
                    StatusBuilder builder = StatusBuilder.builder();
                    nivelRepository.findAllByLvlIsLessThanEqualOrderByLvlAsc(organizacion.getNivel().getLvl()).forEach(nivel -> {
                        builder.node(nivelNode -> { nivelNode
                                    .setId(nivel.getLvl())
                                    .setName(String.valueOf(nivel.getLvl()))
                                    .setDescription(nivel.getDescripcion());
                            nivel.getAreaProcesos().forEach(area -> {
                                nivelNode.add(areaNode -> { areaNode
                                            .setId(area.getId())
                                            .setName(area.getNombre())
                                            .setDescription(area.getDescripcion());
                                    area.getMetaEspecificas().forEach(meta -> {
                                        areaNode.add(metaNode -> { metaNode
                                                    .setId(meta.getId())
                                                    .setName(meta.getNombre())
                                                    .setDescription(meta.getDescripcion());
                                            meta.getPracticaEspecificas().forEach(practica -> {
                                                metaNode.add(practicaNode -> { practicaNode
                                                            .setId(practica.getId())
                                                            .setName(practica.getNombre())
                                                            .setDescription(practica.getDescripcion());
                                                    practicaNode.setCompleted(evidenciaRepository
                                                            .findAllByEvaluation(practica.getId(), organizacion.getId()).stream()
                                                            .anyMatch(e -> !e.getHipervinculos().isEmpty() || !e.getArtefactos().isEmpty())
                                                    );
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                    return ResponseEntity.ok(builder.build());
                }).orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    public enum Status {
        NO_CUMPLE("No cumple"),
        EN_PROGRESO("En progreso"),
        SI_CUMPLE("Si cumple");

        public String message;

        Status(String message) {
            this.message = message;
        }

        public static StatusBuilder builder() {
            return new StatusBuilder();
        }
    }

    private static class StatusBuilder {
        public List<StatusNodeBuilder> nodes = new ArrayList<>();

        public static StatusBuilder builder() {
            return new StatusBuilder();
        }

        public StatusBuilder node(Consumer<StatusNodeBuilder> consumer) {
            StatusNodeBuilder builder = new StatusNodeBuilder();
            consumer.accept(builder);
            this.nodes.add(builder);
            return this;
        }

        public StatusImpl build() {
            StatusImpl first = new StatusImpl();
            for (StatusNodeBuilder node0 : nodes) {
                StatusImpl second = new StatusImpl();
                second.data.id = node0.id;
                second.data.name = node0.name;
                second.data.description = node0.description;
                for (StatusNodeBuilder node1 : node0.nodes) {
                    StatusImpl third = new StatusImpl();
                    third.data.id = node1.id;
                    third.data.name = node1.name;
                    third.data.description = node1.description;
                    for (StatusNodeBuilder node2 : node1.nodes) {
                        StatusImpl fourth = new StatusImpl();
                        fourth.data.id = node2.id;
                        fourth.data.name = node2.name;
                        fourth.data.description = node2.description;
                        for (StatusNodeBuilder node3 : node2.nodes) {
                            StatusImpl fifth = new StatusImpl();
                            fifth.data.id = node3.id;
                            fifth.data.name = node3.name;
                            fifth.data.description = node3.description;
                            fifth.data.status = node3.completed ? Status.SI_CUMPLE : Status.NO_CUMPLE;
                            fifth.data.progress = node3.completed ? 1F : 0F;
                            fourth.children.add(fifth);
                        }
                        third.children.add(fourth.calculate());
                    }
                    second.children.add(third.calculate());
                }
                first.children.add(second.calculate());
            }
            return first.calculate();
        }

        private class StatusNodeBuilder {
            public List<StatusNodeBuilder> nodes = new ArrayList<>();
            public Integer id;
            private String name;
            private String description;
            private boolean completed;

            public StatusNodeBuilder setId(Integer id) {
                this.id = id;
                return this;
            }

            public StatusNodeBuilder setName(String name) {
                this.name = name;
                return this;
            }

            public StatusNodeBuilder setDescription(String description) {
                this.description = description;
                return this;
            }

            public StatusNodeBuilder setCompleted(boolean completed) {
                this.completed = completed;
                return this;
            }

            public StatusNodeBuilder add(Consumer<StatusNodeBuilder> consumer) {
                StatusNodeBuilder builder = new StatusNodeBuilder();
                consumer.accept(builder);
                this.nodes.add(builder);
                return this;
            }
        }
    }

    public static class StatusImpl {
        public List<StatusImpl> children = new ArrayList<>();
        public StatusData data = new StatusData();

        private StatusImpl calculate() {
            if (children.isEmpty()) {
                this.data.progress = 0F;
                this.data.status = Status.NO_CUMPLE;
            } else {
                this.data.progress = (float) this.children.stream().mapToDouble(e -> e.data.progress).sum() / (float) this.children.size();
                this.data.status = this.data.progress == 0F ? Status.NO_CUMPLE : this.data.progress == 1F ? Status.SI_CUMPLE : Status.EN_PROGRESO;
            }
            return this;
        }
    }

    private static class StatusData {
        public Integer id;
        public String name;
        public String description;
        public Status status;
        public float progress;
    }
}
