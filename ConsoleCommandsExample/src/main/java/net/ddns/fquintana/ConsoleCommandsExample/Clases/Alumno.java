package net.ddns.fquintana.ConsoleCommandsExample.Clases;

import net.ddns.fquintana.ConsoleCommandsExample.Personas.Persona;

public class Alumno implements Persona {

    private String Nombre;
    private Clase Clase;

    public Alumno()
    {

    }

    public Alumno(Persona Persona)
    {
        this.Nombre = Persona.getNombre();
    }

    public Alumno(String Nombre)
    {
        this.Nombre = Nombre;
    }

    public void setClase(Clase clase) {
        Clase = clase;
    }

    public Clase getClase() {
        return Clase;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }


    public int getNumLista()
    {
        return getClase().getNumListaAlumno(this);
    }
}
