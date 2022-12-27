package spring.boot.tw.model;

import java.text.DecimalFormat;
import java.util.Date;
public class Anuncio
{
    private String tipo;
    private String estado;
    private String genero;
    private String zona;
    private String anunciante;
    private String tipologia;
    private long aid;
    private Date data;
    private Double preco;
    private String titulo;
    private String descricao;

    public Anuncio(String tipo, String estado, String anunciante, double preco, String genero, String zona, int aid, Date data, String tipologia,String descricao,String titulo){
        this.tipo = tipo;
        this.estado = estado;
        this.anunciante = anunciante;
        this.preco = preco;
        this.genero = genero;
        this.zona = zona;
        this.aid = aid;
        this.data = data;
        this.tipologia = tipologia;
        this.descricao = descricao;
        this.titulo = titulo;
    }
    public Anuncio(){
        this.tipo = null;
        this.estado = null;
        this.anunciante = null;
        this.preco = -1.0;
        this.genero = null;
        this.zona = null;
        this.aid = -1;
        this.data = null;
        this.tipologia = null;
        this.descricao = null;
        this.titulo = null;
    }
    public void setAid(long aid){
        this.aid = aid;
    }
    public void setTipo(String t){
        this.tipo = t;
    }
    public void setEstado(String e){
        this.estado = e;
    }
    public void setAnunciante(String n) {
        this.anunciante = n;
    }
    public void setPreco(double p) {
        this.preco = p;
    }
    public void setGenero(String g) {
        this.genero = g;
    }
    public void setZona(String z) {
        this.zona = z;
    }
    public void setData(Date d){
        this.data = d;
    }
    public void setTipologia(String s){
        this.tipologia = s;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getTipo() {
        return this.tipo;
    }
    public String getEstado() {
        return this.estado;
    }
    public String getAnunciante() {
        return this.anunciante;
    }
    public double getPreco() {
        return this.preco;
    }
    public String getGenero() {
        return this.genero;
    }
    public String getZona() {
        return this.zona;
    }
    public long getAid() {
        return this.aid;
    }
    public Date getData() {
        return this.data;
    }
    public String getTipologia(){
        return this.tipologia;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public String getDescricao(){
        return this.descricao;
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#,##0.00€");

        String p = "-------------------------------------------------------\n" +
                "\t Aid: "+ aid + ":" + titulo+"\n"+
                "\t Tipo: " + tipo + "\n"+
                "\t Estado: "+ estado + "\n"+
                "\t Genero: " + genero+   "\n"+
                "\t Zona: " + zona + "\n"+
                "\t Anunciante:  "+ anunciante + "\n"+
                "\t Tipologia: "+ tipologia + "\n"+
                "\t Data: " + data + "\n"+
                "\t Preço: "+ df.format(preco) +
                "\t Descrição: "+descricao+"\n";

        return p;
    }
}
