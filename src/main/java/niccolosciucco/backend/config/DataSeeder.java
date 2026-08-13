package niccolosciucco.backend.config;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.Pilota;
import niccolosciucco.backend.entity.Team;
import niccolosciucco.backend.repository.PilotaRepository;
import niccolosciucco.backend.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final PilotaRepository pilotaRepository;

    @Override
    public void run(String... args) {
        if (teamRepository.count() > 0) {
            return; // dati già presenti, non duplicare ad ogni riavvio
        }

        Team mclaren = teamRepository.save(Team.builder().name("McLaren").base("Woking, Regno Unito").principal("Andrea Stella").foundedYear(1963).colorHex("#FF8000").build());
        Team mercedes = teamRepository.save(Team.builder().name("Mercedes").base("Brackley, Regno Unito").principal("Toto Wolff").foundedYear(1970).colorHex("#27F4D2").build());
        Team ferrari = teamRepository.save(Team.builder().name("Ferrari").base("Maranello, Italia").principal("Fred Vasseur").foundedYear(1929).colorHex("#E8002D").build());
        Team redbull = teamRepository.save(Team.builder().name("Red Bull Racing").base("Milton Keynes, Regno Unito").principal("Laurent Mekies").foundedYear(2005).colorHex("#1E41FF").build());
        Team astonMartin = teamRepository.save(Team.builder().name("Aston Martin").base("Silverstone, Regno Unito").principal("Adrian Newey").foundedYear(2021).colorHex("#00594F").build());
        Team williams = teamRepository.save(Team.builder().name("Williams").base("Grove, Regno Unito").principal("James Vowles").foundedYear(1977).colorHex("#00A0DE").build());
        Team alpine = teamRepository.save(Team.builder().name("Alpine").base("Enstone, Regno Unito").principal("Steve Nielsen").foundedYear(2021).colorHex("#0090FF").build());
        Team haas = teamRepository.save(Team.builder().name("Haas").base("Kannapolis, Stati Uniti").principal("Ayao Komatsu").foundedYear(2016).colorHex("#B6BABD").build());
        Team racingBulls = teamRepository.save(Team.builder().name("Racing Bulls").base("Faenza, Italia").principal("Alan Permane").foundedYear(2006).colorHex("#6C98FF").build());
        Team audi = teamRepository.save(Team.builder().name("Audi").base("Hinwil, Svizzera").principal("Jonathan Wheatley").foundedYear(2026).colorHex("#BB0A30").build());
        Team cadillac = teamRepository.save(Team.builder().name("Cadillac").base("Fishers, Stati Uniti").principal("Graeme Lowdon").foundedYear(2026).colorHex("#FFCB05").build());

        pilotaRepository.saveAll(List.of(
                Pilota.builder().name("Oscar Piastri").team(mclaren).nationality("AUS").number(81).build(),
                Pilota.builder().name("Lando Norris").team(mclaren).nationality("GBR").number(1).build(),
                Pilota.builder().name("George Russell").team(mercedes).nationality("GBR").number(63).build(),
                Pilota.builder().name("Kimi Antonelli").team(mercedes).nationality("ITA").number(12).build(),
                Pilota.builder().name("Charles Leclerc").team(ferrari).nationality("MON").number(16).build(),
                Pilota.builder().name("Lewis Hamilton").team(ferrari).nationality("GBR").number(44).build(),
                Pilota.builder().name("Max Verstappen").team(redbull).nationality("NED").number(3).build(),
                Pilota.builder().name("Isack Hadjar").team(redbull).nationality("FRA").number(6).build(),
                Pilota.builder().name("Fernando Alonso").team(astonMartin).nationality("ESP").number(14).build(),
                Pilota.builder().name("Lance Stroll").team(astonMartin).nationality("CAN").number(18).build(),
                Pilota.builder().name("Carlos Sainz").team(williams).nationality("ESP").number(55).build(),
                Pilota.builder().name("Alex Albon").team(williams).nationality("THA").number(23).build(),
                Pilota.builder().name("Pierre Gasly").team(alpine).nationality("FRA").number(10).build(),
                Pilota.builder().name("Franco Colapinto").team(alpine).nationality("ARG").number(43).build(),
                Pilota.builder().name("Esteban Ocon").team(haas).nationality("FRA").number(31).build(),
                Pilota.builder().name("Oliver Bearman").team(haas).nationality("GBR").number(87).build(),
                Pilota.builder().name("Liam Lawson").team(racingBulls).nationality("NZL").number(30).build(),
                Pilota.builder().name("Arvid Lindblad").team(racingBulls).nationality("GBR").number(41).build(),
                Pilota.builder().name("Nico Hülkenberg").team(audi).nationality("GER").number(27).build(),
                Pilota.builder().name("Gabriel Bortoleto").team(audi).nationality("BRA").number(5).build(),
                Pilota.builder().name("Sergio Perez").team(cadillac).nationality("MEX").number(11).build(),
                Pilota.builder().name("Valtteri Bottas").team(cadillac).nationality("FIN").number(77).build()
        ));

        System.out.println("Dati di partenza inseriti: " + teamRepository.count() + " team, " + pilotaRepository.count() + " piloti.");
    }
}
