# Projecto de Referência de SpringBoot

## O que fazer

Este projecto serve para verificar que todas as dependências necessárias ao projecto estão a funcionar.

## Dependências

Este projecto necessita de:

* Docker
* Python 3

Na realidade o projecto vai usar Java17+ e Postgres, mas esses vão estar disponíveis pelos containers. Só são necessários se quiser correr a aplicação nativamente.


## Primeiro passo

Deve correr o ficheiro `setup.sh`.

## Segundo passo

Deve correr o ficheiro `run.sh`.

Este comando vai iniciar dois containers:

* Um com a aplicação que existe nesta pasta, com hot reloading.
* Uma instância de um container com Postgres

# Terceiro passo

Abrir http://localhost:8080 num browser e verificar que aparece a string "Olá Mundo".

Pode ainda confirmar que consegue ver um objecto JSON em http://localhost:8080/api/author/1
