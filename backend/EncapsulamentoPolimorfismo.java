package com.inclusaodigital.oop;

import java.time.LocalDate;
import java.util.*;

/**
 * ========== ENCAPSULAMENTO ==========
 * Encapsulamento: Proteção de dados usando propriedades privadas
 * Controle de acesso através de getters e setters
 */

/**
 * Classe com Encapsulamento
 * Protege dados sensíveis e valida antes de alterar
 */
public class Usuario {
    // Propriedades privadas (não acessíveis diretamente de fora)
    private int id;
    private String nome;
    private String email;
    private int nivelAlfabetizacao; // 0 a 100%
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
        return email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    // Método público
    public void exibirPerfil() {
        System.out.println("""
            
              ========== PERFIL DO USUÁRIO ==========
              ID: %d
              Nome: %s
              Email: %s
              Nível de Alfabetização: %d%%
              Data de Cadastro: %s
              ========================================
            """.formatted(id, nome, email, nivelAlfabetizacao, dataCadastro));
    }
}

/**
 * ========== POLIMORFISMO ==========
 */

/**
 * Classe Base: Conteudo
 * Define a interface para diferentes tipos de conteúdo
 */
public abstract class Conteudo {
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

    // Método abstrato que será implementado pelas classes filhas (Polimorfismo)
    public abstract void exibir();

    // Método abstrato que será implementado pelas classes filhas
    public abstract String obterDuracao();

    // Método comum a todas as classes
    public void incrementarVisualizacoes() {
        this.visualizacoes++;
    }

    public String obterInformacoes() {
        return String.format("""
              Título: %s
              Autor: %s
              Visualizações: %d
              Data de Criação: %s
            """, titulo, autor, visualizacoes, dataCriacao);
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public String getAutor() {
        return autor;
    }
}

/**
 * Classe Filha 1: Video
 * Implementação específica para vídeos
 */
public class Video extends Conteudo {
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
        System.out.println(String.format("""
              
              📹 === VÍDEO ===
              %s
              Duração: %d minutos
              Resolução: %s
              ================
            """, obterInformacoes(), duracao, resolucao));
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
public class Artigo extends Conteudo {
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
        System.out.println(String.format("""
              
              📰 === ARTIGO ===
              %s
              Quantidade de Palavras: %d
              Categoria: %s
              ================
            """, obterInformacoes(), quantidadePalavras, categoria));
    }

    // Polimorfismo: Sobrescreve o método obterDuracao()
    @Override
    public String obterDuracao() {
        // Tempo de leitura estimado: 200 palavras por minuto
        int tempoLeitura = (int) Math.ceil((double) quantidadePalavras / 200);
        return tempoLeitura + " minutos de leitura";
    }

    // Método exclusivo desta classe
    public String obterResumo() {
        int tamanhoMaximo = Math.min(100, descricao.length());
        return descricao.substring(0, tamanhoMaximo) + "...";
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
public class Tutorial extends Conteudo {
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
        System.out.println(String.format("""
              
              🎓 === TUTORIAL ===
              %s
              Número de Etapas: %d
              Nível de Dificuldade: %s
              Progresso: %d/%d
              ===================
            """, obterInformacoes(), numeroEtapas, nivelDificuldade, etapasConcluidas, numeroEtapas));
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
            return String.format("✓ Etapa %d/%d concluída (%.0f%%)", etapasConcluidas, numeroEtapas, percentual);
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
 * Classe para processar conteúdos polimorficamente
 */
class ProcessadorConteudo {
    public static void processarConteudo(Conteudo conteudo) {
        System.out.println("\n--- Processando Conteúdo ---");

        // Cada classe filha implementa exibir() de forma diferente
        conteudo.exibir();

        // Cada classe filha implementa obterDuracao() de forma diferente
        System.out.println("Duração/Tempo: " + conteudo.obterDuracao());

        conteudo.incrementarVisualizacoes();
    }

    public static void exibirCatalogo(List<Conteudo> conteudos) {
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
 * Classe AVANÇADA: REPOSITÓRIO COM ENCAPSULAMENTO
 */
public class RepositorioConteudo {
    // Propriedade privada
    private List<Conteudo> conteudos;

    public RepositorioConteudo() {
        this.conteudos = new ArrayList<>();
    }

    // Método para adicionar conteúdo
    public void adicionarConteudo(Conteudo conteudo) {
        if (conteudo instanceof Conteudo) {
            conteudos.add(conteudo);
            System.out.println("✓ Conteúdo \"" + conteudo.getTitulo() + "\" adicionado ao repositório");
        } else {
            System.out.println("✗ Erro: Objeto não é uma instância de Conteudo");
        }
    }

    // Getter para obter todos os conteúdos (retorna cópia para proteger)
    public List<Conteudo> obterConteudos() {
        return new ArrayList<>(conteudos);
    }

    // Método para buscar conteúdo por título
    public List<Conteudo> buscarPorTitulo(String titulo) {
        return conteudos.stream()
            .filter(c -> c.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
            .toList();
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

    // Classe interna para estatísticas
    public static class Estatisticas {
        public int totalConteudos;
        public int totalVisualizacoes;
        public double mediaVisualizacoes;

        public Estatisticas(int totalConteudos, int totalVisualizacoes, double mediaVisualizacoes) {
            this.totalConteudos = totalConteudos;
            this.totalVisualizacoes = totalVisualizacoes;
            this.mediaVisualizacoes = mediaVisualizacoes;
        }
    }

    // Método para gerar estatísticas
    public Estatisticas gerarEstatisticas() {
        int totalConteudos = conteudos.size();
        int totalVisualizacoes = conteudos.stream()
            .mapToInt(Conteudo::getVisualizacoes)
            .sum();
        double mediaVisualizacoes = totalConteudos > 0 ? (double) totalVisualizacoes / totalConteudos : 0;

        return new Estatisticas(totalConteudos, totalVisualizacoes, mediaVisualizacoes);
    }

    // Método para exibir estatísticas
    public void exibirEstatisticas() {
        Estatisticas stats = gerarEstatisticas();
        System.out.println(String.format("""
              
              ========== ESTATÍSTICAS DO REPOSITÓRIO ==========
              Total de Conteúdos: %d
              Total de Visualizações: %d
              Média de Visualizações por Conteúdo: %.2f
              ================================================
            """, stats.totalConteudos, stats.totalVisualizacoes, stats.mediaVisualizacoes));
    }
}

/**
 * Classe com exemplos de uso completo
 */
public class ExemploOOP {
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("1️⃣  DEMONSTRAÇÃO DE ENCAPSULAMENTO");
        System.out.println("=".repeat(60));

        // Criando usuários com encapsulamento
        Usuario usuario1 = new Usuario(1, "João Silva", "joao@email.com");
        usuario1.exibirPerfil();

        // Tentando alterar através de setters (com validação)
        System.out.println("\n--- Alterando dados do usuário ---");
        usuario1.setNome("Jo"); // Erro: menos de 3 caracteres
        usuario1.setNome("João Pedro Silva"); // Sucesso
        usuario1.setEmail("email-invalido"); // Erro: email inválido
        usuario1.setEmail("joao.silva@email.com"); // Sucesso
        usuario1.setNivelAlfabetizacao(150); // Erro: fora do intervalo
        usuario1.setNivelAlfabetizacao(75); // Sucesso

        usuario1.exibirPerfil();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("2️⃣  DEMONSTRAÇÃO DE POLIMORFISMO");
        System.out.println("=".repeat(60));

        // Criando diferentes tipos de conteúdo (polimorfismo)
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
        ProcessadorConteudo.processarConteudo(video);
        ProcessadorConteudo.processarConteudo(artigo);
        ProcessadorConteudo.processarConteudo(tutorial);

        // Simulando múltiplas visualizações
        System.out.println("\n--- Simulando Visualizações ---");
        for (int i = 0; i < 5; i++) {
            artigo.incrementarVisualizacoes();
        }
        for (int i = 0; i < 10; i++) {
            video.incrementarVisualizacoes();
        }
        tutorial.completarEtapa();
        tutorial.completarEtapa();
        tutorial.completarEtapa();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("3️⃣  DEMONSTRAÇÃO DE REPOSITÓRIO COM ENCAPSULAMENTO");
        System.out.println("=".repeat(60));

        // Criando repositório e adicionando conteúdos
        RepositorioConteudo repositorio = new RepositorioConteudo();

        repositorio.adicionarConteudo(video);
        repositorio.adicionarConteudo(artigo);
        repositorio.adicionarConteudo(tutorial);

        // Exibindo catálogo completo (usando polimorfismo)
        ProcessadorConteudo.exibirCatalogo(repositorio.obterConteudos());

        // Buscando conteúdo
        System.out.println("\n--- Buscando Conteúdo ---");
        List<Conteudo> resultados = repositorio.buscarPorTitulo("Alfabetização");
        System.out.println("Encontrados " + resultados.size() + " conteúdo(s) com \"Alfabetização\"");
        resultados.forEach(r -> System.out.println("  - " + r.getTitulo()));

        // Conteúdo mais visualizado
        System.out.println("\n--- Conteúdo Mais Visualizado ---");
        Conteudo maisVisualizado = repositorio.obterMaisVisualizado();
        if (maisVisualizado != null) {
            System.out.println("Título: " + maisVisualizado.getTitulo());
            System.out.println("Visualizações: " + maisVisualizado.getVisualizacoes());
        }

        // Exibindo estatísticas
        repositorio.exibirEstatisticas();

        System.out.println("\n✅ Demonstração Completa Finalizada!");
    }
}
