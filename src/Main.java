public class Main{
    /**
     * Método principal que processa os argumentos da linha de comando e inicia o jogo.
     *
     * @author Bruno Ricardo da Silva Reis
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        // Definindo as variáveis padrão
        int width = 10;
        int height = 10;
        int generations = 10;
        int speed = 500;
        String population = null;

        // Processando os argumentos da linha de comando
        for (String arg : args) {
            String[] parts = arg.split("=");
            if (parts.length == 2) {
                String paramName = parts[0].toLowerCase();
                String paramValue = parts[1];

                switch (paramName) {
                    case "w":
                        try {
                            width = Integer.parseInt(paramValue);
                        } catch (NumberFormatException e) {
                            System.out.println("width = [Inválido]");
                        }
                        break;
                    case "h":
                        try {
                            height = Integer.parseInt(paramValue);
                        } catch (NumberFormatException e) {
                            System.out.println("height = [Inválido]");
                        }
                        break;
                    case "g":
                        try {
                            generations = Integer.parseInt(paramValue);
                        } catch (NumberFormatException e) {
                            System.out.println("generations = [Inválido]");
                        }
                        break;
                    case "s":
                        try {
                            speed = Integer.parseInt(paramValue);
                        } catch (NumberFormatException e) {
                            System.out.println("speed = [Inválido]");
                        }
                        break;
                    case "p":
                        population = paramValue;
                        break;
                    default:
                        System.out.println("Parâmetro desconhecido: " + paramName);
                }
            } else {
                System.out.println("Argumento inválido: " + arg);
            }
        }

        // Inicializando e executando o jogo
        GameOfLife game = new GameOfLife(width, height);
        if (population != null) {
            game.populateFromString(population);
        } else {
            game.randomizeGrid();
        }
        game.start();
        game.runGenerations(generations, speed);
    }
}

