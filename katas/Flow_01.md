* Un Flow è "cold": non fa nulla finchè qualcuno non chiama "collect()"; ogni "collect" ri-esegue il Flow da capo.
* "suspend fun" = 1 valore (o errore) una volta: "Flow" = stream di più valori nel tempo (0..N) con cancellazione/backpressure integrate.
* Error Handling: "catch {...}" intercetta errori "a monte" del Flow; per errori nel "collect {...}" usi "try/catch" attorno al collect.
