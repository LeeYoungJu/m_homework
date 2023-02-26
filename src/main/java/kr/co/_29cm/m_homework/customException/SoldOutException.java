package kr.co._29cm.m_homework.customException;

public class SoldOutException extends Exception {

    public SoldOutException() {
        super();
    }

    public SoldOutException(String msg) {
        super(msg);
    }
}
