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

## Exemplos de uso (register / login / uso do token)

Abaixo há exemplos em PowerShell (recomendado no Windows) e em curl. Ajuste `localhost:8080` se o servidor estiver em outra URL/porta.

1) Registrar um usuário (PowerShell):

```powershell
$body = @{ email='user@example.com'; senha='Senha123'; nome='Fulano' } | ConvertTo-Json
$reg = Invoke-RestMethod -Method Post -Uri http://localhost:8080/auth/register -Body $body -ContentType 'application/json'
$reg | Format-List
```

Resposta esperada (exemplo):

```json
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
	"email": "user@example.com"
}
```

2) Login (PowerShell):

```powershell
$body = @{ email='user@example.com'; senha='Senha123' } | ConvertTo-Json
$login = Invoke-RestMethod -Method Post -Uri http://localhost:8080/auth/login -Body $body -ContentType 'application/json'
$login | Format-List
```

3) Usar o token para acessar um endpoint protegido (PowerShell):

```powershell
$token = $login.token
Invoke-RestMethod -Method Get -Uri http://localhost:8080/cservico/servico -Headers @{ Authorization = "Bearer $token" } | ConvertTo-Json -Depth 5
```

4) Exemplos com curl (Linux/macOS / Git Bash no Windows):

Registrar:
```bash
curl -X POST http://localhost:8080/auth/register \
	-H "Content-Type: application/json" \
	-d '{"email":"user@example.com","senha":"Senha123","nome":"Fulano"}'
```

Login (retorna token):
```bash
curl -X POST http://localhost:8080/auth/login \
	-H "Content-Type: application/json" \
	-d '{"email":"user@example.com","senha":"Senha123"}'
```

Usar token em requisição protegida:
```bash
curl -H "Authorization: Bearer <SEU_TOKEN_AQUI>" http://localhost:8080/cservico/servico
```

5) Observações sobre o token
- O token retornado é um JWT no formato header.payload.signature (três partes separadas por ponto). Não use o hash bcrypt armazenado no banco como token.
- Se receber 401/403 ao acessar um endpoint protegido, verifique:
	- o token não expirou (claim `exp`);
	- o header Authorization está no formato `Bearer <token>`;
	- o `jwt.secret` configurado no backend corresponde ao usado para validar o token.

6) Salvar token localmente (PowerShell)
```powershell
$token | Out-File -Encoding utf8 .\token.txt
```

7) Exemplo rápido de troubleshooting
- Se `/auth/register` retornar erro de email já cadastrado, use `/auth/login`.
- Se o backend retornar erro relacionado a `jwt.secret` ou `Could not resolve placeholder 'jwt.secret'`, defina `jwt.secret` em `src/main/resources/application.properties` (ou via variável de ambiente `JWT_SECRET`).

