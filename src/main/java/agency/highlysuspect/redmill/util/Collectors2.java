package agency.highlysuspect.redmill.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Collectors2 {
	public static <T extends JsonElement> Collector<T, ?, JsonArray> toJsonArray() {
		return toJsonArray(x -> x);
	}
	
	public static <T> Collector<T, ?, JsonArray> toJsonArray(Function<T, JsonElement> toJsonElement) {
		return new Collector<T, JsonArray, JsonArray>() {
			@Override
			public Supplier<JsonArray> supplier() {
				return JsonArray::new;
			}
			
			@Override
			public BiConsumer<JsonArray, T> accumulator() {
				return (array, item) -> array.add(toJsonElement.apply(item));
			}
			
			@Override
			public BinaryOperator<JsonArray> combiner() {
				return (a, b) -> {
					a.addAll(b);
					return a;
				};
			}
			
			@Override
			public Function<JsonArray, JsonArray> finisher() {
				return Function.identity();
			}
			
			@Override
			public Set<Characteristics> characteristics() {
				return Set.of(Characteristics.IDENTITY_FINISH);
			}
		};
	}
}
