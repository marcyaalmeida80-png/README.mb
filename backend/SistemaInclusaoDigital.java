package com.inclusaodigital.sistema;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe base do Sistema
 * Define as propriedades gerais do sistema
 */
public abstract class SistemaInclusaoDigital {
    protected String nome;
    protected String descricao;
    protected List<Requisito> requisitos;

    public SistemaInclusaoDigital(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.requisitos = new ArrayList<>();
    }

    public void adicionarRequisito(Requisito requisito) {
        this.requisitos.add(requisito);
    }

    public void exibirInformacoes() {
        System.out.println("\n=== " + nome + " ===");
        System.out.println("Descrição: " + descricao);
        System.out.println("Total de Requisitos: " + requisitos.size());
    }

    public List<Requisito> obterRequisitos() {
        return new ArrayList<>(requisitos);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}

/**
 * Classe para representar um Requisito
 */
class Requisito {
    private String id;
    private String descricao;
    private LocalDateTime dataCriacao;

    public Requisito(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.dataCriacao = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public String toString() {
        return id + ": " + descricao;
    }
}

/**
 * Classe para Requisitos Funcionais
 * Herda de SistemaInclusaoDigital
 * Define o que o sistema DEVE FAZER
 */
class RequisitosFuncionais extends SistemaInclusaoDigital {
    public RequisitosFuncionais() {
        super("Requisitos Funcionais", "Descrevem o que o sistema deve fazer");
        inicializarRequisitos();
    }

    private void inicializarRequisitos() {
        this.requisitos = Arrays.asList(
            new Requisito("RF01", "Exibir informações sobre desigualdade no acesso à tecnologia em Fortaleza."),
            new Requisito("RF02", "Apresentar dados estatísticos sobre alfabetização digital."),
            new Requisito("RF03", "Permitir a navegação entre diferentes seções do site."),
            new Requisito("RF04", "Exibir gráficos e indicadores relacionados ao tema."),
            new Requisito("RF05", "Disponibilizar conteúdos educativos sobre inclusão digital."),
            new Requisito("RF06", "Permitir a consulta de dados por categoria."),
            new Requisito("RF07", "Exibir informações de forma organizada e intuitiva.")
        );
    }

    public void exibirRequisitos() {
        exibirInformacoes();
        System.out.println("\nRequisitos:");
        requisitos.forEach(req -> System.out.println("  " + req));
    }

    public String verificarRequisito(String id) {
        return requisitos.stream()
            .filter(r -> r.getId().equals(id))
            .map(r -> "✓ " + r)
            .findFirst()
            .orElse("✗ Requisito " + id + " não encontrado");
    }
}

/**
 * Classe para Requisitos Não-Funcionais
 * Herda de SistemaInclusaoDigital
 * Define características de QUALIDADE do sistema
 */
class RequisitosNaoFuncionais extends SistemaInclusaoDigital {
    public RequisitosNaoFuncionais() {
        super("Requisitos Não-Funcionais", "Descrevem características de qualidade do sistema");
        inicializarRequisitos();
    }

    private void inicializarRequisitos() {
        this.requisitos = Arrays.asList(
            new Requisito("RNF01", "O sistema deve possuir interface responsiva para computadores e dispositivos móveis."),
            new Requisito("RNF02", "As páginas devem carregar em até 3 segundos em condições normais de internet."),
            new Requisito("RNF03", "O sistema deve ser desenvolvido utilizando HTML, CSS e JavaScript."),
            new Requisito("RNF04", "O sistema deve apresentar informações de forma clara e acessível."),
            new Requisito("RNF05", "O sistema deve funcionar nos principais navegadores modernos (Chrome, Firefox, Edge e Safari)."),
            new Requisito("RNF06", "O código deve ser organizado e documentado para facilitar a manutenção."),
            new Requisito("RNF07", "O sistema deve garantir a disponibilidade das informações apresentadas.")
        );
    }

    public void exibirRequisitos() {
        exibirInformacoes();
        System.out.println("\nRequisitos:");
        requisitos.forEach(req -> System.out.println("  " + req));
    }

    public String verificarQualidade(String id) {
        return requisitos.stream()
            .filter(r -> r.getId().equals(id))
            .map(r -> "✓ Qualidade " + r)
            .findFirst()
            .orElse("✗ Requisito " + id + " não encontrado");
    }
}

/**
 * Classe abstrata para Módulos do Sistema
 * Herda de SistemaInclusaoDigital
 */
abstract class ModuloSistema extends SistemaInclusaoDigital {
    protected String responsavel;

    public ModuloSistema(String nome, String descricao, String responsavel) {
        super(nome, descricao);
        this.responsavel = responsavel;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Responsável: " + responsavel);
    }

    public String getResponsavel() {
        return responsavel;
    }
}

/**
 * Classe para módulo de Dados
 * Herda de ModuloSistema
 */
class ModuloDados extends ModuloSistema {
    private List<String> tiposDados;

    public ModuloDados() {
        super("Módulo de Dados",
              "Gerencia dados estatísticos e informações sobre desigualdade digital",
              "Desenvolvedor Backend");
        this.tiposDados = Arrays.asList("Estatísticas", "Gráficos", "Indicadores");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipos de Dados: " + String.join(", ", tiposDados));
    }

    public String coletarDados(String categoria) {
        System.out.println("\nColetando dados para a categoria: " + categoria);
        return "Dados de " + categoria + " coletados com sucesso";
    }

    public List<String> obterTiposDados() {
        return new ArrayList<>(tiposDados);
    }
}

/**
 * Classe para módulo de Interface
 * Herda de ModuloSistema
 */
class ModuloInterface extends ModuloSistema {
    private List<String> tecnologias;
    private List<String> plataformas;

    public ModuloInterface() {
        super("Módulo de Interface",
              "Gerencia a interface responsiva e experiência do usuário",
              "Desenvolvedor Frontend");
        this.tecnologias = Arrays.asList("HTML", "CSS", "JavaScript");
        this.plataformas = Arrays.asList("Desktop", "Tablet", "Mobile");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tecnologias: " + String.join(", ", tecnologias));
        System.out.println("Plataformas Suportadas: " + String.join(", ", plataformas));
    }

    public String renderizar(String componente) {
        System.out.println("\nRenderizando componente: " + componente);
        return "Componente " + componente + " renderizado com sucesso";
    }

    public List<String> obterTecnologias() {
        return new ArrayList<>(tecnologias);
    }

    public List<String> obterPlataformas() {
        return new ArrayList<>(plataformas);
    }
}

/**
 * Classe para módulo de Conteúdo Educativo
 * Herda de ModuloSistema
 */
class ModuloConteudoEducativo extends ModuloSistema {
    private List<String> tiposConteudo;

    public ModuloConteudoEducativo() {
        super("Módulo de Conteúdo Educativo",
              "Gerencia materiais educativos sobre inclusão digital",
              "Desenvolvedor de Conteúdo");
        this.tiposConteudo = Arrays.asList("Tutoriais", "Artigos", "Vídeos");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipos de Conteúdo: " + String.join(", ", tiposConteudo));
    }

    public String adicionarConteudo(String titulo, String tipo) {
        System.out.println("\nAdicionando novo conteúdo: \"" + titulo + "\" (Tipo: " + tipo + ")");
        return "Conteúdo \"" + titulo + "\" adicionado com sucesso";
    }

    public List<String> obterTiposConteudo() {
        return new ArrayList<>(tiposConteudo);
    }
}

/**
 * Classe Principal: Gerenciador do Sistema
 * Herda de SistemaInclusaoDigital
 * Coordena todos os componentes do sistema
 */
class GerenciadorSistema extends SistemaInclusaoDigital {
    private RequisitosFuncionais requisitosFuncionais;
    private RequisitosNaoFuncionais requisitosNaoFuncionais;
    private List<ModuloSistema> modulos;

    public GerenciadorSistema() {
        super("Sistema de Inclusão Digital em Fortaleza",
              "Plataforma para disponibilizar informações sobre desigualdade no acesso à tecnologia e alfabetização digital");
        this.requisitosFuncionais = new RequisitosFuncionais();
        this.requisitosNaoFuncionais = new RequisitosNaoFuncionais();
        this.modulos = new ArrayList<>();
        inicializarModulos();
    }

    private void inicializarModulos() {
        this.modulos = Arrays.asList(
            new ModuloDados(),
            new ModuloInterface(),
            new ModuloConteudoEducativo()
        );
    }

    public void exibirRelatorioCompleto() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RELATÓRIO COMPLETO DO SISTEMA");
        System.out.println("=".repeat(60));

        exibirInformacoes();

        requisitosFuncionais.exibirRequisitos();
        requisitosNaoFuncionais.exibirRequisitos();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("MÓDULOS DO SISTEMA");
        System.out.println("=".repeat(60));

        modulos.forEach(ModuloSistema::exibirInformacoes);
    }

    public void verificarImplementacao(String id) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VERIFICAÇÃO: " + id);
        System.out.println("=".repeat(60));

        if (id.startsWith("RF")) {
            System.out.println(requisitosFuncionais.verificarRequisito(id));
        } else if (id.startsWith("RNF")) {
            System.out.println(requisitosNaoFuncionais.verificarQualidade(id));
        } else {
            System.out.println("Tipo de requisito desconhecido: " + id);
        }
    }

    public ModuloSistema obterModuloPorNome(String nome) {
        return modulos.stream()
            .filter(m -> m.getNome().toLowerCase().contains(nome.toLowerCase()))
            .findFirst()
            .orElse(null);
    }

    public List<ModuloSistema> obterModulos() {
        return new ArrayList<>(modulos);
    }

    public RequisitosFuncionais obterRequisitosFuncionais() {
        return requisitosFuncionais;
    }

    public RequisitosNaoFuncionais obterRequisitosNaoFuncionais() {
        return requisitosNaoFuncionais;
    }

    public static void main(String[] args) {
        GerenciadorSistema gerenciador = new GerenciadorSistema();
        gerenciador.exibirRelatorioCompleto();

        gerenciador.verificarImplementacao("RF01");
        gerenciador.verificarImplementacao("RNF02");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXEMPLO DE USO DOS MÓDULOS");
        System.out.println("=".repeat(60));

        ModuloDados moduloDados = (ModuloDados) gerenciador.obterModuloPorNome("Dados");
        System.out.println(moduloDados.coletarDados("Desigualdade de Acesso"));

        ModuloInterface moduloInterface = (ModuloInterface) gerenciador.obterModuloPorNome("Interface");
        System.out.println(moduloInterface.renderizar("Gráfico de Estatísticas"));

        ModuloConteudoEducativo moduloConteudo = (ModuloConteudoEducativo) gerenciador.obterModuloPorNome("Conteúdo");
        System.out.println(moduloConteudo.adicionarConteudo("Guia de Inclusão Digital", "Tutorial"));
    }
}
