/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.util;

/**
 *
 * @author Lorena Franco
 */
public class Mensaje {
    private String destino;
    private String titulo;
    private String mensagem;

    public Mensaje(String mensaje) {
    	mensagem = mensaje;
    	destino = "lorenaf86@gmail.com";
    }
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
