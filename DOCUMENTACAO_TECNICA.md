# Documentação Técnica - Ada Commerce

## Resumo da Implementação

O projeto Ada Commerce foi implementado com **duas arquiteturas diferentes** para demonstrar a evolução de um sistema simples para uma arquitetura mais robusta e escalável.

## Arquiteturas Implementadas

### 1. Arquitetura Original (Simples)
- **Classes principais**: `AdaCommerceApplication.java`
- **Approach**: Controladores trabalham diretamente com entidades
- **Vantagens**: Simplicidade, menos código
- **Desvantagens**: Acoplamento alto, dificuldade de manutenção

### 2. Arquitetura com DTOs e Mappers (Robusta)
- **Classes principais**: `AdaCommerceAppDTO.java`
- **Approach**: Separação clara entre camadas usando DTOs
- **Vantagens**: Baixo acoplamento, alta manutenibilidade, escalabilidade
- **Desvantagens**: Mais código, maior complexidade inicial

## Componentes Implementados

### Camada de Modelo (Domain)
```java
// Entidades principais
- Cliente.java          // Representa um cliente
- Produto.java          // Representa um produto
- Venda.java           // Representa uma venda/pedido
- ItemVenda.java       // Representa um item dentro da venda
- StatusVenda.java     // Enum para status da venda
```

### Camada de DTOs (Data Transfer Objects)
```java
// DTOs de Response (saída)
- ClienteDTO.java      // Cliente para exibição
- ProdutoDTO.java      // Produto para exibição
- VendaDTO.java        // Venda para exibição
- ItemVendaDTO.java    // Item de venda para exibição

// DTOs de Request (entrada)
- ClienteRequestDTO.java     // Dados para criar/atualizar cliente
- ProdutoRequestDTO.java     // Dados para criar/atualizar produto
- ItemVendaRequestDTO.java   // Dados para adicionar item à venda
```

### Camada de Mappers (Conversores)
```java
- ClienteMapper.java   // Cliente ↔ ClienteDTO
- ProdutoMapper.java   // Produto ↔ ProdutoDTO
- VendaMapper.java     // Venda ↔ VendaDTO
- ItemVendaMapper.java // ItemVenda ↔ ItemVendaDTO
```

### Camada de Repositório (Persistência)
```java
// Interfaces
- ClienteRepository.java
- ProdutoRepository.java
- VendaRepository.java

// Implementações em Memória
- ClienteRepositoryImpl.java
- ProdutoRepositoryImpl.java
- VendaRepositoryImpl.java
```

### Camada de Serviços (Business Logic)
```java
// Versão Original
- ClienteService.java
- ProdutoService.java
- VendaService.java

// Versão com DTOs
- ClienteServiceDTO.java
- ProdutoServiceDTO.java
- VendaServiceDTO.java

// Serviços Auxiliares
- NotificacaoService.java (interface)
- EmailNotificacaoService.java (implementação)
```

### Camada de Controladores (Presentation)
```java
// Versão Original
- ClienteController.java
- ProdutoController.java
- VendaController.java

// Versão com DTOs
- ClienteControllerDTO.java
- ProdutoControllerDTO.java
- VendaControllerDTO.java
```

### Utilitários
```java
- InputUtil.java       // Helper para entrada de dados
```

## Princípios SOLID Aplicados

### 1. Single Responsibility Principle (SRP) ✅
- **Entidades**: Cada classe tem uma única responsabilidade
- **Serviços**: Isolam lógica de negócio específica
- **Repositórios**: Focados apenas em persistência
- **Controladores**: Apenas interação com usuário
- **Mappers**: Apenas conversão entre tipos

### 2. Open/Closed Principle (OCP) ✅
- **Interfaces**: Permitem extensão sem modificação
- **Exemplo**: Novos tipos de notificação podem ser adicionados implementando `NotificacaoService`
- **Exemplo**: Novos repositórios (BD, arquivos) podem implementar as interfaces

### 3. Liskov Substitution Principle (LSP) ✅
- **Implementações**: Podem ser substituídas pelas interfaces
- **Exemplo**: `EmailNotificacaoService` pode ser substituído por `SMSNotificacaoService`

### 4. Interface Segregation Principle (ISP) ✅
- **Interfaces específicas**: Clientes não dependem de métodos não usados
- **DTOs específicos**: RequestDTOs diferentes de ResponseDTOs

### 5. Dependency Inversion Principle (DIP) ✅
- **Abstrações**: Classes dependem de interfaces, não implementações
- **Injeção de Dependência**: Via construtor em todos os serviços

## Padrões de Projeto Implementados

### 1. Repository Pattern
```java
public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    // ...
}
```

### 2. Service Layer Pattern
```java
public class VendaServiceDTO {
    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    // Lógica de negócio isolada
}
```

### 3. DTO Pattern
```java
// Separação entre modelo interno e API externa
public class ClienteDTO { /* dados para exibição */ }
public class ClienteRequestDTO { /* dados para entrada */ }
```

### 4. Mapper Pattern
```java
public class ClienteMapper {
    public static ClienteDTO toDTO(Cliente entity) { /* conversão */ }
    public static Cliente toEntity(ClienteRequestDTO dto) { /* conversão */ }
}
```

### 5. MVC Pattern
- **Model**: Entidades + DTOs
- **View**: Menus e exibição no console
- **Controller**: Classes Controller

## Regras de Negócio Implementadas

### Cliente
- ✅ Documento obrigatório
- ✅ Email obrigatório e validado
- ✅ Não permite exclusão (histórico)
- ✅ Não permite documentos duplicados

### Produto
- ✅ Nome e etiqueta obrigatórios
- ✅ Valor deve ser maior que zero
- ✅ Não permite exclusão (histórico)

### Venda
- ✅ Sempre criada com status "Aberto"
- ✅ Data de criação automática
- ✅ Cliente obrigatório
- ✅ Cálculo automático do valor total

### Fluxo de Status
```
ABERTO → AGUARDANDO_PAGAMENTO → PAGO → FINALIZADO
```

### Operações por Status
- **ABERTO**: Adicionar/remover itens, alterar quantidades
- **AGUARDANDO_PAGAMENTO**: Apenas pagamento
- **PAGO**: Apenas entrega
- **FINALIZADO**: Apenas consulta

### Notificações
- ✅ Email ao finalizar pedido
- ✅ Email ao confirmar pagamento
- ✅ Email ao confirmar entrega

## Validações Implementadas

### Entrada de Dados
- ✅ Campos obrigatórios
- ✅ Formatos válidos (email, números)
- ✅ Valores positivos para quantidades e preços

### Regras de Negócio
- ✅ Status válidos para operações
- ✅ Existência de entidades relacionadas
- ✅ Integridade referencial

### Dados Consistentes
- ✅ IDs únicos auto-gerados
- ✅ Cálculos automáticos de totais
- ✅ Thread-safety com ConcurrentHashMap

## Testes Realizados

### Compilação ✅
```bash
javac com\adacommerce\*.java com\adacommerce\dto\*.java com\adacommerce\mapper\*.java # ... todos os pacotes
```

### Execução ✅
```bash
java com.adacommerce.AdaCommerceAppDTO
```

### Funcionalidades Testadas ✅
- Carregamento de dados de exemplo
- Menu principal funcional
- Interface de usuário responsiva

## Métricas do Projeto

### Estrutura
- **28 classes Java** criadas
- **7 pacotes** organizados
- **2 aplicações principais** (original + DTO)
- **2 arquivos de documentação**

### Linhas de Código (aproximado)
- **Entidades**: ~500 linhas
- **DTOs**: ~400 linhas  
- **Mappers**: ~200 linhas
- **Repositórios**: ~300 linhas
- **Serviços**: ~800 linhas
- **Controladores**: ~1200 linhas
- **Total**: ~3400 linhas

### Funcionalidades
- **3 entidades principais** (Cliente, Produto, Venda)
- **15 operações CRUD** implementadas
- **4 status de venda** com transições
- **3 tipos de notificação** por email
- **Validações completas** em todas as camadas

## Próximos Passos Sugeridos

### 1. Persistência
- Implementar JPA/Hibernate
- Conectar com PostgreSQL/MySQL
- Migrations de banco

### 2. API REST
- Spring Boot
- Controllers REST
- Swagger/OpenAPI

### 3. Testes
- JUnit 5
- Mockito
- Testes de integração

### 4. Interface Gráfica
- JavaFX
- Spring Boot + Thymeleaf
- React + Spring Boot

### 5. Segurança
- Spring Security
- JWT Authentication
- Role-based access

## Conclusão

O projeto Ada Commerce demonstra uma implementação completa de um sistema de e-commerce seguindo as melhores práticas de desenvolvimento Java, princípios SOLID e padrões de projeto. A implementação com DTOs e Mappers oferece uma base sólida para expansão e manutenção do sistema.
