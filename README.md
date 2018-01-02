assertj-extensions
==================

assertj-extensions add some assertions to assertj that help in more special scenarios. These extensions are
* Custom assertions extending already supported ones in assertj
* Custom assertions adding support for types not yet supported by assertj  
* Consumers containing complex assertion cascades usable with satisfies


conventions
-----------
This package contains helpers for language or design pattern conventions

* `DefaultEnum` (Consumer<Class<?>>) asserts that an object is an enum (and covers many methods)
* `DefaultEquality` (Consumer<Object>) asserts that an object complies with the common equality convention (and covers some of the equals/hashcode code) 
* `UtilityClass` (Consumer<Class<?>>) asserts that a class is a utility class (and covers all methods related to this convention)

iterables
-----------
This package contains helpers for iterables

* `IterableAssert` is available by calling Assertions.assertThat(Iterable<T>) (Custom assertion extending original assertion)

strings
-------
This package contains helpers for strings.

* `StringAssert` is available by calling Assertions.assertThat(String) (Custom assertion extending original assertion)


Maven Dependency
----------------

```xml
<dependency>
	<groupId>net.amygdalum</groupId>
	<artifactId>assertj-extensions</artifactId>
	<version>0.0.1</version>
</dependency>
```