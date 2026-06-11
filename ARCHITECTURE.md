# 🏗️ Arquitetura de Software Robusto

## Princípios Implementados

### 1. **Desacoplamento do Banco de Dados**
A lógica de negócio está completamente separada do banco de dados através do **Repository Pattern**.

```
Controller → Service → Repository → Database
```

**Benefícios:**
- Fácil trocar de BD sem alterar a lógica
- Facilita testes unitários
- Código mais limpo e organizado

### 2. **Tratamento de Erros Robusto**

#### Backend
- **Classe `AppError`**: Erros customizados com statusCode
- **Middleware `errorHandler`**: Centraliza tratamento de erros
- **`asyncHandler`**: Wrapper para capturar erros em rotas async
- **Try/Catch estratégicos**: Em cada camada da aplicação

#### Frontend
- **Classe `ApiError`**: Erros da API com tratamento específico
- **Validação de dados**: Antes de enviar ao backend
- **Timeout de requisição**: Previne travamentos
- **UI feedback**: Alertas visuais para o usuário

### 3. **Arquitetura em Camadas**

#### Backend
```
├── middleware/
│   └── errorHandler.js       # Tratamento centralizado de erros
├── config/
│   └── database.js           # Isolamento da BD
├── repository/
│   └── userRepository.js     # Acesso aos dados
├── service/
│   └── userService.js        # Lógica de negócio
├── controller/
│   └── userController.js     # Requisições HTTP
├── routes/
│   └── userRoutes.js         # Mapeamento de endpoints
└── server.js                 # Configuração principal
```

#### Frontend
```
├── index.html                # Estrutura HTML
├── styles.css                # Estilos CSS
└── app.js                    # Lógica JavaScript
  ├── ApiClient               # Cliente HTTP
  ├── ApiError                # Tratamento de erros
  └── UIManager               # Gerenciamento de UI
```

## Fluxo de Dados

### Criação de Usuário (Sucesso)
```
1. User preenche formulário
   ↓
2. Frontend valida dados
   ↓
3. POST /api/users com JSON
   ↓
4. Controller valida entrada
   ↓
5. Service aplica regras de negócio
   ↓
6. Repository insere no BD
   ↓
7. JSON response com sucesso
   ↓
8. UI atualiza e mostra mensagem
```

### Fluxo com Erro
```
1. Erro em qualquer camada
   ↓
2. AppError é lançado
   ↓
3. Try/Catch captura
   ↓
4. errorHandler middleware trata
   ↓
5. JSON com error details
   ↓
6. Frontend recebe e mostra ao usuário
```

## Tratamento de Erros por Camada

### Backend

#### Repository (Acesso a BD)
```javascript
async findById(id) {
  try {
    // Validação
    if (!id || isNaN(id)) {
      throw new AppError('ID inválido', 400);
    }
    // Acesso BD
    return await db.query(...);
  } catch (error) {
    // Erro específico do BD
    throw new AppError('Falha ao buscar usuário', 500);
  }
}
```

#### Service (Lógica de Negócio)
```javascript
async createUser(userData) {
  try {
    // Lógica
    if (isDuplicate) {
      throw new AppError('Email duplicado', 409);
    }
    return await repository.create(userData);
  } catch (error) {
    if (error instanceof AppError) throw error;
    throw new AppError('Erro ao criar', 500);
  }
}
```

#### Controller (HTTP)
```javascript
create = asyncHandler(async (req, res) => {
  const { name, email } = req.body;
  if (!name || !email) {
    throw new AppError('Campos obrigatórios', 400);
  }
  const user = await service.createUser(...);
  res.status(201).json({ success: true, data: user });
});
```

### Frontend

#### Validação
```javascript
if (!this.isValidEmail(email)) {
  throw new ApiError('Email inválido', 400);
}
```

#### Requisição com Timeout
```javascript
const controller = new AbortController();
const timeoutId = setTimeout(() => controller.abort(), 5000);
const response = await fetch(url, { signal: controller.signal });
```

#### Tratamento de Status HTTP
```javascript
if (!response.ok) {
  const errorData = await response.json();
  throw new ApiError(errorData.error.message, response.status);
}
```

## Checklist de Robustez

### Backend
- [x] Validação de entrada em controller
- [x] Erro customizado com statusCode
- [x] Middleware centralizado de erro
- [x] Try/catch em operações assíncronas
- [x] Logging de erros
- [x] Graceful shutdown
- [x] Repository pattern para BD
- [x] Service layer com lógica

### Frontend
- [x] Validação de dados antes de envio
- [x] Timeout em requisições
- [x] Tratamento de erros HTTP
- [x] Feedback visual ao usuário
- [x] Desacoplamento de UI/API
- [x] Escape de HTML para XSS
- [x] Classe de erro customizada

## Como Usar

### 1. Instalar dependências
```bash
npm install express
```

### 2. Configurar banco de dados
Edite `backend/config/database.js` com suas credenciais

### 3. Iniciar servidor
```bash
node backend/server.js
```

### 4. Acessar frontend
```
http://localhost:3000
```

## Teste de Robustez

### Teste 1: Erro de Validação
```bash
curl -X POST http://localhost:3000/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "", "email": "invalido"}'
# Response: 400 - "Email inválido"
```

### Teste 2: Recurso Não Encontrado
```bash
curl http://localhost:3000/api/users/9999
# Response: 404 - "Usuário não encontrado"
```

### Teste 3: Timeout (no frontend)
- Parar o servidor
- Tentar criar usuário
- Aguardar 5 segundos
- Resultado: "Requisição expirou"

## Benefícios da Arquitetura

1. **Manutenibilidade**: Código organizado e fácil de entender
2. **Testabilidade**: Cada camada pode ser testada independentemente
3. **Escalabilidade**: Fácil adicionar novas funcionalidades
4. **Robustez**: Erros tratados em todos os níveis
5. **Flexibilidade**: Trocar BD sem afetar lógica
6. **Segurança**: Validação em múltiplas camadas

## Próximos Passos

- [ ] Adicionar autenticação JWT
- [ ] Implementar testes unitários
- [ ] Adicionar rate limiting
- [ ] Implementar cache
- [ ] Adicionar documentação Swagger
- [ ] Configurar CI/CD
- [ ] Monitoramento de erros (Sentry)
