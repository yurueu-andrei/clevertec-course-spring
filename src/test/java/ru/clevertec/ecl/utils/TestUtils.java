package ru.clevertec.ecl.utils;

import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderListDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.UserListDto;
import ru.clevertec.ecl.dto.UserSaveDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtils {

    private static final List<GiftCertificate> GIFT_CERTIFICATES;
    private static final List<Tag> TAGS;
    private static final List<Order> ORDERS;
    private static final List<User> USERS;

    static {
        USERS = new ArrayList<>() {{
            add(new User(1L,
                    "Andrei",
                    "Yuryeu",
                    20,
                    "andrei.yurueu1@gmail.com",
                    "andr562ei123",
                    new HashSet<>()));

            add(new User(2L,
                    "Anastasija",
                    "Yurkova",
                    21,
                    "anastasija.yurkova33@gmail.com",
                    "5616541561",
                    new HashSet<>()));

            add(new User(3L,
                    "Yaroslav",
                    "Vasilevskii",
                    22,
                    "yaros_333@mail.ru",
                    "yaroslav010403",
                    new HashSet<>()));

            add(new User(4L,
                    "Alexander",
                    "Kuprijanenko",
                    23,
                    "alex_kupr228@mail.ru",
                    "qwerty12345",
                    new HashSet<>()));

            add(new User(5L,
                    "Oleg",
                    "Potapenko",
                    24,
                    "over_potap1337@gmail.com",
                    "777alivaria777",
                    new HashSet<>()));
        }};

        ORDERS = new ArrayList<>() {{
            add(new Order(1L,
                    LocalDateTime.parse("2023-05-03T17:26:07.319"),
                    BigDecimal.valueOf(1.29),
                    USERS.get(0), null));

            add(new Order(2L,
                    LocalDateTime.parse("2023-05-04T17:26:07.319"),
                    BigDecimal.valueOf(10.90),
                    USERS.get(1), null));

            add(new Order(3L,
                    LocalDateTime.parse("2023-05-05T17:26:07.319"),
                    BigDecimal.valueOf(12.47),
                    USERS.get(1), null));

            add(new Order(4L,
                    LocalDateTime.parse("2023-05-02T17:26:07.319"),
                    BigDecimal.valueOf(1.29),
                    USERS.get(2), null));

            add(new Order(5L,
                    LocalDateTime.parse("2023-05-01T17:26:07.319"),
                    BigDecimal.valueOf(10.90),
                    USERS.get(2), null));

            add(new Order(6L,
                    LocalDateTime.parse("2023-05-06T17:26:07.319"),
                    BigDecimal.valueOf(12.47),
                    USERS.get(2), null));

            add(new Order(7L,
                    LocalDateTime.parse("2023-05-07T17:26:07.319"),
                    BigDecimal.valueOf(3.88),
                    USERS.get(3), null));

            add(new Order(8L,
                    LocalDateTime.parse("2023-05-05T17:26:07.319"),
                    BigDecimal.valueOf(7.12),
                    USERS.get(3), null));

            add(new Order(9L,
                    LocalDateTime.parse("2023-05-04T17:26:07.319"),
                    BigDecimal.valueOf(3.22),
                    USERS.get(3), null));

            add(new Order(10L,
                    LocalDateTime.parse("2023-05-03T17:26:07.319"),
                    BigDecimal.valueOf(5.55),
                    USERS.get(3), null));

            add(new Order(11L,
                    LocalDateTime.parse("2023-05-01T17:26:07.319"),
                    BigDecimal.valueOf(7.12),
                    USERS.get(4), null));

            add(new Order(12L,
                    LocalDateTime.parse("2023-05-07T17:26:07.319"),
                    BigDecimal.valueOf(3.22),
                    USERS.get(4), null));

            add(new Order(13L,
                    LocalDateTime.parse("2023-05-08T17:26:07.319"),
                    BigDecimal.valueOf(5.55),
                    USERS.get(4), null));

            add(new Order(14L,
                    LocalDateTime.parse("2023-05-02T17:26:07.319"),
                    BigDecimal.valueOf(8.76),
                    USERS.get(4), null));

            add(new Order(15L,
                    LocalDateTime.parse("2023-05-05T17:26:07.319"),
                    BigDecimal.valueOf(2.84),
                    USERS.get(4), null));
        }};

        GIFT_CERTIFICATES = new ArrayList<>() {{
            add(new GiftCertificate(1L,
                    "LPG-body and face massage on the \"LPG Cellu M6 Integral\" device in the aesthetic center \"Clouds\"",
                    "Vacuum hardware-roller massage on the innovative 7th generation LPG Cellu M6 Integral apparatus is the most effective and safe method of figure correction and cellulite elimination. LPG is the only system for splitting fat deposits certified by the Food and Drug Administration (USA FDA). Endermology has a number of applications in the field of facial and body aesthetics (Lipomassage and Endermolift). The result of such procedures is to improve the quality of skin tissue, reduce signs of aging and fat localization.",
                    BigDecimal.valueOf(5.32),
                    60,
                    LocalDateTime.parse("2023-04-02T17:26:07.319"), LocalDateTime.parse("2023-04-02T17:26:07.319"),
                    new HashSet<>(), Set.of(ORDERS.get(3))));

            add(new GiftCertificate(2L,
                    "Hot air balloon flights for adults and children",
                    "Group balloon flights are an ideal opportunity to share the bright emotions of the flight with your loved ones, friends or colleagues. Aerotour Airline is pleased to offer you the largest balloon in Belarus for 6 people, where you can make your first flight together. If your company has more people, then we can always provide you with additional balloons so that you have the opportunity to go on your unforgettable balloon trip at the same time.",
                    BigDecimal.valueOf(10.90).setScale(2),
                    90,
                    LocalDateTime.parse("2023-04-17T13:56:25.818"), LocalDateTime.parse("2023-04-17T13:56:25.818"),
                    new HashSet<>(), Set.of(ORDERS.get(1), ORDERS.get(4))));

            add(new GiftCertificate(3L,
                    "Gift certificates for nursing cosmetology and ultrasound with a discount of up to 50% at the IdealMed medical center",
                    "With the approach of the holidays, the most urgent question is what to give to your family and friends. After all, it is this time that brings relatives and friends together, gives an opportunity and a reason to meet. And no matter how banal the tradition is, the most basic thing is laid down in it: each of us launches a boomerang of warmth and joy to each other. And in order for the holiday to be really enchanting, bright and joyful, you really need quite a bit: take care of gifts and surprises in advance. Cleansing cosmetic procedures are the key to full-fledged skin care to create a charming and attractive appearance. Only a cosmetologist of our IdealMed medical center can determine the type of problem, skin features, and technically correctly carry out cosmetic cleansing of the skin",
                    BigDecimal.valueOf(12.47),
                    30,
                    LocalDateTime.parse("2023-03-26T12:23:19.852"), LocalDateTime.parse("2023-03-26T12:23:19.852"),
                    new HashSet<>(), Set.of(ORDERS.get(2), ORDERS.get(5))));

            add(new GiftCertificate(4L,
                    "Bowling at the Bowling Haus",
                    "\"Bowling Haus\" is 10 lanes of Brunswick bowling equipment, four of which are equipped with automatically rising sides for children, which do not allow a ball thrown by a child to roll into the gutter. Modern interior design, soft and pleasant lighting of our club will boost your mood and allow you to spend a fun and relaxed time not only for you, but also for your friends or family. A varied cafe menu is provided for hungry players. A wide selection of dishes and drinks at the bar will allow you not only to enjoy a game of bowling, but also to arrange a small festive buffet for your family or guests. We constantly host amateur and professional bowling tournaments, corporate parties and childrens birthday parties",
                    BigDecimal.valueOf(3.88),
                    120,
                    LocalDateTime.parse("2023-04-11T15:04:18.051"), LocalDateTime.parse("2023-04-11T15:04:18.051"),
                    new HashSet<>(), Set.of(ORDERS.get(6))));

            add(new GiftCertificate(5L,
                    "Visit to the contact zoo in the shopping center \"Titan\"",
                    "The zoo is home to animals and birds that represent different latitudes and continents. All animals were fed by man and therefore willingly go to communicate with people. Adults and children can get acquainted with the Indian porcupine, sheep and chickens, and for exotic lovers - a huge green iguana. In our zoo, ordinary igrunki appeared. These monkeys are very emotional, able to express their feelings vividly with facial expressions, the movement of tufts of hair on their heads, and various voice signals. Visitors can see a magnificent peacock, flying dogs (Nile bats). By the way, we advise you to ask the zookeepers about its inhabitants – you can learn a lot of interesting things.",
                    BigDecimal.valueOf(7.12),
                    30,
                    LocalDateTime.parse("2023-04-09T20:07:09.661"), LocalDateTime.parse("2023-04-09T20:07:09.661"),
                    new HashSet<>(), Set.of(ORDERS.get(7), ORDERS.get(10))));

            add(new GiftCertificate(6L,
                    "Visit to the Museum of Experimental Sciences \"Experimentus\" in the shopping center \"Palazzo\"",
                    "Have you often been to exhibitions where the presented works could be touched with your hands without being afraid of angry glances and exclamations of curators? If you say that such expositions simply do not exist, it means that you have not been to the Expirimentus exhibition yet. It presents various inventions of scientists of the world. Among them are the prism and the cradle of Newton, the Pythagorean theorem and bowl, the wave pendulum, the Leonardo da Vinci bridge, the Edison light bulb, the plasma ball, the predecessor of the calculator - the arithmometer, the calculator itself and much more.",
                    BigDecimal.valueOf(3.22),
                    60,
                    LocalDateTime.parse("2023-03-23T08:52:04.192"), LocalDateTime.parse("2023-03-23T08:52:04.192"),
                    new HashSet<>(), Set.of(ORDERS.get(8), ORDERS.get(11))));

            add(new GiftCertificate(7L,
                    "Trampoline jumping, individual training in the trampoline center \"Flash Park\"",
                    "In our trampoline arena, everyone will find a suitable occupation. The trampoline center \"Flash park\" is an ideal place for outdoor enthusiasts of all ages. You can look in here to relax after strenuous classes at school, parties are held here and intense workouts are arranged after school or work, its nice to spend a weekend in the company of friends and family.",
                    BigDecimal.valueOf(5.55),
                    90,
                    LocalDateTime.parse("2023-03-21T18:51:59.847"), LocalDateTime.parse("2023-03-21T18:51:59.847"),
                    new HashSet<>(), Set.of(ORDERS.get(9), ORDERS.get(12))));

            add(new GiftCertificate(8L,
                    "Rest in the private cinema \"Cinema room\"",
                    "The Cinema Room is the first private cinema in our country. It consists of 7 separate rooms that are locked with a key and easily accommodate a different number of guests: up to 6 people, up to 10 people, up to 35 people. Each of the rooms for up to 6 people is equipped with a projector with a screen or a plasma TV (the choice is to the taste of the guest with prior reservation). A large selection of movies, the best games for PlayStation 4 pro, a virtual reality helmet (with pre-booking), a large selection of board games, karaoke in the VIP 2 room, a modern speaker system, during the session you are in a private environment and no one bothers you, the opportunity to bring food and drinks with you, popcorn tea and a wide selection of soft drinks for a fee",
                    BigDecimal.valueOf(8.76),
                    14,
                    LocalDateTime.parse("2023-04-12T18:29:26.769"), LocalDateTime.parse("2023-04-12T18:29:26.769"),
                    new HashSet<>(), Set.of(ORDERS.get(8))));

            add(new GiftCertificate(9L,
                    "Group and individual quad bike rides",
                    "The variation of our routes is so flexible and unique that everyone can travel along them. They include unique woodlands and trails, deep forests, swamps, turns and of course slides, both small and large. If you are not sure of yourself or are scared, you can always go on a trip as a passenger.",
                    BigDecimal.valueOf(2.84),
                    21,
                    LocalDateTime.parse("2023-03-20T11:42:40.345"), LocalDateTime.parse("2023-03-20T11:42:40.345"),
                    new HashSet<>(), Set.of(ORDERS.get(9))));

            add(new GiftCertificate(10L,
                    "Shooting from an air pistol/submachine gun, rifle, crossbow in a pneumatic shooting range \"Pushka\"",
                    "In the dash \"Pushka\" you will be able to shoot not only at classic paper targets and cans, but also try various exercises on an interactive steel screen that senses all your bullets with the help of sensors. This is a professional shooting simulator on which special services and hunters train. There are more than 100 targets in it: from static to realistic in the video game format. If you want to please your loved ones with a cool gift, purchase our gift certificates",
                    BigDecimal.valueOf(1.29),
                    150,
                    LocalDateTime.parse("2023-04-18T14:11:50.450"), LocalDateTime.parse("2023-04-18T14:11:50.450"),
                    new HashSet<>(), Set.of(ORDERS.get(0))));
        }};

        TAGS = new ArrayList<>() {{
            add(new Tag(1L, "entertainment"));
            add(new Tag(2L, "massage"));
            add(new Tag(3L, "health"));
            add(new Tag(4L, "shooting"));
            add(new Tag(5L, "zoo"));
            add(new Tag(6L, "museum"));
            add(new Tag(7L, "science"));
            add(new Tag(8L, "driving"));
            add(new Tag(9L, "swimming"));
            add(new Tag(10L, "extremal"));
            add(new Tag(11L, "rest"));
            add(new Tag(12L, "cinema"));
            add(new Tag(13L, "bowling"));
            add(new Tag(14L, "flying"));
            add(new Tag(15L, "cosmetology"));
            add(new Tag(16L, "adults"));
            add(new Tag(17L, "children"));
            add(new Tag(18L, "women"));
            add(new Tag(19L, "men"));
        }};

        //FILL USERS' ORDERS
        for (User user : USERS) {
            Set<Order> set = new HashSet<>();
            for (int i = 0; i < Integer.parseInt(user.getId().toString()); i++) {
                set.add(ORDERS.get(i));
            }
            user.setOrders(set);
        }

        //FILL ORDERS' CERTIFICATES
        ORDERS.get(0).setCertificate(GIFT_CERTIFICATES.get(9));
        ORDERS.get(1).setCertificate(GIFT_CERTIFICATES.get(1));
        ORDERS.get(2).setCertificate(GIFT_CERTIFICATES.get(2));
        ORDERS.get(3).setCertificate(GIFT_CERTIFICATES.get(0));
        ORDERS.get(4).setCertificate(GIFT_CERTIFICATES.get(1));
        ORDERS.get(5).setCertificate(GIFT_CERTIFICATES.get(2));
        ORDERS.get(6).setCertificate(GIFT_CERTIFICATES.get(3));
        ORDERS.get(7).setCertificate(GIFT_CERTIFICATES.get(4));
        ORDERS.get(8).setCertificate(GIFT_CERTIFICATES.get(5));
        ORDERS.get(9).setCertificate(GIFT_CERTIFICATES.get(6));
        ORDERS.get(10).setCertificate(GIFT_CERTIFICATES.get(4));
        ORDERS.get(11).setCertificate(GIFT_CERTIFICATES.get(5));
        ORDERS.get(12).setCertificate(GIFT_CERTIFICATES.get(6));
        ORDERS.get(13).setCertificate(GIFT_CERTIFICATES.get(7));
        ORDERS.get(14).setCertificate(GIFT_CERTIFICATES.get(8));

        //FILL CERTIFICATES' TAGS
        GIFT_CERTIFICATES.get(0).setTags(Set.of(TAGS.get(1), TAGS.get(2), TAGS.get(10), TAGS.get(14), TAGS.get(15), TAGS.get(17)));
        GIFT_CERTIFICATES.get(1).setTags(Set.of(TAGS.get(0), TAGS.get(15), TAGS.get(16), TAGS.get(17), TAGS.get(18), TAGS.get(13), TAGS.get(10)));
        GIFT_CERTIFICATES.get(2).setTags(Set.of(TAGS.get(14), TAGS.get(2), TAGS.get(10), TAGS.get(15), TAGS.get(17)));
        GIFT_CERTIFICATES.get(3).setTags(Set.of(TAGS.get(0), TAGS.get(10), TAGS.get(12), TAGS.get(15), TAGS.get(16), TAGS.get(17), TAGS.get(18)));
        GIFT_CERTIFICATES.get(4).setTags(Set.of(TAGS.get(0), TAGS.get(2), TAGS.get(6), TAGS.get(8), TAGS.get(10), TAGS.get(15), TAGS.get(16), TAGS.get(17), TAGS.get(18)));
        GIFT_CERTIFICATES.get(5).setTags(Set.of(TAGS.get(0), TAGS.get(7), TAGS.get(8), TAGS.get(10), TAGS.get(15), TAGS.get(16), TAGS.get(17), TAGS.get(18)));
        GIFT_CERTIFICATES.get(6).setTags(Set.of(TAGS.get(0), TAGS.get(2), TAGS.get(9), TAGS.get(10), TAGS.get(15), TAGS.get(16), TAGS.get(17), TAGS.get(18)));
        GIFT_CERTIFICATES.get(7).setTags(Set.of(TAGS.get(0), TAGS.get(10), TAGS.get(11), TAGS.get(15), TAGS.get(16), TAGS.get(17), TAGS.get(18)));
        GIFT_CERTIFICATES.get(8).setTags(Set.of(TAGS.get(0), TAGS.get(4), TAGS.get(9), TAGS.get(10), TAGS.get(15), TAGS.get(17), TAGS.get(18)));
        GIFT_CERTIFICATES.get(9).setTags(Set.of(TAGS.get(0), TAGS.get(3), TAGS.get(10), TAGS.get(15), TAGS.get(17), TAGS.get(18)));
    }

    public static List<GiftCertificate> findCertsForFindAllWithoutFiltering() {
        return GIFT_CERTIFICATES.subList(0, 3);
    }

    public static List<GiftCertificate> findCertsForFindAllWithTagNameFiltering() {
        return List.of(GIFT_CERTIFICATES.get(6), GIFT_CERTIFICATES.get(8));
    }

    public static List<GiftCertificate> findCertsForFindAllWithPartOfNameFiltering() {
        return List.of(GIFT_CERTIFICATES.get(6), GIFT_CERTIFICATES.get(8));
    }

    public static List<GiftCertificate> findCertsForFindAllWithPartOfDescriptionFiltering() {
        return List.of(GIFT_CERTIFICATES.get(1), GIFT_CERTIFICATES.get(6));
    }

    public static List<GiftCertificate> findCertsForFindAllWithCombinedFiltering() {
        return List.of(GIFT_CERTIFICATES.get(6));
    }

    public static Tag findTagForTagMapperTest() {
        return TAGS.get(0);
    }

    public static List<Tag> findTags() {
        return List.of(TAGS.get(0), TAGS.get(2), TAGS.get(7));
    }

    public static Order findOrderForOrderMapperTest() {
        return ORDERS.get(2);
    }

    public static OrderDto findOrderDto() {
        Order order = ORDERS.get(2);
        OrderDto dto = new OrderDto();
        dto.setId(3L);
        dto.setCost(order.getCost());
        dto.setCreateDate(order.getCreateDate());
        UserListDto userListDto = new UserListDto(
                order.getUser().getId(),
                order.getUser().getFirstName(),
                order.getUser().getLastName(),
                order.getUser().getAge(),
                order.getUser().getEmail());
        dto.setUser(userListDto);

        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(order.getCertificate().getId());
        certificateDto.setName(order.getCertificate().getName());
        certificateDto.setDescription(order.getCertificate().getDescription());
        certificateDto.setDuration(order.getCertificate().getDuration());
        certificateDto.setPrice(order.getCertificate().getPrice());
        certificateDto.setCreateDate(order.getCertificate().getCreateDate());
        certificateDto.setTags(order.getCertificate().getTags().stream().map(tag -> new TagDto(tag.getName())).collect(Collectors.toList()));
        dto.setCertificate(certificateDto);
        return dto;
    }

    public static List<Order> findOrders() {
        return List.of(ORDERS.get(0), ORDERS.get(1), ORDERS.get(2));
    }

    public static List<OrderDto> findOrderDtos() {
        Order order1 = ORDERS.get(0);
        OrderDto dto1 = new OrderDto();
        dto1.setId(order1.getId());
        dto1.setCost(order1.getCost());
        dto1.setCreateDate(order1.getCreateDate());
        UserListDto userListDto1 = new UserListDto(
                order1.getUser().getId(),
                order1.getUser().getFirstName(),
                order1.getUser().getLastName(),
                order1.getUser().getAge(),
                order1.getUser().getEmail());
        dto1.setUser(userListDto1);

        CertificateDto certificateDto1 = new CertificateDto();
        certificateDto1.setId(order1.getCertificate().getId());
        certificateDto1.setName(order1.getCertificate().getName());
        certificateDto1.setDescription(order1.getCertificate().getDescription());
        certificateDto1.setDuration(order1.getCertificate().getDuration());
        certificateDto1.setPrice(order1.getCertificate().getPrice());
        certificateDto1.setCreateDate(order1.getCertificate().getCreateDate());
        certificateDto1.setTags(order1.getCertificate().getTags().stream()
                .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList()));
        dto1.setCertificate(certificateDto1);

        Order order2 = ORDERS.get(1);
        OrderDto dto2 = new OrderDto();
        dto2.setId(order2.getId());
        dto2.setCost(order2.getCost());
        dto2.setCreateDate(order2.getCreateDate());
        UserListDto userListDto2 = new UserListDto(
                order2.getUser().getId(),
                order2.getUser().getFirstName(),
                order2.getUser().getLastName(),
                order2.getUser().getAge(),
                order2.getUser().getEmail());
        dto2.setUser(userListDto2);

        CertificateDto certificateDto2 = new CertificateDto();
        certificateDto2.setId(order2.getCertificate().getId());
        certificateDto2.setName(order2.getCertificate().getName());
        certificateDto2.setDescription(order2.getCertificate().getDescription());
        certificateDto2.setDuration(order2.getCertificate().getDuration());
        certificateDto2.setPrice(order2.getCertificate().getPrice());
        certificateDto2.setCreateDate(order2.getCertificate().getCreateDate());
        certificateDto2.setTags(order2.getCertificate().getTags().stream()
                .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList()));
        dto2.setCertificate(certificateDto2);

        Order order3 = ORDERS.get(2);
        OrderDto dto3 = new OrderDto();
        dto3.setId(order3.getId());
        dto3.setCost(order3.getCost());
        dto3.setCreateDate(order3.getCreateDate());
        UserListDto userListDto = new UserListDto(
                order3.getUser().getId(),
                order3.getUser().getFirstName(),
                order3.getUser().getLastName(),
                order3.getUser().getAge(),
                order3.getUser().getEmail());
        dto3.setUser(userListDto);

        CertificateDto certificateDto3 = new CertificateDto();
        certificateDto3.setId(order3.getCertificate().getId());
        certificateDto3.setName(order3.getCertificate().getName());
        certificateDto3.setDescription(order3.getCertificate().getDescription());
        certificateDto3.setDuration(order3.getCertificate().getDuration());
        certificateDto3.setPrice(order3.getCertificate().getPrice());
        certificateDto3.setCreateDate(order3.getCertificate().getCreateDate());
        certificateDto3.setTags(order3.getCertificate().getTags().stream()
                .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList()));
        dto3.setCertificate(certificateDto3);
        return List.of(dto1, dto2, dto3);
    }

    public static OrderListDto findOrderListDto() {
        Order order = ORDERS.get(2);
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(order.getCertificate().getId());
        certificateDto.setName(order.getCertificate().getName());
        certificateDto.setDescription(order.getCertificate().getDescription());
        certificateDto.setDuration(order.getCertificate().getDuration());
        certificateDto.setPrice(order.getCertificate().getPrice());
        certificateDto.setCreateDate(order.getCertificate().getCreateDate());
        certificateDto.setTags(order.getCertificate().getTags().stream()
                .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList()));
        return new OrderListDto(order.getId(), order.getCreateDate(), order.getCost(), certificateDto);
    }

    public static GiftCertificate findCertificate() {
        return GIFT_CERTIFICATES.get(2);
    }

    public static CertificateDto findCertificateDto() {
        GiftCertificate certificate = GIFT_CERTIFICATES.get(2);
        List<TagDto> tags = certificate.getTags().stream()
                .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList());
        return new CertificateDto(
                certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                tags);
    }

    public static List<GiftCertificate> findCertificates() {
        return List.of(GIFT_CERTIFICATES.get(0), GIFT_CERTIFICATES.get(1));
    }

    public static List<CertificateListDto> findListOfCertificateListDto() {
        List<GiftCertificate> certs = findCertificates();
        return certs.stream().map(cert -> new CertificateListDto(
                        cert.getId(),
                        cert.getName(),
                        cert.getPrice()))
                .collect(Collectors.toList());
    }

    public static CertificateSaveDto findCertificateSaveDtoForCertificateMapperTest() {
        GiftCertificate certificate = GIFT_CERTIFICATES.get(2);
        List<TagDto> tags = certificate.getTags().stream()
                .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList());
        return new CertificateSaveDto(
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                tags);
    }

    public static GiftCertificate findCertificateForCertificateMapperFromSaveDtoTest() {
        GiftCertificate certificate = GIFT_CERTIFICATES.get(2);
        GiftCertificate certificateResult = new GiftCertificate();
        certificateResult.setName(certificate.getName());
        certificateResult.setDescription(certificate.getDescription());
        certificateResult.setPrice(certificate.getPrice());
        certificateResult.setDuration(certificate.getDuration());
        Set<Tag> tags = certificate.getTags();
        tags.forEach(tag -> tag.setId(null));
        certificateResult.setTags(tags);
        return certificateResult;
    }

    public static User findUserForUserMapperTest() {
        return USERS.get(0);
    }

    public static UserDto findUserDto() {
        User user = USERS.get(0);
        List<OrderListDto> orders = user.getOrders().stream().map(order -> {
            CertificateDto certificateDto = new CertificateDto();
            certificateDto.setId(order.getCertificate().getId());
            certificateDto.setName(order.getCertificate().getName());
            certificateDto.setDescription(order.getCertificate().getDescription());
            certificateDto.setDuration(order.getCertificate().getDuration());
            certificateDto.setPrice(order.getCertificate().getPrice());
            certificateDto.setCreateDate(order.getCertificate().getCreateDate());
            certificateDto.setTags(order.getCertificate().getTags().stream()
                    .map(tag -> new TagDto(tag.getName())).collect(Collectors.toList()));

            return new OrderListDto(
                    order.getId(),
                    order.getCreateDate(),
                    order.getCost(),
                    certificateDto);
        }).collect(Collectors.toList());
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                orders);
    }

    public static List<User> findUsers() {
        return List.of(USERS.get(0), USERS.get(1));
    }

    public static List<UserListDto> findListOfUserListDto() {
        List<User> users = findUsers();
        return users.stream().map(user -> new UserListDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getAge(),
                        user.getEmail()))
                .collect(Collectors.toList());
    }

    public static User findUserForUserMapperFromSaveDtoTest() {
        User user = USERS.get(3);
        User userResult = new User();
        userResult.setFirstName(user.getFirstName());
        userResult.setLastName(user.getLastName());
        userResult.setAge(user.getAge());
        userResult.setEmail(user.getEmail());
        userResult.setPassword(user.getPassword());
        return userResult;
    }

    public static UserSaveDto findUserSaveDtoForUserMapperFromSaveDtoTest() {
        User user = USERS.get(3);
        UserSaveDto userSaveDto = new UserSaveDto();
        userSaveDto.setFirstName(user.getFirstName());
        userSaveDto.setLastName(user.getLastName());
        userSaveDto.setAge(user.getAge());
        userSaveDto.setEmail(user.getEmail());
        userSaveDto.setPassword(user.getPassword());
        return userSaveDto;
    }

    public static User getUser(int id) {
        return USERS.get(id - 1);
    }

    public static GiftCertificate getCertificate(int id) {
        return GIFT_CERTIFICATES.get(id - 1);
    }

    public static Order getOrder(int id) {
        return ORDERS.get(id - 1);
    }

    public static Tag getTag(int id) {
        return TAGS.get(id - 1);
    }


}
