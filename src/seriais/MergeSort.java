package seriais;

// MergeSort serial
// dividir para conquistar em uma única thread:
// divide: O array eh dividido recursivamente ao meio até que cada sub-array contenha apenas um elemento
// conquistar: o array dividido eh ordenado recursivamente
// combinar: o array dividido eh combinado em um array ordenado
// complexidade: O(n log n)

public class MergeSort {
    public static void mergeDaSilva(int[] arrayzinho) {
        int[] arrayAuxiliar = new int[arrayzinho.length];

        dividirParaConquistar(arrayzinho, arrayAuxiliar, 0, arrayzinho.length - 1);
    }

    private static void dividirParaConquistar(int[] arrayzinho, int[] arrayAuxiliar, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;

            //esquerda
            dividirParaConquistar(arrayzinho, arrayAuxiliar, inicio, meio);
            //direita
            dividirParaConquistar(arrayzinho, arrayAuxiliar, meio + 1, fim);

            juntarOsBabados(arrayzinho, arrayAuxiliar, inicio, meio, fim);
        }
    }

    private static void juntarOsBabados(int[] arrayzinho, int[] arrayAuxiliar, int inicio, int meio, int fim) {
        // copia o array original para o auxiliar
        for (int i = inicio; i <= fim; i++) {
            arrayAuxiliar[i] = arrayzinho[i];
        }

        int ponteiroEsquerda = inicio;    // omeço da metade esquerda
        int ponteiroDireita = meio + 1;  // começo da metade direita
        int posicaoAtual = inicio; //começa do começo ne

        while (ponteiroEsquerda <= meio && ponteiroDireita <= fim) {
            if (arrayAuxiliar[ponteiroEsquerda] <= arrayAuxiliar[ponteiroDireita]) {
                arrayzinho[posicaoAtual] = arrayAuxiliar[ponteiroEsquerda];
                ponteiroEsquerda++; // esquerda, anda pra frente
            } else {
                arrayzinho[posicaoAtual] = arrayAuxiliar[ponteiroDireita];
                ponteiroDireita++;  // direita, anda pra frente
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