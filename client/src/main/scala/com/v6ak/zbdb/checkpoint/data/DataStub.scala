package com.v6ak.zbdb.checkpoint.data

object DataStub{
  def byId(id: Int) = participants.find(_.id == id)

  // generated by for i in $(seq 100); do echo "Participant($i, \"$(curl -s https://www.fakenamegenerator.com/gen-random-cs-cz.php | grep '<h3>' | sed -e 's/.*<h3>//' -e 's#</h3>.*##')\", State.$(echo Unknown OnWay Arrived Departed GivenUp | xargs printf "%s\n" | sort -R | head -n1)),"; done
  val participants = Seq(
    Participant(1, "Filip Novotný", State.Arrived),
    Participant(2, "Josef Javorský", State.Arrived),
    Participant(3, "Daniela Hamáčková", State.Unknown),
    Participant(4, "Martin Rak", State.OnWay),
    Participant(5, "Alenka Hronková", State.Unknown),
    Participant(6, "Radim Šťastný", State.Departed),
    Participant(7, "David Riedl", State.Arrived),
    Participant(8, "Samuel Ševčík", State.Unknown),
    Participant(9, "Miloslav Novotný", State.OnWay),
    Participant(10, "Anna Nováková", State.Departed),
    Participant(11, "Břetislav ﻿Horák", State.Arrived),
    Participant(12, "Jana Fialová", State.Departed),
    Participant(13, "Petr Dvořák", State.OnWay),
    Participant(14, "Vojtěch Walter", State.Departed),
    Participant(15, "Milan Staněk", State.Unknown),
    Participant(16, "Jiří Adámek", State.OnWay),
    Participant(17, "Alena Martínková", State.Arrived),
    Participant(18, "Jiřina Maršálková", State.GivenUpBefore),
    Participant(19, "Margita Filípková", State.GivenUpThere),
    Participant(20, "Aneta Karchňáková", State.OnWay),
    Participant(21, "Zdeňka Klikarová", State.OnWay),
    Participant(22, "Kateřina Továrková", State.Departed),
    Participant(23, "Olexandr Bílý", State.Departed),
    Participant(24, "Eva Smetanová", State.OnWay),
    Participant(25, "Petr Hrdina", State.OnWay),
    Participant(26, "Andrea Kolářová", State.Unknown),
    Participant(27, "Hana Nováková", State.OnWay),
    Participant(28, "Marie Hůlová", State.OnWay),
    Participant(29, "Ladislav Slezák", State.OnWay),
    Participant(30, "Natálie Žemličková", State.Unknown),
    Participant(31, "Monika Faltejsková", State.OnWay),
    Participant(32, "Klára Vyšínová", State.Arrived),
    Participant(33, "Marta Hanzalová", State.GivenUpThere),
    Participant(34, "Veronika Gajdošíková", State.Arrived),
    Participant(35, "Miroslava Hrabáková", State.Arrived),
    Participant(36, "Jakub Jirák", State.Unknown),
    Participant(37, "Václava Vodová", State.Departed),
    Participant(38, "Anna Zelená", State.OnWay),
    Participant(39, "Oldřich Kupčík", State.OnWay),
    Participant(40, "Jana Grygarová", State.Unknown),
    Participant(41, "Zuzana Hájková", State.Arrived),
    Participant(42, "Eliška Halíková", State.OnWay),
    Participant(43, "Daniel Urban", State.Unknown),
    Participant(44, "Jaroslava Procházková", State.Arrived),
    Participant(45, "Roman Tomis", State.Arrived),
    Participant(46, "Petr Janíček", State.Departed),
    Participant(47, "Lenka Miklová", State.OnWay),
    Participant(48, "Marie Špičáková", State.Arrived),
    Participant(49, "Marie Křivánková", State.Unknown),
    Participant(50, "Stanislava Antošová", State.OnWay),
    Participant(51, "Josef Sládek", State.Unknown),
    Participant(52, "Luboš Prokop", State.Arrived),
    Participant(53, "Vladimír Šebek", State.GivenUpBefore),
    Participant(54, "Jana Jahodová", State.Departed),
    Participant(55, "Kateřina Maková", State.OnWay),
    Participant(56, "Ladislav Mizera", State.OnWay),
    Participant(57, "Jaroslav Kovářík", State.Unknown),
    Participant(58, "David Krejčík", State.Arrived),
    Participant(59, "Alena Páchová", State.Departed),
    Participant(60, "Denisa Marešová", State.Departed),
    Participant(61, "Marie Urbanová", State.Arrived),
    Participant(62, "Linda Štěpánková", State.Arrived),
    Participant(63, "Martina Jeřábková", State.Departed),
    Participant(64, "Otmar Plachý", State.OnWay),
    Participant(65, "Jaroslav Prokeš", State.Arrived),
    Participant(66, "Mário Jiskra", State.Unknown),
    Participant(67, "Josef Balvín", State.OnWay),
    Participant(68, "Eva Buchtelová", State.GivenUpThere),
    Participant(69, "Antonín Hoang", State.OnWay),
    Participant(70, "Jarmil Veselka", State.OnWay),
    Participant(71, "Věra Kuklová", State.Unknown),
    Participant(72, "Libor Eichler", State.Unknown),
    Participant(73, "Lucie Matějovská", State.GivenUpThere),
    Participant(74, "Josef Beránek", State.Arrived),
    Participant(75, "Jarmila Skálová", State.Arrived),
    Participant(76, "Bohuslav Černý", State.OnWay),
    Participant(77, "Ludmila Šlégrová", State.OnWay),
    Participant(78, "Jaromír Černý", State.OnWay),
    Participant(79, "Martin Jansa", State.Unknown),
    Participant(80, "Bohumil Bučko", State.Arrived),
    Participant(81, "Milada Polišenská", State.Unknown),
    Participant(82, "Patrik Slíva", State.GivenUpThere),
    Participant(83, "Iveta Koutná", State.Unknown),
    Participant(84, "Mária Tarabová", State.Departed),
    Participant(85, "Jaroslav Vu", State.OnWay),
    Participant(86, "Ladislava Podaná", State.Arrived),
    Participant(87, "Martin Jirásek", State.Departed),
    Participant(88, "Josef Adamčík", State.Departed),
    Participant(89, "Jana Hanušová", State.Arrived),
    Participant(90, "Igor Seidl", State.Arrived),
    Participant(91, "Ladislav Miller", State.Departed),
    Participant(92, "Milena Peterková", State.GivenUpBefore),
    Participant(93, "Pavel Musil", State.Arrived),
    Participant(94, "Josef Gazdík", State.GivenUpBefore),
    Participant(95, "David Šimák", State.OnWay),
    Participant(96, "Valérie Váňová", State.Departed),
    Participant(97, "Libuše Šabatová", State.OnWay),
    Participant(98, "Jana Karásková", State.Departed),
    Participant(99, "Michaela Vaňurová", State.OnWay),
    Participant(100, "Marie Dufková", State.Arrived),
  )

}
