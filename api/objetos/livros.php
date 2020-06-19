<?php
class Livros{
    private $conexaoComBancoDeDados;
    private $nomeDaTabela = "livros";

    public $livroId;
    public $autorId;
    public $titulo;
    public $dataDePublicacao;
    public $nomeDoAutor;

    public function __construct($bancoDeDados){
        $this->conexaoComBancoDeDados = $bancoDeDados;
    }

    function read(){
        $query = "SELECT a.nome as nomeDoAutor, l.livroId, l.titulo, l.dataDePublicacao, l.autorId
                  FROM" . $this->nomeDaTabela . "l LEFT JOIN autor a ON l.autorId = a.autorId
                  ORDER BY l.dataDePublicacao DESC";

        $ordem = $this->conexaoComBancoDeDados->prepare($query);
        $ordem->execute();
        return $ordem;
    }
}
?>