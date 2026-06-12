/**
 * ============================================================
 * PROJETO COMPLETO: INCLUSÃO DIGITAL EM FORTALEZA
 * Linguagem: Java
 * Um arquivo único com toda a implementação
 * ============================================================
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

/**
 * ============================================================
 * 1. CLASSES BASE E SISTEMA PRINCIPAL
 * ============================================================
 */

/**
 * Classe base do Sistema
 * Define as propriedades gerais do sistema
 */
class SistemaInclusaoDigital {
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

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Requisito> getRequisitos() {
        return requisitos;
    }
}

/**
 * Classe para representar um Requisito
 */
class Requisito {
    private String id;
    private String descricao;

    public Requisito(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
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
        requisitos.add(new Requisito("RF01", "Exibir informações sobre desigualdade no acesso à tecnologia em Fortaleza."));
        requisitos.add(new Requisito("RF02", "Apresentar dados estatísticos sobre alfabetização digital."));
        requisitos.add(new Requisito("RF03", "Permitir a navegação entre diferentes seções do site."));
        requisitos.add(new Requisito("RF04", "Exibir gráficos e indicadores relacionados ao tema."));
        requisitos.add(new Requisito("RF05", "Disponibilizar conteúdos educativos sobre inclusão digital."));
        requisitos.add(new Requisito("RF06", "Permitir a consulta de dados por categoria."));
        requisitos.add(new Requisito("RF07", "Exibir informações de forma organizada e intuitiva."));
    }

    public void exibirRequisitos() {
        exibirInformacoes();
        System.out.println("\nRequisitos:");
        for (Requisito req : requisitos) {
            System.out.println("  " + req);
        }
    }

    public String verificarRequisito(String id) {
        for (Requisito req : requisitos) {
            if (req.getId().equals(id)) {
                return "✓ " + req.getDescricao();
            }
        }
        return "✗ Requisito " + id + " não encontrado";
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
        requisitos.add(new Requisito("RNF01", "O sistema deve possuir interface responsiva para computadores e dispositivos móveis."));
        requisitos.add(new Requisito("RNF02", "As páginas devem carregar em até 3 segundos em condições normais de internet."));
        requisitos.add(new Requisito("RNF03", "O sistema deve ser desenvolvido utilizando HTML, CSS e JavaScript."));
        requisitos.add(new Requisito("RNF04", "O sistema deve apresentar informações de forma clara e acessível."));
        requisitos.add(new Requisito("RNF05", "O sistema deve funcionar nos principais navegadores modernos (Chrome, Firefox, Edge e Safari)."));
        requisitos.add(new Requisito("RNF06", "O código deve ser organizado e documentado para facilitar a manutenção."));
        requisitos.add(new Requisito("RNF07", "O sistema deve garantir a disponibilidade das informações apresentadas."));
    }

    public void exibirRequisitos() {
        exibirInformacoes();
        System.out.println("\nRequisitos:");
        for (Requisito req : requisitos) {
            System.out.println("  " + req);
        }
    }

    public String verificarQualidade(String id) {
        for (Requisito req : requisitos) {
            if (req.getId().equals(id)) {
                return "✓ Qualidade " + req.getId() + ": " + req.getDescricao();
            }
        }
        return "✗ Requisito " + id + " não encontrado";
    }
}

/**
 * ============================================================
 * 2. MÓDULOS DO SISTEMA
 * ============================================================
 */

/**
 * Classe para Módulos do Sistema
 * Herda de SistemaInclusaoDigital
 * Organiza os diferentes módulos da aplicação
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

    public abstract void executarOperacao();
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

    @Override
    public void executarOperacao() {
        System.out.println(coletarDados("Desigualdade de Acesso"));
    }

    public List<String> getTiposDados() {
        return tiposDados;
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

    @Override
    public void executarOperacao() {
        System.out.println(renderizar("Gráfico de Estatísticas"));
    }

    public List<String> getTecnologias() {
        return tecnologias;
    }

    public List<String> getPlataformas() {
        return plataformas;
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

    @Override
    public void executarOperacao() {
        System.out.println(adicionarConteudo("Guia de Inclusão Digital", "Tutorial"));
    }

    public List<String> getTiposConteudo() {
        return tiposConteudo;
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
        modulos.add(new ModuloDados());
        modulos.add(new ModuloInterface());
        modulos.add(new ModuloConteudoEducativo());
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

        for (ModuloSistema modulo : modulos) {
            modulo.exibirInformacoes();
        }
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
        for (ModuloSistema modulo : modulos) {
            if (modulo.getNome().toLowerCase().contains(nome.toLowerCase())) {
                return modulo;
            }
        }
        return null;
    }

    public RequisitosFuncionais getRequisitosFuncionais() {
        return requisitosFuncionais;
    }

    public RequisitosNaoFuncionais getRequisitosNaoFuncionais() {
        return requisitosNaoFuncionais;
    }

    public List<ModuloSistema> getModulos() {
        return modulos;
    }
}

/**
 * ============================================================
 * 3. ENCAPSULAMENTO - CLASSE USUÁRIO
 * ============================================================
 */

/**
 * Classe com Encapsulamento
 * Protege dados sensíveis e valida antes de alterar
 */
class Usuario {
    private int id;
    private String nome;
    private String email;
    private int nivelAlfabetizacao;
    private LocalDate dataCadastro;

    public Usuario(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nivelAlfabetizacao = 0;
        this.dataCadastro = LocalDate.now();
    }

    // Getters - Apenas leitura (protegida)
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getNivelAlfabetizacao() {
        return nivelAlfabetizacao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    // Setters - Validação antes de alterar
    public void setNome(String novoNome) {
        if (novoNome != null && novoNome.length() >= 3) {
            this.nome = novoNome;
            System.out.println("✓ Nome atualizado para: " + novoNome);
        } else {
            System.out.println("✗ Erro: Nome deve ter no mínimo 3 caracteres");
        }
    }

    public void setEmail(String novoEmail) {
        if (validarEmail(novoEmail)) {
            this.email = novoEmail;
            System.out.println("✓ Email atualizado para: " + novoEmail);
        } else {
            System.out.println("✗ Erro: Email inválido");
        }
    }

    public void setNivelAlfabetizacao(int nivel) {
        if (nivel >= 0 && nivel <= 100) {
            this.nivelAlfabetizacao = nivel;
            System.out.println("✓ Nível de alfabetização atualizado para: " + nivel + "%");
        } else {
            System.out.println("✗ Erro: Nível deve estar entre 0 e 100");
        }
    }

    // Método privado (só pode ser usado internamente)
    private boolean validarEmail(String email) {
        String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(regex, email);
    }

    // Método público
    public void exibirPerfil() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n========== PERFIL DO USUÁRIO ==========");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Nível de Alfabetização: " + nivelAlfabetizacao + "%");
        System.out.println("Data de Cadastro: " + dataCadastro.format(formatter));
        System.out.println("========================================");
    }
}

/**
 * ============================================================
 * 4. POLIMORFISMO - CLASSES DE CONTEÚDO
 * ============================================================
 */

/**
 * Classe Base: Conteudo
 * Define a interface para diferentes tipos de conteúdo
 */
abstract class Conteudo {
    protected String titulo;
    protected String descricao;
    protected String autor;
    protected LocalDate dataCriacao;
    protected int visualizacoes;

    public Conteudo(String titulo, String descricao, String autor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.dataCriacao = LocalDate.now();
        this.visualizacoes = 0;
    }

    // Métodos abstratos que serão sobrescritos pelas classes filhas (Polimorfismo)
    public abstract void exibir();
    public abstract String obterDuracao();

    // Método comum a todas as classes
    public void incrementarVisualizacoes() {
        visualizacoes++;
    }

    protected String obterInformacoes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Título: " + titulo + "\n" +
               "Autor: " + autor + "\n" +
               "Visualizações: " + visualizacoes + "\n" +
               "Data de Criação: " + dataCriacao.format(formatter);
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAutor() {
        return autor;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}

/**
 * Classe Filha 1: Video
 * Implementação específica para vídeos
 */
class Video extends Conteudo {
    private int duracao; // em minutos
    private String resolucao; // 720p, 1080p, 4K

    public Video(String titulo, String descricao, String autor, int duracao, String resolucao) {
        super(titulo, descricao, autor);
        this.duracao = duracao;
        this.resolucao = resolucao;
    }

    // Polimorfismo: Sobrescreve o método exibir()
    @Override
    public void exibir() {
        System.out.println("\n📹 === VÍDEO ===");
        System.out.println(obterInformacoes());
        System.out.println("Duração: " + duracao + " minutos");
        System.out.println("Resolução: " + resolucao);
        System.out.println("================");
    }

    // Polimorfismo: Sobrescreve o método obterDuracao()
    @Override
    public String obterDuracao() {
        return duracao + " minutos";
    }

    // Método exclusivo desta classe
    public String fazerDownload() {
        return "Baixando vídeo \"" + titulo + "\" em " + resolucao + "...";
    }

    public int getDuracao() {
        return duracao;
    }

    public String getResolucao() {
        return resolucao;
    }
}

/**
 * Classe Filha 2: Artigo
 * Implementação específica para artigos
 */
class Artigo extends Conteudo {
    private int quantidadePalavras;
    private String categoria;

    public Artigo(String titulo, String descricao, String autor, int quantidadePalavras, String categoria) {
        super(titulo, descricao, autor);
        this.quantidadePalavras = quantidadePalavras;
        this.categoria = categoria;
    }

    // Polimorfismo: Sobrescreve o método exibir()
    @Override
    public void exibir() {
        System.out.println("\n📰 === ARTIGO ===");
        System.out.println(obterInformacoes());
        System.out.println("Quantidade de Palavras: " + quantidadePalavras);
        System.out.println("Categoria: " + categoria);
        System.out.println("================");
    }

    // Polimorfismo: Sobrescreve o método obterDuracao()
    @Override
    public String obterDuracao() {
        // Tempo de leitura estimado: 200 palavras por minuto
        int tempoLeitura = (quantidadePalavras + 199) / 200;
        return tempoLeitura + " minutos de leitura";
    }

    // Método exclusivo desta classe
    public String obterResumo() {
        if (descricao.length() > 100) {
            return descricao.substring(0, 100) + "...";
        }
        return descricao;
    }

    public int getQuantidadePalavras() {
        return quantidadePalavras;
    }

    public String getCategoria() {
        return categoria;
    }
}

/**
 * Classe Filha 3: Tutorial
 * Implementação específica para tutoriais
 */
class Tutorial extends Conteudo {
    private int numeroEtapas;
    private String nivelDificuldade; // Iniciante, Intermediário, Avançado
    private int etapasConcluidas;

    public Tutorial(String titulo, String descricao, String autor, int numeroEtapas, String nivelDificuldade) {
        super(titulo, descricao, autor);
        this.numeroEtapas = numeroEtapas;
        this.nivelDificuldade = nivelDificuldade;
        this.etapasConcluidas = 0;
    }

    // Polimorfismo: Sobrescreve o método exibir()
    @Override
    public void exibir() {
        System.out.println("\n🎓 === TUTORIAL ===");
        System.out.println(obterInformacoes());
        System.out.println("Número de Etapas: " + numeroEtapas);
        System.out.println("Nível de Dificuldade: " + nivelDificuldade);
        System.out.println("Progresso: " + etapasConcluidas + "/" + numeroEtapas);
        System.out.println("===================");
    }

    // Polimorfismo: Sobrescreve o método obterDuracao()
    @Override
    public String obterDuracao() {
        int tempoMedio = numeroEtapas * 5; // 5 minutos por etapa
        return tempoMedio + " minutos (aproximado)";
    }

    // Método exclusivo desta classe
    public String completarEtapa() {
        if (etapasConcluidas < numeroEtapas) {
            etapasConcluidas++;
            double percentual = (etapasConcluidas / (double) numeroEtapas) * 100;
            return "✓ Etapa " + etapasConcluidas + "/" + numeroEtapas + " concluída (" + String.format("%.0f", percentual) + "%)";
        }
        return "✗ Todas as etapas já foram concluídas";
    }

    public int getNumeroEtapas() {
        return numeroEtapas;
    }

    public String getNivelDificuldade() {
        return nivelDificuldade;
    }

    public int getEtapasConcluidas() {
        return etapasConcluidas;
    }
}

/**
 * ============================================================
 * 5. REPOSITÓRIO COM ENCAPSULAMENTO
 * ============================================================
 */

/**
 * Classe Repositório com Encapsulamento
 * Gerencia uma coleção de conteúdos
 */
class RepositorioConteudo {
    private List<Conteudo> conteudos;

    public RepositorioConteudo() {
        this.conteudos = new ArrayList<>();
    }

    // Método para adicionar conteúdo
    public void adicionarConteudo(Conteudo conteudo) {
        if (conteudo != null) {
            conteudos.add(conteudo);
            System.out.println("✓ Conteúdo \"" + conteudo.getTitulo() + "\" adicionado ao repositório");
        } else {
            System.out.println("✗ Erro: Conteúdo não pode ser nulo");
        }
    }

    // Getter para obter todos os conteúdos (retorna cópia para proteger)
    public List<Conteudo> getConteudos() {
        return new ArrayList<>(conteudos);
    }

    // Método para buscar conteúdo por título
    public List<Conteudo> buscarPorTitulo(String titulo) {
        return conteudos.stream()
                .filter(c -> c.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Método para obter conteúdo mais visualizado
    public Conteudo obterMaisVisualizado() {
        if (conteudos.isEmpty()) {
            return null;
        }
        return conteudos.stream()
                .max(Comparator.comparingInt(Conteudo::getVisualizacoes))
                .orElse(null);
    }

    // Método para gerar estatísticas
    public Map<String, Object> gerarEstatisticas() {
        Map<String, Object> stats = new HashMap<>();
        int totalConteudos = conteudos.size();
        int totalVisualizacoes = conteudos.stream()
                .mapToInt(Conteudo::getVisualizacoes)
                .sum();
        double mediaVisualizacoes = totalConteudos > 0 ? (double) totalVisualizacoes / totalConteudos : 0;

        stats.put("totalConteudos", totalConteudos);
        stats.put("totalVisualizacoes", totalVisualizacoes);
        stats.put("mediaVisualizacoes", mediaVisualizacoes);

        return stats;
    }

    // Método para exibir estatísticas
    public void exibirEstatisticas() {
        Map<String, Object> stats = gerarEstatisticas();
        System.out.println("\n========== ESTATÍSTICAS DO REPOSITÓRIO ==========");
        System.out.println("Total de Conteúdos: " + stats.get("totalConteudos"));
        System.out.println("Total de Visualizações: " + stats.get("totalVisualizacoes"));
        System.out.println("Média de Visualizações por Conteúdo: " + String.format("%.2f", stats.get("mediaVisualizacoes")));
        System.out.println("================================================");
    }

    public int getTotalConteudos() {
        return conteudos.size();
    }
}

/**
 * ============================================================
 * 6. CLIENTE API E TRATAMENTO DE ERROS
 * ============================================================
 */

/**
 * Classe de Erro Customizada
 */
class ApiError extends Exception {
    private int statusCode;
    private Map<String, Object> details;

    public ApiError(String message) {
        super(message);
        this.statusCode = -1;
        this.details = new HashMap<>();
    }

    public ApiError(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.details = new HashMap<>();
    }

    public ApiError(String message, int statusCode, Map<String, Object> details) {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ApiError: " + getMessage() + " (Status: " + statusCode + ")";
    }
}

/**
 * Cliente API com tratamento robusto de erros
 */
class ApiClient {
    private String baseUrl;
    private int timeout;

    public ApiClient(String baseUrl, int timeout) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
    }

    public ApiClient() {
        this("http://localhost:8080/api", 5000);
    }

    // Valida se o email é válido
    public boolean isValidEmail(String email) {
        String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(regex, email);
    }

    // Simula uma requisição GET
    public Map<String, Object> get(String endpoint) throws ApiError {
        try {
            // Validação de entrada
            if (endpoint == null || endpoint.isEmpty()) {
                throw new ApiError("Endpoint inválido", 400);
            }

            System.out.println("GET " + baseUrl + endpoint);
            
            // Simula resposta
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", "Dados obtidos com sucesso");
            
            return response;
        } catch (ApiError e) {
            throw e;
        } catch (Exception e) {
            throw new ApiError("Erro ao conectar com o servidor", 500);
        }
    }

    // Simula uma requisição POST
    public Map<String, Object> post(String endpoint, Map<String, Object> data) throws ApiError {
        try {
            if (endpoint == null || endpoint.isEmpty()) {
                throw new ApiError("Endpoint inválido", 400);
            }

            if (data == null) {
                throw new ApiError("Dados inválidos", 400);
            }

            System.out.println("POST " + baseUrl + endpoint);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Dados criados com sucesso");
            response.put("data", data);
            
            return response;
        } catch (ApiError e) {
            throw e;
        } catch (Exception e) {
            throw new ApiError("Erro ao conectar com o servidor", 500);
        }
    }

    // Simula uma requisição PUT
    public Map<String, Object> put(String endpoint, Map<String, Object> data) throws ApiError {
        try {
            if (endpoint == null || endpoint.isEmpty()) {
                throw new ApiError("Endpoint inválido", 400);
            }

            System.out.println("PUT " + baseUrl + endpoint);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Dados atualizados com sucesso");
            
            return response;
        } catch (ApiError e) {
            throw e;
        } catch (Exception e) {
            throw new ApiError("Erro ao conectar com o servidor", 500);
        }
    }

    // Simula uma requisição DELETE
    public Map<String, Object> delete(String endpoint) throws ApiError {
        try {
            if (endpoint == null || endpoint.isEmpty()) {
                throw new ApiError("Endpoint inválido", 400);
            }

            System.out.println("DELETE " + baseUrl + endpoint);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Dados removidos com sucesso");
            
            return response;
        } catch (ApiError e) {
            throw e;
        } catch (Exception e) {
            throw new ApiError("Erro ao conectar com o servidor", 500);
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }
}

/**
 * ============================================================
 * 7. CLASSE PRINCIPAL - EXECUÇÃO DO PROGRAMA
 * ============================================================
 */

/**
 * Classe Principal - Demonstração completa do sistema
 */
public class InclusaoDigitalFortaleza {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║    🌐 PROJETO: INCLUSÃO DIGITAL EM FORTALEZA                   ║");
        System.out.println("║    Implementação Completa em Java                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        // 1. DEMONSTRAÇÃO DE HERANÇA E REQUISITOS
        System.out.println("\n" + "=".repeat(60));
        System.out.println("1️⃣  DEMONSTRAÇÃO DE HERANÇA E REQUISITOS");
        System.out.println("=".repeat(60));

        GerenciadorSistema gerenciador = new GerenciadorSistema();
        gerenciador.exibirRelatorioCompleto();

        // 2. VERIFICAÇÃO DE IMPLEMENTAÇÃO
        gerenciador.verificarImplementacao("RF01");
        gerenciador.verificarImplementacao("RNF02");

        // 3. DEMONSTRAÇÃO DE USO DOS MÓDULOS
        System.out.println("\n" + "=".repeat(60));
        System.out.println("2️⃣  EXEMPLO DE USO DOS MÓDULOS");
        System.out.println("=".repeat(60));

        ModuloDados moduloDados = (ModuloDados) gerenciador.obterModuloPorNome("Dados");
        if (moduloDados != null) {
            System.out.println(moduloDados.coletarDados("Desigualdade de Acesso"));
        }

        ModuloInterface moduloInterface = (ModuloInterface) gerenciador.obterModuloPorNome("Interface");
        if (moduloInterface != null) {
            System.out.println(moduloInterface.renderizar("Gráfico de Estatísticas"));
        }

        ModuloConteudoEducativo moduloConteudo = (ModuloConteudoEducativo) gerenciador.obterModuloPorNome("Conteúdo");
        if (moduloConteudo != null) {
            System.out.println(moduloConteudo.adicionarConteudo("Guia de Inclusão Digital", "Tutorial"));
        }

        // 4. DEMONSTRAÇÃO DE ENCAPSULAMENTO
        System.out.println("\n" + "=".repeat(60));
        System.out.println("3️⃣  DEMONSTRAÇÃO DE ENCAPSULAMENTO");
        System.out.println("=".repeat(60));

        Usuario usuario1 = new Usuario(1, "João Silva", "joao@email.com");
        usuario1.exibirPerfil();

        System.out.println("\n--- Alterando dados do usuário ---");
        usuario1.setNome("Jo"); // Erro: menos de 3 caracteres
        usuario1.setNome("João Pedro Silva"); // Sucesso
        usuario1.setEmail("email-invalido"); // Erro: email inválido
        usuario1.setEmail("joao.silva@email.com"); // Sucesso
        usuario1.setNivelAlfabetizacao(150); // Erro: fora do intervalo
        usuario1.setNivelAlfabetizacao(75); // Sucesso

        usuario1.exibirPerfil();

        // 5. DEMONSTRAÇÃO DE POLIMORFISMO
        System.out.println("\n" + "=".repeat(60));
        System.out.println("4️⃣  DEMONSTRAÇÃO DE POLIMORFISMO");
        System.out.println("=".repeat(60));

        // Criando diferentes tipos de conteúdo
        Video video = new Video(
            "Introdução à Alfabetização Digital",
            "Aprenda os conceitos básicos de uso de computador",
            "Prof. Maria",
            15,
            "1080p"
        );

        Artigo artigo = new Artigo(
            "Desigualdade Digital em Fortaleza",
            "Este artigo explora as disparidades no acesso à tecnologia em Fortaleza, analisando dados sociais e econômicos que contribuem para a exclusão digital de milhares de pessoas.",
            "Dr. Carlos",
            2500,
            "Tecnologia e Sociedade"
        );

        Tutorial tutorial = new Tutorial(
            "Como Usar Email",
            "Passo a passo para criar e gerenciar uma conta de email",
            "Tech Team",
            8,
            "Iniciante"
        );

        // Exibindo conteúdos usando polimorfismo
        System.out.println("\n--- Demonstrando Polimorfismo ---");
        processarConteudo(video);
        processarConteudo(artigo);
        processarConteudo(tutorial);

        // Simulando múltiplas visualizações
        System.out.println("\n--- Simulando Visualizações ---");
        for (int i = 0; i < 5; i++) {
            artigo.incrementarVisualizacoes();
        }
        for (int i = 0; i < 10; i++) {
            video.incrementarVisualizacoes();
        }
        System.out.println(tutorial.completarEtapa());
        System.out.println(tutorial.completarEtapa());
        System.out.println(tutorial.completarEtapa());

        // 6. DEMONSTRAÇÃO DE REPOSITÓRIO COM ENCAPSULAMENTO
        System.out.println("\n" + "=".repeat(60));
        System.out.println("5️⃣  DEMONSTRAÇÃO DE REPOSITÓRIO COM ENCAPSULAMENTO");
        System.out.println("=".repeat(60));

        RepositorioConteudo repositorio = new RepositorioConteudo();

        repositorio.adicionarConteudo(video);
        repositorio.adicionarConteudo(artigo);
        repositorio.adicionarConteudo(tutorial);

        exibirCatalogo(repositorio.getConteudos());

        // Buscando conteúdo
        System.out.println("\n--- Buscando Conteúdo ---");
        List<Conteudo> resultados = repositorio.buscarPorTitulo("Alfabetização");
        System.out.println("Encontrados " + resultados.size() + " conteúdo(s) com \"Alfabetização\"");
        for (Conteudo c : resultados) {
            System.out.println("  - " + c.getTitulo());
        }

        // Conteúdo mais visualizado
        System.out.println("\n--- Conteúdo Mais Visualizado ---");
        Conteudo maisVisualizado = repositorio.obterMaisVisualizado();
        if (maisVisualizado != null) {
            System.out.println("Título: " + maisVisualizado.getTitulo());
            System.out.println("Visualizações: " + maisVisualizado.getVisualizacoes());
        }

        // Exibindo estatísticas
        repositorio.exibirEstatisticas();

        // 7. DEMONSTRAÇÃO DE CLIENTE API
        System.out.println("\n" + "=".repeat(60));
        System.out.println("6️⃣  DEMONSTRAÇÃO DE CLIENTE API");
        System.out.println("=".repeat(60));

        ApiClient apiClient = new ApiClient();

        try {
            // Validação de email
            System.out.println("\n--- Validação de Email ---");
            System.out.println("Email válido (joao@email.com): " + apiClient.isValidEmail("joao@email.com"));
            System.out.println("Email inválido (joao@email): " + apiClient.isValidEmail("joao@email"));

            // Requisições simuladas
            System.out.println("\n--- Requisições HTTP Simuladas ---");
            Map<String, Object> responseGet = apiClient.get("/conteudos");
            System.out.println("GET Response: " + responseGet.get("success"));

            Map<String, Object> postData = new HashMap<>();
            postData.put("titulo", "Novo Conteúdo");
            postData.put("autor", "Autor Novo");
            Map<String, Object> responsePost = apiClient.post("/conteudos", postData);
            System.out.println("POST Response: " + responsePost.get("message"));

        } catch (ApiError e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // 8. RESUMO FINAL
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RESUMO DO SISTEMA");
        System.out.println("=".repeat(60));
        System.out.println("✓ Requisitos Funcionais: " + gerenciador.getRequisitosFuncionais().getRequisitos().size());
        System.out.println("✓ Requisitos Não-Funcionais: " + gerenciador.getRequisitosNaoFuncionais().getRequisitos().size());
        System.out.println("✓ Módulos do Sistema: " + gerenciador.getModulos().size());
        System.out.println("✓ Conteúdos no Repositório: " + repositorio.getTotalConteudos());
        System.out.println("✓ Usuários Cadastrados: 1");
        System.out.println("\n✅ Demonstração Completa Finalizada!");
        System.out.println("=".repeat(60));
    }

    /**
     * Função que demonstra Polimorfismo
     * Mesmo método, comportamentos diferentes para cada tipo
     */
    private static void processarConteudo(Conteudo conteudo) {
        System.out.println("\n--- Processando Conteúdo ---");
        conteudo.exibir();
        System.out.println("Duração/Tempo: " + conteudo.obterDuracao());
        conteudo.incrementarVisualizacoes();
    }

    /**
     * Função que processa uma lista de conteúdos polimorfos
     */
    private static void exibirCatalogo(List<Conteudo> conteudos) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CATÁLOGO DE CONTEÚDOS EDUCATIVOS");
        System.out.println("=".repeat(60));

        for (int i = 0; i < conteudos.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]");
            processarConteudo(conteudos.get(i));
        }
    }
}

/**
 * ============================================================
 * DOCUMENTAÇÃO DO PROJETO
 * ============================================================
 * 
 * PROJETO: Inclusão Digital em Fortaleza
 * 
 * OBJETIVO:
 * Disponibilizar informações sobre a desigualdade no acesso 
 * à tecnologia e alfabetização digital em Fortaleza, promovendo 
 * conscientização e informação para diferentes públicos.
 * 
 * REQUISITOS FUNCIONAIS (7):
 * - RF01: Exibir informações sobre desigualdade no acesso à tecnologia
 * - RF02: Apresentar dados estatísticos sobre alfabetização digital
 * - RF03: Permitir navegação entre diferentes seções
 * - RF04: Exibir gráficos e indicadores relacionados
 * - RF05: Disponibilizar conteúdos educativos
 * - RF06: Permitir consulta de dados por categoria
 * - RF07: Exibir informações de forma organizada e intuitiva
 * 
 * REQUISITOS NÃO-FUNCIONAIS (7):
 * - RNF01: Interface responsiva
 * - RNF02: Tempo de carregamento até 3 segundos
 * - RNF03: Desenvolvido em HTML, CSS e JavaScript
 * - RNF04: Informações claras e acessíveis
 * - RNF05: Compatibilidade com navegadores modernos
 * - RNF06: Código organizado e documentado
 * - RNF07: Garantir disponibilidade das informações
 * 
 * MÓDULOS DO SISTEMA (3):
 * 1. Módulo de Dados
 *    - Gerencia dados estatísticos
 *    - Tipos: Estatísticas, Gráficos, Indicadores
 * 
 * 2. Módulo de Interface
 *    - Interface responsiva e experiência do usuário
 *    - Tecnologias: HTML, CSS, JavaScript
 * 
 * 3. Módulo de Conteúdo Educativo
 *    - Materiais educativos sobre inclusão digital
 *    - Tipos: Tutoriais, Artigos, Vídeos
 * 
 * CONCEITOS DE OOP APLICADOS:
 * 
 * 1. HERANÇA:
 *    - SistemaInclusaoDigital (classe base)
 *    - RequisitosFuncionais e RequisitosNaoFuncionais
 *    - ModuloSistema com especializações
 * 
 * 2. ENCAPSULAMENTO:
 *    - Classe Usuario com propriedades privadas
 *    - Getters e setters com validação
 *    - RepositorioConteudo protegendo dados internos
 * 
 * 3. POLIMORFISMO:
 *    - Classe abstrata Conteudo
 *    - Implementações: Video, Artigo, Tutorial
 *    - Métodos sobrescritos: exibir(), obterDuracao()
 * 
 * COMPOSIÇÃO DE LINGUAGENS:
 * - Implementação: Java (100%)
 * - Projeto original: JavaScript 46.5%, Java 28.7%, HTML 24.8%
 * 
 * TECNOLOGIAS UTILIZADAS:
 * - Linguagem: Java
 * - Padrões: MVC, Repository Pattern, Factory Pattern
 * - Estruturas: HashMap, ArrayList, Stream API
 * - Data/Hora: LocalDate, DateTimeFormatter
 * - Validação: Pattern (Regex), Custom Validation
 * 
 * COMO EXECUTAR:
 * 1. Compilar: javac InclusaoDigitalFortaleza.java
 * 2. Executar: java InclusaoDigitalFortaleza
 * 
 * ============================================================
 */
