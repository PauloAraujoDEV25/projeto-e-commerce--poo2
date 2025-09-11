# Ada Commerce - E-Commerce

## Descrição do Projeto

O Ada Commerce é um sistema de e-commerce desenvolvido em Java que implementa todas as funcionalidades necessárias para gerenciar clientes, produtos e vendas de uma loja online. O projeto possui duas implementações:

1. **Versão Original**: Trabalha diretamente com entidades do domínio
2. **Versão com DTOs**: Implementa DTOs (Data Transfer Objects) e Mappers para uma arquitetura mais robusta

## Funcionalidades Implementadas

### Clientes
- ✅ Cadastrar cliente (obrigatório ter documento)
- ✅ Listar todos os clientes
- ✅ Atualizar dados do cliente
- ✅ Buscar cliente por documento
- ✅ Não permite exclusão (mantém histórico)

### Produtos
- ✅ Cadastrar produto
- ✅ Listar todos os produtos
- ✅ Atualizar dados do produto
- ✅ Buscar produto por nome
- ✅ Não permite exclusão (mantém histórico)

### Vendas
- ✅ Criar venda para um cliente
- ✅ Adicionar item (produto) com quantidade e preço personalizado
- ✅ Remover item da venda
- ✅ Alterar quantidade do item
- ✅ Finalizar pedido (muda status para "Aguardando pagamento")
- ✅ Realizar pagamento (muda status para "Pago")
- ✅ Entregar pedido (muda status para "Finalizado")
- ✅ Sistema de notificação por email em cada etapa

## Regras de Negócio Implementadas

1. **Cliente**: Todo cliente deve ter documento de identificação
2. **Venda**: Sempre criada com data atual e status "Aberto"
3. **Status Aberto**: Permite adicionar/remover itens e alterar quantidades
4. **Valor de Venda**: Produtos podem ter preço diferente do valor original
5. **Finalização**: Pedido deve ter ao menos um item e valor > 0
6. **Pagamento**: Só pode ser feito em vendas "Aguardando pagamento"
7. **Entrega**: Só pode ser feita em vendas "Pagas"
8. **Notificações**: Cliente é notificado por email em cada etapa

## Princípios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada classe tem uma única responsabilidade
- `Cliente`, `Produto`, `Venda`, `ItemVenda` são entidades puras
- Services isolam a lógica de negócio
- Repositories isolam a persistência
- Controllers isolam a interação com usuário

### Open/Closed Principle (OCP)
- Interfaces permitem extensão sem modificação
- Novos tipos de notificação podem ser adicionados implementando `NotificacaoService`
- Novos repositórios podem ser criados implementando as interfaces

### Liskov Substitution Principle (LSP)
- Implementações podem ser substituídas por suas interfaces
- `EmailNotificacaoService` pode ser substituído por qualquer implementação de `NotificacaoService`

### Interface Segregation Principle (ISP)
- Interfaces específicas e focadas
- Clientes não dependem de métodos que não usam

### Dependency Inversion Principle (DIP)
- Classes dependem de abstrações (interfaces), não de implementações concretas
- Serviços recebem repositórios via construtor (Injeção de Dependência)

## Arquitetura com DTOs e Mappers

### Benefícios dos DTOs (Data Transfer Objects)
- **Separação de Responsabilidades**: DTOs isolam a representação de dados da lógica de negócio
- **Segurança**: Evita exposição de dados internos das entidades
- **Flexibilidade**: Permite diferentes representações para entrada (Request) e saída (Response)
- **Versionamento**: Facilita evolução da API sem quebrar compatibilidade
- **Validação**: DTOs específicos para cada operação com validações adequadas

### Tipos de DTOs Implementados
- **DTOs de Resposta**: `ClienteDTO`, `ProdutoDTO`, `VendaDTO`, `ItemVendaDTO`
- **DTOs de Requisição**: `ClienteRequestDTO`, `ProdutoRequestDTO`, `ItemVendaRequestDTO`

### Mappers - Padrão de Conversão
- **Responsabilidade única**: Conversão entre entidades e DTOs
- **Métodos estáticos**: Facilita uso e testes
- **Conversões bidirecionais**: Entity → DTO e DTO → Entity
- **Atualizações**: Métodos para atualizar entidades existentes

### Vantagens da Arquitetura com DTOs
1. **Desacoplamento**: Mudanças na estrutura interna não afetam a API
2. **Performance**: DTOs podem ser otimizados para cada caso de uso
3. **Segurança**: Controle fino sobre quais dados são expostos
4. **Manutenibilidade**: Código mais organizado e fácil de manter
5. **Testabilidade**: Facilita criação de testes unitários

## Estrutura do Projeto

### Versão Original
```
src/main/java/com/adacommerce/
├── model/                 # Entidades do domínio
│   ├── Cliente.java
│   ├── Produto.java
│   ├── Venda.java
│   ├── ItemVenda.java
│   └── StatusVenda.java
├── repository/            # Camada de persistência
│   ├── ClienteRepository.java
│   ├── ClienteRepositoryImpl.java
│   ├── ProdutoRepository.java
│   ├── ProdutoRepositoryImpl.java
│   ├── VendaRepository.java
│   └── VendaRepositoryImpl.java
├── service/               # Lógica de negócio
│   ├── ClienteService.java
│   ├── ProdutoService.java
│   ├── VendaService.java
│   ├── NotificacaoService.java
│   └── EmailNotificacaoService.java
├── controller/            # Camada de apresentação
│   ├── ClienteController.java
│   ├── ProdutoController.java
│   └── VendaController.java
├── util/                  # Utilitários
│   └── InputUtil.java
└── AdaCommerceApplication.java  # Classe principal
```

### Versão com DTOs e Mappers (Mais Robusta)
```
src/main/java/com/adacommerce/
├── model/                 # Entidades do domínio
├── dto/                   # Data Transfer Objects
│   ├── ClienteDTO.java
│   ├── ClienteRequestDTO.java
│   ├── ProdutoDTO.java
│   ├── ProdutoRequestDTO.java
│   ├── VendaDTO.java
│   ├── ItemVendaDTO.java
│   └── ItemVendaRequestDTO.java
├── mapper/                # Conversores Entity ↔ DTO
│   ├── ClienteMapper.java
│   ├── ProdutoMapper.java
│   ├── VendaMapper.java
│   └── ItemVendaMapper.java
├── repository/            # Camada de persistência
├── service/               # Lógica de negócio
│   ├── ClienteServiceDTO.java
│   ├── ProdutoServiceDTO.java
│   ├── VendaServiceDTO.java
│   └── ... (serviços originais)
├── controller/            # Camada de apresentação
│   ├── ClienteControllerDTO.java
│   ├── ProdutoControllerDTO.java
│   ├── VendaControllerDTO.java
│   └── ... (controladores originais)
├── util/                  # Utilitários
└── AdaCommerceAppDTO.java         # Classe principal com DTOs
```

## Como Executar

### Pré-requisitos
- Java 8 ou superior
- Console/Terminal com suporte a entrada de dados

### Versão Original

```bash
# Navegar para o diretório do projeto
cd projeto-e-commerce-poo2

# Compilar o projeto
javac -cp . -d bin src/main/java/com/adacommerce/*.java src/main/java/com/adacommerce/*/*.java

# Executar a aplicação
java -cp bin com.adacommerce.AdaCommerceApplication
```

### Versão com DTOs (Recomendada)

```bash
# Navegar para o diretório do projeto
cd projeto-e-commerce-poo2

# Compilar o projeto incluindo DTOs e Mappers
javac -cp . -d bin src/main/java/com/adacommerce/*.java src/main/java/com/adacommerce/*/*.java

# Executar a aplicação com DTOs
java -cp bin com.adacommerce.AdaCommerceAppDTO
```

### Execução Simplificada (no diretório src/main/java)

```bash
# Versão Original
javac com/adacommerce/*.java com/adacommerce/*/*.java
java com.adacommerce.AdaCommerceApplication

# Versão com DTOs
javac com/adacommerce/*.java com/adacommerce/*/*.java
java com.adacommerce.AdaCommerceAppDTO
```

## Dados de Exemplo

O sistema carrega automaticamente dados de exemplo:

### Clientes
1. João Silva (12345678901) - joao.silva@email.com
2. Maria Santos (98765432100) - maria.santos@email.com
3. Pedro Costa (45678912300) - pedro.costa@email.com

### Produtos
1. Notebook Dell - R$ 2.500,00
2. Mouse Gamer - R$ 150,00
3. Teclado Mecânico - R$ 300,00
4. Monitor 24" - R$ 800,00
5. Webcam HD - R$ 200,00

## Fluxo de Uso Típico

1. **Criar uma venda** para um cliente existente
2. **Adicionar itens** à venda (produtos com quantidade e preço)
3. **Finalizar o pedido** (cliente recebe email de confirmação)
4. **Realizar o pagamento** (cliente recebe email de confirmação)
5. **Entregar o pedido** (cliente recebe email de entrega)

## Tecnologias Utilizadas

- **Java 8+**: Linguagem principal
- **Collections Framework**: Para armazenamento em memória
- **BigDecimal**: Para cálculos monetários precisos
- **LocalDateTime**: Para controle de datas
- **Streams API**: Para operações funcionais

## Padrões de Projeto Utilizados

- **Repository Pattern**: Abstração da camada de persistência
- **Service Layer**: Isolamento da lógica de negócio
- **MVC (Model-View-Controller)**: Separação de responsabilidades
- **Dependency Injection**: Inversão de controle

## Melhorias Futuras

- [ ] Persistência em banco de dados
- [ ] Interface gráfica (JavaFX/Swing)
- [ ] API REST
- [ ] Autenticação e autorização
- [ ] Relatórios de vendas
- [ ] Estoque de produtos
- [ ] Descontos e promoções
- [ ] Múltiplas formas de pagamento
