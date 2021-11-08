
package Models;

public class Productos {

 
    private int id;
    private String nombre;
    private float precio;
    private String descripcion;
    private int unidades;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Productos() {
    }  
    
 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio(){
       return precio;
    }

    public void setPrecio(float precio){
       this.precio = precio;
    }

    public String getDescripcion(){
       return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return "Productos{" + "nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion + ", unidades=" + unidades +  '}';
    }

}
