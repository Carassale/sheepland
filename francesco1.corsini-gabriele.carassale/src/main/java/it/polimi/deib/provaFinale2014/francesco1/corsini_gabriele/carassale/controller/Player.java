package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.TerrainCard;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe che modellizza il Giocatore come controllore, rendendo disponibili le
 * varie mosse.
 *
 * @author Francesco Corsini
 */
public class Player {

    private ArrayList<Shepard> shepards;
    private ArrayList<ArrayList<TerrainCard>> terrainCardsOwned;
    private int coins;
    private String nickName;
    private boolean isFirstPlayer;

    private String actionDone[];

    /**
     * costruttore solo usato per i test
     *
     * @param firstPlayer
     */
    public Player(boolean firstPlayer) {
        shepards = new ArrayList<Shepard>();
        terrainCardsOwned = new ArrayList<ArrayList<TerrainCard>>();

        actionDone = new String[3];
        for (int i = 0; i < 3; i++) {
            actionDone[i] = new String("");
        }

        coins = 20;
        isFirstPlayer = firstPlayer;

        //serve per inizializzare la lista di liste dell TerrainCardPool
        for (int i = 0; i < 6; i++) {
            ArrayList<TerrainCard> list = new ArrayList<TerrainCard>();
            terrainCardsOwned.add(list);
        }
    }

    public ArrayList<Shepard> getShepards() {
        return shepards;
    }

    /**
     * Ritorna la lista di carte possedute della tipologia desiderata
     *
     * @param string tipologia territorio
     * @return lista dei territori di quella tipologia
     */
    public ArrayList<TerrainCard> getTerrainCardsOwned(String string) {
        if ("Plain".equals(string)) {
            return terrainCardsOwned.get(0);
        } else if ("Forest".equals(string)) {
            return terrainCardsOwned.get(1);
        } else if ("River".equals(string)) {
            return terrainCardsOwned.get(2);
        } else if ("Desert".equals(string)) {
            return terrainCardsOwned.get(3);
        } else if ("Mountain".equals(string)) {
            return terrainCardsOwned.get(4);
        } else {
            return terrainCardsOwned.get(5);
        }
    }

    /**
     * Metodo che permette al giocatore di comprare una carta terreno
     *
     * @param terrainKind la tipologia di terreno che si intende comprare
     * @param game il gameTable su cui si sta giocando
     * @throws CoinException se i soldi non sono sufficenti a comprare la carta
     */
    public void buyTerrainCard(String terrainKind, GameTable game) throws CoinException {
        int cardLeft = game.getTerrainCardPool(terrainKind).size();
        int cost = 5 - cardLeft;
        if (this.coins >= cost) {
            coins = coins - cost;
            TerrainCard newTerrCard = new TerrainCard(terrainKind);
            this.getTerrainCardsOwned(terrainKind).add(newTerrCard);
            game.getTerrainCardPool(terrainKind).remove(0);
        } else {
            throw new CoinException();
        }
    }

    /**
     * Metodo che muove un pastore da una strada ad un altra
     *
     * @param destination strada destinazione
     * @param shepard pastore da muovere
     * @param game gioco su cui si sta giocando
     * @throws CoinException lanciata nel caso soldi insufficenti
     * @throws MoveException lanciata nel caso mossa illegale
     */
    public void moveShepard(Road destination, Shepard shepard, GameTable game) throws CoinException, MoveException {

        boolean canMove = canMoveShepard(destination);
        Road shepPos = shepard.getPosition();

        if (canMove == true) {
            if (shepard.isExpensiveMove(destination)) {
                if (coins == 0) {
                    throw new CoinException();
                } else {
                    coins--;
                }
            }
            shepPos.setFence(true);
            shepPos.setHasShepard(false);
            shepard.setPosition(destination);
            shepard.getPosition().setHasShepard(true);
            game.decreaseFenceNumber();
        } else {
            throw new MoveException();
        }
    }

    /**
     * Metodo per muove pecora tra due territori con in mezzo un pastore
     *
     * @param sheep pecora da muovere
     * @param destination dove si vuole muovere
     * @param game gioco su cui si sta giocando
     * @throws MoveException lanciata nel caso mossa illegale
     */
    public void moveSheep(Sheep sheep, Terrain destination, GameTable game) throws MoveException {

        Road shepPos;

        shepPos = moveSheepOnRoad(sheep.getPosition(), destination);
        if (playerHasShepardOnRoad(shepPos)) {
            sheep.getPosition().getAnimals().remove(sheep);
            sheep.setPosition(destination);
            sheep.getPosition().getAnimals().add(sheep);
        } else {
            throw new MoveException("Non c'è Shepard sulla strada");
        }
    }

    //TODO lancio dado per vedere se si accoppiano
    /**
     * Metodo per fare accoppiare una pecora ed un montone e generare un agnello
     *
     * @param terrain terreno dove sono gli animali
     * @param game gioco su cui si sta giocando
     * @throws MoveException lanciata nel caso mossa illegale
     */
    public void joinSheeps(Terrain terrain, GameTable game) throws MoveException {

        if (isSheepAndRam(terrain)) {
            if (isShepardNear(terrain)) {
                int i = game.getSheeps().get(game.getSheeps().size() - 1).getId();//prende l'id dell'ultima pecora(che è quella con id più alto
                Sheep sheep = new Sheep(terrain, false, i + 1);//e inizializza la nuova pecora con l'id successivo
            } else {
                throw new MoveException("Non c'è vicino un pastore");
            }

        } else {
            throw new MoveException("Non ci sono una pecora ed un montone nel territorio");
        }
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int val) {
        this.coins = val;
    }

    /**
     * Metodo per fare abbattimento di pecora
     *
     * @param sheepToKill pecora da abbattere
     * @param game gioco su cui si sta giocando
     * @throws CoinException lanciata se non ci sono abbastanza soldi
     * @throws MoveException lanciata nel caso mossa illegale
     * @throws WrongDiceNumberException nel caso il lancio non riotrni il numero
     * desiderato
     */
    public void killAnimal(Sheep sheepToKill, GameTable game) throws CoinException, MoveException, WrongDiceNumberException {

        int shepardNearNumber = countShepardNear(sheepToKill.getPosition());
        Terrain sheepPosition = sheepToKill.getPosition();

        if (coins >= shepardNearNumber * 2) {
            if (isShepardNear(sheepPosition)) {
                int random = game.getDice().getRandom();
                if (randomNumberForShepard(sheepPosition, random)) {
                    sheepPosition.deleteAnimal(sheepToKill);
                    game.getSheeps().remove(sheepToKill);
                    int payment = payShepards(sheepPosition);
                    coins = coins - payment;
                } else {
                    throw new WrongDiceNumberException(random);
                }
            } else {
                throw new MoveException("Non c'è vicino un pastore");
            }
        } else {
            throw new CoinException("Non abbastanza soldi per comprare silenzio di tutti i pastori");
        }

    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String val) {
        this.nickName = val;
    }

    /**
     * Metodo di servizio utilizzato da moveShepard che serve a vedere se la
     * destinazione è valida
     *
     * @param destination
     * @param game
     * @return
     */
    private boolean canMoveShepard(Road destination) {
        if (destination.hasFence() == true) {
            return false;
        } else if (destination.hasShepard() == true) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Metodo di servizio utilizzato da moveSheep che serve a vedere se la mossa
     * è valida
     *
     * @param terrainSheep terreno dove è la sheep
     * @param terrainDestination terreno dove deve essere mossa
     * @return la strada in mezzo tra i terreni dove ci deve essere lo shepard
     * @throws MoveException viene lanciata se non c'è una strada tra i due
     * territori
     */
    private Road moveSheepOnRoad(Terrain terrainSheep, Terrain terrainDestination) throws MoveException {
        boolean isShepard = false;
        Road shepPos = null;

        ArrayList<Road> roadsTerrainSheep = terrainSheep.getAdjacentRoads();
        ArrayList<Road> roadsTerrainDestination = terrainDestination.getAdjacentRoads();

        Iterator<Road> itr = roadsTerrainSheep.iterator();
        while (itr.hasNext()) {
            Road ele = itr.next();
            Iterator<Road> itr2 = roadsTerrainDestination.iterator();
            while (itr2.hasNext()) {
                Road ele2 = itr2.next();
                if (ele == ele2) {
                    shepPos = ele;
                    isShepard = true;
                }
            }

        }
        if (isShepard == false) {
            throw new MoveException("Non esiste strada che comunica tra questi due territori");
        }

        return shepPos;
    }

    /**
     * Metodo di servizio utilizzato da moveSheepOnRoad per vedere se sulla
     * strada dove devono trasitare le pecore c'è un pastore del giocatore che
     * ha richiesto la mossa
     *
     * @param road strada dove devono passare le pecore
     * @return true se c'è pastore del giocatore
     */
    private boolean playerHasShepardOnRoad(Road road) {
        boolean thereIsShepard = false;

        Iterator<Shepard> itr = shepards.iterator();
        while (itr.hasNext()) {
            Shepard ele = itr.next();
            if (ele.getPosition().equals(road)) {
                thereIsShepard = true;
            }
        }
        return thereIsShepard;
    }

    /**
     * Metodo di servizio utilizzato da joinSheeps e per controllare se c'è una
     * pecora ed un montone sul terreno
     *
     * @param terrain terreno da controllare
     * @return true se ci sono entrambi
     */
    private boolean isSheepAndRam(Terrain terrain) {
        boolean thereIsSheep = false;
        boolean thereIsRam = false;

        Iterator<Animal> itr = terrain.getAnimals().iterator();
        while (itr.hasNext()) {
            Animal ele = itr.next();
            if (ele instanceof Sheep) {
                Sheep sheep = (Sheep) ele;
                if (sheep.isWhiteSheep()) {
                    thereIsSheep = true;
                } else if (sheep.isRam()) {
                    thereIsRam = true;
                }
            }

        }
        return thereIsSheep && thereIsRam;
    }

    /**
     * Metodo di servizio utilizzato da joinSheeps e da killAnimal per
     * controllare se c'è un pastore in una strada confinante
     *
     * @param terrain terreno su cui cercheremo
     * @return true se c'è pastore vicino
     */
    private boolean isShepardNear(Terrain terrain) {

        boolean thereIsShepard = false;

        Iterator<Road> itr = terrain.getAdjacentRoads().iterator();
        //Iterator<Shepard> itrShep = shepards.iterator();

        while (itr.hasNext()) {
            Road road = itr.next();
            Iterator<Shepard> itrShep = shepards.iterator();
            while (itrShep.hasNext()) {
                Shepard shep = itrShep.next();
                if (shep.getPosition() == road) {
                    thereIsShepard = true;
                }
            }
        }
        return thereIsShepard;
    }

    /**
     * Metodo di servizio utilizzato da killAnimal per contare pastori
     *
     * @param terrain dove contare
     * @return num pastori
     */
    private int countShepardNear(Terrain terrain) {
        int number = 0;
        Iterator<Road> itr = terrain.getAdjacentRoads().iterator();
        while (itr.hasNext()) {
            Road road = itr.next();
            if (road.hasShepard()) {
                number++;
            }
        }
        return number - 1;
    }

    /**
     * Metodo di servizio utilizzato da joinSheeps e da killAnimal per vedere se
     * un tiro di dado corrisponde alla strada su cui è il pastore
     *
     * @param terrain terreno dove si trova la pecora/e in questione
     * @return true se dal dado esce il numero giusto
     */
    private boolean randomNumberForShepard(Terrain terrain, int random) {

        boolean gotRightNumber = false;

        Iterator<Road> itr = terrain.getAdjacentRoads().iterator();

        while (itr.hasNext()) {
            Road road = itr.next();
            Iterator<Shepard> itrShep = shepards.iterator();
            while (itrShep.hasNext()) {
                Shepard shep = itrShep.next();
                if (shep.getPosition() == road) {
                    if (random == road.getRoadNumber()) {
                        gotRightNumber = true;
                    }
                }
            }
        }
        return gotRightNumber;
    }

    private int payShepards(Terrain terrain) {

        int totalCost = 0;

        Iterator<Road> itr = terrain.getAdjacentRoads().iterator();
        Iterator<Shepard> itrShep = shepards.iterator();

        while (itr.hasNext()) {
            Road road = itr.next();
            while (itrShep.hasNext()) {
                Shepard shep = itrShep.next();
                if (road.hasShepard()) {
                    if (!(shep.equals(road.getShepard()))) {
                        totalCost = totalCost + 2;
                        shep.getOwner().setCoins(shep.getOwner().getCoins() + 2);
                    }
                }
            }
        }
        return totalCost;
    }

    public void setFirstPlayer(boolean val) {
        isFirstPlayer = val;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public boolean isPossibleAction(String action) {
        if (actionDone[0].equals("")) {
            actionDone[0] = action;
            return true;
        }

        if (actionDone[1].equals("")) {
            if ((actionDone[0].equals(action) && action.equals("moveShepard"))
                    || (!actionDone[0].equals(action))) {
                actionDone[1] = action;
                return true;
            }
        }

        if (actionDone[2].equals("")) {
            if (actionDone[1].equals(action) && action.equals("moveShepard")) {
                cleanActionDone();
                return true;
            }

            if (action.equals("moveShepard")
                    || (actionDone[0].equals("moveShepard") && !actionDone[1].equals(action))
                    || (actionDone[1].equals("moveShepard"))) {
                cleanActionDone();
                return true;
            }
        }

        return false;
    }

    public void cleanActionDone() {
        for (String action : actionDone) {
            action = new String();
            action = "";
        }
    }

    public void killAnimal(Sheep sheepToKill, GameTable game, int num) throws CoinException, MoveException, WrongDiceNumberException {

        int shepardNearNumber = countShepardNear(sheepToKill.getPosition());
        Terrain sheepPosition = sheepToKill.getPosition();

        if (coins >= shepardNearNumber * 2) {
            if (isShepardNear(sheepPosition)) {
                if (randomNumberForShepard(sheepPosition, num)) {
                    sheepPosition.deleteAnimal(sheepToKill);
                    game.getSheeps().remove(sheepToKill);
                    int payment = payShepards(sheepPosition);
                    coins = coins - payment;
                } else {
                    throw new WrongDiceNumberException(num);
                }
            } else {
                throw new MoveException("Non c'è vicino un pastore");
            }
        } else {
            throw new CoinException("Non abbastanza soldi per comprare il silenzio di tutti i pastori");
        }

    }

}
