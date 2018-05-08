package net.ddns.fquintana.ConsoleCommandsExample.Personas;

import java.util.HashMap;
import java.util.Iterator;

public class PeopleManager {
    private static PeopleManager pm;

    private HashMap<String,Persona> Personas = new HashMap<>();

    private PeopleManager()
    {

    }

    public static PeopleManager getManager() {
        if (pm == null){
            return pm = new PeopleManager();
        }
        return pm;
    }

    public boolean addPersona(Persona persona)
    {
        if (existPersona(persona.getNombre()))
        {
            return false;
        }
        else
        {
            Personas.put(persona.getNombre(),persona);
            return true;
        }
    }


    public Persona getPersona(String name)
    {
        Iterator iterator = Personas.values().iterator();
        while (iterator.hasNext())
        {
            Persona persona = (Persona) iterator.next();
            if (persona.getNombre().equalsIgnoreCase(name))
            {
                return persona;
            }
        }
        return null;
    }

    public boolean existPersona(String name)
    {
        if (getPersona(name) != null)
        {
            return true;
        }
        return false;
    }
}
