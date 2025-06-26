package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Usuario;
import cl.perfulandia.catalogo.service.UsuarioService;
import cl.perfulandia.catalogo.assemblers.UsuarioModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Usuario V2", description = "Operaciones de usuarios con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;

    public UsuarioControllerV2(UsuarioService usuarioService, UsuarioModelAssembler assembler) {
        this.usuarioService = usuarioService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return assembler.toModel(usuario);
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una colección de usuarios con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getTodosUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.obtenerTodosUsuarios().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(usuarios,
            linkTo(methodOn(UsuarioControllerV2.class).getTodosUsuarios()).withSelfRel());
    }

    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario con validación de datos")
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(
            @Validated(UsuarioDTO.CreateValidation.class) @RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioService.crearUsuario(dto);
        EntityModel<Usuario> model = EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getTodosUsuarios()).withRel("usuarios")
        );
        return ResponseEntity.created(
            linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente con validación de datos")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(
            @PathVariable Long id,
            @Validated(UsuarioDTO.UpdateValidation.class) @RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioService.actualizarUsuario(id, dto);
        EntityModel<Usuario> model = EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuario(id)).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getTodosUsuarios()).withRel("usuarios")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
