package com.example.aerospace;

import java.util.*;

/**
 * Clase principal que contiene el punto de entrada al sistema de gestión aeroespacial.
 */
public class AerospaceManagementSystem {
    public static void main(String[] args) {
        // Ejemplo de uso de las clases y métodos
        Rocket rocket = new Rocket("Falcon 9", "SpaceX");
        Satellite satellite = new Satellite("Hubble", "NASA");

        Mission mission = new Mission("Mars Exploration", rocket);
        mission.addPayload(satellite);

        System.out.println(mission.getMissionDetails());
    }
}

/**
 * Clase que representa una nave espacial.
 */
abstract class Spacecraft {
    private String name;
    private String agency;

    /**
     * Constructor de la clase Spacecraft.
     * @param name Nombre de la nave espacial.
     * @param agency Agencia a la que pertenece la nave espacial.
     */
    public Spacecraft(String name, String agency) {
        this.name = name;
        this.agency = agency;
    }

    /**
     * Obtiene el nombre de la nave espacial.
     * @return El nombre de la nave espacial.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la agencia a la que pertenece la nave espacial.
     * @return La agencia a la que pertenece la nave espacial.
     */
    public String getAgency() {
        return agency;
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para describir la misión de la nave espacial.
     * @return La descripción de la misión.
     */
    public abstract String getMissionDescription();
}

/**
 * Clase que representa un cohete.
 */
class Rocket extends Spacecraft {

    /**
     * Constructor de la clase Rocket.
     * @param name Nombre del cohete.
     * @param agency Agencia a la que pertenece el cohete.
     */
    public Rocket(String name, String agency) {
        super(name, agency);
    }

    /**
     * Describe la misión del cohete.
     * @return La descripción de la misión del cohete.
     */
    @Override
    public String getMissionDescription() {
        return "Este cohete lanzará cargas útiles al espacio.";
    }
}

/**
 * Clase que representa un satélite.
 */
class Satellite extends Spacecraft {

    /**
     * Constructor de la clase Satellite.
     * @param name Nombre del satélite.
     * @param agency Agencia a la que pertenece el satélite.
     */
    public Satellite(String name, String agency) {
        super(name, agency);
    }

    /**
     * Describe la misión del satélite.
     * @return La descripción de la misión del satélite.
     */
    @Override
    public String getMissionDescription() {
        return "Este satélite observará y recopilará datos desde el espacio.";
    }
}

/**
 * Clase que representa una misión espacial.
 */
class Mission {
    private String missionName;
    private Spacecraft spacecraft;
    private List<Spacecraft> payloads = new ArrayList<>();

    /**
     * Constructor de la clase Mission.
     * @param missionName Nombre de la misión.
     * @param spacecraft Nave espacial principal de la misión.
     */
    public Mission(String missionName, Spacecraft spacecraft) {
        this.missionName = missionName;
        this.spacecraft = spacecraft;
    }

    /**
     * Añade una carga útil a la misión.
     * @param payload La nave espacial que se añadirá como carga útil.
     */
    public void addPayload(Spacecraft payload) {
        payloads.add(payload);
    }

    /**
     * Obtiene los detalles de la misión.
     * @return Los detalles de la misión.
     */
    public String getMissionDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Misión: ").append(missionName).append("\n");
        details.append("Nave Principal: ").append(spacecraft.getName()).append(" - ").append(spacecraft.getAgency()).append("\n");
        details.append("Descripción: ").append(spacecraft.getMissionDescription()).append("\n");

        if (!payloads.isEmpty()) {
            details.append("Cargas Útiles:\n");
            for (Spacecraft payload : payloads) {
                details.append("  - ").append(payload.getName()).append(" - ").append(payload.getAgency()).append("\n");
                details.append("    Descripción: ").append(payload.getMissionDescription()).append("\n");
            }
        }

        return details.toString();
    }
}
