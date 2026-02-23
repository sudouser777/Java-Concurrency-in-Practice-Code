package chapter04;

import net.jcip.annotations.Immutable;

@Immutable
public record Point(int x, int y) {

}
