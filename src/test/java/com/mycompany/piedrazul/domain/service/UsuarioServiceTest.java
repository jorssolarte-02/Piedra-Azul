package com.mycompany.piedrazul.domain.service;

import com.mycompany.piedrazul.domain.model.Persona;
import com.mycompany.piedrazul.domain.model.Rol;
import com.mycompany.piedrazul.domain.model.Usuario;
import com.mycompany.piedrazul.domain.repository.IMedicoRepository;
import com.mycompany.piedrazul.domain.repository.IPacienteRepository;
import com.mycompany.piedrazul.domain.repository.IPersonaRepository;
import com.mycompany.piedrazul.domain.repository.IUsuarioRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IPersonaRepository personaRepository;

    @Mock
    private IPacienteRepository pacienteRepository;

    @Mock
    private IMedicoRepository medicoRepository;

    @InjectMocks
    private UsuarioService usuarioService;
  
   //Prueba1
 @Test
public void autenticar_deberiaRetornarNull_siUsernameEstaVacio() {
    Usuario resultado = usuarioService.autenticar("", "123456");

    assertNull(resultado);
    verifyNoInteractions(usuarioRepository);
}  

//Prueba2
@Test
public void autenticar_deberiaRetornarNull_siPasswordEstaVacio() {
    Usuario resultado = usuarioService.autenticar("carlos1", "");

    assertNull(resultado);
    verifyNoInteractions(usuarioRepository);
}

//Prueba3
@Test
public void autenticar_deberiaRetornarNull_siUsuarioNoExiste() {
    when(usuarioRepository.findByUsername("noexiste")).thenReturn(null);

    Usuario resultado = usuarioService.autenticar("noexiste", "123456");

    assertNull(resultado);
    verify(usuarioRepository).findByUsername("noexiste");
}

//Prueba4
@Test
public void autenticar_deberiaLanzarExcepcion_siUsuarioEstaInactivo() {
    Usuario usuarioInactivo = new Usuario();
    usuarioInactivo.setUsername("inactivo1");
    usuarioInactivo.setActivo(false);

    when(usuarioRepository.findByUsername("inactivo1")).thenReturn(usuarioInactivo);

    IllegalStateException excepcion = assertThrows(
            IllegalStateException.class,
            () -> usuarioService.autenticar("inactivo1", "123456")
    );

    assertEquals("Usuario inactivo", excepcion.getMessage());
    verify(usuarioRepository).findByUsername("inactivo1");
    verify(usuarioRepository, never()).authenticate(anyString(), anyString());
}

//Prueba5
@Test
public void autenticar_deberiaRetornarUsuarioYResetearIntentos_siCredencialesSonCorrectas() {
    Usuario usuarioActivo = new Usuario();
    usuarioActivo.setUsername("carlos1");
    usuarioActivo.setActivo(true);

    Usuario usuarioAutenticado = new Usuario();
    usuarioAutenticado.setUsername("carlos1");
    usuarioAutenticado.setActivo(true);

    when(usuarioRepository.findByUsername("carlos1")).thenReturn(usuarioActivo);
    when(usuarioRepository.authenticate(eq("carlos1"), anyString())).thenReturn(usuarioAutenticado);

    Usuario resultado = usuarioService.autenticar("carlos1", "123456");

    assertNotNull(resultado);
    assertEquals("carlos1", resultado.getUsername());
    verify(usuarioRepository).findByUsername("carlos1");
    verify(usuarioRepository).authenticate(eq("carlos1"), anyString());
    verify(usuarioRepository).resetearIntentosFallidos("carlos1");
    verify(usuarioRepository, never()).registrarIntentoFallido(anyString());
}

//Prueba6
@Test
public void autenticar_deberiaRetornarNullYRegistrarIntento_siPasswordEsIncorrecta() {
    Usuario usuarioActivo = new Usuario();
    usuarioActivo.setUsername("carlos1");
    usuarioActivo.setActivo(true);

    when(usuarioRepository.findByUsername("carlos1")).thenReturn(usuarioActivo);
    when(usuarioRepository.authenticate(eq("carlos1"), anyString())).thenReturn(null);

    Usuario resultado = usuarioService.autenticar("carlos1", "claveIncorrecta");

    assertNull(resultado);
    verify(usuarioRepository).findByUsername("carlos1");
    verify(usuarioRepository).authenticate(eq("carlos1"), anyString());
    verify(usuarioRepository).registrarIntentoFallido("carlos1");
    verify(usuarioRepository, never()).resetearIntentosFallidos(anyString());
}

//Prueba7
@Test
public void registrarUsuario_deberiaLanzarExcepcion_siUsernameYaExiste() {
    when(usuarioRepository.usernameExists("almagozo")).thenReturn(true);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> usuarioService.registrarUsuario(
                    "almagozo",
                    "Clave123",
                    Rol.PACIENTE,
                    "Alma",
                    "Maria",
                    "Gozo",
                    "Lopez",
                    "MUJER",
                    LocalDate.of(2001, 4, 12),
                    "3104567890",
                    "100200300"
            )
    );

    assertEquals("El username ya existe", excepcion.getMessage());
    verify(usuarioRepository).usernameExists("almagozo");
    verify(personaRepository, never()).dniExists(anyString());
    verify(personaRepository, never()).create(any());
    verify(usuarioRepository, never()).create(any());
    verify(pacienteRepository, never()).create(anyInt());
    verify(medicoRepository, never()).create(anyInt(), anyString());
}

//Prueba8
@Test
public void registrarUsuario_deberiaLanzarExcepcion_siDniYaExiste() {
    when(usuarioRepository.usernameExists("almagozo")).thenReturn(false);
    when(personaRepository.dniExists("100200300")).thenReturn(true);

    IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> usuarioService.registrarUsuario(
                    "almagozo",
                    "Clave123",
                    Rol.PACIENTE,
                    "Alma",
                    "Maria",
                    "Gozo",
                    "Lopez",
                    "MUJER",
                    LocalDate.of(2001, 4, 12),
                    "3104567890",
                    "100200300"
            )
    );

    assertEquals("El DNI ya está registrado", excepcion.getMessage());
    verify(usuarioRepository).usernameExists("almagozo");
    verify(personaRepository).dniExists("100200300");
    verify(personaRepository, never()).create(any());
    verify(usuarioRepository, never()).create(any());
    verify(pacienteRepository, never()).create(anyInt());
    verify(medicoRepository, never()).create(anyInt(), anyString());
}

//Prueba9
@Test
public void registrarUsuario_deberiaLanzarExcepcion_siNoSeCreaPersona() {
    when(usuarioRepository.usernameExists("almagozo")).thenReturn(false);
    when(personaRepository.dniExists("100200300")).thenReturn(false);
    when(personaRepository.create(any())).thenReturn(null);

    RuntimeException excepcion = assertThrows(
            RuntimeException.class,
            () -> usuarioService.registrarUsuario(
                    "almagozo",
                    "Clave123",
                    Rol.PACIENTE,
                    "Alma",
                    "Maria",
                    "Gozo",
                    "Lopez",
                    "MUJER",
                    LocalDate.of(2001, 4, 12),
                    "3104567890",
                    "100200300"
            )
    );

    assertEquals("Error al crear persona", excepcion.getMessage());
    verify(personaRepository).create(any());
    verify(usuarioRepository, never()).create(any());
    verify(pacienteRepository, never()).create(anyInt());
    verify(medicoRepository, never()).create(anyInt(), anyString());
}

//Prueba10
@Test
public void registrarUsuario_deberiaCrearPaciente_siRolEsPaciente() {
    Persona personaCreada = new Persona();
    personaCreada.setId(1);

    when(usuarioRepository.usernameExists("almagozo")).thenReturn(false);
    when(personaRepository.dniExists("100200300")).thenReturn(false);
    when(personaRepository.create(any())).thenReturn(personaCreada);
    when(usuarioRepository.create(any())).thenReturn(true);
    when(pacienteRepository.create(1)).thenReturn(true);

    boolean resultado = usuarioService.registrarUsuario(
            "almagozo",
            "Clave123",
            Rol.PACIENTE,
            "Alma",
            "Maria",
            "Gozo",
            "Lopez",
            "MUJER",
            LocalDate.of(2001, 4, 12),
            "3104567890",
            "100200300"
    );

    assertTrue(resultado);
    verify(usuarioRepository).usernameExists("almagozo");
    verify(personaRepository).dniExists("100200300");
    verify(personaRepository).create(any());
    verify(usuarioRepository).create(any());
    verify(pacienteRepository).create(1);
    verify(medicoRepository, never()).create(anyInt(), anyString());
}
//Prueba11
@Test
public void registrarUsuario_deberiaCrearMedico_siRolEsMedicoTerapista() {
    Persona personaCreada = new Persona();
    personaCreada.setId(2);

    when(usuarioRepository.usernameExists("luciareyes")).thenReturn(false);
    when(personaRepository.dniExists("200300400")).thenReturn(false);
    when(personaRepository.create(any())).thenReturn(personaCreada);
    when(usuarioRepository.create(any())).thenReturn(true);
    when(medicoRepository.create(2, "MEDICO")).thenReturn(true);

    boolean resultado = usuarioService.registrarUsuario(
            "luciareyes",
            "Segura456",
            Rol.MEDICO_TERAPISTA,
            "Lucia",
            "Fernanda",
            "Reyes",
            "Molina",
            "MUJER",
            LocalDate.of(1994, 9, 3),
            "3119876543",
            "200300400"
    );

    assertTrue(resultado);
    verify(usuarioRepository).usernameExists("luciareyes");
    verify(personaRepository).dniExists("200300400");
    verify(personaRepository).create(any());
    verify(usuarioRepository).create(any());
    verify(medicoRepository).create(2, "MEDICO");
    verify(pacienteRepository, never()).create(anyInt());
}


//Prueba12
@Test
public void registrarUsuario_deberiaCrearSoloUsuario_siRolEsAdministrador() {
    Persona personaCreada = new Persona();
    personaCreada.setId(3);

    when(usuarioRepository.usernameExists("valentinapaz")).thenReturn(false);
    when(personaRepository.dniExists("300400500")).thenReturn(false);
    when(personaRepository.create(any())).thenReturn(personaCreada);
    when(usuarioRepository.create(any())).thenReturn(true);

    boolean resultado = usuarioService.registrarUsuario(
            "valentinapaz",
            "Admin789",
            Rol.ADMINISTRADOR,
            "Valentina",
            "Paz",
            "Ruiz",
            "Castro",
            "MUJER",
            LocalDate.of(1998, 11, 21),
            "3121239876",
            "300400500"
    );

    assertTrue(resultado);
    verify(usuarioRepository).usernameExists("valentinapaz");
    verify(personaRepository).dniExists("300400500");
    verify(personaRepository).create(any());
    verify(usuarioRepository).create(any());
    verify(pacienteRepository, never()).create(anyInt());
    verify(medicoRepository, never()).create(anyInt(), anyString());
}

}