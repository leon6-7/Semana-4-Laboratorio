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



