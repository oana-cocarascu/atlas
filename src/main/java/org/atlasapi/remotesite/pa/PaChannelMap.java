package org.atlasapi.remotesite.pa;

import java.util.Map;

import org.atlasapi.media.entity.Channel;

import com.google.common.collect.Maps;
import com.metabroadcast.common.base.Maybe;


public class PaChannelMap {

    Map<Integer, Channel> channelMap = Maps.newHashMap();
    
    public PaChannelMap() {
        channelMap.put(4, Channel.BBC_ONE);
        channelMap.put(1, Channel.BBC_ONE_NORTHERN_IRELAND);
        channelMap.put(2, Channel.BBC_ONE_SCOTLAND);
        channelMap.put(3, Channel.BBC_ONE_WALES);
        channelMap.put(9, Channel.BBC_ONE_EAST);
        channelMap.put(10, Channel.BBC_ONE_EAST_MIDLANDS);
//        channelMap.put(11, Channel.BBC_ONE_MIDLANDS);
//        channelMap.put(12, Channel.BBC_ONE_NORTH);
        channelMap.put(13, Channel.BBC_ONE_NORTH_EAST);
        channelMap.put(14, Channel.BBC_ONE_NORTH_WEST);
        channelMap.put(15, Channel.BBC_ONE_SOUTH);
        channelMap.put(16, Channel.BBC_ONE_SOUTH_EAST);
        channelMap.put(17, Channel.BBC_ONE_SOUTH_WEST);
        channelMap.put(18, Channel.BBC_ONE_WEST);
        channelMap.put(713, Channel.BBC_ONE);
        
        channelMap.put(52, Channel.BBC_TWO);
        channelMap.put(19, Channel.BBC_TWO_NORTHERN_IRELAND);
        channelMap.put(20, Channel.BBC_TWO_SCOTLAND);
        channelMap.put(21, Channel.BBC_TWO_WALES);
//        channelMap.put(22, Channel.BBC_TWO_EAST);
//        channelMap.put(23, Channel.BBC_TWO_EAST_MIDLANDS);
//        channelMap.put(24, Channel.BBC_TWO_MIDLANDS);
//        channelMap.put(25, Channel.BBC_TWO_NORTH);
//        channelMap.put(26, Channel.BBC_TWO_NORTH_EAST);
//        channelMap.put(27, Channel.BBC_TWO_NORTH_WEST);
//        channelMap.put(29, Channel.BBC_TWO_SOUTH);
//        channelMap.put(30, Channel.BBC_TWO_SOUTH_EAST);
//        channelMap.put(31, Channel.BBC_TWO_SOUTH_WEST);
//        channelMap.put(32, Channel.BBC_TWO_WEST);
//        channelMap.put(724, Channel.BBC_TWO_LONDON);
        
        channelMap.put(935, Channel.BBC_THREE);
        channelMap.put(742, Channel.BBC_FOUR);
        channelMap.put(1228, Channel.BBC_HD);
        channelMap.put(734, Channel.CBEEBIES);
        channelMap.put(733, Channel.CBBC);
        channelMap.put(85, Channel.BBC_NEWS);
        channelMap.put(56, Channel.BBC_PARLIMENT);
        channelMap.put(138, Channel.BBC_WORLD_NEWS);
        
        channelMap.put(49, Channel.ITV1_LONDON);
        channelMap.put(35, Channel.ITV1_GRANADA);
        channelMap.put(37, Channel.ITV1_TYNE_TEES);
        channelMap.put(38, Channel.ITV1_BORDER_SOUTH);
        channelMap.put(42, Channel.ITV1_MERIDIAN);
        channelMap.put(43, Channel.ITV1_ANGLIA);
        channelMap.put(44, Channel.ITV1_CHANNEL);
        channelMap.put(45, Channel.ITV1_WALES);
        channelMap.put(46, Channel.ITV1_WEST);
        channelMap.put(47, Channel.ITV1_CARLTON_CENTRAL);
        channelMap.put(48, Channel.ITV1_CARLTON_WESTCOUNTRY);
        channelMap.put(698, Channel.ITV1_BORDER_NORTH);
        channelMap.put(1296, Channel.ITV1_THAMES_VALLEY_NORTH);
        channelMap.put(1297, Channel.ITV1_THAMES_VALLEY_SOUTH);
        channelMap.put(1560, Channel.ITV1_HD);
        channelMap.put(47, Channel.ITV1_CARLTON_CENTRAL);
        
        channelMap.put(451, Channel.ITV2);
        channelMap.put(1602, Channel.ITV2_HD);
        channelMap.put(1065, Channel.ITV3);
        channelMap.put(1612, Channel.ITV3_HD);
        channelMap.put(1174, Channel.ITV4);
        channelMap.put(1613, Channel.ITV4_HD);
        
        channelMap.put(53, Channel.CHANNEL_FOUR);
        channelMap.put(605, Channel.E_FOUR);
        channelMap.put(1167, Channel.MORE_FOUR);
        channelMap.put(220, Channel.FILM_4);
        channelMap.put(1438, Channel.FOUR_MUSIC);
        
        channelMap.put(54, Channel.FIVE);
        channelMap.put(1289, Channel.FIVER);
        channelMap.put(1290, Channel.FIVE_USA);
           
        channelMap.put(36, Channel.YTV);
        channelMap.put(39, Channel.ULSTER);
        channelMap.put(1605, Channel.ULSTER_HD);
        channelMap.put(40, Channel.STV_CENTRAL);
        channelMap.put(1572, Channel.STV_HD);
        channelMap.put(41, Channel.STV_NORTH);
        channelMap.put(1379, Channel.Channel_4_PLUS1);
        channelMap.put(1404, Channel.Channel_4_HD);
        channelMap.put(645, Channel.S4C);
        channelMap.put(1563, Channel.S4C_CLIRLUN);
        channelMap.put(81, Channel.RTE1);
        channelMap.put(82, Channel.RTE2);
        channelMap.put(83, Channel.TG4);
        channelMap.put(84, Channel.TV3);
        channelMap.put(331, Channel.GMTV_DIGITAL);
        channelMap.put(1291, Channel.ITV2_PLUS1);
        channelMap.put(1055, Channel.E4_PLUS1);
        channelMap.put(1534, Channel.E4_HD);
        channelMap.put(1292, Channel.ITV3_PLUS1);
        channelMap.put(1168, Channel.MORE4_PLUS1);
        channelMap.put(1457, Channel.ITV4_PLUS1);
        channelMap.put(1204, Channel.CITV);
        channelMap.put(1485, Channel.FIVER_PLUS1);
        channelMap.put(1484, Channel.FIVE_USA_PLUS1);
        channelMap.put(55, Channel.THE_ADULT_CHANNEL);
        channelMap.put(59, Channel.MTV_HITS);
        channelMap.put(63, Channel.MTV_BASE);
        channelMap.put(64, Channel.MTV);
        channelMap.put(1408, Channel.MTV_PLUS1);
        channelMap.put(66, Channel.TRAVELCHANNEL);
        channelMap.put(943, Channel.TRAVELCHANNEL_PLUS1);
        channelMap.put(67, Channel.TV5);
        channelMap.put(71, Channel.ZEE_TV);
        channelMap.put(73, Channel.PHOENIX_CNE);
        channelMap.put(170, Channel.CHALLENGE);
        channelMap.put(1071, Channel.CHALLENGE_PLUS1);
        channelMap.put(89, Channel.HOME);
        channelMap.put(756, Channel.HOME_PLUS1);
        channelMap.put(90, Channel.SKY_BOX_OFFICE_DIGITAL);
        channelMap.put(92, Channel.BLOOMBERG_TV);
        channelMap.put(93, Channel.THE_BOX);
        channelMap.put(95, Channel.CNN);
        channelMap.put(102, Channel.CARTOON_NETWORK);
        channelMap.put(99, Channel.GOD_CHANNEL);
        channelMap.put(103, Channel.PLAYBOY_TV);
        channelMap.put(168, Channel.ANIMAL_PLANET);
        channelMap.put(700, Channel.ANIMAL_PLANET_PLUS1);
        channelMap.put(114, Channel.SKY_NEWS);
        channelMap.put(1564, Channel.SKY_NEWS_HD);
        channelMap.put(120, Channel.MTV_ROCKS);
        channelMap.put(122, Channel.LIVING);
        channelMap.put(987, Channel.LIVING_PLUS1);
        channelMap.put(1495, Channel.LIVING_HD);
        channelMap.put(166, Channel.BRAVO);
        channelMap.put(986, Channel.BRAVO_PLUS1);
        channelMap.put(1606, Channel.DISCOVERY_HISTORY);
        channelMap.put(1617, Channel.DISCOVERY_HISTORY_PLUS_1);
        channelMap.put(129, Channel.DISCOVERY_SCIENCE);
        channelMap.put(1543, Channel.DISCOVERY_SCIENCE_PLUS1);
        channelMap.put(130, Channel.DISCOVERY_TRAVEL_AND_LIVING);
        channelMap.put(1471, Channel.DISCOVERY_TRAVEL_AND_LIVING_PLUS1);
        channelMap.put(171, Channel.HISTORY);
        channelMap.put(702, Channel.HISTORY_PLUS1);
        channelMap.put(177, Channel.NATIONAL_GEOGRAPHIC);
        channelMap.put(710, Channel.NATIONAL_GEOGRAPHIC_PLUS1);
        channelMap.put(134, Channel.GOLD);
        channelMap.put(899, Channel.GOLD_PLUS1);
        channelMap.put(136, Channel.THE_DISNEY_CHANNEL);
        channelMap.put(707, Channel.THE_DISNEY_CHANNEL_PLUS1);
        channelMap.put(138, Channel.BBC_WORLD_NEWS);
        channelMap.put(139, Channel.TELEVISION_X);
        channelMap.put(150, Channel.VH1);
        channelMap.put(151, Channel.MTV_CLASSIC);
        channelMap.put(169, Channel.DISCOVERY);
        channelMap.put(706, Channel.DISCOVERY_PLUS1);
        channelMap.put(1561, Channel.DISCOVERY_PLUS1_POINT5);
        channelMap.put(153, Channel.DISCOVERY_TURBO);
        channelMap.put(154, Channel.ALIBI);
        channelMap.put(1218, Channel.ALIBI_PLUS1);
        channelMap.put(155, Channel.UNIVERSAL);
        channelMap.put(1569, Channel.UNIVERSAL_HD);
        channelMap.put(1527, Channel.UNIVERSAL_PLUS1);
        channelMap.put(161, Channel.SYFY);
        channelMap.put(1213, Channel.SYFY_PLUS1);
        channelMap.put(1469, Channel.SYFY_HD);
        channelMap.put(185, Channel.COMEDY_CENTRAL);
        channelMap.put(1165, Channel.COMEDY_CENTRAL_PLUS1);
        channelMap.put(186, Channel.SONY_ENTERTAINMENT_TV_ASIA);
        channelMap.put(187, Channel.TCM);
        channelMap.put(188, Channel.NICKELODEON);
        channelMap.put(703, Channel.NICKELODEON_REPLAY);
        channelMap.put(1607, Channel.NICKELODEON_REPLAY);
        channelMap.put(192, Channel.CNBC);
        channelMap.put(195, Channel.NICK_JR);
        channelMap.put(215, Channel.BOOMERANG);
        channelMap.put(1205, Channel.BOOMERANG_PLUS1);
        channelMap.put(216, Channel.QVC);
        channelMap.put(217, Channel.GBC);
        channelMap.put(218, Channel.BBC_ENTERTAINMENT);
        channelMap.put(1581, Channel.FILM4_HD);
        channelMap.put(708, Channel.FILM4_PLUS1);
        channelMap.put(222, Channel.SKY1);
        channelMap.put(227, Channel.DISCOVERY_HOME_AND_HEALTH);
        channelMap.put(1148, Channel.DISCOVERY_HOME_AND_HEALTH_PLUS1);
        channelMap.put(229, Channel.MUTV);
        channelMap.put(231, Channel.SKY_SPORTS_NEWS);
        channelMap.put(275, Channel.EUROSPORT);
        channelMap.put(1434, Channel.EUROSPORT_HD);
        channelMap.put(410, Channel.KISS);
        channelMap.put(418, Channel.PLAYHOUSE_DISNEY);
        channelMap.put(1399, Channel.PLAYHOUSE_DISNEY_PLUS);
        channelMap.put(431, Channel.SKY_SPORTS_1);
        channelMap.put(433, Channel.SKY_SPORTS_2);
        channelMap.put(435, Channel.SKY_SPORTS_3);
        channelMap.put(437, Channel.SKY_SPORTS_4);
        channelMap.put(474, Channel.BIO);
        channelMap.put(485, Channel.BID_TV);
        channelMap.put(602, Channel.SKY_ARTS_1);
        channelMap.put(614, Channel.EURONEWS);
        channelMap.put(619, Channel.B4U_MOVIES);
        channelMap.put(644, Channel.RED_HOT_AMATEUR);
        channelMap.put(663, Channel.EXTREME_SPORTS);
        channelMap.put(664, Channel.MTV_DANCE);
        channelMap.put(678, Channel.STAR_PLUS);
        channelMap.put(691, Channel.TELEG);
        channelMap.put(696, Channel.CHANNEL_9);
        channelMap.put(722, Channel.GOOD_FOOD);
        channelMap.put(988, Channel.GOOD_FOOD_PLUS1);
        channelMap.put(747, Channel.ATTHERACES);
        channelMap.put(874, Channel.NICKTOONS_TV);
        channelMap.put(1546, Channel.NICKTOONS_REPLAY);
        channelMap.put(906, Channel.CHART_SHOW_TV);
        channelMap.put(908, Channel.YESTERDAY);
        channelMap.put(1068, Channel.YESTERDAY_PLUS1);
        channelMap.put(915, Channel.MAGIC);
        channelMap.put(914, Channel.SMASH_HITS);
        channelMap.put(913, Channel.KERRANG);
        channelMap.put(1288, Channel.THE_COMMUNITY_CHANNEL);
        channelMap.put(919, Channel.SKY2);
        channelMap.put(934, Channel.E_EXLAMATION);
        channelMap.put(957, Channel.FLAUNT);
        channelMap.put(958, Channel.SCUZZ);
        channelMap.put(959, Channel.BLISS);
        channelMap.put(962, Channel.ESPN_AMERICA);
        channelMap.put(1582, Channel.ESPN_AMERICA_HD);
        channelMap.put(963, Channel.RED_HOT_40);
        channelMap.put(74, Channel.DAVE);
        channelMap.put(1079, Channel.DAVE_JA_VU);
        channelMap.put(977, Channel.PRICE_DROP_TV);
        channelMap.put(984, Channel.FX);
        channelMap.put(1164, Channel.FX_PLUS);
        channelMap.put(993, Channel.EDEN);
        channelMap.put(1003, Channel.EDEN_PLUS1);
        channelMap.put(1603, Channel.EDEN_HD);
        channelMap.put(994, Channel.BLIGHTY);
        channelMap.put(1031, Channel.THE_HORROR_CHANNEL);
        channelMap.put(1437, Channel.THE_HORROR_CHANNEL_PLUS1);
        channelMap.put(1051, Channel.RACING_UK);
        channelMap.put(1053, Channel.CHELSEA_TV);
        channelMap.put(1056, Channel.STAR_NEWS);
        channelMap.put(1061, Channel.SETANTA_IRELAND);
        channelMap.put(1070, Channel.LIVINGIT);
        channelMap.put(1470, Channel.LIVINGIT_PLUS1);
        channelMap.put(1076, Channel.EUROSPORT_2);
        channelMap.put(1084, Channel.REALLY);
        channelMap.put(1087, Channel.RED_HOT_TV);
        channelMap.put(974, Channel.COMEDY_CENTRAL_EXTRA);
        channelMap.put(1539, Channel.COMEDY_CENTRAL_EXTRA_PLUS1);
        channelMap.put(1586, Channel.COMEDY_CENTRAL_HD);
        channelMap.put(1130, Channel.M95_TV_MARBELLA);
        channelMap.put(1132, Channel.TV3_SPANISH);
        channelMap.put(1133, Channel.DISCOVERY_REAL_TIME);
        channelMap.put(1135, Channel.DISCOVERY_REAL_TIME_PLUS1);
        channelMap.put(1141, Channel.MOTORS_TV);
        channelMap.put(1150, Channel.DISCOVERY_SHED);
        channelMap.put(1163, Channel.TRUE_MOVIES);
        channelMap.put(1172, Channel.SKY3);
        channelMap.put(1587, Channel.SKY_3_PLUS1);
        channelMap.put(1206, Channel.DISNEY_CINEMAGIC);
        channelMap.put(1207, Channel.DISNEY_CINEMAGIC_PLUS1);
        channelMap.put(1481, Channel.DISNEY_CINEMAGIC_HD);
        channelMap.put(1208, Channel.ESPN_CLASSIC);
        channelMap.put(1209, Channel.THREE_E);
        channelMap.put(1224, Channel.FILMFLEX);
        channelMap.put(1225, Channel.TCM2);
        channelMap.put(1226, Channel.CHRISTMAS24);
        channelMap.put(1526, Channel.CHRISTMAS24_PLUS);
        channelMap.put(1233, Channel.DISCOVERY_HD);
        channelMap.put(1232, Channel.NATIONAL_GEOGRAPHIC_HD);
        channelMap.put(1231, Channel.SKY_BOX_OFFICE_HD1);
        channelMap.put(1230, Channel.SKY_BOX_OFFICE_HD2);
        channelMap.put(1229, Channel.SKY_SPORTS_1_HD);
        channelMap.put(1285, Channel.SKY_SPORTS_2_HD);
        channelMap.put(1305, Channel.E_EUROPE);
        channelMap.put(1307, Channel.NAT_GEO_WILD);
        channelMap.put(1308, Channel.CRIME_AND_INVESTIGATION);
        channelMap.put(1491, Channel.CRIME_AND_INVESTIGATION_PLUS1);
//        channelMap.put(1318, Channel.SKY_MOVIES_CHRISTMAS_CHANNEL);
//        channelMap.put(1536, Channel.SKY_MOVIES_CHRISTMAS_CHANNEL_HD);
        channelMap.put(1319, Channel.SKY_MOVIES_CRIME_AND_THRILLER);
        channelMap.put(1537, Channel.SKY_MOVIES_CRIME_AND_THRILLER_HD);
        channelMap.put(1320, Channel.SKY_MOVIES_PREMIERE);
        channelMap.put(1321, Channel.SKY_MOVIES_PREMIERE_PLUS1);
        channelMap.put(1417, Channel.SKY_MOVIES_PREMIERE_HD);
        channelMap.put(1322, Channel.SKY_MOVIES_COMEDY);
        channelMap.put(1449, Channel.SKY_MOVIES_COMEDY_HD);
        channelMap.put(1328, Channel.SKY_MOVIES_ACTION_AND_ADVENTURE);
        channelMap.put(1444, Channel.SKY_MOVIES_ACTION_AND_ADVENTURE_HD);
        channelMap.put(1323, Channel.SKY_MOVIES_FAMILY);
        channelMap.put(1448, Channel.SKY_MOVIES_FAMILY_HD);
        channelMap.put(1324, Channel.SKY_MOVIES_DRAMA_AND_ROMANCE);
        channelMap.put(1445, Channel.SKY_MOVIES_DRAMA_AND_ROMANCE_HD);
        channelMap.put(1329, Channel.SKY_MOVIES_SCIFI_HORROR);
        channelMap.put(1447, Channel.SKY_MOVIES_SCIFI_HORROR_HD);
        channelMap.put(1325, Channel.SKY_MOVIES_CLASSICS);
        channelMap.put(1585, Channel.SKY_MOVIES_CLASSICS_HD);
        channelMap.put(1326, Channel.SKY_MOVIES_MODERN_GREATS);
        channelMap.put(1446, Channel.SKY_MOVIES_MODERN_GREATS_HD);
        channelMap.put(1327, Channel.SKY_MOVIES_INDIE);
        channelMap.put(1514, Channel.SKY_MOVIES_INDIE_HD);
        channelMap.put(1349, Channel.AL_JAZEERA_ENGLISH);
        channelMap.put(1354, Channel.HISTORY_HD);
        channelMap.put(1355, Channel.SKY1_HD);
        channelMap.put(1356, Channel.SKY_ARTS_1_HD);
        channelMap.put(1357, Channel.CARTOONITO);
        channelMap.put(1361, Channel.BEST_DIRECT);
        channelMap.put(1362, Channel.BRAVO_2);
        channelMap.put(1370, Channel.DEUTSCHE_WELLE);
        channelMap.put(1365, Channel.GEMS_TV);
        channelMap.put(1366, Channel.GEM_COLLECTOR);
        channelMap.put(1371, Channel.Channel_S);
        channelMap.put(1375, Channel.SETANTA_SPORTS_1_IRELAND);
        channelMap.put(1383, Channel.DIVA);
        channelMap.put(1525, Channel.DIVA_PLUS1);
        channelMap.put(1387, Channel.Channel_ONE);
        channelMap.put(1529, Channel.Channel_ONE_PLUS1);
        channelMap.put(1400, Channel.CN_TOO);
        channelMap.put(1403, Channel.POP);
        channelMap.put(1402, Channel.TINY_POP);
        channelMap.put(1405, Channel.DMAX);
        channelMap.put(1538, Channel.DMAX_PLUS1);
//        channelMap.put(1614, Channel.DMAX_PLUS1);
        channelMap.put(1412, Channel.HORSE_AND_COUNTRY);
        channelMap.put(1413, Channel.Channel_7);
        channelMap.put(1414, Channel.SKY_SPORTS_3_HD);
        channelMap.put(1415, Channel.FLAVA);
        channelMap.put(1420, Channel.FX_HD);
        channelMap.put(1425, Channel.NATIONAL_GEOGRAPHIC_HD_PAN_EUROPEAN);
        channelMap.put(1431, Channel.MOVIES4MEN);
        channelMap.put(1541, Channel.MOVIES4MEN_PLUS1);
        channelMap.put(1432, Channel.TRUE_MOVIES_2);
        channelMap.put(1433, Channel.MOVIES4MEN2);
        channelMap.put(1542, Channel.MOVIES4MEN2_PLUS1);
        channelMap.put(1435, Channel.MILITARY_HISTORY);
        channelMap.put(1440, Channel.THE_STYLE_NETWORK);
        channelMap.put(1441, Channel.WATCH);
        channelMap.put(1451, Channel.WATCH_PLUS1);
        channelMap.put(1443, Channel.SKY_ARTS_2);
        channelMap.put(1455, Channel.WEDDING_TV);
        channelMap.put(1456, Channel.PROPELLER_TV);
        channelMap.put(1458, Channel.MTVN_HD);
        channelMap.put(1460, Channel.INVESTIGATION_DISCOVERY);
        channelMap.put(1463, Channel.AIT_INTERNATIONAL);
        channelMap.put(1473, Channel.CINEMOI);
        channelMap.put(1475, Channel.SKY_ARTS_2_HD);
        channelMap.put(1479, Channel.BIO_HD);
        channelMap.put(1480, Channel.CRIME_AND_INVESTIGATION_HD);
        channelMap.put(1482, Channel.NAT_GEO_WILD_HD);
        channelMap.put(1483, Channel.QUEST);
        channelMap.put(1521, Channel.DISCOVERY_QUEST_PLUS1);
        channelMap.put(1604, Channel.QUEST_FREEVIEW);
        channelMap.put(1492, Channel.ESPN);
        channelMap.put(1493, Channel.ESPN_HD);
        channelMap.put(1497, Channel.DISNEY_XD);
        channelMap.put(1498, Channel.DISNEY_XD_PLUS1);
        channelMap.put(1608, Channel.DISNEY_XD_HD);
        channelMap.put(1512, Channel.CBS_REALITY);
        channelMap.put(1513, Channel.MGM);
        channelMap.put(1518, Channel.CBS_DRAMA);
        channelMap.put(1520, Channel.CBS_ACTION);
        channelMap.put(1522, Channel.VIVA);
        channelMap.put(1523, Channel.FOOD_NETWORK);
        channelMap.put(1524, Channel.FOOD_NETWORK_PLUS1);
        channelMap.put(1528, Channel.MGM_HD);
        channelMap.put(1530, Channel.MTV_SHOWS);
        channelMap.put(1531, Channel.NICK_JR_2);
        channelMap.put(1535, Channel.NHK_WORLD);
        channelMap.put(1548, Channel.TRUEENT);
        channelMap.put(1550, Channel.BODY_IN_BALANCE);
        channelMap.put(1551, Channel.THE_ACTIVE_CHANNEL);
        channelMap.put(1552, Channel.FITNESS_TV);
        channelMap.put(1554, Channel.RUSH_HD);
        channelMap.put(1555, Channel.BBC_SPORT_INTERACTIVE_BBC_ONE);
        channelMap.put(1558, Channel.BBC_SPORT_INTERACTIVE_FREEVIEW);
        channelMap.put(1562, Channel.SKY_3D);
        channelMap.put(1567, Channel.SKY_SPORTS_4_HD);
        
        channelMap.put(8, Channel.BBC_RADIO_RADIO3);
        channelMap.put(33, Channel.BBC_RADIO_RADIO4);
        channelMap.put(34, Channel.BBC_RADIO_RADIO4_LW);
        channelMap.put(77, Channel.BBC_RADIO_RADIO1);
        channelMap.put(86, Channel.BBC_RADIO_RADIO2);
        channelMap.put(87, Channel.BBC_RADIO_5LIVE);
//        channelMap.put(228, Channel.CLASSIC_FM);
        channelMap.put(232, Channel.BBC_RADIO_RADIOSCOTLAND);
//        channelMap.put(622, Channel.BBC_RADIO_SCOTLAND_MW);
//        channelMap.put(480, Channel.RTE_RADIO_1_FM);
//        channelMap.put(1008, Channel.RTE_RADIO_1_LW);
//        channelMap.put(479, Channel.2_FM);
        channelMap.put(631, Channel.BBC_RADIO_RADIOULSTER);
        channelMap.put(632, Channel.BBC_RADIO_RADIOFOYLE);
//        channelMap.put(1214, Channel.BBC_RADIO_ULSTER_MW);
        channelMap.put(633, Channel.BBC_RADIO_RADIOWALES);
        channelMap.put(634, Channel.BBC_RADIO_RADIOCYMRU);
//        channelMap.put(637, Channel.BBC_RADIO_ORKNEY);
//        channelMap.put(1301, Channel.RADIO_ORKNEY_MW);
//        channelMap.put(638, Channel.BBC_RADIO_SHETLAND);
//        channelMap.put(1302, Channel.RADIO_SHETLAND_MW);
        channelMap.put(641, Channel.BBC_RADIO_RADIONANGAIDHEAL);
        channelMap.put(744, Channel.BBC_RADIO_6MUSIC);
        channelMap.put(745, Channel.BBC_RADIO_5LIVESPORTSEXTRA);
        channelMap.put(875, Channel.BBC_RADIO_1XTRA);
        channelMap.put(932, Channel.BBC_RADIO_RADIO7);
/*        channelMap.put(1227, Channel.PLANET_ROCK);
        channelMap.put(239, Channel.RADIO_AIRE);
        channelMap.put(240, Channel.STAR_RADIO);
        channelMap.put(241, Channel.DUBLIN_CITY_FM);
        channelMap.put(242, Channel.ARROW_FM);
        channelMap.put(244, Channel.BAY);
        channelMap.put(245, Channel.RADIO_KERRY);
        channelMap.put(246, Channel.BEACH_103.4FM);
        channelMap.put(503, Channel.BEACON);
        channelMap.put(248, Channel.TOUCH_RADIO_102);
        channelMap.put(249, Channel.HOSPITAL_RADIO_BEDSIDE);
        channelMap.put(250, Channel.SIGNAL_TWO);
        channelMap.put(251, Channel.BORDERS_RADIO);
        channelMap.put(254, Channel.CALDERDALE_HOSPITAL_RADIO);
        channelMap.put(255, Channel.STAR_107_(CAMBRIDGE));
        channelMap.put(257, Channel.RADIO_CAVELL);
        channelMap.put(258, Channel.CENTRAL_FM);
        channelMap.put(259, Channel.TOUCH_RADIO_101.6/102.4);
        channelMap.put(260, Channel.REAL_RADIO_(NORTH_EAST));
        channelMap.put(261, Channel.CEREDIGION);
        channelMap.put(262, Channel.CFM);
        channelMap.put(263, Channel.HEART_ANGLESEY_AND_GWYNEDD);
        channelMap.put(264, Channel.Channel_103);
        channelMap.put(266, Channel.KFM);
        channelMap.put(273, Channel.98FM);
        channelMap.put(278, Channel.CLARE_FM);
        channelMap.put(279, Channel.CLYDE_1);
        channelMap.put(280, Channel.CLYDE_2);
        channelMap.put(281, Channel.CONNECT_FM);
        channelMap.put(282, Channel.CORK&APOS;S_96FM);
        channelMap.put(283, Channel.98.3FM_CORK_CAMPUS_RADIO);
        channelMap.put(284, Channel.CUH_FM_107.8);
        channelMap.put(285, Channel.COUNTY_SOUND_RADIO);
        channelMap.put(286, Channel.C103_(WEST_CORK));
        channelMap.put(287, Channel.C103_(NORTH_AND_EAST_CORK));
        channelMap.put(289, Channel.DEVON_MW);
        channelMap.put(290, Channel.DURHAM_HOSPITAL_RADIO);
        channelMap.put(291, Channel.EAGLE_96.4);
        channelMap.put(292, Channel.EAST_COAST_RADIO);
        channelMap.put(293, Channel.RED_DOT_RADIO);
        channelMap.put(295, Channel.FM_104);
        channelMap.put(297, Channel.FRESH_AM);
        channelMap.put(300, Channel.GLAMORGAN_HOSPITAL_RADIO);
        channelMap.put(301, Channel.GRAMPIAN_HOSPITAL_RADIO);
        channelMap.put(303, Channel.GRIMSBY_HOSPITAL_RADIO);
        channelMap.put(1477, Channel.HEART_WILTSHIRE);
        channelMap.put(306, Channel.BLACKBURN_HOSPITAL_RADIO);
        channelMap.put(307, Channel.HAVEN_HOSPITAL_RADIO);
        channelMap.put(308, Channel.ISLES_FM);
        channelMap.put(309, Channel.ISLE_OF_WIGHT_RADIO);
        channelMap.put(310, Channel.KETTERING_HOSPITAL_RADIO);
        channelMap.put(311, Channel.KLFM);
        channelMap.put(312, Channel.LEICESTER_SOUND_RADIO);
        channelMap.put(313, Channel.LM_FM_RADIO);
        channelMap.put(314, Channel.LOCHBROOM_FM);
        channelMap.put(315, Channel.MAGIC_AM);
        channelMap.put(316, Channel.MAGIC_999);
        channelMap.put(317, Channel.MAYFIELD_HOSPITAL_RADIO);
        channelMap.put(318, Channel.MERCIA_FM);
        channelMap.put(319, Channel.HEART_SUSSEX);
        channelMap.put(320, Channel.METRO);
        channelMap.put(321, Channel.MINSTER_FM);
        channelMap.put(322, Channel.MORAY_FIRTH_RADIO_FM);
        channelMap.put(621, Channel.MORAY_FIRTH_RADIO_MW);
        channelMap.put(323, Channel.NENE_VALLEY);
        channelMap.put(324, Channel.HEART_HOME_COUNTIES);
        channelMap.put(325, Channel.NORTHSOUND_ONE);
        channelMap.put(326, Channel.NORTHSOUND_TWO);
        channelMap.put(329, Channel.GLIDE_FM);
        channelMap.put(330, Channel.PIRATE_FM);
        channelMap.put(333, Channel.NECR_102.1FM);
        channelMap.put(646, Channel.PLYMOUTH_HOSPITAL_RADIO);
        channelMap.put(336, Channel.GALAXY_SOUTH_COAST);
        channelMap.put(337, Channel.PREMIER_CHRISTIAN_RADIO);
        channelMap.put(338, Channel.RADIO_FIREBIRD);
        channelMap.put(339, Channel.Q102.9);
        channelMap.put(341, Channel.96.3_ROCK_RADIO);
        channelMap.put(342, Channel.NEVIS_RADIO);
        channelMap.put(346, Channel.RADIO_MALDWYN);
        channelMap.put(348, Channel.RADIO_WAVE);
        channelMap.put(349, Channel.RADIO_XL);
        channelMap.put(1575, Channel.TOTAL_STAR_(WEST_SOMERSET));
        channelMap.put(350, Channel.DERBY&APOS;S_RAM_FM);
        channelMap.put(351, Channel.READING_HOSPITAL_RADIO);
        channelMap.put(352, Channel.ROCK_FM);
        channelMap.put(353, Channel.REVOLUTION);
        channelMap.put(355, Channel.ROOKWOOD_SOUND_HOSPITAL_RADIO);
        channelMap.put(356, Channel.REAL_RADIO_(SCOTLAND));
        channelMap.put(357, Channel.HEART_GLOUCESTERSHIRE);
        channelMap.put(358, Channel.SOUTHAMPTON_HOSPITAL_RADIO);
        channelMap.put(359, Channel.SOUTH_EAST_RADIO);*/
        channelMap.put(360, Channel.BBC_RADIO_SURREY);
        channelMap.put(653, Channel.BBC_RADIO_SUSSEX);
/*        channelMap.put(361, Channel.HEART_SUSSEX);
        channelMap.put(362, Channel.SOUTHSIDE_HOSPITAL_RADIO);
        channelMap.put(363, Channel.SOUTH_TYNESIDE_HOSPITAL_RADIO);
        channelMap.put(364, Channel.SOVEREIGN);
        channelMap.put(365, Channel.SPECTRUM_558_AM_RADIO);
        channelMap.put(366, Channel.SPIRE_FM_RADIO);
        channelMap.put(367, Channel.RADIO_SUNDERLAND_FOR_HOSPITALS);
        channelMap.put(368, Channel.SUNRISE_RADIO);
        channelMap.put(369, Channel.SUNRISE_RADIO_103.2FM);
        channelMap.put(370, Channel.SUNSHINE_855);
        channelMap.put(371, Channel.JUICE_107.2);
        channelMap.put(372, Channel.107.4_THE_SEVERN);
        channelMap.put(373, Channel.TFM_RADIO);
        channelMap.put(1191, Channel.BBC_THREE_COUNTIES_RADIO);
        channelMap.put(375, Channel.TIPPERARY_MID_WEST_FM_RADIO);
        channelMap.put(376, Channel.TIPP_FM);
        channelMap.put(377, Channel.TOWER_107.4FM);
        channelMap.put(378, Channel.TRENT_FM);
        channelMap.put(379, Channel.TYNESIDE_HOSPITAL_RADIO);
        channelMap.put(380, Channel.KISS_105-108);
        channelMap.put(381, Channel.VIKING_FM);
        channelMap.put(383, Channel.WATERFORD);
        channelMap.put(384, Channel.WAVE_105.2FM);
        channelMap.put(385, Channel.WAVES_101.2);
        channelMap.put(387, Channel.WESSEX_FM);
        channelMap.put(386, Channel.WEST_FM);
        channelMap.put(388, Channel.WEST_SOUND_FM);
        channelMap.put(389, Channel.WESTSOUND_AYRSHIRE_1035AM);
        channelMap.put(390, Channel.WISH_FM);
        channelMap.put(391, Channel.CHOICE);
        channelMap.put(392, Channel.WYVERN_FM);
        channelMap.put(394, Channel.XFM);
        channelMap.put(395, Channel.YORK_HOSPITAL_RADIO);
        channelMap.put(396, Channel.YORKSHIRE_COAST_96.2FM);
        channelMap.put(399, Channel.TRAX_FM);
        channelMap.put(401, Channel.BRMB_96.4);
        channelMap.put(404, Channel.CITY_BEAT_96.7_FM);
        channelMap.put(405, Channel.KISS_101);
        channelMap.put(406, Channel.GALAXY_NORTH_EAST);
        channelMap.put(407, Channel.HALLAM_FM);
        channelMap.put(408, Channel.HEART_LONDON);
        channelMap.put(409, Channel.HEART_WEST_MIDLANDS);
        channelMap.put(1594, Channel.HEART_ESSEX);
        channelMap.put(1596, Channel.HEART_SOUTH_COAST);
        channelMap.put(1592, Channel.RTE_PULSE);
        channelMap.put(1588, Channel.RTE_RADIO_1_EXTRA);
        channelMap.put(1590, Channel.RTE_2XM);
        channelMap.put(1598, Channel.HEART_NORTH_WEST_AND_WALES);
        channelMap.put(1599, Channel.HEART_DEVON);
        channelMap.put(1593, Channel.HEART_CAMBRIDGESHIRE);
        channelMap.put(1595, Channel.HEART_THAMES_VALLEY);
        channelMap.put(1597, Channel.HEART_WEST_COUNTRY);
        channelMap.put(1600, Channel.HEART_EAST_ANGLIA);
        channelMap.put(1591, Channel.RTE_CHOICE);
        channelMap.put(1589, Channel.RTE_JUNIOR);
        channelMap.put(502, Channel.BBC_ASIAN_NETWORK);
        channelMap.put(424, Channel.TAY_FM);
        channelMap.put(425, Channel.TAY_AM);
        channelMap.put(426, Channel.VICTORIA_HOSPITAL_RADIO);
        channelMap.put(427, Channel.SWANSEA);
        channelMap.put(428, Channel.TODAY_FM);
        channelMap.put(429, Channel.SUN_FM_103.4);
        channelMap.put(430, Channel.STRAY);
        channelMap.put(439, Channel.WOLF);
        channelMap.put(440, Channel.PULSE_2);
        channelMap.put(441, Channel.WAVE_96.4FM);
        channelMap.put(445, Channel.SIGNAL_ONE_FOR_STAFFORD_96.9FM);
        channelMap.put(444, Channel.SIGNAL_ONE_102.6);
        channelMap.put(455, Channel.RED_DRAGON_RADIO);
        channelMap.put(454, Channel.PULSE);
        channelMap.put(456, Channel.KEY_103);
        channelMap.put(457, Channel.PEAK_107FM);
        channelMap.put(460, Channel.MAGIC_1152_(MANCHESTER));
        channelMap.put(461, Channel.RTE_LYRIC_FM);
        channelMap.put(462, Channel.BBC_LONDON);
        channelMap.put(463, Channel.LINCS_FM);
        channelMap.put(464, Channel.Q102);
        channelMap.put(465, Channel.ISLAND_FM);
        channelMap.put(466, Channel.IMAGINE_FM);
        channelMap.put(469, Channel.FORTH_ONE);
        channelMap.put(470, Channel.FORTH_2);
        channelMap.put(471, Channel.DUNE_FM);
        channelMap.put(472, Channel.DOWNTOWN_RADIO);
        channelMap.put(473, Channel.TALKSPORT);
        channelMap.put(475, Channel.COOL_FM);
        channelMap.put(476, Channel.RADIO_CITY_96.7);
        channelMap.put(477, Channel.REAL_RADIO_(NORTH_WEST));
        channelMap.put(482, Channel.CAPITAL_RADIO);
        channelMap.put(478, Channel.RTE_RAIDIÓ_NA_GAELTACHTA);
        channelMap.put(489, Channel.GALAXY_SCOTLAND);
        channelMap.put(491, Channel.COVENTRY_AND_WARWICKSHIRE_FM);*/
        channelMap.put(493, Channel.BBC_RADIO_MANCHESTER);
        channelMap.put(499, Channel.BBC_RADIO_HEREFORDANDWORCESTER);
        channelMap.put(497, Channel.BBC_RADIO_BERKSHIRE);
        channelMap.put(495, Channel.BBC_RADIO_BRISTOL);
        channelMap.put(731, Channel.BBC_RADIO_CAMBRIDGESHIRE);
        channelMap.put(510, Channel.BBC_RADIO_TEES);
        channelMap.put(512, Channel.BBC_RADIO_CORNWALL);
        channelMap.put(514, Channel.BBC_RADIO_CUMBRIA);
        channelMap.put(516, Channel.BBC_RADIO_DERBY);
        channelMap.put(1309, Channel.BBC_RADIO_DEVON);
        channelMap.put(1345, Channel.BBC_RADIO_ESSEX);
        channelMap.put(524, Channel.BBC_RADIO_GLOUCESTERSHIRE);
        channelMap.put(526, Channel.BBC_RADIO_GUERNSEY);
        channelMap.put(528, Channel.BBC_RADIO_HUMBERSIDE);
        channelMap.put(530, Channel.BBC_RADIO_JERSEY);
        channelMap.put(532, Channel.BBC_RADIO_KENT);
        channelMap.put(534, Channel.BBC_RADIO_LANCASHIRE);
        channelMap.put(536, Channel.BBC_RADIO_LEEDS);
        channelMap.put(538, Channel.BBC_RADIO_LEICESTER);
        channelMap.put(540, Channel.BBC_RADIO_LINCOLNSHIRE);
        channelMap.put(542, Channel.BBC_RADIO_MERSEYSIDE);
        channelMap.put(544, Channel.BBC_RADIO_NEWCASTLE);
        channelMap.put(546, Channel.BBC_RADIO_NORFOLK);
        channelMap.put(548, Channel.BBC_RADIO_NORTHAMPTON);
        channelMap.put(550, Channel.BBC_RADIO_NOTTINGHAM);
        channelMap.put(552, Channel.BBC_RADIO_OXFORD);
        channelMap.put(554, Channel.BBC_RADIO_SHEFFIELD);
        channelMap.put(556, Channel.BBC_RADIO_SHROPSHIRE);
        channelMap.put(1027, Channel.BBC_RADIO_SOLENT);
        channelMap.put(560, Channel.BBC_RADIO_SOMERSET);
        channelMap.put(562, Channel.BBC_RADIO_STOKE);
        channelMap.put(564, Channel.BBC_RADIO_SUFFOLK);
//        channelMap.put(567, Channel.BBC_RADIO_SWINDON);
        channelMap.put(569, Channel.BBC_RADIO_WILTSHIRE);
//        channelMap.put(570, Channel.BBC_RADIO_RADIO�WM);
        channelMap.put(568, Channel.BBC_RADIO_YORK);
/*        channelMap.put(575, Channel.ABSOLUTE_RADIO);
        channelMap.put(576, Channel.GALAXY_102);
        channelMap.put(577, Channel.GALAXY_105);
        channelMap.put(578, Channel.GALAXY_102.2);
        channelMap.put(580, Channel.102.2_SMOOTH_RADIO);
        channelMap.put(582, Channel.KISS_100);
        channelMap.put(583, Channel.TOUCH_RADIO_96.2);
        channelMap.put(584, Channel.LBC_97.3);
        channelMap.put(585, Channel.LIVE_95);
        channelMap.put(587, Channel.KINGDOM_FM);
        channelMap.put(588, Channel.BBC_WORLD_SERVICE);
        channelMap.put(589, Channel.MAGIC_105.4);
        channelMap.put(590, Channel.MAGIC);
        channelMap.put(591, Channel.MAGIC_1170);
        channelMap.put(592, Channel.MAGIC_1548);
        channelMap.put(593, Channel.MAGIC_828);
        channelMap.put(596, Channel.LBC_NEWS_1152);
        channelMap.put(597, Channel.MAGIC_1152_AM);
        channelMap.put(600, Channel.WAVE_102FM);
        channelMap.put(601, Channel.REAL_RADIO_(WALES));
        channelMap.put(603, Channel.YORKSHIRE_COAST_(BRID)_102.4FM);
        channelMap.put(607, Channel.107.6_JUICE_FM);
        channelMap.put(673, Channel.COMPASS_FM);
        channelMap.put(717, Channel.GALWAY_BAY_FM_95.8);
        channelMap.put(715, Channel.WINCHESTER_HOSPITAL_RADIO);
        channelMap.put(716, Channel.FIRE);
        channelMap.put(718, Channel.MANSFIELD_103.2);
        channelMap.put(720, Channel.HEART_EAST_MIDLANDS);
        channelMap.put(721, Channel.RADIO_LINK);
        channelMap.put(727, Channel.105.7_SMOOTH_RADIO);
        channelMap.put(902, Channel.BRIGHT_106.4);
        channelMap.put(921, Channel.RUGBY_FM);
        channelMap.put(923, Channel.STAR_107.7);
        channelMap.put(926, Channel.HEARTLAND);
        channelMap.put(927, Channel.KINGSTOWN);
        channelMap.put(928, Channel.BRISTOL_HOSPITAL_RADIO);
        channelMap.put(930, Channel.STAR_107.2);
        channelMap.put(931, Channel.TOTAL_STAR_107.5);
        channelMap.put(940, Channel.REAL_RADIO_(YORKSHIRE));
        channelMap.put(942, Channel.RADIO_PEMBROKESHIRE);
        channelMap.put(944, Channel.106.6_SMOOTH_RADIO);
        channelMap.put(945, Channel.SPIN_1038);
        channelMap.put(946, Channel.NOTTINGHAM_HOSPITAL_RADIO);
        channelMap.put(947, Channel.COTSWOLD_HOSPITAL_RADIO);
        channelMap.put(950, Channel.SABRAS);
        channelMap.put(951, Channel.HOSPITAL_RADIO_FOX);
        channelMap.put(952, Channel.RADIO_GWENDOLEN);
        channelMap.put(961, Channel.SPLASH_FM);
        channelMap.put(964, Channel.DREAM_100);
        channelMap.put(965, Channel.Q);
        channelMap.put(966, Channel.THE_HITS);
        channelMap.put(967, Channel.MAGIC_DIGITAL);
        channelMap.put(968, Channel.SPIRIT_FM_96.6_AND_102.3);
        channelMap.put(969, Channel.RIVIERA_RADIO);
        channelMap.put(970, Channel.BUZZ_ASIA);
        channelMap.put(973, Channel.HEAT);
        channelMap.put(975, Channel.SPECTRUM_FM);
        channelMap.put(978, Channel.NORTH_NORFOLK_RADIO);
        channelMap.put(985, Channel.BCB_106.6FM);
        channelMap.put(990, Channel.100.4_SMOOTH_RADIO);
        channelMap.put(1020, Channel.KMFM_106);
        channelMap.put(1021, Channel.KMFM_96.2/101.6);
        channelMap.put(1022, Channel.KMFM_107.9/100.4);
        channelMap.put(1023, Channel.KMFM_107.2);
        channelMap.put(1024, Channel.KMFM_96.4/106.8);
        channelMap.put(1025, Channel.HEART_KENT);
        channelMap.put(1029, Channel.RED_FM);
        channelMap.put(1034, Channel.NEWSTALK_106-108_FM);
        channelMap.put(1052, Channel.KMFM_105.6);
        channelMap.put(1064, Channel.105.2_SMOOTH_RADIO);
        channelMap.put(1066, Channel.BLAST_FM);
        channelMap.put(1080, Channel.RADIO_ALTEA);
        channelMap.put(1081, Channel.RADIO_SALOBRENA);
        channelMap.put(1082, Channel.COASTLINE_RADIO);
        channelMap.put(1083, Channel.MIDWEST_RADIO);
        channelMap.put(1142, Channel.ONDA_CERO_MARBELLA);
        channelMap.put(1170, Channel.THE_BEE);
        channelMap.put(1171, Channel.2BR);
        channelMap.put(1173, Channel.KMFM_107.6);
        channelMap.put(1179, Channel.WAVE_96.5FM);
        channelMap.put(1181, Channel.U105.8);
        channelMap.put(1182, Channel.WYRE_107.2);
        channelMap.put(1183, Channel.SMASH_HITS!);
        channelMap.put(1184, Channel.KERRANG!);
        channelMap.put(1190, Channel.CONNECT_FM_106.8);
        channelMap.put(1197, Channel.TORBAY_HOSPITAL_RADIO);
        channelMap.put(1203, Channel.EXETER_HOSPITAL_RADIO);
        channelMap.put(1223, Channel.103_THE_EYE);
        channelMap.put(1284, Channel.XFM_MANCHESTER);
        channelMap.put(1286, Channel.RADIO_NORWICH);
        channelMap.put(1287, Channel.FOREST_FM);
        channelMap.put(1293, Channel.102_TOWN_FM);
        channelMap.put(1294, Channel.THE_SEVERN);
        channelMap.put(1330, Channel.ABSOLUTE_XTREME);
        channelMap.put(1331, Channel.ABSOLUTE_CLASSIC_ROCK);
        channelMap.put(1333, Channel.ARROW);
        channelMap.put(1337, Channel.CHILL);
        channelMap.put(1338, Channel.FUN_KIDS);
        channelMap.put(1344, Channel.CHOICE_FM);
        channelMap.put(1341, Channel.UTD_CHRISTIAN_BROADCASTING);
        channelMap.put(1342, Channel.WORLD_RADIO_NETWORK);
        channelMap.put(1343, Channel.DEUTSCHE_WELLE);
        channelMap.put(1372, Channel.HOPE_FM);
        channelMap.put(1377, Channel.GOLD);
        channelMap.put(1380, Channel.KCFM);
        channelMap.put(1381, Channel.IPSWICH_COMMUNITY_RADIO);
        channelMap.put(1382, Channel.FUTURE_RADIO);
        channelMap.put(1392, Channel.WCR_FM);
        channelMap.put(1395, Channel.TEMPO_FM);
        channelMap.put(1396, Channel.LEITH);
        channelMap.put(1397, Channel.BLACK_DIAMOND);
        channelMap.put(1406, Channel.97.5_SMOOTH_RADIO);
        channelMap.put(1409, Channel.CITY_TALK_105.9);
        channelMap.put(1421, Channel.CHELMSFORD_RADIO);
        channelMap.put(1422, Channel.106.1_ROCK_RADIO);
        channelMap.put(1423, Channel.LIFE_FM_93.1);
        channelMap.put(1436, Channel.97.5_KEMET_FM);
        channelMap.put(1453, Channel.JAZZ_FM);
        channelMap.put(1454, Channel.COAST_106);
        channelMap.put(1467, Channel.RADIO_GRAPEVINE);
        channelMap.put(1487, Channel.NEWPORT_CITY_RADIO);
        channelMap.put(1488, Channel.JACK_FM);
        channelMap.put(1489, Channel.4FM);
        channelMap.put(1490, Channel.AMAZING_RADIO);
        channelMap.put(1494, Channel.BAYRADIO_(SPANISH));*/
    }
    
    public String getChannelUri(int channelId) {
        if (channelMap.containsKey(channelId)) {
            return channelMap.get(channelId).uri();
        }
        return null;
    }
    
    public Maybe<Channel> getChannel(int channelId) {
        if (channelMap.containsKey(channelId)) {
            return Maybe.fromPossibleNullValue(channelMap.get(channelId));
        }
        return Maybe.nothing();
    }
}
