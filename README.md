## What do I do?

Given known city routes I walk through all the connections and answer questions such as:

```
is Philadelphia connected to San Diego?
```

where this might not be an obvious connection given.

In other words I have an algorithm that given the existing connections figures out new connections between the cities.

## Can you show me?

Sure.

Here is a [sample file](src/main/resources/cities.txt) with city connections:

```bash
[citimap/src/main/java]$ cat ../resources/cities.txt

Philadelphia, Pittsburgh
Boston, New York
Philadelphia, New York
Los Angeles, San Diego
New York, Croton-Harmon
St. Petersburg, Tampa
Orlando, Tampa
Chicago, New York
```

### compiling the program

```bash
[citimap/src/main/java]$ javac Connected.java
```

### running it

#### missed arguments

```bash
[citimap/src/main/java]$ java Connected foo

[PROBLEM]: I need exactly 3 arguments, but was given 1 arguments

this program reads a file with pairs of connected cities,
creates all the possible interconnections between them,
and helps humans to determine whether any two given cities are interconnected.

usage:       java Connected <path to a file with connections> <from> <to>
for example: java Connected ./cities.txt "New York" "Tampa"
```

#### running it for real

```bash
[citimap/src/main/java]$ java Connected ../resources/cities.txt "New York" "Tampa"
checking whether New York is connected to Tampa...  false
```

```bash
[citimap/src/main/java]$ java Connected ../resources/cities.txt "New York" "Pittsburgh"
checking whether New York is connected to Pittsburgh...  true
```
```bash
[citimap/src/main/java]$ java Connected ../resources/cities.txt "St. Petersburg" "Orlando"
checking whether St. Petersburg is connected to Orlando...  true
```
```bash
[citimap/src/main/java]$ java Connected ../resources/cities.txt "St. Petersburg" "Tampa"
checking whether St. Petersburg is connected to Tampa...  true
```
```bash
[citimap/src/main/java]$ java Connected ../resources/cities.txt "St. Petersburg" "New York"
checking whether St. Petersburg is connected to New York...  false
```
