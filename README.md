# Prav Backend - Autenticação com PostgreSQL

Este projeto usa PostgreSQL como banco de dados. Abaixo estão passos rápidos para preparar o banco e rodar a aplicação localmente.

1) Criar banco e usuário (execute no servidor Postgres ou via psql):

```sql
-- Exemplo: execute como superuser (role: postgres)
CREATE DATABASE pravdb;
CREATE USER prav_user WITH ENCRYPTED PASSWORD 'troque_essa_senha';
GRANT ALL PRIVILEGES ON DATABASE pravdb TO prav_user;
```

2) Configurar `src/main/resources/application.properties` localmente (não comitar)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pravdb
spring.datasource.username=prav_user
spring.datasource.password=troque_essa_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=UMA_CHAVE_SECRETA_MUITO_LONGA_PARA_USAR_AQUI (min 32 chars)
jwt.expirationMs=3600000
```

3) Rodar a aplicação

No PowerShell, a partir da raiz do projeto:

```powershell
mvnw.cmd spring-boot:run
```

4) Testar endpoints de autenticação
- Registrar:
```powershell
curl -Method POST -Uri http://localhost:8080/auth/register -ContentType "application/json" -Body '{"email":"user@example.com","senha":"Senha123","nome":"Fulano"}'
```
- Login:
```powershell
curl -Method POST -Uri http://localhost:8080/auth/login -ContentType "application/json" -Body '{"email":"user@example.com","senha":"Senha123"}'
```

5) Observações
- Nunca comite `application.properties` com credenciais. Use variáveis de ambiente em produção.
- Se preferir que eu faça os testes automatizados contra seu Postgres, me passe as credenciais temporárias ou execute o script SQL acima e confirme que o `application.properties` foi criado com os mesmos dados.
