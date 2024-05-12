import java.util.*;
import java.util.stream.Collectors;

public class Main {
//    найти множество всех рас
//    посчитать общее количество золота на карте
//    найти количество объектов по координате x = 2
//    найти третьего по количеству золота на карте
//    посчитать общее количество золота по расе
//    найти второго по количеству золота на каждой клетке

    public static void main(String[] args) {

        //    найти множество всех рас
        Set<String> races = GameWorld.game.values()
                .stream()
                .flatMap(Collection::stream)
                .map(GameObject::getRace)
                .collect(Collectors.toSet());
        System.out.println(races);

        //    посчитать общее количество золота на карте
        int totalGold = GameWorld.game.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToInt(GameObject::getGold)
                .sum();
        System.out.println(totalGold);

        //    найти количество объектов по координате x = 2
        int totalObjectsAtX2Coordinate = GameWorld.game.entrySet()
                .stream()
                .filter(coordinate -> coordinate.getKey().getX() == 2)
                .map(Map.Entry::getValue)
                .mapToInt(Collection::size)
                .sum();
        System.out.println(totalObjectsAtX2Coordinate);

        //    найти третьего по количеству золота на карте
        GameObject thirdGoldAmountOnMap = GameWorld.game.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(GameObject::getGold).reversed())
                .skip(2)
                .findFirst()
                .orElseThrow();
        System.out.println(thirdGoldAmountOnMap);

        //    посчитать общее количество золота по расе
        Map<String, Integer> goldPerRace = GameWorld.game.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(GameObject::getRace, Collectors.summingInt(GameObject::getGold)));
        goldPerRace.forEach((k, v) -> System.out.println("Race: " + k + " gold: " + v));

        //    найти второго по количеству золота на каждой клетке
        Map <Coordinate, GameObject> secondRichAtCoordinate = GameWorld.game.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        coordinate -> coordinate.getValue()
                                .stream()
                                .sorted(Comparator.comparingInt(GameObject::getGold).reversed())
                                .skip(1)
                                .findFirst()
                                .orElseThrow()
                ));
        secondRichAtCoordinate.forEach((k, v) -> System.out.println("The poorest race at coordinate is " + v));

    }
}

class GameWorld {
    public static final Map<Coordinate, List<GameObject>> game =
            Map.ofEntries(
                    Map.entry(new Coordinate(1, 1), List.of(new GameObject(200, "elf"), new GameObject(205, "gnome"))),
                    Map.entry(new Coordinate(2, 1), List.of(new GameObject(400, "dwarf"), new GameObject(4200, "ork"))),
                    Map.entry(new Coordinate(3, 1), List.of(new GameObject(350, "dwarf"), new GameObject(355, "troll"))),
                    Map.entry(new Coordinate(1, 2), List.of(new GameObject(2400, "human"), new GameObject(325, "human"))),
                    Map.entry(new Coordinate(2, 2), List.of(new GameObject(5400, "human"), new GameObject(2300, "ork"))),
                    Map.entry(new Coordinate(3, 2), List.of(new GameObject(1350, "elf"), new GameObject(6050, "gnome"))),
                    Map.entry(new Coordinate(1, 3), List.of(new GameObject(7400, "gnome"), new GameObject(300, "troll"))),
                    Map.entry(new Coordinate(2, 3), List.of(new GameObject(2356, "human"), new GameObject(1600, "ork"))),
                    Map.entry(new Coordinate(3, 3), List.of(new GameObject(1350, "elf"), new GameObject(4000, "dwarf")))
            );

}

class GameObject {
    private Integer gold;
    private String race;

    public GameObject(Integer gold, String race) {
        this.gold = gold;
        this.race = race;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Integer getGold() {
        return gold;
    }

    public String getRace() {
        return race;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "gold=" + gold +
                ", race='" + race + '\'' +
                '}';
    }
}


class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}