package paralelos;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// insertion sort paralelo
// abordagem por blocos
// complexidade: O(n)
// pior caso: O(n^2)
// paralelizando: dividir o array, ordenar os pedaços e depois fundir tudo.
// divide em blocos: divide em n blocos, um para cada thread
// cria uma tarefa pra cada bloco e joga no pool de threads pra ta rodando ao msm tempo
// cada bichinha aplica o insertion sort serial no seu bloco
//join: espera as threads terminarem pra combinar os blocos
// combina: usa o MergeSort para combinar os blocos

public class InsertionSort {

    public static void insertionSortParalelo(int[] array) {
        ForkJoinPool threadPool = new ForkJoinPool();

        int[] arrayAuxiliar = new int[array.length];

        int nucleos = Runtime.getRuntime().availableProcessors();
        //int nucleos = 8;
        System.out.println("Quantidade de Threads: " + nucleos);

        threadPool.invoke(new TarefaInsertion(array, arrayAuxiliar, 0, array.length - 1));
    }

    static class TarefaInsertion extends RecursiveAction {
        private int[] array;
        private int[] arrayAuxiliar;
        private int inicio;
        private int fim;

       // se for maior que isso aqui quebra ele nos blocos pra cada thread
        private static final int TAMANHO_DA_PORRA_DO_BLOCO = 10000;

        public TarefaInsertion(int[] array, int[] arrayAuxiliar, int inicio, int fim) {
            this.array = array;
            this.arrayAuxiliar = arrayAuxiliar;
            this.inicio = inicio;
            this.fim = fim;
        }

        @Override
        protected void compute() {
            int tamanhoAtual = fim - inicio + 1;

            if (tamanhoAtual <= TAMANHO_DA_PORRA_DO_BLOCO) {
                insertionSerialNoPedacoDoBabado(array, inicio, fim);
            } else {
                // Particiona
                int meio = (inicio + fim) / 2;

                // cria as tarefas pras duas metades
                TarefaInsertion tarefaEsquerda = new TarefaInsertion(array, arrayAuxiliar, inicio, meio);
                TarefaInsertion tarefaDireita = new TarefaInsertion(array, arrayAuxiliar, meio + 1, fim);

                // Fork (despacha as duas) e Join (espera um tico terminarem)
                invokeAll(tarefaEsquerda, tarefaDireita);

                juntarOsBabados(array, arrayAuxiliar, inicio, meio, fim);
            }
        }

        // ordena um bloco mas nn todo so do inicio ao fim (no serial usa length)
        private void insertionSerialNoPedacoDoBabado(int[] array, int inicio, int fim) {
            // supose que o primeiro elemento eh uma sub lista ordenada
            for (int explorador = inicio + 1; explorador <= fim; explorador++) {

                int elementoDaVez = array[explorador];
                int comparadorEsquerdo = explorador - 1;
                 // enquanto o elemento analisado for maior que o elemento a esquerda, move ele uma posicao para a direita
                while (comparadorEsquerdo >= inicio && array[comparadorEsquerdo] > elementoDaVez) {
                    array[comparadorEsquerdo + 1] = array[comparadorEsquerdo];
                    comparadorEsquerdo--;
                }
                array[comparadorEsquerdo + 1] = elementoDaVez;
            }
        }

        // mergesort pra juntar os treco dps de ordenar os bloquinhos
        private void juntarOsBabados(int[] array, int[] arrayAuxiliar, int inicio, int meio, int fim) {
            for (int i = inicio; i <= fim; i++) {
                arrayAuxiliar[i] = array[i];
            }

            int ponteiroEsq = inicio;
            int ponteiroDir = meio + 1;
            int posicaoAtual = inicio;

            while (ponteiroEsq <= meio && ponteiroDir <= fim) {
                if (arrayAuxiliar[ponteiroEsq] <= arrayAuxiliar[ponteiroDir]) {
                    array[posicaoAtual] = arrayAuxiliar[ponteiroEsq];
                    ponteiroEsq++;
                } else {
                    array[posicaoAtual] = arrayAuxiliar[ponteiroDir];
                    ponteiroDir++;
                }
                posicaoAtual++;
            }

            while (ponteiroEsq <= meio) {
                array[posicaoAtual] = arrayAuxiliar[ponteiroEsq];
                posicaoAtual++;
                ponteiroEsq++;
            }
        }
    }
}
