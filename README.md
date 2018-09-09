

# Processamento Digital de Imagens
<i>Dá pra arrumar as fotos e tentar deixar bonitinhas com essas funções.</i>
 - Curso: Ciência da Computação 
 - Semestre: 2018/2 
 - Professor: Clávison Zapelini
 - Universidade: UNISUL
 - Campus: Tubarão/SC

**Licença de uso**
Este repositório está totalmente licenciado open-source para a comunidade de desenvolvedores e acadêmicos. Fique livre para clonar, modificar, reproduzir e fazer o que bem entender com este acervo. Fica dispensada a citação de créditos/fonte por qualquer pessoa e em qualquer parte do mundo, ficando isento de quaisquer legislações de licenciamento de software. Todo e qualquer conteúdo tem caráter educacional.

<hr>
Java Fx e telas com <a href="https://gluonhq.com/products/scene-builder/"><b>Scene Builder - Gluon</b></a>
<hr>
<img src="https://i.snag.gy/O2zJ50.jpg"/>

# Recursos
<p>Você poderá abrir no máximo duas imagens</p>
<p>É possível salvar o resultado em uma pasta do sistema</p>

## Tons de cinza - média aritmética
<p>Soma-se os valores atuais de cada pixel da imagem dos três canais (R, G e B) e divide-se pela quantidade de elementos (3).</p>
<p>Média aritmética: R + G + B / 3</p>
<p>Após obter-se a média, ela será aplicada pixel a pixel na imagem</p>

<pre>
[...]
Color previousColor = pr.getColor(i, j);
  double media = ((previousColor.getRed() + 
          previousColor.getGreen() + 
          previousColor.getBlue()) 
          / 3);
          
Color newColor = new Color(media, media, media, previousColor.getOpacity());
pw.setColor(i, j, newColor);
[...]
</pre>

## Tons de cinza - média ponderada
<p>A única diferença da média aritmética é que no caso da ponderada, cada canal será multiplicado pelo seu peso. (O peso será informado pelo usuário no formato de porcentagem)</p>
<b>A soma dos pesos não poderá ultrapassar 100% e recomenda-se que ela feche sempre 100%, caso seja inferior o sistema informará com uma janela, porém o funcionamento não será prejudicado.</b>

<pre>
[...]
Color previousColor = pr.getColor(i, j);
  media = (previousColor.getRed() * pcR
          + previousColor.getGreen() * pcG
          + previousColor.getBlue() * pcB)
          /100; //Media Ponderada do RGD
          
Color newColor = new Color(media, media, media, previousColor.getOpacity());
pw.setColor(i, j, newColor);
[...]
</pre>

## Negativo
<p>Para aplicar o efeito negativo é necessário realizar a conversão de uma imagem para tons de cinza</p>
<pre>
// Controller
@FXML
public void negativa() {
  imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
  imgResultado = PDIClass.negativa(img1);
  atualizaImageResultado();
}
</pre>

<pre>
// Utils
Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
  Color corNova;

  corNova = new Color(
        ( 1- corAnterior.getRed()), 
        (1 - corAnterior.getGreen()),
        (1 - corAnterior.getBlue()),
        corAnterior.getOpacity()
      );

  pw.setColor(i, j, corNova);
</pre>

## Limiarização
<p>Através de um controle deslizante (SLIDER) o usuário informa a porcentagem de limiar desejada. (0 ~ 1)</p>
<p>No caso da limiarização, compara-se a porcentagem informada pelo usuário com o valor do canal RED (R) no pixel a ser analisado. </p>
- Caso o valor do pixel RED seja maior ou igual ( >= ) ao limiar informado, então o pixel em questão passará a ter 100% (1) em todos os canais e a opacidade de sua cor anterior.
- Caso contrário, a nova cor do pixel em questão será preta e terá a opacidade de sua cor anterior.
<b>Podemos tratar as cores da seguinte forma para melhor entendimento:</b>
<ul>
  <li>PRETO = 1 | 0 (RGB)</li>
  <li>BRANCO = 0 | 255 (RGB)</li>
</ul>

<pre>
[...]
Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
  Color corNova;

  if(corAnterior.getRed() >= limiar) {
    corNova = new Color(1, 1, 1, corAnterior.getOpacity());
  }else {
    corNova = new Color(0, 0, 0, corAnterior.getOpacity());
  }
  pw.setColor(i, j, corNova);
[...]
</pre>

## Redução de ruído
Você poderá remover ruído utilizando três técnicas:

![Técnicas de redução de ruído](https://snag.gy/ZRpIlu.jpg)

 - **Vizinhança em cruz:** vizinhos da vertical e horizontal a partir do pixel atual central
 - **Vizinhança em X:** vizinhos das duas diagonais do pixel atual
 - **Vizinhança 3x3:** todos os vizinhos do pixel atual

<p>A lógica utilizada baseia-se em vários `for`</p>
<p>Ao utilizar uma imagem com grandes dimensões o computador executará um processo longo, fazendo com que travamentos ocorram e o tempo de processamento seja cada vez maior.</p>
<p>É recomendado otimizar o código utilizando array multidimensional ou outras técnicas.</p>

**Como funciona?**
O método `ReducaoRuido.reducao3x3(img1, largura, altura)` retorna um ArrayList com a mediana de cada canal da imagem (RGB). Para reduzir ruído devemos aplicar a mediana aos vizinhos do pixel atual visitado.

### Na prática (pseudocódigo)

**Descobrimos as medidas da imagem**

    int width = (int)image.getWidth();
	int height = (int)image.getHeight();

**Percorremos cada pixel da imagem**

    // largura X
	for(int contX = 0; contX < width; contX++) {
				
	// altura Y
	for(int contY = 0; contY < height; contY++) {

**Descobrimos os vizinhos do pixel que estamos visitando e adicionamos ao ArrayList os valores de seu RGB, para calcularmos a mediana posteriormente**

    if(contX == posicaoX && contY == posicaoY) {											
		// percorre todos os vizinhos
		for(int z = 0; z < 9; z++) {
			
			if(z == 0) {
				Color corVizinho = pr.getColor(contX-1, contY+1);
				vizinhosR.add(corVizinho.getRed());
				vizinhosG.add(corVizinho.getGreen());
				vizinhosB.add(corVizinho.getBlue());
			}
			
			[...]
Ordenamos a lista anteriormente e chamamos o método **`mediana(ArrayList<>)`**

**Calcula a mediana de uma lista informada**

    public static Double mediana(ArrayList<Double> lista) {		

		int restoDivisao = lista.size() % 2;
		
		// tem número ao centro
        if(restoDivisao > 0) {
            return lista.get(Math.round(lista.size() / 2));
        } else {
        	// caso não exista número ao centro
            int menor = (lista.size() /2) -1;
            int maior = (lista.size() /2);

            return (lista.get(menor) + lista.get(maior)) /2;
        }
	}
**Adicionamos a mediana de cada canal em um ArrayList que será retornado**

    medianaCanais.add(mediana(vizinhosR));
	medianaCanais.add(mediana(vizinhosG));
	medianaCanais.add(mediana(vizinhosB));

Nesta etapa calculamos as medianas de todos os canais de todos os vizinhos da forma de redução de ruído escolhida. Retornamos um ArrayList contendo as mesmas.

 - **ArrayList[0]** - mediana do canal ( R )
- **ArrayList[1]** - mediana do canal ( G )
-  **ArrayList[2]** - mediana do canal ( B )

Obtemos as medianas de cada canal do pixel atual e aplicamos a ele.

    Color corNova = new Color(medianas.get(0),
				medianas.get(1),
				medianas.get(2),
				1);
	pw.setColor(largura, altura, corNova);

**Como você percebeu, não aplicamos o valor da mediana a todos os vizinhos do pixel em questão, mas sim apenas ao pixel. A técnica escolhida influencia apenas em quais e quantos vizinhos utilizaremos para calcular a mediana.**
