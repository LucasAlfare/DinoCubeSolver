let distances = null;

function loadDistancesFile(file) {
    const reader = new FileReader();
    reader.onload = function () {
        const {result} = reader;
        distances = new Int8Array(new DataView(result).buffer);
    }
    reader.readAsArrayBuffer(file);
    document.getElementById("search").disabled = false;
}

function State(permutation) {
    return {
        permutation: permutation,
        multiply: function (move) {
            const p = [];
            for (let i = 0; i < 11; i++) {
                p[i] = permutation[move.permutation[i]];
            }
            return new State(p);
        },
        copy: function (state) {
            for (let i = 0; i < 11; i++) {
                permutation[i] = state.permutation[i];
            }
        }
    }
}

function IndexMapping() {
    return {
        evenPermutationToIndex: function (permutation) {
            let index = 0;
            for (let i = 0; i < permutation.length; i++) {
                index *= permutation.length - i;
                for (let j = i + 1; j < permutation.length; j++) {
                    if (permutation[i] > permutation[j]) {
                        index++;
                    }
                }
            }

            return index;
        },

        indexToEvenPermutation: function (index, length) {
            let sum = 0;
            const permutation = []; //bytes
            permutation[length - 1] = 1;
            permutation[length - 2] = 0;

            for (let i = length - 3; i >= 0; i--) {
                permutation[i] = Math.round(index % (length - i));
                sum += permutation[i];
                index /= Math.round(length - i);
                for (let j = i + 1; j < length; j++) {
                    if (permutation[j] >= permutation[i]) {
                        permutation[j]++;
                    }
                }
            }

            if (sum % 2 !== 0) {
                const tmp = permutation[permutation.length - 1];
                permutation[permutation.length - 1] = permutation[permutation.length - 2];
                permutation[permutation.length - 2] = tmp;
            }

            return permutation;
        }
    }
}

const indexMapping = new IndexMapping();

const m1 = new State([6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10]);
const m2 = new State([1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10]);
const m3 = new State([0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10]);
const m4 = new State([0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10]);

const m5 = new State([0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10]);
const m6 = new State([0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7]);

const moves = [
    m1, m1.multiply(m1),
    m2, m2.multiply(m2),
    m3, m3.multiply(m3),
    m4, m4.multiply(m4),

    m5, m5.multiply(m5),
    m6, m6.multiply(m6)
];

const names = [1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6];

function search(target) {
    let solution = '';
    while (true) {
        const currentIndex = indexMapping.evenPermutationToIndex(target.permutation);
        if (distances[currentIndex] === 0) {
            console.log(`solucao encontrada! Solucao: ${solution}`);
            return;
        }

        for (let i = 0; i < moves.length; i++) {
            const aux = target.multiply(moves[i]);
            const nextIndex = indexMapping.evenPermutationToIndex(aux.permutation);
            if (distances[nextIndex] < distances[currentIndex]) {
                solution += names[i] + " ";
                target.copy(aux);
                break;
            }
        }
    }
}
