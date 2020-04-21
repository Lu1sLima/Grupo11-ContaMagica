package com.grupo11;

import org.omg.CORBA.DynAnyPackage.Invalid;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import exceptions.InvalidValueException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){

        String nome = "Adilson";
        Conta cGold = new Conta(Categoria.GOLD, 51000, nome);

        System.out.println(cGold);
        System.out.println("Retirei: 20000");
        try{
            cGold.retirada(20000.0);
        } catch (InvalidValueException e){
            System.out.println(e.getMessage());
        }
        System.out.println(cGold);


        System.out.println("Adicionei: 300000");
        try{
            cGold.deposito(300000.0);
            System.out.println(cGold);
        }catch(InvalidValueException e){
            System.out.println(e.getMessage());
        }
        try{
            System.out.println("Retirei 500k");
            cGold.retirada(500000);
        }
        catch (InvalidValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(cGold);
    
    }
}
