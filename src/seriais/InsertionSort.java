package seriais;

// insertion sort serial
// complexidade: O(n)
// pior caso: O(n^2)
// inicio: assume que o primeiro elemento eh uma sub lista ordenada
// varre o array a partir do segundo elemento
// seleciona o valor do elemento atual
// compara com os elementos da sub-lista a esquerda de tras para frente
// enquanto o elemento analisado for maior que a carta, move ele uma posicao para a direita
// quando encontrar um elemento menor (ou chegar no inicio do array), insere a carta na posicao que ficou vazia

public class InsertionSort {
    public static void insertionSort(int[] arrayzinho) {
        // supose que o primeiro elemento eh uma sub lista ordenada
        for (int explorador = 1; explorador < arrayzinho.length; explorador++) {

            int elementoDaVez = arrayzinho[explorador];
            int comparadorEsquerdo = explorador - 1;

            // enquanto o elemento analisado for maior que o elemento a esquerda, move ele uma posicao para a direita
            while (comparadorEsquerdo >= 0 && arrayzinho[comparadorEsquerdo] > elementoDaVez) {
                arrayzinho[comparadorEsquerdo + 1] = arrayzinho[comparadorEsquerdo];
                comparadorEsquerdo--;
            }
            arrayzinho[comparadorEsquerdo + 1] = elementoDaVez;
        }
    }
}