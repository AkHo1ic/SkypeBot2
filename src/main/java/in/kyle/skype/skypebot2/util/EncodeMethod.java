package in.kyle.skype.skypebot2.util;

public enum EncodeMethod {
    ENCODE, DECODE;
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}