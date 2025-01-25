# Pokémon App

## Índice
- [Introducción](#introducción)
- [Características Principales](#características-principales)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instrucciones de Uso](#instrucciones-de-uso)
- [Conclusiones del Desarrollador](#conclusiones-del-desarrollador)
- [Capturas de Pantalla](#capturas-de-pantalla)

---

## Introducción
Creacion de aPlicaion para tarea de PMDM de ciclo DAM, para la practica de gerstion de datos.
Esta aplicación está diseñada para gestionar Pokémon capturados y explorar una Pokédex utilizando datos obtenidos de la API de Pokémon. Es un proyecto evaluable en el contexto de PMDM (Programación Multimedia y Dispositivos Móviles) y busca implementar conceptos clave como navegación por pestañas, integración con Firebase y un diseño centrado en la experiencia de usuario.

---

## Características Principales

### 1. **Autenticación**
- Registro e inicio de sesión mediante correo electrónico y contraseña.
- Cierre de sesión redirigiendo al usuario a la pantalla de inicio.
- Autentificacion a traves de Google Sing in.

### 2. **Gestor de Pokémon Capturados**
- Los Pokémon capturados se almacenan en Firebase Firestore.
- Uso de RecyclerView y CardViews para mostrar una lista detallada de los Pokémon capturados.
- Opción para eliminar Pokémon con confirmación. (determinar o habilitar esta acción desde ajustes)

### 3. **Pokédex**
- Consumo de la API de Pokémon (https://pokeapi.co/).
- Visualización de Pokémon en un RecyclerView con CardViews. (como la gestion de Pokémon Capturados)
- Captura de Pokémon desde la Pokédex.
- Los Pokémon capturados cambian su estilo visual en la Pokédex para indicar que ya están capturados marcándose en rojo.

### 4. **Pantalla de Ajustes**
- Cambiar el idioma entre español e inglés.
- Activar o desactivar la opción de eliminar Pokémon capturados.
- Visualizar información "Acerca de" del desarrollador y la versión de la app.

### 5. **Diseño y Estilo**
- Estilo personalizado inspirado en los colores característicos de Pokémon.
- Uso de estilos para botones, textos y CardViews.

---

## Tecnologías Utilizadas

- **Android Studio**: IDE principal para el desarrollo de la aplicación. Version Koala
- **Java**: Lenguaje principal del proyecto.
- **Firebase**:
    - Firestore para almacenar los Pokémon capturados.
    - Authentication para gestionar el registro y login.
- **Retrofit**: Para el consumo de la API de Pokémon.
- **Picasso**: Para cargar las imágenes de los Pokémon desde URLs.
- **Material Design**: Diseño de la interfaz.

---

## Instrucciones de Uso

### Requisitos Previos
1. Tener Android Studio instalado.
2. Clonar este repositorio:
   ```bash
   git clone https://github.com/tu-usuario/pokemon-app.git
   ```
3. Configurar Firebase:
    - Descarga el archivo `google-services.json` de tu proyecto en Firebase.
    - Colócalo en el directorio `app` del proyecto.

### Instalación
1. Abre el proyecto en Android Studio.
2. Sincroniza las dependencias de Gradle.
3. Ejecuta la aplicación en un emulador o dispositivo físico.

### Uso
1. Regístrate o inicia sesión con un correo electrónico y contraseña.
2. Navega entre las pestañas para explorar la Pokédex, Tus pokémons capturados y gestionar tus Pokémon .
3. Elegir tus preferencias en la pestaña de ajustes.

---

## Conclusiones del Desarrollador

Esta actividad me ha permito repetir y asi interiorizar le uso y creacion de conceptas clave de Android, como por ejemplo :


- Navegación mediante Fragments y NavHost. Creacion de pestañas y manejo de fragmentos/activities, y menús.
- Consumo de APIs externas usando Retrofit. En este caso la API de Pokemon.
- Integración de servicios en la nube con Firebase. (autentificacion, coleccione, etc)
- Diseño adaptado al usuario con y estilos personalizados.

**Desafíos:**
- Manejo de cambios de idioma en tiempo real.
- Sincronización correcta entre Firebase y la interfaz.
- Autentificación de usuario mediante Google Sign in, reconociendo claves, keys, y diversos métodos.

**Aprendizajes:**
- Gestión de datos en tiempo real.
- Uso de Apis externas
- Gestion de datos en nube

---

## Capturas de Pantalla


