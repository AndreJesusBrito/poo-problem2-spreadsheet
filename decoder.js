function decodeStuff(str) {
    let res = 0;
    for(let i = 0 ; i < str.length; i++) {
        res += (str[str.length - i - 1].charCodeAt(0)-64) * 26**i;
    }
    return res;
}
