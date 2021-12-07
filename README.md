# Projeto de desafio MVC Java de Raphael Mendes Stopa

## Rodando o projeto

Este projeto foi desenvolvido, usando uma técnica para desenvolvimento ágil que usa um Docker de Mysql com migrations do liquibase. Para rodar o Docker use o comando abaixo:

```
docker-compose -p “project_raphael” -f src/main/docker/mysql.yml up -d
```

Na primeira inicialização, pode demorar um pouco. Por favor, seja paciente. Dependendo da IDE, pode ser necessário um pequeno ajuste para melhor conexão com o banco de dados. String de conexão: jdbc:mysql://localhost:3306/gft?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true


## Atenção

Projeto com o Spring Security. Para poder logar use:

```
Para o gerenciador use:
UserName: admin
Password: admin

Para o Scrum Master use:
UserName: scrum
Password: scrum
```

Estes dados estarão automaticamente no banco de dados graças ao liquibase.

Veja o projeto sendo inicializado aqui: https://youtu.be/dXa9FNnM1Mk

## Documentação
Possui duvidas em relação a minha abordagem quanto a este projeto? Para isto, gerei uma documentação com Ascii que se encontra dentro das pastas do projeto.
```
src/docs/asciidocs
```

## Tarefas pedidas.
- [X] Popular o banco (acontece já na inicialização).
- [x] CRUD de todas as entidades.
- [x] Validação dos campos.
- [x] Login de Gerente com acesso a toda aplicação.
- [x] Login de Scrum Master com acesso apenas as Dailys.


## Tarefas excedentes realizadas.
- [X] Cadastro com foto (feito da maneira mais simples).
- [X] Relatório das maiores e menores notas.
- [x] Envio de email automático para o startergft@gmail.com (como solicitado) sempre que um Scrum Master logar na aplicação. (Receberá um email de ava.desafio.gft@gmail.com)

