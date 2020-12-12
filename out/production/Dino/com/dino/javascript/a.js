const exemploArquivo =
    [
         8,  1,  1,  5,  1,  3,  4,  7,  0, 9,
        10,  6,  2,  1,  2,  4,  5,  1,  7, 8,
         9,  4,  5,  6,  8,  4,  1, 10, 10, 5 //(...)
    ];

const mapa = {};
for (let i = 0; i < exemploArquivo.length; i++) {
    if (!mapa[exemploArquivo[i]]) {
        mapa[exemploArquivo[i]] = [];
    }
    mapa[exemploArquivo[i]].push(i);
}

// ordena as listas de índices
// de cada chave de "distância"
for (let o in mapa) {
    mapa[o].sort(function (a, b) {
        return a - b;
    });
}

console.log(mapa);

