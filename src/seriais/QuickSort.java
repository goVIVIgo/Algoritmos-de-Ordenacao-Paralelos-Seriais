package seriais;

// QuickSort - divide e conquista, mas com pivo
// complexidade: O(n log n)
// pior caso: O(n^2)
// passo a passo: divide(com pivo), conquista(com pivo)
// divide: divide o array em duas partes, uma com elementos menores que o pivo e outra com elementos maiores que o pivo
// conquista: ordena as duas partes
// combina: nao tem pq o pivo vai pro canto original de direito dele ne bb

public class QuickSort {
    public static void quickDaSilva(int[] arrayzinho, int inicio, int fim) {

        if (inicio < fim) {
            int lugarCertoDoPivo = separarOsBabados(arrayzinho, inicio, fim);
            //esquerda
            quickDaSilva(arrayzinho, inicio, lugarCertoDoPivo - 1);
            //direita
            quickDaSilva(arrayzinho, lugarCertoDoPivo + 1, fim);
        }
    }

    private static int separarOsBabados(int[] arrayzinho, int inicio, int fim) {
        int pivoChefe = arrayzinho[fim];
        int separacaoMenores = inicio - 1;

        //explora o array do inicio ate antes do pivo (fim - 1)
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

    private static void trocaDeLugarBabadeira(int[] arrayzinho, int posicaoNhomp, int posicaoGlup) {
        int guardandoLugar = arrayzinho[posicaoNhomp];
        arrayzinho[posicaoNhomp] = arrayzinho[posicaoGlup]; // Nhomp comwe o Glup (por isso ele ta glup)
        arrayzinho[posicaoGlup] = guardandoLugar; // Glup recebe o antigo Nhomp
    }
}