// ============================================
// Projeto: Inclusão Digital em Fortaleza
// Implementação com Herança
// ============================================

/**
 * Classe base do Sistema
 * Define as propriedades gerais do sistema
 */
class SistemaInclusaoDigital {
  constructor(nome, descricao) {
    this.nome = nome;
    this.descricao = descricao;
    this.requisitos = [];
  }

  adicionarRequisito(requisito) {
    this.requisitos.push(requisito);
  }

  exibirInformacoes() {
    console.log(`\n=== ${this.nome} ===`);
    console.log(`Descrição: ${this.descricao}`);
    console.log(`Total de Requisitos: ${this.requisitos.length}`);
  }
}

/**
 * Classe para Requisitos Funcionais
 * Herda de SistemaInclusaoDigital
 * Define o que o sistema DEVE FAZER
 */
class RequisitosFuncionais extends SistemaInclusaoDigital {
  constructor() {
    super(
      "Requisitos Funcionais",
      "Descrevem o que o sistema deve fazer"
    );
    this.inicializarRequisitos();
  }

  inicializarRequisitos() {
    this.requisitos = [
      { id: "RF01", descricao: "Exibir informações sobre desigualdade no acesso à tecnologia em Fortaleza." },
      { id: "RF02", descricao: "Apresentar dados estatísticos sobre alfabetização digital." },
      { id: "RF03", descricao: "Permitir a navegação entre diferentes seções do site." },
      { id: "RF04", descricao: "Exibir gráficos e indicadores relacionados ao tema." },
      { id: "RF05", descricao: "Disponibilizar conteúdos educativos sobre inclusão digital." },
      { id: "RF06", descricao: "Permitir a consulta de dados por categoria." },
      { id: "RF07", descricao: "Exibir informações de forma organizada e intuitiva." }
    ];
  }

  exibirRequisitos() {
    this.exibirInformacoes();
    console.log("\nRequisitos:");
    this.requisitos.forEach(req => {
      console.log(`  ${req.id}: ${req.descricao}`);
    });
  }

  verificarRequisito(id) {
    const req = this.requisitos.find(r => r.id === id);
    return req ? `✓ ${req.descricao}` : `✗ Requisito ${id} não encontrado`;
  }
}

/**
 * Classe para Requisitos Não-Funcionais
 * Herda de SistemaInclusaoDigital
 * Define características de QUALIDADE do sistema
 */
class RequisitosNaoFuncionais extends SistemaInclusaoDigital {
  constructor() {
    super(
      "Requisitos Não-Funcionais",
      "Descrevem características de qualidade do sistema"
    );
    this.inicializarRequisitos();
  }

  inicializarRequisitos() {
    this.requisitos = [
      { id: "RNF01", descricao: "O sistema deve possuir interface responsiva para computadores e dispositivos móveis." },
      { id: "RNF02", descricao: "As páginas devem carregar em até 3 segundos em condições normais de internet." },
      { id: "RNF03", descricao: "O sistema deve ser desenvolvido utilizando HTML, CSS e JavaScript." },
      { id: "RNF04", descricao: "O sistema deve apresentar informações de forma clara e acessível." },
      { id: "RNF05", descricao: "O sistema deve funcionar nos principais navegadores modernos (Chrome, Firefox, Edge e Safari)." },
      { id: "RNF06", descricao: "O código deve ser organizado e documentado para facilitar a manutenção." },
      { id: "RNF07", descricao: "O sistema deve garantir a disponibilidade das informações apresentadas." }
    ];
  }

  exibirRequisitos() {
    this.exibirInformacoes();
    console.log("\nRequisitos:");
    this.requisitos.forEach(req => {
      console.log(`  ${req.id}: ${req.descricao}`);
    });
  }

  verificarQualidade(id) {
    const req = this.requisitos.find(r => r.id === id);
    return req ? `✓ Qualidade ${req.id}: ${req.descricao}` : `✗ Requisito ${id} não encontrado`;
  }
}

/**
 * Classe para Módulos do Sistema
 * Herda de SistemaInclusaoDigital
 * Organiza os diferentes módulos da aplicação
 */
class ModuloSistema extends SistemaInclusaoDigital {
  constructor(nome, descricao, responsavel) {
    super(nome, descricao);
    this.responsavel = responsavel;
  }

  exibirInformacoes() {
    super.exibirInformacoes();
    console.log(`Responsável: ${this.responsavel}`);
  }
}

/**
 * Classe para módulo de Dados
 * Herda de ModuloSistema
 */
class ModuloDados extends ModuloSistema {
  constructor() {
    super(
      "Módulo de Dados",
      "Gerencia dados estatísticos e informações sobre desigualdade digital",
      "Desenvolvedor Backend"
    );
    this.tiposDados = ["Estatísticas", "Gráficos", "Indicadores"];
  }

  exibirInformacoes() {
    super.exibirInformacoes();
    console.log(`Tipos de Dados: ${this.tiposDados.join(", ")}`);
  }

  coletarDados(categoria) {
    console.log(`\nColetando dados para a categoria: ${categoria}`);
    return `Dados de ${categoria} coletados com sucesso`;
  }
}

/**
 * Classe para módulo de Interface
 * Herda de ModuloSistema
 */
class ModuloInterface extends ModuloSistema {
  constructor() {
    super(
      "Módulo de Interface",
      "Gerencia a interface responsiva e experiência do usuário",
      "Desenvolvedor Frontend"
    );
    this.tecnologias = ["HTML", "CSS", "JavaScript"];
    this.plataformas = ["Desktop", "Tablet", "Mobile"];
  }

  exibirInformacoes() {
    super.exibirInformacoes();
    console.log(`Tecnologias: ${this.tecnologias.join(", ")}`);
    console.log(`Plataformas Suportadas: ${this.plataformas.join(", ")}`);
  }

  renderizar(componente) {
    console.log(`\nRenderizando componente: ${componente}`);
    return `Componente ${componente} renderizado com sucesso`;
  }
}

/**
 * Classe para módulo de Conteúdo Educativo
 * Herda de ModuloSistema
 */
class ModuloConteudoEducativo extends ModuloSistema {
  constructor() {
    super(
      "Módulo de Conteúdo Educativo",
      "Gerencia materiais educativos sobre inclusão digital",
      "Desenvolvedor de Conteúdo"
    );
    this.tiposConteudo = ["Tutoriais", "Artigos", "Vídeos"];
  }

  exibirInformacoes() {
    super.exibirInformacoes();
    console.log(`Tipos de Conteúdo: ${this.tiposConteudo.join(", ")}`);
  }

  adicionarConteudo(titulo, tipo) {
    console.log(`\nAdicionando novo conteúdo: "${titulo}" (Tipo: ${tipo})`);
    return `Conteúdo "${titulo}" adicionado com sucesso`;
  }
}

/**
 * Classe Principal: Gerenciador do Sistema
 * Herda de SistemaInclusaoDigital
 * Coordena todos os componentes do sistema
 */
class GerenciadorSistema extends SistemaInclusaoDigital {
  constructor() {
    super(
      "Sistema de Inclusão Digital em Fortaleza",
      "Plataforma para disponibilizar informações sobre desigualdade no acesso à tecnologia e alfabetização digital"
    );
    this.requisitosFuncionais = new RequisitosFuncionais();
    this.requisitosNaoFuncionais = new RequisitosNaoFuncionais();
    this.modulos = [];
    this.inicializarModulos();
  }

  inicializarModulos() {
    this.modulos = [
      new ModuloDados(),
      new ModuloInterface(),
      new ModuloConteudoEducativo()
    ];
  }

  exibirRelatorioCompleto() {
    console.log("\n" + "=".repeat(60));
    console.log("RELATÓRIO COMPLETO DO SISTEMA");
    console.log("=".repeat(60));

    this.exibirInformacoes();

    this.requisitosFuncionais.exibirRequisitos();
    this.requisitosNaoFuncionais.exibirRequisitos();

    console.log("\n" + "=".repeat(60));
    console.log("MÓDULOS DO SISTEMA");
    console.log("=".repeat(60));

    this.modulos.forEach(modulo => {
      modulo.exibirInformacoes();
    });
  }

  verificarImplementacao(id) {
    console.log("\n" + "=".repeat(60));
    console.log(`VERIFICAÇÃO: ${id}`);
    console.log("=".repeat(60));

    if (id.startsWith("RF")) {
      console.log(this.requisitosFuncionais.verificarRequisito(id));
    } else if (id.startsWith("RNF")) {
      console.log(this.requisitosNaoFuncionais.verificarQualidade(id));
    } else {
      console.log(`Tipo de requisito desconhecido: ${id}`);
    }
  }

  obterModuloPorNome(nome) {
    return this.modulos.find(m => m.nome.toLowerCase().includes(nome.toLowerCase()));
  }
}

// ============================================
// EXEMPLOS DE USO
// ============================================

// Criar gerenciador do sistema
const gerenciador = new GerenciadorSistema();

// Exibir relatório completo
gerenciador.exibirRelatorioCompleto();

// Verificar requisitos específicos
gerenciador.verificarImplementacao("RF01");
gerenciador.verificarImplementacao("RNF02");

// Usar módulos específicos
console.log("\n" + "=".repeat(60));
console.log("EXEMPLO DE USO DOS MÓDULOS");
console.log("=".repeat(60));

const moduloDados = gerenciador.obterModuloPorNome("Dados");
console.log(moduloDados.coletarDados("Desigualdade de Acesso"));

const moduloInterface = gerenciador.obterModuloPorNome("Interface");
console.log(moduloInterface.renderizar("Gráfico de Estatísticas"));

const moduloConteudo = gerenciador.obterModuloPorNome("Conteúdo");
console.log(moduloConteudo.adicionarConteudo("Guia de Inclusão Digital", "Tutorial"));
