package com.assignment;

import java.io.IOException;
/**
 * Executable for producers
 */
public class Run {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Must have at least one argument");
        }
        switch (args[0]) {
            case "whole":
                WholeTextProducer.main(args);
                break;
            case "message":
                MessageProducer.main(args);
                break;
            default:
                throw new IllegalArgumentException("Cannot create such producer " + args[0]);
        }
    }
}
