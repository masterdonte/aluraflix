# **ALURAFLIX API**
[![Build Status](https://www.travis-ci.com/masterdonte/aluraflix.svg?branch=main&amp;status=passed)](https://app.travis-ci.com/github/masterdonte/aluraflix)  

  Esta API foi sugerida pelo alura challenge para produzir uma plataforma para compartilhamento de vídeos. A solução foi desenvolvida em java.

## **STACK**
    - JAVA 8
    - MYSQL
## **DOCKER**
O arquivo docker-compose.yml tem os recursos necessários para utilização do banco de dados, bem como o Adminer, que é um simples gerenciador de banco de dados. Para acessar o adminer, basta rodar o comando *`docker-compose up -d`* no diretorio da aplicação e acessar a url *http://localhost:8085*.
### **PARAMETROS ADMINER**
    - “System”   : select “MySQL”
    - “Server”   : type   “mysql”
    - “Username” : type   “root”
    - “Password” : type   "root”
    - “Database” : type   “aluraflix”
## **DOCUMENTAÇÂO**
Para acessar a documentação da API clique [aqui](http://ec2-18-231-99-147.sa-east-1.compute.amazonaws.com:8080/swagger-ui.html)
