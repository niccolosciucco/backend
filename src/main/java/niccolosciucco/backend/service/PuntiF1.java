package niccolosciucco.backend.service;

import niccolosciucco.backend.entity.PilotaRisultato;
import niccolosciucco.backend.entity.RaceResultStatus;

import java.util.Map;

public final class PuntiF1 {

    private static final Map<Integer, Integer> PUNTI_PER_POSIZIONE = Map.ofEntries(
            Map.entry(1, 25), Map.entry(2, 18), Map.entry(3, 15), Map.entry(4, 12), Map.entry(5, 10),
            Map.entry(6, 8), Map.entry(7, 6), Map.entry(8, 4), Map.entry(9, 2), Map.entry(10, 1)
    );

    private PuntiF1() {
    }

    public static int calcola(PilotaRisultato risultato) {
        if (risultato.getStatus() == RaceResultStatus.DNF || risultato.getPosition() == null) {
            return 0;
        }
        int punti = PUNTI_PER_POSIZIONE.getOrDefault(risultato.getPosition(), 0);
        if (risultato.isFastestLap() && risultato.getPosition() <= 10) {
            punti += 1;
        }
        return punti;
    }
}