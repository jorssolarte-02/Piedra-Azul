🏥Proyecto: Sistema de Citas Médicas – Piedrazul
📖 Introducción
Este trabajo corresponde al desarrollo de un sistema de software para la gestión de citas médicas en la red de salud Piedrazul, dentro del curso Ingeniería de Software II (2026.1).
La iniciativa surge como reemplazo de una aplicación de escritorio previa, limitada en escalabilidad y usabilidad, con el propósito de optimizar procesos clave como:
- Registro y autenticación de usuarios
- Administración de roles
- Programación de citas médicas
- Organización del personal asistencial

🎯 Objetivos
El sistema busca:
- Automatizar la asignación de citas.
- Disminuir la carga operativa del personal médico.
- Mejorar la experiencia de los pacientes y usuarios internos.
- Garantizar un control de acceso seguro y diferenciado por roles.

🏗️ Avances actuales
En la primera iteración se definió una arquitectura monolítica en capas (MVC), que permite separar responsabilidades y preparar el terreno para futuras refactorizaciones hacia microservicios.
Funcionalidades iniciales
- 🔐 Autenticación de usuarios
- Validación de credenciales y campos obligatorios.
- Redirección según rol: Administrador, Médico/Terapista, Agendador, Paciente.
- ⚠️ Validaciones básicas
- Control de campos vacíos en login.
- Notificación de credenciales incorrectas.
Organización por capas
- Presentación (UI): Interfaz gráfica desarrollada en Swing/JavaFX.
- Aplicación/Servicio: Lógica de negocio y coordinación de casos de uso.
- Dominio: Modelado de entidades principales (Usuario, Rol, Cita).
- Persistencia: En construcción, orientada al acceso a datos con PostgreSQL.

🛠️ Tecnologías utilizadas
- Lenguaje: Java
- Interfaz gráfica: Swing / JavaFX
- Base de datos: PostgreSQL
- Control de versiones: Git + GitHub

⚙️ Ejecución del sistema
- Clonar el repositorio:
git clone <URL_DEL_REPOSITORIO>
- Abrir el proyecto en NetBeans.
- Ejecutar la clase principal para iniciar la aplicación.

🚧 Próximos pasos
- Mejorar el flujo de autenticación.
- Completar la gestión de usuarios.
- Implementar el agendamiento de citas.
- Incorporar validaciones de negocio más avanzadas.

📚 Contexto académico
Proyecto desarrollado en la Universidad del Cauca, curso Ingeniería de Software II, periodo 2026.1.

👥 Equipo de trabajo
- Jors Eduar Solarte
- Julian David Narváez
- Dario Alexander Cárdenas
