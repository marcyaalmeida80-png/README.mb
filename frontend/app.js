/**
 * API Client com Tratamento Robusto de Erros
 * Desacoplado e reutilizável
 */

class ApiClient {
  constructor(baseURL = 'http://localhost:3000/api') {
    this.baseURL = baseURL;
    this.timeout = 5000; // 5 segundos
  }

  /**
   * Fazer requisição HTTP com tratamento de erro
   */
  async request(endpoint, options = {}) {
    try {
      const url = `${this.baseURL}${endpoint}`;
      const config = {
        method: options.method || 'GET',
        headers: {
          'Content-Type': 'application/json',
          ...options.headers
        },
        ...options
      };

      if (options.body) {
        config.body = JSON.stringify(options.body);
      }

      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), this.timeout);

      const response = await fetch(url, { ...config, signal: controller.signal });
      clearTimeout(timeoutId);

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new ApiError(
          errorData.error?.message || `HTTP ${response.status}`,
          response.status,
          errorData
        );
      }

      return await response.json();
    } catch (error) {
      if (error instanceof ApiError) {
        throw error;
      }
      if (error.name === 'AbortError') {
        throw new ApiError('Requisição expirou', 408);
      }
      throw new ApiError(error.message || 'Erro desconhecido', 0, error);
    }
  }

  get(endpoint) {
    return this.request(endpoint, { method: 'GET' });
  }

  post(endpoint, body) {
    return this.request(endpoint, { method: 'POST', body });
  }

  put(endpoint, body) {
    return this.request(endpoint, { method: 'PUT', body });
  }

  delete(endpoint) {
    return this.request(endpoint, { method: 'DELETE' });
  }
}

/**
 * Classe de Erro Customizada
 */
class ApiError extends Error {
  constructor(message, statusCode = 0, data = null) {
    super(message);
    this.name = 'ApiError';
    this.statusCode = statusCode;
    this.data = data;
  }
}

/**
 * Gerenciador de UI
 */
class UIManager {
  constructor() {
    this.alertDiv = document.getElementById('alert');
    this.usersList = document.getElementById('usersList');
    this.createUserForm = document.getElementById('createUserForm');
    this.setupEventListeners();
  }

  setupEventListeners() {
    this.createUserForm.addEventListener('submit', (e) => this.handleCreateUser(e));
  }

  async handleCreateUser(e) {
    e.preventDefault();
    
    try {
      this.showLoading('Criando usuário...');
      
      const name = document.getElementById('name').value.trim();
      const email = document.getElementById('email').value.trim();
      const age = document.getElementById('age').value;

      // Validação no frontend
      if (!name || !email) {
        throw new ApiError('Nome e email são obrigatórios', 400);
      }

      if (!this.isValidEmail(email)) {
        throw new ApiError('Email inválido', 400);
      }

      const userData = { name, email };
      if (age) userData.age = parseInt(age);

      await apiClient.post('/users', userData);

      this.showAlert('✅ Usuário criado com sucesso!', 'success');
      this.createUserForm.reset();
      await this.loadUsers();
    } catch (error) {
      this.handleError(error);
    }
  }

  async loadUsers() {
    try {
      this.showLoading('Carregando usuários...');
      const response = await apiClient.get('/users');
      
      if (response.success && response.data) {
        this.renderUsers(response.data);
        this.showAlert(`📋 ${response.count} usuário(s) carregado(s)`, 'info');
      }
    } catch (error) {
      this.handleError(error);
    }
  }

  renderUsers(users) {
    if (!users || users.length === 0) {
      this.usersList.innerHTML = '<div class="empty-state">Nenhum usuário encontrado</div>';
      return;
    }

    this.usersList.innerHTML = users.map(user => `
      <div class="user-card">
        <div class="user-info">
          <h3>${this.escapeHTML(user.name)}</h3>
          <p>📧 ${this.escapeHTML(user.email)}</p>
          ${user.age ? `<p>🎂 ${user.age} anos</p>` : ''}
          <p style="font-size: 0.8em; color: #999;">ID: ${user.id}</p>
        </div>
        <div class="user-actions">
          <button onclick="ui.editUser(${user.id})">✏️ Editar</button>
          <button onclick="ui.deleteUser(${user.id})" style="background: #e74c3c;">🗑️ Deletar</button>
        </div>
      </div>
    `).join('');
  }

  async editUser(id) {
    try {
      const newName = prompt('Novo nome:');
      if (!newName) return;

      await apiClient.put(`/users/${id}`, { name: newName.trim() });
      this.showAlert('✅ Usuário atualizado!', 'success');
      await this.loadUsers();
    } catch (error) {
      this.handleError(error);
    }
  }

  async deleteUser(id) {
    if (!confirm('Tem certeza que deseja deletar este usuário?')) return;

    try {
      await apiClient.delete(`/users/${id}`);
      this.showAlert('✅ Usuário deletado!', 'success');
      await this.loadUsers();
    } catch (error) {
      this.handleError(error);
    }
  }

  isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }

  escapeHTML(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
  }

  showAlert(message, type = 'info') {
    this.alertDiv.textContent = message;
    this.alertDiv.className = `alert show ${type}`;
    
    setTimeout(() => {
      this.alertDiv.classList.remove('show');
    }, 3000);
  }

  showLoading(message = 'Carregando...') {
    this.usersList.innerHTML = `<div class="loading">${message}</div>`;
  }

  handleError(error) {
    console.error('Erro:', error);
    
    if (error instanceof ApiError) {
      if (error.statusCode === 404) {
        this.showAlert('❌ Recurso não encontrado', 'error');
      } else if (error.statusCode === 400) {
        this.showAlert(`❌ Erro: ${error.message}`, 'error');
      } else if (error.statusCode === 409) {
        this.showAlert('❌ Email já está cadastrado', 'error');
      } else if (error.statusCode === 408) {
        this.showAlert('❌ Requisição expirou. Tente novamente.', 'error');
      } else {
        this.showAlert(`❌ Erro: ${error.message}`, 'error');
      }
    } else {
      this.showAlert('❌ Erro desconhecido. Verifique a conexão.', 'error');
    }
    
    this.usersList.innerHTML = '<div class="empty-state">Erro ao carregar dados</div>';
  }
}

// Instâncias globais
const apiClient = new ApiClient();
const ui = new UIManager();

// Carregar usuários ao iniciar
document.addEventListener('DOMContentLoaded', async () => {
  console.log('✅ Aplicação iniciada');
  await ui.loadUsers();
});

// Função global para buscar por ID
async function searchUser() {
  try {
    const id = document.getElementById('searchId').value.trim();
    
    if (!id) {
      ui.showAlert('❌ Digite um ID válido', 'error');
      return;
    }

    ui.showLoading('Buscando usuário...');
    const response = await apiClient.get(`/users/${id}`);
    
    if (response.success) {
      ui.renderUsers([response.data]);
      ui.showAlert('✅ Usuário encontrado!', 'success');
    }
  } catch (error) {
    ui.handleError(error);
  }
}

// Função global para carregar todos
async function loadAllUsers() {
  await ui.loadUsers();
}
