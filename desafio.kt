import java.util.*

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }


data class Usuario(val nome: String, val id: Int = 0, var coracao:Int = 5)

data class Pergunta(val pergunta: String, val resposta: String)

data class ConteudoEducacional(
    val nome: String,
    var duracao: Int = 60,
    val nivel: Nivel = Nivel.BASICO,
    val pergutas: List<Pergunta> = listOf(),
    val usuario: Usuario = Usuario("", 0)
){
    fun verificarAssistiuCurso(){
        val scanner = Scanner(System.`in`)
        var numeroAcertos = 0
        pergutas.forEach{
            println(it.pergunta)
            val resposta = scanner.next()
            if (resposta == it.resposta){
                numeroAcertos++
            }
        }
        val percetagemAcertos = (numeroAcertos.toDouble() / pergutas.size * 100).toInt()
        if(percetagemAcertos >= 75){
            gerarCertificado()
        }else{
            println("Perdeu um coração")
            usuario.coracao--
        }
    }

    private fun gerarCertificado() {
        println("Parabens ${usuario.nome} voce concluiu o curso de ${nome} com sucesso!!")
        println("Aqui esta o seu certificado")
    }


}

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>) {
    val inscritos = mutableListOf<Usuario>()



    fun registrarNovoConteudo(conteudo:ConteudoEducacional){
        if (conteudos.contains(conteudo)){
            throw Exception("O conteudo ${conteudo.nome} ja foi existe!!")
        }
        conteudos.addLast(conteudo)
    }


    fun matricular(usuario: Usuario) {
        if (inscritos.contains(usuario)) {
            println("O usuario ${usuario.nome} ja se matriculou")
        } else {
            inscritos.add(usuario)
            println("${usuario.nome} matriculado com sucesso ao curso de ${nome}!")
        }
    }

    fun listarConteudosDaFormacao() {
        conteudos.forEach {
            println(it.nome)
        }
    }
}





fun main() {
    val usuario1 = Usuario("John", 1, 5)
    val perguntas1 = listOf(Pergunta("Pergunta 1", "Resposta 1"), Pergunta("Pergunta 2", "Resposta 2"))
    val conteudo1 = ConteudoEducacional("Curso Teste", 60, Nivel.BASICO, perguntas1, usuario1)
    testarConteudo(conteudo1, 5)

    val usuario2 = Usuario("Jane", 2, 5)
    val perguntas2 = listOf(Pergunta("Pergunta 1", "Resposta Errada"), Pergunta("Pergunta 2", "Resposta 2"))
    val conteudo2 = ConteudoEducacional("Curso Teste 2", 60, Nivel.INTERMEDIARIO, perguntas2, usuario2)
    testarConteudo(conteudo2, 4)
}

fun testarConteudo(conteudo: ConteudoEducacional, expectedCoracao: Int) {
    conteudo.verificarAssitiuCurso()
    assertEquals(expectedCoracao, conteudo.usuario.coracao)
}

fun assertEquals(expected: Int, actual: Int) {
    if (expected == actual) {
        println("Teste passou")
    } else {
        throw Exception("Teste falhou")
    }
}
