package chapter03;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class StackConfinement {

    Comparator<Animal> speciesGenderComparator = Comparator.comparing(Animal::getSpecies)
            .thenComparing(Animal::getGender);


    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // animals confined to method, don't let them escape!
        animals = new TreeSet<>(speciesGenderComparator);
        animals.addAll(candidates);
        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate()) {
                candidate = a;
            } else {
                load(new AnimalPair(candidate, a));
                ++numPairs;
                candidate = null;
            }
        }

        return numPairs;
    }

    private void load(AnimalPair pair) {
    }
}
