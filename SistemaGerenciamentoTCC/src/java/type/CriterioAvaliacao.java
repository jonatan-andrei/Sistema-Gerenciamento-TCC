package type;

import java.util.Arrays;

public enum CriterioAvaliacao {

    USO_DE_LINGUAGEM("O texto está escrito com uma linguagem formal, sem erros de pontuação, acentuação ou de estrutura gramatical."),
    APRESENTACAO("O trabalho possui boa apresentação, inclui todos os elementos necessários. A aparência em geral é limpa e organizada."),
    ESTRUTURA_DO_TEXTO("O texto está bem estruturado apresentando as seções com resumo, introdução, fundamentação teórica, objetivos do trabalho, metodologia, cronograma de trabalho e referências bibliográficas."),
    CONTEUDO_DO_TEXTO("A introdução contextualiza bem o tema escolhido. Os objetivos são claros. O referencial teórico está bem elaborado, com referências bibliográficas significativas dentro do tema proposto."),
    RELEVANCIA_AO_PERFIL_PROFISSIONAL("O tema sugerido é relevante. O sistema proposto, para ser desenvolvido, demanda aplicação dos conteúdos trabalhados ao longo do curso. A proposta está de acordo com o perfil de formação profissional contido no PPC.");

    private String descricao;

    private CriterioAvaliacao(String descricao) {
        this.descricao = descricao;
    }

    public static CriterioAvaliacao buscarPorString(String string) {
        return Arrays.asList(values()).stream()
                .filter(criterio -> criterio.name().equals(string))
                .findFirst().orElse(null);
    }

}
