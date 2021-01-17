package com.productengine.test.task1.navigator;

import com.productengine.test.task1.model.FileObject;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class IterationNavigator implements Navigator {

    @Override
    public List<FileObject> getFilesThatMatch(String rootPath, int depth, String mask) {
        if(depth < 0) {
            throw new IllegalArgumentException("Depth must not be negative!");
        }

        File file = new File(rootPath);
        LinkedList<FileObject> stack = new LinkedList<>();
        List<FileObject> fileNames = new ArrayList<>();
        int depthCounter;

        stack.push(new FileObject(0, file.getPath()));

        while(!stack.isEmpty()) {
            FileObject dir = stack.pop();
            file = new File(dir.getPath());
            if(file.list() != null) {
                fileNames.addAll(search(file.list(), dir.getDepth(), mask));
            }

            if(dir.getDepth() < depth) {
                depthCounter = dir.getDepth() + 1;
                List<FileObject> directories = getDirectories(depthCounter, file);
                directories.forEach(stack::push);
            }
        }

        return fileNames;
    }

    private List<FileObject> getDirectories(int depth, File file) {
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        if(directories != null) {
            return Arrays.stream(directories).map(dir -> new FileObject(depth, file.getPath() + "/" + dir)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<FileObject> search(String[] names, int depth, String mask) {
        return Arrays.stream(names).filter(n -> n.contains(mask)).map(n -> new FileObject(depth, n)).collect(Collectors.toList());
    }

}
