# Processamento Digital de Imagens
<i>Dá pra arrumar as fotos e tentar deixar bonitinhas com essas funções.</i><br>

Curso: Ciência da Computação <br>
Semestre: 2018/2 <br>
Professor: Clávison Zapelini<br>
Universidade: UNISUL<br>
Campus: Tubarão/SC

<hr>
Java Fx e telas com <a href="https://gluonhq.com/products/scene-builder/"><b>Scene Builder - Gluon</b></a>
<hr>
<img src="https://i.snag.gy/O2zJ50.jpg"/>

#Recursos
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
<b>A soma dos pesos não poderá ultrapas 100% e recomenda-se que ela feche sempre 100%, caso seja inferior o sistema informará com uma janela, porém o funcionamento não será prejudicado.</b>

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
          
