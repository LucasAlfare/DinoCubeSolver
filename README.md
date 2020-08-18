# The puzzle

Dino cube is a puzzle similar to the Rubik's Cube, however, its movimentations works different of the classic 3x3x3 cube.

While 3x3x3 moves faces, Dino Cube moves its corners. Each moving corner contains 3 "edge" pieces, which permutes its positions by itself.

Jaap Scherphuis has a great detailed explanation about this puzzle in his website: https://www.jaapsch.net/puzzles/dinocube.htm.

# The apporach

This works in a simple approach of searching for puzzles solutions. Firstly are indexed all the permutations of the puzzle. Each permutation are turned into a index in the range of 0 to n-1, where n is the number of different permutations to this puzzle. The methods for this are in the `IndexMapping.java` class.

Each index, then, is associated to a integer number, that represents the distance/depth (how many moves) from the solved state.

After the indexing phase the search just uses the created database as a lookup table. For each state that should be solved, the search tries all possible moves and checks if the current distance of the state is less than the previous one. This loops while the current depth is greater than 0. 


# How this program works

To achieve solutions, the class `Generator.java` performs the indexing phase and stores the results in a binary file of extension `.dino`. The file has 19491 KB.

That file is loaded by the class `Search.java`, which also provides the static method `search(State)`, which returns a list containing the indexes of each move that solves the state passed.

This repository already contains a pre-built file to avoid regenerating it.

# TODO

- Refactor/convert the file structure to reduce disk size;
- Implement better manipulations of `Search.java` class.