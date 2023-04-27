package ru.clevertec.ecl.util;

import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataStorage {
    private static final List<GiftCertificate> GIFT_CERTIFICATES;
    private static final List<Tag> tags;

    static {
        GIFT_CERTIFICATES = new ArrayList<>() {{
            add(new GiftCertificate(1L, "LPG-body and face massage on the \"LPG Cellu M6 Integral\" device in the aesthetic center \"Clouds\"", "Vacuum hardware-roller massage on the innovative 7th generation LPG Cellu M6 Integral apparatus is the most effective and safe method of figure correction and cellulite elimination. LPG is the only system for splitting fat deposits certified by the Food and Drug Administration (USA FDA). Endermology has a number of applications in the field of facial and body aesthetics (Lipomassage and Endermolift). The result of such procedures is to improve the quality of skin tissue, reduce signs of aging and fat localization.", BigDecimal.valueOf(5.32), 60, LocalDateTime.parse("2023-04-02T17:26:07.319"), null, new HashSet<>()));
            add(new GiftCertificate(2L, "Hot air balloon flights for adults and children", "Group balloon flights are an ideal opportunity to share the bright emotions of the flight with your loved ones, friends or colleagues. Aerotour Airline is pleased to offer you the largest balloon in Belarus for 6 people, where you can make your first flight together. If your company has more people, then we can always provide you with additional balloons so that you have the opportunity to go on your unforgettable balloon trip at the same time.", BigDecimal.valueOf(10.91), 90, LocalDateTime.parse("2023-04-17T13:56:25.818"), null, new HashSet<>()));
            add(new GiftCertificate(3L, "Gift certificates for nursing cosmetology and ultrasound with a discount of up to 50% at the IdealMed medical center", "With the approach of the holidays, the most urgent question is what to give to your family and friends. After all, it is this time that brings relatives and friends together, gives an opportunity and a reason to meet. And no matter how banal the tradition is, the most basic thing is laid down in it: each of us launches a boomerang of warmth and joy to each other. And in order for the holiday to be really enchanting, bright and joyful, you really need quite a bit: take care of gifts and surprises in advance. Cleansing cosmetic procedures are the key to full-fledged skin care to create a charming and attractive appearance. Only a cosmetologist of our IdealMed medical center can determine the type of problem, skin features, and technically correctly carry out cosmetic cleansing of the skin", BigDecimal.valueOf(12.47), 30, LocalDateTime.parse("2023-03-26T12:23:19.852"), null, new HashSet<>()));
            add(new GiftCertificate(4L, "Bowling at the Bowling Haus", "\"Bowling Haus\" is 10 lanes of Brunswick bowling equipment, four of which are equipped with automatically rising sides for children, which do not allow a ball thrown by a child to roll into the gutter. Modern interior design, soft and pleasant lighting of our club will boost your mood and allow you to spend a fun and relaxed time not only for you, but also for your friends or family. A varied cafe menu is provided for hungry players. A wide selection of dishes and drinks at the bar will allow you not only to enjoy a game of bowling, but also to arrange a small festive buffet for your family or guests. We constantly host amateur and professional bowling tournaments, corporate parties and childrens birthday parties", BigDecimal.valueOf(3.88), 120, LocalDateTime.parse("2023-04-11T15:04:18.051"), null, new HashSet<>()));
            add(new GiftCertificate(5L, "Visit to the contact zoo in the shopping center \"Titan\"", "The zoo is home to animals and birds that represent different latitudes and continents. All animals were fed by man and therefore willingly go to communicate with people. Adults and children can get acquainted with the Indian porcupine, sheep and chickens, and for exotic lovers - a huge green iguana. In our zoo, ordinary igrunki appeared. These monkeys are very emotional, able to express their feelings vividly with facial expressions, the movement of tufts of hair on their heads, and various voice signals. Visitors can see a magnificent peacock, flying dogs (Nile bats). By the way, we advise you to ask the zookeepers about its inhabitants – you can learn a lot of interesting things.", BigDecimal.valueOf(7.12), 30, LocalDateTime.parse("2023-04-09T20:07:09.661"), null, new HashSet<>()));
            add(new GiftCertificate(6L, "Visit to the Museum of Experimental Sciences \"Experimentus\" in the shopping center \"Palazzo\"", "Have you often been to exhibitions where the presented works could be touched with your hands without being afraid of angry glances and exclamations of curators? If you say that such expositions simply do not exist, it means that you have not been to the Expirimentus exhibition yet. It presents various inventions of scientists of the world. Among them are the prism and the cradle of Newton, the Pythagorean theorem and bowl, the wave pendulum, the Leonardo da Vinci bridge, the Edison light bulb, the plasma ball, the predecessor of the calculator - the arithmometer, the calculator itself and much more.", BigDecimal.valueOf(3.22), 60, LocalDateTime.parse("2023-03-23T08:52:04.192"), null, new HashSet<>()));
            add(new GiftCertificate(7L, "Trampoline jumping, individual training in the trampoline center \"Flash Park\"", "In our trampoline arena, everyone will find a suitable occupation. The trampoline center \"Flash park\" is an ideal place for outdoor enthusiasts of all ages. You can look in here to relax after strenuous classes at school, parties are held here and intense workouts are arranged after school or work, its nice to spend a weekend in the company of friends and family.", BigDecimal.valueOf(5.55), 90, LocalDateTime.parse("2023-03-21T18:51:59.847"), null, new HashSet<>()));
            add(new GiftCertificate(8L, "Rest in the private cinema \"Cinema room\"", "The Cinema Room is the first private cinema in our country. It consists of 7 separate rooms that are locked with a key and easily accommodate a different number of guests: up to 6 people, up to 10 people, up to 35 people. Each of the rooms for up to 6 people is equipped with a projector with a screen or a plasma TV (the choice is to the taste of the guest with prior reservation). A large selection of movies, the best games for PlayStation 4 pro, a virtual reality helmet (with pre-booking), a large selection of board games, karaoke in the VIP 2 room, a modern speaker system, during the session you are in a private environment and no one bothers you, the opportunity to bring food and drinks with you, popcorn tea and a wide selection of soft drinks for a fee", BigDecimal.valueOf(8.76), 14, LocalDateTime.parse("2023-04-12T18:29:26.769"), null, new HashSet<>()));
            add(new GiftCertificate(9L, "Group and individual quad bike rides", "The variation of our routes is so flexible and unique that everyone can travel along them. They include unique woodlands and trails, deep forests, swamps, turns and of course slides, both small and large. If you are not sure of yourself or are scared, you can always go on a trip as a passenger.", BigDecimal.valueOf(2.84), 21, LocalDateTime.parse("2023-03-20T11:42:40.345"), null, new HashSet<>()));
            add(new GiftCertificate(10L, "Shooting from an air pistol/submachine gun, rifle, crossbow in a pneumatic shooting range \"Pushka\"", "In the dash \"Pushka\" you will be able to shoot not only at classic paper targets and cans, but also try various exercises on an interactive steel screen that senses all your bullets with the help of sensors. This is a professional shooting simulator on which special services and hunters train. There are more than 100 targets in it: from static to realistic in the video game format. If you want to please your loved ones with a cool gift, purchase our gift certificates", BigDecimal.valueOf(1.29), 150, LocalDateTime.parse("2023-04-18T14:11:50.450"), null, new HashSet<>()));
        }};

        tags = new ArrayList<>() {{
            add(new Tag(1L, "entertainment", new HashSet<>()));
            add(new Tag(2L, "massage", new HashSet<>()));
            add(new Tag(3L, "health", new HashSet<>()));
            add(new Tag(4L, "shooting", new HashSet<>()));
            add(new Tag(5L, "zoo", new HashSet<>()));
            add(new Tag(6L, "museum", new HashSet<>()));
            add(new Tag(7L, "science", new HashSet<>()));
            add(new Tag(8L, "driving", new HashSet<>()));
            add(new Tag(9L, "swimming", new HashSet<>()));
            add(new Tag(10L, "extremal", new HashSet<>()));
            add(new Tag(11L, "rest", new HashSet<>()));
            add(new Tag(12L, "cinema", new HashSet<>()));
            add(new Tag(13L, "bowling", new HashSet<>()));
            add(new Tag(14L, "flying", new HashSet<>()));
            add(new Tag(15L, "cosmetology", new HashSet<>()));
            add(new Tag(16L, "adults", new HashSet<>()));
            add(new Tag(17L, "children", new HashSet<>()));
            add(new Tag(18L, "women", new HashSet<>()));
            add(new Tag(19L, "men", new HashSet<>()));
        }};
    }

    public static GiftCertificate findCertificateForFindById(Long id) {
        return GIFT_CERTIFICATES.get(Math.toIntExact(id) - 1);
    }

    public static List<GiftCertificate> findCertificateForFindAll() {
        return GIFT_CERTIFICATES;
    }

    public static List<GiftCertificate> findCertificateForFindAllWithCosmetologyTagFilter() {
        return List.of(GIFT_CERTIFICATES.get(0), GIFT_CERTIFICATES.get(2));
    }

    public static List<GiftCertificate> findCertificateForFindAllWithPartOfNameFilter() {
        return List.of(GIFT_CERTIFICATES.get(0), GIFT_CERTIFICATES.get(2), GIFT_CERTIFICATES.get(4));
    }

    public static List<GiftCertificate> findCertificateForFindAllWithPartOfDescriptionFilter() {
        return List.of(GIFT_CERTIFICATES.get(7), GIFT_CERTIFICATES.get(9));
    }

    public static GiftCertificate findCertificateForAdd() {
        return new GiftCertificate(null, "AddName", "AddDescription", BigDecimal.valueOf(7.77), 20, LocalDateTime.now().minusDays(100), null, new HashSet<>());
    }

    public static GiftCertificate findCertificateForUpdateName() {
        return new GiftCertificate(9L, "Hello", null, null, null, null, null, new HashSet<>());
    }

    public static GiftCertificate findCertificateForUpdateDescription() {
        return new GiftCertificate(9L, null, "Bye", null, null, null, null, new HashSet<>());
    }

    public static Tag findTagForFindById(Long id) {
        return tags.get(Math.toIntExact(id) - 1);
    }

    public static Tag findTagForFindByName(String name) {
        return tags.stream().filter(tag -> name.equals(tag.getName())).toList().get(0);
    }

    public static List<Tag> findTagsForFindAll() {
        return tags;
    }

    public static Tag findTagForAdd() {
        return new Tag(20L, "Hello", new HashSet<>());
    }

    public static Tag findTagForUpdate() {
        return new Tag(11L, "Bye", new HashSet<>());
    }
}
