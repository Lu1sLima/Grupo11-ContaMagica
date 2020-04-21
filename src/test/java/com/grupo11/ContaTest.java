package com.grupo11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.matchers.InstanceOf;
import org.omg.CORBA.DynAnyPackage.Invalid;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.activity.InvalidActivityException;
import com.grupo11.Conta;
import exceptions.InvalidValueException;


public class ContaTest {
    
    private Conta cSilver;
    private Conta cGold;
    private Conta cPlatinum;
    private double valor;

    @BeforeEach
    public void initialize(){
        this.cSilver = new Conta(Categoria.SILVER, 0, "Lucas Garcia");
        this.cGold = new Conta(Categoria.GOLD, 0, "Luis Lima");
        this.cPlatinum = new Conta(Categoria.PLATINUM, 0 , "Adilson Junior");
    }

    @Test
    public void testDepositoNegativo() throws InvalidValueException{
        valor = -100;
        Assertions.assertThrows(InvalidValueException.class, () -> this.cSilver.deposito(valor));
        Assertions.assertThrows(InvalidValueException.class, () -> this.cGold.deposito(valor));
        Assertions.assertThrows(InvalidValueException.class, () -> this.cPlatinum.deposito(valor));
    }

    @Test
    public void testeDepositoSilver() throws InvalidValueException{
            
        //Low
        this.cGold = new Conta(Categoria.SILVER, 0, "Lucas Garcia");
        valor = 0.001;
        this.cSilver.deposito(valor);
        Assertions.assertEquals(Categoria.SILVER, this.cSilver.getStatus());
    
        //Medium
        this.cSilver = new Conta(Categoria.SILVER, 2000, "Lucas Garcia");
        valor = 23000;
        this.cSilver.deposito(valor);
        Assertions.assertEquals(Categoria.SILVER, this.cSilver.getStatus());

        //Past Medium        
        this.cSilver = new Conta(Categoria.SILVER, 20000, "Lucas Garcia");
        valor = 29700; 
        this.cSilver.deposito(valor);
        Assertions.assertEquals(Categoria.SILVER, this.cSilver.getStatus());

        //High
        this.cSilver = new Conta(Categoria.SILVER, 0, "Lucas Garcia");
        valor = 49550; 
        this.cSilver.deposito(valor);
        Assertions.assertEquals(Categoria.SILVER, this.cSilver.getStatus());
        
        // //Past High
        this.cSilver = new Conta(Categoria.SILVER, 1000, "Lucas Garcia");
        valor = 50000;
        this.cSilver.deposito(valor);
        Assertions.assertEquals(Categoria.GOLD, this.cSilver.getStatus());

    }

    @Test
    public void testeDepositoGold() throws InvalidValueException{        
        
        //Low
        this.cGold = new Conta(Categoria.GOLD, 0,"Adilson Medronha");
        valor = 24000;
        this.cGold.deposito(valor);
        Assertions.assertEquals(Categoria.SILVER, this.cGold.getStatus());
        
        //Medium
        this.cGold = new Conta(Categoria.GOLD, 0, "Adilson Medronha");
        valor = 100000; 
        this.cGold.deposito(valor);
        Assertions.assertEquals(Categoria.GOLD, this.cGold.getStatus());
        
        //Past Medium
        this.cGold = new Conta(Categoria.GOLD, 0, "Adilson Medronha");
        valor = 198010; // 199,990.1
        this.cGold.deposito(valor);
        Assertions.assertEquals(Categoria.GOLD,this.cGold.getStatus());
        
        //High
        this.cGold = new Conta(Categoria.GOLD, 20000, "Adilson Medronha");
        valor = 178221; //180,003.21 + 20K = 200.003.21
        this.cGold.deposito(valor);
        Assertions.assertEquals(Categoria.PLATINUM, this.cGold.getStatus());
    }
}