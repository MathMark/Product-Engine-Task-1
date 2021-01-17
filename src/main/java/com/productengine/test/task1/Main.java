package com.productengine.test.task1;

import com.productengine.test.task1.model.FileObject;
import com.productengine.test.task1.navigator.IterationNavigator;
import com.productengine.test.task1.navigator.Navigator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter root path: ");
            String rootPath = reader.readLine();
            File file = new File(rootPath);
            if (!file.exists()) {
                throw new IllegalArgumentException("Incorrect file path. Directory does not exist");
            }
            if (!file.isDirectory()) {
                throw new IllegalArgumentException("You need to enter a path to a directory.");
            }

            System.out.print("Enter depth: ");
            int depth = Integer.parseInt(reader.readLine());
            if (depth < 0) {
                throw new IllegalArgumentException("Depth must not be negative!");
            }

            System.out.print("Enter mask: ");
            String mask = reader.readLine();

            Navigator navigator = new IterationNavigator();
            List<FileObject> files = navigator.getFilesThatMatch(rootPath, depth, mask);

            System.out.println("\nResult: \n");
            if(files.size() == 0) {
                System.out.println("No files that match the mask found.");
            } else {
                for(int i = 0; i <= depth; i++) {
                    System.out.println("Depth " + i + ": ");
                    int finalI = i;
                    files.parallelStream().filter(f -> f.getDepth() == finalI).forEach(System.out::println);
                    System.out.println();
                }
            }
        }catch (IOException | IllegalArgumentException e) {
            logger.severe(e.getMessage());
        }
    }
}
