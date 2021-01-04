# Game





##  Game preferences:

1. Obtener el tiempo de la partida indicado en las preferencias.
2. Obtener el modo de juego (**Time**, **Attempts**).
3. Obtener el tiempo de cada palabra.
4. Si el modo de juego es Attempts obtiener el número de intentos.



## Running game 

1. Una vez que comienza el juego, el tiempo se irá decrementando segundo a segundo. la `progressBar` se deberá actualizar debidamente.
2. Una vez transcurrido el tiempo de la palabra, cambiará de palabra y el contador se incrementa.
3. Una vez transcurrido el tiempo o los intentos el juego finaliza.





## Show Word







## Game options:

El juego consta de dos opciones de juego : **Attempts**, **Time**

- Attempts:

1. El juego se inicia.

2. El contador de palabra se incrementa y se muesta la palabra.

3. Elegir opción: Al pulsar en una opción se debe comprobar si la opción pulsada es la correcta.

   1. *Correcta*: Si la opción que ha pulsado el usuario es correcta para esa palabra, se debe de **incrementar el contador de palabras correctas**, **incrementar el contador de palabras**, **mostrar la siguiente palabra**.
   2. *Incorrecta*: Si la opción que ha pulsado el usuario es incorrecta para esa palabra, se debe **decrementar el número de intentos**, **NO TOCAR EL CONTADOR DE PALABRAS CORRECTAS**, **incrementar el contador de palabras**, **mostrar la siguiente palabra**.

4. Finalización del juego: El juego puede finalizar por dos motivos:

   1. El tiempo se ha agotado.
   2. No tiene más intentos disponibles.

   

- Time: 

1. El juego se inicia.

2. El contador de palabra se incrementa y se muesta la palabra.

3. Elegir opción: Al pulsar en una opción se debe comprobar si la opción pulsada es la correcta.

   1. *Correcta*: Si la opción que ha pulsado el usuario es correcta para esa palabra, se debe de **incrementar el contador de palabras correctas**, **incrementar el contador de palabras**, **mostrar la siguiente palabra**.
   2. *Incorrecta*: Si la opción que ha pulsado el usuario es incorrecta para esa palabra, **NO TOCAR EL CONTADOR DE PALABRAS CORRECTAS**, **incrementar el contador de palabras**, **mostrar la siguiente palabra**.

4. Finalización del juego: 

   1. El tiempo se ha agotado.

      

      

      ​	

      

      