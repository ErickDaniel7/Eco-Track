# EcoMonitor
<strong>Desenvolvido por:</strong> Erick Daniel Teixeira Vier

### 📘 Descrição
O <strong>EcoMonitor</strong> é uma API RESTful desenvolvida em Spring Boot para gerenciar estações de monitoramento ambiental e leituras de sensores como temperatura, umidade, CO2 e ruído. Criado para a startup fictícia EcoTrack Solutions, o sistema visa facilitar o acompanhamento em tempo real de dados ambientais.

#

### ⚙️ Tecnologias Utilizadas
- Java 22

- Spring Boot

- Spring Data JPA

- Spring Security + JWT

- H2 Database (para testes)

- Swagger / OpenAPI

- Maven

- Lombok

#

### 🚀 Como Executar o Projeto
### Pré-requisitos

- Java 22 instalado

- Maven instalado

- IDE (IntelliJ, VS Code ou Eclipse)

### ⚠️ Importante: Configuração do Banco de Dados

Antes de executar a aplicação, não esqueça de configurar corretamente o acesso ao banco de dados no arquivo application.properties.

```bash
#spring.datasource.url=jdbc:postgresql://localhost:5432/exemplo
#spring.datasource.username=${JDBC_DATABASE_USERNAME\:postgres}
#spring.datasource.password=${JDBC_DATABASE_PASSWORD\:<<YOUR_PASSWORD>>}
```

### Passo a passo
#### 1. Clone o repositório ou extraia o ZIP:

```bash
git clone https://github.com/seu-usuario/ecotrack-solutions.git
```

ou abra o projeto direto em sua IDE com os arquivos extraídos do .zip.

#### 2. Execute a aplicação com Maven:

```bash
./mvnw spring-boot:run
```

ou diretamente pela IDE executando a classe <strong>EcoMonitorApplication.java.</strong>

#### 3. Acesse a aplicação:

- Swagger: http://localhost:8080/swagger-ui.html

- H2 Console (se habilitado): http://localhost:8080/h2-console

#

### 🔐 Autenticação
- A autenticação usa JWT.

- Perfis:

  - <strong>ADMIN:</strong> acesso completo.

  - <strong>USER:</strong> acesso somente leitura.

Você pode usar credenciais de exemplo no Swagger para testar autenticações (fornecidas nos arquivos <strong>application.properties</strong> ou configuradas em <strong>UserDetailsService).</strong>

#

### 📅 Tarefas Agendadas
- A cada 2 minutos:

  - Verifica se há estações inativas.

  - Gera log de advertência simulando acionamento da equipe de manutenção.
 
#

### 📊 Funcionalidades
- <strong>CRUD de Estações</strong>

- <strong>CRUD de Leituras de Sensores</strong> (com alertas automáticos)

- <strong>Endpoints para consultas filtradas e estatísticas de sensores</strong>

- <strong>JWT + Perfis de acesso</strong>

- <strong>Swagger para documentação e testes</strong>