# Algoritmos: Seriais vs Em Paralelo

Este repositório contém a implementação e os testes de desempenho comparando a execução clássica (Serial / 1 thread) com a execução concorrente (Paralela / Múltiplas threads) usando `ForkJoinPool` e divisão de blocos.

## Algoritmos Comparados
Os algoritmos foram testados de acordo com sua complexidade matemática:
* **O(n log n) quase lineares - 10 Milhões de elementos):** QuickSort e MergeSort.
* **O(n^2) quadráticos - 100 Mil elementos):** InsertionSort e BubbleSort.

## Resultados

* **QuickSort:** o algoritmo mais rápido, ordenou 10 milhões de itens em cerca de ~170ms em paralelo. Foi melhor que o MergeSort porque faz as trocas no próprio array, sem precisar gastar tempo e memória alocando em arrays aux.
* **MergeSort:** manteve uma média muito estável e previsível graças à sua complexidade quase linear O(n log n), resolvendo 10 milhões de itens em ~300ms em paralelo. Só não bateu o QuickSort pq juntar as metades exige a criação constante de arrays aux, o que custa tempo e memória.
* **BubbleSort:** foi bem ruim. Levou quase 12s para ordenar 100 mil itens em  serial. O paralelismo em fases par/ímpar reduziu para ~650ms, mas ele continua sendo bem ruim.
* **InsertionSort:** em serial, foi ruim quanto o bubblesort, mas na versão paralela (fatiando o array em blocos menores de 10.000), ele teve um *speedup* de mais de 25x, resolvendo o problema em mais ou menos 50ms
* **Lei de Amdahl:** paralelismo funciona, mas tem limite, saltar de 2 para 4 núcleos deu um ganho gigante de tempo. Porém, pular de 8 para 12 núcleos quase não mudou nada. O *overhead* (esforço do computador para criar, sincronizar e destruir threads) acaba virando um gargalo.

## Stacks
* **Java 21+:** implementação geral
* **Python (Jupyter Notebook + Plotly):** consumir o csv e gerar gráficos
