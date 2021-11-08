package edu.egg.libreriaspringboot.utilities;

import edu.egg.libreriaspringboot.exception.ExcepcionService;

import java.time.LocalDate;

public class Validacion {

    public static void validarEspacioVacio(String nombre) throws ExcepcionService{
        if(nombre.trim().isEmpty()){
            throw new ExcepcionService("El nombre no puede estar vacío");
        }
    }

    public static void validarEspacioVacioUsuario(String nombre, String apellido, String correo, String clave) throws ExcepcionService{
        if(nombre.trim().isEmpty()){
            throw new ExcepcionService("El nombre no puede estar vacío");
        }

        if ((apellido.trim().isEmpty())){
            throw new ExcepcionService("El apellido no puede esta vacío");
        }

        if ((correo.trim().isEmpty())){
            throw new ExcepcionService("El correo no puede esta vacío");
        }

        if ((clave.trim().isEmpty())){
            throw new ExcepcionService("La clave no puede esta vacía");
        }
    }

    public static void validarNombreSinNumeros(String nombre) throws ExcepcionService{
        validarEspacioVacio(nombre);
            // valido si el nombre se compone de letras y numero o numeros solos
        if (nombre.matches(".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*") || nombre.matches(".*[0-9].*")){
            throw new ExcepcionService("El nombre solo puede contener letras");
        }
    }

    public static void validarTamanioIsbn(Long isbn) throws ExcepcionService{
        validarEspacioVacio(isbn.toString());

        if(isbn.toString().length() != 13){
            throw new ExcepcionService("El ISBN debe contener 13 dígitos");
        }
    }



    public static void validarEjemplares(Integer ejemplares, Integer ejemplaresRestantes) throws ExcepcionService{
        validarEspacioVacio(ejemplares.toString());
        validarEspacioVacio(ejemplaresRestantes.toString());

        if (ejemplares < 0 || ejemplaresRestantes < 0){
            throw new ExcepcionService("No puede ingresar números negativos");
        }

        if ((ejemplaresRestantes > ejemplares)){
            throw  new ExcepcionService("Los ejemplares prestados son mayor a los totales");
        }
    }

    public static void validarAnio(Integer anio) throws ExcepcionService{
        LocalDate fechaActual = LocalDate.now();

        if(fechaActual.getYear() < anio){
            throw new ExcepcionService("El año ingresado es mayor al actual");
        }
    }









}
