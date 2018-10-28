package net.ddns.fquintana.ConsoleCommandsExample.Clases;

import java.util.ArrayList;
import java.util.List;

public class Clase{

    private final static String NombreClase = "El nombre de la clase es: %s";
    private final static String NumeroAlumnos = "En esta clase hay: %s alumnos.";
    private final static String AlumnoInfo = "El alumno nº%d es: %s, de la clase %s";

    private String Nombre;
    private List<Alumno> Alumnos;

    int NumeroListaFix = -1;
    int GetNumeroListaFix = +1;

    public Clase()
    {
        Alumnos = new ArrayList<>();
        Nombre = "";
    }

    public Clase(String Nombre)
    {
        this.Nombre = Nombre;
        Alumnos = new ArrayList<>();
    }

    public void setAlumno(Alumno Alumno, int NumeroLista)
    {
        int NumeroListaFinal = NumeroLista + NumeroListaFix;

        Alumnos.set(NumeroListaFinal,Alumno);
        Alumno.setClase(this);
    }

    public void addAlumno(Alumno Alumno, int NumeroLista)
    {
        int NumeroListaFinal = NumeroLista + NumeroListaFix;

        Alumnos.add(NumeroListaFinal,Alumno);
        Alumno.setClase(this);
        
    }


    public int addAlumno(Alumno Alumno)
    {
        Alumnos.add(Alumno);
        Alumno.setClase(this);
        return Alumnos.indexOf(Alumno);
    }

    public Alumno getAlumno(int NumeroLista) {
        return Alumnos.get(NumeroLista + NumeroListaFix);
    }
    public void removeAlumno(Alumno Alumno)
    {
        removeAlumno(Alumnos.indexOf(Alumno));
    }

    public void removeAlumno(int NumeroLista)
    {
        Alumno alumno = Alumnos.remove(NumeroLista + NumeroListaFix);
        alumno.setClase(null);

    }

    public List<Alumno> getAlumnos() {
        return Alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        Alumnos = alumnos;
    }

    public void setNombre(String nombre)
    {
        this.Nombre = nombre;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public int getNumAlumnos()
    {
        return Alumnos.size();
    }

    public int getNumListaAlumno(Alumno alumno)
    {
        return Alumnos.indexOf(alumno) + GetNumeroListaFix;
    }

    public static void imprimeClase(Clase clase)
    {
        System.out.println(String.format(NombreClase,clase.getNombre()));

        int NumAlumnos = clase.getNumAlumnos();
        System.out.println(String.format(NumeroAlumnos,NumAlumnos));

        for (int i = 1; i <= NumAlumnos; i++)
        {
            Alumno alumno = clase.getAlumno(i);
            System.out.println(String.format(AlumnoInfo,  alumno.getNumLista(), alumno.getNombre(),alumno.getClase().getNombre()));
        }

    }

    public void save() {
        ClassManager.getManager().save(this);
    }

}
