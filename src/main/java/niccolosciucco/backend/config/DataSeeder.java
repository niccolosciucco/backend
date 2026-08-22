package niccolosciucco.backend.config;

import lombok.RequiredArgsConstructor;
import niccolosciucco.backend.entity.*;
import niccolosciucco.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final List<String> ORDINE_PILOTI = List.of(
            "Kimi Antonelli", "George Russell", "Charles Leclerc", "Lewis Hamilton", "Lando Norris",
            "Max Verstappen", "Oscar Piastri", "Isack Hadjar", "Fernando Alonso", "Lance Stroll",
            "Carlos Sainz", "Alex Albon", "Pierre Gasly", "Franco Colapinto", "Esteban Ocon",
            "Oliver Bearman", "Liam Lawson", "Arvid Lindblad", "Nico Hülkenberg", "Gabriel Bortoleto",
            "Sergio Perez", "Valtteri Bottas"
    );
    private final TeamRepository teamRepository;
    private final PilotaRepository pilotaRepository;
    private final CircuitoRepository circuitoRepository;
    private final EventoRepository eventoRepository;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final RisultatoGaraRepository risultatoGaraRepository;
    private final PilotaRisultatoRepository pilotaRisultatoRepository;

    @Override
    public void run(String... args) {
        seedTeamsEPiloti();
        seedCircuitiEEventi();
        seedUtenti();
        seedStorico();
    }

    private void seedTeamsEPiloti() {
        if (teamRepository.count() > 0) {
            return;
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

        System.out.println("Seed team/piloti: " + teamRepository.count() + " team, " + pilotaRepository.count() + " piloti.");
    }

    private void seedCircuitiEEventi() {
        if (circuitoRepository.count() > 0) {
            return;
        }

        Circuito albertPark = circuitoRepository.save(Circuito.builder().name("Albert Park Circuit").location("Melbourne").country("Australia").lengthKm(5.278).laps(58).turns(14).drsZones(4).lapRecordTime("1:19.813").lapRecordDriver("Charles Leclerc").lapRecordYear(2024).description("Circuito cittadino attorno a un lago, riasfaltato negli ultimi anni per favorire i sorpassi con quattro zone DRS.").build());
        Circuito shanghai = circuitoRepository.save(Circuito.builder().name("Shanghai International Circuit").location("Shanghai").country("Cina").lengthKm(5.451).laps(56).turns(16).drsZones(2).lapRecordTime("1:32.238").lapRecordDriver("Michael Schumacher").lapRecordYear(2004).description("La curva 1 a spirale, che si stringe progressivamente, è tra le più impegnative del calendario per il carico da gestire.").build());
        Circuito suzuka = circuitoRepository.save(Circuito.builder().name("Suzuka International Racing Course").location("Suzuka").country("Giappone").lengthKm(5.807).laps(53).turns(18).drsZones(1).lapRecordTime("1:30.983").lapRecordDriver("Lewis Hamilton").lapRecordYear(2019).description("Unico circuito a forma di otto del calendario, con la celebre curva Esses che mette alla prova l'equilibrio della vettura.").build());
        Circuito miami = circuitoRepository.save(Circuito.builder().name("Miami International Autodrome").location("Miami").country("Stati Uniti").lengthKm(5.412).laps(57).turns(19).drsZones(3).lapRecordTime("1:29.708").lapRecordDriver("Max Verstappen").lapRecordYear(2023).description("Tracciato semi-cittadino attorno all'Hard Rock Stadium, con un lungo rettilineo e una sezione di curve a bassa velocità.").build());
        Circuito gillesVilleneuve = circuitoRepository.save(Circuito.builder().name("Circuit Gilles-Villeneuve").location("Montreal").country("Canada").lengthKm(4.361).laps(70).turns(14).drsZones(3).lapRecordTime("1:13.078").lapRecordDriver("Valtteri Bottas").lapRecordYear(2019).description("Ricavato su un'isola artificiale: lunghi rettilinei alternati a chicane strette, con il celebre 'muro dei campioni' all'ultima curva.").build());
        Circuito monaco = circuitoRepository.save(Circuito.builder().name("Circuit de Monaco").location("Monte Carlo").country("Monaco").lengthKm(3.337).laps(78).turns(19).drsZones(1).lapRecordTime("1:12.909").lapRecordDriver("Lewis Hamilton").lapRecordYear(2021).description("Il tracciato più stretto e lento del calendario, dove il sorpasso in pista è quasi impossibile e la qualifica conta più che altrove.").build());
        Circuito barcelona = circuitoRepository.save(Circuito.builder().name("Circuit de Barcelona-Catalunya").location("Montmeló").country("Spagna").lengthKm(4.657).laps(66).turns(14).drsZones(2).lapRecordTime("1:16.330").lapRecordDriver("Max Verstappen").lapRecordYear(2023).description("Curve ad alto carico aerodinamico che per decenni hanno ospitato i test pre-stagionali, mettendo alla prova ogni comparto della vettura.").build());
        Circuito redBullRing = circuitoRepository.save(Circuito.builder().name("Red Bull Ring").location("Spielberg").country("Austria").lengthKm(4.318).laps(71).turns(10).drsZones(3).lapRecordTime("1:05.619").lapRecordDriver("Carlos Sainz").lapRecordYear(2020).description("Il circuito permanente più corto del calendario, con forti pendenze e rettilinei brevi ma intensi.").build());
        Circuito silverstone = circuitoRepository.save(Circuito.builder().name("Silverstone Circuit").location("Silverstone").country("Regno Unito").lengthKm(5.891).laps(52).turns(18).drsZones(2).lapRecordTime("1:27.097").lapRecordDriver("Max Verstappen").lapRecordYear(2020).description("La culla della Formula 1, famosa per il susseguirsi di curve veloci Maggots-Becketts-Chapel prese quasi a pieno gas.").build());
        Circuito spa = circuitoRepository.save(Circuito.builder().name("Circuit de Spa-Francorchamps").location("Stavelot").country("Belgio").lengthKm(7.004).laps(44).turns(19).drsZones(2).lapRecordTime("1:46.286").lapRecordDriver("Valtteri Bottas").lapRecordYear(2018).description("Il circuito più lungo del calendario, reso leggendario dalla salita dell'Eau Rouge-Raidillon presa in pieno carico.").build());
        Circuito hungaroring = circuitoRepository.save(Circuito.builder().name("Hungaroring").location("Mogyoród").country("Ungheria").lengthKm(4.381).laps(70).turns(14).drsZones(1).lapRecordTime("1:16.627").lapRecordDriver("Lewis Hamilton").lapRecordYear(2020).description("Soprannominato 'Monaco senza guardrail': tracciato tortuoso dove il sorpasso in pista è raro e la strategia conta molto.").build());
        Circuito zandvoort = circuitoRepository.save(Circuito.builder().name("Circuit Zandvoort").location("Zandvoort").country("Paesi Bassi").lengthKm(4.259).laps(72).turns(14).drsZones(2).lapRecordTime("1:11.097").lapRecordDriver("Lewis Hamilton").lapRecordYear(2021).description("Tracciato costiero tra le dune, con curve sopraelevate uniche nel calendario che permettono velocità sorprendenti in ingresso curva.").build());
        Circuito monza = circuitoRepository.save(Circuito.builder().name("Autodromo Nazionale Monza").location("Monza").country("Italia").lengthKm(5.793).laps(53).turns(11).drsZones(2).lapRecordTime("1:21.046").lapRecordDriver("Rubens Barrichello").lapRecordYear(2004).description("Il tempio della velocità: rettilinei lunghissimi e frenate durissime alle chicane, poco carico aerodinamico richiesto.").build());
        Circuito madring = circuitoRepository.save(Circuito.builder().name("Madring").location("Madrid").country("Spagna").lengthKm(5.416).laps(57).turns(22).drsZones(2).description("Debutto assoluto nel 2026: circuito ibrido cittadino con 'La Monumental', la curva sopraelevata più lunga di tutto il calendario.").build());
        Circuito baku = circuitoRepository.save(Circuito.builder().name("Baku City Circuit").location("Baku").country("Azerbaigian").lengthKm(6.003).laps(51).turns(20).drsZones(2).lapRecordTime("1:43.009").lapRecordDriver("Charles Leclerc").lapRecordYear(2019).description("Combina un rettilineo lunghissimo lungo il mar Caspio con la sezione più stretta del calendario, tra le mura della città vecchia.").build());
        Circuito bahrain = circuitoRepository.save(Circuito.builder().name("Bahrain International Circuit").location("Sakhir").country("Bahrein").lengthKm(5.412).laps(57).turns(15).drsZones(3).lapRecordTime("1:31.447").lapRecordDriver("Pedro de la Rosa").lapRecordYear(2005).description("Pista nel deserto che per anni ha aperto la stagione, superficie molto abrasiva che stressa parecchio le gomme.").build());
        Circuito singapore = circuitoRepository.save(Circuito.builder().name("Marina Bay Street Circuit").location("Singapore").country("Singapore").lengthKm(4.940).laps(62).turns(19).drsZones(2).lapRecordTime("1:34.486").lapRecordDriver("Daniel Ricciardo").lapRecordYear(2024).description("L'unica gara notturna storica del calendario su strade cittadine, fisicamente durissima per il caldo e l'umidità.").build());
        Circuito austin = circuitoRepository.save(Circuito.builder().name("Circuit of the Americas").location("Austin").country("Stati Uniti").lengthKm(5.513).laps(56).turns(20).drsZones(2).lapRecordTime("1:36.169").lapRecordDriver("Charles Leclerc").lapRecordYear(2019).description("Il primo tratto ricalca Silverstone, con un forte dislivello alla curva 1 ispirato al Corkscrew di Laguna Seca.").build());
        Circuito mexicoCity = circuitoRepository.save(Circuito.builder().name("Autódromo Hermanos Rodríguez").location("Città del Messico").country("Messico").lengthKm(4.304).laps(71).turns(17).drsZones(3).lapRecordTime("1:17.774").lapRecordDriver("Valtteri Bottas").lapRecordYear(2021).description("L'altitudine di oltre 2200 metri riduce la densità dell'aria, penalizzando il carico aerodinamico e il raffreddamento dei motori.").build());
        Circuito interlagos = circuitoRepository.save(Circuito.builder().name("Autódromo José Carlos Pace").location("San Paolo").country("Brasile").lengthKm(4.309).laps(71).turns(15).drsZones(2).lapRecordTime("1:10.540").lapRecordDriver("Valtteri Bottas").lapRecordYear(2018).description("Percorso in senso antiorario con forti saliscendi, spesso teatro di gare bagnate e sorpassi spettacolari.").build());
        Circuito lasVegas = circuitoRepository.save(Circuito.builder().name("Las Vegas Strip Circuit").location("Las Vegas").country("Stati Uniti").lengthKm(6.201).laps(50).turns(17).drsZones(2).lapRecordTime("1:33.365").lapRecordDriver("Lando Norris").lapRecordYear(2024).description("Gara notturna sulla Strip, con rettilinei lunghissimi che regalano alcune delle velocità di punta più alte della stagione.").build());
        Circuito lusail = circuitoRepository.save(Circuito.builder().name("Lusail International Circuit").location("Lusail").country("Qatar").lengthKm(5.419).laps(57).turns(16).drsZones(2).lapRecordTime("1:22.384").lapRecordDriver("Lando Norris").lapRecordYear(2024).description("Curve veloci e fluide che si susseguono senza sosta, tra i tracciati fisicamente più impegnativi per il collo dei piloti.").build());
        Circuito yasMarina = circuitoRepository.save(Circuito.builder().name("Yas Marina Circuit").location("Abu Dhabi").country("Emirati Arabi Uniti").lengthKm(5.281).laps(58).turns(16).drsZones(2).lapRecordTime("1:26.103").lapRecordDriver("Max Verstappen").lapRecordYear(2021).description("Gara che si corre al tramonto fino a sera, tradizionalmente l'ultimo appuntamento della stagione.").build());

        eventoRepository.saveAll(List.of(
                Evento.builder().name("Gp del Giappone").circuito(suzuka).date(LocalDate.of(2026, 3, 27)).status(EventStatus.CONCLUSO).build(),
                Evento.builder().name("Gp del Bahrain").circuito(bahrain).date(LocalDate.of(2026, 10, 2)).status(EventStatus.PROGRAMMATO).build(),
                Evento.builder().name("Gp d'Italia").circuito(monza).date(LocalDate.of(2026, 9, 6)).status(EventStatus.PROGRAMMATO).build(),
                Evento.builder().name("Gp di Spagna").circuito(madring).date(LocalDate.of(2026, 9, 13)).status(EventStatus.PROGRAMMATO).build()
        ));

        System.out.println("Seed circuiti/eventi: " + circuitoRepository.count() + " circuiti, " + eventoRepository.count() + " eventi.");
    }

    private void seedUtenti() {
        if (utenteRepository.count() > 0) {
            return;
        }

        utenteRepository.save(Utente.builder()
                .email("admin@pitwallpro.it")
                .passwordHash(passwordEncoder.encode("admin123"))
                .role(RuoloUtente.ADMIN)
                .build());

        utenteRepository.save(Utente.builder()
                .email("viewer@pitwallpro.it")
                .passwordHash(passwordEncoder.encode("viewer123"))
                .role(RuoloUtente.USER)
                .build());

        System.out.println("Seed utenti: " + utenteRepository.count() + " utenti.");
    }

    private void seedStorico() {
        if (risultatoGaraRepository.count() > 0) {
            return;
        }

        Map<String, Pilota> pilotiPerNome = pilotaRepository.findAll().stream()
                .collect(Collectors.toMap(Pilota::getName, p -> p));
        Map<String, Circuito> circuitiPerNome = circuitoRepository.findAll().stream()
                .collect(Collectors.toMap(Circuito::getName, c -> c));

        seedGara("Gp d'Ungheria", circuitiPerNome.get("Hungaroring"), LocalDate.of(2026, 7, 26), 70, pilotiPerNome, List.of(
                new RisultatoSeed("Lando Norris", 0, false, true),
                new RisultatoSeed("Max Verstappen", 15.080, false, false),
                new RisultatoSeed("Kimi Antonelli", 18.728, false, false),
                new RisultatoSeed("George Russell", 24.611, false, false),
                new RisultatoSeed("Charles Leclerc", 30.955, false, false)
        ));

        seedGara("Gp del Belgio", circuitiPerNome.get("Circuit de Spa-Francorchamps"), LocalDate.of(2026, 7, 19), 44, pilotiPerNome, List.of(
                new RisultatoSeed("Kimi Antonelli", 0, false, true),
                new RisultatoSeed("Charles Leclerc", 1.952, false, false),
                new RisultatoSeed("Max Verstappen", 11.586, false, false),
                new RisultatoSeed("George Russell", 17.209, false, false),
                new RisultatoSeed("Lewis Hamilton", 22.740, false, false)
        ));

        seedGara("Gp di Gran Bretagna", circuitiPerNome.get("Silverstone Circuit"), LocalDate.of(2026, 7, 5), 52, pilotiPerNome, List.of(
                new RisultatoSeed("Charles Leclerc", 0, false, true),
                new RisultatoSeed("George Russell", 0.427, false, false),
                new RisultatoSeed("Lewis Hamilton", 0.772, false, false),
                new RisultatoSeed("Kimi Antonelli", 6.918, false, false),
                new RisultatoSeed("Max Verstappen", 12.304, false, false)
        ));

        seedGara("Gp d'Austria", circuitiPerNome.get("Red Bull Ring"), LocalDate.of(2026, 6, 28), 71, pilotiPerNome, List.of(
                new RisultatoSeed("George Russell", 0, false, true),
                new RisultatoSeed("Max Verstappen", 1.611, false, false),
                new RisultatoSeed("Kimi Antonelli", 1.986, false, false),
                new RisultatoSeed("Lando Norris", 8.312, false, false),
                new RisultatoSeed("Lewis Hamilton", 14.077, false, false)
        ));

        seedGara("Gp di Barcellona-Catalogna", circuitiPerNome.get("Circuit de Barcelona-Catalunya"), LocalDate.of(2026, 6, 14), 66, pilotiPerNome, List.of(
                new RisultatoSeed("Lewis Hamilton", 0, false, false),
                new RisultatoSeed("George Russell", 19.561, false, false),
                new RisultatoSeed("Lando Norris", 23.719, false, true),
                new RisultatoSeed("Kimi Antonelli", 30.204, false, false),
                new RisultatoSeed("Charles Leclerc", 36.550, false, false)
        ));

        seedGara("Gp di Monaco", circuitiPerNome.get("Circuit de Monaco"), LocalDate.of(2026, 6, 7), 78, pilotiPerNome, List.of(
                new RisultatoSeed("Kimi Antonelli", 0, false, false),
                new RisultatoSeed("Lewis Hamilton", 6.271, false, true),
                new RisultatoSeed("Pierre Gasly", 20.369, false, false),
                new RisultatoSeed("Charles Leclerc", 24.918, false, false),
                new RisultatoSeed("George Russell", 29.077, false, false)
        ));

        seedGara("Gp del Canada", circuitiPerNome.get("Circuit Gilles-Villeneuve"), LocalDate.of(2026, 5, 24), 70, pilotiPerNome, List.of(
                new RisultatoSeed("Kimi Antonelli", 0, false, false),
                new RisultatoSeed("Lewis Hamilton", 10.768, false, false),
                new RisultatoSeed("Max Verstappen", 11.276, false, true),
                new RisultatoSeed("Charles Leclerc", 18.903, false, false),
                new RisultatoSeed("George Russell", 25.611, false, false)
        ));

        seedGara("Gp di Miami", circuitiPerNome.get("Miami International Autodrome"), LocalDate.of(2026, 5, 3), 57, pilotiPerNome, List.of(
                new RisultatoSeed("Kimi Antonelli", 0, false, false),
                new RisultatoSeed("Lando Norris", 3.264, false, false),
                new RisultatoSeed("Oscar Piastri", 27.092, false, true),
                new RisultatoSeed("George Russell", 33.417, false, false),
                new RisultatoSeed("Lewis Hamilton", 39.802, false, false)
        ));

        seedGara("Gp del Giappone", circuitiPerNome.get("Suzuka International Racing Course"), LocalDate.of(2026, 3, 29), 53, pilotiPerNome, List.of(
                new RisultatoSeed("Kimi Antonelli", 0, false, false),
                new RisultatoSeed("Oscar Piastri", 13.722, false, false),
                new RisultatoSeed("Charles Leclerc", 15.270, false, true),
                new RisultatoSeed("George Russell", 21.055, false, false),
                new RisultatoSeed("Lando Norris", 27.398, false, false)
        ));

        seedGara("Gp di Cina", circuitiPerNome.get("Shanghai International Circuit"), LocalDate.of(2026, 3, 15), 56, pilotiPerNome, List.of(
                new RisultatoSeed("Kimi Antonelli", 0, false, true),
                new RisultatoSeed("George Russell", 5.515, false, false),
                new RisultatoSeed("Lewis Hamilton", 25.267, false, false),
                new RisultatoSeed("Charles Leclerc", 31.204, false, false),
                new RisultatoSeed("Max Verstappen", 38.910, false, false)
        ));

        seedGara("Gp d'Australia", circuitiPerNome.get("Albert Park Circuit"), LocalDate.of(2026, 3, 8), 58, pilotiPerNome, List.of(
                new RisultatoSeed("George Russell", 0, false, true),
                new RisultatoSeed("Kimi Antonelli", 2.974, false, false),
                new RisultatoSeed("Charles Leclerc", 15.519, false, false),
                new RisultatoSeed("Oscar Piastri", 22.108, false, false),
                new RisultatoSeed("Lando Norris", 28.554, false, false)
        ));

        System.out.println("Seed storico: " + risultatoGaraRepository.count() + " gare, " + pilotaRisultatoRepository.count() + " risultati.");
    }

    private void seedGara(String nome, Circuito circuito, LocalDate data, int giri, Map<String, Pilota> pilotiPerNome, List<RisultatoSeed> primiCinque) {
        RisultatoGara gara = risultatoGaraRepository.save(RisultatoGara.builder()
                .name(nome)
                .circuito(circuito)
                .date(data)
                .laps(giri)
                .build());

        List<PilotaRisultato> risultati = new ArrayList<>();

        // Posizioni 1-5: i risultati specifici già decisi per questa gara.
        for (int i = 0; i < primiCinque.size(); i++) {
            RisultatoSeed rs = primiCinque.get(i);
            Pilota pilota = pilotiPerNome.get(rs.pilotaName());
            risultati.add(PilotaRisultato.builder()
                    .risultatoGara(gara)
                    .pilota(pilota)
                    .position(rs.dnf() ? null : i + 1)
                    .gapSeconds(rs.dnf() ? null : rs.gapSeconds())
                    .status(rs.dnf() ? RaceResultStatus.DNF : RaceResultStatus.FINISHED)
                    .fastestLap(rs.fastestLap())
                    .build());
        }
        
        List<String> nomiGiaAssegnati = primiCinque.stream().map(RisultatoSeed::pilotaName).toList();
        double ultimoGap = primiCinque.get(primiCinque.size() - 1).gapSeconds();
        int posizione = primiCinque.size() + 1;

        for (String nomePilota : ORDINE_PILOTI) {
            if (nomiGiaAssegnati.contains(nomePilota)) {
                continue;
            }
            double gap = ultimoGap + 5 + (posizione - primiCinque.size() - 1) * 3.5;
            Pilota pilota = pilotiPerNome.get(nomePilota);
            risultati.add(PilotaRisultato.builder()
                    .risultatoGara(gara)
                    .pilota(pilota)
                    .position(posizione)
                    .gapSeconds(gap)
                    .status(RaceResultStatus.FINISHED)
                    .fastestLap(false)
                    .build());
            posizione++;
        }

        pilotaRisultatoRepository.saveAll(risultati);
    }

    private record RisultatoSeed(String pilotaName, double gapSeconds, boolean dnf, boolean fastestLap) {
    }
}