package seriais;

// subble bort serial
// complexidade: O(n)
// pior caso: O(n^2)
// passo a passo: compara e troca
// laço externo: repete a varredura várias vezes para garantir que os maiores "borbulhem" para o final do array
// laço interno: varre o array comparando pares adjacentes (o atual com o seguinte)
// condição de troca: se o babado atual for MAIOR que o babado seguinte, troca os dois de lugar
// condição de avanço: se o atual for menor ou igual ao seguinte, não faz nada e continua o laço
// obs: o laço interno sempre para no penúltimo elemento, pois ele vai ser comparado com o último


public class BubbleSort {

    public static void bubbleDaSilva(int[] array) {
        int tamanho = array.length;
        boolean houveTroca;
    
        for (int i = 0; i < tamanho - 1; i++) {
            houveTroca = false;

            // vai varrendo o array e vai empurrando o maior bichinho pro final
            for (int j = 0; j < tamanho - i - 1; j++) {

                // se o atual bichinho for maior que o proximo bichinho, inverte
                if (array[j] > array[j + 1]) {
                    int guardador = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = guardador;
                    houveTroca = true;
                }
            }

            // ta ordenado old mo
            if (!houveTroca) {
                break;
            }
        }
    }
}