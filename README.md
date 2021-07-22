# **ALURAFLIX API**
  Esta API foi sugerida pelo alura challenge para produzir uma plataforma para compartilhamento de vídeos. A solução foi desenvolvida em java.

## **BANCO DE DADOS**
  O arquivo docker-compose.yml tem os recursos necessários para utilização do banco de dados, bem como o Adminer, que é um simples gerenciador de banco de dados.   
  Para acessar o adminer, basta rodar o comando *`docker-compose up -d`* no diretorio da aplicação e acessar a url *http://localhost:8085*.  
  Para conectar no mysql referenciado no docker-compose.yml, basta entrar com os seguintes dados no adminer:
  * “System”   : select “MySQL” ;
  * “Server”   : type   “mysql” ;
  * “Username” : type   “root” ;
  * “Password” : type   "root” ;
  * “Database” : type   “aluraflix” ;
  * Click “login” button.

### **URL BASE**

   ```language
   http://localhost
   ```
### **DADOS DO VÍDEO**
---
   ```language
   GET /videos 
   ```
   Este endpoint retorna todos os vídeos bem como seus detalhes.

---
   ```language
   GET /videos/{id}
   ```
   Este endpoint retorna os detalhes de apenas um vídeo. É necessário especificar o `id`.

---
   ```language
   POST /videos
   ```
   Este endpoint salva um vídeo. Exemplo:
   ```JSON
   {
      "titulo": "Exemplo TDD",
      "descricao": "TDD na prática com Java usando @MockBean",
      "url": "https://www.youtube.com/watch?v=4VmbETu-dcA"
   }
   ```

---
   ```language
   PUT /videos/{id}
   ```
   Este endpoint atualiza os dados de um registro de vídeo já existente. É necessário especificar o `id`.
   ```JSON
   {
      "titulo": "Novo titulo atualizado",
      "descricao": "Nova descrição atualizada",
      "url": "https://www.youtube.com/watch?v=4VmbETu-dcA"
   }
   ```

---
   ```language
   DELETE /videos/{id}
   ```
   Este endpoint exclui o registro de um vídeo previamente cadastrado. É necessário especificar o `id`.

---