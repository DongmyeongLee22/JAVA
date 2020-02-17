package chapter1.item1;

public interface Number {

    static Number createNumber(int num){
        if (num > -1000 && num < 1000){
            return new SmallNum();
        }
        return new BigNum();
    }
}

class SmallNum implements Number{

}

class BigNum implements Number{

}
