Integrantes del grupo: 
Erick Arevalo 22551007
Leonardo Romero 22611318
Omar Zepeda - 22551025
Carlos Corrales - 22551014

Reglas de validacion adoptadas: 
Estas reglas se implenentan en UtilTexto y se aplican tanto a letras como palabras. 

Mayusculas y minusculas - Todo se normaliza a mayusculas de manera tal que "a" y "A" sean reconocidas como la misma letra.
Tildes - Estas son ignoradas completamente de manera tal que "É" y "E" sean tomadas por igual.
Ñ - Es una letra distinta de la n y es tratada como tal. 
ALfabeto valido - Todas las letras desde la A-Z incluyendo la Ñ.
Letra - tiene que ir una sola letra, caso contrario es invalido, si se incluyen espacios se ignoran y campos vacios no son valitos.
Palabra secreta o nueva palabra - Entre 3 y 15 letras, sin espacios, números ni símbolos. Los espacios al inicio o al final se ignoran. 
 Palabras duplicadas - Se comparan ya normalizadas: "ÁRBOL" y "arbol" son la misma palabra. 
 Letra repetida - No resta intentos. 
 Intentos - Máximo 6. Quedarse sin intentos no es una excepción: es el estado "Perdida" de la partida. 

 Mensajes: 
 Letra inválida - «Ingrese una única letra (A–Z o Ñ).» 
 Letra repetida - «La letra X ya fue ingresada. Pruebe con otra.» 
 Partida terminada - «La partida ya terminó. Presione «Jugar de nuevo».» 
 Palabra inválida - «La palabra debe tener entre 3 y 15 letras, sin espacios ni números.» 
 Palabra duplicada - «La palabra «X» ya existe en la lista.» 
 Victoria (estado, no error) - «¡Ganaste! Completaste la palabra correctamente.» 
 Derrota (estado, no error) - «Perdiste. Se agotaron los intentos. Palabra: X» 

Justificacion uso de serializable
Serializable es una interfaz sin metodo que no obliga a implementar nada si no que marca las clases, para que java sepa que sus objetos
se pueden convertir en una serializacion y reconstruirse despues, si una clase no la declara y se intenta resializar uno de sus objetos
aparace el mensaje NotSerializableException

Se prohiben los archivos binarios por eso no se serializan como tal, aun asi las clases modelo declaran serializable porque representan
el estado de una partida (palabra,letra,intentos,etapa y estado). Con esa declaracion el modelo queda preparado para guardar y 
recuperar partidas en un futuro, obligando a que todo el estado este formado solo por tipos serializables.

serialVersionUID` explícito, Es el numero de version de la clase, al no escribirse el compilador calcula unoa segun la esructura de la clase
y cualquier cambio adicionar impediria leer objetos guardados con la version anterior. Fijandolo con !L controlamos cuando la version deja de ser compatible


 1. PAQUETE modelo


              +----------------------------------------+
              | <<interface>>                          |
              | JuegoAhorcado                          |
              +----------------------------------------+
              | +establecerPalabraSecreta(String)      |
              | +ingresarLetra(String): ResultadoLetra |
              | +reiniciar()                           |
              | +getPalabraMostrada(): String          |
              | +getIntentosRestantes(): int           |
              | +getLetrasAcertadas(): List<Character> |
              | +getLetrasErradas(): List<Character>   |
              | +getEtapa(): EtapaAhorcado             |
              | +getEstado(): EstadoPartida            |
              | +getModalidad(): Modalidad             |
              | +getPalabraSecreta(): String           |
              +----------------------------------------+
                                  ^
                                  : implements
                                  :
  +----------------------------------------------+
  | <<abstract>>                                 |
  | JuegoBase   (implements Serializable)        |
  +----------------------------------------------+
  | #palabraSecreta: String                      |
  | #palabraMostrada: char[]                     |
  | #intentosRestantes: int                      |
  | #letrasAcertadas: List<Character>            |
  | #letrasErradas: List<Character>              |
  | #etapa: EtapaAhorcado                        |
  | #estado: EstadoPartida                       |
  | +MAX_INTENTOS: int = 6   {static final}      |
  +----------------------------------------------+
  | +ingresarLetra(String): ResultadoLetra       |
  | +establecerPalabraSecreta(String)            |
  | +reiniciar()                                 |
  | -revelarLetra(char, int)       {recursivo}   |
  | -haGanado(int): boolean        {recursivo}   |
  | +haPerdido(): boolean                        |
  | #elegirPalabra(): String       {abstracto}   |
  | +getModalidad(): Modalidad     {abstracto}   |
  +----------------------------------------------+
                         ^
                         | extends
                   +-----+-----------------------------------+
                   |                                         |
  +---------------------------------+    +----------------------------------------+
  | JuegoPalabraFija                |    | JuegoAlAzar                            |
  +---------------------------------+    +----------------------------------------+
  | -palabraConfigurada: String     |    | -administrador: AdministradorPalabras  |
  +---------------------------------+    +----------------------------------------+
  | +cambiarPalabra(String)         |    | #elegirPalabra(): String               |
  | #elegirPalabra(): String        |    | +getModalidad(): Modalidad             |
  | +getModalidad(): Modalidad      |    +----------------------------------------+
  +---------------------------------+
                                                           <>
                                                           |  (agregacion)
                                                           v
                                          +--------------------------------------------------+
                                          | AdministradorPalabras  (implements Serializable) |
                                          +--------------------------------------------------+
                                          | -palabras: List<String>                          |
                                          | -random: Random   {transient}                    |
                                          +--------------------------------------------------+
                                          | +agregar(String)                                 |
                                          | +obtenerAleatoria(): String                      |
                                          | -existe(String, int): boolean   {recursivo}      |
                                          | +cantidad(): int                                 |
                                          | +getPalabras(): List<String>                     |
                                          +--------------------------------------------------+

  +--------------------------------------------+
  | UtilTexto   {final}                        |
  +--------------------------------------------+
  | +normalizar(String): String   {static}     |
  | +esLetra(char): boolean        {static}    |
  +--------------------------------------------+
  ^ usada por JuegoBase y AdministradorPalabras 

  Enums del modelo:
  +--------------------------+   +--------------------------+
  | <<enum>> EstadoPartida   |   | <<enum>> ResultadoLetra  |
  +--------------------------+   +--------------------------+
  | EN_CURSO                 |   | ACIERTO                  |
  | GANADA                   |   | ERROR                    |
  | PERDIDA                  |   +--------------------------+
  +--------------------------+

  +--------------------------+   +--------------------------------+
  | <<enum>> Modalidad       |   | <<enum>> EtapaAhorcado         |
  +--------------------------+   +--------------------------------+
  | PALABRA_FIJA             |   | VACIO                          |
  | AL_AZAR                  |   | CABEZA                         |
  +--------------------------+   | TRONCO                         |
  | +getTextoBoton(): String |   | BRAZO_IZQ                      |
  +--------------------------+   | BRAZO_DER                      |
                                 | PIERNA_IZQ                     |
                                 | PIERNA_DER                     |
                                 +--------------------------------+
                                 | +dePorErrores(int)  {static}   |
                                 +--------------------------------+

  JuegoAhorcado --> ResultadoLetra   JuegoBase --> EstadoPartida
  JuegoBase --> EtapaAhorcado        JuegoBase --> Modalidad


 2. PAQUETE excepciones

  Exception (java.lang)
      ^
      |
  AhorcadoException   (base, serialVersionUID, constructor(String))
      ^
      |
      +-- LetraInvalidaException ------> lanza: JuegoBase.ingresarLetra
      |
      +-- LetraRepetidaException ------> lanza: JuegoBase.ingresarLetra
      |      (guarda la letra: getLetra())
      |
      +-- PartidaTerminadaException ---> lanza: JuegoBase.ingresarLetra
      |
      +-- PalabraInvalidaException ----> lanza: JuegoBase.establecerPalabraSecreta,
      |                                          AdministradorPalabras.agregar
      |
      +-- PalabraDuplicadaException ---> lanza: AdministradorPalabras.agregar


 3. PAQUETES gui y gui.sonido


  Main
   |-- crea --> AdministradorPalabras
   |-- crea --> GestorSonido
   '-- crea --> VentanaPrincipal  (JFrame + CardLayout)
                     |
                     |--<>--> PanelMenu            ..> Modalidad
                     |
                     |--<>--> PanelJuego --<>--> PanelDibujo ..> EtapaAhorcado
                     |            |
                     |            |--> JuegoAhorcado   (variable polimorfica)
                     |            '--> GestorSonido
                     |
                     '--<>--> PanelAdministracion
                                   |--> AdministradorPalabras
                                   '--> GestorSonido

  +--------------------------------+   +--------------------------------------+
  | GestorSonido  (gui.sonido)     |   | <<enum>> EfectoSonido  (gui.sonido)  |
  +--------------------------------+   +--------------------------------------+
  | +reproducir(EfectoSonido)      |   | ACIERTO                              |
  | +setSilenciado(boolean)        |   | ERROR                                |
  | +isSilenciado(): boolean       |   | ALERTA                               |
  +--------------------------------+   | VICTORIA                             |
                                       | DERROTA                              |
                                       +--------------------------------------+
                                       | +getFrecuencias(): int[]             |
                                       | +getDuraciones(): int[]              |
                                       +--------------------------------------+
  GestorSonido ..> EfectoSonido

 Explicación simbolos:  --|>  herencia   ..>  dependencia   -->  asociacion/uso
           <>--  composicion/agregacion   ^ : implements / extends

