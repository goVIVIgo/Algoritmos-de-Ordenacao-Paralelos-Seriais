import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class App {
    public static void main(String[] args) {

        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("Quantidade de Núcleos: " + nucleos);

        int tamanhoDoArray = 10_000_000;
        System.out.println("Array de " + tamanhoDoArray + " elementos.");

        int tamanhoDoArrayInsertion = 100_000;
        System.out.println("Array do InsertionSort " + tamanhoDoArrayInsertion + " elementos.");

        int tamanhoDoArrayBubble = 100_000;
        System.out.println("Array do BubbleSort " + tamanhoDoArrayBubble + " elementos.");

        StringBuilder dadosCsv = new StringBuilder();

        dadosCsv.append("Threads;Algoritmo;Tamanho_Array;Tempo_Serial_ms;Tempo_Paralelo_ms\n");

        int[] arrayOriginal = gerarArrayAleatorio(tamanhoDoArray);
        //int[] arrayOriginal = gerarArrayAleatorio(tamanhoDoArrayInsertion);
        //int[] arrayOriginal = gerarArrayAleatorio(tamanhoDoArrayBubble);
        int[] arrayProSerial = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
        int[] arrayProParalelo = Arrays.copyOf(arrayOriginal, arrayOriginal.length);

        // QuickSort
        System.out.println("QuickSort Serial: ");
        long tempoInicioSerial = System.currentTimeMillis();
        seriais.QuickSort.quickDaSilva(arrayProSerial, 0, arrayProSerial.length - 1);
        long tempoFimSerial = System.currentTimeMillis();
        long duracaoSerial = tempoFimSerial - tempoInicioSerial;
        System.out.println("Tempo do Serial: " + duracaoSerial + " milissegundos.");

        System.out.println("QuickSort Paralelo: ");
        long tempoInicioParalelo = System.currentTimeMillis();
        paralelos.QuickSort.quickSortParalelo(arrayProParalelo);
        long tempoFimParalelo = System.currentTimeMillis();
        long duracaoParalelo = tempoFimParalelo - tempoInicioParalelo;
        System.out.println("Tempo do Paralelo: " + duracaoParalelo + " milissegundos.");

        dadosCsv.append(nucleos).append(";")
                .append("QuickSort;").append(tamanhoDoArray).append(";")
                .append(duracaoSerial).append(";").append(duracaoParalelo).append("\n");

        // MergeSort
        arrayOriginal = gerarArrayAleatorio(tamanhoDoArray);
        arrayProSerial = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
        arrayProParalelo = Arrays.copyOf(arrayOriginal, arrayOriginal.length);

        System.out.println("MergeSort Serial: ");
        long tempoInicioMergeSerial = System.currentTimeMillis();
        seriais.MergeSort.mergeDaSilva(arrayProSerial);
        long tempoFimMergeSerial = System.currentTimeMillis();
        long duracaoMergeSerial = tempoFimMergeSerial - tempoInicioMergeSerial;
        System.out.println("Tempo do MergeSort Serial: " + duracaoMergeSerial + " milissegundos.");

        System.out.println("MergeSort Paralelo: ");
        long tempoInicioMergeParalelo = System.currentTimeMillis();
        paralelos.MergeSort.mergeSortParalelo(arrayProParalelo);
        long tempoFimMergeParalelo = System.currentTimeMillis();
        long duracaoMergeParalelo = tempoFimMergeParalelo - tempoInicioMergeParalelo;
        System.out.println("Tempo do MergeSort Paralelo: " + duracaoMergeParalelo + " milissegundos.");

        dadosCsv.append(nucleos).append(";")
                .append("MergeSort;").append(tamanhoDoArray).append(";")
                .append(duracaoMergeSerial).append(";").append(duracaoMergeParalelo).append("\n");

        // InsertionSort
        arrayOriginal = gerarArrayAleatorio(tamanhoDoArrayInsertion);
        arrayProSerial = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
        arrayProParalelo = Arrays.copyOf(arrayOriginal, arrayOriginal.length);

        System.out.println("InsertionSort Serial: ");
        long tempoInicioInsertionSerial = System.currentTimeMillis();
        seriais.InsertionSort.insertionSort(arrayProSerial);
        long tempoFimInsertionSerial = System.currentTimeMillis();
        long duracaoInsertionSerial = tempoFimInsertionSerial - tempoInicioInsertionSerial;
        System.out.println("Tempo do InsertionSort Serial: " + duracaoInsertionSerial + " milissegundos.");

        System.out.println("InsertionSort Paralelo: ");
        long tempoInicioInsertionParalelo = System.currentTimeMillis();
        paralelos.InsertionSort.insertionSortParalelo(arrayProParalelo);
        long tempoFimInsertionParalelo = System.currentTimeMillis();
        long duracaoInsertionParalelo = tempoFimInsertionParalelo - tempoInicioInsertionParalelo;
        System.out.println("Tempo do InsertionSort Paralelo: " + duracaoInsertionParalelo + " milissegundos.");

        dadosCsv.append(nucleos).append(";")
                .append("InsertionSort;").append(tamanhoDoArrayInsertion).append(";")
                .append(duracaoInsertionSerial).append(";").append(duracaoInsertionParalelo).append("\n");

        // BubbleSort
        arrayOriginal = gerarArrayAleatorio(tamanhoDoArrayBubble);
        arrayProSerial = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
        arrayProParalelo = Arrays.copyOf(arrayOriginal, arrayOriginal.length);

        System.out.println("BubbleSort Serial: ");
        long tempoInicioBubbleSerial = System.currentTimeMillis();
        seriais.BubbleSort.bubbleDaSilva(arrayProSerial);
        long tempoFimBubbleSerial = System.currentTimeMillis();
        long duracaoBubbleSerial = tempoFimBubbleSerial - tempoInicioBubbleSerial;
        System.out.println("Tempo do BubbleSort Serial: " + duracaoBubbleSerial + " milissegundos.");

        System.out.println("BubbleSort Paralelo: ");
        long tempoInicioBubbleParalelo = System.currentTimeMillis();
        paralelos.BubbleSort.bubbleSortParalelo(arrayProParalelo);
        long tempoFimBubbleParalelo = System.currentTimeMillis();
        long duracaoBubbleParalelo = tempoFimBubbleParalelo - tempoInicioBubbleParalelo;
        System.out.println("Tempo do BubbleSort Paralelo: " + duracaoBubbleParalelo + " milissegundos.");

        dadosCsv.append(nucleos).append(";")
                .append("BubbleSort;").append(tamanhoDoArrayBubble).append(";")
                .append(duracaoBubbleSerial).append(";").append(duracaoBubbleParalelo).append("\n");


        System.out.println("Salvando no csv");
        try (PrintWriter escritorCSV = new PrintWriter(new FileWriter("resultados_ordenacao.csv", true))) {
            escritorCSV.print(dadosCsv.toString());
            System.out.println("arquivi gerado");
        } catch (IOException e) {
            System.out.println("erro");
            e.printStackTrace();
        }

    }

    private static int[] gerarArrayAleatorio(int tamanho) {
        Random aleatorio = new Random();
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = aleatorio.nextInt(1_000_000);
        }
        return array;
    }
}

// Resultados: QuickSort
// o paralelo foi mto melhor que o serial, isso prova que dividir o trabalho entre os nucleos é muito mais eficiente do que deixar tudo na thread principal
// porém so valeu a pena pq era gigantamente gigante, se tivesse sido menorzinho o serial teria sido mais útil, nem exatamente eficiente, mas nn tem pq matar uma formiga com um canhão.
// Lei de Amdahl: "Nhomp come Gluo" eh sequencial e nn da para ser paralelizado ai eh babado, essa parte do código sempre vai ser um "problema"
// testes:      Serial(ms)      Paralelo(ms)         Ganho de Desempenho (Speedup)
// 1                776            174~              4.4x mais rápido
// 2                1481           203~              7.2x mais rápido
// 3                1596           215~              7.4x mais rápido
// 4                1627           224~              7.2x mais rápido
// 5                1243           237~              5.2x mais rápido

// Resultados: MergeSort
// o paralelo foi dnv mto melhor do que o serial, o nucleos estão nucleando bb!
// o mergesort diferente do quicksort, se manteve numa media muito crua, por causa do O(n log n) no melhor, médio e pior caso, ai ele divide na metade e sempre se mantém previsivel old
// quicksort vs mergesort: o quicksort foi melhor pq ele usa menos memória ram, o mergesort precisa ficar copiando os dados para aquele array auxiliar ("array de rascunho") para juntar as metades, isso acaba custando alguns segundos, ja o quicksort faz isso no proprio array real msm
// testes:      Serial(ms)      Paralelo(ms)         Ganho de Desempenho (Speedup)
// 1                1168           257~              4.5x mais rápido
// 2                1210           293~              4.1x mais rápido
// 3                1226           251~              4.9x mais rápido
// 4                1191           265~              4.5x mais rápido
// 5                1209           264~              4.6x mais rápido

// Resultados: InsertionSort
// o paralelo foi muito muito mais rápido do que o serial. Pelo Insertion ser O(n^2), o tempo cresce de forma quadrática, o array foi dividido de um bloco de 100.000 pra blocos de 10.000, ordenar 10 pedacinhos eh mto mais rapido do que um pedação
// Insertion Serial demorou mais ou menos 1.2 segundos para ordenar a so 100 mil elementos esse fudido, quicksort e mergesort demoraram esse MESMO tempo para ordenar 10 MILHÕES. Por isso um eh O(n^2) - cresce de forma exponencial e meio ruimn, e o outro eh O(n log n) - sempre linear
// Insertion Paralelo só foi tão bem assim pq foi usado o dividir do ForkJoin e o combinar do mergesort, um insertion normal jamais faria isso mor.
// testes:      Serial(ms)      Paralelo(ms)         Ganho de Desempenho (Speedup)
// 1                1291             39                ~33.1x mais rápido
// 2                1234             39                ~31.6x mais rápido
// 3                1211             44                ~27.5x mais rápido
// 4                1241             49                ~25.3x mais rápido
// 5                1225             44                ~27.8x mais rápido

// Resultados: BubbleSort
// o paralelo foi bem melhor que o serial, mas em condições especificas, pq aqui o dataset de teste era mto grande e o ubble sort eh  O(n^2), então pra 100.000, ele precisou fazer 100.000 x 100.000, 10 bilhoes eh um babado enorme ne
// continuacao: ai o tempo de sincronizar as 12 threads pra impa par ficou menos custoso do que fazer um calculo tao gigantamente gigante
// divide o array de 100.000 trecos em 12 blocos (pq eh o tanto de nucleo que eu tenho), nas fases impa e par, os 12 núcleos trabalham simultaneamente dentro de seus próprios blocos
// era supostamente pras barreiras do invokeAll serem super demoradas, mas nesse caso especifico aqui nn foram pq era mto grande, cm um volume de dados menor o seria teria vencido
// testes:      Serial(ms)      Paralelo(ms)         Ganho de Desempenho (Speedup)
// 1                11959            656               ~18.2x mais rápido
// 2                12291            650               ~18.9x mais rápido
// 3                12420            698               ~17.8x mais rápido
// 4                12317            644               ~19.1x mais rápido
// 5                12199            665               ~18.3x mais rápido

