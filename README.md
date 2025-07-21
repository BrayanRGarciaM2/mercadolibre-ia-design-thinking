# Experimento Design Thinking - Prototipo Android

## 📋 Descripción

Este es un **prototipo estudiantil** desarrollado para un experimento de Design Thinking que simula una experiencia de compra en Mercado Libre con funcionalidades de inteligencia artificial integradas. El proyecto forma parte del curso de Design Thinking en la Universidad de los Andes.

## Prototipo de marketing

https://youtu.be/6FCO0dPt0Ko?si=CbYq30vho31pAmvS

## Evidencia de ejecución de prototipo

https://github.com/user-attachments/assets/0216af4b-8b05-441b-ba40-8913adc523c1



## 🚀 Cómo Probar el Prototipo

### Requisitos Previos
- **Android Studio** (versión recomendada: Hedgehog | 2023.1.1 o superior)
- **Android SDK API 34** o superior
- **Dispositivo Android** o **Emulador** (API 24 o superior)

### Pasos para Ejecutar el Prototipo

#### 1. Descargar y Abrir el Proyecto
```bash
# Clona el repositorio
git clone https://github.com/tu-usuario/experimento-design-thinking.git

# Abre Android Studio
# File → Open → Selecciona la carpeta del proyecto
```

#### 2. Configurar el Entorno
1. **Sincronizar Gradle**: Android Studio automáticamente sincronizará las dependencias
2. **Configurar Emulador** (si no tienes dispositivo físico):
   - Tools → AVD Manager → Create Virtual Device
   - Selecciona un dispositivo (ej: Pixel 4)
   - Descarga una imagen de sistema (API 34 recomendada)

#### 3. Ejecutar la Aplicación
- Presiona el botón **▶️ Run** (triángulo verde) en Android Studio
- Selecciona tu dispositivo/emulador
- La aplicación se instalará y abrirá automáticamente

## 🧪 Guía de Pruebas del Prototipo

### Escenario 1: Búsqueda Básica
1. **Abre la aplicación** - Verás la pantalla principal con diseño de Mercado Libre
2. **Escribe en la barra de búsqueda**: "termo"
3. **Presiona el ícono de búsqueda** o Enter
4. **Observa los resultados**: Aparecerá un grid de productos con un producto destacado con borde de colores

### Escenario 2: Asistente de IA
1. **En la pantalla de resultados**, busca el **botón flotante "Buscar con IA"**
2. **Arrastra el botón** - Es completamente arrastrable por la pantalla
3. **Toca el botón** - Se abrirá un chat de IA
4. **Escribe en el chat**: "Necesito un termo para deportes"
5. **Presiona "Buscar"** - Verás productos filtrados por la IA

### Escenario 3: Producto Recomendado por IA
1. **Busca el producto con borde de colores** (cyan, azul, púrpura)
2. **Toca el producto** - Se abrirá la pantalla de detalle
3. **Observa los indicadores**: "MÁS VENDIDO", "1° en Termos"
4. **Selecciona una talla** (S, M, L, XL)
5. **Presiona "Comprar ahora"** - Aparecerá un error simulado (parte del prototipo)

### Escenario 4: Búsqueda Específica
1. **Escribe en la barra de búsqueda**: "plastico"
2. **Observa cómo cambian los resultados** - La IA prioriza productos específicos
3. **Usa el botón de IA** y escribe: "termo de plástico"
4. **Compara los resultados** entre búsqueda normal y búsqueda con IA

## 🔧 Configuración del Experimento

### Variable de Control de IA
En el archivo `MainActivity.kt` (línea 89), puedes modificar:

```kotlin
var aiPriceControlEnabled = true
```

- **`true`**: La IA NO sugiere versiones más baratas del mismo producto
- **`false`**: La IA sugiere productos sin restricción de precio

### Datos Mockeados
Los productos son simulados y se encuentran en `MainActivity.kt` (líneas 95-200). Puedes modificar:
- Precios de productos
- Imágenes de productos
- Categorías y etiquetas

## 📊 Elementos a Observar Durante las Pruebas

### Comportamiento del Usuario
- ¿Qué tan rápido encuentra el botón de IA?
- ¿Usa el botón flotante o la búsqueda tradicional?
- ¿Selecciona productos recomendados por IA?
- ¿Reacciona a los indicadores de "MÁS VENDIDO"?

### Funcionalidades del Prototipo
- **Navegación fluida** entre pantallas
- **Botón flotante arrastrable** con efecto de pegado a bordes
- **Chat de IA** con interfaz intuitiva
- **Productos destacados** con bordes especiales
- **Carga infinita** de productos
- **Manejo de errores** simulado

## 🐛 Problemas Conocidos (Prototipo)

- **Error de compra**: El botón "Comprar ahora" siempre muestra un error (comportamiento intencional)
- **Imágenes externas**: Algunas imágenes pueden tardar en cargar
- **Datos simulados**: Todos los productos y precios son ficticios

## 📱 Compatibilidad

- **Versión mínima de Android**: API 24 (Android 7.0)
- **Versión recomendada**: API 34 (Android 14)
- **Dispositivos probados**: Pixel 4, Samsung Galaxy S21, Emulador Android

## 🎓 Contexto Académico

**Universidad**: Universidad de los Andes  
**Curso**: Design Thinking
**Semestre**: 2025  

## 📄 Licencia

Este prototipo es parte de un proyecto académico. Ver archivo `LICENSE` para más detalles.

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/g18/experimentodesignthinking/
│   │   ├── MainActivity.kt          # Actividad principal
│   │   └── ui/theme/               # Temas y estilos
│   ├── res/                        # Recursos de la aplicación
│   └── assets/                     # Imágenes y assets
├── build.gradle.kts               # Configuración de build
└── AndroidManifest.xml            # Manifesto de la aplicación
```

## Funcionalidades Principales

### 1. Pantalla Principal
- Barra de búsqueda estilo Mercado Libre
- Imagen de fondo promocional
- Navegación a resultados de búsqueda

### 2. Pantalla de Resultados
- Grid de productos con diseño responsive
- Botón flotante de IA arrastrable
- Chat de IA para búsquedas inteligentes
- Carga infinita de productos

### 3. Pantalla de Detalle
- Información completa del producto
- Selector de tallas
- Botón de compra con manejo de errores
- Indicadores de popularidad y calificaciones

### 4. Sistema de IA
- Recomendaciones inteligentes de productos
- Control de precios configurable
- Filtrado por categorías y búsquedas específicas

## Configuración de IA

El proyecto incluye una variable de configuración para controlar el comportamiento de la IA:

```kotlin
var aiPriceControlEnabled = true
```

- `true`: La IA no sugiere versiones más baratas del mismo producto
- `false`: La IA sugiere productos sin restricción de precio

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
