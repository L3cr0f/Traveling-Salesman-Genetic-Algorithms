# Traveling-Salesman-Genetic-Algorithms
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
ENGLISH VERSION

Two examples of genetic algorithms written in Java. One uses the library JGap and the other Jenetics.

Both examples show the Traveling Salesman Problem (TSP) with the same map:

    20
  A————B
  |\10/|
40| \/5|15
  | /\ |
  C————D
    10

Also, the map show the distances between the cities.
The optimal solution is 25 with the path ADCB.

Following I give a fuller explanation of the problem in spanish.

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
VERSIÓN EN ESPAÑOL

Aquí muestro dos ejemplos de algoritmos genéticos escritos en Java usando las librerías JGap y Jenetics. A continuación explico ambos programas de forma mas detallada que en la versión en inglés:

Problema a resolver:
Problema del viajante -> 4 ciudades y se sitúa en una ciudad de forma aleatoria.

    20
  A————B
  |\10/|
40| \/5|15
  | /\ |
  C————D
    10

A-B=20
A-C=40
A-D=10
B-C=5
B-D=15
C-D=10

Solución óptima -> 25 => 75 en la función fitness desarrollada

Codificación de los datos:
Una cadenas -> especificando la ruta [A,B,C,D]

===================================================================================
———————————————————————————————————————————————————————————————————————————————————
———————————————————————————————————————————————————————————————————————————————————
===================================================================================

En JGap:

Inicio:

- Parto de una población de 5 individuos elegidos aleatoriamente (si resulta que selecciona una ciudad repetida, vuelve a seleccionar otro destino).
- Evoluciono la población un máximo de 5 iteraciones.
- Utilizo como función fitness una desarrollada por mi en la que ha de contar la distancia de los caminos y, utilizando como máximo la suma de los valores de todos los caminos (los escoja o no), calculo un valor -> Si la diste de la ruta es 30 -> 100-30 = 70 de valor.
- La forma en la que se seleccionan los cromosomas (individuos) es mediante el selector por defecto (selecciona los cromosomas con mejor valor fitness).
- Utilizo como coeficiente de crossover 10/3 = 3 -> 3 de cada 5, es decir = 0,6.
- La función de crossover para A-B que utilizo se basa en la selección del bloque final (constituido por 2 elementos) del elemento A e introducirlo en donde se sitúa el primer elemento de ese bloque en el elemento B. Lo mismo ocurre cuando B-A.
- Utilizo como coeficiente de mutación 0.2 (un valor bastante elevado).

==================================================================

Resultados:

- El algoritmo tarda muy poco tiempo en ejecutarse, encontrando la solución óptima 19 de cada 20 veces.

==================================================================

Conclusiones:

- JGap es una buena herramienta, fácil de comprender (incluye un javadoc para ver y comprender las distintas funciones), aunque algo tediosa de configurar a tus necesidades, teniendo que desarrollar tu mismo tanto la generación inicial (tienes que controlar que sea una ruta válida) como el algoritmo de crossover para el problema del "Vendedor ambulante".

- La estructura que sigue es la siguiente:
	Alelo -> guarda el valor (A, B, C o D)

	Gen -> guarda el alelo

	Cromosoma (individuo) -> guarda los genes

	Genotipo (población) -> guarda los cromosomas

===================================================================================
———————————————————————————————————————————————————————————————————————————————————
———————————————————————————————————————————————————————————————————————————————————
===================================================================================

En Jenetics:

Inicio:

- Parto de una población de 5 individuos elegidos aleatoriamente mediante la permutación de las cuatro ciudades (por lo que no se repiten -> rutas válidas).
- Evoluciono la población un máximo de 20 generaciones.
- Utilizo como función fitness una desarrollada por mi en la que ha de contar la distancia de los caminos y, utilizando como máximo la suma de los valores de todos los caminos (los escoja o no), calculo un valor -> Si la diste de la ruta es 30 -> 100-30 = 70 de valor.
- La forma en la que se seleccionan los cromosomas (individuos) es mediante dos selectores:
	- Selector de supervivencia: determina cuales viven y cuales se descartan. Utilizo
	la ruleta de selección aleatoria.
	- Selector de descendencia: de entre los supervivientes se seleccionan aquellos
	que pueden pasar al crossover. Utilizo el torneo entre cromosomas.
- Utilizo como coeficiente de crossover 0.6.
- La función de crossover utilizada es la llamada "PartiallyMatchedCrossover", la cual permuta bloques y, si se repitiese algún gen en el cromosoma, este también se permutaría.
- Utilizo como coeficiente de mutación 0.2.

==================================================================

Resultados:
- El algoritmo tarda muy poco en ejecutarse y encuentra la solución prácticamente en el 100% de los casos. 

==================================================================

Conclusiones:
- Jenetics es otra buena librería para el desarrollo de algoritmos genéticos. Resulta algo complicada de entender (estructura, funciones…), aún disponiendo de javadoc y de una guía. Lo mejor es su facilidad a la hora de desarrollar cualquier algoritmo para cualquier problema, ya que el tiempo que tienes que dedicar a la programación es básicamente testimonial.

- La estructura que sigue es la siguiente:
	Gen -> guarda la información (alelo)
	Cromosoma (individuo) -> guarda los genes
	Genotipo -> guarda los cromosomas
	Fenotipo -> conjunción entre el genotipo y la función fitness
	Población -> conjunto de fenotipos

===================================================================================
———————————————————————————————————————————————————————————————————————————————————
———————————————————————————————————————————————————————————————————————————————————
===================================================================================

CONCLUSIÓN -> Jenetics es más sencilla a la hora de implementación que JGap, aunque esta es más fácil de entender.
