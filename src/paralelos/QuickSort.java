package paralelos;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// QuickSort com paralelismo
// ForkJoinPool(para dividir e conquistar) e a RecursiveAction (para ordenar)
// limite (threshold): se for pequeno so chama o serial msm
// particionamento (serial): o paticionamente atual continua sendo feito pelo QuickSort serial
// divide (fork): com o pivo no canto certo, cria duas novas tarefas (RecursiveAction): uma para a metade esquerda, outra para a direita
// executa: chama invokeAll() para rodar a ordenação do lado esquerdo e direito ao mesmo tempo em threads diferentes
// conquista (join): espera as tarefas terminarem

public class QuickSort {
    public static void quickSortParalelo(int[] array) {
        ForkJoinPool threadPool = new ForkJoinPool();

        int nucleos = Runtime.getRuntime().availableProcessors();
        //int nucleos = 8;
        System.out.println("Quantidade de Threads: " + nucleos);

        // manda o array todo pra ThreadPool
        threadPool.invoke(new TarefaQuickDaSilva(array, 0, array.length - 1));

    }
    // RecursiveAction serve para criar tarefas independentes
    static class TarefaQuickDaSilva extends RecursiveAction {
        private int[] array;
        private int inicio;
        private int fim;

        // se o array for pequeno, chama o serial msm pq usar thread eh putaria
        private static final int LIMITE = 100;

        public TarefaQuickDaSilva(int[] array, int inicio, int fim) {
            this.array = array;
            this.inicio = inicio;
            this.fim = fim;
        }

        @Override
        protected void compute() {
            if (inicio < fim) {
                if ((fim - inicio) < LIMITE) {
                    seriais.QuickSort.quickDaSilva(array, inicio, fim);
                } else {
                    //particiona
                    int lugarCertoDoPivo = separarOsBabados(array, inicio, fim);

                    // cria duas bichinha
                    TarefaQuickDaSilva tarefaEsquerda = new TarefaQuickDaSilva(array, inicio, lugarCertoDoPivo - 1);
                    TarefaQuickDaSilva tarefaDireita = new TarefaQuickDaSilva(array, lugarCertoDoPivo + 1, fim);

                    // ao msm tempo bota as bicha pra rodar (Fork) e ja espera a resposta (Join)
                    invokeAll(tarefaEsquerda, tarefaDireita);
                }
            }
        }

        // a parte la do limite pequeno chama o serial msm qie ele resolve
        private int separarOsBabados(int[] arrayzinho, int inicio, int fim) {
            int pivoChefe = arrayzinho[fim];
            int separacaoMenores = inicio - 1;

            for (int explorador = inicio; explorador < fim; explorador++) {
                if (arrayzinho[explorador] <= pivoChefe) {
                    separacaoMenores++;
                    trocaDeLugarBabadeira(arrayzinho, separacaoMenores, explorador);
                }
            }
            int posicaoFinalDoPivo = separacaoMenores + 1;
            trocaDeLugarBabadeira(arrayzinho, posicaoFinalDoPivo, fim);
            return posicaoFinalDoPivo;
        }

        private void trocaDeLugarBabadeira(int[] arrayzinho, int posicaoNhomp, int posicaoGlup) {
            int guardandoLugar = arrayzinho[posicaoNhomp];
            arrayzinho[posicaoNhomp] = arrayzinho[posicaoGlup]; // Nhomp comwe o Glup (por isso ele ta glup)
            arrayzinho[posicaoGlup] = guardandoLugar;  // Glup recebe o antigo Nhomp
        }
    }
}
