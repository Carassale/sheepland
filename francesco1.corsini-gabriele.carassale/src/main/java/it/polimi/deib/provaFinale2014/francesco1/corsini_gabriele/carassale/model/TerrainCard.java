package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

/**
 * Carte Terreno. Utile avere classe per poter gestire il loro scambio tra
 * giocatori
 *
 * @author Francesco Corsini
 */
public class TerrainCard {

    private String terrainType;
    private int id;

    /**
     * costruttore di default utilizzato nella creazione iniziale del GameTable
     */
    public TerrainCard() {
    }

    /**
     * costruttore usato dal Giocatore quando compra una carta terra
     *
     * @param territoryType tipologia territorio che si vuole comprare
     */
    public TerrainCard(String territoryType) {
        terrainType = territoryType;
    }

    /**
     * Restituisce il tipo di carta terreno
     *
     * @return Stringa contenente il tipo di terreno
     */
    public String getTerrainType() {
        return terrainType;
    }

    /**
     * Setta il tipo di terreno
     *
     * @param terrainType Tipo terreno da settare
     */
    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }

    /**
     * Restituisce l'id della carta
     *
     * @return int ID della carta
     */
    public int getId() {
        return id;
    }

    /**
     * Setta l'Id della carta
     *
     * @param id int da settare
     */
    public void setId(int id) {
        this.id = id;
    }

}
