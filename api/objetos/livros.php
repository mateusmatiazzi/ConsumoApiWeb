<?php
class Livros{
    private $conexaoComBancoDeDados;
    private $nomeDaTabela = "livros";

    public $livroId;
    public $autorId;
    public $titulo;
    public $dataDePublicacao;

    public function __construct($bancoDeDados){
        $this->conexaoComBancoDeDados = $bancoDeDados;
    }

}
?>