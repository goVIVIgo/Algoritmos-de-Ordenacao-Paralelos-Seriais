package paralelos;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// MergeSort com paralelismo
// ForkJoinPool(para dividir) e a RecursiveAction (para conquistar)
// passo a paso: divide (com fork), conquista (com join), combina tude
// limite (threshold): se o array tiver menos de um certo tamanho, usa o MergeSort clássico pra nn gastar tempo criado thread pra paralelismo
// divide: encontra o meio do array e cria duas novas tarefas (RecursiveAction) - uma para a metade esquerda, outra para a direita
// fork (despacha uma tarefa): envia as duas tarefas para rodarem ao msm tempo com o invokeAll()
// join (espera um tico): a thread aguarda os dois trecos terminarem de ordenar
// combina: usa o MergeSort para combinar as duas metades
// Complexidade: O(n log n)


public class MergeSort {
    public static void mergeSortParalelo(int[] array) {
        ForkJoinPool threadPool = new ForkJoinPool();
        int[] arrayAuxiliar = new int[array.length];

        int nucleos = Runtime.getRuntime().availableProcessors();
        //int nucleos = 8;
        System.out.println("Quantidade de Threads: " + nucleos);

        threadPool.invoke(new TarefaMerge(array, arrayAuxiliar, 0, array.length - 1));
    }

    static class TarefaMerge extends RecursiveAction {
        private int[] array;
        private int[] arrayAuxiliar;
        private int inicio;
        private int fim;

        private static final int LIMITE = 100;

        public TarefaMerge(int[] array, int[] arrayAuxiliar, int inicio, int fim) {
            this.array = array;
            this.arrayAuxiliar = arrayAuxiliar;
            this.inicio = inicio;
            this.fim = fim;
        }

        @Override
        protected void compute() {
            if (inicio < fim) {
                if ((fim - inicio) < LIMITE) {
                    dividirParaConquistarSerial(array, arrayAuxiliar, inicio, fim);
                } else {
                    // Particiona
                    int meio = (inicio + fim) / 2;

                    //cria as tarefas pras duas metades
                    TarefaMerge tarefaEsquerda = new TarefaMerge(array, arrayAuxiliar, inicio, meio);
                    TarefaMerge tarefaDireita = new TarefaMerge(array, arrayAuxiliar, meio + 1, fim);

                    // Fork (despacha as duas) e Join (espera um tico terminarem)
                    invokeAll(tarefaEsquerda, tarefaDireita);

                    // junta os babados usando aquele mesmo código de mesclar
                    juntarOsBabados(array, arrayAuxiliar, inicio, meio, fim);
                }
            }
        }

        // se for serial:
        private void dividirParaConquistarSerial(int[] array, int[] arrayAuxiliar, int inicio, int fim) {
            if (inicio < fim) {
                int meio = (inicio + fim) / 2;
                dividirParaConquistarSerial(array, arrayAuxiliar, inicio, meio);
                dividirParaConquistarSerial(array, arrayAuxiliar, meio + 1, fim);
                juntarOsBabados(array, arrayAuxiliar, inicio, meio, fim);
            }
        }

        private void juntarOsBabados(int[] arrayzinho, int[] arrayAuxiliar, int inicio, int meio, int fim) {
            for (int i = inicio; i <= fim; i++) {
                arrayAuxiliar[i] = arrayzinho[i];
            }

            int ponteiroEsquerda = inicio;
            int ponteiroDireita = meio + 1;
            int posicaoAtual = inicio;

            while (ponteiroEsquerda <= meio && ponteiroDireita <= fim) {
                if (arrayAuxiliar[ponteiroEsquerda] <= arrayAuxiliar[ponteiroDireita]) {
                    arrayzinho[posicaoAtual] = arrayAuxiliar[ponteiroEsquerda];
                    ponteiroEsquerda++;
                } else {
                    arrayzinho[posicaoAtual] = arrayAuxiliar[ponteiroDireita];
                    ponteiroDireita++;
                }
                posicaoAtual++;
            }

            while (ponteiroEsquerda <= meio) {
                arrayzinho[posicaoAtual] = arrayAuxiliar[ponteiroEsquerda];
                posicaoAtual++;
                ponteiroEsquerda++;
            }
        }
    }
}
