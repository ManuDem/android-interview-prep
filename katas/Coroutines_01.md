* "launch" = "fire and forget": avvia una coroutine e ti restituisce un "Job" (non produce un risultato).
* "async" = come "launch" ma con risultato: restituisce un "Deferred<T>" e il valore lo ottieni con un "await()". 
* &nbsp;Cancellation \& structured concurrency: la cancellazione è cooperativa e passa dal `Job/Scope`; le coroutine “figlie” vivono dentro uno scope e vengono cancellate insieme al “genitore” (niente coroutines che restano appese).
