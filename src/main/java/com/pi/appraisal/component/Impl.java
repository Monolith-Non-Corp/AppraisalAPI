package com.pi.appraisal.component;

import com.pi.appraisal.entity.*;
import com.pi.appraisal.entity.AreaProceso.AreaProcesoImpl;
import com.pi.appraisal.entity.Artefacto.ArtefactoImpl;
import com.pi.appraisal.entity.Categoria.CategoriaImpl;
import com.pi.appraisal.entity.Evidencia.EvidenciaImpl;
import com.pi.appraisal.entity.Hipervinculo.HipervinculoImpl;
import com.pi.appraisal.entity.Instancia.InstanciaImpl;
import com.pi.appraisal.entity.InstanciaTipo.InstanciaTipoImpl;
import com.pi.appraisal.entity.MetaEspecifica.MetaEspecificaImpl;
import com.pi.appraisal.entity.Nivel.NivelImpl;
import com.pi.appraisal.entity.Organizacion.OrganizacionImpl;
import com.pi.appraisal.entity.Persona.PersonaImpl;
import com.pi.appraisal.entity.PracticaEspecifica.PracticaEspecificaImpl;
import com.pi.appraisal.entity.Usuario.SessionImpl;
import com.pi.appraisal.entity.Usuario.UsuarioImpl;
import com.pi.appraisal.entity.UsuarioRol.UsuarioRolImpl;

import java.util.HashSet;
import java.util.stream.Collectors;

public final class Impl {

    private Impl() {
    }

    public static NivelImpl to(Nivel nivel) {
        NivelImpl impl = new NivelImpl();
        impl.lvl = nivel.getLvl();
        impl.descripcion = nivel.getDescripcion();
        return impl;
    }

    public static AreaProcesoImpl to(AreaProceso areaProceso) {
        AreaProcesoImpl impl = new AreaProcesoImpl();
        impl.id = areaProceso.getId();
        impl.nombre = areaProceso.getNombre();
        impl.descripcion = areaProceso.getDescripcion();
        impl.categoria = Impl.to(areaProceso.getCategoria());
        return impl;
    }

    public static CategoriaImpl to(Categoria categoria) {
        CategoriaImpl impl = new CategoriaImpl();
        impl.id = categoria.getId();
        impl.nombre = categoria.getNombre();
        return impl;
    }

    public static MetaEspecificaImpl to(MetaEspecifica metaEspecifica) {
        MetaEspecificaImpl impl = new MetaEspecificaImpl();
        impl.id = metaEspecifica.getId();
        impl.nombre = metaEspecifica.getNombre();
        impl.descripcion = metaEspecifica.getDescripcion();
        return impl;
    }

    public static PracticaEspecificaImpl to(PracticaEspecifica practicaEspecifica) {
        PracticaEspecificaImpl impl = new PracticaEspecificaImpl();
        impl.id = practicaEspecifica.getId();
        impl.nombre = practicaEspecifica.getNombre();
        impl.descripcion = practicaEspecifica.getDescripcion();
        return impl;
    }

    public static ArtefactoImpl to(Artefacto artefacto) {
        ArtefactoImpl impl = new ArtefactoImpl();
        impl.id = artefacto.getId();
        impl.nombre = artefacto.getNombre();
        impl.tipo = artefacto.getTipo();
        impl.fecha = artefacto.getFecha();
        return impl;
    }

    public static HipervinculoImpl to(Hipervinculo hipervinculo) {
        HipervinculoImpl impl = new HipervinculoImpl();
        impl.id = hipervinculo.getId();
        impl.link = hipervinculo.getLink();
        impl.fecha = hipervinculo.getFecha();
        return impl;
    }

    public static EvidenciaImpl to(Evidencia evidencia) {
        EvidenciaImpl impl = new EvidenciaImpl();
        impl.id = evidencia.getId();
        impl.artefactos = evidencia.getArtefactos().stream().map(Impl::to).collect(Collectors.toSet());
        impl.hipervinculos = evidencia.getHipervinculos().stream().map(Impl::to).collect(Collectors.toSet());
        return impl;
    }

    public static OrganizacionImpl to(Organizacion organizacion) {
        OrganizacionImpl impl = new OrganizacionImpl();
        impl.id = organizacion.getId();
        impl.nombre = organizacion.getNombre();
        impl.nivel = Impl.to(organizacion.getNivel());
        return impl;
    }

    public static UsuarioRolImpl to(UsuarioRol usuarioRol) {
        UsuarioRolImpl impl = new UsuarioRolImpl();
        impl.id = usuarioRol.getId();
        impl.descripcion = usuarioRol.getDescripcion();
        return impl;
    }

    public static PersonaImpl to(Persona persona) {
        PersonaImpl impl = new PersonaImpl();
        impl.id = persona.getId();
        impl.nombre = persona.getNombre();
        impl.primerApellido = persona.getPrimerApellido();
        impl.segundoApellido = persona.getSegundoApellido();
        return impl;
    }

    public static UsuarioImpl to(Usuario usuario) {
        UsuarioImpl impl = new UsuarioImpl();
        impl.id = usuario.getId();
        impl.username = usuario.getUsername();
        impl.password = usuario.getPassword();
        impl.persona = Impl.to(usuario.getPersona());
        impl.organizacion = Impl.to(usuario.getOrganizacion());
        return impl;
    }

    public static InstanciaTipoImpl to(InstanciaTipo instanciaTipo) {
        InstanciaTipoImpl impl = new InstanciaTipoImpl();
        impl.id = instanciaTipo.getId();
        impl.descripcion = instanciaTipo.getDescripcion();
        return impl;
    }

    public static InstanciaImpl to(Instancia instancia) {
        InstanciaImpl impl = new InstanciaImpl();
        impl.id = instancia.getId();
        impl.nombre = instancia.getNombre();
        impl.instanciaTipo = Impl.to(instancia.getInstanciaTipo());
        impl.areaProcesos = new HashSet<>();
        instancia.getEvidencias().forEach(evidencia -> {
            impl.areaProcesos.add(Impl.to(evidencia.getPracticaEspecifica().getMetaEspecifica().getAreaProceso()));
        });
        return impl;
    }

    public static SessionImpl sessionOf(Usuario usuario) {
        SessionImpl impl = new SessionImpl();
        impl.username = usuario.getUsername();
        impl.usuarioRol = SessionImpl.to(usuario.getUsuarioRol());
        impl.persona = SessionImpl.to(usuario.getPersona());
        if (usuario.getOrganizacion() != null)
            impl.organizacion = Impl.to(usuario.getOrganizacion());
        impl.key = usuario.key;
        impl.token = usuario.token;
        return impl;
    }

    public static Usuario from(UsuarioImpl impl) {
        Usuario usuario = new Usuario();
        usuario.setId(impl.id);
        usuario.setUsername(impl.username);
        usuario.setPassword(impl.password);
        usuario.setPersona(Impl.from(impl.persona));
        usuario.setOrganizacion(Impl.from(impl.organizacion));
        return usuario;
    }

    public static Persona from(PersonaImpl impl) {
        Persona persona = new Persona();
        persona.setId(impl.id);
        persona.setNombre(impl.nombre);
        persona.setPrimerApellido(impl.primerApellido);
        persona.setSegundoApellido(impl.segundoApellido);
        return persona;
    }

    public static Organizacion from(OrganizacionImpl impl) {
        Organizacion organizacion = new Organizacion();
        organizacion.setId(impl.id);
        organizacion.setNombre(impl.nombre);
        organizacion.setNivel(from(impl.nivel));
        return organizacion;
    }

    public static Nivel from(NivelImpl impl) {
        Nivel nivel = new Nivel();
        nivel.setLvl(impl.lvl);
        nivel.setDescripcion(impl.descripcion);
        return nivel;
    }

    public static Instancia from(InstanciaImpl impl) {
        Instancia instancia = new Instancia();
        instancia.setId(impl.id);
        instancia.setNombre(impl.nombre);
        return instancia;
    }
}
