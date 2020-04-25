# Grupo11-ContaMagica

## HOW TO RUN:

- mvn compile

- mvn exec:java - executa o main

- mvn test


# Técnica utilizada: Test Driven Developmen
# Casos de Teste:
[x] Teste de depósito negativo;
[x] Teste de depósito em conta Silver;
- Teste de depósito em conta Gold:
    - Conta com 0 de saldo, deposita 24.000 -> Muda para Silver
    - Conta com 0 de saldo, deposita 100.000 -> Continua Gold
    - Conta com 0 de saldo, deposita 199,990.1 -> Continua Gold
    - Conta com 20.000 de saldo, deposita 178.221,0 (com bônus fica 180.003,21) total: 200.003,21 -> Muda para Platinum
- Teste de depósito Platinum:
    - Conta com saldo 0, deposita 1000 -> Muda para Gold
    - Conta com saldo 200.000, deposita 1 -> Continua em Platinum
- Teste de saque com zero;
- Teste de saque de valor negativo;
- Teste de saque conta Silver:
    - Conta com saldo 2.000, retira 2.000 -> Continua Silver
- Teste de saque conta Gold:
    - Conta gold com saldo de 1, saque(1) -> Muda para Silver
    - Conta gold com saldo de 50.000 - saque(1) -> Continua Gold
    - Conta gold com saldo de 24.999 - saque(1) -> Muda para Silver
- Teste saque conta Platinum:
    - Conta platinum com saldo 1, saque(1) -> Muda para Gold
    - Conta platinum com saldo de 199.999, saque(1) -> Continua Platinum
    - Conta platinum com saldo de 200.000, saque(200.000) -> Continua Platinum (muda só na próxima)
    - Conta platinum com saldo de 99.999, saque(1) -> Muda para Gold (já tinha valor inferior ao permidido para ser Platinum)
