class Ceil{
    constructor(isBomb, isFlagged){
        this.bomb = isBomb;
        this.flag = isFlagged;
    }
}

const board = $('#board');
const flags = $('#flags');

let rows = 8;
let cols = 8;

let bombsCount = 10;
let flagCount = 10;
let intervalId;
let isActive = false;
let counter = 0;

let field;

const relation = {
    5:6,
    6:6,
    7:6,
    8:7,
    9:7,
    10:8,
    11:8,
    12:8,
    13:9,
    14:9,
    15:9,
    16:10,
    17:10,
    18:10,
    19:11,
    20:11
};

function createBoard(rows, cols) {
    board.empty();
    for (let i = 0; i < rows; i++) {
        const row = $('<div>').addClass('row');
        for (let j = 0; j < cols; j++) {
            const col = $('<div>')
                .addClass('col hidden')
                .attr('data-row', i)
                .attr('data-col', j);
            row.append(col);
        }
        board.append(row);
    }
}

function restart() {
    field = initializeField(rows, cols);
    flagCount = bombsCount;
    flags.html(flagCount);
    createBoard(rows, cols);
}

function gameOver(isWin){
    let message = isWin ? 'You win!' : 'Game over!';
    for (let i = 0; i < rows; i++){
        for (let j = 0; j < cols; j++){
            if (field[i][j].bomb){
                const cell = $(`.col[data-row=${i}][data-col=${j}]`);
                cell.addClass('mine');
            }
        }
    }
    $('.col.mine').append($('<i>').addClass('fa fa-bomb'));

    setTimeout(function () {
        alert(message);
        timer();
        restart();
    }, 1000);
}

function reveal(oi, oj){
    const seen = {};

    function helper(i, j) {
        if(i >= rows || j >= cols || i < 0 || j < 0) return;
        const key = `${i} ${j}`;
        if (seen[key]) return;
        const cell = $(`.col[data-row=${i}][data-col=${j}]`);
        const mineCount = getMineCount(i, j);

        if (cell.hasClass('flag')){
            cell.removeClass('flag');
            cell.addClass('hidden');
            flagCount++;
            flags.html(flagCount);
        }

        if (!cell.hasClass('hidden') || field[i][j].bomb){
            return;
        }

        cell.removeClass('hidden');

        if (mineCount){
            cell.text(mineCount);
            return;
        }

        for (let di = -1; di <= 1; di++) {
            for (let dj = -1; dj <= 1; dj++){
                helper(i + di, j + dj);
            }
        }
    }

    helper(oi, oj);
}

function getMineCount(i, j){
    let count = 0;
    for (let di = -1; di <= 1; di++){
        for (let dj = -1; dj <= 1; dj++){
            const ni = i + di;
            const nj = j + dj;
            if(ni >= rows || nj >= cols || ni < 0 || nj < 0) continue;
            if(field[ni][nj].bomb) count++;
        }
    }
    return count;
}


board.on('click', '.col.hidden', function () {
    if(isActive) {
        const cell = $(this);
        const row = cell.data('row');
        const col = cell.data('col');

        if (field[row][col].bomb) {
            gameOver(false);
        } else {
            reveal(row, col);
            const isGameOver = $('.col.hidden').length + $('.col.flag').length === bombsCount;
            if (isGameOver) gameOver(true);
        }
    } else {
        alert("Game wasn't started");
    }
});

$.event.special.rightclick = {
    bindType: "mousedown",
    delegateType: "mousedown",
    handle: function (evt) {
        if (evt.button === 2) {
            let handleObj = evt.handleObj;
            evt.type = handleObj.origType;
            let ret = handleObj.handler.apply(this, arguments);
            evt.type = handleObj.type;

            return ret;
        }
    }
};

board.on('rightclick', '.col.hidden', function () {
    if(isActive) {
        const cell = $(this);
        const row = cell.data('row');
        const col = cell.data('col');
        if (flagCount) {
            cell.removeClass('hidden');
            cell.addClass('flag');
            flagCount--;
            flags.html(flagCount);
            field[row][col].flag = !field[row][col].flag;
        }
    }
    });

board.on('rightclick', '.col.flag', function () {
    const cell = $(this);
    const row = cell.data('row');
    const col = cell.data('col');
    cell.removeClass('flag');
    cell.addClass('hidden');
    flagCount++;
    flags.html(flagCount);
    field[row][col].flag = !field[row][col].flag;
});

function timer(){
    if (intervalId) {
        counter = 0;
        clearInterval(intervalId);
    }
    intervalId = setInterval(function () {
        ++counter;
        $('#seconds').html(counter);
    }, 1000);
}

$("#footerForm").on('submit', function(request) {
    try {
        bombsCount = Number($('#bombs').val());
        if (bombsCount > 20 || bombsCount < 5)
            throw new TypeError('Incorrect number of bombs');
        rows = cols = relation[bombsCount];
        isActive = true;
        timer();
        restart();
        request.preventDefault();
    } catch (TypeError) {
        alert('Incorrect number of bombs');
    }
});

function initializeField(rows, cols) {
    let field = [];
    for (let i=0; i < rows; i++){
        field[i] = [];
        for (let j=0; j < cols; j++){
            field[i][j] = new Ceil(false, false);
        }
    }

    let bombsPlaced = 0;
    while(bombsPlaced !== bombsCount){
        let x = Math.floor(Math.random() * rows);
        let y = Math.floor(Math.random() * rows);
        if (!field[x][y].bomb) {
            field[x][y].bomb = true;
            bombsPlaced++;
        }
    }

    return field;
}

restart();