const nPerms = 19958400;
const map = [];
let indexed = false;

function move(state, m) {
    let a, b, c;

    switch (Math.abs(m)) {
        case 1:
            a = 0;
            b = 3;
            c = 6;
            break;
        case 2:
            a = 0;
            b = 7;
            c = 1;
            break;
        case 3:
            a = 1;
            b = 4;
            c = 2;
            break;
        case 4:
            a = 2;
            b = 5;
            c = 3;
            break;
        case 5:
            a = 4;
            b = 9;
            c = 8;
            break;
        case 6:
            a = 7;
            b = 10;
            c = 9;
            break;
    }

    let tmp = state[a];
    state[a] = state[c];
    state[c] = state[b];
    state[b] = tmp;

    if (m < 0) {
        tmp = state[a];
        state[a] = state[c];
        state[c] = state[b];
        state[b] = tmp;
    }

    return state;
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
    let permutation = [];

    permutation[length - 1] = 1;
    permutation[length - 2] = 0;
    for (let i = length - 3; i >= 0; i--) {
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

function doIndexing() {
    if (indexed) return;

    for (let i = 0; i < nPerms; i++) {
        map[i] = -1;
    }

    let depth = 0;
    let nVisited = 0;

    map[0] = depth;

    const a = Date.now();
    do {
        nVisited = 0;
        for (let i = 0; i < nPerms; i++) {
            if (map[i] === depth) {
                const state = indexToEvenPermutation(i, 11);
                for (let m = -6; m <= 6; m++) {
                    if (m !== 0) {
                        let s = move(JSON.parse(JSON.stringify(state)), m);
                        let idx = evenPermutationToIndex(s);
                        if (map[idx] === -1) {
                            map[idx] = depth + 1;
                            nVisited++;
                        }
                    }
                }
            }
        }
        depth++;
        console.log(nVisited, 'positions at depth', depth);
    } while (nVisited > 0);
    console.log(Date.now() - a, 'milliseconds to index.');
    indexed = true;
}

doIndexing()
