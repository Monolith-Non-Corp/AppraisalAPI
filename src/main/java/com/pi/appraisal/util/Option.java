package com.pi.appraisal.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Option<T> {
	private static final Option<?> EMPTY = new Option<>();
	private final T value;

	private Option() {
		this.value = null;
	}

	public static <T> Option<T> empty() {
		@SuppressWarnings("unchecked")
		Option<T> t = (Option<T>) EMPTY;
		return t;
	}

	private Option(T value) {
		this.value = Objects.requireNonNull(value);
	}

	public static <T> Option<T> of(T value) {
		return new Option<>(value);
	}

	public static <T> Option<T> ofNullable(T value) {
		return value == null ? empty() : of(value);
	}

	public T get() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public Option<T> filter(Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		if (!isPresent()) {
			return this;
		} else {
			return predicate.test(value) ? this : empty();
		}
	}

	public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent()) {
			return empty();
		} else {
			return Option.ofNullable(mapper.apply(value));
		}
	}

	public <U> Option<U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent()) {
			return empty();
		} else {
			@SuppressWarnings("unchecked")
			Option<U> r = (Option<U>) mapper.apply(value);
			return Objects.requireNonNull(r);
		}
	}

	public <U> Option<U> pipe(Supplier<U> supplier) {
		Objects.requireNonNull(supplier);
		if (!isPresent()) {
			return empty();
		} else {
			return Option.ofNullable(supplier.get());
		}
	}

	public Option<T> or(Supplier<? extends Option<? extends T>> supplier) {
		Objects.requireNonNull(supplier);
		if (isPresent()) {
			return this;
		} else {
			@SuppressWarnings("unchecked")
			Option<T> r = (Option<T>) supplier.get();
			return Objects.requireNonNull(r);
		}
	}

	public T orElse(T other) {
		return value != null ? value : other;
	}
}
