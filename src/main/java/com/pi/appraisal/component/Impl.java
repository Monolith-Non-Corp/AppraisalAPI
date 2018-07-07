package com.pi.appraisal.component;

import com.pi.appraisal.entity.*;
import com.pi.appraisal.entity.AreaProceso.AreaProcesoImpl;
import com.pi.appraisal.entity.Artefacto.ArtefactoImpl;
import com.pi.appraisal.entity.Categoria.CategoriaImpl;
import com.pi.appraisal.entity.Evidencia.EvidenciaImpl;
import com.pi.appraisal.entity.Hipervinculo.HipervinculoImpl;
import com.pi.appraisal.entity.MetaEspecifica.MetaEspecificaImpl;
import com.pi.appraisal.entity.Nivel.NivelImpl;
import com.pi.appraisal.entity.Persona.PersonaImpl;
import com.pi.appraisal.entity.PracticaEspecifica.PracticaEspecificaImpl;
import com.pi.appraisal.entity.Usuario.UsuarioImpl;
import com.pi.appraisal.entity.UsuarioRol.UsuarioRolImpl;

import java.util.stream.Collectors;

public final class Impl {

    private Impl() {
    }

    public static NivelImpl from(Nivel nivel) {
        NivelImpl impl = new NivelImpl();
        impl.lvl = nivel.getLvl();
        impl.descripcion = nivel.getDescripcion();
        return impl;
    }

    public static AreaProcesoImpl from(AreaProceso areaProceso) {
        AreaProcesoImpl impl = new AreaProcesoImpl();
        impl.id = areaProceso.getId();
        impl.nombre = areaProceso.getNombre();
        impl.descripcion = areaProceso.getDescripcion();
        impl.categoria = Impl.from(areaProceso.getCategoria());
        return impl;
    }

    public static CategoriaImpl from(Categoria categoria) {
        CategoriaImpl impl = new CategoriaImpl();
        impl.id = categoria.getId();
        impl.nombre = categoria.getNombre();
        return impl;
    }

    public static MetaEspecificaImpl from(MetaEspecifica metaEspecifica) {
        MetaEspecificaImpl impl = new MetaEspecificaImpl();
        impl.id = metaEspecifica.getId();
        impl.nombre = metaEspecifica.getNombre();
        impl.descripcion = metaEspecifica.getDescripcion();
        return impl;
    }

    public static PracticaEspecificaImpl from(PracticaEspecifica practicaEspecifica) {
        PracticaEspecificaImpl impl = new PracticaEspecificaImpl();
        impl.id = practicaEspecifica.getId();
        impl.nombre = practicaEspecifica.getNombre();
        impl.descripcion = practicaEspecifica.getDescripcion();
        return impl;
    }

    public static ArtefactoImpl from(Artefacto artefacto) {
        ArtefactoImpl impl = new ArtefactoImpl();
        impl.id = artefacto.getId();
        impl.nombre = artefacto.getNombre();
        impl.tipo = artefacto.getTipo();
        impl.fecha = artefacto.getFecha();
        return impl;
    }

    public static HipervinculoImpl from(Hipervinculo hipervinculo) {
        HipervinculoImpl impl = new HipervinculoImpl();
        impl.id = hipervinculo.getId();
        impl.link = hipervinculo.getLink();
        impl.fecha = hipervinculo.getFecha();
        return impl;
    }

    public static EvidenciaImpl from(Evidencia evidencia) {
        EvidenciaImpl impl = new EvidenciaImpl();
        impl.id = evidencia.getId();
        impl.artefactos = evidencia.getArtefactos().stream().map(Impl::from).collect(Collectors.toSet());
        impl.hipervinculos = evidencia.getHipervinculos().stream().map(Impl::from).collect(Collectors.toSet());
        return impl;
    }

    public static UsuarioRolImpl from(UsuarioRol usuarioRol) {
        UsuarioRolImpl impl = new UsuarioRolImpl();
        impl.id = usuarioRol.getId();
        impl.descripcion = usuarioRol.getDescripcion();
        return impl;
    }

    public static PersonaImpl from (Persona persona) {
        PersonaImpl impl = new PersonaImpl();
        impl.id = persona.getId();
        impl.nombre = persona.getNombre();
        impl.primerApellido = persona.getPrimerApellido();
        impl.segundoApellido = persona.getSegundoApellido();
        return impl;
    }

    public static UsuarioImpl from(Usuario usuario) {
        UsuarioImpl impl = new UsuarioImpl();
        impl.id = usuario.getId();
        impl.username = usuario.getUsername();
        impl.usuarioRol = Impl.from(usuario.getUsuarioRol());
        impl.persona = Impl.from(usuario.getPersona());
        impl.key = usuario.key;
        impl.token = usuario.token;
        return impl;
    }
}
