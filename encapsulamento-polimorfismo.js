// ============================================
// Encapsulamento e Polimorfismo
// Projeto: Inclusão Digital em Fortaleza
// ============================================

/**
 * ========== ENCAPSULAMENTO ==========
 * Encapsulamento: Proteção de dados usando propriedades privadas (#)
 * Controle de acesso através de getters e setters
 */

/**
 * Classe com Encapsulamento
 * Protege dados sensíveis e valida antes de alterar
 */
class Usuario {
  // Propriedades privadas (não acessíveis diretamente de fora)
  #id;
  #nome;
  #email;
  #nivelAlfabetizacao;
  #dataCadastro;

  constructor(id, nome, email) {
    this.#id = id;
    this.#nome = nome;
    this.#email = email;
    this.#nivelAlfabetizacao = 0; // 0 a 100%
    this.#dataCadastro = new Date();
  }

  // Getters - Apenas leitura (protegida)
  get id() {
    return this.#id;
  }

  get nome() {
    return this.#nome;
  }

  get email() {
    return this.#email;
  }

  get nivelAlfabetizacao() {
    return this.#nivelAlfabetizacao;
  }

  get dataCadastro() {
    return this.#dataCadastro;
  }

  // Setters - Validação antes de alterar
  set nome(novoNome) {
    if (novoNome && novoNome.length >= 3) {
      this.#nome = novoNome;
      console.log(`✓ Nome atualizado para: ${novoNome}`);
    } else {
      console.log(`✗ Erro: Nome deve ter no mínimo 3 caracteres`);
    }
  }

  set email(novoEmail) {
    if (this.#validarEmail(novoEmail)) {
      this.#email = novoEmail;
      console.log(`✓ Email atualizado para: ${novoEmail}`);
    } else {
      console.log(`✗ Erro: Email inválido`);
    }
  }

  set nivelAlfabetizacao(nivel) {
    if (nivel >= 0 && nivel <= 100) {
      this.#nivelAlfabetizacao = nivel;
      console.log(`✓ Nível de alfabetização atualizado para: ${nivel}%`);
    } else {
      console.log(`✗ Erro: Nível deve estar entre 0 e 100`);
    }
  }

  // Método privado (só pode ser usado internamente)
  #validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  }

  // Método público
  exibirPerfil() {
    console.log(`
      ========== PERFIL DO USUÁRIO ==========
      ID: ${this.#id}
      Nome: ${this.#nome}
      Email: ${this.#email}
      Nível de Alfabetização: ${this.#nivelAlfabetizacao}%
      Data de Cadastro: ${this.#dataCadastro.toLocaleDateString('pt-BR')}
      ========================================
    `);
  }
}

// ============================================
// POLIMORFISMO
// ============================================

/**
 * Classe Base: Conteudo
 * Define a interface para diferentes tipos de conteúdo
 */
class Conteudo {
  constructor(titulo, descricao, autor) {
    this.titulo = titulo;
    this.descricao = descricao;
    this.autor = autor;
    this.dataCriacao = new Date();
    this.visualizacoes = 0;
  }

  // Método que será sobrescrito pelas classes filhas (Polimorfismo)
  exibir() {
    throw new Error("Método exibir() deve ser implementado pela classe filha");
  }

  // Método que será sobrescrito pelas classes filhas
  obterDuracao() {
    throw new Error("Método obterDuracao() deve ser implementado pela classe filha");
  }

  // Método comum a todas as classes
  incrementarVisualizacoes() {
    this.visualizacoes++;
  }

  obterInformacoes() {
    return `
      Título: ${this.titulo}
      Autor: ${this.autor}
      Visualizações: ${this.visualizacoes}
      Data de Criação: ${this.dataCriacao.toLocaleDateString('pt-BR')}
    `;
  }
}

/**
 * Classe Filha 1: Video
 * Implementação específica para vídeos
 */
class Video extends Conteudo {
  constructor(titulo, descricao, autor, duracao, resolucao) {
    super(titulo, descricao, autor);
    this.duracao = duracao; // em minutos
    this.resolucao = resolucao; // 720p, 1080p, 4K
  }

  // Polimorfismo: Sobrescreve o método exibir()
  exibir() {
    console.log(`
      📹 === VÍDEO ===
      ${this.obterInformacoes()}
      Duração: ${this.duracao} minutos
      Resolução: ${this.resolucao}
      ================
    `);
  }

  // Polimorfismo: Sobrescreve o método obterDuracao()
  obterDuracao() {
    return `${this.duracao} minutos`;
  }

  // Método exclusivo desta classe
  fazerDownload() {
    return `Baixando vídeo "${this.titulo}" em ${this.resolucao}...`;
  }
}

/**
 * Classe Filha 2: Artigo
 * Implementação específica para artigos
 */
class Artigo extends Conteudo {
  constructor(titulo, descricao, autor, quantidadePalavras, categoria) {
    super(titulo, descricao, autor);
    this.quantidadePalavras = quantidadePalavras;
    this.categoria = categoria;
  }

  // Polimorfismo: Sobrescreve o método exibir()
  exibir() {
    console.log(`
      📰 === ARTIGO ===
      ${this.obterInformacoes()}
      Quantidade de Palavras: ${this.quantidadePalavras}
      Categoria: ${this.categoria}
      ================
    `);
  }

  // Polimorfismo: Sobrescreve o método obterDuracao()
  obterDuracao() {
    // Tempo de leitura estimado: 200 palavras por minuto
    const tempoLeitura = Math.ceil(this.quantidadePalavras / 200);
    return `${tempoLeitura} minutos de leitura`;
  }

  // Método exclusivo desta classe
  obterResumo() {
    const resumo = this.descricao.substring(0, 100) + "...";
    return resumo;
  }
}

/**
 * Classe Filha 3: Tutorial
 * Implementação específica para tutoriais
 */
class Tutorial extends Conteudo {
  constructor(titulo, descricao, autor, numeroEtapas, nivelDificuldade) {
    super(titulo, descricao, autor);
    this.numeroEtapas = numeroEtapas;
    this.nivelDificuldade = nivelDificuldade; // Iniciante, Intermediário, Avançado
    this.etapasConcluidas = 0;
  }

  // Polimorfismo: Sobrescreve o método exibir()
  exibir() {
    console.log(`
      🎓 === TUTORIAL ===
      ${this.obterInformacoes()}
      Número de Etapas: ${this.numeroEtapas}
      Nível de Dificuldade: ${this.nivelDificuldade}
      Progresso: ${this.etapasConcluidas}/${this.numeroEtapas}
      ===================
    `);
  }

  // Polimorfismo: Sobrescreve o método obterDuracao()
  obterDuracao() {
    const tempoMedio = this.numeroEtapas * 5; // 5 minutos por etapa
    return `${tempoMedio} minutos (aproximado)`;
  }

  // Método exclusivo desta classe
  completarEtapa() {
    if (this.etapasConcluidas < this.numeroEtapas) {
      this.etapasConcluidas++;
      const percentual = (this.etapasConcluidas / this.numeroEtapas) * 100;
      return `✓ Etapa ${this.etapasConcluidas}/${this.numeroEtapas} concluída (${percentual.toFixed(0)}%)`;
    }
    return "✗ Todas as etapas já foram concluídas";
  }
}

// ============================================
// DEMONSTRAÇÃO DE POLIMORFISMO
// ============================================

/**
 * Função que demonstra Polimorfismo
 * Mesmo método, comportamentos diferentes para cada tipo
 */
function processarConteudo(conteudo) {
  console.log("\n--- Processando Conteúdo ---");
  
  // Cada classe filha implementa exibir() de forma diferente
  conteudo.exibir();
  
  // Cada classe filha implementa obterDuracao() de forma diferente
  console.log(`Duração/Tempo: ${conteudo.obterDuracao()}`);
  
  conteudo.incrementarVisualizacoes();
}

/**
 * Função que processa uma lista de conteúdos polimorfos
 * Demonstra o poder do polimorfismo
 */
function exibirCatalogo(conteudos) {
  console.log("\n" + "=".repeat(60));
  console.log("CATÁLOGO DE CONTEÚDOS EDUCATIVOS");
  console.log("=".repeat(60));

  conteudos.forEach((conteudo, index) => {
    console.log(`\n[${index + 1}]`);
    processarConteudo(conteudo);
  });
}

// ============================================
// CLASSE AVANÇADA: REPOSITÓRIO COM ENCAPSULAMENTO
// ============================================

class RepositorioConteudo {
  // Propriedade privada
  #conteudos = [];

  // Método para adicionar conteúdo
  adicionarConteudo(conteudo) {
    if (conteudo instanceof Conteudo) {
      this.#conteudos.push(conteudo);
      console.log(`✓ Conteúdo "${conteudo.titulo}" adicionado ao repositório`);
    } else {
      console.log(`✗ Erro: Objeto não é uma instância de Conteudo`);
    }
  }

  // Getter para obter todos os conteúdos (retorna cópia para proteger)
  get conteudos() {
    return [...this.#conteudos]; // Retorna cópia, não referência
  }

  // Método para buscar conteúdo por título
  buscarPorTitulo(titulo) {
    return this.#conteudos.filter(c => 
      c.titulo.toLowerCase().includes(titulo.toLowerCase())
    );
  }

  // Método para obter conteúdo mais visualizado
  obterMaisVisualizado() {
    if (this.#conteudos.length === 0) {
      return null;
    }
    return this.#conteudos.reduce((prev, current) =>
      prev.visualizacoes > current.visualizacoes ? prev : current
    );
  }

  // Método para gerar estatísticas
  gerarEstatisticas() {
    const totalConteudos = this.#conteudos.length;
    const totalVisualizacoes = this.#conteudos.reduce((sum, c) => sum + c.visualizacoes, 0);
    const mediaVisualizacoes = totalConteudos > 0 ? (totalVisualizacoes / totalConteudos).toFixed(2) : 0;

    return {
      totalConteudos,
      totalVisualizacoes,
      mediaVisualizacoes
    };
  }

  // Método para exibir estatísticas
  exibirEstatisticas() {
    const stats = this.gerarEstatisticas();
    console.log(`
      ========== ESTATÍSTICAS DO REPOSITÓRIO ==========
      Total de Conteúdos: ${stats.totalConteudos}
      Total de Visualizações: ${stats.totalVisualizacoes}
      Média de Visualizações por Conteúdo: ${stats.mediaVisualizacoes}
      ================================================
    `);
  }
}

// ============================================
// EXEMPLOS DE USO COMPLETO
// ============================================

console.log("\n" + "=".repeat(60));
console.log("1️⃣  DEMONSTRAÇÃO DE ENCAPSULAMENTO");
console.log("=".repeat(60));

// Criando usuários com encapsulamento
const usuario1 = new Usuario(1, "João Silva", "joao@email.com");
usuario1.exibirPerfil();

// Tentando alterar através de setters (com validação)
console.log("\n--- Alterando dados do usuário ---");
usuario1.nome = "Jo"; // Erro: menos de 3 caracteres
usuario1.nome = "João Pedro Silva"; // Sucesso
usuario1.email = "email-invalido"; // Erro: email inválido
usuario1.email = "joao.silva@email.com"; // Sucesso
usuario1.nivelAlfabetizacao = 150; // Erro: fora do intervalo
usuario1.nivelAlfabetizacao = 75; // Sucesso

usuario1.exibirPerfil();

// ⚠️ Tentando acessar propriedade privada (vai resultar em undefined)
console.log("\n--- Tentando acessar propriedade privada ---");
console.log(`Tentando acessar #id diretamente: ${usuario1.#id}`); // Erro: não é possível

console.log("\n" + "=".repeat(60));
console.log("2️⃣  DEMONSTRAÇÃO DE POLIMORFISMO");
console.log("=".repeat(60));

// Criando diferentes tipos de conteúdo (polimorfismo)
const video = new Video(
  "Introdução à Alfabetização Digital",
  "Aprenda os conceitos básicos de uso de computador",
  "Prof. Maria",
  15,
  "1080p"
);

const artigo = new Artigo(
  "Desigualdade Digital em Fortaleza",
  "Este artigo explora as disparidades no acesso à tecnologia em Fortaleza, analisando dados sociais e econômicos que contribuem para a exclusão digital de milhares de pessoas.",
  "Dr. Carlos",
  2500,
  "Tecnologia e Sociedade"
);

const tutorial = new Tutorial(
  "Como Usar Email",
  "Passo a passo para criar e gerenciar uma conta de email",
  "Tech Team",
  8,
  "Iniciante"
);

// Exibindo conteúdos usando polimorfismo
console.log("\n--- Demonstrando Polimorfismo ---");
processarConteudo(video);
processarConteudo(artigo);
processarConteudo(tutorial);

// Simulando múltiplas visualizações
console.log("\n--- Simulando Visualizações ---");
for (let i = 0; i < 5; i++) {
  artigo.incrementarVisualizacoes();
}
for (let i = 0; i < 10; i++) {
  video.incrementarVisualizacoes();
}
tutorial.completarEtapa();
tutorial.completarEtapa();
tutorial.completarEtapa();

console.log("\n" + "=".repeat(60));
console.log("3️⃣  DEMONSTRAÇÃO DE REPOSITÓRIO COM ENCAPSULAMENTO");
console.log("=".repeat(60));

// Criando repositório e adicionando conteúdos
const repositorio = new RepositorioConteudo();

repositorio.adicionarConteudo(video);
repositorio.adicionarConteudo(artigo);
repositorio.adicionarConteudo(tutorial);

// Exibindo catálogo completo (usando polimorfismo)
exibirCatalogo(repositorio.conteudos);

// Buscando conteúdo
console.log("\n--- Buscando Conteúdo ---");
const resultados = repositorio.buscarPorTitulo("Alfabetização");
console.log(`Encontrados ${resultados.length} conteúdo(s) com "Alfabetização"`);
resultados.forEach(r => console.log(`  - ${r.titulo}`));

// Conteúdo mais visualizado
console.log("\n--- Conteúdo Mais Visualizado ---");
const maisVisualizado = repositorio.obterMaisVisualizado();
console.log(`Título: ${maisVisualizado.titulo}`);
console.log(`Visualizações: ${maisVisualizado.visualizacoes}`);

// Exibindo estatísticas
repositorio.exibirEstatisticas();

console.log("\n✅ Demonstração Completa Finalizada!");
