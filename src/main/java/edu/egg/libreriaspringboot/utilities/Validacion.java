package edu.egg.libreriaspringboot.utilities;

import edu.egg.libreriaspringboot.excepciones.ExcepcionService;

public class Validacion {

    public static void validarTamanioIsbn(Long isbn) throws ExcepcionService{

        if(isbn.toString().length() != 13){
            throw new ExcepcionService("El ISBN debe contener 13 dígitos");
        }
    }

    public static void validarEspacioVacio(String nombre) throws ExcepcionService{
        if(nombre.trim().isEmpty()){
            throw new ExcepcionService("El nombre no puede estar vacío");
        }
    }

    public static void validarNombreSinNumeros(String nombre) throws ExcepcionService{
        validarEspacioVacio(nombre);
            // valido si el nombre tiene se compone de letras y numero o numeros solos
        if (nombre.matches(".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*") || nombre.matches(".*[0-9].*")){
            throw new ExcepcionService("El nombre solo puede contener letras");
        }
    }


}
