<?php
class BancoDeDados{
    private $host = "localhost";
    private $nomeDoBanco = "api";
    private $username = "root";
    private $password = "#lialab";
    public $conexaoComBancoDeDados;

    public function getConexaoComBancoDeDados(){
        $this->conexaoComBancoDeDados = null;
        try{
            $this->conexaoComBancoDeDados = new PDO("mysql:host=". $this->host . ";dbname=" . $this->nomeDoBanco, $this->username, $this->password);
            $this->conexaoComBancoDeDados->exec("set names utf8");
        }catch(PDOException $excecao){
            echo "Erro de conexão com o banco de dados: " . $excecao->getMessage();
        }
        
        return $this->conexaoComBancoDeDados;
    }
}
?>