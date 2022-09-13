package ca.jrvs.apps.grep;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp{

    //JavaGrepLambdaImp inherits all methods except 3 override methods

    public static void main(String[] args) {
        if(args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try{
            //calling parent method
            //but it will call override method
            javaGrepLambdaImp.process();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * Top level search workflow
     *
     * @throws IOException
     */
    @Override
    public void process() throws IOException {
        List<String> matchedLines = listFiles(getRootPath()).stream()
                .map(this::readLines)
                .flatMap(Collection::stream)
                .filter(this::containsPattern)
                .collect(Collectors.toList());
        writeToFile(matchedLines);
    }

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) throws IOException {
       List<File> files = Files.walk(Paths.get(rootDir))
               .filter(Files::isRegularFile)
               .map(Path::toFile)
               .collect(Collectors.toList());
       return files;
    }

    /**
     * Read a file and return all the lines
     *
     * @param inputFile file to read
     * @return all the lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        //List<String> lines = new ArrayList<>();
        try{
            return Files.lines(Paths.get(inputFile.getPath())).collect(Collectors.toList());

        }catch (IOException ex){
            throw new IllegalArgumentException("Input file is not a file");
        }

    }
}
