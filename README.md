📦 Plataforma de Processamento de Pedidos
Sistema backend desenvolvido em Java com Spring Boot para gerenciamento de pedidos, incluindo criação, edição, listagem, consulta por ID e deleção. Utiliza mapeamento DTO, tratamento centralizado de exceções, e será integrado com Kafka futuramente.
🚀 Funcionalidades
- Criar novo pedido
- Listar todos os pedidos
- Buscar pedido por ID
- Editar pedido existente
- Excluir pedido
- Tratamento elegante de exceções com mensagens personalizadas
🛠️ Tecnologias Utilizadas
- Java 24
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Kafka (em breve)
- GitHub
📁 Estrutura de Pacotes
src/main/java/com.br.plataforma_processamento_pedidos
│
├── controller         # (Em desenvolvimento)
├── service            # Regras de negócio
├── model              # Entidades do sistema
├── dtos               # Objetos de transferência de dados
├── exception          # Exceções personalizadas
├── infra              # Tratamento de erro / utilitários
├── repositories       # Interfaces JPA
└── mapper             # Conversão entre DTOs e entidades


🧪 Testes
- Serão implementados utilizando JUnit e Spring Boot Test
- Testes unitários e de integração com MockMvc
✉️ Tratamento de Erros
As exceções de negócio são tratadas com BusinessException, retornando um JSON amigável com a mensagem e status HTTP adequado:
{
  "message": "Pedido com id: 10 não encontrado"
}


📈 Futuras melhorias
- Integração com Apache Kafka para processamento assíncrono
- Criação dos controllers REST
- Documentação com Swagger
- Validações com Bean Validation
- Autenticação e autorização com Spring Security
