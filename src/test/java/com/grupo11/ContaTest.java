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

    @Test
    public void testeDepositoPlatinum() throws InvalidValueException{
        
        valor = 1000;
        this.cPlatinum.deposito(valor);
        Assertions.assertEquals(Categoria.GOLD, this.cPlatinum.getStatus());

        this.cPlatinum = new Conta(Categoria.PLATINUM, 200000, "Luís Lima");
        valor = 1;
        this.cPlatinum.deposito(valor);
        Assertions.assertEquals(Categoria.PLATINUM, this.cPlatinum.getStatus());

    }


    @Test
    public void testeSaqueZerado() throws InvalidValueException{
        valor = 1;
        Assertions.assertThrows(InvalidValueException.class, () -> this.cPlatinum.retirada(valor));
        Assertions.assertThrows(InvalidValueException.class, () -> this.cGold.retirada(valor));
        Assertions.assertThrows(InvalidValueException.class, () -> this.cSilver.retirada(valor));
        
    }

    @Test
    public void testeSaqueNegativo() throws InvalidValueException{
        valor = -100;
        Assertions.assertThrows(InvalidValueException.class, () -> this.cPlatinum.retirada(valor));
        Assertions.assertThrows(InvalidValueException.class, () -> this.cGold.retirada(valor));
        Assertions.assertThrows(InvalidValueException.class, () -> this.cSilver.retirada(valor));

    }

    @Test 
    public void testeSaqueSilver() throws InvalidValueException {

        this.cSilver.setSaldo(2000);
        this.cSilver.retirada(2000);
        Assertions.assertEquals(Categoria.SILVER, cSilver.getStatus());
    }

    @Test 
    public void testeSaqueGold() throws InvalidValueException{
        /**
         * gold 1 real, saque(1) -> silver
         * gold 50000 - saque(1) -> gold
         * gold 49999 - saque(1) -> silver
         */

        this.cGold.setSaldo(1);
        this.cGold.retirada(1);
        Assertions.assertEquals(Categoria.SILVER, this.cGold.getStatus());

        this.cGold = new Conta(Categoria.GOLD, 50000, "Luis Lima");
        this.cGold.retirada(1);
        Assertions.assertEquals(Categoria.GOLD, this.cGold.getStatus());

    
        this.cGold = new Conta(Categoria.GOLD, 50000, "Adilson Medronha");
        this.cGold.retirada(50000);
        Assertions.assertEquals(Categoria.GOLD, this.cGold.getStatus());
        
        this.cGold = new Conta(Categoria.GOLD, 24999, "Luis Lima");
        this.cGold.retirada(1); 
        Assertions.assertEquals(Categoria.SILVER, this.cGold.getStatus());



    }

    @Test 
    public void testeSaquePlatinum() throws InvalidValueException{
        /**
         * platinum 1 real, saque(1) --> gold
         * platinum 199K, saquei(1) --> gold
         * platinum 2 reais, saque(1) --> gold / gold 1 real, saque(1) --> silver
         * platinum 200K, saquei(200k) --> platinum 
         * platinum 100K, saquei(100k) --> platinum 
         */ 

        this.cPlatinum.setSaldo(1);
        this.cPlatinum.retirada(1);
        Assertions.assertEquals(Categoria.GOLD, this.cPlatinum.getStatus());

        this.cPlatinum = new Conta(Categoria.PLATINUM, 199999, "Luis Lima");
        this.cPlatinum.retirada(1);
        Assertions.assertEquals(Categoria.PLATINUM, this.cPlatinum.getStatus());

        this.cPlatinum = new Conta(Categoria.PLATINUM, 200000, "Adilson Medronha");
        this.cPlatinum.retirada(200000);
        Assertions.assertEquals(Categoria.PLATINUM, this.cPlatinum.getStatus());

        this.cPlatinum = new Conta(Categoria.PLATINUM, 99999, "Adilson Medronha");
        this.cPlatinum.retirada(1);
        Assertions.assertEquals(Categoria.GOLD, this.cPlatinum.getStatus());

    }

    @Test
    public void testeToString(){
        String result = "Conta [Categoria=GOLD, Nome=Luis Lima, Saldo=0.0]";
        Assertions.assertEquals(result, cGold.toString());
    }
}