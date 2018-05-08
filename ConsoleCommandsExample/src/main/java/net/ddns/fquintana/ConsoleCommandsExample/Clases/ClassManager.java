package net.ddns.fquintana.ConsoleCommandsExample.Clases;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class ClassManager {
    private static ClassManager cm;

    private HashMap<String,Clase> Clases = new HashMap<>();

    private ClassManager()
    {

    }

    public static ClassManager getManager() {
        if (cm == null){
            cm = new ClassManager();
        }
        return cm;
    }

    public HashMap<String, Clase> getClases() {
        return Clases;
    }

    public boolean addClase(Clase clase)
    {
        if (existClase(clase.getNombre()))
        {
            return false;
        }
        else
        {
            Clases.put(clase.getNombre(),clase);
            return true;
        }
    }


    public Clase getClase(String name)
    {
        Iterator iterator = Clases.values().iterator();
        while (iterator.hasNext())
        {
            Clase clase = (Clase) iterator.next();
            if (clase.getNombre().equalsIgnoreCase(name))
            {
                return clase;
            }
        }
        return null;
    }

    public boolean existClase(String name)
    {
        if (getClase(name) != null)
        {
            return true;
        }
        return false;
    }

    public void saveToFile(Clase clase,String file) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        XMLEncoder encoder =  new XMLEncoder(bufferedOutputStream);
        encoder.writeObject(clase);
        encoder.close();
    }

    public void save() {
        Iterator<Clase> claseIterator = Clases.values().iterator();
        while (claseIterator.hasNext())
        {
            Clase clase = claseIterator.next();
            save(clase);
        }
    }

    public void save(Clase clase) {
        try {
            File folder = new File("Clases" + File.separator);
            if (!folder.exists())
                folder.mkdir();
            saveToFile(clase,"Clases" + File.separator +clase.getNombre() + ".xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Clase loadFromFile(File file) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        XMLDecoder decoder = new XMLDecoder(bufferedInputStream);
        Clase clase = (Clase) decoder.readObject();
        decoder.close();
        return clase;
    }

    public void load()
    {
        File folder = new File("Clases" + File.separator);
        if (folder.exists())
        {
           File[] files = folder.listFiles();
           for (int i = 0; i < files.length; i++)
           {
               try {
                  Clase clase = loadFromFile(files[i]);
                  addClase(clase);
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
           }
        }
    }
}
