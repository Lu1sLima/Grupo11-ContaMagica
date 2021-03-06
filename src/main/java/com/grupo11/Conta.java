package com.grupo11;

import javax.activity.InvalidActivityException;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

import exceptions.InvalidValueException;
import com.grupo11.Categoria;

public class Conta {

    private Categoria categoria;
    private double saldo;
    private String nome;


    public Conta(Categoria categoria, double saldo, String nome){
        this.categoria = categoria;
        this.saldo = saldo;
        this.nome = nome;
    }

    public void deposito(double valor) throws InvalidValueException{
        /*
        * Vamos depositar um valor no saldo
        * Check se com o valor que tenho no saldo é possível eu fazer um upgrad de status
        * Tratamento para depósitos negativos
        * Adicionar a % bônus referente ao status do cliente:
             - Silver = 0%
             - Gold = 1%
             - Platinum = 2.5%
        */
        
        if(valor <= 0){
            throw new InvalidValueException("Deposito negativo ou zerado");
        }else{
            switch(this.categoria){
                case SILVER:
                    double saldoSilver = this.saldo + valor;
                    if(saldoSilver >= 50000){
                        this.categoria = Categoria.GOLD;
                    }
                    this.setSaldo(saldoSilver);
                    break;
                case GOLD:
                    double saldoGoldBonus = this.getSaldoBonus(valor, 0.01);
                    if (saldoGoldBonus >= 200000){
                        this.categoria = Categoria.PLATINUM;
                    }else{
                        if (saldoGoldBonus < 25000){
                            this.categoria = Categoria.SILVER;
                        }
                    }
                    this.setSaldo(saldoGoldBonus);
                    break;
                case PLATINUM:
                    double saldoBonusPlatinum = this.getSaldoBonus(valor, 0.025);
                    if(saldoBonusPlatinum < 100000){
                        this.categoria = Categoria.GOLD;
                    }
                    this.setSaldo(saldoBonusPlatinum);
                    break;
            }
        }
    }

    public void retirada(double valor) throws InvalidValueException{
        /*
        * Vamos retirar um valor no saldo
        * Checkar se com o valor que quero retirar do saldo é possível eu fazer downgrade de status
        * Tratamento para depósitos negativos
        */
        
        if(valor <= 0 || valor > this.getSaldo()){
            throw new InvalidValueException("Valor inválido");
        }
        else{
            switch(this.categoria){
                case GOLD:
                    if(saldo < 25000){
                        this.categoria = Categoria.SILVER;
                    }
                    setSaldo(saldo-valor);
                    break;
                case PLATINUM:
                    if(saldo < 100000){
                        this.categoria = Categoria.GOLD;
                    }
                    setSaldo(saldo-valor);
                    break;
                default:
                    setSaldo(saldo-valor);
            }
        }
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }   
    
    public double getSaldo(){
        return saldo;
    }

    public Categoria getStatus(){
        return this.categoria;
    }

    public double getSaldoBonus(double valor, double porcentagem){
        return (saldo + valor + (valor * porcentagem));
    }

    @Override
    public String toString() {
        return "Conta [Categoria=" + categoria + ", Nome=" + nome + ", Saldo=" + saldo + "]";
    }

}

