package main.com.romanov;

public enum  Colour {
    WHITE, BLACK;

    public Colour opposite(){
        return this == WHITE ? BLACK : WHITE;
    }
}
