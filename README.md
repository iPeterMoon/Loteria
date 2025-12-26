# 🎰 Lotería - Sistema Distribuido de Juego

Proyecto de arquitectura de software distribuido que implementa un sistema de juego de lotería utilizando una arquitectura en capas con múltiples servicios independientes.

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Módulos Principales](#módulos-principales)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Estructura de Directorios](#estructura-de-directorios)
- [Contribuyentes](#contribuyentes)

## 📌 Descripción General

Este proyecto es una aplicación distribuida de lotería que utiliza:
- **Arquitectura P2P (Peer-to-Peer)** para comunicación entre nodos
- **Discovery Service** para localización de servicios
- **MatchMaker Service** para emparejar jugadores en partidas
- **UI JavaFX** para la interfaz gráfica de configuración y juego
- **Comunicación basada en eventos** con procesamiento asíncrono

El sistema está diseñado para ser escalable, con múltiples peers que pueden unirse y participar en partidas de lotería de manera coordinada.

## 🏗️ Arquitectura del Proyecto

La arquitectura se basa en una estructura modular con separación de responsabilidades:

```
┌─────────────────────────────────────────────────────┐
│           Presentación (UI - JavaFX)                │
│  ├─ Presentacion_Config  (Configuración)            │
│  └─ Presentacion_Juego   (Interfaz de Juego)        │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│         Lógica de Negocio (Modelo)                  │
│       Modelo_Juego - Reglas y Managers              │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│    Componentes Distribuidos                         │
│  ├─ Peer (Nodo de Juego)                            │
│  ├─ Discovery (Localización de Servicios)           │
│  ├─ MatchMaker (Emparejamiento)                     │
│  └─ Red (Comunicación de Bajo Nivel)                │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│    Mediador (DTOs e Interfaces)                      │
│        Comunicación entre módulos                   │
└─────────────────────────────────────────────────────┘
```

## 📦 Módulos Principales

### 1. **Loteria** (Aplicación Cliente Principal)
**Ubicación**: `/Loteria`
**Tipo**: Aplicación de escritorio (JavaFX)

Módulos internos:
- **Arrancador**: Punto de entrada principal de la aplicación
  - Configura el modelo del juego
  - Inicializa controles y UI
  - Coordina componentes
  
- **Presentacion_Config**: UI de Configuración
  - Pantalla de configuración inicial
  - Selección de avatares y parámetros
  - Audios, recursos visuales
  
- **Presentacion_Juego**: UI Principal del Juego
  - Interfaz de juego en tiempo real
  - Gestión de eventos visuales
  - Control de entrada del usuario
  
- **Modelo_Juego**: Lógica de negocio
  - Managers: Gestión de estado del juego
  - Mappers: Conversión de datos
  - Repositorios: Acceso a datos persistentes
  - Procesadores: Lógica de eventos del modelo

### 2. **Peer** (Nodo Distribuido)
**Ubicación**: `/Peer`
**Tipo**: Biblioteca JAR

Responsabilidades:
- Representa un nodo individual en la red P2P
- Gestión de conexiones con otros peers
- Procesamiento de mensajes entrantes/salientes
- Manejo de heartbeat para detección de disponibilidad
- Pool de hilos para procesamiento asíncrono

**Componentes principales**:
- `Peer.java`: Clase principal (singleton)
- `PeerFacade.java`: Interfaz de operaciones
- `PeersConectados.java`: Registro de peers activos
- `Heartbeat.java`: Latido del sistema (health check)
- `procesadores_peer/`: Procesadores de eventos
- `network/`: Componentes de red específicos de peers
- `utilPeer/`: Utilidades (pool de hilos, mapeos)

### 3. **Discovery** (Servicio de Localización)
**Ubicación**: `/Discovery`
**Tipo**: Servicio distribuido

Módulos internos:
- **ArrancadorDiscovery**: Punto de entrada del servicio Discovery
- **DiscoveryImpl**: Implementación del servicio
  - Registro de servicios disponibles
  - Búsqueda de peers por características
  - Gestión de metadatos de nodos
  - Procesadores específicos del discovery

**Dependencias**: Red, Mediador

### 4. **MatchMaker** (Servicio de Emparejamiento)
**Ubicación**: `/MatchMaker`
**Tipo**: Servicio distribuido

Responsabilidades:
- Emparejar jugadores en partidas
- Gestión de salas de juego
- Configuración de partidas
- Balanceo de carga entre salas

Módulos internos:
- **ArrancadorMatchMaker**: Punto de entrada
- **MatchMakerImpl**: Implementación
  - `Matchmaker.java`: Lógica de emparejamiento
  - `Sala.java`: Representación de sala de juego
  - `ConfiguracionJuego.java`: Configuración de partidas

**Dependencias**: Peer, Mediador, Red

### 5. **Red** (Comunicación de Bajo Nivel)
**Ubicación**: `/Red`
**Tipo**: Biblioteca JAR

Responsabilidades:
- Comunicación de red (TCP/IP)
- Gestión de conexiones cliente-servidor
- DTOs de red para serialización
- Patrones factory para creación de conexiones

**Componentes principales**:
- `servidor/`: Componentes de servidor
- `cliente/`: Componentes de cliente
- `dtos/`: Data Transfer Objects
- `interfaces/`: Contratos de red
- `factory/`: Patrones de creación

### 6. **Mediador** (Clases compartidas entre proyectos)
**Ubicación**: `/Mediador`
**Tipo**: Biblioteca JAR

Responsabilidades:
- Bus central de eventos
- Comunicación entre módulos
- DTOs compartidos entre servicios
- Enums de tipos de eventos

**Componentes principales**:
- `eventos/`: Definición de eventos
- `dtos/`: Data Transfer Objects compartidos
- `interfaces/`: Contratos de mediación
- `util/`: Patrón Observer y utilidades

## 🔧 Requisitos

### Requisitos Mínimos
- **Java**: JDK 21 o superior
- **Maven**: 3.6.0 o superior
- **Sistema Operativo**: Windows, Linux, macOS
- **Memoria RAM**: [ESPECIFICAR - mínimo recomendado]
- **Almacenamiento**: [ESPECIFICAR - espacio requerido]

### Dependencias Principales
- **GSON**: 2.10.1 (Serialización JSON)
- **JavaFX**: [ESPECIFICAR versión si se usa]
- **Otros**: [COMPLETAR DEPENDENCIAS]

## 📥 Instalación

### 1. Clonar el repositorio
```bash
git clone <URL_REPOSITORIO>
cd Loteria
```

### 2. Compilar el proyecto
```bash
# Compilar todos los módulos
mvn clean install

# O compilar módulo específico
cd Loteria
mvn clean install
```

### 3. Construir artefactos ejecutables
```bash
# Crear JAR con dependencias
mvn package

# Crear ejecutable (si aplica)
mvn assembly:assembly
```

## ⚙️ Configuración

### Archivo de Configuración Principal
**Ubicación**: `Mediador/src/main/resources/configuracion.json`

```json
{
    "ip_servidor": "", //Poner aquí la ip del servidor.
    "puerto_discovery": 12345,
    "puerto_matchmaker": 12346,
    "usuario_matchmaker": "MATCHMAKER"
}
```

### Variables de Configuración
| Variable | Descripción | Valor por Defecto |
|----------|-------------|--------------------|
| `ip_servidor` | IP del servidor central | 10.176.5.135 |
| `puerto_discovery` | Puerto del servicio Discovery | 12345 |
| `puerto_matchmaker` | Puerto del servicio MatchMaker | 12346 |
| `usuario_matchmaker` | Usuario del servicio MatchMaker | MATCHMAKER |

### Configuración por Ambiente
- **Desarrollo**: [ESPECIFICAR configuración de desarrollo]
- **Staging**: [ESPECIFICAR configuración de staging]
- **Producción**: [ESPECIFICAR configuración de producción]

## 🚀 Uso

### Iniciar el Servicio Discovery
```bash
cd Discovery/ArrancadorDiscovery
mvn exec:java -Dexec.mainClass="main.ArrancadorDiscovery"
```

### Iniciar el Servicio MatchMaker
```bash
cd MatchMaker/ArrancadorMatchMaker
mvn exec:java -Dexec.mainClass="org.itson.arrancadormatchmaker.ArrancadorMatchMaker"
```

### Iniciar la Aplicación Cliente
```bash
cd Loteria/Arrancador
mvn exec:java -Dexec.mainClass="main.Arrancador"
```

## 🔄 Flujo de Funcionamiento Principal

1. **Inicio de Aplicación**: El `Arrancador` inicializa UI y modelo
2. **Descubrimiento**: El cliente se registra en el servicio Discovery
3. **Emparejamiento**: El MatchMaker busca otros jugadores disponibles
4. **Conexión P2P**: Se establece comunicación directa entre peers
5. **Ejecución del Juego**: Los peers juegan en sincronía mediante eventos
6. **Finalización**: Actualización de estado y desconexión ordenada

## 🛠️ Compilación y Construcción

### Build Completo
```bash
mvn clean install -DskipTests
```

### Build con Tests
```bash
mvn clean install
```

### Build Específico de Módulo
```bash
mvn -pl :Peer clean install
```

## 📝 Convenciones de Código

- **Lenguaje**: Java 21+
- **Codificación**: UTF-8
- **Convención de nombres**: 
  - Clases: PascalCase
  - Variables/métodos: camelCase
  - Constantes: UPPER_SNAKE_CASE
- **Documentación**: Javadoc para clases y métodos públicos

## 🤝 Contribuyentes

- **Autor Principal**: Pedro Luna
- **Contribuyentes**:  

---

**Última actualización**: Diciembre 2025
**Versión del Proyecto**: 1.0-SNAPSHOT
