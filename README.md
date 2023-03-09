# The puzzle

Dino cube is a puzzle similar to the Rubik's Cube, however, its movimentations works different of the classic 3x3x3 cube.

While 3x3x3 moves faces, Dino Cube moves its corners. Each moving corner contains 3 "edge" pieces, which permutes its positions by itself.

Jaap Scherphuis has a great detailed explanation about this puzzle in his website: https://www.jaapsch.net/puzzles/dinocube.htm.

# Puzzle abstraction

There's many ways to represent any puzzle with virtual data. For this project, we are considering that all pieces of the Dino is a number, from `0` to `11`, since Dino puzzle contains 12 different pieces.

Also, Dino is a puzzle that only is affected by permutation. In other words, their pieces only can live in one position in the same way, they can't be "twisted" around themselves. Twisting a piece like that turns the puzzle unsolvable. For this reason, was choose a simple array to represent the Dino cube, such as:

```
int[] theSolvedState = new int[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
```

Using arrays to do this is a classic approach, however, once we have only 12 different items, wee can use hexadecimals to perform this abstraction. For example, the single number `0x123456789abc` can represent the solved state of the Dino puzzle.

Using hexadecimals make some steps easy and fast to process, however the abstraction in this form stil in progress right now.

# The apporach

To achieve solutioning we have two phases. Firstly, are indexed all the possible permutation (distinct states) of the puzzle. In this way, each permutation is encoded into an index number in the range of `0` to `n-1`, where `n` is the number of different permutations to this puzzle. The methods for this are in the `IndexMapping.java` class.

Each index, then, is associated to an `Int` variable. This number represents the distance/depth (how many moves) the current state is from the solved state. Note that this indexing can be done with some approaches, such a BFS/DFS graph traversal techinique.

After the indexing phase we perform a Searching phase, which consists in take some scrambled Dino state and check its correspondent calculated distance/depth in the previous phase. Knowing the actual distance of the target scrambled state is enough to find the next nearest state, after that we replace the current Dino state (the first input state) to the last searched (the nearest one). Repeating this process the next nearest state eventually will be the state with depth/distance mapped to the value `0`, which is the solved state.

Note that the Searching phase is really fast sincec we have the previous indexes properly processed.

# How this program works

Knowing the implementation approach we can write some code to it. For now, this project creates a file that contais all distances/depthes numbers sequentially. Once each depth value is mapped to an index (`0` to `n-1`) this means that each byte of the file represents the depth/distance of its current position inside the file itself. Also is important to notice that was choose the arbitrary extension "`.dino`" to the file and, after indexing phase, this file achieves 19491 KB of disk size.

With the file in hand we can use it to search solutions to any Dino cube scrambled state. We just need to load the file into some array(s) and perform the Search approach discussed earlier.

This repository already contains a pre-built file to avoid regenerating it.

# TODO

- Refactor/convert the file structure to reduce disk size;
- Implement better manipulations of `Search.java` class;
- Re-implement using hexadecimals approach;
- Move project to Kotlin language/environment;
- Finish Javascript/web version (?);
- General clean up.
