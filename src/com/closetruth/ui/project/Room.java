package com.closetruth.ui.project;

import java.util.*;

public class Room {
    private List<Card> cards = new ArrayList<>();

    {
        //斗地主，点数，花色
        String sizes[] = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        String colors[] = {"♠", "♥", "♣", "♦"};
        for (String size : sizes) {
            for (String color : colors) {
                cards.add(new Card(size, color));
            }
        }
        cards.add(new Card("", "大王"));
        cards.add(new Card("", "小王"));

        System.out.println(cards);
    }

    public void start() {
        Collections.shuffle(cards);
        System.out.println(cards);

        Map<String, List<Card>> players = new HashMap<>();

        List<Card> player1 = new ArrayList();
        players.put("player1", player1);

        List<Card> player2 = new ArrayList();
        players.put("player2", player2);

        List<Card> player3 = new ArrayList();
        players.put("player3", player3);

        for (int i = 0; i < cards.size() - 3; i++) {
            Card card = cards.get(i);
            if (i % 3 == 0) {
                player1.add(card);
            } else if (i % 3 == 1) {
                player2.add(card);
            } else {
                player3.add(card);
            }
        }

        List<Card> lastThreeCards = cards.subList(cards.size() - 3, cards.size());

        player1.addAll(lastThreeCards);

        //  排序底牌
        sortCardsByColorAndSize(player1);
        sortCardsByColorAndSize(player2);
        sortCardsByColorAndSize(player3);


        for (Map.Entry<String, List<Card>> entry : players.entrySet()) {
            String playerName = entry.getKey();
            List<Card> playerCards = entry.getValue();
            System.out.println(playerName + ": " + playerCards);
        }
    }

    private void sortCardsByColorAndSize(List<Card> cards) {
        String[] colorOrder = {"♦", "♣", "♥", "♠", "小王", "大王"};
        String[] sizeOrder = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2", ""};

        cards.sort((card1, card2) -> {
            // 先比较花色

            int sizeComparison =Integer.compare(
                    Arrays.asList(sizeOrder).indexOf(card1.getSize()),
                    Arrays.asList(sizeOrder).indexOf(card2.getSize())
            );

            if (sizeComparison != 0) {
                return sizeComparison;
            }

            int colorComparison = Integer.compare(
                    Arrays.asList(colorOrder).indexOf(card1.getColor()),
                    Arrays.asList(colorOrder).indexOf(card2.getColor())
            );

            return colorComparison;

        });
    }


}
