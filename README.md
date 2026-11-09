# Horgásztó: CLI horgászjáték három tervezési mintával

Körökre osztott parancssoros játék: bedobsz, vársz a kapásra, bevágsz, majd
fárasztod a halat (tekerés/engedés), és közben figyelsz, hogy el ne szakadjon a zsinór.

## Futtatás

```
mvn compile exec:java
```
vagy Maven nélkül (Java 21+):
```
javac -d out $(find src -name "*.java")
java -cp out fishing.Main
```

Parancsok: `dob`, `var`, `bevag`, `teker`, `enged`, `modszer uszos|fenekezo|pergeto`, `stat`, `sugo`, `kilep`

## Szerkezet

```
fishing/
  Main.java               CLI: parancsok beolvasása
  FishingSession.java     kontextus (State, Strategy) + alany (Observer)
  fish/                   Species (enum), Fish (record)
  state/                  State minta:    RodState, IdleState, CastState, BiteState, FightState
  method/                 Strategy minta: FishingMethod, FloatFishing, BottomFishing, Spinning
  event/                  Observer minta: CatchListener, Statistics, CatchLogFile, TrophyAnnouncer
```

---

## Prezentáció vázlat (kb. 6 perc)

### 1. A program (30 mp)
Horgászjáték parancssorban. Rövid demó: `dob` → `var` → KAPÁS → `bevag` → `teker`/`enged` → "Szákban van!"

### 2. State: a bot állapotai (2 perc)
**Probléma:** ugyanaz a parancs mást jelent a helyzettől függően. A `teker` bedobás után
kihúzza a csalit, kapáskor elrántja, fárasztáskor közelebb hozza a halat. A `var` hol
kapást hozhat, hol elszalasztja a halat. Minta nélkül minden parancsban egy
`switch (allapot)` lenne, és egy új állapot az összes metódust érintené.

**Megoldás:** `RodState` interfész, állapotonként egy osztály
(`Idle → Cast → Bite → Fight → Idle`). A `FishingSession` csak továbbad:
`state.reel(this)`. Az átmeneteket maguk az állapotok végzik (`s.setState(...)`).

**Kiemelendő:**
- Az interfész `default` metódusai adják a "most nem lehet" választ, így minden állapot
  csak azt írja felül, ami nála értelmes.
- Az állapot a saját adatait is tárolja: a `FightState` a távolságot és a feszülést,
  a `CastState` a várakozások számát. Ezek nem szemetelik tele a sessiont.

### 3. Strategy: horgászmódszerek (1,5 perc)
**Probléma:** a kapás esélye és a kapó hal fajtája a módszertől függ, és ezt
játék közben váltani lehet.

**Megoldás:** `FishingMethod` interfész (`biteChance`, `chooseSpecies`), három implementáció:
- `FloatFishing` (úszós): állandó, magas kapási esély, főleg keszeg.
- `BottomFishing` (fenekező): minél tovább vársz, annál nagyobb az esély, és annál valószínűbb a harcsa.
- `Spinning` (pergető): az elején a legjobb, aztán csökken, ragadozók jönnek.

Ezek valódi algoritmusbeli különbségek, nem csak más számok.
A `CastState` csak annyit tud: `s.method().biteChance(waitedTurns)`.
Új módszer felvétele = egy új osztály, máshoz nem kell nyúlni (Open/Closed elv).

### 4. Observer: fogási események (1,5 perc)
**Probléma:** egy fogásra több, egymástól független dolognak kell reagálnia:
statisztika, fájlba írt fogási napló, trófea-értesítés. Ha a `FightState` hívná őket
közvetlenül, a játéklogika a fájlkezeléstől a kiírásig mindenhez kötődne.

**Megoldás:** `CatchListener` interfész (`onCatch`, `onEscape`). A `FishingSession`
az alany, a `Main` iratkoztatja fel a megfigyelőket. Új reakció (pl. napi kvóta-figyelő)
= egy új listener, a játéklogika változatlan.

### 5. Miért nem több minta? (30 mp)
Szándékosan nincs Factory, Singleton és hasonló: a halak létrehozása egy sor
(`species.randomFish(random)`), nem indokolna külön mintát. A három minta mindegyike
konkrét problémát old meg.
