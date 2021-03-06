package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepherd;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.TerrainCard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modellizza il Giocatore come controllore, rendendo disponibili le
 * varie mosse.
 *
 * @author Francesco Corsini
 */
public class Player {

    private List<Shepherd> shepherds;
    private List<List<TerrainCard>> terrainCardsOwned;
    private int coins;
    private boolean isFirstPlayer;
    private int idPlayer;
    private boolean onLine;

    private int finalPosition;
    private int finalScore;

    private String[] actionDone;

    /**
     * costruttore
     *
     * @param firstPlayer
     * @param idPlayer
     */
    public Player(boolean firstPlayer, int idPlayer) {
        this.finalPosition = 0;
        this.finalScore = 0;
        this.idPlayer = idPlayer;
        onLine = true;

        shepherds = new ArrayList<Shepherd>();
        terrainCardsOwned = new ArrayList<List<TerrainCard>>();

        actionDone = new String[3];
        for (int i = 0; i < 3; i++) {
            actionDone[i] = "";
        }

        coins = 20;
        isFirstPlayer = firstPlayer;

        //serve per inizializzare la lista di liste dell TerrainCardPool
        for (int i = 0; i < 6; i++) {
            List<TerrainCard> list = new ArrayList<TerrainCard>();
            terrainCardsOwned.add(list);
        }
    }

    /**
     * Ritorna la posizione finale, il primo ha valore 1
     *
     * @return int posizione in classifica
     */
    public int getFinalPosition() {
        return finalPosition;
    }

    /**
     * Setter della posizione finale, il primo ha valore 1
     *
     * @param finalPosition
     */
    public void setFinalPosition(int finalPosition) {
        this.finalPosition = finalPosition;
    }

    /**
     * Getter chiamato per calcolare lo score finale
     *
     * @return score del giocatore
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Setter per immettere il punteggio
     *
     * @param finalScore punteggio
     */
    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * Restituisce l'Arrey dei pastori
     *
     * @return Arrey di pastori
     */
    public List<Shepherd> getShepherds() {
        return shepherds;
    }

    /**
     * Ritorna la lista di carte possedute della tipologia desiderata
     *
     * @param string tipologia territorio
     * @return lista dei territori di quella tipologia
     */
    public List<TerrainCard> getTerrainCardsOwned(String string) {
        if (TypeCard.PLAIN.toString().equals(string)) {
            return terrainCardsOwned.get(0);
        } else if (TypeCard.FOREST.toString().equals(string)) {
            return terrainCardsOwned.get(1);
        } else if (TypeCard.RIVER.toString().equals(string)) {
            return terrainCardsOwned.get(2);
        } else if (TypeCard.DESERT.toString().equals(string)) {
            return terrainCardsOwned.get(3);
        } else if (TypeCard.MOUNTAIN.toString().equals(string)) {
            return terrainCardsOwned.get(4);
        } else {
            return terrainCardsOwned.get(5);
        }
    }

    /**
     * Method che permette al giocatore di comprare una carta terreno
     *
     * @param terrainKind la tipologia di terreno che si intende comprare
     * @param game il gameTable su cui si sta giocando
     * @return Il costo della carta
     * @throws CoinException se i soldi non sono sufficenti a comprare la carta
     * @throws CardException
     */
    public int buyTerrainCard(String terrainKind, GameTable game) throws CoinException, CardException {
        if (!canBuy(terrainKind)) {
            throw new CardException(Message.NO_SHEPARD_BUY_CARD.toString());
        }
        int cardLeft = game.getTerrainCardPool(terrainKind).size();
        if (cardLeft != 0) {
            int cost = 5 - cardLeft;
            if (this.coins >= cost) {
                coins = coins - cost;
                TerrainCard newTerrCard = new TerrainCard(terrainKind);
                this.getTerrainCardsOwned(terrainKind).add(newTerrCard);
                game.getTerrainCardPool(terrainKind).remove(0);

                return cost;
            } else {
                throw new CoinException(Message.NO_MONEY.toString());
            }
        } else {
            throw new CardException(Message.NO_OTHER_CARD.toString());
        }
    }

    private boolean canBuy(String kind) {
        if (!shepherds.isEmpty()) {
            for (Shepherd shepherd : shepherds) {
                if (shepherd.getPosition().getAdjacentTerrain1().getTerrainType().equals(kind)) {
                    return true;
                }
                if (shepherd.getPosition().getAdjacentTerrain2().getTerrainType().equals(kind)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method che muove un pastore da una strada ad un altra
     *
     * @param destination strada destinazione
     * @param shepherd pastore da muovere
     * @param game gioco su cui si sta giocando
     * @throws CoinException lanciata nel caso soldi insufficenti
     * @throws MoveException lanciata nel caso mossa illegale
     * @throws ShepherdException lanciata nel caso non sia un pastore del player
     */
    public void moveShepherd(Road destination, Shepherd shepherd, GameTable game) throws CoinException, MoveException, ShepherdException {
        if (shepherd.getOwner().getIdPlayer() == getIdPlayer()) {
            boolean canMove = canMoveShepherd(destination);
            Road shepPos = shepherd.getPosition();

            if (canMove) {
                checkCoinMovement(shepherd, destination);

                shepPos.setFence(true);
                shepPos.setHasShepherd(false);
                shepherd.setPosition(destination);
                shepherd.getPosition().setHasShepherd(true);
                game.decreaseFenceNumber();
            } else {
                throw new MoveException(Message.ROAD_OCCUPIED.toString());
            }
        } else {
            throw new ShepherdException(Message.NO_YOUR_SHEPARD.toString());
        }
    }

    private void checkCoinMovement(Shepherd shepherd, Road destination) throws CoinException {
        if (shepherd.isExpensiveMove(destination)) {
            if (coins == 0) {
                throw new CoinException(Message.NO_MONEY.toString());
            } else {
                coins--;
            }
        }
    }

    /**
     * Method per muove pecora tra due territori con in mezzo un pastore
     *
     * @param sheep pecora da muovere
     * @param destination dove si vuole muovere
     * @param game gioco su cui si sta giocando
     * @throws MoveException lanciata nel caso mossa illegale
     */
    public void moveSheep(Sheep sheep, Terrain destination, GameTable game) throws MoveException {

        Road shepPos;

        shepPos = moveSheepOnRoad(sheep.getPosition(), destination);
        if (playerHasShepherdOnRoad(shepPos)) {
            sheep.getPosition().getAnimals().remove(sheep);
            sheep.setPosition(destination);
            sheep.getPosition().getAnimals().add(sheep);
        } else {
            throw new MoveException(Message.NO_SHEPARD_ON_THE_ROAD.toString());
        }
    }

    /**
     * Method per fare accoppiare una pecora ed un montone e generare un agnello
     *
     * @param terrain terreno dove sono gli animali
     * @param game gioco su cui si sta giocando
     * @throws MoveException lanciata nel caso mossa illegale
     */
    public void joinSheeps(Terrain terrain, GameTable game) throws MoveException {
        if (isSheepAndRam(terrain)) {
            if (isShepherdNear(terrain)) {
                //prende l'id dell'ultima pecora(che è quella con id più alto
                int i = game.getSheeps().get(game.getSheeps().size() - 1).getId();

                //e inizializza la nuova pecora con l'id successivo
                Sheep sheep = new Sheep(terrain, false, i + 1);
                game.getSheeps().add(sheep);
            } else {
                throw new MoveException(Message.NO_NEAR_SHEPARD.toString());
            }
        } else {
            throw new MoveException(Message.NO_JOINABLE_ANIMAL.toString());
        }
    }

    /**
     * Restituisce il numero di coin del player
     *
     * @return int valore di coin
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Setta il numero di coin del player
     *
     * @param coins valore da settare
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Method per fare abbattimento di pecora
     *
     * @param sheepToKill pecora da abbattere
     * @param game gioco su cui si sta giocando
     * @throws CoinException lanciata se non ci sono abbastanza soldi
     * @throws MoveException lanciata nel caso mossa illegale
     * @throws WrongDiceNumberException nel caso il lancio non riotrni il numero
     * desiderato
     */
    public void killAnimal(Sheep sheepToKill, GameTable game) throws CoinException, MoveException, WrongDiceNumberException {
        int shepherdNearNumber = countShepherdNear(sheepToKill.getPosition());
        Terrain sheepPosition = sheepToKill.getPosition();

        if (coins >= shepherdNearNumber * 2) {
            if (isShepherdNear(sheepPosition)) {
                int random = game.getDice().getRandom();
                if (randomNumberForShepherd(sheepPosition, random)) {
                    sheepPosition.deleteAnimal(sheepToKill);
                    game.getSheeps().remove(sheepToKill);
                    int payment = payShepherds(sheepPosition);
                    coins = coins - payment;
                } else {
                    throw new WrongDiceNumberException("<html>" + Message.NO_CORRECT_DICE.toString() + random + "</html>");
                }
            } else {
                throw new MoveException(Message.NO_NEAR_SHEPARD.toString());
            }
        } else {
            throw new CoinException(Message.NO_MONEY_FOR_MAFIA.toString());
        }

    }

    /**
     * Restituisce l'id del player
     *
     * @return int idPlayer
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setta l'id del player
     *
     * @param idPlayer id da settare
     */
    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * Method di servizio utilizzato da moveShepherd che serve a vedere se la
     * destinazione è valida
     *
     * @param destination
     * @param game
     * @return
     */
    private boolean canMoveShepherd(Road destination) {
        if (destination.hasFence()) {
            return false;
        } else {
            return !destination.hasShepherd();
        }
    }

    /**
     * Method di servizio utilizzato da moveSheep che serve a vedere se la mossa
     * è valida
     *
     * @param terrainSheep terreno dove è la sheep
     * @param terrainDestination terreno dove deve essere mossa
     * @return la strada in mezzo tra i terreni dove ci deve essere lo shepherd
     * @throws MoveException viene lanciata se non c'è una strada tra i due
     * territori
     */
    private Road moveSheepOnRoad(Terrain terrainSheep, Terrain terrainDestination) throws MoveException {
        boolean isShepherd = false;
        Road shepPos = null;

        List<Road> roadsTerrainSheeps = terrainSheep.getAdjacentRoads();
        List<Road> roadsTerrainDestinations = terrainDestination.getAdjacentRoads();

        for (Road roadsTerrainSheep : roadsTerrainSheeps) {
            for (Road roadsTerrainDestination : roadsTerrainDestinations) {
                if (roadsTerrainSheep == roadsTerrainDestination) {
                    shepPos = roadsTerrainDestination;
                    isShepherd = true;
                }
            }
        }

        if (!isShepherd) {
            throw new MoveException(Message.NO_ROAD_COMUNICANT.toString());
        }

        return shepPos;
    }

    /**
     * Method di servizio utilizzato da moveSheepOnRoad per vedere se sulla
     * strada dove devono trasitare le pecore c'è un pastore del giocatore che
     * ha richiesto la mossa
     *
     * @param road strada dove devono passare le pecore
     * @return true se c'è pastore del giocatore
     */
    private boolean playerHasShepherdOnRoad(Road road) {
        boolean thereIsShepherd = false;

        for (Shepherd shepherd : shepherds) {
            if (shepherd.getPosition().getId() == road.getId()) {
                thereIsShepherd = true;
            }
        }

        return thereIsShepherd;
    }

    /**
     * Method di servizio utilizzato da joinSheeps e per controllare se c'è una
     * pecora ed un montone sul terreno
     *
     * @param terrain terreno da controllare
     * @return true se ci sono entrambi
     */
    private boolean isSheepAndRam(Terrain terrain) {
        boolean thereIsSheep = false;
        boolean thereIsRam = false;

        for (Animal animal : terrain.getAnimals()) {
            if (animal instanceof Sheep) {
                Sheep sheep = (Sheep) animal;
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
     * Method di servizio utilizzato da joinSheeps e da killAnimal per
     * controllare se c'è un pastore in una strada confinante
     *
     * @param terrain terreno su cui cercheremo
     * @return true se c'è pastore vicino
     */
    private boolean isShepherdNear(Terrain terrain) {
        boolean thereIsShepherd = false;

        for (Road road : terrain.getAdjacentRoads()) {
            for (Shepherd shepherd : shepherds) {
                if (shepherd.getPosition() == road) {
                    thereIsShepherd = true;
                }
            }
        }

        return thereIsShepherd;
    }

    /**
     * Method di servizio utilizzato da killAnimal per contare pastori
     *
     * @param terrain dove contare
     * @return num pastori
     */
    private int countShepherdNear(Terrain terrain) {
        int number = 0;
        for (Road road : terrain.getAdjacentRoads()) {
            if (road.hasShepherd()) {
                if (road.getShepherd().getOwner().getIdPlayer() != idPlayer) {
                    number++;
                }
            }
        }
        return number;
    }

    /**
     * Method di servizio utilizzato da joinSheeps e da killAnimal per vedere se
     * un tiro di dado corrisponde alla strada su cui è il pastore
     *
     * @param terrain terreno dove si trova la pecora/e in questione
     * @return true se dal dado esce il numero giusto
     */
    private boolean randomNumberForShepherd(Terrain terrain, int random) {
        boolean gotRightNumber = false;

        for (Road road : terrain.getAdjacentRoads()) {
            for (Shepherd shepherd : shepherds) {
                if (shepherd.getPosition() == road
                        && random == road.getRoadNumber()) {
                    gotRightNumber = true;
                }
            }
        }

        return gotRightNumber;
    }

    /**
     * Method per pagare i pastori
     *
     * @param terrain
     * @return La cifra di coin pagati
     */
    private int payShepherds(Terrain terrain) {
        int totalCost = 0;

        for (Road road : terrain.getAdjacentRoads()) {
            for (Shepherd shepherd : shepherds) {
                if (road.hasShepherd()
                        && shepherd.getId() != road.getShepherd().getId()) {
                    totalCost = totalCost + 2;
                    shepherd.getOwner().setCoins(shepherd.getOwner().getCoins() + 2);
                }
            }
        }

        return totalCost;
    }

    /**
     * Imposta il player come primo giocatore del round
     *
     * @param val valore da settare
     */
    public void setFirstPlayer(boolean val) {
        isFirstPlayer = val;
    }

    /**
     * Controlla se è il primo player del round
     *
     * @return True se è il primo
     */
    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    /**
     * Controlla se è possibile effettuare l'azione in base a quelle già fatte
     * (3 azioni massime da verificare)
     *
     * @param action Azione da effettuare
     * @return True se può effettuare la mossa
     */
    public boolean isPossibleAction(String action) {
        if (!"".equals(actionDone[2])) {
            cleanActionDone();
        }

        if ("".equals(actionDone[0])) {
            actionDone[0] = action;
            return true;
        }

        if ("".equals(actionDone[1])
                && ((action.equals(actionDone[0])
                && TypeAction.MOVE_SHEPARD.toString().equals(action))
                || (!action.equals(actionDone[0])))) {
            actionDone[1] = action;
            return true;
        }

        if ("".equals(actionDone[2])) {
            if (action.equals(actionDone[1]) && TypeAction.MOVE_SHEPARD.toString().equals(action)) {
                actionDone[2] = action;
                return true;
            }

            if (TypeAction.MOVE_SHEPARD.toString().equals(action)
                    || (TypeAction.MOVE_SHEPARD.toString().equals(actionDone[0]) && !action.equals(actionDone[1]))
                    || (TypeAction.MOVE_SHEPARD.toString().equals(actionDone[1]))) {
                actionDone[2] = action;
                return true;
            }
        }

        return false;
    }

    /**
     * Svuota la lista delle azioni fatte
     */
    public void cleanActionDone() {
        actionDone = new String[3];
        for (int i = 0; i < 3; i++) {
            actionDone[i] = "";
        }
    }

    /**
     * Nel caso in cui l'ultima azione non sia andata a buon fine viene chiamato
     * questo method per rimuovere l'ultima azione effettuata
     */
    public void clearLastAction() {
        for (int i = 2; i >= 0; i--) {
            if (!"".equals(actionDone[i])) {
                actionDone[i] = "";
                return;
            }
        }
    }

    /**
     * Method solo per TEST
     *
     * @param sheepToKill pecora da uccidere
     * @param game gameTable del gioco
     * @param num valore del dado
     * @throws CoinException
     * @throws MoveException
     * @throws WrongDiceNumberException
     */
    public void killAnimal(Sheep sheepToKill, GameTable game, int num) throws CoinException, MoveException, WrongDiceNumberException {
        int shepherdNearNumber = countShepherdNear(sheepToKill.getPosition());
        Terrain sheepPosition = sheepToKill.getPosition();

        if (coins >= shepherdNearNumber * 2) {
            if (isShepherdNear(sheepPosition)) {
                if (randomNumberForShepherd(sheepPosition, num)) {
                    sheepPosition.deleteAnimal(sheepToKill);
                    game.getSheeps().remove(sheepToKill);
                    int payment = payShepherds(sheepPosition);
                    coins = coins - payment;
                } else {
                    throw new WrongDiceNumberException(Message.NO_CORRECT_DICE.toString() + num);
                }
            } else {
                throw new MoveException(Message.NO_NEAR_SHEPARD.toString());
            }
        } else {
            throw new CoinException(Message.NO_MONEY_FOR_MAFIA.toString());
        }

    }

    /**
     * Restituisce lo status del player
     *
     * @return True se è online
     */
    public boolean isOnLine() {
        return onLine;
    }

    /**
     * Setta lo status del player
     *
     * @param onLine True se è online
     */
    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

}
