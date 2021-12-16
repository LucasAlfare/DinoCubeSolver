function multiply(state, move) {
    const permutation = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    for (let i = 0; i < N_PIECES; i++) {
        permutation[i] = state[move[i]];
    }

    return permutation;
}

function evenPermutationToIndex(permutation) {
    let index = 0;
    for (let i = 0; i < permutation.length - 2; i++) {
        index *= permutation.length - i;
        for (let j = i + 1; j < permutation.length; j++) {
            if (permutation[i] > permutation[j]) {
                index++;
            }
        }
    }

    return index;
}

function indexToEvenPermutation(index, length) {
    let sum = 0;
    const permutation = [];
    for (let i = 0; i < length; i++) permutation.push(0);

    permutation[length - 1] = 1;
    permutation[length - 2] = 0;
    for (let i = length - 3; i >= 0; i--) {
        //is the Math.round() the right function equivalent to a (byte) cast?
        permutation[i] = Math.round((index % (length - i)));
        sum += permutation[i];
        index /= length - i;
        for (let j = i + 1; j < length; j++) {
            if (permutation[j] >= permutation[i]) {
                permutation[j]++;
            }
        }
    }

    if (sum % 2 !== 0) {
        const temp = permutation[permutation.length - 1];
        permutation[permutation.length - 1] = permutation[permutation.length - 2];
        permutation[permutation.length - 2] = temp;
    }

    return permutation;
}

function writeBinary(content, pathname) {
    // TODO
}

const N_PIECES = 11;
const n_PERMUTATIONS = 19_958_400;

const m1 = [6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10];
const m2 = [1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10];
const m3 = [0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10];
const m4 = [0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10];

const m5 = [0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10];
const m6 = [0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7];

const moves = [
    m1, multiply(m1, m1),
    m2, multiply(m2, m2),
    m3, multiply(m3, m3),
    m4, multiply(m4, m4),
    m5, multiply(m5, m5),
    m6, multiply(m6, m6)
];

const names = [1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6];

function perform() {
    // TODO
}
