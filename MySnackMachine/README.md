# MySnackMachine

En enkel, händelsestyrd varuautomat i Java Swing.

## Krav

- JDK 17 eller senare (inklusive `javac` och `jar`)

## Bygg och kör

**Windows PowerShell:**

```powershell
.\build.ps1
java -jar MySnackMachine.jar
```

Om PowerShell blockerar skript: kör kommandona manuellt eller tillåt skript i
aktuell session enligt skolans lokala instruktioner.

**Linux / macOS:**

```bash
./build.sh
java -jar MySnackMachine.jar
```

## Filer

- `Product.java`: interfacet för alla varor.
- `Soda.java`, `Chips.java`, `Choklad.java`, `Water.java`: fyra varuklasser.
- `OutOfStockException.java`, `InsufficientFundsException.java`: egna undantag.
- `SnackMachine.java`: köplogik, saldo och statistik (utan Swing).
- `StockIndicator.java`: egenritad lagervisning (`paintComponent`).
- `MySnackMachine.java`: Swing-fönster, knappar och händelser.

## Köpbeteende

Programmet behåller kvarvarande saldo efter köp för att användaren ska kunna
fortsätta handla. Saldo dras **efter** lyckad `dispense()`; vid för lite pengar
eller tomt lager förändras varken saldo eller lagersaldo. Kvittologgen får en
post för varje köpförsök och sammanfattningen räknar alla tre utfall.

## Modelltester

På Linux/macOS efter `./build.sh`:

```bash
javac -encoding UTF-8 -cp out -d out tests/se/umu/cs/snackmachine/SnackMachineSelfTest.java
java -cp out se.umu.cs.snackmachine.SnackMachineSelfTest
```

På Windows PowerShell efter `.\build.ps1`:

```powershell
javac -encoding UTF-8 -cp out -d out tests/se/umu/cs/snackmachine/SnackMachineSelfTest.java
java -cp out se.umu.cs.snackmachine.SnackMachineSelfTest
```

## Förslag till Git-flöde

Gör återkommande små commits, till exempel när interfacet, varuklasserna,
köplogiken, GUI:t, egenritad komponent och testningen är färdiga. Publicera
projektet på kursens GitLab och lägg till de kursanställda enligt instruktionen.
