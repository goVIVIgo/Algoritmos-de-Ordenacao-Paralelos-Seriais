package paralelos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// bubble sort paralelo (ordenação par-ímpar)
// complexidade: O(n)
// pior caso: O(n^2)
// saida pra paralelizar: usar a variação par-ímpar, que separa os pares para que não se sobreponham.
// utilizar: ExecutorService (pool de threads) junto de uma barreira de sincronização(como Phaser ou CyclicBarrier).

// ordenado_flag = false
//impar: dispara multiplas threads que vão olhar so para pares onde o indice inicial eh impar (1 e 2, 3 e 4, 5 e 6...)
//barrreira  de sincronizacao: aguarda todo mundo terminar a fase impar
//pares: dispara multiplas threads que vao olhar so para pares onde o indice inicial eh par (0 e 1, 2 e 3, 4 e 5...), troca se for preciso ne mor
//barrreira de sincronizacao: aguarda todo mundo terminar a fase par
// se nenhuma thread tiver feito nenhuma troca durante a rodada inteira (somando a fase ímpar e a fase par), então:
// ordedenado_flag = true

//obs: um bichinho não toca no outro, as threads não brigam pela mesma posição do array, impar pega impar e par pega par


public class BubbleSort {

    public static void bubbleSortParalelo(int[] array) {
        int tamanho = array.length;
        boolean ordenado = false;

        int nucleos = Runtime.getRuntime().availableProcessors();
        //int nucleos = 8;
        System.out.println("Quantidade de Threads: " + nucleos);
        ExecutorService threadPool = Executors.newFixedThreadPool(nucleos);

        while (!ordenado) {
            ordenado = true;
            // fase ímpar
            boolean trocaImpar = rodarFase(array, 1, tamanho, nucleos, threadPool);

            // fase par
            boolean trocaPar = rodarFase(array, 0, tamanho, nucleos,   threadPool);

            // se nn houver troca ent ta tudo certo
            if (!trocaImpar && !trocaPar) {
                ordenado = true;
            } else {
                ordenado = false;
            }
        }
        threadPool.shutdown();
    }

    private static boolean rodarFase(int[] array, int inicioFase, int tamanho, int nucleos, ExecutorService pool) {
        List<Callable<Boolean>> tarefas = new ArrayList<>();
        // tamanho do bloco que cada nucleo vai ta processandop
        int tamanhoBloco = tamanho / nucleos;

        for (int i = 0; i < nucleos; i++) {
            int inicioBloco = inicioFase + (i * tamanhoBloco);
            // pual de 2 em 2 por causa da regra la
            if (inicioBloco % 2 != inicioFase % 2) {
                inicioBloco++;
            }

            int fimBloco = (i == nucleos - 1) ? tamanho - 1 : inicioBloco + tamanhoBloco;

            tarefas.add(new TarefaBubbleImpaPar(array, inicioBloco, fimBloco));
        }

        boolean houveAlgumaTroca = false;
        try {
            // iunvokeAll = barreira de sincronizacao, trava aqui e so avança quando todas as threads terminarem a fase delas
            var resultados = pool.invokeAll(tarefas);
            for (var resultado : resultados) {
                if (resultado.get()) {
                    houveAlgumaTroca = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return houveAlgumaTroca;
    }

    // tarefa individual
    static class TarefaBubbleImpaPar implements Callable<Boolean> {
        private int[] array;
        private int inicio;
        private int fim;

        public TarefaBubbleImpaPar(int[] array, int inicio, int fim) {
            this.array = array;
            this.inicio = inicio;
            this.fim = fim;
        }

        @Override
        public Boolean call() {
            boolean trocouLocal = false;

            for (int i = inicio; i < fim - 1; i += 2) {
                if (array[i] > array[i + 1]) {
                    int guardador = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = guardador;
                    trocouLocal = true;
                }
            }
            return trocouLocal;
        }
    }
}