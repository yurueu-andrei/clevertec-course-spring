create table if not exists certificates
(
    id               bigserial primary key not null,
    name             varchar(256)          not null,
    description      varchar(2048)         not null,
    price            numeric(5, 2)         not null,
    duration         int                   not null,
    create_date      timestamp             not null,
    last_update_date timestamp
);

create table if not exists tags
(
    id   bigserial primary key not null,
    name varchar(64)           not null unique,
    constraint unique_name unique (name)
);

create table if not exists certificates_tags_links
(
    certificate_id bigint not null,
    tag_id         bigint not null
);

ALTER TABLE certificates_tags_links
    ADD CONSTRAINT certificate_tag_fk FOREIGN KEY (certificate_id) REFERENCES certificates (id);
ALTER TABLE certificates_tags_links
    ADD CONSTRAINT tag_certificate_fk FOREIGN KEY (tag_id) REFERENCES tags (id);

INSERT INTO certificates(name, description, price, duration, create_date)
values ('LPG-body and face massage on the "LPG Cellu M6 Integral" device in the aesthetic center "Clouds"','Vacuum hardware-roller massage on the innovative 7th generation LPG Cellu M6 Integral apparatus is the most effective and safe method of figure correction and cellulite elimination. LPG is the only system for splitting fat deposits certified by the Food and Drug Administration (USA FDA). Endermology has a number of applications in the field of facial and body aesthetics (Lipomassage and Endermolift). The result of such procedures is to improve the quality of skin tissue, reduce signs of aging and fat localization.' ,5.32::numeric, 60, '2023-04-02 17:26:07.319'),
       ('Hot air balloon flights for adults and children', 'Group balloon flights are an ideal opportunity to share the bright emotions of the flight with your loved ones, friends or colleagues. Aerotour Airline is pleased to offer you the largest balloon in Belarus for 6 people, where you can make your first flight together. If your company has more people, then we can always provide you with additional balloons so that you have the opportunity to go on your unforgettable balloon trip at the same time.', 10.91::numeric, 90, '2023-04-17 13:56:25.818'),
       ('Gift certificates for nursing cosmetology and ultrasound with a discount of up to 50% at the IdealMed medical center', 'With the approach of the holidays, the most urgent question is what to give to your family and friends. After all, it is this time that brings relatives and friends together, gives an opportunity and a reason to meet. And no matter how banal the tradition is, the most basic thing is laid down in it: each of us launches a boomerang of warmth and joy to each other. And in order for the holiday to be really enchanting, bright and joyful, you really need quite a bit: take care of gifts and surprises in advance. Cleansing cosmetic procedures are the key to full-fledged skin care to create a charming and attractive appearance. Only a cosmetologist of our IdealMed medical center can determine the type of problem, skin features, and technically correctly carry out cosmetic cleansing of the skin', 12.47::numeric, 30, '2023-03-26 12:23:19.852'),
       ('Bowling at the Bowling Haus', '"Bowling Haus" is 10 lanes of Brunswick bowling equipment, four of which are equipped with automatically rising sides for children, which do not allow a ball thrown by a child to roll into the gutter. Modern interior design, soft and pleasant lighting of our club will boost your mood and allow you to spend a fun and relaxed time not only for you, but also for your friends or family. A varied cafe menu is provided for hungry players. A wide selection of dishes and drinks at the bar will allow you not only to enjoy a game of bowling, but also to arrange a small festive buffet for your family or guests. We constantly host amateur and professional bowling tournaments, corporate parties and childrens birthday parties', 3.88::numeric, 120, '2023-04-11 15:04:18.051'),
       ('Visit to the contact zoo in the shopping center "Titan"', 'The zoo is home to animals and birds that represent different latitudes and continents. All animals were fed by man and therefore willingly go to communicate with people. Adults and children can get acquainted with the Indian porcupine, sheep and chickens, and for exotic lovers - a huge green iguana. In our zoo, ordinary igrunki appeared. These monkeys are very emotional, able to express their feelings vividly with facial expressions, the movement of tufts of hair on their heads, and various voice signals. Visitors can see a magnificent peacock, flying dogs (Nile bats). By the way, we advise you to ask the zookeepers about its inhabitants – you can learn a lot of interesting things.', 7.12::numeric, 30, '2023-04-09 20:07:09.661'),
       ('Visit to the Museum of Experimental Sciences "Experimentus" in the shopping center "Palazzo"', 'Have you often been to exhibitions where the presented works could be touched with your hands without being afraid of angry glances and exclamations of curators? If you say that such expositions simply do not exist, it means that you have not been to the Expirimentus exhibition yet. It presents various inventions of scientists of the world. Among them are the prism and the cradle of Newton, the Pythagorean theorem and bowl, the wave pendulum, the Leonardo da Vinci bridge, the Edison light bulb, the plasma ball, the predecessor of the calculator - the arithmometer, the calculator itself and much more.', 3.22::numeric, 60, '2023-03-23 08:52:04.192'),
       ('Trampoline jumping, individual training in the trampoline center "Flash Park"', 'In our trampoline arena, everyone will find a suitable occupation. The trampoline center "Flash park" is an ideal place for outdoor enthusiasts of all ages. You can look in here to relax after strenuous classes at school, parties are held here and intense workouts are arranged after school or work, its nice to spend a weekend in the company of friends and family.', 5.55::numeric, 90, '2023-03-21 18:51:59.847'),
       ('Rest in the private cinema "Cinema room"', 'The Cinema Room is the first private cinema in our country. It consists of 7 separate rooms that are locked with a key and easily accommodate a different number of guests: up to 6 people, up to 10 people, up to 35 people. Each of the rooms for up to 6 people is equipped with a projector with a screen or a plasma TV (the choice is to the taste of the guest with prior reservation). A large selection of movies, the best games for PlayStation 4 pro, a virtual reality helmet (with pre-booking), a large selection of board games, karaoke in the VIP 2 room, a modern speaker system, during the session you are in a private environment and no one bothers you, the opportunity to bring food and drinks with you, popcorn tea and a wide selection of soft drinks for a fee', 8.76::numeric, 14, '2023-04-12 18:29:26.769'),
       ('Group and individual quad bike rides', 'The variation of our routes is so flexible and unique that everyone can travel along them. They include unique woodlands and trails, deep forests, swamps, turns and of course slides, both small and large. If you are not sure of yourself or are scared, you can always go on a trip as a passenger.', 2.84::numeric, 21, '2023-03-20 11:42:40.345'),
       ('Shooting from an air pistol/submachine gun, rifle, crossbow in a pneumatic shooting range "Pushka"', 'In the dash "Pushka" you will be able to shoot not only at classic paper targets and cans, but also try various exercises on an interactive steel screen that senses all your bullets with the help of sensors. This is a professional shooting simulator on which special services and hunters train. There are more than 100 targets in it: from static to realistic in the video game format. If you want to please your loved ones with a cool gift, purchase our gift certificates', 1.29::numeric, 150, '2023-04-18 14:11:50.450');

INSERT INTO tags(name)
values ('entertainment'),
       ('massage'),
       ('health'),
       ('shooting'),
       ('zoo'),
       ('museum'),
       ('science'),
       ('driving'),
       ('swimming'),
       ('extremal'),
       ('rest'),
       ('cinema'),
       ('bowling'),
       ('flying'),
       ('cosmetology'),
       ('adults'),
       ('children'),
       ('women'),
       ('men');

INSERT INTO certificates_tags_links(certificate_id, tag_id)
values (1, 2),
       (1, 3),
       (1, 11),
       (1, 15),
       (1, 16),
       (1, 18),
       (2, 1),
       (2, 16),
       (2, 17),
       (2, 18),
       (2, 19),
       (2, 14),
       (2, 11),
       (3, 15),
       (3, 3),
       (3, 11),
       (3, 16),
       (3, 18),
       (4, 1),
       (4, 11),
       (4, 13),
       (4, 16),
       (4, 17),
       (4, 18),
       (4, 19),
       (5, 1),
       (5, 3),
       (5, 7),
       (5, 9),
       (5, 11),
       (5, 16),
       (5, 17),
       (5, 18),
       (5, 19),
       (6, 1),
       (6, 8),
       (6, 9),
       (6, 11),
       (6, 16),
       (6, 17),
       (6, 18),
       (6, 19),
       (7, 1),
       (7, 3),
       (7, 10),
       (7, 11),
       (7, 16),
       (7, 17),
       (7, 18),
       (7, 19),
       (8, 1),
       (8, 11),
       (8, 12),
       (8, 16),
       (8, 17),
       (8, 18),
       (8, 19),
       (9, 1),
       (9, 5),
       (9, 10),
       (9, 11),
       (9, 16),
       (9, 18),
       (9, 19),
       (10, 1),
       (10, 4),
       (10, 11),
       (10, 16),
       (10, 18),
       (10, 19)