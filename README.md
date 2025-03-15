# Event Flow

Event Flow é uma API RESTful desenvolvida para gerenciar inscrições em eventos, controlar convites e oferecer premiações baseadas nos convites realizados pelos usuários. A aplicação permite a criação e gerenciamento de eventos, além de contabilizar os convites feitos por cada usuário para promover sorteios e premiações.

## Tecnologias Utilizadas

- Java
- Spring Boot (Framework para aplicações web e APIs RESTful)
- Spring Data JPA (Acesso a dados)
- MySQL (Banco de dados relacional)
- Docker (Containerização para facilitar o ambiente de desenvolvimento)
- Postman (Documentação e testes da API)
  
## Estrutura do Projeto
O sistema segue um modelo RESTful para manipulação de dados e opera por meio das seguintes entidades principais:

- User: Pessoas que se inscrevem nos eventos e podem convidar outros participantes.
- Event: Eventos cadastrados no sistema, no qual o usuário escolhe para se escrever.
- Subscription: Inscrição do usuários no evento escolhido.
  
## Arquitetura
A arquitetura do projeto é baseada no padrão MVC, organizado da seguinte forma:

- Controller: Define os endpoints da API (EventController, SubscriptionController).
- Service: Contém a lógica de negócios (EventService, SubscriptionService).
- Repository: Interage com o banco de dados (EventRepository, SubscriptionRepository, UserRepository).
- DTO: Objetos usados para transferência de dados entre as camadas.
- Exceptions: Exceções personalizadas para um tratamento de erros eficiente.

## Lógica de Convites e Ranking
A API oferece funcionalidades para gerenciar convites e determinar rankings baseados na quantidade de convites feitos por usuários. A cada convite aceito, o usuário acumula pontos que são utilizados para gerar um ranking de participação nos eventos.

#### O sistema possui dois endpoints principais relacionados a rankings:

- Três primeiros colocados no Ranking: Obtém o ranking geral dos usuários de um evento, baseado na quantidade de convites que cada um fez e que foram aceitos e exibe somente os 3 primeiros.

- Posição do usuário no Ranking: Permite consultar o ranking de um usuário específico em um evento, retornando a posição dele dentro daquele evento com base na quantidade de convites realizados e aceitos
  
## Funcionalidades
- CRUD completo para eventos e inscrições.
- Contabilização de convites realizados e aceitos pelos usuários.
- Documentação/testes da API com Postman.
