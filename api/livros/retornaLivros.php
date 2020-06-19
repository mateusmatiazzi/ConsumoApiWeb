<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/bancoDeDados.php';
include_once '../objetos/livro.php';

$bancoDeDados = new BancoDeDados();
$conexaoComBancoDeDados = $bancoDeDados->getConexaoComBancoDeDados();

$livro = new Livro($conexaoComBancoDeDados);

$listaDeLivros = $livro->read();
$quantidadeDeLivrosNoBancoDeDados = $listaDeLivros->rowCount();

if($quantidadeDeLivrosNoBancoDeDados > 0){
    $livrosArray = array();

    while($linha = $listaDeLivros->fetch(PDO::FETCH_ASSOC)){
        extract($linha);
        $livro_item=array(
            "livroId" => $livroId,
            "autorId" => $autorId,
            "titulo" => $titulo,
            "dataDePublicacao" => $dataDePublicacao,
            "nomeDoAutor" => $nomeDoAutor
        );

        array_push($livrosArray, $livro_item);
    }

    http_response_code(200);

    echo json_encode($livrosArray);
}else{
  
    http_response_code(404);
  
    echo json_encode(
        array("message" => "Nenhum livro encontrado.")
    );
}
?>