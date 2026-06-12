/**
 * Sistema de Inclusão Digital em Fortaleza - Frontend
 * API Client com tratamento robusto de erros
 */

// ============================================
// CLASSE DE ERRO CUSTOMIZADA
// ============================================

class ApiError extends Error {
    constructor(message, statusCode = null, details = null) {
        super(message);
        this.name = 'ApiError';
        this.statusCode = statusCode;
        this.details = details;
        this.timestamp = new Date();
    }

    toString() {
        return `${this.name}: ${this.message} (Status: ${this.statusCode})`;
    }
}

// ============================================
// CLIENTE API
// ============================================

class ApiClient {
    constructor(baseUrl = 'http://localhost:8080/api', timeout = 5000) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        this.headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };
    }

    /**
     * Valida se o email é válido
     */
    isValidEmail(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }

    /**
     * Realiza requisição HTTP com timeout e tratamento de erro
     */
    async fetch(url, options = {}) {
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), this.timeout);

        try {
            const response = await fetch(url, {
                ...options,
                signal: controller.signal
            });

            clearTimeout(timeoutId);

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new ApiError(
                    errorData.message || `HTTP ${response.status}`,
                    response.status,
                    errorData
                );
            }

            return await response.json();
        } catch (error) {
            clearTimeout(timeoutId);

            if (error instanceof ApiError) {
                throw error;
            }

            if (error.name === 'AbortError') {
                throw new ApiError('Requisição expirou (timeout)', null, { timeout: true });
            }

            throw new ApiError(
                error.message || 'Erro ao conectar com o servidor',
                null,
                { originalError: error }
            );
        }
    }

    /**
     * GET - Buscar dados
     */
    async get(endpoint) {
        return this.fetch(`${this.baseUrl}${endpoint}`, {
            method: 'GET',
            headers: this.headers
        });
    }

    /**
     * POST - Criar recurso
     */
    async post(endpoint, data) {
        return this.fetch(`${this.baseUrl}${endpoint}`, {
            method: 'POST',
            headers: this.headers,
            body: JSON.stringify(data)
        });
    }

    /**
     * PUT - Atualizar recurso
     */
    async put(endpoint, data) {
        return this.fetch(`${this.baseUrl}${endpoint}`, {
            method: 'PUT',
            headers: this.headers,
            body: JSON.stringify(data)
        });
    }

    /**
     * DELETE - Remover recurso
     */
    async delete(endpoint) {
        return this.fetch(`${this.baseUrl}${endpoint}`, {
            method: 'DELETE',
            headers: this.headers
        });
    }
}

// ============================================
// GERENCIADOR DE UI
// ============================================

class UIManager {
    static showAlert(message, type = 'info', duration = 4000) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.innerHTML = this.escapeHTML(message);
        alertDiv.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 15px 20px;
            border-radius: 5px;
            background: ${this.getColorByType(type)};
            color: white;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            z-index: 10000;
            animation: slideIn 0.3s ease;
        `;

        document.body.appendChild(alertDiv);

        setTimeout(() => {
            alertDiv.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => alertDiv.remove(), 300);
        }, duration);
    }

    static getColorByType(type) {
        const colors = {
            'success': '#4CAF50',
            'error': '#f44336',
            'warning': '#ff9800',
            'info': '#2196F3'
        };
        return colors[type] || colors['info'];
    }

    /**
     * Escapa HTML para prevenir XSS
     */
    static escapeHTML(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    static showLoading(message = 'Carregando...') {
        const loader = document.createElement('div');
        loader.id = 'loading-spinner';
        loader.innerHTML = `
            <div style="text-align: center; padding: 20px;">
                <div class="spinner"></div>
                <p>${this.escapeHTML(message)}</p>
            </div>
        `;
        document.body.appendChild(loader);
    }

    static hideLoading() {
        const loader = document.getElementById('loading-spinner');
        if (loader) loader.remove();
    }

    static createCard(data) {
        const card = document.createElement('div');
        card.className = 'card';
        card.innerHTML = `
            <h3>${this.escapeHTML(data.titulo)}</h3>
            <p>${this.escapeHTML(data.descricao || '')}</p>
            <p><strong>Autor:</strong> ${this.escapeHTML(data.autor || '')}</p>
            <p><strong>Visualizações:</strong> ${data.visualizacoes || 0}</p>
            <span class="badge">${this.escapeHTML(data.tipo || 'Conteúdo')}</span>
        `;
        return card;
    }
}

// ============================================
// GERENCIADOR DO SISTEMA
// ============================================

class SistemaInclusaoDigital {
    constructor() {
        this.apiClient = new ApiClient();
        this.conteudos = [];
        this.usuarios = [];
        this.inicializar();
    }

    /**
     * Inicializa o sistema
     */
    inicializar() {
        this.setupEventListeners();
        console.log('✓ Sistema inicializado com sucesso');
    }

    /**
     * Configura listeners de eventos
     */
    setupEventListeners() {
        // Buscar requisitos funcionais
        const btnReqFunc = document.getElementById('btn-requisitos-funcionais');
        if (btnReqFunc) {
            btnReqFunc.addEventListener('click', () => this.buscarRequisitosFuncionais());
        }

        // Buscar requisitos não-funcionais
        const btnReqNaoFunc = document.getElementById('btn-requisitos-nao-funcionais');
        if (btnReqNaoFunc) {
            btnReqNaoFunc.addEventListener('click', () => this.buscarRequisitosNaoFuncionais());
        }

        // Buscar módulos
        const btnModulos = document.getElementById('btn-modulos');
        if (btnModulos) {
            btnModulos.addEventListener('click', () => this.buscarModulos());
        }

        // Formulário de conteúdo
        const formConteudo = document.getElementById('form-conteudo');
        if (formConteudo) {
            formConteudo.addEventListener('submit', (e) => this.handleFormConteudo(e));
        }

        // Formulário de usuário
        const formUsuario = document.getElementById('form-usuario');
        if (formUsuario) {
            formUsuario.addEventListener('submit', (e) => this.handleFormUsuario(e));
        }
    }

    /**
     * Busca requisitos funcionais
     */
    async buscarRequisitosFuncionais() {
        try {
            UIManager.showLoading('Buscando requisitos funcionais...');
            const data = await this.apiClient.get('/requisitos/funcionais');
            UIManager.hideLoading();

            this.exibirRequisitos(data, 'Requisitos Funcionais');
            UIManager.showAlert('✓ Requisitos funcionais carregados', 'success');
        } catch (error) {
            UIManager.hideLoading();
            this.handleError(error);
        }
    }

    /**
     * Busca requisitos não-funcionais
     */
    async buscarRequisitosNaoFuncionais() {
        try {
            UIManager.showLoading('Buscando requisitos não-funcionais...');
            const data = await this.apiClient.get('/requisitos/nao-funcionais');
            UIManager.hideLoading();

            this.exibirRequisitos(data, 'Requisitos Não-Funcionais');
            UIManager.showAlert('✓ Requisitos não-funcionais carregados', 'success');
        } catch (error) {
            UIManager.hideLoading();
            this.handleError(error);
        }
    }

    /**
     * Busca módulos
     */
    async buscarModulos() {
        try {
            UIManager.showLoading('Buscando módulos...');
            const data = await this.apiClient.get('/modulos');
            UIManager.hideLoading();

            this.exibirModulos(data);
            UIManager.showAlert('✓ Módulos carregados', 'success');
        } catch (error) {
            UIManager.hideLoading();
            this.handleError(error);
        }
    }

    /**
     * Busca conteúdos
     */
    async buscarConteudos() {
        try {
            UIManager.showLoading('Buscando conteúdos...');
            const data = await this.apiClient.get('/conteudos');
            UIManager.hideLoading();

            this.conteudos = data;
            this.exibirConteudos(data);
            UIManager.showAlert('✓ Conteúdos carregados', 'success');
        } catch (error) {
            UIManager.hideLoading();
            this.handleError(error);
        }
    }

    /**
     * Cria novo conteúdo
     */
    async criarConteudo(dados) {
        try {
            // Validação
            if (!dados.titulo || !dados.autor) {
                throw new ApiError('Título e autor são obrigatórios', 400);
            }

            UIManager.showLoading('Criando conteúdo...');
            const resultado = await this.apiClient.post('/conteudos', dados);
            UIManager.hideLoading();

            this.conteudos.push(resultado);
            UIManager.showAlert('✓ Conteúdo criado com sucesso', 'success');
            return resultado;
        } catch (error) {
            UIManager.hideLoading();
            this.handleError(error);
            throw error;
        }
    }

    /**
     * Cria novo usuário
     */
    async criarUsuario(dados) {
        try {
            // Validação
            if (!dados.nome || dados.nome.length < 3) {
                throw new ApiError('Nome deve ter no mínimo 3 caracteres', 400);
            }

            if (!this.apiClient.isValidEmail(dados.email)) {
                throw new ApiError('Email inválido', 400);
            }

            UIManager.showLoading('Criando usuário...');
            const resultado = await this.apiClient.post('/usuarios', dados);
            UIManager.hideLoading();

            this.usuarios.push(resultado);
            UIManager.showAlert('✓ Usuário criado com sucesso', 'success');
            return resultado;
        } catch (error) {
            UIManager.hideLoading();
            this.handleError(error);
            throw error;
        }
    }

    /**
     * Handler para formulário de conteúdo
     */
    async handleFormConteudo(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const dados = Object.fromEntries(formData);

        try {
            await this.criarConteudo(dados);
            event.target.reset();
        } catch (error) {
            console.error('Erro ao criar conteúdo:', error);
        }
    }

    /**
     * Handler para formulário de usuário
     */
    async handleFormUsuario(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const dados = Object.fromEntries(formData);

        try {
            await this.criarUsuario(dados);
            event.target.reset();
        } catch (error) {
            console.error('Erro ao criar usuário:', error);
        }
    }

    /**
     * Exibe requisitos
     */
    exibirRequisitos(data, titulo) {
        const container = document.getElementById('result-container');
        if (!container) return;

        let html = `<h3>${UIManager.escapeHTML(titulo)}</h3>`;
        html += '<div class="requisitos-list">';

        data.forEach(req => {
            html += `
                <div class="req-item">
                    <span class="req-id">${UIManager.escapeHTML(req.id)}</span>
                    <span class="req-text">${UIManager.escapeHTML(req.descricao)}</span>
                </div>
            `;
        });

        html += '</div>';
        container.innerHTML = html;
    }

    /**
     * Exibe módulos
     */
    exibirModulos(data) {
        const container = document.getElementById('result-container');
        if (!container) return;

        let html = '<h3>Módulos do Sistema</h3>';
        html += '<div class="modulos-container">';

        data.forEach(modulo => {
            html += `
                <div class="modulo-card">
                    <h4>${UIManager.escapeHTML(modulo.nome)}</h4>
                    <p><strong>Descrição:</strong> ${UIManager.escapeHTML(modulo.descricao)}</p>
                    <p><strong>Responsável:</strong> ${UIManager.escapeHTML(modulo.responsavel)}</p>
                </div>
            `;
        });

        html += '</div>';
        container.innerHTML = html;
    }

    /**
     * Exibe conteúdos
     */
    exibirConteudos(data) {
        const container = document.getElementById('result-container');
        if (!container) return;

        container.innerHTML = '';
        data.forEach(conteudo => {
            container.appendChild(UIManager.createCard(conteudo));
        });
    }

    /**
     * Trata erros
     */
    handleError(error) {
        console.error('Erro:', error);

        let mensagem = 'Ocorreu um erro ao processar a requisição';

        if (error instanceof ApiError) {
            if (error.details && error.details.timeout) {
                mensagem = '⏱️ Requisição expirou - servidor pode estar indisponível';
            } else if (error.statusCode === 400) {
                mensagem = '❌ Erro de validação: ' + error.message;
            } else if (error.statusCode === 404) {
                mensagem = '🔍 Recurso não encontrado';
            } else if (error.statusCode === 500) {
                mensagem = '⚠️ Erro no servidor: ' + error.message;
            } else {
                mensagem = error.message;
            }
        }

        UIManager.showAlert(mensagem, 'error');
    }

    /**
     * Obtém estatísticas
     */
    async obterEstatisticas() {
        try {
            const response = await this.apiClient.get('/estatisticas');
            return response;
        } catch (error) {
            console.error('Erro ao obter estatísticas:', error);
            return null;
        }
    }
}

// ============================================
// INICIALIZAÇÃO
// ============================================

document.addEventListener('DOMContentLoaded', () => {
    window.sistema = new SistemaInclusaoDigital();
    console.log('✓ Sistema de Inclusão Digital iniciado');
});

// ============================================
// ESTILOS CSS (pode ser movido para arquivo externo)
// ============================================

const styles = `
    .alert {
        padding: 15px 20px;
        border-radius: 5px;
        margin: 10px 0;
    }

    .alert-success {
        background: #4CAF50;
        color: white;
    }

    .alert-error {
        background: #f44336;
        color: white;
    }

    .alert-warning {
        background: #ff9800;
        color: white;
    }

    .alert-info {
        background: #2196F3;
        color: white;
    }

    .spinner {
        border: 4px solid #f3f3f3;
        border-top: 4px solid #667eea;
        border-radius: 50%;
        width: 40px;
        height: 40px;
        animation: spin 1s linear infinite;
        margin: 0 auto 10px;
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }

    .requisitos-list {
        margin-top: 20px;
    }

    .req-item {
        padding: 15px;
        margin-bottom: 10px;
        background: #f5f7fa;
        border-left: 4px solid #667eea;
        border-radius: 5px;
    }

    .req-id {
        font-weight: bold;
        color: #667eea;
        display: block;
        margin-bottom: 5px;
    }

    .req-text {
        color: #333;
    }

    .modulos-container {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 20px;
        margin-top: 20px;
    }

    .modulo-card {
        background: white;
        padding: 20px;
        border-radius: 10px;
        border-left: 4px solid #667eea;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .modulo-card h4 {
        color: #667eea;
        margin-bottom: 10px;
    }

    .modulo-card p {
        color: #666;
        font-size: 0.95em;
        margin-bottom: 8px;
    }
`;

// Adiciona estilos ao documento
const styleElement = document.createElement('style');
styleElement.innerHTML = styles;
document.head.appendChild(styleElement);
