function insert(num) {
    console.log(num)
    $('#expression').val($('#expression').val() + num);
}

function equal() {
    let exp = $('#expression').val();
    if (exp) {
        try {
            $('#expression').val(parseFloat(eval(exp).toPrecision(9)));
        } catch (e) {
            alert(e);
        }

    }
}

function clean() {
    $('#expression').val('');
}

function back() {
    let exp = $('#expression').val();
    $('#expression').val(exp.substring(0, exp.length - 1));
}

function clicked (code) {
    console.log(code);
    if (code === 'Enter'){
        equal();
    }
    else if(code === '0'){
        insert(0);
    }
    else if(code === '1'){
        insert(1);
    }
    else if(code === '2'){
        insert(2);
    }
    else if(code === '3'){
        insert(3);
    }
    else if(code === '4'){
        insert(4);
    }
    else if(code === '5'){
        insert(5);
    }
    else if(code === '6'){
        insert(6);
    }
    else if(code === '7'){
        insert(7);
    }
    else if(code === '8'){
        insert(8);
    }
    else if(code === '9'){
        insert(9);
    }
    else if(code === '+'){
        insert('+');
    }
    else if(code === '-'){
        insert('-');
    }
    else if(code === '*'){
        insert('*');
    }
    else if(code === '/'){
        insert('/');
    }
    else if(code === '.'){
        insert('.');
    }
    else if(code === 'Backspace'){
        back();
    }
    else if (code === 'C' || code === 'c'){
        clean();
    }

}
document.onkeydown = function (e) {
    let code;
    e.preventDefault();

    if (e.key !== undefined) {
        code = e.key;
    } else if (e.keyIdentifier !== undefined) {
        code = e.keyIdentifier;
    } else if (e.keyCode !== undefined) {
        code = e.keyCode;
    }

    clicked(code);
};