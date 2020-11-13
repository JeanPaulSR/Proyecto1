# Proyecto1

Para compilar el programa: ant build

Para limpiar el directorio: ant clean

Para correr el programa: ant Main

-Dseed=x 
Para definir la semilla agregar el argumento: (Donde x es la semilla querida. Solo recibe enteros).

-Dgen=n
Para generar n numero de soluciones. Si definimos una semilla con -Dseex x, entonces la semilla incial seria x. (Solo recibe enteros)

-Dperm=x
Para generar una permutacion de la lista de ciudades que fueron pasados. x es la semilla de la permutacion que queremos, es decir, siempre genera el mismo permutacion dado un x

-Dpath=s
Busca un documento s.txt que tiene la lista de ciudades que queremos generar su solucion. Por default busca 'tsp.TXT'. Los documentos 40.txt y 150.txt tienen sus propias conjunto de ciudades. (El '.txt' es agregado, entonces para 40 solo necesitas escribir -Dpath=40)

-Dout=s
Impreme los resultados en un documento s.txt y sobrecarga los el informacion en el documento

Mi computadora suele ser muy lento, entonces para los ejemplos generados tuve que bajar l y maximoIntentos en calculaLotes. 

