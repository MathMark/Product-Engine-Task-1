package com.productengine.test.task1.navigator;

import com.productengine.test.task1.model.FileObject;
import java.util.List;

public interface Navigator {
    /**
     * Finds files from a root directory till the specified depth that
     * contains entered string in their names.
     *
     * @param rootPath path to a root directory.
     *
     * @param depth a depth till which the method will search for files.
     *              Should be only positive number.
     *              If depth = 0 then the method will search for files
     *              in the root directory.
     *
     * @param mask a string that files should contain mask in their names.
     *
     * @return list of FileObject that contains names of found files and
     * directories, their paths, and depth.
     */
    List<FileObject> getFilesThatMatch(String rootPath, int depth, String mask);
}
