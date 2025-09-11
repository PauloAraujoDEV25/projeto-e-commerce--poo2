# Exemplo de Uso - Ada Commerce

## Cenário: Venda Completa de Produtos

Este exemplo demonstra um fluxo completo de venda no sistema Ada Commerce.

### 1. Executar o Sistema
```bash
cd src/main/java
javac com/adacommerce/*.java com/adacommerce/*/*.java
java com.adacommerce.AdaCommerceAppDTO
```

### 2. Fluxo de Exemplo

#### Passo 1: Verificar Clientes (Menu 1 → Opção 2)
O sistema já carrega 3 clientes de exemplo:
- ID: 1 - João Silva (12345678901)
- ID: 2 - Maria Santos (98765432100)  
- ID: 3 - Pedro Costa (45678912300)

#### Passo 2: Verificar Produtos (Menu 2 → Opção 2)
O sistema já carrega 5 produtos de exemplo:
- ID: 1 - Notebook Dell - R$ 2.500,00
- ID: 2 - Mouse Gamer - R$ 150,00
- ID: 3 - Teclado Mecânico - R$ 300,00
- ID: 4 - Monitor 24" - R$ 800,00
- ID: 5 - Webcam HD - R$ 200,00

#### Passo 3: Criar Nova Venda (Menu 3 → Opção 1)
```
=== Criar Nova Venda ===
Clientes disponíveis:
ID: 1 - João Silva
ID: 2 - Maria Santos
ID: 3 - Pedro Costa
ID do cliente: 1

Venda criada com sucesso!
VendaDTO{id=1, clienteNome='João Silva', status='Aberto', valorTotal=0, quantidadeItens=0}
```

#### Passo 4: Adicionar Itens à Venda (Menu 3 → Opção 2)
```
=== Adicionar Item à Venda ===
ID da venda: 1

Produtos disponíveis:
ID: 1 - Notebook Dell - R$ 2500.00
ID: 2 - Mouse Gamer - R$ 150.00
...

ID do produto: 1
Quantidade: 1
Valor de venda: 2400.00

Item adicionado com sucesso!
```

Repetir para adicionar mais itens:
- Mouse Gamer (ID: 2): Quantidade 1, Valor R$ 150,00
- Teclado Mecânico (ID: 3): Quantidade 1, Valor R$ 280,00

#### Passo 5: Verificar Detalhes da Venda (Menu 3 → Opção 9)
```
=== Buscar Venda por ID ===
ID da venda: 1

Venda encontrada:
ID: 1
Cliente: João Silva
Status: Aberto
Valor Total: R$ 2830.00
Itens:
  - Notebook Dell (Qtd: 1, Valor: R$ 2400.00, Total: R$ 2400.00)
  - Mouse Gamer (Qtd: 1, Valor: R$ 150.00, Total: R$ 150.00)
  - Teclado Mecânico (Qtd: 1, Valor: R$ 280.00, Total: R$ 280.00)
```

#### Passo 6: Finalizar Pedido (Menu 3 → Opção 5)
```
=== Finalizar Pedido ===
ID da venda: 1

Pedido finalizado com sucesso!
Status: Aguardando pagamento
E-mail de notificação enviado ao cliente.

=== EMAIL ENVIADO ===
Para: joao.silva@email.com
Assunto: Pedido Finalizado - Aguardando Pagamento
Mensagem: Olá João Silva,
Seu pedido #1 foi finalizado e está aguardando pagamento.
Valor total: R$ 2830.00
==================
```

#### Passo 7: Realizar Pagamento (Menu 3 → Opção 6)
```
=== Realizar Pagamento ===
ID da venda: 1

Pagamento realizado com sucesso!
Status: Pago
E-mail de confirmação enviado ao cliente.

=== EMAIL ENVIADO ===
Para: joao.silva@email.com
Assunto: Pagamento Confirmado
Mensagem: Olá João Silva,
O pagamento do seu pedido #1 foi confirmado!
Valor pago: R$ 2830.00
Seu pedido será preparado para entrega.
==================
```

#### Passo 8: Entregar Pedido (Menu 3 → Opção 7)
```
=== Entregar Pedido ===
ID da venda: 1

Pedido entregue com sucesso!
Status: Finalizado
E-mail de confirmação de entrega enviado ao cliente.

=== EMAIL ENVIADO ===
Para: joao.silva@email.com
Assunto: Pedido Entregue
Mensagem: Olá João Silva,
Seu pedido #1 foi entregue com sucesso!
Obrigado por comprar conosco!
==================
```

### 3. Consultas Úteis

#### Listar Vendas por Status (Menu 3 → Opção 10)
```
=== Buscar Vendas por Status ===
1. Aberto
2. Aguardando Pagamento
3. Pago
4. Finalizado
Escolha o status: 4

Vendas com status Finalizado:
ID: 1
Cliente: João Silva
Data: 2025-09-10T10:30:00
Valor Total: R$ 2830.00
```

#### Buscar Cliente por Documento (Menu 1 → Opção 4)
```
=== Buscar Cliente por Documento ===
Documento: 12345678901

Cliente encontrado:
ClienteDTO{id=1, nome='João Silva', documento='12345678901', email='joao.silva@email.com'}
```

### 4. Funcionalidades Avançadas

#### Alterar Quantidade de Item
Durante uma venda com status "Aberto", é possível alterar a quantidade de qualquer item.

#### Remover Item
Items podem ser removidos de vendas com status "Aberto".

#### Cadastrar Novos Clientes/Produtos
O sistema permite cadastrar novos clientes e produtos a qualquer momento.

#### Atualizar Informações
Clientes e produtos podem ter suas informações atualizadas (exceto exclusão para manter histórico).

### 5. Validações do Sistema

- ✅ Cliente deve ter documento obrigatório
- ✅ Produtos devem ter valor > 0
- ✅ Vendas só podem ser modificadas no status "Aberto"
- ✅ Pagamento só pode ser feito em vendas "Aguardando pagamento"
- ✅ Entrega só pode ser feita em vendas "Pagas"
- ✅ Pedido deve ter pelo menos 1 item para ser finalizado
- ✅ Valor total deve ser > 0 para finalização
