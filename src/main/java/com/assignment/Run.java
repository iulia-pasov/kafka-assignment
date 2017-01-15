package com.assignment;

/**
 * Executable for Java producers and stream
 */
public class Run {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("Must have at least one argument");
        }
        switch (args[0]) {
            case "whole-producer":
                WholeTextProducer.main(args);
                break;
            case "message-producer":
                MessageProducer.main(args);
                break;
            case "message-stream":
                MessageStreamDemo.main(args);
                break;
            default:
                throw new IllegalArgumentException("Cannot create a " + args[0]);
        }
    }
}
